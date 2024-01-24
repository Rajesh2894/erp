$(document).ready(function(){
	
	yearLength();
	/*var locId=$("#locId").val();
	if(locId!=null && locId!='' && locId!=undefined && locId>0){
		var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),$("#locId").val());
		$("#wardZone").html(result);
	}*/
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

function proceed(element){
	var errorList = [];
	reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table
	errorList = ValidateDetails(errorList);	
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length == 0) {
			
		var theForm	=	'#frmChangeAssessmentForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ChangeInAssessmentForm.html?proceed';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			
			//var divName = '.content';
			/*var formDivName='.content-page';
			$(formDivName).removeClass('ajaxloader');
			$(formDivName).html(returnData);*/
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
	$(formDivName).html(returnData);
	reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table
	//reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table

	reorderfactor('.specFact','.eachFact'); // reordering Id and Path for unit specific Additional Info Table
	//occupancyTypeChange1();   
	yearLength();

	
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