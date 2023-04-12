package acme.features.student.lecture;

import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentLectureShowService extends AbstractService<Student, Lecture> {

    @Autowired
    protected StudentLectureRepository repository;

    @Override
    public void check() {
        super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
        int id = super.getRequest().getData("id", int.class);
        Lecture object = this.repository.findById(id);
        super.getBuffer().setData(object);
    }

    @Override
    public void unbind(final Lecture object) {
        Tuple tuple = super.unbind(object, "title", "lecAsbtract", "learningTime", "body", "knowledge", "furtherInformation");

            super.getResponse().setData(tuple);
    }
}
