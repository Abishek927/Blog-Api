package com.api.blog.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {

	public String uploadImage(String path,MultipartFile file) throws IOException;
	public InputStream serveImage(String path,String fileName) throws FileNotFoundException;
	
	
}
