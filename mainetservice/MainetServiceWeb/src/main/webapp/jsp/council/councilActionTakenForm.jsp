<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/council/councilActionTaken.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.button.actionTaken"
					text="Action Taken" />
			</h2>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="coucil.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="CouncilActionTaken.html"
				cssClass="form-horizontal" id="actiontaken"
				name="councilActiontaken">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="council.member.proposalNo" text="proposalNo" /><i
						class="text-red-1">*</i></label>


					<div class="col-sm-4 ">
						<form:select path="" id="proposalNo"
							cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" data-rule-required="true">

							<form:option value="">
								<spring:message code='council.management.select' />
							</form:option>
							<c:forEach items="${proposalList}" var="status">
								<form:option value="${status.proposalNo}">${status.proposalNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="text-center">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchCouncilproposal" onclick="searchProposalData(this);">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='CouncilActionTaken.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="proposalActionDatatables">
							<thead>
								<tr>
									<th width="3%" align="center"><spring:message
											code="council.member.srno" text="Sr.No" /></th>
									<th width="10%" align="center"><spring:message
											code="council.member.proposalNo" text="Proposal Number" /></th>
									<th width="15%" align="center"><spring:message
											code="council.member.proposalDetails"
											text="Details of Proposal" /></th>
									<th width="9%" align="center"><spring:message
											code="council.proposal.purpose" text="Purpose of Proposal" /></th>
									<th width="9%" align="center"><spring:message
											code="council.member.action" text="Action" /></th>
								</tr>
							</thead>
							<c:forEach items="${proposalList}" var="prop" varStatus="item">

								<tr>
									<td class="text-center">${item.count}</td>
									<td class="text-center">${prop.proposalNo}</td>
									<td class="text-center">${prop.proposalDetails}</td>
									<td class="text-center">${prop.purposeremark}</td>
									<td class="text-center">
										<button type="button"
											class="btn btn-blue-2 btn-sm margin-right-10 "
											name="button-plus" id="button-plus"
											onclick="showGridOption('${prop.proposalId}','CouncilActionTaken.html','VIEW','V')"
											title="<spring:message code="council.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button"
											class="btn btn-danger btn-sm btn-sm margin-right-10"
											name="button-123" id=""
											onclick="showGridOption('${prop.proposalId}','CouncilActionTaken.html','EDIT','E')"
											title="<spring:message code="council.button.edit" text="edit"></spring:message>">
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button>
									</td>

								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->