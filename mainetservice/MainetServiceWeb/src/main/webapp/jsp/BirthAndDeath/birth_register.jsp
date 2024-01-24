<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>  
<script type="text/javascript" src="js/birthAndDeath/birthRegister.js"></script> 
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script> 
   
   <apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="BirthRegister.without.user" text="Birth Register Without User" />
				<apptags:helpDoc url="BirthRegister.html"></apptags:helpDoc>
			</h2>
		</div>
		<div  class="widget-content padding">
		
		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<div id="errorId"></div>
</div>

		<form:form action="BirthRegister.html" id="frmbirthregister" method="POST" commandName="command" class="form-horizontal form">
		<h4>
						<spring:message code="BirthRegisterDTO.enter.selection.criteria" text="Enter The Selection Criteria For The Report" />
		</h4>
		
		<div class="form-group">
		   <label class="control-label col-sm-2 required-control" 
							for="Census"> <spring:message code="BirthRegisterDTO.registrationUnit"
								text="Registration Unit" />
						</label>
						<c:set var="baseLookupCode" value="REU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegisterDTO.registrationUnit"
							cssClass="form-control" isMandatory="true" showAll="true" hasId="true" 
							selectOptionLabelCode="selectdropdown" />
							
							
			<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegisterDTO.periodOfReportBy"
								text="Period Of Report By" />
						</label>
						<c:set var="baseLookupCode" value="POR" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegisterDTO.periodOfReportBy"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />				
		</div>
		
		<div class="form-group">
		<apptags:date fieldclass="datepicker"
				labelCode="BirthRegisterDTO.fromDate"
				datePath="birthRegisterDTO.fromDate" isMandatory="true"
				cssClass="custDate mandColorClass date">
			</apptags:date>	
			
			<apptags:date fieldclass="datepicker"	
			labelCode="BirthRegisterDTO.toDate"
			datePath="birthRegisterDTO.toDate" isMandatory="true"
			cssClass="custDate mandColorClass date">
			</apptags:date>
			</div>
			
			<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegisterDTO.reportType"
								text="Report Type" />
						</label>
						<c:set var="baseLookupCode" value="REF" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegisterDTO.reportType" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
							
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegisterDTO.sortOrder"
								text="Sort Order" />
						</label>
						<c:set var="baseLookupCode" value="STO" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegisterDTO.sortOrder" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />	
							
					</div>
					
					<div class="text-center margin-top-10">
			<input type="button" onClick="confirmToProceed(this);" value="<spring:message code="BirthRegisterDTO.viewReport" text="Search"/>" class="btn btn-blue-2" id="Search">
			<button type="Reset" class="btn btn-warning" onclick="resetForm(this);">
						<spring:message code="reset" text="Reset"/>
					</button>
			<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print" value="<spring:message code="BirthRegDto.back" text="Back"/>">
		</div>
					
			
		</form:form>
	 </div>
   </div>
</div>
		