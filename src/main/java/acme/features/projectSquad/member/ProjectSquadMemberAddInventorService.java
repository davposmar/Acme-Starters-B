
package acme.features.projectSquad.member;

import java.util.Collection;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Member;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadMemberAddInventorService extends AbstractService<ProjectSquad, Member> {

	@Autowired
	private ProjectSquadMemberRepository	repository;
	private Member							member;
	private Project							project;
	private UserAccount						account;


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(projectId);

		this.member = super.newObject(Member.class);
		this.member.setProject(this.project);
		this.account = null;

	}

	@Override
	public void authorise() {
		boolean isManager = this.project != null && this.project.getManager().isPrincipal();
		boolean result = isManager;
		super.setAuthorised(result);
	}

	@Override
	public void bind() {
		super.bindObject(this.member);
		Entry<String, Object> accountEntry = this.getRequest().getDataEntries().stream().filter(entry -> entry.getKey().equals("account")).findFirst().orElse(null);
		if (accountEntry != null) {
			int userId = Integer.parseInt((String) accountEntry.getValue());
			this.account = this.repository.findOneUserAccountById(userId);
		}

	}

	@Override
	public void validate() {
		boolean isInventorRole = this.account != null && this.account.hasRealmOfType(Inventor.class);
		super.state(isInventorRole, "*", "projectSquad.member.error.not-inventor");
		if (isInventorRole) {
			int projectSquadId = this.account.getRealmOfType(ProjectSquad.class).getId();
			boolean isInProject = this.repository.isMemberOfTheProject(this.project.getId(), projectSquadId);
			super.state(!isInProject, "*", "projectSquad.member.error.already-exists");
		}
	}

	@Override
	public void execute() {
		ProjectSquad projectSquad = this.account.getRealmOfType(ProjectSquad.class);
		Long numPeople = this.repository.countNumPeople(this.project.getId());
		this.project.updateEffortUsingComponentValues(0.0, 0.0, numPeople + 1);
		this.repository.save(this.project);
		this.member.setProjectSquad(projectSquad);
		this.repository.save(this.member);
	}

	@Override
	public void unbind() {
		Collection<UserAccount> inventorsNotInProject;
		SelectChoices accounts;
		Tuple tuple;
		int projectId = super.getRequest().getData("projectId", int.class);

		inventorsNotInProject = this.repository.findAccountOfInventorsNotInProject(projectId);

		accounts = SelectChoices.from(inventorsNotInProject, "username", null);
		tuple = super.unbindObject(this.member);
		tuple.put("accounts", accounts);
		tuple.put("account", null);
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("isInventor", true);

	}

}
