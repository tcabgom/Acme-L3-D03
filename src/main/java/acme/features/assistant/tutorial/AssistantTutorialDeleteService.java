
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {

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

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		final boolean tutorialIsNotPublished = object.isDraftMode();
		final boolean assistantOwnsTutorial = object.getAssistant().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(tutorialIsNotPublished && assistantOwnsTutorial);
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

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		final Collection<TutorialSession> tutorialSessions = this.repository.findManyTutorialSessionsByTutorialId(object);
		this.repository.deleteAll(tutorialSessions);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final Collection<TutorialSession> sessions = this.repository.findManyTutorialSessionsByTutorialId(object);
		final SelectChoices choices;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "tutorialAbstract", "goals", "draftMode");
		final int numberOfSessions = this.repository.findManyTutorialSessionsByTutorialId(object).size();
		tuple.put("numberOfSessions", numberOfSessions);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTotalTime", object.getEstimatedTotalTimeInHours(sessions));
		super.getResponse().setData(tuple);
	}

}
