
function saveDeploymentStaffReqApprovalData(element) {

	var errorList = [];
	if ($("#remarkApproval").val() == "") {
		errorList.push("Please enter the remark")
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest($(theForm).attr('action')+ '?saveDeploymentStaffReqApproval', 'POST', requestData,false, 'json');
		if (object.error != null && object.error != 0) {
			$.each(object.error, function(key, value) {
				$.each(value, function(key, value) {
					if (value != null && value != '') {
						errorList.push(value);
					}
				});
			});
			displayErrorsOnPage(errorList);
		} else {
			
			if (object.wfStatus == 'REJECTED') {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.rejectedStatus'));
			} else if (object.wfStatus == 'SEND_BACK') {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.sendBack'));
			} else if (object.wfStatus == 'FORWARD_TO') {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.forward'));
			} else {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.acceptStatus'));
			}
		}
	}
}
function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Proceed';
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}

$(document).ready(function() {
	prepareDateTag();
	$("#frmDeploymentStaffTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$('body').on('focus', ".hasMobileNo", function() {
		$('.hasMobileNo').keyup(function() {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '10');
		});
	});

});

function addDeploymentStaff(formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function getStaffData(obj) {
	var errorList=[];
	var contStaffIdNo = $('#contStaffIdNo').val();
	var empTypeId = $('#empTypeId').val();
	var vendorId=$('#vmVendorid').val();
	if(contStaffIdNo !=0 && vendorId==""){
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.vendorId"));
	}
	if(errorList.length >0){
		displayErrorsOnPage(errorList);
	}
	else{
		var URL = 'DeploymentOfStaff.html?getStaffData';
		var requestData = {
			'contStaffIdNo' : contStaffIdNo,
			'empTypeId' : empTypeId,
			'vendorId' :vendorId
		}
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		var divName = formDivName;
		$(divName).html(response);
		$("#contStaffSchFrom").removeAttr("disabled"); 
		$("#contStaffSchTo").removeAttr("disabled"); 
		$("#contStaffSchFrom").val('');
		$("#contStaffSchTo").val('');
	}
}

function getVendorData(){

	var errorList=[];
    $('#contStaffIdNo').html('');
	var vendorId=$('#vmVendorid').val();
	var empTypeId = $("#empTypeId :selected").attr('code');
	if(empTypeId==null || empTypeId==undefined){
		errorList.push(getLocalMessage("DeploymentOfStaffDTO.Validation.empTypeId"));
	}
	if(errorList.length >0){
		displayErrorsOnPage(errorList);
	}
	else{
		$('#location').val('0');
		$('#fromCpdShiftId').val('0');		
		$('#location').trigger('chosen:updated');
		$('#fromCpdShiftId').trigger('chosen:updated');
		var URL = "DeploymentOfStaff.html?getVendorDataOnClick";
		var requestData = {
			"vendorId" : vendorId,
			"empTypeId" :empTypeId
		}
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		$('#contStaffIdNo').append(
				$("<option></option>").attr("value", 0).attr("code", 0).text(
						"select"));
		$.each(response, function(index, value) {
			$('#contStaffIdNo').append(
					$("<option></option>").attr("value", index).attr("code",
							index).text(value));
		});
		
		$('#contStaffIdNo').trigger('chosen:updated');
		$("#contStaffSchFrom").val('');
		$("#contStaffSchTo").val('');
		$("#contStaffSchFrom").attr("disabled", "disabled"); 
		$("#contStaffSchTo").attr("disabled", "disabled"); 
		
	}
}

function saveData(element, mode) {

	var errorList = [];
	errorList1 = validateStaff(errorList);

	if (errorList1.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(element,getLocalMessage('swm.saveVehicleScheduling'),'DeploymentOfStaff.html', 'saveform');
		//return saveOrUpdateForm(element, "", 'DeploymentOfStaff.html','saveform');
	}
}

function validateStaff(errorList) {
	var empTypeId = $("#empTypeId").val();
	var contStaffIdNo = $("#contStaffIdNo").val();
	var vendorId=$("#vmVendorid").val();
	var location = $("#locId").val();
	var cpdShiftId = $("#cpdShiftId").val();
	var contStaffFrom = $("#contStaffSchFrom").val();
	var contStaffTo = $("#contStaffSchTo").val();
	var contStaffMob = $("#contStaffMob").val();
	var storeFromDate =$( '.contStaffSchFrom' ).datepicker( "option", "minDate" );	
	var storeToDate =$( '.contStaffSchTo' ).datepicker( "option", "maxDate" );
	
	var date = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var contStaffSchFrom = new Date(contStaffFrom.replace(pattern, '$3-$2-$1'));
	var contStaffSchTo = new Date(contStaffTo.replace(pattern, '$3-$2-$1'));
	 storeFromDate = new Date(storeFromDate.substr(0,10).replace(pattern, '$3-$2-$1'));
	 storeToDate = new Date(storeToDate.substr(0,10).replace(pattern, '$3-$2-$1'));
	 if(contStaffMob != ""){
	        if(contStaffMob.length!=10){
	            errorList
	            .push(getLocalMessage("DeploymentOfStaffDTO.Validation.validMobileNo"));
	        }
	        else{
	            var mobileRegex = /^[789]\d{9}$/;

	            if (!mobileRegex.test(contStaffMob)) {
	                errorList.push(getLocalMessage("ContractualStaffMasterDTO.Validation.validMobileNo"));
	                }
	            }
	    }
	if (empTypeId == "0" || empTypeId == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.empTypeId"));
	if (vendorId == "" || vendorId == "0"|| vendorId == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.vendorId"));
	if (contStaffIdNo == "" ||contStaffIdNo == "0" || contStaffIdNo == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.contStaffIdNo"));
	if (location == "" || location == "0" || location == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.location"));
	if (cpdShiftId == "0" || cpdShiftId == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.cpdShiftId"));
	if (contStaffFrom == "" || contStaffFrom == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.contStaffSchFrom"));
	if (contStaffTo == "" || contStaffTo == null)
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.contStaffSchTo"));
	if (contStaffFrom != "" && contStaffTo != "") {
		if (contStaffSchFrom > contStaffSchTo) {
			errorList
					.push(getLocalMessage("DeploymentOfStaffDTO.Validation.fromTodate "));
		}
	}
	if (contStaffSchFrom != "" && contStaffSchFrom != null){
		
		 if(storeFromDate > contStaffSchFrom){
			 errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.contractMaster.compare.schFromDate "));
		 }
	}
	if (contStaffSchTo != "" && contStaffSchTo != null){
		 if(storeToDate < contStaffSchTo){
			 errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.contractMaster.compare.schToDate  "));
		 }
	}
	
	return errorList;
}

function SearchStaff() {

	var errorList = [];
	var empTypeId = $("#empTypeId").val();
	var vendorId = $('#vendorId').val();
	var cpdShiftId = $('#cpdShiftId').val();
	var locId = $('#locId').val();

	if (empTypeId != 0 || vendorId != "" || cpdShiftId != 0 || locId != "") {
		var requestData = 'empTypeId=' + empTypeId + '&vendorId=' + vendorId
				+ '&cpdShiftId=' + cpdShiftId + '&locId=' + locId;
		var table = $('#frmDeploymentStaffTbl').DataTable();
		var URL = "DeploymentOfStaff.html?searchStaffDetails";
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
		var staffMasterDTOList = response;
		if (staffMasterDTOList.length == 0) {
			errorList
					.push(getLocalMessage("DeploymentOfStaffDTO.Validation.notFound"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						staffMasterDTOList,
						function(index) {
							var obj = staffMasterDTOList[index];
							let contStaffName = obj.contStaffName;
							let contStaffMob = obj.contStaffMob;
							let locDesc = obj.locDesc;
							let shiftDesc = obj.shiftDesc;
							let contStaffSchFrom = obj.contStaffSchFrom;
							let contStaffSchTo = obj.contStaffSchTo;
							let contStaffIdNo = obj.contStaffIdNo;
							let deplId = obj.deplId

							if (contStaffName == null || contStaffName == "") {
								contStaffName = "-";
							}
							if (contStaffMob == "") {
								contStaffMob = "-";
							}
							if (locDesc == null || locDesc == "") {
								locDesc = "-";
							}
							if (shiftDesc == null || shiftDesc == "") {
								shiftDesc = "-";
							}
							if (contStaffSchFrom == null
									|| contStaffSchFrom == "") {
								contStaffSchFrom = "-";
							}
							if (contStaffSchTo == null || contStaffSchTo == "") {
								contStaffSchTo = "-";
							}

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ contStaffName + '</div>',
											'<div class="text-center">'
													+ contStaffMob + '</div>',
											'<div class="text-center">'
													+ locDesc + '</div>',
											'<div class="text-center">'
													+ shiftDesc + '</div>',
											'<div class="text-center">'
													+ getDateFormat(contStaffSchFrom)
													+ '</div>',
											'<div class="text-center">'
													+ getDateFormat(contStaffSchTo)
													+ '</div>',
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="getStaff(\''
													+ deplId
													+ '\',\'DeploymentOfStaff.html\',\'VIEW\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													/*+ '<button type="button" class="btn btn-warning btn-sm "  onclick="getStaff(\''
													+ deplId
													+ '\',\'DeploymentOfStaff.html\',\'EDIT\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'*/
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList
				.push(getLocalMessage("DeploymentOfStaffDTO.Validation.selectAtLeastOne"));
		displayErrorsOnPage(errorList);
	}

}

function getStaff(id, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function resetData(){
	$('#contStaffIdNo').val('0');
	$('#vmVendorid').val('0');
	$('#location').val('0');
	$('#fromCpdShiftId').val('0');
	$('#contStaffIdNo').trigger('chosen:updated');
	$('#vmVendorid').trigger('chosen:updated');
	$('#location').trigger('chosen:updated');
	$('#fromCpdShiftId').trigger('chosen:updated');
}
