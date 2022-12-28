package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {

	Optional<Dealer> findByDealerNumber(String dealerNumber);
	
//	Optional<Dealer> findByDealerNumber(final String dealerNumber);
	
	@Query(value = "select d from Dealer d order by id asc")
	List<Dealer> findAllOrderById();

	Dealer findByName(String dealerName);
}
