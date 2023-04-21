
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecords;
import acme.entities.lecture.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query(value = "SELECT a FROM Audit a WHERE a.id = :id")
	Audit findAuditById(int id);

	@Query(value = "SELECT a FROM Audit a WHERE a.auditor.id = :id")
	Collection<Audit> findAllAudits(int id);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT ar FROM AuditingRecords ar WHERE ar.audit.id = :id")
	Collection<AuditingRecords> findAllAuditingRecordsFromAudit(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT a FROM Auditor a WHERE a.id= :id")
	Auditor findAuditorById(int id);
}
