/*
 * AnyAuditorRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.entities.projects.Project;

@Repository
public interface ProjectSquadAuditReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.auditor.id = :auditorId")
	Collection<AuditReport> findAuditReportsByAuditorId(int auditorId);

	@Query("select ar from AuditReport ar where ar.id = :auditReportId")
	AuditReport findAuditReportById(int auditReportId);

	@Query("select a from AuditSection a where a.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);

	@Query("Select COUNT(a) from AuditSection a where a.auditReport.id = :auditReportId")
	Integer findCountAuditReportsByAuditReportId(int auditReportId);

	@Query("select p from Project p where p.ticker = :ticker and p.draftMode = false")
	Project findPublishedProjectByTicker(String ticker);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findPublishedProjects();

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select a from AuditReport a where a.project.id = :projectId")
	Collection<AuditReport> findAuditReportsByProjectId(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);
}
