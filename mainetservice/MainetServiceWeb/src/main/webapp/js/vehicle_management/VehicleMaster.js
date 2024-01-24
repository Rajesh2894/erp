function addVehicle(formUrl, actionParam) {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
jQuery('.hasDecimal').keyup(function() {
	this.value = this.value.replace(/[^0-9\.]/g, '');
});
function getVehiclemasterData(formUrl, actionParam, veId) {

	var data = {
		"veId" : veId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	sum1();
}

function getViewVehiclemasterData(formUrl, actionParam, veId) {

	var data = {
		"veId" : veId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	sum1();
}

function deleteVehiclemasterData(formUrl, actionParam, veId) {

	if (actionParam == "deleteVehiclemasterData") {
		showConfirmBoxForDelete(veId, actionParam);
	}
}
function showConfirmBoxForDelete(veId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + veId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(veId) {
	$.fancybox.close();
	var requestData = 'veId=' + veId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('veMasterCon.html?'
			+ 'deleteVehiclemasterData', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function resetVehicle() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'veMasterCon.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function searchVehicle() {

	var errorList = [];
	var vehicleType = replaceZero($("#veVetype").val());
	var code=$("#VehicleRegNo").find("option:selected").attr('code');
	var vehicleRegNo = $('#VehicleRegNo').val();
	if (vehicleRegNo == 0) {
		vehicleRegNo = "";
	} else {
		vehicleRegNo = code;
	}
	var deptId = $('#deptId').val();
	var location = $('#location').val();
	if (vehicleType == "" && vehicleRegNo == "" && deptId == ""
			&& location == "") {
		errorList.push(getLocalMessage("PetrolRequisitionDTO.validation.select.field"));
		displayErrorsOnPage(errorList);
	} else {
		var data = {
			"vehicleType" : replaceZero($("#veVetype").val()),
			"vehicleRegNo" : vehicleRegNo,
			"deptId" : $("#deptId").val(),
			"location" : $("#location").val()

		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(
				'veMasterCon.html?searchVehiclemasterData', data, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		var vehDto = ajaxResponse;
		if (vehDto == 0) {
			errorList.push("record not found");
			displayErrorsOnPage(errorList);
		}
		prepareTags();

	}
	/*
	 * var divName = '.content-page'; var ajaxResponse =
	 * doAjaxLoading('VehMaster.html?searchVehiclemasterData', data, 'html',
	 * divName); $('.content').removeClass('ajaxloader');
	 * $(divName).html(ajaxResponse); prepareTags();
	 */
}

function validateSearchForm(errorList) {
	return errorList;
}

function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

function saveVehicleMasterForm(element) {
	var errorList = [];
	//validation for duplicate record start
	
	var inactiveCheck = "Y";
	if($("#status2").is(':checked')){
		inactiveCheck ='N';
	}else if ($("#status1").is(':checked')){
		inactiveCheck ='Y';
	}else if ($("#status3").is(':checked')){
		inactiveCheck ='S';
	}
	var EngineNo = $("#EngineNo").val();
	var chasisno = $("#chasisno").val();
	var veReg = $("#veReg").val();
	var veId = $('#veId').val();
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsPage(errorList);
	} 
   if(veId == undefined){
	   veId = 0
   }
   var returnData=false;
   var returnData1=false;
   if(veReg != null && veReg!=""){
	 //veReg already Added
	var requestData = {
			"EngineNo"   : EngineNo,
			"chasisno"   : "",
			"veReg"      : veReg,
		 	"veId"       : veId
		}
	var URL = 'veMasterCon.html?recordExists';
	 returnData = __doAjaxRequest(URL, 'POST', requestData, false,'json');
   }
   else{
	   returnData=true; 
   }
	if(returnData){
		if(chasisno != null && chasisno!=""){
			//chasisno already Added
			var requestData1 = {
					"EngineNo"   : EngineNo,
					"chasisno"   : chasisno,
					"veReg"      : "",
				 	"veId"       : veId
				}
			var URL = 'veMasterCon.html?recordExists';
			 returnData1 = __doAjaxRequest(URL, 'POST', requestData1, false,'json');
		   }
		   else{
			   returnData1=true; 
		   }
		if(returnData1){
			errorList = errorList.concat(validateFormDetails());
			if (errorList.length > 0) {
				checkDate(errorList);
				displayErrorsPage(errorList);
			} else {
				return saveOrUpdateForm(element,
						getLocalMessage('scheme.master.creation.success'),
						'veMasterCon.html', 'saveform');
			}
		}else{
			errorList.push(getLocalMessage("Vehicle already Added"));
			displayErrorsOnPage(errorList);
		}
		//errorList = validateForm(errorList);
		
	}else{
		errorList.push(getLocalMessage("Vehicle already Added"));
		displayErrorsOnPage(errorList);
	}
	return errorList;
	//validation for duplicate record end
}


function addEntryData(tableId) {

	var errorList = [];
	errorList = validateFormDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow(tableId, false);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateFormDetails(errorList) {
	var errorList = [];

	var i = 0;
	if ($.fn.DataTable.isDataTable('#WasteDetailTable')) {
		$('#WasteDetailTable').DataTable().destroy();
	}
	$("#WasteDetailTable tbody tr")
			.each(
					function(i) {
						var wasteType = $("#wasteType" + i).val();
						var wasteCId = $("#wasteCId" + i).val();
						var rowCount = i + 1;

						if (wasteType == "0" || wasteType == null) {
							errorList
									.push(getLocalMessage("swm.validation.wastType")
											+ rowCount);
						}

						if (wasteCId == "" || wasteCId == null) {
							errorList
									.push(getLocalMessage("swm.validation.capacity.in.kg")
											+ rowCount);
						}

					});
	return errorList;

}

function validateForm(errorList) {

	var veVetype = $("#veVetype").val();
	var vePurDate = $("#vePurDate").val();
	var EngineNo = $("#EngineNo").val();
	var chasisno = $("#chasisno").val();
	var veReg = $("#veReg").val();
	var date = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
//	var vePurDate = new Date(vePurDate.replace(pattern, '$3-$2-$1'));
	if (vePurDate >= date){
		errorList.push(getLocalMessage("Purchase Date Should not be Greater than Current Date"));
	}
	
	if (errorList.length > 0) {
		checkDate(errorList);
	}
	
	
	if (veVetype == "0" || veVetype == null) {
		errorList
				.push(getLocalMessage('vehicle.master.validation.vehicleTypee'));
	}
	if (EngineNo == "" || EngineNo == null || EngineNo == "0") {
		errorList.push(getLocalMessage('vehicle.master.validation.EngineNo'));
	}
	if (chasisno == "" || chasisno == null || chasisno == "0") {
		errorList.push(getLocalMessage('vehicle.master.validation.chasisno'));
	}
	/*if (veReg == "" || veReg == null || veReg == "0") {
		errorList.push(getLocalMessage('vehicle.master.validation.veReg'));
	}*/
	var fuelType = $("#fuelType").val();
	if (fuelType == "0" || fuelType == null || fuelType == "") {
		errorList
				.push(getLocalMessage('vehicle.master.validation.fuelType'));
	}
	//var stardWgt = $("#stardWgt").val();
	var stardWgt = $("#veStdWeight").val();
	/*if (stardWgt == "" || stardWgt == null) {
		errorList.push(getLocalMessage('vehicle.master.validation.stdwgt'));
	}
	if (stardWgt < 0) {
		errorList
				.push(getLocalMessage('vehicle.master.validation.wghtvalidation'));
	}*/
	var MakeModel = $("#MakeModel").val();
	if (MakeModel == "" || MakeModel == null) {
		errorList
				.push(getLocalMessage('vehicle.master.validation.makeModelMfg'));
	}
	var veCapacity = $("#veCapacity").val();
	var veCaunit = $("#veCaunit").val();
	if (veCapacity != "" && veCapacity != null) {
		//errorList.push(getLocalMessage('vehicle.master.validation.veCapacity'));
		if (veCaunit == "0" || veCaunit == null) {
			errorList
					.push(getLocalMessage('vehicle.master.validation.veCaunit'));
		}
	}
	var deAreaUnit = $("#VehicleGPSID").val();
	if (deAreaUnit == "0" || deAreaUnit == null || deAreaUnit == "") {
		errorList
				.push(getLocalMessage('vehicle.master.validation.vehTracking'));
	}
	if ($("#DepartmentownedYes").is(":checked")) {
		var assetCode = $("#AssetCode").val();
		var vePurDate = $("#vePurDate").val();
		var date = new Date();
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
     	var vePurDate = new Date(vePurDate.replace(pattern, '$3-$2-$1'));
		if (vePurDate >= date) {
			errorList.push("Purchase Date should not be greater than today's date");
		}
		/*if (assetCode == "" || assetCode == null || assetCode == "0") {
			errorList.push(getLocalMessage('vehicle.master.validation.AssetId'));
		}*/

	}
		
	else if ($("#DepartmentownedNO").is(":checked")) {
		var vmVendorname = $("#vmVendorname").val();
		if (vmVendorname == "" || vmVendorname == null || vmVendorname == "0") {
			errorList
					.push(getLocalMessage('vehicle.master.validation.vendorName'));
		}
		
		var veRentamt = $("#veRentamt").val();
		if (veRentamt == null || veRentamt == "") {
			errorList.push(getLocalMessage("Please Enter Rent Amount"));
		}
		var veRentFromdate = $("#veRentFromdate").val();
		if (veRentFromdate == null || veRentFromdate == "") {
			errorList.push(getLocalMessage("Please Enter Vehicle Rent From Date"));
		}
		var veRentTodate = $("#veRentTodate").val();
		if (veRentTodate == null || veRentTodate == "") {
			errorList.push(getLocalMessage("Please Enter Vehicle Rent To Date"));
		}
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date($("#veRentFromdate").val().replace(pattern,
				'$3-$2-$1'));
		var sDate = new Date($("#veRentTodate").val().replace(pattern,
				'$3-$2-$1'));
		if (eDate > sDate) {
			errorList.push(getLocalMessage('vehicle.master.validation.RentValidation'));
		}
		
		/*if (vmContractno == "" || vmContractno == null || vmContractno == "0") {
			errorListresetForm
					.push(getLocalMessage('vehicle.master.validation.contractNo'));
		}*/
	} else {
		errorList.push(getLocalMessage('vehicle.master.validation.department'));
	}
	
	var deptId = $("#department").val();
	if (deptId == "0" || deptId == "") {
		errorList.push(getLocalMessage("oem.valid.department"));
	}
	var locId = $("#location").val();
	/*if (locId == "0" || locId == "") {
		errorList.push(getLocalMessage("Please Enter Location  Name"));
	}*/
	var pur = $("#purpose").val();
	/*if (pur == null || pur == "") {
		errorList.push(getLocalMessage("Please Give Some Purpose"));
	}
*/
	return errorList;
	checkDate(errorList);
}

function backVehicleMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'veMasterCon.html');
	$("#postMethodForm").submit();
}


$(document).ready(function() {
	var table = $('.vm').DataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		/*"bPaginate" : true,
		"bFilter" : true,*/
		"ordering":  false,
	    "order": [[ 1, "desc" ]]
	});
	

	
	$("#VehicleRegNo").keypress(function(event) {

		var inputValue = event.which;
		if (!(inputValue != 32 && inputValue != 0)) {
			event.preventDefault();
		}
	});

	$("#EngineNo").keypress(function(event) {

		var inputValue = event.which;
		// allow letters and whitespaces only.
		if (!(inputValue != 32 && inputValue != 0)) {
			event.preventDefault();
		}
	});

	$("#chasisno").keypress(function(event) {

		var inputValue = event.which;
		// allow letters and whitespaces only.
		if (!(inputValue != 32 && inputValue != 0)) {
			event.preventDefault();
		}
	});
	$('input[name="vehicleMasterDTO.veFlag"]').click(function() {

		if ($(this).attr("value") == "Y") {
			$(".hidebox").not(".yes").hide();
			$(".yes").show();
		}
		if ($(this).attr("value") == "N") {
			$(".hidebox").not(".no").hide();
			$(".no").show();
		}
	});
	$('input[name="vehicleMasterDTO.veFlag"]').click(function() {

		if ($(this).attr("value") == "Y") {
			$(".hidebox").not(".yes").hide();
			$(".yes").show();
			$('#veRentFromdate').val('');
			$('#veRentTodate').val('');
			$('#vmVendorname').val('');
			$('#veRentamt').val('');
		}
		if ($(this).attr("value") == "N") {
			$(".hidebox").not(".no").hide();
			$(".no").show();
			$('#vePurDate').val('');
			$('#PurchasePrice').val('');
			$('#SourceofPurchase').val('');
			$('#AssetCode').val('');
		}
	});
	$("#VehicleGPS").val($("#VehicleGPSID").val());
	sum1();
	
	var mode = $('#mode').val();
	if( mode == 'E'  ){
		if ($("#status3").is(':checked')){
			$("#status1").attr('disabled',true);
			$("#status2").attr('disabled',true);
		}
	}
	
});

$('#vePurDateId').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true
});

/*
 * $("#vePurDateId").datepicker({ dateFormat : 'dd/mm/yy', changeMonth : true,
 * changeYear : true, maxDate : '-0d', });
 */
$("#vePurDateId").datepicker('setDate', new Date());

$("#veRentTodateId").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate : '-0d',
});
$("#veRentTodateId").datepicker('setDate', new Date());

$("#veRentFromdateId").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate : '-0d',
});
$("#veRentFromdateId").datepicker('setDate', new Date());

function resetForm() {

	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function replaceZero(value) {
	return value != 0 ? value : "";
}

function getContractNo() {

	$('#vmContractno').html('');
	var crtElementId = $("#vmVendorname").val();
	var requestUrl = "veMasterCon.html?getContractNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');

	$('#vmContractno').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#vmContractno').append(
				$("<option></option>").attr("value", value.contId).attr(
						"fdate", value.fromDate).attr("tdate", value.toDate)
						.attr("code", value.contId).text(value.contractNo));
	});
	$('#vmContractno').trigger('chosen:updated');
	$("#veRentFromdate").val('');
	$("#veRentTodate").val('');
}

function getContract() {

	$('#vmContractno').html('');
	var crtElementId = $("#vmVendorname").val();
	var requestUrl = "veMasterCon.html?getContractNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');

	$('#vmContractno').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#vmContractno').append(
				$("<option></option>").attr("value", value.contId).attr(
						"fdate", value.fromDate).attr("tdate", value.toDate)
						.attr("code", value.contId).text(value.contractNo));
	});
	$('#vmContractno').trigger('chosen:updated');
	$("#veRentFromdate").val('');
	$("#veRentTodate").val('');
}






function getContrctDate() {

	$("#veRentFromdate").val($("#vmContractno option:selected").attr("fdate"));
	$("#veRentTodate").val($("#vmContractno option:selected").attr("tdate"));
}
$(function() {

	/* To add new Row into table */
	$("#WasteDetailTable").on('click', '.wasteAdd', function() {

		var content = $("#WasteDetailTable").find('tr:eq(1)').clone();
		$("#WasteDetailTable").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		$('.error-div').hide();
		reOrderUnitTabIdSequence('.firstWasteRow');
	});
});
function reOrderUnitTabIdSequence(firstWasteRow) {

	$(firstWasteRow).each(
			function(i) {

				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "wasteType" + i);
				$(this).find("input:text:eq(1)").attr("id", "wasteCId" + i);
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr(
						"name",
						"vehicleMasterDTO.tbSwVehicleMasterdets[" + i
								+ "].wasteType");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"vehicleMasterDTO.tbSwVehicleMasterdets[" + i
								+ "].veCapacity");
			});
}
function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('firstWasteRow', obj, ids);
	$('#firstWasteRow').DataTable().destroy();
	triggerTable();
}

function sum1() {
	var i = 0;
	var gtotal = 0;
	$("#WasteDetailTable tbody tr").each(function(i) {
		var wasteCId = $("#wasteCId" + i).val();
		gtotal = parseFloat(gtotal) + parseFloat(wasteCId);
		if (gtotal == 'NaN') {
			gtotal = 0;
		}
	});

	$('#id_grand_Total').val(gtotal.toFixed(2));
}



function checkDate(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}




$('#veRentFromdate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true
});
$("#veRentFromdate").keyup(function(e) {

	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});

$('#veRentTodate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true
});

$("#veRentTodate").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});	

$('#vePurDate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true
});

$("#vePurDate").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});	





