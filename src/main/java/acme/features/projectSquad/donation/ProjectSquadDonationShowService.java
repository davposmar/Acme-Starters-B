
package acme.features.projectSquad.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadDonationShowService extends AbstractService<ProjectSquad, Donation> {

	@Autowired
	private ProjectSquadDonationRepository	repository;

	private Donation						donation;
	private Sponsorship						sponsorship;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);
		this.sponsorship = this.donation.getSponsorship();
	}

	@Override
	public void authorise() {
		boolean status;
		int projectSquadId = super.getRequest().getPrincipal().getRealmOfType(ProjectSquad.class).getId();

		status = this.donation != null && (this.repository.isMemberOfTheProject(this.sponsorship.getProject().getId(), projectSquadId) || !this.donation.getSponsorship().getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());

		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("kinds", choices);
		tuple.put("draftMode", this.donation.getSponsorship().getDraftMode());
	}
}
