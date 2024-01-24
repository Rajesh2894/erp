$(document).ready(function() {
	/*$('#ParawiseRemarkForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});*/

	$("#id_parawiseRemarkTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$( ".uadParComment" ).each(function() {
		  if($( this ).val()==""){
			  $( this ).prop("disabled", false);
		  }else{
			  $( this ).prop("disabled", true);
		  }
		});
		 
	});


function Proceed(element) {
	var errorList = [];
	errorList = validatePara(errorList);
	errorList = validate(errorList);	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('lgl.saveCaseEntry'), 'ParawiseRemark.html',
		'saveform');
	}
}

function validateSearch(){
	var errorList = [];
	var cseSuitNo = $("#cseSuitNo").val();
	var cseDeptid = $("#cseDeptid").val();
	var cseDate = $("#cseDate").val();
	var	cseCatId1 = $("#cseCatId1").val();
	var cseCatId2 =  $("#cseCatId2").val();
	var cseTypId = $("#cseTypId").val();
	var crtId = $("#crtId").val();
	
	if ((cseSuitNo == "" || cseSuitNo == null || cseSuitNo == 'undefined') && (cseDeptid == "" || cseDeptid == null || cseDeptid == 'undefined')
			&& (cseCatId1 == "0" || cseCatId1 == null || cseCatId1 == 'undefined') && (cseCatId2 == "0" || cseCatId2 == null || cseCatId2 == 'undefined')
			&& (cseTypId == "0" || cseTypId == null || cseTypId == 'undefined') && (crtId == "" || crtId == null || crtId == 'undefined')){
			errorList.push('Please provide at least one search criteria.');
			}
	
	return errorList;
}

function searchParawiseRemark(formUrl, actionParam) {
	var errorList = [];
	errorList = validateSearch();
		if (errorList.length > 0){
			//$('#parawiseRemarkDetails').DataTable();
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		}
		else {
		var data = {
				"cseSuitNo" : $("#cseSuitNo").val(),
				"cseDeptid" : $("#cseDeptid").val(),
				"cseDate" : $("#cseDate").val(),
				"cseCatId1" : replaceZero($("#cseCatId1").val()),
				"cseCatId2" : replaceZero($("#cseCatId2").val()),
				"cseTypId" : replaceZero($("#cseTypId").val()),
				"crtId" : replaceZero($("#crtId").val()),

			};
		
		
		var divName = '.content-page';
		var formUrl = "ParawiseRemark.html?searchParawiseRemark";
		var ajaxResponse = doAjaxLoading(formUrl, data, 'html',divName);
		$('.content').removeClass('ajaxloader');		
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

/*function displayErrorOnPage(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$(".warning-div").show();
	errorList = [];
	return false;
}*/
function replaceZero(value) {
	return value != 0 ? value : undefined;
}
function modifyRemark(cseId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode" : mode,
			"id" : cseId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function validate() {
	var errorList = [];

	$(".appendableClass").each(function(i) {
		
		var parPagno=$("#parPagno"+i).val();
		var parSectionno=$("#parSectionno"+i).val();
			
		var rowCount = i + 1;
		
		if(parPagno == "" || parPagno == null || parPagno == 'undefined')
			errorList.push(getLocalMessage("lgl.validation.parUadpagno")+ rowCount);
		
		if(parSectionno == "" || parSectionno == null || parSectionno == 'undefined')
			errorList.push(getLocalMessage("lgl.validation.parUadSectionno")+ rowCount);
		
	
	});
	return errorList;
}

$('body').on('focus', ".hasNumber", function() {
	$('.hasNumber').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function addData() { 
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validate(errorList);
	if (errorList.length == 0) {
		if (errorList.length == 0){
			addTableRow('parawiseRemarkDetails');
			reOrderItemDetailsSequence('.appendableClass');
		}
	} 
	if (errorList.length > 0) {
		//$('#parawiseRemarkDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}
$(function() { 
	
	/* To delete new Row into table */
	var removeIdArray = [];
	$("#parawiseRemarkDetails").on('click', '.delButton', function() {	
		var rowCount = $('#parawiseRemarkDetails tr').length;
		
		if (rowCount <= 2) {
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		}
		$(this).closest('#parawiseRemarkDetails tr').remove();
		reOrderItemDetailsSequence('.appendableClass');
		  // Find the closest tr (table row) to the clicked button
	    var $row = $(this).closest('tr');

	    // Get the value of the hidden input with ID 'parId' within the row
	    var entryId = $row.find('input[id^="parId"]').val();
		if (entryId != '') {
			removeIdArray.push(entryId);
		}
		$('#removeParawiseIds').val(removeIdArray);
		
/*		if ($("#parawiseRemarkDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderItemDetailsSequence(); // reorder id and Path
			
		
			
			// Collect the entry IDs in a list
            var removeParawiseIds = $("#removeParawiseIds").val();
            if (removeParawiseIds === "") {
                removeParawiseIds = entryId;
            } else {
                removeParawiseIds += "," + entryId;
            }
            $("#removeParawiseIds").val(removeParawiseIds);
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}*/
	});
});
// #136459
function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('parawiseRemarkDetails', obj, ids);
	$('#parawiseRemarkDetails').DataTable().destroy();
	triggerTable(); 
	
}

function addDataPara() {
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validatePara(errorList);
	if (errorList.length == 0) {
		
		
		if (errorList.length == 0){
			addTableRow('parawiseRemarkDetailsuad');
			
		}
	} 	
	if (errorList.length > 0) {
		$('#parawiseRemarkDetailsuad').DataTable();
		displayErrorsOnPage(errorList);
	}
	
}

function validatePara(){
	var errorList = [];

	$("#parawiseRemarkDetailsuad tbody .appendableClassPara").each(function(i) {
			
		var parPagno=$("#parUadPagno"+i).val();
		var parSectionno=$("#parUadSectionno"+i).val();
		
		var rowCount = i + 1;
		
		if(parPagno == "" || parPagno == null || parPagno == 'undefined')
			errorList.push(getLocalMessage("lgl.validation.parPagno")+ rowCount);
		
		if(parSectionno == "" || parSectionno == null || parSectionno == 'undefined')
			errorList.push(getLocalMessage("lgl.validation.parSectionno")+ rowCount);
		
	
	});
	return errorList;
	
}

/*function reOrderItemDetailsSequence() {
    $('#parawiseRemarkDetails tbody tr').each(function (i) {
        $(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
        $("#sequence" + i).val(i + 1);

        $(this).find("input:hidden:eq(0)").attr("id", "parId" + i);

        $(this).find("input:text:eq(1)").attr("id", "parPagno" + i);
        $(this).find("input:text:eq(2)").attr("id", "parSectionno" + i);
        $(this).find("input:text:eq(3)").attr("id", "refCaseNo" + i);
        $(this).find("input:text:eq(4)").attr("id", "parComment" + i);

        $(this).find("input:button:eq(0)").attr("id", "delButton" + i);

        $(this).parents('tr').find('.delButton').attr("id", "delButton" + i);

        $(this).find("input:hidden:eq(0)").attr("name", "parawiseRemarkDTOList[" + i + "].parId");
        $(this).find("input:text:eq(1)").attr("name", "parawiseRemarkDTOList[" + i + "].parPagno");
        $(this).find("input:text:eq(2)").attr("name", "parawiseRemarkDTOList[" + i + "].parSectionno");
        $(this).find("input:text:eq(3)").attr("name", "parawiseRemarkDTOList[" + i + "].refCaseNo");
        $(this).find("input:text:eq(4)").attr("name", "parawiseRemarkDTOList[" + i + "].parComment");
    });
}
*/

function reOrderItemDetailsSequence(appendableClass) {
	$(appendableClass).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
		 $(this).find("input:text:eq(1)").attr("id", "parPagno" + i);
	     $(this).find("input:text:eq(2)").attr("id", "parSectionno" + i);
	     $(this).find("input:text:eq(3)").attr("id", "refCaseNo" + i);
	     $(this).find("input:text:eq(4)").attr("id", "parComment" + i);;
	
					
		$(this).find("input:text:eq(0)").val(i + 1);
		
		
		
		$(this).find("input:text:eq(1)").attr("name","parawiseRemarkDTOList[" + i + "].parPagno");
		$(this).find("input:text:eq(2)").attr("name","parawiseRemarkDTOList[" + i + "].parSectionno");
		$(this).find("input:text:eq(3)").attr("name","parawiseRemarkDTOList[" + i + "].refCaseNo");
		$(this).find("input:text:eq(4)").attr("name","parawiseRemarkDTOList[" + i + "].parComment");
	

	});
}
