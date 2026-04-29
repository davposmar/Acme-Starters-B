
package acme.features.projectSquad.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadProjectListService extends AbstractService<ProjectSquad, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadProjectRepository	repository;

	private Collection<Project>				projects;

	private Boolean							isManager;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int porjectSquadId;
		//Integer managerId;

		porjectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.isManager = super.getRequest().getPrincipal().hasRealmOfType(Manager.class);

		// managerId = this.isManager ? super.getRequest().getPrincipal().getRealmOfType(Manager.class).getId() : null;
		//this.projects = this.repository.findProjectsByProjectSquadIdOrManagerId(porjectSquadId, managerId);
		this.projects = this.repository.findProjectsByProjectSquadId(porjectSquadId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, //
			"ticker", "title", "description", "kickOff", "closeOut", //
			"manager.identity.fullName");

		super.unbindGlobal("showCreate", this.isManager);
	}

}
