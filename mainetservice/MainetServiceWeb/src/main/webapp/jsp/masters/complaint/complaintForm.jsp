<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript"
	src="js/masters/complaint/complaintForm.js"></script>

<!-- Start info box -->
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="master.complainttype.master" />
		</h2>
		<apptags:helpDoc url="Complaint.html"></apptags:helpDoc>

	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg" /> <i
				class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" /></span>
		</div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:form action="Complaint.html" method="POST" name="complaintForm"
			class="form-horizontal" id="complaintForm">
			<form:hidden path="departmentComplaint.hiddeValue" id="hiddeValue" />
			<div class="form-group">
				<c:choose>
					<c:when test="${kdmcEnv eq 'Y'}">
						<label class="col-sm-2 control-label required-control" for="deptId"><spring:message
						code="master.complaint.type" /></label>
					</c:when>
					<c:otherwise>
						<label class="col-sm-2 control-label required-control" for="deptId"><spring:message
						code="master.complaint.department" /></label>
					</c:otherwise>
				</c:choose>
				
				<div class="col-sm-4">
					<c:if test="${command.modeType eq 'C'}">
						<form:select path="departmentComplaint.deptId" id="deptId"
							onchange="deptChange();"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="0">
								<spring:message code="master.complaint.select.department" />
							</form:option>
							<c:forEach items="${command.deptList}" var="objArray">
								<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${objArray[0]}" label="${objArray[2]}"></form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</c:if>
					<c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
						<form:input path="departmentComplaint.deptName"
							class="form-control" readonly="true" />
					</c:if>
				</div>
			</div>
			<c:set var="count" value="0" scope="page" />
			<div class="table-responsive margin-top-10">
				<table class="table table-bordered table-striped" id="customFields">
					<tbody>
						<tr>
							<c:choose>
								<c:when test="${kdmcEnv eq 'Y'}">
									<th><spring:message code="master.complaint.subType"
											text="Complaint SubType" /><span class="mand">*</span></th>
								</c:when>
								<c:otherwise>
									<th><spring:message code="common.master.complain.type"
											text="Complaint Type" /><span class="mand">*</span></th>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${kdmcEnv eq 'Y'}">
									<th><spring:message
									code="common.master.complain.subType.regional"
									text="Complaint Subtype Regional" /><span class="mand">*</span></th>
								</c:when>
								<c:otherwise>
									<th><spring:message
									code="common.master.complain.type.regional"
									text="Complaint Type Regional" /><span class="mand">*</span></th>
								</c:otherwise>
							</c:choose>
							<th><spring:message code="common.master.complain.resident"
									text="Resident Id (Y/N)" /></th>
							<th><spring:message code="common.master.complain.dues"
									text="Amount Dues(Y/N)" /></th>
							<th><spring:message code="common.master.complain.isDocument"
									text="Document Req" /></th>
							<th><spring:message code="common.master.complain.otpRequired"
									text="Anonymous Login" /></th>
									<th><spring:message code="common.master.complain.externalFlag"
									text="External WorkFlow Flag" /></th>
							<th width="100"><spring:message code="common.master.active"
									text="Active" /><span class="mand">*</span></th>
									
							<!-- <th scope="col" width="8%"><a onclick='return false;'
								class="btn btn-blue-2 btn-sm addCF" title="Add"><i
									class="fa fa-plus-circle"></i></a></th> -->
							<c:if test="${command.modeType ne 'V'}">
								<th class="text-center" scope="col" width="10%">
								<span class="small"><spring:message code="care.common.addRemove" text="Add/Remove" /></span></th>
							</c:if>
							
						</tr>
						<c:if test="${command.modeType eq 'C'}">
							<tr class="appendableClass">
								<td><form:hidden
										path="departmentComplaint.complaintTypesList[${count}].compId"
										id="compId_${count}"></form:hidden> <form:input
										path="departmentComplaint.complaintTypesList[${count}].complaintDesc"
										class="form-control mandColorClass"
										onkeyup="hasAlphaNumeric(event.keyCode,this);"
										id="complaintDesc_${count}" maxlength="500" /></td>
								<td><form:input
										path="departmentComplaint.complaintTypesList[${count}].complaintDescreg"
										class="form-control mandColorClass"
										id="complaintDescreg_${count}" maxlength="500" /></td>


								<td><form:select
										path="departmentComplaint.complaintTypesList[${count}].residentId"
										class="form-control" id="complaintResident_${count}">

										<form:option value="0">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>

								

								<td><form:select
										path="departmentComplaint.complaintTypesList[${count}].amtDues"
										class="form-control" id="complaintAmtDues_${count}">
										<form:option value="0">Select</form:option>
										<form:option value="Y">Yes</form:option>
										<form:option value="N">No</form:option>
									</form:select></td>
									
								<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].documentReq"
											class="form-control" id="complaintDocument_${count}">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select></td>

								<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].otpValidReq"
											class="form-control" id="complaintOTP_${count}">
											<form:option value="0">Select</form:option>
											<form:option value="Y" selected="selected">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select></td>
										
								<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].externalWorkFlowFlag"
											class="form-control" id="externalFlag_${count}">
											<form:option value="Y">Yes</form:option>
											<form:option value="N" selected="selected">No</form:option>
									</form:select></td>
										
								<td><form:checkbox
										path="departmentComplaint.complaintTypesList[${count}].isActive"
										disabled="true" class="margin-left-20 margin-top-10 isActive"
										id="isActive_${count}" /></td>
								<td class="text-center">
									<a href="javascript:void(0);"
										class="addCF btn btn-success btn-sm" title="Add"><i
											class="fa fa-plus-circle"></i></a>
									
									<a href="javascript:void(0);"
										class="remCF btn btn-danger btn-sm" title="Delete"><i
											class="fa fa-trash"></i></a>
								</td>
							</tr>
						</c:if>
						<c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
							<form:hidden path="departmentComplaint.orgId" id="orgIdHidden" />
							<form:hidden path="departmentComplaint.deptId" id="deptIdHidden" />
							<c:forEach
								items="${command.departmentComplaint.complaintTypesList}"
								var="complaintType">
								<tr class="appendableClass">
									<td><form:hidden
											path="departmentComplaint.complaintTypesList[${count}].compId"
											id="compId_${count}"></form:hidden> <form:input
											path="departmentComplaint.complaintTypesList[${count}].complaintDesc"
											class="form-control mandColorClass"
											onkeyup="hasAlphaNumeric(event.keyCode,this);"
											id="complaintDesc_${count}" maxlength="500" /></td>
									<td><form:input
											path="departmentComplaint.complaintTypesList[${count}].complaintDescreg"
											class="form-control mandColorClass"
											id="complaintDescreg_${count}" maxlength="500" /></td>
									

									<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].residentId"
											class="form-control" id="complaintResident_${count}">

											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select></td>

									
									<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].amtDues"
											class="form-control" id="complaintAmtDues_${count}">

											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select></td>
										
									<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].documentReq"
											class="form-control" id="complaintDocument_${count}">

											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select></td>
									
									<td><form:select
											path="departmentComplaint.complaintTypesList[${count}].otpValidReq"
											class="form-control" id="complaintOTP_${count}">

											<form:option value="0">Select</form:option>
											<form:option value="Y" selected="selected">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select></td>
										
								  <td><form:select
											path="departmentComplaint.complaintTypesList[${count}].externalWorkFlowFlag"
											class="form-control" id="externalFlag_${count}">
											
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
									</form:select></td>


									<td><form:checkbox
											path="departmentComplaint.complaintTypesList[${count}].isActive"
											class="margin-left-20 margin-top-10" id="isActive_${count}" /></td>
											
									<c:if test="${command.modeType ne 'V'}">
										<td class="text-center">
											<a href="javascript:void(0);"
												class="addCF btn btn-success btn-sm" title="Add"><i
													class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);"
												data-placement="top" class="remCF1 btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a>
										</td>
									</c:if>
									
								</tr>
								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="text-center padding-top-10">
				<c:if test="${command.modeType ne 'V'}">
					<button type="button" class="btn btn-success btn-submit"
						id="submitComp">
						<spring:message code="common.master.save" text="Save" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C'}">
					<button type="Reset" class="btn btn-warning" id="resetComp">
						<spring:message code="bt.clear" />
					</button>
				</c:if>
				<input type="button" id="backBtn" class="btn btn-danger"
					onclick="back()" value="<spring:message code="bt.backBtn"/>" />
			</div>
			<form:hidden path="removeChildIds" />
		</form:form>
	</div>
</div>
