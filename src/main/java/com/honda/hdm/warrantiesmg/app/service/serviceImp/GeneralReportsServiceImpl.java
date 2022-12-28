package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.service.GeneralReportsService;
import com.honda.hdm.warrantiesmg.app.web.enums.ModelColumnEnum;
import com.honda.hdm.warrantiesmg.app.web.model.CampaignDto;
import com.honda.hdm.warrantiesmg.app.web.model.HistoricalAmountsDto;
import com.honda.hdm.warrantiesmg.app.web.model.TechnicalSummaryDto;
import com.honda.hdm.warrantiesmg.app.web.model.TechnicalSummaryListDto;
import com.honda.hdm.warrantiesmg.configuration.WmqBusinessLogicException;
import com.honda.hdm.warrantiesmg.util.DateUtils;
import com.honda.hdm.warrantiesmg.app.domain.entity.ApprovedAmount;
import com.honda.hdm.warrantiesmg.app.domain.entity.Campaign;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsReplaced;
import com.honda.hdm.warrantiesmg.app.domain.repository.ApprovedAmountRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.CampaignRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsReplacedRepository;

@Service
public class GeneralReportsServiceImpl implements GeneralReportsService {
	
	@Autowired
	private DateUtils dateUtils;
	
	@Autowired
	private PartsReplacedRepository partsReplacedRepository;
	
	@Autowired
	private ApprovedAmountRepository approvedAmountRepository;
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Override
	public TechnicalSummaryListDto technicalSummaryReport(final String dateIni, final String dateEnd) {
		List<PartsReplaced> warrantyClaims = partsReplacedRepository.findAll();
		TechnicalSummaryListDto technicalSummaryListDto = new TechnicalSummaryListDto();
		technicalSummaryListDto.setAccepted(generateReportDto(warrantyClaims));
		technicalSummaryListDto.setGe(generateReportDto(warrantyClaims));
		technicalSummaryListDto.setGw(generateReportDto(warrantyClaims));
		technicalSummaryListDto.setCampaign(generateReportDto(warrantyClaims));
		technicalSummaryListDto.setPending(generateReportDto(warrantyClaims));
		technicalSummaryListDto.setRejected(generateReportDto(warrantyClaims));
		return technicalSummaryListDto;
	}

	@Override
	public List<CampaignDto> unitsCampignReport() {
		List<Campaign> campaignList = this.campaignRepository.findAll();
		List<CampaignDto> campaignDtoList = new ArrayList<>();
		if(!campaignList.isEmpty()) {
			campaignList.forEach(data -> {
				CampaignDto campaignDto = new CampaignDto();
				BeanUtils.copyProperties(data, campaignDto);
				campaignDto.setRepairDate(dateUtils.timestampToString(data.getRepairDate(), "dd/MMM/yyyy"));
				campaignDto.setSaleDate(dateUtils.timestampToString(data.getSaleDate(), "dd/MMM/yyyy"));
				campaignDtoList.add(campaignDto);
			});
		}
		
		return campaignDtoList;
	}

	@Override
	public List<HistoricalAmountsDto> historicalAmountsReport(final String dateIni, final String dateEnd) {
		List<HistoricalAmountsDto> historicalAmounts = new ArrayList<>();
		List<ApprovedAmount> approvedAmount = this.approvedAmountRepository.findAll();
		approvedAmount.forEach(data -> {
			HistoricalAmountsDto historicalDto = new HistoricalAmountsDto();
			historicalDto.setClaimNumber(data.getClaim());
			historicalDto.setDealerNumber(data.getDealer().getDealerNumber());
			historicalDto.setDescription(data.getDescription());
			historicalDto.setFrt(data.getPartsReplaced().getParts().getFrt());
			historicalDto.setCostPieceUnit(data.getPartsReplaced().getUnitCost());
			historicalDto.setModel(data.getModel().getCodModel());
			historicalDto.setTotal(data.getTotalAproved());
			historicalDto.setPartNumber(data.getPartsReplaced().getParts().getPartNumber());
			historicalDto.setVin(data.getPartsReplaced().getWarrantyClaims().getVin().getVin());
			historicalDto.setStatus(data.getStatus() ? "Aprobado" : "No Aprobado");
			historicalDto.setDateOrder(dateUtils.timestampToString(data.getDateCreation(), "yyyy/MM/dd"));
			historicalAmounts.add(historicalDto);
		});
		return historicalAmounts;
	}
	
	private List<TechnicalSummaryDto> generateReportDto(List<PartsReplaced> partsReplacedList) {
		List<TechnicalSummaryDto> technicalSummaryList = new ArrayList<>();
		
		
		partsReplacedList.forEach(partsReplaced -> {
			TechnicalSummaryDto technicalDto = new TechnicalSummaryDto();
			technicalDto.setAmountPesos(partsReplaced.getParts().getPrice());
			technicalDto.setCode(partsReplaced.getWarrantyClaims().getNumberClaims());
			technicalDto.setDeal(partsReplaced.getWarrantyClaims().getDealer().getDealerNumber());
			technicalDto.setDescriptionPart(partsReplaced.getWarrantyClaims().getTechnicalDiagnosis());
			technicalDto.setEngine(partsReplaced.getWarrantyClaims().getVin().getEngineNumber());
			technicalDto.setFail(dateUtils.format(partsReplaced.getWarrantyClaims().getFailDate(), "dd/MM/yyyy"));
			technicalDto.setFrt(partsReplaced.getParts().getFrt());
			technicalDto.setKm(partsReplaced.getWarrantyClaims().getKm());
			technicalDto.setLabor(partsReplaced.getWarrantyClaims().getLaborCost());
			technicalDto.setPartCausal(partsReplaced.getParts().getPartNumber());
			technicalDto.setProveed(partsReplaced.getWarrantyClaims().getVin().getModel().getPlant().getName());
			technicalDto.setRepair(dateUtils.format(partsReplaced.getWarrantyClaims().getRepairDate(), "dd/MM/yyyy"));
			technicalDto.setReporte(partsReplaced.getWarrantyClaims().getNumberClaims());
			technicalDto.setSale(dateUtils.format(partsReplaced.getWarrantyClaims().getVin().getSalesDate(), "dd/MM/yyyy"));
			technicalDto.setStatus(partsReplaced.getWarrantyClaims().getTypeClaims().getClaimsType());
			technicalDto.setVin(partsReplaced.getWarrantyClaims().getVin().getVin());
			technicalDto.setModel(partsReplaced.getWarrantyClaims().getVin().getModel().getCodModel());
			technicalDto.setReportedSymptom(partsReplaced.getWarrantyClaims().getPartsDefectsSymptom().getSymptom().getDescriptionEng());
			technicalDto.setDefectDescription(partsReplaced.getWarrantyClaims().getPartsDefectsSymptom().getDefect().getDescriptionEng());
			technicalSummaryList.add(technicalDto);
		});
		
		return technicalSummaryList;
	}
	
	private void processWorkbook(File file) {
		List<CampaignDto> campaignDtoList = new ArrayList<>();
		try {
			campaignDtoList = this.processWorkbook2(file);
			if(campaignDtoList.isEmpty()) {
				throw new WmqBusinessLogicException("No models where found on file");
			}
			mapExcelToEntity(campaignDtoList);
		} catch(Exception ex) {
			throw new WmqBusinessLogicException("Error processing the file: " + ex.getMessage());
		}
	}
	
	private void mapExcelToEntity(List<CampaignDto> campaignDtoList) {
		List<Campaign> campaignList = new ArrayList<>();
	
		for(CampaignDto campaignDto : campaignDtoList) {
			Campaign campaign = new Campaign();
			BeanUtils.copyProperties(campaignDto, campaign);
			campaign.setRepairDate(dateUtils.dateToTimeStamp("dd/MMM/yyyy",campaignDto.getRepairDate()));
			campaign.setSaleDate(dateUtils.dateToTimeStamp("dd/MMM/yyyy", campaignDto.getSaleDate()));
			campaignList.add(campaign);
		}
		
		if(!campaignList.isEmpty()) {
			campaignRepository.saveAll(campaignList);
		}	
	}
	
	private List<CampaignDto> processWorkbook2(File file) throws Exception {
		if(file == null) {
			throw new WmqBusinessLogicException("Archivo Invalido.");
		}	
		return readWorkbook(file);		
	}
	
	private List<CampaignDto> readWorkbook(File file) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = workbook.getSheet("CONCENTRADO MC");
			if(null == workbook.getSheet("CONCENTRADO MC")) {
				throw new WmqBusinessLogicException("La estructura es invalida.");
			}
			XSSFRow row = sheet.getRow(0);
			if(row == null) {
				throw new WmqBusinessLogicException("Archivo vacio.");
			}
			return readModels(sheet);
		} catch(Exception ex) {
			throw new WmqBusinessLogicException("Error en el archivo " + ex.getMessage());
		}
	}
	
	private List<CampaignDto> readModels(XSSFSheet sheet) throws ParseException {
		List<CampaignDto> models = new ArrayList<>();
		int index = 1; //We start in 1 because 0 is the header row
		XSSFRow row = sheet.getRow(index);
		
		while(row != null) {
			if(this.isRowEmpty(row)) {
				break;
			}
			CampaignDto model = new CampaignDto();
			model = readModel(row);
			row = sheet.getRow(++index);
			models.add(model);
		}
		return models;
	}
	
	public boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	private CampaignDto readModel(XSSFRow modelRow) throws ParseException {
		CampaignDto model = new CampaignDto();
		XSSFCell cell = modelRow.getCell(ModelColumnEnum.CODE.getColumn());
		model.setCode(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.NUMBER.getColumn());
		model.setBulletinNumber(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.TITLE.getColumn());
		model.setBulletinTitle(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.MODEL.getColumn());
		model.setModel(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.YEAR.getColumn());
		model.setYear(Long.valueOf((cell.getNumericCellValue() + "").substring(0, 4)));
		cell = modelRow.getCell(ModelColumnEnum.VIN.getColumn());
		model.setVin(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.CLIENT.getColumn());
		model.setClient(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.DATE_SALE.getColumn());
		model.setSaleDate(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.DATE_REPAIR.getColumn());
		model.setRepairDate(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.STATUS.getColumn());
		model.setStatus(cell.toString());
		cell = modelRow.getCell(ModelColumnEnum.NOTE.getColumn());
		model.setNote(cell.toString());
		return model;
	}

	@Override
	public void saveUnitsCampignReport(byte[] fileBytes) {
		if(fileBytes.length == 0) {
			throw new WmqBusinessLogicException("File is empty");
		}
		try {
			System.out.println("entro aqui");
			File workbook = File.createTempFile("workbook", ".xlsx");
	        try (FileOutputStream fos = new FileOutputStream(workbook)) {
	            fos.write(fileBytes);
	            fos.flush();
	        }
			processWorkbook(workbook);
		} catch(IOException ex) {
			throw new WmqBusinessLogicException("Error in the file: " + ex);
		}		
	}

	@Override
	public void deleteCampaignRecord(Long id) {
		Optional<Campaign> campaign = this.campaignRepository.findById(id);
		
		if(campaign.isPresent()) {
			this.campaignRepository.delete(campaign.get());
		}
		
	}

	@Override
	public void updateCampaignRecord(CampaignDto campaignDto) {
		Campaign campaign = this.campaignRepository.findById(campaignDto.getId()).get();
		if(campaign != null) {
			BeanUtils.copyProperties(campaignDto, campaign);
			campaign.setRepairDate(dateUtils.dateToTimeStamp("dd/MMM/yyyy",campaignDto.getRepairDate()));
			campaign.setSaleDate(dateUtils.dateToTimeStamp("dd/MMM/yyyy", campaignDto.getSaleDate()));
			this.campaignRepository.save(campaign);
		}
	}
}
