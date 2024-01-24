<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/AccountDeposit.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script>
	$(document).ready(
			function() {

				var response = __doAjaxRequest(
						'AccountReceiptEntry.html?SLIDate', 'GET', {}, false,
						'json');
				var sliDate = new Date(response[0], response[1], response[2]);
				var lessThanSLIDate = new Date(sliDate.getFullYear(), sliDate
						.getMonth(), sliDate.getDate() - 1);
				$("#depEntryDateId").datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate : lessThanSLIDate
				});
				$("#depEntryDateId").keyup(function(e) {
					if (e.keyCode != 8) {
						if ($(this).val().length == 2) {
							$(this).val($(this).val() + "/");
						} else if ($(this).val().length == 5) {
							$(this).val($(this).val() + "/");
						}
					}
				});
				
				var date = new Date();
				var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
				$("#defectLiablityDate").datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate : today
					
				});
				$("#defectLiablityDate").keyup(function(e) {
					if (e.keyCode != 8) {
						if ($(this).val().length == 2) {
							$(this).val($(this).val() + "/");
						} else if ($(this).val().length == 5) {
							$(this).val($(this).val() + "/");
						}
					}
				});
				

				$("#vmVendorid").chosen();
				$("#cpdDepositType").chosen();
				$("#dpDeptid").chosen();
				$("#sacHeadId").chosen();
			});

	$(function() {
		$("#cashDepositPanel").hide();
		$("#cashWithDrawPanel").hide();
		$("#entryType").val("1");
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	});
</script>

<script>
	$(document).ready(function() {

		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}

	});
</script>

<div class="form-div" id="content">
	<script>
		$("#depAmount").keyup(function(event) {
			var bal = $(this).val();
			if (bal != '' && bal != undefined && !isNaN(bal)) {
				var stt = 0;
				stt = parseFloat(bal);
				var result = stt.toFixed(2);
				$("#depRefundBal").val(result);
			}
		});
	</script>

	<div class="widget" id="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.deposite.master" text="Deposit Master" />
			</h2>
		<apptags:helpDoc url="AccountDeposit.html" helpDocRefURL="AccountDeposit.html"></apptags:helpDoc>		
		</div>

		<c:if test="${!viewMode}">
			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
		</c:if>

		<div class="widget-content padding" id="depositDiv">

			<c:url value="${saveAction}" var="url_form_submit" />
			<c:url value="${mode}" var="form_mode" />

			<form:form Method="POST" action="AccountDeposit.html" name="deposit"
				id="deposit" class="form-horizontal"
				modelAttribute="accountDepositBean">

				<form:hidden path="depId" id="depId" />
				<form:hidden path="depNo" id="depNo" />
				<form:hidden path="" value="${keyTest}" id="keyTest" />
				<form:hidden path="liveModeDate" id="liveModeDate" />
				<%-- <form:hidden path="cpdStatus" id="cpdStatus"/> --%>

				<div class="warning-div alert alert-danger alert-dismissible hide"
					id="clientSideErrorDivScrutiny"></div>

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="form-group">

					<c:if test="${form_mode !='create'}">
						<label class="control-label col-sm-2 "><spring:message
								code="account.deposit.depNo" text="" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="depNo" disabled="true"
								class="form-control" />
						</div>
					</c:if>

					<label class="control-label col-sm-2 required-control"><spring:message
							code="account.deposit.deposittype" text="Type of Deposit" /></label>

					<c:if test="${form_mode =='create'}">
						<div class="col-sm-4">
							<form:select id="cpdDepositType" path="cpdDepositType"
								disabled="${viewMode}"
								class="form-control mandColorClass chosen-select-no-results"
								data-rule-required="true"
								onchange="findDepositTypeWiseAccountHeads(this)">
								<form:option value="">
									<spring:message code="account.common.select" text="" />
								</form:option>
								<c:forEach items="${depositType}" varStatus="status"
									var="depositTypeLookUp">
									<form:option value="${depositTypeLookUp.lookUpId }"
										code="${depositTypeLookUp.lookUpId }">${depositTypeLookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

					<c:if test="${form_mode == 'update'}">
						<div class="col-sm-4">
							<form:select id="cpdDepositType" path="cpdDepositType"
								disabled="${viewMode}"
								class="form-control mandColorClass chosen-select-no-results"
								data-rule-required="true"
								onchange="findDepositTypeWiseAccountHeads(this)">
								<form:option value="">
									<spring:message code="account.common.select" text="" />
								</form:option>
								<c:forEach items="${depositType}" varStatus="status"
									var="depositTypeLookUp">
									<form:option value="${depositTypeLookUp.lookUpId }"
										code="${depositTypeLookUp.lookUpId }">${depositTypeLookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

				</div>

				<div class="form-group">

					<label for="depEntryDateId"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.deposite.date" text="Deposit Date:-" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="depEntryDate" id="depEntryDateId"
								cssClass="mandColorClass form-control" disabled="${viewMode}"
								data-rule-required="true" maxlength="10" />
							<label class="input-group-addon mandColorClass"
								for="depEntryDateId"><i class="fa fa-calendar"></i> </label>
						</div>
					</div>

					<label class="control-label col-sm-2 required-control"><spring:message
							code="account.deposit.deposite.name" text="Depositor Name" /></label>
					<div class="col-sm-4">
						<form:select path="vmVendorid" id="vmVendorid"
							class="form-control mandColorClass chosen-select-no-results"
							disabled="${viewMode}" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" text="" />
							</form:option>
							<c:forEach items="${vendorMap}" varStatus="status"
								var="vendorMap">
								<form:option value="${vendorMap.key }" code="${vendorMap.key }">${ vendorMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="account.deposit.Department" text=""></spring:message></label>
					<div class="col-sm-4">
						<form:select path="dpDeptid" id="dpDeptid"
							class="form-control mandColorClass chosen-select-no-results "
							disabled="${viewMode}" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" text="" />
							</form:option>
							<c:forEach items="${deptMap }" varStatus="status" var="deptMap">
								<form:option value="${deptMap.key }" code="${ deptMap.key}">${deptMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control"><spring:message
							code="account.deposit.Head" text="Deposit Head" /></label>
					<div class="col-sm-4">
						<form:select id="sacHeadId" path="sacHeadId"
							disabled="${viewMode}"
							cssClass="form-control mandColorClass chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${deptTyepsMap}" varStatus="status"
								var="sacItem">
								<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"
						for="depAmount"><spring:message
							code="account.deposit.DepositAmount" text="" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="depAmount" id="depAmount"
							disabled="${viewMode}"
							onkeypress="return hasAmount(event, this, 13, 2)"
							class="form-control text-right mandColorClass amount hasNumber"
							onchange="getAmountFormatInStatic('depAmount')"
							data-rule-required="true" />
					</div>


					<label class="control-label col-sm-2 required-control"><spring:message
							code="account.deposit.BalanceAmmount" text="Balance Amount"></spring:message></label>
					<div class="col-sm-4">
						<form:input type="text"
							onkeypress="return hasAmount(event, this, 13, 2)"
							onchange="getAmountFormatInStatic('depRefundBal')"
							id="depRefundBal" path="depRefundBal" disabled="${viewMode}"
							class="form-control text-right mandColorClass amount hasNumber"
							data-rule-required="true" />
					</div>

				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="depReceiptno"><spring:message
							code="account.deposit.Referenceno" text="" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="depReceiptno"
							class="hasNumber form-control mandColorClass" maxlength="10"
							disabled="${viewMode}" data-rule-required="true" />
					</div>

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="account.deposit.narration"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:textarea id="depNarration" path="depNarration"
							class="form-control mandColorClass" maxLength="200"
							disabled="${viewMode}" data-rule-required="true" style="font-size:10pt;"/>
					</div>

				</div>

             <c:if test="${form_mode =='create'}">
				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="account.deposit.defect.liablity.date" text="Defect Liability Date"></spring:message>
					</label>
					<div class="col-sm-4">
					  <div class="input-group">
						<form:input path="defectLiablityDate" id="defectLiablityDate"
							cssClass="mandColorClass form-control" disabled="${viewMode}"
							maxlength="10" />
						<label class="input-group-addon mandColorClass"
							for="defectLiablityDate"><i class="fa fa-calendar"></i> </label>
					</div>
                  </div>
				</div>
             </c:if>
             
				<c:if test="${!viewMode}">
					<c:if test="${form_mode == 'update'}">
						<div class="form-group">

							<label class="control-label col-sm-2 required-control"><spring:message
									code="account.deposit.status" text="" /></label>
							<div class="col-sm-4">
								<form:select id="cpdStatus" path="cpdStatus"
									class="form-control mandColorClass" data-rule-required="true">
									<form:option value="">
										<spring:message code="account.common.select" text="" />
									</form:option>
									<c:forEach items="${depositstatus}" varStatus="status"
										var="depositStatuslookUp">
										<form:option value="${depositStatuslookUp.lookUpId }"
											code="${depositStatuslookUp.lookUpId }">${depositStatuslookUp.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="control-label col-sm-2"> <spring:message
									code="account.deposit.defect.liablity.date"
									text="Defect Liability Date"></spring:message>
							</label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="defectLiablityDate" id="defectLiablityDate"
										cssClass="mandColorClass form-control" disabled="${viewMode}"
										maxlength="10" />
									<label class="input-group-addon mandColorClass"
										for="defectLiablityDate"><i class="fa fa-calendar"></i>
									</label>
								</div>
							</div>
						</div>
					</c:if>
				</c:if>

				<c:if test="${viewMode}">
					<div class="form-group">

						<label class="control-label col-sm-2 required-control"><spring:message
								code="account.deposit.status" text="" /></label>
						<div class="col-sm-4">
							<form:select id="cpdStatus" path="cpdStatus"
								class="form-control mandColorClass" disabled="true"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="account.common.select" text="" />
								</form:option>
								<c:forEach items="${depositstatus}" varStatus="status"
									var="depositStatuslookUp">
									<form:option value="${depositStatuslookUp.lookUpId }"
										code="${depositStatuslookUp.lookUpId }">${depositStatuslookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>

						<label class="control-label col-sm-2"> <spring:message
								code="account.deposit.defect.liablity.date" text="Defect Liability Date"></spring:message>
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="defectLiablityDate" id="defectLiablityDate"
									cssClass="mandColorClass form-control" disabled="${viewMode}"
									maxlength="10" />
								<label class="input-group-addon mandColorClass"
									for="defectLiablityDate"><i class="fa fa-calendar"></i>
								</label>
							</div>
						</div>

					</div>
				</c:if>

				<div class="text-center padding-10">
					<c:if test="${!viewMode}">

						<button type="button" class="btn btn-success btn-submit"
							onclick="saveDepositForm(this)" id="btnSave">
							<spring:message code="account.configuration.save" text="Save" />
						</button>
						<c:if test="${form_mode =='create'}">
							<input type="button" id="Reset"
								class="btn btn-warning createData" value="<spring:message code="account.bankmaster.reset" text="Reset"/>"></input>
						</c:if>
					</c:if>
					<input type="button" class="btn btn-danger"
					onclick="window.location.href='AccountDeposit.html'"
					value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
				</div>
			</form:form>
		</div>
	</div>
</div>