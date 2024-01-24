<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/common/supplementaryBillEntry.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!-- <style>
.search-table-outter {
	overflow-x: scroll;
}

th, td {
	min-width: 100px;
}
</style> -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Supplementary Salary Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="SupplementaryPayBillEntry.html" name="SupplementaryPayBillEntryForm" 
					id="SupplementaryPayBillEntryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>
				

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="suppMonth"><spring:message
							code="" text="Supplementary Month" /></label>
					<div class="col-sm-4">
						<form:select path=""
							id="suppMonth" cssClass="form-control chosen-select-no-results"
							data-rule-required="false" onchange="validateMonthYearData()">
							<c:set var="baseLookupCode" value="MON" />
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.otherField}" code="${lookUp.lookUpCode}" >${lookUp.otherField}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label for="suppYear" class="control-label col-sm-2 required-control"><spring:message
							code="" text="Supplementary Year" /></label>
					<div class="col-sm-4">
						<form:select path=""
							id="suppYear" cssClass="form-control chosen-select-no-results"
							data-rule-required="false" onchange="validateMonthYearData()">
							<c:set var="baseLookupCode" value="CYS" />
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpDesc}" >${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="supptype">
						<spring:message code="" text="Supplementary Type" /></label>
					<div class="col-sm-4">
						<form:select path=""
							id="suppType" cssClass="form-control chosen-select-no-results"
							data-rule-required="false" onchange="">
							<c:set var="baseLookupCode" value="SBT" />
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="table-responsive">
					<c:set var="d" value="0" scope="page" />
					<table class="table table-bordered table-striped" id="employeeDataTableId">
						<thead>
							<tr>
								<th><spring:message
										code="" text="Sr. No." /></th>
								<th class="required-control"><spring:message
										code="" text="Employee Id" /></th>
								<th width="10%" class="required-control"><spring:message
										code="" text="Pay Month" /></th>
								<th width="10%" class="required-control"><spring:message
										code="" text="Pay Year" /></th>
								<th><spring:message code="" text="Employee Name" /></th>
								<th><spring:message code="" text="EL" /></th>
								<th><spring:message code="" text="ML" /></th>
								<th><spring:message code="" text="HPL" /></th>
								<th><spring:message code="" text="Work Days" /></th>
								<th><spring:message code="" text="Remark" /></th>
								<th><spring:message code="" text="Gross Amount" /></th>
								<th><spring:message code="" text="Deduction Amount" /></th>
								<th><spring:message code="" text="Net Pay" /></th>
								<th width="5%"><spring:message code="" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty command.supplimentartPayBillDTOList}">
									<tr class="empDetailsRow">
										<td><input type="text" class="form-control text-center"
											disabled="true" id="srNo${d}" value="${d+1}" /></td>
										<td><form:input path="supplimentartPayBillDTOList[${d}].empId" id="empId${d}"
												onchange="getEmployeeData(${d})" class="form-control hasNumber" maxlength="5"/></td>
										<td><form:select path="supplimentartPayBillDTOList[${d}].payMonth" id="payMonth${d}" 
												onchange="getEmployeeData(${d})"
												cssClass="form-control chosen-select-no-results" data-rule-required="false" >
												<c:set var="baseLookupCode" value="MON" />
												<form:option value="">
													<spring:message code="" text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
													<form:option value="${lookUp.otherField}" code="${lookUp.lookUpCode}" >${lookUp.otherField}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="supplimentartPayBillDTOList[${d}].payYear" id="payYear${d}" 
												onchange="getEmployeeData(${d})"
												cssClass="form-control chosen-select-no-results" data-rule-required="false" >
												<c:set var="baseLookupCode" value="CYS" />
												<form:option value="">
													<spring:message code="" text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
													<form:option value="${lookUp.lookUpDesc}" >${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><ul><li><form:input path="supplimentartPayBillDTOList[${d}].empName" id="empName${d}" 
														class="form-control"  readonly="true"/></li>
												<li>Basic: <form:input path="" id="basicPay${d}" class="form-control" readonly="true" /> 
													Grade: <form:input path="" id="gradePay${d}" class="form-control" readonly="true"/></li>
												</br><li><form:input path="" id="designation${d}" class="form-control" placeholder="Designation"
														readonly="true"/></li></ul>											
											</td>
										<td><form:hidden path="" id="refELeave${d}" />
											<form:input path="supplimentartPayBillDTOList[${d}].eLeave" id="eLeave${d}" 
												onchange="getEmployeePayDetails(${d})" class="form-control" 
												onkeypress="return hasAmount(event, this, 2, 2)"/></td>
										<td><form:hidden path="" id="refMLeave${d}" />
											<form:input path="supplimentartPayBillDTOList[${d}].mLeave" id="mLeave${d}" 
												onchange="getEmployeePayDetails(${d})"  class="form-control" 
												onkeypress="return hasAmount(event, this, 2, 2)"/></td>
										<td><form:hidden path="" id="refHpLeave${d}" />
											<form:input path="supplimentartPayBillDTOList[${d}].hpLeave" id="hpLeave${d}" 
												onchange="getEmployeePayDetails(${d})"  class="form-control"
												onkeypress="return hasAmount(event, this, 2, 2)" /></td>
										<td><form:hidden path="" id="refWorkDays${d}" />
											<form:input path="supplimentartPayBillDTOList[${d}].workDays" id="workDays${d}" 
												onchange="getEmployeePayDetails(${d})" class="form-control" 
												onkeypress="return hasAmount(event, this, 2, 2)"/></td>
										<td><form:input path="supplimentartPayBillDTOList[${d}].remark" id="remark${d}" 
												class="form-control" maxlength="1000"/></td>
										<td><form:input path="supplimentartPayBillDTOList[${d}].grossAmount" id="grossAmount${d}" 
												class="form-control" readonly="true" /></td>
										<td><form:input path="supplimentartPayBillDTOList[${d}].deductionAmount" id="deductionAmount${d}" 
												class="form-control" readonly="true" />
											<div class="padding-top-10 text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View" id="viewDetail${d}" onclick="">
													<i class="fa fa-eye"></i>
												</button>
											</div></td>
										<td><form:input path="supplimentartPayBillDTOList[${d}].netPay" id="netPay${d}" class="form-control"
												readonly="true" /></td>
										<td class="text-center">
											<a href="javascript:void(0);"
												title="<spring:message code="" text="Add" />"
												onclick="addUnitRowInBatch(this);" class="btn btn-success btn-sm " id=""><i
												class="fa fa-plus-circle"></i></a> 
											<a href="javascript:void(0);"
												title="<spring:message code="material.management.delete" text="Delete" />"
												class="btn btn-danger btn-sm delete deleteEmployeeDataRow"
												id="deleteEmployeeDataRow{d}"><i class="fa fa-trash-o"></i></a>
										</td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.supplimentartPayBillDTOList}">
										

										<c:set var="d" value="${d + 1}" scope="page" />	
									</c:forEach>								
								</c:otherwise>
							</c:choose>							
						</tbody>
					</table>
				</div>
				
				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						title='<spring:message code="works.management.save" text="Save" />'
						onclick="submitSupplimentoryBill(this);"
						title="<spring:message code="works.management.save" text="Save"></spring:message>">
						<spring:message code="works.management.submit" text="Submit" />
					</button>

					<button class="btn btn-warning" type="button" id=""
						title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="addSupplementaryPayBill('SupplementaryPayBillEntry.html','addSupplementaryPayBill');"
						title="<spring:message code="works.management.reset" text="Reset"></spring:message>">
						<spring:message code="works.management.reset" text="Reset" />
					</button>

					<apptags:backButton url="SupplementaryPayBillEntry.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>