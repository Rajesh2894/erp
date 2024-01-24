$(document).ready(function() {

	$("#frmMetadataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$('.empClass').show();
	$('.roleClass').hide();
	$('.assignDeptClass').hide();
});

$("#attachDoc").on("click", '.delBtn', function(e) {

	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
	}
	e.preventDefault();
});

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
					removeFileIdArray.push(fileId);
				}
				$('#removeFileById').val(removeFileIdArray);
			}
		});

function documentUpload(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('KmsMetadata.html?fileCountUpload', 'POST',
			requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}

$(function() {
	$("#deptId").change(
			function() {

				var divName = '.content-page';
				var deptId = $("#deptId").val();
				var requestData = {
					'deptId' : deptId
				}
				var ajaxResponse = __doAjaxRequest(
						'KmsMetadata.html?getKmsMetadata', 'POST', requestData,
						false, 'html');
				$(divName).html(ajaxResponse);

			});
});

function getEmpOrRoleList() {

	var radiovalue = $('input[type=radio]:checked').val();
	var divName = '.content-page';
	var requestData = {
		'radiovalue' : radiovalue
	}
	if (radiovalue == 'R') {
		$('.roleClass').show();
		$('.empClass').hide();
		$('.assignDeptClass').hide();
	} else if (radiovalue == 'E') {
		$('.empClass').show();
		$('.roleClass').hide();
		$('.assignDeptClass').hide();
	} else if (radiovalue == 'D') {
		$('.assignDeptClass').show();
		$('.empClass').hide();
		$('.roleClass').hide();
	}
}

function saveForm(element) {

	var errorList = [];
	errorList = validateDetails(errorList);

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, "", 'KmsMetadata.html', 'saveform');
	}
}

function validateDetails(errorList) {

	var errorList = [];
	var flag = "N";
	var deptId = $('#deptId').val();
	var emp = $('#emp').val();
	var gmid = $('#gmid').val();
	var assignDeptIds = $('#assignDeptIds').val();
	if (deptId == null || deptId == "") {
		errorList.push(getLocalMessage("dms.pleaseSelectDept"));
	}
	if (emp == null && gmid == null && assignDeptIds == null) {
		errorList.push(getLocalMessage("dms.knowledgeShare.valid"));
	}
	$(".appendableClass ").each(function(i) {
		if (flag == "N") {
			var docType = $("#doc_DESC_ENGL" + i).val();
			if (docType == null || docType == 0) {
				flag = "Y";
				errorList.push(getLocalMessage("dms.dcoType.valid"));
			}
		}
	});
	return errorList;
}
