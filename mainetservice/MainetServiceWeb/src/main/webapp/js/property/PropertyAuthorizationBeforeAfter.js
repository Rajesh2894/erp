$(function(){

	$('.secondUnitRowAfter').removeClass('in');
	$('.secondUnitRowAfter:last').addClass('in');
	
	$('.secondUnitRowBefore').removeClass('in');
	$('.secondUnitRowBefore:last').addClass('in');
	
	var locId=$("#AfterlocId").val();
	if(locId!=null && locId!='' && locId!=undefined && locId>0){
		var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),locId);
		$("#wardZone").html(result);
	}
	
	var blocId=$("#BeforelocId").val();
	if(blocId!=null && blocId!='' && blocId!=undefined && locId>0){
		var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),blocId);
		$("#beforewardZone").html(result);
	}
	
}); 

function getAfterAuthLandApiDetails(obj){
	
	var landTypePrefix=$(".changePrefixValue").val();
	var data = {};
	var URL = 'SelfAssessmentAuthorization.html?getAfterAuthLandApiDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showAuthApiDetails").html(returnData);
	$("#showAuthApiDetails").show();	

	
}

function getBeforAuthLandApiDetails(obj){
	
	var landTypePrefix=$(".landValue").val();
	var data = {};
	var URL = 'SelfAssessmentAuthorization.html?getBeforeAuthLandTypeApiDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showBeforeAuthApiDetails").html(returnData);
	$("#showBeforeAuthApiDetails").show();	
	
}

function backToMainPage(obj){
	
	var ownerType = $("#ownershipId").val();
	var landTypePrefix=$(".changePrefixValue").val();
	var khasara=$('#displayEnteredKhaNo').val();
	var plot=$("#displayPlotNo").val();
	var data = {};
	var URL = 'SelfAssessmentForm.html?editSelfAssForm';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	reorderFirstRow();
	reOrderChangeUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
	reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
	
//	occupancyTypeChange1();   
	yearLength();
	
	reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table

	var data1 = {"ownershipType" : ownerType};
	var URL1 = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
	var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	
	$("#owner").html(returnData1);
	$("#owner").show();
	$("#proceed").show();
	$("#checkList").hide();
	$("#ResetSelfAssForm").hide();
	$('#checkDetail').prop('checked', true);	
	
	if(landTypePrefix !=undefined && landTypePrefix!=""){

	var landData = {"landType" : landTypePrefix};
	var landURL = 'SelfAssessmentForm.html?getLandTypeDetails';
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

	var khasraURL = 'SelfAssessmentForm.html?getKhasaraDetails';
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

		var URL = 'SelfAssessmentForm.html?getNajoolAndDiversionDetails';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);		
		$("#showApiDetails").html(returnData);
		$("#showApiDetails").show();
	}
	}
	
}