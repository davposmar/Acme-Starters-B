
package acme.features.projectSquad.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.ProjectSquad;

@Service
public class ProjectSquadProjectPublishService extends AbstractService<ProjectSquad, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectSquadProjectRepository	repository;

	private Project							project;
	private boolean							canInventionsBePublished;
	private boolean							canCampaignsBePublished;
	private boolean							canStrategiesBePublished;
	private long							numInventions;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
		this.canInventionsBePublished = this.repository.canInventionsBePublished(id);
		this.canCampaignsBePublished = this.repository.canCampaignsBePublished(id);
		this.canStrategiesBePublished = this.repository.canStrategiesBePublished(id);
		this.numInventions = this.repository.countInvetionsOfPorject(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "ticker", "title", "keyWords", "description", "kickOff", "closeOut");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
		final long MIN_INVENTIONS = 1L;
		{
			super.state(this.canInventionsBePublished, "*", "acme.validation.project.inventions.message");
			super.state(this.canCampaignsBePublished, "*", "acme.validation.project.campaigns.message");
			super.state(this.canStrategiesBePublished, "*", "acme.validation.project.strategies.message");
		}
		{
			Date startMoment, endMoment;
			boolean isValidInterval;
			boolean isStartFuture;
			boolean isEndFuture;

			startMoment = this.project.getKickOff();
			endMoment = this.project.getCloseOut();

			if (startMoment != null && endMoment != null) {
				isStartFuture = MomentHelper.isFuture(startMoment);
				isEndFuture = MomentHelper.isFuture(endMoment);
				isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
				super.state(isValidInterval, "*", "acme.validation.project.moments.message");
				//super.state(isStartFuture, "startMoment", "acme.validation.invention.startMoment.message");
				//super.state(isEndFuture, "endMoment", "acme.validation.invention.endMoment.message");
			}
		}
		{
			boolean hasMinInventions;

			hasMinInventions = this.numInventions >= MIN_INVENTIONS;
			super.state(hasMinInventions, "*", "acme.validation.project.invention.message");

		}
	}

	@Override
	public void execute() {

		this.repository.publishAllInventiosOfProject(this.project.getId());
		this.repository.publishAllCampaignsOfProject(this.project.getId());
		this.repository.publishAllStrategiesOfProject(this.project.getId());
		this.project.setDraftMode(false);
		this.repository.save(this.project);

	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.project, "ticker", "title", "keyWords", "description", "kickOff", "closeOut", "draftMode", "manager.identity.fullName");
		tuple.put("effort", this.project.getEffort());
		super.unbindGlobal("isManager", this.project.getManager().isPrincipal());
	}
}
