
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LecturesInCourse;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l inner join LecturesInCourse lc on l = lc.lecture inner join Course c on lc.course = c where c.id = :id")
	Collection<Lecture> findLecturesByCourse(int id);

	@Query("select l from Lecture l where l.lecturer = :lecturer")
	Collection<Lecture> findLecturesByLecturer(Lecturer lecturer);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c inner join LecturesInCourse cl on c = cl.course inner join Lecture l on cl.lecture = l where l = :lecture")
	Collection<Course> getCourseByLecture(Lecture lecture);

	@Query("select lc from LecturesInCourse lc where lc.lecture = :lecture")
	Collection<LecturesInCourse> findLecturesInCourseByLecture(Lecture lecture);

}
