
package acme.features.auditor.auditingRecords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.AuditingRecords;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordsShowService extends AbstractService<Auditor, AuditingRecords> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecords object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final AuditingRecords object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assesment", "auditingPeriodInitial", "auditingPeriodEnd", "furtherInformation", "mark", "draftMode");
		tuple.put("readonly", false);
		tuple.put("auditId", object.getAudit().getId());

		super.getResponse().setData(tuple);
	}

}
