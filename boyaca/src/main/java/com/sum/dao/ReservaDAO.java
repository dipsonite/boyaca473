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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.sum.dao.criteria.ReservaCriteria;
import com.sum.domain.Reserva;
import com.sum.domain.Usuario;

@Repository
public class ReservaDAO implements GenericPersistentDAO<Reserva, Integer> {
	
	private static final Log LOGGER = LogFactory.getLog(ReservaDAO.class);
	
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;
	
//	private Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	@Override
	public Reserva create(Reserva entity) {
		this.entityManager.persist(entity);
		LOGGER.info("El usuario ".concat(((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).concat(" crea la reserva ").concat(entity.toString()));
		return entity;
	}

	@Override
	public Reserva retrieve(Integer id) {
		return this.entityManager.find(Reserva.class, id);
	}

	@Override
	public Reserva update(Reserva entity) {
//		this.retrieve(entity.getId());
		LOGGER.info("El usuario ".concat(((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).concat(" modifica la reserva ").concat(entity.toString()));
		return this.entityManager.merge(entity);
	}

	@Override
	public void delete(Integer id) {
		Reserva entity = this.retrieve(id);
		LOGGER.info("El usuario ".concat(((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).concat(" elimina la reserva ").concat(entity.toString()));
		this.entityManager.remove(entity);
	}

	public List<Reserva> getReservasEntreFechas(ReservaCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reserva> query = builder.createQuery(Reserva.class);
        Root<Reserva> reserva = query.from(Reserva.class);
        query.select(reserva);
        query.where();
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(criteria.getMin() != null) {
        	Predicate pred = builder.greaterThanOrEqualTo(reserva.get("inicio"), criteria.getMin());
			predicates.add(pred);
        }

        if(criteria.getMax() != null) {
        	Predicate pred = builder.lessThanOrEqualTo(reserva.get("fin"), criteria.getMax());
			predicates.add(pred);
        }
        
        if(!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        TypedQuery<Reserva> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
	}

	public List<Reserva> getReservasParaUfYFecha(ReservaCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reserva> query = builder.createQuery(Reserva.class);
        Root<Reserva> reserva = query.from(Reserva.class);
        query.select(reserva);
        query.where();
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(criteria.getUf() != null) {
        	Predicate pred = builder.equal(reserva.get("unidadFuncional"), criteria.getUf());
			predicates.add(pred);
        }
        
        if (criteria.getIdReserva() != null) {
        	Predicate pred = builder.notEqual(reserva.get("id"), criteria.getIdReserva());
			predicates.add(pred);
        }

        if(criteria.getMax() != null) {
        	Predicate pred = builder.greaterThanOrEqualTo(reserva.get("fin"), criteria.getMax());
			predicates.add(pred);
        }
        
        if(!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        TypedQuery<Reserva> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
	}
	

	public List<Reserva> getReservasSolapadas(ReservaCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reserva> query = builder.createQuery(Reserva.class);
        Root<Reserva> reserva = query.from(Reserva.class);
        query.select(reserva);
        query.where();
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (criteria.getIdReserva()!= null) {
        	predicates.add(builder.notEqual(reserva.get("id"), criteria.getIdReserva()));
        }
        
        Predicate a = builder.greaterThan(reserva.get("fin"), criteria.getMin());
        Predicate b = builder.lessThan(reserva.get("fin"), criteria.getMax());
        Predicate c = builder.greaterThan(reserva.get("inicio"), criteria.getMin());
        Predicate d = builder.lessThan(reserva.get("inicio"), criteria.getMax());
        
        Predicate e = builder.lessThanOrEqualTo(reserva.get("inicio"), criteria.getMin());
        Predicate f = builder.greaterThanOrEqualTo(reserva.get("fin"), criteria.getMax());
        
        Predicate g = builder.greaterThanOrEqualTo(reserva.get("inicio"), criteria.getMin());
        Predicate h = builder.lessThanOrEqualTo(reserva.get("fin"), criteria.getMax());
        
        predicates.add(builder.or(builder.or(builder.and(a, b), builder.and(c, d)), builder.and(e, f), builder.and(g, h)));
        
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        
        TypedQuery<Reserva> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
	}
}
