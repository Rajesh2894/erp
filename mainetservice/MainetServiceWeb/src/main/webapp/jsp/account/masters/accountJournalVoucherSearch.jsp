<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script
	src="js/account/transaction/accountJournalVoucherEntry.js"></script>

<script>
	$(function() {
		$("#fromDate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		$("#toDate").keyup(function(e) {
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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<c:if test="${command.mode ne 'AUTH'}">
				<h2>
					<strong><spring:message code="account.journal.voucher.transaction" text="Voucher Transaction" /></strong>
				</h2>
			</c:if>
			<c:if test="${command.mode eq 'AUTH'}">
				<h2>
					<strong><spring:message code="account.voucher.transaction.authorisation"
							text="Voucher Transaction Authorisation" /></strong>
				</h2>
			</c:if>
			<apptags:helpDoc url="AccountVoucherEntry.html"
				helpDocRefURL="AccountVoucherEntry.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding ">
			<form:form action="AccountVoucherEntry.html" method="post"
				class="form-horizontal">
				<form:hidden path="" value="${command.mode}" id="mode" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group" id="dateDiv">
					<c:set var="now" value="<%=new java.util.Date()%>" />
					<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
						var="formatDate" />
					<label class="col-sm-2 control-label required-control"><spring:message
							code="from.date.label" text="From Date" /></label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="fromDate"
								cssClass="mandColorClass form-control" value="${formatDate}"
								readonly="false" maxlength="10" />
							<label class="input-group-addon mandColorClass" for="fromDate"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="to.date.label" text="To Date" /></label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="toDate"
								cssClass="mandColorClass form-control" value="${formatDate}"
								readonly="false" maxlength="10" />
							<label class="input-group-addon mandColorClass" for="toDate"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>
				</div>
				<div class="form-group">

					<c:if test="${command.mode ne 'AUTH'}">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.voucher.authorisation" text="Authorisation Status" /></label>
						<div class="col-sm-4">
							<select name="authStatus" id="authStatus" class="form-control">
								<option value="0"><spring:message code="account.select.voucher.status"
										text="Select Voucher Status" /></option>
								<option value="Y"><spring:message code=""
										text="Authorised" /></option>
								<option value="N"><spring:message code=""
										text="Unauthorised" /></option>
							</select>
						</div>
					</c:if>

					<c:if test="${command.mode eq 'AUTH'}">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.voucher.authorisation" text="Authorisation Status" /></label>
						<div class="col-sm-4">
							<select name="authStatus" id="authStatus" class="form-control">
								<option value="0"><spring:message code="account.select.voucher.status"
										text="Select Voucher Status" /></option>
								<option value="N"><spring:message code=""
										text="Unauthorised" /></option>
								<option value="Y"><spring:message code=""
										text="Authorised" /></option>
							</select>
						</div>
					</c:if>

					<label class="col-sm-2 control-label"><spring:message
							code="account.amount" text="Amount" /></label>
					<div class="col-sm-4">
						<input name="amount" type="text" class="form-control" id="amount"></input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.voucher.type" text="Voucher Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="VOT" />
						<select name="voucherType" class="form-control" id="voucherType">
							<option value="0"><spring:message code="account.select.voucher.type"
									text="Select Voucher Type" /></option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</option>
							</c:forEach>
						</select>
					</div>



					<input type="radio" name="dateType" value="V" id="voucherDate"
						checked="checked" class="hide" /> <label for="refNo"
						class="col-sm-2 control-label"><spring:message code="account.voucher.transaction.no"
							text="Transaction Ref No." /></label>
					<div class="col-sm-4">
						<input type="text" name="refNo" id="refNo" class="form-control" />
					</div>



				</div>



				<div class="text-center padding-bottom-10">
					<a href="javascript:void(0);" onclick="searchData()"
						class="btn btn-success" id="search"><i class="fa fa-search"></i>&nbsp;<spring:message
							code="account.bankmaster.search" text="Search" /></a> <input type="button"
						class="btn btn-warning" onclick="resetGridForm('${command.mode}')"
						value="<spring:message code="account.bankmaster.reset" text="Reset" />">

					<c:if test="${command.mode ne 'AUTH'}">
						<a href="javascript:void(0);" onclick="addForm()"
							class="btn btn-blue-2" id="btnsearch"><i
							class="fa fa-plus-circle"></i> <spring:message
								code="account.bankmaster.add" text="Add" /></a>
						<button type="button" class="btn btn-primary"
							onclick="exportTemplate()">
							<spring:message code="account.bankmaster.export.import" text="Export/Import" />
						</button>
					</c:if>

				</div>

				<table id="grid"></table>
				<div id="pagered"></div>



			</form:form>
		</div>
	</div>
</div>


