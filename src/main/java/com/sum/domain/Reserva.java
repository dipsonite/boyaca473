package com.sum.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UF")
    private Usuario unidadFuncional;

    @Column(name = "inicio", nullable = false)
    private Timestamp inicio;

    @Column(name = "fin", nullable = false)
    private Timestamp fin;

    public Reserva() {

    }

    public Reserva(Usuario unidadFuncional, Timestamp inicio, Timestamp fin) {
        this.unidadFuncional = unidadFuncional;
        this.inicio = inicio;
        this.fin = fin;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(Usuario unidadFuncional) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Reserva ");
        if (this.id != null) {
            builder.append("ID: ").append(this.id);
        }
        if (this.unidadFuncional != null) {
            builder.append(" UF: ").append(this.unidadFuncional.getUsername());
        }
        if (this.inicio != null) {
            builder.append(" Inicio: ").append(this.inicio);
        }
        if (this.fin != null) {
            builder.append(" Fin: ").append(this.fin);
        }
        return builder.toString();
    }
}
