<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/works_management/scheduleofrate.js"></script>
<!-- Schedule of rate Master home page jsp -->
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="sor" text="Schedule Of Rate Master" />
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url="WmsMaterialMaster.html"></apptags:helpDoc>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form action="ScheduleOfRate.html" class="form-horizontal"
					name="ScheduleOfRate" id="scheduleOfRate">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="UADstatus" id="uadStatus" />
					<form:hidden path="" id="isDefaultStatus"
						value="${userSession.organisation.defaultStatus}" />
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="sor.name" text="SOR Name" /> </label>
						<c:set var="SRMlookUp" value="SRM" />
						<apptags:lookupField items="${command.getLevelData(SRMlookUp)}"
							path="mstDto.sorCpdId"
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="work.management.select" />
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="sor.fromdate" text="From Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="mstDto.sorFromDate" id="sorFromDate"
									class="form-control datepicker" value="" readonly="true" />
								<label class="input-group-addon" for="sorFromDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id=sorFromDate></label>
							</div>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="sor.todate" text="To Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="mstDto.sorToDate" id="sorToDate"
									class="form-control datepickerEndDate" value="" readonly="true" />
								<label class="input-group-addon" for="sorToDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id=sorToDate></label>
							</div>
						</div>
					</div>
					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-blue-2" id="searchSOR">
							<i class="fa fa-search padding-right-5"></i>
							<spring:message code="search.data" text="Search" />
						</button>
						<button type="Reset" class="btn btn-warning" onclick="resetSOR()">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="reset.msg" text="Reset" />
						</button>
						<c:if
							test="${command.UADstatus ne  'YES' || userSession.organisation.defaultStatus eq 'Y'}">
							<button type="button" class="btn btn-primary" id="createSOR">
								<i class="fa fa-plus-circle padding-right-5"></i>
								<spring:message code="add.msg" text="Add" />
							</button>
							<button type="button" class="btn btn-success" id="importSOR">
								<spring:message code="sor.export.Import" text="Export/Import" />
							</button>
						</c:if>
					</div>
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="datatables">
							<thead>
								<tr>
									<td width="30%" align="center"><spring:message
											code="sor.name" text="SOR Name" /></td>
									<td width="15%" align="center"><spring:message
											code="sor.fromdate" text="From Date" /></td>
									<td width="15%" align="center"><spring:message
											code="sor.todate" text="To Date" /></td>
									<td width="15%" align="center"><spring:message
											code="works.management.action" text="Action" /></td>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${command.UADstatus eq 'YES' && userSession.organisation.defaultStatus ne 'Y'}">
										<c:forEach items="${mstDtoList}" var="mstDto">
											<tr>
												<td>${mstDto.sorName}</td>
												<td>${mstDto.fromDate}</td>
												<td>${mstDto.toDate}</td>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
												    onClick="viewSOR(${mstDto.sorId})" title="<spring:message code="sor.view.schedule" text="View Schedule"></spring:message>">
												<i class="fa fa-eye"></i>
												</button>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${mstDtoList}" var="mstDto">
											<tr>
												<td>${mstDto.sorName}</td>
												<td>${mstDto.fromDate}</td>
												<td>${mstDto.toDate}</td>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onClick="viewSOR(${mstDto.sorId})" title="<spring:message code="sor.view.schedule" text="View Schedule"></spring:message>">
														<i class="fa fa-eye"></i>
													</button> <c:if test="${mstDto.sorActive eq 'Y' }">
														<button type="button" class="btn btn-success btn-sm"
													    onClick="editSOR(${mstDto.sorId})" title="<spring:message code="sor.edit.schedule" text="Edit Schedule"></spring:message>">
													<i class="fa fa-pencil-square-o"></i>
													</button>
													</c:if>

												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>