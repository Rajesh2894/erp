<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/works_management/bidMaster.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<script>
	$(function() {
		chosen();
	})
</script>


<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.BID.comparison" text="Bid Comparison" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="common.sequenceconfig.fieldwith"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="common.sequenceconfig.mandatory" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="BIDMaster.html" name="bidMaster" id="bidMaster"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="wms.BID.tenderNo" text="Tender No" /> </label>
					<div class="col-sm-4">
						<form:select path=""
							class="form-control mandColorClass chosen-select-no-results"
							id="tndNo">
							<form:option value="0">Select</form:option>
							<c:forEach items="${tnderNoList}" var="tenderNo">
								<form:option value="${tenderNo}">${tenderNo}</form:option>

							</c:forEach>

						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="wms.BID.tenderDate" text="Tender Date" /></label>
					<div class="col-sm-4">

						<div class="input-group">
							<form:input path="" id="tenderDate"
								class="form-control tenderDatePicker" value="" />
							<label class="input-group-addon" for="tenderDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=resolutionDate></label>
						</div>
					</div>

				</div>

				<%-- <div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="wms.BID.overAllTechScore" text="Over All Technical Score" /></label>
					<div class="col-sm-4">
						<form:input path="" id="overAllTechScore"
							cssClass="form-control hasNumber" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="wms.BID.overAllComScore" text="Over All Commercial Score" /></label>
					<div class="col-sm-4">
						<form:input path="" id="overAllComScore"
							cssClass="form-control hasNumber" />
					</div>
				</div> --%>




				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchTender()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="showBIDSearchForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="common.sequenceconfig.reset" text="Reset"></spring:message>
					</button>


				</div>


				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>

								<th width="20%" align="center"><spring:message
										code="tender.tenderNo" text="Tender/Quotation No." /></th>
								<th width="20%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>
								<th width="10%" align="center"><spring:message
										code="leadlift.master.category" text="Category" /></th>
								<th width="15%"><spring:message
										code="tender.estimated.amount" text="Estimated Work Cost" /></th>

								<th width="15%"><spring:message code=""
										text="Tender Status" /></th>

								<th width="20%" align="center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${tenderList}" var="tenderDto">
								<tr>
									<td>${tenderDto.tenderNo}</td>
									<td>${tenderDto.projectName}</td>
									<td>${tenderDto.tenderCategoryDesc}</td>
									<td align="right">${tenderDto.tenderTotalEstiAmount}</td>
									<td>${tenderDto.statusDesc}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code="works.management.compare" text="Compare"></spring:message>"
											onClick="openBidCompareForm(${tenderDto.tndId})">
											<i class="fa fa-eye"></i>
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

