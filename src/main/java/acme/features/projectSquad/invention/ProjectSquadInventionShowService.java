
package acme.features.projectSquad.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadInventionShowService extends AbstractService<ProjectSquad, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadInventionRepository	repository;

	private Invention						invention;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findinventionById(id);
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
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "inventor.identity.fullName");
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("cost", this.invention.getCost());
		super.unbindGlobal("isOwner", this.invention.getInventor().isPrincipal());
	}
}
