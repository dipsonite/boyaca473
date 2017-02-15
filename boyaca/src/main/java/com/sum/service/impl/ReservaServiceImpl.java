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
	public List<Reserva> buscarReservasParaMesYAnio(Integer anio, Integer mes) {
		ReservaCriteria criteria = new ReservaCriteria(anio, mes);
		return dao.getReservas(criteria);
	}
}