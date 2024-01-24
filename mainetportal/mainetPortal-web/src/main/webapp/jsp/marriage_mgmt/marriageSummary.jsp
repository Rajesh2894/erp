<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/marriage_mgmt/marriageSummary.js"></script>
<div id="searchAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="mrm.marriage.summary" />
				</h2>
				<apptags:helpDoc url="MarriageRegistration.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">

					<form:form id="marriageSearch" name="marriageSearch"
						class="form-horizontal" action="MarriageRegistration.html"
						method="post">

						<div class="compalint-error-div">
							<jsp:include page="/jsp/tiles/validationerror.jsp" />
							<div
								class="warning-div error-div alert alert-danger alert-dismissible"
								id="errorDiv"></div>
						</div>

						<div class="form-group">
							<apptags:date fieldclass="datepicker"
								labelCode="mrm.marriage.marriageDate"
								datePath="marriageDTO.marDate"></apptags:date>

							<apptags:date fieldclass="datepicker"
								labelCode="mrm.marriage.applicationDate"
								datePath="marriageDTO.appDate"></apptags:date>
						</div>
						<div class="form-group">

							<label class="col-sm-2 control-label" for="applicantId">
								<spring:message code="mrm.acknowledgement.applicantName" />
							</label>

							<div class="col-sm-4 ">
								<form:select path="" id="husbandId"
									cssClass="form-control chosen-select-no-results"
									class="form-control mandColorClass" data-rule-required="true">
									<form:option value="">Select</form:option>
									<c:forEach items="${command.husbandList}" var="husband">
										<form:option value="${husband.husbandId}">${husband.firstNameEng} ${husband.lastNameEng}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="mrm.status" text="Status" /></label>
							<div class="col-sm-4">
								<form:select path="" id="status"
									class="chosen-select-no-results" data-rule-required="true">
									<form:option value="">
										<spring:message code="mrm.select" />
									</form:option>
									<form:option value="D">
										<spring:message code="" text="DRAFT"></spring:message>
									</form:option>
									<form:option value="PENDING">
										<spring:message code="" text="PENDING"></spring:message>
									</form:option>
									<form:option value="PROCESSING">
										<spring:message code="" text="PROCESSING"></spring:message>
									</form:option>
									<form:option value="CONCLUDE">
										<spring:message code="" text="CONCLUDE"></spring:message>
									</form:option>
								</form:select>
							</div>
						</div>

						<div class="text-center clear padding-10">
							<button class="btn btn-blue-2  search" id="searchMRM"
								type="button">
								<i class="button-input"></i>
								<spring:message code="mrm.button.search" />
							</button>
							<button type="button"
								onclick="window.location.href='MarriageRegistration.html'"
								class="btn btn-warning" title="Reset">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="mrm.button.reset" text="Reset" />
							</button>
							<button class="btn btn-success add" id="AddMarriageRegisration"
								type="button">
								<i class="button-input"></i>
								<spring:message code="mrm.button.add" />
							</button>


						</div>
						<!-- End button -->

						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="searchMarriage">
								<thead>
									<tr>
										<%-- <td width="3%" align="center"><spring:message
												code="mrm.srno" text="Sr.No" /></td> --%>
										<td align="center"><spring:message
												code="mrm.marriage.appNo" text="Application No" /></td>
										<td align="center"><spring:message code=""
												text="Applicant Name" /></td>
										<td align="center"><spring:message
												code="mrm.marriage.marriageDate" text="Marriage Date" /></td>
										<td align="center"><spring:message
												code="mrm.marriage.applicationDate" text="Application Date" /></td>
										<td align="center" width="10%"><spring:message
												code="mrm.status" text="Status" /></td>
										<td align="center"><spring:message code="mrm.action"
												text="Action" /></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.marriageDTOs}" var="summaryDTO"
										varStatus="index">
										<tr>
											<%-- <td class="text-center">${index.count}</td> --%>
											<td align="center">${summaryDTO.applicationId}</td>
											<td align="center">${summaryDTO.applicantName}</td>
											<td align="center"><fmt:formatDate
													value="${summaryDTO.marDate}" pattern="dd-MM-yyyy" /></td>
											<td align="center"><fmt:formatDate
													value="${summaryDTO.appDate}" pattern="dd-MM-yyyy" /></td>
											<td align="center">${summaryDTO.status}</td>


											<td class="text-center"><c:if
													test="${summaryDTO.status eq 'D'}">
													<button type="button" class="btn btn-danger btn-sm btn-sm"
														onClick="draftMRM(${summaryDTO.marId})"
														title="<spring:message code="mrm.summary.draft" text="Draft"></spring:message>">
														<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
													</button>
												</c:if>
												<button type="button" class="btn btn-blue-2 btn-sm"
													onClick="viewMRM(${summaryDTO.marId})"
													title="<spring:message code="mrm.summary.view"></spring:message>">
													<i class="fa fa-eye"></i>
												</button> <c:if
													test="${not empty summaryDTO.serialNo && summaryDTO.serialNo ne 'NA' }">
													<button type="button" class="btn btn-danger btn-sm btn-sm"
														onClick="rePrintCertificate(${summaryDTO.marId})"
														title="<spring:message code="" text="Re-Print"></spring:message>">
														<i class="fa fa-print" aria-hidden="true"></i>
													</button>
												</c:if></td>

										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
