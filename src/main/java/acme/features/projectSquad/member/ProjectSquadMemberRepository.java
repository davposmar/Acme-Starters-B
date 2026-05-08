
package acme.features.projectSquad.member;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Member;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Repository
public interface ProjectSquadMemberRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select m from Member m where m.project.id = :projectId")
	Collection<Member> findMembersByProjectId(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

	@Query("select ps from ProjectSquad ps")
	Collection<ProjectSquad> findAllProjectSquads();

	@Query("select m.projectSquad from Member m where m.project.id = ?1")
	Collection<ProjectSquad> findAssignedSquadsByProjectId(int projectId);

	@Query("select ps from ProjectSquad ps where ps.id = ?1")
	ProjectSquad findProjectSquadById(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("SELECT i.userAccount FROM Inventor i WHERE i.userAccount NOT IN (SELECT m.projectSquad.userAccount FROM Member m WHERE m.project.id = :projectId)")
	Collection<UserAccount> findAccountOfInventorsNotInProject(int projectId);

	@Query("SELECT s.userAccount FROM Spokesperson s WHERE s.userAccount NOT IN (SELECT m.projectSquad.userAccount FROM Member m WHERE m.project.id = :projectId)")
	Collection<UserAccount> findAccountOfSpokespersonNotInProject(int projectId);

	@Query("SELECT f.userAccount FROM Fundraiser f WHERE f.userAccount NOT IN (SELECT m.projectSquad.userAccount FROM Member m WHERE m.project.id = :projectId)")
	Collection<UserAccount> findAccountOfFundraisersNotInProject(int projectId);

	@Query("select count(m) from Member m where m.project.id = :projectId")
	Long countNumPeople(int projectId);
	/*
	 * @Query("SELECT i.userAccount FROM Inventor i WHERE i.userAccount NOT IN (SELECT m.projectSquad.userAccount FROM Member m WHERE m.project.id = :projectId)")
	 * Collection<UserAccount> findAccountOfNotInProject(int projectId);
	 */

}
