
package acme.features.projectSquad.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadCampaignListService extends AbstractService<ProjectSquad, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadCampaignRepository	repository;

	private Project							project;
	private int								projectId;
	private int								projectSquadId;
	private Collection<Campaign>			campaigns;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findProjectById(this.projectId);
		this.campaigns = this.repository.findCampaignsInProject(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && (this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId) || !this.project.getDraftMode());

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment", "spokesperson.identity.fullName");
		super.unbindGlobal("projectId", this.projectId);
	}
}
