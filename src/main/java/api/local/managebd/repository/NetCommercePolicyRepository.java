package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.NetCommercePolicy;

@Repository
public interface NetCommercePolicyRepository extends CrudRepository<NetCommercePolicy, Long> {

	NetCommercePolicy findByIdHeader(final Long idHeader);
	
}
