
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteRepository repository;

	// AbstractService --------------------------------------------------------


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
		final Note object = new Note();
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		final UserAccount userAccount = this.repository.findOneUserAccountById(userAccountId);
		final Date moment = MomentHelper.getCurrentMoment();

		object.setInstantiationMoment(moment);
		object.setAuthor(userAccount.getUsername() + " - " + userAccount.getIdentity().getSurname() + ", " + userAccount.getIdentity().getName());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "author", "message", "email", "link");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		final boolean confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "authenticated.note.form.error.code");

		final Date date = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		super.getResponse().setAuthorised(MomentHelper.isAfter(object.getInstantiationMoment(), date));
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "title", "author", "message", "email", "link");
		tuple.put("confirmation", false);
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
