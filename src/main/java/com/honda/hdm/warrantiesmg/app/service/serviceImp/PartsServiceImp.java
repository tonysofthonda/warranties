package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Parts;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsRepository;
import com.honda.hdm.warrantiesmg.app.service.PartsService;
import com.honda.hdm.warrantiesmg.app.web.model.PartDto;

@Service
public class PartsServiceImp implements PartsService {

	@Autowired
	private PartsRepository partsRepository;

	@Override
	public Parts findPartById(Long partId) {
		Optional<Parts> t = this.partsRepository.findById(partId);
		return t.isPresent() ? t.get() : null;
	}

	@Override
	public PartDto getPartByPartNumber(String partNumber) throws Exception {
		Optional<Parts> part = this.partsRepository.findByPartNumber(partNumber);
		if (part.isPresent()) {
			PartDto partDto = new PartDto();
			partDto.setId(part.get().getId());
			partDto.setModel(part.get().getModel());
			partDto.setFrt(part.get().getFrt());
			partDto.setYearModelFrom(part.get().getYearModelFrom());
			partDto.setYearModelTo(part.get().getYearModelTo());
			partDto.setPartNumber(part.get().getPartNumber());
			partDto.setDescriptionPart(part.get().getDescriptionPart());
			partDto.setPrice(part.get().getPrice());
			partDto.setLonCode(part.get().getLonCode());
			partDto.setReference(part.get().getReference());
			partDto.setBlock(part.get().getBlock());
			partDto.setPublishCatalogNumber(part.get().getPublishCatalogNumber());
			return partDto;
		} else {
			return null;
		}
	}

	@Override
	public void savePart(PartDto part) {
		// TODO Auto-generated method stub
		Parts newPart = new Parts();
		newPart.setFrt(part.getFrt());
		newPart.setPartNumber(part.getPartNumber());
		newPart.setDescriptionPart(part.getDescriptionPart());
		newPart.setPrice(part.getPrice());
		newPart.setModel(part.getModel());
		newPart.setYearModelFrom(1);
		newPart.setYearModelTo(1);
		newPart.setLonCode("1");
		newPart.setReference(1);
		newPart.setBlock("1");
		newPart.setPublishCatalogNumber("1");
		newPart.setStatus(true);
		newPart.setDateCreation(new Timestamp(new Date().getTime()));
		this.partsRepository.save(newPart);
	}
}
