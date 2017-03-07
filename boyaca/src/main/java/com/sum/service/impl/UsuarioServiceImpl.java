package com.sum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sum.dao.UsuarioDAO;
import com.sum.domain.Usuario;
import com.sum.exceptions.UsuarioException;
import com.sum.service.UsuarioService;
import com.sum.web.dto.UsuarioDTO;

@Service("userDetailsService")
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioDAO dao) {
		usuarioDAO = dao;
	}

	@Transactional(readOnly=true)
	@Override
	public Usuario loadUserByUsername(final String username) throws UsernameNotFoundException {
		Usuario user = usuarioDAO.retrieve(username);
		return user;
	}
	
	@Override
	public Usuario modificarUsuario(Usuario usuario, UsuarioDTO dto) throws UsuarioException {
		Usuario usuarioContexto = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioContexto.getUsername().equals(String.valueOf(dto.getUf()))) {
			throw new UsuarioException("Usuario de contexto modificado.");
		}
		
		if (!usuario.getEmail().equals(dto.getEmail())) {
			usuario.setEmail(dto.getEmail());
		}
		if (!usuario.getEmail2().equals(dto.getEmail2())) {
			usuario.setEmail2(dto.getEmail2());
		}
		if (!usuario.getPassword().equals(dto.getPassword())) {
			usuario.setPassword(dto.getPassword());
		}
		return usuarioDAO.update(usuario);
	}
	
}
