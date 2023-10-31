package com.jdc.cthu.repo;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepositoryImplementation<T, ID>{
	
	<R> List<R> findAll(Function<CriteriaBuilder, CriteriaQuery<R>> queryFun);

	<R> Page<R> findAll(
			Function<CriteriaBuilder, CriteriaQuery<R>> queryFun,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFun,
			int page,int size);
}
