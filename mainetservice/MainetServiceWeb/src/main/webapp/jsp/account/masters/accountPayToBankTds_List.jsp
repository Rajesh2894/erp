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
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>

<script>
	function SearchChequeData() {

		debugger;
		$('.error-div').hide();

		var errorList = [];

		var tdsType = $("#tdsType").val();

		if (tdsType == "" || tdsType == "0") {
			errorList.push(getLocalMessage("account.master.tds.select"));
		}

		if (errorList.length > 0) {

			var errorMsg = '<ul>';
			$
					.each(
							errorList,
							function(index) {
								errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
										+ errorList[index] + '</li>';
							});
			errorMsg += '</ul>';
			$('#errorId').html(errorMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		}

		if (errorList.length == 0) {

			var url = "AccountPayToBankTDS.html?getChequeData";

			var requestData = {
				"tdsType" : tdsType,
			};

			var result = __doAjaxRequest(url, 'POST', requestData, false,
					'json');

			if (result != null && result != "") {
				$("#grid").jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger('reloadGrid');
			} else {
				var errorList = [];

				errorList
						.push(getLocalMessage("No records found for selected criteria"));

				if (errorList.length > 0) {
					var errorMsg = '<ul>';
					$
							.each(
									errorList,
									function(index) {
										errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
												+ errorList[index] + '</li>';
									});
					errorMsg += '</ul>';
					$('#errorId').html(errorMsg);
					$('#errorDivId').show();
					$('html,body').animate({
						scrollTop : 0
					}, 'slow');
				}
				$("#grid").jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger('reloadGrid');
			}
		}
	};

	$(function() {
		$(document)
				.on(
						'click',
						'.createData',
						function() {
							var url = "AccountPayToBankTDS.html?form";
							$
									.ajax({
										url : url,
										success : function(response) {
											$("#content").html(response);
											$("#content").show();
											$('#divHeaderId').show();
										},
										error : function(xhr, ajaxOptions,
												thrownError) {
											var errorList = [];
											errorList
													.push(getLocalMessage("admin.login.internal.server.error"));
											showError(errorList);
										}
									});
						});
	});

	var ptbId = '';
	$(function() {
		$("#grid")
				.jqGrid(
						{
							url : "AccountPayToBankTDS.html?getGridData",
							datatype : "json",
							mtype : "POST",
							colNames : [
									getLocalMessage('accounts.ptb.id'),
									getLocalMessage('account.pay.tds.type'),
									getLocalMessage('account.tenderentrydetails.VendorEntry'),
									getLocalMessage('receipt.register.bankaccountno'),
									getLocalMessage('accounts.master.status'),
									getLocalMessage('bill.action') ],
							colModel : [ {
								name : "ptbId",
								width : 0,
								sortable : true,
								hidden : true,
								searchoptions : {
									"sopt" : [ "bw", "eq" ]
								}
							}, {
								name : "tdsTypeName",
								width : 45,
								sortable : true,
								searchoptions : {
									"sopt" : [ "bw", "eq" ]
								}
							}, {
								name : "vendorCode",
								width : 65,
								sortable : true,
								searchoptions : {
									"sopt" : [ "bw", "eq" ]
								}
							}, {
								name : "ptbBankAcNo",
								width : 45,
								sortable : false,
								searchoptions : {
									"sopt" : [ "eq" ]
								}
							}, {
								name : "statusName",
								width : 45,
								sortable : false,
								searchoptions : {
									"sopt" : [ "eq" ]
								}
							}, {
								name : 'ptbId',
								index : 'ptbId',
								width : 20,
								align : 'center !important',
								formatter : addLink,
								search : false
							}, ],
							pager : "#pagered",
							emptyrecords: getLocalMessage("jggrid.empty.records.text"),
							rowNum : 30,
							rowList : [ 5, 10, 20, 30 ],
							sortname : "ptbId",
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
							caption : getLocalMessage('account.configuration.bank.master.entry.for.tds.master')
						});
		jQuery("#grid").jqGrid('navGrid', '#pagered', {
			edit : false,
			add : false,
			del : false,
			search : true,
			refresh : false
		});
		$("#pagered_left").css("width", "");
	});

	function returnEditUrl(cellValue, options, rowdata, action) {
		ptbId = rowdata.ptbId;
		return "<a href='#'  return false; class='addBankClass' value='"+ptbId+"'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
	}
	function returnViewUrl(cellValue, options, rowdata, action) {

		return "<a href='#'  return false; class='viewBankClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
	}

	function addLink(cellvalue, options, rowdata) {
		return "<a class='btn btn-blue-3 btn-sm viewBankClass' title='View'value='"+rowdata.ptbId+"' id='"+rowdata.ptbId+"' ><i class='fa fa-building-o'></i></a> "
				+ "<a class='btn btn-warning btn-sm addBankClass' title='Edit'value='"+rowdata.ptbId+"' id='"+rowdata.ptbId+"' ><i class='fa fa-pencil'></i></a> ";
	}

	$(function() {
		$(document)
				.on(
						'click',
						'.addBankClass',
						function() {
							var errorList = [];
							var $link = $(this);
							var ptbId = $link.closest('tr').find('td:eq(0)')
									.text();
							var authStatus = $link.closest('tr').find(
									'td:eq(4)').text();
							var url = "AccountPayToBankTDS.html?formForUpdate";
							var requestData = "ptbId=" + ptbId + "&MODE1="
									+ "Edit";
							var returnData = __doAjaxRequest(url, 'post',
									requestData, false);
							if (authStatus != "Inactive") {
								$('.content').html(returnData);
							} else {
								errorList
										.push("It is already Inactive so EDIT is not allowed!");
								if (errorList.length > 0) {
									var errorMsg = '<ul>';
									$
											.each(
													errorList,
													function(index) {
														errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
																+ errorList[index]
																+ '</li>';
													});
									errorMsg += '</ul>';
									$('#errorId').html(errorMsg);
									$('#errorDivId').show();
									$('html,body').animate({
										scrollTop : 0
									}, 'slow');
								}
							}
						});

		$(document).on('click', '.viewBankClass', function() {
			var $link = $(this);
			var ptbId = $link.closest('tr').find('td:eq(0)').text();
			var url = "AccountPayToBankTDS.html?viewMode";
			var requestData = "ptbId=" + ptbId + "&MODE1=" + "View";
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			var divName = '.content';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			return false;
		});

	});
</script>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="content form-div" id="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.pay.tds.entry" text="TDS Master" />
			</h2>
			<apptags:helpDoc url="AccountPayToBankTDS.html"
				helpDocRefURL="AccountPayToBankTDS.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">


			<form:form id="tbAcPayToBank" action=""
				modelAttribute="tbAcPayToBank" class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label requiresd-control"><spring:message
							code="account.pay.tds.type" text="TDS Type" /></label>
					<div class="col-sm-4">
						<form:select id="tdsType" path="ptbTdsType"
							class="form-control chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="account.budget.code.master.selecttdstype" text="Select TDS Type" />
							</form:option>
							<c:forEach items="${tdsTypeLookUp}" varStatus="status"
								var="levelParent">
								<form:option code="${levelParent.lookUpCode}"
									value="${levelParent.lookUpId}">${levelParent.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success"
						onclick="SearchChequeData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="AccountPayToBankTDS.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="reset.msg" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData"
						id="addButton">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>

			</form:form>
		</div>
	</div>
</div>