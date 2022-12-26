package api.local.managebd.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.JapanFilesDto;
import api.local.managebd.dto.Request;
import api.local.managebd.dto.ModelDto;
import api.local.managebd.dto.NetCommerceDto;
import api.local.managebd.dto.Response;
import api.local.managebd.dto.SalesDto;
import api.local.managebd.dto.SparePartsDto;
import api.local.managebd.enums.EnumStatusError;
import api.local.managebd.enums.EnumType;
import api.local.managebd.service.IManagebdService;
import lombok.extern.java.Log;

@Log
@Service
public class ManagebdService implements IManagebdService {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private SparePartsService sparePartsService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private NetCommerceService netCommerceService;
	
	@Autowired
	private JapanFilesService japanFilesService;
	
	ObjectMapper objectMapper = new ObjectMapper();
		
	@Override
	public <T> BaseResponse<?> typeService(Request<T> dataDto) throws JsonMappingException, JsonProcessingException, ParseException {
		if(dataDto.getType().equals(EnumType.MODEL.getType())) {
			try {
				log.info("Start method save model");
				ModelDto modelDto = new ModelDto();
				modelDto = objectMapper.convertValue(dataDto.getData(), new TypeReference<ModelDto>() {});
				
				return this.modelService.saveModel(modelDto, dataDto.getType());
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}						
		} else if(dataDto.getType().equals(EnumType.SPARE_PARTS.getType())) {
			try {
				log.info("Start method save spare parts");
				SparePartsDto spareParts = new SparePartsDto();
				spareParts = objectMapper.convertValue(dataDto.getData(), new TypeReference<SparePartsDto>() {});	

				return this.sparePartsService.insertSparePartsDto(spareParts, dataDto.getType());
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}
			
		} else if(dataDto.getType().equals(EnumType.SALES.getType())) {
			try {
				log.info("Start method save sales");
				SalesDto salesDto = new SalesDto();
				salesDto = objectMapper.convertValue(dataDto.getData(), new TypeReference<SalesDto>() {});	
				
				return this.salesService.insertSales(salesDto, dataDto.getType());
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());		
			}
			
		} else if(dataDto.getType().equals(EnumType.NETCOMMERCE.getType())) {
			try {
				log.info("Start method save netcommerce");
				NetCommerceDto netCommerceDto = new NetCommerceDto();
				netCommerceDto = objectMapper.convertValue(dataDto.getData(), new TypeReference<NetCommerceDto>() {});	
				
				return this.netCommerceService.insertNetCommerce(netCommerceDto, dataDto.getType());
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());			
			}			
		
		} else if(dataDto.getType().equals(EnumType.JAPAN_FILES.getType())) {
			try {
				log.info("Start method save japan files");
				JapanFilesDto japanFilesDto = new JapanFilesDto();
				japanFilesDto = objectMapper.convertValue(dataDto.getData(), new TypeReference<JapanFilesDto>() {});	
				
				return this.japanFilesService.insertapanFiles(japanFilesDto, dataDto.getType());
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());		
			}
		} else {
			return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());				
		}
	}
	
	@Override
	public <T> BaseResponse<?> getData(Request<T> dataDto) throws JsonMappingException, JsonProcessingException, ParseException {
		if(dataDto.getType().equals(EnumType.MODEL.getType())) {
			try {
				log.info("Start method get model");
				if(dataDto.getModel() == null || dataDto.getModel().equals("")) {
					return new Response<>().response(EnumStatusError.MODEL_EMPTY.getType(), EnumStatusError.MODEL_EMPTY.getValue(), EnumType.MODEL.getType());
				}
				ModelDto modelDto = this.modelService.getModel(dataDto.getModel());
				if(modelDto == null) {
					return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), EnumType.MODEL.getType());
				} else {
					return new Response<>().response(modelDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), EnumType.MODEL.getType());
				}
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}						
		} else if(dataDto.getType().equals(EnumType.SPARE_PARTS.getType())) {
			try {
				log.info("Start method get spare parts");
				if(dataDto.getNum_part() == null || dataDto.getNum_part().equals("")) {
					return new Response<>().response(EnumStatusError.NOPART_EMPTY.getType(), EnumStatusError.NOPART_EMPTY.getValue(), EnumType.SPARE_PARTS.getType());
				}
				SparePartsDto sparePartsDto = this.sparePartsService.getSpareParts(dataDto.getNum_part());
			
				if(sparePartsDto == null) {
					return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), EnumType.SPARE_PARTS.getType());
				} else {
					return new Response<>().response(sparePartsDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), EnumType.SPARE_PARTS.getType());
				}
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}						
		} else if(dataDto.getType().equals(EnumType.SALES.getType())) {
			try {
				log.info("Start method get sales");
				if(dataDto.getVin() == null || dataDto.getVin().equals("")) {
					return new Response<>().response(EnumStatusError.VIN_EMPTY.getType(), EnumStatusError.VIN_EMPTY.getValue(), EnumType.SALES.getType());
				}
				SalesDto salesDto = this.salesService.getSales(dataDto.getVin());
			
				if(salesDto == null) {
					return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), EnumType.SALES.getType());
				} else {
					return new Response<>().response(salesDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), EnumType.SALES.getType());
				}
			} catch (Exception e) {
				log.warning(e.getMessage());
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}	
			
		} else if(dataDto.getType().equals(EnumType.NETCOMMERCE.getType())) {
			try {
				log.info("Start method get netcommerce");
				if(dataDto.getVin() == null || dataDto.getVin().equals("")) {
					return new Response<>().response(EnumStatusError.VIN_EMPTY.getType(), EnumStatusError.VIN_EMPTY.getValue(), EnumType.NETCOMMERCE.getType());
				}
				NetCommerceDto netCommerceDto = this.netCommerceService.getNetCommerce(dataDto.getVin());
			
				if(netCommerceDto == null) {
					return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), EnumType.NETCOMMERCE.getType());
				} else {
					return new Response<>().response(netCommerceDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), EnumType.NETCOMMERCE.getType());
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}	
		} else if(dataDto.getType().equals(EnumType.JAPAN_FILES.getType())) {			
			try {
				log.info("Start method get japan files");
				JapanFilesDto japanFilesDto = new JapanFilesDto();
				if((dataDto.getNum_part() == null || dataDto.getNum_part().equals("")) &&
					(dataDto.getFile() == null || dataDto.getFile().equals(""))) {
					return new Response<>().response(EnumStatusError.EMPTY_NUM_PART_OR_FILENAME.getType(), EnumStatusError.EMPTY_NUM_PART_OR_FILENAME.getValue(), EnumType.JAPAN_FILES.getType());
				}
				
				if(dataDto.getNum_part() != null && !dataDto.getNum_part().equals("")) {
					japanFilesDto = this.japanFilesService.getJapanFilesByNumPart(dataDto.getNum_part());
				} else {
					if(dataDto.getFile() != null && !dataDto.getFile().equals("")) {
						japanFilesDto = this.japanFilesService.getJapanFilesByfile(dataDto.getFile());
					}
				}
							
				if(japanFilesDto == null) {
					return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), EnumType.JAPAN_FILES.getType());
				} else {
					return new Response<>().response(japanFilesDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), EnumType.JAPAN_FILES.getType());
				}
			} catch (Exception e) {
				return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());	
			}
		} else {
			return new Response<>().response(dataDto, EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), dataDto.getType());			
		}
	
	}
}
