
package acme.features.projectSquad.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadInventionUnlinkService extends AbstractService<ProjectSquad, Invention> {

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

		status = this.invention != null && this.invention.getInventor().isPrincipal() && this.invention.getProject() != null && this.invention.getProject().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		int id = super.getRequest().getData("id", int.class);

		Invention previousInvention = this.repository.findinventionById(id);
		Project project = previousInvention.getProject();

		if (project != null) {
			long numPeople = this.repository.countNumPeople(project.getId());
			project.updateEffortUsingComponentValues(previousInvention.getMonthsActive(), 0.0, numPeople);
			this.repository.save(project);
		}
		previousInvention.setProject(null);
		this.repository.save(previousInvention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "inventor.identity.fullName");
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("cost", this.invention.getCost());
	}
}
