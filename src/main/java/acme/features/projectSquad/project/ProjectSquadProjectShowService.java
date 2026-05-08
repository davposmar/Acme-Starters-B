/*
 * SpokespersonCampaignShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.projectSquad.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadProjectShowService extends AbstractService<ProjectSquad, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadProjectRepository	repository;

	private Project							project;

	private boolean							isManager;

	private Integer							projectId;

	private Boolean							isProjectManager;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(this.projectId);
		this.isManager = super.getRequest().getPrincipal().hasRealmOfType(Manager.class);
		this.isProjectManager = this.project != null ? this.project.getManager().isPrincipal() : false;
	}

	@Override
	public void authorise() {
		boolean status;
		boolean isProjectSquad;
		boolean isInProject;
		Integer projectSquadId;

		var principal = super.getRequest().getPrincipal();

		isProjectSquad = principal.hasRealmOfType(ProjectSquad.class);
		projectSquadId = isProjectSquad ? principal.getRealmOfType(ProjectSquad.class).getId() : null;

		isInProject = projectSquadId != null && (this.isProjectManager || this.repository.isMemberOfTheProject(this.projectId, projectSquadId));
		status = this.project != null && (isInProject || !this.project.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.project, "ticker", "title", "keyWords", "description", "kickOff", "closeOut", "draftMode", "manager.identity.fullName");
		tuple.put("effort", this.project.getEffort());
		super.unbindGlobal("isManager", this.isProjectManager);
	}

}
