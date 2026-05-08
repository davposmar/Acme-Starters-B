
package acme.features.projectSquad.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadDonationListService extends AbstractService<ProjectSquad, Donation> {

	@Autowired
	private ProjectSquadDonationRepository	repository;

	private Sponsorship						sponsorship;
	private Collection<Donation>			donations;


	@Override
	public void load() {
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.repository.findsponsorshipById(sponsorshipId);
		this.donations = this.repository.findDonationsBySponsorshipId(sponsorshipId);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		status = this.sponsorship != null && (this.repository.isMemberOfTheProject(this.sponsorship.getProject().getId(), projectSquadId) || !this.sponsorship.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.donations, "name", "notes", "money", "kind");
		super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
	}
}
