
package acme.features.authenticated.company;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :userAccountId")
	UserAccount findUserAccountById(int userAccountId);

	@Query("select c from Company c where c.VATNumber = :vatNumber")
	Company findOneCompanyByVATNumber(String vatNumber);

	@Query("select c from Company c where c.userAccount.id = :userAccountId")
	Company findCompanyByUserAccountId(int userAccountId);

	@Query("select c from Company c where c.VATNumber = :vatNumber AND c.id = :id")
	Collection<Company> findOneCompanyByVATNumberAndId(String vatNumber, int id);

}
