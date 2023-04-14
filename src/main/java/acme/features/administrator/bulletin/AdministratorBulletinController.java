package acme.features.administrator.bulletin;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class AdministratorBulletinController extends AbstractController<Administrator, Bulletin> {

    // Internal state ---------------------------------------------------------

    @Autowired
    protected AdministratorBulletinCreateService createService;

    // Constructors -----------------------------------------------------------

    @PostConstruct
    protected void initialise() {
        super.addBasicCommand("create", this.createService);

    }



}
