
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialUpdateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		final int tutorialOwnerId = object.getAssistant().getId();
		final int userAccountId = super.getRequest().getPrincipal().getActiveRoleId();

		final boolean tutorialExists = object != null;
		final boolean assistantOwnsTutorial = tutorialOwnerId == userAccountId;
		final boolean tutorialIsNotPublished = object.isDraftMode();

		super.getResponse().setAuthorised(tutorialExists && assistantOwnsTutorial && tutorialIsNotPublished);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);

		super.bind(object, "code", "title", "tutorialAbstract", "goals");
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final Tutorial potentialDuplicate = this.repository.findOneTutorialByCode(object.getCode());
			super.state(potentialDuplicate == null || potentialDuplicate.getId() == object.getId(), "code", "assistant.tutorial.form.error.code");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choices;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "tutorialAbstract", "goals", "draftMode");
		final int numberOfSessions = this.repository.findManyTutorialSessionsByTutorialId(object).size();
		tuple.put("numberOfSessions", numberOfSessions);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
