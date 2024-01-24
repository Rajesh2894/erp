<!-- Start JSP Necessary Tags -->
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
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/excel-export.js"></script>
<script src="js/account/assetsAndLiabilitiesScheduleReport.js"></script>
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
		$('#transactionDateId').change(function() {
			//alert();
			var check = $(this).val();
			if (check == '') {
				$(this).parent().switchClass("has-success", "has-error");
			} else {
				$(this).parent().switchClass("has-error", "has-success");
			}
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
	});
	</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>BalanceSheet Nmam Report</h2>
		</div>
		<apptags:helpDoc url="incomeAndExpenditureSheduleReport.html"
			helpDocRefURL="incomeAndExpenditureSheduleReport.html"></apptags:helpDoc>
		<div class="widget-content padding">
			<form:form action="" class="form-horizontal">
				<div class="form-group">
					<label for="date"
										class="col-sm-2 control-label col-sm-2required-control"><spring:message
											code="account.financial.as.on.date" text="As on Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="transactionDateId"
								cssClass="mandColorClass form-control datepicker"
								data-rule-required="true" maxlength="10" />
							<label class="input-group-addon mandColorClass"
								for="transactionDateId"><i class="fa fa-calendar"></i></label>
						</div>
					</div>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-blue-2"
						onClick="assetsAndLiabilitiesReport('liabilitiesAndAssetsScheduleReport.html','assetsAndliabilitiesreport')"
						title="View Report">
						<spring:message code="" text="View Report"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onClick="incomeExpendituterReset(this)" title="Reset">
						<spring:message code="" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>