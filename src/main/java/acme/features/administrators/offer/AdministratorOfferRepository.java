
package acme.features.administrators.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select o from Offer o")
	Collection<Offer> findAllOffers();

	@Query("select o from Offer o where o.id = :id")
	Offer findOneOfferById(int id);

}