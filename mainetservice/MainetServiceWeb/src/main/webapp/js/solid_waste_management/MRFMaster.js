$(document).ready(function() {
	var table = $('.dpsM').DataTable({
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
	if(($('option:selected', $("#integratedCT")).attr('value')) == 'N'){
		$("#mrfIsagreIntegrated").prop('disabled', true);
		$("#mrfIsagreIntegrated").val("0");	
	}else{
		$("#mrfIsagreIntegrated").prop('disabled', false);	
	}
	if(($('option:selected', $("#rdfId")).attr('value')) == 'N'){
		$("#qntRDF").prop('disabled', true);
		$("#qntRDF").val("");		
	}else{
		$("#qntRDF").prop('disabled', false);	
	}
	if(($('option:selected', $("#intrigatationId")).attr('value')) == 'N'){
		$("#intrigatationPlantId").prop('disabled', true);	
		$("#intrigatationPlantId").val("");	
	}else{
		$("#intrigatationPlantId").prop('disabled', false);			
	}
});

function addRoutAndCollection(formUrl, actionParam) { 
 	var propertyNo = $("#propertyNo").val();
 	
		if(propertyNo == undefined) { 
			propertyNo = "";
		}

	var data = {
		"propertyNo" : propertyNo
	};
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

var propertyExists = function (formUrl, actionParam) {
	var propertyNo = $("#propertyNo").val();
	if(propertyNo == undefined) { 
		propertyNo = "";
	}
	var data = {
		"propertyNo" : propertyNo
	};

	var divName = '.content-page';
	var returnData=__doAjaxRequestForSave(formUrl + '?' + actionParam, 'post',data, false,'');
	
	return returnData;
}


function getViewData(formUrl, actionParam, MrfId) {
	
	var data = {
		"MrfId" : MrfId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getEditData(formUrl, actionParam, MrfId) {
	
	var data = {
		"MrfId" : MrfId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchMRF(formUrl, actionParam) {
	
	var data = {
		"PlantId" : $("#mrfPlantId").val(),
		"PlantName" : $("#mrfPlantName").val()
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function resetMRF() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'MRFMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function saveDisposalForm(element) { 
	var errorList = [];
	errorList = validateForm(errorList);
	errorList = errorList.concat(validateEntryDetails());
	errorList = errorList.concat(validateEntryDetails1());
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element,getLocalMessage('swm.mrfMaster.save.success'),'MRFMaster.html', 'saveform');
	}
}

function validateForm(errorList){
	
	var mrfPlantId = $("#mrfPlantId").val();
	var mrfPlantName = $("#mrfPlantName").val();
	var PlantCategory = $("#mrfCategory").val();
	var deCentralised = $("#Decentralized").val();
	var ownerShip = $("#ownershipId").val();
	var Location = $("#Location").val();
	var dateOperation = $("#mrfDateOpen").val();
	var plantCapacity = $("#mrfPlantCap").val();
	var integratedCT= $("#integratedCT").val();
	var rdfId= $("#rdfId").val();
	var regex = new RegExp("^[0-9-!@#$%*?]");
    var projProgress= $("#projProgress").val();
	var intrigatationId= $("#intrigatationId").val();
	var AssetId = $("#AssetId").val();
	var propertyNo = $("#propertyNo").val();
	var propertyActiveStatus = $("#PropertyActiveStatus").val();
	
	if (mrfPlantName == "" || mrfPlantName == null)
		errorList.push( getLocalMessage("swm.validation.mrfPlantName"));
	if (PlantCategory == "0" || PlantCategory == null)
		errorList.push( getLocalMessage("swm.validation.mrfCategory"));
	if (deCentralised == "" || deCentralised == null)
		errorList.push( getLocalMessage("swm.validation.Decentralized"));
	if (ownerShip == "" || ownerShip == null)
		errorList.push( getLocalMessage("swm.validation.ownershipId"));
	if (Location == "" || Location == null)
		errorList.push( getLocalMessage("swm.validation.mrf.Location"));
	/*if (dateOperation == "" || dateOperation == null)
		errorList.push( getLocalMessage("swm.validation.mrfDateOpen"));*/
	if (plantCapacity == "" || plantCapacity == null)
		errorList.push( getLocalMessage("swm.validation.mrfPlantCap"));
	if(propertyActiveStatus != null && propertyActiveStatus == 'Y'){
	/*if (propertyNo == "" || propertyNo == null)
		errorList.push( getLocalMessage("swm.validation.propertyNo"));*/
	 }
	if(($('option:selected', $("#intrigatationId")).attr('value')) == 'Y'){
		if($("#intrigatationPlantId").val() == "" || $("#intrigatationPlantId").val()==null || $("#intrigatationPlantId").val()=="0"){
			errorList.push( getLocalMessage("swm.validation.integrated.plant.Id"));
		}
	}
	if(($('option:selected', $("#rdfId")).attr('value')) == 'Y'){
		if($("#qntRDF").val() == "" || $("#qntRDF").val()==null || $("#qntRDF").val()=="0"){
			errorList.push( getLocalMessage("swm.validation.quantityofRDF"));
		}
	}
	if(($('option:selected', $("#integratedCT")).attr('value')) == 'Y'){
		if($("#mrfIsagreIntegrated").val() == "" || $("#mrfIsagreIntegrated").val()==null || $("#mrfIsagreIntegrated").val()=="0"){
			errorList.push( getLocalMessage("swm.validation.arrangmnt.If.Integrated"));
		}			
	}
	if(projProgress!= ""){
	 if (!regex.test(projProgress)) {
		errorList.push( getLocalMessage("swm.validation.project.Progress"));
	 }
	}
	if (AssetId == "" || AssetId == null || AssetId == "0")
		errorList.push( getLocalMessage("swm.validation.AssetId"));
	if(propertyActiveStatus != null && propertyActiveStatus == 'Y'){
	if(!propertyExists('MRFMaster.html','checkPropertyNo'))
		errorList.push( getLocalMessage("swm.validation.property.details.exist"));
	}
	return errorList;
	
}
function validateEntryDetails() {
	var errorList = [];
	
	if ($.fn.DataTable.isDataTable('#empDetailTable')) {
		$('#empDetailTable').DataTable().destroy();
	}
	$("#empDetailTable tbody tr").each(function(i) {
						
						var dsgnId = $("#dsgnId" + i).val();
						var empAvlCountId = $("#empAvlCountId" + i).val();
						var empReqCountId = $("#empReqCountId" + i).val();
						var rowCount = i + 1;
						if (dsgnId == "0" || dsgnId == null || dsgnId == "") {
							errorList.push(getLocalMessage("swm.validation.dsgnId")+ rowCount);
						}
						if (empAvlCountId == "" || empAvlCountId == null) {
							errorList.push(getLocalMessage("swm.validation.empAvlCountId")+ rowCount);
						}
						if (empReqCountId == "" || empReqCountId == null) {
							errorList.push(getLocalMessage("swm.validation.empReqCountId")+ rowCount);
						}
					});
	return errorList;
}
function validateEntryDetails1() {
	var errorList = [];
	
	if ($.fn.DataTable.isDataTable('#unitDetailTable')) {
		$('#unitDetailTable').DataTable().destroy();
	}
	$("#unitDetailTable tbody tr").each(
					function(i) {
						
						var veVeType = $("#veVeType" + i).val();
						var vechAvlId = $("#vechAvlId" + i).val();
						var vechReqId = $("#vechReqId" + i).val();
						var rowCount = i + 1;
						/*if (veVeType == "0" || veVeType == null ||veVeType =="") {
							errorList.push(getLocalMessage("swm.validation.veTypeId")+ rowCount);
						}
						if (vechAvlId == "" || vechAvlId == null) {
							errorList.push( getLocalMessage("swm.validation.vechAvlId")+ rowCount);
						}
						if (vechReqId == "" || vechReqId == null) {
							errorList.push(getLocalMessage("swm.validation.vechReqId")+ rowCount);
						}*/
					});
	return errorList;
}


function deleteEntry(tableId, obj, ids) {
	if(ids != "1"){
	deleteTableRow(tableId, obj, ids, false);
	}
}

function ResetForm(resetBtn){
		
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MRFMaster.html?Add', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function backMrfMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'MRFMaster.html');
	$("#postMethodForm").submit();
}

$(function() {
	
	var errorList = [];
	/* To add newVehicle Row into table */
	$("#unitDetailTable").on('click', '.addCF', function() {
		
		errorList = validateEntryDetails1();
		if(errorList.length==0){
		var content = $("#unitDetailTable").find('tr:eq(1)').clone();
		$("#unitDetailTable").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		$('.error-div').hide();
		reOrderUnitTabIdVehicleSequence('.firstUnitRow');
		}else{
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		}
	});
});
function reOrderUnitTabIdVehicleSequence(firstRow) {
	
	$(firstRow).each(
			function(i) {
				
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "veVeType" + i);
				$(this).find("input:text:eq(1)").attr("id", "vechAvlId" + i);
				$(this).find("input:text:eq(2)").attr("id", "vechReqId" + i);
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr("name","mRFMasterDto.tbSwMrfVechicleDet[" + i + "].veVeType");
				$(this).find("input:text:eq(1)").attr("name","mRFMasterDto.tbSwMrfVechicleDet[" + i + "].mrfvAvalCnt");
				$(this).find("input:text:eq(2)").attr("name","mRFMasterDto.tbSwMrfVechicleDet[" + i + "].mrfvReqCnt");
			});
}

$(function() {
	
	/* To add newEmployee  Row into table */
	var errorList = [];
	$("#empDetailTable").on('click', '.empAdd', function() {
		
		errorList = validateEntryDetails();
		if(errorList.length==0){
		var content = $("#empDetailTable").find('tr:eq(1)').clone();
		$("#empDetailTable").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		$('.error-div').hide();
		reOrderUnitTabIdEmpSequence('.firstEmpRow');
		}else{
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		}
	});
});
function reOrderUnitTabIdEmpSequence(firstEmpRow) {
	
	$(firstEmpRow).each(
			function(i) {
				
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "dsgnId" + i);
				$(this).find("input:text:eq(1)").attr("id", "empAvlCountId" + i);
				$(this).find("input:text:eq(2)").attr("id", "empReqCountId" + i);
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr("name","mRFMasterDto.tbSwMrfEmployeeDet[" + i + "].dsgId");
				$(this).find("input:text:eq(1)").attr("name","mRFMasterDto.tbSwMrfEmployeeDet[" + i + "].mrfeAvalCnt");
				$(this).find("input:text:eq(2)").attr("name","mRFMasterDto.tbSwMrfEmployeeDet[" + i + "].mrfeReqCnt");
			});
}

function deleteEntry(obj, ids) {
	deleteTableRow('firstUnitRow', obj, ids);
	$('#firstUnitRow').DataTable().destroy();
	triggerTable();
}

function showMap() {
	
	data = {};
	var URL = 'MRFMaster.html?getMapData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'Json');
	mapList = returnData[0];
	mapdeatails = returnData[1];
	initMap2(mapList, mapdeatails);

}
function initMap2(mapList, mapdeatails) {
	
	var locations = [ mapList ];
	var locDeatils = [ mapdeatails ];
	var feature = [];
	var map = new google.maps.Map(document.getElementById('map-canvas'), {
		zoom : parseInt(getLocalMessage("disposal.map.zoom")),
		center : new google.maps.LatLng(
				(getLocalMessage("disposal.lattitute.initialize")),
				parseFloat(getLocalMessage("disposal.Longitutde.initialize"))),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
	var infowindow = new google.maps.InfoWindow();
	var marker, i;
for (i = 0; i < locations[0].length; i++) {  
		                  marker = new google.maps.Marker({
		                    position: new google.maps.LatLng(locations[0][i][1], locations[0][i][2]),		                   
		                    map: map,
		                  });
		                  google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
		                    return function() {
		                   var contentString = '<div id="content" style="margin-left:5px;margin-top:3px;overflow:hidden;">'+ '<div id="bodyContent">'+'<img src="'+locations[0][i][4]+'" style="width:200px;height:200px;" alt="NO IMAGE FOUND"/>' +'<br><font style="color:darkblue;font:10px tahoma;margin-left:5px;">MRF Center </font>'+'<br><div style="font:10px verdana;color:darkgreen;margin-left:5px;">Plant Name : ' + locations[0][i][3]+' <br>Plant Category :'+ locations[0][i][0] +'<br>Plant Id :'+locations[0][i][5]+'<br>Plant Capacity :'+locations[0][i][6] + 'TPD</div>' + '</div>'+'</div>';
		                   infowindow.setContent(contentString);
		                      infowindow.open(map, marker);		                    
		                    }
		                  })(marker, i));
		                  google.maps.event.addListener(marker, 'mouseout', (function(marker, i) {
			                      return function() {				                    	
			                      this.setIcon('https://maps.gstatic.com/mapfiles/api-3/images/spotlight-poi2.png');
			                      infowindow.open(map, marker);		                    
			                    }
			                  })(marker, i));

	}
}
function changeIntegratedPlant(){
	if(($('option:selected', $("#intrigatationId")).attr('value')) == 'N'){
		$("#intrigatationPlantId").prop('disabled', true);	
		$("#intrigatationPlantId").val("");	
	}else{
		$("#intrigatationPlantId").prop('disabled', false);			
	}
}

function changeRDF(){
	if(($('option:selected', $("#rdfId")).attr('value')) == 'N'){
		$("#qntRDF").prop('disabled', true);
		$("#qntRDF").val("");		
	}else{
		$("#qntRDF").prop('disabled', false);	
	}
}


function changeIntegratedCT(){
	if(($('option:selected', $("#integratedCT")).attr('value')) == 'N'){
		$("#mrfIsagreIntegrated").prop('disabled', true);
		$("#mrfIsagreIntegrated").val("0");
	}else{
		$("#mrfIsagreIntegrated").prop('disabled', false);	
	}
}
