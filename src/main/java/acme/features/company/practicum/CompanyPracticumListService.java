
package acme.features.company.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

	// Internal state -----------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		super.getBuffer().setData(this.repository.findAllPracticumByCompany(super.getRequest().getPrincipal().getActiveRoleId()));
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "code", "title", "abstractPracticum", "goals", "draftMode");
		super.getResponse().setData(tuple);
	}

}
