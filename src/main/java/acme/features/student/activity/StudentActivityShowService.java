package acme.features.student.activity;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.enumerates.ActivityType;
import acme.entities.lecture.Course;
import acme.framework.components.accounts.DefaultUserIdentity;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

    // Internal state ---------------------------------------------------------

    @Autowired
    protected StudentActivityRepository repository;

    // AbstractService interface ----------------------------------------------


    @Override
    public void check() {
        boolean status = super.getRequest().hasData("id", int.class);

        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        int id = super.getRequest().getData("id", int.class);
        Enrolment enrolment = this.repository.findById(id).getEnrolment();
        int currentStudentId = super.getRequest().getPrincipal().getActiveRoleId();

        Student student = enrolment.getStudent();
        Student currentStudent = this.repository.findStudentById(currentStudentId);

        boolean status = student.getId() == currentStudent.getId();
        super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
        int id = super.getRequest().getData("id", int.class);
        Activity object = this.repository.findById(id);

        super.getBuffer().setData(object);
    }

    @Override
    public void unbind(final Activity object) {
        assert object != null;

        SelectChoices choices = SelectChoices.from(ActivityType.class, object.getType());

        Tuple tuple = super.unbind(object, "title", "activityAbstract", "type", "periodStart", "periodEnd", "furtherInformation");
        tuple.put("readonly", true);
        tuple.put("types", choices);
        tuple.put("type", choices.getSelected());

        super.getResponse().setData(tuple);
    }
}
