package com.jdc.cthu.demo.output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.cthu.utils.exception.ApiBusinessException;
import com.jdc.cthu.utils.exception.ApiValidationException;

@Service
public class TextFileReader {

	public List<String> reader(MultipartFile file) {
		
		var list = new ArrayList<String>();
		
		try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
			String line = null;
			
			while(null != (line = reader.readLine())) {
				list.add(line);
			}
			
		}catch (IOException e) {
			new ApiBusinessException("invalid file format");
		}
		
		return list;
	}
	
}
