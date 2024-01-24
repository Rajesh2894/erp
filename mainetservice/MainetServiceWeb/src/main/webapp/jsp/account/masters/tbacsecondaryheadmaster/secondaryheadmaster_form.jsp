<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<div xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:s="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:util="urn:jsptagdir:/WEB-INF/tags/util"
	xmlns:input="urn:jsptagdir:/WEB-INF/tags/input" version="2.0">
	<%@page import="java.util.Date"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
	<%
	response.setContentType("text/html; charset=utf-8");
%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<script src="js/account/SecondaryheadMaster.js"></script>
	<c:if test="${mode == 'create'}">
		<SCRIPT>
	$(document).ready(function() {
		jQuery('.hasCharacter').keyup(function () { 
		    this.value = this.value.replace(/[^a-z A-Z]/g,'');
		});
		$(".BankName").show();
		$(".Bank").show();
		$(".Other").hide();
		$(".Vendor").hide();
		$(".VendorList").hide();
		
		});
	
	function closeErrBox() {
		$('.error-div').hide();
		
}
	
	
</SCRIPT>
	</c:if>
	<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>

	<c:if test="${mode != 'create'}">
		<SCRIPT>
	$(document).ready(function() {
		jQuery('.hasCharacter').keyup(function () { 
		    this.value = this.value.replace(/[^a-z A-Z]/g,'');
		});
		
		$("#sacHeadCode").attr('disabled',true);
		var x = $(".listOfTbAcFunctionMasterItems").find(':selected').attr('code');
		
		$("#primeryObejctdiscription").val(x);
		$("#listOfTbAcFunctionMasterItems").prop('disabled', true).trigger("chosen:updated");;
		/* $("#listOfTbAcPrimaryMasterItems").prop('disabled', true).trigger("chosen:updated");;
		$("#fundId").prop('disabled', true).trigger("chosen:updated");; */
		$("#fieldId").prop('disabled', true).trigger("chosen:updated");
		$("#LedgerType").attr('disabled', false);
		$("#bmBankid").attr('disabled', 'disabled');
		$("#baAccountidedit").attr('disabled', 'disabled');
		$("#vmVendorid").attr('disabled', 'disabled');
		
		

		
	
		});
	
	function closeErrBox() {
		$('.error-div').hide();
}
</SCRIPT>
	</c:if>

	<jsp:directive.page contentType="text/html;charset=UTF-8" />


	<div class="widget" id="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="accounts.Secondaryhead.SecondaryheadMaster"
					text="Secondaryhead Master" />
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div> -->
			<apptags:helpDoc url="tbAcSecondaryheadMaster.html" helpDocRefURL="tbAcSecondaryheadMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">

			<util:message message="${message}" messages="${messages}" />

			<form:form id="frmMaster" class="form-horizontal"
				modelAttribute="secondaryheadMaster" name="frmMaster" method="POST"
				action="tbAcSecondaryheadMaster.html">
				<form:errors path="*" cssClass="alert alert-danger  hello"
					element="div" onclick="">
				</form:errors>
				<input type="hidden" value="${mode}" id="test" />
				<form:hidden path="" value="${keyTest}" id="keyTest" />
				<form:hidden path="sacLeddgerTypeCpdCode" id="sacLeddgerTypeCpdCode" />
				<form:hidden path="secondaryStatus" value="${secondaryStatus}"
					id="secondaryStatus" />
				<form:hidden path="" value="${fundStatus}" id="fundStatus" />
				<form:hidden path="" value="${fieldStatus}" id="fieldStatus" />


				<div class="mand-label clearfix">
					<span><spring:message code="account.common.mandmsg" /> <i
						class="text-red-1">*</i> <spring:message
							code="account.common.mandmsg1" /></span>
				</div>
				<div class="warning-div alert alert-danger alert-dismissible hide"
					id="errorDivScrutiny">
					<button type="button" class="close" aria-label="Close"
						onclick="closeErrBox()">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li><form:errors path="*" /></li>
					</ul>
				</div>

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="error-div alert alert-danger alert-dismissible "
					id="errorDivIdI" style="display: none;">
					<span id="errorIdI"></span>
				</div>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<c:set var="count" value="0" scope="page" />

				<ul id="ulId">
					<li>
						<fieldset id="divId" class="clear">

							<div class="panel-group accordion-toggle"
								id="accordion_single_collapse">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse"
												href="#Additional_Owners"><spring:message code="direct.payment.entry.acc.head.detail"
													text="Account Head Details" /></a>
										</h4>
									</div>
									<div id="Additional_Owners" class="panel-collapse collapse in">
										<div class="panel-body">



											<c:if test="${mode != 'create'}">
												<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
												<form:hidden path="sacHeadId" />
											</c:if>

											<div class="form-group">

												<c:if test="${fundStatus == 'Y'}">
													<label class="control-label col-sm-2 required-control"><spring:message
															code="account.budget.code.master.fundcode" /></label>
													<div class="col-sm-4">
														<form:select id="fundId" path="fundId"
															cssClass="fundId form-control mandColorClass chosen-select-no-results"
															data-rule-required="true">
															<form:option value="">
																<spring:message
																	code="account.budget.code.master.selectfundcode" />
															</form:option>
															<c:forEach items="${listOfTbAcFundMasterItems}"
																varStatus="status" var="fundItem">
																<form:option value="${fundItem.key}"
																	code="${fundItem.key}">${fundItem.value}</form:option>
															</c:forEach>
														</form:select>
													</div>
												</c:if>

												<c:if test="${fieldStatus == 'Y'}">
													<label class="control-label col-sm-2 required-control"><spring:message
															code="account.budget.code.master.fieldcode" /></label>
													<div class="col-sm-4">
														<form:select id="fieldId" path="fieldId"
															cssClass="fieldId form-control mandColorClass chosen-select-no-results"
															data-rule-required="true">
															<form:option value="">
																<spring:message
																	code="account.budget.code.master.selectfieldcode" />
															</form:option>
															<c:forEach items="${listOfTbAcFieldMasterItems}"
																varStatus="status" var="fieldItem">
																<form:option value="${fieldItem.key}"
																	code="${fieldItem.key}">${fieldItem.value}</form:option>
															</c:forEach>
														</form:select>
													</div>
												</c:if>

											</div>

											<div class="form-group">
							
												<c:if test="${functionStatus == 'Y'}">
													<label class="col-sm-2 control-label required-control"><spring:message
															code="budget.allocation.master.functioncode" text="Function" /></label>
													<div class="col-sm-4">
														<form:select id="listOfTbAcPrimaryMasterItems"
															path="functionId"
															cssClass="listOfTbAcPrimaryMasterItems form-control mandColorClass chosen-select-no-results"
															data-rule-required="true">
															<form:option value="">
																<spring:message
																	code="account.budget.code.master.selectfunctioncode"
																	text="Select Function" />
															</form:option>
															<c:forEach items="${listOfTbAcPrimaryMasterItems}"
																varStatus="status" var="funcItem">
																<form:option value="${funcItem.key}"
																	code="${funcItem.value}">${funcItem.value}</form:option>
															</c:forEach>
														</form:select>

													</div>
												</c:if>
												
												<c:if test="${primaryStatus == 'Y'}">
													<label class="col-sm-2 control-label required-control"><spring:message
															code="budget.allocation.master.primaryaccountcode" text="Primary Head" /></label>
													<div class="col-sm-4">
														<form:select id="listOfTbAcFunctionMasterItems"
															path="pacHeadId"
															cssClass="listOfTbAcFunctionMasterItems form-control mandColorClass chosen-select-no-results"
															data-rule-required="true">
															<form:option value="">
																<spring:message
																	code="budget.allocation.master.selectprimaryaccountcode"
																	text="Select Primary Head" />
															</form:option>
															<c:forEach items="${listOfTbAcFunctionMasterItems}"
																varStatus="status" var="functionItem">
																<form:option value="${functionItem.key}"
																	code="${functionItem.value}">${functionItem.value}</form:option>
															</c:forEach>
														</form:select>

													</div>
												</c:if>

											</div>

											<div class="form-group">

												<label class="col-sm-2 control-label required-control">
													<spring:message code="secondary.head.ledger.type" text="Ledger Type" />
												</label>
												<div class="col-sm-4">
													<form:select id="LedgerType" path="sacLeddgerTypeCpdId"
														cssClass="form-control mandColorClass LedgerType"
														data-rule-required="true">
														<form:option value="">
															<spring:message code="secondary.head.sel.ledger.type"
																text="Select Ledger Type" />
														</form:option>
														<c:forEach items="${legerTypeList}" var="LedgerType">
															<form:option value="${LedgerType.lookUpId}"
																code="${LedgerType.lookUpCode}">${LedgerType.descLangFirst}</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>


										</div>
									</div>
								</div>


								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse" href="#otherdiv"><spring:message
													code="budget.allocation.master.secondaryaccount.detail" text="Secondary Head Details" /></a>
										</h4>
									</div>
									<div id="otherdiv" class="panel-collapse collapse in">
										<div class="panel-body">
											<c:if test="${mode == 'create'}">
												<div class="form-group">
												
													<label class="col-sm-2 control-label required-control"
														for="sacHeadDesc"><spring:message code="account.common.desc.eng"
															text="Description(Eng)" /></label>
													<div class="col-sm-4">
														<form:textarea id="sacHeadDesc" path="sacHeadDesc"
															class=" form-control mandColorClass sacHeadDesc "
															maxLength="500" readonly="false"
															data-rule-required="true" />
													
													</div>
													
													<label class="col-sm-2 control-label required-control"
														for="sacHeadDescReg"><spring:message code="account.common.desc.reg"
															text="Description(Reg)" /></label>
													<div class="col-sm-4">
														<form:textarea id="sacHeadDescReg" path="oldAliasLendgerCode"
															class=" form-control mandColorClass sacHeadDescReg "
															maxLength="100" readonly="false"
															data-rule-required="true" />
													
													</div>
													
													</div>
												 <div class="form-group">
													<label class="col-sm-2 control-label"
														for="oldSacHeadCode"><spring:message code="old.account.head.code"
															text="Old Account Head Code" /></label>
													<div class="col-sm-4">
														<form:textarea id="oldAccHead" path="oldSacHeadCode"
															class=" form-control  oldSacHeadCode "
															maxLength="100" readonly="false"
															data-rule-required="false" />
													
													</div>
												</div>
											</c:if>
											<c:if test="${mode != 'create'}">
												<div class="form-group">

													<c:if test="${secondaryStatus == 'Y'}">
														<label class="col-sm-2 control-label required-control"><spring:message
																code="budget.allocation.master.secondaryaccountcode" text="Secondary Head" /></label>
														<div class="col-sm-4">
															<form:select id="sacHeadId" path="sacHeadId"
																cssClass="form-control chosen-select-no-results"
																disabled="true">
																<form:option value="">
																	<spring:message
																		code="budget.allocation.master.selectsecondaryaccountcode"
																		text="Select Secondary Head" />
																</form:option>
																<c:forEach items="${listOfTbAcSecondaryMasterItems}"
																	varStatus="status" var="secondaryItem">
																	<form:option value="${secondaryItem.key}"
																		code="${secondaryItem.value}">${secondaryItem.value}</form:option>
																</c:forEach>
															</form:select>

														</div>
													</c:if>

													<label class="col-sm-2 control-label required-control"
														for="sacHeadDesc"><spring:message code="account.common.desc.eng"
															text="Description(Eng)" /></label>
													<div class="col-sm-4 ">
														<form:textarea id="sacHeadDesc" path="sacHeadCodeDesc"
															class=" form-control mandColorClass sacHeadDesc "
															maxLength="500" readonly="false"
															data-rule-required="true" />
													</div>
													
													
												</div>
											</c:if>

											<c:if test="${mode != 'create'}">
												<div class="form-group">
													<label class="col-sm-2 control-label required-control"><spring:message
															code="accounts.master.status" text="Status" /></label>
													<div class="col-sm-4 ">
														<form:select id="sacStatusCpdId" path="sacStatusCpdId"
															cssClass="form-control mandColorClass"
														
															disabled="${viewMode}" data-rule-required="true">
															<c:forEach items="${activeDeActiveMap}"
																varStatus="status" var="activeItem">
																<form:option code="${activeItem.lookUpCode}"
																	value="${activeItem.lookUpId}">${activeItem.descLangFirst}</form:option>
															</c:forEach>
														</form:select>
													</div>
													<label class="col-sm-2 control-label required-control"
														for="sacHeadDescReg"><spring:message code="account.common.desc.reg"
															text="Description(Reg)" /></label>
													<div class="col-sm-4">
														<form:textarea id="sacHeadDescReg" path="oldAliasLendgerCode"
															class=" form-control mandColorClass sacHeadDescReg "
															maxLength="100" readonly="false"
															data-rule-required="true" />
													</div>
												</div>
												<div class="form-group">
												<c:if test="${mode != 'create'}">
												  <label class="col-sm-2 control-label"
														for="IncativeReasone"><spring:message code="account.Secondaryhead.inactive.reason"
															text="Inactive Reason" /></label>
													<div class="col-sm-4">
														<form:textarea id="IncativeReason" path="statusReason"
															class=" form-control  IncativeReason "
															maxLength="400" readonly="false"
															data-rule-required="false" />
													
													</div>
												 </c:if>	
												<label class="col-sm-2 control-label"
														for="oldSacHeadCode"><spring:message code="old.account.head.code"
															text="old Account Head Code" /></label>
													<div class="col-sm-4">
														<form:textarea id="oldAccHead" path="oldSacHeadCode"
															class=" form-control  oldSacHeadCode "
															maxLength="100" readonly="false"
															data-rule-required="false" />
													
													</div>
												</div>
											</c:if>

										</div>
									</div>
								</div>
							</div>

						</fieldset>
					</li>
				</ul>



				<!-- ACTION BUTTONS -->
				<div class=" text-center">
					<c:if test="${mode != 'create'}">
						<!-- "DELETE" button ( HREF link ) -->
						<s:url var="deleteButtonURL"
							value="/tbAcSecondaryheadMaster/delete/${tbAcSecondaryheadMaster.sacHeadId}" />
					</c:if>
					<c:if test="${mode == 'create'}">
						<input type="button" id="saveBtn" class="btn btn-success btn-submit"
							onclick="saveLeveledData(this)" value="<spring:message code="accounts.receipt.save" text="Save" />"> </input>
						<input type="button" id="Reset"
							class="btn btn-warning " value="<spring:message code="account.bankmaster.reset" text="Reset" />" onclick="resetForm()"></input>
					</c:if>

					<c:if test="${mode == 'update'}">
						<input type="button" id="saveBtn" class="btn btn-success btn-submit"
							onclick="saveLeveledData(this)" value="<spring:message code="accounts.receipt.save" text="Save" />"> </input>
					</c:if>
					<!-- "CANCEL" button ( HREF link ) -->
					<s:url var="cancelButtonURL" value="/tbAcSecondaryheadMaster" />
					<a title="Cancel" class="btn btn-danger"
						href="tbAcSecondaryheadMaster.html"><s:message code="Cancel" />
						<spring:message code="account.back" text="Back" /></a>
				</div>
			</form:form>

		</div>
	</div>
</div>
