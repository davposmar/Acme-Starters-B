
package acme.features.projectSquad.member;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Member;
import acme.realms.ProjectSquad;

@Controller
public class ProjectSquadMemberController extends AbstractController<ProjectSquad, Member> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectSquadMemberListService.class);
		super.addBasicCommand("create", ProjectSquadMemberCreateService.class);
		super.addBasicCommand("show", ProjectSquadMemberShowService.class);
	}

}
