$(document).ready(function() {
    $('.monthPick').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	yearRange : "-100:-0",
    });

    $("#datatables").dataTable({
	"oLanguage" : {
	    "sSearch" : ""
	},
	"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	"iDisplayLength" : 5,
	"bInfo" : true,
	"lengthChange" : true

    });

});

function addHomeComposting(formUrl, actionParam) {
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();

}
function backHomeCompostingForm() {
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'HomeCompostingForm.html');
    $("#postMethodForm").submit();
}

function addEntryData(tableId) {
    
    var errorList = [];
    errorList = validateWetDetails();
    if (errorList.length == 0) {
	$("#errorDiv").hide();
	addTableRow(tableId, false);

    } else {
	displayErrorsOnPage(errorList);
    }
}
function deleteEntry(obj, ids) {
    
    var totalWeight = 0;
    deleteTableRow('firstWetWasteRow', obj, ids);
    $('#firstWetWasteRow').DataTable().destroy();
    triggerTable();
}

function saveHomeComposting(element) {
    
    var errorList = [];
    errorList = validateForm(errorList);
    errorList = errorList.concat(validateWetDetails(errorList));
    if (errorList.length > 0) {
	displayErrorsOnPage(errorList);
    } else {
	return saveOrUpdateForm(element,
		getLocalMessage('swm.save.homeMinister.success'),
		'HomeCompostingForm.html', 'saveform');
    }

}

function getcitizenMasterData(formUrl, actionParam, registrationid) {
    
    var data = {
	"registrationid" : registrationid
    };
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

function searchCitizen() {
    
    var data = {
	"zone" : $('#swCod1').val(),
	"ward" : $('#swCod2').val(),
	"block" : $('#swCod3').val(),
	"route" : $('#swCod4').val(),
	"house" : $('#swCod5').val(),
	"mobileNo" : $('#mobileNo').val(),
	"name" : $('#name').val(),

    };
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading('HomeCompostingForm.html?searchCitizen',
	    data, 'html', divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();

}

function checkDuplicateItemName() {
    
    $("#wetWasteCompostingTable tbody tr").each(function(i) {
	
	var itemName = $("#itemName" + i).val().trim();
	var duplicate = '';
	var myarray = new Array();
	if (itemName != '') {
	    for (j = 0; j < i; j++) {
		myarray[j] = $("#itemName" + j).val();
	    }
	}
	if (myarray.includes(itemName)) {
	    duplicate = 'Duplicate item names are not allowed';
	}
	if (duplicate != '') {
	    $("#itemName" + i).val('');
	    var errorList = [];
	    errorList.push(duplicate);
	    displayErrorsOnPage(errorList);

	}

    });
}

function validateForm(errorList) {

    var nameOfCitizen = $('#nameOfCitizen').val();
    var address = $('#address').val();
    var mobileNo = $('#mobileNo').val();
    var composeDate = $('#composeDate').val();

    if (nameOfCitizen = null || nameOfCitizen == "") {
	errorList.push(getLocalMessage("swm.home.composting.validate.name"));
    }
    if (address = null || address == "") {
	errorList.push(getLocalMessage("swm.home.composting.validate.address"));
    }
    if (mobileNo = null || mobileNo == "") {
	errorList
		.push(getLocalMessage("swm.home.composting.validate.mobileNo"));
    }
    if (composeDate = null || composeDate == "") {
	errorList
		.push(getLocalMessage("swm.home.composting.validate.compostDate"));
    }

    return errorList;
}

function validateWetDetails(errorList) {
    var errorList = [];
    var i = 0;
    if ($.fn.DataTable.isDataTable('#wetWasteCompostingTable')) {
	$('#wetWasteCompostingTable').DataTable().destroy();
    }
    $("#wetWasteCompostingTable tbody tr")
	    .each(
		    function(i) {
			
			var itemName = $("#itemName" + i).val();
			var rowCount = i + 1;
			if (itemName == "" || itemName == undefined
				|| itemName == "0") {
			    errorList
				    .push(getLocalMessage("swm.home.composting.validate.itemName")
					    + rowCount);
			}

		    });
    return errorList;

}