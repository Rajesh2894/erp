<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/disaster_management/departmentalScrutiny.js"></script>

<!-- <style>
	::-webkit-datetime-edit-year-field:not([aria-valuenow]),
	::-webkit-datetime-edit-month-field:not([aria-valuenow]),
	::-webkit-datetime-edit-day-field:not([aria-valuenow]) {
    	color: transparent;
	}
	
</style> -->

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="ComplainRegisterDTO.form.name" text="Complain Register" /></strong>
			</h2>
		</div> 

		<div class="widget-content padding">

			<form:form action="DepartmentalScrutiny.html" method="POST" commandName="command" class="form-horizontal form" name="frmdeptComplainRegister" id="frmdeptComplainRegister">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				<form:hidden path="" id="createdDate"  value="${command.complainRegisterDTO.createdDate}"/>
				<%-- Defect #157988 --%>
				<spring:message code="ComplainRegisterDTO.select.attendee.name" var="attendeeNamePlaceholder" />
				<div class="panel-group accordion-toggle" id="accordion_single_collapse"> 

					<div class="panel panel-default">
								
								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="CMT" hasId="true" disabled="true"
										pathPrefix="complainRegisterDTO.complaintType" isMandatory="true"
										hasLookupAlphaNumericSort="true" 
										hasSubLookupAlphaNumericSort="true" cssClass="form-control"
										showAll="false" />
								</div>

								<div class="form-group">
									<apptags:input labelCode="ComplainRegisterDTO.complainerName" isReadonly="true"
										path="complainRegisterDTO.complainerName" cssClass="hasNameClass" 
										isMandatory="true" maxlegnth="50" />
									<apptags:input labelCode="ComplainRegisterDTO.complainerMobile" isReadonly="true"
										cssClass="hasMobileNo" maxlegnth="10" dataRuleMinlength="10"
										path="complainRegisterDTO.complainerMobile" isMandatory="true" />
								</div>

								<div class="form-group">
									<apptags:textArea
										labelCode="ComplainRegisterDTO.complainerAddress" isReadonly="true"
										path="complainRegisterDTO.complainerAddress" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" />
									<apptags:textArea
										labelCode="ComplainRegisterDTO.complaintDescription" isReadonly="true"
										path="complainRegisterDTO.complaintDescription" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" />
								</div>
								<div class="form-group">
								
								<apptags:input labelCode="ComplainRegisterDTO.complainerMobile1"
										cssClass="hasMobileNo" maxlegnth="10" isReadonly="true"
										path="complainRegisterDTO.complainerMobile1" isMandatory="" />
									
									<label class="col-sm-2 control-label required-control"
										for="location"><spring:message
											code="ComplainRegisterDTO.location"
											text="ComplainRegisterDTO.location" /></label>
									<div class="col-sm-4">
										<form:select path="complainRegisterDTO.location" cssClass="form-control" disabled="true"
											id="location" data-rule-required="true">
											<form:option value="">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${locations}" var="loc">
												<form:option value="${loc.locId}">${loc.locNameEng}-${loc.locArea}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
											
		<%-- <c:if test="${command.atdFname ne null && command.atdFname ne ''}">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ExcelFileUpload">
					<spring:message code="intranet.upldFileNm" text="Uploaded File Name" />
				</label>&nbsp
				<div class="col-sm-4">			
					<div class="form-group">
						&nbsp &nbsp
						<apptags:filedownload filename="${command.atdFname}"
							filePath="${command.atdPath}" actionUrl="DeathRegistration.html?Download" >
						</apptags:filedownload>
					</div>
				</div>
			</div>
		</c:if> --%>
		<c:if test="${command.attachDocs ne null && not empty command.attachDocs} ">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
											code="" text="Uploaded File Name" /></label>
					<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocument">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>																		
									</tr>
									<c:forEach items="${command.attachDocs}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}"  dmsDocId="${lookUp.dmsDocId}"
													filePath="${lookUp.attPath}"
													actionUrl="DepartmentalScrutiny.html?Download" /></td>										
										</tr>
									</c:forEach>
								</table>
							</div>
					</div>
				</div>
		</c:if>	
		
		
				<div class="widget-header">
			<h2>
				<strong><spring:message code="ComplainRegisterDTO.form.div.callAttedanceRecord" text="Call Attendance Record" /></strong>
			</h2>
		</div>
		
		<br>

					<div class="form-group">				

						<label class="col-sm-2 control-label mandatory"><spring:message
								code="ComplainRegisterDTO.callAttendDate"
								text="Call Attend Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="complainRegisterDTO.callAttendDate"
									cssClass="form-control mandColorClass" id="callAttendDate"
									readonly="${command.saveMode eq 'V'}" placeholder="dd/mm/yyyy"/>
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>

						<label class="control-label col-sm-2 mandatory" for="">
							<spring:message code="ComplainRegisterDTO.callAttendTime"
								text="Call Attend Time" />
						</label>
						<div class="col-sm-4">
							<form:input path="complainRegisterDTO.callAttendTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="callAttendTime" placeholder="hh:mm"
								disabled="${command.saveMode eq 'V' ? true : false }" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2 "
											for="callAttendEmployeeList"> <spring:message
												code="ComplainRegisterDTO.callAttendEmployee"
												text="Employee who Attend Call"  /></label>
										<div class="col-sm-4">
											<form:select id="callAttendEmployeeList"
												path="complainRegisterDTO.callAttendEmployee"
												cssClass="form-control chosen-select-no-results" 
												data-rule-required="false" multiple="true"
												disabled="${command.saveMode eq 'V'}" data-placeholder="${attendeeNamePlaceholder}">

												<c:forEach items="${secuDeptEmployee}" var="empl">
													<form:option value="${empl.empId}">${empl.fullName}(${empl.designName})</form:option>
												</c:forEach>
											</form:select>
										</div>
						<apptags:input labelCode="ComplainRegisterDTO.reasonForDelay"
											cssClass="hasNumClass form-control" maxlegnth="1000"
											path="complainRegisterDTO.reasonForDelay" isMandatory="false"
											isDisabled="${command.saveMode eq 'V'}" />
						</div>	
		
												 
		<div class="widget-header">
			<h2>
				<strong><spring:message code="complaintRegister.userAction" text="User Action" /></strong>
			</h2>
		</div>
		
		<br>
	
		<div class="form-group">		
			<label class="col-sm-2 control-label "><spring:message
							code="ComplainRegisterDTO.status" text="Status" /><span class="mand">*</span></label>
						<div class="radio col-sm-4 margin-top-5">
							<label> <form:radiobutton
									path="complainRegisterDTO.complaintStatus" value=""
									
									id="approve" onclick="setStatusApproval()" /> <spring:message code="complainRegisterDTO.approve"
									text="Approve" /></label> <label> <form:radiobutton
									path="complainRegisterDTO.complaintStatus" value=""
									id="reject" onclick="setStatusRejection()"/> <spring:message code="complainRegisterDTO.reject"
									text="Reject" /></label>
									<label> <form:radiobutton
									path="complainRegisterDTO.complaintStatus" value=""
									id="forward" onclick="setStatusForward()"/> <spring:message code="ComplainRegisterDTO.forward"
									text="Forward" />
							</label>
						</div>
						<form:hidden path="" value="" id="hiddenStatus"/>
						<apptags:textArea
				labelCode="ComplainRegisterDTO.remark" isMandatory="true"
				path="complainRegisterDTO.remark" cssClass="hasNumClass form-control"
				maxlegnth="500" />
		</div>
	<div class="table-responsive" id="billDetailTableDiv"
	style="overflow: visible;">
	<table id="billDetailTable" class="table table-bordered table-striped billDetailTableClass">
		<thead>
			<tr>
			    <th scope="col" id="locNo" width="20%"><spring:message code="ComplainRegisterDTO.wardOffice" text="Ward Office" /><span class="mand">*</span></th>
				<th scope="col" id="thBillNo" width="20%"><spring:message code="ComplainRegisterDTO.department.name" text="Department Name" /><span class="mand">*</span></th>
				<th scope="col" id="desId" width="20%"><spring:message code="ComplainRegisterDTO.designation" text="Designation" /><span class="mand">*</span></th>
				<th scope="col" id="bchId" width="20%"><spring:message code="ComplainRegisterDTO.employee.name" text="Employee Name" /><span class="mand">*</span></th>
				<th scope="col" id="thAction" width="10%"><spring:message code="ComplainRegisterDTO.action" text="Action" /></th> 
			</tr>
		</thead>
		<tbody>
				<c:set value="${status.index}" var="count"></c:set>
				<c:set value="0" var="count1"></c:set>
				
				<tr class="billDetailClass"> 
				
				<td>
						<form:select path="entity.locId" cssClass="form-control  chosen-select-no-results " onchange="getDepBasedOnLoc(0)" data-rule-required="true" id="locId${count}" multiple="false">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${locationCat}" var="loc">
								<form:option value="${loc.locId}">${loc.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</td>
					<td>
						<form:select path="entity.department" cssClass="form-control  chosen-select-no-results " onchange="getDesgBasedOnDept(0)" data-rule-required="true" id="department${count}" multiple="false">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</td>		
					
					<td>
						<form:select path="entity.designation" cssClass="form-control  chosen-select-no-results " onchange="getEmployeeByDept(0)" data-rule-required="true" id="designation${count}" multiple="false">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${designlist}" var="dept">
								<form:option value="${dept.dsgid}">${dept.dsgname}</form:option>
							</c:forEach>
						</form:select>
					</td>		
						
					<td><form:select path="entity.employee" cssClass="form-control  chosen-select-no-results " id="employee${count}" data-rule-required="true" multiple="false">
							<form:option value="">  
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.employeList}" var="employee">
								<form:option value="${employee.empId}">${employee.empname}</form:option>
							</c:forEach> 
						</form:select> 
					</td>
							
					<td class="text-center">
						<button data-placement="top" title="<spring:message code="ComplainRegisterDTO.Add" />"
							class="btn btn-success btn-sm addButton11"
							id="addButton12${count}">
							<i class="fa fa-plus-circle"></i>
						</button>
						<button data-placement="top" title="<spring:message code="ComplainRegisterDTO.Delete" />"
							class="btn btn-danger btn-sm delButton"
							id="delButton12${count}">
							<i class="fa fa-trash-o"></i>
						</button>
					</td>
				</tr>
		</tbody>
	</table>
	<form:hidden path="entity.strDepartmentList" id="dept"/>
	<form:hidden path="entity.strEmployeeList"   id="emp" />
</div>
				<%-- <apptags:CheckerAction hideForward="true" hideSendback="true"/> --%>
						
						<div class="text-center clear padding-10">
							<%-- <button type="submit" class="button-input btn btn-success" onclick="confirmToProceed(this)" name="button-submit" style="" id="button-submit">
								<spring:message code="bt.save" text="Submit" />
							</button> --%>
							
							<input type="button" value="<spring:message code="bt.save"/>" onclick="confirmToProceed(this)"
								title='<spring:message code="bt.save" text='Save'/>'
								class="btn btn-success" id="Save">
							
							<%-- Defect #152078 --%>
							<button type="button" class="btn btn-warning" id="resetBtn"
								title='<spring:message code="rstBtn" text="Reset" />'>
								<spring:message code="rstBtn" text="Reset" />
							</button>
							
							<input type="button" onclick="window.location.href='DepartmentalScrutiny.html'"
								title='<spring:message code="bt.backBtn" text='Back' />'
								class="btn btn-danger  hidden-print" value="<spring:message code="bt.backBtn"/>">
						</div>

					</div>
				</div> 
			</form:form>
		</div> 
	</div>


