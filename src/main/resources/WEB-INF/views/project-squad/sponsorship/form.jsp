<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox readonly="true" code="project-squad.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox readonly="true" code="project-squad.sponsorship.form.label.name" path="name"/>
	<acme:form-textarea readonly="true" code="project-squad.sponsorship.form.label.description" path="description"/>
	<acme:form-moment readonly="true" code="project-squad.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-moment readonly="true" code="project-squad.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-url readonly="true" code="project-squad.sponsorship.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double readonly="true" code="project-squad.sponsorship.form.label.monthsActive" path="monthsActive"/>
	<acme:form-double readonly="true" code="project-squad.sponsorship.form.label.totalMoney" path="totalMoney"/>
	<acme:form-textbox readonly="true" code="project-squad.sponsorship.form.label.project-squad.identity.fullName" path="sponsor.identity.fullName"/>
	
	<acme:button code="project-squad.sponsorship.form.button.donations" action="/project-squad/donation/list?sponsorshipId=${id}"/>	
			
</acme:form>
