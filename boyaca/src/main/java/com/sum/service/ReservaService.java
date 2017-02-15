package com.sum.service;

import java.util.List;

import com.sum.domain.Reserva;

public interface ReservaService {
	
	public List<Reserva> buscarReservasParaMesYAnio(Integer anio, Integer mes);
	
}
