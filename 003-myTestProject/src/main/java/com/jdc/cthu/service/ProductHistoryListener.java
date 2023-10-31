package com.jdc.cthu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.jdc.cthu.demo.entity.Product;
import com.jdc.cthu.demo.entity.ProductHistory;
import com.jdc.cthu.demo.entity.ProductHistoryPK;
import com.jdc.cthu.demo.output.ProductHistoryEvent;
import com.jdc.cthu.repo.ProductHistoryRepo;

import jakarta.transaction.Transactional;

@Service
public class ProductHistoryListener {
	
	@Autowired
	ProductHistoryRepo productHistoryRepo;

	@EventListener
	@Transactional
	public void handle(ProductHistoryEvent event) {
		
		event.product().stream().map(p -> new ProductHistory(p))
		.forEach( p -> {
						var historyPk = new ProductHistoryPK();
						historyPk.setVersion(1);
						productHistoryRepo.save(p);
					}		
				);		
	}
	
	@EventListener
	@Transactional
	public void handle(Product event) {
		
		var productHistory = new ProductHistory(event);		
		var pkVersion = productHistory.getProductHistoryPK().getVersion();
		if(null == event.getProducthistory()) {
			productHistory.getProductHistoryPK().setVersion(1);
		}else {
			productHistory.getProductHistoryPK().setVersion(productHistory.getProductHistoryPK().getVersion() + 1);
		}
		
		productHistoryRepo.save(productHistory);
		
	}
	
	
	
}
