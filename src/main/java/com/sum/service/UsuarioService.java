package com.sum.service;

import com.sum.domain.Usuario;
import com.sum.exceptions.UsuarioException;
import com.sum.web.dto.UsuarioDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UsuarioService extends UserDetailsService {

    Usuario modificarUsuario(Usuario usuario, UsuarioDTO dto) throws UsuarioException;

    Collection<Usuario> getAllUsers();

    String resetUser(String uf) throws UsuarioException;
}
