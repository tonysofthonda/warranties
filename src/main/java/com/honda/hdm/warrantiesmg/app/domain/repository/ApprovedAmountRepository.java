package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.ApprovedAmount;

@Repository
public interface ApprovedAmountRepository extends JpaRepository<ApprovedAmount, Long> {

	@Query(value = "select a from ApprovedAmount a where ((a.dealer.id is null or a.dealer.id = :idDealer) or "
			+ "(CAST(a.createTimestamp AS date) between :dateIni and :dateEnd)) and a.status = true")
	List<ApprovedAmount> findByFilters(@Param("idDealer") Long idDealer, @Param("dateIni") Timestamp dateIni, @Param("dateEnd") Timestamp dateEnd);
	
	List<ApprovedAmount> findAllByOrderByIdAsc();
}
