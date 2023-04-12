package acme.features.authenticated.bulletin;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedBulletinShowService extends AbstractService<Authenticated, Bulletin> {

    // Internal state ---------------------------------------------------------

    @Autowired
    protected AuthenticatedBulletinRepository repository;

    // AbstractService interface ----------------------------------------------

    @Override
    public void check() {
        boolean status;
        status = super.getRequest().hasData("id", int.class);
        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        int id = super.getRequest().getData("id", int.class);
        Bulletin bulletin = this.repository.findBulletinById(id);
        boolean status = bulletin != null;
        super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
        int id = super.getRequest().getData("id", int.class);
        Bulletin bulletin = this.repository.findBulletinById(id);
        super.getBuffer().setData(bulletin);

    }

    @Override
    public void unbind(Bulletin object) {
        assert object != null;
        Tuple tuple = super.unbind(object, "instantiationMoment", "title", "critical", "message", "furtherInformation");
        super.getResponse().setData(tuple);
    }
}
