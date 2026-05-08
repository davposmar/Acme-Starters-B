
package acme.features.projectSquad.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Milestone;
import acme.entities.campaigns.MilestoneKind;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadMilestoneShowService extends AbstractService<ProjectSquad, Milestone> {

	@Autowired
	private ProjectSquadMilestoneRepository	repository;

	private Milestone						milestone;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		status = this.milestone != null && this.milestone.getCampaign() != null && (this.repository.isMemberOfTheProject(this.milestone.getCampaign().getProject().getId(), projectSquadId) || !this.milestone.getCampaign().getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());

		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind", "campaign.name");
		tuple.put("draftMode", this.milestone.getCampaign().getDraftMode());
		tuple.put("kinds", choices);
	}
}
