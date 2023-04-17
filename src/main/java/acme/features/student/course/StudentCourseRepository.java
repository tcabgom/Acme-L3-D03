package acme.features.student.course;

import acme.entities.lecture.Course;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

    @Query("select c from Course c where c.draftMode = false")
    Collection<Course> findAllPublishedCourses();

    @Query("select c from Course c where c.id = :id")
    Course findById(int id);

}
