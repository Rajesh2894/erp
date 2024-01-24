 	/**
 	 * script being used for reversal entry
 	 * 				
 	 */
var map ={};
var transactionMap = {};
var removedCount = 0;
$(function(){
	
	
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		minDate : $("#transDate").val(),
		maxDate : '-0d',
		changeYear : true
	});
	
	//'<input class="margin-left-0" type="checkbox" id="checkAll" onclick="checkBox(this)" />'
	
	$("#voucherReversalGrid").jqGrid({   
				url : "AccountVoucherReversal.html?getGridData",
				datatype : "json",
				colNames : [ '',getLocalMessage('account.cheque.cash.transaction'),getLocalMessage('contingent.claim.bill.date'),getLocalMessage('bill.amount'),getLocalMessage('accounts.receipt.narration'),getLocalMessage('accounts.already.reversed'),getLocalMessage('bill.action'),'',''],
				colModel : [ {name : "primaryKey",width : 0,  hidden :  true},
				             {name : "transactionNo",width : 50,search :true,sortable :  true},
				             {name : "transactionDate",width : 50,search :true,align:'center'}, 
				             {name : "transactionAmount",width:50, search:true, sortable:true,align:'right'},
				             {name : "narration",width : 200,search :false}, 
				             {name : "reversedOrNot",width : 50,align:'center',search:false}, 
				             {name: 'enbll', index: 'enbll', width: 50, align: 'center !important',formatter:link,search:false},
				             {name : "id",index:'id',hidden :  true},
				             {name : "viewURL",width : 0,hidden :  true},
				            ],
				pager : "#voucherReversalPager",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "code",
				sortorder : "desc",
				multiselect: true,
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
				caption : getLocalMessage("accounts.receipt.transaction.reversal.form"),
				loadComplete: function () {
						//alert('in loadComplete');
						var rowids = $('#voucherReversalGrid').jqGrid('getDataIDs');
						var count = 0;
						$.each(map, function(key, value){
							if (value == 'Yes') {
								$('#jqg_voucherReversalGrid_'+key).parent().parent().addClass('ui-state-disabled');
								$('#jqg_voucherReversalGrid_'+key).prop('disabled', true);
								$('#jqg_voucherReversalGrid_'+key).prop('checked', true);
								count++;
							}
						});
						removedCount =	count;
				},
				onSelectRow: function(row, isSelected) {
					
					var transactionTypeIdHidden =$('#transactionTypeIdHidden').val();
					if (transactionTypeIdHidden == 'RP') {
						var rows = $("#voucherReversalGrid").jqGrid('getGridParam', 'selarrrow');
						var dataFromTheRow = jQuery('#voucherReversalGrid').jqGrid ('getRowData', row);
						if (isSelected) {
							requestData = {"rowId": row}
							var ajaxResponse = __doAjaxRequest('AccountVoucherReversal.html?checkDepositSlip', 'GET', requestData, false,'json');
							if (ajaxResponse != 'N') {
								//jqg_voucherReversalGrid_368
								$('#jqg_voucherReversalGrid_'+row).attr('checked', false);
								var errorList = [];
								errorList.push(ajaxResponse);
								displayErrorsOnPage(errorList);
								$("html, body").animate({ scrollTop: 0 }, "slow");
							}
						} else {
							 $("#errorDivId").hide();
						}
					} 
				}
			});
	       $("#voucherReversalGrid").jqGrid('navGrid','#voucherReversalPager',{edit:false,add:false,del:false,search:true,refresh:false}); 
	       $("#pagered_left").css("width", "");

	
	//$('#reversalTable').hide();
	 
	       $('#transactionTypeId').change(function(){
	    	   var value = $('option:selected', $("#transactionTypeId")).attr('code');
	    	   $('#transactionTypeIdHidden').val(value);
	    	   var formURL = 'AccountVoucherReversal.html';
	    		__doAjaxRequest(formURL+'?clear', 'POST', {}, false,'json');
	    		 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	    	 // $('#voucherReversalGrid').jqGrid('clearGridData');
	    	  // $('#voucherReversalGrid').jqGrid('GridUnload');
	       });
	/**
	  * being used to search records for reversal
	  */     
	 $('#search').click(function() {
		
		 var errorList = validateTransactionType();
		 if (errorList.length == 0) {
			 var formURL = 'AccountVoucherReversal.html';
			 var requestData = $('#frmReversalSearch').serialize();
			 console.log('requestData'+requestData);
		 	var ajaxResponse = __doAjaxRequest(formURL+'?searchForReversal', 'GET', requestData, false,'json');
		 	if ( ajaxResponse == 'Internal Server Error.') {
		 		// display error page
			 } else if (ajaxResponse =='R') {
				 var errorList = [];
				 errorList.push(getLocalMessage("account.reversal.transaction.number.invalid"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 } 
			 else if (ajaxResponse =='S') {
				 var errorList = [];
				 errorList.push(getLocalMessage("account.reversal.transaction.number.invalid"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 }
			 else if (ajaxResponse =='B') {
				 var errorList = [];
				 errorList.push(getLocalMessage("account.reversal.transaction.number.invalid"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 }
			 else if (ajaxResponse =='P') {
				 var errorList = [];
				 errorList.push(getLocalMessage("account.reversal.transaction.number.invalid"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 }
			 else if (ajaxResponse =='D') {
				 var errorList = [];
				 errorList.push(getLocalMessage("account.reversal.transaction.number.invalid"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 } else if (ajaxResponse =='NA') {
				 var errorList = [];
				 errorList.push(getLocalMessage("account.reversal.transaction.number.invalid"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			 }else if (ajaxResponse =='REV') {
				 var errorList = [];
				 errorList.push(getLocalMessage("receipt.already.reversed"));
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid'); 
			 }else if (ajaxResponse =='DSP') {
				 var errorList = [];
				 errorList.push("Deposite Slip Reversal Is Pending.");
				 displayErrorsOnPage(errorList);
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid'); 
			 }else {
				 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				 $("#errorDivId").hide();
				 $('#actionURL').val(ajaxResponse);
//				 var rowids = $('#voucherReversalGrid').jqGrid('getDataIDs');
//				 alert('rowids='+rowids);
			 }
		 } else {
			 displayErrorsOnPage(errorList); 
		 }

	 });
	 
	 /**
	  * being used to do reversal
	  */ 
	 $('#saveBtn').click(function() {
		  
		 var errors = validateReversalForm();
		 if (errors.length == 0) {
			 var errorList = [];
			 var recordsInGrid = $('#voucherReversalGrid').jqGrid('getDataIDs');
			 if (jQuery.isEmptyObject(recordsInGrid)) {
				 errorList.push("First search record and then select to reverse.");
				 displayErrorsOnPage(errorList);
				 $("html, body").animate({ scrollTop: 0 }, "slow");
			 } else {
				 var checkedRows = jQuery("#voucherReversalGrid").jqGrid('getGridParam','selarrrow');
					$('#checkedId').val(checkedRows);
					var reversedCount=0;
					$.each(recordsInGrid, function(index , recordId) {
						$.each(map, function(key , val) {
							if (recordId == key
									&& val == 'Yes') {
								reversedCount++;
							}
						});
					});
					if (recordsInGrid.length > reversedCount) {
						if (checkedRows.length == 0) {
							errorList.push("Please select record from grid to reverse.");
						}
					} else if (recordsInGrid.length == reversedCount) {
						errorList.push('Item in grid Already reversed!');
					}
					if (errorList.length > 0) {
						 displayErrorsOnPage(errorList);
						 $("html, body").animate({ scrollTop: 0 }, "slow");
					} else  {
						var requestData = $('#frmReversalSearch').serialize();
						var actionURL = $('#actionURL').val();
						//alert('actionURL='+actionURL);
						var ajaxResponse = __doAjaxRequest(actionURL, 'POST', requestData, false,'json');
						var reversalSuccessMessage = getLocalMessage('account.reversal.success.message');
						if (ajaxResponse == 'SUCCESS') {
							displayMessageOnSubmit(reversalSuccessMessage, 'AccountVoucherReversal.html');
						} else {
							var errorList = [];
							errorList.push(ajaxResponse);
							displayErrorsOnPage(errorList);
							$("html, body").animate({ scrollTop: 0 }, "slow");
						}
					}
			 } 
		 } else {
			 displayErrorsOnPage(errors); 
			 $("html, body").animate({ scrollTop: 0 }, "slow");
		 }
		
	 });
	 
});

function validateTransactionType() {
	
	
	var transactionType = $('#transactionTypeId').val();
	var transactionNo = $('#transactionNo').val();
	var dpDeptid = $('#dpDeptid').val();
	var transDate = $('#transDate').val();
	var errorList = [];
	if (dpDeptid == undefined || dpDeptid == null || dpDeptid == "" || dpDeptid == '0') {
		errorList.push('Department must be selected.');
	}
	if (transactionType == undefined || transactionType == '0') {
		errorList.push(getLocalMessage('account.select.transaction.type'));
	}
	if (transactionNo == undefined || transactionNo == null || transactionNo == "" || transactionNo == '0') {
		errorList.push(getLocalMessage('account.select.transaction.no'));
	}
	if (transDate == undefined || transDate == null || transDate == "" || transDate == '0') {
		errorList.push(getLocalMessage('account.select.transaction.date'));
	}
	if(transDate!=null)
		{
		errorList = validatedate(errorList,'transDate');
		}
	return errorList;
}

function validateReversalForm() {
	
	var transactionType = $('#transactionTypeId').val();
	var approvalOrderNo = $('#approvalOrderNo').val();
	var approvedBy = $('#approvedBy').val();
	var narration = $('#narration').val();
	
	var errorList = [];
	if (transactionType == undefined || transactionType == '0') {
		errorList.push(getLocalMessage('account.select.transaction.type'));
		
	} if (transactionType == undefined  || $.trim(approvalOrderNo) == '' ) {
		errorList.push(getLocalMessage('account.select.Approval.Order.no'));
	} if (approvedBy == undefined || approvedBy == '0') {
		errorList.push(getLocalMessage('account.select.approval.authority'));
	} if (narration == undefined  || $.trim(narration) == '' ) {
		errorList.push(getLocalMessage('account.reversal.reason'));
	}
	
	return errorList;
	
}

/*function checkBox(obj) {
	//alert('checkBox');
	//$('.check').attr('checked', true);
   // $('.check').prop('checked', obj.checked);
	alert($('#checkAll').is(":checked"));
	//$("#checkAll").prop('checked', 'checked');
	$('#checkAll').attr('checked', this.checked);
	$(".check").children().prop('checked', 'checked');
	
}
*/
function link(cellvalue, options, rowObject) 
{
	
	map[rowObject.primaryKey] = rowObject.reversedOrNot;
	transactionMap[rowObject.primaryKey] =rowObject.transactionNo; 
   return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewVoucherDetails('"+rowObject.primaryKey+"','"+rowObject.viewURL+"')\"><i class='fa fa-building-o'></i></a> ";
}

/**
 * used to open form in view mode
 * @param gridId
 */
function viewVoucherDetails(gridId, viewURL){
	
	var divName	=	formDivName;
    var requestData = 'gridId='+gridId;
    var returnData = __doAjaxRequest(viewURL, 'POST', requestData, false);
    var divName = '.widget-content';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	return false;
	//$('#frmdivId').html(ajaxResponse);
}

/**
 * $(document).on('click', '.viewClass', function() {
		var $link = $(this);
		var bmId = $link.closest('tr').find('td:eq(0)').text();
		var url;
		if($("#authorizationMode").val()!='Auth'){
			url = "AccountBillEntry.html?formForView";
		}
		else{
			url = "AccountBillAuthorization.html?formForView";
		}
		var requestData = "bmId=" + bmId + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();

	});
 * @returns {Boolean}
 */

/*function searchReceiptData(){
	
	
	$('.error-div').hide();
	var errorList = [];
	if (errorList.length == 0) 
	{
	var rmRcptno=$("#rmRcptno").val();
	var rdamount=$("#rdamount").val();
	var rm_Receivedfrom=$("#rm_Receivedfrom").val();
	var rmDate=$("#rmDatetemp").val();
	

	var url ="AccountVoucherReversal.html?getjqGridsearch";
	var requestData ={ "rmAmount" : rdamount, "rmRcptno" : rmRcptno, "rm_Receivedfrom" : rm_Receivedfrom, "rmDate" : rmDate };  
     
	var response= __doAjaxRequestValidationAccor(obj,formURL+'?saveForm', 'POST', requestData, false, 'json');
	} else {
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
	$('.error-div').html(errMsg);	
	$('.error-div').show();
	 return false;
	}
}*/

/**
 * being used to display validation errors on page 
 * @param errorList : pass array of errors
 * @returns {Boolean} 
 */
function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	
	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	
	$('#errorDivId').html(errMsg);
	$("#errorDivId").show();
	
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}

function searchRecordsForReversal(obj) {
	
}

function displayMessageOnSubmit(successMsg,redirectURL) {
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.proceed.btn');
	//var redirectToURL = ''+redirectURL;
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToHomePage(\''+redirectURL+'\')"/></div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}

function redirectToHomePage (redirectURL) {
	$.fancybox.close();
	window.location.href=redirectURL;
}

function onSearchRecord(obj) {
	 
	 var formURL = 'AccountVoucherReversal.html';
	 var requestData = $('#frmReversalSearch').serialize();
	 console.log('requestData'+requestData);
//	var ajaxResponse = __doAjaxRequest(formURL+'?searchForReversal', 'GET', requestData, false,'json');
	var ajaxResponse= __doAjaxRequestValidationAccor(obj,formURL+'?searchForReversal', 'GET', requestData, false, 'json');
	if ( ajaxResponse == 'Internal Server Error.') {
		// display error page
	 } else if (ajaxResponse =='N') {
		 var errorList = [];
		 errorList.push("No record Found.");
		 displayErrorsOnPage(errorList);
	 } else {
		 $("#voucherReversalGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		 $("#errorDivId").hide();
		 $('#actionURL').val(ajaxResponse);
//		 var rowids = $('#voucherReversalGrid').jqGrid('getDataIDs');
//		 alert('rowids='+rowids);
	 }
	
	//$('#reversalTable').html(ajaxResponse);
	//$('#reversalTable').show();
	/*chosen();
	var prefixNotFound = $.trim($('#noDataFound').val());
	var errorList = [];
	errorList.push(''+prefixNotFound);
	if (prefixNotFound == 'N' || prefixNotFound == '' || prefixNotFound == null || prefixNotFound == undefined) {
		// do nothing
	} else {
		displayErrorsOnPage(errorList);
	}*/

}

