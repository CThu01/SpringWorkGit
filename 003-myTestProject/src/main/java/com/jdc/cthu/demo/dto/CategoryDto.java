package com.jdc.cthu.demo.dto;

import com.jdc.cthu.demo.entity.Category;

import lombok.Data;

@Data
public class CategoryDto {

	private int id;
	private String name;
	
	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
}
