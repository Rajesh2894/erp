<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<script src="js/eip/citizen/citizenContactUs.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
$(document).ready(function(){
  
	 $('#empDetailId').hide();//dont show if isUpdclEmp cheked
    $("#isUpdclEmp").change(function() {
    	debugger
        if(this.checked) {
            $('#empDetailId').show();
        }else{
        	 $('#empDetailId').hide();
        }
    });
});

function saveNewEleCon()
{
	debugger
	var	formName =	findClosestElementId($('#getFormId'), 'form');
	var theForm	=	'#'+formName;
	var requestData = {};		
	requestData = __serializeForm(theForm);

	var ajaxResponse = __doAjaxRequest('DoonIntegration.html?saveNewElecon1', 'post', requestData,
			false, 'json');
	var ajaxResponse = __doAjaxRequest('DoonIntegration.html?saveUpdclNewEleCon', 'post', requestData,
			false, 'json');
	
	if (ajaxResponse.length > 0) {
		
	$(".error-div").show();
		showError(ajaxResponse);
		return false;
	
	}
	}

</script>
<style>
	#newUPDCLConn .zone label[for="zone1"] + div,
	#newUPDCLConn .zone label[for="zone2"] + div {
		margin-bottom: 0.5rem;
	}
</style>

<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i> <spring:message code="menu.home"/></a></li>
	<li><spring:message code="updcl.heading" text="Apply new Connection"/></li>
</ol>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="updcl.heading" text="UPDCL New Registration" />
			</h2>
			<div class="additional-btn">
				<a href="#" class="widget-toggle"><span class="hide">Addditional</span><strong class="icon-down-open-2"></strong></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="panel-group accordion-toggle" id="accordion-new-elec">
			<form:form method="post" action="DoonIntegration.html"
				name="newUPDCLConn" id="newUPDCLConn"
				class="form-horizontal" role="form">
				<form:hidden path="newUPDCLEleConDTO.consumerType" value="1"/>
				<form:hidden path="newUPDCLEleConDTO.serviceNumber" value="1"/>
				<form:hidden path="newUPDCLEleConDTO.loadUnit" value="KW"/>
				  <input type="hidden" id="getFormId">
				  
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion-new-elec" href="#new-elec-1">Electricity Details</a>
						</h4>
					</div>
					<div id="new-elec-1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="service">
									<spring:message code="updcl.service" text="service" /></label>
								<c:set var="categoryTypePrefix" value="UPR" />
								<apptags:lookupField
									items="${command.getLevelData(categoryTypePrefix)}"
									path="newUPDCLEleConDTO.service"
									cssClass="form-control required-control chosen-select-no-results"
									selectOptionLabelCode="selectdropdown" hasId="true" />
							</div>
							<div class="form-group zone">
								<apptags:lookupFieldSet cssClass="form-control required-control"
									baseLookupCode="UDI" hasId="true" pathPrefix="newUPDCLEleConDTO.zone"
									hasLookupAlphaNumericSort="true" disabled="false"
									hasSubLookupAlphaNumericSort="true" showAll="false"
									isMandatory="true" />
							</div>
							<div class="form-group">
								<apptags:lookupFieldSet cssClass="form-control required-control"
									baseLookupCode="UPX" hasId="true" pathPrefix="newUPDCLEleConDTO.categor"
									hasLookupAlphaNumericSort="true" disabled="false"
									hasSubLookupAlphaNumericSort="true" showAll="false"
									isMandatory="true" />
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="purpose"><spring:message
											code="updcl.purpose" text="purpose" /></label>
									<c:set var="categoryTypePrefix" value="UPS" />
									<apptags:lookupField
										items="${command.getLevelData(categoryTypePrefix)}"
										path="newUPDCLEleConDTO.purpose"
										cssClass="form-control required-control chosen-select-no-results"
										selectOptionLabelCode="selectdropdown" hasId="true" />
								<label class="col-sm-2 control-label required-control" for="appliedLoad"><spring:message code="updcl.appliedLoad" text="appliedLoad" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input type="text" class="input2  form-control" id="appliedLoad" name=""
											path="newUPDCLEleConDTO.appliedLoad" placeholder="appliedLoad"></form:input>
										<span class="input-group-addon">KW</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="plotSize"><spring:message code="updcl.plotSize" text="plotSize" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input type="text" class="input2  form-control" id="plotSize" name=""
											path="newUPDCLEleConDTO.plotSize" placeholder="plotSize"></form:input>
										<span class="input-group-addon">Sq Mts</span>
									</div>
								</div>
							
								<label class="col-sm-2 control-label required-control" for="builtupArea"><spring:message code="updcl.builtupArea" text="builtupArea" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input type="text" class="input2  form-control" id="builtupArea" name=""
											path="newUPDCLEleConDTO.builtupArea" placeholder="builtupArea"></form:input>
										<span class="input-group-addon">Sq Mts</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<apptags:lookupFieldSet cssClass="form-control required-control"
									baseLookupCode="UDX" hasId="true" pathPrefix="newUPDCLEleConDTO.divisionOffic"
									hasLookupAlphaNumericSort="true" disabled="false"
									hasSubLookupAlphaNumericSort="true" showAll="false"
									isMandatory="true" />
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion-new-elec" href="#new-elec-2">Personal Details</a>
						</h4>
					</div>
					<div id="new-elec-2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="Fname"><spring:message code="updcl.applicantName" text="Applicant Name" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="input2  form-control" id="Fname" name=""
										path="newUPDCLEleConDTO.applicantName" placeholder="First Name"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control" for="fatherHusbandName"><spring:message code="updcl.fatherHusbandName" text="fatherHusbandName" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="input2  form-control" id="Fname" name=""
										path="newUPDCLEleConDTO.fatherHusbandName" placeholder="fatherHusbandName"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="housePlotNumber"><spring:message code="updcl.housePlotNumber" text="House Name" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="form-control" id="housePlotNumber" name=""
										path="newUPDCLEleConDTO.housePlotNumber" placeholder="house Name"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control" for="streetName"><spring:message code="updcl.streetName" text="streetName" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="form-control" id="streetName" name=""
										path="newUPDCLEleConDTO.streetName" placeholder="streetName"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="colonyArea"><spring:message code="updcl.colonyArea" text="colonyArea Name" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="form-control" id="colonyArea" name=""
										path="newUPDCLEleConDTO.colonyArea" placeholder="colonyArea Name"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control" for="residencePhone"><spring:message code="updcl.residencePhone" text="residencePhone" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="form-control" id="residencePhone" name=""
										path="newUPDCLEleConDTO.residencePhone" placeholder="residencePhone"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control" for="phone"><spring:message code="updcl.phone" text="phone" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="form-control" id="phone" name=""
										path="newUPDCLEleConDTO.phone" placeholder="phone"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control" for="email"><spring:message code="updcl.email" text="email" /></label>
								<div class="col-sm-4">
									<form:input type="text"
										class="form-control" id="email" name=""
										path="newUPDCLEleConDTO.email" placeholder="email"></form:input>
								</div>
							</div>
					
							<div class="form-group">
								<apptags:checkbox labelCode="UpdclEmployee" value="Y" path="newUPDCLEleConDTO.isUpdclEmp"/>
							</div>
							<div id="empDetailId">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control" for="empId"><spring:message
											code="updcl.empId" text="empId" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="empId" name=""
											path="newUPDCLEleConDTO.empId" placeholder="empId"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="empOfficename"><spring:message
											code="updcl.empOfficename" text="empOfficename" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="empOfficename"
											name="" path="newUPDCLEleConDTO.empOfficename"
											placeholder="empOfficename"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="empName"><spring:message code="updcl.empName"
											text="empName" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="empName" name=""
											path="newUPDCLEleConDTO.empName" placeholder="empName"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="empStatus"><spring:message code="updcl.empStatus"
											text="empStatus" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="empStatus"
											name="" path="newUPDCLEleConDTO.empStatus"
											placeholder="empStatus"></form:input>
									</div>
								</div>
							</div>
							<div>
								<div class="row margin-bottom-20">
									<label class="col-sm-2 control-label "> <spring:message
											code="profile.image" text="Profile Image" /></label>
									<apptags:formField fieldType="7" hasId="true"
										fieldPath="newUPDCLEleConDTO.applicantImage" isMandatory="false"
										labelCode="" currentCount="0" showFileNameHTMLId="true"
										folderName="EIP_HOME" fileSize="COMMOM_MAX_SIZE"
										maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
										cssClass="form-control"
										callbackOtherTask="otherTaskWithImageThumbNail('CitizenRegistration.html')" />
			
									<div id="uploadPreview" class="col-sm-4">
										<ul></ul>
									</div>
								</div>
							</div>
							<div class="form-group">
								<%-- <c:set  var="hi" value="${command.getUUIDNo() }"/> --%>
								<label class="col-sm-2 control-label "> <spring:message
													code="label.checklist.upload" text="Upload " /></label>
								<apptags:formField fieldType="7" hasId="true"
									fieldPath="newUPDCLEleConDTO.nscForm" isMandatory="false"
									labelCode="label.checklist.upload" currentCount="1" showFileNameHTMLId="true"
									folderName="EIP_HOME" fileSize="20485760"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
									cssClass="form-control mandClassColor" /> 
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="text-center margin-top-20">
					<input type="button" value="Save" onClick="return saveNewEleCon(this);" class="btn btn-success">
					<button type="reset" class="btn btn-danger">Reset</button>
				</div>
			</form:form>
			</div>
		</div>
	</div>
</div>