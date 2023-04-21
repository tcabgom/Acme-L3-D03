
package acme.features.lecturer.lecturerDashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.forms.LecturerDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

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
		final LecturerDashboard object = new LecturerDashboard();

		Principal principal;
		int userAccountId;
		int assistantId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		assistantId = principal.getActiveRoleId();
		final Lecturer assistant = this.repository.findOneLecturerByUserAccountId(userAccountId);

		final Map<ActivityType, Integer> sessionsPerType = new HashMap<>();
		for (final ActivityType at : ActivityType.values())
			sessionsPerType.put(at, this.repository.findNumOfLecturerLecturesByType(at, assistant));
		object.setTotalNumberOfLecturesPerType(sessionsPerType);
	}

	@Override
	public void unbind(final LecturerDashboard object) {

		Tuple tuple;
		tuple = super.unbind(object, "assistantSession", "assistantTutorials");
		tuple.put("totalNumberOfHandsOnLectures", object.getTotalNumberOfLecturesPerType().get(ActivityType.HANDS_ON));
		tuple.put("totalNumberOfTheoryLectures", object.getTotalNumberOfLecturesPerType().get(ActivityType.THEORY));
		tuple.put("totalNumberOfBalancedLectures", object.getTotalNumberOfLecturesPerType().get(ActivityType.BALANCED));
		super.getResponse().setData(tuple);

	}
}
