
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		//super.addBasicCommand("list", FundraiserStrategyListService.class);
		//super.addBasicCommand("show", FundraiserStrategyShowService.class);
		super.addBasicCommand("create", ManagerProjectCreateService.class);
		//super.addBasicCommand("delete", FundraiserStrategyDeleteService.class);
		//super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
