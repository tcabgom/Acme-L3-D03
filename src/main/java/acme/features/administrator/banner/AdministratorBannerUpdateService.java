
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiation", "displayPeriodInitial", "displayPeriodEnding", "linkToPicture", "slogan", "linWebDocument");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodInitial"))
			super.state(MomentHelper.isAfter(object.getDisplayPeriodInitial(), object.getInstantiation()), "displayPeriodInitial", "administrator.banner.form.error.beginning-close");
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnding"))
			super.state(MomentHelper.isLongEnough(object.getDisplayPeriodInitial(), object.getDisplayPeriodEnding(), 7, ChronoUnit.DAYS), "displayPeriodEnding", "administrator.banner.form.error.ending-not-long-enough");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);

	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "displayPeriodInitial", "displayPeriodEnding", "linkToPicture", "slogan", "linWebDocument");
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
