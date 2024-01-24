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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Summery Supplementary Salary Details" />
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
					<label class="control-label col-sm-2" for="suppMonth"><spring:message
							code="" text="Supplementary Month" /></label>
					<div class="col-sm-4">
						<form:select path=""
							id="suppMonth" cssClass="form-control"
							data-rule-required="false" onchange="">
							<c:set var="baseLookupCode" value="MON" />
							<form:option value="0">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label for="suppYear" class="control-label col-sm-2"><spring:message
							code="" text="Supplementary Year" /></label>
					<div class="col-sm-4">
						<form:select path=""
							id="suppYear" cssClass="form-control"
							data-rule-required="false" onchange="">
							<c:set var="baseLookupCode" value="CYS" />
							<form:option value="0">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpDesc}" >${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="supptype">
						<spring:message code="" text="Supplementary Type" /></label>
					<div class="col-sm-4">
						<form:select path=""
							id="suppType" cssClass="form-control"
							data-rule-required="false" onchange="">
							<c:set var="baseLookupCode" value="SBT" />
							<form:option value="0">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2" for="empId"><spring:message
							code="" text="Employee ID" /></label>
					<div class="col-sm-4">
						<form:input path="" class="form-control" />
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchSupplementaryPayBill()">
						<i class="fa fa-search"></i><spring:message code="" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='SupplementaryPayBillEntry.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success" name="button-Add" id="button-submit"
						onclick="addSupplementaryPayBill('SupplementaryPayBillEntry.html','addSupplementaryPayBill');" >
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th width="2%"><spring:message
										code="" text="Employee Id" /></th>
								<th width="5%"><spring:message
										code="" text="Pay Month" /></th>
								<th width="5%"><spring:message
										code="" text="Pay Year" /></th>
								<th width="15%"><spring:message code=""
										text="Employee Name" /></th>
								<th width="8%"><spring:message code="" text="EL" /></th>
								<th width="8%"><spring:message code="" text="ML" /></th>
								<th width="8%"><spring:message code="" text="HPL" /></th>
								<th width="8%"><spring:message code="" text="Work Days" /></th>
								<th width="10%"><spring:message code="" text="Remark" /></th>
								<th width="5%"><spring:message code="" text="Gross Amount" /></th>
								<th width="5%"><spring:message code=""
										text="Deduction Amount" /></th>
								<th width="5%"><spring:message code="" text="Net Pay" /></th>
								<th width="5%"><spring:message code="" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"></td>
								<td class="text-center"><!-- <div class="input-group">
										<input path="" class="form-control" readonly="true" />
									</div> -->
									<div class="padding-top-10">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View" onclick="">
											<i class="fa fa-eye"></i>
										</button>
									</div></td>
								<td class="text-center"></td>
								<td class="text-center"><a href='#' data-toggle="tooltip"
									data-placement="top"
									class="btn btn-warning btn-sm delButton margin-top-10"
									onclick=""
									title="<spring:message code="works.management.edit" text="Edit"></spring:message>"><i
										class="fa fa-edit"></i></a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>