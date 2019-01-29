package com.sum.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private static final Log LOGGER = LogFactory.getLog(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletResponse response) {
        LOGGER.debug("> login() - method = RequestMethod.GET");
        ModelAndView model = new ModelAndView("login");
        response.setContentType("application/javascript");
        LOGGER.debug("< login() - method = RequestMethod.GET");
        return model;
    }
}