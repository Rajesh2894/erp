$(document).ready(
		function() {
			
			
			
			$(".datepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				yearRange : "1900:2200",
				maxDate: 0
				
			});
			
			$('.chosen-select-no-results').chosen();
			
			$('.alphaNumeric').keyup(function() {
				
				var regx = /^[A-Za-z0-9.\s]*$/;
				
				var amount	=	$(this).val();
			
				if(!regx.test(amount))
				{
					amount = amount.substring(0, amount.length-1);
					
					$(this).val(amount);	
				} 
			});

		});


function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FPOManagementCost.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];
	
	var fpoId = $("#fpoId").val();
	var cbboId = $("#cbboId").val();
	var iaId = $("#iaId").val();
	var fy = $("#financialYear").val();
	var divName = '.content-page';
	if ((fpoId == "0" || fpoId == "" || fpoId == undefined) || (cbboId == "0" || cbboId == undefined || cbboId == "") ) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
				"fpoId" : fpoId,
				"cbboId" : cbboId,
				"iaId" :iaId,
				"fyId":fy
		};
		var ajaxResponse = doAjaxLoading('FPOManagementCost.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}



function addCostButton(obj) {
	var errorList = [];
    errorList = validateCostTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#mcDetailsTable tr').last().clone();
		$('#mcDetailsTable tr').last().after(content);
		 content.find("select").val('0');
	    content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		 content.find('[id^="particulars"]').chosen().trigger("chosen:updated");
		
      
       // content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeCostTable();
		
		
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCostDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#mcDetailsTable tr').length;
	if ($("#mcDetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCostTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCostTable() {
	$("#mcDetailsTable tbody tr").each(function(i) {
		// Id
		
	
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('select:eq(0)').attr('id','particulars' + i);
		$(this).find('select:eq(1)').attr('id','managementCostExpected' + i);
		
		$(this).find('input:text:eq(3)').attr('id','managementCostIncurred' + i);
	
		
		
		$(this).find("input:text:eq(0)").attr("name", "dto.fpoManagementCostDetailDTOs[" + i + "].sNo");
		$(this).find("select:eq(0)").attr("name", "dto.fpoManagementCostDetailDTOs[" + i + "].particulars").attr("onchange","getLimit(" + i + ")");
		$(this).find("select:eq(1)").attr("name", "dto.fpoManagementCostDetailDTOs[" + i + "].particulars");
	
		$(this).find("input:text:eq(3)").attr("name", "dto.fpoManagementCostDetailDTOs[" + i + "].managementCostIncurred");
		
		$("#sNo" + i).val(i + 1);
		
		$('#mcDetailsTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
		
	
	});
}

function validateCostTable() {
	var errorList = [];
	var rowCount = $('#mcDetailsTable tr').length;	


	if (errorList == 0)
		$("#mcDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var particulars = $("#particulars" + i).val();
				var managementCostExpected = $("#managementCostExpected" + i).val();
				var managementCostIncurred = $("#managementCostIncurred" + i).val();
				var constant = 1;
       }
        else{
        	var particulars = $("#particulars" + i).val();
			var managementCostExpected = $("#managementCostExpected" + i).val();
			var managementCostIncurred = $("#managementCostIncurred" + i).val();
	        var constant = i+1;
     }
		
				
				if (particulars == undefined || particulars == "" || particulars == "0") {
					errorList.push(getLocalMessage("sfac.fpo.mgmt.validation.particulars") + " " + (i + 1));
				}
				
				
				if (managementCostIncurred == undefined || managementCostIncurred == "") {
					errorList.push(getLocalMessage("sfac.fpo.mgmt.validation.managementCostIncurred") + " " + (i + 1));
				}
				
				
				
				
		});
	
	return errorList;
}

function fileCountUpload(element) {
	var errorList = [];
	errorList = validateDocDetailsTable();
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var row = $("#docDetailsTable tbody .appendableDocDetails").length;
	
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('FPOManagementCost.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		$("#docDetails").find('.collapse').collapse('hide');
	
		$('.content-page').html(response);
	prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}

}

function validateDocDetailsTable(){
	

	var errorList = [];
	var rowCount = $('#docDetailsTable tr').length;	


	if (errorList == 0)
		$("#docDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var documentDescription = $("#documentDescription" + i).val();
			
				var constant = 1;
       }
        else{
        	var documentDescription = $("#documentDescription" + i).val();
        	var constant = i+1;
     }
		
				
				if (documentDescription == undefined || documentDescription == "" || documentDescription =="0") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.documentDescription") + " " + (i + 1));
				}
				
				/*if( document.getElementById("dto.auditedBalanceSheetInfoDetailEntities" + i + ".attachments" + i + ".uploadedDocumentPath").files.length == 0 ){
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.docUpload") + " " + (i + 1));
				}*/
				
			
		});
	
	return errorList;

}

function deleteDocDetails(obj, id) {

	requestData = {
		"id" : id
	};

	var row = $("#docDetailsTable tbody .appendableDocDetails").length;
	if (row != 1) {
		var response = __doAjaxRequest('FPOManagementCost.html?doEntryDeletion',
				'POST', requestData, false, 'html');
		$(".content-page").html(response);
		prepareTags();
	}
}

function saveFPOMgmtCostForm(obj) {
	
	var errorList = [];
	
	errorList = validateFPOMgmtForm(errorList);
  
	if (errorList.length == 0) {
		
		return saveOrUpdateForm(obj, "FPO Management Cost Details Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateFPOMgmtForm(errorList) {
	


	 var fpoId =$("#fpoId").val();
	    var cbboId =$("#cbboId").val();
	    var iaId =$("#iaId").val();
	    var financialYear =$("#financialYear").val();
	    var rowCount = $('#mcDetailsTable tr').length;	
	    
		if (fpoId == undefined || fpoId == "" || fpoId == "0") {
			errorList.push(getLocalMessage("sfac.fpo.mgmt.validation.fpo") );
		}
		if (cbboId == undefined || cbboId == "" || cbboId == "0") {
			errorList.push(getLocalMessage("sfac.validation.cbboId"));
		}
		/*if (iaId == undefined || iaId == "" || iaId == "0") {
			errorList.push(getLocalMessage("sfac.validation.ianame"));
		}*/
		if (financialYear == undefined || financialYear == "" || financialYear == "0") {
			errorList.push(getLocalMessage("sfac.fpo.pm.validation.finYear") );
		}
		
	    
				
				if (rowCount == undefined || rowCount == "" || rowCount == "0") {
					errorList.push(getLocalMessage("sfac.fpo.mgmt.validation.cost") );
				}
				
				
				
				
				
				
		
	
	return errorList;
}

function modifyCase(fmcId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"fmcId" : fmcId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveFPOManagementCostApprovalData(obj){
	return saveOrUpdateForm(obj, "FPO Managemet Cost Saved Successfully!", 'AdminHome.html', 'saveform');
}

function getLimit(id){
	
	var mcp = $('#particulars'+id).val();
	
	$("#managementCostExpected" + id).val(mcp);
}

function ResetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FPOManagementCost.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}


