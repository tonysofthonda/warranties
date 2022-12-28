package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Plant;
import com.honda.hdm.warrantiesmg.app.domain.repository.PlantRepository;
import com.honda.hdm.warrantiesmg.app.service.PlantService;
import com.honda.hdm.warrantiesmg.app.web.model.PlantDto;

@Service
public class PlantServiceImp implements PlantService {

	@Autowired
	private PlantRepository plantRepository;

	@Override
	public List<PlantDto> getAllPlants() {
		List<PlantDto> plantList = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Plant> plantsEntity = this.plantRepository.findAll();
		for (Plant plant : plantsEntity) {
			System.out.print(plant);
			plantList.add(modelMapper.map(plant, PlantDto.class));
		}
		return plantList;
	}

	@Override
	public void savePlant(PlantDto plant) {
		// TODO Auto-generated method stub
		if (plant.getId() != null) {
			Optional<Plant> tempPlant = this.plantRepository.findById(plant.getId());
			if (tempPlant.isPresent()) {
				tempPlant.get().setUpdateTimestamp(new Timestamp(new Date().getTime()));
				tempPlant.get().setName(plant.getName());
				tempPlant.get().setPlantCountry(plant.getPlantCountry());
				tempPlant.get().setTypeClassification(plant.getTypeClassification());
				tempPlant.get().setStatus(plant.getStatus());
				this.plantRepository.save(tempPlant.get());
			}
		} else {
			Plant newPlant = new Plant();
			newPlant.setStatus(plant.getStatus());
			newPlant.setName(plant.getName());
			newPlant.setObs("");
			newPlant.setBstate(0);
			newPlant.setPlantCountry(plant.getPlantCountry());
			newPlant.setTypeClassification(plant.getTypeClassification());
			this.plantRepository.save(newPlant);
		}
	}
}
