
package acme.features.projectSquad.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadProjectController extends AbstractController<ProjectSquad, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectSquadProjectListService.class);
		super.addBasicCommand("show", ProjectSquadProjectShowService.class);
		super.addBasicCommand("update", ProjectSquadProjectUpdateService.class);
		//super.addBasicCommand("delete", FundraiserStrategyDeleteService.class);
		//super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
