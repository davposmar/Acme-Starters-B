
package acme.features.projectSquad.inventor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.realms.Inventor;

@Repository
public interface ProjectSquadInventorRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("SELECT i FROM Inventor i WHERE i NOT IN (SELECT m.projectSquad FROM Member m WHERE m.project.id = :projectId)")
	Collection<Inventor> findInventorsNotInProject(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

	@Query("select i from Inventor i where i.id = :inventorId")
	Inventor findInventorById(int inventorId);

}
