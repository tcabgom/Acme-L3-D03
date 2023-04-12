package acme.features.student.lecture;

import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.DefaultUserIdentity;
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
        DefaultUserIdentity identity = object.getLecturer().getUserAccount().getIdentity();
        String lecturerName = identity.getName() + " " + identity.getSurname();

        Tuple tuple = super.unbind(object, "title", "lecAbstract", "learningTime", "body", "knowledge", "furtherInformation");
        tuple.put("lecturer", lecturerName);

        super.getResponse().setData(tuple);
    }
}
