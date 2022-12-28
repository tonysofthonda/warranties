package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Model;
import com.honda.hdm.warrantiesmg.app.domain.entity.Plant;
import com.honda.hdm.warrantiesmg.app.domain.repository.ModelRepository;
import com.honda.hdm.warrantiesmg.app.service.ModelService;
import com.honda.hdm.warrantiesmg.app.web.model.ModelDto;

@Service
public class ModelServiceImp implements ModelService {
	
	@Autowired
	private ModelRepository modelRepository;

	@Override
	public List<ModelDto> getAllModels() {
		List<ModelDto> modelDto = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Model> modelEntity = this.modelRepository.findAllOrderById();
		for(Model model: modelEntity) {
			modelDto.add(modelMapper.map(model, ModelDto.class));
		}
		return modelDto;
	}
	
	@Override
	public ModelDto getOneModel(final String model) {
		ModelDto modelDto = null;
		Model modelEntity = this.modelRepository.findOneByModel(model);
		if(modelEntity != null) {
			modelDto = new ModelDto();
			BeanUtils.copyProperties(modelEntity, modelDto);
		}
		
		return modelDto;
	}

	@Override
	public ModelDto getById(Long id) {
		ModelDto modelDto = new ModelDto();
		Optional<Model> model = modelRepository.findById(id);
		if(model.isPresent()) {
			BeanUtils.copyProperties(model, modelDto);
			return modelDto;
		}
		throw new RuntimeException("El modelo no existe");
	}

	@Override
	public void saveModel(ModelDto modelDto) {
		
		if(modelDto.getId() == null) {
			Model modelSave = new Model();
			Plant plant = new Plant();
			BeanUtils.copyProperties(modelDto.getPlant(), plant);
			modelSave.setName(modelDto.getName());
			modelSave.setModel(modelDto.getModel());			
			modelSave.setCodModel(modelDto.getCodModel());			
			modelSave.setSegment(modelDto.getSegment());
			modelSave.setYear(modelDto.getYear());		
			modelSave.setVds(modelDto.getVds());
			modelSave.setType(modelDto.getType());			
			modelSave.setPlant(plant);
			modelSave.setCchp(modelDto.getCchp());
			modelSave.setColor(modelDto.getColor());
			modelSave.setStatus(modelDto.getStatus());
			modelRepository.save(modelSave);
		} else {
			Optional<Model> dto = modelRepository.findById(modelDto.getId());
			Plant plant = new Plant();
			BeanUtils.copyProperties(modelDto.getPlant(), plant);
			dto.get().setName(modelDto.getName());
			dto.get().setModel(modelDto.getModel());			
			dto.get().setCodModel(modelDto.getCodModel());			
			dto.get().setSegment(modelDto.getSegment());
			dto.get().setYear(modelDto.getYear());		
			dto.get().setVds(modelDto.getVds());
			dto.get().setType(modelDto.getType());			
			dto.get().setPlant(plant);
			dto.get().setCchp(modelDto.getCchp());
			dto.get().setColor(modelDto.getColor());
			dto.get().setStatus(modelDto.getStatus());
			modelRepository.save(dto.get());
		}
	}

	@Override
	public void updateStatus(ModelDto modelDto) {
		Model model = modelRepository.findById(modelDto.getId()).orElseGet(null);
		if(model != null) {
			model.setStatus(modelDto.getStatus());
			modelRepository.save(model);
		}
		
	}
}
