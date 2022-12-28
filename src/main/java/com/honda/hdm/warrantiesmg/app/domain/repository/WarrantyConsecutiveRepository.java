package com.honda.hdm.warrantiesmg.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyConsecutive;

@Repository
public interface WarrantyConsecutiveRepository extends JpaRepository<WarrantyConsecutive, Long> {

	@Query(value="SELECT w FROM WarrantyConsecutive w WHERE w.dealer.dealerNumber = :dealerNum")
	WarrantyConsecutive findByDealerNumber(@Param("dealerNum") String dealerNum);
	
	

}
