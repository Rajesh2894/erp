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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<script>
	$(function() {
		chosen();
	})
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.BID.bidMaster" text="Bid Master" />
			</h2>

			<div class="additional-btn">
				<apptags:helpDoc url="TenderInitiation.html"></apptags:helpDoc>
			</div>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span> <spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="TenderInitiation.html" class="form-horizontal"
				name="TenderInitiation" id="TenderInitiation"
				modelAttribute="command">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>


				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="bidMaster" class="bidMaster">
							<!-- class="table table-striped table-condensed table-bordered"
							id="sequenceTable" -->

							<!-- <div class="table-responsive"> -->



							<thead>
								<tr>
									<th width="8%"><spring:message code='wms.BID.vendorName'
											text="Vendor Name" /></th>
									<th width="10%"><spring:message code='wms.BID.bidId'
											text="Bid ID" /></th>
									<th width="10%"><spring:message
											code='wms.BID.overAllTechScore'
											text="Over All Technical Score" /></th>
									<th width="10%"><spring:message
											code='wms.BID.overAllComScore'
											text="Over All Commercial Score" /></th>
									<th width="10%"><spring:message code='wms.BID.AccRejLost'
											text="Accepted/Rejected/Lost" /></th>
									<th width="10%"><spring:message
											code="common.sequenceconfig.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.bidMasterDtos}" var="bidMaster"
									varStatus="index">
									<tr>
										<form:hidden path="" id="seqConfigId"
											value="${bidMaster.bidId}" />
										<td>${bidMaster.vendorName}</td>
										<td>${bidMaster.bidIdDesc}</td>
										<td>${bidMaster.overAllTechScore}</td>
										<td>${bidMaster.overAllCommScore }</td>
										<td>${bidMaster.status}</td>
										<td class="text-center">

											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View BID Master"
												onclick="editBIDForm(${bidMaster.bidId},'V')">

												<!-- editadvertiserMaster(${masterList.agencyId},'V') -->
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit BID Master"
												onclick="editBIDForm(${bidMaster.bidId},'E')">
												<!-- editadvertiserMaster(${masterList.agencyId},'E') -->
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>

										</td>

									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>


				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="showBIDForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>

		</div>
		<!-- End Widget Content here -->
	</div>
</div>
<!-- End of Content -->