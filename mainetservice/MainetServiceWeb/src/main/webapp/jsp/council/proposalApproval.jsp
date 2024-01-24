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
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/council/proposalApproval.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<!-- <script src="../assets/libs/chosen/chosen.jquery.js"
	type="text/javascript"></script> -->

<!-- As per html we have designed but later removed this design -->
<!-- <style>
.view-mode {
	border: none !important;
	border-bottom: 1px dashed #848484 !important;
}

.view-mode[disabled] {
	background-color: #fff !important;
	cursor: inherit !important;
}
</style> -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.proposal.approval.title"
					text="Proposal Approval" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
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
			<form:form action="CouncilProposalApproval.html"
				cssClass="form-horizontal" id="ProposalApproval">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="form-group">

					<apptags:input labelCode="council.proposal.no"
						path="couProposalMasterDto.proposalNo"
						cssClass="form-control view-mode" isDisabled="true"></apptags:input>

				</div>

				<div class="form-group">


					<apptags:input labelCode="council.member.department"
						path="couProposalMasterDto.proposalDeptName"
						cssClass="form-control view-mode" isDisabled="true"></apptags:input>

					<apptags:input labelCode="council.proposal.amount"
						path="couProposalMasterDto.proposalAmt" onChange="getAmountFormatInDynamic((this),'proposalAmt')"
						cssClass="form-control hasDecimal text-right view-mode" isDisabled="true"></apptags:input>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2"><spring:message
							code='council.member.proposalDetails' text='Details of Proposal' /></label>
					<div class="col-sm-10">
						<form:textarea path="couProposalMasterDto.proposalDetails"
							class="form-control alfaNumricSpecial view-mode" maxlength="1000"
							disabled="true" />
					</div>

				</div>

				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="council.member.ward" text="Ward/Zone" /></label>
					<div class="col-sm-4 ">
						<form:select multiple="true" path="couProposalMasterDto.wards" 
							id="wards" cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" data-rule-required="true"
							disabled="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.lookupListLevel1}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>

					</div>

					<%-- <label for="" class="col-sm-2 control-label"> <spring:message
							code="council.member.documents" text="Documents" /></label>
					<c:set var="count" value="0" scope="page"></c:set> --%>
					<%-- <div class="col-sm-4">
						
							
					

						<c:if
							test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
							<input type="hidden" name="deleteFileId"
								value="${command.attachDocsList[0].attId}">
							<input type="hidden" name="downloadLink"
								value="${command.attachDocsList[0]}">
							<apptags:filedownload
								filename="${command.attachDocsList[0].attFname}"
								filePath="${command.attachDocsList[0].attPath}"
								actionUrl="CouncilProposalMaster.html?Download"></apptags:filedownload>
						</c:if>

					</div> --%>
				


				</div>
				
				<!--View Uploaded Documents start-->
								<c:if test="${not empty command.attachments}">
									<h4 class="margin-top-0 margin-bottom-10 panel-title">
										<a data-toggle="collapse" href="#DocumentUpload"><spring:message
												code="council.member.upload.doc" /></a>
									</h4>
									<div id="DocumentUpload">
										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="council.member.srno" text="Sr No" /></label></th>
																<%-- <th><label class="tbold"><spring:message
																			code="council.member.attachBy" text="Attach By" /></label></th> --%>
																<th><label class="tbold"><spring:message
																			code="council.member.downlaod" text="Download"/></label></th>
															</tr>

															<c:forEach items="${command.attachments}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td align="center" ><label>${lk.count}</label></td>
																	<%-- <td><label>${lookUp.attBy}</label></td> --%>
																	<td align="center"><c:set var="links"
																			value="${fn:substringBefore(lookUp.uploadedDocumentPath, lookUp.documentName)}" />
																		<apptags:filedownload filename="${lookUp.documentName}"
																			filePath="${lookUp.uploadedDocumentPath}"
																			dmsDocId="${lookUp.documentSerialNo}"
																			actionUrl="CouncilProposalMaster.html?Download"></apptags:filedownload>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</c:if>
				<!--View Uploaded Documents end -->

  <!-- As per HTML Designed thereafter as per nilima mam removed -->
				<!-- Workflow History -->
				<%-- <div class="widget-header">
					<h4>
						<spring:message code="council.workflow.history.title"
							text="Workflow History" />
					</h4>

				</div>
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="workflowHisTable">
						<thead>
							<tr>
								<th><spring:message code="council.srno" text="Sr.No." /></th>
								<th width="10%" align="center"><spring:message
										code="council.workflow.date" text="Date" /></th>
								<th width="15%" align="center"><spring:message
										code="council.workflow.updatedby" text="Updated By" /></th>
								<th width="10%" align="center"><spring:message
										code="council.workflow.status" text="Status" /></th>
								<th width="20%" align="center"><spring:message
										code="council.workflow.current.owner" text="Current Owners" /></th>
								<th width="20%" align="center"><spring:message
										code="council.member.department" text="Department" /></th>
								<th width="20%" align="center"><spring:message
										code="councilworkflow.comments" text="Comments" /></th>
							</tr>
						</thead>
						<c:set var="rowCount" value="0" scope="page" />
						<c:forEach items="${command.actionHistory}" var="action"
							varStatus="status">
							<tr>
								<td><c:set var="rowCount" value="${rowCount+1}"
										scope="page" /> <c:out value="${rowCount}"></c:out></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
										value="${action.dateOfAction}" /></td>
								<td><c:out value="${action.modifiedBy}"></c:out></td>
								<td></td>
								<c:set var="statusString" value="${action.decision}" />
								<td><spring:message
										code="workflow.action.decision.submitted"
										text="${action.decision}" />
								<td><c:out value="${action.empName}"></c:out></td>
								<td></td>
								<td><c:out value="${action.comments}"></c:out></td>
							</tr>
						</c:forEach>
					</table>
				</div> --%>

				<apptags:CheckerAction showInitiator="true" hideForward="true" hideSendback="true" />

				<!-- Start button -->
				<div class="text-center">

					<button type="button" class="btn btn-success" title="Submit"
						onclick="showConfirmBoxForApproval(this);">
						<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.submit" text="Submit" />
					</button>


					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" id="button-Cancel"
						onclick="window.location.href='AdminHome.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="council.button.back" text="Back" />
					</button>
				</div>
				<!-- End button -->

			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->