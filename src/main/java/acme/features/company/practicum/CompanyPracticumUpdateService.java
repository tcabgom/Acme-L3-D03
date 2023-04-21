
package acme.features.company.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
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

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		final Company company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		final Course course = this.repository.findCourseById(super.getRequest().getData("course", int.class));

		object.setCourse(course);
		object.setCompany(company);
		object.setDraftMode(true);

		super.bind(object, "code", "title", "abstractPracticum", "goals");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final Practicum potentialDuplicate = this.repository.findPracticumByCode(object.getCode());
			super.state(potentialDuplicate == null || potentialDuplicate.equals(object), "code", "company.practicum.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "company.practicum.form.error.draftMode");
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
		final SelectChoices choices = SelectChoices.from(this.repository.findAllCourses(), "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstractPracticum", "goals", "draftMode");
		tuple.put("readonly", false);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
