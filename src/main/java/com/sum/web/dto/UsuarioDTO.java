package com.sum.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioDTO {

	@JsonCreator
	public UsuarioDTO(@JsonProperty("uf") Integer uf,
			@JsonProperty("piso") String piso,
			@JsonProperty("depto") String depto,
			@JsonProperty("email") String email,
			@JsonProperty("email2") String email2,
			@JsonProperty("password") String password) {
		this.uf = uf;
		this.piso = piso;
		this.depto = depto;
		this.email = email;
		this.email2 = email2;
		this.password = password;
	}

	private Integer uf;
	private String piso;
	private String depto;
	private String email;
	private String email2;
	private String password;

	public Integer getUf() {
		return uf;
	}
	public void setUf(Integer uf) {
		this.uf = uf;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getDepto() {
		return depto;
	}
	public void setDepto(String depto) {
		this.depto = depto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



}
