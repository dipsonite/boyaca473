package com.sum.service;

import java.util.List;

import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.exceptions.ReservaInvalidaException;
import com.sum.web.dto.ReservaDTO;

public interface ReservaService {
	
	public Reserva buscarReservaPorId(Integer id);
	public List<Reserva> buscarReservasConCriteria(ReservaCriteria criteria);
	public Reserva crearNuevaReserva(Reserva reserva) throws ReservaInvalidaException;
	public void eliminarReserva(Integer id);
	public Reserva modificarReserva(Reserva reserva, ReservaDTO dto) throws ReservaInvalidaException;
}
