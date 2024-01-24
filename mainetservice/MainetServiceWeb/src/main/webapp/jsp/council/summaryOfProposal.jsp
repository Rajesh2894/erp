<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/council/councilProposalMaster.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.proposal.summary.title"
					text="Summary of Proposal" />
			</h2>
			<!-- 	<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilProposalMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="CouncilProposalMaster.html"
				cssClass="form-horizontal" id="CouncilProposalMaster"
				name="CouncilProposalMaster">
				<!-- Start Validation include tag -->
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />--%>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="council.member.department" text="Department" /></label>
					<div class="col-sm-4 ">
						<form:select path="couProposalMasterDto.proposalDepId"
							id="proposalDepId"
							cssClass="form-control chosen-select-no-results"
							class="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code='council.management.select' />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="lookUp">
								<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${lookUp.dpDeptid}">${lookUp.dpNameMar}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							
							
						</form:select>
					</div>
					<apptags:input labelCode="council.proposal.no"
						path="couProposalMasterDto.proposalNo" cssClass="form-control"></apptags:input>
				</div>

				<!-- date picker input set -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="council.proposal.from.date"
						datePath="couProposalMasterDto.fromDate"></apptags:date>
					<apptags:date fieldclass="datepicker"
						labelCode="council.proposal.to.date"
						datePath="couProposalMasterDto.toDate"></apptags:date>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="council.member.ward" text="Ward/Zone" /></label>
					<div class="col-sm-4 ">
						<form:select path="couProposalMasterDto.wardId" id="ward"
							cssClass="form-control chosen-select-no-results"
							class="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code='council.management.select' />
							</form:option>
							<c:forEach items="${command.lookupListLevel1}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-success" title="Search"
						id="searchCouncilproposal">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='CouncilProposalMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="addProposalMaster('CouncilProposalMaster.html','addproposal','N',0,0);">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.addproposal" text="Add" />
					</button>
				</div>
				<!-- End button -->
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="proposalDatatables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="council.member.srno" text="Sr.No" /></th>
								<th width="15%" align="center"><spring:message
										code="council.member.department" text="Department" /></th>
								<th width="10%" align="center"><spring:message
										code="council.member.proposalNo" text="Proposal Number" /></th>
								<th width="14%" align="center"><spring:message
										code="council.member.ward" text="Ward/Zone" /></th>
								<th width="15%" align="center"><spring:message
										code="council.member.proposalDetails"
										text="Details of Proposal" /></th>
								<th width="9%" align="center"><spring:message
										code="council.member.proposal.status" text="Proposal Status" /></th>
								<th width="10%" align="center"><spring:message
										code="council.meeting.date" text="Meeting date" /></th>
								<th width="9%" align="center"><spring:message
										code="council.meeting.decision" text="Meeting Decision" /></th>
								<th width="15%" align="center"><spring:message
										code="council.member.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.couProposalMasterDtoList}"
								var="masterData" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${masterData.proposalDeptName}</td>
									<td>${masterData.proposalNo}</td>
									<td>${masterData.wardDescJoin}</td>
									<td>${masterData.proposalDetails}</td>
									<td>${masterData.proposalStatusDesc}</td>
									<td>${masterData.meetingDate}</td>
									<td>${masterData.meetingProposalStatus}</td>
									<td class="text-center"><c:choose>
											<c:when test="${masterData.proposalStatus eq 'Draft'}">
												<button type="button"
													class="btn btn-blue-2 btn-sm margin-right-10 "
													name="button-plus" id="button-plus"
													onclick="showGridOption(${masterData.proposalId},'V')"
													title="<spring:message code="council.button.view" text="view"></spring:message>">
													<i class="fa fa-eye" aria-hidden="true"></i>
												</button>

												<button type="button"
													class="btn btn-warning btn-sm btn-sm margin-right-10"
													name="button-123" id=""
													onclick="showGridOption(${masterData.proposalId},'E')"
													title="<spring:message code="council.button.edit" text="edit"></spring:message>">
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
												</button>
												
												<button type="button"
														class="btn btn-green-3 btn-sm margin-right-10"
														title="<spring:message code="council.proposal.sent.for.approval" text="Sent For Approval"></spring:message>"
														onclick="sendForApproval(${masterData.proposalId},'${masterData.proposalType}');">
														<i class="fa fa-share-square-o "></i>
													</button>
												<!-- commenting because as per requirement - workflow is not applicable   -->
												<%-- <button type="button"
													class="btn btn-green-3 btn-sm margin-right-10"
													title="<spring:message code="council.proposal.sent.for.approval" text="Sent For Approval"></spring:message>"
													onclick="sendForApproval(${masterData.proposalId},'${masterData.proposalType}');">
													<i class="fa fa-share-square-o "></i>
												</button> --%>
											</c:when>

											<c:otherwise>
												<button type="button"
													class="btn btn-blue-2 btn-sm margin-right-10 "
													name="button-plus" id="button-plus"
													onclick="showGridOption(${masterData.proposalId},'V')"
													title="<spring:message code="council.button.view" text="view"></spring:message>">
													<i class="fa fa-eye" aria-hidden="true"></i>
												</button>

												<button type="button"
													class="btn btn-danger btn-sm btn-sm margin-right-10"
													disabled="disabled" name="button-123" id=""
													onclick="showGridOption(${masterData.proposalId},'E')"
													title="<spring:message code="council.button.edit" text="edit"></spring:message>">
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
												</button>

												<c:if
													test="${masterData.approvalFlag eq 'Y'  && masterData.proposalStatus eq 'Draft'}">


													<button type="button"
														class="btn btn-green-3 btn-sm margin-right-10"
														title="<spring:message code="council.proposal.sent.for.approval" text="Sent For Approval"></spring:message>"
														onclick="sendForApproval(${masterData.proposalId},'${masterData.proposalType}');">
														<i class="fa fa-share-square-o "></i>
													</button>

												</c:if>

												<c:if
													test="${masterData.approvalFlag eq 'N'  || masterData.proposalStatus eq 'Approved' || masterData.proposalStatus eq 'Pending'}">
													<button type="button"
														class="btn btn-green-3 btn-sm margin-right-10"
														disabled="disabled"
														title="<spring:message code="council.proposal.sent.for.approval" text="Sent For Approval"></spring:message>"
														onclick="sendForApproval(${masterData.proposalId},'${masterData.proposalType}');">
														<i class="fa fa-share-square-o "></i>
													</button>
												</c:if>
												<!-- commenting because as per requirement - workflow is not applicable  -->
												<%-- <button type="button"
													class="btn btn-green-3 btn-sm margin-right-10"
													disabled="disabled"
													title="<spring:message code="council.proposal.sent.for.approval" text="Sent For Approval"></spring:message>"
													onclick="sendForApproval(${masterData.proposalId},${masterData.proposalType});">
													<i class="fa fa-share-square-o "></i>
												</button> --%>

											</c:otherwise>
										</c:choose></td>

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