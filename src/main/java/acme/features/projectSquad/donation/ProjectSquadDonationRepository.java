
package acme.features.projectSquad.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface ProjectSquadDonationRepository extends AbstractRepository {

	@Query("Select ss from Sponsorship ss where ss.id = :sponsorshipId")
	Sponsorship findsponsorshipById(int sponsorshipId);

	@Query("Select d from Donation d where d.id = :id")
	Donation findDonationById(int id);

	@Query("Select d from Donation d where d.sponsorship.id = :sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("SELECT  COUNT(m) > 0 FROM Member m  WHERE m.project.id = :projectId AND m.projectSquad.id = :projectSquadId ")
	boolean isMemberOfTheProject(Integer projectId, Integer projectSquadId);
}
