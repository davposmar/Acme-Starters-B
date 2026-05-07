<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<jstl:choose>
		 <jstl:when test="${isInventor == true}">
		 	<acme:form-select readonly="false" code="project-squad.member.inventor.form.label.username" path="account" choices="${accounts}"/>
			<acme:submit code="project-squad.member.inventor.add" action="/project-squad/member/addInventor?projectId=${projectId}" />
		</jstl:when>
		<jstl:when test="${isSpokesperson == true}">
		 	<acme:form-select readonly="false" code="project-squad.member.spokesperson.form.label.username" path="account" choices="${accounts}"/>
			<acme:submit code="project-squad.member.spokesperson.add" action="/project-squad/member/addSpokesperson?projectId=${projectId}" />
		</jstl:when>
	</jstl:choose>

</acme:form>