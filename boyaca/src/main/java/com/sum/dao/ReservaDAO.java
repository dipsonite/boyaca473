package com.sum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;

@Repository
public class ReservaDAO implements GenericPersistentDAO<Reserva, Integer> {
	
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public Reserva create(Reserva entity) {
		this.entityManager.persist(entity);
		return entity;
	}

	@Override
	public Reserva retrieve(Integer id) {
		return this.entityManager.find(Reserva.class, id);
	}

	@Override
	public Reserva update(Reserva entity) {
		return this.entityManager.merge(entity);
	}

	@Override
	public void delete(Integer id) {
		this.entityManager.remove(this.retrieve(id));
	}

	public List<Reserva> getReservas(ReservaCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reserva> query = builder.createQuery(Reserva.class);
        Root<Reserva> reserva = query.from(Reserva.class);
        query.select(reserva);
        query.where();
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(criteria.getFechaMin() != null) {
        	Predicate pred = builder.greaterThanOrEqualTo(reserva.get("fecha"), criteria.getFechaMin());
			predicates.add(pred);
        }

        if(criteria.getFechaMax() != null) {
        	Predicate pred = builder.lessThanOrEqualTo(reserva.get("fecha"), criteria.getFechaMax());
			predicates.add(pred);
        }
        
        if(!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        TypedQuery<Reserva> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
	}
}
