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
<script type="text/javascript" src="js/birthAndDeath/deathRegister.js"></script> 
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script> 
    
    
    
    
    <apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="Death Register Without User" text="Death Register Without User" />
				<apptags:helpDoc url="DeathRegister.html"></apptags:helpDoc>
			</h2>
		</div>
		<div  class="widget-content padding">
		
		<div class="warning-div error-div aaaaaalert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
</div>

		<form:form action="DeathRegister.html" id="frmdeathregister" method="POST" commandName="command" class="form-horizontal form">
		
		<h4>
						<spring:message text="Enter The Selection Criteria For The Report" />
		</h4>
		
		<div class="form-group">
		   <label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="DeathRegisterDTO.registrationUnit"
								text="Registration Unit" />
						</label>
						<c:set var="baseLookupCode" value="REU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="deathRegisterDTO.registrationUnit"
							cssClass="form-control" isMandatory="true" showAll="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
							
							
			<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="DeathRegisterDTO.periodOfReportBy"
								text="Period Of Report By" />
						</label>
						<c:set var="baseLookupCode" value="POD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="deathRegisterDTO.periodOfReportBy"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />				
		</div>
		
		<div class="form-group">
		<apptags:date fieldclass="datepicker"
				labelCode="DeathRegisterDTO.fromDate"
				datePath="deathRegisterDTO.fromDate" isMandatory="true"
				cssClass="custDate mandColorClass date">
			</apptags:date>	
			
			<apptags:date fieldclass="datepicker"	
			labelCode="DeathRegisterDTO.toDate"
			datePath="deathRegisterDTO.toDate" isMandatory="true"
			cssClass="custDate mandColorClass date">
			</apptags:date>
			</div>
			
			<div class="form-group">
							
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="DeathRegisterDTO.sortOrder"
								text="Sort Order" />
						</label>
						<c:set var="baseLookupCode" value="STO" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="deathRegisterDTO.sortOrder" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />	
							
					</div>
					
					<div class="text-center margin-top-10">
			<input type="button" onClick="confirmToProceed(this);" value="<spring:message code="DeathRegisterDTO.viewReport"/>" class="btn btn-blue-2" id="Search">
			<button type="Reset" class="btn btn-warning" onclick="resetForm(this);">
						<spring:message code="reset" text="Reset"/>
					</button>
			<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print" value="Back">
		</div>
					
			
		</form:form>
	 </div>
   </div>
</div>
    