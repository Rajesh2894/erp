 $(document).ready(function(){
	$('.lessthancurrdate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
	 $("#showFlatNo").hide();
	 $("#serchBtnDirect").hide();
	prepareDateTag();
	
	$('.parentPropNos').hide();
});

 function prepareDateTag() {
		var dateFields = $('.lessthancurrdate');
		dateFields.each(function () {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
	}



function SearchButton(obj)
 {
	showloader(true);
	setTimeout(function () {
		var errorList = [];
		var billMethod = $("#billingMethod").val();
		if(billMethod == 'I'){
			var flatNo = $("#flatNo").val();
			if (flatNo == "" || flatNo == undefined || flatNo == null
					|| flatNo == '0') {
				errorList.push(getLocalMessage('prop.select.flatNo'));
			}
		}
		var searchType=$("input[name='propBillPaymentDto.specNotSearchType']:checked").val();
		if (searchType != "GP") {
			errorList = ValidateDetails(errorList);
		}
		if (errorList.length == 0)
		{
			var searchType=$("input[name='propBillPaymentDto.specNotSearchType']:checked").val();
			var theForm	=	'#PropertyBillPaymentSearch';
			var requestData = {};
			requestData = __serializeForm(theForm);
			var ajaxResponse = __doAjaxRequest(
					'PropertyBillPayment.html?getBillPaymentDetail', 'POST',
					requestData, false, 'html');

			$("#dataDiv").html(ajaxResponse);
			if (searchType == "GP") {
				$("input[name='propBillPaymentDto.specNotSearchType']:checked").val("GP");
				showSingleMultiple();
			}
			if(billMethod == 'I'){
				 $("#showFlatNo").show();
		    	  $("#serchBtn").show();
		    	  $("#serchBtnDirect").hide();
			}
			return false;
			
 	 	}		
		else{
 		showErrorOnPage(errorList);
		}
		showloader(false);
	},2000);
 }
 
 function ValidateDetails(errorList){
		
		var propNo=$("#assNo").val();
		var oldPropNo= $("#assOldpropno").val();
		if(propNo== "" && oldPropNo==""){
			errorList.push(getLocalMessage("property.changeInAss"));
		}
		return errorList;

	}
	 function showErrorOnPage(errorList){
			var errMsg = '<ul>';
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$("#errorDiv").html(errMsg);
			$("#errorDiv").show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
			return false;
		}
	 
	 
	 function redirectToSearchPropNo(obj) {
		 showloader(true);
		 setTimeout(function () {
		    var errorList = [];
		    var propNo=$("#assNo").val();
			var oldPropNo= $("#assOldpropno").val();
			var parentPropNo= $("#parentPropNo").val();
			var searchType=$("input[name='propBillPaymentDto.specNotSearchType']:checked").val();
			if (searchType != "GP") {
				if (propNo == "" && oldPropNo == "") {
					errorList.push(getLocalMessage("property.changeInAss"));
				}
				if (errorList.length == 0){
				 var propNo=$("#assNo").val();
				    var requestData = {
					"propNo" : propNo
				    };

				    var ajaxResponse = doAjaxLoading(
					    'PropertyBillPayment.html?getBillingMethod',
					    requestData, 'html');

				    if (ajaxResponse != null && ajaxResponse != "") {
				    	var prePopulate = JSON.parse(ajaxResponse);

				    	$.each(prePopulate, function(index, value) {
				    		$('#flatNo').append(
				    				$("<option></option>").attr("value", value).text(
				    						(value)));
				    	});
				    	$('#flatNo').trigger("chosen:updated");
				    	  $("#showFlatNo").show();
				    	  $("#serchBtn").hide();
				    	  $("#serchBtnDirect").show();
				    	  $("#billingMethod").val('I');
				    }else{
				    	SearchButton(obj);
				    } 
			}else{
				showErrorOnPage(errorList);
			}
			}else{
				if (searchType == "GP" && (parentPropNo == "" || parentPropNo == "0")) {
					errorList.push(getLocalMessage("property.parentPropValidn"));
					showErrorOnPage(errorList);
				}else{
					SearchButton(obj);
				}
			}
			showloader(false);
			},2000);
		}
	 
	 

function showSingleMultiple() {

	if ($("input[name='propBillPaymentDto.specNotSearchType']:checked")
			.val() == "SM") {
		$("#parentPropNo").val("").trigger("chosen:updated");
		//$('#parentPropNo').val('');			
		$('#singleProps').show();
		$('.singleProps').show();
		$('.showFlatNo').hide();
		$('.parentPropNos').hide();		
	} else if ($(
			"input[name='propBillPaymentDto.specNotSearchType']:checked")
			.val() == "GP") {
		$('#assNo').val('');
		$('#assOldpropno').val('');
		$('#showFlatNo').hide();
		$('.singleProps').hide();
		$('.parentPropNos').show();
	}
}
			