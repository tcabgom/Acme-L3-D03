
package acme.features.authenticated.offer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Offer object;
		final int id = super.getRequest().getData("id", int.class);
		final Date now = new Date();

		object = this.repository.findOneOfferById(id);

		super.getResponse().setAuthorised(now.compareTo(object.getAvailabilityPeriodEnd()) < 0);
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
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiatiation", "header", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "moreInfo");

		super.getResponse().setData(tuple);
	}

}
