<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/nocForOtherGovtDept.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="NOCForOtherGovtDept.html" method="post"
				class="form-horizontal" name="nocForOtherGovtDeptSummary"
				id="nocForOtherGovtDeptSummary">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="NOC.service.name"
						text="NOC for Other Govt. Department" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select path="serviceId"
							class="form-control mandColorClass chosen-select-no-results"
							id="serviceId">

							<form:option value="">
								<spring:message code="work.management.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${userSession.languageId eq 1}">
									<c:forEach items="${command.tbServicesMsts}"
										var="tbServicesMst">
										<form:option value="${tbServicesMst.smServiceId }">${tbServicesMst.smServiceName }</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.tbServicesMsts}"
										var="tbServicesMst">
										<form:option value="${tbServicesMst.smServiceId }">${tbServicesMst.smServiceNameMar }</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.application.no" text="Application Number" /></label>
					<div class="col-sm-4">
						<form:select path="refId"
							class="form-control mandColorClass chosen-select-no-results"
							id="appId">

							<form:option value="">
								<spring:message code="work.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.refIds}" var="refId">
								<form:option value="${refId}">${refId}</form:option>
							</c:forEach>

						</form:select>
					</div>


				</div>

				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchData()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="NHP.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetSummaryForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="NHP.reset" text="Reset"></spring:message>
					</button>


					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="formForCreate()">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="NHP.add" text="Add"></spring:message>
					</button>
				</div>

				<form:hidden path="" id="flag" value="${command.flag}" />

				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="configurationId" class="configurationTable">
							<!-- class="table table-striped table-condensed table-bordered"
							id="sequenceTable" -->
							<thead>
								<tr>
									<th><spring:message code="NHP.application.number"
											text="Application Number" /></th>
									<th><spring:message code="NHP.service.name"
											text="Service Name" /></th>
									<th><spring:message code="NHP.first.name"
											text="First Name" /></th>
									<th><spring:message code="NHP.last.name" text="Last Name" /></th>

									<th><spring:message code="NHP.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.homeSummaryDtos}" var="data"
									varStatus="index">
									<tr>
										<form:hidden path="" id="appId" value="${data.appId}" />
										<td>${data.refNo}</td>
										<td>${data.serviceName}</td>
										<td>${data.fName}</td>
										<td>${data.lName}</td>

										<td class="text-center">

											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View NOC Form Data"
												onclick="viewNOCFormData('NOCForOtherGovtDept.html','viewNOCFormData','${data.appId}')">
												<i class="fa fa-eye"></i>

											</button>
										</td>

									</tr>
								</c:forEach>
							</tbody>


						</table>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>
