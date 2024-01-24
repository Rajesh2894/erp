/**
 * Lalit Mohan Prusti
 */
$(function(){
     showHideBPL( $('#povertyLineId').val());
	 $("#povertyLineId").change(function () {
	        var isBelowPoverty = $('#povertyLineId').val();
	        showHideBPL(isBelowPoverty);
	    });
	//Defect #153420
	$('#resetDiscDetails').hide();
	$('#csIdn').on('change', function(){
		$('#resetDiscDetails').show();
	});
	$('#resetDiscDetails').on('click', function(){
		$('#csIdn, #consumerName, #consumerAreaName, #discReason, #fromdate, #todate').val('');
		$('select#discType').val('0');
		$('select#plumber').val('').trigger('chosen:updated');
		$(this).hide();
	});
	resetFormFields();
});
//Defect #153420
function resetFormFields() {
	$('#resetBtn').on('click', function() {
		$('input[type=text], textarea, select').each(function() {
			var errorField = $(this).next().hasClass('error');
			if('input[type=text], textarea') {
				$(this).val('');
			}
			if('select') {
				if($(this).val() != '') {
					$(this).val('0');
				} else {
					$(this).val('');
				}
			}
			$(this).parent().removeClass('has-error');
			if (errorField == true) {
				$(this).next().remove();
			}
        });
		$('.chosen-select-no-results').val('').trigger('chosen:updated');
		var fileUploadList = $('.fileUpload').next();
		if(fileUploadList.children().length > 0) {
			var removeImgId = fileUploadList.find('ul > li > img[alt="Remove"]').attr('id');
			var obj = $('img#' + removeImgId);
			doFileDelete(obj);
		}
		$('#resetDiscDetails').hide();
	});
}

function showHideBPL(value){
	 if (value == 'Y') {
     	$('#bpldiv').show();
     } else {
     	$('#bpldiv').hide();
     }
}
function resetDisconnection() {
$("#csIdn").val("").attr("readonly", false);
$("#consumerName").val("");
$("#consumerAreaName").val("");
}

function resetForm(){
    $('#dwzid3').prop('selected', function() {
        return this.defaultSelected;
    });
    $("#discReason").val("");
	$("#areaName").val("");
	$("#villTownCity").val("");
	$("#aadharNo").val("");
     $('#dwzid2').prop('selected', function() {
        return this.defaultSelected;
    });
     $('#dwzid3').prop('selected', function() {
        return this.defaultSelected;
    });
     $('#discType').prop('selected', function() {
        return this.defaultSelected;
    });
     $('#plumber').prop('selected', function() {
        return this.defaultSelected;
    });
    
}

function openViewModeForm(obj){
	
	var rowId = obj.split("E")[1];
	var row = jQuery('#gridWaterDisconnection').jqGrid ('getRowData', rowId);
	var colData ;
	
	colData = row['csCcn'];
	$("#csIdn").val(colData);
	colData = row['consumerName'];
	$("#consumerName").val(colData);
	colData = row['csAdd'];	
	$("#consumerAreaName").val(colData);
	
	$.fancybox.close();
}

function saveFormDisconnection(element)
{
	
	 if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')
	  	{
		  return saveOrUpdateForm(element,"Your application for  Water Disconnection saved successfully!", 'WaterDisconnectionForm.html?PrintReport', 'saveform');
       }
          else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y')
	      {
	
         	 return saveOrUpdateForm(element,"Your application for  Water Disconnection saved successfully!", 'WaterDisconnectionForm.html?redirectToPay', 'saveform');
	     } 
          else
         	 {
         	  return saveOrUpdateForm(element,"Your application for Water Disconnection saved successfully!", 'CitizenHome.html', 'saveform');
         	 }
	
}

function tempConnection(val){
	if("T" === val){
    	
    	$("#tempDisconnection").show();
    	
    }else{
    	
    	$("#tempDisconnection").hide();
    }
}
function notUlbPlumber() {
	if($("#No").is(':checked'))
		$('#plumId').attr('disabled',false);
	else
		$('#plumId').attr('disabled',true);
}


$(function() {
	notUlbPlumber();
	tempConnection($(' #discType option:selected').attr('code'));
	$("#discType").change(function(){
		
		tempConnection($('option:selected', this).attr('code'));
	});
	
	$(".disDate").datepicker({		     	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate:0,
        onSelect: function(selected) {
          $(".distDate").datepicker("option","minDate", selected)	
        }	
    });
	$(".distDate").datepicker({		     	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate:0,	
    });

	/*$("#datepicker2").datepicker({			      	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate:0,
        onSelect: function(selected) {	
           $("#datepicker1").datepicker("option","maxDate", selected)	
        }	
    }); */
	       
});

function search(obj){
	
	 var formName =	"#"+findClosestElementId(obj, 'form');
	 var requestData = __serializeForm(formName);
	 console.log(requestData);
		var url	=	"WaterDisconnectionForm.html?searchConnectionDetails";
		var returnData = __doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
		$(formDivName).html(returnData);
		//Defect #153420
		var connectionNo = $('#csIdn').val();
		if(connectionNo != '') {
			$('#resetDiscDetails').show();
		}
		if($('#validationerrordiv').length === 0) {
			$("#confirmToProceedId").prop("disabled",false);
			$("#resetBtn").prop("disabled",false);
		}else{
			$("#confirmToProceedId").prop("disabled",true);
			$("#resetBtn").prop("disabled",true);
		}
}

function getChecklistAndCharges(obj) {
	
	var errorList = [];
	var connectionNo = $('#csIdn').val();
	/*errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);*/
	var isBPL = $('#povertyLineId').val();
	
	$('.appendableClass').each(function(i) {
		row=i+1;
		if (isAdditionalOwnerDetailRequired(i) == 'Y') {
			errorList = validateAdditionalOwnerTableData(errorList,i);
		}
	});
	
	
	if (errorList.length == 0) {
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {
				"isBPL" : isBPL
		};
		
			requestData = __serializeForm(theForm);

		var url	=	'WaterDisconnectionForm.html?getCheckListAndCharges';
		var returnData=__doAjaxRequest(url, 'post',requestData, false,'',obj);
		
		$(formDivName).html(returnData);
		$('#povertyLineId').val(isBPL);
		$('#searchConnection').attr('disabled',true);
		$('#csIdn').val(connectionNo);
		/*$('#confirmToProceedId').attr('disabled',true);*/
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true
});

$( document ).ready(function() {

	$('#frmwaterDisconnectionForm').validate({
		   onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	       onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}});
	prepareDateTag();
	
	$(".disDate").datepicker({		     	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate:0,
        /*onSelect: function(selected) {
          $("#datepicker2").datepicker("option","minDate", selected)	
        }*/	
    });
    
     $("#resetform").click(function() {
		this.form.reset();
		resetWaterForm();
		resetOtherFields();
	});
});

function prepareDateTag() {
	var dateFields = $('.disDate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}
