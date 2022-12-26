package api.local.sales.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.NetcommerceDto;
import api.local.sales.dto.Request;
import api.local.sales.dto.RequestBpcsDto;
import api.local.sales.dto.RequestManagebdDto;
import api.local.sales.dto.RequestNetcommerceDto;
import api.local.sales.dto.RequestRemote;
import api.local.sales.dto.SalesDto;
import api.local.sales.enums.EnumActions;
import api.local.sales.enums.EnumType;
import api.local.sales.service.SalesService;
import lombok.extern.java.Log;

@Log
@Service
public class SalesServiceImpl implements SalesService {
	
	@Autowired
	private RemoteServiceImpl remoteService;
	
	@Autowired
	private RemoteNetCommeceServiceImpl netcommerceService;
	
	@Autowired
	private RemoteBpcsServiceImpl bpcsService;

	@Override
	public BaseResponse<?> sales(final Request request) {
		log.info("start SalesServiceImpl");
		RequestManagebdDto requestRemote = new RequestManagebdDto();
		requestRemote.setAction(EnumActions.GET.getAction());
		requestRemote.setType(EnumType.SALES.getType());
		requestRemote.setVin(request.getVin());
		BaseResponse<?> response = remoteService.getManagebd(requestRemote);
		return response.getStatus() == 3 ? checkInNetCommerce(response.getStatus(), request.getVin()) : response;
		
	}
	
	private BaseResponse<?> checkInNetCommerce(final Integer status, final String vin) {
		RequestNetcommerceDto requestRemote = new RequestNetcommerceDto();
		requestRemote.setVin(vin);
		BaseResponse<? extends NetcommerceDto> response = netcommerceService.getNetcommerce(requestRemote);
		log.info("method checkInNetCommerce");
		return response.getStatus() == 3 ? checkInBpcs(response.getStatus(), vin) : 
			(response.getStatus() == 1 ? checkInBpcsAndSaveManagedb(status, vin, response.getData()) : response);
	}
	
	private BaseResponse<?> checkInBpcs(final Integer status, final String vin) {
		RequestBpcsDto request = new RequestBpcsDto();
		request.setVin(vin);
		request.setType(EnumType.SALES.getType());
		log.info("method checkInBpcs");
		BaseResponse<? extends SalesDto> response = bpcsService.getBpcs(request);
		if(response.getStatus() == 1 || response.getStatus() == 2) {
			response.getData().setModel("");
			response.getData().setNomotor("");
		}
		return response;
	}
	
	private BaseResponse<?> checkInBpcsAndSaveManagedb(final Integer status, final String vin, final NetcommerceDto netcommerceDto) {
		RequestBpcsDto request = new RequestBpcsDto();
		request.setVin(vin);
		request.setType(EnumType.SALES.getType());
		BaseResponse<? extends SalesDto> response = bpcsService.getBpcs(request);
		if(response.getStatus() != 0) {
			SalesDto sales = new SalesDto();
			RequestRemote<? extends SalesDto> requestRemote = new RequestRemote<>();
			sales.setVin(vin);
			sales.setNum_dealer((netcommerceDto.getNum_dealer() != null) ? netcommerceDto.getNum_dealer() : null);
			sales.setName_dealer((netcommerceDto.getName_dealer() != null) ? netcommerceDto.getName_dealer() : null);
			sales.setDate_invoice((netcommerceDto.getData_guarantee().getDate_invoice() != null) ? netcommerceDto.getData_guarantee().getDate_invoice() : new Date());
			sales.setModel(netcommerceDto.getModel());
			sales.setNomotor(netcommerceDto.getNomotor());
			requestRemote.setData(sales);
			requestRemote.setAction(EnumActions.POST.getAction());
			requestRemote.setType(EnumType.SALES.getType());
			requestRemote.setVin(vin);
			log.info("save in managedb");
			response = remoteService.insertManagebd(requestRemote);
		}
		log.info("method checkInBpcsAndSaveManagedb");
		return response;
	}
}
