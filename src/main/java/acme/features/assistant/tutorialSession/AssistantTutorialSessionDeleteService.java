
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		status = tutorial != null && (!tutorial.isDraftMode() || super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "title", "sessionAbstract", "sessionType", "sessionStart", "sessionEnd", "moreInfo");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;
		final SelectChoices sessionTypeChoices = SelectChoices.from(ActivityType.class, object.getSessionType());

		tuple = super.unbind(object, "title", "sessionAbstract", "sessionStart", "sessionEnd", "moreInfo", "tutorial");
		tuple.put("sessionType", sessionTypeChoices.getSelected().getKey());
		tuple.put("sessionTypes", sessionTypeChoices);
		tuple.put("tutorialId", super.getRequest().getData("tutorialId", int.class));

		super.getResponse().setData(tuple);
	}

}
