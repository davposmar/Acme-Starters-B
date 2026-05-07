
package acme.features.projectSquad.fundraiser;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadFundraiserListService extends AbstractService<ProjectSquad, Fundraiser> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadFundraiserRepository	repository;

	private Project								project;
	private int									projectId;
	private int									projectSquadId;
	private Collection<Fundraiser>				fundraisers;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(this.projectId);
		this.fundraisers = this.repository.findFundraisersNotInProject(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;
		int projectId;
		int projectSquadId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		project = this.repository.findProjectById(projectId);

		res = project != null && this.repository.isMemberOfTheProject(projectId, projectSquadId);

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.fundraisers, "identity.name", "bank", "statement", "agent");
		super.unbindGlobal("projectId", this.projectId);
		super.unbindGlobal("isManager", this.project.getManager().isPrincipal());
	}
}
