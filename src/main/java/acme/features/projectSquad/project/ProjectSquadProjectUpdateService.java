/*
 * SpokespersonCampaignUpdateService.java
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
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadProjectUpdateService extends AbstractService<ProjectSquad, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadProjectRepository	repository;

	private Project							project;

	private boolean							isProjectManager;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
		this.isProjectManager = this.project.getManager().isPrincipal();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "ticker", "ticker", "title", "keyWords", "description", "kickOff", "closeOut");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.project, "ticker", "title", "keyWords", "description", "kickOff", "closeOut", "draftMode", "manager.identity.fullName");
		tuple.put("effort", this.project.getEffort());
		super.unbindGlobal("isManager", this.isProjectManager);
	}

}
