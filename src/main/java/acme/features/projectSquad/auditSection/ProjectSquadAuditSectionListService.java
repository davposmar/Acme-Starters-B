/*
 * ProjectSquadAuditSectionListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadAuditSectionListService extends AbstractService<ProjectSquad, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadAuditSectionRepository	repository;

	private AuditReport							auditReport;
	private Collection<AuditSection>			auditSections;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findAuditReportById(auditReportId);
		this.auditSections = this.repository.findAuditSectionsByAuditReportId(auditReportId);
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
		boolean showCreate;

		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
		showCreate = this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();
		super.unbindGlobal("auditReportId", this.auditReport.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
