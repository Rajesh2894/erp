$(document).ready(function() { 
	
	$('.chosen-select-no-results').chosen();

	$('#binDefMasterFrm,#binLocMasFrm').validate({
		onkeyup: function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout: function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#searchBinDef, #searchBinLoc").dataTable({
		"oLanguage": {
			"sSearch": ""
		},
		"aLengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
		"iDisplayLength": 5,
		"bInfo": true,
		"lengthChange": true
	});

	$("#AddBinDefintion").click(
		function() {
			editViewFormBin('', 'C');
		});

	$("#AddBinLocation").click(
		function() {
			editViewFormBinLoc('', 'C');
		});
});

function proceed(element) {
	var errorList = [];
	errorList = validateBinDefMasterForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element,
			getLocalMessage('master.form.submit.success'),
			'BinDefMaster.html', 'saveform');
	}

}

function validateBinDefMasterForm(errorList) {
	var errorList = [];
	if ($("#defName").val() == '' || $("#defName").val() == undefined) {
		errorList.push(getLocalMessage("error.bindef.defName"));
	}
	if ($("#priority").val() == '' || $("#priority").val() == undefined) {
		errorList.push(getLocalMessage("error.bindef.priority"));
	} else {
		if ($("#priority").val() == '' || $("#priority").val().length > 4) {
			errorList.push(getLocalMessage("error.bindef.priority.length"));
		}
		checkIsDigit($('#priority').val(), errorList);
	}
	if ($("#description").val() == '' || $("#description").val() == undefined) {
		errorList.push(getLocalMessage("error.bindef.description"));
	}

	return errorList
}

function checkIsDigit(val, errorList) {

	if (!/^[0-9]+$/.test(val)) {
		errorList.push(getLocalMessage("error.bindef.priority.digit"));
	}
}


function proceedLocMas(element) {
	var errorList = [];
	errorList = validateBinLocationMasForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element,
			getLocalMessage('master.form.submit.success'),
			'BinLocationMas.html', 'saveform');
	}

}

function validateBinLocationMasForm(errorList) {
	var errorList = [];
	var storeId = $("#storeId").val();
	var storeStatus = $("#storeId option:selected").attr("code");
	var storeLocation = $("#storeLocation").val();
	var storeAdd = $("#storeAdd").val();
	var binLocation = $("#binLocation").val();
	var definitions = $("#definitions").val();
	if (storeId == '' || storeId == undefined || storeId == "0")
		errorList.push(getLocalMessage("error.binloc.storeName"));
	if ("Y" != storeStatus)
		errorList.push(getLocalMessage("store.inactive.validation"));
	if (storeLocation == '' || storeLocation == undefined)
		errorList.push(getLocalMessage("error.binloc.storeLocation"));
	if (storeAdd == '' || storeAdd == undefined)
		errorList.push(getLocalMessage("error.binloc.storeAdd"));
	if (binLocation == '' || binLocation == undefined)
		errorList.push(getLocalMessage("error.binloc.locationName"));
	if (definitions == 0 || definitions == '' || definitions == undefined)
		errorList.push(getLocalMessage("error.binloc.definitions"));
	return errorList
}


function homePage() {
	window.location.href = 'AdminHome.html';
}

function editViewFormBin(bindefId, type) {
	var requestData = 'binDefId=' + bindefId + '&type=' + type;
	var response = __doAjaxRequest('BinDefMaster.html?form', 'POST', requestData,
		false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function editViewFormBinLoc(binLocId, type) {
	var requestData = 'binLocId=' + binLocId + '&type=' + type;
	var response = __doAjaxRequest('BinLocationMas.html?form', 'POST', requestData,
		false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function getStoreDetails() {
	var storeId = $("#storeId").val();
	var requestData = {
		"storeId": storeId,
	};
	if (storeId == "") {
		$('#storeLocation').val('');
		$('#storeAdd').val('');
	} else {
		var storeDetail = __doAjaxRequest("BinLocationMas.html?getStoreDetails", 'POST', requestData, false, 'json');
		$.each(storeDetail, function(key, value) {
			if (key == 'location')
				$('#storeLocation').val(value);
			if (key == 'address')
				$('#storeAdd').val(value);
		});
	}
}