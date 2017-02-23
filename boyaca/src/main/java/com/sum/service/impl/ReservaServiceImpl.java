package com.sum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sum.dao.ReservaDAO;
import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.service.ReservaService;

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
		return dao.getReservas(criteria);
	}
	
	@Transactional
	@Override
	public Reserva crearNuevaReserva(Reserva reserva) {
		return dao.create(reserva);
	}
	
	@Transactional
	@Override
	public void eliminarReserva(Integer id) {
		dao.delete(id);
	}

	@Override
	public Reserva modificarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}
}