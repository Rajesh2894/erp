$(document).ready(function() {
	
	prepareDateTag();
	$("#InsuranceClaimTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bPaginate" : true,
		"bFilter" : true,
		"ordering":  false,
	    "order": [[ 1, "desc" ]]
	});

	

	function openForm(formUrl, actionParam) {

		var divName = '.content-page';
		var requestData = {

		};
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
				'html', false);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);

	}	
	
	
});

function getVehicleTypeByDept() {

	$('#fuelType').html('');
	$('#vehicleType').html('');
	$('#veid').html('');
	$('#fuelType').html('');
	var department = $("#department").val();
	var requestUrl = "insuranceClaim.html?getDepartmentType";
	var requestData = {
		"id" : department
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#vehicleType').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#vehicleType').append(
				$("<option></option>").attr("value", index).attr("code", index)
						.text(value));
	});
	$('#vehicleType').trigger('chosen:updated');

}

function showVehicleRegNo(obj, param) {

	$('#veid').html('');
	var crtElementId = $("#vehicleType").val();
	var mode = param;
	var requestUrl = "insuranceClaim.html?getVehicleNo";
	var requestData = {
		"id" : crtElementId,
		"mode" : mode
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#veid').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#veid').append(
				$("<option></option>").attr("value", index).attr("code", index)
						.text(value));
	});
	$('#veid').trigger('chosen:updated');

}

function searchInsuranceDetails(obj) {
	
	var errorList = [];
	var divName = '.content-page';
	var department = $('#department').val();
	var vehicleType = $("#vehicleType").val();
	var veid = $('#veid').val();

	if (department == "" || vehicleType == "0" || veid == "" || veid == "0") {
		errorList.push(getLocalMessage("insurance.detail.validation.mandatory.fields"));
	}

	if (errorList.length == 0) {
		if (department != '' || vehicleType != '0' || veNo != '') {

			var requestData = '&department=' + department + '&veid=' + veid
					+ '&vehicleType=' + vehicleType;
			var response = __doAjaxRequest(
					'insuranceClaim.html?SearchInsuranceDetails', 'POST',
					requestData, false, 'html');
			$(divName).html(response);
		}
	}
}

function searchInsuranceClaim() {
	 
    var errorList = [];
	  var department = $('#department').val();
	  var vehicleType =$("#vehicleType").val();
	  var veid =$('#veid').val();
	 
	     if(department=="" || vehicleType=="0" || veid=="" || veid=="0"){
	              errorList.push(getLocalMessage("insurance.detail.validation.mandatory.fields"));
	     }
	 

	    if(errorList.length ==0){
	    if (department != '' || vehicleType != '0' || veNo !='') {
	    	
		var requestData = '&department=' + department + '&veid=' + veid + '&vehicleType=' + vehicleType;
		var table = $('#InsuranceClaimTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest('insuranceClaim.html?SearchInsuranceClaim', 'POST',requestData, false, 'json');
		var insuranceDetailsDTOs = response;
		if (insuranceDetailsDTOs.length == 0) {
			errorList.push(getLocalMessage("insurance.detail.validation.record.not.found"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$  
				.each(  insuranceDetailsDTOs,
						function(index) {
					    var obj = insuranceDetailsDTOs[index];
				    	let insuranceClaimId = obj.insuranceClaimId
				    	let department =obj.deptDesc;
				    	let vehicleType = obj.veDesc;
				    	var veid = obj.veNo;
				    	var insuredBy = obj.insuredName;
				    	let issueDate = getDateFormat(obj.issueDate);
				    	
				    	var viewMessage = getLocalMessage("vehicle.view");
				    	var editMessage = getLocalMessage("vehicle.edit");
				    	
					result
							.push([
									'<div class="text-center">' + (index + 1) + '</div>',
									'<div class="text-center">' + department + '</div>',
									'<div class="text-center">' + vehicleType + '</div>',
									'<div class="text-center">' + veid + '</div>',
									'<div class="text-center">' + insuredBy + '</div>',
									'<div class="text-center">' + issueDate + '</div>',
									
									'<div class="text-center">' 
									+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyInsuranceRequest(\''
									+ insuranceClaimId
									+ '\',\'insuranceClaim.html\',\'viewInsuranceForm\',\'V\')" title="'+viewMessage+'"><i class="fa fa-eye"></i></button>'
									+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyInsuranceRequest(\''
									+ insuranceClaimId
									+ '\',\'insuranceClaim.html\',\'editInsuranceForm\',\'E\')"  title="'+editMessage+'"><i class="fa fa-pencil"></i></button>'
									+ '</div>' ]);

				});
table.rows.add(result);
table.draw();
}
	    } 
	    
else {
//	errorList.push(getLocalMessage("Select At least One Criteria"));
	displayErrorsOnPage(errorList);
}
}

function confirmToProceed(element) {

	var errorList = [];
	var date = new Date();
	var issueDate = $("#issueDate").val();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var eDate = new Date(issueDate.replace(pattern, '$3-$2-$1'));

	if (eDate > date) {
		errorList.push(getLocalMessage("insurance.detail.validation.date1"));
	}
	var department = $("#department").val();
	var vehicleType = $("#vehicleType").val();
	var veNo = $("#veid").val();
	var vePurSource = $("#vePurSource").val();
	var issueDate = $("#issueDate").val();
	var endDate = $("#endDate").val();
	var insuredAmount = $("#insuredAmount").val();
	var claimAmount = $("#claimAmount").val();
	var claimApprovedAmount = $("#claimApprovedAmount").val();
	var iDate = new Date(issueDate.replace(pattern, '$3-$2-$1'));
	var enDate = new Date(endDate.replace(pattern, '$3-$2-$1'));

	if (department == null || department == "") {
		errorList.push(getLocalMessage("insurance.detail.validation.select.department"));
	}
	if (vehicleType == null || vehicleType == "0") {
		errorList.push(getLocalMessage("insurance.detail.validation.select.vehicle.type"));
	}
	if (veNo == null || veNo == "0" || veNo == "") {
		errorList.push(getLocalMessage("insurance.detail.validation.select.vehice.number"));
	}
	if (vePurSource == null || vePurSource == "" || vePurSource == "0") {
		errorList.push(getLocalMessage("insurance.claim.select.any.insured.by"));
	}
	if (issueDate == null || issueDate == "") {
		errorList.push(getLocalMessage("insurance.claim.insurance.issue.date.cannot.empty"));
		if (endDate == null || endDate == "")
			errorList.push(getLocalMessage("insurance.detail.validation.enter.insurance.end.date"));
	}
	if (iDate > enDate) {
		errorList.push(getLocalMessage("insurance.detail.validation.date2"));
	}
	if (insuredAmount == null || insuredAmount == "") {
		errorList.push(getLocalMessage("insurance.detail.validation.enter.insured.amount"));
	}
	if (insuredAmount != null && insuredAmount != "" && insuredAmount == "0") {
		errorList.push(getLocalMessage("insurance.detail.validation.insured.amount1"));
	}
	if (claimAmount == null || claimAmount == "" || claimAmount == NaN ) {
		errorList.push(getLocalMessage("insurance.validation.claim.amount"));
	}
	if (claimAmount != null && claimAmount != "" && claimAmount == "0") {
		errorList.push(getLocalMessage("insurance.validation.claim.amount.greater.zero"));
	}
	if (claimApprovedAmount == null || claimApprovedAmount == "") {
		errorList.push(getLocalMessage("insurance.claim.select.any.claim.approved.amount"));
	}
	if (claimApprovedAmount != null && claimApprovedAmount != "" && claimApprovedAmount == "0") {
		errorList.push(getLocalMessage("insurance.validation.claim.approvedamount.greater.zero"));
	}
	if (parseFloat(claimAmount) > insuredAmount) {
		errorList.push(getLocalMessage("insurance.validation.claim.amount.should.less.insured.amount"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var requestData = {
			"veNo" : veNo,
			"issueDate" : issueDate,
			"endDate" : endDate
		}
		var URL = 'insuranceClaim.html?recordAlreadyExists';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'json');

		if (returnData) {
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
				return false;
			} else {
				 return saveOrUpdateForm(element, "", 'insuranceClaim.html',
				 'saveform');
			}
		} else {
			errorList
					.push(getLocalMessage("insurance.validation.claim.already.exists"))
			displayErrorsOnPage(errorList);
		}
	}
}

function modifyInsuranceRequest(insuranceDetId, formUrl, actionParam, mode) {
    
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : insuranceDetId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

 }

var fileArray = [];
$("#deleteDoc").on(
		"click",
		'#deleteFile',
		function(e) {
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