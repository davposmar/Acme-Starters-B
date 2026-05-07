
package acme.features.projectSquad.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Tactic;
import acme.entities.fundraising.TacticKind;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadTacticShowService extends AbstractService<ProjectSquad, Tactic> {

	@Autowired
	private ProjectSquadTacticRepository	repository;

	private Tactic							tactic;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		status = this.tactic != null && this.tactic.getStrategy() != null && (this.repository.isMemberOfTheProject(this.tactic.getStrategy().getProject().getId(), projectSquadId) || !this.tactic.getStrategy().getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind", "strategy.name");
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
		tuple.put("kinds", choices);
	}
}
