package com.jdc.cthu.repo;

import java.util.Optional;

import com.jdc.cthu.demo.entity.Category;

public interface CategoryRepo extends BaseRepository<Category, Integer>{

	Optional<Category> findOneByNameIgnoreCase(String catName);

}
