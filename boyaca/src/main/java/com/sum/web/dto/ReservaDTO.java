package com.sum.web.dto;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaDTO {
	
	@JsonCreator
	public ReservaDTO(@JsonProperty("id") Integer reservaId,
			@JsonProperty("title") Integer uf,
			@JsonProperty("date") Date fecha,
			@JsonProperty("start") Time inicio,
			@JsonProperty("end") Time fin) {
		this.id = reservaId;
		this.start = inicio;
		this.date = fecha;
		this.end = fin;
		this.title = uf;
	}

	private Integer id;
	private Integer title;
	private Date date;
	private Time start;
	private Time end;
	
	public Integer getId() {
		return id;
	}

	public Integer getTitle() {
		return title;
	}
	
	public Date getDate() {
		return date;
	}

	public Time getStart() {
		return start;
	}
	
	public Time getEnd() {
		return end;
	}
}
