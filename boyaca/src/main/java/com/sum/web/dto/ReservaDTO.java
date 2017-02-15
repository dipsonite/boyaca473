package com.sum.web.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaDTO {
	
	@JsonCreator
	public ReservaDTO(@JsonProperty("reservaId") Integer reservaId,
			@JsonProperty("unidadFuncional") Integer uf,
			@JsonProperty("fecha") Date fecha,
			@JsonProperty("turno") String turno) {
		this.reservaId = reservaId;
		this.uf = uf;
		this.fecha = fecha;
		this.turno = turno;
	}

	private Integer reservaId;
	private Integer uf;
	private Date fecha;
	private String turno;
	
	public Integer getReservaId() {
		return reservaId;
	}

	public Integer getUf() {
		return uf;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getTurno() {
		return turno;
	}
}
