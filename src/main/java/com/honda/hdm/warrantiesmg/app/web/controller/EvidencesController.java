package com.honda.hdm.warrantiesmg.app.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.honda.hdm.warrantiesmg.app.service.EvidenceService;
import com.honda.hdm.warrantiesmg.app.service.serviceImp.FileStorageService;
import com.honda.hdm.warrantiesmg.app.web.model.EvidencesDto;
import com.honda.hdm.warrantiesmg.util.UploadFileResponse;

@CrossOrigin(origins = "http://qhdmglserwfs01:8080/")
@RestController
public class EvidencesController {

	private static final Logger logger = LoggerFactory.getLogger(EvidencesController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private EvidenceService evidenceService;
    
    public EvidencesController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/viewFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/uploadFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/viewFile/{fileName:.+}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @GetMapping("/evidences")
    public ResponseEntity<?> getEvidenceByWarrantyId(@RequestParam(name = "warrantyId") Long warrantyId) {
    	List<UploadFileResponse> evidencesDto = this.evidenceService.getEvidenceByWarranty(warrantyId);
    	if(evidencesDto == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
    	return new ResponseEntity<>(evidencesDto, HttpStatus.OK);
	}
}
