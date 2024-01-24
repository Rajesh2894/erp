<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/disaster_management/complainRegister.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="ComplainRegisterDTO.form.name" text="Complain Register" /></strong>
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

			<form:form action="ComplainRegister.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmComplainRegister" id="frmComplainRegister">
				<form:hidden path="saveMode" id="saveMode" />

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle" id="accordion_single_collapse"> 

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="ComplainRegisterDTO.form.name"
										text="Complain Register" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
							
							<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="CMT" hasId="true"
										pathPrefix="entity.complaintType" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" cssClass="form-control"
										showAll="false" />
								</div>
								
								<div class="form-group">
									<apptags:input labelCode="ComplainRegisterDTO.manual.complain.no"
										path="entity.manualComplainNo" cssClass=""
										isMandatory="false" maxlegnth="45" />
										<apptags:input labelCode="ComplainRegisterDTO.complainerName"
										path="entity.complainerName" cssClass="hasNameClass"
										isMandatory="true" maxlegnth="200" />
									
								</div>


								<div class="form-group">
									
									<apptags:input labelCode="ComplainRegisterDTO.complainerMobile"
										cssClass="hasMobileNo" maxlegnth="10"
										path="entity.complainerMobile" isMandatory="true" />
										
										<apptags:textArea
										labelCode="ComplainRegisterDTO.complainerAddress"
										path="entity.complainerAddress" cssClass=""
										maxlegnth="500" isMandatory="true" />
								</div>
							
								<div class="form-group">
									
									<apptags:input labelCode="ComplainRegisterDTO.complainerMobile1"
										cssClass="hasMobileNo" maxlegnth="10"
										path="entity.complainerMobile1" isMandatory="" />
										
										<label class="col-sm-2 control-label required-control"
										for="location"><spring:message
											code="ComplainRegisterDTO.location"
											text="ComplainRegisterDTO.location" /></label>
									<div class="col-sm-4">
										<form:select path="entity.location" cssClass="form-control chosen-select-no-results"
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

							</div>

						<!-- <div class="form-group"> -->
									<%-- <apptags:input labelCode="ComplainRegisterDTO.form.incidentlocation"
										path="entity.complainerName" cssClass="hasNameClass" isMandatory="false" maxlegnth="50" /> --%>
									
							<!-- 	</div> -->


							<div class="form-group">
							
							<apptags:textArea
										labelCode="ComplainRegisterDTO.complaintDescription"
										path="entity.complaintDescription" cssClass=""
										maxlegnth="500" isMandatory="true" />
							
							<apptags:radio radioLabel="bt.yes,bt.no" radioValue="Y,N" 
										labelCode="ComplainRegisterDTO.callerArea" defaultCheckedValue="N"
										path="entity.callerArea"  />
							</div>
										
							<div class="form-group">
										
									<label class="col-sm-2 control-label"><spring:message
											code="workflow.checkAct.AttchDocument" /></label>
									<div class="col-sm-4">
										<apptags:formField fieldType="7" fieldPath=""
											showFileNameHTMLId="true" fileSize="ECHALLAN_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_ECHALLAN"
											currentCount="0">
										</apptags:formField>
										<%-- Defect #152071 --%>
										<small class="text-blue-2">
											<spring:message code="complainRegisterDTO.document.upload.tooltip"
												text="(Upload File upto 50MB and Only .jpeg, .png, .jpg, .avi, .mp4, .mpeg, .3gp, .wmv, .flv and .mkv. extension(s) file(s) are allowed.)"/>
										</small>
									</div>
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
						

						<div class="text-center clear padding-10">
							<c:if
								test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
								<input type="button" value="<spring:message code="bt.save"/>" onclick="confirmToProceed(this)" class="btn btn-success"
									title='<spring:message code="bt.save"/>'
									id="Save">
								
							</c:if>
							<c:if test="${command.saveMode eq 'C'}">
								<button type="Reset" class="btn btn-warning"
									title='<spring:message code="bt.clear" text="Reset"></spring:message>'
									onclick="window.location.href='ComplainRegister.html'">
									<spring:message code="bt.clear" text="Reset"></spring:message>
								</button>
							</c:if>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>

						</div>
						<!-- End button -->
					
					</div>
				</div>
				</div>
				
			</form:form>
			<!-- End Form -->
		
	</div>
	<!-- End Widget Content here -->
</div>
</div>
<!-- End of Content -->


