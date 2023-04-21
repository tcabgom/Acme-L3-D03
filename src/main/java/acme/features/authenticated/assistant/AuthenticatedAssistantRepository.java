
package acme.features.authenticated.assistant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AuthenticatedAssistantRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select a from Assistant a where a.userAccount.id = :id")
	Assistant findOneAssistantByUserAccountId(int id);

}
