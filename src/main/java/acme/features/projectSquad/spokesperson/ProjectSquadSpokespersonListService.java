
package acme.features.projectSquad.spokesperson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;
import acme.realms.Spokesperson;

@Service
public class ProjectSquadSpokespersonListService extends AbstractService<ProjectSquad, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadSpokespersonRepository	repository;

	private Project								project;
	private int									projectId;
	private int									projectSquadId;
	private Collection<Spokesperson>			spokespersons;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(this.projectId);
		this.spokespersons = this.repository.findSpokespersonsNotInProject(this.projectId);
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
		super.unbindObjects(this.spokespersons, "identity.name", "cv", "achievements", "licensed");
	}
}
