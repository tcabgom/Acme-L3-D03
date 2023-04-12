package acme.features.student.lecture;

import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentLectureListService extends AbstractService<Student, Lecture> {

    @Autowired
    StudentLectureRepository repository;

    @Override
    public void check() {
        boolean status = super.getRequest().hasData("courseId", int.class);
        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
        int courseId = super.getRequest().getData("courseId", int.class);
        Collection<Lecture> lectures = repository.findLecturesByCourseId(courseId);

        super.getBuffer().setData(lectures);

    }

    @Override
    public void unbind(Lecture object) {
        assert object != null;

        Tuple tuple = super.unbind(object, "title", "lecAbstract", "learningTime", "body", "knowledge", "furtherInformation");

        super.getResponse().setData(tuple);
    }
}
