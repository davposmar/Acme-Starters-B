<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-textbox  readonly="true" code="any.project.form.label.ticker" path="ticker"/>
	<acme:form-textbox readonly="true" code="any.project.form.label.title" path="title"/>
	<acme:form-textbox readonly="true" code="any.project.form.label.keyWords" path="keyWords"/>
	<acme:form-textarea readonly="true" code="any.project.form.label.description" path="description"/>
	<acme:form-moment readonly="true" code="any.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment readonly="true" code="any.project.form.label.closeOut" path="closeOut"/>
	
	<acme:form-double readonly="true" code="any.project.form.label.effort" path="effort"/>
	<acme:form-textbox readonly="true" code="any.project.form.label.manager" path="manager.identity.fullName"/>
	<acme:button code="any.project.form.button.invention" action="/any/invention/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.strategy" action="/any/strategy/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.campaign" action="/any/campaign/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.sponsorship" action="/any/sponsorship/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.audit-report" action="/any/audit-report/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.member" action="/any/member/list?projectId=${id}"/>
</acme:form>
