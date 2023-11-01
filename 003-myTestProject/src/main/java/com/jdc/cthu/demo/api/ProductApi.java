package com.jdc.cthu.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.cthu.demo.dto.ProductDetailDto;
import com.jdc.cthu.demo.dto.ProductDto;
import com.jdc.cthu.demo.form.ProductEditForm;
import com.jdc.cthu.demo.form.ProductSearchForm;
import com.jdc.cthu.demo.output.ProductUploadResult;
import com.jdc.cthu.demo.output.TextFileReader;
import com.jdc.cthu.repo.ProductRepo;
import com.jdc.cthu.service.ImageFileWriter;
import com.jdc.cthu.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductApi{
	
	@Autowired
	ProductService productService;
	
	@Autowired
	TextFileReader textFileReader;
	
	@Autowired
	ImageFileWriter imageFileWriter;
	
	@Autowired
	ProductRepo productRepo;

	@GetMapping
	public Page<ProductDto> search(
			ProductSearchForm form,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int size){
		return productService.search(form, size, page);
	}
	
	@PostMapping
	public ProductUploadResult create(@Validated @RequestBody ProductEditForm form,BindingResult result) {
		return productService.create(form);
	}
	
	@PostMapping("upload")
	public ProductUploadResult upload(@RequestParam MultipartFile file) {
		var lines = textFileReader.reader(file);
		var result = productService.create(lines);
		return result;
	}
	
	@PostMapping("{id}/photos")
	public ProductDetailDto uploadPhotos(@PathVariable int id,@RequestParam MultipartFile[] file) {
		var images = imageFileWriter.save(id, file);
		productService.uploadPhoto(id,images);
		
		return productService.findById(id);
	}
	
	@GetMapping("{id}")
	public ProductDetailDto findById(@PathVariable int id) {
		return productService.findById(id);
	}
	
	@PutMapping("update/{id}")
	public ProductUploadResult update(@PathVariable int id,@Validated @RequestBody ProductEditForm form,BindingResult result) {
		return productService.update(id,form);
	}
	


	
	
	
}



