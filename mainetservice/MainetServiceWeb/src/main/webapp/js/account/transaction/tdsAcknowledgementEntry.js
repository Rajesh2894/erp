$(function() {
	$("#grid").jqGrid(
			{
				url : "TdsAcknowledgementEntry.html?getGridData",datatype : "json",mtype : "POST",colNames : [ '',getLocalMessage('accounts.payment.no'),getLocalMessage('advance.management.master.paymentdate'),getLocalMessage('advance.management.master.paymentamount'),"", getLocalMessage('bill.action')],
				colModel : [ {name : "paymentId",width : 10,sortable :  false,searchoptions: { "sopt": [ "eq"] },hidden:true  },
				             {name : "paymentNo",width : 25,sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']} },
				             {name : "paymentDate",width : 30,sortable :  true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni']}  },
				             {name : "tdsPaymentAmt",width : 35,sortable : true,classes:'text-right', editable: true,sortable : true,searchoptions: { "sopt": ['cn', 'bw', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bn', 'ew', 'en', 'nc', 'nu', 'nn', 'in', 'ni'] } },
				             {name : "paymentIdFlagExist",width : 20,sortable : true,searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name: 'paymentId', index: 'paymentId', width:15 , align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "dsgid",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,
				},
				autoencode : true,
				caption : "Tds Acknowledgement Payment List"
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", ""); 
	 
});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClass' title='View'value='"+rowdata.paymentId+"' paymentId='"+rowdata.paymentId+"' ><i class='fa fa-eye'></i></a> " +
   		 "<a class='btn btn-blue-3 btn-sm editClass' title='Process'value='"+rowdata.paymentId+"' paymentId='"+rowdata.paymentId+"' ><i class='fa fa-file-text-o'></i></a> ";
}
$(function() {		
	$(document).on('click', '.viewClass', function() {
		
		var errorList = [];
		var $link = $(this);
		var paymentId = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(4)').text();
		
		var url = "TdsAcknowledgementEntry.html?tdsAckpaymentReportForm";
		var requestData = "paymentId=" + paymentId + "&MODE=" + "VIEW";
		if(authStatus=="Y"){
			var returnData =__doAjaxRequest(url,'post',requestData,false);
			$(".widget-content").html(returnData);
			$(".widget-content").show();
			$('select').attr("disabled", true);
			$('input[type=text]').attr("disabled", true);
			$('input[type="text"], textarea').attr("disabled", true);
			$('select').prop('disabled', true).trigger("chosen:updated");
			prepareDateTag();
		}
		else{
			errorList.push("No Records Found in TDS Aknowledgement, so VIEW is not allow!");
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	
				return false;
		    }
		}
	});
		
});
function proceedForPayment(element) {
	var errorList = [];
	errorList = validateListForm(errorList);
	if (errorList.length == 0) {
		var url = "PaymentEntry.html?proceedPayment";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var returnData = __doAjaxRequestValidationAccor(element, url, 'post',
				requestData, false, 'html');
		if (returnData != false) {
			$('.content').html(returnData);
			prepareDateTag();
			return false;
			var totalAmount = $("#totalAmount").val();
			$("#amountToPay").val(totalAmount);
		}
	} else {
		displayErrorsPage(errorList);
	}
}

/*******************************************************************************/

$(function() {	
	
	$(document).on('click', '.editClass', function() {
		var errorList = [];
		var $link = $(this);
		var paymentId = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(4)').text();
		var paymentDate = $link.closest('tr').find('td:eq(2)').text();
		var url = "TdsAcknowledgementEntry.html?tdsAckPaymentForm";
		var qrtId= $("#qtrId").val();
	
		var requestData = "paymentId=" + paymentId + "&qrtId=" + qrtId + "&paymentDate=" + paymentDate + "&MODE=" + "EDIT";
		var returnData =__doAjaxRequest(url,'post',requestData,false);
		if(authStatus!="Y"){
			$(".widget-content").html(returnData);
			$(".widget-content").show();
		}
		else{
			errorList.push("Already Exists this given record in TDS Aknowledgement, so Process is not allow!");
			if(errorList.length>0){
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		    	});
		    	errorMsg +='</ul>';
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');	
				return false;
		    }
		}
	});
		
});

/**************************************************************************************/

$(function() {
	var response = __doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET',
			{}, false, 'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	
	$("#fromDateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	
	$("#fromDateId").datepicker('setDate', new Date());

	$('#fromDateId, #toDateId').change(function() {
		var check = $(this).val();
		if (check == '') {
			$(this).parent().switchClass("has-success", "has-error");
		} else {
			$(this).parent().switchClass("has-error", "has-success");
		}
	});
	$("#toDateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	$("#toDateId").datepicker('setDate', new Date());

	
	$("#ackDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	$("#challanDateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	
});

/**
 * *****************************************************Form
 * Validation**********************************
 */

function getAckDetails(obj) {
	
	$('.error-div').hide();
	var errorList = [];
	errorList = validateTDSForm(errorList);

	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}

		if (errorList.length == 0) 
		{
			var url = "TdsAcknowledgementEntry.html?tdsPaymentDetails";
			var formName = findClosestElementId(obj, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		
		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];
			
			errorList.push(getLocalMessage("account.norecord.criteria"));
			
			if(errorList.length>0){		    	
		    	var errorMsg = '<ul>';
		    	$.each(errorList, function(index){
		    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
		    	});
		    	errorMsg +='</ul>';		    	
		    	$('#errorId').html(errorMsg);
		    	$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');		    		    	
		    }
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	  }
}

/** *************************************************** */

function validateTDSForm(errorList) {
	
	var tdsTypeId = $("#tdsTypeId").val();
	if (tdsTypeId == "" || tdsTypeId == null) {
		errorList.push(getLocalMessage('account.select.tdsType'));
	}
	
	var qtrId=$("#qtrId").val();
	if (qtrId == "" || qtrId == null) {
		errorList.push(getLocalMessage('account.select.quarter'));
	}
	
	var fromDateId = $("#fromDateId").val();
	if (fromDateId == "" || fromDateId == null) {
		errorList.push(getLocalMessage('account.select.fromDate'));
	}
	
	var toDateId = $("#toDateId").val();
	if (toDateId == "" || toDateId == null) {
		errorList.push(getLocalMessage('account.select.toDate'));
	}
	if(fromDateId!=null)
		{
		errorList = validatedate(errorList,'fromDateId');
		}
	if(toDateId!=null)
	{
	errorList = validatedate(errorList,'toDateId');
	}
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var fromdate = new Date($("#fromDateId").val().replace(pattern, '$3-$2-$1'));
	var todate = new Date($("#toDateId").val().replace(pattern, '$3-$2-$1'));

	if (todate < fromdate) {
		alert("From Date  can not be greater than To Date")
		/* errorList.push("To Date can not be greater than From Date "); */
	}
	return errorList;
}

/**
 * *****************************************************End Form
 * Validation**********************************
 */

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

function saveTdsAckPaymentEntry(element) {
	
	var errorList = [];
	errorList = tdsAckvalidateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	if (errorList.length == 0) {
		var url = "TdsAcknowledgementEntry.html?create";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var status = __doAjaxRequestForSave(url, 'post', requestData, false,
				'', element);
		if (status != false) {
			if (status == 'Y') {
				showConfirmBox();
			} else {
				$(".widget-content").html(status);
				$(".widget-content").show();
			}
		} else {
			displayErrorsPage(errorList);
		}
	}
}

function tdsAckvalidateForm(errorList) {

	var challanId = $("#challanId").val();

	if (challanId == "" || challanId == null) {
		errorList.push(getLocalMessage('account.enter.challanNo'));
	}
	var challanDate = $("#challanDateId").val();
	if(challanDate!=null)
		{
		errorList = validatedate(errorList,'challanDateId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#challanDateId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Challan date can not be less than SLI date");
			  }
			}
		  }
		}
	if (challanDate == "" || challanDate == null) {
		errorList.push(getLocalMessage('account.enter.challanDate'));
	}
	var acknowledgementId = $("#acknowledgementId").val();
	
	if (acknowledgementId == "" || acknowledgementId == null) {
		errorList.push(getLocalMessage('account.select.ackNo'));
	}
	var ackDate = $("#ackDate").val();
	if(ackDate!=null)
		{
		errorList = validatedate(errorList,'ackDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#ackDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Acknowledgement date can not be less than SLI date");
			  }
			}
		  }
		}
	if (ackDate == "" || ackDate == null) {
		errorList.push(getLocalMessage('account.select.ackDate'));
	}
	
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	
	var oldPaymentDate = $("#paymentEntryDate").val();
	var paymentDate = new Date($("#paymentEntryDate").val().replace(pattern, '$3-$2-$1'));
	var challandate = new Date($("#challanDateId").val().replace(pattern, '$3-$2-$1'));
	var acknowDate = new Date($("#ackDate").val().replace(pattern, '$3-$2-$1'));
	
	if (challandate < paymentDate ) {
		 errorList.push("Challan Date should be equal or greater than TDS Payment Date."+oldPaymentDate); 
	}
	
	if (acknowDate < paymentDate ) {
		 errorList.push("Acknowledgement Date should be equal or greater than TDS Payment Date."+oldPaymentDate); 
	}
	

	return errorList;
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ "TDS Acknowledgemrnt Payment entry has been saved successfully"
			+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function proceed() {
	window.location.href = 'TdsAcknowledgementEntry.html';
}























