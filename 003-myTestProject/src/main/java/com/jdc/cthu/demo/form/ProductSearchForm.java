package com.jdc.cthu.demo.form;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.cthu.demo.entity.Category_;
import com.jdc.cthu.demo.entity.Product;
import com.jdc.cthu.demo.entity.Product_;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchForm {

	private Integer category;
	private String keyword;
	private Integer priceFrom;
	private Integer priceTo;
	private Status status;
	
	public Predicate[] where(CriteriaBuilder cb,Root<Product> root) {
		
		var list = new ArrayList<Predicate>();
		
		if(null != category && category > 0) {
			var category = root.join(Product_.CATEGORY,JoinType.INNER);
			var predicate = cb.equal(category.get(Category_.ID), this.category);
			list.add(predicate);
		}
		
		if(StringUtils.hasLength(keyword)) {
			var predicate = cb.or(
					cb.equal(cb.lower(root.get(Product_.NAME)), keyword.toLowerCase().concat("%")),
					cb.equal(cb.lower(root.get(Product_.DESCRIPTION)),"%".concat(keyword.toLowerCase().concat("%")))
					);
			list.add(predicate);
		}
		
		if(null != priceFrom && priceFrom > 0) {
			var predicate = cb.ge(root.get(Product_.PRICE), priceFrom);
			list.add(predicate);
		}
		
		if(null != priceTo && priceTo > 0) {
			var predicate = cb.le(root.get(Product_.PRICE), priceTo);
			list.add(predicate);
		}
		
		if(null != status) {
			var predicate = cb.equal(root.get(Product_.STATUS), status);
			list.add(predicate);
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
	
}











