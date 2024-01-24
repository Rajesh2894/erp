$(document).ready(function() {
	var table = $('.grantMaster').DataTable({
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
	
	$("#sactNo").attr('maxlength', 500);
	$("#receivedAmt").attr('maxlength', 14);
	$("#grtName").attr('maxlength', 200);
	$("#grantNature").attr('maxlength', 500);
	$("#santionAmt").attr('maxlength', 14);
	$("#sanctionAuth").attr('maxlength', 500);
	
	var correct = true; 
	
	
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
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
	});
	 
	if($("#id1").val()!=null)
	{
		("id1").hide();
	}
	if($("#grtDate").val()!=null && $("#sliDate").val()!=null){
	 var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	 var grtDate = new Date($("#grtDate").val().replace(pattern,'$3-$2-$1'));
	 var slidate = new Date($("#sliDate").val().replace(pattern,'$3-$2-$1'));
	 if (grtDate < slidate) {
		 $('#Opening').show();
		  }
	 else{
		 $('#Opening').hide();
	 }
	
	}
	else {
		 $('#Opening').hide();
	}
	
});	

function createGrantMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
var elementTemp;
function saveGrantMaster(element) {
	elementTemp=element;
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	else{
		showConfirmBoxSave();
	 //return saveOrUpdateForm(element,'Grant Master saved sucessfully.','grantMaster.html', 'saveform');
	}
}

function showConfirmBoxSave(){

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
	return saveOrUpdateForm(elementTemp,'Grant Master saved sucessfully.','grantMaster.html', 'saveform');		
}

function viewGrantMaster(formUrl, actionParam,grantId) {
	
	var requestdata = {
			"grantId":grantId
			};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function editGrantMaster(formUrl, actionParam,grantId) {
	 
	var requestdata = {
	"grantId":grantId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function ResetForm(resetBtn){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('grantMaster.html?createForm', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function resetGrant() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'grantMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function backGrantMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'grantMaster.html');
	$("#postMethodForm").submit();
}

function searchForm(formUrl,actionParam) {
	
	 var grtType = $("input[name='accountGrantMasterDto.grtType']:checked").val();
	 var grtName = $("#grtName").val();
	 var grtNo = $("#grtNo").val();
	 var fundId = $("#fundId").val();
	 
	 errorList = [];
	// errorList= validateSearch(errorList);
	 if(errorList.length > 0)
		 {
		 displayErrorsPage(errorList);
		 }
	 else{
		 if(grtName==""){
			 grtName=null; 
		 }
	     var requestdata = {
				"grtType":grtType,
				"grtName":grtName,
				"grtNo":grtNo,
				"fundId":fundId
				};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	 }
}

function validateSearch(errorList)
{
	 var grtName = $("#grtName").val();
	 var grtNo = $("#grtNo").val();
	 var fundId = $("#fundId").val();
	 var errorList =[];
	if((grtName == "" || grtName == null) && (grtNo == "" || grtNo == null) && (fundId == "" || fundId == null))
		{
		errorList.push('Please provide at least one search criteria.');
		}
	return errorList;
	
}


function doOpenReceiptPageForGrant(grantId,grtNature,grtNo,sanctionAmt,sanctionDate,grtDate){
	
		var errorList = [];
		var cpdIdStatusDup="Open";
		var categoryTypeId=$("#categoryTypeId").val();
		var requestData = {"grantId" : grantId,"categoryTypeId" : categoryTypeId,"grtNature" :grtNature,"grtNo" : grtNo,"sanctionAmt":sanctionAmt,"MODE" :"CREATE"};
		var formUrl='AccountReceiptEntry.html';
		var actionParam='createformFromGrant';
		var slidate = $("#sliDate").val();
		// As discuss with Samadhan Sir
		errorList = validateReceiptDate(grtDate);
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		
		else
		{
			if(cpdIdStatusDup!="Close"){
				var returnData = __doAjaxRequest(formUrl + '?' + actionParam , 'post', requestData, false);
				$('.content').html(returnData);	
			}
			else{
				errorList.push("It is already Close so Receipt is not allowed!");
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

function doOpenBillPageForGrant(grantId){
	
    var errorList = [];
	var url = "AccountBillEntry.html?refund";
	var categoryType=$("#categoryType").val();
	var requestData = {"categoryType" : categoryType};
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	var statusCodeFlag="";
	if(statusCodeFlag!="DO"){
		$('.content').html(returnData);
	}
	else{
		errorList.push("It is already "+ statusCodeValue +" so Refund is not allowed!");
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
};

function receiptData(formUrl,actionParam){
	
	var rmRcptid=$("#rmRcptid").val();
	var requestData = {"rmRcptid" : rmRcptid};
	var returnData = __doAjaxRequest(formUrl + '?' + actionParam , 'post', requestData, false);
	if (returnData != "") {
		$.each(returnData, function(index, value) {
					$('#rmRcptid').append($("<option></option>").attr("value", value.rmRcptid).text(value.rmRcptid));
		});
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("service.error.noservice"));
		displayErrorsOnPage(errorList);
	}
	$("#rmRcptid").trigger("chosen:updated");
}
//========================================================================================================================================================================
var correct = true; 

function validateForm(errorList)
{	
	var grtName = $("#grtName").val();
	var grantNature = $("#grantNature").val();
	var fromYearId =$("#fromYearId>option:selected").text();
	var fromYearId =  parseFloat(fromYearId);
	var toYearId =$("#toYearId>option:selected").text();
	var toYearId =  parseFloat(toYearId);
	var receivedAmt = $("#receivedAmt").val();
	var sactNo = $("#sactNo").val();
	var grtDate = $("#grtDate").val();
	var santionAmt = $("#santionAmt").val();
	var sanctionAuth = $("#sanctionAuth").val();
	var sanctionDate = $("#sanctionDate").val();
	var fundId = $("#fundId").val();
	var sliDate = $("#sliDate").val();
	if (grtName == "" || grtName == null) {
	
		errorList.push(getLocalMessage('account.grant.name'));
	
	}
	if (grantNature == "" || grantNature == null) {
		errorList.push(getLocalMessage('account.grant.nature'));
		
	}
	if (isNaN(fromYearId)) {
		errorList.push(getLocalMessage('account.grant.fromPerd '));
		
	}
	if (isNaN(toYearId)) {
		errorList.push(getLocalMessage('account.grant.toPerd'));
		
	}

	if (receivedAmt == "" || receivedAmt == null) {
		errorList.push(getLocalMessage('account.grant.amount'));
		
	}
	if (grtDate == "" || grtDate == null) {
		
		errorList.push(getLocalMessage('account.grant.date'));
		
	}
	else	
		{
			var dateErrList = validateDateFormat($("#grtDate").val());
			if(correct == false)
				{
					errorList.push('Grant Date : '+dateErrList);
				}
			//call method
			var result = compareGrantPeriod(grtDate, fromYearId)
			if(result == false)
				{
					errorList.push('Grant Period and Grant Date Did not Match');
				}
		}
	
	if (sactNo == "" || sactNo == null) {
		errorList.push(getLocalMessage('account.grant.sanctionNo'));
		
	}
	if (santionAmt == "" || santionAmt == null) {
		errorList.push(getLocalMessage('account.grant.sanctionAmt'));
		
	}else{
		if(parseFloat(receivedAmt) > parseFloat(santionAmt)){
			errorList.push("Received Amount Should Not Be Greater Than Sanction Amount");
		}
	}
	
	if (sanctionAuth == "" || sanctionAuth == null) {
		errorList.push(getLocalMessage('account.grant.sanctionAut'));
		
	}
	if (sanctionDate == "" || sanctionDate == null) {
		errorList.push(getLocalMessage('account.grant.sanctionDate'));
		
	}
	else	
	{
		var dateErrList = validateDateFormat($("#sanctionDate").val());
		if(correct == false)
			{
			errorList.push('Sanction Date : '+dateErrList);
			}
	}
	if (fundId == "" || fundId == null) {
		errorList.push(getLocalMessage('account.grant.fund '));
		
	}
	  if(fromYearId >toYearId)
	     {
	    	 errorList.push(getLocalMessage('account.grant.fromTo'));
		 
	    	
	     }
	  
	  if(grtDate != "" || grtDate != null && sliDate != "" || sliDate != null)
		  {
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			 var grtDate1 = new Date($("#grtDate").val().replace(pattern,'$3-$2-$1'));
			 var slidate1 = new Date($("#sliDate").val().replace(pattern,'$3-$2-$1'));
			 if (grtDate1 < slidate1) {
				 if($("#openingBalance").val()== null || $("#openingBalance").val()=="")
				 errorList.push("Please Enter Opening Balance Amount");
				  }
			 
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


function dateValueCheck()
{	
	 var errorList = [];
		var fromYearId =$("#fromYearId>option:selected").text();
		var toYearId =$("#toYearId>option:selected").text();
		var grtDate = $("#grtDate").val();
		var fromYearId =  parseFloat(fromYearId);
		var toYearId =  parseFloat(toYearId);
     if(fromYearId >toYearId)
     {
    	 errorList.push('Grant From Period is greater than Grant To Period');
    	 displayErrorsPage(errorList);
     }
     var date = grtDate.split('/');
     var yy = parseInt(date[2]);
}

function validateDateFormat(dateElementId)
{
	 var errorList = [];
	 var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	 var gDates=$("#grtDate").val();
	 var sDates=$("#sliDate").val();
	 var dateValue = dateElementId;
	 if (gDates != undefined && sDates != undefined) {
		var grtDate = new Date($("#grtDate").val().replace(pattern, '$3-$2-$1'));
		var slidate = new Date($("#sliDate").val().replace(pattern, '$3-$2-$1'));
		if (grtDate < slidate) {
			$('#Opening').show();
		} else {
			$('#Opening').hide();
		}
	}
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

function validateReceiptDate(dateElementId)
{
	
	 var errorList = [];
		var slidate = $("#sliDate").val(); //SLI Date
	 var dateValue= dateElementId;
	 if(dateValue != null && dateValue != ""){
	 	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 	var opera = dateValue.split(" ")
	 	var opera1 = opera[0].split('-');
	 	var temp  = slidate.split(" ");
	 	var temp1 = temp[0];
	 	var sli = temp1.split('/');   //Splitting SliDate
	
	 	lopera1 = opera1.length;
	 	lsli = sli.length;
	 	if (lopera1>1)
	 	{
	 	var pdate = dateValue.split('/');
	 	}
	 	var dd = parseInt(opera1[2]);
	 	var mm  = parseInt(opera1[1]);
	 	var yy = parseInt(opera1[0]);
	 	
	 	var slimonth = parseInt(sli[1]);
	 	var sliyear  = parseInt(sli[2]);
	 	var sliday = parseInt(sli[0]);
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
	 }
	 displayErrorsPage(errorList);
		return errorList;
	
	 
}



function compareGrantPeriod(dateElementId, fromYearId)
{
	// get the month
	// if month less than or equal to 3 range of grant from year is yy-1,yy
	//else yy, yy+1
	
	var errorList = [];
	var dateValue1 = dateElementId;
	//split the dates
	var splittedValue = dateValue1.split('/');
	
	var mm = parseInt(splittedValue[1]);
	var yy = parseInt(splittedValue[2]);
	
	var grantFromPeriod
	var grantToPeriod
	
	if(mm <= 3)
	{
		grantFromPeriod = yy-1
		grantToPeriod = yy
	}
	else
	{
		grantFromPeriod = yy
		grantToPeriod = yy+1
		
	}
	
	if(fromYearId!=grantFromPeriod)
	{
		return false;
	}
	else return true;
}



// GRANT REGISTER



function report(formUrl, actionParam) {
	
	   var errorList = [];
	   var faYearId = $("#faYearId").val();
	   var grtName =  $("#grtName").val();
	   var regFromDate = $('#regFromDate').val();
	   var regToDate = $('#regToDate').val(); 
	   var faYearName =$("#faYearId>option:selected").text();
	   var fullGrantName = $("#grtName>option:selected").text();
	   errorList = validateReportSearch(errorList);	 
	  if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
	 else
	 {  
		 if(faYearName == "Select" )
		{
			 faYearName = null;
		}
		  var data = {
		    		 "faYearId"	: faYearId,
		    		 "grtName": grtName,
		    		 "faYearName":faYearName,
		    		 "regFromDate": regFromDate,
		    		 "regToDate":regToDate,
		    		 "fullGrantName":fullGrantName
		 			};
		   
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	 }
		   
	 //  }
	
}


function validateReportSearch(errorList)
{	
	 var faYearId = $("#faYearId").val();
	   var grtName =  $("#grtName").val();
	   var regFromDate = $('#regFromDate').val();
	   var regToDate = $('#regToDate').val(); 
	   
	
	  if((faYearId == null || faYearId =="") && (grtName == null || grtName =="")
			  && (regFromDate ==null ||regFromDate ==undefined ||regFromDate =="")
				&& (regToDate ==null ||regToDate ==undefined ||regToDate =="") )
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
			 
		   errorList.push(getLocalMessage('from.Date.cannot.be.greater.than.To.Date'));
	  return errorList;
}



function back() {
	
	 var id1 = $("#id1").val();
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'grantRegister.html');
	$("#postMethodForm").submit();
}


function reportBack() {

//	 var id1 = $("#id1").val();
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdminHome.html');
	$("#postMethodForm").submit();
}

function ResetReport(resetBtn){

	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	//datepicker
	$(".datepicker").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}

function PrintDiv(title) {
	var divContents = document.getElementById("grantRegister").innerHTML;
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
	 return errorList;
}


function doOpenRefundPageForGrantRefund(grantId, grantType,flag){
	debugger;
	var errorList = [];
	var requestData = {"grantType":grantType,"grantId":grantId};
	var object = __doAjaxRequest(
			"grantMaster.html?checkingGrantRefundsApplicable", 'POST',
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
			var payableAmount = object.payableAmount;
			var url = "AccountBillEntry.html?createFormForGrantUtilization";
			var requestData = {"grantId":grantId, "payableAmount":payableAmount,"flag":flag};
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			$('.content').html(returnData);
			
		}
	
}

function doOpenRefundPageForGrantUtilization(grantId,grantType,flag){
	 
	var errorList = [];
	var requestData = {"grantType":grantType,"grantId":grantId};
	var object = __doAjaxRequest(
			"grantMaster.html?checkingGrantRefundsApplicable", 'POST',
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
			var payableAmount = object.payableAmount;
			var url = "AccountBillEntry.html?createFormForGrantUtilization";
			var requestData = {"grantId":grantId, "payableAmount":payableAmount,"flag":flag};
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			
			$('.content').html(returnData);
			
		}
}