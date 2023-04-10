
package acme.features.any;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPrincipalBannerListActiveService extends AbstractService<Any, Banner> {

	@Autowired
	protected AnyPrincipalBannerRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Banner> object;

		object = this.repository.findAllBanners();
		final List<Banner> objects = object.stream().filter(b -> MomentHelper.isAfter(MomentHelper.getCurrentMoment(), b.getDisplayPeriodInitial()) && MomentHelper.isBefore(MomentHelper.getCurrentMoment(), b.getDisplayPeriodEnding()))
			.collect(Collectors.toList());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "displayPeriodInitial", "displayPeriodEnding", "linkToPicture", "slogan", "linWebDocument");

		super.getResponse().setData(tuple);
	}

}
