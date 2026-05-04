
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;

@Service
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;
	private Project				project;
	private int					projectSquadId;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findinventionById(id);

		this.projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		this.project = this.invention != null ? this.invention.getProject() : null;
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && (this.invention.getInventor().isPrincipal() || !this.invention.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices projects = null;
		if (this.project == null || this.project != null && this.project.getDraftMode()) {
			Collection<Project> availableProjects = this.repository.findMyNotPublishedProjects(this.projectSquadId);
			projects = SelectChoices.from(availableProjects, "ticker", this.project != null ? this.project : this.invention.getProject());
		}
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "inventor.identity.fullName", "project");
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("cost", this.invention.getCost());
		if (this.project == null || this.project != null && this.project.getDraftMode()) {
			tuple.put("projects", projects);
			tuple.put("canEdit", true);
		} else {
			tuple.put("canEdit", false);
			tuple.put("proTick", this.project.getTicker());
		}

	}
}
