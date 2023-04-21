package acme.features.student.activity;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.lecture.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

    @Autowired
    protected StudentActivityRepository repository;

    // AbstractService interface ------------------------------------------

    @Override
    public void check(){
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
    public void load() {
        int enrolmentId = super.getRequest().getData("enrolmentId", int.class);
        Collection<Activity> object = this.repository.findActivitiesByEnrolmentId(enrolmentId);
        super.getResponse().setGlobal("enrolmentId", enrolmentId);

        super.getBuffer().setData(object);
    }

    @Override
    public void unbind(Activity object) {
        assert object != null;
        Tuple tuple = super.unbind(object, "title", "activityAbstract", "type", "periodStart", "periodEnd", "furtherInformation");

        super.getResponse().setData(tuple);
    }

}
