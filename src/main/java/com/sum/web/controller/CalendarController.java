package com.sum.web.controller;

import com.sum.domain.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalendarController {

    private static final Log LOGGER = LogFactory.getLog(CalendarController.class);

    @RequestMapping(value = "/")
    public ModelAndView home() {
        return calendar();
    }

    @RequestMapping(value = "/calendar")
    public ModelAndView calendar() {
        LOGGER.debug("> calendar()");
        ModelAndView model = new ModelAndView();
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addObject("uf", usuario.getUsername());
        model.addObject("rol", usuario.getRol());
        model.setViewName("calendar");
        LOGGER.debug("< calendar()");
        return model;
    }
}