package api.local.managebd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.local.managebd.model.JapanFiles;

@Repository
public interface JapanFilesRepository extends CrudRepository<JapanFiles, Long> {

	JapanFiles findByNumPart(final String numPart);
	
	JapanFiles findByFile(final String file);
}
