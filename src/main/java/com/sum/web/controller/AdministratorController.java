package com.sum.web.controller;

import com.sum.exceptions.UsuarioException;
import com.sum.service.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdministratorController {

    private static final Log LOGGER = LogFactory.getLog(AdministratorController.class);

    @Autowired
    public UsuarioService usuarioService;

    @GetMapping(value = "/admin")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("admin");
        mav.addObject("userList", usuarioService.getAllUsers());
        return mav;
    }

    @PostMapping("/admin/{id}/reset")
    public ModelAndView finishOrder(@PathVariable("id") String userId) throws UsuarioException {
        String newPass = usuarioService.resetUser(userId);
        return new ModelAndView("redirect:/admin/?userId="+userId+"&newPass="+newPass);
    }
}