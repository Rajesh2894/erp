function vlaidateForm() {
	var errorList = [];

	var titleId = $("#titleId").val();
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var gender = $("#gender").val();

	var bldgName = $("#bldgName").val();
	var blockName = $("#blockName").val();
	var roadName = $("#roadName").val();
	var wardNo = $("#wardNo").val();
	var cityName = $("#cityName").val();
	var pincodeNo = $("#pincodeNo").val();
	var mobileNo = $("#mobileNo").val();
	var email = $("#email").val();

	var deId = $("#deId").val();
	var serviceId = $("#serviceId").val();

	if (titleId == "0") {
		errorList.push(getLocalMessage("requestDTO.valid.title"));
	}
	if (fName == "") {
		errorList.push(getLocalMessage("requestDTO.valid.fName"));
	}
	if (lName == "" || lName == null || lName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.lName"));
	}
	//#133472 commented fields made as optional
	/*if (gender == "0" || gender == null || gender == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.gender"));
	}*/
	if (bldgName == "" || bldgName == null || bldgName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.buildingName"));
	}
	/*if (blockName == "" || blockName == null || blockName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.blockName"));
	}
	if (roadName == "" || roadName == null || roadName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.roadName"));
	}*/
	/*if (wardNo == "Select Ward Name" || wardNo == "" || wardNo == null
			|| wardNo == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.wardName"));
	}*/
	if (cityName == "" || cityName == null || cityName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.cityName"));
	}
	/*if (pincodeNo == "" || pincodeNo == null || pincodeNo == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.pinCode"));
	}*/ 
	if (pincodeNo != "") {
		 var pattern=/^(?!0{6})[A-Z0-9][0-9]{5}$/;
			if (!pattern.test(pincodeNo)) {
				errorList.push(getLocalMessage("requestDTO.valid.invalidPinCode"));
		    }else if(pincodeNo < 400601 || pincodeNo > 421699){
		    	errorList.push(getLocalMessage("rts.kdmc.pincode.validate"));
		    }
		}
	
	if (mobileNo == "" || mobileNo == null || mobileNo == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.mobile"));
	} else {
		if (mobileNo.length < 10) {
			errorList.push(getLocalMessage("requestDTO.valid.invalidMobile"));
		}
	}
	/*if (email == "" || email == null || email == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.emailId"));
	} */
		var emailId = $.trim($("#email").val());
		if (emailId != "") {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(emailId);
			if (!valid) {
				errorList.push(getLocalMessage("requestDTO.valid.invalidEmail"));
			}
		}
	
	/*if (serviceId == "Select Service Name") {
		errorList.push(getLocalMessage("requestDTO.valid.service"));
	}*/
	return errorList;
}
function serviceForm(element, formUrl, actionParam) {

	var errorList = [];
	errorList = vlaidateForm();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	// set serviceCode in hidden value
	else {

		var serviceId = $("#serviceId").val();
		var saveMode = $("#saveMode").val();
		/*
		 * var requestData = { "serviceId" : serviceId
		 *  };
		 */
		var requestData = element;
		var divName = '.content-page'
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
				requestData, false, '', 'html');

		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function getServiceList(element, formUrl, actionParam) {
	var requestData = element;
	var divName = '.content-page'
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;

	var requestData = __serializeForm(theForm);
	var serviceName = $("#serviceId>option:selected").text();
	var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
			requestData, false, '', 'html');
	$(divName).html(response);
	$(divName).removeClass('ajaxLoader')
	prepareTags();
}

function back() {
	window.location.href = getLocalMessage("CitizenHome.html");
}

function resetApplicantForm() {
	var requestData = null;
	var divName = '.content-page'
	var ajaxResponse = __doAjaxRequest('rtsService.html?applicantForm', 'POST',
			requestData, false, '', 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}
