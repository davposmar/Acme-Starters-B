
package acme.features.projectSquad.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.fundraising.Strategy;
import acme.entities.inventions.Invention;
import acme.entities.projects.Member;
import acme.entities.projects.Project;

@Repository
public interface ProjectSquadProjectRepository extends AbstractRepository {

	/*
	 * @Query("SELECT DISTINCT p FROM Project p  WHERE (p.manager.id = :managerId AND :managerId IS NOT NULL) OR EXISTS ( SELECT m FROM Member m WHERE m.project.id = p.id AND m.projectSquad.id = :projectSquadId ) ")
	 * Collection<Project> findProjectsByProjectSquadIdOrManagerId(final int projectSquadId, Integer managerId);
	 */

	@Query("SELECT DISTINCT m.project FROM Member m WHERE m.projectSquad.id = :projectSquadId  ")
	Collection<Project> findProjectsByProjectSquadId(final int projectSquadId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("SELECT c FROM Campaign c WHERE c.project.id = :projectId")
	Collection<Campaign> findCampaignsInProject(int projectId);

	@Query("SELECT s FROM Strategy s WHERE s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProject(int projectId);

	@Query("SELECT m FROM Member m WHERE m.project.id = :projectId")
	Collection<Member> findMembersByProject(int projectId);

	@Query("SELECT COUNT(i) = 0 FROM Invention i WHERE i.project.id = :projectId AND NOT EXISTS ( SELECT p FROM Part p WHERE p.invention = i)")
	Boolean canInventionsBePublished(int projectId);

	@Query("SELECT COUNT(c) = 0 FROM Campaign c WHERE c.project.id = :projectId AND NOT EXISTS ( SELECT m FROM Milestone m WHERE m.campaign = c)")
	Boolean canCampaignsBePublished(int projectId);

	@Query("SELECT COUNT(s) = 0 FROM Strategy s WHERE s.project.id = :projectId AND NOT EXISTS ( SELECT t FROM Tactic t WHERE t.strategy = s)")
	Boolean canStrategiesBePublished(int projectId);

	@Query("SELECT COUNT(i) FROM Invention i WHERE i.project.id = :projectId")
	Long countInvetionsOfPorject(int projectId);

}
