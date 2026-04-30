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

package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipAssignService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;
	private String							projectTicker;
	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
		this.project = this.sponsorship.getProject();
		this.projectTicker = this.project != null ? this.project.getTicker() : "";
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && !this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		String projectTicker;

		projectTicker = super.getRequest().getData("projectTicker", String.class);
		this.projectTicker = projectTicker != null ? projectTicker.trim() : null;
		this.project = null;
	}

	@Override
	public void validate() {
		boolean isEmpty;

		isEmpty = this.projectTicker == null || this.projectTicker.isBlank();

		super.state(!isEmpty, "projectTicker", "acme.validation.project.required.message");
		if (!isEmpty) {
			this.project = this.repository.findPublishedProjectByTicker(this.projectTicker);
			super.state(this.project != null, "projectTicker", "acme.validation.project.not-found.message");
		}
	}

	@Override
	public void execute() {
		this.sponsorship.setProject(this.project);
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		Collection<Project> publishedProjects;
		SelectChoices projects;
		Project selectedProject;
		Tuple tuple;

		publishedProjects = this.repository.findPublishedProjects();
		selectedProject = this.project != null ? this.project : (this.projectTicker == null || this.projectTicker.isBlank() ? null : this.repository.findPublishedProjectByTicker(this.projectTicker));
		projects = SelectChoices.from(publishedProjects, "ticker", selectedProject);

		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "sponsor.identity.fullName");
		tuple.put("monthsActive", this.sponsorship.getMonthsActive());
		tuple.put("totalMoney", this.sponsorship.getTotalMoney());
		tuple.put("projectTicker", this.projectTicker);
		tuple.put("projects", projects);
	}

}
