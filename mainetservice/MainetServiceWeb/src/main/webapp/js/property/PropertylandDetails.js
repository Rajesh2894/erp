$(document).ready(function(){	
	$('.khaNoNote').hide();
	$('.plotNoNote').hide();
});


function toGetKhasraNoList(){
	$("#tppPlotNoCs").empty();
	$('#displayKhaNo').empty();
	var errorList = [];
	$('.khaNoNote').show();
	$("#showApiDetails").html('');
	var enteredVal=$("#khasraNo").val();
	$('#enteredVal').val(enteredVal);
	var data = {"villageId":$('#tppVillageMauja option:selected').attr('value'),
			"tehsilId":$('#assTahasil option:selected').attr('value'),
			"districtId":$('#assDistrict option:selected').attr('value'),
			"landType":$("#assLandType option:selected").attr("code"),"khasara":enteredVal};
	if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined){				

	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
			var URL = 'DataEntrySuite.html?getKhasraNoList';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') || ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
		var URL = 'SelfAssessmentForm.html?getKhasraNoList';
	}	
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getKhasraNoList';
	}	
			var returnData = __doAjaxRequest(URL, 'POST', data, false,'json');				
			if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
			{			
				 $('#displayKhaNo').html('');
		         $('#displayKhaNo').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
		         $.each(returnData, function(index, value) {
		         $('#displayKhaNo').append($("<option></option>").attr("value",value.descLangFirst).text(value.descLangFirst));
		         });				
			}	
		
	/*	else{
			errorList.push('Please select District,Tehsil,Village and khasra number ');					
		}*/			
	}
	
	//return errorList;	
}

function toGetPlotNoList(){
	
	$("#tppPlotNo").empty();
	var errorList = [];
	$('.plotNoNote').show();
	$("#showApiDetails").html('');
	var enteredVal=$("#plotNo").val();
	$('#enteredPlotVal').val(enteredVal);
	
	var landType=$("#assLandType" + " option:selected").attr("code");
	var districtId=$('#assDistrict').val();
	var TehsilId=$('#assTahasil').val();
	var villageId=$('#tppVillageMauja').val();
	var mohallaId=$('#mohalla').val();
	var streetNo=$('#assStreetNo').val();	
	
	var data = {"landType" : landType,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"mohallaId" : mohallaId,"streetNo" : streetNo,"plotNo" : enteredVal};
	if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined && $('#mohalla').val()!=undefined && $('#assStreetNo').val()!=undefined){				
	
	//For Data entry suit
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
			if(landType=='NZL'){
				var URL = 'DataEntrySuite.html?getNajoolPlotList';
			}
			if(landType=='DIV'){
				var URL = 'DataEntrySuite.html?getDiversionPlotList';				
			}
	}
	
	//For self Assessment & Change In assessment Authorization 
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
		if(landType=='NZL'){
		var URL = 'SelfAssessmentForm.html?getNajoolPlotList';
		}
		if(landType=='DIV'){
			var URL = 'SelfAssessmentForm.html?getDiversionPlotList';
		}
	}
	
	//For Amalgamation
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		if(landType=='NZL'){
			var URL = 'AmalgamationForm.html?getNajoolPlotList';
			}
			if(landType=='DIV'){
				var URL = 'AmalgamationForm.html?getDiversionPlotList';
			}	
	}
	
			var returnData = __doAjaxRequest(URL, 'POST', data, false,'json');				
			if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
			{
		
				 $('#tppPlotNo').html('');
		         $('#tppPlotNo').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
		         $.each(returnData, function(index, value) {
		         $('#tppPlotNo').append($("<option></option>").attr("value",value.descLangFirst).text(value.descLangFirst));
		         });
				
			}	
		
	/*	else{
			errorList.push('Please select District,Tehsil,Village,mohalla,block/sheet and Plot number ');					
		}	*/		
	}
	//return errorList;	
}

function fetchKhasaraDetails(){
	$("#showApiDetails").html('');
	var landType=$("#assLandType" + " option:selected").attr("code");
	var districtId=$('#assDistrict').val();
	var TehsilId=$('#assTahasil').val();
	var villageId=$('#tppVillageMauja').val();
	var khasara=$("#displayKhaNo" + " option:selected").attr("value");
	var data = {"landType" : landType,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"khasara":khasara};
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
	var URL = 'DataEntrySuite.html?getKhasaraDetails';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
	var URL = 'SelfAssessmentForm.html?getKhasaraDetails';
	}
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getKhasaraDetails';
	}
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showApiDetails").html(returnData);
	$("#showApiDetails").show();	
}

function fetchNajoolAndDiversionDetails(){
		$("#showApiDetails").html('');
		var landType=$("#assLandType" + " option:selected").attr("code");
		var districtId=$('#assDistrict').val();
		var TehsilId=$('#assTahasil').val();
		var villageId=$('#tppVillageMauja').val();
		var mohallaId=$('#mohalla').val();
		var streetNo=$('#assStreetNo').val();	
		var plotNo=$('#tppPlotNo').val();
		
		var data = {"landType" : landType,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"mohallaId" : mohallaId,"streetNo" : streetNo,"plotNo" : plotNo};
		if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
		var URL = 'DataEntrySuite.html?getNajoolAndDiversionDetails';
		}
		if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
			var URL = 'SelfAssessmentForm.html?getNajoolAndDiversionDetails';
		}
		if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
			var URL = 'AmalgamationForm.html?getNajoolAndDiversionDetails';
		}
		
		var returnData = __doAjaxRequest(URL, 'POST', data, false);		
		$("#showApiDetails").html(returnData);
		$("#showApiDetails").show();	
}


function getLandTypeDetails(){	

	var landType = $("#assLandType" + " option:selected").attr("code");
	
	if(landType !=undefined){
	var data = {"landType" : landType};
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
	var URL = 'DataEntrySuite.html?getLandTypeDetails';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
		var URL = 'SelfAssessmentForm.html?getLandTypeDetails';
	}
	
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getLandTypeDetails';
	}
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$("#landType").html(returnData);
	$('#assDistrict').val('');
	$('#assTahasil').empty();
	$('#tppVillageMauja').empty();
	$('#mohalla').empty();
	$('#assStreetNo').empty();
	$('#khasraNo').val('');
	$('#plotNo').val('');
	$('#displayKhaNo').val('');	
	$('#tppPlotNo').val('');
	$("#landType").show();
	$("#proceed").hide();
	$("#checkList").show();
	$("#checkListDiv").hide();
	}
	else{
		
		var data = {};
		if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
		var URL = 'DataEntrySuite.html?deleteLandEntry';
		}
		if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
			var URL = 'SelfAssessmentForm.html?deleteLandEntry';
		}
		if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
			var URL = 'AmalgamationForm.html?deleteLandEntry';	
		}
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#landType").html('');
	//	$("#landType").html("");
	}
}

function getTehsilListByDistrict(){	
	$('#assTahasil').empty();
	$('#tppVillageMauja').empty();
	$('#mohalla').empty();
	$('#assStreetNo').empty();
	$('#plotNo').val('');
	$('#khasraNo').val('');
	$('#tppPlotNo').empty();
	$('#displayKhaNo').empty();
	$("#showApiDetails").html('');
	var requestData = {"districtId":$('#assDistrict option:selected').attr('value'),
						"landType":$("#assLandType option:selected").attr("code")}
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
	var URL = 'DataEntrySuite.html?getTehsilListByDistrict';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
	var URL = 'SelfAssessmentForm.html?getTehsilListByDistrict';
	}
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getTehsilListByDistrict';	
	}
	
	var returnData=  __doAjaxRequest(URL,'POST',requestData, false,'html');
	$('#assTahasil').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#assTahasil').append(
					$("<option></option>").attr("value", value.lookUpCode).text(
							(value.descLangFirst)));
		});		
	}	
}

function getVillageListByTehsil(){
	$('#tppVillageMauja').empty();
	$('#mohalla').empty();
	$('#assStreetNo').empty();
	$('#plotNo').val('');
	$('#khasraNo').val('');
	$('#tppPlotNo').empty();
	$('#displayKhaNo').empty();
	$("#showApiDetails").html('');
	var Data = {"tehsilId":$('#assTahasil option:selected').attr('value'),
				"districtId":$('#assDistrict option:selected').attr('value'),
				"landType":$("#assLandType option:selected").attr("code")}
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
	var URL = 'DataEntrySuite.html?getVillageListByTehsil';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
	var URL = 'SelfAssessmentForm.html?getVillageListByTehsil';
	}
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getVillageListByTehsil';	
	}
	var returnData=  __doAjaxRequest(URL,'POST',Data, false,'html');

	$('#tppVillageMauja').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);

		if($("#assLandType option:selected").attr("code")=='KPK'){
		$.each(prePopulate, function(index, value) {
			$('#tppVillageMauja').append(
					$("<option></option>").attr("value", value.lookUpCode).text(value.descLangFirst  +" ( "+ value.otherField+" ) "));
		});		
		}
		else{
			$.each(prePopulate, function(index, value) {
				$('#tppVillageMauja').append(
						$("<option></option>").attr("value", value.lookUpCode).text(value.descLangFirst));
			});		
		}
	}
}

function getMohallaListByVillageId(){
	$('#mohalla').empty();
	$('#assStreetNo').empty();
	$('#plotNo').val('');
	$('#khasraNo').val('');
	$('#tppPlotNo').empty();
	$('#displayKhaNo').empty();
	$("#showApiDetails").html('');
	var Data = {"villageId":$('#tppVillageMauja option:selected').attr('value'),
				"tehsilId":$('#assTahasil option:selected').attr('value'),
				"districtId":$('#assDistrict option:selected').attr('value'),
				"landType":$("#assLandType option:selected").attr("code")}
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
	var URL = 'DataEntrySuite.html?getMohallaListByVillageId';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
		var URL = 'SelfAssessmentForm.html?getMohallaListByVillageId';
	}
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getMohallaListByVillageId';	
	}
	var returnData=  __doAjaxRequest(URL,'POST',Data, false,'html');

	$('#mohalla').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#mohalla').append(
					$("<option></option>").attr("value", value.lookUpCode).text((value.descLangFirst)));
		});		
	}
}

function getStreetListByMohallaId(){
	$('#assStreetNo').empty();
	$('#plotNo').val('');
	$('#khasraNo').val('');
	$('#tppPlotNo').empty();
	$('#displayKhaNo').empty();
	$("#showApiDetails").html('');
	var Data = {"mohallaId":$('#mohalla option:selected').attr('value'),
				"villageId":$('#tppVillageMauja option:selected').attr('value'),
				"tehsilId":$('#assTahasil option:selected').attr('value'),
				"districtId":$('#assDistrict option:selected').attr('value'),
				"landType":$("#assLandType option:selected").attr("code")}
	if($("#assType").val()=='DES' || $("#assType").val()=='E' || ($("#serviceShortCode").val()=='DES' && $("#assType").val()=='A')){
	var URL = 'DataEntrySuite.html?getStreetListByMohallaId';
	}
	if($("#assType").val()=='N' || ($("#serviceShortCode").val()=='SAS' && $("#assType").val()=='A') ||  ($("#serviceShortCode").val()=='CIA' && $("#assType").val()=='A')){
	var URL = 'SelfAssessmentForm.html?getStreetListByMohallaId';
	}
	if($("#assType").val()=='AML' || ($("#serviceShortCode").val()=='AML' && $("#assType").val()=='A')){
		var URL = 'AmalgamationForm.html?getStreetListByMohallaId';	
	}
	var returnData=  __doAjaxRequest(URL,'POST',Data, false,'html');

	$('#assStreetNo').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#assStreetNo').append(
					$("<option></option>").attr("value", value.lookUpCode).text(
							(value.descLangFirst)));
		});
		
	}
}



