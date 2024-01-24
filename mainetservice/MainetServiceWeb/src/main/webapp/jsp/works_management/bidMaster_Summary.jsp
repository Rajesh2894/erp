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
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/works_management/bidMaster.js"></script>
<script type="text/javascript" src="js/works_management/tenderinitiation.js"></script>
<script>
	$(function() {
		chosen();
	})
</script>
<!-- <style>

.btn.btn-green-3:disabled {
    background-color: #209c83;
    color: #fff;
    cursor: not-allowed; /* Optional: Change cursor on hover */
}
</style>
 -->

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.BID.comparison"
					text="Bid Entry and Bid Finalization" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="common.sequenceconfig.fieldwith"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="common.sequenceconfig.mandatory" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="BIDEntry.html" name="" id="bidMaster"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="project.master.projname" text="Project Name" /> </label>
					<div class="col-sm-4">
						<form:select path="bidMasterDto.projectid" id="projId"
							class="form-control chosen-select-no-results"
							onchange="getTenderData();">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${tenderProjects}" var="projArray">
								<c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
									<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[1]}</form:option>
								</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
									<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[3]}</form:option>
								</c:if>

							</c:forEach>

						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="wms.BID.tenderNo" text="Tender No" /></label>
					<div class="col-sm-4">
						<form:select path="bidMasterDto.tndNo"
							class="form-control mandColorClass chosen-select-no-results"
							id="tndNo">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.tnderNoList}" var="tender">
								<form:option value="${tender.initiationNo}">${tender.initiationNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>


				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-blue-2" title="Search"
						onclick="searchForm('BIDMaster.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="works.management.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetForm('BIDMaster.html','bidEntryReset')">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="works.management.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-green-3" title="Add"
						onclick="addBidEntry('BIDMaster.html','ADD');">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="works.management.add" text="Add"></spring:message>
					</button>
				</div>

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="20%" align="center"><spring:message
										code="works.bidId" text="Bid ID" /></th>
								<th width="20%" align="center"><spring:message
										code="works.bidderName" text="Bidder Name" /></th>
								<th width="20%" align="center"><spring:message code=""
										text="Technical Evaluation" /></th>
								<th width="20%" align="center"><spring:message code=""
										text="Financial Evaluation" /></th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${command.bidDtoList}" var="data"
								varStatus="index">
								<tr>
									<td>${data.bidNo}</td>
									<td>${data.bidIdDesc}</td>

									<td class="text-center"><c:if
											test="${empty data.technicalBIDDetailDtos}">
											<button itype="button" id="bidButton" class="btn btn-green-3"
												title="<spring:message code='work.management.technical'></spring:message>"
												onclick="bidTechnicalEntry('BIDMaster.html','Technical',${data.bidId});">
												<i class="fa fa-plus-circle" aria-hidden="true"></i>
											</button>
										</c:if>


										<button type="button" class="btn btn-blue-2 btn-sm"
											title="<spring:message code='work.management.technical'></spring:message>"
											onclick="viewBidTechnicalEntry('BIDMaster.html','viewTechnical',${data.bidId});">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>
										<button type="button" class="btn btn-warning"
											title="<spring:message code='work.management.technical'></spring:message>"
											onclick="editBidTechnicalEntry('BIDMaster.html','editTechnical',${data.bidId});">
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button></td>

									<td class="text-center"><c:if
											test="${not empty data.technicalBIDDetailDtos}">

											<c:if
												test="${not empty data.technicalBIDDetailDtos && not empty data.commercialBIDDetailDtos}">
												<c:set var="hasQuotedPrice" value="false" />
												<c:forEach items="${data.commercialBIDDetailDtos}"
													var="commercialDetail">
													<c:if test="${not empty commercialDetail.quotedPrice}">
														<c:set var="hasQuotedPrice" value="true" />
													</c:if>
												</c:forEach>
												<c:if test="${not hasQuotedPrice}">
													<!-- Your button code here -->
													<button type="button" class="btn btn-green-3"
														title="<spring:message code='work.management.financial'></spring:message>"
														onclick="bidTechnicalEntry('BIDMaster.html','Financial',${data.bidId});">
														<i class="fa fa-plus-circle" aria-hidden="true"></i>
													</button>
												</c:if>
											</c:if>



											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code='work.management.financial'></spring:message>"
												onclick="viewBidFinancialEntry('BIDMaster.html','viewFinancial',${data.bidId});">
												<i class="fa fa-eye" aria-hidden="true"></i>
											</button>

											<button type="button" class="btn btn-warning"
												title="<spring:message code='work.management.financial'></spring:message>"
												onclick="editBidFinancialEntry('BIDMaster.html','editFinancial',${data.bidId});">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>
										</c:if> <c:if test="${empty data.technicalBIDDetailDtos}">
											
											<button type="button" class="btn btn-green-3"
												title="<spring:message code='work.management.financial'></spring:message>"
												onclick="bidValidFunction(${data.bidId}); ">
												<i class="fa fa-plus-circle" aria-hidden="true"></i>
											</button>

											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code='work.management.financial'></spring:message>"
												onclick="bidValidFunction(${data.bidId});">
												<i class="fa fa-eye" aria-hidden="true"></i>
											</button>

											<button type="button" class="btn btn-warning"
												title="<spring:message code='work.management.financial'></spring:message>"
												onclick="bidValidFunction(${data.bidId});">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
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
