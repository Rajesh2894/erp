
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/fire_management/callRegister.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script>
$(document).ready(function() {
	$('#callForwarded1').hide();
	prepareDateTag11();
	$('.morethancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: '-0d'
	});
	
	

});

function prepareDateTag11() {

	var dateFields = $('.morethancurrdate');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	$('.morethancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		//minDate: '-0d',
		maxDate: new Date()
	});

	$('.morethancurrdate1').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		//minDate: '-0d',
		maxDate: new Date()
	});
	
	 var mode = $('#mode').val();
	if (mode != 'E') {
			$('.morethancurrdate').datepicker('setDate', new Date());
		}

		$(".timepicker1").timepicker({
			changeMonth : true,
			changeYear : true,
			minDate : '0',
		});
		if (mode != 'E') {
			/* $(".timepicker1").on('click', function (){
				$(this).timepicker('setTime', new Date());
			}); */
			var dt = new Date();
			var time = dt.getHours() + ":" + dt.getMinutes();
			$("#time").val(time);
		}

	}
</script>

<!-- 
<script type="text/javascript">
$(document).ready(function() {
$(".datepicker").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true,
});

$(".timepicker").timepicker({
	// s dateFormat: 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
});

$("#time").timepicker({

});


</script> -->


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="FireCallRegisterDTO.form.fire.department.call.slip"
						text="Fire Department Call Slip" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>
			<!-- End mand-label -->

			<form:form action="FireCallRegister.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmFireCallRegister" id="frmFireCallRegister">


				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<form:hidden path="entity.statusFlag"
					cssClass="hasNumber form-control" id="status" />
				<form:hidden path="entity.fireDraftId"
					cssClass="hasNumber form-control" id="fireDraftId" />
					
				 <form:hidden path="" value="${command.saveMode}" id="mode"/>


				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.name"
										text="Call Detail" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<%-- <apptags:date labelCode="FireCallRegisterDTO.date"
										datePath="entity.date"
										fieldclass="custDate mandColorClass date" isMandatory="true" /> --%>

									<apptags:date fieldclass="morethancurrdate"
										labelCode="FireCallRegisterDTO.date" isMandatory="true"
										datePath="entity.date" 
										cssClass="" >
									</apptags:date>

									<apptags:date labelCode="FireCallRegisterDTO.time"
										datePath="entity.time" fieldclass="timepicker1"
										isMandatory="false" />
								</div>



							
								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.callerMobileNo"
										cssClass="hasMobileNo" maxlegnth="10"
										path="entity.callerMobileNo" isMandatory="true" />

									<apptags:input labelCode="FireCallRegisterDTO.incidentLocation"
										cssClass="" maxlegnth="100"
										path="entity.incidentLocation" isMandatory="true" />
								</div>

								<div class="form-group">
									<apptags:textArea labelCode="FireCallRegisterDTO.incidentDesc"
										path="entity.incidentDesc" cssClass=""
										maxlegnth="500" isMandatory="true" />


									<label class="control-label col-sm-2 required-control" for="cpdCallType">
										<spring:message code="FireCallRegisterDTO.cpdCallType"
											text="Call Type" />
									</label>
									<c:set var="baseLookupCode" value="FCN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.cpdCallType"
										cssClass="mandColorClass form-control" isMandatory="false"
										hasId="true" selectOptionLabelCode="selectdropdown"
										disabled="${command.saveMode eq 'V'}" />

								</div>
								
									<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.callerName"
										path="entity.callerName" cssClass="hasNameClass"
										isMandatory="false" maxlegnth="50" />

									<apptags:textArea labelCode="FireCallRegisterDTO.callerAdd"
										cssClass="" maxlegnth="100"
										path="entity.callerAdd" isMandatory="false" />

								</div>
								


								<div class="form-group">


									<apptags:radio radioLabel="bt.yes,bt.no" radioValue="Y,N" changeHandler="selectArea(this)"
										labelCode="FireCallRegisterDTO.callerArea" defaultCheckedValue="N"
										path="entity.callerArea"  />
										
										<apptags:textArea
										labelCode="FireCallRegisterDTO.operatorRemarks"
										path="entity.operatorRemarks" cssClass=""
										maxlegnth="100" isMandatory="false" />
								</div>
										
										
                                <div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
											code="workflow.checkAct.AttchDocument" /></label>
									<div class="col-sm-4">
										<apptags:formField fieldType="7" fieldPath=""
											showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
											currentCount="0">
										</apptags:formField>
									</div>
									
									<div class="col-sm-4">
									<c:if test="${command.saveMode eq 'E'}">
										<c:if test="${command.entity.atdFname ne null && command.entity.atdFname ne ''}">
											<div class="form-group">
												<label class="col-sm-2 control-label" for="ExcelFileUpload">
													<spring:message code="intranet.upldFileNm" text="Uploaded File Name" />
												</label>&nbsp
												<apptags:filedownload filename="${command.entity.atdFname}"
													filePath="${command.entity.atdPath}" actionUrl="DeathRegistration.html?Download" >
												</apptags:filedownload>
											</div>
										</c:if>	
									</c:if>
									</div>
									
								</div>


							</div>

						</div>


						<div class="panel panel-default">
							<div class="panel-heading">

								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#collapse1">
										<spring:message code="FireCallRegisterDTO.form.Record"
											text="Call Attend Record" />
									</a>
								</h4>
							</div>

                                        <div id="a2" class="panel-collapse collapse in">
										<div class="panel-body">
												<div class="form-group">
												<label class="control-label col-sm-2 required-control"
													for="fireStationsAttendCall"> <spring:message
														code="FireCallRegisterDTO.cpdFireStation" text="Fire Station" /></label>
												<c:set var="baseLookupCode" value="FSN" />
												<div class="col-sm-4">
													<form:select id="cpdFireStationList" path="entity.cpdFireStationList" cssClass="form-control chosen-select-no-results" data-rule-required="true" multiple="true" disabled="${command.saveMode eq 'V'}">
														<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
															<form:option value="${lookUp.lookUpId}"	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select>
												</div> 
												



								<label class="control-label col-sm-2 required-control" for="nameVisitingOff">
									<spring:message code="FireCallRegisterDTO.form.duty.officer" text="Duty Officer" />
								</label>
								<div class="col-sm-4">
									<form:select path="entity.dutyOfficer" id="dutyOfficer"
										data-rule-required="true"
										cssClass="form-control chosen-select-no-results"
										disabled="${command.saveMode eq 'V'}">
										<form:option value="">
											<spring:message code="Select" text="Select" />
										</form:option>
										<c:forEach items="${secuDeptEmployee}" var="empl">
											<form:option value="${empl.empId}" label="${empl.fullName} - ${empl.designName}"></form:option>
										</c:forEach>
									</form:select>
								</div>

							</div>

							<div class="form-group">

								<apptags:input labelCode="FireCallRegisterDTO.form.call.attended.by" path="entity.callAttendedBy"
								cssClass="hasNameClass" isMandatory="false" maxlegnth="50" />

								<apptags:input labelCode="FireCallRegisterDTO.form.recorded.by" path="entity.recordedBy"
								cssClass="hasNameClass" isMandatory="false" maxlegnth="50" />
							</div>

							<div class="form-group">
							<div id="callForwarded1">
							<apptags:input labelCode="FireCallRegisterDTO.form.call.forwarded" path="entity.callForwarded" 
							cssClass="hasNameClass" isMandatory="false" maxlegnth="50" /></div>
							
							<label class="control-label col-sm-2 "
									for="VehicleType"><spring:message code="FireCallRegisterDTO.form.name.of.operator"
										text="Name of Operator" /></label>
								<c:set var="baseLookupCode" value="OPR" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="entity.operator" cssClass="form-control "
									selectOptionLabelCode="selectdropdown"
									hasId="true" />
							</div>

                             <div class="form-group">

							<apptags:date fieldclass="lessthancurrdate"
							 labelCode="FireCallRegisterDTO.vehicleOutDate" isMandatory="true"
							 datePath="entity.vehicleOutDate" cssClass="">
							</apptags:date>


							<apptags:date fieldclass="lessthancurrdate"
							labelCode="FireCallRegisterDTO.vehicleInDate" isMandatory="false"
							datePath="entity.vehicleInDate" cssClass="">
							</apptags:date>


									</div>

								<div class="form-group">
								<label class="control-label col-sm-2" for="nameVisitingOff">
									<spring:message code="FireCallRegisterDTO.form.assign.vehicle" text="Assign Vehicle" />
								</label>
								<div class="col-sm-4">
									<form:select path="entity.assignVehicleList" id="nameVisitingOff" multiple="true"
						
										cssClass="form-control chosen-select-no-results">
										<form:option value="0">
											<spring:message code="Select" text="Select" />
										</form:option>
										<c:forEach items="${listVeh}" var="empl">
											<form:option value="${empl.assignVehicle}"
												label="${empl.vehNoDesc}"></form:option>
										</c:forEach>
									</form:select>
								</div>
								
									<apptags:date fieldclass="timepicker"
									labelCode="FireCallRegisterDTO.vehicleOutTime"
									datePath="entity.vehicleOutTime" isMandatory="false"
									cssClass="">
								</apptags:date>

							</div>

							<div class="form-group">
								<apptags:date fieldclass="timepicker"
									labelCode="FireCallRegisterDTO.vehicleInTime" isDisabled="true"
									datePath="entity.vehicleInTime" cssClass="">
								</apptags:date>
								
								<label class="control-label col-sm-2"
									for="Remarks"><spring:message code="FireCallRegisterDTO.form.other.details"
										text="Other Details" /></label>
								<div class="col-sm-4">
									<form:textarea path="entity.otherDetails" maxLength="100"
										class="form-control mandColorClass" id="purpose"></form:textarea>
								</div>
							</div>

<!-- 
							<div class="form-group">

                                 </div> -->
                            </div>
                              </div>
                              </div>

								<div class="text-center clear padding-10">
								
								
									<c:if test="${command.saveMode ne 'V'}">
									
									<button type="button"
												value="<spring:message code="bt.save"/>"
												onclick="saveDraftFireData(this)" class="btn btn-green-3" 
												title="<spring:message code="FireCallRegisterDTO.form.bt.saveasdraft" text="Save As Draft" />">
												<spring:message code="FireCallRegisterDTO.form.bt.saveasdraft" text="Save As Draft" />
											</button>
									
										<input type="button" value="<spring:message code="bt.save"/>"
											title='<spring:message code="bt.save"/>'
											onclick="confirmToProceed(this)" class="btn btn-success"
											id="Submit">
										<c:if test="${command.saveMode ne 'E'}">


											<button type="button" id="reset"
												onclick="openForm('FireCallRegister.html','fireCallRegister')"
												class="btn btn-warning" title='<spring:message code="rstBtn" text="Reset" />'>
												<spring:message code="rstBtn" text="Reset" />
											</button>
										</c:if>
									</c:if>
									<input type="button"
										title='<spring:message code="bckBtn" text="Back" />'
										onclick="window.location.href='FireCallRegister.html'"
										class="btn btn-danger  hidden-print" value='<spring:message code="bckBtn" text="Back" />'>


								</div>
								<!-- End button -->

							
						
					</div>

				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->