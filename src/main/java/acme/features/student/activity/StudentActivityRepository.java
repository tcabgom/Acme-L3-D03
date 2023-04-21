package acme.features.student.activity;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.lecture.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

    @Query("select a from Activity a where a.enrolment.id = :id")
    Collection<Activity> findActivitiesByEnrolmentId(int id);

    @Query("select a from Activity a where a.id = :id")
    Activity findById(int id);

    @Query("select e from Enrolment e where e.id = :id")
    Enrolment findEnrolmentById(int id);

    @Query("select s from Student s where s.id = :id")
    Student findStudentById(int id);
}
