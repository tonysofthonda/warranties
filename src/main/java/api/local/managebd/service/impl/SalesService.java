package api.local.managebd.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.Response;
import api.local.managebd.dto.SalesDto;
import api.local.managebd.enums.EnumActions;
import api.local.managebd.enums.EnumStatusError;
import api.local.managebd.model.Sales;
import api.local.managebd.repository.SalesRepository;
import api.local.managebd.service.ISalesService;
import api.local.managebd.utils.DateUtils;
import lombok.extern.java.Log;

@Log
@Service
public class SalesService implements ISalesService {

	@Autowired
	private SalesRepository salesRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	public SalesDto getSales(final String vin) {
		log.info("get sales in SalesService");
		Sales sales = this.salesRepository.findByVin(vin);
		return this.convertEntityToDto(sales);
	}
	
	public <T> BaseResponse<?> insertSales(final SalesDto salesDto, final String type) throws ParseException {
		log.info("insert sales in SalesService");
		List<SalesDto> salesListDto = new ArrayList<SalesDto>();
		if(this.validData(salesDto)) {
			return new Response<>().response(salesListDto, EnumStatusError.INCOMPLETE_DATA.getType().concat(dataIncomplete(salesDto)),
					EnumStatusError.INCOMPLETE_DATA.getValue(), type);
		} else {
			Sales sales = this.salesRepository.findByVin(salesDto.getVin());
			if(sales == null) {
				sales = new Sales();
				Long id = this.salesRepository.count() + 1;
				BeanUtils.copyProperties(salesDto, sales);
				sales.setObs(this.dateUtils.transformObs(EnumActions.POST.getAction()));
				sales.setBstate(1);
				sales.setId(id);
				sales.setDate_creation(this.dateUtils.parseDate());
				SalesDto salesD = convertEntityToDto(salesRepository.save(sales));
				return new Response<>().response(salesD, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), type);
			}
			return new Response<>().response(salesListDto, EnumStatusError.RECORD_ALREADY_EXIST.getType(), EnumStatusError.RECORD_ALREADY_EXIST.getValue(), type);
		}
	}
	
	private boolean validData(final SalesDto salesDto) {
		if((salesDto.getVin() == null || salesDto.getVin().equals("")) || 
		   (salesDto.getNum_dealer() == null || salesDto.getNum_dealer().equals("")) ||
		   (salesDto.getName_dealer() == null || salesDto.getName_dealer().equals("")) || 
		   salesDto.getDate_invoice() == null ||
		   (salesDto.getModel() == null || salesDto.getModel().equals("")) ||
		   (salesDto.getNomotor() == null || salesDto.getNomotor().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	private String dataIncomplete(final SalesDto salesDto) {
		StringBuilder sb = new StringBuilder();
		if(salesDto.getModel() == null || salesDto.getModel().equals("")) {
			sb.append(" model ");
		}
		if(salesDto.getDate_invoice() == null) {
			sb.append(" invoice_date " );
		}
		if(salesDto.getVin() == null || salesDto.getVin().equals("")) {
			sb.append(" vin" );
		}
		
		if(salesDto.getNum_dealer() == null || salesDto.getNum_dealer().equals("")) {
			sb.append(" num_dealer ");
		}
		
		if(salesDto.getNomotor() == null || salesDto.getNomotor().equals("")) {
			sb.append(" motor ");
		}
		
		if(salesDto.getName_dealer() == null || salesDto.getName_dealer().equals("")) {
			sb.append(" name_dealer ");
		}

		return sb.toString();
	}
	
	
	private SalesDto convertEntityToDto(final Sales data) {
		SalesDto sales = null;
		if(data != null) {
			sales = new SalesDto();
			BeanUtils.copyProperties(data, sales);
		}
		return sales;
	}
}
