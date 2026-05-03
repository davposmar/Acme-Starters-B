
package acme.features.projectSquad.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.fundraising.Strategy;
import acme.entities.fundraising.Tactic;

@Repository
public interface ProjectSquadTacticRepository extends AbstractRepository {

	@Query("Select s from Strategy s where s.id = :strategyId")
	Strategy findStrategyById(int strategyId);

	@Query("Select t from Tactic t where t.id = :id")
	Tactic findTacticById(int id);

	@Query("Select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticsByStrategyId(int strategyId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);
}
