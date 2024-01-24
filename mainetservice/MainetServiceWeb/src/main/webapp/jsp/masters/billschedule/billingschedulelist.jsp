<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<!-- <link rel="stylesheet" type="text/css"
	href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script> -->

<script type="text/javascript">
	$(function() {
		$("#grid")
				.jqGrid(
						{
							url : "BillSchedule.html?getMasGridData",
							datatype : "json",
							mtype : "GET",
							colNames : [
									getLocalMessage("master.billschedule.MeterType"),
									getLocalMessage("master.billschedule.FromMonth"),
									getLocalMessage("master.billschedule.ToMonth"),
									getLocalMessage("master.billschedule.BillingFrequency"),
									getLocalMessage("master.financialYr"),
									getLocalMessage("view.msg") ],
							colModel : [ {
								name : "meterType",
								sortable : true,
								width : 100
							}, {
								name : "fromMonth",
								sortable : false,
								width : 60
							}, {
								name : "toMonth",
								sortable : false,
								width : 60
							}, {
								name : "billingFreq",
								sortable : false,
								width : 60
							}, {
								name : "finYr",
								sortable : false,
								width : 60
							}, {
								name : 'cnsId',
								index : 'cnsId',
								align : 'center',
								width : 40,
								sortable : false,
								formatter : viewScrutinyMst
							} ],
							pager : "#pagered",
							rowNum : 30,
							rowList : [ 5, 10, 20, 30 ],
							sortname : "cpmId",
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
							caption : "Billing List"
						});
	});

	function editScrutinyMst(cellValue, options, rowdata, action) {
		return "<a href='#'  return false; class='editClass' value='"+rowdata.cnsId+"' ><img src='css/images/edit.png' width='20px' alt='Edit Billing Schedule' title='Edit Billing Schedule' /></a>";
	}
	function viewScrutinyMst(cellValue, options, rowdata, action) {
		return "<a href='#'  return false; class='viewClass' value='"+rowdata.cnsId+"'><img src='css/images/grid/view-icon.png' width='20px' alt='View Billing Schedule' title='iew Billing Schedule' /></a>";
	}

	$(function() {
		$(document)
				.on(
						'click',
						'.editClass',
						function() {
							var url = "BillSchedule.html?formForUpdate";
							var cnsId = $(this).attr('value');
							var returnData = {
								"cnsId" : cnsId,
								"mode" : 'edit'
							};

							$
									.ajax({
										url : url,
										datatype : "json",
										mtype : "POST",
										data : returnData,
										success : function(response) {
											var divName = '.form-div';
											$("#comparamMasDiv").html(response);
											$("#comparamMasDiv").show();
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

	$(function() {
		$(document)
				.on(
						'click',
						'.viewClass',
						function() {
							var url = "BillSchedule.html?formForUpdate";
							var cnsId = $(this).attr('value');
							var returnData = {
								"cnsId" : cnsId,
								"mode" : 'view'
							};

							$
									.ajax({
										url : url,
										datatype : "json",
										mtype : "POST",
										data : returnData,
										success : function(response) {
											var divName = '.form-div';
											$("#comparamMasDiv").html(response);
											$("#comparamMasDiv").show();
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

	function openBillScheduleForm() {
		var url = "BillSchedule.html?form";
		$
				.ajax({
					url : url,
					success : function(response) {

						var divName = '.form-div';
						$("#content").html(response);
						$("#content").show();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	}
	function closeErrBox() {
		$('.warning-div').addClass('hide');
	}

	function searchBillingData() {
		var cnsMn = $("#cnsMn").val();
		var cnsYearid = $("#cnsYearid").val();
		var dependsOnType = $("#dependsOnType").val();
		var billFreq = $("#billFreq").val();
		var cnsCcgid1 = 0;
		var fromMonth = $("#fromMonth").val();

		var wwzLength = $("#wwzLength").val();
		var codIdWwz1 = -1;
		var codIdWwz2 = -1;
		var codIdWwz3 = -1;
		var codIdWwz4 = -1;
		var codIdWwz5 = -1;
		if (dependsOnType == 1) {
			for (var counter = 1; counter <= wwzLength; counter++) {
				if (counter == 1) {
					codIdWwz1 = $("#codIdWwz" + counter).val();
				}
				if (counter == 2) {
					codIdWwz2 = $("#codIdWwz" + counter).val();
				}
				if (counter == 3) {
					codIdWwz3 = $("#codIdWwz" + counter).val();
				}
				if (counter == 4) {
					codIdWwz4 = $("#codIdWwz" + counter).val();
				}
				if (counter == 5) {
					codIdWwz5 = $("#codIdWwz" + counter).val();
				}
			}
		} else if (dependsOnType == 2) {
			var cnsCcgid1 = $("#cnsCcgid1").val();
		}

		var url = "BillSchedule.html?searchBillingData";
		var returnData = {
			"cnsMn" : cnsMn,
			"cnsYearid" : cnsYearid,
			"dependsOnType" : dependsOnType,
			"billFreq" : billFreq,
			"codIdWwz1" : codIdWwz1,
			"codIdWwz2" : codIdWwz2,
			"codIdWwz3" : codIdWwz3,
			"codIdWwz4" : codIdWwz4,
			"codIdWwz5" : codIdWwz5,
			"cnsCcgid1" : cnsCcgid1,
			"fromMonth" : fromMonth
		}

		$
				.ajax({
					url : url,
					datatype : "json",
					mtype : "POST",
					data : returnData,
					success : function(response) {
						$("#grid").jqGrid('setGridParam', {
							datatype : 'json'
						}).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});

	}
	function toggleDependsOn(obj) {
		var dependsOnVal = $(obj).find('option:selected').attr('code');
		$("#taxValueType").val("");

		var wwzLength = $("#wwzLength").val();
		for (var counter = 1; counter <= wwzLength; counter++) {
			$("#codIdWwz" + counter).val("");
		}

		if (dependsOnVal == "WZ") {
			$("#consumerTypeDiv").addClass('hide');
			$("#wardZoneDiv").removeClass('hide');
		} else if (dependsOnVal == "CN") {
			$("#consumerTypeDiv").removeClass('hide');
			$("#wardZoneDiv").addClass('hide');
		} else {
			$("#consumerTypeDiv").addClass('hide');
			$("#wardZoneDiv").addClass('hide');
		}
	}

	function setToMonthData(month) {
		debugger;
		var billFreq = $("#billFreq").find('option:selected').attr('code') - 1;
		//var fromMnth = $(obj).val();
		var monthVal = parseInt(month) + parseInt(billFreq);

		if (monthVal > 12) {
			monthVal = monthVal - 12;
			$("#toMonth").val(monthVal);
			$("#cnsToDateId").val(monthVal);
		} else {
			$("#toMonth").val(parseInt(month) + parseInt(billFreq));
			$("#cnsToDateId").val(parseInt(month) + parseInt(billFreq));
		}
	}

	function bifurcateBillMonths(obj) {
		debugger;
		var requestData = "freqId="
				+ $(obj).find('option:selected').attr('code');
		var url = "BillSchedule.html?bifurcateBillMonth";

		$
				.ajax({
					url : url,
					data : requestData,
					type : 'POST',
					success : function(response) {

						$("#fromMonth").html('');
						/* $("#fromMonth").append(
								$("<option></option>").attr("value", "").attr(
										"code", "").text('Select')); */

						if (response != "") {
							var month;
							$.each(response, function(index, value) {
								if (value.monthName == 'APR') {
									month = value.monthId;
									$("#fromMonth").append(
											$("<option></option>").attr(
													"value", value.monthId)
													.attr("code",
															value.tempMonthId)
													.text(value.monthName));
								}

							});
							setToMonthData(month)
							/* var billFreq = $("#billFreq").find('option:selected').attr('code') - 1;
							//var fromMnth = $(obj).val();
							var monthVal = parseInt(month) + parseInt(billFreq);

							if (monthVal > 12) {
							monthVal = monthVal - 12;
							$("#toMonth").val(monthVal);
							$("#cnsToDateId").val(monthVal);
							} else {
							$("#toMonth").val(parseInt(month) + parseInt(billFreq));
							$("#cnsToDateId").val(parseInt(month) + parseInt(billFreq));
							} */
						}

					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
		$("#toMonth").val("");

		//	function setToMonthData(obj) {
		/* var schFreq = $("#billFreq").val();
		var finYear = $("#financialYear").val()
		if (schFreq != 0) {
			//var data = {"schFreq" : schFreq};	
			var URL = 'BillSchedule.html?createSchedule';
			var formName = findClosestElementId(obj, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
			$("#reloadDiv").html(returnData);
			if (finYear != null) {
				$("select[id=financialYear]").val(finYear);
			}
		} else {
			$("#SchedulewiseDueDate").html("");
		} */

		//	}
	}
	function getChildPrefixData(nextLevelUrl, obj) {
		var tempIds = $(obj).attr('id');
		var idIndx = tempIds.charAt(tempIds.length - 1);
		var idName = tempIds.substring(0, tempIds.length - 1);
		var nextId = parseInt(idIndx) + 1;

		var selectedText = $(obj).find('option:selected').text();

		$("#codUsageName" + idIndx).val(selectedText);

		var cpdId = $(obj).val();
		var requestData = "cpdId=" + cpdId;

		$
				.ajax({
					url : nextLevelUrl,
					data : requestData,
					type : 'POST',
					success : function(response) {

						$("#" + idName + nextId).html('');
						$("#" + idName + nextId).append(
								$("<option></option>").attr("value", "").attr(
										"code", "").text(
										getLocalMessage('select')));

						if (response != "") {
							$.each(response, function(index, value) {
								$("#" + idName + nextId).append(
										$("<option></option>").attr("value",
												value.lookUpId).attr("code",
												value.lookUpCode).text(
												value.lookUpDesc));
							});
						}
						$("#" + idName + nextId).append(
								$("<option></option>").attr("value", '-1')
										.text('All'));

					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	}

	function resetBillForm(obj) {
		$("#billScheduleForm").submit();
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div id="reloadDiv">
	<div id="content" class="content">
		<div class="widget" id="comparamMasDiv">
			<div class="widget-header">
				<h2>
					<spring:message code="common.master.billing" text="Billing" />
					<strong><spring:message
							code="common.master.billing.schedule" text="Schedule" /></strong>
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="BillSchedule.html" class="form-horizontal"
					id="billScheduleForm">
					<input type="hidden" value="${fn:length(wwzTempPrefixList)}"
						id="wwzLength" />

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="common.master.billing.meter.type" text="Meter Type" /> </label>
						<div class="col-sm-4">
							<select id="cnsMn" class="form-control" name="cnsMn">
								<option value=""><spring:message code='master.selectDropDwn'/></option>
								<c:forEach items="${wnmPrefixList}" var="wnmPrefixData">
									<option value="${wnmPrefixData.lookUpCode }"
										code="${wnmPrefixData.lookUpCode }">${wnmPrefixData.lookUpDesc }</option>
								</c:forEach>
							</select>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="common.master.billing.financial" text="Financial Year" />
						</label>
						<div class="col-sm-4">
							<select id="cnsYearid" class="form-control" name="cnsYearid">
								<option value=""><spring:message code='master.selectDropDwn'/></option>
								<c:forEach items="${finYearData}" var="finYearData">
									<option value="${finYearData.key }">${finYearData.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="common.master.billing.depends" text="Depends On" /></label>
						<div class="col-sm-4">
							<select id="dependsOnType" class="form-control"
								name="dependsOnType" onchange="toggleDependsOn(this)">
								<option value=""><spring:message code='master.selectDropDwn'/></option>
								<option value="1" code="WZ">Ward/Zone</option>
								<option value="2" code="CN">Consumer type</option>
							</select>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="common.master.billing.frequency" text="Billing Frequency" /></label>
						<div class="col-sm-4">
							<select id="billFreq" class="form-control" name=""
								onchange="bifurcateBillMonths(this)">
								<option value=""><spring:message code='master.selectDropDwn'/></option>
								<c:forEach items="${bscPrefixList}" var="bscPrefixData">
									<option value="${bscPrefixData.lookUpId }"
										code="${bscPrefixData.lookUpCode }">${bscPrefixData.lookUpDesc }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group hide" id="wardZoneDiv">
						<c:forEach items="${wwzTempPrefixList}" var="lookUp"
							varStatus="status">
							<c:set var="fieldPath" value="${codIdWwz}${status.count}" />
							<c:set var="forId" value="${fieldPath}" />
							<label class="col-sm-2 control-label">${lookUp.lookUpDesc}</label>
							<div class="col-sm-4">
								<c:choose>
									<c:when test="${status.count == 1}">
										<select name="${fieldPath}" class="form-control"
											id="${fieldPath}"
											onchange="getChildPrefixData('${getChildPrefixData}',this)">
											<option value="">Select</option>
											<c:forEach items="${wwzPrefixList}" var="prefixData">
												<option value="${prefixData.lookUpId }"
													code="${prefixData.lookUpCode }">${prefixData.lookUpDesc }</option>
											</c:forEach>
											<option value="-1">All</option>
										</select>
									</c:when>
									<c:otherwise>
										<select name="${fieldPath}" class="form-control"
											id="${fieldPath}"
											onchange="getChildPrefixData('${getChildPrefixData}',this)">
											<option value="">Select</option>
											<option value="-1">All</option>
										</select>
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
					</div>

					<div class="form-group hide" id="consumerTypeDiv">
						<label class="col-sm-2 control-label"><spring:message
								code="common.master.billing.consumer" text="Consumer Type" /></label>
						<div class="col-sm-4">
							<select id="cnsCcgid1" class="form-control" name="cnsCcgid1">
								<option value=""><spring:message code='master.selectDropDwn'/></option>
								<c:forEach items="${ccgPrefixList}" var="ccgPrefixData">
									<option value="${ccgPrefixData.lookUpId }"
										code="${ccgPrefixData.lookUpCode }">${ccgPrefixData.lookUpDesc }</option>
								</c:forEach>
								
							</select>
						</div>
					</div>

					<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="common.master.billing.from.month" text="From Month"/></label>
					<div class="col-sm-4">
						<select id="fromMonth" class="form-control" name="cnsFromDate" onchange="setToMonthData(this)" readonly="true">
							<option value=""><spring:message code='master.selectDropDwn'/></option>
							<c:forEach items="${billingMonthList}" var="monPrefixData">
								<option value="${monPrefixData.monthId }" code="${monPrefixData.tempMonthId }">${monPrefixData.monthName }</option>
							</c:forEach>
						</select>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message code="common.master.billing.to.month" text="To Month"/></label>
					<div class="col-sm-4">
						<select id="toMonth" class="form-control" name="cnsToDateTemp" disabled="disabled">
							<option value=""><spring:message code='master.selectDropDwn'/></option>
							<c:forEach items="${billingMonthTempList}" var="monPrefixData">
								<option value="${monPrefixData.monthId }" code="${monPrefixData.tempMonthId }">${monPrefixData.monthName }</option>
							</c:forEach>
						</select>
					</div>
				</div>


					<div class="text-center">
						<input type="BUTTON" id="search" value="Search Data"
							class="btn btn-blue-2" onclick="searchBillingData()" /> <input
							type="button" id="reset" value="Reset" class="btn btn-warning"
							onclick="resetBillForm(this)" /> <input type="button"
							value="Add" class="btn btn-success"
							onclick="openBillScheduleForm()">
					</div>

					<div class="widget-content padding-top-10">

						<div class="table-responsive">
							<table id="grid"></table>
							<div id="pagered"></div>
						</div>

					</div>


				</form>
			</div>
		</div>
	</div>
</div>