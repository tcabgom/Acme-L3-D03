
package acme.features.lecturer.lecturerDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enumerates.ActivityType;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(s) from Lecture s where s.lecturer = :a and s.activityType = :st")
	Integer findNumOfLecturerLecturesByType(ActivityType st, Lecturer a);

	@Query("select a from Lecturer a where a.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);
}
