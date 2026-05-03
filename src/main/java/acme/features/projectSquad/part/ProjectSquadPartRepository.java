
package acme.features.projectSquad.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Repository
public interface ProjectSquadPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.id = :partId")
	Part findPartById(int partId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("select i from Invention i where i.id = :inventionId")
	Invention findInvenitonById(int inventionId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

}
