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

});



function openaddEmployeeMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchEmployeeList(formUrl, actionParam) {
	
	var errorList = [];
    var mrfId = $("#mrfId").val();
    var empId = $("#empId").val();
        
    if (mrfId == 0 || mrfId == "" || mrfId == null || mrfId == undefined ){
        errorList.push(getLocalMessage("vehicle.employee.validation.mrfId"));
        displayErrorsOnPage(errorList);
    }
    if (empId == 0 || empId == "" || empId == null || empId == undefined ){
        errorList.push(getLocalMessage("vehicle.employee.validation.select.driver.name"));
        displayErrorsOnPage(errorList);
    }
    
    if (errorList.length > 0) {
        displayErrorsOnPage(errorList);

   } else {
        var data = {
		"mrfId" : $('#mrfId').val(),
		"empId" : $('#empId').val()		
	};
    var divName = '.content-page';
   	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
		divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);     
   }
}

function Proceed(element) {
	var errorList = [];
	var errorList = validateForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, getLocalMessage('Successfully Added'),
				'vehicleEmpDetails.html', 'saveform');
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
	

	if (mrfId == "" || mrfId == null)
		errorList.push(getLocalMessage("vehicle.employee.validation.mrfId"));
	
	if (dsgnId == "" || dsgnId == null)
		errorList.push(getLocalMessage("vehicle.employee.validation.designation"));

	if (empUId == "" || empUId == null) {
		errorList.push(getLocalMessage("vehicle.employee.validation.employeeCode"));
	}
	if (ttlId == "" || ttlId == null) {
		errorList.push(getLocalMessage("vehicle.employee.validation.title"));
	}
	if (empName == "" || empName == null)
		errorList.push(getLocalMessage("vehicle.employee.validation.firstName"));
	
	if (empLName == "" || empLName == null)
		errorList.push(getLocalMessage("vehicle.employee.validation.lastName"));
	
	if (gender == "" || gender == null)
		errorList.push(getLocalMessage("vehicle.employee.validation.gender"));
	
	if (empMobNo == "" || empMobNo == null || empMobNo == undefined){
		errorList.push(getLocalMessage("vehicle.employee.validation.mobNo"));
	}
		
	else {
		if(empMobNo.length < 10) {
			errorList.push(getLocalMessage("vehicle.employee.validation.invalid.mobile.no"));
		}
		/*empMobNo = $("#empMobNo").val();
		var error=validateMobNo(empMobNo);
		if(error.length > 0){
			errorList.push(error);
		}*/
		
	}
	if(empMobNo!=null){
		var data={
				"empMobNo" :empMobNo
		}
		var URL="vehicleEmpDetails.html?checkDuplicateNumber";
		var response = __doAjaxRequest(URL, 'POST', data, false, 'json');
		if(response==false){
			errorList.push(getLocalMessage("vehicle.employee.validation.duplicate.mobile.number.exists"));
		}
	}
	
	if($.trim($("#empEmailId").val()) != '') {
		var emailAdd = $.trim($("#empEmailId").val());
		var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(!emailPattern.test(emailAdd)) {
			errorList.push(getLocalMessage("emp.error.notValid.email"));
		}
	}

	
	if (empAddress == "" || empAddress == null)
		errorList.push(getLocalMessage("vehicle.employee.validation.address"));
	
//	if (empPincode == "" || empPincode == null)
//		errorList.push(getLocalMessage("vehicle.employee.validation.pincode"));
	
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
			'vehicleEmpDetails.html?addEmployeeMaster', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}


function ResetSearchForm(resetBtn) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'vehicleEmpDetails.html', {}, 'html',
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


function getEmpCode(element) {
	var errorList = [];
	var empcode = $("#empUId").val();
	if (empcode != "") {
		var url = "vehicleEmpDetails.html?checkEmpCode";
		var requestData = "empUId=" + $("#empUId").val();

		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');

		if (returnData == false) {
			errorList.push(getLocalMessage("vehicle.employee.validation.employee.code.registered"));
			displayErrorsOnPage(errorList);
		}

	}
}

function getEmpDetails() {
	
	var divName = '.content-page';
	var empcode = $("#empUId").val();
	
	var url = "vehicleEmpDetails.html?getEmpDetail";
	var requestData = "empUId=" + $("#empUId").val();

	var ajaxResponse1 = __doAjaxRequest(url, 'post', requestData, false,'html');
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse1)
	$('.error-div').hide();
	
}

