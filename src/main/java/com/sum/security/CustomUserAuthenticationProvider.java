package com.sum.security;

import com.sum.domain.Usuario;
import com.sum.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class CustomUserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    public UsuarioService usuarioService;

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken arg1)
            throws AuthenticationException {
        System.out.println("> retrieveUser: Primer parametro: " + username + " Segundo parámetro: " + arg1);
        Usuario user = (Usuario) this.usuarioService.loadUserByUsername(username);
        System.out.println("< retrieveUser");
        return user;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        System.out.println("> additionalAuthenticationChecks");
        if (token.getCredentials() == null || userDetails.getPassword() == null) {
            throw new BadCredentialsException("Las credenciales no pueden ser nulas.");
        }
        System.out.println("< additionalAuthenticationChecks");
    }

}

