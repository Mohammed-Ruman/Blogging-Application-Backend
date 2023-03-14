package com.blog.userservice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	public String uploadimage(String path, MultipartFile file) throws IOException;

	InputStream getImage(String path,String fileName) throws FileNotFoundException;

}
