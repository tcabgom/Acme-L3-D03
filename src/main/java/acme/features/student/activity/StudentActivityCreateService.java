package acme.features.student.activity;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.enumerates.ActivityType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

    @Autowired
    protected StudentActivityRepository repository;

    // AbstractService interface ------------------------------------------

    @Override
    public void check() {
        boolean status;
        status = super.getRequest().hasData("enrolmentId", int.class);
        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        int id = super.getRequest().getData("enrolmentId", int.class);
        Enrolment enrolment = this.repository.findEnrolmentById(id);
        int studentRoleId = super.getRequest().getPrincipal().getActiveRoleId();

        Student student = enrolment.getStudent();
        Student currentStudent = this.repository.findStudentById(studentRoleId);

        boolean status = student.getId() == currentStudent.getId();
        super.getResponse().setAuthorised(status);
    }

    @Override
    public void bind(Activity object) {
        assert object != null;

        super.bind(object, "title", "activityAbstract", "type", "periodStart", "periodEnd", "furtherInformation");
    }

    @Override
    public void validate(Activity object) {
        assert object != null;

        if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
            super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), object.getPeriodStart()), "periodEnd", "student.enrolment.form.error.periodEnd");
        }
    }

    @Override
    public void perform(Activity object) {
        assert object != null;

        repository.save(object);

    }

    @Override
    public void load() {
        int enrolmentId = super.getRequest().getData("enrolmentId", int.class);
        Enrolment enrolment = this.repository.findEnrolmentById(enrolmentId);
        Activity object = new Activity();

        object.setEnrolment(enrolment);
        super.getResponse().setGlobal("enrolmentId", enrolmentId);

        super.getBuffer().setData(object);
    }

    @Override
    public void unbind(Activity object) {
        assert object != null;

        SelectChoices choices = SelectChoices.from(ActivityType.class, object.getType());

        Tuple tuple = super.unbind(object, "title", "activityAbstract", "type", "periodStart", "periodEnd", "furtherInformation");

        tuple.put("types", choices);
        tuple.put("type", choices.getSelected());

        super.getResponse().setData(tuple);
    }
}
