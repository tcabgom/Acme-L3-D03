
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.lecture.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


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
		Audit object;

		final int auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		final Auditor auditor = this.repository.findAuditorById(auditorID);

		object = new Audit();
		object.setAuditor(auditor);
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
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
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final List<String> codes = this.repository.findAllAudits(super.getRequest().getPrincipal().getActiveRoleId()).stream().map(Audit::getCode).collect(Collectors.toList());
			super.state(!codes.contains(object.getCode()), "code", "administrator.audit.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "administrator.audit.form.error.draftMode");
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);

	}

	@Override
	public void unbind(final Audit object) {
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
