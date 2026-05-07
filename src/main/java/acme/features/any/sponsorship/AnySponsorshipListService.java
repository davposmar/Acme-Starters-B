/*
 * SponsorSponsorshipListService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository	repository;

	private Collection<Sponsorship>		sponsorships;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		// Nuestro escudo anti-500
		if (super.getRequest().hasData("projectId")) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.sponsorships = this.repository.findPublishedSponsorshipsByProjectId(projectId);
		} else
			// Si no vienes de un proyecto, mostramos todos los sponsorships publicados
			this.sponsorships = this.repository.findPublishedSponsorships();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorships, "ticker", "name", "description", "startMoment", "endMoment", "sponsor.identity.fullName");

		// Rebotamos el projectId al JSP si es que existe
		if (super.getRequest().hasData("projectId"))
			super.unbindGlobal("projectId", super.getRequest().getData("projectId", int.class));
	}

}
