
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

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

		super.getResponse().setAuthorised(tutorialExists && assistantOwnsTutorial);
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
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final Collection<TutorialSession> sessions = this.repository.findManyTutorialSessionsByTutorialId(object);
		final SelectChoices choices;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "tutorialAbstract", "goals", "draftMode");
		final int numberOfSessions = sessions.size();
		tuple.put("numberOfSessions", numberOfSessions);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTotalTime", object.getEstimatedTotalTimeInHours(sessions));
		tuple.put("readonly", !object.isDraftMode());
		super.getResponse().setData(tuple);
	}

}
