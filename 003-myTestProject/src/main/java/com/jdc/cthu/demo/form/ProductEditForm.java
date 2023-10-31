package com.jdc.cthu.demo.form;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.jdc.cthu.demo.entity.Category;
import com.jdc.cthu.demo.entity.Product;
import com.jdc.cthu.demo.entity.Product.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEditForm {

	private String name;
	private Integer price;
	private String description;
	private Status status;
	private Map<String, String> features;
	private List<Integer> categories;
	
	public Product entity(Function<Integer, Category> catFun) {
		
		var product = new Product();
		
		product.setName(name);
		product.setPrice(price);
		product.setDescription(description);
		product.setStatus(status);
		product.setFeatures(features);
		for(var catName : categories) {
			product.getCategory().add(catFun.apply(catName));
		}
		
		return product;
	}
}
