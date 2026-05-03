
package acme.features.projectSquad.tactic;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.fundraising.Tactic;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadTacticController extends AbstractController<ProjectSquad, Tactic> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectSquadTacticListService.class);
		super.addBasicCommand("show", ProjectSquadTacticShowService.class);
	}
}
