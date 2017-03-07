package com.sum.web.dto;

import com.sum.domain.Usuario;

public class UsuarioTranslator {
	public static UsuarioDTO getUsuarioDTO(Usuario user) {
		return new UsuarioDTO(Integer.valueOf(user.getUsername()), user.getPiso(), user.getDepto(), 
				user.getEmail(), user.getEmail2(), user.getPassword());
	}
	
//	public static Usuario translate(UsuarioDTO dto) {
//		return new Usuario();
//	}
}
