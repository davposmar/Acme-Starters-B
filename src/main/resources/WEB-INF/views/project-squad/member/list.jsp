<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox readonly="true" code="project-squad.member.list.label.projectTicker" path="projectTicker"/>
    <acme:form-textbox readonly="true" code="project-squad.member.list.label.projectTitle" path="projectTitle"/>
</acme:form>

<acme:list>
    <acme:list-column code="project-squad.member.list.label.name" path="projectSquad.identity.name" width="33%"/>
    <acme:list-column code="project-squad.member.list.label.surname" path="projectSquad.identity.surname" width="33%"/>
    <acme:list-column code="project-squad.member.list.label.email" path="projectSquad.identity.email" width="34%"/>
    <acme:list-hidden path="project.id"/>
</acme:list>
