
package acme.features.assistant.tutorialSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		status = tutorial != null && (!tutorial.isDraftMode() || super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final TutorialSession object;

		final int masterId = super.getRequest().getData("masterId", int.class);
		final Tutorial tutorial = this.repository.findOneTutorialById(masterId);

		object = new TutorialSession();
		object.setTitle("");
		object.setSessionAbstract("");
		object.setTutorial(tutorial);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "title", "sessionAbstract", "sessionType", "sessionStart", "sessionEnd", "moreInfo", "tutorial");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("sessionStart")) {
			final Date minimunValidStartDate = MomentHelper.deltaFromMoment(MomentHelper.getCurrentMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getSessionStart(), minimunValidStartDate), "sessionStart", "assistant.tutorialSession.form.error.sessionStart");
		}

		if (!super.getBuffer().getErrors().hasErrors("sessionEnd")) {
			final Date minimunValidEndDate = MomentHelper.deltaFromMoment(object.getSessionStart(), 5, ChronoUnit.HOURS);
			super.state(MomentHelper.isBefore(object.getSessionEnd(), minimunValidEndDate), "sessionEnd", "assistant.tutorialSession.form.error.sessionEnd");
		}

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;
		final SelectChoices sessionTypeChoices = SelectChoices.from(ActivityType.class, object.getSessionType());

		tuple = super.unbind(object, "title", "sessionAbstract", "sessionStart", "sessionEnd", "moreInfo", "tutorial");
		tuple.put("sessionType", sessionTypeChoices.getSelected().getKey());
		tuple.put("sessionTypes", sessionTypeChoices);
		tuple.put("masterId", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
