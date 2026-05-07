
package acme.features.projectSquad.spokesperson;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Repository
public interface ProjectSquadSpokespersonRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("SELECT s FROM Spokesperson s WHERE s NOT IN (SELECT m.projectSquad FROM Member m WHERE m.project.id = :projectId)")
	Collection<Spokesperson> findSpokespersonsNotInProject(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

	@Query("select s from Spokesperson s where s.id = :spokespersonId")
	Spokesperson findSpokespersonById(int spokespersonId);

}
