package com.sum.web.dto;

import com.sum.domain.Reserva;

public class ReservaTranslator {
	public static ReservaDTO getReservaDTO(Reserva reserva) {
		return new ReservaDTO(reserva.getId(), reserva.getUnidadFuncional(), 
				reserva.getFecha(), reserva.getInicio(), reserva.getFin());
	}
	
	public static Reserva translate(ReservaDTO dto) {
		return new Reserva(dto.getTitle(), dto.getDate(), dto.getStart(), dto.getEnd());
	}
}
