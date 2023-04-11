
package acme.features.authenticated.note;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.note.Note;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("select n from Note n where n.instantiationMoment >= :date")
	Collection<Note> findAllRecentNotes(Date date);

	@Query("select n from Note n where n.id = :id")
	Note findNoteById(int id);

}
