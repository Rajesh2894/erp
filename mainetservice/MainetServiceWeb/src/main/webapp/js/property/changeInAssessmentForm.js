$(document).ready(function() {
	var selfAss = $("#selfAss").val();
	if('C' == selfAss){
		$("#changeProceed").show();
		$("#backToSearch").show();
	} else {
		$("#changeProceed").hide();
		$("#backToSearch").hide();
	}
		$("#backToSearch").on("click", function(){ 
			  window.location.reload("#propertyAssessmentType");
			});
		
		var locId=$("#locId").val();
		if(locId!=null && locId!='' && locId!=undefined && locId>0){
			var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),$("#locId").val());
			$("#wardZone").html(result);
		}
	yearLength();
	
	$('#checkDetail').prop('checked', true);
	//occupancyTypeChange1();
});


function yearLength(){	
	var dateFields = $('.dateClass');
    dateFields.each(function () {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}

/*function occupancyTypeChange1(){
	$('.secondUnitRow').each(function(j){
		if(($('option:selected', $("#assdOccupancyType"+j)).attr('code')) == 'TAN'){
			$("#proAssdAnnualRent"+j).prop('disabled', false);;
			$("#proAssdAnnualRent"+j).prop('required',true);
			
		}else{
			$("#proAssdAnnualRent"+j).val('');
			$("#proAssdAnnualRent"+j).prop('disabled', true);;
			$("#proAssdAnnualRent"+j).prop('required',false);
		}
	});
}*/
function proceed(element){
	var errorList = [];
	errorList = ValidateDetails(errorList);	
	errorList = validateUnitDetailTable(errorList);					
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table

	if (errorList.length == 0) {
			
		var theForm	=	'#frmChangeAssessmentForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ChangeInAssessmentForm.html?proceed';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			$(formDivName).html(returnData);
			reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table
			//reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			reorderfactor('.specFact','.eachFact'); // reordering Id and Path for unit specific Additional Info Table
			//occupancyTypeChange1();   
			
			$("#changeProceed").show();
			$("#backToSearch").show();
			$("#checkList").hide();			
			$("#backToSearchPage").hide();
		}
		yearLength();
		}	
	else {
		displayErrorsOnPage(errorList);
	}
}

function fetchCheckList(element){
	var errorList = [];
	errorList = ValidateDetails(errorList);	
	errorList = validateUnitDetailTable(errorList);					
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table

	if (errorList.length == 0) {
			
		var theForm	=	'#frmChangeAssessmentForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ChangeInAssessmentForm.html?getCheckList';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			$(formDivName).html(returnData);
			reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table
			//reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			reorderfactor('.specFact','.eachFact'); // reordering Id and Path for unit specific Additional Info Table
			//occupancyTypeChange1();   
			$("#changeProceed").show();
			$("#backToSearch").show();
			$("#checkList").hide();
			$("#backToSearchPage").hide();
		}
		yearLength();
		}	
	else {
		displayErrorsOnPage(errorList);
	}
}


function ValidateDetails(errorList){
var noOfDaysEditableFlag = $("#noOfDaysEditableFlag").val();
if(noOfDaysEditableFlag != '' && noOfDaysEditableFlag == 'Y'){
	if($("#checkDetail").prop('checked')==false){
		  errorList.push(getLocalMessage('prop.toProceed.check.unitDetails')); 
	}
}
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


function editChangeAssForm(obj){

	//var ownerType = $("#ownershipId").val();
	var data = {};
	var URL = 'ChangeInAssessmentForm.html?editChangeAssForm';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	
	// For Assessment Type
	if(($('option:selected', $("#selectedAssType")).attr('code')) == 'C'){
	$(".content").html(returnData);
	}	
	$(formDivName).html(returnData);
	
	reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table
	//reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table

	reorderfactor('.specFact','.eachFact'); // reordering Id and Path for unit specific Additional Info Table
	//occupancyTypeChange1();   
	yearLength();
	$("#changeProceed").show();
	$("#backToSearch").show();
	$("#checkList").hide();
	$("#backToSearchPage").hide();
	
}

// fetch API details on selection of button 
function getLandApiDetails(obj){
	var landTypePrefix=$(".landValue").val();
	var data = {};
	var URL = 'ChangeInAssessmentForm.html?getLandTypeApiDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showAuthApiDetails").html(returnData);
	$("#showAuthApiDetails").show();		
}

function loadOccupierName(element){
	
	 var occupancyTypeId = $(element).attr('id');
	//D#151376 if we add 11th floor then occupancyType Id become "assdOccupancyType10" and below line gives index as '0'
	 //var index = occupancyTypeId.charAt(occupancyTypeId.length - 1);
	 var index = occupancyTypeId.replace(/\D/g, "");
	 
	var value = null;
	if((index != 'e') && (index > '0' || index == '0')){
		value = $("#assdOccupancyType" + index).find("option:selected").attr('code');
	}else{
		value = $("#assdOccupancyType").find("option:selected").attr('code');
	}
	
	var ownerTypeCode = $("#ownerTypeId").find("option:selected").attr('code');
	
	if(value == 'OWN'){
		var ownerName = null;
		if(ownerTypeCode == 'JO'){
			ownerName = $("#assoOwnerName_0").val();
		}else{
			ownerName = $("#assoOwnerName").val();
		}
		
		
		if((index != 'e') && (index > '0' || index == '0')){
			$("#occupierName" + index).val(ownerName);
		}else{
			$("#occupierName").val(ownerName);
		}
	}else{
		if((index != 'e') && (index > '0' || index == '0')){
			$("#occupierName" + index).val('');
		}else{
			$("#occupierName").val('');
		}
		
	}
}

function getOwnerTypeDetails() {

	var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
	if (ownerType != undefined) {
		var data = {
			"ownershipType" : ownerType
		};
		var URL = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		clearOccupancyDetails();
		$("#owner").html(returnData);
		$("#owner").show();
		$("#proceed").hide();
		$("#checkList").show();
		$("#checkListDiv").hide();
	} else {
		$("#owner").html("");
	}
}

function clearOccupancyDetails() {

	var rowCount = $('#unitDetailTable tr').length;
	if (rowCount > 3) {
		for (var i = 0; i < rowCount; i++) {
			$("#occupierName" + i).val('');
			$('#assdOccupancyType' + i).val('0').trigger('chosen:updated');
		}
	} else {
		$("#occupierName").val('');
		$('#assdOccupancyType').val('0').trigger('chosen:updated');
	}

}