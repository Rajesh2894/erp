$(function(){
	chosen();

	$('.secondUnitRow').removeClass('in');
	$('.secondUnitRow:last').addClass('in');
	
	if($("#top-header").hasClass('hide')){
		$("#top-header").removeClass('hide');
	}
	if($(".header-inner").hasClass('hide')){
		$(".header-inner").removeClass('hide');
	}
	
});


function NextToViewPage(obj){ 	
	var errorList = [];
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {
	
		var theForm	=	'#PropertyTaxCalculator';
		var requestData = {};
		requestData = __serializeForm(theForm);
	
		var URL = 'PropertyTaxCalculator.html?nextToViewPage';		
		var returnData = __doAjaxRequestValidationAccor(obj,URL,'POST',requestData, false,'html');
		if(returnData)
		{			
			$(formDivName).html(returnData);
		}

	}
	else {
		showErrorOnPage(errorList);
	}
}

function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}

function BackToMainPage(obj){

	var data = {};
	var URL = 'PropertyTaxCalculator.html?BackToMainPage';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	$('.orgName').hide();

	reorderFirstRow();
	reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
	reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
	yearLength();
}

function validateFactorStatus(errorList){

	$('.factorQes').each(function(i){
		var level = i+1;
		var selectFactor = $.trim($('input[name="provisionalAssesmentMstDto.proAssfactor['+i+']"]:checked').val());
		if(selectFactor==0 || selectFactor=="")
		errorList.push(getLocalMessage("property.factorStatus")+" "+level);
		});	
	return errorList;
}

function yearLength(){	
	var dateFields = $('.dateClass');
    dateFields.each(function () {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}

function reorderfactor(specFact,eachFact)
{
	$(eachFact).each(function(j) {
		$(specFact).each(function(i) {
			$(this).find("select:eq(0)").attr("id", "unitNoFact"+i).attr('onchange','resetFactorValue(this,'+i+')');
			$(this).find("select:eq(1)").attr("id", "assfFactorValueId"+i).attr('onchange','enabledisable(this,'+i+')');
			$(this).find("input:hidden:eq(0)").attr("name","provAsseFactDtlDto["+i+"].assfFactorId");
			$(this).find("input:hidden:eq(1)").attr("id","factPref"+i);
			$(this).find("input:hidden:eq(1)").attr("name","provAsseFactDtlDto["+i+"].factorValueCode");
			$(this).find("input:hidden:eq(2)").attr("id","proAssfId"+i);
			$(this).find("input:hidden:eq(2)").attr("name","provAsseFactDtlDto["+i+"].proAssfId");
			$(this).find("select:eq(0)").attr("name","provAsseFactDtlDto["+i+"].unitNoFact");
			$(this).find("select:eq(1)").attr("name","provAsseFactDtlDto["+i+"].assfFactorValueId");
			$(this).find("a:eq(0)").attr("onclick", "addUnitRow("+(i)+")");
			$(this).find("a:eq(1)").attr("id", "deleteFactorTableRow_"+i);			
		 });
		$(this).find("select:eq(0)").attr("class","form-control mandColorClass selectUnit factQuestion"+j);

		});
}
function ShowFormDetails(obj){
	$('.ulbList').hide();
	$("#displayDetails").hide();
	var theForm	=	'#PropertyTaxCalculatorPage';
	requestData = __serializeForm(theForm);
	var URL = 'PropertyTaxCalculator.html?ShowFormDetails';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);

	$("#showTaxCalculator").html(returnData);
	
	Data = {};
	var URL1 = 'PropertyTaxCalculator.html?clearSessionData';
	var returnData1 = __doAjaxRequest(URL1, 'POST', Data, false);
	
}

