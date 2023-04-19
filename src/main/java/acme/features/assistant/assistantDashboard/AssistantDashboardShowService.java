
package acme.features.assistant.assistantDashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
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
		int assistantId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		assistantId = principal.getActiveRoleId();
		final Assistant assistant = this.repository.findOneAssistantByUserAccountId(userAccountId);

		//////// Total Number Of Sessions Per Type

		final Map<ActivityType, Integer> sessionsPerType = new HashMap<>();
		for (final ActivityType at : ActivityType.values())
			sessionsPerType.put(at, this.repository.findNumOfAssistantSessionsBySessionType(at, assistant));
		object.setTotalNumberOfSessionsPerType(sessionsPerType);

		//////// Tutorial Statistics

		// This variable is for the session statistics, but because we obtain all of the assistant's sessions here,
		// to optimise the code it stores the sessions now instead of calling the repository again
		final Collection<TutorialSession> assistantSessions = new ArrayList<>();

		final Statistics tutorialsStatistics = new Statistics();
		final Collection<Tutorial> assistantTutorials = this.repository.findManyTutorialsByAssistantId(assistantId);
		final Collection<Double> tutorialsDuration = new ArrayList<>();

		for (final Tutorial t : assistantTutorials) {
			final Collection<TutorialSession> thisTutorialSessions = this.repository.findManySessionsByTutorialId(t.getId());
			final Double hours = t.getEstimatedTotalTimeInHours(thisTutorialSessions);

			assistantSessions.addAll(thisTutorialSessions);
			tutorialsDuration.add(hours);
		}

		tutorialsStatistics.obtainValues(tutorialsDuration);
		object.setAssistantTutorials(tutorialsStatistics);

		super.getBuffer().setData(object);

		//////// Session Statistics

		final Statistics sessionsStatistics = new Statistics();
		final Collection<Double> sessionsDuration = new ArrayList<>();

		final int hoursInMilliseconds = 3600000;
		final int minutesInMilliseconds = 60000;

		for (final TutorialSession ts : assistantSessions) {

			Double thisSessionDuration;

			final double thisSessionStartTime = ts.getSessionStart().getTime();
			final double thisSessionEndTime = ts.getSessionEnd().getTime();

			final double thisSessionHours = Math.abs(thisSessionEndTime / hoursInMilliseconds - thisSessionStartTime / hoursInMilliseconds);
			final double thisSessionMinutes = Math.abs(thisSessionEndTime / minutesInMilliseconds - thisSessionStartTime / minutesInMilliseconds) % 60 * 0.01;

			thisSessionDuration = thisSessionHours + thisSessionMinutes;

			sessionsDuration.add(thisSessionDuration);
		}

		sessionsStatistics.obtainValues(sessionsDuration);
		object.setAssistantSession(sessionsStatistics);

	}

	@Override
	public void unbind(final AssistantDashboard object) {

		Tuple tuple;
		tuple = super.unbind(object, "assistantSession", "assistantTutorials");
		tuple.put("totalNumberOfHandsOnSessions", object.getTotalNumberOfSessionsPerType().get(ActivityType.HANDS_ON));
		tuple.put("totalNumberOfTheorySessions", object.getTotalNumberOfSessionsPerType().get(ActivityType.THEORY));
		tuple.put("totalNumberOfBalancedSessions", object.getTotalNumberOfSessionsPerType().get(ActivityType.BALANCED));
		super.getResponse().setData(tuple);

	}

}
