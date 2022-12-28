package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.Group;
import com.honda.hdm.warrantiesmg.app.domain.entity.Location;
import com.honda.hdm.warrantiesmg.app.domain.entity.Zone;
import com.honda.hdm.warrantiesmg.app.domain.repository.DealerRepository;
import com.honda.hdm.warrantiesmg.app.service.DealerService;
import com.honda.hdm.warrantiesmg.app.service.GroupService;
import com.honda.hdm.warrantiesmg.app.service.LocationService;
import com.honda.hdm.warrantiesmg.app.service.ZoneService;
import com.honda.hdm.warrantiesmg.app.web.model.DealersDto;
import com.honda.hdm.warrantiesmg.app.web.model.GroupDto;
import com.honda.hdm.warrantiesmg.app.web.model.LocationDto;
import com.honda.hdm.warrantiesmg.app.web.model.StateDto;
import com.honda.hdm.warrantiesmg.app.web.model.ZoneDto;

@Service
public class DealerServiceImp implements DealerService{

	@Autowired
	private DealerRepository dealerRepository;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	private LocationService locationService;
	
	@Override
	public List<DealersDto> getAllDealers(){		
		List<Dealer> dealer = this.dealerRepository.findAllOrderById();
		List<DealersDto> returnValues = new ArrayList<>();		
		for(Dealer dealersEntity: dealer) {
			DealersDto dealersDto = new DealersDto();
			dealersDto.setId(dealersEntity.getId());
			dealersDto.setDealerNumber(dealersEntity.getDealerNumber());
			dealersDto.setName(dealersEntity.getName());
			dealersDto.setWorkTime(dealersEntity.getOpeningHours());
			dealersDto.setType(dealersEntity.getType());
			dealersDto.setSaturday(dealersEntity.getOpeningHoursSat());
			dealersDto.setEmail(dealersEntity.getGeneralManagerEmail());
			dealersDto.setPhone(dealersEntity.getGeneralManagerTel());
			dealersDto.setAddrStreet(dealersEntity.getAddrStreet());
			dealersDto.setAddrNeigborthoot(dealersEntity.getNeighborhood());
			dealersDto.setGerenteGral(dealersEntity.getGeneralManager());
			dealersDto.setGerenteServ(dealersEntity.getServiceManager());
			dealersDto.setStatus(dealersEntity.getStatus());
			
			GroupDto group = new GroupDto();
			group.setId(dealersEntity.getGroup().getId());
			group.setName(dealersEntity.getGroup().getName());
			group.setDescription(dealersEntity.getGroup().getDescription());
			dealersDto.setGroup(group);
			StateDto state = new StateDto();
			state.setId(dealersEntity.getLocation().getId());
			state.setName(dealersEntity.getLocation().getName());			
			dealersDto.setState(state);
			
			ZoneDto zone = new ZoneDto();
			zone.setId(dealersEntity.getZone().getId());
			zone.setStatus(dealersEntity.getZone().getStatus());
			zone.setZoneName(dealersEntity.getZone().getName());
			dealersDto.setZone(zone);
			
			LocationDto location = new LocationDto();
			location.setId(dealersEntity.getLocation().getId());
			location.setName(dealersEntity.getLocation().getName());
			location.setState(dealersEntity.getLocation().getState());
			dealersDto.setLocation(location);
			dealersDto.setUserType(dealersEntity.getUserType());
			returnValues.add(dealersDto);
		}
		return returnValues;
	}
	
	public DealersDto getById(Long id) {
		DealersDto dealersDto = new DealersDto();
		Optional<Dealer> dealerEntity = dealerRepository.findById(id);
		if(dealerEntity.isPresent()) {
			BeanUtils.copyProperties(dealerEntity, dealersDto);
			return dealersDto;
		}
		throw new RuntimeException("El Dealer no existe");
	}

	
	@Override
	public DealersDto getByDealerNumber(String dealerNumber) {
		DealersDto dealersDto = new DealersDto();
		Optional<Dealer> dealer = dealerRepository.findByDealerNumber(dealerNumber);
		if(dealer.isPresent()) {
//			BeanUtils.copyProperties(dealer, dealersDto);
			dealersDto.setAddrNeigborthoot(dealer.get().getNeighborhood());
			dealersDto.setAddrStreet(dealer.get().getAddrStreet());
			dealersDto.setDealerNumber(dealer.get().getDealerNumber());
			dealersDto.setEmail(dealer.get().getGeneralManagerEmail());
			dealersDto.setGerenteGral(dealer.get().getGeneralManager());
			dealersDto.setGerenteServ(dealer.get().getServiceManager());
			
			dealersDto.setId(dealer.get().getId());
			dealersDto.setName(dealer.get().getName());
			dealersDto.setPhone(dealer.get().getGeneralManagerTel());
			dealersDto.setSaturday(dealer.get().getOpeningHoursSat());
			dealersDto.setStatus(dealer.get().getStatus());
			dealersDto.setType(dealer.get().getType());
			dealersDto.setUserType(dealer.get().getUserType());
			dealersDto.setWorkTime(dealer.get().getOpeningHours());
			
			GroupDto group = new GroupDto();
			group.setId(dealer.get().getGroup().getId());
			group.setName(dealer.get().getGroup().getName());
			group.setDescription(dealer.get().getGroup().getDescription());
			dealersDto.setGroup(group);
			StateDto state = new StateDto();
			state.setId(dealer.get().getLocation().getId());
			state.setName(dealer.get().getLocation().getName());			
			dealersDto.setState(state);
			
			ZoneDto zone = new ZoneDto();
			zone.setId(dealer.get().getZone().getId());
			zone.setStatus(dealer.get().getZone().getStatus());
			zone.setZoneName(dealer.get().getZone().getName());
			dealersDto.setZone(zone);
			
			LocationDto location = new LocationDto();
			location.setId(dealer.get().getLocation().getId());
			location.setName(dealer.get().getLocation().getName());
			location.setState(dealer.get().getLocation().getState());
			dealersDto.setLocation(location);
			dealersDto.setUserType(dealer.get().getUserType());
			return dealersDto;
		}
		return null;
	}
	
	@Override
	public void saveDealer(DealersDto dealerDto) {
		
		Group groupEntity = new Group();
		GroupDto groupDto = this.groupService.getById(dealerDto.getGroup().getId());
		if(groupDto != null) {
			BeanUtils.copyProperties(groupDto, groupEntity);			
		}else {
			throw new RuntimeException("El grupo no existe");
		}
		
		Zone zone = new Zone();
		ZoneDto zoneDto = this.zoneService.getById(dealerDto.getZone().getId());
		if(zoneDto != null) {
			BeanUtils.copyProperties(zoneDto, zone);
		} else {
			throw new RuntimeException("La zona no existe");
		}
		
		Location location = new Location();
		LocationDto locationDto = locationService.getByIdSelect(dealerDto.getLocation().getId());
		if(locationDto != null) {
			
			BeanUtils.copyProperties(locationDto, location);
		}else {
			throw new RuntimeException("El Municipio no existe");
		}
		
		Dealer dealer = new Dealer();
		if(dealerDto.getId() == null) {
			dealer.setDealerNumber(dealerDto.getDealerNumber().toString());
			dealer.setName(dealerDto.getName());
			dealer.setAddrStreet(dealerDto.getAddrStreet());
			dealer.setNeighborhood(dealerDto.getAddrNeigborthoot());
			dealer.setDealerOwner("");
			dealer.setZipCode("");
			dealer.setOpeningHours(dealerDto.getWorkTime());
			dealer.setOpeningHoursSat(dealerDto.getSaturday());
			
			dealer.setGeneralManager(dealerDto.getGerenteGral());
			dealer.setGeneralManagerEmail(dealerDto.getEmail());
			dealer.setGeneralManagerTel(dealerDto.getPhone());
			
			dealer.setServiceManager(dealerDto.getGerenteServ());
			dealer.setServicesManagerEmail("");
			dealer.setServicesManagerTel("");
			
			dealer.setSparePartsContact("");
			dealer.setSparePartsEmail("");
			dealer.setSparePartsTel("");
			
			dealer.setType(dealerDto.getType());
			dealer.setStatus(true);
			
			dealer.setGroup(groupEntity);
			dealer.setZone(zone);
			dealer.setLocation(location);
			dealer.setUserType(dealerDto.getUserType());
			dealerRepository.save(dealer);
		} else {
			if (this.dealerRepository.findById(dealerDto.getId()).isPresent()) {
				System.out.println(dealerDto.getGerenteGral());
				Dealer dealerEnt = this.dealerRepository.findById(dealerDto.getId()).get();
				BeanUtils.copyProperties(dealerEnt, dealer);
				dealerEnt.setGroup(groupEntity);
				dealerEnt.setZone(zone);
				dealerEnt.setLocation(location);
				dealerEnt.setStatus(dealerDto.getStatus());
				dealerEnt.setUserType(dealerDto.getUserType());
				dealerEnt.setDealerNumber(dealerDto.getDealerNumber().toString());
				dealerEnt.setName(dealerDto.getName());
				dealerEnt.setAddrStreet(dealerDto.getAddrStreet());
				dealerEnt.setNeighborhood(dealerDto.getAddrNeigborthoot());
				dealerEnt.setOpeningHours(dealerDto.getWorkTime());
				dealerEnt.setOpeningHoursSat(dealerDto.getSaturday());
				dealerEnt.setGeneralManager(dealerDto.getGerenteGral());
				dealerEnt.setGeneralManagerEmail(dealerDto.getEmail());
				dealerEnt.setGeneralManagerTel(dealerDto.getPhone());
				dealerEnt.setType(dealerDto.getType());
				dealerEnt.setStatus(dealerDto.getStatus());
				dealerEnt.setServiceManager(dealerDto.getGerenteServ());
				dealerRepository.save(dealerEnt);
			}else {
				throw new RuntimeException("El Dealer no existe");
			}			
		}
		
	}


	@Override
	public void updateStatus(DealersDto dealersDto) {
		Dealer dealer = this.dealerRepository.findById(dealersDto.getId()).get();
		if(dealer != null) {
			dealer.setStatus(dealersDto.getStatus());
			this.dealerRepository.save(dealer);
		}
	}
	
}
