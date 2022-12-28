package com.honda.hdm.warrantiesmg.app.service;

import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.honda.hdm.warrantiesmg.app.web.model.ApprovedAmountDto;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedDto;

public interface IApprovedAmountsService {

	ApprovedDto getApprovedAmounts(Long idDealer, String dateIni, String dateEnd) throws ParseException;
	
	void changeDate(final Long id);
	
	void deleteRecord(final Long id, String comments);

	void sendApprovedAmounts(Long idDealer, String dateIni, String dateEnd, Double total)
			throws MailException, IllegalArgumentException, MessagingException, ParseException;

	List<ApprovedAmountDto> getAllHistory();
	
	void insertIntoAmount(final List<Long> dataId);

}
