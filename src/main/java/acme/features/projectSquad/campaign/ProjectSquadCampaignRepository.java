
package acme.features.projectSquad.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Repository
public interface ProjectSquadCampaignRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select s from Campaign s where s.id = :campaignId")
	Campaign findCampaignById(int campaignId);

	@Query("SELECT c FROM Campaign c WHERE c.project.id = :projectId")
	Collection<Campaign> findCampaignsInProject(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

}
