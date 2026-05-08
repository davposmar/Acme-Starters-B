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

package acme.features.projectSquad.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadCampaignShowService extends AbstractService<ProjectSquad, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadCampaignRepository	repository;

	private Campaign						campaign;
	private int								projectSquadId;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();

	}

	@Override
	public void authorise() {
		boolean status;

		boolean isMemeber = this.campaign != null && this.campaign.getProject() != null && this.repository.isMemberOfTheProject(this.campaign.getProject().getId(), this.projectSquadId);
		status = this.campaign != null && (this.campaign.getDraftMode() == false || isMemeber);

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "spokesperson.identity.fullName");
		super.unbindGlobal("isOwner", this.campaign.getSpokesperson().isPrincipal());
	}

}
