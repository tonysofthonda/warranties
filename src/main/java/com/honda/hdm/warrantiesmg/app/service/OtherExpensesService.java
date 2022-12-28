package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.OtherExpensesDto;

public interface OtherExpensesService {

	List<OtherExpensesDto> getAllOtherExpensesByWarranty(Long warrantyId);
	
	void otherExpensesSave(OtherExpensesDto otherExpensesDto) throws Exception;
	
	void deleteOtherExpenses(Long otherExpensesId) throws Exception;
	
}
