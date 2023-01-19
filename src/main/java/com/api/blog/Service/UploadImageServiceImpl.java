package com.api.blog.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UploadImageServiceImpl implements UploadImageService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//get the file name
		String fileName=file.getOriginalFilename();
		System.out.print(fileName);
		
		
		
		String uniqueName=UUID.randomUUID().toString();
		String uniqueFileName = uniqueName.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		//make the full path
		String fullPath=path+File.separator+uniqueFileName;
		
		System.out.println(fullPath);
		
		
		//create image folder if not exist
		
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//upload file
		Files.copy(file.getInputStream(),Paths.get(fullPath));
		
		
		
		return uniqueFileName;
	}

	@Override
	public InputStream serveImage(String path, String fileName) throws FileNotFoundException {
		
		//String separator = System.getProperty("file.separator");
		String fullFileName=path+File.separator+fileName;
		
		InputStream inputStream=new FileInputStream(fullFileName);
		
		
		return inputStream;
	}

}
