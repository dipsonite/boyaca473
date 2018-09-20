package com.sum.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.sum.domain.Usuario;

@Repository
public class UsuarioDAO implements GenericPersistentDAO<Usuario, String> {
	
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;
	
	@Override
	public Usuario create(Usuario entity) {
		return null;
	}

	@Override
	public Usuario retrieve(String id) {
		return this.entityManager.find(Usuario.class, id);
	}

	@Override
	public Usuario update(Usuario entity) {
		this.retrieve(entity.getUsername());
		return this.entityManager.merge(entity);
	}

	@Override
	public void delete(String id) {
	}
}
