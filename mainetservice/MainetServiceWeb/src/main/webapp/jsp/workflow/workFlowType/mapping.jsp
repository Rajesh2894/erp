<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="js/workflow/workFlowType/mapping.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<form:form name="workFlowSubTypeForm" id="workFlowSubTypeForm">
	<c:set var="count" value="0" scope="page" />
	<!--  <div class="table-responsive" > -->
	<table class="table table-bordered table-striped" id="customFields">
		<tbody>
			<tr>
				<th width="80"><spring:message code="workflow.event"
						text="Event" /><span class="mand">*</span></th>
				<th width="100"><spring:message code="workflow.org"
						text="Organization" /><span class="mand">*</span></th>
				<th width="100"><spring:message code="workflow.dept"
						text="Department" /><span class="mand">*</span></th>
				<th width="80"><spring:message code="workflow.roleEmp"
						text="Role/Emp" /><span class="mand">*</span></th>
				<th width="100"><spring:message code="workflow.details"
						text="Details" /><span class="mand">*</span></th>
				<th width="30"><spring:message code="workflow.sla" text="SLA" /></th>
				<th width="80"><spring:message code="workflow.units"
						text="Units" /></th>
				<th width="30"><spring:message code="workflow.appr"
						text="Number of Approvers" /><span class="mand">*</span></th>
				<th width="70"><spring:message code="workflow.addRemove"
						text="Add/Remove" /></th>
			</tr>
			<tr class="appendableClass">
				<td><form:input type="hidden" id="id_${count}" value=""
						path="workFlowMasDTO.workflowDet[${count}].wfdId" /> <form:select
						path="workFlowMasDTO.workflowDet[${count}].eventId"
						class="form-control"
						onchange="checkForDuplicateEvent(this,${count});"
						id="eventMasterId_${count}">
						<form:option value="0">
							<spring:message code="selectdropdown" text="select" />
						</form:option>
						<c:forEach items="${command.eventList}" var="objArray">
							<c:if test="${userSession.languageId eq 1}">
								<form:option value="${objArray[3]}">${objArray[0]}</form:option>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								<form:option value="${objArray[3]}">${objArray[1]}</form:option>
							</c:if>
						</c:forEach>
					</form:select></td>

				<td><form:select
						path="workFlowMasDTO.workflowDet[${count}].mapOrgId"
						class="form-control" id="mapOrgId_${count}"
						onchange="showMappingDept(this);">
						<form:option value="0">
							<spring:message code="selectdropdown" text="select" />
						</form:option>
						<c:forEach items="${command.orgList}" var="orgArray">
							<c:if test="${userSession.languageId eq 1}">
								<form:option value="${orgArray[0]}" label="${orgArray[1]}"></form:option>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								<form:option value="${orgArray[0]}" label="${orgArray[2]}"></form:option>
							</c:if>
						</c:forEach>
					</form:select></td>

				<td><form:select
						path="workFlowMasDTO.workflowDet[${count}].mapDeptId"
						class="form-control" id="mapDeptId_${count}">
						<form:option value="0">
							<spring:message code="selectdropdown" text="select" />
						</form:option>
					</form:select></td>

				<td><form:select
						path="workFlowMasDTO.workflowDet[${count}].roleType"
						class="form-control" id="roleType_${count}"
						onchange="showEmpOrRole(this);">
						<form:option value="0">
							<spring:message code="selectdropdown" text="select" />
						</form:option>
						<form:option value="R">
							<spring:message code="workflow.role" text="ROLE" />
						</form:option>
						<form:option value="E">
							<spring:message code="workflow.employee" text="Employee" />
						</form:option>
					</form:select></td>
				<td class="mul_${count}"><form:select
						path="workFlowMasDTO.workflowDet[${count}].roleOrEmpIds"
						class="form-control multiple-chosen" multiple="multiple"
						id="roleOrEmpId_${count}">
						<%-- <form:option value="0"><spring:message code="selectdropdown" text="select"/></form:option> --%>
					</form:select></td>
				<td><form:input path="workFlowMasDTO.workflowDet[${count}].sla"
						class="form-control " id="sla_${count}" maxlength="6"
						onkeypress="return hasAmount(event, this, 5, 2)"
						onchange="getAmountFormatInDynamic((this),'sla')" /></td>
				<td><c:set var="baseLookupCode" value="UTS" /> <form:select
						path="workFlowMasDTO.workflowDet[${count}].unit"
						class="form-control" id="units_${count}">
						<form:option value="0">
							<spring:message code="selectdropdown" text="select" />
						</form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}"
							var="lookUp">
							<c:if
								test="${lookUp.lookUpCode eq 'D' || lookUp.lookUpCode eq 'H'}">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:if>
						</c:forEach>
					</form:select></td>
				<td><form:input
						path="workFlowMasDTO.workflowDet[${count}].apprCount"
						class="form-control hasNumber" maxlength="2"
						id="apprCount_${count}" /></td>

				<td align="center"><a href="javascript:void(0);"
					data-toggle="tooltip" data-placement="top"
					class="addCF btn btn-success btn-sm"><i
						class="fa fa-plus-circle"></i></a> <a href="javascript:void(0);"
					data-toggle="tooltip" data-placement="top"
					class="remCF btn btn-danger btn-sm"><i class="fa fa-minus"></i></a></td>
			</tr>
		</tbody>
	</table>
	<!--  </div>   -->
</form:form>


