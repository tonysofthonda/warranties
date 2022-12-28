package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.util.UploadFileResponse;

public interface EvidenceService {

	List<UploadFileResponse> getEvidenceByWarranty(Long warrantyId);

}
