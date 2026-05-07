
package acme.features.projectSquad.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.realms.Fundraiser;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadFundraiserShowService extends AbstractService<ProjectSquad, Fundraiser> {

	@Autowired
	private ProjectSquadFundraiserRepository	repository;

	private Fundraiser							fundraiser;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.fundraiser = this.repository.findFundraiserById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.fundraiser != null);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "identity.name", "bank", "statement", "agent");
	}
}
