
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionDeleteService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum object;

		object = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));

		status = object != null && (super.getRequest().getPrincipal().hasRole(object.getCompany()) || !object.isDraftMode());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;

		object = this.repository.findPracticumSessionById(super.getRequest().getData("id", int.class));

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstractSession", "startWeek", "finishWeek", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "title", "abstractSession", "startWeek", "finishWeek", "link");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		tuple.put("draftMode", object.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
