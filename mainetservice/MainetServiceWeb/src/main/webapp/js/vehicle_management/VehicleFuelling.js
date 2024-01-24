$(document).ready(function(){
	var table = $('.sm').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true,
	    "ordering":  false,
	    "order": [[ 1, "desc" ]]
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
	sum();
		
});	

function addVehicleFueling(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function deleteVehicleFuellingData(formUrl, actionParam, vefId) {
	if (actionParam == "deleteVehicleFueling") {
		showConfirmBoxForDelete(vefId, actionParam);
	}		
}

function getPumpType() {
	
	var requestData = {
		'pumpId' : $("#puId").val(),
	}
	var url = 'vehicleFuel.html?fetchVehicleFuelingDetails';
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$("#pumpFuelDets").html(ajaxResponse);
	prepareTags();
}
function showConfirmBoxForDelete(vefId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'+ getLocalMessage('Do you want to delete?') +'</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + vefId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(vefId) {
	$.fancybox.close();
	var requestData = 'vefId=' + vefId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('vehicleFuel.html?'+ 'deleteVehicleFueling', requestData, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchVehicleFueling(){
	
	var data = {
			"vehicleType":replaceZero($("#veVetype").val()),
			"pumpId":replaceZero($("#puId").val()),
			"fromDate":replaceZero($("#fromDate").val()),
			"todDate":replaceZero($("#toDate").val())
	};
		var divName = '.content-page';
		var url = "vehicleFuel.html?searchVehicleFueling";
		var ajaxResponse = doAjaxLoading(url , data, 'html',divName);
		$('.content').removeClass('ajaxloader');		
		$(divName).html(ajaxResponse);
		prepareTags();
}

function searchVehicleFuelling(formUrl, actionParam) {
	
	var data = {	
			"vehicleType":replaceZero($("#veVetype").val()),
			"pumpId":replaceZero($("#puId").val()),
			"fromDate":replaceZero($("#fromDate").val()),
			"todDate":replaceZero($("#toDate").val())
			
	};
	var divName = '.content-page';
	var formUrl = "vehicleFuel.html?searchVehicleFueling";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html',divName);
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getVehicleFuellingData(formUrl, actionParam, vefId) {  
	
	var divName = '.content-page';
	var data= {
		"vefId":vefId	
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	targetInfo();
}

function getVehicleFuellingDataView(formUrl, actionParam, vefId) {
	
	var data = {
		"vefId":vefId	
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveVehicleFuellingData(element) {

	var errorList = [];
	errorList = validateFormDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	else{
		// validate date's with vehicle purchase date start
		var veNo = $("#vemId").val();
		data = {
			"veNo" : veNo
		}
		var url = "vehicleFuel.html?searchVeNoData";
		var returnData = __doAjaxRequest(url, 'post', data, false, 'json');
	
		var vePurDate1 = returnData.vehiclePurchaseDate;
		var veFromDate1 = returnData.vehicleFromDate;
		var veToDate1 = returnData.vehicleToeDate;
		var vefDate = $("#vefDate").val();
		var invoiceDate = $("#vefDmdate").val();
	
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var vefDate11 = new Date($("#vefDate").val().replace(pattern, '$3-$2-$1'));// fule
		// date
		var invoiceDate22 = new Date($("#vefDmdate").val().replace(pattern,
				'$3-$2-$1'));// Advice date
		if (vePurDate1 != null) {
			var vePurDate11 = new Date(vePurDate1.replace(pattern, '$3-$2-$1'));
			if (invoiceDate22 != null && vePurDate11 != null) {
				if (invoiceDate22 < vePurDate11) {
					// errorList.push(getLocalMessage("Advice Date cannot be greater
					// than purchase Date"));
					errorList
							.push((getLocalMessage("vehicle.fueling.validation.date1"))
									+ vePurDate1);
				}
			}
			if (vefDate11 != null && vePurDate11 != null) {
				if (vefDate11 < vePurDate11) {
					// errorList.push(getLocalMessage("Vehicle fueling Date cannot
					// be greater than purchase Date"));
					errorList
							.push((getLocalMessage("vehicle.fueling.validation.date2"))
									+ vePurDate1);
				}
			}
	
		}
		if (veFromDate1 != null && veToDate1 != null) {
			var veFromDate11 = new Date(veFromDate1.replace(pattern, '$3-$2-$1'));
			var veToDate11 = new Date(veToDate1.replace(pattern, '$3-$2-$1'));
			var dash = "-";
			var spc = ")";
	
			if (veFromDate11 != null && veToDate11 != null && invoiceDate22 != null) {
				if (invoiceDate22 < veFromDate11 || invoiceDate22 > veToDate11) {
					errorList.push(getLocalMessage('vehicle.adviceDate.valid')
							+ veFromDate1 + dash + veToDate1 + spc);
				}
	
			}
			if (veFromDate11 != null && veToDate11 != null && vefDate11 != null) {
				if (vefDate11 < veFromDate11 || vefDate11 > veToDate11) {
					errorList.push(getLocalMessage('vehicle.fullingDate.valid')
							+ veFromDate1 + dash + veToDate1 + spc);
				}
			}
	
		}
		// validate date's with vehicle purchase date end
		if (errorList.length == 0) {
			var data = {
				"vehicleId" : $('#vemId').val()
			};
			var url = "vehicleFuel.html?lastMeterReading";
			var lastMeterReading = __doAjaxRequest(url, 'post', data, false, 'json')
			var curMeterReading = $('#vefReading').val();
			var mode = $('#mode').val();
			if (curMeterReading <= lastMeterReading && mode != 'E') {
				errorList
						.push(getLocalMessage("vehicle.fueling.validation.enter.valid.meter.reading")
								+ lastMeterReading);
			}
		}
		errorList = validateEntryDetails(errorList);
		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();
			return saveOrUpdateForm(element,
					getLocalMessage('vehicle.fuelling.add.success'),
					'vehicleFuel.html', 'saveform');
		}
	}
}

function targetInfo() {
	var pumpId = $("#puId" + " option:selected").attr("value");
	if (pumpId != 0) {
		var data = {
			"pumpId" : pumpId
		};
		var URL = 'vehicleFuel.html?fetchVehicleFuelingDetails';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
		$("#pumpFuelDets").html(returnData);
	} else {
		$("#pumpFuelDets").html("");
	}
	prepareTags();
	fuelTypeCheck();
}

function addEntryData() {
	
	sum();
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateEntryDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('vehicleFuelling');
	} else {
		$('#vehicleFuelling').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function validateVehicleReading(formUrl, actionParam){
	var reading = $("#vefReading").val();
	var vemId = $("#vemId").val();
	var data = {
			"vemId":vemId,
			"reading":reading
		};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html');
	return reading;
}

function process(date){
	
	   var parts = date.split("/");
	   return new Date(parts[2], parts[1] - 1, parts[0]);
	}

function validateFormDetails(errorList){
	
	var vehicleType = $("#veVetype").val();
	var pumpId = $("#puId").val();
	var vefDate = $("#vefDate").val();
	var veNo = $("#vemId").val();
	var vefReading = $("#vefReading").val();
	//var driverName = $("#driverName").val();
	var driverName = $("#veDriverName").val();
	var invoiceNumber = $("#vefDmno").val();
	var invoiceDate = $("#vefDmdate").val();
	var spc=" ";
	
	var veRentFromDt = $("#veRentFromDt").val();
	var veRentToDt = $("#veRentToDt").val();
	var vePurchaseDt = $("#vePurchaseDt").val();
	var veDeptFlag = $("#veDeptFlag").val();
	
	var saveMode = $("#saveMode").val();
	
	if(veRentFromDt != undefined){
		var frmdate = veRentFromDt.split(' ')[0];
	}
	if(veRentToDt != undefined){
		var todate = veRentToDt.split(' ')[0];
	}
	if(vePurchaseDt != undefined){
		var purchdate = vePurchaseDt.split(' ')[0];
	}

	/*if(veDeptFlag="Y") {
		if(invoiceDate!="" && purchdate!=""){
			if(invoiceDate >= purchdate){
				errorList.push(getLocalMessage("Advice Date cannot be greater than purchase Date"));
			}
		}
	}*/

	if(veDeptFlag="N") {
		if(invoiceDate!="" && frmdate!="" && todate!=""){
			if(invoiceDate >= frmdate && invoiceDate <= todate){
				errorList.push(getLocalMessage("vehicle.fueling.validation.date3"));
			}
		}
	}	

	if(process(vefDate) > process(invoiceDate)){
		errorList.push(getLocalMessage('vehicle.fuelling.adviceDate')+spc+getLocalMessage('solid.waste.validation.greater')+spc+getLocalMessage('vehicle.fuelling.fuelingDate'));
	}
	if (vehicleType == "" || vehicleType == null || vehicleType == 0) {
		errorList.push(getLocalMessage('VehicleMaintenanceMasterDTO.veVetype')+spc+getLocalMessage('solid.waste.validation.empty'));
	}
	if (pumpId == "" || pumpId == null || pumpId == 'undefined') {
		errorList.push(getLocalMessage('refueling.pump.master.name')+spc+getLocalMessage('solid.waste.validation.empty'));
	}	
	if (vefDate == "" || vefDate == null || vefDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.fuelingDate')+spc+getLocalMessage('solid.waste.validation.empty'));
	}	
	if (veNo == "" || veNo == "0" || veNo == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.vehicleRegistrationNumber')+spc+getLocalMessage('solid.waste.validation.empty'));
	}	
	if (vefReading == "" || vefReading == null || vefReading == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.vehicleReading')+spc+getLocalMessage('solid.waste.validation.empty'));
		
	}	
	/*if(saveMode=='A') {
		if (driverName == 0 || driverName == "" || driverName == null || driverName == 'undefined') {
			errorList.push(getLocalMessage('vehicle.fuelling.driverName')+spc+getLocalMessage('solid.waste.validation.empty'));
		}	
	}*/
	if (invoiceNumber == "" || invoiceNumber == null || invoiceNumber == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.adviceno')+spc+getLocalMessage('solid.waste.validation.empty'));
	}	
	if (invoiceDate == "" || invoiceDate == null || invoiceDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.adviceDate')+spc+getLocalMessage('solid.waste.validation.empty'));
	}	
	
	return errorList;
}

function validateEntryDetails(errorList){    
	var errorList = [];
	$('.appendableClass').each(function(i) {
		if($("#vefdQuantity" + i).is(':disabled') == false ||  $("#vefdCost" + i).is(':disabled') == false){
			var vefdQuantity = $("#vefdQuantity" + i).val();
			var vefdCost = $("#vefdCost" + i).val();
			 
			if (vefdQuantity == "" || vefdQuantity == null || vefdQuantity == 'undefined' || vefdQuantity == 0) 
				errorList.push(getLocalMessage('fuelling.advice.quantity.validation')+ " " + (i + 1));
			if (vefdCost == "" || vefdCost == null || vefdCost == 'undefined' || vefdCost == 0) 
				errorList.push(getLocalMessage('vehicle.fuelling.cost.validation')+ " " + (i + 1));
		}	
	});
	return errorList;
}
function deleteEntry(obj, ids) {
	var totalWeight = 0;
	var rowCount = $('#vehicleFuelling >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("Cannot delete"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	deleteTableRow('vehicleFuelling', obj, ids);
	$('#vehicleFuelling').DataTable().destroy();
	sum();
	triggerTable();	
}




function deleteEntryEdit(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('vehicleFuelling', obj, ids);
	$('#vehicleFuelling').DataTable().destroy();
	sum();
	triggerTable();	
}




function resetScheme(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action','vehicleFuel.html');
	$('.error-div').hide();
}

function resetForm() {
	
	$("#vehicleFuelling").hide();
	$('input[type=text]').val('');  
    $('#Address').val(''); 
    $('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function resetVehicleFuelling() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'vehicleFuel.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
function backVehicleFuellingForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'vehicleFuel.html');
	$("#postMethodForm").submit();
}

function showVehicleRegNo(){

	var errorList = [];
	$(".warning-div").hide();
	$('#vemId').html('');
	var crtElementId = $("#veVetype").val();
	var requestUrl = "vehicleFuel.html?vehicleNo";
	var vefDate = $("#vefDate").val();
	var requestData = {
		"id" : crtElementId,
		"date":vefDate
	};
	if (vefDate == "" || vefDate == null || vefDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.date.valid'));
	}	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		 $("#veVetype").val(0);
		return false;
	}
	else {
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');

	 $('#vemId').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
	  $('#vemId').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	 });
	 $('#vemId').trigger('chosen:updated');	
	}
}


function replaceZero(value){
	return value != 0 ? value : undefined;
}

function printdiv(formUrl, actionParam, vefId) {
	
	var data = {
		"vefId":vefId	
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function PrintDiv(title){
	
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-darkblue.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-danger hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function sum() {
	
	var i = 0;
	var sumadd = 0;
	var multiply=0;
	$("#vehicleFuelling tbody tr").each(function(i) {
		
		var vefdQuantity = $("#vefdQuantity"+i).val();
		var vefdCost = $("#vefdCost"+i).val();
		sumadd = parseFloat(sumadd) + parseFloat(vefdCost * vefdQuantity);
		multiply = parseFloat(vefdCost * vefdQuantity).toFixed(2);
		$('#multi'+i).val(multiply);
	});
	if (isNaN(sumadd)) {
		$('#id_total').val(0);
	} else {
		$('#id_total').val(sumadd.toFixed(2));
	}
	
}

function searchVeNo() {
	var spc=" ";
	var vemId = $('#vemId').val();
	var errorList = [];
	if(vemId == null || vemId == undefined || vemId == "" || vemId == "0"){
		errorList.push(getLocalMessage('vehicle.fuelling.vehicleRegistrationNumber')+spc+getLocalMessage('solid.waste.validation.empty'));
		displayErrorsOnPage(errorList);
		return false;
	}else{
		$('.error-div').hide();
		var requestData = {
				"vemId" : $("#vemId").val()
			    };
		var ajaxResponse = doAjaxLoading(
			    'vehicleFuel.html?searchVeNo', requestData,
			    'json');
		$('#vehicleFuelTypeVal').val(ajaxResponse.vName)
		fuelTypeCheck()
	}
	  /*Defect #176546*/
	/* $('#vemId').html('');
    $('#vemId').html(
	    $("<option></option>").attr("value", "0").text(
		    getLocalMessage('selectdropdown'))).trigger("chosen:updated");*/
	/* var veNodata='<option value="'+ajaxResponse.veId+'">'+ajaxResponse.veNo+'</option>'
	$("#vemId").html(veNodata).trigger("chosen:updated");*/

    
}

var fileArray=[];
$("#deleteDoc").on("click",'#deleteFile',function(e) {
	
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {

		$(this).parent().parent().remove();
		var fileId = $(this).parent().parent().find(
				'input[type=hidden]:first').attr('value');
		if (fileId != '') {
			fileArray.push(fileId);
		}
		$('#removeFileById').val(fileArray);
	}
});

function deleteEntryPermanent(obj, ids,vefId,vefdId) {
	var totalWeight = 0;
	var rowCount = $('#vehicleFuelling >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("Cannot delete"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	 showConfirmBoxForDelete(vefId,vefdId);
	
}

function showConfirmBoxForDelete(vefId,vefdId){
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+ getLocalMessage('vehicle.delete') +'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + vefId +',' + vefdId +')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(vefId,vefdId){
	$.fancybox.close();
	var requestData = {
			"mode":"D",
			"vefId":vefId,
			"vefdId":vefdId
	};
	var ajaxResponse = doAjaxLoading('vehicleFuel.html?deleteVehicleFuelingDet', requestData, 'html');
	 if (ajaxResponse == 'false') {
		 showErrormsgboxTitle(getLocalMessage('ERROR OCCURED'))
	 }else{
		 getVehicleFuellingData('vehicleFuel.html','editVehicleFuelling',vefId)
	 }
}
function fuelTypeCheck(){
	
	var vehicleFuelType = $('#vehicleFuelTypeVal').val();
	//if(vehicleFuelType != undefined || vehicleFuelType != "" ){
	$(".appendableClass").each(function(i) {	
		
				var puFuName = $("#puFuName" + i).val();
				var puActive = $("#puActive" + i).val();
				var vefdQuantity = $("#vefdQuantity" + i).val();
				var vefdCost = $("#vefdCost" + i).val();
				var multi = $("#multi" + i).val();
				
			if(vehicleFuelType.indexOf(puFuName) != -1 && puActive=="Y"){//we are checking contains
				 $("#vefdQuantity" + i).val(vefdQuantity).prop( "disabled", false );
				 $("#vefdCost" + i).val(vefdCost).prop( "disabled", false );
				 $("#multi" + i).val(multi).prop( "disabled", false );	
				 //$("#vefdQuantity" + i).val();
				 //$("#vefdCost" + i).val();
				// $("#multi" + i).val();				
			}else{
				 $("#vefdQuantity" + i).val(vefdQuantity).prop( "disabled", true );
				 $("#vefdCost" + i).val(vefdCost).prop( "disabled", true );
				 $("#multi" + i).val(multi).prop( "disabled", true );	
			}
			});
	//}
}

