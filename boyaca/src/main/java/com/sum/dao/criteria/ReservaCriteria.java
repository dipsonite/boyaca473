package com.sum.dao.criteria;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaCriteria {
	
	private Integer idReserva;
	private Integer uf;
	private Timestamp max;
	private Timestamp min;
	
	public ReservaCriteria() {
		
	}
	
	@JsonCreator
	public ReservaCriteria(@JsonProperty("min") Timestamp fechaMin, 
			@JsonProperty("max") Timestamp fechaMax) {
		this.min = fechaMin;
		this.max = fechaMax;
	}

	public Integer getIdReserva() {
		return idReserva;
	}
	
	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}
	
	public Integer getUf() {
		return uf;
	}

	public void setUf(Integer uf) {
		this.uf = uf;
	}

	public Timestamp getMax() {
		return max;
	}

	public void setMax(Timestamp max) {
		this.max = max;
	}

	public Timestamp getMin() {
		return min;
	}

	public void setMin(Timestamp min) {
		this.min = min;
	}
}