
package acme.features.auditor.auditorDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashBoardRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.userAccount.id = :id")
	Auditor findOneAuditorByUserAccountId(int id);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourse();

	@Query("select l from Lecture l inner join LecturesInCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = :id")
	Collection<Lecture> findLecturesByCourse(int id);

	@Query("SELECT a FROM Audit a WHERE a.course.id= :id")
	Collection<Audit> findAuditsFromCourse(int id);

}
