package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.Evidences;
import com.honda.hdm.warrantiesmg.app.domain.entity.FirstOwner;
import com.honda.hdm.warrantiesmg.app.domain.entity.GradeType;
import com.honda.hdm.warrantiesmg.app.domain.entity.Location;
import com.honda.hdm.warrantiesmg.app.domain.entity.Model;
import com.honda.hdm.warrantiesmg.app.domain.entity.OtherExpenses;
import com.honda.hdm.warrantiesmg.app.domain.entity.Parts;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsDefectsSymptom;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsReplaced;
import com.honda.hdm.warrantiesmg.app.domain.entity.ProblemCategory;
import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceModel;
import com.honda.hdm.warrantiesmg.app.domain.entity.Setting;
import com.honda.hdm.warrantiesmg.app.domain.entity.State;
import com.honda.hdm.warrantiesmg.app.domain.entity.TypeClaims;
import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClient;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyConsecutive;
import com.honda.hdm.warrantiesmg.app.domain.repository.DealerRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.EvidenceRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.FirstOwnerRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.GradeTypeRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.LocationRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ModelRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.OtherExpensesRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsDefectsSymptomRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsReplacedRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ProblemCategoryRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ServiceModelRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.SettingRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.StateRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.TypeClaimsRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.VinRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.WarrantyClaimsRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.WarrantyClientRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.WarrantyConsecutiveRepository;
import com.honda.hdm.warrantiesmg.app.service.WarrantyClaimService;
import com.honda.hdm.warrantiesmg.app.web.enums.StatusEnum;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedAmountDto;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedDto;
import com.honda.hdm.warrantiesmg.app.web.model.EvidenceDto;
import com.honda.hdm.warrantiesmg.app.web.model.OtherJobInformationDto;
import com.honda.hdm.warrantiesmg.app.web.model.RelationPartDto;
import com.honda.hdm.warrantiesmg.app.web.model.TotalsApprovedAmountDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyConsecutiveDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyConsultDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyDetailDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyInformationDto;
import com.honda.hdm.warrantiesmg.configuration.WmqBusinessLogicException;
import com.honda.hdm.warrantiesmg.util.DateUtils;

@Service
public class WarrantyClaimServiceImpl implements WarrantyClaimService {

	@Autowired
	private WarrantyClaimsRepository warrantyClaimsRepository;

	@Autowired
	private WarrantyClientRepository warrantyClientRepository;

	@Autowired
	private WarrantyConsecutiveRepository warrantyConsecutiveRepository;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private FirstOwnerRepository firstOwnerRepository;

	@Autowired
	private GradeTypeRepository gradeTypeRepository;

	@Autowired
	private VinRepository vinRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;

	@Autowired
	private PartsDefectsSymptomRepository partsDefectsSymptomRepository;

	@Autowired
	private TypeClaimsRepository typeClaimsRepository;

	@Autowired
	private ServiceModelRepository serviceModelRepository;

	@Autowired
	private OtherExpensesRepository otherExpensesRepository;

	@Autowired
	private PartsReplacedRepository partsReplacedRepository;

	@Autowired
	private EvidenceRepository evidenceRepository;
	
	@Autowired
	private ModelRepository modelRepository;
	
	@Autowired
	private SettingRepository settingsRepository;

	@Autowired
	private DateUtils dateUtils;

	public void saveWarrantyClaimInEnviado(WarrantyInformationDto warrantyInformation) {
		WarrantyClaims warrantyClaim = this.warrantyClaimsRepository.findByNumberClaims(
				warrantyInformation.getPersonalInformation().getReportInformation().getClaimNumber());
		warrantyClaim.setStatus(StatusEnum.ENVIADO.getStatus());
		System.out.println(warrantyClaim.getStatus());
		try {
			this.warrantyClaimsRepository.save(warrantyClaim);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void saveWarrantyClaimOnHold(Long idWarranty) { 
		Optional<WarrantyClaims> warrantyClaim = this.warrantyClaimsRepository.findById(idWarranty);
		warrantyClaim.get().setStatus(StatusEnum.ONHOLD.getStatus());
		System.out.println(warrantyClaim.get().getStatus());
		try {
			this.warrantyClaimsRepository.save(warrantyClaim.get());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void saveWarrantyClaimReactive(Long idWarranty) { 
		Optional<WarrantyClaims> warrantyClaim = this.warrantyClaimsRepository.findById(idWarranty);
		warrantyClaim.get().setStatus(StatusEnum.ENVIADO.getStatus());
		System.out.println(warrantyClaim.get().getStatus());
		try {
			this.warrantyClaimsRepository.save(warrantyClaim.get());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public WarrantyConsecutiveDto getWarrantyClaimConsecutive(Integer dealerNumber) {
		WarrantyConsecutive warrantyConsecutive = this.warrantyConsecutiveRepository
				.findByDealerNumber(dealerNumber.toString());
		WarrantyConsecutiveDto warrantyConsecutiveDto = new WarrantyConsecutiveDto();

		if (warrantyConsecutive != null) {
			System.out.println("Diferente de null");
			warrantyConsecutiveDto.setId(warrantyConsecutive.getId());
			warrantyConsecutiveDto.setDealer(warrantyConsecutive.getDealer().getName());
			Integer consecutive = 1;
			String period = warrantyConsecutive.getCurrentPeriod().toString();
			if (period.equals(getCurrentDate())) {
				consecutive = warrantyConsecutive.getConsecutive() + 1;
			} else {
				period = getCurrentDate();
				consecutive = 1;
			}
			warrantyConsecutiveDto.setWarrantyConsecutive(createConsecutive(
					dealerNumber(warrantyConsecutive.getDealer().getDealerNumber()), period, consecutive));

			return warrantyConsecutiveDto;
		} else {
			System.out.println("null");
			Optional<Dealer> dealer = dealerRepository.findByDealerNumber(dealerNumber.toString());
			warrantyConsecutiveDto.setWarrantyConsecutive(
					createConsecutive(dealerNumber(dealerNumber.toString()), getCurrentDate(), 1));
			warrantyConsecutiveDto.setDealer(dealer.get().getName());
		}
		return warrantyConsecutiveDto;
	}

	private String createConsecutive(String dealerNum, String currentDate, Integer consecutive) {
		return dealerNum + "-" + currentDate + "-" + String.format("%03d", consecutive);
	}

	private String dealerNumber(String dealerNum) {
		dealerNum = dealerNum.substring(dealerNum.length() - 4, dealerNum.length());
		return dealerNum;
	}

	private String getCurrentDate() {
		return dateUtils.generateTimesTamWithFormat("YYMM");
	}

	@Override
	public Boolean existWarrantyClaim(Long warrantyClaimId) {
		Optional<WarrantyClaims> warrantyClaims = this.warrantyClaimsRepository.findById(warrantyClaimId);
		return warrantyClaims.isPresent() ? true : false;
	}

	@Override
	public List<WarrantyDto> getWarrantiesClaim() {
		List<WarrantyDto> warrantyListDto = new ArrayList<>();
		List<WarrantyClaims> warranty = warrantyClaimsRepository.findAll();
		if (!warranty.isEmpty()) {
			warranty.stream().forEach(data -> {
				WarrantyDto warrantyDto = new WarrantyDto();
				warrantyDto.setId(data.getId());
				warrantyDto.setRepairDate(this.dateUtils.convertTimestampToString(data.getRepairDate(), "dd/MM/yyyy"));
				warrantyDto.setServiceNo(data.getNoServOrder());
				warrantyDto.setStatus(data.getStatus());
				warrantyDto.setVin(data.getVin().getVin());
				warrantyDto.setClaimNo(data.getNumberClaims());
				warrantyListDto.add(warrantyDto);
			});
		}
		return warrantyListDto;
	}

	public WarrantyDetailDto getWarrantyClaimDetail(Long id) {
		Optional<WarrantyClaims> warranty = warrantyClaimsRepository.findById(id);
		WarrantyDetailDto warrantyDto = new WarrantyDetailDto();
		if (warranty.isPresent()) {

			warrantyDto.setServiceModel(warranty.get().getServiceModel());
			warrantyDto.setNumberClaims(warranty.get().getNumberClaims());
			warrantyDto.setId(warranty.get().getId());
			warrantyDto.setRepairDate(warranty.get().getRepairDate());
			warrantyDto.setNoServOrder(warranty.get().getNoServOrder());
			warrantyDto.setStatus(warranty.get().getStatus());
			warrantyDto.setVin(warranty.get().getVin());
			warrantyDto.setProblemCategory(warranty.get().getProblemCategory());
			warrantyDto.setCorrectiveAction(warranty.get().getCorrectiveAction());
			warrantyDto.setCreateDate(warranty.get().getCreateDate());
			warrantyDto.setKm(warranty.get().getKm());
			warrantyDto.setLaborCost(warranty.get().getLaborCost());
			warrantyDto.setPackagingCost(warranty.get().getPackagingCost());
			warrantyDto.setPartsCost(warranty.get().getPartsCost());
			warrantyDto.setRepairDate(warranty.get().getRepairDate());
			warrantyDto.setServiceEmployee(warranty.get().getServiceEmployee());
			warrantyDto.setSparePartsCost(warranty.get().getSparePartsCost());
			warrantyDto.setTechnicalDiagnosis(warranty.get().getTechnicalDiagnosis());
			warrantyDto.setTechnicalEmployee(warranty.get().getTechnicalEmployee());
			warrantyDto.setTotalAmount(warranty.get().getTotalAmount());
			warrantyDto.setTtlCost(warranty.get().getTtlCost());
			warrantyDto.setUpdateDate(warranty.get().getUpdateDate());
			warrantyDto.setWarrantyClient(warranty.get().getWarrantyClient());
			warrantyDto.setGradeType(warranty.get().getGradeType());
			warrantyDto.setDealer(warranty.get().getDealer());
			warrantyDto.setPartsDefectsSymptom(warranty.get().getPartsDefectsSymptom());
			warrantyDto.setFirstOwner(warranty.get().getFirstOwner());
			warrantyDto.setTypeClaims(warranty.get().getTypeClaims());
			warrantyDto.setObs(warranty.get().getObs());
			warrantyDto.setBstate(warranty.get().getBstate());
			warrantyDto.setDateCreation(warranty.get().getDateCreation());
			warrantyDto.setDateUpdate(warranty.get().getDateUpdate());
			warrantyDto.setSaleBy(warranty.get().getSoldBy());
			warrantyDto.setElaboratedBY(warranty.get().getServiceEmployee());

		}
		return warrantyDto;
	}

	@Override
	public List<WarrantyConsultDto> getWarrantiesConsult(String search, String dateIni, String dateEnd) {
		List<WarrantyConsultDto> listWarrantyConsult = new ArrayList<WarrantyConsultDto>();
		List<WarrantyClaims> warranty = warrantyClaimsRepository.findAllByFilters(search,
				this.dateUtils.dateToTimeStamp("yyyy/MM/dd", dateIni),
				this.dateUtils.dateToTimeStamp("yyyy/MM/dd", dateEnd));
		if (!warranty.isEmpty()) {
			warranty.stream().forEach(data -> {

				List<PartsReplaced> partsReplaced = this.partsReplacedRepository
						.findAllByWarrantyClaimsId(data.getId());
				if (!partsReplaced.isEmpty()) {
					partsReplaced.stream().forEach(part -> {
						WarrantyConsultDto warrantyDto = new WarrantyConsultDto();
						warrantyDto.setId(data.getId());
						warrantyDto.setDateCreate(this.dateUtils.timestampToString(data.getCreateDate(), "yyyy/MM/dd"));
						warrantyDto.setServiceNo(data.getNoServOrder());
						warrantyDto.setStatus(data.getStatus());
						warrantyDto.setVin(data.getVin().getVin());
						warrantyDto.setClaimNo(data.getNumberClaims());
						warrantyDto.setDealerNo(data.getDealer().getDealerNumber());
						warrantyDto.setClientName(data.getWarrantyClient().getClientName());
						warrantyDto.setModel(data.getVin().getModel().getCodModel());
						warrantyDto.setMotor(data.getVin().getEngineNumber());
						warrantyDto.setSale(this.dateUtils.format(data.getVin().getSalesDate(), "yyyy/MM/dd"));
						warrantyDto.setFail(this.dateUtils.format(data.getFailDate(), "yyyy/MM/dd"));
						warrantyDto.setRepair(this.dateUtils.format(data.getRepairDate(), "yyyy/MM/dd"));
						warrantyDto.setKm(String.valueOf(data.getKm()));
						warrantyDto.setCausalPart(part.getParts().getPartNumber());
						warrantyDto.setDescriptionPart(part.getParts().getDescriptionPart());
						warrantyDto.setReportSymptom(data.getPartsDefectsSymptom().getSymptom().getDescriptionSpa());
						warrantyDto.setDescriptionDefect(part.getDescription());
						warrantyDto.setCode(data.getNumberClaims());
						warrantyDto.setFrt(String.valueOf(part.getParts().getFrt()));
						warrantyDto.setAmountPesos(
								(data.getSparePartsCost() != null) ? String.valueOf(data.getTotalAmount()) : "0");
						warrantyDto.setUsd("USD");
						warrantyDto.setSpareparts(
								String.valueOf((data.getSparePartsCost() != null) ? data.getSparePartsCost() : "0"));
						warrantyDto.setUnship(String
								.valueOf((data.getSparePartsCost() != null) ? (data.getSparePartsCost() * .25) : "0"));
						warrantyDto.setLabor(String.valueOf((data.getLaborCost() != null) ? data.getLaborCost() : "0"));
						warrantyDto.setResponseDate("");
						warrantyDto.setClose("");
						warrantyDto.setSamples("");
						warrantyDto.setNote("");
						warrantyDto.setDealerName(data.getDealer().getName());
						warrantyDto.setReport(data.getNumberClaims());
						warrantyDto.setComments(data.getComments());
						listWarrantyConsult.add(warrantyDto);
					});
				} else {
					WarrantyConsultDto warrantyDto = new WarrantyConsultDto();
					warrantyDto.setId(data.getId());
					warrantyDto.setDateCreate(this.dateUtils.timestampToString(data.getCreateDate(), "yyyy/MM/dd"));
					warrantyDto.setServiceNo(data.getNoServOrder());
					warrantyDto.setStatus(data.getStatus());
					warrantyDto.setVin(data.getVin().getVin());
					warrantyDto.setClaimNo(data.getNumberClaims());
					warrantyDto.setDealerNo(data.getDealer().getDealerNumber());
					warrantyDto.setClientName(data.getWarrantyClient().getClientName());
					warrantyDto.setModel(data.getVin().getModel().getCodModel());
					warrantyDto.setMotor(data.getVin().getEngineNumber());
					warrantyDto.setSale((data.getVin().getSalesDate() != null) ? this.dateUtils.format(data.getVin().getSalesDate(), "yyyy/MM/dd") : null);
					warrantyDto.setFail((data.getFailDate() != null) ? this.dateUtils.format(data.getFailDate(), "yyyy/MM/dd") : null);
					warrantyDto.setRepair((data.getRepairDate() != null) ? this.dateUtils.format(data.getRepairDate(), "yyyy/MM/dd") : null);
					warrantyDto.setKm(String.valueOf(data.getKm()));
					warrantyDto.setCausalPart("");
					warrantyDto.setDescriptionPart("");
					warrantyDto.setReportSymptom((data.getPartsDefectsSymptom() != null) ? data.getPartsDefectsSymptom().getSymptom().getDescriptionSpa() : null);
					warrantyDto.setDescriptionDefect("");
					warrantyDto.setCode(data.getNumberClaims());
					warrantyDto.setFrt("");
					warrantyDto.setAmountPesos(
							(data.getSparePartsCost() != null) ? String.valueOf(data.getTotalAmount()) : "0");
					warrantyDto.setUsd("USD");
					warrantyDto.setSpareparts(
							String.valueOf((data.getSparePartsCost() != null) ? data.getSparePartsCost() : "0"));
					warrantyDto.setUnship(String
							.valueOf((data.getSparePartsCost() != null) ? (data.getSparePartsCost() * .25) : "0"));
					warrantyDto.setLabor(String.valueOf((data.getLaborCost() != null) ? data.getLaborCost() : "0"));
					warrantyDto.setResponseDate("");
					warrantyDto.setClose("");
					warrantyDto.setSamples("");
					warrantyDto.setNote("");
					warrantyDto.setDealerName(data.getDealer().getName());
					warrantyDto.setReport(data.getNumberClaims());
					warrantyDto.setComments(data.getComments());
					listWarrantyConsult.add(warrantyDto);
				}

			});
		}
		return listWarrantyConsult;
	}

	@Override
	public ApprovedDto getApprovedAmounts(Long idDealer, String dateIni, String dateEnd) throws ParseException {
		// TODO Auto-generated method stub
		List<ApprovedAmountDto> listApprovedAmounts = new ArrayList<>();
		if (idDealer == 0) {
			idDealer = null;
		}
		if (dateIni.equals("null")) {
			dateIni = null;
		}
		if (dateEnd.equals("null")) {
			dateEnd = null;
		}

		List<WarrantyClaims> warranties = this.warrantyClaimsRepository.findByFilters(idDealer,
				this.dateUtils.dateToTimeStamp("yyyy/MM/dd", dateIni),
				this.dateUtils.dateToTimeStamp("yyyy/MM/dd", dateEnd));

		if (warranties != null) {
			warranties.forEach(data -> {
				ApprovedAmountDto approvedAmountDto = new ApprovedAmountDto();
				approvedAmountDto.setId(data.getId());
				approvedAmountDto.setClaim(data.getNumberClaims());
				approvedAmountDto.setDealer(data.getDealer().getName());
				approvedAmountDto
						.setDescription(data.getPartsDefectsSymptom().getPartMotorcycle().getDescriptionSpanish());
				approvedAmountDto.setLabor((data.getLaborCost() != null) ? data.getLaborCost() : 0);
				approvedAmountDto.setModel(data.getVin().getModel().getModel());
				approvedAmountDto.setRepair((data.getSparePartsCost() != null) ? data.getSparePartsCost() : 0);
				approvedAmountDto.setUnship((data.getSparePartsCost() != null) ? data.getSparePartsCost() * 0.25 : 0);
				approvedAmountDto.setPart(null);
				approvedAmountDto.setStatus(data.getStatus());
				listApprovedAmounts.add(approvedAmountDto);
			});
		}

		ApprovedDto approvedDto = new ApprovedDto();
		approvedDto.setApproved(listApprovedAmounts);
		approvedDto.setTotals(this.listTotals(listApprovedAmounts));
		return approvedDto;
	}
	
	public ApprovedDto getTotalById (Long idWarranty) throws ParseException {
		List<ApprovedAmountDto> listApprovedAmounts = new ArrayList<>();
		Optional<WarrantyClaims> warranties = this.warrantyClaimsRepository.findById(idWarranty);
		if (warranties != null) {
			ApprovedAmountDto approvedAmountDto = new ApprovedAmountDto();
			approvedAmountDto.setId(warranties.get().getId());
			approvedAmountDto.setClaim(warranties.get().getNumberClaims());
			approvedAmountDto.setDealer(warranties.get().getDealer().getName());
			approvedAmountDto
					.setDescription(warranties.get().getPartsDefectsSymptom().getPartMotorcycle().getDescriptionSpanish());
			approvedAmountDto.setLabor((warranties.get().getLaborCost() != null) ? warranties.get().getLaborCost() : 0);
			approvedAmountDto.setModel(warranties.get().getVin().getModel().getModel());
			approvedAmountDto.setRepair((warranties.get().getSparePartsCost() != null) ? warranties.get().getSparePartsCost() : 0);
			approvedAmountDto.setUnship((warranties.get().getSparePartsCost() != null) ? warranties.get().getSparePartsCost() * 0.25 : 0);
			approvedAmountDto.setPart(null);
			approvedAmountDto.setStatus(warranties.get().getStatus());
			listApprovedAmounts.add(approvedAmountDto);
		}
		ApprovedDto approvedDto = new ApprovedDto();
		approvedDto.setApproved(listApprovedAmounts);
		approvedDto.setTotals(this.listTotals(listApprovedAmounts));
		return approvedDto;
	}

	private List<TotalsApprovedAmountDto> listTotals(final List<ApprovedAmountDto> listApprovedAmounts) {
		List<TotalsApprovedAmountDto> listTotals = new ArrayList<>();
		TotalsApprovedAmountDto totals = new TotalsApprovedAmountDto();
		DecimalFormat df = new DecimalFormat("#,###.##");

		Double totalRepair =  listApprovedAmounts.stream().mapToDouble(a -> (a != null) ? a.getRepair().doubleValue() : 0).sum();
		Double labor = listApprovedAmounts.stream().mapToDouble(a -> a.getLabor().doubleValue()).sum();
		Double unship = listApprovedAmounts.stream().mapToDouble(a -> a.getUnship().doubleValue()).sum();
		Double subTotal = totalRepair + labor + unship;
		Double iva = subTotal * 0.16;
		Double total = subTotal + iva;
		
		List<Setting> laborCost =this.settingsRepository.findByName("labor_cost");

		totals.setTitle("TOTAL REFFACCIONES");
		totals.setTotal(df.format(totalRepair));
		listTotals.add(totals);
		totals = new TotalsApprovedAmountDto();
		totals.setTitle("TOTAL T. LABOR");
		totals.setTotal(df.format(labor * laborCost.get(0).getValueNumber()));
		listTotals.add(totals);
		totals = new TotalsApprovedAmountDto();
		totals.setTitle("COSTO DESEMBARQUE");
		totals.setTotal(df.format(unship));
		listTotals.add(totals);
		totals = new TotalsApprovedAmountDto();
		totals.setTitle("SUB TOTAL");
		totals.setTotal(df.format(subTotal));
		listTotals.add(totals);
		totals = new TotalsApprovedAmountDto();
		totals.setTitle("IVA");
		totals.setTotal(df.format(iva));
		listTotals.add(totals);
		totals = new TotalsApprovedAmountDto();
		totals.setTitle("TOTAL");
		totals.setTotal(df.format(total));
		listTotals.add(totals);
		return listTotals;
	}

	@Override
	public void updateWarrantyClaim(Long warrantyId, String status) {
		// TODO Auto-generated method stub
		Optional<WarrantyClaims> warranty = this.warrantyClaimsRepository.findById(warrantyId);
		if (warranty.isPresent()) {
			warranty.get().setStatus(status);
		}
		this.warrantyClaimsRepository.save(warranty.get());
	}

	@Override
	public void saveWarrantyClaimInDraft(WarrantyInformationDto warrantyInformation) {
		// TODO Auto-generated method stub
		WarrantyClaims warranty = this.warrantyClaimsRepository.findByNumberClaims(
				warrantyInformation.getPersonalInformation().getReportInformation().getClaimNumber());
		// Creacion de primer estapa en Draft
		if (warranty == null) {
			warranty = new WarrantyClaims();
		}
		warranty.setNumberClaims(warrantyInformation.getPersonalInformation().getReportInformation().getClaimNumber());
		warranty.setServiceEmployee(
				warrantyInformation.getPersonalInformation().getReportInformation().getElaboratedBY());
		warranty.setCreateDate(
				warrantyInformation.getPersonalInformation().getReportInformation().getReportCreationDate());
		warranty.setStatus(StatusEnum.DRAFT.getStatus());
		Dealer dealer = this.dealerRepository
				.findByName(warrantyInformation.getPersonalInformation().getReportInformation().getDealerName());
		warranty.setDealer(dealer);
		String serviceNumber = (warrantyInformation.getPersonalInformation().getReportInformation()
				.getServiceOrderNumber() != null)
						? warrantyInformation.getPersonalInformation().getReportInformation().getServiceOrderNumber()
						: "";
		warranty.setNoServOrder(serviceNumber);

		// Creacion de datos generales
		if (warranty.getVin() == null) {
			if (warrantyInformation.getPersonalInformation().getMobileUnitInformation().getVinNumber() != null) {
				Optional<Vin> vin = this.vinRepository
						.findByVin(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getVinNumber());
				if(!vin.isPresent()) {
					Vin newVin = new Vin();
					newVin.setEngineNumber(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getEngineSerie());
					newVin.setSalesDate(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getSaleDate());
					newVin.setVin(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getVinNumber());
					Optional<Model> model = this.modelRepository.findById(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getModel().getValue());
					newVin.setModel(model.get());
					this.vinRepository.save(newVin);
					warranty.setVin(newVin);
				} else {
					warranty.setVin(vin.get());
				}
				
				warranty.setKm(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getMileage());
				FirstOwner firstOwner = this.firstOwnerRepository.findByFirstName(
						warrantyInformation.getPersonalInformation().getMobileUnitInformation().getOwner());
				if (firstOwner == null) {
					if(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getOwner() != null) {
						firstOwner = new FirstOwner();
						firstOwner.setAddrStreet(warrantyInformation.getPersonalInformation().getClientInformation().getAddress());
						firstOwner.setDateCreation(new Timestamp(new Date().getTime()));
						firstOwner.setEmail(warrantyInformation.getPersonalInformation().getClientInformation().getEmail());
						firstOwner.setFirstName(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getOwner());
						firstOwner.setLastName("");
						firstOwner.setPhone(warrantyInformation.getPersonalInformation().getClientInformation().getPhone());
						Optional<Location> location = this.locationRepository.findById(
								warrantyInformation.getPersonalInformation().getClientInformation().getLocation().getValue());
						firstOwner.setLocation(location.get());
						warranty.setFirstOwner(firstOwner);
					} else {
						warranty.setFirstOwner(null);
					}
				} else {
					warranty.setFirstOwner(firstOwner);
				}				
			}
		} else {
			Optional<Vin> vin = this.vinRepository
					.findByVin(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getVinNumber());
			
			if(!vin.isPresent()) {
				Vin newVin = new Vin();
				newVin.setEngineNumber(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getEngineSerie());
				newVin.setSalesDate(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getSaleDate());
				newVin.setVin(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getVinNumber());
				Optional<Model> model = this.modelRepository.findById(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getModel().getValue());
				newVin.setModel(model.get());
				this.vinRepository.save(newVin);
				warranty.setVin(newVin);
			} else {
				warranty.setVin(vin.get());
			}
			warranty.setKm(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getMileage());
			FirstOwner firstOwner = this.firstOwnerRepository.findByFirstName(
					warrantyInformation.getPersonalInformation().getMobileUnitInformation().getOwner());
			if (firstOwner == null) {
				if(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getOwner() != null) {
					firstOwner = new FirstOwner();
					firstOwner.setAddrStreet(warrantyInformation.getPersonalInformation().getClientInformation().getAddress());
					firstOwner.setDateCreation(new Timestamp(new Date().getTime()));
					firstOwner.setEmail(warrantyInformation.getPersonalInformation().getClientInformation().getEmail());
					firstOwner.setFirstName(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getOwner());
					firstOwner.setLastName("");
					
					firstOwner.setPhone(warrantyInformation.getPersonalInformation().getClientInformation().getPhone());
					Optional<Location> location = this.locationRepository.findById(
							warrantyInformation.getPersonalInformation().getClientInformation().getLocation().getValue());
					firstOwner.setLocation(location.get());
					warranty.setFirstOwner(firstOwner);
				} else {
					warranty.setFirstOwner(null);
				}
			} else {
				warranty.setFirstOwner(firstOwner);
			}
		}
		
		if(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getSaleBy() != null) {
			warranty.setSoldBy(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getSaleBy());
		}

		if (warranty.getWarrantyClient() == null) {
			if (warrantyInformation.getPersonalInformation().getClientInformation().getClientName() != null) {
				WarrantyClient newClient = new WarrantyClient();
				newClient.setAddressStreet(
						warrantyInformation.getPersonalInformation().getClientInformation().getAddress());
				newClient.setClientName(
						warrantyInformation.getPersonalInformation().getClientInformation().getClientName());
				newClient.setCreateTimeStamp(new Timestamp(new Date().getTime()));
				newClient.setEmail(warrantyInformation.getPersonalInformation().getClientInformation().getEmail());
				newClient.setPhone(warrantyInformation.getPersonalInformation().getClientInformation().getPhone());
				Optional<Location> location = this.locationRepository.findById(
						warrantyInformation.getPersonalInformation().getClientInformation().getLocation().getValue());
				newClient.setLocation(location.get());
				warranty.setWarrantyClient(newClient);
			}
		} else {
			WarrantyClient updateClient = warranty.getWarrantyClient();
			updateClient
					.setAddressStreet(warrantyInformation.getPersonalInformation().getClientInformation().getAddress());
			updateClient
					.setClientName(warrantyInformation.getPersonalInformation().getClientInformation().getClientName());
			updateClient.setCreateTimeStamp(new Timestamp(new Date().getTime()));
			updateClient.setEmail(warrantyInformation.getPersonalInformation().getClientInformation().getEmail());
			updateClient.setPhone(warrantyInformation.getPersonalInformation().getClientInformation().getPhone());
			Optional<Location> location = this.locationRepository.findById(
					warrantyInformation.getPersonalInformation().getClientInformation().getLocation().getValue());
			updateClient.setLocation(location.get());
			warranty.setWarrantyClient(updateClient);
		}
		// Creacion de datos generales
		if (warrantyInformation.getClassificationInformation().getCategoryProblem().getLabel() != null) {
			Optional<TypeClaims> type = this.typeClaimsRepository
					.findById(warrantyInformation.getClassificationInformation().getTypeClaim().getValue());
			warranty.setTypeClaims(type.get());
			Optional<GradeType> grade = this.gradeTypeRepository
					.findById(warrantyInformation.getClassificationInformation().getGrade().getValue());
			warranty.setGradeType(grade.get());
			
			ServiceModel serviceModel = null;
			if(warranty.getServiceModel() != null) {
				//Actualizar
				serviceModel = warranty.getServiceModel();
				serviceModel.setKm(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getMileage());
				serviceModel.setModel(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getModel().getLabel());
				serviceModel.setServiceNumber(Integer.parseInt( warrantyInformation.getClassificationInformation().getServicePerformed()));
				serviceModel.setDateUpdate(new Timestamp(new Date().getTime()));
			} else {
				//Nuevo
				serviceModel = new ServiceModel();
				serviceModel.setKm(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getMileage());
				serviceModel.setModel(warrantyInformation.getPersonalInformation().getMobileUnitInformation().getModel().getLabel());
				serviceModel.setServiceNumber(Integer.parseInt( warrantyInformation.getClassificationInformation().getServicePerformed()));
				serviceModel.setStatus(true);
				serviceModel.setDateCreation(new Timestamp(new Date().getTime()));
			}
			this.serviceModelRepository.save(serviceModel);			
			warranty.setServiceModel(serviceModel);
			Optional<ProblemCategory> problem = this.problemCategoryRepository
					.findById(warrantyInformation.getClassificationInformation().getCategoryProblem().getValue());
			warranty.setProblemCategory(problem.get());
			warranty.setTechnicalEmployee(warrantyInformation.getClassificationInformation().getRepairTechnician());
			warranty.setFailDate(warrantyInformation.getClassificationInformation().getReportedDate());
			warranty.setRepairDate(warrantyInformation.getClassificationInformation().getRapairDate());
		} 

		if (warrantyInformation.getPartInformation().getDefectPart().getAffectedDefect().getLabel() != null) {
			PartsDefectsSymptom partDefect = this.partsDefectsSymptomRepository.findByPartDefectAndSimptom(
					warrantyInformation.getPartInformation().getDefectPart().getAffectedPart().getValue(),
					warrantyInformation.getPartInformation().getDefectPart().getAffectedDefect().getValue(),
					warrantyInformation.getPartInformation().getDefectPart().getSymptom().getValue());
			warranty.setPartsDefectsSymptom(partDefect);
			warranty.setTechnicalDiagnosis(warrantyInformation.getPartInformation().getDefectPart().getDiagTechnical());
			warranty.setCorrectiveAction(
					warrantyInformation.getPartInformation().getDefectPart().getCorrectiveAction());
		} 

		if (warrantyInformation.getPartInformation().getOtherJobInformation() != null) {
			if (warrantyInformation.getPartInformation().getOtherJobInformation().size() > 0) {
				if (warrantyInformation.getPartInformation().getOtherJobInformation().get(0).getDescription() != null) {
					for (OtherJobInformationDto otherJob : warrantyInformation.getPartInformation()
							.getOtherJobInformation()) {
						OtherExpenses otherExpenses = new OtherExpenses();
						if (otherJob.getId() != 0L) {
							otherExpenses.setId(otherJob.getId());
						}
						otherExpenses.setCost(otherJob.getCost());
						otherExpenses.setDescriptionExpenses(otherJob.getDescription());
						otherExpenses.setDetails(otherJob.getDetails());
						otherExpenses.setInvoice(otherJob.getInvoice());
						otherExpenses.setQuantity(otherJob.getQuantity());
						otherExpenses.setTotal(otherJob.getTotal());
						otherExpenses.setWarrantyClaims(warranty);
						this.otherExpensesRepository.save(otherExpenses);
					}
				}
			}
		}

		if (warrantyInformation.getPartInformation().getRelationPart() != null) {
			if (warrantyInformation.getPartInformation().getRelationPart().size() > 0) {
				if (warrantyInformation.getPartInformation().getRelationPart().get(0).getDescription() != null) {
					for (RelationPartDto partRelation : warrantyInformation.getPartInformation().getRelationPart()) {
						PartsReplaced partsReplaced = new PartsReplaced();
						if (partRelation.getId() != 0L) {
							partsReplaced.setId(partRelation.getId());
						}

						Parts part = new Parts();
						part.setBlock(partRelation.getPart().getBlock());
						part.setDescriptionPart(partRelation.getPart().getDescriptionPart());
						part.setFrt(partRelation.getPart().getFrt());
						part.setId(partRelation.getPart().getId());
						part.setLonCode(partRelation.getPart().getLonCode());
						part.setModel(partRelation.getPart().getModel());
						part.setPartNumber(partRelation.getPart().getPartNumber());
						part.setPrice(partRelation.getPart().getPrice());
						part.setPublishCatalogNumber(partRelation.getPart().getPublishCatalogNumber());
						part.setReference(partRelation.getPart().getReference());
						part.setStatus(true);
						part.setYearModelFrom(partRelation.getPart().getYearModelFrom());
						part.setYearModelTo(partRelation.getPart().getYearModelTo());

						partsReplaced.setUnitCost(partRelation.getUnitCost());
						partsReplaced.setDescription(partRelation.getDescription());
						partsReplaced.setPackingCost(partRelation.getPackingCost());
						partsReplaced.setParts(part);
						partsReplaced.setQuantity(partRelation.getQuantity());
						partsReplaced.setTotal(partRelation.getTotal());
						partsReplaced.setWarrantyClaims(warranty);
						System.out.println(partsReplaced.getParts().getPartNumber() + " "
								+ partsReplaced.getParts().getDescriptionPart() + " "
								+ partsReplaced.getParts().getId());
						this.partsReplacedRepository.save(partsReplaced);
					}
				}
			}
		}

		if (warrantyInformation.getPartInformation().getEvidence() != null) {
			if (warrantyInformation.getPartInformation().getEvidence().size() > 0) {
				if (warrantyInformation.getPartInformation().getEvidence().get(0).getFileName() != null) {
					for (EvidenceDto evidence : warrantyInformation.getPartInformation().getEvidence()) {
						Evidences newEvidence = this.evidenceRepository.findByFilePath(evidence.getFileViewUri());

						if (newEvidence == null) {
							newEvidence = new Evidences();
							newEvidence.setCreateTimestamp(new Timestamp(new Date().getTime()));
							newEvidence.setFilePath(evidence.getFileViewUri());
							newEvidence.setStatus(true);
							newEvidence.setTypeFile(evidence.getFileType());
							newEvidence.setWarrantyClaim(warranty);
						} else {
							newEvidence.setUpdateTimestamp(new Timestamp(new Date().getTime()));
						}

						this.evidenceRepository.save(newEvidence);
						System.out.println("Se crea la evidencia en la base de datos");
					}
				}
			}
		}
		
		if (warrantyInformation.getPartInformation().getCostDetail() != null) {
			warranty.setPartsCost(warrantyInformation.getPartInformation().getCostDetail().getSparePartCost());
			warranty.setPackagingCost(warrantyInformation.getPartInformation().getCostDetail().getLandingCost());
			warranty.setLaborCost(warrantyInformation.getPartInformation().getCostDetail().getLaborTime());
			warranty.setSparePartsCost(warrantyInformation.getPartInformation().getCostDetail().getTotalSparePart());
			warranty.setTotalAmount(warrantyInformation.getPartInformation().getCostDetail().getTotalSparePartAndLabor());
			warranty.setTtlCost(warrantyInformation.getPartInformation().getCostDetail().getTotalLaborTime());
		}

		WarrantyConsecutive warrantyConsecutive = this.warrantyConsecutiveRepository
				.findByDealerNumber(dealer.getDealerNumber().toString());

		Integer consecutive = 0;
		String period = "";

		if (warrantyConsecutive != null) {
			period = warrantyConsecutive.getCurrentPeriod().toString();
			if (period.equals(getCurrentDate())) {
				consecutive = warrantyConsecutive.getConsecutive() + 1;
			} else {
				period = getCurrentDate();
				consecutive = 1;
			}
		} else {
			warrantyConsecutive = new WarrantyConsecutive();
			period = getCurrentDate();
			consecutive = 1;
		}

		warrantyConsecutive.setConsecutive(consecutive);
		warrantyConsecutive.setCurrentPeriod(Integer.parseInt(period));
		warrantyConsecutive.setDealer(dealer);

		// Creamos el consecutivo cuando es un nuevo reclamo
		if (warranty.getId() == null) {
			this.warrantyConsecutiveRepository.save(warrantyConsecutive);
			System.out.println("Consecutivo guardado solamente si es un nuevo registro");
		}

		this.warrantyClaimsRepository.save(warranty);

	}

	@Override
	public void saveWarrantyClaimInCancel(WarrantyConsultDto warrantyConsultDto) {
		// TODO Auto-generated method stub
		Optional<WarrantyClaims> warrantyClaim = this.warrantyClaimsRepository.findById(warrantyConsultDto.getId());
		warrantyClaim.get().setStatus(StatusEnum.CANCELADO.getStatus());
		System.out.println(warrantyClaim.get().getStatus());
		try {
			this.warrantyClaimsRepository.save(warrantyClaim.get());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public WarrantyDto getWarrantyClaim(String claimNumber) {
		// TODO Auto-generated method stub
		WarrantyClaims warranty = this.warrantyClaimsRepository.findByNumberClaims(claimNumber);
		WarrantyDto dto = new WarrantyDto();
		if(warranty != null) {
			dto.setClaimNo(warranty.getNumberClaims());
			dto.setClientName(warranty.getFirstOwner().getFirstName());
			dto.setId(warranty.getId());
			dto.setServiceNo(warranty.getNoServOrder());
			dto.setStatus(warranty.getStatus());
			dto.setVin(warranty.getVin().getVin());
			dto.setRepairDate(warranty.getRepairDate().toString());
		}
		return dto;
	}

}
