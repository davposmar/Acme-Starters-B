<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="manager.project.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="manager.project.form.label.title" path="title"/>
	<acme:form-textbox code="manager.project.form.label.keyWords" path="keyWords"/>
	<acme:form-textarea code="manager.project.form.label.description" path="description"/>
	<acme:form-moment code="manager.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="manager.project.form.label.closeOut" path="closeOut"/>
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
