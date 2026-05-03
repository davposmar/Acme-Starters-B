<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<jstl:choose>
		<jstl:when test="${canEdit}">
			<acme:form-select readonly="false" code="inventor.invention.project.form.label.ticker" path="project" choices="${projects}"/>
			<acme:submit code="inventor.invention.project.form.button.assign" action="/inventor/invention/assign"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-textbox readonly="true" code="inventor.invention.project.form.label.ticker" path="proTick"/>
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:form-textbox readonly="${draftMode == false}" code="inventor.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox readonly="${draftMode == false}" code="inventor.invention.form.label.name" path="name"/>
	<acme:form-textarea readonly="${draftMode == false}" code="inventor.invention.form.label.description" path="description"/>
	<acme:form-moment readonly="${draftMode == false}" code="inventor.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment readonly="${draftMode == false}" code="inventor.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url readonly="${draftMode == false}" code="inventor.invention.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:form-double readonly="true" code="inventor.invention.form.label.monthsActive" path="monthsActive"/>
			<acme:form-double readonly="true" code="inventor.invention.form.label.cost" path="cost"/>
			<acme:form-textbox readonly="true" code="inventor.invention.form.label.inventor" path="inventor.identity.fullName"/>
			<acme:button code="inventor.invention.form.button.part" action="/inventor/part/list?inventionId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:form-double readonly="true" code="inventor.invention.form.label.monthsActive" path="monthsActive"/>
			<acme:form-double readonly="true" code="inventor.invention.form.label.cost" path="cost"/>
			<acme:button code="inventor.invention.form.button.part" action="/inventor/part/list?inventionId=${id}"/>
			<acme:submit code="inventor.invention.form.button.update" action="/inventor/invention/update"/>
			<acme:submit code="inventor.invention.form.button.delete" action="/inventor/invention/delete"/>
			<acme:submit code="inventor.invention.form.button.publish" action="/inventor/invention/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.invention.form.button.create" action="/inventor/invention/create"/>
		</jstl:when>
	</jstl:choose>
	
	
	
</acme:form>
