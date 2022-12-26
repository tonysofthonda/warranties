package api.local.netcommerce.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import api.local.netcommerce.dto.BaseResponse;
import api.local.netcommerce.dto.CustumerDto;
import api.local.netcommerce.dto.NetCommerceDto;
import api.local.netcommerce.dto.Request;
import api.local.netcommerce.dto.RequestManagebdDto;
import api.local.netcommerce.dto.RequestManagedbDataDto;
import api.local.netcommerce.dto.Response;
import api.local.netcommerce.dto.ResponseEncode;
import api.local.netcommerce.dto.ResponseNetcommerceDto;
import api.local.netcommerce.dto.WarrantyPolicyDto;
import api.local.netcommerce.enums.EnumActions;
import api.local.netcommerce.enums.EnumStatusError;
import api.local.netcommerce.enums.EnumType;
import api.local.netcommerce.service.NetcommerceService;
import api.local.netcommerce.service.RemoteService;
import lombok.extern.java.Log;

@Log
@Service
public class NetcommerceServiceImpl implements NetcommerceService {
		  
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	private Client client;
	
	@Value("${netcommerce.secret-key}")
	private String secretKey;
	
	@Value("${netcommerce.url-api}")
	private String urlApi;
	
	@Value("${netcommerce.user}")
	private String user;
	
	@Value("${netcommerce.pass}")
	private String pass;
	
	@Value("${netcommerce.encode}")
	private String encode;
		
	@Override
	public BaseResponse<?> checkVinManagebd(final Request requestDto) throws Exception {
		log.info("start checkVinManagebd in NetcommerceServiceImpl");
		if(requestDto.getVin() == null || requestDto.getVin().equals("")) {
			return new Response<>().response(EnumStatusError.VIN_EMPTY.getType(), EnumStatusError.VIN_EMPTY.getValue());
		}

		RequestManagebdDto request = new RequestManagebdDto();
		request.setAction(EnumActions.GET.getAction());
		request.setVin(requestDto.getVin());
		request.setType(EnumType.NETCOMMERCE.getType());
		
		BaseResponse<?> baseResponse = remoteService.getManagebd(request);
		if(baseResponse.getStatus() != 1) {
			baseResponse = this.getVin(requestDto.getVin());
		}
		return baseResponse;
	}

	private BaseResponse<?> getVin(String vin) throws Exception {
		ResponseEncode[] responseToken = this.generateToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + responseToken[0].getToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Gson gson = new Gson();

		String jsonString = new JSONObject()
                .put("vin", getEncode(vin))
                .put("code_auth", responseToken[0].getCode_auth())
                .toString();
		
		ResponseEntity<String> response = this.client.getWithBody(jsonString, headers);
		ResponseNetcommerceDto[] responseEncode = gson.fromJson(response.getBody(), ResponseNetcommerceDto[].class);
		RequestManagedbDataDto<NetCommerceDto> request = new RequestManagedbDataDto<NetCommerceDto>();
		request.setAction(EnumActions.POST.getAction());
		request.setType(EnumType.NETCOMMERCE.getType());
		request.setData(createNetcommerceDto(responseEncode));
		return remoteService.insertManagebd(request);
        
	}
	
	private NetCommerceDto createNetcommerceDto(ResponseNetcommerceDto[] response) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		CustumerDto data_custumer = new CustumerDto();
		data_custumer.setCity(getDecode(response[0].getData()[0].getSalesDto()[0].getCustomerDto()[0].getCity()));
		data_custumer.setEmail(getDecode(response[0].getData()[0].getSalesDto()[0].getCustomerDto()[0].getEmail()));
		data_custumer.setName(getDecode(response[0].getData()[0].getSalesDto()[0].getCustomerDto()[0].getName()));
		
		WarrantyPolicyDto data_guarantee = new WarrantyPolicyDto();
		data_guarantee.setDate_invoice(response[0].getData()[0].getGuaranteepolicyDto().length == 0 ? null : formatter.parse(getDecode(response[0].getData()[0].getGuaranteepolicyDto()[0].getDateInvoice())));
		data_guarantee.setFolio(response[0].getData()[0].getGuaranteepolicyDto().length == 0 ? null : getDecode(response[0].getData()[0].getGuaranteepolicyDto()[0].getFolio()));
		data_guarantee.setFolio_invoice(getDecode(response[0].getData()[0].getSalesDto()[0].getFolioInvoice()));
		
		NetCommerceDto netDto = new NetCommerceDto();
		netDto.setColor(getDecode(response[0].getData()[0].getColor()));
		netDto.setName_dealer(getDecode(response[0].getData()[0].getName_dealer()));
		netDto.setNum_dealer(getDecode(response[0].getData()[0].getNum_dealer()));
		netDto.setData_custumer(data_custumer);
		netDto.setData_guarantee(data_guarantee);
		netDto.setDate_sale(response[0].getData()[0].getSalesDto()[0].getDateSale() == null ? null : formatter.parse(getDecode(response[0].getData()[0].getSalesDto()[0].getDateSale())));
		netDto.setModel(getDecode(response[0].getData()[0].getModel()));
		netDto.setNomotor(getDecode(response[0].getData()[0].getNo_motor()));
		netDto.setVin(getDecode(response[0].getData()[0].getVin()));
		netDto.setYear(Integer.valueOf(getDecode(response[0].getData()[0].getYear())));
		
		return netDto;		
	}

	private String getEncode(final String value) {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> response = restTemplate.postForEntity(encode, "[" + generateJson(value, "encode") + "]", String.class);
		return response.getBody();
	}
	
	private String getDecode(final String value) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
  		
		ResponseEntity<String> response = restTemplate.postForEntity(encode, "[" + generateJson(value, "decode") + "]", String.class);
		return response.getBody();
	}
	
	private ResponseEncode[] generateToken() {
		RestTemplate restTemplate = new RestTemplate();

		Map<String, String> map = new HashMap<String, String>();
		map.put("user", user);
		map.put("pass", this.getEncode(pass));
		ResponseEntity<String> response = restTemplate.postForEntity(urlApi, map, String.class);
		Gson gson = new Gson();
		ResponseEncode[] responseEncode = gson.fromJson(response.getBody(), ResponseEncode[].class);
		return responseEncode;
	}
	
	private String generateJson(String value, String action) {
		String jsonString = new JSONObject()
				.put("secret_key", secretKey)
				.put("value", value)
				.put("action", action)
                .toString();
		return jsonString;
	}

}
