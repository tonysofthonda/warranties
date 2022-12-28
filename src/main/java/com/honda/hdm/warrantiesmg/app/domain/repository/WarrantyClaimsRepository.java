package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;

@Repository
public interface WarrantyClaimsRepository extends JpaRepository<WarrantyClaims, Long> {

	@Query(value = "select w from WarrantyClaims "
			+ " w where ((:search is null or w.numberClaims like '%'||:search||'%') "
			+ " or (:search is null or w.noServOrder like '%'||:search||'%') "
			+ " or (:search is null or w.vin.vin like '%'||:search||'%') "
			+ " or (:search is null or w.status like '%'||:search||'%') "
			+ " or (:search is null or w.warrantyClient.clientName like '%'||:search||'%')) "
			+ " and CAST (w.createDate  AS date) between :dateIni and :dateEnd "
			+ " order by w.noServOrder desc")
	List<WarrantyClaims> findAllByFilters(@Param("search") String search, @Param("dateIni") Timestamp dateIni, @Param("dateEnd") Timestamp dateEnd);
	
	@Query(value = "select a from WarrantyClaims a where ((a.dealer.id is null or a.dealer.id = :idDealer) or "
			+ "(CAST(a.createDate AS date) between :dateIni and :dateEnd)) and a.status in ('Enviado','Onhold')")
	List<WarrantyClaims> findByFilters(@Param("idDealer") Long idDealer, @Param("dateIni") Timestamp dateIni, @Param("dateEnd") Timestamp dateEnd);
	
	WarrantyClaims findByNumberClaims(String numberClaims);
	
}
