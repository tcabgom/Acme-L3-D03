
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorialSession.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Collection<TutorialSession> findManyTutorialSessionsByAssistantId(int id);

	@Query("select ts from TutorialSession ts where ts.id = :id")
	TutorialSession findOneTutorialSessionById(int id);

}
