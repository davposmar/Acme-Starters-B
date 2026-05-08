
package acme.entities.projects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidHeader;
import acme.constraints.ValidProject;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.manager.member.MemberRepository;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@ValidProject
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "ticker, draftMode"), @Index(columnList = "draftMode"), @Index(columnList = "ticker")
})
public class Project extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				title;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidText
	@Column
	private String				keyWords;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				kickOff;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				closeOut;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	@Mandatory
	@ValidNumber(min = 0)
	@Column
	private Double				totalActiveMonths;

	@Mandatory
	@ValidNumber(min = 0)
	@Column
	private Double				effort;

	// Derived attributes -----------------------------------------------------

	@Mandatory
	@Valid
	@Transient
	@Autowired
	private MemberRepository	memberRepository;


	public Double computeEffort(final long people) {

		//MemberRepository memberRepository = SpringHelper.getBean(MemberRepository.class)
		Double months = this.getTotalActiveMonths();
		//long people = this.memberRepository.countNumPeople(this.getId());
		return months / people;
	}

	public void updateEffortUsingComponentValues(final Double previousActiveMonths, final Double newActiveMonths, final long people) {
		this.setTotalActiveMonths(Math.max(this.totalActiveMonths - previousActiveMonths + newActiveMonths, 0.0));
		this.setEffort(this.computeEffort(people));
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager manager;

}
