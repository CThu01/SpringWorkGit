package com.jdc.cthu.demo.dto;

import com.jdc.cthu.demo.entity.Product;
import com.jdc.cthu.demo.entity.Product.Status;
import com.jdc.cthu.demo.entity.Product_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private int id;
	private String name;
	private int price;
	private String Description;
	private Status status;
	private String images;
	
	public static void select(CriteriaQuery<ProductDto> query,Root<Product> root) {
		query.multiselect(
					root.get(Product_.ID),
					root.get(Product_.NAME),
					root.get(Product_.PRICE),
					root.get(Product_.DESCRIPTION),
					root.get(Product_.STATUS),
					root.get(Product_.IMAGE)
				);
	}
}
