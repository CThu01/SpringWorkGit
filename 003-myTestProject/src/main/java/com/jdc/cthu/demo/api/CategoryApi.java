package com.jdc.cthu.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.cthu.demo.entity.Category;
import com.jdc.cthu.demo.form.CategoriesEditForm;
import com.jdc.cthu.service.CategoryService;

@RestController
@RequestMapping("categories")
public class CategoryApi {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping
	Category create(@Validated @RequestBody CategoriesEditForm form,BindingResult result) {
		return categoryService.create(form);
	}
	
	@PutMapping("update/{id}")
	Long update(@RequestParam int id,@Validated @RequestBody CategoriesEditForm form,BindingResult result) {
		return null;
	}
	
	@GetMapping
	List<Category> getAll(){
		return null;
	}
}








