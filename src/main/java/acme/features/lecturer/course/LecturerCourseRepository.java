
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LecturesInCourse;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.lecturer.userAccount.id = :id")
	Collection<Course> findCoursesByLecturerId(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select lc from LecturesInCourse lc where lc.course = :course")
	Collection<LecturesInCourse> findCourseLecturesByCourse(Course course);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select l from Lecture l inner join LecturesInCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = :id")
	Collection<Lecture> findLecturesByCourse(int id);

}
