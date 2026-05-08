
package acme.features.projectSquad.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;

@Repository
public interface ProjectSquadInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.ticker = :ticker")
	Invention findInventionByTicker(String ticker);

	@Query("select i from Invention i where i.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	@Query("select i from Invention i where i.id = :inventionId")
	Invention findinventionById(int inventionId);

	@Query("select p from Project p where p.id = :projectId")
	Project findPorjectById(int projectId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

	@Query("select count(m) from Member m where m.project.id = :projectId")
	Long countNumPeople(int projectId);
}
