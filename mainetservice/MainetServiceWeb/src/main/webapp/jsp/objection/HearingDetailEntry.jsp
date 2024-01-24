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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="obj.HearingDetailEntry"></spring:message></b>
			</h2>

			<%-- <div class="additional-btn">
			<apptags:helpDoc url="objectionAddDetails.html"></apptags:helpDoc>
		</div> --%>
		</div>

		<div class="widget-content padding">
			<form:form action="HearingDetailEntry.html" class="form-horizontal"
				name="HearingDetailEntry" id="HearingDetailEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="obj.objappealissuerdetails" /></a>
						</h4>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="applicantTitle"><spring:message code="obj.title" /></label>
									<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="objectionDetailsDto.title" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="rti.title" disabled="true" />

									<apptags:input labelCode="obj.firstName"
										cssClass="hasCharacter" path="objectionDetailsDto.fName"
										isMandatory="true" isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="obj.middleName"
										cssClass="hasCharacter" path="objectionDetailsDto.mName"
										isDisabled="true"></apptags:input>
									<apptags:input labelCode="obj.lastName" cssClass="hasCharacter"
										path="objectionDetailsDto.lName" isMandatory="true"
										isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="applicantinfo.label.gender" /></label>
									<c:set var="baseLookupCode" value="GEN" />
									<apptags:lookupField items="${command.getLevelData('GEN')}"
										path="objectionDetailsDto.gender" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" disabled="true" />

									<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
										path="objectionDetailsDto.mobileNo" isMandatory="true"
										isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="EmailID"><spring:message
											code="obj.emailId" text="Email ID" /></label>
									<div class="col-sm-4">
										<form:input name="" type="email"
											class="form-control mandColorClass" id="EmailID"
											path="objectionDetailsDto.eMail" data-rule-required="true"
											data-rule-email="true" disabled="true"></form:input>
									</div>

									<apptags:input labelCode="obj.Aadhar"
										cssClass="form-control hasAadharNo"
										path="objectionDetailsDto.uid" 
										maxlegnth="12" isDisabled="true"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:textArea isMandatory="true" labelCode="obj.address"
										path="objectionDetailsDto.address" maxlegnth="500"
										cssClass="preventSpace" isDisabled="true"></apptags:textArea>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a2"> <spring:message
									code="obj.objappealdetails" /></a>
						</h4>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="obj.dept"
										path="objectionDetailsDto.deptName" isDisabled="true"></apptags:input>
									<apptags:input labelCode="obj.serviceName"
										path="objectionDetailsDto.serviceName" isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<c:set var="baseLookupCode" value="OBJ" />
									<apptags:select labelCode="obj.dept"
										items="${command.getLevelData(baseLookupCode)}"
										path="objectionDetailsDto.objectionOn"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showAll="false" isLookUpItem="true"
										disabledOption="true"></apptags:select>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										id="refNo" for="dept"><spring:message
											code="obj.ReferenceNo" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass"
											id="objectionReferenceNumber"
											path="objectionDetailsDto.objectionReferenceNumber"
											data-rule-required="true" disabled="true"></form:input>
									</div>

									<br>
									<br>
									<div class="form-group">
										<label class="col-sm-10 text-red">&nbsp&nbsp&nbsp<spring:message
												code="obj.note" /></label>
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "
										for="billNo"><spring:message code="obj.billNo" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass" id="billNo"
											path="objectionDetailsDto.billNo" data-rule-required="true"
											disabled="true"></form:input>

									</div>
									<apptags:input labelCode="obj.billDueDate"
										path="objectionDetailsDto.billDueDate" isDisabled="true"></apptags:input>


								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "
										for="noticeNo"><spring:message code="obj.noticeNo" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass" id="noticeNo"
											path="objectionDetailsDto.noticeNo" data-rule-required="true"
											disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="rtiDetails"><spring:message
											code="obj.appealobjdetails" /></label>
									<div class="col-sm-10">

										<form:textarea name="" cols="" rows=""
											class="form-control mandColorClass" id="ObjDetails"
											path="objectionDetailsDto.objectionDetails" disabled="true"></form:textarea>
									</div>
								</div>
							</div>

						</div>

					</div>
					
					<c:if test="${not empty command.documentList}">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#DocumentUpload"><spring:message
									code="obj.UploadDocumnet" /></a>
						</h4>
						<div id="DocumentUpload">
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>

													<th><label class="tbold"><spring:message
																code="water.download" /></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lk.count}</label></td>

														<td><c:set var="links"
																value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																dmsDocId="${lookUp.dmsDocId}"
																actionUrl="HearingInspection.html?Download"></apptags:filedownload>
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


					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"><spring:message
								code="obj.hearing.det.entry" text="Hearing Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="form-group padding-top-10">
							<apptags:textArea labelCode="obj.nameOfHearingAuthority"
								path="hearingInspectionDto.nameofHearingAuthoritys"
								isMandatory="true"></apptags:textArea>
							<apptags:textArea labelCode="obj.HearingRemark"
								path="hearingInspectionDto.remark" isMandatory="true"></apptags:textArea>
						</div>

						<div class="form-group">
							<label for="file" class="col-sm-2 control-label"><spring:message
									code="obj.fileUpload"></spring:message> </label>
							<div class="col-sm-4">
								<apptags:formField fieldType="7" labelCode="" hasId="true"
									fieldPath="hearingInspectionDto.hearingDocs[0]"
									isMandatory="false" showFileNameHTMLId="true"
									fileSize="BND_COMMOM_MAX_SIZE"
									maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
									currentCount="0" />
							</div>

							<div id="showPioName">
								<label class="col-sm-2 control-label" for="Pio Name"><spring:message
										code="obj.pioName" text="Pio Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="Pio Name"
										class="form-control mandColorClass" id="pioName"
										path="pioName" data-rule-required="true" disabled="true"></form:input>
								</div>
							</div>

						</div>




						<div class="form-group padding-top-10 ownerName">
							<apptags:radio radioLabel="obj.owner,obj.representative"
								radioValue="O,R" labelCode="obj.Selection"
								path="hearingInspectionDto.availPerson"></apptags:radio>
								
							<c:set var="baseLookupCode" value="DIF" />
							<apptags:select labelCode="Decision in favor of"
								items="${command.getLevelData(baseLookupCode)}"
								path="hearingInspectionDto.decisionInFavorOf"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" showAll="false" isLookUpItem="true"></apptags:select>
						</div>
						<div class="form-group">
							<div class=" applicantName">
								<apptags:radio radioLabel="obj.applicant,obj.representative"
									radioValue="O,R" labelCode="obj.Selection"
									path="hearingInspectionDto.availPerson"
									changeHandler="displayPiorepName(this)"></apptags:radio>
							</div>
							<%-- <div id="appName">
								<apptags:input labelCode="obj.applicant.name"
									path="hearingInspectionDto.applicantName" isMandatory="true"
									cssClass="hasCharacter" maxlegnth="50"></apptags:input>
							</div> --%>
							<div id="applicantRepName">
								<apptags:input labelCode="obj.applicant.rep.name"
									path="hearingInspectionDto.applicantRepName" isMandatory="true"
									cssClass="hasCharacter" maxlegnth="50"></apptags:input>
							</div>
						</div>

						<div class="form-group">
							<div class="pioname">
								<apptags:radio radioLabel="obj.pio,obj.pio.representative"
									radioValue="P,E" labelCode="obj.Selection"
									path="hearingInspectionDto.repName"
									changeHandler="displayPioName(this)"></apptags:radio>
							</div>
							<div id="pionrepname">
								<apptags:input labelCode="obj.pio.representative.name"
									path="hearingInspectionDto.pioRepresentativeName"
									isMandatory="true" cssClass="hasCharacter" maxlegnth="50"></apptags:input>
							</div>
						</div>


						<div class="form-group">
							<div id="nameofPerson">
								<apptags:input labelCode="obj.NameOfPerson"
									path="hearingInspectionDto.name" isMandatory="true"></apptags:input>
							</div>
							<c:set var="baseLookupCode" value="HRD" />
							<apptags:select labelCode="obj.hearingStatus"
								items="${command.getLevelData(baseLookupCode)}"
								path="hearingInspectionDto.hearingStatus"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" showAll="false" isLookUpItem="true"></apptags:select>
						</div>
						<div class="form-group">
							<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
								path="hearingInspectionDto.mobno" isMandatory="true"></apptags:input>

							<label class="col-sm-2 control-label" for="EmailID"><spring:message
									code="obj.emailId" text="Email ID" /></label>
							<div class="col-sm-4">
								<form:input type="email" class="form-control mandColorClass"
									id="emailid" path="hearingInspectionDto.emailid"
									data-rule-required="true" data-rule-email="true"></form:input>
							</div>
						</div>
					</div>
					<div class="padding-top-10 text-center">
						<button type="button" class="btn btn-success btn-submit" id="save"
							onclick="submitEntryDetails(this)">
							<spring:message code="obj.submit" />
						</button>
						<input type="button" class="btn btn-warning"
							value="<spring:message
										code="reset.msg" text="Reset" />"
							onclick="resetForm(this)" id="Reset" />
					</div>
				</div>
				<input type="hidden" value="${command.objectionDetailsDto.deptCode}" id="deptCode"/>
			</form:form>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/objection/hearingDetailEntry.js"></script>