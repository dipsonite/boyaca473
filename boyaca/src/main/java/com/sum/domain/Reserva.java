package com.sum.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESERVA")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVA_ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "UF", nullable = false)
    private Integer unidadFuncional;
    
    @Column(name = "FECHA", nullable = false)
    private Date fecha;
    
    @Column(name = "INICIO", nullable = false)
    private Time inicio;
    
    @Column(name = "FIN", nullable = false)
    private Time fin;
    
    public Reserva() {
    
    }
    
    public Reserva(Integer unidadFuncional, Date fecha, Time inicio, Time fin) {
    	this.unidadFuncional = unidadFuncional;
    	this.fecha = fecha;
    	this.inicio = inicio;
    	this.fin = fin;
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
    
    public Time getInicio() {
    	return inicio;
    }
    
    public Time getFin() {
    	return fin;
    }
}
