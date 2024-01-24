$(document).ready(function(){
   
	$('#parentPropNo, #amalgmatedPropNo').keyup(function () { 

		$("#parentOldPropNo").prop("disabled", true);
		$("#amalgmatedOldPropNo").prop("disabled", true);

	});
	
	$('#parentOldPropNo, #amalgmatedOldPropNo').keyup(function () { 
		
		$("#parentPropNo").prop("disabled", true);
		$("#amalgmatedPropNo").prop("disabled", true); 

	});

	var minYear= $("#dateOfEffect").val();
	//datepicker function for year of acquisition
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: minYear,
		maxDate: '-0d',
		yearRange: "-100:-0",
	});
	var assType = $("#assType").val();
	if('AML' == assType){
		$("#proceed").show();
	}else{
		$("#proceed").hide();		
	}
$("#back").hide();
prepareDateTag();

/*var locId=$("#locId").val();

if(locId!=null && locId!='' && locId!=undefined && locId>0){
	var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),$("#locId").val());
	$("#wardZone").html(result);
}*/

});


function SearchButton(element) {
	
	var errorList = [];
	errorList = ValidateInfo(errorList);	
	if (errorList.length == 0) {
		var data = $('#AmalgamationForm').serialize();
		var URL = 'AmalgamationForm.html?searchProperty';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
		prepareDateTag();
		if('N' == $("#successMessage").val()){
			$("#searchDetails").show();
		}else{
			$("#searchDetails").hide();			
		}
	}
	else {
		displayError(errorList);
	}
}

function ValidateInfo(errorList){

	var parentProNo = $("#parentPropNo").val();
	var amalgmatedPropNo =$("#amalgmatedPropNo").val();
	var parentOldPropNo=$("#parentOldPropNo").val();
	var amalgmatedOldPropNo=$("#amalgmatedOldPropNo").val();
	
	if((parentProNo == "" && amalgmatedPropNo=="") && (parentOldPropNo == "" && amalgmatedOldPropNo == "")){
		errorList.push(getLocalMessage("property.searchValidation"));
	}
	else if((parentProNo!= "" && amalgmatedPropNo=="") || (parentProNo == "" && amalgmatedPropNo!=""))
	{
		errorList.push(getLocalMessage("property.searchprpvalid"));
	}
	else if((parentOldPropNo != "" && amalgmatedOldPropNo=="") || (parentOldPropNo == "" && amalgmatedOldPropNo!=""))
	{
	errorList.push(getLocalMessage("property.searcholdpropvalid"));
	}
	return errorList;
}
function resetDetails(){
	var data={};
	var URL='AmalgamationForm.html?backToMainPage';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}

function editDetails(obj){
	
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var formAction	=	$(theForm).attr('action');	
	
	var data = {};
	var URL = formAction+'?editAmalgamationForm';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	
	prepareDateTag();
	var landTypePrefix = $("#assLandType" + " option:selected").attr("code");
	
	var ownerType = $("#ownerTypeCode").val();	
	var data1 = {"ownershipType" : ownerType};
	var URL1 = formAction+'?getOwnershipTypeInfoDiv';
	var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	
	$("#owner").html(returnData1);
	$("#owner").show();
	
	$("#proceed").hide();
	
	if(landTypePrefix !=undefined && landTypePrefix!=""){

		var landData = {"landType" : landTypePrefix};
		var landURL = 'AmalgamationForm.html?getLandTypeDetails';
		var returnLandData = __doAjaxRequest(landURL, 'POST', landData, false);
		$("#landType").html(returnLandData);
		var khasara=$('#displayKhaNo').val();
		var plot=$("#tppPlotNo").val();
		$("#landType").show();	
		}
		
		if(landTypePrefix=='KPK'){
		var districtId=$('#assDistrict').val();
		var TehsilId=$('#assTahasil').val();
		var villageId=$('#tppVillageMauja').val();
		$('#khasraNo').val(khasara);
		
		var khasraData = {"landType" : landTypePrefix,"khasara":khasara,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId};
		if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined && $('#displayKhaNo').val()!=""){				

		var khasraURL = 'AmalgamationForm.html?getKhasaraDetails';
		var returnkhasraData = __doAjaxRequest(khasraURL, 'POST', khasraData, false);		
		$("#showApiDetails").html(returnkhasraData);
		$("#showApiDetails").show();
		}
		}
		else if(landTypePrefix=='NZL' || landTypePrefix=='DIV' ){
			//var landType=$("#assLandType" + " option:selected").attr("code");
			var districtId=$('#assDistrict').val();
			var TehsilId=$('#assTahasil').val();
			var villageId=$('#tppVillageMauja').val();
			var mohallaId=$('#mohalla').val();
			var streetNo=$('#assStreetNo').val();	
			//var plotNo=$('#tppPlotNo').val();
			$('#plotNo').val(plot);	
			var data = {"landType" : landTypePrefix,"plotNo" : plot,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"mohallaId" : mohallaId,"streetNo" : streetNo};
			if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined && $('#mohalla').val()!=undefined && $('#assStreetNo').val()!=undefined){				

			var URL = 'AmalgamationForm.html?getNajoolAndDiversionDetails';
			var returnData = __doAjaxRequest(URL, 'POST', data, false);		
			$("#showApiDetails").html(returnData);
			$("#showApiDetails").show();
		}
		}
	
}

function getOwnerTypeInfo(obj) {

var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var formAction	=	$(theForm).attr('action');	

	
	var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
	if(ownerType!=undefined){
		$("#ownerTypeCode").val(ownerType);
	var data = {"ownershipType" : ownerType};
	var URL = formAction+'?getOwnershipTypeInfoDiv';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$("#owner").html(returnData);
	$("#owner").show();
	if($("#assType").val()=='AML'){
	$("#proceed").hide();
	$("#back").hide();
	$("#checkList").show();
	$("#checkListDiv").hide();
	}
	}
	else{
		$("#owner").html("");
	}
}

function backToMainPage(obj){

	var data={};
	var URL='AmalgamationForm.html?backToMainPage';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}

function getAmalgCheckList(element){

	var formName	=	findClosestElementId(element, 'form');
	var theForm	=	'#'+formName;
	var formAction	=	$(theForm).attr('action');	
	var errorList = [];
	errorList = ValidateFormDetails(errorList);
	if (errorList.length == 0) {
	var requestData = __serializeForm(theForm);

	var URL = formAction+'?getCheckList';
	
	var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
	if(returnData)
	{
		$(formDivName).html(returnData);
		var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
		var data1 = {"ownershipType" : ownerType};
		var URL1 = formAction+'?getOwnershipTypeInfoDiv';
		var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
		
		$("#owner").html(returnData1);
		$("#owner").show();
		$("#proceed").show();
		$("#back").show();
		$("#backToAmalgSearch").hide();
		$("#checkList").hide();
	}
	}
	else {
		displayError(errorList);
	}

}

function saveData(element){

	var formName	=	findClosestElementId(element, 'form');
	
	var theForm	=	'#'+formName;
	
	var formAction	=	$(theForm).attr('action');	
	
	var errorList = [];
	errorList = ValidateFormDetails(errorList);
	if (errorList.length == 0) {
	var requestData = __serializeForm(theForm);

	var URL = formAction+'?saveProceed';
	
	var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
	if(returnData)
	{
		var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
		$(formDivName).html(returnData);
	
		var data1 = {"ownershipType" : ownerType};
		var URL1 = formAction+'?getOwnershipTypeInfoDiv';
		var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
		
		$("#owner").html(returnData1);
		$("#owner").show();
		$("#proceed").show();
		$("#back").show();
		$("#checkList").hide();
		$("#backToAmalgSearch").hide();
//		var divName = '.content';
//		$(divName).html(returnData);
//		prepareDateTag();
	}
	}
	else {
		displayError(errorList);
	}

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
	var dateOfEffect= $("#dateOfEffect").val();

	if(totalPlot=='' || totalPlot == undefined || (totalPlot<=0)){			 
		 errorList.push(getLocalMessage("property.enterTotalPlotArea")); 
	 }
	if(buildup=='' || buildup == undefined || (buildup<=0)){			 
		 errorList.push(getLocalMessage("property.enterBuildUpArea")); 
	 }
	if(buildup > totalPlot){	
		errorList.push(getLocalMessage("property.totalPloatArea"));
	}
	if(dateOfEffect=='' || dateOfEffect == undefined ){			 
		 errorList.push(getLocalMessage("property.dateOfEffectValid")); 
	}

	return errorList;
}


function editAmalgamationViewForm(obj){
	var landTypePrefix=$(".landValue").val();
	var khasara=$('#displayEnteredKhaNo').val();
	var plot=$("#tppPlotNo").val();
	
	var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var formAction	=	$(theForm).attr('action');	
	

	var ownerType = $("#ownerId").val();
	var data = {};
	var URL = formAction+'?editAmalgamationUpdateDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	prepareDateTag();
	var data1 = {"ownershipType" : ownerType};
	var URL1 = formAction+'?getOwnershipTypeInfoDiv';
	var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	
	$("#owner").html(returnData1);
	$("#owner").show();
	$("#proceed").show();
	$("#back").show();
	$("#backToAmalgSearch").hide();
	$("#checkList").hide();
	
	if(landTypePrefix !=undefined && landTypePrefix!=""){

		var landData = {"landType" : landTypePrefix};
		var landURL = 'AmalgamationForm.html?getLandTypeDetails';
		var returnLandData = __doAjaxRequest(landURL, 'POST', landData, false);
		$("#landType").html(returnLandData);
		$("#landType").show();	
		}
		
		if(landTypePrefix=='KPK'){
		var districtId=$('#assDistrict').val();
		var TehsilId=$('#assTahasil').val();
		var villageId=$('#tppVillageMauja').val();
		$('#khasraNo').val(khasara);
		
		var khasraData = {"landType" : landTypePrefix,"khasara":khasara,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId};
		if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined && $('#displayKhaNo').val()!=""){				

		var khasraURL = 'AmalgamationForm.html?getKhasaraDetails';
		var returnkhasraData = __doAjaxRequest(khasraURL, 'POST', khasraData, false);		
		$("#showApiDetails").html(returnkhasraData);
		$("#showApiDetails").show();
		}
		}
		else if(landTypePrefix=='NZL' || landTypePrefix=='DIV' ){
			//var landType=$("#assLandType" + " option:selected").attr("code");
			var districtId=$('#assDistrict').val();
			var TehsilId=$('#assTahasil').val();
			var villageId=$('#tppVillageMauja').val();
			var mohallaId=$('#mohalla').val();
			var streetNo=$('#assStreetNo').val();	
			//var plotNo=$('#tppPlotNo').val();
			$('#plotNo').val(plot);	
			var data = {"landType" : landTypePrefix,"plotNo" : plot,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"mohallaId" : mohallaId,"streetNo" : streetNo};
			if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined && $('#mohalla').val()!=undefined && $('#assStreetNo').val()!=undefined){				

			var URL = 'AmalgamationForm.html?getNajoolAndDiversionDetails';
			var returnData = __doAjaxRequest(URL, 'POST', data, false);		
			$("#showApiDetails").html(returnData);
			$("#showApiDetails").show();
		}
		}
}

function saveForm(element){
	 return saveOrUpdateForm(element,"Amalgamation done successfully!", 'AdminHome.html', 'saveform');
}


function prepareDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}


function downloadSheet(obj){
	
var formName	=	findClosestElementId(obj, 'form');
	
	var theForm	=	'#'+formName;
	
	var formAction	=	$(theForm).attr('action');	
	
	window.location.href=formAction+'?downloadSheet';

}

function getLandApiDetails(obj){
	var landTypePrefix=$(".landValue").val();
	var data = {};
	var URL = 'AmalgamationForm.html?getLandTypeApiDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showAuthApiDetails").html(returnData);
	$("#showAuthApiDetails").show();	

	
}
