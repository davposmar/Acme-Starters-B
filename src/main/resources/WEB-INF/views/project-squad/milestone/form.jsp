<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox readonly="true" code="project-squad.milestone.form.label.campaign" path="campaign.name"/>
	<acme:form-textbox code="project-squad.milestone.form.label.title" path="title"/>
	<acme:form-textarea code="project-squad.milestone.form.label.achievements" path="achievements"/>
	<acme:form-double code="project-squad.milestone.form.label.effort" path="effort"/>
	<acme:form-select code="project-squad.milestone.form.label.kind" path="kind" choices="${kinds}"/>
</acme:form>
