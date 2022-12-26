package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.SpareParts;

@Repository
public interface SparePartsRepository extends CrudRepository<SpareParts, Long> {
	
	SpareParts findByNumPart(final String numPart);
}
