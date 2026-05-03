
package acme.features.projectSquad.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadMemberListService extends AbstractService<ProjectSquad, Member> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadMemberRepository	repository;

	private Project							project;
	private int								projectId;
	private int								projectSquadId;
	private Collection<Member>				members;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {

		this.projectSquadId = super.getRequest().getPrincipal().getActiveRealm().getId();

		this.projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(this.projectId);
		this.members = this.repository.findMembersByProjectId(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && this.repository.isMemberOfTheProject(this.projectId, this.projectSquadId);
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.members, "projectSquad.identity.name", "projectSquad.identity.surname", "projectSquad.identity.email");
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		super.unbindGlobal("isManager", this.project.getManager().isPrincipal());
	}
}
