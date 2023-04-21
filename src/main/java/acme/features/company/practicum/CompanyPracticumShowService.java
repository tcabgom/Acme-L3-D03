
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

	// Internal state -----------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		final Collection<Course> courses = this.repository.findAllCourses();
		final Collection<PracticumSession> sessions = this.repository.findPracticumSessionByPracticum(object);
		final SelectChoices choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstractPracticum", "goals", "draftMode");
		tuple.put("readonly", !object.isDraftMode());
		tuple.put("estimatedTime", object.getEstimatedTotalTimeInHours(sessions));
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
