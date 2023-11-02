package com.jdc.cthu.demo.dto;

import java.util.List;
import java.util.Map;

import com.jdc.cthu.demo.entity.Product;
import com.jdc.cthu.demo.entity.Product.Status;

import lombok.Data;

@Data
public class ProductDetailDto {

	private int id;
	private String name;
	private int price;
	private String description;
	private Map<String, String> features;
	private String image;
	private List<String> images;
	private Status status;
	private List<CategoryDto> category;
	private List<ProductHistoryDto> productHistory;
	
	public ProductDetailDto(Product entity) {
		
		this.id = entity.getId();
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.description = entity.getDescription();
		this.features = entity.getFeatures();
		this.image = entity.getImage();
		this.images = entity.getImages();
		this.category = entity.getCategory().stream().map(CategoryDto::new).toList();
		this.productHistory = entity.getProducthistory().stream().map(ProductHistoryDto::new).toList();
	}
	
	
	
}
