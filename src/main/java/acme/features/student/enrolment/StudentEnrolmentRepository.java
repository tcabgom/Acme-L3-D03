
package acme.features.student.enrolment;

import acme.entities.banner.Banner;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query(value = "SELECT s FROM Student s WHERE s.id = :id")
	Student findStudentById(int id);

	@Query(value = "SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findById(int id);

	@Query(value = "SELECT e FROM Enrolment e WHERE e.student.id = :studentId")
	Collection<Enrolment> findEnrolmentsByStudentId(int studentId);
}
