<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list  navigable="false">
    <acme:list-column code="any.member.list.label.name" path="projectSquad.identity.name" width="25%"/>
    <acme:list-column code="any.member.list.label.surname" path="projectSquad.identity.surname" width="25%"/>
    <acme:list-column code="any.member.list.label.email" path="projectSquad.identity.email" width="25%"/>
    <acme:list-column code="any.member.list.label.username" path="projectSquad.userAccount.username" width="25%"/>
</acme:list>
