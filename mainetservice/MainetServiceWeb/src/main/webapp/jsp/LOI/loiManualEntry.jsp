<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- <script src="js/cfc/scrutiny.js"></script> -->
<script>
$( document ).ready(function(){
	
	$('.loiDate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});

	initializeTable();
	
	});

	function initializeTable() {
		$('#loiChargesTable').DataTable({
			"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
			"iDisplayLength" : 10,
			"bInfo" : true,
			"lengthChange" : true,
			"scrollCollapse" : true,
			"bSort" : false
		});
	}

	
	
	function addLoitDetailsWith(e) {
		var errorList = [];
		var index = $('.loiCharges').index($(e).closest('tr'));
		var taxList = $("#taxList" + index).val();
		var amount = $("#amount" + index).val();
		var no = index + 1;
		if (taxList == "" || taxList == "0") {
			errorList.push(getLocalMessage("loi.entry.validation.chargeName") + " "
					+ no);
		}
		if (amount == "0" || amount == "") {
			errorList.push(getLocalMessage("loi.entry.validation.amount") + " " + no);
		}
		if (errorList.length == 0) {
			var count = $('#loiChargesTable tr').length - 1;

			var clickedRow = $(e).closest('tr');
			var content = clickedRow.clone();
			content.find("select").val("");
			content.find("input:text").val('');
			content.find("input:hidden").val('');
			content.find('div.chosen-container').remove();
			content.find("select:eq(0)").chosen().trigger("chosen:updated");

			clickedRow.after(content);
			reOrderLoiDetails();
		} else {
			displayErrorsOnPage(errorList);
		}

	}

	function deleteTableRow1(tableId, rowObj, deletedId, isDataTable) {
		var $table = $("#" + tableId);
		var $row = $(rowObj).closest("tr");

		// Get the index of the row
		var rowIndex = $row.index();

		// Remove the row from the table
		$row.remove();

		// Reorder the rows if needed
		if (isDataTable == undefined || isDataTable) {
			$table.DataTable().row(rowIndex).remove().draw(false);
		} else {
			reOrderLoiDetailDelete(tableId);
		}

		// Update the deletedIds field if necessary
		var deletedSorId = $row.find("input[type=hidden]:first").val();
		if (deletedSorId) {
			var prevValue = $("#" + deletedId).val();
			if (prevValue) {
				$("#" + deletedId).val(prevValue + "," + deletedSorId);
			} else {
				$("#" + deletedId).val(deletedSorId);
			}
		}
		updateTotalAmount();
	}

	function reOrderLoiDetails() {
		$('.loiCharges').each(
				function(i) {
					var $row = $(this);
					var newIndex = i;

					$row.find("select:eq(0)").attr("id", "taxList" + newIndex);
					$row.find("input:text:eq(1)").attr("id",
							"amount" + newIndex);
					$row.find("select:eq(0)").attr("name",
							"loiDets[" + i + "].loiChrgid");
					$row.find("input:text:eq(1)").attr("name",
							"loiDets[" + i + "].loiAmount");
				});
	}

	function reOrderLoiDetailDelete(tableId) {
		var $table = $("#" + tableId);

		$table.find('.loiCharges').each(
				function(i) {
					var $row = $(this);
					var newIndex = i;

					$row.find("select:eq(0)").attr("id", "taxList" + newIndex);
					$row.find("input:text:eq(1)").attr("id",
							"amount" + newIndex);
					$row.find("select:eq(0)").attr("name",
							"loiDets[" + i + "].loiChrgid");
					$row.find("input:text:eq(1)").attr("name",
							"loiDets[" + i + "].loiAmount");
				});
	}

	function getTaxList() {
		$("#errorDiv").hide();
		var errorList = [];

		if ($('#dpDeptId').val() > 0) {
			var requestData = {
				deptId : $("#dpDeptId").val(),
			};
			var url = "LoiManualEntry.html?taxList";
			var returnData = __doAjaxRequest(url, 'post', requestData, false,
					'', '');
			if (returnData != "" || returnData.length > 0) {
				var rowCount = $('.loiCharges').length;
				if (rowCount > 0) {
					for (var i = 0; i <= rowCount; i++) {
						var taxListId = "#taxList" + i;
						var taxList = $(taxListId);

						// Clear existing options
						taxList.empty();

						// Append "Select Tax" option
						taxList
								.append($("<option></option>")
										.attr("value", "")
										.text(
												"<spring:message code='tax.selectTax' text='Select Tax'/>"));

						// Append tax list options
						$
								.each(
										returnData,
										function(index, value) {
											if (value.lookUpId != null) {
												taxList
														.append($(
																"<option></option>")
																.attr(
																		"value",
																		value.lookUpId)
																.text(
																		value.lookUpDesc));
											} else {
												errorList
														.push(getLocalMessage('tax.error.notax'));
												displayErrorsOnPage(errorList);
											}
										});
					}
				} else {
					errorList.push(getLocalMessage('tax.error.notax'));
					displayErrorsOnPage(errorList);
				}
			} else {
				errorList.push(getLocalMessage('tax.error.notax'));
				displayErrorsOnPage(errorList);
			}
		} else if ($('#dpDeptId').val() == '') {
			$(".loiCharges select.taxList").empty(); // Empty the tax list options in all rows
			$(".loiCharges select.taxList")
					.append(
							$("<option></option>")
									.attr("value", "")
									.text(
											"<spring:message code='tax.selectTax' text='Select Tax'/>"));
		}
		$(".chosen-select-no-results").trigger("chosen:updated");

		getServices();
	}

	function getServices() {

		var requestData = {
			deptId : $("#dpDeptId").val(),
		};
		$('#serviceId').html('');
		$('#serviceId').append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));

		var result = __doAjaxRequest("LoiManualEntry.html?services", 'post',
				requestData, false, 'json');
		var serviceList = result[0];
		$.each(serviceList, function(index, value) {
			if ($('#langId').val() == 1) {
				$('#serviceId').append(
						$("<option></option>").attr("value", value[0]).attr(
								"code", value[2]).text(value[1]));
			} else {
				$('#serviceId').append(
						$("<option></option>").attr("value", value[0]).attr(
								"code", value[2]).text(value[3]));
			}
		});
		$('#serviceId').trigger("chosen:updated");

	};

	function onAmountChange(element) {
		updateTotalAmount();
	}

	function updateTotalAmount() {
		var total = 0;
		$('.loiCharges').each(function() {
			var amountField = $(this).find('input[id^="amount"]');
			var amountValue = parseFloat(amountField.val()) || 0;
			total += amountValue;
		});
		$('#totAmount').val(total);
	}

	function saveAndGenerateLoi(obj) {
		var errorList = [];
		validateApplicantDetails(errorList);
		validateLoiDetails(errorList);
		if (errorList.length == 0) {
			var formName = findClosestElementId(obj, 'form');
			var theForm = '#' + formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			var returnData = __doAjaxRequest('LoiManualEntry.html?generateLOI',
					'POST', requestData, false);
			showConfirmBoxFoLoi(returnData);
		} else {
			displayErrorsOnPage(errorList);
		}
	}

	function validateApplicantDetails(errorList) {

		var dept = $("#dpDeptId").val();
		var serviceId = $("#serviceId").val();
		var applicantName = $("#applicantName").val();
		var applicationDate = $("#applicationDate").val();
		var mobNo = $("#mobNo").val();
		var email = $("#email").val();

		if (dept == "" || dept == undefined || dept == "0") {
			errorList.push(getLocalMessage("master.select.dept"));
		}
		if (serviceId == "" || serviceId == undefined || serviceId == "0") {
			errorList.push(getLocalMessage("master.select.serviceName"));
		}
		if (applicantName == "" || applicantName == undefined) {
			errorList.push(getLocalMessage("loi.entry.validation.applicantName"));
		}
		if (applicationDate == "" || applicationDate == undefined) {
			errorList.push(getLocalMessage("loi.entry.validation.applicantDate"));
		}
		if (mobNo == "" || mobNo == undefined || mobNo.length < 10 || !(/^[789]\d{9}$/).test(mobNo)) {
			errorList.push(getLocalMessage("loi.entry.validation.mobileNo"));
		}
		/* if (email == "" || email == undefined) {
			errorList.push(getLocalMessage("loi.entry.validation.emailId"));
		} */

		return errorList;
	}

	function validateLoiDetails(errorList) {

		$('.loiCharges')
				.each(
						function(i) {
							var taxList = $("#taxList" + i).val();
							var amount = $("#amount" + i).val();
							var no = i + 1;
							if (taxList == "" || taxList == "0") {
								errorList
										.push(getLocalMessage("loi.entry.validation.chargeName")
												+ " " + no);
							}
							if (amount == "0" || amount == "") {
								errorList
										.push(getLocalMessage("loi.entry.validation.amount")
												+ " " + no);
							}

						});

		return errorList;
	}

	function showConfirmBoxFoLoi(sucessMsg) {

		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage("bt.proceed");

		$('button').prop('disabled', true);
		$('select').prop('disabled', true).trigger("chosen:updated");;
		$('input').prop('disabled', true);
		$('textarea').prop('disabled', true);
		// Disable buttons
		$('.action-buttons').find('a').addClass('disabled').attr('disabled', true);


		message += '<h4 class=\"text-center text-blue-2 padding-12\">'
				+ sucessMsg.command.message + '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceed()"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		return false;
	}


	function proceed() {
		window.location.href = 'LoiManualEntry.html';
		$.fancybox.close();
		enableContentInteraction();
	}
	
</script>
<style>
.msg-dialog-box {
  border: 2px solid black; /* Set the border color to black */
  padding: 10px; /* Add padding to make the border visible */
}

</style>
<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
	<li><spring:message code="master.brdCum.Home" text="Home"/></li>
	<li><spring:message code="master.loi.letter" text="LOI"/></li>
	<li class="active"><spring:message code="loi.manual.entry.header" text="Manual LOI Generation"/></li>
</ol>
<div id="loiEntryPage">
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
				<spring:message code="master.loi.letter" text="LOI"/> <spring:message code="master.loi.letter.generation" text="Generation"/>
			</h2>
				<apptags:helpDoc url="LoiManualEntry.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">
					<div class="mand-label clearfix">
						<span><spring:message code="common.sequence.ismandtry"
								text="Field with * is mandatory" /> </span>
					</div>
					<form:form action="LoiManualEntry.html" method="POST"
						class="form-horizontal" id="loiEntryForm" name="LoiManualEntry">
						<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
						</div>
						<input type="hidden" value="${userSession.languageId}" id="langId"/>


						<h4>
							<spring:message code="master.loi.app.details"
								text="Application Details" />
						</h4>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" for="dpDeptId"><spring:message code='master.department'/></label>
							<div class="col-sm-4">
								<form:select id="dpDeptId" path="deptId" class="form-control chosen-select-no-results"  onchange="getTaxList();">
									<form:option value=""><spring:message code='common.taxmaster.selDept' text="Select Department"/></form:option>
									<c:forEach items="${deptList}" var="deptListData">
										<form:option value="${deptListData.dpDeptid }" >${deptListData.dpDeptdesc }</form:option>
									</c:forEach>
								</form:select>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="master.serviceName" text="Service Name" /></label>
							<div class="col-sm-4">
								<form:select path="serviceId" class="form-control chosen-select-no-results"
									id="serviceId" disabled="">
									<form:option value="">
										<spring:message code="scrutiny.select" text="Select" />
									</form:option>
									<c:choose>
										<c:when test="${userSession.languageId eq 1}">
											<c:forEach items="${serviceList}" var="serviceData">
												<form:option value="${serviceData.smServiceId }">${serviceData.smServiceName }</form:option>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${serviceList}" var="serviceData">
												<form:option value="${serviceData.smServiceId }">${serviceData.smServiceNameMar }</form:option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="master.loi.applicant.name" text="Applicant Name" /> </label>
							<div class="col-sm-4">
								<form:input path="applicantName" type="text" class="form-control hasCharacter" />
							</div>
							
							
							<label class="col-sm-2 control-label required-control"
								for=""><spring:message
									code="master.loi.appDateTime"
									text="Application Date Time" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input
										class="form-control mandColorClass datepicker2 addColor loiDate"
										placeholder="DD/MM/YYYY" autocomplete="off"
										id="applicationDate"
										path="applicationDate"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									text="Applicant Mobile No " code="master.loi.applicant.mob" />
							</label>
							<div class="col-sm-4">

								<form:input path="mobNo" type="text" class="form-control hasNumber" 
									maxlength="10"/>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									text="Applicant Email Id" code="master.loi.applicant.email" />
							</label>
							<div class="col-sm-4">

								<form:input path="email" type="text" class="form-control" />
							</div>
						</div>


						<h4>
							<spring:message text="LOI Fees and Charges in Details"
								code="master.loi.feesandcharges" />
						</h4>
						
						
						
			        	<c:set var="d" value="0" scope="page" />
					    <table class="table table-bordered table-condensed margin-bottom-10" id="loiChargesTable">
							<tr>
								<th scope="col"><spring:message text="Charge Name"
										code="master.loi.charge.name" /> <span class="mand">*</span></th>
								<th scope="col"><spring:message text="Amount"
										code="master.loi.amt" /> <span class="mand">*</span></th>
								<th width=8% scope="col"><spring:message text="Action"
										code="master.grid.column.action" /></th>
							</tr>
							<tbody>
					        	
					            <tr class="loiCharges" style="height: 20px;">
					                <td>
					                    <form:select id="taxList${d}" path="loiDets[${d}].loiChrgid" class="form-control chosen-select-no-results taxList">
					                        <form:option value=""><spring:message code='tax.selectTax' text="Select Tax"/></form:option>
					                        <c:forEach items="${txnPrefixData}" var="txnPrefixData">
					                            <form:option value="${txnPrefixData.lookUpId}">${txnPrefixData.lookUpDesc }</form:option>
					                        </c:forEach>
					                    </form:select>
					                </td>

									<td><div class="input-group">
											<form:input path="loiDets[${d}].loiAmount"
												class="form-control text-right" maxlength="10"
												id="amount${d}"
												onkeypress="return hasAmount(event, this, 10, 2)"
												placeholder="Enter Amount Value"
												onchange="onAmountChange(this)" />
											<label class="input-group-addon"><i class="fa fa-inr"></i><span
												class="hide"><spring:message code="" text="Rupees" /></span></label>
										</div></td>


									<td width=8% align="center" class="action-buttons">
									    <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" onclick="addLoitDetailsWith(this);" class="btn btn-success addtable btn-sm" id="addButton"><i class="fa fa-plus-circle"></i></a>
									    <a class="btn btn-danger btn-sm delButton" onclick="deleteTableRow1('loiChargesTable', $(this), 'removedIds', 'isDataTable');" id="deleteButton"><i class="fa fa-minus"></i></a>
									</td>
					            </tr>
					            <tr>
									<td width=50%><span><b><spring:message
													text="Total LOI Amount" code="master.loi.total.amt" /></b></span></td>
									<td>
										<div class="input-group">
											<form:input path="total" cssClass="form-control text-right"
												id="totAmount" readonly="true" />
											<label class="input-group-addon"><i class="fa fa-inr"></i><span
												class="hide"><spring:message code="" text="Rupees" /></span>
										</div>
									</td>
								</tr>
					        </tbody>
					    </table>

						
						
						<div class="form-group padding-top-10">
							<label class="col-sm-2 control-label"><spring:message
									code="dashboard.actions.remarks" text="Remarks" /></label>
							<div class="col-sm-4">
								<form:textarea path="entity.loiRemark" class="form-control" id="loiRemark" />
							</div>
						</div>

						<div class="text-center padding-top-10">
							<button type="button" class="btn btn-success btn-submit"
								onclick="return saveAndGenerateLoi(this);">
								<spring:message code="submit.msg" text="Submit" />
							</button>
							<button type="button" class="btn btn-warning" onclick="window.location.href='LoiManualEntry.html'"><spring:message code="reset.msg" text="Reset"/></button>
               				<button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'"><spring:message code="back.msg" text="Back"/></button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
