
package acme.features.lecturer.lecturerDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enumerates.ActivityType;
import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(s) from Lecture s where s.lecturer = :a and s.knowledge = :at")
	Integer findNumOfLecturerLecturesByType(ActivityType at, Lecturer a);

	@Query("select a from Lecturer a where a.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);

	@Query("select l from Lecture l join LecturesInCourse lc on lc.course.id = :id and lc.lecture.id = l.id")
	Collection<Lecture> findManyLecturesByCourseId(int id);
}
