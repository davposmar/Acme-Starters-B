/*
 * ProjectSquadSponsorshipShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadSponsorshipShowService extends AbstractService<ProjectSquad, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadSponsorshipRepository	repository;

	private Sponsorship							sponsorship;
	private int									projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && (this.sponsorship.getDraftMode() == false || this.repository.isMemberOfTheProject(this.sponsorship.getProject().getId(), this.projectSquadId));

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices projects = null;
		Tuple tuple;

		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "sponsor.identity.fullName");
		tuple.put("monthsActive", this.sponsorship.getMonthsActive());
		tuple.put("totalMoney", this.sponsorship.getTotalMoney());
		tuple.put("sponsorId", this.sponsorship.getSponsor().getId());

	}
}
