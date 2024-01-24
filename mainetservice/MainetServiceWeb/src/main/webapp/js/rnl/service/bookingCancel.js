//Author: ISRAT

$(document).ready(function() {
	$('#reasonId').hide();
	$('#vendor').hide();
	$('#taxDetails').hide();
	$('#submitBT').hide();
	
});

function getChargesForCancelBooking(obj) {
	var errorList = [];
	var bookingId = $("#bookingId").val();
	if(bookingId == ''){
		errorList.push(getLocalMessage("rnl.estate.booking.validation.cancel.bookingNo"));
	}
	if (errorList.length == 0) {
		
		var cancelReason = $("#cancelReason").val();
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = {
				
		};
		//requestData = __serializeForm(theForm);
		var apmId= $("#bookingId").find("option:selected").attr('data-apmId');
		var orgId= $("#bookingId").find("option:selected").attr('data-orgId');
		requestData={
				applicationId:apmId,
				orgId:orgId
		}
		var url = 'EstateBookingCancel.html?getCharges';
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '',obj);

		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		prepareTags();
		
		let errors = $('.error-msg').text();
		if(errors != undefined && errors != ''){
			$('#submitBT').hide();
		}else{
			$('#submitBT').show();
			$('#bookingId').val(bookingId);
			$("#bookingId").prop('disabled', 'disabled');
			$('select').trigger("chosen:updated");
			$("#cancelReason").val(cancelReason);
			$('#reasonId').show();
			$('#vendor').show();
			$('#taxDetails').show();
			$('#searchBT').hide();
		}
		
		
		
		

	} else {
		showErr(errorList);
	}
}

//submit Booking cancel
function saveBookingCancel(object) {
	let errorList = [];
	var bookingId = $('#bookingId').val();
	$('#bookingIdSet').val(bookingId);
	let bookingNo = $( "#bookingId option:selected" ).text();
	let booking = bookingNo.split("-");
	$('#bookingNoSet').val(booking[0]);
	let actionParam = "";
    errorList = validateCancelDetails(errorList);
    if (errorList.length > 0) {
        $("#errorDiv").show();
        showErr(errorList);
    } else {
    	$("#errorDiv").hide();
        var formName = findClosestElementId(object, 'form');
        var theForm = '#' + formName;
        var requestData = __serializeForm(theForm);
        return saveOrUpdateForm(object, '', 'EstateBookingCancel.html','saveform');
    }
}

//validation method for cancelBookingDetails
function validateCancelDetails(errorList) {
	//check reason and vendorId not empty
	if($('#cancelReason').val() ==''){
		errorList.push(getLocalMessage("rnl.estate.booking.validation.cancel.reason"));
	}
	if($('#vendorId').val() == ''){
		errorList.push(getLocalMessage("rnl.estate.booking.validation.cancel.vendorId"));
	}
	$("#taxDetails tbody tr").each(function(i) {
        let position = i + 1;
        let feeDesc = $("#feeDesc" +i).text();
        let feeAmt = Number($("#feeAmt" +i).text());
        let refundAmtDesc = $("#refundAmt" + i).val();
        let refundAmt = Number($("#refundAmt" + i).val());
        let flag=$("#flag").val();
        let dedPacHeadId=Number($("#dedPacHeadId" + i).val());
        
        //D#39609 D#77912
        if (refundAmtDesc == undefined  || refundAmtDesc == "") {
            //errorList.push(getLocalMessage("rnl.estate.booking.validation.cancel.refundAmt") + " at Sr No " + position);
        	errorList.push(getLocalMessage("Please Enter "+feeDesc+" Refund Amount "));
        }
        if(flag == "L"){
            if (dedPacHeadId == undefined  || dedPacHeadId == "") {
              errorList.push(getLocalMessage("Please Enter Account Head at Sr No "+position+" "));
            }
            }
        
        //check refund AMT is not more than amount
        if(feeAmt == 0.00 && refundAmt>feeAmt){
        	//D#75606
        	//errorList.push(getLocalMessage("rnl.estate.booking.validation.cancel.refundAmtGreater") + " at Sr No " + position);
        	errorList.push(getLocalMessage("From Refund Amount is not more than Amount at Sr No "+position+" to "+feeDesc+" Refund Amount is more than "+feeDesc+" Amount"));
        }else if(refundAmt>feeAmt){
        	//errorList.push(getLocalMessage("rnl.estate.booking.validation.cancel.refundAmtGreater") + " at Sr No " + position);
        	errorList.push(getLocalMessage("From Refund Amount is not more than Amount at Sr No "+position+" to "+feeDesc+" Refund Amount is more than "+feeDesc+" Amount"));
        }
        
    });
    return errorList;
}

function showErr(errorList) {
    $(".warning-div").removeClass('hide');
    var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
    $.each(errorList, function(index) {
        errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' +
            errorList[index] + '</li>';
    });
    errMsg += '</ul>';
    $("#errorDiv").html(errMsg);

    $("html, body").animate({
        scrollTop: 0
    }, "slow");
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function checkDuplicateHead(index) {
    $("#errorDiv").hide();
    var errorList = [];
    var serialNumberArray = [];

    $("#taxDetails tbody tr").each(function (i) {
        var dedPacHeadId = $("#dedPacHeadId" + i).val();

       
        if (dedPacHeadId.trim() !== "") {
            if (serialNumberArray.includes(dedPacHeadId)) {
                errorList.push(getLocalMessage("Budget Heads cannot be same,please select another Budget Head"));
                $("#dedPacHeadId" + index).val('').trigger("chosen:updated");
            }

            serialNumberArray.push(dedPacHeadId);
        }
    });

    if (errorList.length > 0) {
        $("#errorDiv").show();
        displayErrorsOnPage(errorList);
    }

    return errorList;
}