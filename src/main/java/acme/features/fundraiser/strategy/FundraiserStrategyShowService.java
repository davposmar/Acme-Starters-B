
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;
import acme.realms.ProjectSquad;

public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;
	private Project							project;
	private int								projectSquadId;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);

		this.projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		this.project = this.strategy != null ? this.strategy.getProject() : null;
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && (this.strategy.getFundraiser().isPrincipal() || !this.strategy.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices projects = null;
		if (this.project == null || this.project != null && this.project.getDraftMode()) {
			Collection<Project> availableProjects = this.repository.findMyNotPublishedProjects(this.projectSquadId);
			projects = SelectChoices.from(availableProjects, "ticker", this.project != null ? this.project : this.strategy.getProject());
		}
		Tuple tuple;

		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "fundraiser.identity.fullName", "project");
		tuple.put("monthsActive", this.strategy.getMonthsActive());
		tuple.put("expectedPercentage", this.strategy.getExpectedPercentage());
		if (this.project == null || this.project != null && this.project.getDraftMode()) {
			tuple.put("projects", projects);
			tuple.put("canEdit", true);
		} else {
			tuple.put("canEdit", false);
			tuple.put("proTick", this.project.getTicker());
		}

	}
}
