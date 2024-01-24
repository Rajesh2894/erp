<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript"
	src="js/security_management/dailyIncidentReg.js"></script>
<script type="text/javascript">

		$(".Moredatepicker").timepicker({
			//s dateFormat: 'dd/mm/yy',		
			changeMonth : true,
			changeYear : true,
			minDate : '0',
		});

		$("#time").timepicker({
			changeMonth : true,
			changeYear : true,
			minDate : '0',

		});

	$(function(){
	    $(".chosen-select-no-results").chosen();
	});
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="DailyIncidentRegisterDTO.form.name"
						text="Daily Incident Register" /></strong>
			</h2>
		</div>
		
		<div class="widget-content padding">
		<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->

			<form:form action="dailyIncidentRegister.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmDailyIncident" id="frmDailyIncident">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${dailyIncidentRegisterDTO.date}" var="dateFormat" />
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="DailyIncidentRegisterDTO.date" text="Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dailyIncidentRegisterDTO.Date" disabled="${command.saveMode eq 'V'}" 
							 id="date"
								class="form-control mandColorClass datepicker dateValidation"  
								value="" readonly="false" placeholder="dd/mm/yyyy"
								maxLength="10" />
							<label class="input-group-addon" for="date"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=date></label>
						</div>
					</div>
					<apptags:input labelCode="DailyIncidentRegisterDTO.time"
						path="dailyIncidentRegisterDTO.time" 
						maxlegnth="5"
						isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
						cssClass="hasTime timepicker mandColorClass" />
				</div>

				<div class="form-group">
					<apptags:textArea labelCode="DailyIncidentRegisterDTO.remarks"
						path="dailyIncidentRegisterDTO.remarks" maxlegnth="500" isMandatory="true"
						isReadonly="${command.saveMode eq 'V'}" />

					<label class="control-label col-sm-2 required-control" for="nameVisitingOff">
						<spring:message code="DailyIncidentRegisterDTO.nameVisitingOff" 
							text="Name of Visiting Officer" />
					</label>
					<div class="col-sm-4">

						<%-- 	<form:select id="nameVisitingOff" path="dailyIncidentRegisterDTO.nameVisitingId" cssClass="form-control" multiple="multiple" disabled="${command.saveMode eq 'V'}">
														<form:option value="0">Select</form:option>
															<c:forEach items="${secuDeptEmployee}" var="empl">
																<form:option value="${empl.empId}">${empl.fullName}</form:option>
														</c:forEach> 
													</form:select> --%>
						<%-- <input type="hidden" id="empIds" value="${empIDs}"> --%>

						<form:select path="dailyIncidentRegisterDTO.visitingOfficerIds"
							id="nameVisitingOff" multiple="multiple" data-rule-required="true"
							cssClass="chosen-select-no-results"  
							disabled="${command.saveMode eq 'V'}">
							<c:forEach items="${secuDeptEmployee}" var="empl">
								<form:option value="${empl.empId}" label="${empl.fullName}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							title='<spring:message code="bt.save"/>'
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Save">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								onclick="openForm('dailyIncidentRegister.html','dailyIncident')">
								<spring:message code="DailyIncidentRegisterDTO.form.reset"
									text="Reset" />
							</button>
							<%-- 			<button type="button" 
						          onclick="emptyForm(this);"
						          class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="DailyIncidentRegisterDTO.form.reset"
							text="Reset" />
					</button> --%>
						</c:if>
					</c:if>
					<input type="button"
						title='<spring:message code="DeploymentOfStaffDTO.form.back"/>'
						onclick="window.location.href='dailyIncidentRegister.html'"
						class="btn btn-danger  hidden-print" value="<spring:message code="DeploymentOfStaffDTO.form.back"/>">
				</div>

			</form:form>

		</div>

	</div>
</div>










