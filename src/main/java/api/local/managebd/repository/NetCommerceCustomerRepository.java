package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.NetCommerceCustomer;
@Repository
public interface NetCommerceCustomerRepository extends CrudRepository<NetCommerceCustomer, Long> {

	NetCommerceCustomer findByIdHeader(final Long id);
}
