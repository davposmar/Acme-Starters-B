/*
 * SponsorSponsorshipAssignService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;
import acme.realms.ProjectSquad;

@Service
public class FundraiserStrategyAssignService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;
	private Project							project;
	private int								projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);

		this.project = this.strategy != null ? this.strategy.getProject() : null;

		this.projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && this.strategy.getFundraiser().isPrincipal() && (this.project == null || this.project != null && this.project.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "project");
		this.project = this.strategy != null ? this.strategy.getProject() : null;
	}

	@Override
	public void validate() {

		if (this.strategy != null && this.project != null) {
			boolean projectIsAvailable;

			projectIsAvailable = this.repository.findMyNotPublishedProjects(this.projectSquadId).stream().anyMatch(p -> p.getId() == this.project.getId());
			super.state(projectIsAvailable, "project", "fundraiser.acme.validation.project.not-found.message");
		}

	}

	@Override
	public void execute() {
		int id;

		id = super.getRequest().getData("id", int.class);

		Strategy previousStrategy = this.repository.findStrategyById(id);
		Project previousProject = previousStrategy.getProject();
		if (previousProject != null) {
			long numPeople = this.repository.countNumPeople(previousProject.getId());
			previousProject.updateEffortUsingComponentValues(previousStrategy.getMonthsActive(), 0.0, numPeople);
			this.repository.save(previousProject);
		}
		if (this.project != null) {
			long numPeople = this.repository.countNumPeople(this.project.getId());
			this.project.updateEffortUsingComponentValues(0.0, previousStrategy.getMonthsActive(), numPeople);
			this.repository.save(this.project);
		}

		this.strategy.setProject(this.project);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		Collection<Project> myNotPublishedProjects;
		SelectChoices projects;
		Tuple tuple;

		myNotPublishedProjects = this.repository.findMyNotPublishedProjects(this.projectSquadId);

		projects = SelectChoices.from(myNotPublishedProjects, "ticker", this.project != null ? this.project : this.strategy.getProject());

		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "fundraiser.identity.fullName", "project");
		tuple.put("monthsActive", this.strategy.getMonthsActive());
		tuple.put("expectedPercentage", this.strategy.getExpectedPercentage());
		tuple.put("projects", projects);
	}

}
