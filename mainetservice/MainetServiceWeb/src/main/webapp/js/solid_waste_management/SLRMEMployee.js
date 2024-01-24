$(document).ready(function() {
	$("#id_SLRMEmployeeTable").dataTable({
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
	var isTscl = $('#envFlag').val();
	if(isTscl){
		$('#empUId').removeClass('required-control');
	}

});

function openaddEmployeeMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchEmployeeList(formUrl, actionParam) {
	errorList = [];
	
	var mrfId = $('#mrfId').val();
	
	if(mrfId == "" || mrfId == null || mrfId == undefined){
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.mrfId"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}else{
		var empUId = $('#empUId').val(); 
		var data = {
			"mrfId" : $('#mrfId').val(),
			"empUId" : $('#empUId').val()		
		}
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
				divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$('#mrfId').val(mrfId);
		$('#empUId').val(empUId);
		prepareTags();
	}
	 
	
}

function Proceed(element) {
	var errorList = [];
	var errorList = validateForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, getLocalMessage('swm.success.add'),
				'SLRMEmployeeMaster.html', 'saveform');
	}
}

function validateForm(errorList) {
	var mrfId = $("#mrfId").val();
	var dsgnId = $("#dsgnId").val();
	var empUId = $("#empUId").val();
	var ttlId = $("#ttlId").val();
	var empName = $("#empName").val();
	var empLName = $("#empLName").val();
	var gender = $("#gender").val();
	var empMobNo = $("#empMobNo").val();
	var empAddress = $("#empAddress").val();
	var empPincode = $("#empPincode").val();
	
	var isTSCL = $("#envFlag").val();
	

	if (mrfId == "0" || mrfId == null)
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.mrfId"));
	
	if (dsgnId == "" || dsgnId == null)
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.designation"));
	
	if(!isTSCL){
		if (empUId == "" || empUId == null || empUId == undefined) {
			errorList.push(getLocalMessage("swm.SLRM.employee.validation.employeeCode"));
		}
	}
	
	if (ttlId == "" || ttlId == null) {
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.title"));
	}
	if (empName == "" || empName == null)
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.firstName"));
	
	if (empLName == "" || empLName == null)
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.lastName"));
	
	if (gender == "" || gender == null)
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.gender"));
	
	if (empMobNo == "" || empMobNo == null){
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.mobNo"));
	}
		
	else {
		empMobNo = $("#empMobNo").val();
		var error=validateMobNo(empMobNo);
		if(error.length > 0){
			errorList.push(error);
		}
		
	}

	
	if (empAddress == "" || empAddress == null)
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.address"));
	
	if (empPincode == "" || empPincode == null){
		errorList.push(getLocalMessage("swm.SLRM.employee.validation.pincode"));
	}else {
		if (empPincode.length < 6) {
			errorList.push(getLocalMessage("swm.valid.pincode"));
		}
	}
	return errorList;

}

/*function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}*/

//function to validate contact number
function validateContactNum() {
	var errorList = [];
	var mobileNo = $("#empMobNo").val();
	errorList = validateMobNo(mobileNo);

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}

function ResetForm(resetBtn) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'SLRMEmployeeMaster.html?addEmployeeMaster', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function modifyEmployee(empId, formUrl, actionParam) {
	
	var divName = '.content-page';
	var requestData = {		
		"empId" : empId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

