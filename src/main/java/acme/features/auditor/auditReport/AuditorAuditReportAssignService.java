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
public class AuditorAuditReportAssignService extends AbstractService<Auditor, AuditReport> {

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

		status = this.auditReport != null && !this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "project");
		this.project = this.auditReport != null ? this.auditReport.getProject() : null;
	}

	@Override
	public void validate() {
		//super.validateObject(this.auditReport);

		if (this.auditReport != null && this.project != null)
			super.state(this.repository.findPublishedProjectByTicker(this.project.getTicker()) != null, "project", "acme.validation.project.not-found.message");
	}

	@Override
	public void execute() {
		this.auditReport.setProject(this.project);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		Collection<Project> publishedProjects;
		SelectChoices projects;
		Tuple tuple;

		publishedProjects = this.repository.findPublishedProjects();
		projects = SelectChoices.from(publishedProjects, "ticker", this.project != null ? this.project : this.auditReport.getProject());

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor.identity.fullName", "project");
		tuple.put("monthsActive", this.auditReport.getMonthsActive());
		tuple.put("hours", this.auditReport.getHours());
		tuple.put("projects", projects);
	}

}
