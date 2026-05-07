
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {

	@Autowired
	private AnyStrategyRepository	repository;

	private Collection<Strategy>	strategies;


	@Override
	public void load() {
		if (super.getRequest().hasData("projectId")) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.strategies = this.repository.findPublishedByProjectId(projectId);
		} else
			this.strategies = this.repository.findPublishedStrategies();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment", "endMoment", "fundraiser.identity.fullName");

		if (super.getRequest().hasData("projectId"))
			super.unbindGlobal("projectId", super.getRequest().getData("projectId", int.class));
	}

}
