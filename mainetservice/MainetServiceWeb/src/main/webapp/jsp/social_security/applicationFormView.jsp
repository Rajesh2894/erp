<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/social_security/applicationFormView.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Application Form For Social Security Scheme" />
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>

			</div>
			<div class="widget-content padding">
				<form:form id="schemeApplicationFormId"
					action="SchemeApplicationForm.html" method="POST"
					class="form-horizontal" name="schemeApplicationFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<!-- added by rahul.chaubey -->
					<form:hidden path="applicationformdto.approvalFlag"
						id="approvalFlag" />

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1">Applicant
										Details</a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.schemename" /></label>

										<div class="col-sm-4">

											<form:select name="applicationformdtoId"
												path="applicationformdto.selectSchemeName" id="schemenameId"
												class="form-control" disabled="true">
												<option value="0"><spring:message text="Select" /></option>
												<c:forEach items="${command.serviceList}" var="objArray">
													<form:option value="${objArray[0]}" code="${objArray[2]}"
														label="${objArray[1]}"></form:option>
												</c:forEach>
											</form:select>
										</div>


										<apptags:input labelCode="social.sec.nameapplicant"
											path="applicationformdto.nameofApplicant" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="50" isDisabled="true"></apptags:input>
									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control" for=""
											id="dispoDate"><spring:message
												code="social.sec.applicantdob" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													id="applicationDobId" maxlength="10"
													data-label="#dispoDate"
													path="applicationformdto.applicantDob" isMandatory="true"
													onchange="dobdetails();" disabled="true"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<apptags:input labelCode="social.sec.ageason"
											path="applicationformdto.ageason" isMandatory="true"
											cssClass="text-right" isDisabled="true"></apptags:input>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.gen" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.genderId"
												class="form-control" disabled="true">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.genderList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<apptags:textArea labelCode="social.sec.applicantAdress"
											path="applicationformdto.applicantAdress" isMandatory="true"
											cssClass="hasAddressClass" maxlegnth="200" isDisabled="true"></apptags:textArea>
									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.adharnumber"
											path="applicationformdto.aadharCard" isMandatory="true"
											cssClass="hasAadharNo text-right" maxlegnth="12"
											isDisabled="true"></apptags:input>

										<apptags:input labelCode="social.sec.pincode"
											path="applicationformdto.pinCode"
											cssClass="hasPincode text-right" isMandatory="true"
											isDisabled="true"></apptags:input>

									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.mobnum"
											path="applicationformdto.mobNum"
											cssClass="hasMobileNo text-right" isMandatory="true"
											maxlegnth="10" onChange="validateMobNum()" isDisabled="true"></apptags:input>

										<label class="col-sm-2 control-label"><spring:message
												code="social.sec.education" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.educationId"
												class="form-control" disabled="true"
												onchange="geteducationdetails();">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.educationList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.mstatus" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.maritalStatusId"
												class="form-control" disabled="true">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.maritalstatusList}"
													var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.category" /></label>
										<div class="col-sm-4">
											<form:select path="applicationformdto.categoryId"
												class="form-control" disabled="true">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.categoryList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.annualincome"
											path="applicationformdto.annualIncome"
											cssClass="hasNumber text-right" maxlegnth="8"
											isDisabled="true"></apptags:input>

										<label class="col-sm-2 control-label"><spring:message
												code="social.sec.typeofdisability" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.typeofDisId"
												class="form-control" disabled="true"
												onchange="disabilitydetails();">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.typeofdisabilityList}"
													var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.percentofdis"
											path="applicationformdto.percenrofDis"
											cssClass="hasNumber text-right" maxlegnth="2"
											isDisabled="true"></apptags:input>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.bpl" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.bplid"
												cssClass="form-control" disabled="true"
												onchange="bpldetails();">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.bplList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
											<form:hidden path="applicationformdto.isBplApplicable"
												id="isBplApplicableId" />
										</div>
									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.bplinspectionyr"
											path="applicationformdto.bplinspectyr" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="4"
											isDisabled="true"></apptags:input>

										<apptags:input labelCode="social.sec.bplfamilyid"
											path="applicationformdto.bplfamily" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="8"
											isDisabled="true"></apptags:input>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label required-control" for=""><spring:message
												code="social.sec.lifecerti" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control" id="lastDateLifeCertiId"
													data-label="#dispoDate"
													path="applicationformdto.lastDateofLifeCerti"
													isMandatory="true" disabled="true"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>

								</div>
							</div>
						</div>


						<!----------------------------- Bank Details  ---------------------------->

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a2">Bank
										Details</a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.bankname" /></label>

										<div class="col-sm-4">

											<form:select name="applicationformdtoId"
												path="applicationformdto.bankNameId" id="banknameId"
												class="form-control" disabled="true">
												<option value="0"><spring:message text="Select" /></option>
												<c:forEach items="${command.bankList}" varStatus="status"
													var="cbBankid">
													<form:option value="${cbBankid.bankId}"
														code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
												</c:forEach>
											</form:select>
										</div>

										<apptags:input labelCode="social.sec.accountnum"
											path="applicationformdto.accountNumber" isMandatory="true"
											cssClass="alphaNumeric text-right" maxlegnth="20"
											isDisabled="true"></apptags:input>
									</div>

								</div>
							</div>
						</div>
						<!----------------------------- Bank Details End ---------------------------->



						<!-------------------------- Applicant Details Start  ------------------------>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3">Applicant
										Family Details</a>
								</h4>
							</div>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">
									<div class="form-group">

										<apptags:input labelCode="social.sec.nameoffather/spouse"
											path="applicationformdto.nameofFather" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="20" isDisabled="true"></apptags:input>

										<apptags:input labelCode="social.sec.nameofmother"
											path="applicationformdto.nameofMother" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="20" isDisabled="true"></apptags:input>

									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.contactnum"
											path="applicationformdto.contactNumber" maxlegnth="10"
											isMandatory="true" cssClass="hasNumber text-right"
											isDisabled="true"></apptags:input>

										<apptags:input labelCode="social.sec.detailsfamilyincome"
											path="applicationformdto.detailsoffamIncomeSource"
											isMandatory="true" cssClass="hasCharacter" maxlegnth="20"
											isDisabled="true"></apptags:input>
									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.familyannualincome"
											path="applicationformdto.annualIncomeoffam"
											cssClass="hasNumber text-right" maxlegnth="20"
											isDisabled="true"></apptags:input>

									</div>
								</div>
							</div>
						</div>
						<!----------------------------- Applicant Details End  ---------------------------->



					</div>
					<%-- <div class="text-center">
						<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveschemeApplicationFormData(this);">
							<spring:message code="asset.transfer.save" text="Save" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div> --%>

					<div class="panel-group accordion-toggle" id="applicationFormId">
						<div class="panel panel-default">
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="widget-content padding">
											<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
										</div>
										<div class="text-center">
											<button type="button" id="save"
												class="btn btn-success btn-submit"
												onclick="saveschemeApplicationFormData(this);">
												<spring:message code="asset.transfer.save" text="Save" />
											</button>
											<apptags:backButton url="AdminHome.html"></apptags:backButton>
										</div>

									</div>
								</div>

							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
</div>
</div>

<script>
	var applicationDobId = $('#applicationDobId').val();
	var lastDateLifeCertiId = $('#lastDateLifeCertiId').val();
	if (applicationDobId) {
		$('#applicationDobId').val(applicationDobId.split(' ')[0]);
	}
	if (lastDateLifeCertiId) {
		$('#lastDateLifeCertiId').val(lastDateLifeCertiId.split(' ')[0]);
	}
</script>