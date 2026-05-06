
package acme.features.projectSquad.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Principal;
import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadMemberCreateService extends AbstractService<ProjectSquad, Member> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadMemberRepository	repository;

	private Member							member;
	private Project							project;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.member = super.newObject(Member.class);
		this.member.setProject(this.project);
	}

	@Override
	public void authorise() {
		boolean status = false;

		if (this.project != null) {
			Principal principal = super.getRequest().getPrincipal();
			int managerId = principal.getActiveRealm().getId();
			status = this.project.getManager().getId() == managerId && this.project.getDraftMode();
		}

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.member, "projectSquad");
	}

	@Override
	public void validate() {
		super.validateObject(this.member);

		if (this.member.getProjectSquad() != null) {
			boolean isAlreadyMember = this.repository.isMemberOfTheProject(this.project.getId(), this.member.getProjectSquad().getId());
			super.state(!isAlreadyMember, "projectSquad", "projectSquad.member.error.already-exists");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.member);
		int numberOfPeople = this.repository.findMembersByProjectId(this.project.getId()).size();

		double totalActiveMonths = this.project.getTotalActiveMonths();
		double newEffort = 0.0;

		if (numberOfPeople > 0)
			newEffort = totalActiveMonths / numberOfPeople;

		this.project.setEffort(newEffort);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.member, "projectSquad");
		Collection<ProjectSquad> squads = this.repository.findAllProjectSquads();
		super.getRequest().addData("projectSquads", squads);
	}

	@Override
	public void onSuccess() {
		;
	}
}
