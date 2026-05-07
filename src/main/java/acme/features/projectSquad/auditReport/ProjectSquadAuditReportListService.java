/*
 * ProjectSquadAuditReportListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadAuditReportListService extends AbstractService<ProjectSquad, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadAuditReportRepository	repository;
	private int									projectId;
	private int									projectSquadId;
	private Project								project;
	private Collection<AuditReport>				auditReports;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findProjectById(this.projectId);
		this.auditReports = this.repository.findAuditReportsByProjectId(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && !this.project.getDraftMode();

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, //
			"ticker", "name", "description", "startMoment", "endMoment",//
			"auditor.identity.fullName");
	}

}
