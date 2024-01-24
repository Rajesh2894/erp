function getChequeNos(){
	
	var bankAcId = $('#bankAccountId').val();
	var data = "&bankAcId="+bankAcId;
	var errorList =[];
	
		$('#issuedChequeNo').find('option:gt(0)').remove();
		var response = __doAjaxRequest('Chequebookleafmaster.html?getIssuedChequeNumbers','POST',data,false,'json');

			var chequeNoMap = '';
			$.each(response,function(key,value){
				chequeNoMap += "<option value='" +key+"' selected>" + value + "</option>";
			});
			$('#issuedChequeNo').append(chequeNoMap);
			$('#issuedChequeNo').val('');	
			$('#issuedChequeNo').trigger('chosen:updated');
}


function getNotIssuedChequeNos(){

	var bankAcId = $('#bankAccountId').val();
	var data = "&bankAcId="+bankAcId;
	var errorList =[];
	if(bankAcId!=""){
			$('#notIssuedChequeNo').find('option:gt(0)').remove();
			var response = __doAjaxRequest('ContraVoucherEntry.html?getChequeNumbers','POST',data,false,'json');
	
				var chequeNoMap = '';
				$.each(response,function(key,value){
					chequeNoMap += "<option value='" +key+"' selected>" + value + "</option>";
				});
				$('#notIssuedChequeNo').append(chequeNoMap);
				$('#notIssuedChequeNo').val('');	
				$('#notIssuedChequeNo').trigger('chosen:updated');
				
				var bankAcocunt = $("#bankAccountId option:selected").text();		
				var chequeNo = $("#issuedChequeNo option:selected").text();
				if(bankAccount!="Select" && chequeNo !="Select"){
					$('#bankAccount').val(bankAcocunt);	
					$('#existingInstrumentNo').val(chequeNo);	
				}
				$('#errorDivId').hide();
	}else{
		errorList.push(getLocalMessage('account.chequeCancel.bankAcValidation'));
		displayErrorsPage(errorList);
	}
}


var elementTemp;
function saveChequeCancellation(element){
	debugger;
	elementTemp=element;
	var errorList =[];
	var  cancellationDate =$("#cancellationDate").val();
	if(cancellationDate!=null)
		{
		errorList = validatedate(errorList,'cancellationDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#cancellationDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Cancellation date can not be less than SLI date");
			  }
			}
		  }
		}
	    if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
	else{   
		
		showConfirmBoxSave();
	/*var url = "ChequeCancellationAndReIssue.html?create";
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var status = __doAjaxRequestValidationAccor(element,url,'post', requestData, false, 'html');
	 if(status != false){
		var obj = $(status).find('#successfulFlag');
		if ($('.content').html(status)) {
			if (obj.val() == 'Y') {

				showConfirmBox();
			}
		} else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	 }*/
	    }
}


function showConfirmBoxSave(){
	
	  var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
	    //if(auth=='Auth')
		 //saveorAproveMsg="Approve"; 
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\">'+ ""+saveorAproveMsg+""+ '</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>  '+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg(){
	
	var url = "ChequeCancellationAndReIssue.html?create";
	var formName = findClosestElementId(elementTemp, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var status = __doAjaxRequestValidationAccor(elementTemp,url,'post', requestData, false, 'html');
	 if(status != false){
		var obj = $(status).find('#successfulFlag');
		if ($(formDivName).html(status)) {
			if (obj.val() == 'Y') {

				showConfirmBox();
			}
		} else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	 }
}


function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls =  getLocalMessage('account.proceed.btn');
	
	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+
			 getLocalMessage('cheque.book.utilization.cancellation.reissue.proceed')+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}


function proceed() {
	window.location.href = 'ChequeCancellationAndReIssue.html';
}


function clearForm(){
	$('#bankAccountId').val('').trigger('chosen:updated');
	$('#issuedChequeNo').val('').trigger('chosen:updated');
	$('#notIssuedChequeNo').val('').trigger('chosen:updated');
}

function displayErrorsPage(errorList) {

	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}


function checkingBackDatedEntryDate(element){
	
	var errorList = [];
	var issuedChequeNo = $('#issuedChequeNo').val();
	
	if (issuedChequeNo == 0 || issuedChequeNo == ""){
		errorList.push(getLocalMessage('account.select.instrument.no'));
		$("#cancellationDate").val("");
	}
	
	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
	
	if (errorList.length == 0) {
		
		var postdata = 'issuedChequeNo=' + issuedChequeNo;
		var response = __doAjaxRequest('ChequeCancellationAndReIssue.html?getDateByInstrumentNo', 'POST',	postdata, false, 'json');
		
		var from = response.split("/");
		var transactionDate = new Date(from[2], from[1] - 1, from[0]);
		var to = $("#cancellationDate").val().split("/");
		var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
		
		if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
			if(instrumentDate < transactionDate){
				errorList.push("cancellation date should be equal or greater than payment date");
				$("#cancellationDate").val("");
				}
			}
		}
		if (errorList.length > 0) {
				var errMsg = '<ul>';
					$.each(
					errorList,
					function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index]
						+ '</li>';
					});
					errMsg += '</ul>';
					$('#errorId').html(errMsg);
					$('#errorDivId').show();
					$('html,body').animate({
						scrollTop : 0
					}, 'slow');
				return false;
		}
}
