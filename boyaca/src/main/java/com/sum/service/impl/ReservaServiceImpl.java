package com.sum.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sum.dao.ReservaDAO;
import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.domain.Usuario;
import com.sum.exceptions.ReservaInvalidaException;
import com.sum.service.ReservaService;
import com.sum.service.UsuarioService;
import com.sum.web.dto.ReservaDTO;

@Service
public class ReservaServiceImpl implements ReservaService {

	private ReservaDAO dao;
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	public ReservaServiceImpl(ReservaDAO reservaDAO) {
		this.dao = reservaDAO;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Reserva> buscarReservasConCriteria(ReservaCriteria criteria) {
		return dao.getReservasEntreFechas(criteria);
	}
	
	@Transactional
	@Override
	public Reserva crearNuevaReserva(Reserva reserva) throws ReservaInvalidaException {
		validacionesParaReservar(reserva);
		return dao.create(reserva);
	}
	
	@Transactional
	@Override
	public void eliminarReserva(Integer id) {
		dao.delete(id);
	}
	
	@Transactional
	@Override
	public Reserva modificarReserva(Reserva reserva, ReservaDTO dto) throws ReservaInvalidaException {
		Usuario nuevaUF = (Usuario) userService.loadUserByUsername(String.valueOf(dto.getUf()));
		if (!reserva.getUnidadFuncional().getUsername().equals(nuevaUF.getUsername())) {
			reserva.setUnidadFuncional(nuevaUF);
		}
		if (reserva.getInicio().compareTo(dto.getStart()) != 0) {
			reserva.setInicio(dto.getStart());
		}
		if (reserva.getFin().compareTo(dto.getEnd()) != 0) {
			reserva.setFin(dto.getEnd());
		}
		validacionesParaReservar(reserva);
		return dao.update(reserva);
	}

	private void validacionesParaReservar(Reserva reserva) throws ReservaInvalidaException {
		if (reserva.getUnidadFuncional() == null) {
			throw new ReservaInvalidaException("La UF ingresada no existe. Corríjala y vuelva a probar.");
		}
		if (ufConReservaActiva(reserva.getUnidadFuncional(), reserva.getId())) {
			throw new ReservaInvalidaException("La UF ya tiene una reserva activa. No es posible realizar otra reserva.");
		}
		if (hayReservaSolapada(reserva.getId(), reserva.getInicio(), reserva.getFin())) {
			throw new ReservaInvalidaException("Esta reserva se solapa con otra. Modifique las fechas de inicio y fin y reintente.");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public Reserva buscarReservaPorId(Integer id) {
		return dao.retrieve(id);
	}
	
	private boolean ufConReservaActiva(Usuario uf, Integer idReserva) {
		ReservaCriteria criteria = new ReservaCriteria();
		criteria.setIdReserva(idReserva);
		criteria.setUf(Integer.valueOf(uf.getUsername()));
		criteria.setMax(new Timestamp(System.currentTimeMillis()));
		if (dao.getReservasParaUfYFecha(criteria).isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean hayReservaSolapada(Integer idReserva, Timestamp inicio, Timestamp fin) {
		ReservaCriteria criteria = new ReservaCriteria(inicio, fin);
		criteria.setIdReserva(idReserva);
		if (dao.getReservasSolapadas(criteria).isEmpty()) {
			return false;
		}
		return true;
	}
}