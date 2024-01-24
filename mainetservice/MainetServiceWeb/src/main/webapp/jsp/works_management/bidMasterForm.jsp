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
				name="BIDMaster" id="BIDMaster" modelAttribute="command">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div>


					<!-- <div class="table-responsive"> -->

					<table class="table table-bordered table-striped" id="bidMaster">

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
								<th width="10%"><spring:message code='wms.BID.L1QCBS'
										text="L1/QCBS" /></th>
								<th scope="col" width="5%"></th>
							</tr>
						</thead>
						<tbody>
							<tr class="bidMasterAdd">

								<td><form:select path="bidMasterDto.vendorId" id="vendorId"
										class="form-control">
										<form:option value="">
											<spring:message code='work.management.select' />
										</form:option>
										<c:forEach items="${command.vendorList}" var="vendorList">
											<form:option value="${vendorList.vmVendorid }">${vendorList.vmVendorname}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:input path="bidMasterDto.bidIdDesc"
										id="bidIdDesc" cssClass="form-control" /></td>
								<td><form:input path="bidMasterDto.overAllTechScore"
										id="overAllTechScore" cssClass="form-control hasNumber" /></td>
								<td><form:input path="bidMasterDto.overAllCommScore"
										id="overAllComScore" cssClass="form-control hasNumber" /></td>
								<td><form:select path="bidMasterDto.status" id="AccRejLost"
										class="form-control">
										<form:option value="">
											<spring:message code='work.management.select' />
										</form:option>

										<form:option value="A">Accepted</form:option>
										<form:option value="R">Rejected</form:option>
										<form:option value="L">Lost</form:option>

									</form:select></td>
								<td><form:select path="bidMasterDto.billType" id="L1QCBS"
										class="form-control">
										<form:option value="">
											<spring:message code='work.management.select' />
										</form:option>
										<form:option value="L1">L1</form:option>
										<form:option value="QCBS">QCBS</form:option>
									</form:select></td>

								<td><c:if test="${command.bidMode ne 'V'}">
										<button class="btn btn-primary"
											onclick="showaddBIDDetailsForm(this);" type="button"
											id="bidDetail">
											<i class="fa fa-plus-circle padding-right-5"></i>
											<spring:message code="wms.BID.addDetails" text="Add Details" />
										</button>
									</c:if> <c:if test="${command.bidMode eq 'V'}">
										<button class="btn btn-primary"
											onclick="showBIDDetailsViewModeForm();" type="button"
											id="bidDetail">
											<i class="fa fa-plus-circle padding-right-5"></i>
											<spring:message code="wms.BID.viewDetails"
												text="View Details" />
										</button>
									</c:if></td>

							</tr>

						</tbody>
					</table>
				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.bidMode eq 'A'}">
						<%-- <button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="updateTender(${command.tndId})" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />

						</button> --%>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="openTenderUpdateForm()" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />

						</button>
					</c:if>
					<c:if test="${command.bidMode ne 'A'}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="showBIDDetailsForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />

						</button>
					</c:if>
					<c:if test="${command.bidMode eq 'A'}">
						<button class="btn btn-success add"
							onclick="showBIDDetailsForm();" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="" text="Show Bid Details" />
						</button>
					</c:if>
				</div>
			</form:form>

		</div>
		<!-- End Widget Content here -->
	</div>
</div>
<!-- End of Content -->