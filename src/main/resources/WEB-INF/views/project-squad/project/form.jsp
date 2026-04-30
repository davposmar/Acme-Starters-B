<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${isManager == true && draftMode == true}">
			<acme:form-textbox code="project-squad.project.form.label.ticker" path="ticker"/>
			<acme:form-textbox code="project-squad.project.form.label.title" path="title"/>
			<acme:form-textbox code="project-squad.project.form.label.keyWords" path="keyWords"/>
			<acme:form-textarea code="project-squad.project.form.label.description" path="description"/>
			<acme:form-moment code="project-squad.project.form.label.kickOff" path="kickOff"/>
			<acme:form-moment code="project-squad.project.form.label.closeOut" path="closeOut"/>
		</jstl:when>
		<jstl:when test="${isManager == false}">
			<acme:form-textbox  readonly="true" code="project-squad.project.form.label.ticker" path="ticker"/>
			<acme:form-textbox readonly="true" code="project-squad.project.form.label.title" path="title"/>
			<acme:form-textbox readonly="true" code="project-squad.project.form.label.keyWords" path="keyWords"/>
			<acme:form-textarea readonly="true" code="project-squad.project.form.label.description" path="description"/>
			<acme:form-moment readonly="true" code="project-squad.project.form.label.kickOff" path="kickOff"/>
			<acme:form-moment readonly="true" code="project-squad.project.form.label.closeOut" path="closeOut"/>
		</jstl:when>
	</jstl:choose>
	<acme:form-double readonly="true" code="project-squad.project.form.label.effort" path="effort"/>
	<acme:form-textbox readonly="true" code="project-squad.project.form.label.manager" path="manager.identity.fullName"/>
	<acme:button code="project-squad.project.form.button.invention" action="/project-squad/invention/list?projectId=${id}"/>
	<acme:button code="project-squad.project.form.button.strategy" action="/project-squad/strategy/list?projectId=${id}"/>
	<acme:button code="project-squad.project.form.button.campaign" action="/project-squad/campaign/list?projectId=${id}"/>
	<acme:button code="project-squad.project.form.button.member" action="/project-squad/member/list?projectId=${id}"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && isManager == true  && draftMode == true}">
			<acme:submit code="project-squad.project.form.button.update" action="/project-squad/project/update"/>
			<acme:submit code="project-squad.project.form.button.delete" action="/project-squad/project/delete"/>
			<acme:submit code="project-squad.project.form.button.publish" action="/project-squad/project/publish"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
