package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.ApprovedAmount;
import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsReplaced;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;
import com.honda.hdm.warrantiesmg.app.domain.repository.ApprovedAmountRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.DealerRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsReplacedRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.WarrantyClaimsRepository;
import com.honda.hdm.warrantiesmg.app.service.IApprovedAmountsService;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedAmountDto;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedDto;
import com.honda.hdm.warrantiesmg.app.web.model.TotalsApprovedAmountDto;
import com.honda.hdm.warrantiesmg.configuration.EmailService;
import com.honda.hdm.warrantiesmg.configuration.WmqBusinessLogicException;
import com.honda.hdm.warrantiesmg.util.DateUtils;

@Service
public class ApprovedAmountsServiceImpl implements IApprovedAmountsService {
	
	@Autowired
	private ApprovedAmountRepository approvedAmountRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private DealerRepository dealerRepository;
	
	@Autowired
	private WarrantyClaimsRepository warrantyClaimsRepository;
		
	@Autowired
	private PartsReplacedRepository partsReplacedRepository;

	@Override
	public ApprovedDto getApprovedAmounts(Long idDealer, String dateIni, String dateEnd) throws ParseException {
		List<ApprovedAmountDto> listApprovedAmounts = new ArrayList<>();
		if(idDealer == 0) { idDealer = null; }
		if(dateIni.equals("null")) { dateIni = null; }
		if(dateEnd.equals("null")) { dateEnd = null; }
		List<ApprovedAmount> approvedAmount = this.approvedAmountRepository.findByFilters(idDealer,
				this.dateUtils.dateToTimeStamp("yyyy/MM/dd",dateIni), this.dateUtils.dateToTimeStamp("yyyy/MM/dd",dateEnd));

		if(approvedAmount != null) {
			approvedAmount.forEach(data -> {
				ApprovedAmountDto approvedAmountDto = new ApprovedAmountDto();
				approvedAmountDto.setId(data.getId());
				approvedAmountDto.setClaim(data.getClaim());
				approvedAmountDto.setDealer(data.getDealer().getName());
				approvedAmountDto.setDescription(data.getDescription());
				approvedAmountDto.setLabor(data.getLabor());
				approvedAmountDto.setModel(data.getModel().getModel());
				approvedAmountDto.setRepair(data.getRepair());
				approvedAmountDto.setUnship(data.getRepair() * 0.25);
				approvedAmountDto.setPart(data.getPartsReplaced().getParts().getDescriptionPart());
				approvedAmountDto.setStatus(data.getStatus() ? "NO APROBADO" : "APROBADO");
				listApprovedAmounts.add(approvedAmountDto);
			});
		}
				
		ApprovedDto approvedDto = new ApprovedDto();
		approvedDto.setApproved(listApprovedAmounts);
		approvedDto.setTotals(this.listTotals(listApprovedAmounts));
		return approvedDto;
	}

	@Override
	public void changeDate(Long id) {
		Optional<ApprovedAmount> approved = approvedAmountRepository.findById(id);
		if(approved.isPresent()) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(approved.get().getCreateTimestamp().getTime());
			cal.add(Calendar.DAY_OF_MONTH, 30);
			Timestamp timestamp = new Timestamp(cal.getTime().getTime());
			approved.get().setCreateTimestamp(timestamp);
			approved.get().setUpdateTimestamp(new Timestamp(new Date().getTime()));
			approvedAmountRepository.save(approved.get());
		}			
	}

	@Override
	public void deleteRecord(Long id, String comments) {
		Optional<ApprovedAmount> approved = approvedAmountRepository.findById(id);
		if(approved.isPresent()) {
			approvedAmountRepository.delete(approved.get());
		}
		this.changeStatus(id, "No aprobado", comments);
	}
	
	private void changeStatus(final Long id, final String status, String comments) {
		Optional<WarrantyClaims> claim = warrantyClaimsRepository.findById(id);
		if(claim.isPresent()) {
			claim.get().setStatus(status);
			claim.get().setComments(comments);
			warrantyClaimsRepository.save(claim.get());
		}
	}
		
	@Override
	public void insertIntoAmount(List<Long> dataId) {
		ApprovedAmount amount = null;
		List<ApprovedAmount> listAmount = new ArrayList<>();
		List<WarrantyClaims> claims = new ArrayList<>();
		for(Long id: dataId) {
			Optional<WarrantyClaims> warrantyClaim = warrantyClaimsRepository.findById(id);
			if (warrantyClaim.isPresent()) {
				amount = new ApprovedAmount();
				amount.setCreateTimestamp(new Timestamp(new Date().getTime()));
				amount.setClaim(warrantyClaim.get().getNumberClaims());
				amount.setDealer(warrantyClaim.get().getDealer());
				amount.setDescription(
						warrantyClaim.get().getPartsDefectsSymptom().getPartMotorcycle().getDescriptionSpanish());
				amount.setLabor(warrantyClaim.get().getLaborCost());
				amount.setModel(warrantyClaim.get().getVin().getModel());
				amount.setRepair(warrantyClaim.get().getSparePartsCost());
				amount.setStatus(true);
				List<PartsReplaced> parts = partsReplacedRepository
						.findAllByWarrantyClaimsId(warrantyClaim.get().getId());
				amount.setPartsReplaced(parts.get(0));
				amount.setLabor(warrantyClaim.get().getLaborCost());

				Double subTotal = warrantyClaim.get().getSparePartsCost() + warrantyClaim.get().getLaborCost()
						+ warrantyClaim.get().getPackagingCost();
				Double iva = subTotal * 0.16;
				Double total = subTotal + iva;

				amount.setTotalAproved(total);

				listAmount.add(amount);
				warrantyClaim.get().setStatus("Aprobado");
				claims.add(warrantyClaim.get());
			}
		}
		this.warrantyClaimsRepository.saveAll(claims);
		this.approvedAmountRepository.saveAll(listAmount);
	}
	
	@Override
	public void sendApprovedAmounts(Long idDealer, String dateIni, String dateEnd, Double totalAmount) throws MailException, IllegalArgumentException, MessagingException, ParseException {
		ApprovedDto approved = this.getApprovedAmounts(idDealer, dateIni, dateEnd);
		if(approved == null || approved.getApproved() == null) {
			throw new WmqBusinessLogicException("No existen datos para aprobar"); 
		}
		
		Optional<Dealer> dealer = dealerRepository.findById(idDealer);
		
		if(!dealer.isPresent()) {
			throw new WmqBusinessLogicException("No existe el dealer"); 
		}
		
		if(dealer.get().getGeneralManagerEmail() == null) {
			throw new WmqBusinessLogicException("El dealer no cuenta no email"); 
		}
		
		DecimalFormat df = new DecimalFormat("#,###.##");
		String warranty = "<p><b>PERIODO DEL "+ dateIni +" AL " + dateEnd + "</b></p>";
		warranty += "<br><p>Dealer: " + dealer.get().getDealerNumber() + "</p><br>";
		warranty += "<table style='width:25%' border='1'>"
				+ "<tr>"
					+ "<th>NO. DE RECLAMO</th>" 
					+ "<th>MODELO</th>" 
					+ "<th>PARTE DEFECTUOSA</th>" 
					+ "<th>REFACCIONES</th>"
					+ "<th>DESEMBARQUE</th>"
					+ "<th>LABOR</th>"
				+ "</tr>";
		for(ApprovedAmountDto data : approved.getApproved()) {
			warranty += "<tr> "
				+ "<td style='text-align: center'>" + data.getClaim() + "</td>"
				+ "<td style='text-align: center'>" + data.getModel()  + "</td>"
				+ "<td style='text-align: center'>" + data.getPart() + "</td>"
				+ "<td style='text-align: center'>" + df.format(data.getRepair()) + "</td>"
				+ "<td style='text-align: center'>" + df.format(data.getUnship()) + "</td>"
				+ "<td style='text-align: center'>" + df.format(data.getLabor()) + "</td>"
			+ "</tr>";
			Optional<ApprovedAmount> amount = this.approvedAmountRepository.findById(data.getId());
			if(amount.isPresent()) {
				amount.get().setStatus(false);
				amount.get().setTotalAproved(totalAmount);
				this.approvedAmountRepository.save(amount.get());
			}
		}
		warranty += "</table>";
		warranty += "<br> <br> <br>";
		Double subTotal = approved.getApproved().stream().mapToDouble(a -> a.getRepair().doubleValue()).sum();
		Double unship =  (approved.getApproved().stream().mapToDouble(a -> a.getRepair().doubleValue()).sum()) * 0.25;
		Double totalRepair = subTotal + unship;
		Double labor = approved.getApproved().stream().mapToDouble(a -> a.getLabor().doubleValue()).sum();
		warranty += "<table style='width:25%' border='1'>"
				+ "<tr>"
					+ "<td>SUB TOTAL</td>" 
					+ "<td>" + df.format(subTotal) + "</td>" 
				+ "</tr>"
				+ "<tr>"
					+ "<td>COSTO DE DESEMBARQUE</td>" 
					+ "<td>" + df.format(unship) + "</td>" 
				+ "</tr>"
				+ "<tr>"
					+ "<td>TOTAL REFACCIONES</td>" 
					+ "<td>" + df.format(totalRepair) + "</td>" 
				+ "</tr>"
				+ "<tr>"
					+ "<td>MANO DE OBRA</td>"
					+ "<td>" + df.format(labor) +"</td>"
				+ "</tr>";
		
		warranty += "</table>";
		warranty += "<br> <br> <br>";
		Double sub = totalRepair + labor;
		Double iva = sub * 0.16;
		Double total = sub + iva;
		warranty += "<table style='width:25%' border='1'>"
				+ "<tr>"
					+ "<td>SUB TOTAL</td>" 
					+ "<td>"+ df.format(sub) +"</td>"
				+ "</tr>"
				+ "<tr>"
					+ "<td>IVA</td>" 
					+ "<td>"+ df.format(iva) +"</td>"
				+ "</tr>"
				+ "<tr>"
					+ "<td>TOTAL</td>" 
					+ "<td>" + df.format(total) +"</td>"
				+ "</tr>";
		
		warranty += "</table>";
		warranty += "<br><br>";
		warranty += "<p>*ENVIE SUS FACTURAS A HDM, EN CUANTO LE LLEGUEN LOS MONTOS APROBADOS.</p>";
		warranty += "<p>*UTILICE UNA FACTURA PARA LOS SERVICIOS, Y OTRA PARA LAS GARANTIAS </p>";
		warranty += "<p>*SI ES NUEVO CONCESIONARIO Y ES LA 1ra VEZ QUE LA ENVÍA GARATIAS Y/O SERVICIOS,"
				+ " ES NECESARIO QUE NOS ENVÍE CIERTA INFORMACIÓN FISCAL PARA COMPLETAR EL PROCESO. </p>";
		warranty += "<br><br>";
		warranty += "<p><b>AUTORIZADO</b></p>";
		warranty += "<br><br>";
		warranty += "Giovanni F. Aquino Trigueros";
		warranty += "<br>";
		warranty += "Sub-Coordinador de Servicio";
		List<String> emails = new ArrayList<>();
		emails.add(dealer.get().getGeneralManagerEmail());
		
		String subject = "RESUMEN DE LAS GARANTIAS RECLAMADAS POR EL DISTRIBUIDOR";
		emailService.send(emails, subject, warranty);
		
	}
	
	private List<TotalsApprovedAmountDto> listTotals(final List<ApprovedAmountDto> listApprovedAmounts) {
		List<TotalsApprovedAmountDto> listTotals = new ArrayList<>();
		TotalsApprovedAmountDto totals = new TotalsApprovedAmountDto();
		DecimalFormat df = new DecimalFormat("####.##");
		
		Double totalRepair = listApprovedAmounts.stream().mapToDouble(a -> a.getRepair().doubleValue()).sum();
		Double labor = listApprovedAmounts.stream().mapToDouble(a -> a.getLabor().doubleValue()).sum();
		Double unship = listApprovedAmounts.stream().mapToDouble(a -> a.getUnship().doubleValue()).sum();
		Double subTotal = totalRepair + labor + unship;
		Double iva = subTotal * 0.16;
		Double total = subTotal + iva;
		
		totals.setTitle("TOTAL REFFACCIONES");
		totals.setTotal(df.format(totalRepair));
		listTotals.add(totals);
		totals = new TotalsApprovedAmountDto();
		totals.setTitle("TOTAL T. LABOR");
		totals.setTotal(df.format(labor));
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
	public List<ApprovedAmountDto> getAllHistory() {
		List<ApprovedAmountDto> listApprovedAmounts = new ArrayList<>();
		List<ApprovedAmount> approvedAmount = this.approvedAmountRepository.findAllByOrderByIdAsc();

		if(approvedAmount != null) {
			approvedAmount.forEach(data -> {
				ApprovedAmountDto approvedAmountDto = new ApprovedAmountDto();
				approvedAmountDto.setId(data.getId());
				approvedAmountDto.setClaim(data.getClaim());
				approvedAmountDto.setDealer(data.getDealer().getName());
				approvedAmountDto.setDescription(data.getDescription());
				approvedAmountDto.setLabor(data.getLabor());
				approvedAmountDto.setModel(data.getModel().getModel());
				approvedAmountDto.setRepair(data.getRepair());
				approvedAmountDto.setVin(data.getPartsReplaced().getWarrantyClaims().getVin().getVin());
				approvedAmountDto.setAmount(data.getTotalAproved());
				approvedAmountDto.setDateIni(this.dateUtils.timestampToString(data.getDateCreation(), "yyyy-MM-dd"));
				approvedAmountDto.setUnship(data.getRepair() * 0.25);
				approvedAmountDto.setPart(data.getPartsReplaced().getParts().getDescriptionPart());
				approvedAmountDto.setStatus(data.getStatus() ? "APROBADO" : "NO APROBADO");
				listApprovedAmounts.add(approvedAmountDto);
			});
		}

		return listApprovedAmounts;
	}
	
}
