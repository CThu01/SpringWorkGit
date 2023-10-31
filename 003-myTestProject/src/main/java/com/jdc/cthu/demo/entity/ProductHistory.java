package com.jdc.cthu.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.NA;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.jdc.cthu.demo.entity.Product.Status;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class ProductHistory implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductHistoryPK productHistoryPK;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private String description;
	
	@ManyToOne(optional = false)
	@JoinColumn(updatable = false,insertable = false)
	private Product product;
	
	@ManyToMany
	@JoinColumn(updatable = false,insertable = false)
	private List<Category> category = new ArrayList<>();
	
	@ElementCollection
	@MapKeyColumn(name = "feature_name")
	private Map<String, String> features = new HashMap<>();
	
	private String remark;
	private Status status;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	public ProductHistory(Product product) {
		
		this.name = product.getName();
		this.price = product.getPrice();
		this.description = product.getDescription();
		this.category = product.getCategory();
		this.features = product.getFeatures();
		this.status = product.getStatus();
		this.createDate = LocalDateTime.now();
		this.productHistoryPK = new ProductHistoryPK();
		this.productHistoryPK.setProductId(product.getId());
	}
	
	
	
}















