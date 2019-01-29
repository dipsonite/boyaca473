package com.sum.web.controller;

import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.domain.Usuario;
import com.sum.exceptions.ReservaInvalidaException;
import com.sum.service.ReservaService;
import com.sum.service.UsuarioService;
import com.sum.web.dto.ReservaDTO;
import com.sum.web.dto.ReservaTranslator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private static final Log LOGGER = LogFactory.getLog(ReservaController.class);

    private ReservaService reservaService;
    private UsuarioService usuarioService;

    public ReservaController(ReservaService service1, UsuarioService service2) {
        this.reservaService = service1;
        this.usuarioService = service2;
    }

    @RequestMapping(path = "/crear", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ReservaDTO> crearReserva(@RequestBody ReservaDTO dto) throws ReservaInvalidaException {
        LOGGER.debug("> crearReserva");
        Reserva res = new Reserva((Usuario) usuarioService.loadUserByUsername(String.valueOf(dto.getUf())), dto.getStart(), dto.getEnd());
        Reserva nuevaReserva = reservaService.crearNuevaReserva(res);
        dto.setId(nuevaReserva.getId());
        return new ResponseEntity<ReservaDTO>(dto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ReservaDTO>> recuperarReservas(@RequestBody ReservaCriteria criteria) {
        LOGGER.debug("> retrieveReservas");
        List<ReservaDTO> reservasDTO = new ArrayList<ReservaDTO>();
        List<Reserva> reservas = reservaService.buscarReservasConCriteria(criteria);
        for (Reserva reserva : reservas) {
            ReservaDTO dto = new ReservaTranslator().getReservaDTO(reserva);
            reservasDTO.add(dto);
        }
        LOGGER.debug("< retrieveReservas");
        return new ResponseEntity<List<ReservaDTO>>(reservasDTO, HttpStatus.OK);
    }

    @RequestMapping(path = "/modificar", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<ReservaDTO> actualizarReserva(@RequestBody ReservaDTO dto) throws ReservaInvalidaException {
        Reserva reserva = reservaService.modificarReserva(this.reservaService.buscarReservaPorId(dto.getId()), dto);
        return new ResponseEntity<ReservaDTO>(new ReservaTranslator().getReservaDTO(reserva), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/eliminar/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<HttpStatus> eliminarReserva(@PathVariable("id") Integer id) {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
