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

package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.projects.Project;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportShowService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;
	private Project							project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);

		this.project = this.auditReport != null ? this.auditReport.getProject() : null;
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && (this.auditReport.getAuditor().isPrincipal() || !this.auditReport.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices projects = null;
		if (!this.auditReport.getDraftMode()) {
			Collection<Project> publishedProjects;

			publishedProjects = this.repository.findPublishedProjects();
			projects = SelectChoices.from(publishedProjects, "ticker", this.project != null ? this.project : this.auditReport.getProject());
		}

		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor.identity.fullName", "project");
		tuple.put("monthsActive", this.auditReport.getMonthsActive());
		tuple.put("hours", this.auditReport.getHours());

		if (!this.auditReport.getDraftMode())
			tuple.put("projects", projects);
	}

}
