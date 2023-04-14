
package acme.features.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		final int auditorID = super.getRequest().getPrincipal().getAccountId();
		final Auditor auditor = this.repository.findAuditorById(auditorID);

		object.setAuditor(auditor);
		object.setDraftMode(true);

		this.repository.save(object);

	}
	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void load() {
		Audit object;

		object = new Audit();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "displayPeriodInitial", "displayPeriodEnding", "linkToPicture", "slogan", "linWebDocument");
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
