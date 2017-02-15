package com.sum.dao.criteria;

public class ReservaCriteria {
	
	public ReservaCriteria(Integer anio, Integer mes) {
		this.anio = anio;
		this.mes = mes;
	}

	private Integer anio;
	private Integer mes;
	
	public Integer getAnio() {
		return anio;
	}
	public Integer getMes() {
		return mes;
	}
}