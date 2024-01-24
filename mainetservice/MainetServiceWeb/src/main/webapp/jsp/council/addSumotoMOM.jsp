<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/council/sumoto.js"></script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.meeting.details.title"
					text="Meeting Details" />
			</h2>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilMOM.html" cssClass="form-horizontal"
				id="sumotoPage">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeSumotoIds" id="removeSumotoIds" />

				<h4 class="margin-bottom-10">
					<spring:message code="council.sumoto.title"
						text="Add Sumoto Resolution" />
				</h4>
				<c:set var="d" value="0" scope="page" />

				<table class="table table-bordered table-striped"
					id="sumotoDetailsTab">
					<thead>
						<tr>
							<th scope="col" width="3%"><spring:message
									code="council.srno" text="Sr. No." /><span class="mand">*</span></th>
							<th scope="col"><spring:message
									code="council.sumoto.resolutionNo" text="Resolution No" /><span
								class="mand"></span></th>
							<th><spring:message code="council.sumoto.department"
									text="Department" /><span class="mand">*</span></th>
							<th><spring:message code="council.sumoto.resolutionDetails"
									text="Details Of Resolution" /><span class="mand">*</span></th>
							<th><spring:message code="council.mom.amount" text="Amount" /><span
								class="mand">*</span></th>
							<th class="text-center">Resolution Comments<span
								class="mand">*</span></th>
							<th class="text-center">Action Taken<span class="mand">*</span></th>

							<c:if test="${command.saveMode eq 'ADD' }">
								<th scope="col" width="8%"><a onclick='return false;'
									class="btn btn-blue-2 btn-sm addReButton"> <i
										class="fa fa-plus-circle"></i></a></th>
							</c:if>
						</tr>
					</thead>

					<c:forEach var="sumotoData" items="${command.momSumotoDtos}"
						varStatus="status">
						<tr class="appendableClass"
							id="${command.momSumotoDtos[d].sumotoResoId}">
							<td align="center"><form:input path=""
									cssClass="form-control mandColorClass " id="sequence${d}"
									value="${d+1}" disabled="true" /> <form:input type="hidden"
									path="momSumotoDtos[${d}].sumotoResoId" id="sumotoId${d}" /></td>
							<td><form:input path="momSumotoDtos[${d}].resolutionNo"
									cssClass="form-control text-center" readonly="true" /></td>

							<td><form:select path="momSumotoDtos[${d}].sumotoDepId"
									class="form-control chosen-select-no-results" id="sumotoDep${d}"
									disabled="${command.saveMode eq 'VIEW'}">
									<form:option value="">
										<spring:message code="holidaymaster.select" />
									</form:option>
									<c:forEach items="${command.departmentList}" var="lookUp">
										<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:input id="detailsOfReso${d}"
									path="momSumotoDtos[${d}].detailsOfReso"
									cssClass="form-control text-center"
									disabled="${command.saveMode eq 'VIEW'}" /></td>

							<td><form:input id="amount${d}"
									path="momSumotoDtos[${d}].amount"
									cssClass="form-control text-right"
									onkeypress="return hasAmount(event, this, 7, 2)"
									onchange="getAmountFormatInDynamic((this),'meActLength')"
									disabled="${command.saveMode eq 'VIEW'}" /></td>

							<td><form:textarea id="resolutionComment${d}"
									path="momSumotoDtos[${d}].resolutionComment"
									class="form-control" readonly="${command.saveMode eq 'VIEW'}" /></td>

							<td><form:select id="status${d}"
									path="momSumotoDtos[${d}].status" cssClass="form-control chosen-select-no-results"
									disabled="${command.saveMode eq 'VIEW' }">
									<%-- <form:option value="">
											<spring:message code='council.dropdown.select' />
										</form:option>
										<form:option value="A">
											<spring:message code='council.mom.Approved' />
										</form:option>
										<form:option value="R">
											<spring:message code='council.mom.Rejected' />
										</form:option>
										<form:option value="P">
											<spring:message code='council.mom.Pending' />
										</form:option> --%>
									<form:option value="0">
										<spring:message code='council.dropdown.select' />
									</form:option>
									<c:forEach items="${command.getLevelData('PPS')}" var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>

							<c:if test="${command.saveMode eq 'ADD'}">
								<td class="text-center"><a href='#' onclick='return false;'
									class='btn btn-danger btn-sm delButton'><i
										class="fa fa-trash"></i></a></td>
							</c:if>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:forEach>

				</table>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'VIEW'}">
						<button class="btn btn-success save"
							onclick="saveSumotoDetails(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="council.sumoto.save" text="SAVE" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backToCreateMOMForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="council.sumoto.back" text="BACK" />
					</button>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->