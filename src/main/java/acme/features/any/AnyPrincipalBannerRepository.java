
package acme.features.any;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPrincipalBannerRepository extends AbstractRepository {

	@Query(value = "SELECT b FROM Banner b WHERE b.id = :id")
	Banner findBannerById(int id);

	@Query(value = "SELECT b FROM Banner b")
	Collection<Banner> findAllBanners();

}
