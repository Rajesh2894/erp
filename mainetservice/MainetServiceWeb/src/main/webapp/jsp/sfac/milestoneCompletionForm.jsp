<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/sfac/milestoneCompletion.js"></script>

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.milestone.comp.title"
					text="Milestone Completion Request Form" />
			</h2>
			<apptags:helpDoc url="MilestoneCompletionForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="MilestoneEntryForm"
				action="MilestoneCompletionForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#milestoneDetails"> <spring:message
										code="sfac.milestone.details" text="Milestone Details" />
								</a>
							</h4>
						</div>
						<div id="milestoneDetails" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.milestone.name" text="Milestone Name" /></label>
									<div class="col-sm-4">
										<form:select path="dto.msId" id="msId" disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results" onchange="getMilestoneDetails(this)">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select'" />
											</form:option>
											<c:forEach items="${command.milestoneMasterDtos}" var="dto">
												<form:option value="${dto.msId}" code="${dto.msId}">${dto.milestoneId}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.IA.name" text="IA Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.iaName" id="iaName"
											class="form-control " disabled="true" />
										<form:hidden path="dto.iaId" id="iaId" />

									</div>

								</div>



								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.date.of.workorder"
											text="Date Of Work Order" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.dateOfWorkOrder" type="text"
												class="form-control datepicker mandColorClass"
												disabled="true"
												id="dateOfWorkOrder" placeholder="dd/mm/yyyy"
												readonly="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>

									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.comp.targetDate" text="Target Date " /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.targetDate" type="text"
												class="form-control datepicker mandColorClass"
												disabled="true"
												id="targetDate" placeholder="dd/mm/yyyy" readonly="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>

									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.comp.actualDate"
											text="Actual Date Of Completion" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.actualCompletionDate" type="text"
												class="form-control datepicker mandColorClass"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="actualCompletionDate" placeholder="dd/mm/yyyy"
												readonly="true" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>

									</div>



								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.comp.allocatedBudget"
											text="Allocated Budget" /></label>
									<div class="col-sm-4">
										<form:input path="dto.allocationBudget" id="allocationBudget"
											onkeypress="return hasAmount(event, this, 10, 2)"
											class="form-control"
											disabled="true" />


									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.comp.invoiceAmt" text="Invoice Amount" /></label>
									<div class="col-sm-4">
										<form:input path="dto.invoiceAmount" id="invoiceAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											class="form-control"
											disabled="${command.viewMode eq 'V' ? true : false }" />


									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.milestone.comp.invoiceUpload"
											text="Invoice Upload" /></label>
									<div class="col-sm-4">


										<c:if test="${command.viewMode ne 'V'}">
											<apptags:formField fieldType="7"
												fieldPath="dto.attachments[0].uploadedDocumentPath"
												currentCount="0" showFileNameHTMLId="true"
												folderName="bpdfg0" fileSize="CARE_COMMON_MAX_SIZE"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="ALL_UPLOAD_VALID_EXTENSION">
											</apptags:formField>
										</c:if>
										<c:if
											test="${command.dto.attachDocsList[0] ne null  && not empty command.dto.attachDocsList[0] }">
											<input type="hidden" name="deleteFileId"
												value="${command.dto.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.dto.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.dto.attachDocsList[0].attFname}"
												filePath="${command.dto.attachDocsList[0].attPath}"
												actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
												<c:if test="${command.viewMode ne 'V'}">
											<small class="text-blue-2"> <spring:message
													code="sfac.fpo.checklist.validation"
													text="(Upload Image File upto 5 MB)" />
											</small>
											</c:if>
										</c:if>


									</div>



								</div>


							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#docDetails">
									<spring:message code="sfac.milestone.comp.docdetails.title"
										text="Document Details" />
								</a>
							</h4>
						</div>
						<div id="docDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="msDetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th><spring:message code="sfac.fpo.pm.docDesc"
													text="Document Description" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseDocUpload"
													text="Document Upload" /></th>


										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.milestoneCompletionDocDetailsDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.milestoneCompletionDocDetailsDtos}"
													varStatus="status">
													<tr class="appendableMSDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>





														<td><form:input
																path="dto.milestoneCompletionDocDetailsDtos[${d}].docDescription"
																id="docDescription${d}" class="form-control "
																disabled="true" /></td>

														<td><c:if test="${command.viewMode ne 'V'}">
																<apptags:formField fieldType="7"
																	fieldPath="dto.milestoneCompletionDocDetailsDtos[${d}].attachments[0].uploadedDocumentPath"
																	currentCount="${d+1}" showFileNameHTMLId="true"
																	folderName="${d+1}" fileSize="CARE_COMMON_MAX_SIZE"
																	isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="ALL_UPLOAD_VALID_EXTENSION">
																</apptags:formField>
															</c:if> <c:if
																test="${command.dto.milestoneCompletionDocDetailsDtos[d].attachDocsList[0] ne null  && not empty command.dto.milestoneCompletionDocDetailsDtos[d].attachDocsList[0] }">
																<input type="hidden" name="deleteFileId"
																	value="${command.dto.milestoneCompletionDocDetailsDtos[d].attachDocsList[0].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.dto.milestoneCompletionDocDetailsDtos[d].attachDocsList[0]}">
																<apptags:filedownload
																	filename="${command.dto.milestoneCompletionDocDetailsDtos[d].attachDocsList[0].attFname}"
																	filePath="${command.dto.milestoneCompletionDocDetailsDtos[d].attachDocsList[0].attPath}"
																	actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
																
															</c:if>
															<c:if test="${command.viewMode ne 'V'}">
															<small class="text-blue-2"> <spring:message
																		code="sfac.fpo.checklist.validation"
																		text="(Upload Image File upto 5 MB)" />
																</small>
																</c:if>
															</td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											
										</c:choose>

									</tbody>
								</table>




							</div>
						</div>
					</div>
					

				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveMilestoneCompletionForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm();">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="MilestoneCompletionForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>