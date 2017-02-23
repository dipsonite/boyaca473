package com.sum.service;

import java.util.List;

import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;

public interface ReservaService {
	
	public List<Reserva> buscarReservasConCriteria(ReservaCriteria criteria);
	public Reserva crearNuevaReserva(Reserva reserva);
	public void eliminarReserva(Integer id);
	public Reserva modificarReserva(Reserva reserva);
	
}
