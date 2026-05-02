
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.entities.projects.Project;
import acme.realms.Manager;
import acme.realms.ProjectSquad;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		Manager manager;

		manager = (Manager) super.getRequest().getPrincipal().getActiveRealm();

		this.project = super.newObject(Project.class);
		this.project.setDraftMode(true);
		this.project.setEffort(0.0);
		this.project.setTotalActiveMonths(0.0);
		this.project.setManager(manager);

	}

	@Override
	public void authorise() {
		super.setAuthorised(super.getRequest().getPrincipal().hasRealmOfType(ProjectSquad.class));
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "ticker", "title", "keyWords", "description", "kickOff", "closeOut");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		ProjectSquad projectSquad;

		projectSquad = (ProjectSquad) super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class);
		this.repository.save(this.project);
		Member member = super.newObject(Member.class);
		member.setProject(this.project);
		member.setProjectSquad(projectSquad);
		this.repository.save(member);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.project, "ticker", "title", "keyWords", "description", "kickOff", "closeOut", "draftMode", "manager.identity.fullName");
		tuple.put("effort", this.project.getEffort());
	}
}
