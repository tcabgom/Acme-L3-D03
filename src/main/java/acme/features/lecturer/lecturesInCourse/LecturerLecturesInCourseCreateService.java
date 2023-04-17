
package acme.features.lecturer.lecturesInCourse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LecturesInCourse;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLecturesInCourseCreateService extends AbstractService<Lecturer, LecturesInCourse> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLecturesInCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("lectureId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Lecture object;
		int id;
		id = super.getRequest().getData("lectureId", int.class);
		object = this.repository.findLectureById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getLecturer().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		LecturesInCourse object;
		object = new LecturesInCourse();
		final Lecture lecture;
		int lectureId;
		lectureId = super.getRequest().getData("lectureId", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		object.setLecture(lecture);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final LecturesInCourse object) {
		assert object != null;
		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "id");
		object.setCourse(course);
	}

	@Override
	public void validate(final LecturesInCourse object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("lecture") && !super.getBuffer().getErrors().hasErrors("course")) {

			final Collection<Lecture> lectures = this.repository.findLecturesByCourse(object.getCourse().getId());
			super.state(lectures.isEmpty() || !lectures.contains(object.getLecture()), "course", "lecturer.courseLecture.form.error.lecture");
		}
		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(object.getCourse().isDraftMode(), "course", "lecturer.courseLecture.form.error.course");
	}

	@Override
	public void perform(final LecturesInCourse object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final LecturesInCourse object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "lecture", "course");
		final int lectureId = super.getRequest().getData("lectureId", int.class);
		tuple.put("lectureId", super.getRequest().getData("lectureId", int.class));
		final Lecturer lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		Collection<Course> courses;
		courses = this.repository.findCoursesByLecturer(lecturer);
		final Lecture lecture = this.repository.findOneLectureById(lectureId);
		tuple.put("draftMode", lecture.isDraftMode());

		final SelectChoices choices;
		choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
