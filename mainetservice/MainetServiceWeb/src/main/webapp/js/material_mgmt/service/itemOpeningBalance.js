var url = "ItemOpeningBalance.html"
var currentRow;

$(document).ready(function() { debugger;
	
	$('.chosen-select-no-results').chosen();
	
	$("#balSerachFrm, #openingBalanceForm").validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#openingDate").datepicker({
		dateFormat : "dd/mm/yy",
		maxDate : new Date()
	}).datepicker("setDate", new Date());

	$('.datepickerMfg').datepicker({
		dateFormat : "dd/mm/yy",
		maxDate : new Date()
	});

	$('.datepickerExp').datepicker({
		dateFormat : "dd/mm/yy",
		minDate : +1
	});

	$("#methodTable").on('click', '.remOF', function() {
		var method = $("#valueMethodCode").val().trim();
		var rowCount = $('#methodTable tr').length;
		if (rowCount <= 2) {
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		}
		$(this).closest('#methodTable tr').remove();
		reOrderOpeningTabIdSequence('.firstRow', method);
	});

	$("#methodTable").on('click','.remEdit', function() {
		var rowCount = $('#methodTable tr').length;
		if (rowCount <= 2) {
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		} else {
			id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			currentRow = $(this).parent().parent();
			deleteOpeningBalanceDetails(id);
		}
	});
				
	timeRemover();
	
/*	$('#openingBalance').val(parseFloat($('#openingBalance').val()).toFixed(1));
	$('#unitPrice').val(parseFloat($('#unitPrice').val()).toFixed(2));
	$('.firstRow').each(function(i) {
		$("#quantity" + i).val(parseFloat($("#quantity" + i).val()).toFixed(1));
	});*/	
	
})
				
				
function timeRemover(){
	$('.firstRow').each(function(i) {
		var mfgDate = $("#mfgDate" + i).val();
		if (mfgDate)
			$("#mfgDate" + i).val(mfgDate.split(' ')[0]);
		var expiryDate = $("#expiryDate" + i).val();
		if (expiryDate)
			$("#expiryDate" + i).val(expiryDate.split(' ')[0]);
	});	
}


function proceed(element) {
	var requestData = $('form').serialize();
	var errorList = [];
	var management = $("#valueMethodCode").val().trim();
	errorList = validateOpeningForm(errorList);
	errorList = validateDetailsForm(errorList, management);
	
	if ("N" != management && errorList.length == 0) {
		var requestData = $('form').serialize();
		var duplicateItemNumbers = __doAjaxRequest(url + "?duplicateChecker", 'POST', requestData, false, 'json');
		if (duplicateItemNumbers.length > 0) {
			if ("B" == management)
				errorList.push(getLocalMessage("material.validation.Duplicate.Batch.Number.Batch.Number ") + " " + duplicateItemNumbers);
			else if ("S" == management)
				errorList.push(getLocalMessage("material.Duplicate.Serial.Number.Found.for.Serial.Number") + " " + duplicateItemNumbers);
		}
	}	
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		$("#labelItemNo ,#labelExpiryDate , #methodName").removeClass("mandColorClass");
	} else {
		var returnData = __doAjaxRequest(url + "?saveform", 'POST',	requestData, false, 'json');
		if ($.isPlainObject(returnData)) {
			var message = returnData.command.message;
			var hasError = returnData.command.hasValidationError;
			if (!message) {
				message = successMessage;
			}
			if (message && !hasError) {
				if (returnData.command.hiddenOtherVal == 'SERVERERROR')
					showSaveResultBox(returnData, message, 'AdminHome.html');
				else
					showSaveResultBox(returnData, message, url);
			} else if (hasError) {
				$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');
			}
			showloader(false);
			return false;
		}
	}
}

function reOrderOpeningTabIdSequence(firstRow, method) {
	$(firstRow).each(function(i) {
		$('.datepickerExp').removeClass("hasDatepicker");
		$('.datepickerMfg').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("name", "srNo" + i);
		$(this).find("input:text:eq(0)").attr("id", "srNo" + i);
		$("#srNo" + i).val(i + 1);

		$(this).find("input:text:eq(1)").attr( "name",
				"itemOpeningBalanceDto.itemOpeningBalanceDetDto[" + i + "].mfgDate");
		$(this).find("input:text:eq(1)").attr("id", "mfgDate" + i);

		$(this).find("input:text:eq(2)").attr("name",
				"itemOpeningBalanceDto.itemOpeningBalanceDetDto[" + i + "].expiryDate");
		$(this).find("input:text:eq(2)").attr("id", "expiryDate" + i);

		$(this).find("select:eq(0)").attr("name",
				"itemOpeningBalanceDto.itemOpeningBalanceDetDto[" + i + "].binLocId");
		$(this).find("select:eq(0)").attr("id", "binLocId" + i);

		if ("N" == method) {
			$(this).find("input:text:eq(3)").attr("name",
					"itemOpeningBalanceDto.itemOpeningBalanceDetDto[" + i + "].quantity");
			$(this).find("input:text:eq(3)").attr("id", "quantity" + i);
		} else {
			$(this).find("input:text:eq(3)").attr("name",
					"itemOpeningBalanceDto.itemOpeningBalanceDetDto[" + i + "].itemNo");
			$(this).find("input:text:eq(3)").attr("id", "itemNo" + i);

			if ("S" == method)
				$(this).find("input:text:eq(4)").attr("name",
						"itemOpeningBalanceDto.itemOpeningBalanceDetDto["+ i + "].quantity").val('1');
			else
				$(this).find("input:text:eq(4)").attr("name",
						"itemOpeningBalanceDto.itemOpeningBalanceDetDto["+ i + "].quantity");
			$(this).find("input:text:eq(4)").attr("id", "quantity" + i);
		}
		
		$(this).find("a:eq(0)").attr("id", "addUnitRow_" + i);
		$(this).find("a:eq(1)").attr("id", "deleteRow_" + i);
		$(this).find("input:hidden:eq(0)").attr("name",
				"itemOpeningBalanceDto.itemOpeningBalanceDetDto[" + i + "].openBalDetId");
		$(this).find("input:hidden:eq(0)").attr("id", "openBalDetId" + i);
		datePickerLogic();
	});
}

function showConfirmBox(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	var C = "C";
	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeBox();"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function validateOpeningForm(errorList) {
	var storeId = $("#storeId").val();
	var storeStatus = $("#storeId option:selected").attr("code");
	var openingDate = $("#openingDate").val();
	var itemId = $("#itemId").val();
	var openingBalance = $("#openingBalance").val();
	var unitPrice = $("#unitPrice").val();
	
	if (storeId == '' || storeId == undefined || storeId == "0")
		errorList.push(getLocalMessage("material.item.pleaseSelectStoreName"));
	if ("Y" != storeStatus)
		errorList.push(getLocalMessage("store.inactive.validation"));
	if (openingDate == '' || openingDate == undefined)
		errorList.push(getLocalMessage("material.item.pleaseEnterOpeningDate"));
	if (itemId == '' || itemId == undefined || itemId == "0")
		errorList.push(getLocalMessage("material.item.pleaseSelectItemName"));
	if (openingBalance == '' || openingBalance == undefined)
		errorList.push(getLocalMessage("material.item.pleaseEnterOpeningBalance"));
	if (unitPrice == '' || unitPrice == undefined)
		errorList.push(getLocalMessage("material.item.pleaseEnterUnitPrice"));

	var totalQuantity = parseInt("0");
	$('.firstRow').each(function(i) {
		totalQuantity += parseInt($.trim($("#quantity" + i).val()));
	});
	if (parseInt($("#openingBalance").val()) != totalQuantity)
		errorList.push(getLocalMessage("material.item.quantity.sum.opening.balance.validate"));
	return errorList;
}

function validateDetailsForm(errorList, method) {
	$('.firstRow').each(function(i) {
		var mfgDate = $.trim($("#mfgDate" + i).val());
		if (mfgDate == null || mfgDate == "")
			errorList.push(getLocalMessage("material.item.mfgDateforRow") + (i + 1));

		if ($("#isExipryId").val() == 'Y' || $("#isExpiry").val() == 'Y') {
			var expiryDate = $.trim($("#expiryDate" + i).val());
			if (expiryDate == null || expiryDate == "")
				errorList.push(getLocalMessage("material.item.expDateforRow") + (i + 1));
		}
		
		var binLocId = $.trim($("#binLocId" + i).val());
		if (binLocId == 0 || binLocId == "")
			errorList.push(getLocalMessage("material.item.selectBinLocationForRow") + (i + 1));
		
		if ("N" != method) {
			var itemNo = $.trim($("#itemNo" + i).val());
			if (itemNo == null || itemNo == "") {
				if ("S" == method)
					errorList.push(getLocalMessage("material.item.enterSerialNumberForRow") + (i + 1));
				else
					errorList.push(getLocalMessage("material.item.enterBatchNumberForRow") + (i + 1));
			}
		}

		var quantity = $.trim($("#quantity" + i).val());
		if (quantity == null || quantity == "")
			errorList.push(getLocalMessage("material.item.enterQuantityForRow") + (i + 1));
	});					
	return errorList;
}

function datePickerLogic() {
	$('.datepickerMfg').datepicker({
		dateFormat : "dd/mm/yy",
		maxDate : new Date()
	});

	$('.datepickerExp').datepicker({
		dateFormat : "dd/mm/yy",
		// minDate : new Date()
		minDate : +1
	});
	
	$('.hasNumber').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	  
	});
}

function getMethodForm(element) {
	var selectedValue = $("#itemId").find("option:selected").val();
	$("#group , #subGroup , #uom , #valueMethod , #valueMethodCode , #methodName , #isExipryId , #expiryType, #methodName, #itemNoLabel,expiryDateLabel")
			.val('');
	if (selectedValue == "" || selectedValue == "0") {
	} else {
		var requestData = {
			"itemId" : selectedValue
		};
		var response = __doAjaxRequest(url + '?ItemDetails', 'POST',
				requestData, false, 'json');
		$("#group").val(response.itemGroupDesc);
		$("#subGroup").val(response.itemSubGroupDesc);
		$("#uom").val(response.uomDesc);
		$("#valueMethod").val(response.managementDesc);
		$("#valueMethodCode").val(response.itemCode); // management method
														// code
		$("#methodName").text(response.managementDesc);
		$("#methodName span").removeClass("mandColorClass");
		var methodData = {
			"methodType" : response.itemCode
		};
		var ajaxResponse = doAjaxLoading(url + '?FindMethod', methodData,
				'html', '');

		$("#appendtable").removeClass('ajaxloader');
		$("#appendtable").html(ajaxResponse);
		if ('B' == response.itemCode) {
			$("#labelItemNo").text("Batch Number");
			$("#labelItemNo").removeClass("mandColorClass");
		}
		if (response.isExpiry != 'Y') {
			$("[id^=expiryDate]").attr("disabled", true);
			// $("#table th:nth-child(3)").removeClass("required-control");
			// //TODO required mandatory class
		} else {
			$("#isExipryId").val(response.isExpiry);
			$("#expiryType").val(response.expiryTypeDesc); //expirycode
		}
		if ('WE' == response.expiryTypeDesc) {
			$("#labelExpiryDate").text("Warrenty End");
			$("#labelExpiryDate span").removeClass("mandColorClass");
		}
	}
}

function deleteOpeningBalanceDetails(id) {
	var yes = getLocalMessage('eip.commons.yes');
	var warnMsg = getLocalMessage("material.item.wantToDelete");
	message = '<p class="text-blue-2 text-center padding-15">' + warnMsg
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="doDeletion(\'' + id + '\')"/>'
			+ '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;
}

var removeIdArray = [];
function doDeletion(id) {
	var method = $("#valueMethodCode").val().trim();
	if (id != '') {
		requestData = {
			"id" : id
		};
		var response = __doAjaxRequest(url + '?doDeletion', 'POST', requestData, false, 'json');
		removeIdArray.push(id);
		$('#removeChildIds').val(removeIdArray);
	}
	currentRow.remove();
	reOrderOpeningTabIdSequence('.firstRow', method);
	closeFancyOnLinkClick(childDivName);
}



function addUnitRow(){
	var errorList = [];
	var method = $("#valueMethodCode").val().trim();
	$('.datepickerExp').removeClass("hasDatepicker");
	$('.datepickerMfg').removeClass("hasDatepicker");
	if("N" != method)
		errorList = validateUniqueItemNo();
	errorList = validateDetailsForm(errorList, method);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		$("#labelItemNo").removeClass("mandColorClass");
		$("#labelExpiryDate").removeClass("mandColorClass");
		return false;
	} else {
		var content = $("#methodTable").find('tr:eq(1)').clone();
		$("#methodTable").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('');
		$('.error-div').hide();
		datePickerLogic();
		reOrderOpeningTabIdSequence('.firstRow', method);	
	}
}	



function validateUniqueItemNo(){
	$("#errorDiv").hide();
	var itemNoArray=[];
	var errorList = [];
	var management = $("#valueMethodCode").val();
	$('.firstRow').each(function(i){
		var itemNo = $("#itemNo" + i).val();
		if (itemNoArray.includes(itemNo)){
			if("S" == management)
				errorList.push(getLocalMessage("material.Duplicate.Serial.Number.Found.for.Serial.Number")+ " " + itemNo);
			else
				errorList.push(getLocalMessage("material.validation.Duplicate.Batch.Number.Batch.Number ")+ " " + itemNo);
		}		
		itemNoArray.push(itemNo);
		$('#itemNumberList').val(itemNoArray);
	});		
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}


$('.hasCharacterNumbers').on('keyup', function() {
	this.value = this.value.replace(/[^a-z A-Z 0-9]/g, '');
});