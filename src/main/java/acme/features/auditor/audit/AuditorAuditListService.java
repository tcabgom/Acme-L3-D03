
package acme.features.auditor.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecords;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListService extends AbstractService<Auditor, Audit> {

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
	public void load() {
		Collection<Audit> object;

		object = this.repository.findAllAudits(super.getRequest().getPrincipal().getActiveRoleId());

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		final Collection<AuditingRecords> records = this.repository.findAllAuditingRecordsFromAudit(object.getId());
		final List<String> mark = new ArrayList<>();
		records.stream().map(AuditingRecords::getMark).forEach(m -> mark.add(m.toString()));

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("auditorID", object.getAuditor().getProffesionalID());
		tuple.put("auditor", object.getAuditor().getIdentity().getFullName());
		tuple.put("mark", mark);

		super.getResponse().setData(tuple);
	}

}
