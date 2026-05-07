
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.projects.Project;
import acme.features.manager.project.ManagerProjectRepository;

public class ProjectValidator extends AbstractValidator<ValidProject, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidProject annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Project project, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		final long MIN_INVENTIONS = 1L;

		if (project == null)
			result = true;
		else {
			{
				boolean uniqueProject;
				Project existingProject;
				existingProject = this.repository.findProjectByTicker(project.getTicker());
				uniqueProject = existingProject == null || existingProject.equals(project);

				super.state(context, uniqueProject, "ticker", "acme.validation.project.duplicated-ticker.message");
			}
			{
				boolean correctParts;
				boolean publicMode = !project.getDraftMode() && this.repository.countInvetionsOfPorject(project.getId()) >= MIN_INVENTIONS;
				correctParts = project.getDraftMode() || publicMode;

				super.state(context, correctParts, "*", "acme.validation.project.invention.message");
			}
			{
				boolean correctDates;
				if (project.getCloseOut() != null && project.getKickOff() != null) {
					correctDates = MomentHelper.isAfter(project.getCloseOut(), project.getKickOff());
					super.state(context, correctDates, "*", "acme.validation.project.moments.message");
				}

			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}
