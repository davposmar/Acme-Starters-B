/*
 * ProjectSquadSponsorshipListService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.projectSquad.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadSponsorshipListService extends AbstractService<ProjectSquad, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadSponsorshipRepository	repository;

	private Project								project;
	private int									projectId;
	private int									projectSquadId;
	private Collection<Sponsorship>				sponsorships;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findProjectById(this.projectId);
		this.sponsorships = this.repository.findSponsorshipsByProject(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && !this.project.getDraftMode() && this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId);

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorships, "ticker", "name", "description", "startMoment", "endMoment", "draftMode");
	}

}
