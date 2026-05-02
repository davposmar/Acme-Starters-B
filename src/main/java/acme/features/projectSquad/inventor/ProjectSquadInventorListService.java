
package acme.features.projectSquad.inventor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadInventorListService extends AbstractService<ProjectSquad, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadInventorRepository	repository;

	private Project							project;
	private int								projectId;
	private int								projectSquadId;
	private Collection<Inventor>			inventors;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findProjectById(this.projectId);
		this.inventors = this.repository.findInventorsNotInProject(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId);

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventors, "identity.name", "bio", "keyWords", "licensed");
		super.unbindGlobal("projectId", this.projectId);
	}
}
