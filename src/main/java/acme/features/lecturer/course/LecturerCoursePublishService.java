
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enumerates.ActivityType;
import acme.entities.lecture.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService Interface -------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getLecturer().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "courseAbstract", "retailPrice", "furtherInformation");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		final Collection<Lecture> lectures = this.repository.findLecturesByCourse(object.getId());
		super.state(!lectures.isEmpty(), "draftMode", "lecturer.course.form.error.emptycourse");
		if (!lectures.isEmpty()) {
			boolean allTheory;
			allTheory = lectures.stream().anyMatch(x -> x.getKnowledge().equals(ActivityType.HANDS_ON));
			super.state(allTheory, "activityType", "lecturer.course.form.error.nohandson");
			boolean allPublished;
			allPublished = lectures.stream().allMatch(x -> x.isDraftMode() == false);
			super.state(allPublished, "draftMode", "lecturer.course.form.error.lecturenp");
		}
	}

	@Override
	public void perform(final Course object) {
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "courseAbstract", "retailPrice", "furtherInformation", "draftMode", "lecturer");

		final List<Lecture> lectures = this.repository.findLecturesByCourse(object.getId()).stream().collect(Collectors.toList());
		final ActivityType activityType = object.courseActivityType(lectures);

		tuple.put("activityType", activityType);

		super.getResponse().setData(tuple);
	}
}
