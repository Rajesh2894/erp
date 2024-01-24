<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/property/billingSchedule.js"></script>
<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="bill.schedule" text="Billing Schedule"/></h2>
		</div>
		<div class="widget-content padding">
		<form:form method="post" action="BillingSchedule.html" name="BillingSchedule" id="BillingSchedule" class="form-horizontal">
		<div class="error-div alert alert-danger alert-dismissible" style="display: none;" id="errorDiv"></div>	
		<div class="text-center padding-bottom-10">
		<button type="button" class="btn btn-success btn-submit" id="createSchedule"><i class="fa fa-plus-circle"></i> <spring:message code="bt.add" text="Add" /></button>
		</div>
		<table id="sheduleGrid"></table>
		<div id="pagered"></div>
		</form:form>
	</div>
	</div>
</div>
</div>