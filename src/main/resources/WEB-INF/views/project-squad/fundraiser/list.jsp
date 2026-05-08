<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list navigable="false">
    <acme:list-column code="project-squad.fundraiser.list.label.name" path="identity.name" width="20%"/>
    <acme:list-column code="project-squad.fundraiser.list.label.bank" path="bank" width="20%"/>
    <acme:list-column code="project-squad.fundraiser.list.label.statement" path="statement" width="20%"/>
    <acme:list-column code="project-squad.fundraiser.list.label.agent" path="agent" width="20%"/>
</acme:list>
<jstl:if test="${isManager}">
	<acme:button code="project-squad.fundraiser.add" action="/project-squad/member/addFundraiser?projectId=${projectId}"/>
</jstl:if>