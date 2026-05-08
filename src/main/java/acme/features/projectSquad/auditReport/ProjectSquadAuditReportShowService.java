/*
 * AnyAuditorShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.projectSquad.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadAuditReportShowService extends AbstractService<ProjectSquad, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadAuditReportRepository	repository;

	private AuditReport							auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
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
		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor.identity.fullName", "project");
		tuple.put("monthsActive", this.auditReport.getMonthsActive());
		tuple.put("hours", this.auditReport.getHours());
	}

}
