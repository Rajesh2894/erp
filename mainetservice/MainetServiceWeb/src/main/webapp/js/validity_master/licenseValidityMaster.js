$(document).ready(function() {
	$("#LicCatSubCategry").hide();
	var DeptShortName = $("#DeptShortName").val();
	if(DeptShortName=="ML"){
		$("#LicCatSubCategry").show();	
	}
	$('#licenseValidateForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "-100:-0",
	});

	$("#licenseValidateTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

})

function searchServicesBydeptId() {
	$("#LicCatSubCategry").hide();
	var requestData = {
		"deptId" : $("#departmentId").val()
	};
	$('#serviceId').html('');
	$('#serviceId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading(
			'LicenseValidityMaster.html?searchServicesBydeptId', requestData,
			'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#serviceId').append(
				$("<option></option>").attr("value", value.smServiceId).text(
						(value.smServiceName)));
	});
	$('#serviceId').trigger("chosen:updated");
	// code added for SKDCL ENV
	var ajaxResponse = doAjaxLoading(
			'LicenseValidityMaster.html?getDepartmentShortName', requestData,
			'html');
	var prePopulate1 = JSON.parse(ajaxResponse);
	if (prePopulate1 == "ML") {
		$("#LicCatSubCategry").show();
		$("#DeptShortName").val(prePopulate1);
	}
}

function addLicenseValidityEntry() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'LicenseValidityMaster.html?addLicenseValidityEntry', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function save(element) {
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, 'License Validity saved successfully',
				'LicenseValidityMaster.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function searchLicenseValidityMaster() {

	var errorList = [];

	var deptId = $("#departmentId").val();
	var serviceId = $("#serviceId").val();

	var requestData = {
		"deptId" : deptId,
		"serviceId" : serviceId
	};

	if ((deptId != '' || serviceId != '')
			&& (deptId != '0' || serviceId != '0')) {
		var table = $('#licenseValidateTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'LicenseValidityMaster.html?searchLicenseValidityData',
				requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							result
									.push([
											obj.deptName,
											obj.serviceName,
											obj.licTypeDesc,
											obj.licDependsOnDesc,
											obj.licTenure,
											obj.unitDesc,
											'<td >'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 margin-left-5"  onclick="editOrViewLicValidityMaster(\''
													+ obj.licId
													+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm margin-right-5 margin-left-5" onclick="editOrViewLicValidityMaster(\''
													+ obj.licId
													+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>' ]);

						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList.push(getLocalMessage("adh.validate.search"));
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList
				.push(getLocalMessage("advertiser.master.validate.serach.button"));
		displayErrorsOnPage(errorList);
	}

}

function editOrViewLicValidityMaster(licId, saveMode) {

	var divName = '.content-page';
	var requestData = {

		"licId" : licId,
		"saveMode" : saveMode
	};
	var ajaxResponse = doAjaxLoading(
			'LicenseValidityMaster.html?editOrViewLicenseValidity',
			requestData, 'html', divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function validateForm(errorList) {

	var departmentId = $("#departmentId").val();
	var serviceId = $("#serviceId").val();
	var licDependsOn = $("#licDependsOn").val();
	var licType = $("#licType").val();
	var licTenure = $("#licTenure").val();
	var unit = $("#unit").val();

	if (departmentId == "" || departmentId == undefined || departmentId == null
			|| departmentId == '0') {
		errorList.push(getLocalMessage('license.validity.select.dept.name'));
	}
	if (serviceId == "" || serviceId == undefined || serviceId == null
			|| serviceId == '0') {
		errorList.push(getLocalMessage('license.validity.select.service.name'));
	}
	if (licDependsOn == "" || licDependsOn == undefined || licDependsOn == null
			|| licDependsOn == '0') {
		errorList.push(getLocalMessage('license.validity.select.dependsOn'));
	}
	if (licType == "" || licType == undefined || licType == null
			|| licType == '0') {
		errorList.push(getLocalMessage('license.validity.select.license.type'));
	}
	if (licTenure == "" || licTenure == undefined || licTenure == null) {
		errorList
				.push(getLocalMessage('license.validity.enter.license.tenure'));
	} else {
		if (licTenure == '0') {
			errorList
					.push(getLocalMessage('license.validate.tenure.start.zero'));
		}
	}
	if (unit == "" || unit == undefined || unit == null || departmentId == '0') {
		errorList.push(getLocalMessage('license.validity.select.license.unit'));
	}

	return errorList;
}
function resetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'LicenseValidityMaster.html?addLicenseValidityEntry', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function searchLicenseSubCatagory() {
	var requestData = {
		"triCode1" : $("#triCodeList1").val()
	};
	$('#triCodeList2').html('');
	$('#triCodeList2').append(
			$("<option></option>").attr("value", "0").text("All"));

	var ajaxResponse = doAjaxLoading(
			'LicenseValidityMaster.html?getLicenseSubCatagory', requestData,
			'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#triCodeList2').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#triCodeList2').trigger("chosen:updated");

}