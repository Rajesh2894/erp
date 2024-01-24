$(document).ready(function() {
	var table = $('.loanMaster').DataTable({
		"oLanguage" : {
		"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bPaginate" : true,
		"bFilter" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});
	
	 $('#repayTable').validate({
			onkeyup : function(element) {
			    this.element(element);
			    console.log('onkeyup fired');
			},
			onfocusout : function(element) {
			    this.element(element);
			    console.log('onfocusout fired');
			}
		    });
	 
	 
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
		
	});
/*	$("#lnPurpose").attr('maxlength', 100);
	$("#loanName").attr('maxlength', 50);
	
	
	$("#resNo").attr('maxlength', 12);
	
	
	
	$("#instSeqno").attr('maxlength', 12);*/
	$("#loanName").attr('maxlength', 50);
	$("#loanPeriod").attr('maxlength', 12);
	$("#SanctionNo").attr('maxlength', 20);
	$("#noOfInstallments").attr('maxlength', 12);
	$("#lmRemark").attr('maxlength', 100);
	$("#lnPurpose").attr('maxlength', 100);
	

});

var prnpalAmount;
var intAmount;
var balIntAmt;
var repaymentAmount;
var correct = true;
var sum = 0;
function addLoanMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

var elementTemp;
function saveLoanForm(element) {
	elementTemp=element;
	var errorList = [];
	errorList = validateForm(errorList);
	errorList = validateAreaDetails(errorList);
	//errorList =sumValidation(errorList);
	var ans = sumValidation();
	if(parseInt(ans) >  parseInt($("#santionAmount").val()))
	{
		errorList.push("Sum of Principal Amount should be less than or equal to Loan Amount")
	}
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	
	else
	{
	
      showConfirmBoxSave();
	  /*var flag =  saveOrUpdateForm(element,
			getLocalMessage('account.save.loanMaster.success'),
		'loanmaster.html', 'saveform');*/
	}
	
}

function showConfirmBoxSave(){
	debugger;
	var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
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
	var flag =  saveOrUpdateForm(elementTemp,
			getLocalMessage('account.save.loanMaster.success'),
		'loanmaster.html', 'saveform');
}

function editLoanMaster(formUrl, actionParam, loanId) {

	var requestdata = {
				"loanId" : loanId
			};

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		
		
}
function viewLoanMaster(formUrl, actionParam, loanId) {
	
	var requestdata = {
		"loanId" : loanId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


//validateSearch
function validateSearch(errorList)
{
	var errorList = [];
	var deptId = $('#deptId').val();
	var lnPurpose = $('#loanDesc').val();
	var loanCode = $('#loanCode').val();
	 if ((deptId == undefined || deptId == '0' || deptId == "") && (lnPurpose == undefined || lnPurpose == null || lnPurpose == "") 
			 && (loanCode == undefined || loanCode == null || loanCode == "")) 
	 	{
			errorList.push(getLocalMessage('account.searchBtn.validation'));
		}
		  return errorList;
	
}



function searchForm(formUrl, actionParam) {

	var deptId = $('#deptId').val();
	var lnPurpose = $('#lnPurpose').val();
	var loanCode = $('#loanCode').val();
	var errorList = [];
	errorList = validateSearch(errorList);
	if(errorList.length > 0)
		{
			displayErrorsPage(errorList);
		}
	
	else
	{
		if (deptId == "") {
			deptId = null;
		}
		if (lnPurpose == "") {
			lnPurpose = null;
		}
		if (loanCode == "Select") {
			loanCode = null;
		}
		var requestdata = {
			"deptId" : deptId,
			"lnPurpose" : lnPurpose,
			"loanCode":loanCode
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
				'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function ResetForm(resetBtn) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('loanmaster.html?craeteForm', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function resetlistForm() {
	
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'loanmaster.html');
    $("#postMethodForm").submit();
    $('.error-div').hide();
    prepareTags();
}


function backLoanMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'loanmaster.html');
	$("#postMethodForm").submit();
}

function doOpenReceiptPageForLoan(formUrl, actionParam, loanId,lmRemark,lnPurpose,santionAmount,sanctionDate) {
	
	
	var errorList = [];
	var cpdIdStatusDup = "Open";
	var categoryTypeId = $("#categoryTypeId").val();
	var slidate = $("#sliDate").val();
	var checkSanctionDate = true;
	errorList = validateReceiptDate(sanctionDate);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	else
		{
		var requestData = {
					"loanId" : loanId,
					"categoryTypeId" : categoryTypeId,
					"loanremarks" : lmRemark,
					"loanPurpose" : lnPurpose,
					"santionamount" : santionAmount,
					"MODE" : "CREATE"
				};
				if (cpdIdStatusDup != "Close") {
					var returnData = __doAjaxRequest(formUrl + '?' + actionParam, 'post',
							requestData, false);
					$('.content').html(returnData);
				} else {
					errorList.push("It is already Close so Receipt is not allowed!");
					if (errorList.length > 0) {
						var errorMsg = '<ul>';
						$
								.each(
										errorList,
										function(index) {
											errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
													+ errorList[index] + '</li>';
										});
						errorMsg += '</ul>';
						$('#errorId').html(errorMsg);
						$('#errorDivId').show();
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');
					}
				}
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				return false;
		}

		}
	
	



function doOpenBillPageForLoan(formUrl, actionParam, categoryTypeId,loanId) {
  
	var errorList = [];
	var cpdIdStatusDup = "Open";
	var categoryTypeId = $("#categoryTypeId").val();
	
	var requestData = {
		
		"categoryTypeId" : categoryTypeId,
		"loanId" : loanId,
		"MODE" : "CREATE"
	};
	if (cpdIdStatusDup != "Close") {
		var returnData = __doAjaxRequest(formUrl + '?' + actionParam, 'post',
				requestData, false);
		$('.content').html(returnData);
	} else {
		errorList.push("It is already Close so Receipt is not allowed!");
		if (errorList.length > 0) {
			var errorMsg = '<ul>';
			$
					.each(
							errorList,
							function(index) {
								errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
										+ errorList[index] + '</li>';
							});
			errorMsg += '</ul>';
			$('#errorId').html(errorMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		}
	}
	var divName = '.content';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	return false;
}


//=========================================
function validateForm(errorList)
{	
	var loanName = $("#loanName").val();
	var  deId = $("#deId").val();
	var santionAmount = $("#santionAmount").val();
	var lnPurpose = $("#lnPurpose").val();
	var vendeId = $("#vendeId").val();
	var loanPeriod = $("#loanPeriod").val();
	var SanctionNo = $("#SanctionNo").val();
	var sanctionDate = $("#sanctionDate").val();
	var noOfInstallments = $("#noOfInstallments").val();
	var lnInrate = $("#lnInrate").val();
	var resNo = $("#resNo").val();
	var rsnDate = $("#rsnDate").val();
	var lmRemark = $("#lmRemark").val();
	
	var instDueDate = $("#instDueDate").val();
	var prnpalAmount = $("#prnpalAmount").val();
	var intAmount = $("#intAmount").val();
	var balIntAmt = $("#balIntAmt").val();
	var periodUnit = $("#unit").val();
	var loanCode =  $("#loanCode").val();
	  var instAmt =  $("#instAmt").val();
	if (loanName == "" || loanName == null) {
		errorList.push(getLocalMessage('account.enter.loanName'));
	}
	if ( deId == "" ||  deId == null) {
		errorList.push(getLocalMessage('receivable.demand.entry.validation.deptName'));
	}
	if (lnPurpose == "" || lnPurpose == null) {
		errorList.push(getLocalMessage('account.enter.loanPurpose'));
	}
	if (santionAmount == "" || santionAmount == null) {
		errorList.push(getLocalMessage('account.enter.loanAmt'));
	}
	if (SanctionNo == "" || SanctionNo == null) {
		errorList.push(getLocalMessage('account.enter.sanctionNo'));
	}
	 
	if (vendeId == "" || vendeId == null) {
		errorList.push(getLocalMessage('account.select.received.from'));
	}
	
	if(loanPeriod == null || loanPeriod =="")
	{
		errorList.push(getLocalMessage('account.enter.loanPeriod'));
	}
	if(periodUnit == null || periodUnit =="" || periodUnit =="Unit")
	{
		errorList.push(getLocalMessage('account.enter.loanPeriod.unit'));
	}
/*	else if(Integer.isNumber(loanPeriod))
	{
		errorList.push(getLocalMessage('Loan Period :Decimal Value Not Allowed'));
	}*/
	if (sanctionDate == "" || sanctionDate == null) {
		errorList.push(getLocalMessage('account.select.loanSanctionDate'));
	}
	else	
	{
		var dateErrList = validateDateFormat($("#sanctionDate").val());
		if(correct == false)
			{
			errorList.push(getLocalMessage('Sanction Date : '+dateErrList));
			}
	}
	if (noOfInstallments == "" || noOfInstallments == null) {
		errorList.push(getLocalMessage('account.enter.no.of.installments'));
	}
	if (lnInrate == "" || lnInrate == null) {
		errorList.push(getLocalMessage('accounnt.enter.interestRate'));
	}
	
	if (resNo == "" || resNo == null) {
		errorList.push(getLocalMessage('account.enter.resolution.no'));
	}
	if (rsnDate == null || rsnDate == "") {
		errorList.push(getLocalMessage('account.select.resolutionDate'));
	}	else	
	{
		var dateErrList = validateDateFormat($("#rsnDate").val());
		if(correct == false)
			{
			errorList.push(getLocalMessage('Resolution Date : '+ dateErrList));
			}
		
		
	}
	if (lmRemark == "" || lmRemark == null) {
		errorList.push(getLocalMessage('account.enter.loanRemark'));
	}
	if (instAmt == "" || instAmt == null) {
		errorList.push(getLocalMessage('account.enter.installmentAmt'));
	}
 
	if(parseFloat( $("#instAmt").val()) > parseFloat( $("#santionAmount").val()))
	{
		errorList.push(getLocalMessage('account.installmentAmt.less.loanAmt'));
	}
	
	if(balIntAmt  >9999999999.99 )
	{
		errorList.push(getLocalMessage('account.repayment.totalAmt.exceed'));
	}
	if(santionAmount >9999999999.99)
    {
    	 errorList.push(getLocalMessage('account.loanAmt.length.exceed'));
    	 displayErrorsPage(errorList);
    }
 if(lnInrate >9999999999.99)
    {
    	 errorList.push(getLocalMessage('account.interestRate.length.exceed'));
    	 displayErrorsPage(errorList);
    }
	
 return errorList;
}



function displayErrorsPage(errorList) {
	
	
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
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


function rangeValidation()
{	
		
		var errorList = [];
	     var santionAmount = parseFloat($("#santionAmount").val()); //12,2
	     var lnInrate =parseFloat($("#lnInrate").val()); //16,2
	     
	    
	     if(santionAmount >9999999999.99)
		    {
		    	 errorList.push(getLocalMessage('account.loanAmt.length.exceed'));
		    	 displayErrorsPage(errorList);
		    }
	     if(lnInrate >9999999999.99)
		    {
		    	 errorList.push(getLocalMessage('account.interestRate.length.exceed'));
		    	 displayErrorsPage(errorList);
		    }
	   
}



function validateDateFormat(dateElementId)
{
	 var errorList = [];
	
	 var dateValue= dateElementId;
	 if(dateValue != null && dateValue != ""){
	 	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 	if(dateValue.match(dateformat))
	 	{
	 	var opera1 = dateValue.split('/');
	 	lopera1 = opera1.length;
	 	if (lopera1>1)
	 	{
	 	var pdate = dateValue.split('/');
	 	}
	 	var dd = parseInt(pdate[0]);
	 	var mm  = parseInt(pdate[1]);
	 	var yy = parseInt(pdate[2]);
	 	var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	 	if (mm==1 || mm>2)
	 	{
	 	if (dd>ListofDays[mm-1])
	 	{
	 	errorList.push('Invalid Date Format');
	 	}
	 	}
	 	if (mm==2)
	 	{
	 	var lyear = false;
	 	if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
	 	{
	 	lyear = true;
	 	}
	 	if ((lyear==false) && (dd>=29))
	 	{
	 	errorList.push('Invalid Date Format');
	 	}
	 	if ((lyear==true) && (dd>29))
	 	{
	 	errorList.push('Invalid Date Format');
	 	}
	 	}
	 	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	 	var sDate = new Date(dateValue.replace(pattern,'$3-$2-$1'));
	 	if (sDate > new Date()) {
	 	}
	 	}
	 	else
	 	{
	 	errorList.push('Invalid Date Format');
	 	}	
	 }
	 
	 displayErrorsPage(errorList);
	  if(errorList.length > 0)
	 { correct = false;}
	 else{correct = true;}
		return errorList;
	
	 
}



function addRow() {

    var errorList = [];
    $("#errorDiv").hide();
  
    if (errorList.length == 0) {
	 
	addTableRow('repayTable',false);
	//$('#repayTable').DataTable();
    } else {
 
	displayErrorsOnPage(errorList);
    }
}

function deleteRow(obj, ids) {
    deleteTableRow('repayTable', obj, ids);
    $('#repayTable').DataTable().destroy();
    triggerTable();
}


function dynamicSum(element)
{ 
				
				prnpalAmount = $(element).attr('id');
				intAmount = $(element).attr('id');
			
			//	var index = $('#repayTable tr').length;
				var index = prnpalAmount.match(/(\d+)/);
				var sum = 0;
				
				var prnpalAmt =  parseFloat($('#prnpalAmount'+index).val());
				sum = prnpalAmt;
				
				var intAmt =  parseFloat($('#intAmount'+ index).val());
				sum = prnpalAmt+ intAmt;
				
				$(this).find(".amt").attr("id", "repaymentAmount" + index).val(sum);
				$(this).find(".amt").attr("id", "balIntAmt" + index).val(sum);
				
				var balanceIntAmount =parseFloat( $('#balIntAmt'+index).val()); //NaN
				balanceIntAmount=sum;  // sum
				
				var repaymentAmount =parseFloat( $('#repaymentAmount'+index).val()); //NaN
				repaymentAmount=sum;
				$('#balIntAmt'+index).val(balanceIntAmount);
				$('#repaymentAmount'+index).val(repaymentAmount);
				
				 
				console.log('balanceIntAmount'+index+$('#balIntAmt'+index).val());
				console.log('variable balanceIntAmount'+index+" = "+balanceIntAmount);
				
}


function validateAreaDetails(errorList) {

	    var i = 0;
	    
	    if ($.fn.DataTable.isDataTable('#repayTable')) {
		$('#repayTable').DataTable().destroy();
	    }
	    var sanctionDate = $("#sanctionDate").val();

	    $("#repayTable tbody tr").each(
		    function(i) {
		    	
		    	var instDueDate = $("#instDueDate" + i).val();
		    	var prnpalAmount = $("#prnpalAmount" + i).val();
		    	var intAmount = $("#intAmount" + i).val();
		    	var balIntAmt = parseFloat($("#balIntAmt" + i).val());
		    	
		    	var rowCount = i + 1;

			if (instDueDate == "" || instDueDate == undefined  || instDueDate == "") {
				 errorList.push(getLocalMessage("account.enter.repaymentDate.row")
						    + rowCount );
			}
			else if(instDueDate != "" || instDueDate!= undefined  || instDueDate!= "")
			{
				
				var dateErrList = validateDateFormat($("#instDueDate" + i).val());
				dateErrList =  compareDate($("#sanctionDate").val(), $("#instDueDate" + i).val())
				if(dateErrList.length > 0)
					{
						errorList.push(getLocalMessage('Repayment Date: '+ instDueDate+' Cannot Be Less Than Sanction Date: '+ $("#sanctionDate").val()+' in row '+rowCount));
					}
			}
			if (prnpalAmount == "" || prnpalAmount == null||prnpalAmount == undefined) {
			    errorList.push(getLocalMessage("account.enter.principalAmt.row")
				    + rowCount );
			}
			/*else{
					sumValidation(rowCount);
			}*/
		/*	else
			{
				console.log('Before: '+sum);
					sum = parseInt(sum) + parseInt(prnpalAmount);
				console.log('After: '+sum);
			}*/

			if (intAmount == "" || intAmount == null||intAmount == undefined) {
				 errorList.push(getLocalMessage("account.enter.interestAmt.row")
						    + rowCount );
			}
			if (balIntAmt == "" || balIntAmt == null || balIntAmt == undefined) {
				 errorList.push(getLocalMessage("account.enter.blnc.interestAmt.row")
						    + rowCount );
			}

		    });
	    return errorList;

	}

function validateReceiptDate(dateElementId)
{
	 
	 var errorList = [];
		var slidate = $("#sliDate").val(); //SLI Date
	 var dateValue= dateElementId;
	 if(dateValue != null && dateValue != ""){
	 	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 	// Match the date format through regular expression
	 /*	if(dateValue.match(dateformat))
	 	{*/
	 	//document.form1.text1.focus();
	 	//Test which seperator is used '/' or '-'
	 	var opera = dateValue.split(" ")
	 	var opera1 = opera[0].split('-');
	 	var temp  = slidate.split(" ");
	 	var temp1 = temp[0];
	 	var sli = temp1.split('/');   //Splitting SliDate
	
	 	//var opera2 = dateValue.split('-');
	 	lopera1 = opera1.length;
	 	lsli = sli.length;
	 	//lopera2 = opera2.length;
	 	// Extract the string into month, date and year
	 	if (lopera1>1)
	 	{
	 	var pdate = dateValue.split('/');
	 	}
	 	/*else if (lopera2>1)
	 	{
	 	var pdate = dateValue.split('-');
	 	}*/
	 	var dd = parseInt(opera1[2]);
	 	var mm  = parseInt(opera1[1]);
	 	var yy = parseInt(opera1[0]);
	 	
	 	var slimonth = parseInt(sli[1]);
	 	var sliyear  = parseInt(sli[2]);
	 	var sliday = parseInt(sli[0]);
	 	// Create list of days of a month [assume there is no leap year by default]
	 	var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	 	if (mm==1 || mm>2)
	 	{
	 	if (dd>ListofDays[mm-1])
	 	{
	 	errorList.push('Invalid Date Format');
	 	//alert('Invalid date format!');
	 	//return false;
	 	}
	 	}
	 	if (mm==2)
	 	{
	 	var lyear = false;
	 	if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
	 	{
	 	lyear = true;
	 	}
	 	if ((lyear==false) && (dd>=29))
	 	{
	 	errorList.push('Invalid Date Format');
	 	//alert('Invalid date format!');
	 	//return false;
	 	}
	 	if ((lyear==true) && (dd>29))
	 	{
	 	errorList.push('Invalid Date Format');
	 	//alert('Invalid date format!');
	 	//return false;
	 	}
	 	}
	 	//date value less than or equal today day in date wise
	 	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	 	var sDate = new Date(dateValue.replace(pattern,'$3-$2-$1'));
	 	if (sDate > new Date()) {
	 		//errorList.push('Invalid date format!' + ' max date is :' + ' current date.');		
	                         //alert('Invalid date format!');
	 		//return false;  
	 	}
	 	//year value between 1902 and 2018
		if(yy == sliyear)
	 	{
	 		if(mm < slimonth && mm != slimonth)
 			{
 			 errorList.push("Receipt Entry Not Allowed");
 			}
	 	}
	 	if(yy <sliyear )
	 	{
	 		 errorList.push("Receipt Entry Not Allowed");
	 	}
	 	//}
	 	/*else
	 	{
	 	//alert("Invalid date format!");
	 	//document.form1.text1.focus();
	 	//return false;
	 	}	*/
	 }
	 displayErrorsPage(errorList);
	 /*if(errorList.length > 0)
	 { correct = false;}
	 else{correct = true;}*/
		return errorList;
	
	 
}

function compareDate(date1,date2)
{

	var errorList = [];
	var dateValue1 = date1;
	var dateValue2 = date2;
	//split the dates
	var split1 = dateValue1.split('/');
	var split2 = dateValue2.split('/');
	
	var dd1 = parseInt(split1[0]);
	var mm1 = parseInt(split1[1]);
	var yy1 = parseInt(split1[2]);
	
	var dd2 = parseInt(split2[0]);
	var mm2 = parseInt(split2[1]);
	var yy2 = parseInt(split2[2]);
	//comapre the dates directly
	
	//1 is sanction
	
	if(yy1 == yy2)
	{
			if(mm1 > mm2)
			{
				errorList.push("Installment Date cannot Be Less Than Sanction Date");
			}
			if(mm1 == mm2)
				{
					if(dd1 > dd2)
						{
						errorList.push("Installment Date cannot Be Less Than Sanction Date");
						}
				}
	}
	else if(yy1 > yy2)
		{
		errorList.push("Installment Date cannot Be Less Than Sanction Date");
		}
	 displayErrorsPage(errorList);
	return errorList;
}



function sumValidation() {
	
	var i ;
	sum = 0;
    if ($.fn.DataTable.isDataTable('#repayTable')) {
	$('#repayTable').DataTable().destroy();
    }
    $("#repayTable tbody tr").each(
	    function(i) {
	    	var prnpalAmount = parseFloat($("#prnpalAmount"+i ).val());
	    	var santionAmount =parseFloat( $("#santionAmount"+i).val());
	    	
	    	var rowCount = i + 1;
	    	sum = sum + prnpalAmount;
	    });
    return sum;
}
	    //id="receiptAccountHeadsTable"


function doOpenBillRefundPageForLoan(loanId,loanDetId, loanPayAmount){
	var errorList = [];
	var requestData = {"loanRepaymentId":loanDetId};
	var object = __doAjaxRequest(
			"loanmaster.html?checkLoanBillDone", 'POST',
			requestData, false, 'json');

		if (object.error != null && object.error != 0) {
		    $.each(object.error, function(key, value) {
			$.each(value, function(key, value) {
			    if (value != null && value != '') {
				errorList.push(value);
			    }
			});
		    });
		    displayErrorsOnPage(errorList);
		} else {
			var url = "AccountBillEntry.html?createFormForLoan";
			var requestData = {"loanId":loanId,"loanRepaymentId":loanDetId,"loanPayAmount":loanPayAmount};
			var returnData = __doAjaxRequest(url, 'POST', requestData, false);
			$('.content').html(returnData);
		}
	
	
	
	
	
			
		
}