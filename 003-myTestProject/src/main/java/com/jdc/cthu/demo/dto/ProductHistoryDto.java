package com.jdc.cthu.demo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.jdc.cthu.demo.entity.Product.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdc.cthu.demo.entity.ProductHistory;

import lombok.Data;

@Data
public class ProductHistoryDto {

	private int id;
	private int version;
	private String name;
	private int price;
	private String description;
	private Status status;
	private String remark;
	private Map<String, String> features;
	private List<CategoryDto> category;
	
	@JsonFormat(pattern = "YYYY/MM/dd hh:mm")
	private LocalDateTime createDate;
	
	public ProductHistoryDto(ProductHistory entity) {
		this.id = entity.getProductHistoryPK().getProductId();
		this.version = entity.getProductHistoryPK().getVersion();
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.description = entity.getDescription();
		this.status = entity.getStatus();
		this.remark = entity.getRemark();
		this.features = entity.getFeatures();
		this.category = entity.getCategory().stream().map(CategoryDto::new).toList();
	}
	
	
}
