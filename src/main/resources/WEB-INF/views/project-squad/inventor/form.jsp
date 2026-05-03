<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="project-squad.inventor.form.label.name" path="identity.name" readonly="true"/>
	<acme:form-textbox code="project-squad.inventor.form.label.bio" path="bio" readonly="true"/>
	<acme:form-textbox code="project-squad.inventor.form.label.keyWords" path="keyWords" readonly="true"/>
	<acme:form-textbox code="project-squad.inventor.form.label.licensed" path="licensed" readonly="true"/>	
	
	<acme:submit code="project-squad.inventor.form.button.add" action="/project-squad/inventor/add?inventorId=${inventorId}"/>

</acme:form>

