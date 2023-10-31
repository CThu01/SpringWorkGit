package com.jdc.cthu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdc.cthu.demo.dto.ProductDetailDto;
import com.jdc.cthu.demo.dto.ProductDto;
import com.jdc.cthu.demo.entity.Category;
import com.jdc.cthu.demo.entity.Product;
import com.jdc.cthu.demo.entity.ProductHistory;
import com.jdc.cthu.demo.entity.Product_;
import com.jdc.cthu.demo.form.ProductEditForm;
import com.jdc.cthu.demo.form.ProductSearchForm;
import com.jdc.cthu.demo.output.ProductHistoryEvent;
import com.jdc.cthu.demo.output.ProductUploadResult;
import com.jdc.cthu.repo.CategoryRepo;
import com.jdc.cthu.repo.ProductRepo;
import com.jdc.cthu.utils.exception.ApiBusinessException;
import com.jdc.cthu.utils.exception.ApiValidationException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	EntityManager em;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	public Page<ProductDto> search(ProductSearchForm form,int size,int page){
		
		Function<CriteriaBuilder, CriteriaQuery<ProductDto>> queryFunc = 
				cb -> {
					var query = cb.createQuery(ProductDto.class);
					var root = query.from(Product.class);
					ProductDto.select(query, root);
					query.where(form.where(cb, root));
					return query;
				};
				
		Function<CriteriaBuilder, CriteriaQuery<Long>> queryCount = 
				cb -> {
					var query = cb.createQuery(Long.class);
					var root = query.from(Product.class);
					query.select(cb.count(root.get(Product_.ID)));
					query.where(form.where(cb, root));
					return query;
				};
		
		return productRepo.findAll(queryFunc, queryCount, page, size);
	}
	
	public List<ProductDto> upload(){
		
		return null;
	}

	
	public ProductUploadResult create(ProductEditForm form) {
		var product = form.entity(id -> categoryRepo.findById(id)
						.orElseThrow(() -> new ApiBusinessException("There is no Category")));
		
		product = productRepo.save(product);
		
		eventPublisher.publishEvent(product);
		
		return new ProductUploadResult(product.getId(), "create product successfully");
	}
	
	public ProductUploadResult update(int id, ProductEditForm form) {
		
//		var productResult = productRepo.findById(id).orElseThrow(() -> new ApiBusinessException("Invalid Product"));
		
		var productResult = form.entity(num -> categoryRepo.findById(num)
							.orElseThrow(() -> new ApiBusinessException(null)));
		productResult.setId(id);
		productRepo.save(productResult);
		
		eventPublisher.publishEvent(productResult);
		
		return new ProductUploadResult(id, "product Id %d updated successfully".formatted(productResult.getId()));
	}
	

	public ProductDetailDto findById(int id) {

		return productRepo.findById(id).map(ProductDetailDto::new).orElseThrow(() -> new EntityNotFoundException());
	}


	
	@Transactional
	public ProductUploadResult create(List<String> productList) {
	
		var message = new ArrayList<String>();
		var products = new ArrayList<Product>();

		for(int i=0; i<productList.size(); i++) {
			
			var line = productList.get(i);
			var array = line.split("\t");
			
			if(array.length < 4) {
				message.add("line %d : Invalid column size".formatted(i+1));
				continue;
			}
			
			try {
				Product productResult = convertArrayToProduct(array);	
				products.add(productResult);
				
			}catch(ApiBusinessException e) {
				message.add("line %d : %s".formatted((i+1),e.getMessage()));
			}
		}

		if(!message.isEmpty()) {
			throw new ApiValidationException(message);
		}
		
		List<Product> productUpload = productRepo.saveAll(products);
		
		eventPublisher.publishEvent(new ProductHistoryEvent(productUpload));
		
		return new ProductUploadResult(productUpload.size(), " %d products add successfully".formatted(productUpload.size()));
	}

	private Product convertArrayToProduct(String[] array) {

		var product = new Product();

		if(array[0].isEmpty()) {
			throw new ApiBusinessException("Enter product name");
		}
		try {
			product.setName(array[0]);
			product.setPrice(Integer.parseInt(array[1]));
			product.setDescription(array[2]);
			
			var catArray =  array[3].split(",");
			for(var catName : catArray) {
				var category = findCategory(catName);
				product.getCategory().add(category);
			}
			
		}catch(NumberFormatException e) {
			throw new ApiBusinessException("Enter Digits for price");
		}
		
		return product;
	}
	
	private Category findCategory(String catName) {
		
		Map<String, Category> categoryMap = new HashMap<>();
		
		var categoryName = categoryMap.get(catName.toLowerCase());
		
		if(null == categoryName) {
			categoryName = categoryRepo.findOneByNameIgnoreCase(catName)
							.orElseGet(() -> {
									var entity = new Category();
									entity.setName(catName);
									categoryRepo.save(entity);
									return entity;
								}
							);
			categoryMap.put(catName.toLowerCase(), categoryName);
		}
		
		return categoryName;
	}

	
	
	
	
	
	
	
	
	
	
	
}





















