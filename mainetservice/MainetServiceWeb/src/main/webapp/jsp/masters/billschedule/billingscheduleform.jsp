<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" /> -->
<!-- <link rel="stylesheet" type="text/css"
	href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script> -->
<script type="text/javascript">
	function getChildPrefixDataView(nextLevelUrl, id) {
		var tempIds = id;
		var idIndx = tempIds.charAt(tempIds.length - 1);
		var idName = tempIds.substring(0, tempIds.length - 1);
		var nextId = parseInt(idIndx) + 1;

		var cpdId = $(id).val();
		var requestData = "cpdId=" + cpdId;

		$
				.ajax({
					url : nextLevelUrl,
					data : requestData,
					type : 'POST',
					async : false,
					success : function(response) {

						$(idName + nextId).html('');
						$(idName + nextId).append(
								$("<option></option>").attr("value", "").attr(
										"code", "").text(
										getLocalMessage('select')));

						if (response != "") {
							$.each(response, function(index, value) {
								$(idName + nextId).append(
										$("<option></option>").attr("value",
												value.lookUpId).attr("code",
												value.lookUpCode).text(
												value.lookUpDesc));
							});
						}
						$(idName + nextId).append(
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

	function saveBillSchForm(obj) {

		var errorList = [];

		var cnsMn = $("#cnsMn").val();
		var cnsYearid = $("#cnsYearid").val();
		var dependsOnType = $("#dependsOnType").val();
		var billFreq = $("#billFreq").val();
		var fromMonth = $("#fromMonth").val();

		if (cnsMn == '') {
			errorList.push("Please Select Meter Type");
		}
		if (cnsYearid == '') {
			errorList.push("Please Select Financial Year");
		}
		if (dependsOnType == '') {
			errorList.push("Please Select Depends On");
		} else {
			//dependsOnType
			if (dependsOnType == 1) {
				var wwzLength = $("#wwzLength").val();
				var tempCounter = 0;
				var codIdWwz1 = '';
				var codIdWwz2 = '';
				var codIdWwz3 = '';
				var codIdWwz4 = '';
				var codIdWwz5 = '';
				for (var counter = 1; counter <= wwzLength; counter++) {
					if (counter == 1) {
						codIdWwz1 = $("#codIdWwz" + counter).val();
						if (codIdWwz1 == '') {
							errorList.push("Please Select Zone");
						}
					} else if (counter == 2) {
						codIdWwz2 = $("#codIdWwz" + counter).val();
						if (codIdWwz2 == '') {
							errorList.push("Please Select Ward");
						}
					}
				}
			} else {
				var cnsCcgid1 = $("#cnsCcgid1").val();
				if (cnsCcgid1 == '') {
					errorList.push("Please Select Consumer Type");
				}
			}
		}
		if (billFreq == '') {
			errorList.push("Please Select Billing Frequency");
		}
		if (fromMonth == '') {
			errorList.push("Please Select From Month");
		}

		if (errorList.length > 0) {
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});
			errMsg += '</ul>';
			$(".warning-div").html(errMsg);
			$(".warning-div").removeClass('hide');
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');

			errorList = [];
			return false;
		} else {
			if ($("#formModeId").val() == 'create') {
				var validateUrl = "BillSchedule.html?validateBillingData";
				var formName = findClosestElementId(obj, 'form');
				var theForm = '#' + formName;
				var requestData = __serializeForm(theForm);
				var returnValidateData = __doAjaxRequestForSave(validateUrl,
						'post', requestData, false, '', obj);

				if (returnValidateData != '0') {
					var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
					errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Data already present</li></ul>';
					$(".warning-div").html(errMsg);
					$(".warning-div").removeClass('hide');
					$('html,body').animate({
						scrollTop : 0
					}, 'slow');
					$("#submitForm").prop('disabled', false);
					return false;
				} else {
					var formName = findClosestElementId(obj, 'form');
					var theForm = '#' + formName;

					var url = $(theForm).attr('action');
					var returnData = __doAjaxRequestForSave(url, 'post',
							requestData, false, '', obj);

					if ($.isPlainObject(returnData)) {
						showSubmitConfirmBox();
					} else {
						$("#billSchDiv").html(returnData);
						$("#billSchDiv").show();
						$(".warning-div").removeClass("hide");
						return false;
					}
				}
			} else {
				var formName = findClosestElementId(obj, 'form');
				var theForm = '#' + formName;
				var requestData = __serializeForm(theForm);
				var url = $(theForm).attr('action');
				var returnData = __doAjaxRequestForSave(url, 'post',
						requestData, false, '', obj);

				if ($.isPlainObject(returnData)) {
					showSubmitConfirmBox();
				} else {
					$("#billSchDiv").html(returnData);
					$("#billSchDiv").show();
					$(".warning-div").removeClass("hide");
					return false;
				}
			}
		}
		return false;
	}

	function showSubmitConfirmBox() {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Proceed';

		message += '<p>Form Submitted Successfully</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'css_btn \'    '
				+ ' onclick="proceed()"/>' + '</p>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}

	function proceed() {
		window.location.href = 'BillSchedule.html';
	}

	function resetSchedule(ele) {
		$("#consumerTypeDiv").addClass('hide');
		$("#wardZoneDiv").addClass('hide');
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
		if ($(obj).find('option:selected').attr('value') != "") {
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
								$
										.each(
												response,
												function(index, value) {
													if (value.monthName == 'APR') {
														month = value.monthId;
														$("#fromMonth")
																.append(
																		$(
																				"<option></option>")
																				.attr(
																						"value",
																						value.monthId)
																				.attr(
																						"selected",
																						"selected")
																				.attr(
																						"code",
																						value.tempMonthId)
																				.text(
																						value.monthName));
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
			var schFreq = $("#billFreq").val();
			var finYear = $("#financialYear").val()
			if (schFreq != 0) {
				//var data = {"schFreq" : schFreq};	
				var URL = 'BillSchedule.html?createSchedule';
				var formName = findClosestElementId(obj, 'form');
				var theForm = '#' + formName;
				var requestData = __serializeForm(theForm);
				var returnData = __doAjaxRequest(URL, 'POST', requestData,
						false);
				$("#reloadDiv").html(returnData);
				if (finYear != null) {
					$("select[id=financialYear]").val(finYear);
				}
			} else {
				$("#SchedulewiseDueDate").html("");
			}
		}

	}
</script>
<div id="reloadDiv">
	<div class="widget" id="billSchDiv">
		<div class="widget-header">
			<h2>
				<spring:message code="common.master.billing" text="Billing" />
				<strong><spring:message
						code="common.master.billing.schedule" text="Schedule" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<c:url value="${saveAction}" var="url_form_submit" />
			<form:form method="post" action="${url_form_submit}"
				class="form-horizontal" name="billScheduleForm"
				id="billScheduleForm" commandName="tbWtBillSchedule">
				<div class="warning-div alert alert-danger alert-dismissible hide"
					id="errorDivScrutiny">
					<button type="button" class="close" aria-label="Close"
						onclick="closeErrBox()">
						<span aria-hidden="true">&times;</span>
					</button>

					<ul>
						<li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors
								path="*" /></li>
					</ul>
					<script>
						$(".warning-div ul")
								.each(
										function() {
											var lines = $(this).html().split(
													"<br>");
											$(this)
													.html(
															'<li>'
																	+ lines
																			.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;")
																	+ '</li>');
										});
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');
					</script>
				</div>
				<input type="hidden" id="formModeId" value="${mode}" />
				<c:if test="${mode != 'create'}">
					<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
					<form:hidden path="cnsId" id="cnsId" />
					<form:hidden path="cnsMn" id="cnsMn" />
					<form:hidden path="cnsYearid" id="cnsYearid" />
					<form:hidden path="dependsOnType" id="dependsOnType" />
					<form:hidden path="codIdWwz1" id="wwz1" />
					<form:hidden path="codIdWwz2" id="wwz2" />
					<form:hidden path="codIdWwz3" id="wwz3" />
					<form:hidden path="codIdWwz4" id="wwz4" />
					<form:hidden path="codIdWwz5" id="wwz5" />

				</c:if>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.meter.type" text="Meter Type :" /></label>
					<div class="col-sm-4">
						<form:select id="cnsMn" class="form-control" path="cnsMn"
							disabled="${mode != 'create' ? true : false}">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${wnmPrefixList}" var="wnmPrefixData">
								<form:option value="${wnmPrefixData.lookUpCode }"
									code="${wnmPrefixData.lookUpCode }">${wnmPrefixData.lookUpDesc }</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.financial" text="Financial Year :" /></label>
					<div class="col-sm-4">
						<form:select id="cnsYearid" class="form-control" path="cnsYearid"
							disabled="${mode != 'create' ? true : false}">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${finYearData}" var="finYearData">
								<form:option value="${finYearData.key }">${finYearData.value }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.depends" text="Depends On :" /></label>
					<div class="col-sm-4">
						<form:select id="dependsOnType" class="form-control"
							path="dependsOnType" onchange="toggleDependsOn(this)"
							disabled="${mode != 'create' ? true : false}">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="1" code="WZ">
								<spring:message code="common.master.billing.ward.zone"
									text="Ward/Zone" />
							</form:option>
							<form:option value="2" code="CN">
								<spring:message code="common.master.billing.consumer.type"
									text="Consumer type" />
							</form:option>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.frequency" text="Billing Frequency :" /></label>
					<div class="col-sm-4">
						<form:select id="billFreq" class="form-control" path="cnsCpdid"
							onchange="bifurcateBillMonths(this)">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${bscPrefixList}" var="bscPrefixData">
								<form:option value="${bscPrefixData.lookUpId }"
									code="${bscPrefixData.lookUpCode }">${bscPrefixData.lookUpDesc }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<input type="hidden" value="${fn:length(wwzTempPrefixList)}"
					id="wwzLength" />
				<div
					class="form-group ${tbWtBillSchedule.dependsOnType eq 1 ? '':'hide'}"
					id="wardZoneDiv">
					<c:forEach items="${wwzTempPrefixList}" var="lookUp"
						varStatus="status">
						<c:set var="fieldPath" value="${codIdWwz}${status.count}" />
						<c:set var="forId" value="${fieldPath}" />
						<label class="col-sm-2 control-label required-control">${lookUp.lookUpDesc}:</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${status.count eq 1}">
									<form:select path="${fieldPath}" class="form-control"
										id="${fieldPath}"
										onchange="getChildPrefixData('${getChildPrefixData}',this)">
										<form:option value="">Select</form:option>
										<c:forEach items="${wwzPrefixList}" var="prefixData">
											<form:option value="${prefixData.lookUpId }"
												code="${prefixData.lookUpCode }">${prefixData.lookUpDesc }</form:option>
										</c:forEach>
										<form:option value="-1">
											<spring:message code="common.master.billing.all" text="All" />
										</form:option>
									</form:select>
								</c:when>
								<c:otherwise>
									<c:if test="${status.count eq 2}">
										<c:set value="${tbWtBillSchedule.codIdWwz1}" var="codIdWwz1"></c:set>
										<form:select path="${fieldPath}" class="form-control"
											id="${fieldPath}"
											onchange="getChildPrefixData('${getChildPrefixData}',this)">
											<form:option value="">
												<spring:message code="contract.label.select" text="Select" />
											</form:option>
											<c:forEach items="${wwzTempPrefixList}" var="prefixTempData">
												<c:if test="${not empty prefixTempData.lookUpParentId}">
													<c:if test="${codIdWwz1 eq prefixTempData.lookUpParentId}">
														<form:option value="${prefixTempData.lookUpId }"
															code="${prefixTempData.lookUpParentId }">${prefixTempData.lookUpDesc }</form:option>
													</c:if>
												</c:if>
											</c:forEach>
											<form:option value="-1">
												<spring:message code="common.master.billing.all" text="All" />
											</form:option>
										</form:select>
									</c:if>
									<c:if test="${status.count eq 3}">
										<c:set value="${tbWtBillSchedule.codIdWwz2}" var="codIdWwz2"></c:set>
										<form:select path="${fieldPath}" class="form-control"
											id="${fieldPath}"
											onchange="getChildPrefixData('${getChildPrefixData}',this)">
											<form:option value="">
												<spring:message code="contract.label.select" text="Select" />
											</form:option>
											<c:forEach items="${wwzTempPrefixList}" var="prefixTempData">
												<c:if test="${not empty prefixTempData.lookUpParentId}">
													<c:if test="${codIdWwz2 eq prefixTempData.lookUpParentId}">
														<form:option value="${prefixTempData.lookUpId }"
															code="${prefixTempData.lookUpParentId }">${prefixTempData.lookUpDesc }</form:option>
													</c:if>
												</c:if>
											</c:forEach>
											<form:option value="-1">
												<spring:message code="" text="All" />
											</form:option>
										</form:select>
									</c:if>
									<c:if test="${status.count eq 4}">
										<c:set value="${tbWtBillSchedule.codIdWwz3}" var="codIdWwz3"></c:set>
										<form:select path="${fieldPath}" class="form-control"
											id="${fieldPath}"
											onchange="getChildPrefixData('${getChildPrefixData}',this)">
											<form:option value="">
												<spring:message code="contract.label.select" text="Select" />
											</form:option>
											<c:forEach items="${wwzTempPrefixList}" var="prefixTempData">
												<c:if test="${not empty prefixTempData.lookUpParentId}">
													<c:if test="${codIdWwz3 eq prefixTempData.lookUpParentId}">
														<form:option value="${prefixTempData.lookUpId }"
															code="${prefixTempData.lookUpParentId }">${prefixTempData.lookUpDesc }</form:option>
													</c:if>
												</c:if>
											</c:forEach>
											<form:option value="-1">
												<spring:message code="common.master.billing.all" text="All" />
											</form:option>
										</form:select>
									</c:if>
									<c:if test="${status.count eq 5}">
										<c:set value="${tbWtBillSchedule.codIdWwz4}" var="codIdWwz4"></c:set>
										<form:select path="${fieldPath}" class="form-control"
											id="${fieldPath}"
											onchange="getChildPrefixData('${getChildPrefixData}',this)">
											<form:option value="">
												<spring:message code="contract.label.select" text="Select" />
											</form:option>
											<c:forEach items="${wwzTempPrefixList}" var="prefixTempData">
												<c:if test="${not empty prefixTempData.lookUpParentId}">
													<c:if test="${codIdWwz4 eq prefixTempData.lookUpParentId}">
														<form:option value="${prefixTempData.lookUpId }"
															code="${prefixTempData.lookUpParentId }">${prefixTempData.lookUpDesc }</form:option>
													</c:if>
												</c:if>
											</c:forEach>
											<form:option value="-1">
												<spring:message code="common.master.billing.all" text="All" />
											</form:option>
										</form:select>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</c:forEach>
				</div>

				<div
					class="form-group ${tbWtBillSchedule.dependsOnType eq 2 ? '':'hide'}"
					id="consumerTypeDiv">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.consumer" text="Consumer Type :" /></label>
					<div class="col-sm-4">
						<form:select id="cnsCcgid1" class="form-control" path="cnsCcgid1">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${ccgPrefixList}" var="ccgPrefixData">
								<form:option value="${ccgPrefixData.lookUpId }"
									code="${ccgPrefixData.lookUpCode }">${ccgPrefixData.lookUpDesc }</form:option>
							</c:forEach>
							<%-- <form:option value="-1">
								<spring:message code="common.master.billing.all" text="All" />
							</form:option> --%>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.from.month" text="From Month :" /></label>
					<div class="col-sm-4">
						<form:select id="fromMonth" class="form-control"
							path="cnsFromDate" onchange="setToMonthData(this)"
							readonly="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${billingMonthList}" var="monPrefixData">
								<form:option value="${monPrefixData.monthId }"
									code="${monPrefixData.tempMonthId }">${monPrefixData.monthName }</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="common.master.billing.to.month" text="To Month :" /></label>
					<div class="col-sm-4">
						<form:select id="toMonth" class="form-control"
							path="cnsToDateTemp" disabled="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${billingMonthTempList}" var="monPrefixData">
								<form:option value="${monPrefixData.monthId }"
									code="${monPrefixData.tempMonthId }">${monPrefixData.monthName }</form:option>
							</c:forEach>
						</form:select>
						<form:hidden path="cnsToDate" id="cnsToDateId" />
					</div>
				</div>
				<div id="SchedulewiseDueDate">
					<c:if test="${not empty scheduleList}">
						<h4>
							<spring:message code="bill.SchedulewiseDueDate"
								text="Schedule wise Due Date" />
						</h4>
						<table id="main-tableQ"
							class="table text-left table-striped table-bordered">
							<tbody>
								<tr>
									<th><spring:message code="bill.srNo" text="Sr. No." /></th>
									<th><spring:message code="bill.ScheduleFrom"
											text="Schedule From" /></th>
									<th><spring:message code="bill.ScheduleTo"
											text="Schedule To" /></th>
								</tr>
								<c:forEach items="${scheduleList}" var="billSchDto"
									varStatus="status">
									<tr class="dueDateDetail">
										<td>${status.count}</td>
										<td>${billSchDto.billFromMonth}</td>
										<td>${billSchDto.billToMonth}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
				<div class="text-center margin-top-10">
					<c:choose>
						<c:when test="${formMode eq 'view'}">
							<input type="button" class="btn btn-danger" value="Back"
								onclick="window.location.href='BillSchedule.html'" />
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-success btn-submit"
								onclick="return saveBillSchForm(this);" id="submitForm">
								<spring:message code="contract.label.submit" text="Submit" />
							</button>
							<input class="btn btn-warning"
								value="<spring:message code="bt.clear"/>" type="Reset"
								id="resetBtn" onclick="resetSchedule(this);" />
							<input type="button" class="btn btn-danger"
								value="<spring:message code="bt.backBtn"/>"
								onclick="window.location.href='BillSchedule.html'" />
						</c:otherwise>
					</c:choose>

				</div>
			</form:form>
		</div>

	</div>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {

				if ('${formMode}' == 'view') {
					if ($('#codIdWwz1').val() != null
							&& $('#codIdWwz1').val() != undefined
							&& $('#codIdWwz1').val() != '') {
						getChildPrefixDataView(
								"BillSchedule.html?getChildPrefixData",
								'#codIdWwz1');
					}
					if ($('#codIdWwz2').val() != null
							&& $('#codIdWwz2').val() != undefined
							&& $('#codIdWwz2').val() != '') {
						getChildPrefixDataView(
								"BillSchedule.html?getChildPrefixData",
								'#codIdWwz2');
					}
					if ($('#codIdWwz3').val() != null
							&& $('#codIdWwz3').val() != undefined
							&& $('#codIdWwz3').val() != '') {
						getChildPrefixDataView(
								"BillSchedule.html?getChildPrefixData",
								'#codIdWwz3');
					}
					if ($('#codIdWwz4').val() != null
							&& $('#codIdWwz4').val() != undefined
							&& $('#codIdWwz4').val() != '') {
						getChildPrefixDataView(
								"BillSchedule.html?getChildPrefixData",
								'#codIdWwz4');
					}

					$('#codIdWwz1').val($('#wwz1').val());
					$('#codIdWwz2').val($('#wwz2').val());
					$('#codIdWwz3').val($('#wwz3').val());
					$('#codIdWwz4').val($('#wwz4').val());
					$('#codIdWwz5').val($('#wwz5').val());

					$('select').prop('disabled', 'disabled');
				}
			});
</script>
