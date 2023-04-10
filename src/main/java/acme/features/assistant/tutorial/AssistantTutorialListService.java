
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialListService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		Collection<Tutorial> objects;

		final int userAccountId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyTutorialsByAssistantId(userAccountId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "title", "course.title", "draftMode");
		final int numberOfSessions = this.repository.findManyTutorialSessionsByTutorialId(object).size();
		tuple.put("numberOfSessions", numberOfSessions);
		super.getResponse().setData(tuple);
	}

}
