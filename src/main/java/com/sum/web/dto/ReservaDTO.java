package com.sum.web.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaDTO {
	
	@JsonCreator
	public ReservaDTO(@JsonProperty("id") Integer reservaId,
			@JsonProperty("uf") Integer uf,
			@JsonProperty("piso") String piso,
			@JsonProperty("depto") String depto,
			@JsonProperty("start") Timestamp inicio,
			@JsonProperty("end") Timestamp fin,
			@JsonProperty("email") String email,
			@JsonProperty("email2") String email2) {
		this.id = reservaId;
		this.start = inicio;
		this.end = fin;
		this.piso = piso;
		this.depto = depto;
		this.uf = uf;
		this.email = email;
		this.email2 = email2;
	}

	private Integer id;
	private String piso;
	private String depto;
	private Timestamp start;
	private Timestamp end;
	private Integer uf;
	private String email;
	private String email2;
	
	public String getDepto() {
		return depto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUf() {
		return uf;
	}

	public String getEmail() {
		return email;
	}

	public String getEmail2() {
		return email2;
	}

	public String getPiso() {
		return piso;
	}
	
	public Timestamp getStart() {
		return start;
	}
	
	public Timestamp getEnd() {
		return end;
	}
}
