<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/masters/tborgdesignation/orgdesgmap.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="master.organisation.designation.map"/></h2>
			<apptags:helpDoc url="OrgDesignation.html" helpDocRefURL="OrgDesignation.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
		<c:url value="${saveAction}" var="url_form_submit" />
		<form:form method="post" action="${url_form_submit}" name="orgDesgMap" id="orgDesgMap" class="form-horizontal" modelAttribute="tbOrgDesignation">
		<div class="error-div alert alert-danger alert-dismissible" style="display: none;" id="errorDivOrgMas"></div>	
		<div class="text-center padding-bottom-10">
		<button type="button" class="btn btn-success" onclick="addDesignation()"><i class="fa fa-plus-circle"></i> <spring:message code="bt.add" /></button>
		</div>
		<table id="orgDesgGrid"></table>
		<div id="pagered"></div>
		</form:form>
	</div>
	</div>
</div>