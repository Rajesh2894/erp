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
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
	$(function() {
		var response = __doAjaxRequest('AccountReceiptEntry.html?SLIDate',
				'GET', {}, false, 'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date
				.getDate());

		$("#transactionDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today
		});

		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$("#fromDateId").datepicker('setDate', new Date());

		$('#fromDateId, #toDateId, #transactionDateId').change(function() {
			//alert();
			var check = $(this).val();
			if (check == '') {
				$(this).parent().switchClass("has-success", "has-error");
			} else {
				$(this).parent().switchClass("has-error", "has-success");
			}
		});

		$("#toDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$("#toDateId").datepicker('setDate', new Date());
		$("#accountHeadId").chosen({
			width : "95%"
		});
		$("#transactionDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		$("#toDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		$("#fromDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	});
</script>

<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					 <spring:message code="account.Collection.Report"
						text="Account Collection Report" /> 
					<%--  ${testPage}  --%>
				</h2>
			</div>
			<div class="widget-content padding">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:form action="AccountFinancialReport.html"
					modelAttribute="reportDTO" class="form-horizontal"
					id="AccountFinancialReportfrm">
					<input type="hidden" value="${reportType}" id="reportTypeHidden">
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="reportTypeId"><spring:message
								code="account.financial.report.type" text="Report Type" /></label>
						<div class="col-sm-4">
							<form:select path="reportTypeId"
								class="form-control mandColorClass" data-rule-required="true"
								id="reportTypeId">
								<form:option value="">
									<spring:message code="account.financial.sel.report.type"
										text="Select Report Type" />
								</form:option>
								<c:forEach items="${reportTypeLookUps}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<c:choose>
							<c:when
								test="${reportType eq 'GLR' or reportType eq 'TBR' or reportType eq 'CAR' or reportType eq  'RPR' or reportType eq  'BSR' or reportType eq 'INE' or reportType eq 'GBB' or reportType eq 'GCB' or reportType eq 'RDP' or reportType eq 'BRS' or reportType eq 'DYB' or reportType eq 'JRB'  or reportType eq 'PCR' or reportType eq 'DCR'  or reportType eq 'BAS' or reportType eq 'CCR' or reportType eq 'TRR' or reportType eq 'CRR' or reportType eq 'CCN' or reportType eq 'CDR' or reportType eq 'CSR' or reportType eq 'ATR' or reportType eq 'TFC' }">

								<c:if
									test="${reportType ne 'GCB' and reportType ne 'RDP' and reportType ne 'INE' and reportType ne 'DYB' and reportType ne 'JRB'  and reportType ne 'PCR' and reportType ne 'DCR' and reportType ne 'BAS' and reportType ne 'TRR' and reportType ne 'CRR' and reportType ne 'CCN' and reportType ne 'CDR' and reportType ne 'CSR' and reportType ne 'ATR' and reportType ne 'TFC' and reportType ne 'BRS' and reportType ne 'TBR'}">
									<label class="control-label col-sm-2 required-control"
										for="accountHeadId"><spring:message
											code="account.deposit.accountHead" text="Account Head" /></label>

									<div class="col-sm-4">
										<form:select path="accountHeadId"
											class="form-control mandColorClass chosen-select-no-results"
											data-rule-required="true" id="accountHeadId">
											<form:option value="-1">
												<spring:message code="account.common.all"
													text="All" />
											</form:option>
											<c:forEach items="${acHeadLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${reportType eq 'BRS'}">
									<label class="control-label col-sm-2 required-control"
										for="accountHeadId"><spring:message code="account.fund.bank.acc"
											text="Bank Account" /></label>
									<div class="col-sm-4">
										<form:select path="accountHeadId"
											class="form-control mandColorClass" data-rule-required="true"
											id="accountHeadId">
											<form:option value="">
												<spring:message code="account.financial.sel.report.type"
													text="Select Report Type" />
											</form:option>
											<c:forEach items="${bankList}" var="bankAccountMap">
												<form:option value="${bankAccountMap.key}">${bankAccountMap.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>
								<c:if test="${reportType eq 'TBR'}">
									<label class="control-label col-sm-2 required-control"
										for="accountHeadId"><spring:message
											code="account.deposit.accountHead" text="Account Head" /></label>
									<div class="col-sm-4">
										<form:select path="accountHeadId"
											class="form-control mandColorClass chosen-select-no-results"
											data-rule-required="true" id="accountHeadId" disabled="true">
											<form:option value="4">Secondary Head Code</form:option>
											<%-- <c:forEach items="${acHeadLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
											</c:forEach> --%>
										</form:select>
									</div>
								</c:if>
								<c:if test="${reportType eq 'INE'}">
									<label class="control-label col-sm-2 required-control"
										for="accountHeadId"><spring:message
											code="account.deposit.accountHead" text="Account Head" /></label>

									<div class="col-sm-4">
										<form:select path="accountHeadId"
											class="form-control mandColorClass chosen-select-no-results"
											data-rule-required="true" id="accountHeadId">
											<form:option value="">
												<spring:message
													code="account.budget.code.master.selectaccountheads"
													text="Select Account Head" />
											</form:option>
											<c:forEach items="${acHeadLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<c:if test="${reportType eq 'RDP'}">

									<label class="control-label col-sm-2 required-control"
										for="registerDepTypeId"><spring:message code="account.deposit.deposit.type"
											text="Deposit Type" /></label>
									<div class="col-sm-4">
										<form:select path="registerDepTypeId"
											class="form-control mandColorClass" data-rule-required="true"
											id="registerDepTypeId">
											<form:option value="">
												<spring:message code="account.select.deposit.type" text="Select Deposit Type" />
											</form:option>
											<c:forEach items="${registerDepositList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</c:if>
								<c:if test="${reportType eq 'CRR'}">

									<label class="control-label col-sm-2 required-control"
										for="categoryId"><spring:message code=""
											text="Category" /></label>
									<div class="col-sm-4">
										<form:select path="categoryId"
											class="form-control mandColorClass" data-rule-required="true"
											id="categoryId">
											<form:option value="">
												<spring:message code="" text="Select Category" />
											</form:option>
											<c:forEach items="${clearLookUpList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</c:if>
								<c:if test="${reportType eq 'GCB'}">

									<label class="control-label col-sm-2 required-control"
										for="paymodeId"><spring:message code="acc.type" text="Type" /></label>
									<div class="col-sm-4">
										<form:select path="paymodeId"
											class="form-control mandColorClass" data-rule-required="true"
											id="paymodeId">
											<form:option value="">
												<spring:message code="bank.master.seltype" text="Select Type" />
											</form:option>
											<c:forEach items="${paymentMode}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</c:if>
								<c:if test="${reportType eq 'CDR'}">
									<label class="col-sm-2 control-label "
										for="registerDepTypeId"> <spring:message
											code="bill.department" text="Department"></spring:message>
									</label>
									<div class="col-sm-4">
										<!--  registerDepTypeId used for carrying the DeparmentId -->
										<form:select id="registerDepTypeId" path=""
											class="form-control chosen-select-no-results mandColorClass"
										>
											<form:option value="" selected="true">
												<spring:message code="advance.management.master.select"
													text="Select" />
											</form:option>
											<form:option value="-1">
												<spring:message code="account.cheque.cash.all" text="All" />
											</form:option>
											<c:forEach items="${depList}" var="dept">
												<form:option value="${dept.key}" code="${dept.key}">${dept.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if></div>
					
					<div class="form-group">
						<c:if test="${reportType eq 'BSR'}">
							<label for="transactionDateId"
								class="col-sm-2 control-label col-sm-2 required-control"><spring:message
									code="account.financial.as.on.date" text="As on Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="transactionDate" id="transactionDateId"
										cssClass="mandColorClass form-control datepicker"
										data-rule-required="true" maxlength="10" />
									<label class="input-group-addon mandColorClass"
										for="transactionDateId"><i class="fa fa-calendar"></i></label>
								</div>
							</div>
						</c:if>
					</div>
					
					
					<c:if test="${reportType eq 'BRS'}">
						<div class="form-group">
							<label for="transactionDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="budget.reappropriation.master.transactiondate"
									text="Transaction Date" /></label>

							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="transactionDate" id="transactionDateId"
										cssClass="mandColorClass form-control datepicker"
										data-rule-required="true" maxlength="10" />

									<label class="input-group-addon mandColorClass"
										for="transactionDateId"><i class="fa fa-calendar"></i>
									</label>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${reportType eq 'GBB' ||reportType eq 'GCB' || reportType eq 'JRB'||reportType eq 'CDR'||reportType eq 'BAS'}">
					<div class="form-group">
						<label for="Field" class="col-sm-2 control-label "><spring:message
								code="budget.estimate.sheet.field" text="Field" /></label>
						<div class="col-sm-4">
							<form:select path="fieldId"
								class="form-control mandColorClass chosen-select-no-results"
								name="field" id="fieldId">
								<option value=""><spring:message
										code="account.common.select" /></option>
								<c:forEach items="${fieldMasterLastLvls}" varStatus="status"
									var="fieldItem">
									<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
						</div>
					</c:if>
					
					<div class="form-group">
						<c:if test="${reportType eq 'TRR'}">
							<label class="col-sm-2 control-label"><spring:message
									code="accounts.receipt.transaction.type"
									text="Transaction Type" /><span class="mand">*</span></label>
							<form:hidden path="" id="transactionTypeIdHidden" />
							<div class="col-sm-4">
								<form:select path="transactionTypeId"
									class="form-control mandColorClass" data-rule-required="true"
									id="transactionTypeId">
									<form:option value="0"><spring:message
									code="account.common.select" />

									</form:option>
									<c:forEach items="${transactionTypeLookUps}" var="lookUp">
										<c:choose>
											<c:when test="${lookUp.lookUpId eq transactionTypeId}">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
							
							<label for="Field" class="col-sm-2 control-label "><spring:message
							code="budget.estimate.sheet.field" text="Field" /></label>
					<div class="col-sm-4">
						<form:select path="fieldId"
							class="form-control mandColorClass chosen-select-no-results"
							name="field" id="fieldId">
							<option value=""><spring:message
									code="account.common.select" /></option>
							<c:forEach items="${fieldMasterLastLvls}"
								varStatus="status" var="fieldItem">
								<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
						</c:if>
					</div>
					

					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${reportType eq 'BSR' or reportType eq 'CFS'}">
								<label for="transactionDateId"
									class="col-sm-2 control-label required-control"><spring:message
										code="account.financial.as.on.date" text="As on Date" /></label>
							</c:when>
							<c:otherwise>
								<c:if
									test="${reportType ne 'BER' and reportType ne 'RAS' and reportType ne 'OBS' and  reportType ne 'PCR' and  reportType ne 'DCR' and reportType ne 'BAS' and reportType ne 'TRR'  and reportType ne 'EBS' and reportType ne 'RBS'  and reportType ne 'CRR' and reportType ne 'CCN' and reportType ne 'CDR' and reportType ne 'CSR' }">
									<label for="transactionDateId" id="transactionDateIdlabel"
										class="col-sm-2 control-label required-control"><spring:message
											code="budget.reappropriation.master.transactiondate"
											text="Transaction Date" /></label>
								</c:if>
							</c:otherwise>
						</c:choose>
						<c:if
							test="${reportType ne 'BER' and reportType ne 'RAS' and reportType ne 'OBS' and  reportType ne 'PCR' and  reportType ne 'DCR'  and  reportType ne 'BAS' and  reportType ne 'TRR' and reportType ne 'EBS' and reportType ne 'RBS' and reportType ne 'CRR' and reportType ne 'CCN' and reportType ne 'CDR' and reportType ne 'CSR'}">
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="transactionDate" id="transactionDateId"
										cssClass="mandColorClass form-control datepicker"
										data-rule-required="false" maxlength="10" />
									<label class="input-group-addon mandColorClass" id="transactionDateIdcal"
										for="transactionDateId"><i class="fa fa-calendar"></i></label>
								</div>
							</div>

						</c:if>

					</c:otherwise>
					</c:choose>
					<c:if
						test="${reportType eq 'GLR' or reportType eq 'TBR' or reportType eq 'CAR' or reportType eq 'RPR' or reportType eq 'INE' or reportType eq 'GBB' or reportType eq 'GCB' or reportType eq 'RDP' or reportType eq 'DYB' or reportType eq 'JRB' or reportType eq 'PCR' or reportType eq 'DCR' or reportType eq 'BAS' or reportType eq 'TRR' or reportType eq 'CRR' or reportType eq 'CCN' or reportType eq 'CDR' or reportType eq 'CSR' or reportType eq 'ATR' }">
						<div class="form-group">
						 <c:if test="${reportType eq 'PCR'}">
									<label class="control-label col-sm-2 required-control"
										for="accountHeadId"><spring:message code="account.fund.bank.acc"
											text="Bank Account" /></label>
									<div class="col-sm-4">
										<form:select path="accountHeadId"
											class="form-control mandColorClass" data-rule-required="true"
											id="accountHeadId">
											<form:option value="-1">
												<spring:message code="account.common.all"
													text="All" />
											</form:option>
											<c:forEach items="${bankList}" var="bankAccountMap">
												<form:option value="${bankAccountMap.key}">${bankAccountMap.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
						
							
					 <label for="Field" class="col-sm-2 control-label required-control"><spring:message
							code="budget.estimate.sheet.field" text="Field" /></label>
					  <div class="col-sm-4">
						<form:select path=""
							class="form-control mandColorClass chosen-select-no-results" data-rule-required="true"
							name="field" id="fieldId">
							<option value="-1"><spring:message
									code="account.common.all" /></option>
							<c:forEach items="${listOfTbAcFieldMasterItems}"
								varStatus="status" var="fieldItem">
								<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
							</c:forEach>
						</form:select>
					 </div>	
				</c:if>	
					</div>
						<div class="form-group">
							<label for="fromDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="from.date.label" text="From Date" />
							</label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="fromDate" id="fromDateId"
										cssClass="mandColorClass form-control"
										data-rule-required="true" maxlength="10" />
									<label class="input-group-addon mandColorClass"
										for="fromDateId"><i class="fa fa-calendar"></i> </label>
								</div>
							</div>
							<label for="toDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="budget.reappropriation.authorization.todate"
									text="To Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="toDate" id="toDateId"
										cssClass="mandColorClass form-control datepicker"
										data-rule-required="true" maxlength="10" />
									<label class="input-group-addon mandColorClass" for="toDateId"><i
										class="fa fa-calendar"></i> </label>
								</div>
							</div>
						</div>
						
						

					</c:if>

					<c:if
						test="${reportType eq 'BER' or reportType eq 'RAS' or reportType eq 'OBS' or reportType eq 'EBS' or reportType eq 'RBS'  or reportType eq 'TFC'}">
						<!-- <div class="form-group"> -->
							<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.reappropriation.master.financialyear" text="" /></label>
							<div class="col-sm-4">
								<form:select id="faYearid" path="faYearid"
									cssClass="form-control mandColorClass"
									onchange="setHiddenField(this);" data-rule-required="true">
									<c:forEach items="${aFinancialYr}" varStatus="status"
										var="financeMap">
										<form:option value="${financeMap.key}"
											code="${financeMap.key}">${financeMap.value}</form:option>
									</c:forEach>
								</form:select>
							<!-- </div> -->

							<c:if test="${reportType eq 'TFC'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Vendor Name" /></label>
								<div class="col-sm-4">
									<form:select path="vendorName"
										class="form-control chosen-select-no-results mandColorClass"
										name="vmVendorname" id="vmVendorname">
										<option value=""><spring:message
												code="account.select" text="Select" /></option>
										<c:forEach items="${vendorList}" varStatus="status"
											var="vendor">
											<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:if>
						</div>
				 </div>
						<!-- new function and department list start -->
					 <c:if test="${reportType eq 'RBS'}">
					   <div class="form-group">	
					    	<label class="col-sm-2 control-label required-control" for="registerDepTypeId"> <spring:message
									code="bill.department" text="Department"></spring:message>
							</label>
							<div class="col-sm-4">
							<!--  registerDepTypeId used for carrying the DeparmentId --> 
								<form:select id="registerDepTypeId" path=""
									class="form-control chosen-select-no-results mandColorClass" data-rule-required="true">
									<form:option value="" selected="true">
										<spring:message code="advance.management.master.select" text="Select" />
									</form:option>
									<c:forEach items="${depList}" var="dept">
										<form:option value="${dept.key}" code="${dept.key}">${dept.value}</form:option>
									</c:forEach>
								</form:select>
							</div>	
							<label class="col-sm-2 control-label required-control" for="categoryId"> <spring:message
									code="account.budget.code.master.functioncode" text="Function"></spring:message>
							</label>
							<!--   categoryId use this for carrying functionId -->
							<div class="col-sm-4">
								<form:select id="categoryId" path=""
									class="form-control chosen-select-no-results mandColorClass" data-rule-required="true">
									<form:option value="" selected="true">
										<spring:message code="advance.management.master.select" text="Select" />
									</form:option>
									<c:forEach items="${functionList}" var="function">
										<form:option value="${function.key}" code="${function.key}">${function.value}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					 </c:if>
						<!-- new function and department list  -->
						
					</c:if>
					<div class="clear"></div>
					<div class="text-center margin-top-10">
						<button type="button" class="btn btn-blue-2"
							onclick="viewReport(this)" title="View Report">
							<spring:message code="account.financial.view.report"
								text="View Report"  />
						</button>
						<button type="button" class="btn btn-warning resetSearch"
							onclick="window.location.href = '${resetPage}'" title="Reset">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

