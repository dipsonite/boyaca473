package com.sum.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sum.dao.ReservaDAO;
import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.exceptions.ReservaInvalidaException;
import com.sum.service.ReservaService;
import com.sum.web.dto.ReservaDTO;

@Service
public class ReservaServiceImpl implements ReservaService {

	private ReservaDAO dao;
	
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
		
 		if (ufConReservaActiva(reserva.getUnidadFuncional())) {
			throw new ReservaInvalidaException("La UF ya tiene una reserva activa. No es posible realizar otra reserva.");
		}
		
		if (hayReservaSolapada(reserva.getInicio(), reserva.getFin())) {
			throw new ReservaInvalidaException("Esta reserva se solapa con otra. Modifique las fechas de inicio y fin y reintente.");
		}
		
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
		if (hayReservaSolapada(dto.getStart(), dto.getEnd())) {
			throw new ReservaInvalidaException("Esta reserva se solapa con otra. Modifique las fechas de inicio y fin y reintente.");
		}
		if (!reserva.getUnidadFuncional().equals(dto.getTitle())) {
			reserva.setUnidadFuncional(dto.getTitle());
		}
		if (reserva.getInicio().compareTo(dto.getStart()) != 0) {
			reserva.setInicio(dto.getStart());
		}
		if (reserva.getFin().compareTo(dto.getEnd()) != 0) {
			reserva.setFin(dto.getEnd());
		}
		return dao.update(reserva);
	}

	@Override
	@Transactional(readOnly=true)
	public Reserva buscarReservaPorId(Integer id) {
		return dao.retrieve(id);
	}
	
	private boolean ufConReservaActiva(Integer uf) {
		ReservaCriteria criteria = new ReservaCriteria();
		criteria.setUf(uf);
		criteria.setMax(new Timestamp(System.currentTimeMillis()));
		if (dao.getReservasParaUfYFecha(criteria).isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean hayReservaSolapada(Timestamp inicio, Timestamp fin) {
		ReservaCriteria criteria = new ReservaCriteria(inicio, fin);
		if (dao.getReservasSolapadas(criteria).isEmpty()) {
			return false;
		}
		return true;
	}
}