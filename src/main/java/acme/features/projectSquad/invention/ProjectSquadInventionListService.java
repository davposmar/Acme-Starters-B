
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
	private int								projectId;
	private int								projectSquadId;
	private Collection<Invention>			inventions;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId);

		super.setAuthorised(res);
	}

	@Override
	public void load() {
		this.projectId = super.getRequest().getData("projectId", int.class);
		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findPorjectById(this.projectId);
		this.inventions = this.repository.findInventionsByProjectId(this.projectId);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "name", "startMoment", //
			"draftMode", "endMoment", "moreInfo", "description");
	}
}
