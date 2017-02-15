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
		return null;
	}

	@Override
	public Reserva update(Reserva entity) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		
	}

	public List<Reserva> getReservas(ReservaCriteria criteria) {
		
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reserva> query = cb.createQuery(Reserva.class);
        Root<Reserva> person = query.from(Reserva.class);
        query.select(person);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(criteria.getAnio() != null && criteria.getMes() != null) {
        	
//            predicates.add(cb.and(cb.like(person.<String>get("name"), "%" + personCriteria.getName() + "%")));
        }

//        if(personCriteria.getMinAge() != null) {
//            predicates.add(cb.and(cb.ge(person.<Integer>get("age"), personCriteria.getMinAge())));
//        }
//
//        if(personCriteria.getMaxAge() != null) {
//            predicates.add(cb.and(cb.le(person.<Integer>get("age"), personCriteria.getMaxAge())));
//        }
        
        if(!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        TypedQuery<Reserva> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
	}
}
