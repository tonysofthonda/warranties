package api.local.managebd.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.ModelDto;
import api.local.managebd.dto.Response;
import api.local.managebd.enums.EnumActions;
import api.local.managebd.enums.EnumStatusError;
import api.local.managebd.model.Model;
import api.local.managebd.repository.ModelRepository;
import api.local.managebd.service.IModelService;
import api.local.managebd.utils.DateUtils;
import lombok.extern.java.Log;

@Log
@Service
public class ModelService implements IModelService {
	
	@Autowired
	private ModelRepository modelRepository;
	
	@Autowired
	private DateUtils dateUtils;

	public ModelDto getModel(final String model) {
		log.info("start get by model in ModelService ");
		return this.convertEntityToDto(this.modelRepository.findByModel(model));
	}
	
	private List<ModelDto> getAllModels() {
		List<ModelDto> listModelDto = new ArrayList<>();
		
		Iterable<Model> mapModel = this.modelRepository.findAll();

		if(mapModel != null) {
			mapModel.forEach(data -> {
				listModelDto.add(this.convertEntityToDto(data));
			});
		}
		return listModelDto;
	}
	
	public <T> BaseResponse<?> saveModel(ModelDto dataDto, final String type) throws ParseException {
		log.info("insert in ModelService");
		if(this.validData(dataDto)) {
			return new Response<>().response(dataDto, EnumStatusError.INCOMPLETE_DATA.getType().concat(this.dataIncomplete(dataDto)), 
					EnumStatusError.INCOMPLETE_DATA.getValue(), type);
		} else {
			Model model = this.modelRepository.findByModel(dataDto.getModel());
			if(model == null) {
				model = new Model();
				Long id = Long.valueOf(this.getAllModels().size() + 1);
				BeanUtils.copyProperties(dataDto, model);
				model.setId(id);
				model.setDate_creation(this.dateUtils.parseDate());
				model.setObs(this.dateUtils.transformObs(EnumActions.POST.getAction()));
				model.setBstate(1);
				ModelDto mode = this.convertEntityToDto(modelRepository.save(model));
				return new Response<>().response(mode, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), type);
			}
			return new Response<>().response(model, EnumStatusError.RECORD_ALREADY_EXIST.getType(), EnumStatusError.RECORD_ALREADY_EXIST.getValue(), type);
		}
	}
	
	private ModelDto convertEntityToDto(final Model data) {
		ModelDto modelDto = null;
		if(data != null) {
			modelDto = new ModelDto();
			BeanUtils.copyProperties(data, modelDto);
		}
		return modelDto;
	}
	
	private boolean validData(final ModelDto modelDto) {
		if((modelDto.getModel() == null || modelDto.getModel().equals("")) ||
		   modelDto.getAnio() == null ||
		   (modelDto.getDescription() == null || modelDto.getDescription().equals("")) ||
		   (modelDto.getVds() == null || modelDto.getVds().equals("")) ||
		   (modelDto.getOrigin() == null || modelDto.getOrigin().equals(""))) {
			return true;
		} else {
			return false;
		}
	}
	
	private String dataIncomplete(final ModelDto modelDto) {
		StringBuilder sb = new StringBuilder();
		if(modelDto.getModel() == null || modelDto.getModel().equals("")) {
			sb.append(" model ");
		}
		if(modelDto.getAnio() == null) {
			sb.append(" anio" );
		}
		
		if(modelDto.getDescription() == null || modelDto.getDescription().equals("")) {
			sb.append(" description ");
		}
				
		if(modelDto.getVds() == null || modelDto.getVds().equals("")) {
			sb.append(" vds ");
		}
		
		if(modelDto.getOrigin() == null || modelDto.getOrigin().equals("")) {
			sb.append(" origin ");
		}
		return sb.toString();
	}
}
