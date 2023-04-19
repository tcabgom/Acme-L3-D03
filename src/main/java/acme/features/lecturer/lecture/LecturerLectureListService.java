
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Course object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findCourseById(masterId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getLecturer().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findLecturesByCourse(masterId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "lecAbstract", "learningTime", "knowledge");

		final int masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().setGlobal("masterId", masterId);
		tuple.put("masterId", masterId);
		final Course course = this.repository.findCourseById(masterId);
		final boolean showCreate = course.isDraftMode();
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Lecture> object) {
		assert object != null;

		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().setGlobal("masterId", masterId);
		final Course course = this.repository.findCourseById(masterId);
		final boolean showCreate = course.isDraftMode();
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
