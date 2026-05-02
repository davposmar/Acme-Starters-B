/*
 * ManagerDashboardShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.forms.Dashboard;
import acme.realms.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository	repository;

	private Dashboard					dashboard;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Manager manager;

		manager = (Manager) super.getRequest().getPrincipal().getActiveRealm();

		Integer totalProjects;
		Double minEffort;
		Double maxEffort;
		Double averageEffort;
		Double deviationEffort;
		Double deviationProjects;

		totalProjects = this.repository.getTotalProjectsByManagerId(manager.getId());
		deviationProjects = this.repository.getProjectsDeviationByManagerId(manager.getId());
		minEffort = this.repository.getMinEffortByManagerId(manager.getId());
		maxEffort = this.repository.getMaxEffortByManagerId(manager.getId());
		averageEffort = this.repository.getAverageEffortByManagerId(manager.getId());
		deviationEffort = this.repository.getEffortDeviationByManagerId(manager.getId());

		this.dashboard = super.newObject(Dashboard.class);
		this.dashboard.setTotalProjects(totalProjects);
		this.dashboard.setDeviationProjects(deviationProjects);
		this.dashboard.setMinEffort(minEffort);
		this.dashboard.setMaxEffort(maxEffort);
		this.dashboard.setAverageEffort(averageEffort);
		this.dashboard.setDeviationEffort(deviationEffort);

	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.dashboard, //
			"totalProjects", "deviationProjects", "minEffort", // 
			"maxEffort", "averageEffort", //
			"deviationEffort");
	}

}
