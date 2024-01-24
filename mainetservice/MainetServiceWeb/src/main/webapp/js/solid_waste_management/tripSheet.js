$(document).ready(function() {
	$(function() {
		$('.datetimepicker3').timepicker();
	});
	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	$("#id_tripSheetTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering":  false,
	    "order": [[ 1, "desc" ]]
	});
	sum();
	sub();
	yearLength();
});

function openaddtripsheet(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function yearLength(){
	var frmdateFields = $('#fromDate');
	var todateFields = $('#toDate');
	frmdateFields.each(function () {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
    todateFields.each(function () {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}

function Proceed(element) {
	var errorList = [];
	errorList = ValidateTripSheetMaster(errorList);
	errorList = errorList.concat(validateEntryDetails());
	let totalVol =  Number($('#id_total').val());
	let totalGarbage = Number($('#tripTotalgarbage').val());
	if(totalVol != totalGarbage){
		var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
		errorList.push( getLocalMessage("swm.validation.totalweightvalidation"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element,
				getLocalMessage('swm.saveTripSheetMaster'),
				'TripSheetMaster.html', 'saveform');
	}
}

function ValidateTripSheetMaster(errorList) {
	
	var tripDate = $("#tripDate").val();
	var veId = $("#veId").val();
	var beatNo= $("#beatNo").val();
	var tripIntime = $("#tripIntimeDesc").val();
	var tripOuttime = $("#tripOuttimeDesc").val();
	var tripEntweight = parseFloat($("#tripEntweight").val());
	var tripExitweight = parseFloat($("#tripExitweight").val());
	var tripTotalgarbage = $("#tripTotalgarbage").val();
	var deId = $("#deId").val();
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	if (tripDate == "" || tripDate == null)
		errorList.push( getLocalMessage("swm.validation.tripDate"));
	if (veId == "0" || veId == null)
		errorList.push( getLocalMessage("swm.validation.veId"));
	if (tripIntime == "" || tripIntime == null)
		errorList.push( getLocalMessage("swm.validation.tripIntime"));
/*	if (tripOuttime == "" || tripOuttime == null)
		errorList.push( getLocalMessage("swm.validation.tripOuttime"));*/
	if (isNaN(tripEntweight) || tripEntweight == "" || tripEntweight == null)
		errorList.push( getLocalMessage("swm.validation.tripEntweight"));
	if (tripTotalgarbage == "" || tripTotalgarbage == null)
		errorList.push(			 getLocalMessage("swm.validation.tripTotalgarbage"));
	if (deId == "0" || deId == null)
		errorList.push( getLocalMessage("swm.validation.deId "));
	if(tripOuttime != ""){
	if(tripOuttime < tripIntime){
		errorList.push(getLocalMessage("swm.validation.timevalidation "));
		}
	}
	if(tripEntweight < tripExitweight){
		errorList.push( getLocalMessage("swm.validation.weightvalidation"));
	}
	return errorList;
}

function addEntryData(tableId) {
	
	var errorList = [];
	errorList = validateEntryDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow(tableId,false);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateEntryDetails() {
	var errorList = [];
	
	var i = 0;
	var waste = [];
	var collTypArray=[];
	if ($.fn.DataTable.isDataTable('#id_tripsheetTbl')) {
		$('#id_tripsheetTbl').DataTable().destroy();
	}
	$("#id_tripsheetTbl tbody tr")
			.each(
					function(i) {
						var wastType = $("#wastType" + i).val();
						var tripVolume = $("#tripVolume" + i).val();
					/*	var collType =$("#collType" +i).val();
						var collCount= $("#collCount" + i ).val();*/
						waste.push($("#wastType" + i).val());
						/*collTypArray.push(collType);*/
						var rowCount = i + 1;

						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						
			/*			if (collType == "0" || collType == null) {
							errorList
									.push(
											 getLocalMessage("Please Select Collection Type")
											+ rowCount);
						}
						
						if (collCount == "" || collCount == null) {
							errorList
									.push(
											 getLocalMessage("Collection Count Unit can not be Empty ")
											+ rowCount);
						}*/

						if (wastType == "0" || wastType == null) {
							errorList
									.push(
											 getLocalMessage("swm.validation.wastType")
											+ rowCount);
						}

						if (tripVolume == "" || tripVolume == null) {
							errorList
									.push(
											 getLocalMessage("swm.validation.tripVolume")
											+ rowCount);
						}
						
				
						
						
					});
	i=0;
	var j = 1 ;
	var k = 1 ;
	var count = 0;
	for(i =0 ;i<=waste.length;i++){
		for( j=k ;j<=waste.length;j++){
			if(waste[i]==waste[j] ){
				if(count==0){
				errorList.push( getLocalMessage("swm.validation.wastTypechange"));
				}
				count++;
			}
		}k++;	
	}

	return errorList;
}
function deleteEntry(tableId, obj, ids) {
	if(ids != "1"){
	deleteTableRow(tableId, obj, ids, false);
	}
	sum();
}
function modifyTrip(tripId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : tripId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};
function searchTripSheet(formUrl, actionParam) {
	var fomdate=$('.fromDate').val();
	var typ=$('#veType').val();
	var veid = $('#veId').val();
	var todte=	 $('.toDate').val();
	var data = {
		"veType" : replaceZero1($('#veType').val()),
		"id" : replaceZero($('#veId').val()),		
		"fromDate" : $('.fromDate').val(),
		"toDate" : $('.toDate').val(),
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#veType').val(typ);
	prepareTags();
}
function replaceZero(value) {
	return value != 0 ? value : undefined;
}
function replaceZero1(value) {
	return value != 0 ? value : "";
}
function sum() {
	
	var i = 0.000;
	var sumadd = 0;
	$("#id_tripsheetTbl tbody tr").each(function(i) {
		var tripVolume = $("#tripVolume" + i).val();
		sumadd = (parseFloat(sumadd) + parseFloat(tripVolume)).toFixed(2);
	});
	if (isNaN(sumadd)) {
		$('#id_total').val(0.000);
	} else {
		$('#id_total').val(sumadd);
	}
}

function showVehicleRegNo(){
	
	$('#veId').html('');
	var crtElementId = $("#veType").val();
	var requestUrl = "TripSheetMaster.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#veId').append($("<option></option>").attr("value", "").attr("code", "").text("select"));
	$.each(ajaxResponse, function(index, value) {
		$('#veId').append($("<option></option>").attr("value", index).attr("code", index).text(value));
	});
	$('#veId').trigger('chosen:updated');
	
}


function ResetForm(resetBtn) {
	
	resetForm(resetBtn);
	;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'TripSheetMaster.html?AddTripSheetMaster', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();

	prepareTags();
}
function sub() {
	var sub = 0.000;
		var tripEntweight = $("#tripEntweight").val();
		var tripExitweight = $("#tripExitweight").val();
		sub = parseFloat(tripEntweight) - parseFloat(tripExitweight);
	if (isNaN(sub)) {
		$('#tripTotalgarbage').val(tripEntweight);
	} else {
		$('#tripTotalgarbage').val(sub);
	}
}