
package acme.features.auditor.auditorDashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.enumerates.ActivityType;
import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.forms.AuditorDashboard;
import acme.forms.Statistics;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDahsBoardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashBoardRepository repository;

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
		final AuditorDashboard object = new AuditorDashboard();

		Principal principal;
		int userAccountId;
		int auditorId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		auditorId = principal.getActiveRoleId();
		final Auditor assistant = this.repository.findOneAuditorByUserAccountId(userAccountId);

		//////// Total Number Of Sessions Per Type

		final Collection<Course> courses = this.repository.findAllCourse();
		final List<Audit> handsOn = new ArrayList<>();
		final List<Audit> theory = new ArrayList<>();
		final List<Audit> balanced = new ArrayList<>();

		final Map<ActivityType, Integer> auditsPerType = new HashMap<>();

		for (final Course c : courses) {
			final List<Lecture> lectures = new ArrayList<>(this.repository.findLecturesByCourse(c.getId()));
			if (c.courseActivityType(lectures) == ActivityType.BALANCED) {
				final Collection<Audit> audits = this.repository.findAuditsFromCourse(c.getId());
				balanced.addAll(audits);
			} else if (c.courseActivityType(lectures) == ActivityType.HANDS_ON) {
				final Collection<Audit> audits = this.repository.findAuditsFromCourse(c.getId());
				handsOn.addAll(audits);
			} else {
				final Collection<Audit> audits = this.repository.findAuditsFromCourse(c.getId());
				theory.addAll(audits);
			}
		}

		auditsPerType.put(ActivityType.BALANCED, balanced.size());
		auditsPerType.put(ActivityType.HANDS_ON, handsOn.size());
		auditsPerType.put(ActivityType.THEORY, theory.size());

		object.setTotalNumberOfAuditsByCourseType(auditsPerType);

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
	public void unbind(final AuditorDashboard object) {

		Tuple tuple;
		tuple = super.unbind(object, "assistantSession", "assistantTutorials");
		tuple.put("totalNumberOfHandsOnSessions", object.getTotalNumberOfSessionsPerType().get(ActivityType.HANDS_ON));
		tuple.put("totalNumberOfTheorySessions", object.getTotalNumberOfSessionsPerType().get(ActivityType.THEORY));
		tuple.put("totalNumberOfBalancedSessions", object.getTotalNumberOfSessionsPerType().get(ActivityType.BALANCED));
		super.getResponse().setData(tuple);

	}

}
