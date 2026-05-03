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
	

	<acme:form-textbox readonly="true" code="project-squad.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox readonly="true" code="project-squad.invention.form.label.name" path="name"/>
	<acme:form-textarea readonly="true" code="project-squad.invention.form.label.description" path="description"/>
	<acme:form-moment readonly="true" code="project-squad.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment readonly="true" code="project-squad.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url readonly="true" code="project-squad.invention.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double readonly="true" code="project-squad.invention.form.label.monthsActive" path="monthsActive"/>
	<acme:form-double readonly="true" code="project-squad.invention.form.label.cost" path="cost"/>
	<acme:button code="project-squad.invention.form.button.part" action="/project-squad/part/list?inventionId=${id}"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false && isOwner}">
			<acme:button code="project-squad.invention.form.button.edit" action="/inventor/invention/show?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'show' && isOwner}">
			<acme:submit code="project-squad.invention.form.button.unlink" action="/inventor/invention/create"/>
		</jstl:when>
	</jstl:choose>
	
	
	
</acme:form>
