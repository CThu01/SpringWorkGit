package com.jdc.cthu.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFileWriter {

	@Value("${app.image.folder}")
	private String imagePath;
	private Logger logger = LoggerFactory.getLogger(ImageFileWriter.class);
	
	private static final String fileFormat = "p_%06d_%s_%03d.%s";
	private static final DateTimeFormatter dFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	public List<String> save(int id, MultipartFile ... files){
		
		List<String> fileNameList = new ArrayList<String>();
		
		for(int i=0; i<files.length; i++) {
			
			try {
				var file = files[i];
				var fileName = getFileName(file,id,i);
				var destination = Path.of(imagePath, fileName);
			
				Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
				fileNameList.add(fileName);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
		return fileNameList;
	}

	private String getFileName(MultipartFile file,int productId,int id) {
		var dateTime = LocalDateTime.now().format(dFmt);
		System.out.println("File Original File Name : %s".formatted(file.getOriginalFilename()));
		var extension = file.getOriginalFilename().split("\\.");
		var fileNameResult = fileFormat.formatted(productId,dateTime,id+1,extension[extension.length-1]);
		return fileNameResult;
	}
}
