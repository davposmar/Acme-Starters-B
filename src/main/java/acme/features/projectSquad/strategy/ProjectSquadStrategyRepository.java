
package acme.features.projectSquad.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.fundraising.Strategy;
import acme.entities.projects.Project;

@Repository
public interface ProjectSquadStrategyRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select s from Strategy s where s.id = :strategyId")
	Strategy findStrategyById(int strategyId);

	@Query("SELECT s FROM Strategy s WHERE s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProject(int projectId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);

}
