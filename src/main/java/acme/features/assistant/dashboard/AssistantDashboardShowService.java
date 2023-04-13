
package acme.features.assistant.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.forms.AssistantDashboard;
import acme.forms.Statistics;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// Abstract Service interface ----------------------------------------------


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
		final AssistantDashboard object = new AssistantDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Assistant assistant = this.repository.findOneAssistantByUserAccountId(userAccountId);

		// Total Number Of Sessions Per Type

		final Map<ActivityType, Integer> sessionsPerType = new HashMap<>();
		for (final ActivityType at : ActivityType.values())
			sessionsPerType.put(at, this.repository.findNumOfAssistantSessionsBySessionType(at, assistant));
		object.setTotalNumberOfSessionsPerType(sessionsPerType);

		// Session Statistics

		final Statistics sessionsStatistics = new Statistics();
		final Collection<Double> sessionsDuration = null;
		sessionsStatistics.obtainValues(sessionsDuration);
		object.setAssistantSession(sessionsStatistics);

		// Tutorial Statistics

		final Statistics tutorialsStatistics = new Statistics();
		final Collection<Double> tutorialsDuration = null;
		tutorialsStatistics.obtainValues(tutorialsDuration);
		object.setAssistantSession(tutorialsStatistics);

		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final AssistantDashboard object) {

		Tuple tuple;
		tuple = super.unbind(object, "totalNumberOfSessionsPerType", "assistantSession", "assistantTutorials");
		super.getResponse().setData(tuple);

	}

}
