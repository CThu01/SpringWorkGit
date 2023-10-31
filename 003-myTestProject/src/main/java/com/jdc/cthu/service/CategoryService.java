package com.jdc.cthu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.cthu.demo.entity.Category;
import com.jdc.cthu.demo.form.CategoriesEditForm;
import com.jdc.cthu.repo.CategoryRepo;

@Service
@Transactional
public class CategoryService {

	@Autowired
	CategoryRepo categoryRepo;
	
	public Category create(CategoriesEditForm form) {
		
		Category category = new Category();
		category.setName(form.getName());
		var createResult = categoryRepo.save(category);
		return createResult;
	}
}
