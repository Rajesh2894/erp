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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rti/rtiPioResponse.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>


<style>
textarea.form-control {
	resize: vertical !important;
	height: 2.3em;
}
.empRequest td select{
	width:310px !important;
}
.empRequest td textarea{
	height:3rem !important;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="rti.applicantDetailsView"></spring:message></b>
			</h2>

			<apptags:helpDoc url="RtiApplicationDetailForm.html"></apptags:helpDoc>
			<!-- <a href="RtiApplicationDetailForm.html?ShowHelpDoc" target="_new" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide">Help</span></i></a> -->
		</div>

		<div class="widget-content padding">

			<form:form method="POST" action="PioResponse.html"
				commandName="command" class="form-horizontal" id="rtiPioForm"
				name="rtiPioForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="rti.applicantDetails" /></a>
						</h4>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<table
									class="table table-bordered  table-condensed margin-bottom-10">
									<tbody>
										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.applicantDetails" /></th>
										</tr>
										<tr>
											<td width="116"><spring:message code="rti.applicationNO" /></td>
											<td width="373">${command.reqDTO.apmApplicationId}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.date" /></td>
											<td>${command.reqDTO.apmApplicationDateDesc}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.typeApp" /></td>
											<td>${command.reqDTO.applicant}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.applicationName" /></td>
											<td>${command.cfcEntity.apmFname}
												${command.cfcEntity.apmMname} ${command.cfcEntity.apmLname}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.title" /></td>
											<td>${command.reqDTO.title}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.firstName" /></td>
											<td>${command.cfcEntity.apmFname}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.middleName" /></td>
											<td>${command.cfcEntity.apmMname}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.lastName" /></td>
											<td>${command.cfcEntity.apmLname}</td>
										</tr>
										<tr>
											<td><spring:message code="applicantinfo.label.gender" /></td>
											<td>${command.reqDTO.gen}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.mobile1" /></td>
											<td>${command.cfcAddressEntity.apaMobilno}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.location" /></td>
											<td>${command.reqDTO.locationName}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.address" />
											<td>${command.cfcAddressEntity.apaAreanm}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.pinCode" /></td>
											<td>${command.cfcAddressEntity.apaPincode}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.uidNo" /></td>
											<td>${command.cfcEntity.apmUID}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.type" /></td>
											<td>${command.reqDTO.isBPL}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.bplNo" /></td>
											<td>${command.cfcEntity.apmBplNo}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.yearofissue" /></td>
											<td>${command.cfcEntity.apmBplYearIssue}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.issueauthority" /></td>
											<td>${command.cfcEntity.apmBplIssueAuthority}</td>
										</tr>
										<c:if
											test="${not empty command.cfcAddressEntity.apaAreanm && not empty command.reqDTO.pincodeNo}">

											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.corrAdd" /></th>
											</tr>
											<tr>
												<td><spring:message code="rti.address" /></td>
												<td>${command.reqDTO.corrAddrsAreaName}</td>
											</tr>
											<tr>
												<td><spring:message code="rti.pinCode" /></td>
												<td>${command.reqDTO.corrAddrsPincodeNo}</td>
											</tr>

										</c:if>
										<c:if
											test="${not empty command.reqDTO.inwAuthorityName && not empty command.reqDTO.inwAuthorityDept && not empty command.reqDTO.inwAuthorityAddress && not empty command.reqDTO.inwReferenceNo && not empty command.reqDTO.referenceDate}">
											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.refdetails" />(Form E)</th>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwAuthorityName}">
											<tr>
												<td><spring:message code="rti.authorityName" /></td>
												<td>${command.reqDTO.inwAuthorityName}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwAuthorityDept}">
											<tr>
												<td><spring:message code="rti.depName" /></td>
												<td>${command.reqDTO.departmentName}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwAuthorityAddress}">
											<tr>
												<td><spring:message code="rti.address" /></td>
												<td>${command.reqDTO.inwAuthorityAddress}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.inwReferenceNo}">
											<tr>
												<td><spring:message code="rti.refno" /></td>
												<td>${command.reqDTO.inwReferenceNo}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.referenceDate}">
											<tr>
												<td><spring:message code="rti.refdate" /></td>
												<td>${command.reqDTO.referenceDate}</td>
											</tr>
										</c:if>
										<c:if
											test="${not empty command.reqDTO.stampNo && not empty command.reqDTO.stampAmt && not empty command.reqDTO.inwardTypeName}">
											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.refdetails" />(Stamp)</th>

											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.stampNo}">
											<tr>
												<td><spring:message code="rti.stampno" /></td>
												<td>${command.reqDTO.stampNo}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.stampAmt}">
											<tr>
												<td><spring:message code="rti.stampamt" /></td>
												<td>${command.reqDTO.stampAmt}</td>
											</tr>
										</c:if>
										<c:if
											test="${not empty command.reqDTO.postalCardNo && not empty command.reqDTO.postalAmt && not empty command.reqDTO.inwardTypeName}">
											<tr>
												<th colspan="2" style="text-align: left;"><spring:message
														code="rti.postal.refdetails" text="Postal Details" /></th>

											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.postalCardNo}">
											<tr>
												<td><spring:message code="rti.postCard.no" /></td>
												<td>${command.reqDTO.postalCardNo}</td>
											</tr>
										</c:if>
										<c:if test="${not empty command.reqDTO.postalCardAmt}">
											<tr>
												<td><spring:message code="rti.amount" /></td>
												<td>${command.reqDTO.postalCardAmt}</td>
											</tr>
										</c:if>
										<%-- <tr>
											<td><spring:message code="rti.download" /></td>
											<td><c:forEach items="${command.fetchDocumentList}"
													var="lookUp">
													<apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="PioResponse.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr> --%>
										<tr>
											<td><spring:message code="rti.inwardtype" /></td>
											<td>${command.reqDTO.inwardTypeName}</td>
										</tr>
										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.rtiInfo" /></th>
										</tr>
										<tr>
											<td><spring:message code="rti.dept" /></td>
											<td>${command.reqDTO.departmentName}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.related.dept"
													text="RTI Related Department" /></td>
											<td>${command.reqDTO.relatedDeptName}</td>
										</tr>
										<tr>
											<td><spring:message code="rti.subject" /></td>
											<td>${command.reqDTO.rtiSubject}</td>
										</tr>
										<c:if
											test="${not empty command.reqDTO.rtiRemarks && command.reqDTO.rtiRemarks !='0'}">
											<tr>
												<td><spring:message code="rti.remarks" /></td>
												<td>${command.reqDTO.rtiRemarks}</td>
											</tr>
										</c:if>
										<c:if
											test="${not empty command.reqDTO.rtiDesc && command.reqDTO.rtiDesc !='0'}">
											<tr>
												<td><spring:message code="rti.desc"
														text="Rti Description" /></td>
												<td>${command.reqDTO.rtiDesc}</td>
											</tr>
										</c:if>
										<%-- <tr>
											<td><spring:message code="rti.appln.uploadedfile" /></td>
											<td><c:forEach items="${command.fetchApplnUpload}"
													var="lookUp">
													<apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="PioResponse.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr> --%>
										<!-- MOM POINTS -->
										<%-- <tr>
											<td><spring:message code="ApplicationForm.rtiDetails" /></td>
											<td>${command.reqDTO.rtiDetails}</td>
										</tr> --%>
										<tr>
											<td>
												<%-- <a href="PioResponse.html?showHistoryDetails"> <spring:message
														code="rti.history" text="Action History" />
											</a> --%> <a
												class="fancybox fancybox.ajax text-small text-info"
												href="PioResponse.html?showHistoryDetails"> <spring:message
														code="rti.history"
														text="Click here to show remarks and attachment history" />
													<i class="fa fa-question-circle "></i></a>
											</td>
										</tr>

										<tr>
											<th colspan="2" style="text-align: left;"><spring:message
													code="rti.doc" /></th>
										</tr>

										<tr>
											<td><spring:message code="rti.checklist.file" /></td>
											<td><c:forEach items="${command.fetchDocumentList}"
													var="lookUp">
													<apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="PioResponse.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr>

										<tr>
											<td><spring:message code="rti.appln.uploadedfile" /></td>
											<td><c:forEach items="${command.fetchApplnUpload}"
													var="lookUp">
													<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
														filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
														actionUrl="PioResponse.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr>
										<tr>
											<td><spring:message code="rti.appln.uploadedfile.pio"
													text="Document Uploaded By APIO" /></td>
											<td><c:forEach items="${command.fetchPioUploadDoc}"
													var="lookUp">
													<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
														filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
														actionUrl="PioResponse.html?Download"></apptags:filedownload>
													<form:hidden path="" value="${lookUp.attId}" />
												</c:forEach></td>
										</tr>

										<c:if test="${not empty command.fetchStampDoc}">
											<tr>
												<td><spring:message code="rti.appln.stamp"
														text=" Uploaded Stamp Document" /></td>
												<td><c:forEach items="${command.fetchStampDoc}"
														var="lookUp">
														<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="PioResponse.html?Download"></apptags:filedownload>
														<form:hidden path="" value="${lookUp.attId}" />
													</c:forEach></td>
											</tr>
										</c:if>
										<c:if test="${not empty command.fetchPostalDoc}">
											<tr>
												<td><spring:message code="rti.appln.postal"
														text=" Uploaded Postal Document" /></td>
												<td><c:forEach items="${command.fetchPostalDoc}"
														var="lookUp">
														<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="PioResponse.html?Download"></apptags:filedownload>
														<form:hidden path="" value="${lookUp.attId}" />
													</c:forEach></td>
											</tr>
										</c:if>


									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="rti.appStatus" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<label class="col-sm-2 control-label required-control"
										for="action" id="rtiaction"><spring:message
											code="rti.rtiAction" /></label>

									<c:set var="baseLookupCode" value="RAC" />
									<apptags:lookupField items="${command.getLevelData('RAC')}"
										path="reqDTO.rtiAction" hasChildLookup="false" hasId="true"
										showAll="false"
										changeHandler="getRemarkDetailsByAction(this);"
										selectOptionLabelCode="applicantinfo.label.select"
										cssClass="form-control" isMandatory="true"></apptags:lookupField>



									<div id="fullpartial">
										<label class="col-sm-2 control-label required-control"
											for="fullpartial"><spring:message
												code="rti.full/partial" /></label>
										<c:set var="baseLookupCode" value="RIN" />
										<apptags:lookupField items="${command.getLevelData('RIN')}"
											path="reqDTO.partialInfoFlag" hasChildLookup="false"
											hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											cssClass="form-control" isMandatory="true"></apptags:lookupField>
									</div>

								</div>

								<div id="approved" class="">
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="action"><spring:message
												code="rti.loiApplicableType" /></label>
										<c:set var="baseLookupCode" value="RLF" />
										<apptags:lookupField items="${command.getLevelData('RLF')}"
											path="reqDTO.loiApplicable" hasChildLookup="false"
											hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											cssClass="form-control" isMandatory="true"></apptags:lookupField>


										<div id="reasonForLoiNa" style="display: none;">
											<label for="textarea"
												class="col-sm-2 control-label required-control"><spring:message
													code="rti.reasonForSkip">
												</spring:message></label>
											<div class="col-sm-4">
												<form:textarea name="" cols="" rows=""
													class="form-control mandColorClass" id="reasonForSkipLOI"
													path="reqDTO.reasonForLoiNa"></form:textarea>
											</div>
										</div>
									</div>

								</div>

								<div id="uploadTagDiv"></div>
								<div id="forwardLoc" style="display: none;">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="action" id="rtiaction"><spring:message
												code="rti.second.apl.dept" /></label>
										<apptags:lookupField items="${command.fdlDepartments}"
											path="reqDTO.fdlDeptId" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title"
											changeHandler="getWardZone(this);" />
									</div>
								</div>

								<div class="boxshowhide Locationwiseradio"
									style="display: none;">
									<div class="form-group">
										<div id="areaMappingId"></div>
									</div>
								</div>

								<div id="forward" style="display: none;">
									-
									<div class="form-group">

										<label for="orgName"
											class="col-sm-2 control-label required-control"><spring:message
												code="rti.orgName" text="Organisation Name">
											</spring:message></label>
										<div class="col-sm-4">
											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<form:select class="form-control" id="frdOrgId"
														path="reqDTO.frdOrgId">
														<form:option value="0">
															<spring:message code="applicantinfo.label.select"
																text="Select" />
														</form:option>

														<c:forEach var="org" items="${command.listOrg}">
															<form:option value="${org.orgid}">${org.ONlsOrgname}</form:option>
														</c:forEach>
													</form:select>
												</c:when>
												<c:otherwise>
													<form:select class="form-control" id="frdOrgId"
														path="reqDTO.orgId">
														<form:option value="0">
															<spring:message code="applicantinfo.label.select"
																text="Select" />
														</form:option>

														<c:forEach var="org" items="${command.listOrg}">
															<form:option value="${org.orgid}">${org.oNlsOrgnameMar}</form:option>
														</c:forEach>
													</form:select>
												</c:otherwise>
											</c:choose>
										</div>
										<div>
											<apptags:textArea labelCode="rti.otherreamrk"
												path="reqDTO.otherRemarks" isMandatory="true"
												maxlegnth="1000"></apptags:textArea>
										</div>

									</div>
								</div>
								<div id="WardZone" style="display: none;">
									<div class="form-group">

										<label for="" class="col-sm-2 control-label required-control"><spring:message
												code="rti.ward" text="Select Ward">
											</spring:message></label>
										<div class="col-sm-4">
											<form:select path="reqDTO.trdWard1" id="pioWard"
												class="form-control mandColorClass">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
											</form:select>
										</div>
										<label for="" class="col-sm-2 control-label required-control"><spring:message
												code="rti.zone" text="Select zone">
											</spring:message></label>
										<div class="col-sm-4">
											<form:select path="reqDTO.trdWard2" id="pioZone"
												class="form-control mandColorClass">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
											</form:select>
										</div>

									</div>
								</div>

								<div id="forwardtodept" style="display: none;">
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="rtiEmpIncreTable">
										<tbody>
											<tr>
												<th width="10%"><spring:message code="rti.serialNo"></spring:message></th>
												<th width="25%"><spring:message
														code="rti.second.apl.dept" text="Department">
														<span class="mand">*</span>
													</spring:message></th>
												<th width="30%"><spring:message code="rti.employee"
														text="Employee Name">
														<span class="mand">*</span>
													</spring:message></th>
												<th width="30%"><spring:message code="rti.remark"
														text="Remark">
														<span class="mand">*</span>
													</spring:message></th>
												<th width="5%"><a title="Add"
													class="addEMP btn btn-blue-2 btn-sm" id="addUnitRow1"><i
														class="fa fa-plus"></i></a></th>
											</tr>
											<tr class="empRequest">
												<td><form:input path="" type="text"
														class="form-control text-center unit required-control hasNumber"
														id="unitNO1" value="1" /></td>
												<td align="center"><apptags:lookupField
														items="${command.departments}" path="reqDTO.frdDeptId"
														cssClass="form-control" hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="applicantinfo.label.select"
														isMandatory="true"
														showOnlyLabel="applicantinfo.label.title"
														changeHandler="getEmployeeName(this);" /></td>
												<td><form:select path="reqDTO.empname" id="empname1"
														class="form-control mandColorClass">
														<form:option value="">
															<spring:message code='master.selectDropDwn' />
														</form:option>
													</form:select></td>
												<td><form:textarea class="form-control" id="remList"
														maxlength="500" path="reqDTO.empRmk"></form:textarea></td>
												<td class="text-center"><a href="javascript:void(0);"
													class="remvCF btn btn-danger btn-sm delete"><i
														class="fa fa-minus"></i></a></td>
											</tr>
										</tbody>
									</table>

									<div class="form-group">


										<label class="col-sm-2 control-label required-control"
											for="Department Forward Date"><spring:message
												code="rti.deptForwardDate" text="Department Forward Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="rtiDeptidFdate" path="reqDTO.rtiDeptidFdate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<%-- <label class="col-sm-2 control-label required-control"
											for="remarks"><spring:message code="rti.otherreamrk"
												text="Other Remarks" /></label>
										<div class="col-sm-4">
											<form:textarea class="form-control" id="otherreamrk"
												maxlength="500" path="reqDTO.otherRemarks"></form:textarea>
										</div> --%>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="UploadDocuments"><spring:message
												code="care.upload" text="Upload Document" /></label>
										<div class="col-sm-4">

											<apptags:formField fieldType="7" fieldPath="attachments[0]"
												currentCount="200" showFileNameHTMLId="true" folderName="0"
												fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
											</apptags:formField>
											<small class="text-blue-2"> <spring:message
													code="rti.validator.fileUploadNote"
													text="(Upload File upto 5MB and only .pdf ,.doc,.docs,.xls,.xlsx)" />
											</small>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>

					<div class="panel-default">
						<div class="panel-heading" id="remarkfields"
							style="display: none;">

							<div id="rejectremark" style="display: none;">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"><spring:message
											code="rti.rejectremarkdetails"></spring:message></a>
								</h4>
							</div>


							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="rtiIncreTable">
										<tbody>
											<tr>
												<th width="10%"><spring:message code="rti.serialNo"></spring:message></th>
												<th><spring:message code="rti.remarks">
														<span class="mand">*</span>
													</spring:message></th>
												<th><a title="Add" class="addCF btn btn-blue-2 btn-sm"
													id="addUnitRow"><i class="fa fa-plus"></i></a></th>
											</tr>
											<tr class="rtiRequestt">
												<td width=8%><form:input path="reqDTO.remarksSerialNo"
														type="text"
														class="form-control text-center unit required-control hasNumber"
														id="unitNo0" value="1" />
												<td><form:select path="reqDTO.rtiRemarks" id="remark"
														class="form-control mandColorClass chosen-select-no-results">
														<form:option value="">
															<spring:message code='master.selectDropDwn' />
														</form:option>
														<c:forEach items="${command.apprejMasList}"
															var="activeRemarkName">
															<form:option value="${activeRemarkName.artId}"
																code="${activeRemarkName.artId}">${activeRemarkName.artRemarks}</form:option>
														</c:forEach>
													</form:select></td>
												<td class="text-center"><a href="javascript:void(0);"
													class="remCF btn btn-danger btn-sm delete"><i
														class="fa fa-minus"></i></a></td>
											</tr>
										</tbody>
									</table>

									<div class="form-group">

										<label for="textarea" class="col-sm-2 control-label "><spring:message
												code="rti.otherreamrk"></spring:message> </label>
										<div class="col-sm-10">
											<textarea type="textarea" class="form-control"
												name="textarea" id="textarea" path="reqDTO.otherRemarks"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-top-20">


						<input type="button" class="btn  btn-success" id="submit"
							name="submit" value="<spring:message code="rti.proceed"/>"
							onclick="submitPioResponse(this);" />

						<button type="button" class="btn btn-danger" id="back"
							onclick="backPage()">
							<spring:message code="rti.back"></spring:message>
						</button>

					</div>
					<!-- </div> -->
				</div>
			</form:form>
		</div>
	</div>
</div>

<script>
	var rtiDeptidFdate = $('#rtiDeptidFdate').val();

	if (rtiDeptidFdate) {
		$('#rtiDeptidFdate').val(rtiDeptidFdate.split(' ')[0]);
	}
</script>


