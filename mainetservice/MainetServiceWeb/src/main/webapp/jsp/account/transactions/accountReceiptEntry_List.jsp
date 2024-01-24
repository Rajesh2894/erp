<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
	function createData() {
		$("#create_btn").attr("disabled", true);
		var url = "AccountReceiptEntry.html?form";
		$
				.ajax({
					url : url,
					datatype : "html",
					type : 'POST',
					success : function(response) {
						var divName = '.content';
						$(divName).html(response);
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});

	}
	
	function searchReceiptData() {
     
		var errorsList = [];
		errorsList = validateReceiptSearchForm(errorsList);
		if (errorsList.length > 0) {

			var errMsg = '<ul>';
			$.each(errorsList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorsList[index] + '</li>';
			});
			errMsg += '</ul>';
			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		} else {

			var rmRcptno = $("#rmRcptno").val();
			var rdamount = $("#rdamount").val();
			var rm_Receivedfrom = $("#rm_Receivedfrom").val();
			var rmDate = $("#rmDatetemp").val();

			var url = "AccountReceiptEntry.html?getjqGridsearch";
			var requestData = {
				"rmAmount" : rdamount,
				"rmRcptno" : rmRcptno,
				"rm_Receivedfrom" : rm_Receivedfrom,
				"rmDate" : rmDate
			};

			$
					.ajax({
						url : url,
						data : requestData,
						datatype : "json",
						type : 'POST',

						success : function(response) {

							if (response != 'N' && response!='R') {
								$("#grid").jqGrid('setGridParam', {
									datatype : 'json'
								}).trigger('reloadGrid');
								$('#errorDivId').hide();
							} else {
								var errorList = [];
								if(response =='N'){
								errorList
										.push(getLocalMessage("account.norecord.criteria"));
								}
								if(response =='R'){
									errorList
									.push("Receipt is already reversed !");
								}
								if (errorList.length > 0) {

									var errMsg = '<ul>';
									$
											.each(
													errorList,
													function(index) {
														errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
																+ errorList[index]
																+ '</li>';
													});
									errMsg += '</ul>';
									$('#errorId').html(errMsg);
									$('#errorDivId').show();
									$('html,body').animate({
										scrollTop : 0
									}, 'slow');
								}
								$("#grid").jqGrid('setGridParam', {
									datatype : 'json'
								}).trigger('reloadGrid');
							}
						},
						error : function(xhr, ajaxOptions, thrownError) {
							var errorList = [];
							errorList
									.push(getLocalMessage("admin.login.internal.server.error"));
							showError(errorList);
						}
					});

		}
	} 

	function validateReceiptSearchForm(errorList) {
		var receiptNo = $("#rmRcptno").val();
		var receiptDate = $("#rmDatetemp").val();
		var receiptAmount = $("#rdamount").val();
		var payeeName = $("#rm_Receivedfrom").val();
		if (receiptNo == "" && receiptDate == "" && receiptAmount == ""
				&& payeeName == "" && payeeName == "") {
			errorList
					.push(getLocalMessage('Please select at least one criteria for search'));
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}

		if (receiptDate != null) {
			errorList = validatedate(errorList, 'rmDatetemp');
		}
		return errorList;
	}

	var ptbId = '';
	$(function() {
		$("#grid").jqGrid(
				{
					url : "AccountReceiptEntry.html?getGridData",
					datatype : "json",
					mtype : "POST",
					colNames : [ getLocalMessage('accounts.rmreceipt.id'),
							getLocalMessage('accounts.receipt.receipt_no'),
							getLocalMessage('accounts.receipt.receipt_date'),
							getLocalMessage('accounts.receipt.payeename'),
							getLocalMessage('accounts.receipt.receiptAmount'),
							getLocalMessage('bill.action') ],
					colModel : [ {
						name : "rmRcptid",
						sortable : true,
						hidden : true,

					}, {
						name : "rmRcptno",
						align : 'center',
						sortable : true,

					}, {
						name : "rmDate",
						align : 'center',
						sortable : true,

						formatter : dateTemplate
					}, {
						name : "rmReceivedfrom",
						align : 'left',
						sortable : true,

					}, {
						name : "formattedCurrency",
						sortable : true,
						classes : 'text-right',
						editable : true,
						sortable : true,

					}, {
						name : 'rmRcptid',
						index : 'rmRcptid',
						align : 'center !important',
						formatter : addLink,
						search : false
					} ],
					pager : "#pagered",
					emptyrecords: getLocalMessage("jggrid.empty.records.text"),
					rowNum : 30,
					rowList : [ 5, 10, 20, 30 ],
					sortname : "rmRcptno",
					sortorder : "desc",
					height : 'auto',
					viewrecords : true,
					gridview : true,
					loadonce : true,
					jsonReader : {
						root : "rows",
						page : "page",
						total : "total",
						records : "records",
						repeatitems : false,
					},
					autoencode : true,
					caption : getLocalMessage("accounts.receipt.receipt_details")
				});
		jQuery("#grid").jqGrid('navGrid', '#pagered', {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : false
		});
		$("#pagered_left").css("width", "");
	});

	function addLink(cellvalue, options, rowdata) {
		return "<a class='btn btn-blue-3 btn-sm viewReceiptClass' title='view' value='"+rowdata.rmRcptid+"' rmRcptid='"+rowdata.rmRcptid+"' ><i class='fa fa-building-o'></i></a> "
				+ " <a class='btn btn-blue-3 btn-sm printClass' title='Print' value='"+rowdata.rmRcptid+"' rmRcptid='"+rowdata.rmRcptid+"'><i class='fa fa-print'></i></a>";

	}

	$(function() {
		$(document).on(
				'click',
				'.viewReceiptClass',
				function() {
					var $link = $(this);
					var ptbId = $link.closest('tr').find('td:eq(0)').text();
					var url = "AccountReceiptEntry.html?viewMode";
					var requestData = "rmRcptid=" + ptbId + "&MODE1=" + "View"
							+ "&postFlag=" + "R";
					var returnData = __doAjaxRequest(url, 'post', requestData,
							false);
					var divName = '.content';
					$(divName).removeClass('ajaxloader');
					$(divName).html(returnData);
					return false;
				});

	});

	$(function() {
		$(document).on(
				'click',
				'.printClass',
				function() {
					var $link = $(this);
					var ptbId = $link.closest('tr').find('td:eq(0)').text();

					var url = "AccountReceiptEntry.html?formForPrint";

					var requestData = "rmRcptid=" + ptbId + "&MODE1=" + "Print"
							+ "&postFlag=" + "R";
					var returnData = __doAjaxRequest(url, 'post', requestData,
							false);
					var divName = '.content';
					$(divName).removeClass('ajaxloader');
					$(divName).html(returnData);
					return false;
				});

	});

	$(document).ready(
			function() {
				var response = __doAjaxRequest(
						'AccountReceiptEntry.html?SLIDate', 'GET', {}, false,
						'json');
				var disableBeforeDate = new Date(response[0], response[1],
						response[2]);
				var date = new Date();
				var today = new Date(date.getFullYear(), date.getMonth(), date
						.getDate());
				$("#rmDatetemp").datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					minDate : disableBeforeDate,
					maxDate : today,
					changeYear : true
				});
				$("#rmDatetemp").datepicker('setDate', new Date());

				$("#rmDatetemp").keyup(function(e) {
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
<div class="totContent">
	<div class="content form-div" id="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<strong><spring:message code="account.field.acc"
							text="Account" /></strong>
					<spring:message code="bank.reconciliation.receipts" text="Receipts" />
				</h2>
				<apptags:helpDoc url="AccountReceiptEntry.html"
					helpDocRefURL="AccountReceiptEntry.html"></apptags:helpDoc>
			</div>
	
			<div class="widget-content padding">
				<form:form action="" modelAttribute="tbServiceReceiptMasBean"
					class="form-horizontal">

					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeOutErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span id="errorId"></span>
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
					</div>

					<div class="form-group">
						<label class="control-label  col-sm-2"> <spring:message
								code="accounts.receipt.receipt_no"></spring:message>
						</label>
						<div class="col-sm-4">
							<input id="rmRcptno" class="form-control hasNumber"
								maxLength="12" />
						</div>
						<label class="control-label  col-sm-2  "> <spring:message
								code="accounts.receipt.receipt_date"></spring:message>
						</label>

						<div class="col-sm-4">
							<div class="input-group">
								<input name="" Class="datepicker form-control" id="rmDatetemp"
									maxLength="10" /> <label for="rmDatetemp"
									class="input-group-addon"> <i class="fa fa-calendar"></i><span
									class="hide"><spring:message
											code="account.additional.supplemental.auth.icon" text="icon" /></span><input
									type="hidden" id="rmDatetemp"></label>
							</div>
						</div>

					</div>

					<div class="form-group">
						<label class="control-label  col-sm-2"> <spring:message
								code="accounts.receipt.receipt_amount" text="Receipt Amount" />
						</label>
						<div class="col-sm-4">
							<input id="rdamount" class="form-control"
								onkeypress="return hasAmount(event, this, 12, 2)" />
						</div>
						<label class="control-label  col-sm-2"> <spring:message
								code="accounts.receipt.name"></spring:message>
						</label>
						<div class="col-sm-4">
							<form:select id="rm_Receivedfrom" path="rmReceivedfrom"
								class="form-control chosen-select-no-results">
								<form:option value="">
									<spring:message code="account.common.select" />
								</form:option>
								<c:forEach items="${payeeList}" var="payee">
									<form:option value="${payee}">${payee}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="text-center padding-bottom-10">

						<button type="button" class="btn btn-success" id="searchReceipt"
							onclick="searchReceiptData()">
							<i class="fa fa-search"></i>
							<spring:message code="account.bankmaster.search" text="Search" />

						</button>
						<button type="button" class="btn btn-warning resetSearch"
							onclick="window.location.href = 'AccountReceiptEntry.html'">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
						<button type="button" class="btn btn-blue-2" id="createReceip"
							onClick="createData();">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</div>
					<div class="text-right padding-bottom-10"></div>
					<table id="grid"></table>
					<div id="pagered"></div>
				</form:form>
			</div>
		</div>
	</div>
</div>