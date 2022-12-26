package api.local.managebd.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.Response;
import api.local.managebd.dto.SparePartsDto;
import api.local.managebd.enums.EnumActions;
import api.local.managebd.enums.EnumStatusError;
import api.local.managebd.model.SpareParts;
import api.local.managebd.repository.SparePartsRepository;
import api.local.managebd.service.ISparePartsService;
import api.local.managebd.utils.DateUtils;
import lombok.extern.java.Log;

@Log
@Service
public class SparePartsService implements ISparePartsService {
	
	@Autowired
	private SparePartsRepository sparePartsRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	public SparePartsDto getSpareParts(final String numPart) {
		log.info("start get spare parts by num_part in SparePartsService");
		SpareParts sparePart = this.sparePartsRepository.findByNumPart(numPart);
		SparePartsDto spareParts = null;
		if(sparePart != null) {
			spareParts = new SparePartsDto();
			spareParts.setLabor_time(sparePart.getLabor_time());
			spareParts.setModel(sparePart.getModel());
			spareParts.setNum_part(sparePart.getNumPart());
			spareParts.setPrice(sparePart.getPrice());
		}
		return spareParts;
	}

	public List<SparePartsDto> getAllSpareParts() {
		List<SparePartsDto> listSparePartsDto = new ArrayList<SparePartsDto>();
		Iterable<SpareParts> sparePartsList = this.sparePartsRepository.findAll();
		
		if(sparePartsList != null) {
			sparePartsList.forEach(data -> {
				SparePartsDto spareParts = new SparePartsDto();
				BeanUtils.copyProperties(spareParts, data);
				listSparePartsDto.add(spareParts);
			});
		}
		return listSparePartsDto;
	}
	
	public <T> BaseResponse<?> insertSparePartsDto(final SparePartsDto dataDto, final String type) throws ParseException {
		log.info("start insert in SparePartsService");
		if(validData(dataDto)) {
			return new Response<>().response(dataDto, EnumStatusError.INCOMPLETE_DATA.getType().concat(dataIncomplete(dataDto)),
					EnumStatusError.INCOMPLETE_DATA.getValue(), type);
		} else {
			SpareParts spareParts = this.sparePartsRepository.findByNumPart(dataDto.getNum_part());
			if(spareParts == null) {
				spareParts = new SpareParts();
				Long id = this.sparePartsRepository.count() + 1;
				BeanUtils.copyProperties(dataDto, spareParts);
				spareParts.setId(id);
				spareParts.setNumPart(dataDto.getNum_part());
				spareParts.setDate_creation(this.dateUtils.parseDate());
				spareParts.setBstate(0);
				spareParts.setObs(this.dateUtils.transformObs(EnumActions.POST.getAction()));
				SparePartsDto data = this.convertEntityToDto(this.sparePartsRepository.save(spareParts));
				return new Response<>().response(data, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), type);
			}
			return new Response<>().response(dataDto, EnumStatusError.RECORD_ALREADY_EXIST.getType(), EnumStatusError.RECORD_ALREADY_EXIST.getValue(), type);
		}
	}

	private boolean validData(final SparePartsDto sparePartsDto) {
		if((sparePartsDto.getNum_part() == null || sparePartsDto.getNum_part().equals("")) ||
           sparePartsDto.getPrice() == null || 
		   (sparePartsDto.getModel() == null || sparePartsDto.getModel().equals("")) ||
		   sparePartsDto.getLabor_time() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	private String dataIncomplete(final SparePartsDto sparePartsDto) {
		StringBuilder sb = new StringBuilder();
		if(sparePartsDto.getModel() == null || sparePartsDto.getModel().equals("")) {
			sb.append(" model ");
		}
		if(sparePartsDto.getPrice() == null) {
			sb.append(" price " );
		}
		
		if(sparePartsDto.getLabor_time() == null) {
			sb.append(" labor_time " );
		}
		if(sparePartsDto.getNum_part() == null || sparePartsDto.getNum_part().equals("")) {
			sb.append(" num_part " );
		}
		
		return sb.toString();
	}
	
	
	private SparePartsDto convertEntityToDto(final SpareParts data) {
		SparePartsDto sparePartsDto = new SparePartsDto();
		BeanUtils.copyProperties(data, sparePartsDto);
		sparePartsDto.setNum_part(data.getNumPart());
		return sparePartsDto;
	}
}
