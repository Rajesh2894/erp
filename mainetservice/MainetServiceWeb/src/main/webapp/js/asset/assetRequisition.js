$(document).ready(function() {
	$("#requisitionSummaryDT").dataTable(
			{
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
						[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
	
	$("#searchRequisition").click(function() {
				var errorList = [];
				var astCategory = $("#astCategory").val();
				var astLoc = $("#astLoc").val();
				var astDept = $("#astDept").val();

				if (astCategory != 0 || astLoc != ''||  astDept != '') {

					var requestData = '&astCategoryId=' + astCategory + '&locId=' + astLoc + '&deptId=' +astDept;
					var table = $('#requisitionSummaryDT').DataTable();
					table.rows().remove().draw();
					$(".warning-div").hide();
					var ajaxResponse = __doAjaxRequest('AssetRequisition.html?searchReqData','POST', requestData, false,'json');
					if (ajaxResponse.length == 0) {
						errorList.push(getLocalMessage("asset.search.dataNotFound"));
						displayErrorsOnPage(errorList);
						return false;
					}

					var result = [];
					$.each(
									ajaxResponse,
									function(index) {
									    var obj = ajaxResponse[index];
										let astCategoryDesc = obj.astCategoryDesc;
										let astName = obj.astName;
										let astDesc = obj.astDesc;
										let astQty = obj.astQty;
										let astLocDesc = obj.astLocDesc;
										let astDeptDesc = obj.astDeptDesc;
										let status = obj.status;
										result
												.push([
														'<div class="text-center">'
																+ (index + 1)
																+ '</div>',
														'<div class="text-center">'
																+ astCategoryDesc
																+ '</div>',
														'<div class="text-center">'
																+ astName
																+ '</div>',
														'<div class="text-center">'
																+ astDesc
																+ '</div>',
														'<div class="text-center">'
																+ astQty
																+ '</div>',
														'<div class="text-center">'
																+ astLocDesc
																+ '</div>',
														'<div class="text-center">'
																+ astDeptDesc
																+ '</div>',
														'<div class="text-center">'
																+ status
																+ '</div>']);
									});
					table.rows.add(result);
					table.draw();
				} else {
					errorList
							.push(getLocalMessage("land.acq.val.selectAtleastOneField"));
					displayErrorsOnPage(errorList);
				}

			});
	
	/*Reset Form*/
	$("#resetForm").click(function(){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('AssetRequisition.html?addRequisition', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	});

});

function openRequisitionForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveRequisition(element) {
	var errorList = [];
	$("#errorDiv").html('');
	let astCategory = $('#astCategory').val();
	let astName = $("#astName").val();
	let astDesc = $("#AssetDescription").val();
	let astQty = $("#astQty").val();
	let astLoc = $("#astLoc").val();
	let astDept = $("#astDept").val();

	if (astCategory == "0" || astCategory == undefined || astCategory == '') 
		errorList.push(getLocalMessage('asset.requisition.vldnn.astCategory'));
	if (astName == undefined || astName == '')
		errorList.push(getLocalMessage('asset.requisition.vldnn.name'));
	if (astDesc == undefined || astDesc == '')
		errorList.push(getLocalMessage('asset.requisition.vldnn.description'));
	if (astQty == undefined || astQty == '')
		errorList.push(getLocalMessage('asset.requisition.vldnn.quantity'));
	if (astLoc == "0" || astLoc == undefined || astLoc == '')
		errorList.push(getLocalMessage('asset.requisition.vldnn.location'));
	if (astDept == "0" || astDept == undefined || astDept == '')
		errorList.push(getLocalMessage('asset.requisition.vldnn.department'));

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element, getLocalMessage('asset.requisition.savesuccessmsg'), ''+$("#moduleDeptUrl").val()+'', 'saveform');
	}
}

function saveDecisionAction(approvalData){
	var errorList = [];
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	var count = $('#assetCodeDetail tr').length - 1;
	var dispatchQuantity = Math.trunc($('#dispatchQuantity').val());
	var rejectedQuantity = $("#rejectedQuantity").val();
	var astQty=  $('#astQty').val();
	
	
	if (rejectedQuantity == undefined || rejectedQuantity == '')
		errorList.push(getLocalMessage('asset.requisition.vldnn.rejectedQuantity'));
	
	var rem=astQty-dispatchQuantity;
	if(rem < rejectedQuantity){
		errorList.push(getLocalMessage('asset.requisition.vldnn.rejectedQuantity.astqty'));
		
	}
	
	if (dispatchQuantity == undefined || dispatchQuantity == '')
		errorList.push(getLocalMessage('asset.validation.dispatch.quantity'));
	
	
	if(decision == undefined || decision == '') {
		errorList.push(getLocalMessage('asset.info.approval'));
	} else if(comments == undefined || comments =='') {
		errorList.push(getLocalMessage('asset.info.comment'));
	}
	var approvalLastFlag = $("#approvalLastFlag").val();
	if(approvalLastFlag=='Y')
		{
		var empId = $("#empIds").val();
		var dispatchedDate = $("#dispatchedDate").val();
		if(empId == '') {
			errorList.push(getLocalMessage('Select Employee'));
		} 
		if(dispatchedDate == '') {
			errorList.push(getLocalMessage('Dispatched Date'));
		} 
		errorList = validateAssetAndSerial(errorList);
		if(count != dispatchQuantity) {
			errorList.push(getLocalMessage('assest.validation.asset.quantity'));
		}
		
		}
		if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData, '', 'AdminHome.html', 'saveDecision');
	}

}
function validateAssetAndSerial(errorList) {
	var count1 = $('#assetCodeDetail tr').length - 1;
	var dispatchQuantity = $('#dispatchQuantity').val();
	if (count1 >dispatchQuantity){
		errorList.push(getLocalMessage("assest.validation.asset.quantity"));
	}
	$(".assetCodeDetail").each(function(i) {
        var astCode = $('#astCode' + i).val();
        var serialNo = $('#serialNo' + i).val();
        var row = i + 1;
        if (astCode == '') {
    	errorList.push(getLocalMessage("asset.transfer.valid.assetCode")+row);
        }
        if (serialNo == '') {
    	errorList
    		.push(getLocalMessage("asset.transfer.valid.assetSerial")+ row );
        }
    });
	return errorList;

}

function checkForDuplicateSerialNo(event, currentRow) {
	$(".error-div").hide();
	var errorList = [];
	$('.assetCodeDetail').each(function(i) {
		 var astCode = $('#astCode' + i).val();
	     var serialNo = $('#serialNo' + i).val();
		var c = i + 1;

		if (errorList.length == 0) {
			if (currentRow != i&& ($("#astCode" + currentRow).val() == astCode && event.value == serialNo)) {
				errorList.push(getLocalMessage('asset.duplicateSerialNo'));
				$("#serialNo" + currentRow).val("");
				$("#serialNo" + currentRow).trigger('chosen:updated');
				$("#errorDiv").show();
				 $("#errorDiv").removeClass('hide');
				displayErrorsOnPage(errorList);
				return false;
			}
		} else {
			$("#serialNo" + i).val('');
			displayErrorsOnPage(errorList);
			return false;
		}
	});
}

function addAssteCode() {
	var errorList = [];
	errorList = validateAssetAndSerial(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		$("#errorDiv").removeClass('hide');
		displayErrorsOnPage(errorList);
	} else {
		var count = $('#assetCodeDetail tr').length - 1;
		var dispatchQuantity = Math.trunc($('#dispatchQuantity').val());
		
		if (count < dispatchQuantity ){
			//$("#errorDiv").addClass('hide');
			var clickedRow = $(this).parent().parent().index();
			var content = $('#assetCodeDetail tr').last().clone();
			$('#assetCodeDetail tr').last().after(content);
			content.find("input:text").attr("value", "");
			content.find("select").attr("selected", "selected").val('');
			content.find('div.chosen-container').remove();
			content.find("select:eq(0)").chosen().trigger("chosen:updated");
			content.find("select:eq(1)").chosen().trigger("chosen:updated");
			content.find("input:text").val('');
			content.find("input:hidden").val('');
			reOrderAsset();
		} else {
			errorList.push(getLocalMessage("assest.validation.asset.quantity"));
			displayErrorsOnPage(errorList);
		}
	}
}


function quantityCheck() {

	var errorList = [];
	var data = {
		"astCategory" : $('#astCategory').val(),
		"dispatchQuantity" : $('#dispatchQuantity').val(),

	};
	var divName = '.content-page';
	var ajaxResponse = __doAjaxRequest("ITAssetRequisition.html?quantityCheck",
			'POST', data, false, 'json');
	var dispatchQuantity=  parseInt($('#dispatchQuantity').val());
	var astQty= parseInt($('#astQty').val());
	
	if (ajaxResponse < dispatchQuantity) {
		$('#dispatchQuantity').val("");
		errorList.push(getLocalMessage("asset.requisition.vldnn.dispatch.more.avail")+" "+ ajaxResponse);
		displayErrorsOnPage(errorList);
	}
	if (astQty < dispatchQuantity) {
		$('#dispatchQuantity').val("");
		errorList.push(getLocalMessage("asset.validation.quantity"));
		displayErrorsOnPage(errorList);
	}

}






function reOrderAsset() {
	$('.assetCodeDetail').each(
			function(i) {

				$(this).find("select:eq(0)").attr("id", "astCode" + (i)).attr(
						"onchange", "getSerialNo(this," + (i) + ")");
				$(this).find("select:eq(1)").attr("id", "serialNo" + (i)).attr(
						"onchange", "checkForDuplicateSerialNo(this," + (i) + ")");

				$(this).find("select:eq(0)").attr("name",
						"astRequisitionDTO.dto[" + i + "].astCode");
				$(this).find("select:eq(1)").attr("name",
						"astRequisitionDTO.dto[" + i + "].serialNo");

			});
}

function getSerialNo(element, id) {
    var astCode = $(element).val();
    
    var serialNoArray = [];
    $('.assetCodeDetail').each(function(i) {
        var serialNo = $('#serialNo' + i).val();
        if (astCode == $('#astCode' + i).val()) {
            serialNoArray.push(serialNo);
        }
    });

    var postData = 'astCode=' + astCode;
    var ajaxResponse = __doAjaxRequest('ITAssetRequisition.html?getSerialNo', 'POST', postData, false, 'json');
    
    if (jQuery.isEmptyObject(ajaxResponse)) {
        var selectOption = getLocalMessage('account.common.select');
       /* $("#serialNo" + id).empty().append(
            '<option selected="selected" value="0">' + selectOption + '</option>'
        );*/
        $("#serialNo" + id).trigger('chosen:updated');
        errorList.push('No serial No available against selected asset code');
        displayErrorsOnPage(errorList);
    } else {
        var selectOption = getLocalMessage('account.common.select');
        var $serialNoSelect = $("#serialNo" + id);
        
        // Clear and populate the dropdown with the available serial numbers that are not in use
        $serialNoSelect.empty().append(
            '<option selected="selected" value="0">' + selectOption + '</option>'
        );

        $.each(ajaxResponse, function(index, value) {
            if (!serialNoArray.includes(value)) {
                $serialNoSelect.append(
                    $("<option></option>").attr('value', value).text(value)
                );
            }
        });
        
        $serialNoSelect.trigger('chosen:updated');
    }
}

$("#assetCodeDetail").on("click",'.delButton',function(e) {

	var errorList = [];
	var countRows = -1;
	$('.assetCodeDetail').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;

	if (row == 0) {
		$("#astCode").val("").trigger('chosen:updated');	
		$("#serialNo").val("").trigger('chosen:updated');
		var deletedYearId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (deletedYearId != '') {
			//removeYearIdArray.push(deletedYearId);
		}
	}
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
		var deletedYearId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (deletedYearId != '') {
			//removeYearIdArray.push(deletedYearId);
		}
	}
	
	//$('#removeYearIds').val(removeYearIdArray);
	reOrderAsset();
	//getTotalAmount();
	e.preventDefault();
});