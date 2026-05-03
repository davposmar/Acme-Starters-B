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

package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignAssignService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;
	private Project							project;
	private int								projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);

		this.project = this.campaign != null ? this.campaign.getProject() : null;

		this.projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal() && (this.project == null || this.project != null && this.project.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "project");
		this.project = this.campaign != null ? this.campaign.getProject() : null;
	}

	@Override
	public void validate() {

		if (this.campaign != null && this.project != null) {
			boolean projectIsAvailable;

			projectIsAvailable = this.repository.findMyNotPublishedProjects(this.projectSquadId).stream().anyMatch(project -> project.getId() == this.project.getId());
			super.state(projectIsAvailable, "project", "spokesperson.acme.validation.project.not-found.message");
		}

	}

	@Override
	public void execute() {
		this.campaign.setProject(this.project);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		Collection<Project> myNotPublishedProjects;
		SelectChoices projects;
		Tuple tuple;

		myNotPublishedProjects = this.repository.findMyNotPublishedProjects(this.projectSquadId);

		projects = SelectChoices.from(myNotPublishedProjects, "ticker", this.project != null ? this.project : this.campaign.getProject());

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "spokesperson.identity.fullName", "project");
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		tuple.put("effort", this.campaign.getEffort());
		tuple.put("projects", projects);
	}

}
