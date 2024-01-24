$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});
	$(".datepicker").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$('#bill').val(parseFloat(Math.round($(this).val())));
	$('.hasNumber').on("load",function() {
		var $this = $(this);
		var value = parseFloat(Math.round($this.val()));
		$this.val(value);
	});
	$("#id_customerTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});

	$("#receivableEntryTable ,.rclass, .aclass, .fclass , .addS").hide();
	$('input[name="receivableDemandEntryDto.refFlag"]').click(function() {
		if ($(this).attr("value") == "A") {
			$(".rclass, .addS").hide();
			$(".aclass, .fclass, .searchData").show();
		}
		if ($(this).attr("value") == "R") {
			$(".rclass, .fclass , .searchData").show();
			$(".aclass, .addS").hide();
		}
		if ($(this).attr("value") == "N") {
			$(".rclass, .aclass, .fclass , .searchData").hide();
			$(".addS").show();

		}
	});
	refreshServiceData();

	if ($("#saveMode").val() == 'E') {
		editShowHide();
	}
	$('#custAddress').attr("maxlength", 800);
});

function getAccountCode(i) {
	var errorList = [];
	var optionsAsString = '';
	$('#accHead' + i).html('');
	var utp = i * 6;
	var data = {
		"deptId" : $('#deptId').val(),
		"taxCategory" : $('#taxCategory' + (utp + 1)).val(),
		"taxSubCategory" : $('#taxCategory' + (utp + 2)).val()
	};
	var url = "ReceivableDemandEntry.html?getHeadList";
	var ajaxResponse = __doAjaxRequest(url, 'post', data, false, '');

	for (var j = 0; j < ajaxResponse.length; j++) {
		var acHeadCode = ajaxResponse[j].acHeadCode.replace(/-/g, "");
		acHeadCode = acHeadCode.substring(11, 20);
		optionsAsString += "<option value='" + ajaxResponse[j].sacHeadId + "'>" + acHeadCode + "</option>";
	}
	$("#accHead" + i).append(optionsAsString);
	return errorList;
}

function openaddForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$("#receivableEntryTable").hide();
}

function searchReceivableDemandEntry(formUrl, actionParam) {
	var errorList = [];
	var refNumber = $('#refNumber').val();
	var billNo = $('#billNo').val();
	
	if (refNumber != "" || billNo != "") {
		var ccnregexPattern = /^[a-zA-Z @.$#]{3}\d{7}$/;
		var oldIdnregexPattern = /^[a-zA-Z]{5}\d{6}$/;
		var newIdnregexPattern = /^[a-zA-Z]{5}\d{7}$/;
		var billregexPattern = /^\d{4}[HES]{3}\d{8}$/;
		var oldbillregexPattern = /^\d{2}[HES]{3}\d{8}$/;

		if (refNumber != "") {
			if (!(oldIdnregexPattern.test(refNumber) || newIdnregexPattern.test(refNumber) || ccnregexPattern.test(refNumber))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.ccnidn"));
			}
		} else {
			if (!(billregexPattern.test(billNo) || oldbillregexPattern.test(billNo))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.billNo"));
			}
		}
		if (errorList.length == 0) {
			var divName = '.content-page';
			var requestData = {
				"refNumber" : refNumber,
				"billNo" : billNo
			};

			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
			$('#refNumber').val(refNumber);
			$('#billNo').val(billNo);
			var listSize = $('#listSize').val();
			if (listSize == "0") {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.no.record.found") + refNumber + " " + billNo);
			}

		}
	} else {
		errorList.push(getLocalMessage("misc.search.error"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
}

function Proceed(element) {
	var errorList = [];
	errorList = validateForm(errorList);
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var status = saveOrUpdateForm(element, getLocalMessage('account.form.submit.success'), 'ReceivableDemandEntry.html?printChallan', 'saveform');
		return status;
	}
}

function validateForm(errorList) {

	var deptId = $("#deptId").val();
	var serviceId = $("#serviceId").val();
	var refNumber = $("#refNumber").val();
	var custName = $("#custName").val();
	var custAddress = $("#custAddress").val();
	var demandDate = $("#billDate").val();
	var plotNo = $("#custKhasaraNo").val();
	var area = $("#custLocation").val();
	var city = $("#cityName").val();
	var pincode = $("#pinCode").val();
	var isNewIdn = $("#isNewIdn").val();

	if (deptId == null || deptId == "0" || deptId == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.deptName"));
	}
	if (serviceId == null || serviceId == "0" || serviceId == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.serviceName"));
	}
	if (custName == null || custName == "0" || custName == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.custName"));
	}
	if (custAddress == null || custAddress == "0" || custAddress == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.custAdd"));
	}else if(custAddress.length>800){
		errorList.push(getLocalMessage("receivable.demand.entry.validation.custAdd"));
	}
	if (demandDate == null || demandDate == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.demandDate"));
	}
	
	if(isNewIdn =="N"){
		if (area == null || area == "0" || area == "") {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.area"));
		}
		if (plotNo == null || plotNo == "0" || plotNo == "") {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.plotNo"));
		}
		if (city == null || city == "0" || city == "") {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.city"));
		}
		if (pincode == null || pincode == "") {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.pincd"));
		}		
	}
	validateCustData(errorList);
	return errorList;
}

function printChallan(billId, formUrl, actionParam) {
	var divName = '.content-page';
	var requestData = {
		"id" : billId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function ViewReceivableDemandEntry(custId, formUrl, actionParam) {
	var errorList = [];
	var divName = '.content-page';
	var requestData = {
		"id" : custId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

	if ((typeof $("#formError").val() !== 'undefined')) {
		errorList.push($("#formError").val());
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		showPageForEdit();
	}
};

function showPageForEdit() {
	
	var deptId = $("#depId").val();
	var serviceId = $("#serId").val();
	refreshServiceData();
	$('#serviceId').val(serviceId);
	toggleReferanceOrApplicationNo();
	if ($("#sliStatus").val() == "Active") {
		reOrderTableIdSequence1('.firstUnitRow');
	} else {
		reOrderTableIdSequence2('.secondUnitRow');
	}
	sum1();
	$("#id_receivableDemandEntryTbl tbody tr").each(function(i) {		
		utp = i*6;
		showTaxSubCategoryListForEdit(utp);
		getAccountCode(i);
	});
}
function editShowHide() {
	$('#refNumber').attr("readOnly", "true");
	$("#id_search").show();
	$("#id_receivableDemandEntryTbl tbody tr").each(function(i) {
		$("#id_total" + i).val(parseFloat(Math.round($("#id_total" + i).val())));
	});
}

function toggleReferanceOrApplicationNo() {
	if ($("#refFlag1").is(":checked")) {
		$(".rclass").hide();
		$(".aclass, .fclass").show();
	}
	if ($("#refFlag2").is(":checked")) {
		$(".rclass, .fclass").show();
		$(".aclass").hide();
	}
	if ($("#refFlag3").is(":checked")) {
		$(".rclass, .aclass, .fclass").hide();
	}
}

function refreshServiceData() {
	$('.error-div').hide();
	$("#serviceId").text('');
	$("#serviceId").html('');
	var deptId = $('#deptId').val();
	var requestData = "deptId=" + deptId;
	if ($('#deptId').val() > 0) {
		var url = "ReceivableDemandEntry.html?refreshServiceData";
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '');
		if (returnData != "") {
			$.each(returnData, function(index, value) {

				if ($("#saveMode").val() == 'V') {
					if ($("#langId").val() == 1) {
						$('#serviceId').append(
								$("<option></option>").attr("value", value.smServiceId).attr("code", value.smShortdesc).text(value.smServiceName));
					} else {
						$('#serviceId').append(
								$("<option></option>").attr("value", value.smServiceId).attr("code", value.smShortdesc).text(value.smServiceNameMar));
					}
				} else {
					if (value.smShortdesc == "SBD") {
						if ($("#langId").val() == 1) {
							$('#serviceId').append(
									$("<option></option>").attr("value", value.smServiceId).attr("code", value.smShortdesc).text(value.smServiceName));
						} else {
							$('#serviceId').append(
									$("<option></option>").attr("value", value.smServiceId).attr("code", value.smShortdesc).text(value.smServiceNameMar));
						}
					}
				}
			});
		} else {
			var errorList = [];
			$('#serviceId').append($("<option></option>").attr("value", -1).text("Select Service"));
			errorList.push(getLocalMessage("service.error.noservice"));
			displayErrorsOnPage(errorList);
		}
		$("#serviceId").trigger("chosen:updated");
	}
}

function getTaxDetails() {
	var errorList = [];

	errorList = validateSearch(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {

		var divName = '.content-page';
		var deptId = $('#deptId').val();
		var serviceId = $('#serviceId').val();
		var refNumber = $('#refNumber').val();
		var caseNo = $('#caseNo').val();
		var refFlag = $("input[name='receivableDemandEntryDto.refFlag']:checked").val();
		var requestData = {
			"serviceId" : serviceId,
			"deptId" : deptId,
			"refNumber" : refNumber,
			"refFlag" : refFlag
		}
		if ($('#serviceId').val() > 0) {
			var url = "ReceivableDemandEntry.html?getTaxDetails";
			var ajaxResponse = doAjaxLoading(url, requestData, 'html', divName);

			var taxCount = $(ajaxResponse).find('#taxCount');
			var cust = $(ajaxResponse).find('#custName');

			if ($('.content').html(ajaxResponse)) {
				$(divName).removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();

				$('#deptId').val(deptId);
				refreshServiceData();
				$('#serviceId').val(serviceId);
				$('#deptId').val(deptId);
				$('#refNumber').val(refNumber);

				if (taxCount.val() > 0) {
					if (cust.val() == null || cust.val() == undefined || cust.val() == "") {
						if ($("#refFlag1").is(":checked")) {
							errorList.push(getLocalMessage("receivable.demand.entry.validation.notFound.appNo") + refNumber
									+ getLocalMessage("receivable.demand.entry.validation.invalid.appNo"));
						}
						if ($("#refFlag2").is(":checked")) {
							errorList.push(getLocalMessage("receivable.demand.entry.validation.notFound.refNo") + refNumber
									+ getLocalMessage("receivable.demand.entry.validation.custDetails"));
						}
						if ($("#refFlag3").is(":checked")) {
							$("#receivableEntryTable").show();
							$("#id_search").hide();
							$('#refNumber').attr("readOnly", "true");
							$('#deptId ,#serviceId ,#refFlag1 ,#refFlag2, #refFlag3').attr("disabled", "true");
						}
					} else {
						$('#refNumber').attr("readOnly", "true");
						$("#receivableEntryTable").show();
						$("#id_search").hide();
						$('#refNumber ,#custName ,#custAddress ,#custTINNo ,#custKhasaraNo ,#custLocation ,#cityName ,#pinCode ,#custMobile ,#custEmailId ')
								.attr("readOnly", "true");
						$('#applicationType ,#deptId ,#serviceId ,#refFlag1 ,#refFlag2, #refFlag3').attr("disabled", "true");
					}
					if (errorList.length > 0)
						displayErrorsOnPage(errorList);
				} else {
					errorList.push(getLocalMessage("receivable.demand.entry.validation.tax.notFound"));
					displayErrorsOnPage(errorList);
				}
			}
			toggleReferanceOrApplicationNo();
			$('#caseNo').val(caseNo);
			$("#id_receivableDemandEntryTbl tbody tr").each(function(i) {
				$("#id_total" + i).val(parseFloat(Math.round($("#id_total" + i).val())));
			});
		}
	}
}

function validateSearch(errorList) {

	var deptId = $('#deptId').val();
	var serviceId = $('#serviceId').val();
	var refNumber = $("#refNumber").val();
	var ccnregexPattern = /^[a-zA-Z @.$#]{3}\d{7}$/;
	var oldIdnregexPattern = /^[a-zA-Z]{5}\d{6}$/;
	var newIdnregexPattern = /^[a-zA-Z]{5}\d{7}$/;
	var billregexPattern = /^\d{4}[HES]{3}\d{8}$/;
	var wardIdn = $("#wardIdn").val();
	// var oldbillregexPattern = /^\d{2}[HES]{3}\d{8}$/;

	if (deptId == null || deptId == "0" || deptId == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.deptName"));
	}
	if (serviceId == null || serviceId == "-1" || serviceId == "") {
		errorList.push(getLocalMessage("receivable.demand.entry.validation.serviceName"));
	}
	
	if (!$("#refFlag3").is(":checked")) {
		if ($("#refFlag1").is(":checked")) {
			if (!(oldIdnregexPattern.test(refNumber) || newIdnregexPattern.test(refNumber))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.idn"));
			} else if (!(refNumber.toUpperCase().includes(wardIdn))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.wardIdn"));
			}
		} else {
			if (!(ccnregexPattern.test(refNumber))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.ccn"));
			} else if (wardIdn.substring(0, 2).toUpperCase() != refNumber.substring(0, 2).toUpperCase()) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.wardccn"));
			}
		}
	}
	return errorList;
}

function addEntryData(tableId, tableCode) {

	var errorList = [];
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow2(tableId, tableCode);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteEntry(tableId, obj, ids, tableCode) {
	deleteTableRow2(tableId, obj, ids, tableCode);
}

function addTableRow2(tableId, tableCode) {

	var id = "#" + tableId;
	if ($.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	var content = $(id + ' tr').last().clone();
	content.find("input:text").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find("select").prop("disabled", false);
	content.appendTo('tbody');
	if (tableCode == 'firstUnitRow') {
		reOrderTableIdSequence1('.firstUnitRow');
	} else {
		reOrderTableIdSequence2('.secondUnitRow');
	}
	dataTableProperty(id);

}

function deleteTableRow2(tableId, rowObj, deletedId, tableCode) {
	
	var id = "#" + tableId;
	var error = [];
	// remove datatable specific properties
	if ($.fn.DataTable.isDataTable(id)) {
		$(id).DataTable().destroy();
		$(".datepicker").datepicker("destroy");
	}
	var rowCount = $("" + id + " tbody tr").length;
	// if rowCount is 1, it means only one row present
	if (rowCount != 1) {
		var deletedSorId = $(rowObj).closest('tr').find('input[type=hidden]:first').attr('value');
		$(rowObj).closest('tr').remove();
		if (deletedSorId != '') {
			var prevValue = $('#' + deletedId).val();
			if (prevValue != '')
				$('#' + deletedId).val(prevValue + "," + deletedSorId);
			else
				$('#' + deletedId).val(deletedSorId);
		}
		if (tableCode == 'firstUnitRow') {
			reOrderTableIdSequence1('.firstUnitRow');
		} else {
			reOrderTableIdSequence2('.secondUnitRow');
		}

		dataTableProperty(id);
		sum1();
	}
}

function deleteRow(i) {
	var data = {
		"deletedRow" : i
	};
	var URL = 'ReceivableDemandEntry.html?deletedDemandDetailRow';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);

}

function hasNumber1(i) {
	$("#id_total" + i).val(($("#id_total" + i).val().replace(/[^0-9]+/g, "")));
}

function sum1() {
	var i = 0;
	var gtotal = 0;
	$("#id_receivableDemandEntryTbl tbody tr").each(function(i) {

		var amnt = $("#id_total" + i).val();
		gtotal = parseFloat(Math.round(gtotal)) + parseFloat(Math.round(amnt));
		if (gtotal == 'NaN') {
			gtotal = 0;
		}
	});

	$('#id_grand_Total').val(gtotal);
}

function reOrderTableIdSequence1(firstRow) {
	var colCount = $("#id_receivableDemandEntryTbl tfoot th").length - 4;
	$(firstRow).each(function(i) {

		var utp = i;
		if (i > 0) {
			utp = i * 6;
		}
		// IDs
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
		$(this).find("select:eq(0)").attr("id", "taxCategory" + (utp + 1));
		$(this).find("select:eq(0)").attr("onchange", "showTaxSubCategoryList(" + utp + ")");
		$(this).find("select:eq(1)").attr("id", "taxCategory" + (utp + 2));
		$(this).find("select:eq(1)").attr("onchange", "getAccountCode(" + i + ")");
		$(this).find("select:eq(2)").attr("id", "accHead" + i);
		$(this).find("input:text:eq(1)").attr("id", "id_total" + i);
		$(this).find("input:text:eq(1)").attr("onkeyup", "hasNumber1(" + i + ")");
		$(this).find("input:hidden:eq(0)").attr("id", "taxId" + i);
		$(this).find(".deleteB").attr('id', "deleteRow(" + i + ")");

		// names
		$(this).find("input:text:eq(0)").attr("name", i);
		$(this).find("select:eq(0)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].taxCategory1");
		$(this).find("select:eq(1)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].taxCategory2");
		$(this).find("select:eq(2)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].sacHeadId");
		$(this).find("input:text:eq(1)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].billDetailsAmount");
		$(this).find("input:hidden:eq(0)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].taxId");
	});
}

function reOrderTableIdSequence2(firstRow) {
	var colCount = $("#id_receivableDemandEntryTbl tfoot th").length - 4;
	$(firstRow).each(function(i) {

		var utp = i;
		if (i > 0) {
			utp = i * 6;
		}
		// IDs
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
		$(this).find("select:eq(0)").attr("id", "taxCategory" + (utp + 1));
		$(this).find("select:eq(0)").attr("onchange", "showTaxSubCategoryList(" + utp + ")");
		$(this).find("select:eq(1)").attr("id", "taxCategory" + (utp + 2));
		$(this).find("select:eq(1)").attr("onchange", "getAccountCode(" + i + ")");
		$(this).find("input:text:eq(1)").attr("id", "accHead" + i);
		$(this).find("input:text:eq(2)").attr("id", "id_total" + i);
		$(this).find("input:text:eq(2)").attr("onkeyup", "hasNumber1(" + i + ")");
		$(this).find("input:hidden:eq(0)").attr("id", "taxId" + i);
		$(this).find(".deleteB").attr('id', "deleteRow(" + i + ")");
		// names

		$(this).find("input:text:eq(0)").attr("name", i);
		$(this).find("select:eq(0)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].taxCategory1");
		$(this).find("select:eq(1)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].taxCategory2");
		$(this).find("input:text:eq(1)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].accNo");
		$(this).find("input:text:eq(2)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].billDetailsAmount");
		$(this).find("input:hidden:eq(0)").attr("name", "receivableDemandEntryDto.rcvblDemandList[" + i + "].taxId");
	});
}

function validateUnitDetailTable(errorList) {
	var colCount = $("#id_receivableDemandEntryTbl tfoot th").length - 4;
	var tableRowName = null;
	var accHeadArray = [];

	if ($("#sliStatus").val() == "Active") {
		tableRowName = '.firstUnitRow';
	} else {
		tableRowName = '.secondUnitRow';
	}
	$(tableRowName).each(function(i) {

		var utp = i;
		if (i > 0) {
			utp = i * 6;
		}
		var custId = $("#custId" + i).val();
		var tax = $("#tax").val();
		var accHead = $("#accHead" + i).val();
		var tax1 = $("#taxCategory" + (utp + 1)).val();
		var tax2 = $("#taxCategory" + (utp + 2)).val();
		var id_total = $("#id_total" + i).val();
		accHeadArray.push(accHead);
		errorList1 = getAccountCode(i);
		if (errorList1.length != 0) {
			errorList.push(getAccountCode(i));
		}
		if (accHead == '' || accHead == undefined || accHead == '0') {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.accCode.notMapped") + (i + 1));
		}
		if (tax1 == '' || tax1 == undefined || tax1 == '0') {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.collType") + (i + 1));
		}
		if (tax2 == '' || tax2 == undefined || tax2 == '0') {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.desc") + (i + 1));
		}
		if (id_total == '' || Math.round(id_total) == '0' || id_total == undefined) {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.amnt") + (i + 1));
		}

	});
	// checking duplicate accountHead
	var j, k = 1;
	var count = 0;
	for (i = 0; i <= accHeadArray.length; i++) {
		for (j = k; j < accHeadArray.length; j++) {

			if (accHeadArray[i] == accHeadArray[j]) {
				if (count == 0) {
					errorList.push(getLocalMessage("receivable.demand.entry.validation.duplicate.accHead"));
				}
				count++;
			}
		}
		k++;
	}
	return errorList
}

function showTaxSubCategoryList(rowNo) {
	
	var optionsAsString = '';
	$('#taxCategory' + (rowNo + 2)).html('');
	var taxCategory = $("#taxCategory" + (rowNo + 1) + " option:selected").attr("value");
	if (taxCategory != 0) {
		var data = {
			"taxCategory" : taxCategory
		};
		var URL = 'ReceivableDemandEntry.html?selectTaxCategory';
		var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false, 'json');
		optionsAsString += "<option value='0'> Select</option>";
		for (var i = 0; i < ajaxResponse.length; i++) {
			optionsAsString += "<option value='" + ajaxResponse[i].lookUpId + "'>" + ajaxResponse[i].descLangFirst + "</option>";
		}
		$("#taxCategory" + (rowNo + 2)).append(optionsAsString);
		$("#accHead" + (rowNo/6)).find('option').remove().end().append("<option value='0'> Select</option>");
	}
}
function process(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function validateCustData(errorList) {
	var custMobile = $("#custMobile").val();
	var pinCode = $("#pinCode").val();
	var custEmailId = $("#custEmailId").val();
	
	if (custMobile != "") {
		var regexPattern = /^\d{10}$/;
		if (!regexPattern.test(custMobile) && custMobile != '') {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.mobileNo"));
		}
	}
	if (pinCode != "") {
		var regexPattern = /^[4]\d{5}$/;
		if (!regexPattern.test(pinCode) && pinCode != '') {
			errorList.push(getLocalMessage("receivable.demand.entry.validation.pincode"));
		}
	}
	if (custEmailId != "") {
		var regexPattern = /\S+@\S+\.\S+/;
		if (!regexPattern.test(custEmailId) && custEmailId != '') {
			errorList.push(getLocalMessage("accounts.customerMaster.error.validCustEmail"));
		}
	}
	return errorList;
}

function showTaxSubCategoryListForEdit(rowNo) {
	
	var optionsAsString = '';	
	var taxCategory = $("#taxCategory" + (rowNo + 1) + " option:selected").attr("value");
	if (taxCategory != 0) {
		var data = {
			"taxCategory" : taxCategory
		};
		var URL = 'ReceivableDemandEntry.html?selectTaxCategory';
		var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false, 'json');	
		for (var i = 0; i < ajaxResponse.length; i++) {
			optionsAsString += "<option value='" + ajaxResponse[i].lookUpId + "'>" + ajaxResponse[i].descLangFirst + "</option>";
		}
		$("#taxCategory" + (rowNo + 2)).append(optionsAsString);		
	}
}
