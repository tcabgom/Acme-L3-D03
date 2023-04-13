package acme.features.authenticated.bulletin;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

    // Internal state -----------------------------------------------------

    @Autowired
    protected AuthenticatedBulletinRepository repository;

    // AbstractService interface ------------------------------------------

    @Override
    public void check(){
        super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
        super.getBuffer().setData(repository.findAllBulletins());
    }

    @Override
    public void unbind(Bulletin object) {
        assert object != null;
        Tuple tuple = super.unbind(object, "instantiationMoment", "title", "critical", "message", "furtherInformation");
        super.getResponse().setData(tuple);
    }
}
