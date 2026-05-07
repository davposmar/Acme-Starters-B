
package acme.features.projectSquad.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.realms.ProjectSquad;
import acme.realms.Spokesperson;

@Service
public class ProjectSquadSpokespersonShowService extends AbstractService<ProjectSquad, Spokesperson> {

	@Autowired
	private ProjectSquadSpokespersonRepository	repository;

	private Spokesperson						spokesperson;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.spokesperson = this.repository.findSpokespersonById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.spokesperson != null);
	}

	@Override
	public void unbind() {
		// Ajusta los campos según lo que tenga tu entidad Spokesperson
		super.unbindObject(this.spokesperson, "identity.name", "cv", "achievements", "licensed");
	}
}
