
package acme.features.any.member;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Member;
import acme.entities.projects.Project;

@Repository
public interface AnyMemberRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select m from Member m where m.project.id = :projectId")
	Collection<Member> findMembersByProjectId(int projectId);

}
