/*
 * ManagerDashboardRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(p) from Project p WHERE p.manager.id = :id")
	Integer getTotalProjectsByManagerId(Integer id);

	@Query("SELECT MIN(p.effort) from Project p WHERE p.manager.id = :id")
	Double getMinEffortByManagerId(Integer id);

	@Query("SELECT MAX(p.effort) from Project p WHERE p.manager.id = :id")
	Double getMaxEffortByManagerId(Integer id);

	@Query("SELECT AVG(p.effort) from Project p WHERE p.manager.id = :id")
	Double getAverageEffortByManagerId(Integer id);

	@Query("SELECT AVG(p.effort) from Project p WHERE p.manager.id != :id")
	Double getAverageEffortOfOtherManagers(Integer id);

	@Query("select avg(select count(p) from Project p where p.manager.id = m.id) from Manager m where m.id != :id")
	Double getAverageProjectsOfOtherManagers(Integer id);

	default Double getProjectsDeviationByManagerId(final Integer id) {
		Double myTotal;
		Double othersAverage;

		myTotal = this.getTotalProjectsByManagerId(id) * 1.0;
		othersAverage = this.getAverageProjectsOfOtherManagers(id);

		return myTotal - othersAverage;
	}

	default Double getEffortDeviationByManagerId(final Integer id) {
		Double avg;
		Double othersAvg;

		avg = this.getAverageEffortByManagerId(id);
		othersAvg = this.getAverageEffortOfOtherManagers(id);

		return othersAvg - avg;
	}

}
