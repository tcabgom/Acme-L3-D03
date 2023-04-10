
package acme.features.authenticated.offer;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedOfferRepository extends AbstractRepository {

	@Query("select o from Offer o WHERE o.availabilityPeriodEnd >= :date")
	Collection<Offer> findActiveOffers(Date date);

	@Query("select o from Offer o where o.id = :id")
	Offer findOneOfferById(int id);

}
