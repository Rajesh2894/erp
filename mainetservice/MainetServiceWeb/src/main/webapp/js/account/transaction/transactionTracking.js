$(function() {
	$("#transactionTrackingGrid").jqGrid(
			{
				url : "TransactionTracking.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('classified.abstact.acc.code'), getLocalMessage('account.cheque.dishonour.account.head'),getLocalMessage('opening.balance.report.opeingbalance'),getLocalMessage('accounts.dr.amount'),getLocalMessage('accounts.cr.amount'),getLocalMessage('bank.master.closeBal'),getLocalMessage('bill.action')],
				colModel : [ {name : "budgetCodeId",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "accountCode",width : 55,sortable : true, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "accountHead",width : 55,sortable : true, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "openingBalance",width : 55,sortable : true, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "debitAmount",width : 65,sortable : false, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : "creditAmount",width : 65,sortable : false, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : "closingBalance",width : 65,sortable : true, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : '', index: '', width:30 , align: 'center !important',formatter:addLink,search :false},
				            ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "",
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
				caption : "Transaction Details"
			});
	 jQuery("#transactionTrackingGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClassMnth' title='View'value='"+rowdata.budgetCodeId+"' id='"+rowdata.budgetCodeId+"' ><i class='fa fa-building-o'></i></a>";
}

$(function() {
	
	$('#monthWiseDiv').hide();
	$("#monthWiseGrid").jqGrid(
			{
				url : "TransactionTracking.html?getMonthWiseGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '', "Month", "Opening Balance", "Dr. Amount", "Cr. Amount", "Closing Balance", "Action"],
				colModel : [ {name : "budgetCodeId",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }, hidden:true},
				             {name : "Month",width : 55,sortable : true, align : 'center', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "openingBalance",width : 55,sortable : true, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, 
				             {name : "debitAmount",width : 65,sortable : false, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : "creditAmount",width : 65,sortable : false, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : "closingBalance",width : 65,sortable : true, align : 'right', searchoptions: { "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }},
				             {name : '', index: '', width:30 , align: 'center !important',formatter:addLink,search :false},
				            ],
				pager : "#pageredMnth",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "",
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
				caption : "Transaction Details"
			});
	 jQuery("#monthWiseGrid").jqGrid('navGrid','#pageredMnth',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
});



function searchTransactionDetails(){
	
	var errorList = [];
	errorList = validateForm(errorList);
	displayErrorsPage(errorList);
	if(errorList <= 0){
		var url = "TransactionTracking.html?searchTransactionData";
		var returnData = {
			"faYearid" : $("#faYearid").val(),
		};
		var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
		 if(ajaxResponse != false){
		    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
		    	if (hiddenVal == 'Y') {
		    		var errorList =[];
		    		errorList.push('No record found!');
		    		displayErrorsOnPage(errorList);
		    	} else {
		    		$(formDivName).html(ajaxResponse);
		    	}
		    }

		
		
		
		
		
		/*if (result != null && result != '') {
			$("#transactionTrackingGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			$('#errorDivId').hide();
		}else {
			var errorsList = [];
			errorsList.push(getLocalMessage("No records found for selected criteria"));
			if (errorList.length > 0) {
				displayErrorsPage(errorsList);
			}
			$("#transactionTrackingGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}*/
	}
}

$(document).on('click', '.viewClassMnth', function() {
	var $link = $(this);
	var id = $link.closest('tr').find('td:eq(0)').text();
	var url;
		url = "TransactionTracking.html?getMonthWiseData";
	var requestData = "budgetCodeId=" + id;
	var result = __doAjaxRequest(url, 'post', requestData, false);
	$('#monthWiseDiv').show();
	
	if (result != null && result != '') {
		$("#monthWiseGrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
		$('#errorDivId').hide();
	}else {
		var errorsList = [];
		errorsList.push(getLocalMessage("account.norecord.criteria"));
		if (errorList.length > 0) {
			displayErrorsPage(errorsList);
		}
		$("#monthWiseGrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}
	
	//$('.content').html(returnData);
	prepareDateTag();

});








function validateForm(errorList){
	
	var faYearid =  $("#faYearid").val();
	if(faYearid=="" || faYearid == null || faYearid == "0"){
		errorList.push(getLocalMessage('account.select.financialYear'));
	}
	
	var budgetCodeId =  $("#budgetCodeId").val();
		if(budgetCodeId==""){
			errorList.push(getLocalMessage('account.select.acchead'));
		}
		return errorList;
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
		return false
	}
	
}




function findHeadValue(obj,openingDr,openingCr)
{
	
	
	var url = "TransactionTracking.html?findHeadWiseTransactions";
	var returnData = {
		"accountHead" : obj,"faYearid" : $("#faYearid").val(),"openingDr":openingDr,"openingCr" :openingCr,
	};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$(formDivName).html(ajaxResponse);
	    	}
	    }
	
	
}

function findMonthWise(obj1,obj2) {
	
	var from=obj1;
	var to=obj2;
	var transactionDrId=$("#transactionDrId").val();
	var transactionCrId=$("#transactionCrId").val();
	var url = "TransactionTracking.html?findDayWiseTransactions";
	var returnData = {
		"accountHead" : $("#accountHead").val(),"faYearid" : $("#faYearid").val(), "fromDate" : from, "toDate" : to,"openDr": $("#openDr").val(),"openCr": $("#openCr").val(), 
	};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#successfulFlag').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$(formDivName).html(ajaxResponse);
	    	}
	    }
	
	
	
}

function findVoucherWise(obj1) {
	
	var from=obj1;
	var voucherDate=$("#fromDate").val();
	var url = "TransactionTracking.html?findVoucherWiseTransactions";
	var returnData = {
		"accountHead" : $("#accountHead").val(), "fromDate" : from, "toDate": $("#toDate").val(), "faYearid" : $("#faYearid").val(),"openDr": $("#openDr").val(),"openCr": $("#openCr").val(), 
		"voucherDate":voucherDate,};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$(formDivName).html(ajaxResponse);
	    	}
	    }
	
	

}




function findShowVoucher(obj1) {
	
	var vouId=obj1;
	var url = "AccountVoucherEntry.html?transactionFormForView";
	var returnData = {
		"vouId":vouId,"accountHead" : $("#accountHead").val(),"voucherDate" : $("#voucherDate").val(),"fromDate" : $("#fromDate").val(), "toDate": $("#toDate").val(), "faYearid" : $("#faYearid").val(),"openDr": $("#openDr").val(),"openCr": $("#openCr").val(), 
	};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	var formDivName = '.content';
	$(formDivName).removeClass('ajaxloader');
	$(formDivName).html(ajaxResponse);
	return false;
	
	
}
function findHeadValueBack() {
	
	
	
	var url = "TransactionTracking.html?findHeadWiseTransactions";
	var returnData = {
		"accountHead" : $("#accountHead").val(),"faYearid" : $("#faYearid").val(),"openingDr":$("#openDr").val(),"openingCr" :$("#openCr").val(),
	};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$(formDivName).html(ajaxResponse);
	    	}
	    }
	
	
}
function findMonthWiseBack()
{
	
	var url = "TransactionTracking.html?findDayWiseTransactions";
	var returnData = {
		"accountHead" : $("#accountHead").val(),"faYearid" : $("#faYearid").val(), "fromDate" : $("#fromDate").val(), "toDate" : $("#toDate").val(),"openDr": $("#openDr").val(),"openCr": $("#openCr").val(),
	};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$(formDivName).html(ajaxResponse);
	    	}
	    }






}

function findVoucherWiseBack() {
	
	var url = "TransactionTracking.html?findVoucherWiseTransactions";
	var returnData = {
		"accountHead" : $("#accountHead").val(), "fromDate" : $("#voucherDates").val(), "toDate": $("#toDate").val(), "faYearid" : $("#faYearId").val(),"openDr": $("#openDr").val(),"openCr": $("#openCr").val(), 
		"voucherDate":$("#fromDate").val(),};
	var ajaxResponse = __doAjaxRequest(url, 'POST', returnData, false);
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$(formDivName).html(ajaxResponse);
	    	}
	    }
	
	
}








