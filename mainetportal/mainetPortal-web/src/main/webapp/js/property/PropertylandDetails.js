$(document).ready(function(){	
	$('.khaNoNote').hide();
	$('.plotNoNote').hide();
});

function toGetKhasraNoList(){
	$("#tppPlotNoCs").empty();
	$('.khaNoNote').show();
	$("#showApiDetails").html('');
	var enteredVal=$("#khasraNo").val();
	var data = {"villageId":$('#tppVillageMauja option:selected').attr('value'),
			"tehsilId":$('#assTahasil option:selected').attr('value'),
			"districtId":$('#assDistrict option:selected').attr('value'),
			"landType":$("#assLandType option:selected").attr("code"),"khasara":enteredVal};
	if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined){				
	var URL = 'SelfAssessmentForm.html?getKhasraNoList';
	
	var returnData = __doAjaxRequest(URL, 'POST', data, false,'json');				
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{			
				 $('#displayKhaNo').html('');
		         $('#displayKhaNo').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
		         $.each(returnData, function(index, value) {
		         $('#displayKhaNo').append($("<option></option>").attr("value",value.descLangFirst).text(value.descLangFirst));
		         });				
	}		
	}	
}

function toGetPlotNoList(){
	
	$("#tppPlotNo").empty();
	$('.plotNoNote').show();
	$("#showApiDetails").html('');
	var enteredVal=$("#plotNo").val();
	var landType=$("#assLandType" + " option:selected").attr("code");
	var districtId=$('#assDistrict').val();
	var TehsilId=$('#assTahasil').val();
	var villageId=$('#tppVillageMauja').val();
	var mohallaId=$('#mohalla').val();
	var streetNo=$('#assStreetNo').val();	
	
	var data = {"landType" : landType,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"mohallaId" : mohallaId,"streetNo" : streetNo,"plotNo" : enteredVal};
	if($('#assDistrict').val()!=undefined && $('#assTahasil').val()!=undefined && $('#tppVillageMauja').val()!=undefined && $('#mohalla').val()!=undefined && $('#assStreetNo').val()!=undefined){				

	if(landType=='NZL'){
		var URL = 'SelfAssessmentForm.html?getNajoolPlotList';
	}
	if(landType=='DIV'){
		var URL = 'SelfAssessmentForm.html?getDiversionPlotList';
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
	}	
}

function getLandTypeDetails(){	

	var landType = $("#assLandType" + " option:selected").attr("code");
	if(landType !=undefined){
	var data = {"landType" : landType};
	var URL = 'SelfAssessmentForm.html?getLandTypeDetails';
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
	
	}
	else{
		var data = {};
		var URL = 'SelfAssessmentForm.html?deleteLandEntry';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#landType").html("");
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
	
	var URL = 'SelfAssessmentForm.html?getTehsilListByDistrict';
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
	var errorList = [];
	var Data = {"tehsilId":$('#assTahasil option:selected').attr('value'),
				"districtId":$('#assDistrict option:selected').attr('value'),
				"landType":$("#assLandType option:selected").attr("code")}

	var URL = 'SelfAssessmentForm.html?getVillageListByTehsil';
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

	var URL = 'SelfAssessmentForm.html?getMohallaListByVillageId';
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
	
	var URL = 'SelfAssessmentForm.html?getStreetListByMohallaId';
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

function fetchKhasaraDetails(){
	$("#showApiDetails").html('');
	var landType=$("#assLandType" + " option:selected").attr("code");
	var districtId=$('#assDistrict').val();
	var TehsilId=$('#assTahasil').val();
	var villageId=$('#tppVillageMauja').val();
	var khasara=$("#displayKhaNo" + " option:selected").attr("value");
	var data = {"landType" : landType,"districtId" : districtId,"TehsilId" : TehsilId,"villageId" : villageId,"khasara":khasara};
	var URL = 'SelfAssessmentForm.html?getKhasaraDetails';

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
	
	var URL = 'SelfAssessmentForm.html?getNajoolAndDiversionDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showApiDetails").html(returnData);
	$("#showApiDetails").show();	
}