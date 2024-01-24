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

	<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});
</script>

	<jsp:directive.page contentType="text/html;charset=UTF-8" />

	<div class="widget" id="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="accounts.Secondaryhead.SecondaryheadMaster"
					text="Secondaryhead Master" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
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
													<label class="col-sm-2 control-label "><spring:message
															code="account.budget.code.master.fundcode" text="" /></label>
													<div class="col-sm-4">

														<c:set value="${secondaryheadMaster.fundId}" var="fundId"></c:set>
														<c:forEach items="${listOfTbAcFundMasterItems}"
															varStatus="status" var="fundItem">
															<c:if test="${fundId eq fundItem.key}">
																<form:input type="text" value="${fundItem.value}"
																	path="" class="form-control" id="fundId" />
															</c:if>
														</c:forEach>

													</div>
												</c:if>

												<c:if test="${fieldStatus == 'Y'}">
													<label class="col-sm-2 control-label "><spring:message
															code="" text="Function" /></label>
													<div class="col-sm-4">

														<c:set value="${secondaryheadMaster.fieldId}"
															var="fieldId"></c:set>
														<c:forEach items="${listOfTbAcFieldMasterItems}"
															varStatus="status" var="fieldItem">
															<c:if test="${fieldId eq fieldItem.key}">
																<form:input type="text" value="${fieldItem.value}"
																	path="" class="form-control" id="fieldId" />
															</c:if>
														</c:forEach>

													</div>
												</c:if>

											</div>

											<div class="form-group">
												<c:if test="${functionStatus == 'Y'}">
													<label class="col-sm-2 control-label "><spring:message
															code="account.common.function" text="Function" /></label>
													<div class="col-sm-4">

														<c:set value="${secondaryheadMaster.functionId}"
															var="functionId"></c:set>
														<c:forEach items="${listOfTbAcPrimaryMasterItems}"
															varStatus="status" var="funcItem">
															<c:if test="${functionId eq funcItem.key}">
																<form:input type="text" value="${funcItem.value}"
																	path="" class="form-control" id="functionId" />
															</c:if>
														</c:forEach>

													</div>
												</c:if>
												
												<c:if test="${primaryStatus == 'Y'}">
													<label class="col-sm-2 control-label "><spring:message
															code="account.budget.code.master.primaryaccountcode" text="Primary Head" /></label>
													<div class="col-sm-4">

														<c:set value="${secondaryheadMaster.pacHeadId}"
															var="pacHeadId"></c:set>
														<c:forEach items="${listOfTbAcFunctionMasterItems}"
															varStatus="status" var="functionItem">
															<c:if test="${pacHeadId eq functionItem.key}">
																<form:input type="text" value="${functionItem.value}"
																	path="" class="form-control" id="pacHeadId" />
															</c:if>
														</c:forEach>

													</div>
												</c:if>

											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label "> <spring:message
														code="account.budget.code.master.primaryaccountcode" text="Ledger Type" /></label>
												<div class="col-sm-4">
													<form:input type="text" path="sacLeddgerTypeCpdDesc"
														class="form-control" id="sacLeddgerTypeCpdDesc" />
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
											<div class="form-group">
												<c:if test="${secondaryStatus == 'Y'}">
													<label class="col-sm-2 control-label " for="sacHeadDesc"><spring:message
															code="account.secondary.head" text="Secondary Head(Eng)" /></label>
													<div class="col-sm-4 ">
														<form:textarea id="sacHeadDesc" path="sacHeadCodeDesc"
															class=" form-control  sacHeadDesc " maxLength="500"
															readonly="false" />
													</div>
													
													<label class="col-sm-2 control-label " for="acHeadCode"><spring:message
															code="account.tenderentrydetails.budgethead" text="Account Head" /></label>
													<div class="col-sm-4 ">
														<form:textarea id="acHeadCode" path="acHeadCode"
															class=" form-control " maxLength="500"
															readonly="false" />
													</div>
													
												</c:if>
											</div>

											<c:if test="${mode != 'create'}">
												<div class="form-group">
													<label class="col-sm-2 control-label "><spring:message
															code="budget.additionalsupplemental.authorization.status" text="Status" /></label>
													<div class="col-sm-4 ">
														<form:input type="text" path="sacStatusCpdDesc"
															class="form-control" id="sacStatusCpdDesc" />
													</div>
													<label class="col-sm-2 control-label " for="sacHeadDescReg"><spring:message
															code="account.secondary.head.reg" text="Secondary Head(Reg)" /></label>
													<div class="col-sm-4 ">
														<form:textarea id="sacHeadDescReg" path="oldAliasLendgerCode"
															class=" form-control  sacHeadDescReg " maxLength="500"
															readonly="false" />
													</div>
													
													
												</div>
												<div class="form-group">
												
												  <label class="col-sm-2 control-label"
														for="IncativeReasone"><spring:message code="account.Secondaryhead.inactive.reason"
															text="Inactive Reason" /></label>
													<div class="col-sm-4">
														<form:textarea id="IncativeReason" path="statusReason"
															class=" form-control  IncativeReason "
															maxLength="400" readonly="true"
															data-rule-required="false" />
													
													</div>
												 
												
												<label class="col-sm-2 control-label"
														for="oldSacHeadCode"><spring:message code="old.account.head.code"
															text="Old Account Head Code" /></label>
													<div class="col-sm-4">
														<form:textarea id="oldAccHead" path="oldSacHeadCode"
															class=" form-control  oldSacHeadCode "
															maxLength="100" readonly="true"
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

					<!-- "CANCEL" button ( HREF link ) -->
					<s:url var="cancelButtonURL" value="/tbAcSecondaryheadMaster" />
					<a title="Cancel" class="btn btn-danger"
						href="tbAcSecondaryheadMaster.html"><s:message code="Cancel" />
						<spring:message code="account.bankmaster.back" text="Back" /></a>
				</div>
			</form:form>

		</div>
	</div>
</div>
