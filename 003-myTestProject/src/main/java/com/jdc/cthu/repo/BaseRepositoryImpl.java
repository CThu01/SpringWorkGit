package com.jdc.cthu.repo;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>{

	private EntityManager em;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityinformation, EntityManager entityManager) {
		super(entityinformation, entityManager);
		this.em = entityManager;
	}

	@Override
	public <R> Page<R> findAll(
			Function<CriteriaBuilder, CriteriaQuery<R>> queryFun,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFun, 
			int size, int page) {
		
		var count = em.createQuery(countFun.apply(em.getCriteriaBuilder())).getSingleResult();
		var content = em.createQuery(queryFun.apply(em.getCriteriaBuilder()))
									.setFirstResult(size * page)
									.setMaxResults(size)
									.getResultList();
		
		return new PageImpl<R>(content, PageRequest.of(page, size), count);
	}

	@Override
	public <R> List<R> findAll(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun) {
		return em.createQuery(queryFun.apply(em.getCriteriaBuilder())).getResultList();
	}

}







