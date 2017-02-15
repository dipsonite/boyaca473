package com.sum.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sum.domain.Reserva;
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
	
	@RequestMapping("/anio/{anio}/mes/{mes}")
    @ResponseBody
    public ResponseEntity<List<ReservaDTO>> retrieveReservas(@PathVariable("anio") Integer anio,
    		@PathVariable("mes") Integer mes) {
		LOGGER.debug("> retrieveReservas");
		List<ReservaDTO> reservasDTO = new ArrayList<ReservaDTO>();
        List<Reserva> reservas = reservaService.buscarReservasParaMesYAnio(anio, mes);
        for (Reserva reserva : reservas) {
        	reservasDTO.add(ReservaTranslator.getReservaDTO(reserva));
		}
        LOGGER.debug("< retrieveReservas");
		return new ResponseEntity<List<ReservaDTO>>(reservasDTO, HttpStatus.OK);
    }
}
