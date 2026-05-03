<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="project-squad.part.form.label.name" path="name"/>
	<acme:form-textarea code="project-squad.part.form.label.description" path="description"/>
	<acme:form-money code="project-squad.part.form.label.cost" path="cost"/>
	<acme:form-select code="project-squad.part.form.label.kind" path="kind" choices= "${kinds}"/>		
</acme:form>

