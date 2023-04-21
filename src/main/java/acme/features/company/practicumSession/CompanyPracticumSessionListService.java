
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

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
		Practicum practicum;

		practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));
		status = practicum != null && (!practicum.isDraftMode() || super.getRequest().getPrincipal().hasRole(practicum.getCompany()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;

		objects = this.repository.findPracticumSessionsByMasterId(super.getRequest().getData("practicumId", int.class));

		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("practicumId", super.getRequest().getData("practicumId", int.class));
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSession");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<PracticumSession> objects) {
		assert objects != null;

		Practicum practicum;
		final boolean showCreate;

		practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));
		showCreate = practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setGlobal("practicumId", super.getRequest().getData("practicumId", int.class));
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
