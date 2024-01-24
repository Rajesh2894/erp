$(document).ready(function() {

	var showHideFlag = $("#showHideFlag").val();
    if (showHideFlag == 'N' || showHideFlag == 'M')
	$('.viewBlockTable').hide();
    else
     $('.viewBlockTable').show();
    
	$("#blockDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$('.showMand').hide();

});

function searchForm(obj, formUrl, actionParam) {

	var errorList = [];
    var orgNameId = $("#organizationNameId").val();
    var orgTypeId = $("#orgTypeId").val();
    var allocationYearId = $("#allocationYearId").val();
    var sdb1 = $("#sdb1").val();
	var sdb2 = $("#sdb2").val();
	var sdb3 = $("#sdb3").val();
    
    var showHideFlag = $("#showHideFlag").val();
    var divName = '.content-page';
	if ((orgTypeId == "0" || orgTypeId == undefined)  && (orgNameId == "0" || orgNameId == undefined) && (allocationYearId == "0" || allocationYearId == undefined)
			&& (sdb1 == "0" || sdb1 =="") && (sdb2 == "0" || sdb2 =="") && (sdb3 == "0" || sdb3 =="")){
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}else if((orgTypeId !="0" || orgTypeId != undefined) && (orgNameId == "0" || orgNameId == undefined)){
		errorList.push(getLocalMessage("sfac.validation.OrganizationName"));
	}else if((orgNameId !="0" || orgNameId != undefined) && (orgTypeId == "0" || orgTypeId == undefined)){
		errorList.push(getLocalMessage("sfac.validation.organizationType"));
	}
	if (errorList.length == 0) {
		var orgTypeId = $("#orgTypeId").val();
	    var organizationNameId = $("#organizationNameId").val();
	    var allocationYearId = $("#allocationYearId").val();

		var requestData = {
			"orgTypeId" : orgTypeId,
			"organizationNameId" : organizationNameId,
			"allocationYearId" : allocationYearId,
			"sdb1" : sdb1,
			"sdb2" : sdb2,
			"sdb3" : sdb3
		};
		/*var table = $('#blockDatatables').DataTable();
		table.rows().remove().draw();*/
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'ChangeofBlockForm.html?getAllBlockData', requestData,
				'html');
		if (showHideFlag == 'M') {
			errorList.push(getLocalMessage("collection.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		}
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveAllocationOfBlockForm(obj) {
	var errorList = [];
	
	var orgNameId = $("#organizationNameId").val();
	var orgTypeId = $("#orgTypeId").val();
	var allocationYearId = $("#allocationYearId").val();
/*	if (orgNameId == "0" || orgNameId == undefined || orgNameId == "")
		errorList.push(getLocalMessage("sfac.validation.OrganizationName"));

	if (orgTypeId == "0" || orgTypeId == undefined || orgTypeId == "")
		errorList.push(getLocalMessage("sfac.validation.organizationType"));

	if (allocationYearId == "0" || allocationYearId == undefined || allocationYearId == "")
		errorList.push(getLocalMessage("sfac.validation.AllocationYear"));*/
	

		errorList = errorList.concat(validateFormDetails());

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Block Details Saved Successfully!", 'AdminHome.html', 'saveAllocationOfBlockForm');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateFormDetails(errorList) {
	var block = [];
	var errorList =[];
	
	var rowCount = $('#blockDetails tr').length;
	if ($.fn.DataTable.isDataTable('#blockDetails')) {
		$('#blockDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#blockDetails tbody tr")
				.each(
						function(i) {
							

							if (rowCount <= 2) {
								
								var stateId = $("#stateId" + i).val();
								var distId = $("#distId" + i).val();
								var blckId = $("#blckId" + i).val();
						 		
								var level = 1;
								var reasonForBlockChange = $("#reason" + i).val();
								var allocationCategory = $("#allocationCategoryDet" + i).val();
								var allocationSubCategory = $("#allocationSubCategoryDet" + i).val();
								var cat = $("#allocationCategoryDet" + i).find("option:selected").attr('code');
								 

							} else {
								var stateId = $("#stateId" + i).val();
								var distId = $("#distId" + i).val();
								var blckId = $("#blckId" + i).val();
								
								var reasonForBlockChange = $("#reason" + i).val();
								var allocationCategory = $("#allocationCategoryDet" + i).val();
								var allocationSubCategory = $("#allocationSubCategoryDet" + i).val();
								var cat = $("#allocationCategoryDet" + i).find("option:selected").attr('code');
								var level = i+1;

							}
				
							if (stateId == "" || stateId == undefined || stateId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.SDB1")
												+ " " + (i + 1));
							}

							if (distId == "" || distId == undefined	|| distId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.SDB2")
												+ " " + (i + 1));
							}
							
							if (blckId == "" || blckId == undefined || blckId == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.SDB3")
												+ " " + (i + 1));
							}else if(blckId !=null || blckId !=0){
								if (block.includes(blckId)) {
									errorList
											.push(getLocalMessage("sfac.dup.block")
													+ (i + 1));
								}
								if (errorList.length == 0) {
									block.push(blckId);
								}
							}
							
							
							
							if (allocationCategory == "" || allocationCategory == undefined
									|| allocationCategory == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.allCategory")
												+ " " + (i + 1));
							}
							if (cat =='SPC' && (allocationSubCategory == ""	|| allocationSubCategory == undefined
								|| allocationSubCategory == "0")) {
								errorList.push(getLocalMessage("sfac.validation.alcTargetSubCategory")
												+ " " + (i + 1));
							}
							
							if (reasonForBlockChange == "" || reasonForBlockChange == undefined
									|| reasonForBlockChange == "0") {
								errorList
										.push(getLocalMessage("sfac.validation.reason")
												+ " " + (i + 1));
							}
						
						});
	return errorList;
}


function getDistrictList(id) {
	var requestData = {
		"stateId" : $("#stateId"+ id).val()
	};
		var URL = 'ChangeofBlockForm.html?getDistrictList';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		$('#distId'+ id).html('');
		$('#distId'+ id).append(
				$("<option></option>").attr("value", "").text(getLocalMessage('selectdropdown')));
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#distId'+ id).append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.lookUpDesc)));
		});
		$('#distId'+ id).trigger("chosen:updated");
}


function getBlockList(id) {
	var distId = $("#distId" + id).val();
    var orgTypeId = $("#orgTypeId").val();
    var requestData = {
		"distId" : distId,
		"orgTypeId" : orgTypeId
	};
	var URL = 'ChangeofBlockForm.html?getBlockList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#blckId' + id).html('');
	$('#blckId' + id).append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#blckId'+ id).append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#blckId'+ id).trigger("chosen:updated");
}

/*function reOrderBlockDetailsSequence() {
	
	$("#blockDetails tbody tr").each(
			function(i) {
				
				$(this).find("select:eq(0)").attr("id", "stateId" + i);
				$(this).find("select:eq(1)").attr("id", "distId" + i);
				$(this).find("select:eq(2)").attr("id", "blckId" + i);
				
				$(this).find("select:eq(3)").attr("id", "allCate" + i);
				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
				$(this).find("input:text:eq(1)").attr("id", "reasonForBlockChange" + i);
			
				
				$(this).find("select:eq(0)").attr("name", "blockDtoList[" + i + "].stateId")
				.attr("onchange","getDistrictList(" + i + ")");
				$(this).find("select:eq(1)").attr("name", "blockDtoList[" + i + "].distId").attr("onchange","getBlockList(" + i + ")");
				$(this).find("select:eq(2)").attr("name", "blockDtoList[" + i + "].blckId");
			
				$(this).find("select:eq(3)").attr("name",	"blockDtoList[" + i	+ "].allCate");
				$(this).find("input:text:eq(1)").attr("name", "blockDtoList[" + i +"].reasonForBlockChange");
				$("#sNo" + i).val(i + 1);
			});

        //$('#blockDetails tr:last div[id^=file_list_]').html('');
}*/

/*$(function() {
	 To add new Row into table 
	$("#blockDetails").on('click', '.addItemCF', function() {
		var errorList = [];
		errorList = validateFormDetails(errorList);
		if (errorList.length == 0) {
			var content = $("#blockDetails").find('tr:eq(1)').clone();
			$("#blockDetails").append(content);
			content.find("select").val('');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			content.find("input:file").val("");
			$('.error-div').hide();
			 reOrderBlockDetailsSequence(); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

$(function() {
	 To add new Row into table 
	$("#blockDetails").on('click', '.delButton', function() {
		if ($("#blockDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderBlockDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("sfac.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});*/


//Function used to sent proposal for approval
function sendForApproval(proposalId, proposalType) {
    
    var divName = '.content-page';
    var url = "ChangeofBlockForm.html?sendForApproval";
    var requestData = {
	'proposalId' : proposalId,
	'proposalType' : proposalType
    }
    var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
    showProposalApprovalStatus(ajaxResponse, proposalId);
 
}

//function used to show proposal approval status
function showProposalApprovalStatus(ajaxResponse, proposalId) {

    var errMsgDiv = '.msg-dialog-box';
    var message = '';
    var cls = '';
    var sMsg = '';
    var Proceed = getLocalMessage("council.proceed");
    if (ajaxResponse.checkStausApproval == "Y") {
	sMsg = getLocalMessage("council.proposal.approval.initiated");
    } else if (ajaxResponse.checkStausApproval == "N") {
	sMsg = getLocalMessage("council.proposal.approval.process.not.defined");
    } else if (ajaxResponse.checkStausApproval == "A") {
	sMsg = getLocalMessage("council.proposal.creation");
    } else {
	sMsg = getLocalMessage("council.proposal.approval.initiated.fail");
	sMsg1 = getLocalMessage("council.proposal.approval.contact.administration");
    }

    if (ajaxResponse.checkStausApproval == "Y") {
	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center text-blue-2 padding-12">' + sMsg
		+ '</p>' + '</div>';

	message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    } else if (ajaxResponse.checkStausApproval == "N") {

	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center text-blue-2 padding-12">' + sMsg
		+ '</p>' + '</div>';

	message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    } else if (ajaxResponse.checkStausApproval == "A") {

	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center text-blue-2 padding-12">' + sMsg
		+ '</p>' + '</div>';

	message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    } else {
	message += '<div class="text-center padding-top-25">'
		+ '<p class="text-center red padding-12">' + sMsg + '</p>'
		+ '<p class="text-center red padding-12">' + sMsg1 + '</p>'
		+ '</div>';

	message += '<div class=\'text-center padding-top-10 padding-bottom-10\'>'
		+ '<input class="btn btn-info" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed
		+ '\'  id=\'Proceed\' '
		+ ' onclick="closeSend();"/>'
		+ '</div>';
    }
    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $(errMsgDiv).html(message);
    $(errMsgDiv).show();
    $('#btnNo').focus();
    showModalBoxWithoutClose(errMsgDiv);
    return false;
}

function saveAllocationOfBlockApprovalData(element) {
	var errorList = [];	
	if ($("#authRemark").val() == "") {
		errorList.push(getLocalMessage("sfac.reamrk.validate"));
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, "Change Block Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function  getOrganizationName(){

	$('#organizationNameId').html('');
	$('#organizationNameId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var orgid = $("#orgTypeId").find("option:selected").val();
	var postdata = 'orgid=' + orgid;

	var json = __doAjaxRequest('ChangeofBlockForm.html?getMasterDetail',
			'POST', postdata, false, 'json');
	$.each(json, function(index, value) {getOrganizationName()
		if (value.id != null) {
			$("#organizationNameId").append(
					$("<option></option>").attr("value", value.id).text(
							value.name)).trigger("chosen:updated");
		}
	});
}


function modifyCase(blockId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"blockId" : blockId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function statusChange(id) {
    var ifChecked = $("#status"+id).prop('checked');
	if (ifChecked) {
		$("#status"+id).val("I");
	} else {
		$("#status"+id).val("A");
	}
}





$(function() {
	/* To add new Row into table */
	$("#blockDetails").on('click', '.addItemCF', function() {
		if ($.fn.DataTable.isDataTable('#blockDetails')) {
			$('#blockDetails').DataTable().destroy();
		}
		var errorList = [];
		errorList = validateFormDetails(errorList);
		if (errorList.length == 0) {
			var content = $("#blockDetails").find('tr:eq(1)').clone();
			$("#blockDetails").append(content);
			content.find("select").val('0');
			content.find("input:hidden").val('');
		//	content.find("input:text").val("");
		    content.find('[id^="reason"]').val("");

			$('.error-div').hide();
			reOrderBlockDetailsSequence(); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});



$(function() {
	$("#blockDetails").on('click', '.delButton', function() {
		var row = $("#blockDetails tbody .appendableClass").length;
		if (row != 1) {
			$(this).parent().parent().remove();
			let removeId = $(this).parent().parent().find(
			'input[type=hidden]:first').attr('value');
			if (removeId != '') {
				removeIds.push(removeId);
				$('#removedIds').val(removeIds);
			}
			reOrderBlockDetailsSequence();
		}else {
			var errorList = [];
			errorList.push(getLocalMessage("sfac.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
		e.preventDefault();
	});
});

function reOrderBlockDetailsSequence() {

	$("#blockDetails tbody tr").each(
			function(i) {
				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
				$(this).find("input:text:eq(2)").attr("id", "reason" + (i));
				$(this).find("select:eq(0)").attr("id", "stateId" + i);
				$(this).find("select:eq(1)").attr("id", "distId" + i);
				$(this).find("select:eq(2)").attr("id", "blckId" + i);

				$(this).find("select:eq(3)").attr("id","allocationCategoryDet" + i);
				$(this).find("select:eq(4)").attr('id', 'allocationSubCategoryDet' + i);
				$(this).find("input:text:eq(1)").attr("id", "cbboId" + i);
				
				$(this).find("input:text:eq(2)").attr("name","newBlockAllocationDto.blockDetailDto[" + i + "].reason")

				$(this).find("select:eq(0)").attr("name","newBlockAllocationDto.blockDetailDto[" + i + "].stateId")
						.attr("onchange", "getDistrictList(" + i + ")");
			
				$(this).find("select:eq(1)").attr("name","newBlockAllocationDto.blockDetailDto[" + i + "].distId")
						.attr("onchange", "getBlockList(" + i + ")");
				
				$(this).find("select:eq(2)").attr("name","newBlockAllocationDto.blockDetailDto[" + i + "].blckId");
			
				$(this).find("select:eq(3)").attr("name","newBlockAllocationDto.blockDetailDto[" + i+ "].allocationCategory").attr("onchange", "getAlcSubCatListDet(" + i + ")");
			
				$(this).find("select:eq(4)")
						.attr('name','newBlockAllocationDto.blockDetailDto[' + i + '].allocationSubCategory');
				
				$(this).find("input:text:eq(1)").attr("name","newBlockAllocationDto.blockDetailDto[" + i + "].cbboId");
			
				$("#sNo" + i).val(i + 1);
			});
}


function getAlcSubCatListDet(id) {
	var cat = $("#allocationCategoryDet" + id).find("option:selected").attr('code');
	if (cat == 'BLW') {
		$('#allocationSubCategoryDet' + id).attr("disabled", true);
		$('.showMand').hide();
	} else {
		$('#allocationSubCategoryDet' + id).removeAttr('disabled');
		$('.showMand').show();
		var requestData = {
			"allocationCategory" : $("#allocationCategoryDet" + id).val()
		};
		var URL = 'AllocationOfBlocks.html?getAlcSubCatList';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		$('#allocationSubCategoryDet' + id).html('');
		$('#allocationSubCategoryDet' + id).append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#allocationSubCategoryDet' + id).append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.lookUpDesc)));
		});
		$('#allocationSubCategoryDet' + id).trigger("chosen:updated");
	}
}

function fileCountUpload(element) {

	var errorList = [];
	errorList = validateJudgementDetails(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var row = $("#blockDetails tbody .appendableDetails").length;
		$("#length").val(row);
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('ChangeofBlockForm.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		var divName = formDivName;
		$(divName).html(response);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function doFileDeletion(obj, id) {

	requestData = {
		"id" : id
	};

	var row = $("#blockDetails tbody .appendableClass").length;
	if (row != 1) {
		var response = __doAjaxRequest('ChangeofBlockForm.html?doEntryDeletion',
				'POST', requestData, false, 'html');
	}
}

function getDistrictData() {
	var errorList = [];
	if (errorList.length == 0){
	var requestData = {
		"sdb1" : $("#sdb1").val()
	};
	var URL = 'ChangeofBlockForm.html?getDistrictData';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb2').html('');
	$('#sdb2').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb2').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb2').trigger("chosen:updated");
}
}

function getBlockData() {
	var sdb2 = $("#sdb2").val();
	
	var requestData = {
		"sdb2" : sdb2,
	};
	var URL = 'ChangeofBlockForm.html?getBlockData';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb3').html('');
	$('#sdb3').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb3').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb3').trigger("chosen:updated");
}