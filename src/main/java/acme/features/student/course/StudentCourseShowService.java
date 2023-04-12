package acme.features.student.course;

import acme.entities.lecture.Course;
import acme.framework.components.accounts.DefaultUserIdentity;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

    // Internal state ---------------------------------------------------------

    @Autowired
    protected StudentCourseRepository repository;

    // AbstractService interface ----------------------------------------------


    @Override
    public void check() {
        boolean status = super.getRequest().hasData("id", int.class);

        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
        int id = super.getRequest().getData("id", int.class);
        Course object = this.repository.findById(id);

        super.getBuffer().setData(object);
    }

    @Override
    public void unbind(final Course object) {
        assert object != null;
        DefaultUserIdentity identity = object.getLecturer().getUserAccount().getIdentity();
        String lecturerName = identity.getName() + " " + identity.getSurname();

        Tuple tuple = super.unbind(object, "code", "title", "courseAbstract", "retailPrice", "furtherInformation", "draftMode");
        tuple.put("readonly", true);
        tuple.put("courseId", object.getId());
        tuple.put("lecturer", lecturerName);

        super.getResponse().setData(tuple);
    }
}
