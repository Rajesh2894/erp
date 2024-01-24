$(document).ready(function() {
	prepareDateTag();


	
	$("#frmStaffTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"bInfo" : true,
		"lengthChange" : true
	});

	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});

	// add multiple select / deselect functionality
	$('#selectall').on('change', function(e) {
		  let chx = $(this)[0].checked ? true : false;
		  $(':checkbox').prop('checked', chx);
		});
	
	$(':checkbox:not(#selectall)').on('change', function(e) {
		  if (!$(this).is(':checked')) {
		    $('#selectall').prop('checked', false);
		  }
		});
	
	$('button').on('click', function(e) {
		  let val = $(':checkbox:checked').map(function() {
		    return $(this).val();
		  }).get();
		  $('#saveBtn').val(val);
		});

	// if particular checkbox is selected then to set value
	$(".case").change(function() {

		if ($(this).prop('checked') == true) {
			$(this).val("Y");
		} else {
			$(this).val("N");
		}
	});

});
function clearDataTable() {
	var table = $('#frmStaffTbl').DataTable();
	table.clear().draw();
	$('.hideThisOne').hide();
}

function saveStaff(obj) {

	var errorList = [];
	errorList = validateRowData(obj);

	var reasonTransfer = $('#reasonTransfer').val();
	if (reasonTransfer == "" || reasonTransfer == null) {
		errorList.push(getLocalMessage("securityManagement.select.reason.transfer"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'TransferAndDutyScheduling.html',
				'saveform');

	}
}

function validateRowData(obj) {

	var errorList = [];
	var courtName = [];
	var n = 0;
	var m = 0;
	var a = 0;
	var t = 0;
	var j = 0;
	var s = 0;
	if ($.fn.DataTable.isDataTable('#frmStaffTbl')) {
		$('#frmStaffTbl').DataTable().destroy();
	}

	$("#frmStaffTbl tbody tr")
			.each(
					function(i) {
						var memberSelected = $("#memberSelected" + i).val();
						if (memberSelected == "Y") {
							s++;

							var contStaffSchFrom = $("#contStaffSchFrom" + i)
									.val();
							var contStaffSchTo = $("#contStaffSchTo" + i).val();
							var cpdShiftId = $("#cpdShiftId" + i).val();
							var locId = $("#location" + i).val();
							var rowCount = i + 1;

							if (contStaffSchFrom == "" && n == 0) {
								n++;
								errorList
										.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.contStaffSchFrom")+ " " + rowCount);
							}
							if (contStaffSchTo == "" && m == 0) {
								m++;
								errorList
										.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.contStaffSchTo")+ " " + rowCount);
							}

							var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
							var eDate = new Date(contStaffSchFrom.replace(
									pattern, '$3-$2-$1'));
							var sDate = new Date(contStaffSchTo.replace(
									pattern, '$3-$2-$1'));

							if (eDate > sDate) {

								errorList
										.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.fromDateToDate "));
							}

							if (cpdShiftId == 0 && a == 0) {
								a++;
								errorList
										.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.cpdShiftId")+ " " + rowCount);
							}
							if (locId == 0 && t == 0) {
								t++;
								errorList
										.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.location")+ " " + rowCount);
							}
						}
					});
	if (s == 0) {
		errorList
				.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.selectOne "));
	}
	return errorList;
}

function SearchStaff() {
	var errorList = [];
	var empTypeId = $('#empTypeId').val();
	var vendorId = $('#vendorId').val();
	var cpdShiftId = $('#cpdShiftId').val();
	var locId = $('#locId').val();

	if (empTypeId == 0 || empTypeId == null)
		errorList.push(getLocalMessage("DeploymentOfStaffDTO.Validation.empTypeId"));
	if (vendorId == "" || vendorId == null)
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.vendorId"));
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	else if (empTypeId != 0 || vendorId != 0 || cpdShiftId != 0 || locId != 0) {
		var requestData = 'empTypeId=' + empTypeId + '&vendorId=' + vendorId
				+ '&cpdShiftId=' + cpdShiftId + '&locId=' + locId;
		var URL = "TransferAndDutyScheduling.html?searchStaffDetails";
		var response = __doAjaxRequest(URL, 'POST', requestData, false);
		var staffMasterDTOList = response;
		if (staffMasterDTOList.length == "") {
			errorList.push(getLocalMessage("DeploymentOfStaffDTO.Validation.notFound"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();

	} else {
		errorList
				.push(getLocalMessage("TransferSchedulingOfStaffDTO.Validation.selectOneCriteria"));
		displayErrorsOnPage(errorList);
	}

}
