
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findPracticumSessionsByMasterId(Integer id);

	@Query("select ps.practicum from PracticumSession ps where ps.id = :id")
	Practicum findPracticumByPracticumSessionId(Integer id);

	@Query("select ps from PracticumSession ps where ps.id = :id")
	PracticumSession findPracticumSessionById(int id);

}
