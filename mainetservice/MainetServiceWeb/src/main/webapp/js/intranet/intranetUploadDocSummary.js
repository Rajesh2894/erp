$(document).ready(function() {
	
	prepareDateTag();
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});
	
	prepareDateTag1();
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
	
	$("#hospitalDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function searchIntranetData() {
	var errorList = [];
	var docCateType = $('#docCateType').val();

	if (docCateType != 0) {
		var requestData = '&docCateType=' + docCateType;
		var table = $('#hospitalDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(
				'UploadIntranetDocSummary.html?searchIntranet', 'POST',
				requestData, false, 'json');
		var hospitalMasterDtoList = response;
		if (hospitalMasterDtoList.length == 0) {
			errorList.push(getLocalMessage("record not found"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						hospitalMasterDtoList,
						function(index) {
							var obj = hospitalMasterDtoList[index];
							let inId = obj.inId;
							let docCateType = obj.docCatDesc;
							let docName = obj.docName;
							let docDesc = obj.docDesc;
							let deptId = obj.deptDesc;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ docCateType + '</div>',
											'<div class="text-center">'
													+ docName + '</div>',
											'<div class="text-center">'
													+ docDesc + '</div>',
											'<div class="text-center">'
													+ deptId + '</div>',

											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyIntranetDoc(\''
													+ inId
													+ '\',\'UploadIntranetDocSummary.html\',\'editIntranetForm\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList
				.push(getLocalMessage("No records Found Please select Document Category Type"));
		displayErrorsOnPage(errorList);
	}
}

function confirmToProceed(element) {
	var errorList = [];
	var hiName = $("#hiName").val();
	var hiNameMar = $("#hiNameMar").val();
	var hiAddr = $("#hiAddr").val();
	var hiAddrMar = $("#hiAddrMar").val();
	var cpdTypeId = $("#cpdTypeId").val();
	var hiCode = $("#hiCode").val();

	if (hiName == null || hiName == "") {
		errorList.push(getLocalMessage("Enter Hospital Name"));
	}
	if (hiNameMar == null || hiNameMar == "") {
		errorList.push(getLocalMessage("Enter Hospital Name(Reg)"));
	}
	if (hiAddr == null || hiAddr == "") {
		errorList.push(getLocalMessage("Enter Hospital Address"));
	}
	if (hiAddrMar == null || hiAddrMar == "") {
		errorList.push(getLocalMessage("Enter Hospital Address(Reg)"));
	}
	if (cpdTypeId == null || cpdTypeId == 0) {
		errorList.push(getLocalMessage("Please select Hospital"));
	}
	if (hiCode == null || hiCode == "") {
		errorList.push(getLocalMessage("Enter Hospital Code"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(element, "", 'HospitalMaster.html', 'saveform');
	}
}

function modifyIntranetDoc(inId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : inId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function resetForm(element) {
	$("#frmUploadIntranetDocSummary").submit();
}

function openFormIntranet(formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode" : mode,
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}


function saveIntranetUploadData(element) {
	
	var errorList = [];
	var docName = $('#docName').val();
	var docDesc = $("#docDesc").val();
	var department = $("#department").val();
	var docCateType = $("#docCateType").val();
	var docFromDate = $("#docFromDate").val();
	var docToDate = $("#docToDate").val();
	var docCatOrder = $("#docCatOrder").val();
	var docOrderNo = $("#docOrderNo").val();
	var docStatus = $("#docStatus").val();
	var uploadFileList = $("#uploadFileList").val();
	var saveMode = $("#saveMode").val();

	if (docName == null || docName == "") {
		errorList.push(getLocalMessage("Please Enter Document Name"));
	}
	if (docDesc == null || docDesc == "") {
		errorList.push(getLocalMessage("Please Enter Document Desc"));
	}
	if (department == null || department == "") {
		errorList.push(getLocalMessage("Please Enter Department"));
	}
	if (docCateType == null || docCateType == 0) {
		errorList.push(getLocalMessage("Please Enter Document Category"));
	}
	if (docFromDate == null || docFromDate == "") {
		errorList.push(getLocalMessage("Please Enter Document From Date"));
	}
	if (docToDate == null || docToDate == "") {
		errorList.push(getLocalMessage("Please Enter Document To Date"));
	}
	if (docCatOrder == null || docCatOrder == "") {
		errorList.push(getLocalMessage("Please Enter Document Category Order"));
	}
	/*if (docOrderNo == null || docOrderNo == "") {
		errorList.push(getLocalMessage("Please Enter Document Category Type"));
	}*/
	if (saveMode!="" && (saveMode!="E")){
		if (uploadFileList == null || uploadFileList == "") {
			errorList.push(getLocalMessage("Please Upload File Document"));
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		if ($("#frmUploadIntranetDocSummary").valid() == true)
			var errorList = [];
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		} else {
			if ($("#frmUploadIntranetDocSummary").valid() == true) {
				return saveOrUpdateForm(element, "", 'UploadIntranetDocSummary.html', 'saveform');
			} else {
			}
		}
		return saveOrUpdateForm(element, "", 'UploadIntranetDocSummary.html', 'saveform');
	}

}

function resetIntranetForm(element) {
	var docName = $('#docName').val("");
	var docDesc = $("#docDesc").val("");
	var department = $("#department").val(0);
	var docCateType = $("#docCateType").val(0);
	var docFromDate = $("#docFromDate").val("");
	var docToDate = $("#docToDate").val("");
	var docCatOrder = $("#docCatOrder").val("");
	var docOrderNo = $("#docOrderNo").val("");
	var docStatus = $("#docStatus").val("");
	var uploadFileList = $("#uploadFileList").val("");

}

/*ashish test*/



