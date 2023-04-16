package acme.features.administrator.bulletin;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AdministratorBulletinRepository extends AbstractRepository {

    @Query("select b from Bulletin b where b.id = :bulletinId")
    Bulletin findBulletinById(int bulletinId);

    @Query("select b from Bulletin b")
    Collection<Bulletin> findAllBulletins();

}
