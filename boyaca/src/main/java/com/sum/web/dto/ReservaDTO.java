package com.sum.web.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaDTO {
	
	@JsonCreator
	public ReservaDTO(@JsonProperty("id") Integer reservaId,
			@JsonProperty("title") Integer uf,
			@JsonProperty("start") Timestamp inicio,
			@JsonProperty("end") Timestamp fin) {
		this.id = reservaId;
		this.start = inicio;
		this.end = fin;
		this.title = uf;
	}

	private Integer id;
	private Integer title;
	private Timestamp start;
	private Timestamp end;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTitle() {
		return title;
	}
	
	public Timestamp getStart() {
		return start;
	}
	
	public Timestamp getEnd() {
		return end;
	}
}
