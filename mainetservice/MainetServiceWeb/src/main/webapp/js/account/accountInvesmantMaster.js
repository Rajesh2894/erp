$(function() {
		$("#frmdate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		$("#tocdate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		
		$("#inFdrNo").attr('maxlength', 20);
		$("#remarks").attr('maxlength', 200);

		$(".fromDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".toDateClass").datepicker("option", "minDate", selected)
			}
		});
		$(".toDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".fromDateClass").datepicker("option", "maxDate", selected)

			}
		});
		
	});

$(document).ready(function() {
	var table = $('.myTable').DataTable({
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
	
	var regtable = $('.invregtable').DataTable({
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
	
	 $("#registerId").dataTable({
			"oLanguage" : {
			    "sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
		    });
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	
});
var correct = true;
function createInvestMentMaster(formUrl, actionParam) {
	 
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

var elementTemp;
function saveInvestMentForm(element) {
	elementTemp=element;
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	else
	{
		showConfirmBoxSave();
	//return saveOrUpdateForm(element,getLocalMessage('account.save.investmentMaster.success'),'investmentMaster.html', 'saveform');
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
	return saveOrUpdateForm(elementTemp,getLocalMessage('account.save.investmentMaster.success'),'investmentMaster.html', 'saveform');	
}

function editInvestMentMaster(formUrl, actionParam,invstId,status) {
	var errorList = [];
	if (status == 'C') {
		errorList.push("It is already closed so edit is not allowed !")
		displayErrorsPage(errorList);
	} else {
		var requestdata = {
			"invstId" : invstId
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestdata, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}
function viewInvestMentMaster(formUrl,actionParam,invstId) {
	 
	 var requestdata = {
				"invstId":invstId
				};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchForm(formUrl, actionParam) {
	 
	 var errorList = [];
	 errorList =  validateSearch(errorList);
	 if(errorList.length>0)
		 {displayErrorsPage(errorList);}
	 else{
	 var bankId=$("#bankId").val();
	 var invstAmount=$("#invstAmount").val();
	 var invstNo=$("#invstNo").val();
	 var fundId=$("#fundId").val();
	 
	 var requestdata = {
				"bankId":bankId,
				"invstAmount":invstAmount,
				"invstNo":invstNo,
				"fundId":fundId
				
				};
	 
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	 }
}

//validateSearch
function validateSearch(errorList)
{
	var errorList = [];
	 var bankId=$("#bankId").val();
	 var invstAmount=$("#invstAmount").val();
	 var invstNo=$("#invstNo").val();
	 var fundId=$("#fundId").val();
	  if ((fundId == undefined || fundId == '0' || fundId == "") && (invstAmount == undefined || $.trim(invstAmount) == '' || invstAmount == "") && (invstNo == undefined || $.trim(invstNo) == '' || invstNo == "") && (bankId == undefined || bankId == '0' || bankId == "") ) 
	 	{
			
			  errorList.push(getLocalMessage('please.select.From.Date.as.well'));
		}
	 else if (isNaN(invstAmount))
		 {
		 	errorList.push('Invalid Input for Investment Amount');
		 }
	/* if(isNaN(invstNo))
		 {
		 	errorList.push('Invalid Input for Investment Number');
		 }*/
		  return errorList;
	
}



function resetlistForm() {
	
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'investmentMaster.html');
    $("#postMethodForm").submit();
    $('.error-div').hide();
    prepareTags();
}

function ResetForm(resetBtn){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('investmentMaster.html?createForm', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function backMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'investmentMaster.html');
	$("#postMethodForm").submit();
}

function doOpenInvestPageForLoan(formUrl, actionParam,invstId,invstNo,status,invstAmount,invstDate,fundId,inFdrNo,bankName){
debugger;
	var errorList = [];
	var cpdIdStatusDup="Open";
	var categoryTypeId=$("#categoryTypeId").val();
	var slidate = $("#sliDate").val();
	
	var checkSanctionDate = true;
	errorList = validateReceiptDate(invstDate);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	
	else{
	var requestData = {
			"invstId" : invstId,
			"categoryTypeId" : categoryTypeId, 
			"invstNo" :invstNo,
			"status" : status,
			"invstAmount":invstAmount,
			"fundId":fundId,
			"inFdrNo":inFdrNo,
			"bankName":bankName,
			"MODE" :"CREATE"
				};
	/*if(cpdIdStatusDup!="Close"){
		var returnData = __doAjaxRequest(formUrl + '?' + actionParam , 'post', requestData, false);
		$('.content').html(returnData);	
	}*/
	if(status !== "C")
	{
		var returnData = __doAjaxRequest(formUrl + '?' + actionParam , 'post', requestData, false);
		$('.content').html(returnData);	
	}
	else{
		errorList.push("It is already closed so receipt is not allowed!");
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
	}
	var divName = '.content';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	return false;
	}
}

function validateForm(errorList)
{ 
	var bankId = $("#bankId").val();
	var inFdrNo = $("#inFdrNo").val();
	var invstType= $("#invstType").val();
	var frmdate = $("#frmdate").val();
	var invstAmount = $("#invstAmount").val();
	var remarks  = $("#remarks").val();
	var instAmt  = $("#instAmt").val();
	var maturityAmt = $("#maturityAmt").val();
	var resNo = $("#resNo").val();
	var rsnDate= $("#rsnDate").val();
	var fundId = $("#fundId").val();
	var tocdate = $("#tocdate").val();
	var instRate = $("#instRate").val();
	var slidate = $("#slidate").val();
	var accountNumber = $("#accountNumber").val();
	if ( bankId == "" ||  bankId == null) {
		errorList.push(getLocalMessage('account.select.bank.name'));
	}
	if (  inFdrNo == "" || inFdrNo  == null) {
		errorList.push(getLocalMessage('account.enter.fdr.no'));
	}
	if (invstType  == "0") {
		errorList.push(getLocalMessage('account.select.investmentType'));
	}
	if ( accountNumber != "" && accountNumber.length <8 || accountNumber.length>16) {
		errorList.push("Please Enter Account Number Minimum 8 or Maximum 16 Digit");
	}
	
	/*if(invstType= "Select" )
	{
		errorList.push(getLocalMessage('Please Enter Investment Type'));
	}*/
	if ( frmdate == "" || frmdate  == null) {
		errorList.push(getLocalMessage('account.select.investmentDate'));
	}
	else	
	{
		var dateErrList = validateDateFormat($("#frmdate").val());
		if(correct == false)
			{
				errorList.push(getLocalMessage('Investment Date : '+dateErrList));
			}

	}
	if (invstAmount  == "" ||invstAmount   == null) {
		errorList.push(getLocalMessage('account.enter.investmentAmt'));
	}
	if (remarks   == "" || remarks  == null) {
		errorList.push(getLocalMessage('account.enter.remark'));
	}
	if ( instAmt == "" ||  instAmt == null) {
		errorList.push(getLocalMessage('account.enter.interestAmt'));
	}
	if ( maturityAmt == "" || maturityAmt  == null) {
		errorList.push(getLocalMessage('account.enter.maturityAmt'));
	}
	if ( tocdate == "" || tocdate  == null) {
		errorList.push(getLocalMessage('account.enter.maturityDate'));
	}
	else	
	{
		var dateErrList = validateDateFormat($("#tocdate").val());
		if(correct == false)
			{
				errorList.push(getLocalMessage('Maturity Date : '+dateErrList));
			}
		var compareError = 	compareDate(frmdate,tocdate);
		if(compareError.length > 0)
		{
			errorList.push(getLocalMessage('account.maturityDate.greater.investmentDate'));
		}
	}
	if ( resNo == "" ||  resNo == null) {
		errorList.push(getLocalMessage('account.enter.resolution.no'));
	}
	if (rsnDate  == "" ||  rsnDate == null) {
		errorList.push(getLocalMessage('account.select.resolutionDate'));
	}
	else	
	{
		var dateErrList = validateDateFormat($("#rsnDate").val());
		if(correct == false)
			{
			errorList.push(getLocalMessage('Resolution Date : '+dateErrList));
			}
		var compareError = 	compareDate(rsnDate,frmdate);
		if(compareError.length > 0)
		{
			errorList.push(getLocalMessage('account.investmentDate.greater.resolutionDate'));
		}
	}
	if (fundId == "" || fundId == null) {
		errorList.push(getLocalMessage('account.bill.entry.exp.fundId'));
	}
	if (instRate == "" || instRate == null) {
		errorList.push(getLocalMessage('accounnt.enter.interestRate'));
	}
	if(maturityAmt  >9999999999.99 )
	{
		errorList.push(getLocalMessage('account.maturityAmt.exceed'));
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



function dynamicSum()
{	
	var errorList = [];
	 var instAmt = parseFloat($('#instAmt').val());
     var invstAmount = parseFloat($('#invstAmount').val());
     var      result = instAmt + invstAmount;
  /*   $('#maturityAmt').val(result);
     $('#maturityAmount').val(result);*/
     
   /*  if(instAmt == 0 && invstAmount!=0 )
    	 {
    	 $('#invstAmount').val(invstAmount);
    	 }
     if(instAmt != 0 && invstAmount==0 )
	 {
	 $('#instAmt').val(instAmt);
	 }
     */
     if(result <= 19999999999.98 && result >= 10000000000.00)
    	 {
    	 errorList.push(getLocalMessage('account.maxLength.exceed.maturityAmt'));
    	 displayErrorsPage(errorList);
    	 }
  
    	 $('#maturityAmt').val(result);
         $('#maturityAmount').val(result)
    
     
}

/*function validateDateFormat(dateElementId)
{
	 
	 var errorList = [];
	 var sliDate = $("#sliDate").val(); //SLI Date
	 var dateValue= dateElementId;
	 if(dateValue != null && dateValue != ""){
	 	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 	// Match the date format through regular expression
	 	if(dateValue.match(dateformat))
	 	{
	 	//document.form1.text1.focus();
	 	//Test which seperator is used '/' or '-'
	 	var opera1 = dateValue.split('/');
	 	var temp  = sliDate.split(" ");
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
	 	else if (lopera2>1)
	 	{
	 	var pdate = dateValue.split('-');
	 	}
	 	var dd = parseInt(pdate[0]);
	 	var mm  = parseInt(pdate[1]);
	 	var yy = parseInt(pdate[2]);
	 	
	 	var slimonth = parseInt(sli[1]);
	 	var sliyear  = parseInt(sli[2]);
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
	 	//year value between sli and current year
		if(yy == sliyear)
	 	{
	 		if(mm < slimonth && mm != slimonth)
 			{
 			 errorList.push("Invalid value for month: " + mm + " Transaction Date must be between " +temp1+" and " + (new Date()).getFullYear());
 			}
	 	}
	 	if(yy <sliyear  || yy > (new Date()).getFullYear())
	 	{
	 		 errorList.push("Invalid value for year: " + yy + " Transaction Date must be between " +temp1+" and " + (new Date()).getFullYear());
	 	}
	 	}
	 	else
	 	{
	 	errorList.push('Invalid Date Format');
	 	//alert("Invalid date format!");
	 	//document.form1.text1.focus();
	 	//return false;
	 	}	
	 }
	 displayErrorsPage(errorList);
	 if(errorList.length > 0)
	 { correct = false;}
	 else{correct = true;}
		return errorList;
	
	 
}*/



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
	
	//1 should not be greater than 2
	
	if(yy1 == yy2)
	{
			if(mm1 > mm2)
			{
				errorList.push("month is invalid");
			}
			if(mm1 == mm2)
				{
					if(dd1 > dd2)
						{
						errorList.push("Date is invalid");
						}
				}
	}
	else if(yy1 > yy2)
		{
		errorList.push("Year is invalid");
		}
	if(yy1 == yy2 && mm1 == mm2 && dd1 == dd2)
		{
		errorList.push("Date is invalid");
		}
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
		//Defect #156201
	 	/*if(yy <sliyear )
	 	{
	 		 errorList.push("Receipt Entry Not Allowed");
	 	}*/
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




//Investment Register Code by Rahul S Chaubey
function report(formUrl, actionParam) {
	
	var errorList=[];
	errorList = validateReportSearch(errorList);
	if(errorList.length > 0)
		{
			displayErrorsOnPage(errorList);
		}
	
	else{
	   var invstNo = $('#invstNo').val();
	   var regFromDate = $('#regFromDate').val();
	   var regToDate = $('#regToDate').val(); 
	  var fundId= $('#fundId').val(); 
	  var fundName =$("#fundId>option:selected").text();
	 
	  if(fundName == "Select")
		  {
		  fundName = null;
		  }
	  
		     var data = {
		 			"invstNo" : invstNo,
		 			"fundId" : fundId,
		 			"regFromDate" : regFromDate,
		 			"regToDate" : regToDate,
		 			"fundName": fundName
		 			};
		   
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	  }
	
}


function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'investmentRegister.html');
	$("#postMethodForm").submit();
}


function PrintDiv(title) {
	var divContents = document.getElementById("investmentRegister").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, .tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style"); $(".table .tfoot").addClass("hide");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}	
	

function validateReportSearch(errorList)
{	
	 var invstNo = $('#invstNo').val();
	   var regFromDate = $('#regFromDate').val();
	   var regToDate = $('#regToDate').val(); 
	  var fundId= $('#fundId').val(); 
	
	  if((invstNo ==null ||invstNo ==undefined ||invstNo =="") 
		&& (regFromDate ==null ||regFromDate ==undefined ||regFromDate =="")
		&& (regToDate ==null ||regToDate ==undefined ||regToDate =="")
		&& (fundId ==null ||fundId ==undefined ||fundId ==""))
	  {
		       errorList.push(getLocalMessage('please.select.atleast.one.search.parameter'));
	  }
	  // from and to date
	  else  if( regFromDate !== "")
	  {
		  var formaterror = validateDateFormat($('#regFromDate').val());
		  if(formaterror.length == 0 && regToDate == "")
		  {
			 errorList.push(getLocalMessage('Please.select.To.Date.as.well'));
		  }
		  else if(formaterror.length > 0)
		  {
			  errorList.push(formaterror);
		  }
	  }
	  else if ( regToDate != "" && regFromDate  == "")
	  {
		  var formaterror = validateDateFormat($('#regToDate').val());
		  if(formaterror.length == 0 && regFromDate  == "")
		  {
			 errorList.push(getLocalMessage('please.select.From.Date.as.well'));
		  }
		  else if(formaterror.length>0)
		  {
			  errorList.push(formaterror);
		  }
	  }
	 
			 var formaterror = compareDate(regFromDate,regToDate);
			 if(formaterror.length > 0) 	
			 errorList.push("From Date cannot be greater than To Date");
	 
	  //compare Date
	  return errorList;
}


function loadInvestNumber(obj) {
	var errorList = [];
	var fundId = $("#fundId").val();
	if (fundId == "")
		errorList.push(getLocalMessage('Select Fund'));
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	else{
		var postData = 'fundId=' + fundId;
		var url = "investmentRegister.html?InvestNumber";
		var response1 = __doAjaxRequest(url, 'post', postData, false,"json");
		if (jQuery.isEmptyObject(response1)) {
			errorList.push('No Investment are available against selected Fund');
			displayErrorsPage(errorList);
		}
		else {
			$.each(response1, function(index,value) {
				  $('#invstNo').append($("<option></option>").attr("value",value).text(value));
			});
			$('#invstNo').trigger('chosen:updated');
		}
	}
	
}
