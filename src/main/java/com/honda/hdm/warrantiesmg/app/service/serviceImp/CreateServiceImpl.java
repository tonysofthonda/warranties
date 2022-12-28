package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.CreateService;
import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.Location;
import com.honda.hdm.warrantiesmg.app.domain.entity.Model;
import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceClient;
import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;
import com.honda.hdm.warrantiesmg.app.domain.repository.CreateServiceRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.DealerRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.LocationRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ModelRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ServiceClientRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.VinRepository;
import com.honda.hdm.warrantiesmg.app.service.CreateServiceService;
import com.honda.hdm.warrantiesmg.app.web.model.CreateServiceDetailDto;
import com.honda.hdm.warrantiesmg.app.web.model.CreateServiceDto;
import com.honda.hdm.warrantiesmg.app.web.model.HistoryServiceDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoClientDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoCreateServiceDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoMobileUnitDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoReportDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoServiceDetailDto;
import com.honda.hdm.warrantiesmg.configuration.WmqBusinessLogicException;
import com.honda.hdm.warrantiesmg.util.DateUtils;

@Service
public class CreateServiceImpl implements CreateServiceService {

	@Autowired
	private ServiceClientRepository serviceClientRepository;

	@Autowired
	private CreateServiceRepository createServiceRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private VinRepository vinRepository;

	@Autowired
	private DateUtils dateUtils;

	@Override
	public List<CreateServiceDto> getAllServiceCreated() {
		List<CreateServiceDto> createServiceDto = new ArrayList<CreateServiceDto>();
		List<CreateService> createServiceList = createServiceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		if (!createServiceList.isEmpty()) {
			createServiceList.stream().forEach(data -> {
				CreateServiceDto create = new CreateServiceDto();
				create.setId(data.getId());
				create.setCreateDate(dateUtils.timestampToString(data.getCreateTimestamp(), "dd/MM/yyyy"));
				create.setDateService(dateUtils.timestampToString(data.getDateService(), "dd/MM/yyyy"));
				create.setDealer(data.getDealer().getDealerNumber());
				create.setVin(data.getVin().getVin());
				create.setStatus(data.getStatus());
				create.setServiceNumber(data.getServiceNumber());
				create.setKm(data.getKm().toString());
				createServiceDto.add(create);
			});
		}
		return createServiceDto;
	}

	@Override
	public CreateServiceDetailDto getServiceCreatedDetail(long id) {
		CreateServiceDetailDto serviceCreatedDetail = new CreateServiceDetailDto();
		CreateService serviceCreated = createServiceRepository.findById(id);
		if (!Objects.isNull(serviceCreated)) {
			serviceCreatedDetail.setVin(serviceCreated.getVin());
			serviceCreatedDetail.setDealer(serviceCreated.getDealer());
			serviceCreatedDetail.setServiceClient(serviceCreated.getServiceClient());
			serviceCreatedDetail.setCreateReportBy(serviceCreated.getCreateReportBy());
			serviceCreatedDetail.setCreateTimestamp(serviceCreated.getCreateTimestamp());
			serviceCreatedDetail.setDateService(dateUtils.timestampToString(serviceCreated.getDateService(), "yyyy-MM-dd"));
			serviceCreatedDetail.setId(serviceCreated.getId());
			serviceCreatedDetail.setKm(serviceCreated.getKm());
			serviceCreatedDetail.setModel(serviceCreated.getModel());
			serviceCreatedDetail.setOwner(serviceCreated.getOwner());
			serviceCreatedDetail.setSaleDate(dateUtils.timestampToString(serviceCreated.getSaleDate(), "yyyy-MM-dd"));
			serviceCreatedDetail.setServiceDetail(serviceCreated.getServiceDetail());
			serviceCreatedDetail.setServiceNumber(serviceCreated.getServiceNumber());
			serviceCreatedDetail.setServicePerformed(serviceCreated.getServicePerformed());
			serviceCreatedDetail.setSoldBy(serviceCreated.getSoldBy());
			serviceCreatedDetail.setStatus(serviceCreated.getStatus());
			serviceCreatedDetail.setTechnical(serviceCreated.getTechnical());
			serviceCreatedDetail.setUpdateTimestamp(serviceCreated.getUpdateTimestamp());
		}
		return serviceCreatedDetail;
	}

	@Override
	public void saveCreateService(InfoCreateServiceDto infoCreateService) {
		ServiceClient serviceClient = this.saveInformationClient(infoCreateService.getInfoClientDto());
		this.saveCreateService(infoCreateService.getInfoReportDto(), infoCreateService.getInfoMobileUnitDto(),
				infoCreateService.getInfoServiceDetailDto(), serviceClient);
	}

	// *Function to return last order number*//
	@Override
	public String getServiceNumber() {
		List<CreateService> service = createServiceRepository.findByOrderByIdDesc();
		int serviceNumber = Integer.parseInt(service.get(0).getServiceNumber()) + 1;
		return "" + serviceNumber;
	}

	private ServiceClient saveInformationClient(final InfoClientDto infoClientDto) {
		Optional<Location> location = locationRepository.findByIdAndStateId(infoClientDto.getTownship(),
				infoClientDto.getState());
		ServiceClient serviceClient = new ServiceClient();
		if (location.isPresent()) {
			serviceClient.setAddress(infoClientDto.getAddress());
			serviceClient.setCreateTimestamp(new Timestamp(new Date().getTime()));
			serviceClient.setEmail(infoClientDto.getEmail());
			serviceClient.setName(infoClientDto.getClientName());
			serviceClient.setPhone(infoClientDto.getPhone());
			serviceClient.setLocation(location.get());
			serviceClient.setUpdateTimestamp(new Timestamp(new Date().getTime()));
			serviceClient = serviceClientRepository.save(serviceClient);
		}
		return serviceClient;
	}

	private void saveCreateService(final InfoReportDto infoReportDto, final InfoMobileUnitDto infoMobileUnit,
			final InfoServiceDetailDto serviceDetail, final ServiceClient serviceClient) {

		Optional<Dealer> dealer = dealerRepository.findByDealerNumber(infoReportDto.getNumberDealer());

		if (!dealer.isPresent()) {
			throw new WmqBusinessLogicException("El dealer no existe");
		}

		Optional<Model> model = modelRepository.findById(infoMobileUnit.getModel());

		// Consulta BPCS
		if (!model.isPresent()) {
			throw new WmqBusinessLogicException("El modelo no existe");
		}

		// Consulta BPCS
		Optional<Vin> vin = vinRepository.findByVin(infoMobileUnit.getVin());

		if (!vin.isPresent()) {
			throw new WmqBusinessLogicException("El vin no existe");
		}
		CreateService createService = new CreateService();
		createService.setServiceNumber(infoReportDto.getServiceNumber());
		createService.setDealer(dealer.get());
		createService.setKm(infoMobileUnit.getKm());
		createService.setModel(model.get());
		createService.setOwner(infoMobileUnit.getOwner());
		createService.setSoldBy(infoMobileUnit.getSoldBy());
		createService.setCreateReportBy(infoReportDto.getCreateReportBy());
		createService.setVin(vin.get());
		createService.setServiceClient(serviceClient);
		createService.setStatus(infoReportDto.getStatus());
		createService.setCreateTimestamp(new Timestamp(new Date().getTime()));
		createService.setTechnical(serviceDetail.getTechnical());
		Timestamp serviceDate = dateUtils.stringToTimeStamp(serviceDetail.getDateService());
		createService.setDateService(serviceDate);
		createService.setServicePerformed(serviceDetail.getServicePerformed());
		createService.setServiceDetail(serviceDetail.getServiceDetail());
		createServiceRepository.save(createService);
	}

	@Override
	public void deleteService(Long id) {
		Optional<CreateService> create = createServiceRepository.findById(id);
		if (create.isPresent()) {
			this.createServiceRepository.delete(create.get());
		}
	}

	@Override
	public void updateService(InfoCreateServiceDto infoCreateService, long id) {
		CreateService createService = createServiceRepository.findById(id);
		if (!Objects.isNull(createService)) {
			
			Optional<Dealer> dealer = dealerRepository.findByDealerNumber(infoCreateService.getInfoReportDto().getNumberDealer());

			if (!dealer.isPresent()) {
				throw new WmqBusinessLogicException("El dealer no existe");
			}

			Optional<Model> model = modelRepository.findById(infoCreateService.getInfoMobileUnitDto().getModel());

			if (!model.isPresent()) {
				throw new WmqBusinessLogicException("El modelo no existe");
			}

			Optional<Vin> vin = vinRepository.findByVin(infoCreateService.getInfoMobileUnitDto().getVin());
			
			Optional<ServiceClient> client = serviceClientRepository.findById(createService.getServiceClient().getId());
			
			Optional <Location> location = locationRepository.findByIdAndStateId(infoCreateService.getInfoClientDto().getTownship(), infoCreateService.getInfoClientDto().getState());
			
			if (!Objects.isNull(client)) { 
				client.get().setAddress(infoCreateService.getInfoClientDto().getAddress());
				client.get().setEmail(infoCreateService.getInfoClientDto().getEmail());
				client.get().setLocation(location.get());
				client.get().setName(infoCreateService.getInfoClientDto().getClientName());
				client.get().setPhone(infoCreateService.getInfoClientDto().getPhone());
				client.get().setUpdateTimestamp(new Timestamp(new Date().getTime()));
			}
			
			if (!vin.isPresent()) {
				throw new WmqBusinessLogicException("El vin no existe");
			}
			
			try {
				
				createService.setServiceNumber(infoCreateService.getInfoReportDto().getServiceNumber());
				createService.setDealer(dealer.get());
				createService.setKm(infoCreateService.getInfoMobileUnitDto().getKm());
				createService.setModel(model.get());
				createService.setOwner(infoCreateService.getInfoMobileUnitDto().getOwner());
				createService.setSoldBy(infoCreateService.getInfoMobileUnitDto().getSoldBy());
				createService.setCreateReportBy(infoCreateService.getInfoReportDto().getCreateReportBy());
				createService.setVin(vin.get());
				createService.setServiceClient(client.get());
				createService.setStatus(infoCreateService.getInfoReportDto().getStatus());
				//createService.setCreateTimestamp();
				createService.setTechnical(infoCreateService.getInfoServiceDetailDto().getTechnical());
				Timestamp serviceDate = dateUtils.stringToTimeStamp(infoCreateService.getInfoServiceDetailDto().getDateService());
				createService.setDateService(serviceDate);
				createService.setServicePerformed(infoCreateService.getInfoServiceDetailDto().getServicePerformed());
				createService.setServiceDetail(infoCreateService.getInfoServiceDetailDto().getServiceDetail());
				createServiceRepository.save(createService);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		//return createDto;
	}

	@Override
	public List<HistoryServiceDto> getHistoryService(final String vin) {
		List<HistoryServiceDto> historyServiceListDto = new ArrayList<>();
		Optional<Vin> tempVin = vinRepository.findByVin(vin);
		Optional<List<CreateService>> createServiceList = this.createServiceRepository.findByVin(tempVin.get());
		if (createServiceList.isPresent()) {
			createServiceList.get().stream().forEach(data -> {
				HistoryServiceDto historyServiceDto = new HistoryServiceDto();
				historyServiceDto.setDealerNo(data.getDealer().getDealerNumber());
				historyServiceDto
					.setDateService((data.getDateService() != null) ? dateUtils.convertTimestampToString(data.getDateService(), "dd/MM/yyyy") : "Este registro no contiene fecha");
				historyServiceDto.setDescription(data.getServiceDetail());
				historyServiceDto.setServiceNo(data.getServiceNumber());
				historyServiceListDto.add(historyServiceDto);
			});
		}
		return historyServiceListDto;
	}
}
