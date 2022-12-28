package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.util.Iterator;
import java.util.Random;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(Environment env) {
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "/images")).toAbsolutePath()
				.normalize();
		System.out.println(fileStorageLocation);
//		try {
//			Files.createDirectories(this.fileStorageLocation);
//		} catch (Exception ex) {
//			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
//		}
	}

	@SuppressWarnings("unused")
	private String getFileExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		String[] fileNameParts = fileName.split("\\.");

		return fileNameParts[fileNameParts.length - 1];
	}

	public String storeFile(MultipartFile file) {
		Random rand = new Random();
		int range = 100000;
		int random = rand.nextInt(range);
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileNameWithOutExt = FilenameUtils.removeExtension(file.getOriginalFilename());

		String fileName = StringUtils.cleanPath(fileNameWithOutExt + "(" + random + ")." + extension);

		try {
			// Check if the filename contains invalid characters
			if (fileName.contains("..")) {
				throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new RuntimeException("File not found " + fileName);
			}
		} catch (IOException ex) {
			throw new RuntimeException("File not found " + fileName, ex);
		}
	}

}
