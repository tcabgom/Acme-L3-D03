package acme.features.student.course;

import acme.entities.lecture.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseListService extends AbstractService<Student, Course> {

    @Autowired
    protected StudentCourseRepository repository;

    // AbstractService interface ------------------------------------------

    @Override
    public void check(){
        super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
        super.getBuffer().setData(repository.findAllPublishedCourses());
    }

    @Override
    public void unbind(Course object) {
        assert object != null;
        Tuple tuple = super.unbind(object, "code", "title", "courseAbstract", "retailPrice", "furtherInformation", "draftMode", "lecturer");
        super.getResponse().setData(tuple);
    }

}
