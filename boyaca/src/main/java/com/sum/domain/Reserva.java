package com.sum.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESERVA")
public class Reserva {

	@Id
    @GeneratedValue
    @Column(name = "RESERVA_ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "UF", nullable = false)
    private Integer unidadFuncional;

    @Column(name = "FECHA", nullable = false)
    private Date fecha;
    
    @Column(name = "TURNO", nullable = false)
    private String turno;
    
    public Reserva(Integer unidadFuncional, Date fecha, String turno) {
    	this.unidadFuncional = unidadFuncional;
    	this.fecha = fecha;
    	this.turno = turno;
    }
    
    public Integer getId() {
    	return id;
    }
    
    public Integer getUnidadFuncional() {
    	return unidadFuncional;
    }
    
    public Date getFecha() {
    	return fecha;
    }
    
    public String getTurno() {
    	return turno;
    }
}
