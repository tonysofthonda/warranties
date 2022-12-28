package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Group;
import com.honda.hdm.warrantiesmg.app.domain.entity.Location;
import com.honda.hdm.warrantiesmg.app.domain.entity.State;
import com.honda.hdm.warrantiesmg.app.domain.entity.Zone;
import com.honda.hdm.warrantiesmg.app.domain.repository.GroupRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.LocationRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.StateRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ZoneRepository;
import com.honda.hdm.warrantiesmg.app.service.SitesService;
import com.honda.hdm.warrantiesmg.app.web.enums.SitesEnum;
import com.honda.hdm.warrantiesmg.app.web.model.SitesDto;

@Service
public class SitesServiceImpl implements SitesService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ZoneRepository zoneRepository;

	@Override
	public List<SitesDto> getAllState() {
		List<SitesDto> sitesDtoList = new ArrayList<>();
		List<State> stateList = this.stateRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		if (stateList != null) {
			stateList.stream().forEach(data -> {
				SitesDto sitesDto = new SitesDto();
				sitesDto.setName(data.getName());
				sitesDto.setNumber(data.getId());
				sitesDto.setStatus(data.getStatus());
				sitesDtoList.add(sitesDto);
			});
		}
		return sitesDtoList;
	}

	@Override
	public List<SitesDto> getAllTownship() {
		List<SitesDto> sitesDtoList = new ArrayList<>();
		List<Location> location = this.locationRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

		if (location != null) {
			location.stream().forEach(data -> {
				SitesDto sitesDto = new SitesDto();
				sitesDto.setName(data.getName());
				sitesDto.setNumber(data.getId());
				sitesDto.setStatus(data.getStatus());
				sitesDto.setIdState(data.getState().getId());
				sitesDtoList.add(sitesDto);
			});
		}

		return sitesDtoList;
	}

	@Override
	public List<SitesDto> getAllArea() {
		List<SitesDto> sitesDtoList = new ArrayList<>();
		List<Zone> zoneList = this.zoneRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		if (zoneList != null) {
			zoneList.stream().forEach(data -> {
				SitesDto sitesDto = new SitesDto();
				sitesDto.setName(data.getName());
				sitesDto.setNumber(data.getId());
				sitesDto.setStatus(data.getStatus());
				sitesDtoList.add(sitesDto);
			});
		}
		return sitesDtoList;
	}

	@Override
	public List<SitesDto> getAllGroup() {
		List<SitesDto> sitesDtoList = new ArrayList<>();
		List<Group> groupList = this.groupRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		if (groupList != null) {
			groupList.stream().forEach(data -> {
				SitesDto sitesDto = new SitesDto();
				sitesDto.setName(data.getName());
				sitesDto.setNumber(data.getId());
				sitesDto.setStatus(data.getStatus());
				sitesDtoList.add(sitesDto);
			});
		}
		return sitesDtoList;
	}

	@Override
	public void saveSites(SitesDto sitesDto) throws Exception {
		System.out.println(sitesDto.getType());
		if (SitesEnum.AREA.getName().equals(sitesDto.getType())) {
			System.out.println("AREA");
			if(sitesDto.getNumber() != null) {
				Optional<Zone> zone = zoneRepository.findById(sitesDto.getNumber());
				zone.get().setName(sitesDto.getName());
				zone.get().setStatus(sitesDto.getStatus());
				zone.get().setDateUpdate(new Timestamp(new Date().getTime()));
				zoneRepository.save(zone.get());
			} else {
				Zone zone = new Zone();
				zone.setName(sitesDto.getName());
				zone.setStatus(sitesDto.getStatus());
				zone.setDateCreation(new Timestamp(new Date().getTime()));
				zoneRepository.save(zone);
			}
		} else if (SitesEnum.TOWNSHIP.getName().equals(sitesDto.getType())) {
			System.out.println("TOWNSHIP");
			if(sitesDto.getNumber() != null) {
				Optional<Location> location = this.locationRepository.findById(sitesDto.getNumber());
				Optional<State> state = this.stateRepository.findById(sitesDto.getIdState());
					location.get().setName(sitesDto.getName());
					location.get().setState(state.get());
					location.get().setStatus(sitesDto.getStatus());
					location.get().setDateUpdate(new Timestamp(new Date().getTime()));
					this.locationRepository.save(location.get());
			} else {
				Optional<State> state = this.stateRepository.findById(sitesDto.getIdState());
				Location location = new Location();
				location.setName(sitesDto.getName());
				location.setState(state.get());
				location.setStatus(sitesDto.getStatus());
				location.setDateCreation(new Timestamp(new Date().getTime()));
				this.locationRepository.save(location);
			}
		} else if (SitesEnum.STATE.getName().equals(sitesDto.getType())) {
			System.out.println("STATE");
			if(sitesDto.getNumber() != null) {
				Optional<State> state = this.stateRepository.findById(sitesDto.getNumber());
				state.get().setDateUpdate(new Timestamp(new Date().getTime()));
				state.get().setName(sitesDto.getName());
				state.get().setStatus(sitesDto.getStatus());
				this.stateRepository.save(state.get());
			} else {
				State state = new State();
				state.setName(sitesDto.getName());
				state.setStatus(sitesDto.getStatus());
				state.setDateCreation(new Timestamp(new Date().getTime()));
				this.stateRepository.save(state);
			}
		} else if (SitesEnum.GROUP.getName().equals(sitesDto.getType())) {
			System.out.println("GROUP");
			System.out.println(sitesDto.getStatus());
			System.out.println(sitesDto.getNumber());
			if(sitesDto.getNumber() != null) { 
				Optional<Group> group = this.groupRepository.findById(sitesDto.getNumber());
				group.get().setName(sitesDto.getName());
				group.get().setDescription(sitesDto.getName());
				group.get().setStatus(sitesDto.getStatus());
				group.get().setDateUpdate(new Timestamp(new Date().getTime()));
				this.groupRepository.save(group.get());
			} else {
				Group group = new Group();
				group.setName(sitesDto.getName());
				group.setDescription(sitesDto.getName());
				group.setStatus(sitesDto.getStatus());
				group.setDateCreation(new Timestamp(new Date().getTime()));
				this.groupRepository.save(group);
			}
		}

	}

	@Override
	public void updateSite(SitesDto sitesDto) throws Exception {
		if (sitesDto.getNumber() == null) {
			throw new Exception("El id no esta presente.");
		}
		this.saveSites(sitesDto);

	}

}
