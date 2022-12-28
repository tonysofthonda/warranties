package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Evidences;
import com.honda.hdm.warrantiesmg.app.domain.repository.EvidenceRepository;
import com.honda.hdm.warrantiesmg.app.service.EvidenceService;
import com.honda.hdm.warrantiesmg.util.UploadFileResponse;

@Service
public class EvidenceServiceImpl implements EvidenceService {

	@Autowired
	private EvidenceRepository evidenceRepository;

	@Override
	public List<UploadFileResponse> getEvidenceByWarranty(Long warrantyId) {
		// TODO Auto-generated method stub
		List<Evidences> evidences = this.evidenceRepository.findByWarrantyClaimId(warrantyId);
		List<UploadFileResponse> uploadFileResponse = new ArrayList<>();
		for (Evidences evidence : evidences) {
			UploadFileResponse fileResponse = new UploadFileResponse(evidence.getFilePath(), evidence.getFilePath(),
					evidence.getTypeFile(), 0);

			uploadFileResponse.add(fileResponse);
		}
		return uploadFileResponse;
	}

}
