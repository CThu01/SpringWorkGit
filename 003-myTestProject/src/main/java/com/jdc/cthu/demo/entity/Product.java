package com.jdc.cthu.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private String description;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ElementCollection
    @CollectionTable(name = "features_table",
    		joinColumns = {@JoinColumn(name = "feature_joinColumn",referencedColumnName = "id")}
    		)
	@MapKeyColumn(name = "features_name")
	@Column(name = "features_value")
	private Map<String,String> features = new HashMap<>();
	
	@ManyToMany
	private List<Category> category = new ArrayList<>();
	
	private String image;
	
	@OneToMany(mappedBy = "product")
	private List<ProductHistory> producthistory;
	
	public enum Status {
		Active,Deleted,SoldOut
	}
	

}
