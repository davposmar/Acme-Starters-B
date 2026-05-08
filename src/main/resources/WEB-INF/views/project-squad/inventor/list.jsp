<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list navigable="false">
    <acme:list-column code="project-squad.inventor.list.label.name" path="identity.name" width="20%"/>
    <acme:list-column code="project-squad.inventor.list.label.bio" path="bio" width="20%"/>
    <acme:list-column code="project-squad.inventor.list.label.keyWords" path="keyWords" width="20%"/>
    <acme:list-column code="project-squad.inventor.list.label.licensed" path="licensed" width="20%"/>
</acme:list>
<jstl:if test="${isManager}">
	<acme:button code="project-squad.inventor.add" action="/project-squad/member/addInventor?projectId=${projectId}"/>
</jstl:if>