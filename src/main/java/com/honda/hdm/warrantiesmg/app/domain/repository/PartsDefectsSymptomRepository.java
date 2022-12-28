package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Defect;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsDefectsSymptom;
import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;

@Repository
public interface PartsDefectsSymptomRepository extends JpaRepository<PartsDefectsSymptom, Long> {

	@Query(value = "SELECT DISTINCT pds.defect, pds.defect.id FROM PartsDefectsSymptom pds WHERE pds.partMotorcycle.id = :partId order by pds.defect.id asc")
	List<Defect> findAllByPartMotorcycleId(@Param("partId") Long partId);
	
	@Query(value="SELECT DISTINCT pds.symptom, pds.symptom.id FROM PartsDefectsSymptom pds WHERE pds.partMotorcycle.id = :partId and pds.defect.id = :defectId order by pds.symptom.id asc")
	List<Symptom> findAllByPartIdAndDefectId(@Param("partId")Long partId, @Param("defectId")Long defectId);

	@Query(value="SELECT pds FROM PartsDefectsSymptom pds WHERE (:status IS NULL OR pds.status =:status) order by pds.id asc")
	List<PartsDefectsSymptom> findAllByStatus(@Param("status") Boolean status);

	@Query(value="SELECT pds FROM PartsDefectsSymptom pds WHERE pds.partMotorcycle.id = :partId AND pds.defect.id = :defectId AND pds.symptom.id = :symptomId order by pds.id asc")
	PartsDefectsSymptom findByPartDefectAndSimptom(@Param("partId") Long partId, @Param("defectId") Long defectId, @Param("symptomId") Long symptomId);
	
}
