/**
 * ritesh.patil
 * 
 */

$(document).ready(function(){
	$('#complaintTypeDiv').hide();
	$('#sourceOfFundDiv').hide();
	$('#schemeNames').hide();
	$('#billTypeDiv').hide();
	$('#tradeCateg').hide();
	$('#workflowBaseDeptDiv').hide();
	$('#mappingDiv').hide();
	$('#workdeprtIdByDept').hide();
	$('#sfacMasterDiv').hide();
	$('#vehMainteinedBy').hide();
	$('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
	
	$('#serviceId').change(function(){
		var serviceId = $('#serviceId').val();
		var deptId= $('#departmentId').val();
		var deptCode =  $("#departmentId :selected").attr('code');
		var auditDeptWiseFlag = $("#auditDeptWiseFlag").val();
		if(serviceId>0 && deptId>0){
			$('#zone-ward').hide();
			 $('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
			var requestData = {"orgId":$('#onOrgSelect').val(),"deptId":deptId,serviceId};
			var response=__doAjaxRequest('WorkFlowType.html?mapping','post',requestData,false,'html');
			$('#table-responsive').html(response);
			chosen();
			$('#mappingDiv').show();
			$("#mapOrgId_0").val($("#onOrgSelect").val());
			if($("#onOrgSelect").val()>0){
				showMappingDept('#mapOrgId_0');
			} 
			var serviceCode =  $("#serviceId :selected").attr('code');
			if(serviceCode=="CARE"){
				$('#complaintTypeDiv').show();
				 $('#complaintId').val('0').trigger('chosen:updated');
			}else{
				$('#complaintTypeDiv').hide();
			}
			if(serviceCode=="VB" || serviceCode=="MBA"){
				$('#billTypeDiv').show();
			 $('#billTyp').val('0').trigger('chosen:updated');
			}else{
				$('#billTypeDiv').hide();
			}
			if(serviceCode=="NTL" || serviceCode=="RTL" || serviceCode=="TLA"){
				$('#tradeCateg').show();
			 $('#tradeCateg').val('0').trigger('chosen:updated');
			}else{
				$('#tradeCateg').hide();
			}
			if(serviceCode=="MOV"){
	            $('#vehMainteinedBy').show();
	        }else{
	            $('#vehMainteinedBy').hide();
	        }
			
			if(serviceCode=="IARS" || deptCode=="CMT" || (auditDeptWiseFlag == "Y" && deptCode == "AD")){
				$('#workdeprtIdByDept').show();
			}else{
				$('#workdeprtIdByDept').hide();
			}
		}else{
			$('#mappingDiv').hide();
		}
		
	});
	
	$('#complaintId').change(function(){
		 $('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
		 $('#zone-ward').hide();
		var serviceCode =  $("#serviceId :selected").attr('code');
		/*
		 * if(serviceCode=="CARE"){ if($("#complaintId
		 * :selected").attr('code')>0){ $('#mapDeptId_0').val($("#complaintId
		 * :selected").attr('code')); }else{ $('#mapDeptId_0').val("0"); } }
		 */
	});
	
	
	 $('input:radio[name="workFlowMasDTO.type"]').change(function () {
		 var wardZoneType= $("input[name='workFlowMasDTO.type']:checked").val();
         if (wardZoneType == 'A') {
        	 $("#codIdOperLevel1").val(null);
        	 $("#codIdOperLevel2").val(null);
        	 $("#codIdOperLevel3").val(null);
        	 $("#codIdOperLevel4").val(null);
        	 $("#codIdOperLevel5").val(null);
             $('#zone-ward').hide();
         } else if (wardZoneType == 'N') {
        	 
        	var serviceCode =  $("#serviceId :selected").attr('code');
        	var comId= $('#complaintId').val(); 
        	
     		if(serviceCode=="CARE" && comId>0){
     			// U#113577
     			if($('#kdmcEnv').val()== "Y"){
     				var deptId= $("#complaintId :selected").attr('code');
     			}else{
     				var deptId= $('#departmentId').val();
     			}
     		}
     		else{
     			var deptId= $('#departmentId').val();
     			var auditDeptWiseFlag = $("#auditDeptWiseFlag").val();
     			var deptCode =  $("#departmentId :selected").attr('code');
     			if( deptCode == 'IAST' || deptCode == 'CMT' || (auditDeptWiseFlag == "Y" && deptCode == "AD")){
     				var deptId= $('#workdepartmentId').val();
         		}
     		}
     		
     		
            var requestData = {"orgId":$('#onOrgSelect').val(),"deptId":deptId,"serviceCode":serviceCode}
        	var response = __doAjaxRequest('WorkFlowType.html?wardZoneMapping','post',requestData,false,'html');
            if(response=="") {
            	$('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
        		var warnMsg = getLocalMessage('workflow.type.noworkflow') ;
        		var message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
        		$(childDivName).addClass('ok-msg').removeClass('warn-msg');
        		$(childDivName).html(message);
        		$(childDivName).show();
        		$('#yes').focus();
        		showModalBox(childDivName);
        		return false;
   			} else{
   			    $('#zone-ward').html(response);
				$('#zone-ward').show();
			}
         }
         $('.appendableClass').each(function(i) {
     		$('#'+$(this).find("select:eq(1)").attr("id")).val($('#onOrgSelect').val())
     	});
         
		});

 $('#departmentId').change(function(){
		$('.error-div').hide();
		$('#complaintTypeDiv').hide();
		$('#sourceCode').val('0');
		$('#sourceOfFundDiv').hide();
		$('#workflowBaseDeptDiv').hide();
		$('#schemeNames').hide();
		$('#vehMaintainBy').val('0');
		$('#sfacMasterDiv').hide();
		$('#vehMainteinedBy').hide();
		var requestData = {"orgId":$('#onOrgSelect').val(),"deptId":$('#departmentId').val()};
		$('#serviceId').html('');
		$('#serviceId').append($("<option></option>").attr("value","0").text(getLocalMessage('selectdropdown')));
		$('#complaintId').html('');
		$('#complaintId').append($('<option></option>').attr("value","0").text(getLocalMessage('selectdropdown')));
		var result=__doAjaxRequest("WorkFlowType.html?services",'post',requestData,false,'json');
		var serviceList=result[0];
		var compList=result[1];
		
		  
		$.each(serviceList, function(index, value) {
		if($('#langId').val() == 1){
			$('#serviceId').append($("<option></option>").attr("value",value[0]).attr("code",value[2]).text(value[1]));
			}else{
				$('#serviceId').append($("<option></option>").attr("value",value[0]).attr("code",value[2]).text(value[3]));
			}
		});
		 
		$.each(compList, function( index, value ) {
			$('#complaintId').append($('<option></option>').attr("value",value.compId).attr("code",value.deptId).text((value.deptName)+" => "+(value.complaintDesc)));
		});
			
			$('#serviceId').trigger("chosen:updated");
			$('#complaintId').trigger("chosen:updated");
		 $('#mappingDiv').hide();
		 $('#zone-ward').hide();
		 $('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
		 
	});
 
$('#submitFlow').click(function(){
	
	     var errorList = [];
		 errorList = validateFlowForm(errorList);
		 if (errorList.length == 0) {
			      errorList=validateFlowMapping(errorList);
			      if(errorList.length == 0){
			    	  errorList = checkExistingWorkflow(errorList);
			    	  if(errorList.length == 0){
			    		  //remove from DOM same path wmSchCodeId1
			    		  if($("#serviceId :selected").attr('code')=='LQE'){
			    			  $('#sourceOfFundDiv').remove();
			    			  $('#sfacMasterDiv').remove();
			    		  }else{
			    			  $('#workflowBaseDeptDiv').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='VB' || $("#serviceId :selected").attr('code')=='MBA'){
			    			  $('#schemeNames').remove();
			    			  $('#tradeCateg').remove();
			    			  //$('#workflowBaseDeptDiv').remove();
			    		  }
			    		  else {
			    			  $('#billTypeDiv').remove();
			    		  }
			    		  if($("#departmentId :selected").attr('code')=='IAST' || $("#departmentId :selected").attr('code')=='CMT' || ($("#auditDeptWiseFlag").val() == "Y" && $("#departmentId :selected").attr('code') == "AD")){
			    			  $('#schemeNames').remove();
			    			  $('#tradeCateg').remove();
			    			  $('#billTypeDiv').remove();
			    			  //$('#workflowBaseDeptDiv').remove();
			    		  }
			    		  else {
			    			  $('#workdeprtIdByDept').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='NTL'|| $("#serviceId :selected").attr('code')=='RTL' || $("#serviceId :selected").attr('code')=='TLA'){
			    			  $('#schemeNames').remove();
			    			  $('#billTyp').remove();
			    		  }
			    		  else {
			    			  $('#tradeCateg').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='MOV'){
			    			  $('#sfacMasterDiv').remove();
			    		  }
			    		  else {
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='CAE'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='FMC'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='EGR'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='CGF'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='MSC'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='DPR'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='ABS'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
			    		  if($("#serviceId :selected").attr('code')=='FPO'){
			    			  $('#vehMainteinedBy').remove();
			    		  }
                          if($("#serviceId :selected").attr('code')=='FAE'){
			    			  $('#vehMainteinedBy').remove();
			    		  }


			    	   var requestData=$('#workFlowTypeForm').serialize()+"&"+$("#workFlowSubTypeForm").serialize();
			    	   var returnData=__doAjaxRequestForSave('WorkFlowType.html?saveform', 'post',requestData, false,'',$(this));
			    	    getSaveFormResponse(returnData);
			    	  }else{showRLValidation(errorList);}
					}else{showRLValidation(errorList);
		    }}else{showRLValidation(errorList);
		 }
   });

$("#resetFlow").click(function(){
	var ajaxResponse = __doAjaxRequest(workFlowUrl+'?form', 'POST', {}, false,'html');
	$('.content').html(ajaxResponse);
});

});


function showMappingDept(obj) {
   var	arr=$(obj).prop('id').split('_');
	var mappedOrg=$(obj).val();
	$('#roleType_'+arr[1]).val('0');
	$('#roleOrEmpId_'+arr[1]).val('0');
	 // $('#mapDeptId_'+arr[1]).append($("<option></option>").attr("value","0").text("Select"));
	if(mappedOrg>0){
		$('#mapDeptId_'+arr[1]).html('');
		$('#mapDeptId_'+arr[1]).append($("<option></option>").attr("value","0").text("Select"));
		   var requestData = {"orgId":mappedOrg }
		  var result=__doAjaxRequest("WorkFlowType.html?department",'POST',requestData,false,'json');
			$.each(result, function(index, value) {
				if($('#langId').val() == 1){
					 $('#mapDeptId_'+arr[1]).append($("<option></option>").attr("value",value[0]).text(value[1]));
				}else{
					$('#mapDeptId_'+arr[1]).append($("<option></option>").attr("value",value[0]).text(value[2]));
				}
			});
			if($(obj).val() == $("#onOrgSelect").val()){
				$('#mapDeptId_'+arr[1]).val($("#departmentId").val());
			}
		  $('#apprCount_'+arr[1]).val('1');
	}
		$('.mul_'+arr[1]).empty();
		$('.mul_'+arr[1]).append('<select id="roleOrEmpId_'+(arr[1])+'" name="workFlowMasDTO.workflowDet['+(arr[1])+'].roleOrEmpIds" class="form-control multiple-chosen" multiple="multiple"></select>')
		$('#roleOrEmpId_'+arr[1]).multiselect('rebuild');	
}


function checkExistingWorkflow(errorList){
	var orgId  =$('#onOrgSelect').val();
	var deptId= $('#departmentId').val();
	var serviceId=$('#serviceId').val();
	var wardZoneType= $("input[name='workFlowMasDTO.type']:checked").val();
	var compId=$('#complaintId').val();	
	var OperLevel1 = $("#codIdOperLevel1").val();
	var OperLevel2 = $("#codIdOperLevel2").val();
	var OperLevel3 = $("#codIdOperLevel3").val();
	var OperLevel4 = $("#codIdOperLevel4").val();
	var OperLevel5 = $("#codIdOperLevel5").val();
	var fromAmount = $('#fromAmount').val(); 
	var toAmount = $('#toAmount').val();
	var sourceOfFund=$('#sourceCode').val();
	var schemeId=$('#schemeId').val();
	var extIdentifier = $('#vehMaintainBy').val();
	
	
	//here if service is LQE than set DEPT id in source of fund
	if($("#serviceId :selected").attr('code')=='LQE'){
		sourceOfFund =$('#baseDeptId').val(); 
	}
	//here if service is VB than bill type id in schemeId
	if($('#billType').val()=='Y'){
	  if($("#serviceId :selected").attr('code')=='VB'|| $("#serviceId :selected").attr('code')=='MBA' ){
		var schemeId =$('#billTyp').val(); 
	 }
	}
	if($('#category').val()=='Y'){
	  if($("#serviceId :selected").attr('code')=='NTL'|| $("#serviceId :selected").attr('code')=='RTL' || $("#serviceId :selected").attr('code')=='TLA'){
		var schemeId =$('#tradeCat').val(); 
	 }
	}
	
	if($('#workdepartmentId').val()!=null){
		  if($("#serviceId :selected").attr('code')=='IARS' || $("#departmentId :selected").attr('code')=='CMT' ||   ($("#auditDeptWiseFlag").val() == 'Y') && $("#departmentId :selected").attr('code')=='AD'){
			var schemeId =$('#workdepartmentId').val(); 
		 }
		}
	
	if($("#serviceId :selected").attr('code')=='MOV'){
		var extIdentifier = $('#vehMaintainBy').val();
	}
	
	if($("#serviceId :selected").attr('code')=='FPO' || $("#serviceId :selected").attr('code')=='CAE' || $("#serviceId :selected").attr('code')=='FAE' || $("#serviceId :selected").attr('code')=='MSC' || $("#serviceId :selected").attr('code')=='DPR' || $("#serviceId :selected").attr('code')=='FMC' || $("#serviceId :selected").attr('code')=='EGR' || $("#serviceId :selected").attr('code')=='CGF' || $("#serviceId :selected").attr('code')=='ABS'){
		var extIdentifier = $('#masId').val();
	}
	if(OperLevel1==undefined)
	 OperLevel1 = null;
	
	if(OperLevel2==undefined)
		OperLevel2 =null;

	if(OperLevel3==undefined)
		OperLevel3 =null;
	
	if(OperLevel4==undefined)
		OperLevel4 =null;
	
	if(OperLevel5==undefined)
		OperLevel5 =null;
	
	if(sourceOfFund==undefined){
		sourceOfFund=null;
	}
	
	if(schemeId==undefined){
		schemeId=null;
	}
	
	
	 var requestData = { "orgId":orgId,"deptId":deptId,"serviceId":serviceId,"compId":compId,"wardZoneType":wardZoneType,
			 "firstLevel":OperLevel1,
			 "secondLevel":OperLevel2,
			 "thirdLevel":OperLevel3,
			 "fourthLevel":OperLevel4,
			 "fiveLevel":OperLevel5,
			 "fromAmount":fromAmount,
			 "toAmount":toAmount,
			 "sourceOfFund":sourceOfFund,
			 "schemeId":schemeId,
			 "extIdentifier":extIdentifier
			 }
	 var response = __doAjaxRequest(workFlowUrl+'?checkExisting', 'POST', requestData, false,'json');
	 if(response){
		 errorList.push('Workflow already Exist.');
	 }
	 return errorList;
}

function reOrderTableIdSequence() {

	$('.appendableClass').each(function(i) {
		$(this).find("input:hidden:eq(0)").attr("id", "id_"+i);
		$(this).find("select:eq(0)").attr("id", "eventMasterId_"+i).attr('onchange','checkForDuplicateEvent(this,'+i+')');
		$(this).find("select:eq(1)").attr("id", "mapOrgId_"+i);
		$(this).find("select:eq(2)").attr("id", "mapDeptId_"+i);
		$(this).find("select:eq(3)").attr("id", "roleType_"+i);
		$(this).find("select:eq(4)").attr("id", "roleOrEmpId_"+i);
		$(this).find("select:eq(5)").attr("id", "units_"+i);
		
		$(this).find("td.mul_"+(i>0 ? i-1:0)).removeClass("mul_"+(i>0 ? i-1:0)).addClass("mul_"+(i>0 ? i:0));
		$(this).find("input:text:eq(0)").attr("id", "sla_"+i);
		$(this).find("input:text:eq(1)").attr("id", "apprCount_"+i);
		
		$(this).find("input:hidden:eq(0)").attr("name", "workFlowMasDTO.workflowDet["+i+"].wfdId");
		
		$(this).find("select:eq(0)").attr("name","workFlowMasDTO.workflowDet["+i+"].eventId");
		$(this).find("select:eq(1)").attr("name","workFlowMasDTO.workflowDet["+i+"].mapOrgId");
		$(this).find("select:eq(2)").attr("name","workFlowMasDTO.workflowDet["+i+"].mapDeptId");
		$(this).find("select:eq(3)").attr("name","workFlowMasDTO.workflowDet["+i+"].roleType");
		$(this).find("select:eq(4)").attr("name","workFlowMasDTO.workflowDet["+i+"].roleOrEmpIds");
		$(this).find("select:eq(5)").attr("name","workFlowMasDTO.workflowDet["+i+"].unit");
		
		$(this).find("input:text:eq(0)").attr("name", "workFlowMasDTO.workflowDet["+i+"].sla");
		$(this).find("input:text:eq(1)").attr("name", "workFlowMasDTO.workflowDet["+i+"].apprCount");
	});
	
}

function validateFlowMapping(errorList){
$('.appendableClass').each(function(i) {
	 row=i+1;
		errorList =  validateDetailsTableData(errorList,i);
  });
return errorList;

}

function checkForDuplicateEvent(event, currentRow){
	$(".error-div").hide();
	 var errorList = [];
	 var serviceCode =  $("#serviceId :selected").attr('code');
      if(serviceCode=='0' || serviceCode == undefined){
		 errorList.push(getLocalMessage('workflow.select.service')); }
      if(errorList.length == 0){
    		$('.appendableClass').each(function(i) {	
    			if(currentRow != i && (event.value == $("#eventMasterId_"+i).val()) && serviceCode!="CARE"){	
    				$("#eventMasterId_"+currentRow).val("0");		
    				errorList.push(getLocalMessage('workflow.form.validation.duplicant.event'));
    				displayErrorsOnPageView(errorList);
    				return false;}});
      }else{ $("#eventMasterId_"+currentRow).val("0");	
     	 displayErrorsOnPageView(errorList);
		 return false;  }
}


/**
 * validate each mandatory column of additional owner details
 * 
 * @param errorList
 * @param i
 * @returns
 */
function validateDetailsTableData(errorList, i) {
     var res = [];
	 var event = $("#eventMasterId_"+i).val();
	 var roleEmpName = $("#roleType_"+i).val();
	 var details = $("#roleOrEmpId_"+i).val();
	 var sla = $.trim($("#sla_"+i).val());
	 var units= $("#units_"+i).val();
	 var org= $("#mapOrgId_"+i).val(); 
	 var dept= $("#mapDeptId_"+i).val();   
	 var approver = $.trim($("#apprCount_"+i).val());
	 var wfMode = $("#workflowMode :selected").attr('code');
	 var j=i+1;
     var numItems= $('#roleOrEmpId_'+i+' option:selected').length;
     var type =  $('#roleType_'+i+' :selected').attr('value');

	 if(event =="" || event =='0'  || event == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.event') +" -"+j);
	 }
	 
	 if(org =="" || org =='0'  || org == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.org') +" -"+j);
	 }
	 
	 if(dept =="" || dept =='0'  || dept == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.dept') +" -"+j);
	 }
	 if(roleEmpName =="" || roleEmpName =='0'  || roleEmpName == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.roleemp') +" -"+j);
	 }
	 if(details =="" || details =='0'  || details == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.details') +" -"+j);
	 }
	 
	 if(wfMode == "AE" ){
		 if(sla == "" || sla == undefined){
			 errorList.push(getLocalMessage('workflow.form.validation.enter.sla')+" -"+j);
		 }	 
		 if(units =="" || units =='0'  || units == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.select.unit') +" -"+j);
		 }
	 }
	 if (sla != "" && sla != undefined && units != "" && units != '0'
			&& units != undefined) {
		res = sla.split('.');
		if ($('#units_' + i + ' option:selected').attr('code') == 'D') {
			if (res.length > 0 && res[1] >= 24) {
				errorList
						.push(getLocalMessage('workflow.enter.value.hours')
								+ " -" + j);
			}
		}
		if ($('#units_' + i + ' option:selected').attr('code') == 'H') {
			if (res.length > 0 && res[1] >= 60) {
				errorList
						.push(getLocalMessage('workflow.enter.value.minutes')
								+ " -" + j);
			}
		}
	}
	
	 if(approver == "" || approver == undefined){
		 errorList.push(getLocalMessage('workflow.form.validation.enter.aprvcount')+" -"+j);
	 }
	 if(approver =='0'){
		 errorList.push(getLocalMessage('workflow.form.validation.apprvr.zero')+" -"+j);
		 $("#apprCount_"+i).val('');
	 }
	 if(approver !=null && approver !='0' && approver != undefined && approver != "" && roleEmpName !="" && roleEmpName !='0'  && roleEmpName != undefined  ){
		 if(type=='R'){
		 var roleIds= $("#roleOrEmpId_"+i).val().toString(); 
		 var requestData = {"orgId":org,"roleIds":roleIds};
			  var totalEmp=__doAjaxRequest("WorkFlowType.html?getTotalEmployeeCountByRoles",'POST',requestData,false,'');
			  if(approver>totalEmp){
			$("#apprCount_"+i).val('')
			 errorList.push(getLocalMessage('workflow.form.validation.apprvrcount.detailselection') +" -"+j+". Total: "+totalEmp+ " Employees are mapped for selected roles.");
		} 
	 }else{
	  	 	if(approver>numItems){
	  		$("#apprCount_"+i).val('')
	  		 errorList.push(getLocalMessage('workflow.form.validation.apprvrcount.detailselection') +" -"+j);
	  	}	
	  	}
	 }
	return errorList;
}

function validateFlowForm(errorList) {
		
		var deptId= $('#departmentId').val();
		var serviceId=$('#serviceId').val();
		var sourceCode=$('#sourceCode').val();
		var workflowMode=$('#workflowMode').val();
		var wardZoneType= $("input[name='workFlowMasDTO.type']:checked").val();
		var complaintId= $('#complaintId').val(); 
		var fromAmount = $('#fromAmount').val(); 
		var toAmount = $('#toAmount').val(); 
		var schemeId = $('#schemeId').val();
		var baseDeptId=$('#baseDeptId').val();
		var billType=$('#billType').val();
		var category=$('#category').val();
		var tradeCat=$('#tradeCat').val();
		var billTyp = $('#billTyp').val();
		var vehMainBy = $('#vehMaintainBy').val();
		
		var serviceCode =  $("#serviceId :selected").attr('code');
		
		if(deptId == '0' || deptId == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.select.dept'));
		}
		if(serviceId == '0' || serviceId == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.select.service'));
		}
		if((sourceCode == '0' || sourceCode == undefined) && serviceCode == 'WOA'){
			errorList.push(getLocalMessage('workflow.form.validation.sourceCode'));
		}
		if((schemeId == '0' || schemeId == undefined) && serviceCode == 'WOA'){
			errorList.push(getLocalMessage('workflow.select.schemeName'));
		}
		if((baseDeptId == '0' || baseDeptId == undefined) && serviceCode == 'LQE'){
			errorList.push(getLocalMessage('workflow.select.baseDept'));
		}
		if(workflowMode == '0' || workflowMode == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.select.wfmode'));
		}
		var serviceCode =  $("#serviceId :selected").attr('code');
		if(serviceCode=="CARE"){
			if(complaintId == '0' || complaintId == undefined ){
				 errorList.push(getLocalMessage('workflow.form.validation.select.comptype'));
			}		
		}
		if(serviceCode=="MOV"){
			if(vehMainBy == '0' || vehMainBy == undefined ||  vehMainBy == ""){
				 errorList.push(getLocalMessage('workflow.form.validation.select.comptype'));
			}		
		}
		if(billType =='Y' && (serviceCode=="VB" || serviceCode=="MBA")){
			if(billTyp == '0' || billTyp == undefined ){
				 errorList.push(getLocalMessage('workflow.form.validation.select.billType'));
			}		
		}
		if(category =='Y' && (serviceCode=="NTL" || serviceCode=="RTL" || serviceCode=="TLA")){
			if(tradeCat == "" || tradeCat == undefined ){
				 errorList.push(getLocalMessage('workflow.form.validation.select.category'));
			}		
		}
		
		errorList = amountValidation(errorList, fromAmount,toAmount);
		errorList = wardZoneValidation(errorList ,wardZoneType);
		return errorList;	
}

function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}

function showAddPropertyValidation(){
	var warnMsg=getLocalMessage("rl.common.can.not.add.property");
	message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	$(childDivName).html(message);
	$(childDivName).show();
	showModalBox(childDivName);
	return false;
}   

function showEmpOrRole(obj) {

	$('.error-div').hide();
	arr=$(obj).prop('id').split('_');
	$('#roleOrEmpId_'+arr[1]).html('');
	var mapOrg= $('#mapOrgId_'+arr[1]).val();
	var mapDept= $('#mapDeptId_'+arr[1]).val();
	var type= $('#roleType_'+arr[1]).val();
	 var errorList = [];
	 $('#roleOrEmpId_'+arr[1]).prop("disabled",false);
	 
	if(mapOrg == '0' || mapOrg == undefined ){
		 errorList.push(getLocalMessage('workflow.type.val.sel.org'));
	  }
	if(type == '0' || type == undefined ){
		 errorList.push(getLocalMessage('workflow.type.val.sel.roleOrEmp'));
	}
	if(errorList.length>0){
		$('#roleType_'+arr[1]).val('0');
		 $('#roleOrEmpId_'+arr[1]).append($("<option></option>").attr("value","0").text("Select"));
			showRLValidation(errorList);
	}
	else{
		var requestData = {"orgId":mapOrg,"deptId":mapDept, "flag":$(obj).val()}
		   result=__doAjaxRequest("WorkFlowType.html?empOrRole",'POST',requestData,false,'json');
		$('.mul_'+arr[1]).empty();
		$('.mul_'+arr[1]).append('<select id="roleOrEmpId_'+(arr[1])+'" name="workFlowMasDTO.workflowDet['+(arr[1])+'].roleOrEmpIds" class="form-control multiple-chosen" multiple="multiple"></select>')
	    	 if($(obj).val() == 'E'){
					$.each(result, function(index, value) {
						var mvalue='';
						if(value[1] != null){mvalue=value[1];}
						$('#roleOrEmpId_'+arr[1]).append($("<option></option>").attr("value",value[3]).text(value[0]+" "+mvalue+" "+value[2]));
					});
			  }else{
				  $.each(result, function(index, value) {
						$('#roleOrEmpId_'+arr[1]).append($("<option></option>").attr("value",value[0]).text(value[1]));});
			  }
		     multiSel();
			 $('#roleOrEmpId_'+arr[1]).multiselect('rebuild');
		}
}




function multiSel(){
	  $(".multiple-chosen").multiselect({
		  numberDisplayed: 1,
		  includeSelectAllOption : true,
		  selectAllName : 'select-all-name'});
}

function checkOrgDept(errorList, i) {
	var orgId= $('#onOrgSelect').val();
	var deptId= $('#departmentId').val();
	if(orgId == '0' || orgId == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.org'));
	 }
	
	if(deptId == '0' || deptId == undefined ){
		 errorList.push(getLocalMessage('workflow.form.validation.select.dept'));
	}
	return errorList;
}

function getSaveFormResponse(returnData){
	var successUrl='WorkFlowType.html';
	if ($.isPlainObject(returnData))
	{
		var message = returnData.command.message;
		var hasError = returnData.command.hasValidationError;
		if (!message) {
			message = successMessage;
		}
		if(message && !hasError)
			{
			   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
			   		showSaveResultBox(returnData, message, 'AdminHome.html');
			   	else
			   		showSaveResultBox(returnData, message, successUrl);
			}
		else if(hasError)
		{
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
		}
		else
			return returnData;
		
	}
	else if (typeof(returnData) === "string")
	{
		$(formDivName).html(returnData);	
		prepareTags();
	}
	
	return false;
}

function wardZoneValidation(errorList,wardZoneType){
	
	if(wardZoneType == 'N'){
			var OperLevel1 = $("#codIdOperLevel1").val();
			var OperLevel2 = $("#codIdOperLevel2").val();
			var OperLevel3 = $("#codIdOperLevel3").val();
			var OperLevel4 = $("#codIdOperLevel4").val();
			var OperLevel5 = $("#codIdOperLevel5").val();
			
			if(OperLevel1 == '0') {
				errorList.push(getLocalMessage("workflow.form.validation.wardzone1"));
			}
			if(OperLevel2 == '0'){
				errorList.push(getLocalMessage("workflow.form.validation.wardzone2"));
			}
			if(OperLevel3 == '0'){
				errorList.push(getLocalMessage("workflow.form.validation.wardzone3"));
			}
			if(OperLevel4 == '0'){
				errorList.push(getLocalMessage("workflow.form.validation.wardzone4"));
			}
			if(OperLevel5 == '0'){
				errorList.push(getLocalMessage("workflow.form.validation.wardzone5"));
			}
	 }
	
	return errorList;
	
 }

function amountValidation(errorList, fromAmount,toAmount){
	
	var hasVlaue = false;
	if((fromAmount != '' && fromAmount != undefined) || ( toAmount != '' && toAmount != undefined) ){
		 hasVlaue = true;
	}
	if(hasVlaue){
		if(fromAmount == '' || fromAmount == undefined  || toAmount == '' || toAmount == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.enter.fromToAmount'));
		}else{
			fromAmount = parseFloat(fromAmount);
			toAmount = parseFloat(toAmount);
			if(toAmount <= fromAmount){
				errorList.push(getLocalMessage('workflow.form.validation.enter.amountRange'));
			}
		}
	}
	
	return errorList;
}

function getServiceCode(){
	var serviceCode =  $("#serviceId :selected").attr('code');
	var deptCode =  $("#departmentId :selected").attr('code');
	var auditDeptWiseFlag = $("#auditDeptWiseFlag").val();
	if(serviceCode=="WOA"){
	$('#sourceOfFundDiv').show();
	$('#schemeNames').show();
	$('#workflowBaseDeptDiv').hide();
	}else if(serviceCode=="LQE"){
		$('#workflowBaseDeptDiv').show();
		$('#sourceOfFundDiv').hide();//because same path use for binding value
		$('#sourceCode').val('0');
		$('#schemeNames').hide();
	}else if(serviceCode=="CAE" || serviceCode=="FMC" || serviceCode == "EGR" || serviceCode == "CGF" || serviceCode == "MSC" || serviceCode == "DPR"  || serviceCode == "FPO" || serviceCode == "FAE" || serviceCode == "ABS"){
		$('#sfacMasterDiv').show();
		$('#vehMainteinedBy').hide();
	}
	
	
	else if(serviceCode== "IARS" || deptCode=="CMT" || (auditDeptWiseFlag == "Y" && deptCode == "AD")){
		 $("#workdeprtIdByDept").show(); 
		
	}
	else{
		$('#sourceCode').val('0');
		$('#sourceOfFundDiv').hide();
		$('#schemeNames').hide();
		$('#workflowBaseDeptDiv').hide();
		$('#vehMaintainBy').val('0');
		$('#vehMainteinedBy').hide();
		$('#sfacMasterDiv').hide();
	}
}


function getSchemeNames(obj) {
	
	var requestData = {
		"sourceCode" : $(obj).val()
	}
	$('#schemeId').html('');
	$('#schemeId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('WorkFlowType.html?schemeNames', 'post',
			requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#schemeId').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#schemeId').trigger("chosen:updated");
}

function changeWardZone(){
	$('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
	 $('#zone-ward').hide();
}

/* Commented as per Pooja Maske
$('#serviceId').on('change', function() {
	var serviceCode =  $("#serviceId :selected").attr('code');
	var requestData = {
		"serviceCode" : serviceCode
	}
	$('#masId').html('');
	$('#masId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	//if (serviceCode =='CAE' || serviceCode =='FPO' || serviceCode == 'MSC' || serviceCode == 'DPR'){
	var response = __doAjaxRequest('WorkFlowType.html?getMasterDetail', 'post',
			requestData, false, 'html');
	var prePopulate = JSON.parse(response);
	if (serviceCode =='CAE' || serviceCode =='FAE'){
	$.each(prePopulate, function(index, value) {
		$('#masId').append(
				$("<option></option>").attr("value", value.id).text(
						(value.name)+' - '+(value.iaName)));
	});
	}else{
		$.each(prePopulate, function(index, value) {
			
			$('#masId').append(
					$("<option></option>").attr("value", value.id).text(
							(value.name)));
		});
	}
	$('#masId').trigger("chosen:updated");
	//}
	
});
	*/
