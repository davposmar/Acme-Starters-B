
package acme.features.projectSquad.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface ProjectSquadMilestoneRepository extends AbstractRepository {

	@Query("Select s from Campaign s where s.id = :campaignId")
	Campaign findCampaignById(int campaignId);

	@Query("Select t from Milestone t where t.id = :id")
	Milestone findMilestoneById(int id);

	@Query("Select t from Milestone t where t.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);
}
