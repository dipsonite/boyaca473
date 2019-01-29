package com.sum.dao;

import com.sum.domain.Usuario;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public Collection<Usuario> getAllUsers() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot = query.from(Usuario.class);
        query.select(usuarioRoot);


        List<Order> orderList = new ArrayList();
        orderList.add(builder.asc(usuarioRoot.get("piso")));
        orderList.add(builder.asc(usuarioRoot.get("depto")));
        query.orderBy(orderList);

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
