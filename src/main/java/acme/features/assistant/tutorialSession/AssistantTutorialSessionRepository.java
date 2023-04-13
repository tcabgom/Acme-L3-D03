
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialSession.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Collection<TutorialSession> findManyTutorialSessionsByAssistantId(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findManyTutorialSessionsByMasterId(int id);

	@Query("select ts from TutorialSession ts where ts.id = :id")
	TutorialSession findOneTutorialSessionById(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findManyTutorialsByAssistantId(int id);

	@Query("select ts.tutorial from TutorialSession ts where ts.id = :id")
	Tutorial findOneTutorialBySessionId(int id);

}
