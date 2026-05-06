
package acme.features.projectSquad.member;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
