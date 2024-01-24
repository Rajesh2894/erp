	$(document).ready(function(e) {


		
		 $('#manualReeiptDate').datepicker({
		    	dateFormat : 'dd/mm/yy',
		    	changeMonth : true,
		    	changeYear : true,    	
		    	yearRange : "-100:-0",
		        });
		 
		
	});

function SearchButton(element)
 {
			/*var theForm	=	'#PreviousYearManualReceiptWithBillChange';
			var requestData = {};
			requestData = __serializeForm(theForm);
			var ajaxResponse = __doAjaxRequest(
					'PreviousYearManualReceiptWithBillChange.html?searchPropertyDetails', 'POST',
					requestData, false, 'html');

			$("#dataDiv").html(ajaxResponse);
			return false;*/
			
			
			
			
			var divName = '.content-page';
			var URL = 'PreviousYearManualReceiptWithBillChange.html?searchPropertyDetails';
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
				'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			prepareTags();
			
		
 }


function updaeReviseBill(element) {
	var errorList = [];
	var manualReeiptDate = $("#manualReeiptDate").val();
	var manualRecptArrearPaidAmnt = $("#manualRecptArrearPaidAmnt").val();
	var manualReceiptNo = $("#assesmentManualNo").val();
	if (manualReeiptDate == "" || manualReeiptDate == '0'
	    || manualReeiptDate == undefined) {
	errorList.push(getLocalMessage('Please select manual receipt date'));
    }
	if (manualRecptArrearPaidAmnt == "" || manualRecptArrearPaidAmnt == '0'
	    || manualRecptArrearPaidAmnt == undefined || manualRecptArrearPaidAmnt == '0.0') {
	errorList.push(getLocalMessage('Please enter  receipt amount'));
    }
	if (manualReceiptNo == "" || manualReceiptNo == '0'|| manualReceiptNo == undefined ) {
	errorList.push(getLocalMessage('Please enter  Manual Receipt No'));
    }
	if (errorList.length == 0) {
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest(
			"PreviousYearManualReceiptWithBillChange.html?updateRevisedBill", 'POST',
			requestData, false, 'json');
		
		if (object.error != null && object.error != 0) {
			errorList.push(getLocalMessage('Manual receipt date cannot be less than previous receipt Date'));
		    displayErrorsOnPage(errorList);
		}else if(object.document != null){
			errorList.push(getLocalMessage('Please upload document'));
			displayErrorsOnPage(errorList);
		}else if(object.payerror!=null){
			for (const error of object.payerror) {
				  errorList.push(getLocalMessage(error.defaultMessage));
			}
			displayErrorsOnPage(errorList);
		}
		else {
		    if (object.successFlag != null) {
			showBoxForApproval(getLocalMessage("Bill updated succesfully"));
		    }
		}
	}else{
		displayErrorsOnPage(errorList);
	}
	    
}

function showBoxForApproval(succesMessage) {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed + '\'  id=\'Proceed\' '
		+ ' onclick="closeApproval();"/>' + '</div>';

    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
    $(childDivName).html(message);
    $(childDivName).show();
    $('#Proceed').focus();
    showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}