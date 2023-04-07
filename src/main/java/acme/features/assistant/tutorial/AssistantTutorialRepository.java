
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findManyTutorialsByAssistantId(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

}
