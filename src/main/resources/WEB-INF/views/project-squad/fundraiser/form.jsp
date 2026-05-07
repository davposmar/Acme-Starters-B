<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<%-- Datos de Identity (Nombre y Apellidos suelen estar aquí en el framework) --%>
	<acme:form-textbox code="project-squad.fundraiser.form.label.name" path="identity.name" readonly="true"/>
	
	<%-- Atributos específicos del modelo C03 [cite: 29] --%>
	<acme:form-textbox code="project-squad.fundraiser.form.label.bank" path="bank" readonly="true"/>
	<acme:form-textbox code="project-squad.fundraiser.form.label.statement" path="statement" readonly="true"/>
	<acme:form-textbox code="project-squad.fundraiser.form.label.agent" path="agent" readonly="true"/>	
	
	<%-- Botón de acción: Usamos el ID del proyecto que viene por URL y el ID del fundraiser --%>
	<acme:submit code="project-squad.fundraiser.form.button.add" action="/project-squad/member/create?projectId=${projectId}&squadId=${id}"/>

</acme:form>