
package acme.features.student.enrolment;

import acme.entities.enrolment.Enrolment;
import acme.entities.lecture.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final Enrolment potentialDuplicate = this.repository.findByCode(object.getCode());
			super.state(potentialDuplicate == null, "code", "student.enrolment.form.error.code");
		}
	}


	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);

	}
	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);
		object.setCourse(course);

		super.bind(object, "code", "motivation", "goals");
	}

	@Override
	public void load() {
		Enrolment object = new Enrolment();
		int studentId = super.getRequest().getPrincipal().getActiveRoleId();
		Student student = this.repository.findStudentById(studentId);

		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses = this.repository.findAllPublishedCourses();
		SelectChoices choices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple = super.unbind(object, "code", "motivation", "goals");
		tuple.put("readonly", false);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
