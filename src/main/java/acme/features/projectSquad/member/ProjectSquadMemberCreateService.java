
package acme.features.projectSquad.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;
import acme.realms.Spokesperson;

@Service
public class ProjectSquadMemberCreateService extends AbstractService<ProjectSquad, Member> {

	@Autowired
	private ProjectSquadMemberRepository	repository;

	private Member							member;
	private Project							project;
	private ProjectSquad					squad;


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		int squadId = super.getRequest().getData("squadId", int.class);

		this.project = this.repository.findProjectById(projectId);
		this.squad = this.repository.findProjectSquadById(squadId);

		this.member = super.newObject(Member.class);
		this.member.setProject(this.project);
		this.member.setProjectSquad(this.squad);
	}

	@Override
	public void authorise() {
		boolean isInventorRole = this.squad != null && this.squad.getUserAccount().hasRealmOfType(Inventor.class);
		boolean isFundraiserRole = this.squad != null && this.squad.getUserAccount().hasRealmOfType(Fundraiser.class);
		boolean isSpokesperson = this.squad != null && this.squad.getUserAccount().hasRealmOfType(Spokesperson.class);
		boolean canBeMember = isInventorRole || isFundraiserRole || isSpokesperson;
		boolean isManager = this.project != null && this.project.getManager().isPrincipal();
		boolean result = isManager && canBeMember;
		super.setAuthorised(result);
	}

	@Override
	public void bind() {
		;
	}

	@Override
	public void validate() {
		super.validateObject(this.member);
		if (this.squad != null) {
			boolean isAlreadyMember = this.repository.isMemberOfTheProject(this.project.getId(), this.squad.getId());
			super.state(!isAlreadyMember, "projectSquad", "projectSquad.member.error.already-exists");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.member);
	}

	@Override
	public void unbind() {
		// 1. Pasamos los datos básicos del miembro/squad
		super.unbindObject(this.member, "projectSquad.userAccount.username", "projectSquad.userAccount.identity.name", "projectSquad.userAccount.identity.surname");

		// 2. DETECTAMOS EL ROL DINÁMICAMENTE
		// Miramos qué tipo de perfil tiene el usuario para mostrarlo en el JSP
		String roleName = "";
		if (this.squad.getUserAccount().hasRealmOfType(Inventor.class))
			roleName = "Inventor";
		else if (this.squad.getUserAccount().hasRealmOfType(Fundraiser.class))
			roleName = "Fundraiser";
		else if (this.squad.getUserAccount().hasRealmOfType(Spokesperson.class))
			roleName = "Spokesperson";

		// 3. Pasamos el nombre del rol y los IDs como globals
		super.unbindGlobal("roleName", roleName);
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("squadId", this.squad.getId());
	}
}
