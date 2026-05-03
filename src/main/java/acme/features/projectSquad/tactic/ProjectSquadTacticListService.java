
package acme.features.projectSquad.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.entities.fundraising.Tactic;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadTacticListService extends AbstractService<ProjectSquad, Tactic> {

	@Autowired
	private ProjectSquadTacticRepository	repository;

	private Strategy						strategy;
	private Collection<Tactic>				tactics;


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		status = this.strategy != null && (this.repository.isMemberOfTheProject(this.strategy.getProject().getId(), projectSquadId) || !this.strategy.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "notes", "expectedPercentage", "kind");
		super.unbindGlobal("strategyId", this.strategy.getId());
	}
}
