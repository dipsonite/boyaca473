package com.sum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sum.dao.UsuarioDAO;
import com.sum.domain.Usuario;
import com.sum.service.UsuarioService;

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
}
