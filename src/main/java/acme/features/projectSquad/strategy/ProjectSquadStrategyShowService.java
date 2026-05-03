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

package acme.features.projectSquad.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadStrategyShowService extends AbstractService<ProjectSquad, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadStrategyRepository	repository;

	private Strategy						strategy;
	private int								projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && (this.strategy.getDraftMode() == false || this.repository.isMemberOfTheProject(this.strategy.getProject().getId(), this.projectSquadId));

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "fundraiser.identity.fullName");
	}

}
