
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Lecture object;
		object = new Lecture();
		object.setDraftMode(true);
		final Lecturer lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setLecturer(lecturer);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "lecAbstract", "learningTime", "body", "knowledge", "furtherInformation");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("learningTime"))
			super.state(object.getLearningTime() >= 0.01, "learningTime", "lecturer.lecture.form.error.learningTime");

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;

		final SelectChoices choices = SelectChoices.from(ActivityType.class, object.getKnowledge());

		tuple = super.unbind(object, "title", "lecAbstract", "learningTime", "body", "knowledge", "furtherInformation", "draftMode", "lecturer");

		tuple.put("knowledge", choices.getSelected().getKey());
		tuple.put("choices", choices);
		super.getResponse().setData(tuple);
	}
}
