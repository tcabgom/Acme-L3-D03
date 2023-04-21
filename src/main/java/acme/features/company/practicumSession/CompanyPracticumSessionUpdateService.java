
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("practicumId", int.class) && super.getRequest().hasData("id", int.class);

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
		final PracticumSession object = new PracticumSession();

		final Practicum practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));

		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		final Practicum practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));

		object.setPracticum(practicum);

		super.bind(object, "title", "abstractSession", "startWeek", "finishWeek", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finishWeek"))
			super.state(MomentHelper.isLongEnough(object.getStartWeek(), object.getFinishWeek(), 1, ChronoUnit.WEEKS), "finishWeek", "company.pacticumSession.form.error.not-long-enough");

		if (!super.getBuffer().getErrors().hasErrors("startWeek"))
			super.state(MomentHelper.isAfter(object.getStartWeek(), MomentHelper.deltaFromMoment(MomentHelper.getCurrentMoment(), 1, ChronoUnit.WEEKS)), "startWeek", "company.practicumSession.form.error.sessionStart");
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSession", "startWeek", "finishWeek", "link");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
