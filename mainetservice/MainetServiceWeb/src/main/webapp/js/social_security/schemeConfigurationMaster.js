

/*$(document).ready(function() {
debugger;
	beneficiaryStatus( $("#toDate").val());

});*/
/*window.onload = function() {
  init();
  debugger;
  beneficiaryStatus( $("#toDate").val());
};*/

$(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".toDateClass").datepicker("option", "minDate", selected)
			}
		});
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate :'0d',
			onSelect : function(selected) {
				$(".fromDateClass").datepicker("option", "maxDate", selected)

			}
		});
		
	});
function resetlistForm() {
	debugger
    $("#postMethodForm").prop('action', '');
    $("#postMethodForm").prop('action', 'schemeConfigurationMaster.html');
    $("#postMethodForm").submit();
    $('.error-div').hide();
    prepareTags();
}

function backMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'schemeConfigurationMaster.html');
	$("#postMethodForm").submit();
}

function ResetForm(){
	debugger;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('schemeConfigurationMaster.html?C', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}


function addConfigurationMaster(formUrl, actionParam) {
	debugger;
	requestdata = null;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveConfigurationMasterForm(element, saveMode) {
	
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		//$.fancybox.close();
		var divName = '.content-page';

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;

		return saveOrUpdateForm(element, 'Scheme configured successfully',
				'schemeConfigurationMaster.html', 'saveform');
	}
}

//edit&view
function editConfigurationMaster(schemeMstId,mode) {
	//debugger;
	var divName = '.content-page';
	var requestData = {
		"saveMode" : mode,
		"schemeMstId" : schemeMstId
	};
	var ajaxResponse = doAjaxLoading('schemeConfigurationMaster.html?EDIT', requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function searchConfigurationMaster() {debugger;
	var errorList = [];
	var schemeMstId = $("#schemeMstId").val();
	if (schemeMstId == null || schemeMstId == undefined || schemeMstId == "0") {
		//errorList.push(getLocalMessage('Please select Scheme Name.'));
	//	displayErrorsOnPage(errorList);
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var requestData = {
			"schemeMstId" : schemeMstId
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('schemeConfigurationMaster.html?search',
				requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function validateForm(errorList) {
	var errorList = [];
	debugger;
	var beneficiaryCount = $("#beneficiaryCount").val();
	var schemeMstId = $("#schemeMstId").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();

	if (schemeMstId == null || schemeMstId == undefined || schemeMstId == "0") {
		errorList.push(getLocalMessage("social.sec.schemename.req"));
	}
	if (beneficiaryCount == null || beneficiaryCount == undefined
			|| beneficiaryCount == "") {
		errorList.push(getLocalMessage('social.beneficiary.count'));
	}
	if (fromDate == null || fromDate == undefined|| fromDate == "") {
		errorList.push(getLocalMessage('social.demographicReport.valid.fromdate'));
	}
	else
	{
		var dateErrList = validateDateFormat(fromDate);
		if(correct == false)
			{
			errorList.push('From Date : '+dateErrList);
			}
	}
	if (toDate==""||toDate == null || toDate == undefined) {
		errorList.push(getLocalMessage('social.demographicReport.valid.todate'));
	}
	else
	{
		var dateErrList = validateDateFormat(toDate );
		if(correct == false)
			{
			errorList.push('To Date : '+dateErrList);
			}
	}
	//compare date
	if((toDate!=false|| toDate!=""||toDate != null || toDate != undefined) && (fromDate != null || fromDate != undefined|| fromDate != ""))
	{
		var formaterror = compareDate1(fromDate,toDate);
		 if(formaterror.length > 0) 	
		 errorList.push("From Date cannot be greater than To Date");
	}
	
	//social.sec.invalid.date.format
	return errorList;
}

var correct =true;
function validateDateFormat(dateElementId)
{
	debugger;
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
	 
	 displayErrorsOnPage(errorList);
	  if(errorList.length > 0)
	 { correct = false;}
	 else{correct = true;}
		return errorList;
	
	 
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}


function compareDate1(date1,date2)
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

function dateEnable()
 {
	debugger;
	var toDate = $("#toDate").val();

	var newDate = new Date();

	var dd = newDate.getDate();
	var mm = newDate.getMonth() + 1;
	var yy = newDate.getFullYear();
	var todate = dd + "/" + mm + "/" + yy;

	var dateError = compareDate1(toDate, todate);
	if (dateError.length > 0) {
		$("#beneficiaryCount").prop("disabled", false);
	} else {
		$("#beneficiaryCount").prop("disabled", true);
	}

	/*
	 * date1 = date1.split(' ')[0];//remove time
	 * 
	 * let today = moment().format('DD/MM/YYYY');
	 * 
	 * if ((compareDate(date1)) < compareDate(today)) {
	 * $("#beneficiaryCount").prop("disabled", true); } else {
	 * $("#beneficiaryCount").prop("disabled", false); }
	 */
}
