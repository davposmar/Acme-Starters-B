<%@page language="java"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<input type="hidden" name="projectId" value="${projectId}" />
	<input type="hidden" name="squadId" value="${squadId}" />

	<h3>Confirmar adición al proyecto</h3>

	<%-- Solo los datos reales del tío, nada de roles inventados --%>
	<acme:form-textbox code="projectSquad.username" path="projectSquad.userAccount.username" readonly="true" />
	<acme:form-textbox code="projectSquad.name" path="projectSquad.userAccount.identity.name" readonly="true" />
	<acme:form-textbox code="projectSquad.surname" path="projectSquad.userAccount.identity.surname" readonly="true" />

	<%-- METEMOS LOS IDs EN LA URL PARA ASEGURAR QUE NO SE PIERDAN AL GUARDAR --%>
	<acme:submit code="acme.button.save" action="/project-squad/member/create?projectId=${projectId}&squadId=${squadId}" />
	
	<acme:button code="acme.button.cancel" action="/project-squad/member/list?projectId=${projectId}" />

</acme:form>