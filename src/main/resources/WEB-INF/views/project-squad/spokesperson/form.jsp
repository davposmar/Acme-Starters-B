<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<%-- Datos de Identity --%>
	<acme:form-textbox code="project-squad.spokesperson.form.label.name" path="identity.name" readonly="true"/>
	
	<%-- Atributos específicos del modelo C02 [cite: 4] --%>
	<acme:form-textbox code="project-squad.spokesperson.form.label.cv" path="cv" readonly="true"/>
	<acme:form-textbox code="project-squad.spokesperson.form.label.achievements" path="achievements" readonly="true"/>
	<acme:form-textbox code="project-squad.spokesperson.form.label.licensed" path="licensed" readonly="true"/>	
	
	<%-- Botón de acción --%>
	<acme:submit code="project-squad.spokesperson.form.button.add" action="/project-squad/member/create?projectId=${param.projectId}&squadId=${id}"/>

</acme:form>