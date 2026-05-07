
package acme.features.projectSquad.fundraiser;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;

@Repository
public interface ProjectSquadFundraiserRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("SELECT f FROM Fundraiser f WHERE f NOT IN (SELECT m.projectSquad FROM Member m WHERE m.project.id = :projectId)")
	Collection<Fundraiser> findFundraisersNotInProject(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

	@Query("select f from Fundraiser f where f.id = :fundraiserId")
	Fundraiser findFundraiserById(int fundraiserId);

}
