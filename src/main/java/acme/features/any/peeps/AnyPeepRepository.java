
package acme.features.any.peeps;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p")
	Collection<Peep> findAllPeeps();

	@Query("select o from Peep o where o.id = :id")
	Peep findPeepById(int id);

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findOneUserAccountById(int id);
}
