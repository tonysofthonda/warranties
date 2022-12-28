package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;
import com.honda.hdm.warrantiesmg.app.web.model.PartMotorcycleDto;

public interface PartMotorcycleService {
	
	List<PartMotorcycleDto> getAllPartsMotorcycle(Boolean status);

	void savePartMotorcycle(PartMotorcycleDto partMotorcycleDto) throws Exception;
	
	void updateStatusPartMotorcycle(PartMotorcycleDto partMotorcycleDto) throws Exception;
	
}
