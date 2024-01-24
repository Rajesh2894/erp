$(function(){
	var minYear= $("#proAssAcqDate").val();
	//datepicker function for year of acquisition
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: minYear,
		maxDate: '-0d',
		yearRange: "-100:-0",
	});


prepareDateTag();

if('N' == $("#successMessage").val()){
	$("#proceedBifu").show();
}else{
	$("#proceedBifu").hide();
}
	
});

function prepareDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}

function SearchButton(element) {
	var errorList = [];
	errorList = ValidateInfo(errorList);	
	if (errorList.length == 0) {
		var data = $('#BifurcationFormSearch').serialize();
		var URL = 'BifurcationForm.html?searchProperty';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
		reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');

		if($("#ownerTypeId").val()>0){
			var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
			var data1 = {"ownershipType" : ownerType};
			var URL1 = 'BifurcationForm.html?getOwnershipTypeInfoDiv';
			var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
			$("#owner").html(returnData1);
			$("#owner").show();
			}
		
	}
	else {
		displayError(errorList);
	}
}

function ValidateInfo(errorList){

	var parentProNo = $("#assNo").val();
	var parentOldPropNo=$("#assOldpropno").val();
	if((parentProNo === "" && parentOldPropNo==="")){
		errorList.push(getLocalMessage("prop.enter.valid.propertyNo.oldpropertyNo"));
	}
	return errorList;
}


function proceed(element){
	var errorList = [];
	errorList = ValidateDetails(errorList);	
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {
			
		var theForm	=	'#BifurcationFormSearch';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'BifurcationForm.html?proceed';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			
			reorderFirstRow();
			reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
			reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			//occupancyTypeChange1();   
			yearLength();
			reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
			//var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
			var ownerType = $("#ownershipId").val();

			$(formDivName).html(returnData);
			var data1 = {"ownershipType" : ownerType};
			var URL1 = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
			var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
			
			$("#owner").html(returnData1);
			$("#owner").show();
			$('#checkDetail').prop('checked', true);
			$("#proceedBifu").show();
			$("#checkListBifu").hide();		
		}
		yearLength();
		}	
	else {
		displayErrorsOnPage(errorList);
	}
}

//$("#smAppliChargeFlag").prop('checked') == false
$(function() {
	if ($('#assdBifurcateNo').prop('checked' == true)) {
		var a = $("#assdBifurcateNo").val("Y");
	} else if ($('#assdBifurcateNo').prop('checked' == false)) {
		var b=$("#assdBifurcateNo").val("N");
	}
});
function getBuferCheckList(element){
	var errorList = [];
	errorList = ValidateDetails(errorList);	
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {
		var theForm	=	'#BifurcationFormSearch';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'BifurcationForm.html?getCheckList';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			$(formDivName).html(returnData);
			reorderFirstRow();
			reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
			reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			
			//occupancyTypeChange1();   
			yearLength();
			
			reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
			var ownerType = $("#ownershipId").val();

			//var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
			var data1 = {"ownershipType" : ownerType};
			var URL1 = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
			var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
			
			$("#owner").html(returnData1);
			$("#owner").show();
			$('#checkDetail').prop('checked', true);
			$("#proceedBifu").show();
			$("#checkListBifu").hide();
		}
		yearLength();
		}	
	else {
		displayErrorsOnPage(errorList);
	}
}

function compareBeforeAfterAuthBifurcation(element){
	var errorList = [];
	errorList = ValidateDetails(errorList);	
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {
			
		var theForm	=	'#BifurcationFormSearch';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'BifurcationForm.html?compareBeforeAfterAuth';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			$(".content").html(returnData);
		}
		yearLength();
		}	
	else {
		displayErrorsOnPage(errorList);
	}
}


function ValidateDetails(errorList){

	var totalPlot =parseFloat($("#totalplot").val());
	var buildup =parseFloat($("#buildup").val());

	if(totalPlot=='' || totalPlot == undefined || (totalPlot<=0)){			 
		 errorList.push(getLocalMessage("property.enterTotalPlotArea")); 
	}
	if(buildup=='' || buildup == undefined || (buildup<=0)){			 
		 errorList.push(getLocalMessage("property.enterBuildUpArea")); 
	}
	if(buildup > totalPlot){	
		errorList.push(getLocalMessage("property.totalPloatArea"));
	}
	return errorList;
}


function editDetails(obj){
	
var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var formAction	=	$(theForm).attr('action');	
	
	var data = {};
	var URL = formAction+'?editBifurcationUpdateDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}

function displayError(errorList){
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

function ValidateFormDetails(errorList){

	var totalPlot =parseFloat($("#totalplot").val());
	var buildup =parseFloat($("#buildup").val());

	if(totalPlot=='' || totalPlot == undefined || (totalPlot<=0)){			 
		 errorList.push(getLocalMessage("property.enterTotalPlotArea")); 
	 }
	if(buildup=='' || buildup == undefined || (buildup<=0)){			 
		 errorList.push(getLocalMessage("property.enterBuildUpArea")); 
	 }
	if(buildup > totalPlot){	
		errorList.push(getLocalMessage("property.totalPloatArea"));
	}

	return errorList;
}


function saveForm(element){
	 return saveOrUpdateForm(element,"Bifurcation done successfully!", 'AdminHome.html', 'saveform');
}



//reorder ID unit details fields on page load
function reorderUnitDetailsTable(classNameFirst,classNamesecond){
	
	$(classNameFirst).each(function(i) {

	$(this).find("select:eq(0)").attr("id", "year"+i);
//	$(this).find("select:eq(1)").attr("id", "assdUnitTypeId"+i);
	$(this).find("select:eq(1)").attr("id", "assdFloorNo"+i);
	$(this).find("select:eq(2)").attr("id", "assdConstruType"+i);
	
            var utp=i;
			if(i>0){
			utp=i*6;
			}
			$(this).find("select:eq(3)").attr("id", "assdUsagetype"+utp);
			$(this).find("select:eq(4)").attr("id", "assdUsagetype"+(utp+1));
			$(this).find("select:eq(5)").attr("id", "assdUsagetype"+(utp+2));
			$(this).find("select:eq(6)").attr("id", "assdUsagetype"+(utp+3));
			$(this).find("select:eq(7)").attr("id", "assdUsagetype"+(utp+4));

	});
	
	$(classNamesecond).each(function(j){
//		$(this).find("select:eq(0)").attr("id", "assdRoadFactor"+j);
		$(this).find("select:eq(0)").attr("id", "assdOccupancyType"+j);	
//		$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(j)+")");		
	});
}

function reorderFirstRow(){
//	$(".firstUnitRow").find("select:eq(1)").attr("id", "assdUnitTypeId");
	$(".firstUnitRow").find("select:eq(1)").attr("id", "assdFloorNo");
	$(".firstUnitRow").find("select:eq(2)").attr("id", "assdConstruType");
	$(".firstUnitRow").find("select:eq(3)").attr("id", "assdUsagetype1");
	$(".firstUnitRow").find("select:eq(4)").attr("id", "assdUsagetype2");
	$(".firstUnitRow").find("select:eq(5)").attr("id", "assdUsagetype3");
	$(".firstUnitRow").find("select:eq(6)").attr("id", "assdUsagetype4");
	$(".firstUnitRow").find("select:eq(7)").attr("id", "assdUsagetype5");
//	$(".secondUnitRow ").find("select:eq(0)").attr("id", "assdRoadFactor");
	$(".secondUnitRow ").find("select:eq(0)").attr("id", "assdOccupancyType");
}

//fetch API details on selection of button 
function getLandApiDetails(obj){
	var landTypePrefix=$(".landValue").val();
	var data = {};
	var URL = 'ChangeInAssessmentForm.html?getLandTypeApiDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showAuthApiDetails").html(returnData);
	$("#showAuthApiDetails").show();		
}



function editSelfAssBifurcationForm(obj){
	var ownerType = $("#ownershipId").val();
	var data = {};
	var URL = 'BifurcationForm.html?editBifurcationForm';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	reorderFirstRow();
	reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
	reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
	yearLength();
	reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
	$("#checkListBifu").hide();
	$("#proceedBifu").show();
	var data1 = {"ownershipType" : ownerType};
	var URL1 = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
	var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	$("#owner").html(returnData1);
	$("#owner").show();
	$('#checkDetail').prop('checked', true);
	
}