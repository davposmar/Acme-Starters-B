<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.project.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="any.project.list.label.title" path="title" width="40%"/>
	<acme:list-column code="any.project.list.label.kickOff" path="kickOff" width="20%"/>
	<acme:list-column code="any.project.list.label.closeOut" path="closeOut" width="20%"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="manager.identity.fullName"/>
</acme:list>



