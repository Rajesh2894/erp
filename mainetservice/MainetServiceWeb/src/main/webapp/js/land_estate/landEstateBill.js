$(document)
		.ready(
				function() {
					
					$('#fromDate').datepicker({
				        dateFormat : 'dd/mm/yy',
				    		changeMonth : true,
				    		changeYear : true,
				    		maxDate:'0d',
				    		//yearRange : "1900:2200",
				    		onClose: function( selectedDate ) {
				        $( "#toDate" ).datepicker( "option", "minDate", selectedDate );
				      }
				    });
				    
				    $( "#toDate" ).datepicker({
				    		dateFormat : 'dd/mm/yy',
				    		changeMonth : true,
				    		changeYear : true,
				    		maxDate:'0d',
				    		//yearRange : "1900:2200",
				    		onClose: function( selectedDate ) {
				        $( "#fromDate" ).datepicker( "option", "maxDate", selectedDate );
				      }
				    });
				    
				   /* $('.datepicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						yearRange : "-100:-0"
					    });
*/
					

				    $('#receiptDate').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate:'0d',
						//yearRange : "-100:+20",  
					});
					
					$('#instrumentalNo1').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
				
						maxDate:'0d',
						yearRange : "-100:+20",  
					});
					
					$('#receiptDate2').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate:'0d',
						//yearRange : "-100:+20",  
					});
					/*var revenueFromdate=$('#revenueFromdate').val();
					$('#revenueFromdate').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
				
						maxDate:'0d',
						yearRange : "-100:+20",  
					});
					$('#revenueTodate').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,	
						  minDate:revenueFromdate,
						maxDate:'0d',
						yearRange : "-100:+20",  
					});*/
					
					$("#searchNoticeGeneration").click(
							function() {
								var errorList = [];
								var proposalNo = $("#proposalNo").val();
								var payTo = $("#payTo").val();
								var acqStatus = $("#acqStatusId").val();
								var locId = $("#locId").val();

								if (proposalNo == undefined)
									proposalNo = "";

								if (payTo == undefined)
									payTo = "";

								if (acqStatus == undefined)
									acqStatus = "";

								if (locId == undefined)
									locId = "";

								if (proposalNo != 0 || payTo != ''|| acqStatus != ''|| locId != 0) {

								
								} else {
									errorList
											.push(getLocalMessage("land.acq.val.selectAtleastOneField"));
									displayErrorsOnPage(errorList);
								}

							});
					
				});


function landRentNoticeGeneration(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}

function billPayment(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}

function searchReport(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}

function landRevenueReport(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}

function landOutstandingRegister(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}

function landDefaulterRegister(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}


function landBillPaymentValidation(errorList) {
	
	var acqDate = $("#acqDate").val();
	var category = $("#category").val();
	var contractName = $("#contractName").val();
	var payName = $("#lnDesc").val();
	var receiptHead=$("#receiptHead").val();
	var amt=$("#amt").val();
	var mode=$("#mode").val();
	var bankName=$("#bankName").val();
	var instrumentalNo=$("#instrumentalNo").val();
	var instrumentalDate=$("#instrumentalDate").val();

	if (acqDate == "0" || acqDate == undefined || acqDate == '') {
		errorList.push(getLocalMessage('land.acq.val.receiptDate'));
	}
	if (category == "0" || category == undefined || category == '') {
		errorList.push(getLocalMessage('land.acq.val.receiptCategory'));
	}

	if (contractName == "0" || contractName == undefined || contractName == '') {
		errorList.push(getLocalMessage('land.acq.val.contractNumber'));
	}
	if (payName == "0" || payName == undefined || payName == '') {
		errorList.push(getLocalMessage('land.acq.val.payerName'));
	}
	if (receiptHead == "0" || receiptHead == undefined || receiptHead == '') {
		errorList.push(getLocalMessage('land.acq.val.receiptHead'));
	}
	if (amt == "0" || amt == undefined || amt == '') {
		errorList.push(getLocalMessage('land.acq.val.receiptAmt'));
	}
	if (mode == "0" || mode == undefined || mode == '') {
		errorList.push(getLocalMessage('land.acq.val.mode'));
	}
	if (bankName == "0" || bankName == undefined || bankName == '') {
		errorList.push(getLocalMessage('land.acq.val.bankName'));
	}
	if (instrumentalNo == "0" || instrumentalNo == undefined || instrumentalNo == '') {
		errorList.push(getLocalMessage('land.acq.val.instrumentNo'));
	}
	if (instrumentalDate == "0" || instrumentalDate == undefined || instrumentalDate == '') {
		errorList.push(getLocalMessage('land.acq.val.instrumentDate'));
	}

	return errorList;
}


//save bill payment 
function saveBillPayForm(obj) {
	
	var errorList = [];
	errorList = landBillPaymentValidation(errorList);
	// validation for save form submit

	if (errorList.length > 0) {
		// display error msg
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'AdminHome.html', 'saveform');
	}
}



