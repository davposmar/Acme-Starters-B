
package acme.features.projectSquad.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadMilestoneListService extends AbstractService<ProjectSquad, Milestone> {

	@Autowired
	private ProjectSquadMilestoneRepository	repository;

	private Campaign						campaign;
	private Collection<Milestone>			milestones;


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);
		this.milestones = this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		status = this.campaign != null && (this.repository.isMemberOfTheProject(this.campaign.getProject().getId(), projectSquadId) || !this.campaign.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestones, "title", "achievements", "effort", "kind");
		super.unbindGlobal("campaignId", this.campaign.getId());
	}
}
