package com.sum.web.dto;

import com.sum.domain.Reserva;
import com.sum.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservaTranslator {

    @Autowired
    public UsuarioService service;

    public ReservaDTO getReservaDTO(Reserva reserva) {
        return new ReservaDTO(reserva.getId(), Integer.valueOf(reserva.getUnidadFuncional().getUsername()),
                reserva.getUnidadFuncional().getPiso(), reserva.getUnidadFuncional().getDepto(),
                reserva.getInicio(), reserva.getFin(),
                reserva.getUnidadFuncional().getEmail(), reserva.getUnidadFuncional().getEmail2());
    }
}
