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
	src="js/additionalServices/cfcSchedulingTrx.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="CFCSchedulingTrx.html" method="post"
				class="form-horizontal" name="cfcSchedulingTrxSummary"
				id="cfcSchedulingTrxSummary">

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
					<spring:message code="SFT.collection.sumary.info"
						text="Collection Master Info Summary" />
				</h4>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="SFT.collection.center.no" text="Collection Center No." /></label>

					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							id="collectionNo" onchange="getCounterNosByCollectionNo()">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.collectionNos}" var="collectionNo">
								<form:option value="${collectionNo}">${collectionNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="SFT.counter.no" text="Counter No." /></label>

					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							id="counterNo">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<%-- <c:forEach items="${command.empList}" var="emp">
								<form:option value="${emp.empId}">${emp.empname}</form:option>
							</c:forEach> --%>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="SFT.user.name" text="User Name" /></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							id="userId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.empList}" var="emp">
								<form:option value="${emp.empId}">${emp.empname}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="SFT.status" text="Status" /></label>
					<div class="col-sm-4">
						<form:select path="" id="status"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>

							<form:option value="A">Active</form:option>
							<form:option value="I">InActive</form:option>


						</form:select>
					</div>
				</div>


				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm('CFCSchedulingTrx.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="SFT.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="showSummary()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="NHP.reset" text="Reset"></spring:message>
					</button>


					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="formForCreate()">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="NHP.add" text="Add"></spring:message>
					</button>
				</div>

				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="collectionTableId" class="collectionTable">
							<!-- class="table table-striped table-condensed table-bordered"
							id="sequenceTable" -->
							<thead>
								<tr>
									<th><spring:message code="SFT.coll.center.no"
											text="Collection Center no" /></th>
									<th><spring:message code="SFT.count.no" text="Counter No" /></th>
									<th><spring:message code="SFT.user" text="User" /></th>
									<th><spring:message code="SFT.fm.date" text="From Date" /></th>
									<th><spring:message code="SFT.toDate" text="To Date" /></th>
									<th><spring:message code="SFT.status" text="Status" /></th>

									<th><spring:message code="SFT.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.cfcSchedularSummaryDtos}" var="data"
									varStatus="index">
									<tr>
										<form:hidden path="" id="collectionId"
											value="${data.counterScheduleId}" />
										<td>${data.collectionnNo}</td>
										<td>${data.counterNo}</td>
										<td>${data.userName}</td>
										<td>${data.startDate }</td>
										<td>${data.endDate}</td>
										<td class="text-center"><c:if
												test="${data.status eq 'A'}">
												<a title='Active' alt='Active' value='A'
													class='fa fa-check-circle fa-2x green' href='#'></a>
											</c:if> <c:if test="${data.status eq 'I'}">
												<a title='Inactive' alt='Inactive' value='I'
													class='fa fa-times-circle fa-2x red' href='#'></a>
											</c:if></td>
										<c:if test="${data.status eq 'A'}">
											<td class="text-center">
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit Sequence Master"
													onclick="editSequenceMaster('CFCSchedulingTrx.html','formForEdit',${data.counterScheduleId})">

													<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
												</button>
											</td>
										</c:if>
										<c:if test="${data.status eq 'I'}">
											<td></td>
										</c:if>

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
