
package acme.features.projectSquad.invention;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Invention;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadInventionController extends AbstractController<ProjectSquad, Invention> {
	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", ProjectSquadInventionListService.class);

		super.addBasicCommand("show", ProjectSquadInventionShowService.class);

		//super.addCustomCommand("publish", "update", InventorInventionPublishService.class);
	}
}
