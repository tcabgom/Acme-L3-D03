
package acme.features.company.practicum;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	// Internal state -----------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final Practicum object = this.repository.findPracticumById(super.getRequest().getData("id", int.class));

		status = object != null && super.getRequest().getPrincipal().hasRole(Company.class) && object.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Practicum object = this.repository.findPracticumById(super.getRequest().getData("id", int.class));
		//final Company company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());

		//object.setCompany(company);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		//final Company company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		final Course course = this.repository.findCourseById(super.getRequest().getData("course", int.class));

		//object.setCompany(company);
		object.setCourse(course);

		super.bind(object, "code", "title", "abstractPracticum", "goals");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final List<String> codes = this.repository.findAllPracticumsByCode(object.getCode());
			super.state(codes == null || codes.size() == 1, "code", "administrator.Practicum.form.error.code");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		final Collection<Course> courses = this.repository.findAllCourses();
		final SelectChoices choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstractPracticum", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
