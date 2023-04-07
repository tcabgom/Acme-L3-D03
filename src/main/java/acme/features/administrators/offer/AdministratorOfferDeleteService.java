
package acme.features.administrators.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferDeleteService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final int id = super.getRequest().getData("id", int.class);
		final Offer object = this.repository.findOneOfferById(id);
		super.getResponse().setAuthorised(MomentHelper.getCurrentMoment().before(object.getAvailabilityPeriodStart()));
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "header", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "moreInfo");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiatiation", "header", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "moreInfo");

		final boolean readonly = MomentHelper.getCurrentMoment().after(object.getAvailabilityPeriodStart());
		tuple.put("readonly", readonly);

		super.getResponse().setData(tuple);
	}

}
