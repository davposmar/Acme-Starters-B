
package acme.features.projectSquad.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadStrategyListService extends AbstractService<ProjectSquad, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadStrategyRepository	repository;

	private Project							project;
	private int								projectId;
	private int								projectSquadId;
	private Collection<Strategy>			strategies;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findProjectById(this.projectId);
		this.strategies = this.repository.findStrategiesByProject(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && (this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId) || !this.project.getDraftMode());

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment", "endMoment", "fundraiser.identity.fullName");
		super.unbindGlobal("projectId", this.projectId);
	}
}
