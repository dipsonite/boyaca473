package com.sum.dao.criteria;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ReservaCriteria {
	
	private Date fechaMax;
	private Date fechaMin;
	private Time max;
	private Time min;
	
	@JsonCreator
	public ReservaCriteria(@JsonProperty("fechaMin") Date fechaMin, 
			@JsonProperty("fechaMax") Date fechaMax) {
		this.fechaMin = fechaMin;
		this.fechaMax = fechaMax;
	}

//	@JsonCreator
//	public ReservaCriteria(@JsonProperty("start") Time min, 
//			@JsonProperty("end") Time max) {
//		this.min = min;
//		this.max = max;
//	}
	
	public ReservaCriteria(Time min, Time max) {
		this.min = min;
		this.max = max;
	}

	public Date getFechaMax() {
		return fechaMax;
	}

	public void setFechaMax(Date fechaMax) {
		this.fechaMax = fechaMax;
	}

	public Date getFechaMin() {
		return fechaMin;
	}

	public void setFechaMin(Date fechaMin) {
		this.fechaMin = fechaMin;
	}

	public Time getMax() {
		return max;
	}

	public void setMax(Time max) {
		this.max = max;
	}

	public Time getMin() {
		return min;
	}

	public void setMin(Time min) {
		this.min = min;
	}
}