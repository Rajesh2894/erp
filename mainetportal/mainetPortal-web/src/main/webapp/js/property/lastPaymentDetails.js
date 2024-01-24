
$(function() {
//	 By default Not Applicable, disable All field 

	
	if($("input[name='provisionalAssesmentMstDto.proAssBillPayment']:checked").val()==null || $("input[name='provisionalAssesmentMstDto.proAssBillPayment']:checked").val()=="N" )
	{	
	$('#notApplicaple').prop('checked', true);
	$('#assLpReceiptNo, #proAssLpReceiptAmt, #proAssLpReceiptDate, #lastpaymentMadeUpto, #proAssLpBillCycle, #billAmount, #outstandingAmount').prop("disabled", true);
}
});

//to unable/disable input fields based on Bill Payment Status(Not Applicable/Manual)
function billPaymentValue(){
	
	if($("input[name='provisionalAssesmentMstDto.proAssBillPayment']:checked").val()=="N")
    {      
    	 $('#assLpReceiptNo, #proAssLpReceiptAmt, #proAssLpReceiptDate, #lastpaymentMadeUpto, #proAssLpBillCycle, #billAmount, #outstandingAmount').prop("disabled", true);
    	 $('#assLpReceiptNo, #proAssLpReceiptAmt, #proAssLpReceiptDate, #lastpaymentMadeUpto, #proAssLpBillCycle, #billAmount, #outstandingAmount').val('');
    }
    else {
   	 $('#assLpReceiptNo, #proAssLpReceiptAmt, #proAssLpReceiptDate, #lastpaymentMadeUpto, #proAssLpBillCycle,#billAmount, #outstandingAmount').removeProp("disabled");
    }
	clearTableData();   // clear unit detail table data
}