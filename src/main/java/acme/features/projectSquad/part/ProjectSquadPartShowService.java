
package acme.features.projectSquad.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.entities.inventions.PartKind;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadPartShowService extends AbstractService<ProjectSquad, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadPartRepository	repository;

	private Part						part;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		boolean isMemeber = this.part != null && this.part.getInvention() != null && this.part.getInvention().getProject() != null && this.repository.isMemberOfTheProject(this.part.getInvention().getProject().getId(), projectSquadId);
		status = this.part != null && this.part.getInvention() != null && (isMemeber || !this.part.getInvention().getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(PartKind.class, this.part.getKind());

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		tuple.put("kinds", choices);
		tuple.put("draftMode", this.part.getInvention().getDraftMode());
	}

}
