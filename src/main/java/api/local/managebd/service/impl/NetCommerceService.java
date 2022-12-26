package api.local.managebd.service.impl;

import java.text.ParseException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.CustumerDto;
import api.local.managebd.dto.NetCommerceDto;
import api.local.managebd.dto.Response;
import api.local.managebd.dto.WarrantyPolicyDto;
import api.local.managebd.enums.EnumActions;
import api.local.managebd.enums.EnumStatusError;
import api.local.managebd.model.NetCommerceCustomer;
import api.local.managebd.model.NetCommerceHeader;
import api.local.managebd.model.NetCommercePolicy;
import api.local.managebd.repository.NetCommerceCustomerRepository;
import api.local.managebd.repository.NetCommerceHeaderRepository;
import api.local.managebd.repository.NetCommercePolicyRepository;
import api.local.managebd.service.INetCommerceService;
import api.local.managebd.utils.DateUtils;
import lombok.extern.java.Log;

@Log
@Service
public class NetCommerceService implements INetCommerceService {
		
	@Autowired
	private NetCommerceCustomerRepository netCustomerRepository;
	
	@Autowired
	private NetCommerceHeaderRepository netHeaderRepository;
	
	@Autowired
	private NetCommercePolicyRepository netCommercePolicyRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	public NetCommerceDto getNetCommerce(final String vin) {
		log.info("get netcommerce : " + vin);
		NetCommerceHeader commerceHeader = this.netHeaderRepository.findByVin(vin);
		return this.convertEntityToDto(commerceHeader);
	}
	
	private WarrantyPolicyDto findWarrantyPolicy(final NetCommerceHeader netCommerceHeader) {
		NetCommercePolicy netCommercePolicy = netCommercePolicyRepository.findByIdHeader(netCommerceHeader.getId_header());
		WarrantyPolicyDto warrantyPolicy = new WarrantyPolicyDto();
		if(netCommercePolicy != null) {
			BeanUtils.copyProperties(netCommercePolicy, warrantyPolicy);
		}
		
		return warrantyPolicy;
	}
	
	private CustumerDto findCustumer(final NetCommerceHeader netCommerceHeader) {
		NetCommerceCustomer netCommerceCustomer = netCustomerRepository.findByIdHeader(netCommerceHeader.getId_header());
		
		CustumerDto custumerDto = new CustumerDto();
		if(netCommerceCustomer != null) {
			BeanUtils.copyProperties(netCommerceCustomer, custumerDto);
		}
		
		return custumerDto;
	}
	
	private boolean validData(final NetCommerceDto netCommerceDto) {
		if(netCommerceDto.getVin() == null || 
		   netCommerceDto.getNomotor() == null || 
		   netCommerceDto.getModel() == null ||
		   netCommerceDto.getYear() == null || 
		   netCommerceDto.getColor() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	private String dataIncomplete(final NetCommerceDto netCommerceDto) {
		StringBuilder sb = new StringBuilder();
		if(netCommerceDto.getModel() == null || netCommerceDto.getModel().equals("")) {
			sb.append(" model ");
		}
		if(netCommerceDto.getYear() == null) {
			sb.append(" year" );
		}
		if(netCommerceDto.getVin() == null || netCommerceDto.getVin().equals("")) {
			sb.append(" vin" );
		}
		
		if(netCommerceDto.getDate_sale() == null) {
			sb.append(" date_sale ");
		}
		
		if(netCommerceDto.getNomotor() == null || netCommerceDto.getNomotor().equals("")) {
			sb.append(" nomotor ");
		}
		
		if(netCommerceDto.getColor() == null || netCommerceDto.getColor().equals("")) {
			sb.append(" color ");
		}

		return sb.toString();
	}
	
	private NetCommerceDto convertEntityToDto(final NetCommerceHeader data) {
		NetCommerceDto netCommerce = null;
		if(data != null) {
			netCommerce = new NetCommerceDto();
			netCommerce.setData_custumer(this.findCustumer(data));
			netCommerce.setData_guarantee(this.findWarrantyPolicy(data));
			BeanUtils.copyProperties(data, netCommerce);
		}
		return netCommerce;
	}
	
	public <T> BaseResponse<?> insertNetCommerce(final NetCommerceDto netCommerceDto, final String type) throws ParseException {
		if(this.validData(netCommerceDto)) {
			return new Response<>().response(netCommerceDto, EnumStatusError.INCOMPLETE_DATA.getType().concat(dataIncomplete(netCommerceDto)),
					EnumStatusError.INCOMPLETE_DATA.getValue(), type);
		} else {
			NetCommerceHeader netCommerceHeader = this.netHeaderRepository.findByVin(netCommerceDto.getVin());
			if(netCommerceHeader == null) {
				Long id = this.netHeaderRepository.count();

				netCommerceHeader = new NetCommerceHeader();
				netCommerceHeader.setBstate(1);
				netCommerceHeader.setName_dealer(netCommerceDto.getName_dealer());
				netCommerceHeader.setNum_dealer(netCommerceDto.getNum_dealer());
				netCommerceHeader.setId_header(id + 1);
				netCommerceHeader.setDate_creation(this.dateUtils.parseDate());
				netCommerceHeader.setObs(this.dateUtils.transformObs(EnumActions.POST.getAction()));
				this.saveCustumer(netCommerceHeader, netCommerceDto);
				this.saveWarrantyPolicy(netCommerceHeader, netCommerceDto);
				BeanUtils.copyProperties(netCommerceDto, netCommerceHeader);
				netCommerceHeader = this.netHeaderRepository.save(netCommerceHeader);
				
				return new Response<>().response(this.convertEntityToDto(netCommerceHeader), EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), type);
			}
			return new Response<>().response(netCommerceDto, EnumStatusError.RECORD_ALREADY_EXIST.getType(), EnumStatusError.RECORD_ALREADY_EXIST.getValue(), type);
		}
	}
	
	private NetCommercePolicy saveWarrantyPolicy(final NetCommerceHeader netCommerceHeader, final NetCommerceDto netCommerceDto) throws ParseException {
		NetCommercePolicy netCommercePolicy = this.netCommercePolicyRepository.findByIdHeader(netCommerceHeader.getId_header());

		if(netCommercePolicy == null ) {
			netCommercePolicy = new NetCommercePolicy();
			BeanUtils.copyProperties(netCommerceDto.getData_guarantee(), netCommercePolicy);
			Long id = this.netCommercePolicyRepository.count();
			netCommercePolicy.setBstate(1);
			netCommercePolicy.setId_guarantee(id + 1l);
			netCommercePolicy.setVin(netCommerceDto.getVin());
			netCommercePolicy.setIdHeader(netCommerceHeader.getId_header());
			netCommercePolicy.setDate_creation(this.dateUtils.parseDate());
			netCommercePolicy.setObs(this.dateUtils.transformObs(EnumActions.POST.getAction()));
			netCommercePolicyRepository.save(netCommercePolicy);
		}
		
		return netCommercePolicy;
	}
	
	private NetCommerceCustomer saveCustumer(final NetCommerceHeader netCommerceHeader, final NetCommerceDto netCommerceDto) throws ParseException {
		NetCommerceCustomer netCommerceCustomer = this.netCustomerRepository.findByIdHeader(netCommerceHeader.getId_header());
		
		if(netCommerceCustomer == null) {
			netCommerceCustomer = new NetCommerceCustomer();
			BeanUtils.copyProperties(netCommerceDto.getData_custumer(), netCommerceCustomer);
			Long id = this.netCustomerRepository.count();
			netCommerceCustomer.setBstate(1);
			netCommerceCustomer.setDate_creation(this.dateUtils.parseDate());
			netCommerceCustomer.setId_customer(id + 1);
			netCommerceCustomer.setVin(netCommerceDto.getVin());
			netCommerceCustomer.setIdHeader(netCommerceHeader.getId_header());
			netCommerceCustomer.setObs(this.dateUtils.transformObs(EnumActions.POST.getAction()));
			netCustomerRepository.save(netCommerceCustomer);
		}
		
		return netCommerceCustomer;
	}
}
