
package acme.features.projectSquad.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadPartListService extends AbstractService<ProjectSquad, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadPartRepository	repository;

	private Invention				invention;
	private Collection<Part>		parts;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInvenitonById(inventionId);
		this.parts = this.repository.findPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		boolean isMemeber = this.invention != null && this.invention.getProject() != null && this.repository.isMemberOfTheProject(this.invention.getProject().getId(), projectSquadId);
		status = this.invention != null && (isMemeber || !this.invention.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
		super.unbindGlobal("inventionId", this.invention.getId());
	}
}
