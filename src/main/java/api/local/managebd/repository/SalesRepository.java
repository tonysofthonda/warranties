package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.Sales;

@Repository
public interface SalesRepository extends CrudRepository<Sales, Long> {
	
	Sales findByVin(final String vin);
}
