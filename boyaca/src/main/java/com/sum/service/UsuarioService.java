package com.sum.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sum.domain.Usuario;
import com.sum.exceptions.UsuarioException;
import com.sum.web.dto.UsuarioDTO;

public interface UsuarioService extends UserDetailsService {
	
	public Usuario modificarUsuario(Usuario usuario, UsuarioDTO dto) throws UsuarioException;


}
