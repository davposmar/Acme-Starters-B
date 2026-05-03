
package acme.features.projectSquad.milestone;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.campaigns.Milestone;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadMilestoneController extends AbstractController<ProjectSquad, Milestone> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectSquadMilestoneListService.class);
		super.addBasicCommand("show", ProjectSquadMilestoneShowService.class);
	}
}
