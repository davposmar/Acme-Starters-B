
package acme.features.projectSquad.donation;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Donation;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadDonationController extends AbstractController<ProjectSquad, Donation> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectSquadDonationListService.class);
		super.addBasicCommand("show", ProjectSquadDonationShowService.class);
	}
}
