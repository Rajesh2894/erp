<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/account/receivableDemandEntry.js"></script>
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="receivable.demand.entry.form" text="Receivable Demand Entry Form" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="ReceivableDemandEntry.html" commandName="command" class="form-horizontal form" name="ReceivableDemandEntry" id="id_ReceivableDemandEntry">
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<form:hidden path="receivableDemandEntryDto.taxCount" id="taxCount" />
				<form:hidden path="receivableDemandEntryDto.sliStatus" id="sliStatus" />
				<input type="hidden" id="langId" value="${userSession.languageId}" />
				<input type="hidden" id="taxIdFound" value="${taxIdFound}">
				<input type="hidden" id="saveMode" value="${command.saveMode}">	
				<input type="hidden" id="wardIdn" value="${command.receivableDemandEntryDto.wardIdnPattern}">
				<input type="hidden" id="isNewIdn" value="${command.receivableDemandEntryDto.newIdn}">

				<div class="form-group padding-top-20">

					<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.deptName" text="Department Name" /></label>
					<div class="col-sm-4">
						<form:select class="form-control  required-control" id="deptId" path="receivableDemandEntryDto.deptId" onchange="refreshServiceData()">
							
							<c:choose>
								<c:when test="${userSession.languageId eq 1}">
									<c:forEach items="${command.deptList}" var="deptData">
										<option value="${deptData.dpDeptid}">${deptData.dpDeptdesc}</option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.deptList}" var="deptData">
										<option value="${deptData.dpDeptid}">${deptData.dpNameMar}</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select class="form-control" id="serviceId" path="receivableDemandEntryDto.serviceId" onchange="">
							<option value="">Select Service</option>
						</form:select>
					</div>

				</div>
				<div class="form-group ">
					<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.casNo" text="Case No." /></label>
					<div class="col-sm-4">
						<form:input path="receivableDemandEntryDto.caseNo" onchange="" readonly="" cssClass="form-control  mandColorClass " autocomplete="off" id="caseNo" disabled="" maxlength="20"/>
					</div>

					<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.locWard" text="Location" /></label>
					<div class="col-sm-4">
						<form:input path="receivableDemandEntryDto.locName" onchange="" readonly="true" cssClass="form-control  mandColorClass " autocomplete="off" id="LocName" disabled="" />

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="receivable.demand.entry.refNo" text="Reference Number" /></label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton id="refFlag1" path="receivableDemandEntryDto.refFlag" value="A" disabled="" /> <spring:message code="receivable.demand.entry.appNumber" text="IDN No." />
						</label> <label class="radio-inline "> <form:radiobutton id="refFlag2" path="receivableDemandEntryDto.refFlag" value="R" checked="" disabled="" /> <spring:message code="receivable.demand.entry.refNumber" text="CCN No." />
						</label> <label class="radio-inline "> <form:radiobutton id="refFlag3" path="receivableDemandEntryDto.refFlag" value="N" checked="" disabled="" /> <spring:message code="receivable.demand.entry.new" text="New" />
						</label>
					</div>

					<label class="col-sm-2 control-label required-control rclass" id="rNo" for=""><spring:message code="receivable.demand.entry.refNumber" text="CCN Number" /></label> <label
						class="col-sm-2 control-label required-control aclass" id="aNo" for=""><spring:message code="receivable.demand.entry.appNumber" text="IDN Number" /></label>
					<div class="col-sm-4 fclass">
						<form:input path="receivableDemandEntryDto.refNumber" cssClass="form-control refStatus" id="refNumber" readonly="" disabled="" maxlength="12"/>
					</div>
				</div>

				<div class="text-center padding-bottom-10" id="id_search">
					<button type="button" class="btn btn-success searchData" onclick="getTaxDetails()">
						<i class="fa fa-search"></i>
						<spring:message code="master.search" text="Search" />
					</button>
					<button type="button" class="btn btn-blue-2 createData addS" onclick="getTaxDetails()">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="add.msg" text="Add" />
					</button>
					<button type="button" class="btn btn-danger" onclick="javascript:openRelatedForm('ReceivableDemandEntry.html');">
						<i class="fa fa-chevron-circle-left padding-right-5" aria-hidden="true"></i>
						<spring:message code="back.msg" text="Back" />
					</button>
					         
				</div>


				<div class=" padding-top-20" id="receivableEntryTable">
					<jsp:include page="/jsp/account/transactions/receivableDemandEntryFormTable.jsp" />
				</div>

			</form:form>
		</div>
	</div>
</div>





