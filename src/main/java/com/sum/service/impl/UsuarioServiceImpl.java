package com.sum.service.impl;

import com.sum.dao.UsuarioDAO;
import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.domain.Usuario;
import com.sum.exceptions.UsuarioException;
import com.sum.service.ReservaService;
import com.sum.service.UsuarioService;
import com.sum.web.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service("userDetailsService")
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDAO usuarioDAO;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    public UsuarioServiceImpl(UsuarioDAO dao) {
        usuarioDAO = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario loadUserByUsername(final String username) throws UsernameNotFoundException {
        Usuario user = usuarioDAO.retrieve(username);
        return user;
    }

    @Transactional
    @Override
    public Usuario modificarUsuario(Usuario usuario, UsuarioDTO dto) throws UsuarioException {
//        Usuario usuarioContexto = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!usuarioContexto.getUsername().equals(String.valueOf(dto.getUf()))) {
//            throw new UsuarioException("Usuario de contexto modificado.");
//        }

        if (!usuario.getEmail().equals(dto.getEmail())) {
            usuario.setEmail(dto.getEmail());
        }
        if (!usuario.getEmail2().equals(dto.getEmail2())) {
            usuario.setEmail2(dto.getEmail2());
        }
        if (dto.getPassword() != "" && dto.getPassword() != null && !usuario.getPassword().equals(dto.getPassword())) {
            usuario.setPassword(dto.getPassword());
        }
        return usuarioDAO.update(usuario);
    }

    @Override
    public Collection<Usuario> getAllUsers() {
        return usuarioDAO.getAllUsers();
    }

    @Transactional
    public String resetUser(String uf) throws UsuarioException {
        Usuario userToReset = loadUserByUsername(uf);
        if ("ADMIN".equals(userToReset.getRol())) {
            throw new UsuarioException("No se puede reiniciar este usuario, ya que tiene rol Administrador.");
        }
        ReservaCriteria criteria = new ReservaCriteria();
        criteria.setUf(Integer.valueOf(uf));
        criteria.setMin(Timestamp.valueOf(LocalDateTime.now()));
        List<Reserva> reservas = reservaService.buscarReservasConCriteria(criteria);

        for (Reserva reserva: reservas) {
            reservaService.eliminarReserva(reserva.getId());
        }

        UsuarioDTO dto = new UsuarioDTO(Integer.valueOf(uf), null, null, "","", String.valueOf(generateRandomPassword()));
        modificarUsuario(loadUserByUsername(uf), dto);
        return dto.getPassword();
    }

    private static final Integer generateRandomPassword() {
        int min = 100000;
        int max = 999999;
        return new Random().nextInt((max - min) + 1) + min;
    }

}
