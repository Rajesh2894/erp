<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<apptags:breadcrumb></apptags:breadcrumb>
<script type="text/javascript">
function refresh() {
	value = "PropertyBillGenerationLog.html";
	$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', value);
		$("#postMethodForm").submit();
}
function backToMain(){	
	value = "PropertyBillGeneration.html";
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', value);
	$("#postMethodForm").submit();
}
</script>
<!-- Start Content here -->
<!-- <iframe id="txtArea1" style="display: none"></iframe> -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.bill.generation.error"
					text="Bill Generation Errors Detail" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="PropertyBillGenerationLog.html" method="post"
				class="form-horizontal" name="PropertyBillGenerationLog"
				id="propertyBillGenerationForm">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.waterBillErrorLog.TotalRecordsforbillgeneration" /></label>
					<div class="col-sm-4">
						<form:input path="propertyBillGenerationMap.noOfBillsForGeneration"
							cssClass="form-control" id="" readonly="true"></form:input>
					</div>

					<%-- <label class="col-sm-2 control-label"><spring:message
							code="water.waterBillErrorLog.TotalRecordsselectedforbillgeneration" /></label>
					<div class="col-sm-4">
						<form:input
							path="propertyBillGenerationMap.noOfBillsSelectedForGeneration"
							cssClass="form-control" id="" readonly="true" />
					</div> --%>
					<label class="col-sm-2 control-label"><spring:message
							code="water.waterBillErrorLog.TotalnoOfbillsgenerated" /></label>
					<div class="col-sm-4">
						<form:input
							path="propertyBillGenerationMap.noOfBillsGeneratedSuccessfull"
							cssClass="form-control" id="" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					
					<label class="col-sm-2 control-label"><spring:message
							code="water.waterBillErrorLog.TotalnoOfbillsgotErros" /></label>
					<div class="col-sm-4">
						<form:input path="propertyBillGenerationMap.noOfBillsGotErrors"
							cssClass="form-control" id="" readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Approximately Time Taken For Bill Generation (in sec)"/></label>
					<div class="col-sm-4">
						<form:input path="propertyBillGenerationMap.approximatelyTakenTimeForBillGeneration"
							cssClass="form-control" id="" readonly="true" />
					</div>
				</div>
				<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
							code="" text="No Of records pending for bill generation"/></label>
					<div class="col-sm-4">
						<form:input path="propertyBillGenerationMap.noOfBillsPending"
							cssClass="form-control" id="" readonly="true" />
					</div>
				</div>
				
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success"
						onclick="backToMain()">
						<spring:message code="water.meter.ok" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="refresh()">
						<spring:message code="" text="Refresh"/>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
