package com.sum.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "UF", nullable = false)
    private Integer unidadFuncional;
    
    @Column(name = "inicio", nullable = false)
    private Timestamp inicio;
    
    @Column(name = "fin", nullable = false)
    private Timestamp fin;
    
    public Reserva() {
    
    }
    
    public Reserva(Integer unidadFuncional, Timestamp inicio, Timestamp fin) {
    	this.unidadFuncional = unidadFuncional;
    	this.inicio = inicio;
    	this.fin = fin;
    }
    
    public Integer getId() {
    	return id;
    }
    
    public Integer getUnidadFuncional() {
    	return unidadFuncional;
    }
    
    public void setUnidadFuncional(Integer unidadFuncional) {
		this.unidadFuncional = unidadFuncional;
	}

	public Timestamp getInicio() {
    	return inicio;
    }
    
    public void setInicio(Timestamp inicio) {
		this.inicio = inicio;
	}

	public Timestamp getFin() {
    	return fin;
    }

	public void setFin(Timestamp fin) {
		this.fin = fin;
	}
}
