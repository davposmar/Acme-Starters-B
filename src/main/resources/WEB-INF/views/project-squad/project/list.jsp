<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="projectSquad.project.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="projectSquad.project.list.label.title" path="title" width="40%"/>
	<acme:list-column code="projectSquad.project.list.label.draftMode" path="draftMode" width="20%"/>
	<acme:list-column code="projectSquad.project.list.label.kickOff" path="kickOff" width="20%"/>
	<acme:list-column code="projectSquad.project.list.label.closeOut" path="closeOut" width="20%"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="manager.identity.fullName"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="projectSquad.project.list.button.create" action="/manager/project/create"/>
</jstl:if>


