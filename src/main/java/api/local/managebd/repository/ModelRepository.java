package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.Model;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long> {
	
	Model findByModel(final String model);
	
}
