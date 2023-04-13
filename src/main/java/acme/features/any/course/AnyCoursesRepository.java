
package acme.features.any.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCoursesRepository extends AbstractRepository {

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.id = :id and c.draftMode = false")
	Course findCourseById(int id);

	@Query("select l from Lecture l inner join LecturesInCourse cl on l = cl.lecture inner join Course c on cl.course = c where c.id = :id")
	Collection<Lecture> findLecturesByCourse(int id);
}
