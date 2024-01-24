<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountBudgetOpenBalanceMaster.js"></script>

<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}      
	});
	
	function crdrtype(){
		debugger;
		  var cpdIdDrcr = $('#cpdIdDrcr0 option:selected').text();
		if(cpdIdDrcr=="DR"){
			var drTypeValue = $("#drTypeValue").val();
			$("#cpdIdDrcr0").val(drTypeValue);
		}else if(cpdIdDrcr=="CR"){
			var crTypeValue = $("#crTypeValue").val();
			$("#cpdIdDrcr0").val(crTypeValue);
		}
	}
</script>

<c:if test="${tbAcBugopenBalance.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>

<div class="widget" id="widget">
	<div class="widget-header">

		<h2>
			<spring:message code="account.budgetopenmaster.title" />
		</h2>
		<apptags:helpDoc url="AccountBudgetOpenBalanceMaster.html" helpDocRefURL="AccountBudgetOpenBalanceMaster.html"></apptags:helpDoc>
	</div>

	<div class="widget-content padding">

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcBugopenBalance" name="frmMaster" method="POST"
			action="AccountBudgetOpenBalanceMaster.html">
			<form:hidden path="secondaryId" id="secondaryId" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="flagFlzdDup" id="flagFlzdDup" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="drTypeValue" id="drTypeValue" />
			<form:hidden path="crTypeValue" id="crTypeValue" />

			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
			<form:hidden path="hasError" />
			<form:hidden path="opnId" />
			<form:hidden path="index" id="index" />
			<form:hidden path="flagFlzdDup" id="flagFlzdDup" />
			<input type="hidden" value="${viewMode}" id="test" />

			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
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
				id="errorDivIdF" style="display: none;">
				<span id="errorIdF"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />




			<c:set var="count" value="0" scope="page" />

			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"><spring:message
									code="account.budgetopenmaster.financialyear" text="" /></label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<form:select id="faYearid" path="faYearid"
										cssClass="form-control mandColorClass" disabled="${viewMode}"
										onchange="findClearAllData(this);" data-rule-required="true">
										<%-- <form:option value="">
											<spring:message
												code="account.budgetopenmaster.selectfinancialyear" text="" />
										</form:option> --%>
										<c:forEach items="${financeMap}" varStatus="status"
											var="financeMap">
											<form:option value="${financeMap.key}"
												code="${financeMap.key}">${financeMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<c:if test="${MODE_DATA == 'EDIT'}">
								<div class="col-sm-4">
									<form:hidden path="faYearid" id="faYearid" />
									<form:select id="faYearid" path="faYearid"
										cssClass="form-control mandColorClass"
										disabled="${viewMode ne 'true'}"
										onchange="findClearAllData(this);">
										<form:option value="">
											<spring:message
												code="account.budgetopenmaster.selectfinancialyear" text="" />
										</form:option>
										<c:forEach items="${financeMap}" varStatus="status"
											var="financeMap">
											<form:option value="${financeMap.key}"
												code="${financeMap.key}">${financeMap.value}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

							<label class="control-label col-sm-2 required-control"><spring:message
									code="account.budgetopenmaster.headcategory" text="Head Category" /> </label>

							<c:if test="${MODE_DATA == 'create'}">
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="COA" />
									<form:select path="opnBalType"
										class="form-control mandColorClass" id="opnBalType"
										disabled="${viewMode}"
										onchange="getPrimarySecondaryALLOpeningBalanceData(this)"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="account.budgetopenmaster.selectheadcategory" text="Select Head Category" />
										</form:option>
										<c:forEach items="${levelMap}" varStatus="status" var="levelChild">
											<form:option code="${levelChild.lookUpCode}"
												value="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
								</form:select>
								</div>
							</c:if>

							<c:if test="${MODE_DATA == 'EDIT'}">
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="COA" />
									<form:hidden path="opnBalType" id="opnBalType" />
									<form:select path="opnBalType"
										class="form-control mandColorClass" id="opnBalType"
										disabled="${viewMode ne 'true'}"
										onchange="getPrimarySecondaryALLOpeningBalanceData(this)">
										<form:option value="">
											<spring:message code="account.budgetopenmaster.selectheadcategory" text="Select Head Category" />
										</form:option>

										<c:forEach items="${levelMap}" varStatus="status"
											var="levelChild">
											<form:option code="${levelChild.lookUpCode}"
												value="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>

						</div>

						<div class="form-group">

							<c:if test="${fundStatus == 'Y'}">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budgetopenmaster.fundcode" text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="fundId" path="fundId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode}" onchange="findClearAllData(this);"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="account.budgetopenmaster.selectfundcode" text="" />
											</form:option>
											<c:forEach items="${listOfTbAcFundMasterItems}"
												varStatus="status" var="fundItem">
												<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
										<form:hidden path="fundId" id="fundId" />
										<form:select id="fundId" path="fundId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode ne 'true'}"
											onchange="findClearAllData(this);">
											<form:option value="">
												<spring:message
													code="account.budgetopenmaster.selectfundcode" text="" />
											</form:option>
											<c:forEach items="${listOfTbAcFundMasterItems}"
												varStatus="status" var="fundItem">
												<form:option value="${fundItem.key}" code="${fundItem.key}">${fundItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
							</c:if>

							<c:if test="${fieldStatus == 'Y'}">
								<label class="control-label col-sm-2 required-control"><spring:message
										code="account.budgetopenmaster.fieldcode" text="" /></label>

								<c:if test="${MODE_DATA == 'create'}">
									<div class="col-sm-4">
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode}" onchange="findClearAllData(this);"
											data-rule-required="true">
											<form:option value="">
												<spring:message
													code="account.budgetopenmaster.selectfieldcode" text="" />
											</form:option>
											<c:forEach items="${listOfTbAcFieldMasterItems}"
												varStatus="status" var="fieldItem">
												<form:option value="${fieldItem.key}"
													code="${fieldItem.key}">${fieldItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${MODE_DATA == 'EDIT'}">
									<div class="col-sm-4">
										<form:hidden path="fieldId" id="fieldId" />
										<form:select id="fieldId" path="fieldId"
											cssClass="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode ne 'true'}"
											onchange="findClearAllData(this);">
											<form:option value="">
												<spring:message
													code="account.budgetopenmaster.selectfieldcode" text="" />
											</form:option>
											<c:forEach items="${listOfTbAcFieldMasterItems}"
												varStatus="status" var="fieldItem">
												<form:option value="${fieldItem.key}"
													code="${fieldItem.key}">${fieldItem.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
							</c:if>

						</div>

						<form:hidden path="opnId" />
						<div id="opnId" class="">
							<div class="table-overflow-sm" id="opnBalTableDivID">
								<table class="table table-bordered table-condensed"
									id="opnBalTable">

									<c:if test="${MODE_DATA == 'create'}">
										<tr>
											<th scope="col" width="50%" style="text-align: center"><spring:message
													code="account.budgetopenmaster.accountheads" text="Account Heads" /><span
												class="mand">*</span></th>
											<th scope="col" width="20%"><spring:message
													code="account.budgetopenmaster.openingbalance" text="Opening Balance" /><span
												class="mand">*</span></th>
											<th scope="col" width="10%"><spring:message code="voucher.template.dr.cr"
													text="DR/CR" /><span class="mand">*</span></th>
											<th scope="col" width="10%"><spring:message
													code="account.budgetopenmaster.finalized" text="Finalized" />&nbsp;
												&nbsp;<label class="margin-left-10" for="flag"><input
													type="checkbox" id="flag">
												<spring:message code="account.budgetopenmaster.selectall"
														text="Select All" /></label></th>
											<th class="text-center" scope="col" width="25%"><span
												class="small"><spring:message
														code="account.budgetopenmaster.addremove" text="Add/Remove" /></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<tr>
											<th scope="col" width="50%" style="text-align: center">
												<spring:message code="account.budgetopenmaster.accountheads"
													text="" /> <span class="mand">*</span>
											</th>
											<th scope="col" width="25%"><spring:message
													code="account.budgetopenmaster.openingbalance" text="Opening Balance" /><span
												class="mand">*</span></th>
											<th scope="col" width="15%"><spring:message code="voucher.template.dr.cr"
													text="DR/CR" /><span class="mand">*</span></th>
											<th scope="col" width="15%"><spring:message
													code="account.budgetopenmaster.finalized" text="Finalized" />&nbsp;
												&nbsp;<label class="margin-left-10" for="flag"><input
													type="checkbox" id="flag">
												<spring:message code="account.budgetopenmaster.selectall"
														text="Select All" /></label></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'VIEW'}">
										<tr>
											<th scope="col" width="50%" style="text-align: center">
												<spring:message code="account.budgetopenmaster.accountheads"
													text="" /> <span class="mand">*</span>
											</th>
											<th scope="col" width="25%"><spring:message
													code="account.budgetopenmaster.openingbalance" text="Opening Balance" /><span
												class="mand"></span></th>
											<th scope="col" width="15%"><spring:message
													code="account.budgetopenmaster.finalized" text="Finalized" />&nbsp;
												&nbsp;<label class="margin-left-10" for="flag"><input
													type="checkbox" id="flag">
												<spring:message code="account.budgetopenmaster.selectall"
														text="Select All" /></label></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'create'}">
										<tr id="opnBalId" class="appendableClass">
											<td><form:select
													path="bugReappMasterDtoList[${count}].sacHeadId"
													cssClass="form-control mandColorClass chosen-select-no-results applyChoosen"
													id="pacHeadId${count}"
													onchange="findduplicatecombinationexit(${count})"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="account.budgetopenmaster.selectaccountheadcodes" text="Select Account Head Codes" />
													</form:option>
													<c:forEach items="${bugpacsacHeadList}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input
													cssClass="form-control mandColorClass text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="openbalAmt${count}"
													path="bugReappMasterDtoList[${count}].openbalAmt"
													onchange="getAmountIndianCurrencyFormat(${count})"
													data-rule-required="true"></form:input></td>
											<td><form:select
													path="bugReappMasterDtoList[${count}].cpdIdDrcr"
													cssClass="form-control mandColorClass"
													id="cpdIdDrcr${count}" data-rule-required="true" onchange="crdrtype(this)">
													<form:option value="">
														<spring:message code="account.budgetopenmaster.selectdrcr" text="Select DR/CR" />
													</form:option>
													<c:forEach items="${drCrLevelMap}" varStatus="status"
														var="levelChild">
														<form:option code="${levelChild.lookUpCode}"
															value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><label
												class="checkbox-inline padding-top-0" for="flagFlzd"><input
													type="checkbox" id="flagFlzd${count}"
													name="bugReappMasterDtoList[${count}].flagFlzd" value="Y"></label></td>
											<td class="text-center">
												<button title="Add" class="btn btn-success btn-sm addButton"
													id="addButton${count}">
													<i class="fa fa-plus-circle"></i>
												</button>
												<button title="Delete"
													class="btn btn-danger btn-sm delButton"
													id="delButton${count}">
													<i class="fa fa-trash-o"></i>
												</button>
											</td>
										</tr>
									</c:if>





									<c:if test="${MODE_DATA == 'EDIT'}">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><form:select
													path="bugReappMasterDtoList[0].sacHeadId"
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="pacHeadIdO"
													onchange="findduplicatecombinationexit(${0})"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="" text="Select Account Head Codes" />
													</form:option>
													<c:forEach items="${bugpacsacHeadList}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input
													cssClass="form-control mandColorClass text-right"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="openbalAmtO" path="bugReappMasterDtoList[0].openbalAmt"
													onchange="getAmountIndianCurrencyFormatStatic(${0})"
													data-rule-required="true"></form:input></td>

											<td><form:select
													path="bugReappMasterDtoList[0].cpdIdDrcr"
													cssClass="form-control mandColorClass" id="cpdIdDrcrO"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="" text="Select DR/CR" />
													</form:option>
													<c:forEach items="${drCrLevelMap}" varStatus="status"
														var="levelChild">
														<form:option code="${levelChild.lookUpCode}"
															value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><label
												class="checkbox-inline padding-top-0" for="flagFlzd"><form:checkbox
														id="flagFlzd" path="bugReappMasterDtoList[0].flagFlzd"
														value="Y" /></label></td>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'VIEW'}">
										<c:set value="${status.index}" var="count"></c:set>
										<tr>
											<td><form:select
													path="bugReappMasterDtoList[0].sacHeadId"
													cssClass="form-control " id="pacHeadId${count}">
													<form:option value="">
														<spring:message code="" text="Select Account Head Codes" />
													</form:option>
													<c:forEach items="${bugpacsacHeadList}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value} </form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input
													cssClass="hasMyNumber form-control text-right"
													id="openbalAmt${count}"
													path="bugReappMasterDtoList[0].openbalAmt"></form:input></td>
											<td align="center"><label
												class="checkbox-inline padding-top-0" for="flagFlzd"><form:checkbox
														id="flagFlzd" path="bugReappMasterDtoList[0].flagFlzd"
														value="Y" /></label></td>
										</tr>
									</c:if>

								</table>
							</div>
						</div>

					</fieldset>
				</li>
			</ul>

			<INPUT type="hidden" id="count" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value='<spring:message code="account.configuration.save" text="Save" />'> </input>


					<button type="Reset" id="clearBtn"
						class="btn btn-warning createData">
						<spring:message code="reset.msg" text="Reset" />
					</button>

				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value='<spring:message code="account.configuration.save" text="Save" />'> </input>

				</c:if>

				<spring:url var="cancelButtonURL"
					value="AccountBudgetOpenBalanceMaster.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>


			</div>




		</form:form>




	</div>
</div>



<c:if test="${tbAcBugopenBalance.hasError =='true'}">
	</div>
</c:if>



