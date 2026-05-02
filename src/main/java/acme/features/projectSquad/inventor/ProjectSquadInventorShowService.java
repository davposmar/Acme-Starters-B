/*
 * SponsorSponsorshipShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.projectSquad.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadInventorShowService extends AbstractService<ProjectSquad, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadInventorRepository	repository;

	private Inventor						inventor;
	private int								projectId;
	private int								projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.inventor = this.repository.findInventorById(id);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.inventor != null;
		//		&& this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId);

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "identity.name", "bio", "keyWords", "licensed");
	}

}
