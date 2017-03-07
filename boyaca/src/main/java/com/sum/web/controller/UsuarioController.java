package com.sum.web.controller;

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

import com.sum.domain.Usuario;
import com.sum.exceptions.UsuarioException;
import com.sum.service.UsuarioService;
import com.sum.web.dto.UsuarioDTO;
import com.sum.web.dto.UsuarioTranslator;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private static final Log LOGGER = LogFactory.getLog(UsuarioController.class);
	
	UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService service) {
		usuarioService = service;
	}
	
//	@RequestMapping(path="/", consumes="application/json", method=RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<List<ReservaDTO>> recuperarReservas(@RequestBody ReservaCriteria criteria) {
//		LOGGER.debug("> retrieveReservas");
//		List<ReservaDTO> reservasDTO = new ArrayList<ReservaDTO>();
//        List<Reserva> reservas = reservaService.buscarReservasConCriteria(criteria);
//        for (Reserva reserva : reservas) {
//        	reservasDTO.add(ReservaTranslator.getReservaDTO(reserva));
//		}
//        LOGGER.debug("< retrieveReservas");
//		return new ResponseEntity<List<ReservaDTO>>(reservasDTO, HttpStatus.OK);
//    }
	
	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
	public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable("idUser") Integer id) {
		LOGGER.debug("Recuperamos el usuario por ID: " + id);
		Usuario user = (Usuario) usuarioService.loadUserByUsername(String.valueOf(id));
		return new ResponseEntity<UsuarioDTO>(UsuarioTranslator.getUsuarioDTO(user), HttpStatus.OK);
	}
	
	@RequestMapping(path="/modificar", method=RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public ResponseEntity<UsuarioDTO> actualizarReserva(@RequestBody UsuarioDTO dto) throws UsuarioException {
		Usuario esteUsuario = (Usuario) usuarioService.loadUserByUsername(String.valueOf(dto.getUf()));
		Usuario usuario = usuarioService.modificarUsuario(esteUsuario, dto);
		return new ResponseEntity<UsuarioDTO>(UsuarioTranslator.getUsuarioDTO(usuario), HttpStatus.ACCEPTED);
	}
	
//	@RequestMapping(path="/eliminar/{id}", method=RequestMethod.DELETE)
//	@ResponseBody
//	public ResponseEntity<HttpStatus> eliminarReserva(@PathVariable("id") Integer id) {
//		usuarioService.eliminarReserva(id);
//		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
//	}
	
}
