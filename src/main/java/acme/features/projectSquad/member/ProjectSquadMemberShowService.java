
package acme.features.projectSquad.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadMemberShowService extends AbstractService<ProjectSquad, Member> {

	@Autowired
	private ProjectSquadMemberRepository	repository;

	private Member							virtualMember;


	@Override
	public void load() {
		// 1. Cogemos el ID de la persona que has pinchado en la lista
		int squadId = super.getRequest().getData("squadId", int.class);
		ProjectSquad candidato = this.repository.findProjectSquadById(squadId);

		// 2. Creamos un miembro fantasma para contentar a tu controlador
		this.virtualMember = new Member();
		this.virtualMember.setProjectSquad(candidato);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true); // Cualquiera puede ver esta ficha
	}

	@Override
	public void unbind() {
		// 3. Mandamos los datos a la vista
		super.unbindObject(this.virtualMember, "projectSquad.userAccount.username", "projectSquad.userAccount.identity.name", "projectSquad.userAccount.identity.surname", "projectSquad.bio");

		// 4. Mandamos los IDs para que el botón de añadir sepa qué hacer
		int projectId = super.getRequest().getData("projectId", int.class);
		super.unbindGlobal("projectId", projectId);
		super.unbindGlobal("squadId", this.virtualMember.getProjectSquad().getId());
	}
}
