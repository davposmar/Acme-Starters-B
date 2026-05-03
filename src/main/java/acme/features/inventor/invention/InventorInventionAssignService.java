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

package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;

@Service
public class InventorInventionAssignService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;
	private Project				project;
	private int					projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findinventionById(id);

		this.project = this.invention != null ? this.invention.getProject() : null;

		this.projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal() && (this.project == null || this.project != null && this.project.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "project");
		this.project = this.invention != null ? this.invention.getProject() : null;
	}

	@Override
	public void validate() {

		if (this.invention != null && this.project != null) {
			boolean projectIsAvailable;

			projectIsAvailable = this.repository.findMyNotPublishedProjects(this.projectSquadId).stream().anyMatch(project -> project.getId() == this.project.getId());
			super.state(projectIsAvailable, "project", "inventor.acme.validation.project.not-found.message");
		}

	}

	@Override
	public void execute() {
		this.invention.setProject(this.project);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Collection<Project> myNotPublishedProjects;
		SelectChoices projects;
		Tuple tuple;

		myNotPublishedProjects = this.repository.findMyNotPublishedProjects(this.projectSquadId);

		projects = SelectChoices.from(myNotPublishedProjects, "ticker", this.project != null ? this.project : this.invention.getProject());

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "inventor.identity.fullName", "project");
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("cost", this.invention.getCost());
		tuple.put("projects", projects);
	}

}
