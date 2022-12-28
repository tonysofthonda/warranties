package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.ClientSale;
import com.honda.hdm.warrantiesmg.app.domain.repository.ClientSaleRepository;
import com.honda.hdm.warrantiesmg.app.service.ClientSaleService;
import com.honda.hdm.warrantiesmg.app.web.model.ClientSaleDto;

@Service
public class ClientSaleServiceImp implements ClientSaleService{

	@Autowired
	private ClientSaleRepository clientSaleRepository;
	
	@Override
	public List<ClientSaleDto> getAll() {
		List<ClientSaleDto> clientDto = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<ClientSale> clientEntity = this.clientSaleRepository.findAll();
		for(ClientSale client: clientEntity) {
			clientDto.add(modelMapper.map(client, ClientSaleDto.class));
		}
		return clientDto;
	}

	@Override
	public ClientSaleDto getById(Long id) {
		ClientSaleDto clientSaleDto = new ClientSaleDto();
		Optional<ClientSale> clientFound = this.clientSaleRepository.findById(id);
		if(clientFound.isPresent()) {
			BeanUtils.copyProperties(clientFound, clientSaleDto);
			return clientSaleDto;
		}
		throw new RuntimeException("El cliente no existe");
	}
	
	

}
