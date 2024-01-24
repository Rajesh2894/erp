function addVehicleMaintenance(formUrl, actionParam) {  //addVehicleMaintenance
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
//searchVehicleMaintenance

function getVehicleMaintenancemasterData(formUrl, actionParam, vemeId) {  //getVehicleMaintainancemasterData
	var divName = '.content-page';
	var data= {
		"vemeId":vemeId	
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getViewVehicleMaintenancemasterData(formUrl, actionParam,vemeId) {
	var data = {
		"vemeId":vemeId	
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function backVehicleMaintenanceMasterForm() {  //backVehicleMaintenanceMasterForm()
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'vehicleMaintenanceMasController.html');
	$("#postMethodForm").submit();
}

function validateFormData(errorList){
	
		//var vemMetype = $("#vemMetype").val();
		var veVetype = $("#veVetype").val();
		var vemDowntime = $("#veDowntime").val();
		var vemDowntimeunit = $("#veDowntimeUnit").val();
		var vemReceiptno = $("#veMainUnit").val();
		var vemReceiptdate = $("#veMainday").val();
		
		if (veVetype == "" || veVetype == null || veVetype == 'undefined' || veVetype == "0") {
			errorList.push(getLocalMessage('vehicle.maintenance.master.type'));
		}
		
		if (vemReceiptdate == "" || vemReceiptdate == null || vemReceiptdate == 'undefined') {
			errorList.push(getLocalMessage('vehicle.maintenance.maintenance.after'));
		}
		
		if (vemReceiptno == "" || vemReceiptno == null || vemReceiptno == 'undefined') {
			errorList.push(getLocalMessage('vehicle.maintenance.maintenance.unit'));
		}
		
		
		if (vemDowntime == "" || vemDowntime == null || vemDowntime == 'undefined') {
			errorList.push(getLocalMessage('vehicle.maintenance.master.estimatedDowntime'));
		}	
		if (vemDowntimeunit == "" || vemDowntimeunit == null || vemDowntimeunit == 'undefined' || vemDowntimeunit == "0") {
			errorList.push(getLocalMessage('vehicle.maintenance.master.downtime.unit'));
		}
		
		
		
		
		return errorList;
	}

function saveVehicleMaintenanceMasterForm(element){
	var errorList = [];
	errorList = validateFormData(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,getLocalMessage('vehicle.maintainance.master.add.success'), 'vehicleMaintenanceMasController.html', 'saveform');
	}
}
function deleteVehicleMaintenanceMasterData(formUrl, actionParam, vemeId) {
	if (actionParam == "deleteVehicleMaintenanceMaster") {
		showConfirmBoxForDelete(vemeId, actionParam);
	}		
}

function showConfirmBoxForDelete(vemeId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'+ getLocalMessage('Do you want to delete?') +'</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + vemeId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(vemeId) {
	$.fancybox.close();
	var requestData = 'vemeId=' + vemeId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('vehicleMaintenanceMasController.html?'+ 'deleteVehicleMaintenanceMaster', requestData, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchVehicleMaintenanceMaster(formUrl, actionParam) {
	
	var data = {
		"vehType":replaceZero($("#veVetype").val())
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchVehicleMaintenanceMaster(){
	
	var data = {
			"vehType":replaceZero($("#veVetype").val())
		};
		var divName = '.content-page';
		var url = "vehicleMaintenanceMasController.html?searchVehicleMaintenanceMaster";
		var ajaxResponse = doAjaxLoading(url , data, 'html',divName);
		$('.content').removeClass('ajaxloader');		
		$(divName).html(ajaxResponse);
		prepareTags();
}

function resetScheme(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action','vehicleMaintenanceMasController.html');
}

function resetVehicleMaintenanceMaster() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'vehicleMaintenanceMasController.html');
	$("#postMethodForm").submit();
}

$(document).ready(function(){
	var table = $('.vm').DataTable({
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
		
});	
function replaceZero(value){
	return value != 0 ? value : undefined;
}
function resetForm() {
	
	$("#errorDiv").hide();
	$('input[type=text]').val('');  
    $('input[type=select]').val('');
	$("select").val("").trigger("chosen:updated");
}

function ResetForm2(resetBtn){
		
	resetForm(resetBtn);;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('vehicleMaintenanceMasController.html?AddVehicleMaintenanceMaster', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)	
	$('input[type=text]').val('');  
    $('input[type=select]').val('');
	$("select").val("").trigger("chosen:updated");
	prepareTags();
}
