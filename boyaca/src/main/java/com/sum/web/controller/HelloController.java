package com.sum.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sum.domain.Usuario;

@Controller
public class HelloController {
	
	private static final Log LOGGER = LogFactory.getLog(HelloController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		LOGGER.debug("> login() - method = RequestMethod.GET");
		ModelAndView model = new ModelAndView("login");
		LOGGER.debug("< login() - method = RequestMethod.GET");
		return model;
	}

	@RequestMapping(value = "/hello")
	public ModelAndView hello() {
		LOGGER.debug("> hello()");
		ModelAndView model = new ModelAndView();
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addObject("uf", usuario.getUnidadFuncional());
		model.setViewName("calendar");
		LOGGER.debug("< hello()");
		return model;
	}
}