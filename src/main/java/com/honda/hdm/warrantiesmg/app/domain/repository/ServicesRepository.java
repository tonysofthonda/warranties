package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>{
	
	@Query(value = "SELECT s FROM Services s WHERE s.status <> 'Eliminado' ORDER BY s.idService ASC")
	List<Services> findAll();
	
	@Query(value = "SELECT s FROM Services "
			+ " s WHERE ((:search is null or s.orderNumber LIKE '%'||:search||'%') "
			+ " OR (:search IS NULL OR s.dealerNum LIKE '%'||:search||'%') "
			+ " OR (:search IS NULL OR s.dealerName LIKE '%'||:search||'%') "
			+ " OR (:search IS NULL OR s.reportedBy LIKE '%'||:search||'%') "
			+ " or (:search IS NULL or s.status LIKE '%'||:search||'%') "
			+ " OR (:search IS NULL OR s.customerName LIKE '%'||:search||'%') "
			+ " OR (:search IS NULL OR s.vin LIKE '%'||:search||'%')) "
			+ " AND CAST (s.dateRegister AS date) = :creationDate "
			+ " ORDER BY s.orderNumber DESC")
	List<Services> findAllByFilters(@Param("search") String search, @Param("creationDate") Timestamp creationDate);
	
	Services findTopByOrderByIdServiceDesc();
	
	Services findByOrderNumber(String orderNumber);

}
