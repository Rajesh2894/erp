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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/council/councilActionTaken.js"></script>


<!-- Start Main Page Heading -->
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="council.proposal.title" text="Add Action Taken" />
		</h2>
		<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
		<apptags:helpDoc url="CouncilActionTaken.html" />
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
		<form:form action="CouncilActionTaken.html" cssClass="form-horizontal"
			id="actionMasterId">
			<!-- Start Validation include tag -->
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv"></div>
			<!-- End Validation include tag -->
			<form:hidden path="saveMode" id="saveMode" />
			<form:hidden path="couProposalMasterDto.proposalId" />
			<form:hidden path="actionTakenDto.proposalId" id="proposalId" />
			<form:hidden id="proposalType"
				value="${couProposalMasterDto.proposalType}" path="" />
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-target="#a1" data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="council.proposal.detail" text="Proposal Details" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="">
								<div class="form-group">


									<label class="col-sm-2 control-label required-control"><spring:message
											code="council.proposal.date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="couProposalMasterDto.proposalDate"
												id="proposalDate"
												class="form-control mandColorClass datepicker dateValidation "
												value="" disabled="true" data-rule-required="true"
												maxLength="10" onchange="setDefaultFY();" />
											<label class="input-group-addon" for="proposalDate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span><input type="hidden"
												id=proposalDate></label>
										</div>
									</div>

									<label class="control-label col-sm-2"><spring:message
											code="council.member.department" text="Department" /></label>

									<div class="col-sm-4 ">
										<form:select path="couProposalMasterDto.proposalDepId"
											id="proposalDepId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="true">
											<form:option value="">
												<spring:message code='council.management.select' />
											</form:option>
											<c:forEach items="${command.departmentsList}" var="lookUp">
												<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="inwardType"><spring:message
											code="council.proposal.source" text="Source of Proposal" /></label>
									<c:set var="baseLookupCode" value="SOP" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="couProposalMasterDto.proposalSource"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										showOnlyLabel="Source of Proposal" disabled="true" />


									<label class="col-sm-2 control-label required-control"><spring:message
											code="council.proposal.typeOfProposal" text="Type of Proposal" /></label>
									<div class="radio col-sm-4 margin-top-5">
										<label> <form:radiobutton
												path="couProposalMasterDto.proposalType" value="F" id="type"
												class="type" disabled="true" /> <spring:message
												code="council.proposal.type.financial" /></label> <label> <form:radiobutton
												path="couProposalMasterDto.proposalType" value="N" id="type"
												class="type" disabled="true" /> <spring:message
												code="council.proposal.type.non.financial" />
										</label>
									</div>

								</div>

								<div class="form-group">

									<label class="control-label col-sm-2 required-control"><spring:message
											code='council.proposal.details' text='Details of Proposal' /></label>
									<div class="col-sm-10">
										<form:textarea path="couProposalMasterDto.proposalDetails"
											id="proposalDetails" class="form-control alfaNumricSpecial"
											maxlength="1000" disabled="true" />
									</div>

								</div>

								<div class="form-group">

									<label class="control-label col-sm-2"><spring:message
											code='council.proposal.purpose' text='Purpose of Proposal' /></label>
									<div class="col-sm-10">
										<form:textarea path="couProposalMasterDto.purposeremark"
											id="proposalPurpose" class="form-control alfaNumricSpecial"
											maxlength="1000" disabled="true" />
									</div>

								</div>

								<div class="form-group">
									<c:set var="count" value="${count + 1}" scope="page" />
									<label class="control-label col-sm-2"><spring:message
											code="council.member.ward" text="Election Ward" /></label>
									<div class="col-sm-4 ">
										<form:select multiple="true" path="couProposalMasterDto.wards"
											id="wards" cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="true">
											<%-- <form:option value="">Select</form:option> --%>
											<c:forEach items="${command.lookupListLevel1}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>


									<label for="" class="col-sm-2 control-label"> <spring:message
											code="council.member.documents" text="Documents" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
										<c:if test="${command.saveMode eq 'C' }">
											<apptags:formField fieldType="7"
												fieldPath="attachments[${count}].uploadedDocumentPath"
												currentCount="${count}" folderName="${count}"
												fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
											</apptags:formField>
										</c:if>

										<c:if
											test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
											<input type="hidden" name="deleteFileId"
												value="${command.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.attachDocsList[0].attFname}"
												filePath="${command.attachDocsList[0].attPath}"
												actionUrl="CouncilActionTaken.html?Download"></apptags:filedownload>
										</c:if>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>


				<!-- Action taken Table -->


				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">

							<a data-target="#a2" data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#a2"> <spring:message
									code="council.proposal.title" text="Add Action Taken" /></a>

						</h4>
					</div>
					<c:set var="e" value="0"></c:set>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">
							<table class="table table-bordered table-striped"
								id="actionTakenTable">
								<thead>
									<tr>
										<th scope="col" width="3%"><spring:message
												code="council.srno" text="Sr.No." /></th>
										<th align="center"><spring:message
												code="council.member.date" text="Date" /></th>
										<th align="center"><spring:message
												code="council.member.department" text="Department" /><i
											class="text-red-1">*</i></th>
										<th align="center"><spring:message
												code="council.member.empName" text="Employee Name" /></th>
										<th align="center"><spring:message
												code="council.member.actionTaken"
												text="Details of Action Taken" /><i class="text-red-1">*</i></th>
										<c:if test="${command.saveMode ne 'V'}">
											<th width="50"><a href="#" data-toggle="tooltip"
												data-placement="top" class="btn btn-blue-2  btn-sm"
												data-original-title="Add" onclick="addEntryData();"><strong
													class="fa fa-plus"></strong><span class="hide"></span></a></th>
										</c:if>

									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.councilActionTakenDto)>0 }">

											<c:forEach var="actionData"
												items="${command.councilActionTakenDto}">
            
												<tr class="appendableClass">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${e}"
															value="${e+1}" disabled="true" readonly="true" /></td>
													<td>
														<div class="input-group">
															<form:input path="councilActionTakenDto[${e}].propDate"
																id="patDate${e}" class="form-control datepicker"
																value=" " readonly="false" maxLength="10"
																disabled="${ (command.saveMode eq 'V'  || actionData.propDisableFlag eq 'Y') ? true : false }" />
															<label class="input-group-addon" for="patDate"><i
																class="fa fa-calendar"></i><span class="hide"> <spring:message
																		code="" text="icon" /></span><input type="hidden" id=patDate></label>
														</div>

													</td>

													<td><form:select
															path="councilActionTakenDto[${e}].patDepId"
															id="patDepId${e}" cssClass="form-control"
															class="form-control mandColorClass"
															disabled="${( command.saveMode eq 'V'  || actionData.propDisableFlag eq 'Y' )? true : false }"
															data-rule-required="true" isMandatory="true">
															<form:option value="">
																<spring:message code='council.management.select' />
															</form:option>
															<c:forEach items="${command.departments}" var="lookUp">
																<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:select
															path="councilActionTakenDto[${e}].patEmpId"
															id="patEmpId${e}" cssClass="form-control"
															disabled="${(command.saveMode eq 'V'  || actionData.propDisableFlag eq 'Y' )? true : false }"
															class="form-control mandColorClass"  onchange="searchForActiveEmp(this)">
															<form:option value="0">
																<spring:message code='council.management.select' />
															</form:option>
															<c:forEach items="${command.listOfAllemployee}" var="emp">
																<form:option value="${emp.empId}">${emp.fullName}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input
															path="councilActionTakenDto[${e}].patDetails"
															cssClass="form-control mandColorClass preventSpace"
															class="preventSpace" id="patDetails${e}" maxlength="250"
															disabled="${(command.saveMode eq 'V' || actionData.propDisableFlag eq 'Y') ? true : false }" /></td>
													<c:if test="${command.saveMode ne 'V'}">
														<td><a href="#" data-toggle="tooltip"
															data-placement="top" class="btn btn-danger btn-sm"
															data-original-title="Delete"
															disabled="${ (command.saveMode eq 'V'  || actionData.propDisableFlag eq 'Y') ? true : false }"
															onclick="deleteEntry($(this),'removedIds');"> <strong
																class="fa fa-trash"></strong> <span class="hide"><spring:message
																		code="lgl.delete" text="Delete" /></span>
														</a></td>
													</c:if>
													<c:set var="e" value="${e + 1}" />
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>


											<c:if test="${command.saveMode ne 'V'}">
												<tr class="appendableClass">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${e}"
															value="${e+1}" disabled="true" readonly="true" /></td>
													<td>
														<div class="input-group">
															<form:input path="councilActionTakenDto[${e}].patDate"
																id="patDate${e}" class="form-control   datepicker"
																value=" " readonly="false" maxLength="10"
																data-rule-required="true"
																disabled="${command.saveMode eq 'V'}" />
															<label class="input-group-addon" for="patDate"><i
																class="fa fa-calendar"></i><span class="hide"> <spring:message
																		code="" text="icon" /></span><input type="hidden" id=patDate></label>
														</div>
													</td>

													<td><form:select
															path="councilActionTakenDto[${e}].patDepId"
															id="patDepId${e}" cssClass="form-control"
															class="form-control mandColorClass" isMandatory="true"
															data-rule-required="true"
															disabled="${command.saveMode eq 'V'}">
															<form:option value="">
																<spring:message code='council.management.select' />
															</form:option>
															<c:forEach items="${command.departments}" var="lookUp">
																<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:select
															path="councilActionTakenDto[${e}].patEmpId"
															id="patEmpId${e}" cssClass="form-control "
															class="form-control mandColorClass"
															disabled="${command.saveMode eq 'V'}">
															<form:option value="0">
																<spring:message code='council.management.select' />
															</form:option>
															<c:forEach items="${command.employee}" var="emp">
																<form:option value="${emp.empId}">${emp.fullName}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:textarea
															path="councilActionTakenDto[${e}].patDetails"
															id="patDetails${e}"
															cssClass="form-control mandColorClass preventSpace"
															maxlength="250" disabled="${command.saveMode eq 'V'}" /></td>

													<c:if test="${command.saveMode ne 'V'}">
														<td><a href="#" data-toggle="tooltip"
															data-placement="top" class="btn btn-danger btn-sm"
															data-original-title="Delete"
															onclick="deleteEntry($(this),'removedIds');"> <strong
																class="fa fa-trash"></strong> <span class="hide"><spring:message
																		code="lgl.delete" text="Delete" /></span>
														</a></td>
													</c:if>
												</tr>
											</c:if>

										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>




				<!-- Start button -->
				<div class="padding-top-10 text-center">
					<c:if test="${command.saveMode ne 'V'}">

						<button type="button" onclick="saveActionForm(this)"
							class="btn btn-success" title="Submit">
							<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.submit" text="Submit" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backActionForm();" id="button-Cancel">
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
