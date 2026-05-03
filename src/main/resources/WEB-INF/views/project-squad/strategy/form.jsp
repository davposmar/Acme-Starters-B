<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="project-squad.strategy.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="project-squad.strategy.form.label.name" path="name" readonly="true"/>
	<acme:form-textarea code="project-squad.strategy.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="project-squad.strategy.form.label.startMoment" path="startMoment" readonly="true"/>
	<acme:form-moment code="project-squad.strategy.form.label.endMoment" path="endMoment" readonly="true"/>
	<acme:form-url code="project-squad.strategy.form.label.moreInfo" path="moreInfo" readonly="true"/>
</acme:form>