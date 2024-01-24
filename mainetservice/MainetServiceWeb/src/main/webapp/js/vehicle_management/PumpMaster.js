
function addPumpMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function deletePumpMasterData(formUrl, actionParam, pumpId) {
	if (actionParam == "deleteRefuellingPumpMaster") {
		showConfirmBoxForDelete(pumpId, actionParam);
	}		
}
function showConfirmBoxForDelete(pumpId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'+ getLocalMessage('Do you want to delete?') +'</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + pumpId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(pumpId) {
	$.fancybox.close();
	var requestData = 'pumpId=' + pumpId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('fuelPumpMas.html?'+ 'deleteRefuellingPumpMaster', requestData, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}
function searchPumpMaster(){
	
	var data = {
			"pumpType":$("#puPutype").val(),
			"puPumpname":$("#puPumpname").val()
		};
		var divName = '.content-page';
		var url = "fuelPumpMas.html?searchPumpMaster";
		var ajaxResponse = doAjaxLoading(url , data, 'html',divName);
		$('.content').removeClass('ajaxloader');		
		$(divName).html(ajaxResponse);
		prepareTags();
}
function searchPumpMaster(formUrl, actionParam) {
	
	var data = {	
		"pumpType":$("#puPutype").val(),
		"puPumpname":$("#puPumpname").val()
	};
	var divName = '.content-page';
	var formUrl = "fuelPumpMas.html?searchPumpMaster";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html',divName);
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}
function getPumpmasterData(formUrl, actionParam, pumpId) {  
	
	var divName = '.content-page';
	var data= {
		"pumpId":pumpId	
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function getPumpmasterDataView(formUrl, actionParam, pumpId) {
	
	var data = {
		"pumpId":pumpId	
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function savePumpmasterData(element){

	var errorList = [];
	  
	var pumpType   = $("#puPutype").val();
	var vendorName = $("#vendorId").val();
	var puAddress  = $("#puAddress").val();
	var puPumpname = $("#puPumpname").val();
	var requestData = {
			"pumpType"   : pumpType,
			"vendorName" : vendorName,
			"puAddress"  : puAddress,
			"puPumpname" : puPumpname
		}
	var URL = 'fuelPumpMas.html?statusExits';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var mode = $("#mode").val(); 
	
	if(mode=='A') {
		if(returnData){
			errorList = validateFormDetails(errorList);
		    errorList = validateEntryDetails(errorList);
			if (errorList.length > 0) {
				$("#errorDiv").show();
				displayErrorsOnPage(errorList);
			} else {
				$("#errorDiv").hide();
				return saveOrUpdateForm(element,getLocalMessage('refueling.pump.master.add.success'),'fuelPumpMas.html', 'saveform');
			}
		}else {
			errorList.push(getLocalMessage("fuel.pump.master.exists"));
			displayErrorsOnPage(errorList);
		}
	}
	if(mode=='E') {
		errorList = validateFormDetails(errorList);
	    errorList = validateEntryDetails(errorList);
		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();
			return saveOrUpdateForm(element,getLocalMessage('refueling.pump.master.add.success'),'fuelPumpMas.html', 'saveform');
		}
	}
	
	return errorList;
}

function targetInfo(){
	
	var fuelItems = [];
}
function addEntryData(i) {
	
	var intt = parseInt(i);
	var incrementVar = intt+1;
	var count = ($('#refuellingPumpMaster tr').length)-1;
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateEntryDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('refuellingPumpMaster');
		$("#puApplicable"+count).val('N');
	} else {
		$('#refuellingPumpMaster').DataTable();
		displayErrorsOnPage(errorList);
	}
}
function validateFormDetails(errorList){
	
	var pumpType = $("#puPutype").val();
	var pumpName = $("#puPumpname").val();
	var puAddress = $("#puAddress").val();
	var vendorName = $("#vendorId").val();
	//var puApplicable = $("#puApplicable").val();
	if (pumpType == "" || pumpType == null) {
		errorList.push(getLocalMessage("refueling.pump.master.pump.type.empty"));
	}
	if (pumpName == "" || pumpName == null || pumpName == 'undefined') {
		errorList.push(getLocalMessage("refueling.pump.master.pump.name.empty"));
	}	
	if (puAddress == "" || puAddress == null || puAddress == 'undefined') {
		errorList.push(getLocalMessage("refueling.pump.master.pump.address.empty"));
	}	
	if (vendorName == "" || vendorName == null || vendorName == 'undefined') {
		errorList.push(getLocalMessage("refueling.pump.master.pump.vendor.name.empty"));
	}	
	/*if (puApplicable == "N" || puApplicable == 'undefined') {
		errorList.push(getLocalMessage("refueling.pump.master.add.fuel.check")+ rowCount);
	}*/
	return errorList;
}

function validateEntryDetails(errorList){
	
	var puFuidList=[];
	var cnt = 0;
	$(".appendableClass").each(function(i) {
				
				var puFuid = $("#puFuid" + i).val();
				var puFuunit = $("#puFuunit" + i).val();
				var puApplicable = $("#puApplicable" + i).val();
				var rowCount = i + 1;
				if(puFuidList.includes(puFuid)){
					errorList.push(getLocalMessage("vehicle.fueling.pump.exist")+ " " + rowCount);
				}
				if (puFuid == "" || puFuid == null) {
					errorList.push(getLocalMessage("vehicle.pump.master.add.fuel") + " " + rowCount);
				}
				if (puFuunit == "" || puFuunit == null || puFuunit == 'undefined') {
					errorList.push(getLocalMessage("vehicle.pump.master.add.fuel.unit")+ " " + rowCount);
				}
				if (puApplicable == "Y") {
					//errorList.push(getLocalMessage("refueling.pump.master.add.fuel.check")+ rowCount);
					cnt++;
				}
				puFuidList.push(puFuid);
			});
	if (cnt==0){
		errorList.push(getLocalMessage("refueling.pump.master.validation.select.one.checkbox"));
	}
	
	return errorList;
}

function deleteEntryEdit(obj, ids) {
	var totalWeight = 0;
	
	var rowCount = $('#refuellingPumpMaster >tbody >tr').length;
		let errorList = [];
		if (rowCount == 1) {
			errorList.push(getLocalMessage("First.Row.Cannot.Be.Deleted"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			return false;
		}
	deleteTableRow('refuellingPumpMaster', obj, ids);
	$('#refuellingPumpMaster').DataTable().destroy();
	triggerTable();
}

function deleteEntry(obj, ids) {
	
    var rowCount = $('#refuellingPumpMaster >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("First.Row.Cannot.Be.Deleted"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	deleteTableRow('refuellingPumpMaster', obj, ids);
	$('#refuellingPumpMaster').DataTable().destroy();

}

function resetScheme(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action','fuelPumpMas.html');
	$('.error-div').hide();
}

function resetRefuellingPumpStationMaster() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fuelPumpMas.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
function backPumpMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fuelPumpMas.html');
	$("#postMethodForm").submit();
}
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
	
	//Defect #171286 >> chosen not working issue
	  $("#vendorId").chosen();
		
});	

function replaceZero(value){
	return value != 0 ? value : undefined;
}

function clickCheckBox() {
	$(".appendableClass").each(function(i) {
		var checkBox = $("#puApplicable"+i).is(':checked');
		if (checkBox) {
		    $("#puApplicable"+i).val('Y');
		} else {
			$("#puApplicable"+i).val('N');
		}
		
	});
}
	
