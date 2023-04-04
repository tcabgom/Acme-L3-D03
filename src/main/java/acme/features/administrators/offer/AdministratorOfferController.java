
package acme.features.administrators.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorOfferController extends AbstractController<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferListAllService	listAllService;

	@Autowired
	protected AdministratorOfferShowService		showService;

	@Autowired
	protected AdministratorOfferCreateService	createService;

	@Autowired
	protected AdministratorOfferUpdateService	updateService;

	@Autowired
	protected AdministratorOfferDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
