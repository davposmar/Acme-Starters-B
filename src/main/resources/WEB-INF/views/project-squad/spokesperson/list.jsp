<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list navigable="false">
    <acme:list-column code="project-squad.spokesperson.list.label.name" path="identity.name" width="20%"/>
    <acme:list-column code="project-squad.spokesperson.list.label.cv" path="cv" width="20%"/>
    <acme:list-column code="project-squad.spokesperson.list.label.achievements" path="achievements" width="20%"/>
    <acme:list-column code="project-squad.spokesperson.list.label.licensed" path="licensed" width="20%"/>
    <acme:list-hidden path="id"/>
</acme:list>
<jstl:if test="${isManager}">
	<acme:button code="project-squad.spokesperson.add" action="/project-squad/member/addSpokesperson?projectId=${projectId}"/>
</jstl:if>