<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="tender.init"
					text="Tender/Quotation Initialization" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="TenderInitiation.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="TenderInitiation.html" class="form-horizontal"
				name="TenderInitiation" id="TenderInitiation"
				modelAttribute="command">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="mode" id="mode" />
				<form:hidden path="removeWorkIdArray" id="removeWorkIdArray" />
				<form:hidden path="initiationDto.tndId" id="tndId" />
				<form:hidden path="" id="project"
					value="${command.initiationDto.projId }" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.dept" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="initiationDto.deptId"
							cssClass="form-control chosen-select-no-results" id="deptId"
							onchange="getProjects(this);"
							disabled="${command.mode eq 'E' || command.mode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.departmentList}" var="list">
								<form:option value="${list.dpDeptid}" code="${list.dpDeptid}">${list.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>

					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" text="Project Name" /></label>
					<div class="col-sm-4">
						<c:choose>
							<c:when test="${command.mode eq 'C'}">
								<form:select path="initiationDto.projId"
									cssClass="form-control chosen-select-no-results"
									data-rule-required="true" id="projId"
									onchange="getApprovedWork(this);">
									<form:option value="">
										<spring:message code='work.management.select' />
									</form:option>
								</form:select>
							</c:when>
							<c:otherwise>
								<form:input path="initiationDto.projectName" id="projName"
									class="form-control preventSpace" data-rule-required="true"
									readonly="true" />
								<form:hidden path="initiationDto.projId" id="projId" />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.resolutionno" text="Resolution No." /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.resolutionNo" id="resolutionNo"
							class="form-control preventSpace" maxlength="50"
							data-rule-required="true" />
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.resolutiondate" text="Resolution Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.resolutionDate"
								id="resolutionDate" class="form-control resolutionDatePicker"
								value="" disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="resolutionDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=resolutionDate></label>
						</div>
					</div>
				</div>

				<div class="form-group">


					<label class="col-sm-2 control-label"><spring:message
							code="wms.PreBidMeetingDate" text="Pre-Bid Meeting Date" /></label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.preBidMeetDateDesc"
								id="preBidMeetDate" class="form-control preBidMeetDatePicker"
								readonly="true" disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="preBidMeetDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=preBidMeetDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="wms.MeetingLocation" text="Meeting Location" /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.tenderMeetingLoc" id=""
							readonly="${command.mode eq 'V'}" class="form-control"
							maxlength="100" />
					</div>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.issuefromdate" text="Issue From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.issueFromDate" id="issueFromDate"
								class="form-control issueFromDatePicker" value=""
								readonly="true" disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="issueFromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=issueFromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.issuetilldate" text="Issue Till Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.issueToDate" id="issueToDate"
								class="form-control issueToDatePicker" value="" readonly="true"
								disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="issueToDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=issueToDate></label>
						</div>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.publishdate" text="Publish Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.publishDateDesc" id="publishDate"
								class="form-control publishDatePicker" readonly="true"
								disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="publishDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=publishDate></label>
						</div>
					</div>
				</div>



				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.techbid.opendate" text="Technical Bid Open Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.technicalOpenDateDesc"
								id="technicalOpenDate"
								class="form-control technicalOpenDatePicker" readonly="true"
								disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="technicalOpenDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id=technicalOpenDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="tender.finbid.opendate" text="Financial Bid Open Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="initiationDto.financialeOpenDateDesc"
								id="financialeOpenDate"
								class="form-control financialeOpenDatePicker" readonly="true"
								disabled="${command.mode eq 'V'}" />
							<label class="input-group-addon" for="financialeOpenDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id=financialeOpenDate></label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="tender.type" text="Tender Type" /> </label>
					<c:set var="CATlookUp" value="CAT" />
					<apptags:lookupField items="${command.getLevelData(CATlookUp)}"
						path="initiationDto.tenderCategory"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" />

					<label class="col-sm-2 control-label required-control"><spring:message
							code="tender.validity" text="valdity of tender" /></label>
					<div class="col-sm-4">
						<form:input path="initiationDto.tndValidityDay"
							id="tndValidityDay" class="form-control hasNumber text-right"
							maxlength="3" placeholder="999" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="TenderAmt"><spring:message
							code="scheme.master.upload" text="Upload
						Document" /></label>
					<c:set var="count" value="0" scope="page"></c:set>
					<div class="col-sm-4">
						<apptags:formField fieldType="7"
							fieldPath="attachments[${count}].uploadedDocumentPath"
							currentCount="${count}" showFileNameHTMLId="true"
							folderName="${count}" fileSize="WORK_COMMON_MAX_SIZE"
							isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
							cssClass="clear">
						</apptags:formField>
						<small class="text-blue-2"> <spring:message
							code="scheme.master.UploadFile"
				            text="(Upload Image File upto 5 MB and Only pdf,doc,docx,xls,xlsx extension(s) file(s) are allowed.)" />
					    </small>
						<c:if
							test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
							<input type="hidden" name="deleteFileId"
								value="${command.attachDocsList[0].attId}">
							<input type="hidden" name="downloadLink"
								value="${command.attachDocsList[0]}">
							<apptags:filedownload
								filename="${command.attachDocsList[0].attFname}"
								filePath="${command.attachDocsList[0].attPath}"
								actionUrl="TenderInitiation.html?Download"></apptags:filedownload>
						</c:if>
					</div>
					<c:set var="count" value="${count + 1}" scope="page" />
				</div>

				<div class="workClass"></div>
				<div class="workProj">
					<c:if
						test="${command.mode eq 'E' || command.mode eq 'V' || not empty command.workList }">
						<div>
							<h4>
								<spring:message code="add.work.estimate.tender.form"
									text="Add Work Estimates"></spring:message>
							</h4>
							<c:set var="c" value="0" scope="page" />
							<div class="table-responsive">
								<table class="table table-bordered table-striped" id="tendWorks">
									<thead>
										<tr>
											<th width="2%"><spring:message
													code='work.management.select' text="Select" /></th>
											<th width="10%"><spring:message code='work.def.workCode'
													text="Work Code" /></th>
											<th width="15%"><spring:message code='work.def.workname'
													text="Work Name" /></th>
											<th width="8%"><spring:message code='tender.type'
													text="Tender Type" /></th>
											<th width="6%"><spring:message code='wms.TenderFees'
													text="Tender Fees" /></th>
											<th width="8%"><spring:message
													code='tender.contractorClass' text="Contractor Class" /></th>
											<th width="8%"><spring:message code='tender.emd'
													text="Earnest Money Deposit" /></th>

											<th width="8%"><spring:message
													code='tender.WorkDuration' text="Time of Completion" /></th>
											<th width="8%"><spring:message
													code='wms.WorkDurationUnit' text="Work Duration Unit" /></th>

											<th width="10%"><spring:message
													code='tender.estimate.cost' text="" /></th>
											<th width="17%"><spring:message
													code="works.management.action" text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${command.workList}" var="mstDto">
											<tr class="tenderWork">
												<td align="center"><form:input type="hidden"
														path="initiationDto.workDto[${c}].tndWId" id="tndWId${c}" />
													<form:input type="hidden"
														path="initiationDto.workDto[${c}].workId"
														value="${mstDto.workId}" /> <c:if
														test="${command.modeCpd eq 'Y'}">
														<form:input type="hidden"
															path="initiationDto.workDto[${c}].workEstimateAmt"
															value="${mstDto.estimateWithoutOverheadAmt}"
															id="workEstimateAmt${c}" />
													</c:if> <c:if test="${command.modeCpd ne 'Y'}">
														<form:input type="hidden"
															path="initiationDto.workDto[${c}].workEstimateAmt"
															value="${mstDto.workEstAmt}" id="workEstimateAmt${c}" />
													</c:if> <form:checkbox
														path="initiationDto.workDto[${c}].initiated"
														id="initiated${c}" onclick="setAmount(${c})" />
												<td align="center">${mstDto.workcode}</td>
												<td>${mstDto.workName}</td>

												<td><form:select
														path="initiationDto.workDto[${c}].tenderType"
														cssClass="form-control mandColorClass "
														id="tenderType${c}" data-rule-required="true">
														<form:option value="">
															<spring:message code='work.management.select' />
														</form:option>
														<c:forEach items="${command.tenderTpyes}" var="payType">
															<form:option value="${payType.lookUpId }"
																code="${payType.lookUpCode}">${payType.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input
														path="initiationDto.workDto[${c}].tenderFeeAmt"
														cssClass="form-control mandColorClass text-right"
														id="tenderFeeAmt${c}"
														onkeypress="return hasAmount(event, this, 6, 2)"
														onchange="getAmountFormatInDynamic((this),'tenderFeeAmt')"
														placeholder="999999.99" data-rule-required="true" /></td>

												<td><form:select
														path="initiationDto.workDto[${c}].venderClassId"
														cssClass="form-control mandColorClass"
														id="venderClassId${c}" data-rule-required="true">
														<form:option value="">
															<spring:message code="work.management.select" />
														</form:option>
														<c:forEach items="${command.venderCategoryList}"
															var="vClass">
															<form:option value="${vClass.lookUpId}"
																code="${vClass.lookUpCode}">${vClass.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input
														path="initiationDto.workDto[${c}].tenderSecAmt"
														cssClass="form-control mandColorClass text-right"
														id="tenderSecAmt${c}"
														onkeypress="return hasAmount(event, this, 6, 2)"
														onchange="getAmountFormatInDynamic((this),'tenderSecAmt')"
														placeholder="999999.99" data-rule-required="true"
														readonly="true" /></td>



												<td><form:input
														path="initiationDto.workDto[${c}].vendorWorkPeriod"
														cssClass="form-control mandColorClass"
														id="vendorWorkPeriod${c}" data-rule-required="true" /></td>
												<td><form:select
														path="initiationDto.workDto[${c}].vendorWorkPeriodUnit"
														cssClass="form-control mandColorClass"
														id="vendorWorkPeriodUnit${c}" data-rule-required="true">
														<form:option value="">
															<spring:message code="work.management.select" />
														</form:option>
														<c:forEach items="${command.workDurationUnit}"
															var="duration">
															<form:option value="${duration.lookUpId}"
																code="${duration.lookUpCode}">${duration.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<c:if test="${command.modeCpd eq 'Y'}">
													<td align="right">${mstDto.workEstAmt}</td>
												</c:if>
												<c:if test="${command.modeCpd ne 'Y'}">
													<td align="right">${mstDto.workEstAmt}</td>
												</c:if>
												<c:choose>
													<c:when test="${mstDto.tenderInitiated}">
														<script>
						   $(document).ready(function(){
							   $('#initiated'+'${c}').prop('checked', true);
						   });
				               </script>
													</c:when>
													<c:otherwise>
														<script>
						   $(document).ready(function(){
							   var isSelected = $("#initiated"+'${c}').is(':checked');
							   if(isSelected){
								   $("#tenderFeeAmt"+'${c}').prop("disabled",false);
									$("#venderClassId"+'${c}').prop("disabled",false);
									$("#tenderSecAmt"+'${c}').prop("disabled",false);
									$("#vendorWorkPeriodUnit"+'${c}').prop("disabled",false);
									$("#vendorWorkPeriod"+'${c}').prop("disabled",false);
							   }else{
								   $("#tenderFeeAmt"+'${c}').prop("disabled",true);
									$("#venderClassId"+'${c}').prop("disabled",true);
									$("#tenderSecAmt"+'${c}').prop("disabled",true);
									$("#vendorWorkPeriodUnit"+'${c}').prop("disabled",true);
									$("#vendorWorkPeriod"+'${c}').prop("disabled",true); 
							   }
							   
						   });
				               </script>
													</c:otherwise>
												</c:choose>

												<td class="text-center "><button type="button"
														class="btn btn-blue-2 btn-sm viewEstimate"
														onClick="viewWorkEstimate(${mstDto.workId})"
														title="<spring:message code="works.management.view.estimate"></spring:message>">
														<i class="fa fa-eye"></i>
													</button> <c:if
														test="${mstDto.workStatus eq 'I' || mstDto.workStatus eq 'T'}">
														<button type="button"
															class="btn btn-warning btn-sm viewNIT  "
															onClick="printNoticeInvitingTender(${mstDto.workId})"
															title="Notice Inviting Tender">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															NIT
														</button>
														<button type="button" class="btn  btn-sm viewNIT  "
															style="background: #4B515D"
															onClick="printTenderFormReport(${mstDto.workId},'B')"
															title="Form B">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															<font color="yellow">FB</font>
														</button>
													</c:if> <c:if
														test="${(mstDto.workStatus eq 'I' || mstDto.workStatus eq 'T') && (mstDto.workEstAmt lt command.amountToCheckValidation) && (mstDto.tenderpercent eq 'PER')}">
														<button type="button"
															class="btn btn-info btn-sm viewFormA "
															style="margin-top: 5px;"
															onClick="printTenderFormReport(${mstDto.workId} ,'A')"
															title="Form A">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															FA
														</button>
													</c:if> <c:if
														test="${(mstDto.workStatus eq 'I' || mstDto.workStatus eq 'T' ) &&  (mstDto.workEstAmt gt command.amountToCheckValidation) && (mstDto.tenderpercent eq 'PER')}">
														<button type="button"
															class="btn btn-succes btn-sm viewPQR   "
															onClick="preQualificationDocument(${mstDto.workId})"
															title="Pre Qualification Documents">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															PQR
														</button>

														<button type="button"
															class="btn btn-info btn-sm viewFormA "
															style="margin-top: 5px;"
															onClick="printTenderFormReport(${mstDto.workId},'A')"
															title="Form A">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															FA
														</button>
													</c:if> <c:if
														test="${(mstDto.workStatus eq 'I' || mstDto.workStatus eq 'T' ) &&  (mstDto.workEstAmt gt command.amountToCheckValidation) && (mstDto.tenderpercent eq 'AMT')}">
														<button type="button"
															class="btn btn-succes btn-sm viewPQR   "
															onClick="preQualificationDocument(${mstDto.workId})"
															title="Pre Qualification Documents">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															<spring:message code="tender.report.pqr" text="PQR"></spring:message>
														</button>

														<button type="button"
															class="btn btn-info btn-sm viewFormF "
															style="margin-top: 5px;"
															onClick="printTenderFormReport(${mstDto.workId},'F')"
															title="Form F">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															<spring:message code="tender.report.form.f" text="FF"></spring:message>
														</button>

													</c:if> <c:if
														test="${(mstDto.workStatus eq 'I' || mstDto.workStatus eq 'T') && (mstDto.workEstAmt lt command.amountToCheckValidation) && (mstDto.tenderpercent eq 'AMT')}">
														<button type="button"
															class="btn btn-info btn-sm viewFormF "
															style="margin-top: 5px;"
															onClick="printTenderFormReport(${mstDto.workId} ,'F')"
															title="Form F">
															<i class="glyphicon glyphicon-print" aria-hidden="true"></i>
															<spring:message code="tender.report.form.f" text="FF"></spring:message>
														</button>
													</c:if></td>
												<c:set var="c" value="${c + 1}" scope="page" />
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td align="left"><b><spring:message
														code="work.estimate.total.amount" text="Total Amount" /></b></td>
											<td align="right"><form:input
													class="form-control text-right" type="input"
													readonly="true" id="totalAmt"
													path="initiationDto.tenderTotalEstiAmount" /></td>
											<td></td>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>

					</c:if>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.mode eq 'C' || command.mode eq 'E'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveTender(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.mode eq 'C' }">
						<button class="btn btn-warning" type="button" id="resetTender">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='TenderInitiation.html'"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>

			</form:form>
		</div>
	</div>
</div>