
package acme.features.projectSquad.part;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Part;
import acme.realms.Inventor;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadPartController extends AbstractController<ProjectSquad, Part> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectSquadPartListService.class);
		super.addBasicCommand("show", ProjectSquadPartShowService.class);
	}
}
