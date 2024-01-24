$(document).ready(function() {

	$("#cemeteryDataTable").dataTable({
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
			$('#ceName').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#ceName").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'ceNameMar',event,'');
				}else{
					$("#ceNameMar").val('');
				}
			});
			$('#ceAddr').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#ceAddr").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'ceAddrMar',event,'');
				}else{
					$("#ceAddrMar").val('');
				}
			});
		}
});

function confirmToProceed(element) {
	var errorList = [];
	var ceName = $("#ceName").val();
	var ceNameMar = $("#ceNameMar").val();
	var ceAddr = $("#ceAddr").val();
	var ceAddrMar = $("#ceAddrMar").val();
	var cpdTypeId = $("#cpdTypeId").val();

	if (ceName == null || ceName == "") {
		errorList.push(getLocalMessage("CemeteryMasterDTO.ceNm"));
	}
	
	if (ceNameMar == null || ceNameMar == "") {
		errorList.push(getLocalMessage("CemeteryMasterDTO.ceNmReg"));
	}
	
	if (ceAddr == null || ceAddr == "") {
		errorList.push(getLocalMessage("CemeteryMasterDTO.ceaddr"));
	}
	
	if (ceAddrMar == null || ceAddrMar == "") {
		errorList.push(getLocalMessage("CemeteryMasterDTO.ceaddrReg"));
	}
	
	if (cpdTypeId == null || cpdTypeId == 0) {
		errorList.push(getLocalMessage("CemeteryMasterDTO.ceSelect"));
	}	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	else{
		return saveOrUpdateForm(element, "", 'CemeteryMaster.html', 'saveform');
	} 
}


function searchCemeteryData() {
	var errorList = [];
	var ceName = $('#ceName').val();
	var cpdTypeId = $('#cpdTypeId').val();

	if (ceName != 0 || cpdTypeId != '') {
		var requestData = '&ceName=' + ceName + '&cpdTypeId=' + cpdTypeId;
		var table = $('#cemeteryDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest('CemeteryMaster.html?searchCemetery',
				'POST', requestData, false, 'json');
		var cemeteryMasterDtoList = response;
		if (cemeteryMasterDtoList.length == 0) {
			errorList.push(getLocalMessage("record.not.found.msg"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						cemeteryMasterDtoList,
						function(index) {
							var obj = cemeteryMasterDtoList[index];
							let ceId = obj.ceId;
							let ceNameMar = obj.ceNameMar;
							let ceName = obj.ceName;
							let ceAddrMar = obj.ceAddrMar;
							let ceAddr = obj.ceAddr;
							let cpdTypeId = obj.cpdDesc;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ ceNameMar + '</div>',
											'<div class="text-center">'
													+ ceName + '</div>',
											'<div class="text-center">'
													+ ceAddrMar + '</div>',
											'<div class="text-center">'
													+ ceAddr + '</div>',
											'<div class="text-center">'
													+ cpdTypeId + '</div>',
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyCemetery(\''
													+ ceId
													+ '\',\'CemeteryMaster.html\',\'viewBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyCemetery(\''
													+ ceId
													+ '\',\'CemeteryMaster.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		errorList.push(getLocalMessage("error"));
		displayErrorsOnPage(errorList);
	}
}

function modifyCemetery(ceId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : ceId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function resetForm(){
	window.location.href = 'CemeteryMaster.html';	
}


