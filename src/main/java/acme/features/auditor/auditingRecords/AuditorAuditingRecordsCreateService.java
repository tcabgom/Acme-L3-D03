
package acme.features.auditor.auditingRecords;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecords;
import acme.entities.lecture.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordsCreateService extends AbstractService<Auditor, AuditingRecords> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


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
	public void validate(final AuditingRecords object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("auditingPeriodEnd"))
			super.state(MomentHelper.isLongEnough(object.getAuditingPeriodInitial(), object.getAuditingPeriodEnd(), 1, ChronoUnit.HOURS), "auditingPeriodEnd", "auditor.records.form.error.not-long-enough");
	}

	@Override
	public void perform(final AuditingRecords object) {
		assert object != null;

		this.repository.save(object);

	}
	@Override
	public void bind(final AuditingRecords object) {
		assert object != null;
		final int auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		final Auditor auditor = this.repository.findAuditorById(auditorID);
		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);

		object.setCourse(course);
		object.setAuditor(auditor);
		object.setDraftMode(true);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void load() {
		Audit object;

		final int auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		final Auditor auditor = this.repository.findAuditorById(auditorID);

		object = new Audit();
		object.setAuditor(auditor);
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final AuditingRecords object) {
		assert object != null;

		Tuple tuple;
		final SelectChoices choices;
		final Collection<Course> courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("readonly", false);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
