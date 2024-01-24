<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="tender.init.summery"
					text="Tender/Quotation Initiation Summary" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="TenderInitiation.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="TenderInitiation.html" class="form-horizontal"
				name="" id="tenderInitiation">
				<form:hidden path="serviceFlag" id="serviceFlag" />
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" text="Project Name" /></label>
					<div class="col-sm-4">

						<form:select path="initiationDto.projId"
							cssClass="form-control chosen-select-no-results" id="projId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${tenderProjects}" var="projArray">
								<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label for="" class="col-sm-2 control-label "><spring:message
							code="leadlift.master.category" text="Category" /> </label>
					<c:set var="CATlookUp" value="CAT" />
					<apptags:lookupField items="${command.getLevelData(CATlookUp)}"
						path="initiationDto.tenderCategory"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select" />

				</div>

				<%-- <div class="form-group">
					<label class="control-label col-sm-2" for="initiationNo"><spring:message
							code="tender.init.no" text="Initiation No." /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.initiationNo" id="initiationNo"
							type="text" class="form-control preventSpace" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="tender.init.date" text="Initiation Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.initiationDate"
								id="initiationDate" class="form-control initiationDatePicker"
								value="" readonly="true" disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="initiationDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=initiationDate></label>
						</div>
					</div>
				</div>

				<div class="form-group"></div> --%>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" id="searchTender">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning" type="button"
						onclick="window.location.href='TenderInitiation.html'">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-primary" id="prepareTender">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add" text="Add" />
					</button>
				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<%-- <th width="10%" align="center"><spring:message
										code="tender.init.no" text="Initiation No." /></th>
								<th width="10%" align="center"><spring:message
										code="tender.init.date" text="Initiation Date" /></th> --%>
								<th width="20%" align="center"><spring:message
										code="tender.tenderNo" text="Tender/Quotation No." /></th>
								<th width="20%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>
								<th width="10%" align="center"><spring:message
										code="tender.type" text="Tender Type" /></th>
								<th width="15%"><spring:message
										code="tender.estimated.amount" text="Estimated Work Cost" /></th>
								<th width="15%" align="center"><spring:message
										code="" text="LOA Number" /></th>
								<c:if test="${command.serviceFlag eq 'A'}">
									<th width="15%"><spring:message code=""
											text="Tender Status" /></th>
								</c:if>
								<th width="20%" align="center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${command.serviceFlag eq 'A'}">
									<c:forEach items="${tenderList}" var="tenderDto">
										<tr>
											<td class="text-center">${tenderDto.tenderNo}</td>
											<td>${tenderDto.projectName}</td>
											<td class="text-center">${tenderDto.tenderCategoryDesc}</td>
											<td align="right">${tenderDto.tenderTotalEstiAmount}</td>
											<td align="text-center">${tenderDto.tndLOANo}</td>
											<td class="text-center">${tenderDto.statusDesc}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message code="works.management.view"></spring:message>"
													onClick="tenderView(${tenderDto.tndId})">
													<i class="fa fa-eye"></i>
												</button> <c:if test="${tenderDto.status eq 'A' }">
													<button type="button" class="btn btn-primary btn-sm"
														onClick="updateTender(${tenderDto.tndId})"
														title="<spring:message code="works.management.update"></spring:message>">
														<i class="fa fa-line-chart"></i>
													</button>
												</c:if> <c:if test="${not empty tenderDto.tenderNo}">
													<button type="button" class="btn btn-warning btn-sm"
														onClick="printLoa(${tenderDto.tndId})"
														title="<spring:message code="works.management.print.loa"></spring:message>">
														<i class="fa fa-print"></i>
													</button>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${tenderList}" var="tenderDto">
										<tr>
											<%-- <td>${tenderDto.initiationNo}</td>
									<td>${tenderDto.initiationDateDesc}</td> --%>
											<td>${tenderDto.tenderNo}</td>
											<td>${tenderDto.projectName}</td>
											<td>${tenderDto.tenderCategoryDesc}</td>
											<td align="right">${tenderDto.tenderTotalEstiAmount}</td>
											<td align="text-center">${tenderDto.tndLOANo}</td>
											<td class="text-center"><c:if
													test="${ empty tenderDto.initiationNo}">
													<button type="button" class="btn btn-green-3 btn-sm"
														onClick="initiateTender(${tenderDto.tndId})"
														title="<spring:message code="works.management.initiate.tender"></spring:message>">
														<i class="fa fa-share-square-o "></i>
													</button>
												</c:if>
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message code="works.management.view"></spring:message>"
													onClick="tenderView(${tenderDto.tndId})">
													<i class="fa fa-eye"></i>
												</button> <c:if test="${not empty tenderDto.initiationNo }">
													<button type="button" class="btn btn-primary btn-sm"
														onClick="updateTender(${tenderDto.tndId})"
														title="<spring:message code="works.management.update"></spring:message>">
														<i class="fa fa-line-chart"></i>
													</button>
												</c:if> <c:if test="${empty tenderDto.initiationNo }">
													<button type="button" class="btn btn-warning btn-sm"
														onClick="editTender(${tenderDto.tndId})"
														title="<spring:message code="works.management.edit"></spring:message>">
														<i class="fa fa-pencil"></i>
													</button>
													<button type="button" class="btn btn-danger btn-sm"
														onClick="deleteTender(${tenderDto.tndId})"
														title="<spring:message code="works.management.delete"></spring:message>">
														<i class="fa fa-trash"></i>
													</button>

												</c:if> <c:if test="${not empty tenderDto.tenderNo }">
													<button type="button" class="btn btn-warning btn-sm"
														onClick="printLoa(${tenderDto.tndId})"
														title="<spring:message code="works.management.print.loa"></spring:message>">
														<i class="fa fa-print"></i>
													</button>

												</c:if> <%-- <button type="button" class="btn btn-danger btn-sm"
											onClick="deleteTender(${tenderDto.tndId})" title="Delete">
											<i class="fa fa-trash"></i>
										</button> --%></td>
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
