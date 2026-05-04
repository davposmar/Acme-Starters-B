/*
 * ProjectSquadAuditSectionShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.projectSquad.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.entities.audits.SectionKind;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadAuditSectionShowService extends AbstractService<ProjectSquad, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadAuditSectionRepository	repository;

	private AuditSection						auditSection;
	private AuditReport							auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
		this.auditReport = this.auditSection.getAuditReport();
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		boolean isMemeber = this.auditReport != null && this.auditReport.getProject() != null && this.repository.isMemberOfTheProject(this.auditReport.getProject().getId(), projectSquadId);
		status = this.auditReport != null && (isMemeber || !this.auditReport.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("kinds", choices);
		tuple.put("draftMode", this.auditSection.getAuditReport().getDraftMode());
	}

}
