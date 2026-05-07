
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface AnyInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.draftMode = false")
	Collection<Invention> findPublicInventions();
	@Query("select i from Invention i where i.id = :inventionId and i.draftMode = false")
	Invention findPublicInventionById(int inventionId);

	@Query("select i from Invention i where i.project.id = ?1 and i.project.draftMode = false")
	Collection<Invention> findPublicInventionsByProjectId(int projectId);
}
