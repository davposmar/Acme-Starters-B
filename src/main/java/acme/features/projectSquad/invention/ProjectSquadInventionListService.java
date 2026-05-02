
package acme.features.projectSquad.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadInventionListService extends AbstractService<ProjectSquad, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadInventionRepository	repository;
	private Project							project;
	private Collection<Invention>			inventions;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findPorjectById(projectId);
		this.inventions = this.repository.findInventionsByProjectId(projectId);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "name", "startMoment", //
			"draftMode", "endMoment", "moreInfo", "description");
	}
}
