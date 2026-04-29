
package acme.features.manager.project.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invention i WHERE i.project.id = :projectId")
	Long countInvetionsOfPorject(int projectId);

	@Query("select p from Project p where p.ticker = :ticker")
	Project findProjectByTicker(String ticker);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}
