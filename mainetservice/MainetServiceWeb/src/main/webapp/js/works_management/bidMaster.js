var bidIdList = new Array();
$(document).ready(function() {

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	checkTenderType();
	calculateTotal();
})

function checkTenderType() {
	var tenderType = $("#tenderType" + 0).find("option:selected").attr('code');
	if (tenderType == "ITR"){
		$("#itemRateDivision").show();
		$("#percenttype" + 0).prop("disabled", true);
		$('#percenttype' + 0).prop('selectedIndex', 0);
		$("#percentvalue" + 0).prop("disabled", true);
	}		
	else
		$("#itemRateDivision").hide();
}

function calculateTotal() {
	if ($.fn.DataTable.isDataTable('#itemDetailsTableID'))
		$('#itemDetailsTableID').DataTable().destroy();
	
	var totalAmount = 0.00;
	$(".itemDetailRow").each(function(i) {
		var quantity = $("#quantity" + i).val();
		var perUnitRate = $("#perUnitRate" + i).val();
		var amount = $("#amount" + i).val();
		
		if (perUnitRate) {
			amount = quantity * perUnitRate;
			$("#amount" + i).val(amount.toFixed(2));
			totalAmount += amount;
		}
	});
	$("#quotedPrice" + 0).val(totalAmount.toFixed(2));
	$("#quotedPrice").val(totalAmount.toFixed(2));
}


function openBidCompareForm(tndId) {
	

	var requestdata = {
		"tndId" : tndId
	};

	var divName = '.content-page';

	var ajaxResponse = doAjaxLoading("BIDMaster.html?openBidCompareForm",
			requestdata, 'html', divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

	$('#searchAssetHome').on('click', 'tbody tr .selectedRow', function() {
		
		var value = $(this).attr("value");
		if (this.checked) {
			bidIdList.push(value);
		} else {
			bidIdList.pop(bidIdList.indexOf(value), 1);
		}
	});
}

function showBIDSearchForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading("BIDMaster.html?openBIDCompareSearchForm",
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchTender() {
	
	var tenderNo = $("#tndNo").val();
	var tenderDate = $("#tenderDate").val();

	var errorList = [];
	if (tenderNo == "0" && tenderDate == "") {
		errorList.push(getLocalMessage("wms.select.oneField"));
	}
	if (errorList.length != 0) {
		displayErrorsOnPage(errorList);
	} else {
		var requestdata = {
			"tenderNo" : tenderNo,
			"tenderDate" : tenderDate,

		};

		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading("BIDMaster.html?searchTender",
				requestdata, 'html', divName);

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();

	}
}

function getBidDetailsToCompare() {
	
	var bidId = $("#bidId").val();
	var vendorId = $("#vendorId").val();

	var errorList = [];
	if (vendorId == "" && bidId == "") {
		errorList.push(getLocalMessage("wms.enter.bidId.vendor"));
	}
	if (errorList.length != 0) {
		displayErrorsOnPage(errorList);
	} else {

		if (bidIdList != 'undefined' && bidIdList.length > 0 || bidId != "") {

			var requestdata = {
				"bidId" : bidId,
				"vendorIds" : bidIdList.toString()
			};

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading("BIDMaster.html?searchBIdDetByVendorIdandBidId",
					requestdata, 'html', divName);

			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}

	}

}


function searchForm(formUrl, actionParam) { 
	var errorList = [];

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var projId = $("#projId").val();
		var tndNo = $("#tndNo").val();
		
		if(projId == '' && tndNo == '0'){
			errorList.push(getLocalMessage('work.management.valid.select.any.field'));
			displayErrorsOnPage(errorList);
			return false;
		} 
		
		var requestdata = {
			"projId" : projId,
			"tndNo" : tndNo
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestdata, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function bidTechnicalEntry(formUrl, actionParam, bidId) {
	
		
	var divName = '.content-page';
	var requestdata = {
		"bidId" : bidId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function calculateTotalAmount(index) {
	var tenderType = $("#tenderType" + index).find("option:selected").attr(
			'code');
	var percentvalue = $("#percentvalue" + index).val();
	var workEstimateAmt = $("#workEstimateAmt").val();
	var percenttype = $("#percenttype" + index).find("option:selected").attr(
			"code");
	var quotedPrice = "";

	if (tenderType == "PER") {
		$("#percenttype" + index).prop("disabled", false);
	} else if (tenderType == "ITR") {
		$("#percenttype" + index).prop("disabled", true);
		$('#percenttype' + index).prop('selectedIndex', 0);
		$("#percentvalue" + index).prop("disabled", true);
	} else {
		$("#percenttype" + index).prop("disabled", true);
		$('#percenttype' + index).prop('selectedIndex', 0);
	}

	if (tenderType != "" && percentvalue != "" && workEstimateAmt != "") {
		if (tenderType == "PER") {
			if (percenttype == "B") {
				quotedPrice = (Number(workEstimateAmt
						* Number(100 - Number(percentvalue)) / 100)).toFixed(2);
				$("#quotedPrice" + index).val(quotedPrice);
			} else {
				quotedPrice = (Number(workEstimateAmt
						* Number(100 + Number(percentvalue)) / 100)).toFixed(2);
				$("#quotedPrice" + index).val(quotedPrice);
			}
		} else if (tenderType == "AMT") {
			$("#quotedPrice" + index).val(percentvalue);
		} else if (tenderType == "MUL") {
			$("#quotedPrice" + index).val(
					(workEstimateAmt * percentvalue).toFixed(2));
		}
	}

}

function gettender() {

	var divName = '.content-page';
	var projId = $("#projId").val();
	var url = "BIDMaster.html?gettender";
	var requestData = "projId=" + $("#projId").val();
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();

}

function getTenderData() {

	var divName = '.content-page';
	var projId = $("#projId").val();

	var url = "BIDMaster.html?getTenderData";
	var requestData = "projId=" + $("#projId").val();
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	$("#projId").val(projId);

}

function deleteEntry(obj, ids) {

	var rowCount = $('#bidentryDetail >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("Cannot delete"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	deleteTableRow('bidentryDetail', obj, ids);
	$('#bidentryDetail').DataTable().destroy();
	triggerTable();
}

function addEntryData() {
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		addTableRow('bidentryDetail');
	} else {
		$('#bidentryDetail').DataTable();
		displayErrorsOnPage(errorList);
	}

}

function addtechnicalData() {
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		var evaluationValue = $('#evaluationc0').val();
		addTableRow('technicalevaluation');
		setEvaluationForProceedingBidders(evaluationValue);
	} else {
		$('#technicalevaluation').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function setEvaluationForProceedingBidders(value) {

	$('input[id^="evaluationc"]').each(function(index) {
		if (index !== 0) {
			$(this).val(value);
		}
	});
}

function deletetechnical(obj, ids) {

	var rowCount = $('#technicalevaluation >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("Cannot delete"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	deleteTableRow('technicalevaluation', obj, ids);
	$('#technicalevaluation').DataTable().destroy();
	triggerTable();
}

function saveForm(obj) {
	
	var errorList = [];
	errorList = validateBid(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	} else {
	var divName = '.content-page';
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var URL = 'BIDMaster.html?saveData';
	var ajaxResponse = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	showConfirmBox();
}
}

function validateBid(errorList) {
	
	var errorList = [];
	var projId = $("#projId").find("option:selected").attr('value');
	var tndNo = $("#tndNo").val();

	if (projId == "" || projId == null){
		errorList.push(getLocalMessage("Bidadd.Validation.Project"));
	}
	if (tndNo == "0" || tndNo == null){
		errorList.push(getLocalMessage("Bidadd.Validation.tender"));
	}
	$("#bidentryDetail tbody tr")
			.each(
					function(i) {
						
						var bidId = $("#bidId" + i).val();
						var vendorName = $("#vendorName" + i).val();
						var rowCount = i + 1;

						if (bidId == 0 || bidId == null) {
							errorList.push(getLocalMessage("Bidadd.Validation.Bidid")+ " " + rowCount);
						}
						if (vendorName == 0 || vendorName == null) {
							errorList.push(getLocalMessage("Bidadd.Validation.Bidname")+ " " + rowCount);
						}
					});
	return errorList;
}

function showConfirmBox() {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	var msg = getLocalMessage("works.data.saved");

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ msg + '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceedAuth()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();

	showModalBoxWithoutClose(errMsgDiv);
}

function proceedAuth(){
    
	var divName	=	formDivName;
	var requestData = {};
	var ajaxResponse = doAjaxLoading("BIDMaster.html?bidEntryReset",
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
    $.fancybox.close();
}

function saveTechForm(obj) {
	
	var errorList = [];
	errorList = validateTech(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	} else {
	var divName = '.content-page';
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var URL = 'BIDMaster.html?saveTechData';
	var ajaxResponse = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	showConfirmBox();
}
	
function validateTech(errorList) {

	var errorList = [];
	$("#technicalevaluation tbody tr")
		.each(
				function(i) {

					var evaluationc = $("#evaluationc" + i).val();
					var criteria = $("#criteria" + i).val();
					var acceptreject = $("#acceptreject" + i).val();
					var rowCount = i + 1;

					if (evaluationc == 0 || evaluationc == null) {
						errorList.push(getLocalMessage("bidtechnical.evaluationc")+ " " + rowCount);
					}
					if (criteria == 0 || criteria == null) {
						errorList.push(getLocalMessage("bidtechnical.criteria")+ " " + rowCount);
					}
					if (acceptreject == 0 || acceptreject == null) {
						errorList.push(getLocalMessage("bidtechnical.acceptreject")+ " " + rowCount);
					}
				});
	
	$("#financialevaluation tbody tr").each(function(i) {
		var tenderType = $("#tenderType" + i).val();
		var percenttype = $("#percenttype" + i).val();
		var percentvalue = $("#percentvalue" + i).val();
		var quotedPrice = $("#quotedPrice" + i).val();
		var tenderTypeCode = $("#tenderType" + 0).find("option:selected").attr('code');
		var rowCount = i + 1;

		if (tenderType == 0 || tenderType == null || tenderType == "") {
			errorList.push(getLocalMessage("Bidadd.Validation.tendertype"));
		}
		if (tenderTypeCode == "PER") {
			if (percenttype == 0 || percenttype == null || percenttype == "") {
				errorList.push(getLocalMessage("Bidadd.Validation.percenttype"));
			}
		}
		if (tenderTypeCode != "ITR" && (percentvalue == null || percentvalue == "")) {
			errorList.push(getLocalMessage("Bidadd.Validation.percentvalue"));
		}
		if (quotedPrice == null || quotedPrice == "") {
			errorList.push(getLocalMessage("bidfinancial.quotedAmount"));
		}
				
		if (tenderTypeCode == "ITR") {
			$(".itemDetailRow").each(function(i) {
				var itemId = $("#itemId" + i).val();
				var itemName = $("#itemName" + i).val();
				var quantity = $("#quantity" + i).val();
				var perUnitRate = $("#perUnitRate" + i).val();
				var amount = $("#amount" + i).val();
				if (!itemId || !itemName)
					errorList.push(getLocalMessage("Item Name Can Not Be Empty") + rowCount);
				if (!quantity)
					errorList.push(getLocalMessage("Quantity Required Can Not Be Empty") + rowCount);
				if (!perUnitRate)
					errorList.push(getLocalMessage("Please Enter Per Unit Rate") + rowCount);
				if (!amount)
					errorList.push(getLocalMessage("Amount Can Not Be Empty") + rowCount);
			});
		}
	});
	return errorList;
	}
}

function addBidEntry(formUrl, actionParam) {
	
	var divName	=	formDivName;
	var ajaxResponse	=	doAjaxLoading(formUrl+'?'+actionParam,{},'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
}

function resetForm() { 

	var divName	=	formDivName;
	var requestData = {};
	var ajaxResponse = doAjaxLoading("BIDMaster.html?bidEntryReset",
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
}

function CheckName(i) {
    var errorList = [];
    
    var data = {
    		
        "bidNo": $('#bidId'+i).val(),
        
    };
    var divName = '.content-page';
    var ajaxResponse = __doAjaxRequest("BIDMaster.html?CheckName",'POST', data, false, 'json');
    if (!ajaxResponse) {
        $('#bidId'+i).val("");
        errorList.push(getLocalMessage("duplicate.bidid"));
        displayErrorsOnPage(errorList);
    }
}


function formatDecimalInput(input, decimalPlaces) {
	
    var value = input.value;
    value = value.replace(/[^0-9.]/g, '');
    var parts = value.split('.');
    if (parts.length > 2) {
        value = parts[0] + '.' + parts[1];
    } else if (parts.length === 2 && parts[1].length > decimalPlaces) {
        value = parts[0] + '.' + parts[1].substring(0, decimalPlaces);
    }
    input.value = value;
}



function viewBidTechnicalEntry(formUrl, actionParam, bidId) {
	var data = {
		"bidId": bidId,
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function editBidTechnicalEntry(formUrl, actionParam, bidId) {
	var data = {
		"bidId": bidId,
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function viewBidFinancialEntry(formUrl, actionParam, bidId) {
	var data = {
		"bidId": bidId,
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function editBidFinancialEntry(formUrl, actionParam, bidId) {
	var data = {
		"bidId": bidId,
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function bidValidFunction(bidId) {
	
	var errorList = [];
	var bidId=bidId;
	if(bidId!=0){
		errorList.push(getLocalMessage("Bidadd.Validation.techinical.evaluation"));	
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	}	
}


