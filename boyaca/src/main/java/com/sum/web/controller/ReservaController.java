package com.sum.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.exceptions.ReservaInvalidaException;
import com.sum.service.ReservaService;
import com.sum.web.dto.ReservaDTO;
import com.sum.web.dto.ReservaTranslator;

@Controller
@RequestMapping("/reservas")
public class ReservaController {
	
	private static final Log LOGGER = LogFactory.getLog(ReservaController.class);
	
	ReservaService reservaService;
	
	public ReservaController(ReservaService service) {
		reservaService = service;
	}
	
	@RequestMapping(path="/crear", consumes="application/json", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ReservaDTO> crearReserva(@RequestBody ReservaDTO dto) throws ReservaInvalidaException {
		LOGGER.debug("> crearReserva");
		Reserva nuevaReserva = reservaService.crearNuevaReserva(ReservaTranslator.translate(dto));
		dto.setId(nuevaReserva.getId());
		return new ResponseEntity<ReservaDTO>(dto, HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/", consumes="application/json", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ReservaDTO>> recuperarReservas(@RequestBody ReservaCriteria criteria) {
		LOGGER.debug("> retrieveReservas");
		List<ReservaDTO> reservasDTO = new ArrayList<ReservaDTO>();
        List<Reserva> reservas = reservaService.buscarReservasConCriteria(criteria);
        for (Reserva reserva : reservas) {
        	ReservaDTO dto = ReservaTranslator.getReservaDTO(reserva);
        	reservasDTO.add(dto);
		}
        LOGGER.debug("< retrieveReservas");
		return new ResponseEntity<List<ReservaDTO>>(reservasDTO, HttpStatus.OK);
    }
	
	@RequestMapping(path="/modificar", method=RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public ResponseEntity<ReservaDTO> actualizarReserva(@RequestBody ReservaDTO dto) throws ReservaInvalidaException {
		Reserva reserva = reservaService.modificarReserva(this.reservaService.buscarReservaPorId(dto.getId()), dto);
		return new ResponseEntity<ReservaDTO>(ReservaTranslator.getReservaDTO(reserva), HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(path="/eliminar/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> eliminarReserva(@PathVariable("id") Integer id) {
		reservaService.eliminarReserva(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
