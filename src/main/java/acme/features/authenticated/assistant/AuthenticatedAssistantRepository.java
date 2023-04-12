
package acme.features.authenticated.assistant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAssistantRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findUserAccountById(int id);

}
