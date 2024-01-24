function searchForm(obj) {
	var errorList = [];
	var contStaffName = $('#contStaffName').val();
	var empTypeId = $("#empTypeId").val();
	var locId = $("#location").val();
	var vendorId = $("#vmVendorid").val();
	var cpdShiftId = $("#cpdShiftId").val();

	if (contStaffName == "" || contStaffName == undefined) {
		contStaffName = "X";
	}

	var reqData = 'contStaffName=' + contStaffName + '&empTypeId=' + empTypeId
			+ '&locId=' + locId + '&vendorId=' + vendorId + '&cpdShiftId='
			+ cpdShiftId;
	var URL = 'SecurityReport.html?GetLocationDetails';
	var resData = __doAjaxRequest(URL, 'POST', reqData, false);

	window.open(resData, '_blank');

	return true;

}

function resetForm1() {
	$("#frmLocationDetailStaff").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}
