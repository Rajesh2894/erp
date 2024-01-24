<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/social_security/applicationForm.js"></script>	
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Application Form" code="soc.application.form"/>
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>

			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="social.fiels.mandatory.message"
							text="Field with * is mandatory" /></span>
				</div>
				<form:form id="schemeApplicationFormId"
					action="SchemeApplicationForm.html" method="POST"
					class="form-horizontal" name="schemeApplicationFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<!-- added by rahul.chaubey -->
					<form:hidden path="applicationformdto.approvalFlag"
												id="approvalFlag" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"><spring:message
												code="soc.applicant.detail" text="Applicant Details"/></label>
										</a>
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
												class="form-control chosen-select-no-results" disabled="false"
												onChange ="checkDate(this);">
												<option value="0"><spring:message text="Select" /></option>
												<c:forEach items="${command.serviceList}" var="objArray">
												<c:choose>
													<c:when	test="${userSession.getCurrent().getLanguageId() eq 1}">
													<form:option value="${objArray[0]}" code="${objArray[2]}"
														label="${objArray[1]}"></form:option>
													</c:when>
													<c:otherwise>
															<form:option value="${objArray[0]}" code="${objArray[2]}"
																label="${objArray[3]}"></form:option>
													</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</div>


										<apptags:input labelCode="social.sec.nameapplicant"
											path="applicationformdto.nameofApplicant" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="50"></apptags:input>
									</div>
									
									
									
									

									<div class="form-group">

										<apptags:input labelCode="social.sec.adharnumber"
											path="applicationformdto.aadharCard"
											cssClass="hasAadharNo text-right" isMandatory="true"
											maxlegnth="12"></apptags:input>
											
										<label class="col-sm-2 control-label required-control" for=""
											id="dispoDate"><spring:message
												code="social.sec.applicantdob" /></label>
										 <div class="col-sm-4"> 
											<div class="input-group">
												<form:input class="form-control datepicker"
													id="applicantId" 
													data-label="#dispoDate"
													path="applicationformdto.applicantDob" isMandatory="true"
														onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
												maxlength="10"
													onchange="dobdetails();"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

                                           <%-- <div class="col-sm-4">
											<div class="input-group">
												<form:input
													class="form-control mandColorClass datepicker31 addColor"
													id="applicantId"
													path="applicationformdto.applicantDob"
													onchange="dobdetails();"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div> --%>

										
									</div>
									<div class="form-group">
									
									<apptags:input labelCode="social.sec.ageason"
											path="applicationformdto.ageason" isMandatory="true"
											cssClass="text-right" isReadonly="true"></apptags:input>
									
										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.gen" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.genderId"
												class="form-control chosen-select-no-results"  disabled="false">
												<form:option value="">
													<spring:message text="Select" />
												</form:option>
												<c:forEach items="${command.genderList}" var="slookUp">
													<form:option value="${slookUp.lookUpId}"
														code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										
									</div>

									<div class="form-group">
									
									
									<apptags:textArea labelCode="social.sec.applicantAdress"
											path="applicationformdto.applicantAdress" isMandatory="true"
											cssClass="hasAddressClass" maxlegnth="200"></apptags:textArea>

										<apptags:input labelCode="social.sec.pincode"
											path="applicationformdto.pinCode"
											cssClass="hasPincode text-right" isMandatory="true"
											maxlegnth="6"></apptags:input>

										

									</div>


									<div class="form-group">

										<apptags:input labelCode="social.sec.mobnum"
											path="applicationformdto.mobNum"
											cssClass="hasMobileNo text-right" isMandatory="true"
											maxlegnth="10" onChange="validateMobNum()"></apptags:input>

										<label class="col-sm-2 control-label"><spring:message
												code="social.sec.education" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.educationId"
												class="form-control chosen-select-no-results"  disabled="false"
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
												class="form-control chosen-select-no-results"  disabled="false">
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
												class="form-control chosen-select-no-results"  disabled="false">
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
											cssClass="hasNumber text-right" maxlegnth="8"></apptags:input>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.typeofdisability" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.typeofDisId"
												class="form-control chosen-select-no-results"  disabled="false"
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
											path="applicationformdto.percenrofDis" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="2"></apptags:input>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="social.sec.bpl" /></label>

										<div class="col-sm-4">
											<form:select path="applicationformdto.bplid"
												class="form-control chosen-select-no-results" disabled="false"
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
											cssClass="hasNumber text-right" maxlegnth="4"></apptags:input>

										<apptags:input labelCode="social.sec.bplfamilyid"
											path="applicationformdto.bplfamily" isMandatory="true"
											cssClass="hasNumber text-right" maxlegnth="14"></apptags:input>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label required-control" for=""><spring:message
												code="social.sec.lifecerti" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control" id="lastDateLifeCertiId"
													data-label="#dispoDate"
														onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
													path="applicationformdto.lastDateofLifeCerti"
													maxlength = "10"></form:input>
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
										data-parent="#accordion_single_collapse" href="#a2">
										<spring:message code="social.sec.bankDet" text="Bank Details" /></a>
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
												class="form-control chosen-select-no-results"  disabled="false">
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
											cssClass="hasNumber text-right" maxlegnth="16"></apptags:input>
										&nbsp;<a class="text-center" href="#" onclick="openBankForm();"><spring:message code="add.bank.note" text="Click Here To Add New Bank"/></a>.
										
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
										data-parent="#accordion_single_collapse" href="#a3">
										<spring:message code="social.sec.app.family.det" text="Applicant Family Details" /> 
										</a>
								</h4>
							</div>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">
									<div class="form-group">

										<apptags:input labelCode="social.sec.nameoffather/spouse"
											path="applicationformdto.nameofFather" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="20"></apptags:input>

										<apptags:input labelCode="social.sec.nameofmother"
											path="applicationformdto.nameofMother" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="20"></apptags:input>

									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.contactnum"
											path="applicationformdto.contactNumber" maxlegnth="10" isMandatory="true"
											cssClass="hasNumber text-right" onChange="validateContactNum()"></apptags:input>


										<apptags:input labelCode="social.sec.detailsfamilyincome"
											path="applicationformdto.detailsoffamIncomeSource" isMandatory="true"
											cssClass="hasCharacter" maxlegnth="12"></apptags:input>

									</div>

									<div class="form-group">

										<apptags:input labelCode="social.sec.familyannualincome"
											path="applicationformdto.annualIncomeoffam"
											cssClass="hasNumber text-right" maxlegnth="10"></apptags:input>



									</div>
								</div>
							</div>
						</div>
						<!----------------------------- Applicant Details End  ---------------------------->



					</div>
					<div class="text-center">
						<button type="button" class="btn btn-blue-2" title="Submit"
							onclick="saveData(this)"><spring:message code="social.sec.proceed.button" text="Proceed" />
							<i class="fa fa-arrow-right padding-left-5"
								aria-hidden="true"></i>
						</button>

						<button type="Reset" class="btn btn-warning" id="resetform"
							onclick="resetSchemeApplicationForm(this)">
							<spring:message text="Reset" code="social.btn.reset"/>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script>
 	var applicantId = $('#applicantId').val();
	var lastDateLifeCertiId = $('#lastDateLifeCertiId').val();
	if (applicantId) {
	$('#applicantId').val(applicantId.split(' ')[0]);
	}
	if (lastDateLifeCertiId) {
	$('#lastDateLifeCertiId').val(lastDateLifeCertiId.split(' ')[0]);
	} 
</script>
