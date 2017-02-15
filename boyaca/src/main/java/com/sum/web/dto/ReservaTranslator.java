package com.sum.web.dto;

import com.sum.domain.Reserva;

public class ReservaTranslator {
	public static ReservaDTO getReservaDTO(Reserva reserva) {
		return new ReservaDTO(reserva.getId(), reserva.getUnidadFuncional(),
				reserva.getFecha(), reserva.getTurno());
	}
}
