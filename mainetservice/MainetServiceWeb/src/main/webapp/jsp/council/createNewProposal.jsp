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
<script type="text/javascript" src="js/council/councilProposalMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <script src="/assets/libs/chosen/chosen.jquery.js"
	type="text/javascript"></script> -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.proposal.master.title"
					text="Create Proposal" />
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilProposalMaster.html" />
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
			<form:form action="CouncilProposalMaster.html"
				cssClass="form-horizontal" id="ProposalMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="couProposalMasterDto.proposalId" />
				<form:hidden id="proposalType" value="${couProposalMasterDto.proposalType}" path=""/>
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

										<%-- <apptags:input labelCode="council.proposal.no"
						path="couProposalMasterDto.proposalNo" cssClass="form-control"
						isDisabled="${command.saveMode eq 'V'}"></apptags:input> --%>


										<%-- <apptags:date fieldclass="datepicker"
						datePath="couProposalMasterDto.proposalDate"
						labelCode="council.proposal.date" cssClass="mandColorClass"
						isMandatory="true"  isDisabled="${command.saveMode eq 'V'}"></apptags:date> --%>

										<label class="control-label col-sm-2 required-control"><spring:message
												code="council.proposal.date" text="Proposal Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="couProposalMasterDto.proposalDate"
													id="proposalDate"
													class="form-control mandColorClass datepicker dateValidation "
													value="" disabled="${command.saveMode eq 'V'}"
													data-rule-required="true" maxLength="10"
													onchange="setDefaultFY();" />
												<label class="input-group-addon" for="proposalDate"><i
													class="fa fa-calendar"></i><span class="hide"> <spring:message
															code="" text="icon" /></span><input type="hidden"
													id=proposalDate></label>
											</div>
										</div>

										<label class="control-label col-sm-2"><spring:message
												code="council.member.department" text="Department" /></label>
												
										<!-- As per requirement changes done 05-11-19 -->
										<div class="col-sm-4 ">
											<form:select path="couProposalMasterDto.proposalDepId"
												id="proposalDepId"
												cssClass="form-control chosen-select-no-results"
												class="form-control mandColorClass"
												data-rule-required="true"
												disabled="${command.saveMode eq 'V'}">
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

										<%-- <!--  those Department will come which is mapped with employee -->
										<div class="col-sm-4">
											<form:hidden path="couProposalMasterDto.proposalDepId"
												id="proposalDepId" />
											<form:input path="couProposalMasterDto.proposalDeptName"
												readonly="true" cssClass=" form-control"></form:input>
										</div> --%>

										<!-- In below account integration section amount added -->
										<%-- <apptags:input labelCode="council.proposal.amount"
						path="couProposalMasterDto.proposalAmt"
						cssClass="form-control hasDecimal" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}"
						onChange="getAmountFormatInDynamic((this),'proposalAmt')"></apptags:input> --%>

									</div>
									<!-- <button type="button" class="btn btn-primary" title="Check Budget">Check Budget</button> -->

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
											showOnlyLabel="Source of Proposal"
											disabled="${command.saveMode eq 'V'}" />

										<%-- <apptags:radio radioLabel="Financial,Non Financial"
											radioValue="F,N" labelCode="Type of Proposal"
											path="couProposalMasterDto.proposalType"
											disabled="${command.saveMode eq 'V'}" isMandatory="true"></apptags:radio> --%>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="council.proposal.typeOfProposal" text="Type of Proposal" /></label>
										<div class="radio col-sm-4 margin-top-5">
											<label> <form:radiobutton
													path="couProposalMasterDto.proposalType" value="F"
													id="type" class="type"
													disabled="${command.saveMode eq 'V'}" /> <spring:message
													code="council.proposal.type.financial" /></label> <label>
												<form:radiobutton path="couProposalMasterDto.proposalType"
													value="N" id="type" class="type"
													disabled="${command.saveMode eq 'V'}" /> <spring:message
													code="council.proposal.type.non.financial" />
											</label>
										</div>

									</div>
									
									<div class="form-group">

										<label class="control-label col-sm-2"><spring:message
												code='council.proposal.purpose' text='Purpose of Proposal' /></label>
										<div class="col-sm-10">
											<form:textarea path="couProposalMasterDto.purposeremark"
												id="proposalPurpose" class="form-control alfaNumricSpecial"
												onkeyup="countChar(this,1000,'proposalPurposeCount')" onfocus="countChar(this,1000,'proposalPurposeCount')"
												maxlength="1000" disabled="${command.saveMode eq 'V'}" />
												<div class="pull-right">
													<spring:message code="charcter.remain" text="characters remaining " /><span id="proposalPurposeCount">1000</span>
												</div>
										</div>

									</div>

									<div class="form-group">

										<label class="control-label col-sm-2 required-control"><spring:message
												code='council.proposal.details' text='Details of Proposal' /></label>
										<div class="col-sm-10">
											<form:textarea path="couProposalMasterDto.proposalDetails"
												id="proposalDetails" class="form-control alfaNumricSpecial"
												onkeyup="countChar(this,3000,'proposalDetailCount')" onfocus="countChar(this,3000,'proposalDetailCount')"
												maxlength="3000" disabled="${command.saveMode eq 'V'}" />
												<div class="pull-right">
													<spring:message code="charcter.remain" text="characters remaining " /><span id="proposalDetailCount">3000</span>
												</div>
										</div>

									</div>
									
									

									<div class="form-group">
										<c:set var="count" value="${count + 1}" scope="page" />
										<label class="control-label col-sm-2"><spring:message
												code="council.member.ward" text="Election Ward" /></label>
										<div class="col-sm-4 ">
											<form:select multiple="true"
												path="couProposalMasterDto.wards" id="wards"
												cssClass="form-control chosen-select-no-results"
												class="form-control mandColorClass"
												data-rule-required="true"
												disabled="${command.saveMode eq 'V'}">
												<%-- <form:option value="">Select</form:option> --%>
												<c:forEach items="${command.lookupListLevel1}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>

										</div>

										<c:if test="${command.saveMode eq 'C'}">
										<label for="" class="col-sm-2 control-label"> <spring:message
												code="council.member.documents" text="Documents" /><span
											class="mand">*</span></label>
										<c:set var="count" value="0" scope="page"></c:set>
										<div class="col-sm-4">
											<c:if
												test="${command.saveMode eq 'C'  || command.saveMode eq 'E' }">
												<apptags:formField fieldType="7"
													fieldPath="attachments[${count}].uploadedDocumentPath"
													currentCount="${count}" folderName="${count}"
													fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
													isMandatory="true" maxFileCount="WORKS_MANAGEMENT_MAXSIZE"
													validnFunction="ALL_UPLOAD_VALID_EXTENSION"
													cssClass="clear">
												</apptags:formField>
												<div class="col-sm-12">
													<small class="text-blue-2 "> <spring:message
															code="council.validator.fileUploadNote"
															text="(Upload File upto 2MB and only pdf,doc,docx,jpeg,jpg,png,gif)" />
													</small>
												</div>
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
													actionUrl="CouncilProposalMaster.html?Download"></apptags:filedownload>
											</c:if>

										</div>
										</c:if>	


									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Account Integration section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="council.account.info"
										text="Year-wise Financial Information" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="financeDataDetails">
										<thead>
											<tr>
												<th width="25%"><spring:message code="council.fin.year"
														text="Financial Year" /></th>
												<th width="35%"><spring:message
														code="council.budget.head" text="Budget Head" /><span
											class="mand">*</span></th>
												<th width="20%"><spring:message
														code="council.proposal.amount"
														text="Amount to be Approved" /><span
											class="mand">*</span></th>
												<th width="20%"><spring:message
														code="council.check.budget" text="Budget" /></th>
											</tr>
										</thead>
										<tbody>
											<tr class="finacialInfoClass">

												<td><form:select path="couProposalMasterDto.yearId"
														cssClass="form-control chosen-select-no-results"
														id="yearId" disabled="${command.saveMode eq 'V'}">
														<form:option value="">
															<spring:message code='council.management.select'
																text="Select" />
														</form:option>
														<c:forEach items="${command.faYears}" var="lookUp">
															<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
														</c:forEach>
													</form:select></td>
												<c:choose>
													<c:when test="${command.cpdMode eq 'L'}">
														<td><form:select
																path="couProposalMasterDto.sacHeadId"
																cssClass="form-control mandColorClass chosen-select-no-results"
																id="sacHeadId" disabled="${command.saveMode eq 'V'}">
																<form:option value="">
																	<spring:message code='council.management.select'
																		text="Select" />
																</form:option>
																<c:forEach items="${command.budgetList}" var="lookUp">
																	<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																</c:forEach>
															</form:select></td>
													</c:when>
													<c:otherwise>
														<td><form:input
																path="couProposalMasterDto.budgetHeadDesc"
																cssClass="form-control" id="budgetHeadDesc"
																disabled="${command.saveMode eq 'V'}" /></td>
													</c:otherwise>
												</c:choose>

												<td><form:input path="couProposalMasterDto.proposalAmt"
														cssClass="form-control hasDecimal text-right" id="Amount"
														disabled="${command.saveMode eq 'V'}"
														onChange="getAmountFormatInDynamic((this),'proposalAmt')" /></td>
												<c:choose>
													<c:when
														test="${command.saveMode eq 'V' || command.cpdMode eq 'S'}">
														<td class="text-center">
															<button type="button" class="btn btn-primary btn-sm"
																id="checkBudget" disabled
																onclick="return viewExpenditureDetails();">
																<spring:message code="council.check.budget"
																	text="Check Budget" />
																<i class="fa fa-eye" aria-hidden="true"></i>
															</button>
														</td>
													</c:when>
													<c:otherwise>
														<td class="text-center">
															<button type="button" class="btn btn-primary btn-sm"
																id="checkBudget"
																onclick="return viewExpenditureDetails();">
																<spring:message code="council.check.budget"
																	text="Check Budget" />
																<i class="fa fa-eye" aria-hidden="true"></i>
															</button>
														</td>
													</c:otherwise>
												</c:choose>
											</tr>
										</tbody>

									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--View Uploaded Documents start-->
								<c:if test="${not empty command.documentDtos}">
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

															<c:forEach items="${command.documentDtos}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td align="center"><label>${lk.count}</label></td>
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
				
				<!-- Start button -->
				<div class="text-center">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button"
							onclick="saveProposalform(this,'${command.cpdMode}')"
							class="btn btn-success" title="Submit">
							<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.submit" text="Submit" />
						</button>


					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="button" onclick="resetProposalMaster(this);"
							class="btn btn-warning" title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.reset" text="Reset" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backProposalMasterForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="council.button.back" text="Back" />
					</button>
				</div>
				<!-- End button -->


				<form:hidden path="couProposalMasterDto.proposalStatus" value="D" />
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->