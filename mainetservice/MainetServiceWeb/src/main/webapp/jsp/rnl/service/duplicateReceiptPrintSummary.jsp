<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet"
	type="text/css" />
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript"
	src="js/rnl/service/duplicateReceiptPrinting.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Duplicate Receipt Printing"></spring:message>
			</h2>
		</div>


		<div class="widget-content padding">
			<form:form method="POST" action="DuplicateReceiptPrinting.html"
				class="form-horizontal" name="duplicateReceiptPrintForm"
				id="duplicateReceiptPrintForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="FromDate"><spring:message
							code="rnl.booking.from.date" text="Booking From Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="bookingReqDTO.estateBookingDTO.fromDate"
								type="text" id="fromDate"
								class="lessthancurrdatefrom  form-control"
								data-rule-required="true"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i><span
								class="hide"><spring:message code="rnl.from.date"
										text="From Date"></spring:message></span></span>
						</div>
					</div>
					<label class="control-label col-sm-2 required-control" for="ToDate"><spring:message
							code="rnl.booking.to.date" text="Booking To Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="bookingReqDTO.estateBookingDTO.toDate"
								type="text" id="toDate" class="lessthancurrdateto form-control"
								data-rule-required="true"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i><span
								class="hide"><spring:message code="rnl.to.date"
										text="To Date"></spring:message></span></span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="Property Name"><spring:message
							code="rnl.property.name" text="Property Name"></spring:message></label>
					<div class="col-sm-4">
						<form:select path="estatePropMasterDto.name" id="propertyName"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.estatePropMasterList}" var="proDto">
								<form:option value="${proDto.name}">${proDto.name}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success" id="btnsearch"
						onclick="searchDetails();">
						<i class="fa fa-search"></i>&nbsp;
						<spring:message code="bt.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetForm();">
						<spring:message code="account.bankmaster.reset" text="Reset"></spring:message>
					</button>

				</div>


				<div class="table-responsive clear padding-10">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="5%"><spring:message
										code="estate.table.column.srno" text="Sr. No."></spring:message></th>
								<th width="10%"><spring:message
										code="rnl.booking.from.date" text="Booking From Date"></spring:message></th>
								<th width="10%"><spring:message code="rnl.booking.to.date"
										text="Booking To Date"></spring:message></th>
								<th width="25%"><spring:message
										code="rl.property.label.name" text="Property Name"></spring:message></th>

								<th width="15%"><spring:message
										code="rnl.booking.applicant.name" text="Applicant Name"></spring:message></th>

								<th width="10%"><spring:message code="" text="Action"></spring:message></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.estatePropResponseDTO}"
								var="responseDto" varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${responseDto.fromDate}</td>
									<td class="text-center">${responseDto.toDate}</td>
									<td class="text-center">${responseDto.propName}</td>
									<td class="text-center">${responseDto.applicantName}</td>

									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm margin-right-5"
											onClick="propertyInfo(${responseDto.bookingId})"
											title="<spring:message code="works.management.view"></spring:message>">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="downoladReceipt('${responseDto.receiptId}','${responseDto.receiptNo}','${responseDto.applicationId}')"										
											title="<spring:message code="works.management.edit"></spring:message>">
											<i class="fa fa-pencil-square-o"></i>
										</button>
									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>

			</form:form>

		</div>
	</div>
</div>
