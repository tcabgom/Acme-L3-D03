package acme.features.student.lecture;

import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

    @Query("select lc.lecture from LecturesInCourse lc where lc.course.id = :id")
    Collection<Lecture> findLecturesByCourseId(int id);

    @Query("select l from Lecture l where l.id = :id")
    Lecture findById(int id);
}
