package com.jdc.cthu.demo.output;

import java.util.List;

import com.jdc.cthu.demo.entity.Product;

public record ProductHistoryEvent(
		List<Product> product
		) {

}
