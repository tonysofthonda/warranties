package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.NetCommerceHeader;

@Repository
public interface NetCommerceHeaderRepository extends CrudRepository<NetCommerceHeader, Long> {

	NetCommerceHeader findByVin(final String vin);
}
