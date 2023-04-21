
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final Practicum practicum;

		practicum = this.repository.findPracticumByPracticumSessionId(super.getRequest().getData("id", int.class));
		status = practicum != null && (!practicum.isDraftMode() || super.getRequest().getPrincipal().hasRole(practicum.getCompany()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSession", "startWeek", "finishWeek", "link");
		tuple.put("practicumId", object.getPracticum().getId());
		tuple.put("draftMode", object.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
