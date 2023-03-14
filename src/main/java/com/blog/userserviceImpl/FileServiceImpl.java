package com.blog.userserviceImpl;

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

import com.blog.userservice.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadimage(String path, MultipartFile file) throws IOException {

		//File name
		String name = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String newfilename = randomId.concat(name.substring(name.lastIndexOf('.')));

		//Path name
		String filepath=path+File.separator+newfilename;

		//Folder making
		File p=new File(path);
		if(!p.exists()) {
			p.mkdir();
		}

		//Copying the file

		Files.copy(file.getInputStream(), Paths.get(filepath));

		return newfilename;
	}

	@Override
	public InputStream getImage(String path, String fileName) throws FileNotFoundException {
		String fullpath=path+File.separator+fileName;
		InputStream is= new FileInputStream(fullpath);
		return is;
	}

}
