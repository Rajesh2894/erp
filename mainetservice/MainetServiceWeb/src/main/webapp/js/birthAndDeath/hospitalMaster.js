$(document).ready(function() {

	$("#hospitalDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	//D#127333 Translator	 
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$('#hiName').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#hiName").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'hiNameMar',event,'');
				}else{
					$("#hiNameMar").val('');
				}
			});
			$('#hiAddr').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#hiAddr").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'hiAddrMar',event,'');
				}else{
					$("#hiAddrMar").val('');
				}
			});
		}
});

function searchHospitalData() {
	var errorList = [];
	var hiName = $('#hiName').val();
	var cpdTypeId = $('#cpdTypeId').val();

	if (hiName != 0 || cpdTypeId != '') {
		var requestData = '&hiName=' + hiName + '&cpdTypeId=' + cpdTypeId;
		var table = $('#hospitalDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest('HospitalMaster.html?searchHospital',
				'POST', requestData, false, 'json');
		var hospitalMasterDtoList = response;
		if (hospitalMasterDtoList.length == 0) {
			errorList.push(getLocalMessage("record.not.found.msg"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						hospitalMasterDtoList,
						function(index) {
							var obj = hospitalMasterDtoList[index];
							let hiId = obj.hiId;
							let hiNameMar = obj.hiNameMar;
							let hiName = obj.hiName;
							let hiAddrMar = obj.hiAddrMar;
							let hiAddr = obj.hiAddr;
							let cpdTypeId = obj.cpdDesc;
							let hiCode = obj.hiCode;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ hiNameMar + '</div>',
											'<div class="text-center">'
													+ hiName + '</div>',
											'<div class="text-center">'
													+ hiAddrMar + '</div>',
											'<div class="text-center">'
													+ hiAddr + '</div>',
											'<div class="text-center">'
													+ cpdTypeId + '</div>',
											'<div class="text-center">'
													+ hiCode + '</div>',
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyHospital(\''
													+ hiId
													+ '\',\'HospitalMaster.html\',\'viewBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyHospital(\''
													+ hiId
													+ '\',\'HospitalMaster.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList.push(getLocalMessage("error"));
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
	//var hiCode = $("#hiCode").val();

	if (hiName == null || hiName == "") {
		errorList.push(getLocalMessage("HospitalMasterDTO.hospNm"));
	}
	if (hiNameMar == null || hiNameMar == "") {
		errorList.push(getLocalMessage("HospitalMasterDTO.hospNmReg"));
	}
	if (hiAddr == null || hiAddr == "") {
		errorList.push(getLocalMessage("HospitalMasterDTO.hospAddr"));
	}
	if (hiAddrMar == null || hiAddrMar == "") {
		errorList.push(getLocalMessage("HospitalMasterDTO.hospAddrReg"));
	}
	if (cpdTypeId == null || cpdTypeId == 0) {
		errorList.push(getLocalMessage("HospitalMasterDTO.hospSelect"));
	}
	/*if (hiCode == null || hiCode == "") {
		errorList.push(getLocalMessage("HospitalMasterDTO.hospCode"));
	} */	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	else{
		return saveOrUpdateForm(element, "", 'HospitalMaster.html', 'saveform');
	} 
}


function modifyHospital(hiId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : hiId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}


function resetForm(element){
	$("#frmHospitalMaster").submit();
}


function openForm(formUrl, actionParam) {

	var divName = '.content-page';
	var requestData = {

	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}



