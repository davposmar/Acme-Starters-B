
package acme.features.any.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.entities.projects.Project;

@Service
public class AnyMemberListService extends AbstractService<Any, Member> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyMemberRepository	repository;

	private Project				project;
	private int					projectId;
	private Collection<Member>	members;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {

		this.projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(this.projectId);
		this.members = this.repository.findMembersByProjectId(this.projectId);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.project != null && this.project.getDraftMode() == false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.members, "projectSquad.identity.name", "projectSquad.identity.surname", "projectSquad.identity.email");
	}
}
