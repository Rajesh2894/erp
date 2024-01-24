
/*function sumValidation() {
	
	var i ;
	sum = 0;
	var minAmt = ($('#balanceAmount').val());
	//parseFloat(maxAmt)
    if ($.fn.DataTable.isDataTable('#receiptAccountHeadsTable')) {
	$('#receiptAccountHeadsTable').DataTable().destroy();
    }
    $("#receiptAccountHeadsTable tbody tr").each(
	    function(i) {
	    	//var maxAmt = ($('#rfFeeamount'+i).val());
	    	var maxAmt =  parseFloat($('#rfFeeamount'+i).val());
	    	var rowCount = i + 1;
	    	sum = sum + maxAmt;
	    	console.log(sum);
	    });
    return  parseFloat(sum);
}
*/




function saveDataReceipt(element) {

	debugger;
	//debugger;
	var myerrorList = [];
	var minAmt =parseFloat($('#balanceAmount').val());
	var receiptAmount = parseFloat($("receiptAmount").val());
/*    var rmdatetemp = $.trim($("#rmDatetemp").val());
 
	
    if (rmdatetemp == 0 || rmdatetemp == "")
		myerrorList.push(getLocalMessage('Receipt Date can not be empty '));*/
	
	var receiptDate = $.trim($('#transactionDateId').val());
	if (receiptDate == "" || receiptDate == undefined ) {
		myerrorList.push(getLocalMessage('account.select.receiptDate'));
		
	}
	var mobileNumber=$('#mobile_Number').val();
	if(mobileNumber!=""){
 	 if($('#mobile_Number').val().length<10){
 		myerrorList.push(getLocalMessage('account.valid.mobileNo'));
	 }
	}
	if(receiptDate != null){
		myerrorList = validatedate(myerrorList,'transactionDateId');
		if (myerrorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				myerrorList.push("SLI Prefix is not configured");
			}else{
				var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
				var recDate = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
				var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
				if (recDate < sliDate) {
					myerrorList.push("Receipt date can not be less than SLI date");
				}
			}
		}
	}
	
	var receiptCategoryId = $.trim($("#receiptCategoryId").val());
	if (receiptCategoryId == 0 || receiptCategoryId == ""){
		myerrorList.push(getLocalMessage('account.select.receiptCategory'));	
	}
	
	if(receiptCategoryId != null && receiptCategoryId != ""){
	var depositSlipType=$("#receiptCategoryId option:selected").attr("code");
	if(depositSlipType=="M" || depositSlipType=="INV"){
		
	}else{
		var vm_VendorId = $.trim($("#vm_VendorId").val());
		if (vm_VendorId == 0 || vm_VendorId == ""){
			myerrorList.push(getLocalMessage('account.select.received.from'));
		}
	  }
	}
	var sudaEnv=$.trim($("#sudaEnv").val());
	var rmreceivedfrom = $.trim($("#rm_Receivedfrom").val());
	if ((sudaEnv!="true")&& (rmreceivedfrom == 0 || rmreceivedfrom == ""))
		myerrorList.push(getLocalMessage('account.enter.payeeName'));

	var rmnarration = $.trim($("#rmNarration").val());
	if (rmnarration == 0 || rmnarration == "")
		myerrorList.push(getLocalMessage('account.narration'));

	
	var emailId = $.trim($("#email_Id").val());
	if (emailId !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(emailId);
	   if (!valid) {
		   myerrorList.push(getLocalMessage('account.invalid.emailAdd'));
	   } 
	}
	  
	$('.accountClass').each(function(i) {
		for (var m = 0; m < i; m++) {
			if (($('#budgetCode' + m).val() == $('#budgetCode' + i).val())) {
				myerrorList.push("Receipt Heads cannot be same,please select another Receipt Head");
			}
		}

	});	  
	
	$('.accountClass')
			.each(
					function(i) {
					
					  /*var functionid = $.trim($("#functionId" + i).val());
						var pacheadId = $.trim($("#pacHeadId" + i).val());
						var sacheadId = $.trim($("#sacHeadId" + i).val());*/
						
						var budgetCode = $.trim($("#budgetCode" + i).val());
						
						
						var rffeeamount = $.trim($("#rfFeeamount" + i).val());
						var level = i + 1;
					
						if (budgetCode == 0 || budgetCode == "") {
					 	myerrorList.push(getLocalMessage("account.select.receiptHead"));
					    }
						
						if (rffeeamount == 0 || rffeeamount == "") {
							myerrorList.push(getLocalMessage("misc.demand.collection.validation.receipt.amount"));
						}

					});
	var tbl = document.getElementById("receiptAccountHeadsTable");
	var key ;
	var key1 ;
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	/*for (var i = 0; i < rowCount-1; i++)
	{
	    key =parseInt($("#fundId" + i).val()) + ";" +  parseInt($("#functionId" + i).val())+ ";" +    parseInt($("#sacHeadId" + i).val());
	    for (var j = 0; j < i; j++)
	    	{
	    	key1 =parseInt($("#fundId" + j).val()) + ";" +  parseInt($("#functionId" + j).val())+ ";"   +  parseInt($("#sacHeadId" + j).val());
	    	var dupVal=(key == key1);
	    	if(dupVal)
	    		{
	    	     	myerrorList.push(getLocalMessage('Duplicate receipt collection details found '+ i));
	    		}
	    	}
	}*/
	
	var cpdfeemode = $.trim($("#cpdFeemode").val());
	if (cpdfeemode == 0 || cpdfeemode == "")
		myerrorList.push(getLocalMessage('account.select.receiptMode'));
	
    var option=$("#cpdFeemode option:selected").attr("code");
    if (option=='Q' || option=='D' || option=='F' || option=='P')
    	{
    	
    	var cbbankid = $.trim($("#bankId").val());
    	if (cbbankid == 0 || cbbankid == "")
    		myerrorList.push(getLocalMessage('account.select.bank.name'));
    	
    	var rdchequeddnotemp = $.trim($("#rdchequeddno").val());
    	if ((rdchequeddnotemp == 0 || rdchequeddnotemp == "")&& option!='Q')
    		myerrorList.push(getLocalMessage('account.enter.instrument.no'));
    	
    	var rdchequedddate = $.trim($("#rdchequedddatetemp").val());
    	if(rdchequedddate!=null)
    		{
    		myerrorList=validatedate(myerrorList,'rdchequedddatetemp');
    		if (myerrorList.length == 0) {
    			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
    			if(response == "Y"){
    				myerrorList.push("SLI Prefix is not configured");
    			}else{
    			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
    			var date = new Date($("#rdchequedddatetemp").val().replace(pattern,'$3-$2-$1'));
    			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
    			if (date < sliDate) {
    				myerrorList.push("Instrument date can not be less than SLI date");
    			  }
    			}
    		  }
    		}
    	if (rdchequedddate == 0 || rdchequedddate == "")
    		myerrorList.push(getLocalMessage('account.select.instrument.date'));
    	
    	}

    
    if (option=='RT'  || option=='N'  || option=='B' )
	{
	var baaccountid = $.trim($("#baAccountId").val());
	if (baaccountid == 0 || baaccountid == "")
		myerrorList.push(getLocalMessage('account.select.bank.name'));
	
	var tranrefNumber1 = $.trim($("#tranRefNumber1").val());
	/*if (tranrefNumber1 == 0 || tranrefNumber1 == "")
		myerrorList.push(getLocalMessage('account.enter.instrument.no'));*/
	
	
	var tranrefDate1 = $.trim($("#tranRefDate1").val());
	if(tranrefDate1!=null)
		{
		myerrorList = validatedate(myerrorList,'tranRefDate1');
		if (myerrorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				myerrorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#tranRefDate1").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				myerrorList.push("Instrument date can not be less than SLI date");
			  }
			}
		  }
		}
	if (tranrefDate1 == 0 || tranrefDate1 == "")
		myerrorList.push(getLocalMessage('account.select.instrument.date'));
	}
    
    if (cpdfeemode == 0 || cpdfeemode == ""){
    	var tranrefNumber1 = $.trim($("#tranRefNumber1").val());
    	if (tranrefNumber1 == 0 || tranrefNumber1 == ""){
		myerrorList.push(getLocalMessage('account.enter.instrument.no'));
    	}
    }
	
   var feeModeStatus =  $("#feeModeStatus").val();
   if (feeModeStatus == "N"){
		myerrorList.push(getLocalMessage("account.budgetHead.not.available.against.receiptMode"));
	}
   
   var templateExistsFlag = $("#templateExistsFlag").val();
	if(templateExistsFlag=="N"){
		myerrorList.push(getLocalMessage('accounts.receipt.receipt.voucher'));
	}
    
	var totalAmount = $("#rdamount").val();
	var balance =  parseFloat($('#balanceAmount').val())
	var receipt = parseFloat($('#totalReceiptAmount').val())
	var difference =  parseFloat($('#balanceAmount').val()) -parseFloat($('#totalReceiptAmount').val());
	debugger;

	if($("#url").val()=="INV")
	{
		/*if(totalAmount != minAmt)
		{
		myerrorList.push(getLocalMessage('Total Receipt Amount should equal to Investment Amount: '+ minAmt));
		}*/
	}
	else if(totalAmount > minAmt)
		{
		myerrorList.push(getLocalMessage('account.totalReceiptAmt.less.equalTo.blncAmt')+' '+minAmt);
		}
	else if(totalAmount > difference )
	{
		myerrorList.push(getLocalMessage('account.recieptAmt.exceed'));
	}
	
/*	if($("#url").val()=="INV")
	{
		if(totalAmount != balance)
			{
				myerrorList.push(getLocalMessage('Total Receipt Amount should be equal to Investment Amount: '+ minAmt));
			}
	}
		else {

		if (totalAmount > minAmt) {
			myerrorList
					.push(getLocalMessage('Total Receipt Amount should be less than or equal to Balance Amount: '
							+ minAmt));
		} else if (totalAmount > difference) {
			myerrorList.push(getLocalMessage('Receipt amount exceeded'));
		}
	}
	*/
	
	var sumOfDedctionAmt = 0;
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	
	for (var i = 0; i < rowCount - 1; i++) {
		sumOfDedctionAmt += parseFloat($('#rfFeeamount' + i).val());
	}
	
	if((sumOfDedctionAmt != null) && (sumOfDedctionAmt != "") && (!isNaN(sumOfDedctionAmt)) && (sumOfDedctionAmt != undefined) ){
		if(parseFloat(sumOfDedctionAmt) != parseFloat(totalAmount)){
			myerrorList.push(getLocalMessage('account.discrepency.found.receiptEntry'));
		}
	}
	
/*	var rdamount = $.trim($("#rdamount").val());
	if (rdamount == 0 || rdamount == "")
		myerrorList
				.push(getLocalMessage('Receipt mode amount can not be empty '));*/

	if (myerrorList.length > 0) {

		var errMsg = '<ul>';
		$.each(myerrorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ myerrorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorId").html(errMsg);
		$('#errorDivId').show();
		$("#errorDivId").removeClass('hide');
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

		errorList = [];
		return false;
	}

	else {
		showConfirmBox();
	
		//HELLO
		/*var flag = $("#url").val()
		if(flag == "invest")
		{
			var formUrl = "investmentMaster.html"
			var invstId = $("#prAdvEntryId").val();
			var actionParam = "updateStatus";
			var requestdata = {
					"invstId" : invstId
				};
			
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
			
		}*/

		
		
	}
}

function saveDataAndShowSuccessMsg(){
	
	$.fancybox.close();
	//var formName = findClosestElementId(element, 'form');
	//var theForm = '#' + formName;
	//var requestData = __serializeForm(theForm);
	
	var requestData = $('#tbServiceReceiptMas').serialize();
	var url ='AccountReceiptEntry.html?create';
	/*var url = $(theForm).attr('action');*/
	
	var response = __doAjaxRequestForSave(url, 'post', requestData, false,'', '');
	var receiptmsg=getLocalMessage('accounts.receipt.receiptnumber');
	var msg=getLocalMessage('accounts.receipt.success');
	if ($.isPlainObject(response)) {
		var receiptmsg=getLocalMessage('accounts.receipt.receiptnumber');
		var msg=getLocalMessage('accounts.receipt.success');
		var flagType = $('#advanceFlag').val();
		if(flagType=="Y"){
			
			showConfirmBoxReceiptAdvance(receiptmsg + " " + response.tbServiceReceiptMas.rmRcptno + " " + msg);
		}else{
			showConfirmBoxReceipt(receiptmsg + " " + response.tbServiceReceiptMas.rmRcptno + " " + msg);
		}
	} else {
		var divName = '.widget';
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		CashModeValidation();
		return false;
	}
}


function showConfirmBox(){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
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

function editSaveDataReceipt(element)
{
	var myerrorList = [];
	
	var receiptdelremark = $.trim($("#receiptDelRemark").val());
	if (receiptdelremark == 0 || receiptdelremark == "")
		myerrorList.push(getLocalMessage('account.deletion.remark.not.empty'));

	if (myerrorList.length > 0) {

		var errMsg = '<ul>';
		$.each(myerrorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ myerrorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorId").html(errMsg);
		$('#errorDivId').show();
		$("#errorDivId").removeClass('hide');
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

		errorList = [];
		return false;
	}

	else {	
	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var receiptDelRemark = $('#receiptDelRemark').val();
	var rmRcptid = $('#rmRcptid').val();
	var receiptDelDatetemp= $('#receiptDelDatetemp').val();
	var requestData = "receiptDelRemark=" + receiptDelRemark + "&rmRcptid=" + rmRcptid+ "&receiptDelDatetemp=" + receiptDelDatetemp;
	var url ='AccountVoucherReversal.html?Update';/* $(theForm).attr('action');*/
	var response = __doAjaxRequestForSave(url, 'post', requestData, false,'', element);
	
	 var obj=$(response).find('#successFlag'); 
	 
     if(obj.val()=='Y')
	    	{
	    	showConfirmBox_del();
	    	}
	     else
	    	{
	    	 var divName = '.widget';
	 		$(divName).removeClass('ajaxloader');
	 		$(divName).html(response);
	 		return false;
	    	}
	}
}


function showConfirmBox_del()
{
	  var	errMsgDiv		='.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		var msg ="Receipt Delete Successfully";
     	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+msg+'</h5>';
   	    message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
}
function proceed() {
	window.location.href = 'AccountVoucherReversal.html'; 
}

// to generate dynamic table
$("#taxHeadsTable")
		.on(
				"click",
				'.addButton',
				function(e) {
					var errorList = [];
					$('.appendableClass tr').each(function(i) {
						row = i - 1;
					});
					
					$('.accountClass').each(function(i) {
						for (var m = 0; m < i; m++) {
							if (($('#budgetCode' + m).val() == $('#budgetCode' + i).val())) {
								errorList.push("Receipt Heads cannot be same,please select another Receipt Head");
							}
						}

					});				

					var budgetCode = $.trim($("#budgetCode" + row).val());
					if (budgetCode == 0 || budgetCode == ""){
						errorList.push(getLocalMessage('account.select.receiptHead'));
					}
					var rfFeeamount = $.trim($("#rfFeeamount" + row).val());
					if (rfFeeamount == 0 || rfFeeamount == ""){
						errorList.push(getLocalMessage('misc.demand.collection.validation.receipt.amount'));
					}

					if (errorList.length > 0) {

						var errMsg = '<ul>';
						$
								.each(
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

					e.preventDefault();
					//$(".datepicker").datepicker("destroy");

					var content = $(this).closest('#receiptAccountHeadsTable tr').clone();
					$(this).closest("#receiptAccountHeadsTable").append(content);

					// reset values
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find('div.chosen-container').remove();
					 content.find("select:eq(0)").chosen().trigger("chosen:updated");
					// for generating dynamic Id
					content.find("select:eq(0)").attr("id", "budgetCode" + (row));
					
					content.find("input:text:eq(1)").attr("id","rfFeeamount" + (row));

					content.find("select:eq(0)").attr("name","receiptFeeDetail[" + row+ "].sacHeadId");
					content.find("input:text:eq(1)").attr("name","receiptFeeDetail[" + row + "].rfFeeamount");
					content.find('.delButton').attr("id", "delButton" + (row));
					content.find('.addButton').attr("id", "addButton" + (row));

					// to add date picker on dynamically created Date fields
					/*$(".datepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true
					});*/
					content.find("select").val("");
					reOrderTableIdSequence();

				});

// to delete row
$("#taxHeadsTable").on("click", '.delButton', function(e) {
   //debugger;
   $("[data-toggle='tooltip']").tooltip('hide');
	var rowCount = $('#taxHeadsTable tr').length;
	if (rowCount <= 2) {
		return false;
	}

	$(this).closest('#taxHeadsTable tr').remove();
	//totalReceiptamount();
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	dynamicTotalReceiptAmount(rowCount)
	reOrderTableIdSequence();
	e.preventDefault();
});

function reOrderTableIdSequence() {

	$('.appendableClass tr').each(
			function(i) {
				i = i - 1;

				//$(".datepicker").datepicker("destroy");

				$(this).find("select:eq(0)").attr("id", "budgetCode" + i);
				$(this).find("input:text:eq(1)").attr("id", "rfFeeamount" + i);


				$(this).find("select:eq(0)").attr("name","receiptFeeDetail[" + i+ "].sacHeadId");
				$(this).find("input:text:eq(1)").attr("name","receiptFeeDetail[" + i + "].rfFeeamount");
				$(this).parents('tr').find('.delButton').attr("id","delButton" + i);
				$(this).parents('tr').find('.addButton').attr("id","addButton" + i);

				/*$(".datepicker").datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true
				});*/

			});
}

function setVendorName(obj)
{
	$('.error-div').hide();
	var errorList = [];
	
	$('#mobile_Number').val("");	
	$('#email_Id').val("");
	
	var  vmVendrnameId = $("#vm_VendorId option:selected").val();
	var  vmVendrname= $("#vm_VendorId option:selected").text();
	
	if(vmVendrnameId != null && vmVendrnameId != undefined && vmVendrnameId != ""){
		$("#rm_Receivedfrom").val(vmVendrname).prop("readonly",true);	
	}else{
		$("#rm_Receivedfrom").val("").prop("readonly",false);
	}
	
	//$("#rm_Receivedfrom").prop("readonly",true);
	
	if (errorList.length == 0) {

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "AccountReceiptEntry.html?getVendorPhoneNoAndEmailId";
		var returnRevData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnRevData, function(index , value) {
		
			$('#mobile_Number').val(returnRevData[0]);	
			$('#email_Id').val(returnRevData[1]);
			
			$("#mobile_Number").prop("readonly",true);	
			$("#email_Id").prop("readonly",true);	
			
		});
	}
}


function enableMobileAndEmail(){
	
	$("#mobile_Number").prop("readonly",false);	
	$("#email_Id").prop("readonly",false);	
	
	
}




var thisCount;
function setSecondaryCodeFinance(count) {
	thisCount = count;
	var primaryCode = $('#pacHeadId' + count).val();
	$('#sacHeadId' + count).find('option:gt(0)').remove();

	if (primaryCode > 0) {
		var postdata = 'pacHeadId=' + primaryCode;
		var json = __doAjaxRequest('AccountReceiptEntry.html?sacHeadItemsList',	'POST', postdata, false, 'json');
		var optionsAsString = '';
		$.each(json, function(key, value) {
			optionsAsString += "<option value='" + key + "'>" + value+ "</option>";
		});
		$('#sacHeadId' + count).append(optionsAsString);
	}
}

  
$( document ).ready(function() {

					$('.tdsClass')
							.each(
									function(i) {
										var primaryCode = $('#pacHeadId' + i)
												.val();
										$('#sacHeadId' + i).find('option:gt(0)').remove();
										if (primaryCode > 0) {
											var postdata = 'pacHeadId='	+ primaryCode;
											var json = __doAjaxRequest('AccountReceiptEntry.html?sacHeadItemsList','POST', postdata, false,'json');
											var optionsAsString = '';

											$
													.each(
															json,
															function(key, value) {
																if (key == $('#selectedSecondaryValue'+ i).val()) {
																	optionsAsString += "<option value='"+ key+ "' selected>"+ value+ "</option>";
																} else {
																	optionsAsString += "<option value='"+ key+ "'>"+ value+ "</option>";
																}
															});
											$('#sacHeadId' + i).append(
													optionsAsString);

										}
									});
				
				});

$( document ).ready(function() {

	var flagType = $('#advanceFlag').val();
	if(flagType=="Y")
		{
		$('#rfFeeamount0').change(function() {
			
		var minAmt = ($('#balanceAmount').val());
		var maxAmt = ($('#rfFeeamount0').val());
		var totalReceiptAmount =  ($('#totalReceiptAmount').val());
		 
		  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
		    try{
		      maxAmt = parseFloat(maxAmt);
		      minAmt = parseFloat(minAmt);

		      if(maxAmt > minAmt) {
		        var msg = "Receipt Amount can not be greater than Advance Balance Amount.!";
		    	  showErrormsgboxTitle(msg);
		        $('#rfFeeamount0').val("");
		        $('#rdamount').val("");
		        return false;
		      }
		    }catch(e){
		      return false;
		    }
		  } //end maxAmt minAmt comparison 
		 });
		}
});
function CashModeValidation() {

    
	   var option=$("#cpdFeemode option:selected").attr("code");
	   var cpdFeemode=$("#cpdFeemode option:selected").val();
	   //alert(cpdFeemode)
	   
        if (option == 'C') {
        	
        	
        	      rdchequeddno.value = "";
        	      bankId.value = "";
        		  rdchequedddatetemp.value = "";
	              baAccountId.value = "";
	              tranRefNumber1.value = "";
	              tranRefDate1.value = "";
        		  $('#tablePayModeHeading_cheqDD').hide();
        		  $('#tablePayModeHeading_cash').show();
        		  $('#tablePayModecbBankid').hide();
        		  $('#rdchequeddnodata').hide();
        		  $('#rdchequedddatetempdata').hide();
        	  	  $('#tablePayModeHeading_nftwebrtgs').hide();
        	      $('#tableRecModecbBankid').hide();
        	   	  $('#tranRefNumber').hide();
        	   	  $('#tranRefDate').hide();
        	   	  $('#baAccountId').val('').trigger('chosen:updated');
        	   	 
        }
        else if (option =="Q" || option =="D" || option =="F" || option =="P") 
                	  {
			        	baAccountId.value = "";
			        	tranRefNumber1.value = "";
			        	tranRefDate1.value = "";
            		  $('#tablePayModeHeading_cheqDD').show();
            		  $('#tablePayModeHeading_cash').hide();
            		  $('#tablePayModecbBankid').show();
            		  $('#rdchequeddnodata').show();
            		  $('#rdchequedddatetempdata').show();
            	      $('#tablePayModeHeading_nftwebrtgs').hide();
            	 	  $('#tableRecModecbBankid').hide();
            	      $('#tranRefNumber').hide();
            	   	  $('#tranRefDate').hide();
            	   	$('#baAccountId').val('').trigger('chosen:updated');
            	   	$("#tranRefDate1").datepicker('setDate', new Date()); 
            		$("#rdchequedddatetemp").datepicker('setDate', new Date()); 
            	    	   if(option =="Q"){
            	    		   $("#DDno").hide();
            	    	   }
                	  }
       else
        	{
    	  
    	   
    	//   cbBankid.value="";
   	         rdchequeddno.value = "";
   	         bankId.value = "";
			 rdchequedddatetemp.value = "";
        	 $('#tablePayModeHeading_cheqDD').hide();
   		     $('#tablePayModeHeading_cash').hide();
   		     $('#tablePayModecbBankid').hide();
   		     $('#rdchequeddnodata').hide();
   		     $('#tablePayModeHeading_nftwebrtgs').show();
   		     $('#rdchequedddatetempdata').hide();
   	 		 $('#tableRecModecbBankid').show();
	   		 $('#tranRefNumber').show();
	   		 $('#tranRefDate').show();
	   		$('#baAccountId').val('').trigger('chosen:updated');
	   	 	$("#tranRefDate1").datepicker('setDate', new Date()); 
	   	 $("#rdchequedddatetemp").datepicker('setDate', new Date()); 
	   	 if(option =="B"){
  		   $("#DDno1").hide();
  	   }
        	}
        
        var postData = "cpdFeemode="+cpdFeemode;
        var url = "AccountReceiptEntry.html?checkBudgetCodeForFeeMode"
        var result = __doAjaxRequest(url, 'POST', postData, false, 'json');
        $("#feeModeStatus").val(result);
        
        copyReceiptDate();
}


function validateReceiptHead(){
	
	var myerrorList = [];
	
	$('.accountClass').each(function(i) {
		for (var m = 0; m < i; m++) {
			if (($('#budgetCode' + m).val() == $('#budgetCode' + i).val())) {
				myerrorList.push("Receipt Heads cannot be same,please select another Receipt Head");
				$('#budgetCode'+ i).val(""); 
				$("#budgetCode"+ i).val('').trigger('chosen:updated');
				var errMsg = '<ul>';
				$.each(myerrorList, function(index) {
					errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
							+ myerrorList[index] + '</li>';
				});

				errMsg += '</ul>';
				$("#errorId").html(errMsg);
				$('#errorDivId').show();
				$("#errorDivId").removeClass('hide');
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');

				errorList = [];
				return false;
			}
		}

	});
	
	
}
        

function CashModeValidationView() {
	
    
	   var option=$("#cpdFeemodeCode").val();

	   
     if (option == 'C') {
     	
     
     	      rdchequeddno.value = "";
     	      //bankId.value = "";
     		  rdchequedddatetemp.value = "";
	          //    baAccountId.value = "";
	              tranRefNumber1.value = "";
	              tranRefDate1.value = "";
     		  $('#tablePayModeHeading_cheqDD').hide();
     		  $('#tablePayModeHeading_cash').show();
     		  $('#tablePayModecbBankid').hide();
     		  $('#rdchequeddnodata').hide();
     		  $('#rdchequedddatetempdata').hide();
     	  	  $('#tablePayModeHeading_nftwebrtgs').hide();
     	      $('#tableRecModecbBankid').hide();
     	   	  $('#tranRefNumber').hide();
     	   	  $('#tranRefDate').hide();
     	   		
     } else if (option =="Q" || option =="D" || option =="F" || option =="P") {
 	       // 	baAccountId.value = "";
	        	tranRefNumber1.value = "";
	        	tranRefDate1.value = "";
         		  $('#tablePayModeHeading_cheqDD').show();
         		  $('#tablePayModeHeading_cash').hide();
         		  $('#tablePayModecbBankid').show();
         		  $('#rdchequeddnodata').show();
         		  $('#rdchequedddatetempdata').show();
         	      $('#tablePayModeHeading_nftwebrtgs').hide();
         	 	  $('#tableRecModecbBankid').hide();
         	      $('#tranRefNumber').hide();
         	   	  $('#tranRefDate').hide();
        }  else {

        	
	         //rdchequeddno.value = "";
	        // bankId.value = "";
			 rdchequedddatetemp.value = "";
     	 $('#tablePayModeHeading_cheqDD').hide();
		     $('#tablePayModeHeading_cash').hide();
		     $('#tablePayModecbBankid').hide();
		     $('#rdchequeddnodata').hide();
		     $('#tablePayModeHeading_nftwebrtgs').show();
		     $('#rdchequedddatetempdata').hide();
	 		 $('#tableRecModecbBankid').show();
	   		 $('#tranRefNumber').show();
	   		 $('#tranRefDate').show();
	   		 
     	}
   
}
     

function totalReceiptamount(){
	//debugger;
    var amount=0; 
  
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	
	
	/*for (var i = 0; i < rowCount - 1; i++) {*/
	
	/*$('.accountClass').each(function(i) {*/
		for (var m = 0; m <= rowCount - 1; m++) {
			
			   var  n= parseFloat(parseFloat($("#rfFeeamount" + m).val()));
			    if (isNaN(n)) {
			        return n=0;
			    }
			    amount += n ;
			    
		var result = amount.toFixed(2);
		$("#rdamount").val(result);
		$("#rmAmount").val(result);
		
		}
	//});
		
	//}
		
}

function getAmountFormat(){
  
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	
		for (var m = 0; m <= rowCount - 1; m++) {
			
			var transferAmount = $('#rfFeeamount'+ m).val();

			if (transferAmount != undefined && !isNaN(transferAmount) && transferAmount != null && transferAmount != '') {
				
			var actualAmt = transferAmount.toString().split(".")[0];
			var decimalAmt = transferAmount.toString().split(".")[1];
			
			var decimalPart =".00";
			if(decimalAmt == null || decimalAmt == undefined){
				$('#rfFeeamount'+ m).val(actualAmt+decimalPart);
			}else{
				if(decimalAmt.length <= 0){
					decimalAmt+="00";
					$('#rfFeeamount'+ m).val(actualAmt+(".")+decimalAmt);
				}
				else if(decimalAmt.length <= 1){
					decimalAmt+="0";
					$('#rfFeeamount'+ m).val(actualAmt+(".")+decimalAmt);
				}else{
					if(decimalAmt.length <= 2){
					$('#rfFeeamount'+ m).val(actualAmt+(".")+decimalAmt);
					} 
				  }	
			   }
		    }
		}
		
}

function dynamicTotalReceiptAmount(rowCount){
	//debugger;
    var amount=0; 
  
		for (var m = 0; m <= rowCount-1; m++) {
			
			   var  n= parseFloat(parseFloat($("#rfFeeamount" + m).val()));
			    if (isNaN(n)) {
			       n=0;
			    }
			    amount += n ;
			    
		$("#rdamount").val(amount.toFixed(2));
		$("#rmAmount").val(amount.toFixed(2));
		}
}

function resetDate(){
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		minDate : $("#tranRefDate1").val(),
		maxDate : '-0d',
		changeYear : true
	});
	$("#tranRefDate1").datepicker('setDate', new Date()); 
	$("#rdchequedddatetemp").datepicker('setDate', new Date()); 
	
}

function copyReceiptDate(){
	
	var errorList = [];
	
	var option = $("#cpdFeemode option:selected").attr("code");
	if (option != "C"){
	
	var transactionDateId = $.trim($("#transactionDateId").val());
	if (transactionDateId == 0 || transactionDateId == ""){
		errorList.push(getLocalMessage('account.select.receiptDate'));
		$('#cpdFeemode').val("");
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
	}else{
		var transactionDateId = $('#transactionDateId').val();
		$('#rdchequedddatetemp').val(transactionDateId);
		$('#tranRefDate1').val(transactionDateId);
		}
	}
}

$('#createBtn').click(function(){
	
	 var ajaxResponse = __doAjaxRequest('AccountReceiptEntry.html?form', 'POST', {}, false,'html');
	 var divName = '.content';
	 $(divName).html(ajaxResponse);
	 //$(".widget-content").html(ajaxResponse);
	 //$(".widget-content").show();
});	


function validateChequeDate(){
	
	var errorList = [];
	
	
	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#rdchequedddatetemp").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);
	
	if((transactionDate != "" && transactionDate != null) && (instrumentDate != "" && instrumentDate != null)){
		if(instrumentDate > transactionDate){
			errorList.push("Cheque date should not be greater than receipt date");
			$("#rdchequedddatetemp").val("");
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
	//errorList = validatePaymentForm(errorList);
	if (errorList.length == 0) {
	
	$('#errorDivId').hide();
	var postData = __serializeForm("#tbServiceReceiptMas");
	
	var url = "AccountReceiptEntry.html?validateChequeDate";
	var returnData = __doAjaxRequest(url, 'post', postData, false);
	if (returnData) {
			errorList.push("Cheque Date should not be older than 3 months then receipt date.");
			$("#rdchequedddatetemp").val("");
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
	}
}

function resetReceiptModeDetails(obj){
	
	$('#cpdFeemode').val("");
}

function resetReceiptCategoryForm(obj){
	
	$('.error-div').hide();
	var errorList = [];
	
	
	
	var receiptCategoryId = $('#receiptCategoryId').val();
	if(receiptCategoryId != null && receiptCategoryId != ""){
	var depositSlipType=$("#receiptCategoryId option:selected").attr("code");
	if(depositSlipType=="M" || depositSlipType=="INV"){
		$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
	}else{
		$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
	}
	
	if (errorList.length == 0) {
		
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountReceiptEntry.html?getReceiptAccountHeadData";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		var divName = '.content';
		$(divName).html(response);
		//$(".widget-content").html(response);
		//$(".widget-content").show();
		
		var depositSlipType1=$("#receiptCategoryId option:selected").attr("code");
		if(depositSlipType1=="M" || depositSlipType=="INV"){
			$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
		}else{
			$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
		}
		
		}
	}
};


function back()
{
	var flag = $("#url").val()
	if(flag == "LNR")
	{
		flag = "loanmaster.html";
	}
	else if(flag == "INV")
	{
		flag = "investmentMaster.html";
		
	}
	else if(flag == "GRT")
	{
		flag = "grantMaster.html";
	}
	else
	{
		flag = "AccountReceiptEntry.html";
	}
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', flag);
	$("#postMethodForm").submit();
}