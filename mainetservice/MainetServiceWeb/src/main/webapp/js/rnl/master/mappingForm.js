 var arrProp=[];
$(document).ready(function() {
	
	
	$("#datatables1").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : false,
	    "lengthChange": true
	});	
	
	$(document).on("change","input[type=radio][name=estateContMappingDTO\\.contractId]",function(){
		$('#estateId').val('0').trigger("chosen:updated");
		$('#propertyNo_0').val('');
		$('#usage_0').val('');
		$('#unit_0').val('');
		$('#floor_0').val('');
		$('#totalArea_0').val('');
		$('#propId_0').val('0').trigger("chosen:updated");
	})
	
	$('#estateId').chosen();
	 $('#estateId').change(function(){
		 if($('input[type=radio][name=estateContMappingDTO\\.contractId]').is(":checked") == false){
			  $('#estateId').val('0').trigger("chosen:updated");
			  errorList=[];
			  errorList.push(getLocalMessage('rnl.select.one.of.contract'));
			  showRLValidation(errorList);
		 }else{
			 	 let contractId = $('input[name="estateContMappingDTO.contractId"]:checked').val();
			     var requestData = {
			    		 "esId":$(this).val(),
			    		 "contId":contractId
			     }
				 var ajaxResponse = __doAjaxRequest('EstateContractMapping.html?propDetailsForm', 'POST', {}, false,'html');
				 $('#propTableId').html(ajaxResponse);
				 var result=__doAjaxRequest("EstateContractMapping.html?propList",'post',requestData,false,'json');
				 if(result.length!=0){
					 $('#propId_0').html('');
					 $('#propId_0').append($("<option></option>").attr("value","0").text(getLocalMessage('rnl.master.select')));
					 $.each(result, function(index, value) {
						 $('#propId_0').append($("<option></option>").attr("value",value[0]).text(value[1]));
					 });
				 }else{
					 //alert MSG
					 showAlertBoxForMsg();
				 }
				 
				 $('#propId_0').trigger("chosen:updated");
		   }
	  });
	 
	 $('#mappingsubmit').click(function(){
		    $('.alert-dismissible').hide();
		     var errorList = [];
			 errorList = validateMappingForm(errorList);
			 if (errorList.length == 0) {
				      errorList=validateProperty(errorList);
				      if(errorList.length == 0){
					    	  for (var i = 0; i < arrProp.length; i++) 
					          {
					              for (var j = i+1; j < arrProp.length; j++) 
					              {                  
					                      if (arrProp[i] == arrProp[j]) 
					                      {
					                    	  $("#dupCheckFlag").val("Y"); // means there are duplicate values
					                      }
	                               }
					          }
				    	    requestData=$('#mappingForm').serialize()+"&"+$("#workFlowSubTypeForm").serialize();
				    	    var returnData=__doAjaxRequestForSave('EstateContractMapping.html?saveform', 'post',requestData, false,'',$(this));
				    	    getSaveFormResponse(returnData);
						}else{
							showRLValidation(errorList);
						 }	 
			 }else{
				showRLValidation(errorList);
			 }
	  });
	 
	 /*Reset Form*/
	 $("#resetEstate").click(function(){
			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading('EstateContractMapping.html?form', {}, 'html',divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		});
	 
 });

function showAlertBoxForMsg(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">All Properties Under this Estate are booked</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	 $('#toDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}
function closeAlertForm(){
	var childDivName	=	'.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}




function showDetails(obj) {
	   //count= $(obj).prop('id').substr($(obj).prop('id').indexOf("_") + 1);
	if($(obj).val() != '0'){
	   arr=$(obj).prop('id').split('_');
	   var requestData ={"propId":$(obj).val()}
				$('#propertyNo_'+arr[1]).val('');
				$('#usage_'+arr[1]).val('');
				$('#unit_'+arr[1]).val('');
				$('#floor_'+arr[1]).val('');
				$('#totalArea_'+arr[1]).val('');
				arrProp.push($(obj).val());
				var result=__doAjaxRequest("EstateContractMapping.html?propDetails",'POST',requestData,false,'json');
				$('#propertyNo_'+arr[1]).val(result.propertyNo);
				$('#usage_'+arr[1]).val(result.usage);
				$('#unit_'+arr[1]).val(result.unit);
				$('#floor_'+arr[1]).val(result.floor);
				$('#totalArea_'+arr[1]).val(result.totalArea);	
	   }		
  }


function reOrderSequenceForContract() {

	$('.appendableClass').each(function(i) {
        $(this).find("select:eq(0)").attr("id","propId_"+i);
		$(this).find("input:text:eq(1)").attr("id", "propertyNo_"+i);
		$(this).find("input:text:eq(2)").attr("id", "usage_"+i);
		$(this).find("input:text:eq(3)").attr("id", "unit_"+i);
		$(this).find("input:text:eq(4)").attr("id", "floor_"+i);
		$(this).find("input:text:eq(5)").attr("id", "totalArea_"+i);
		$(this).find("select:eq(0)").attr("name","estateContMappingDTO.contractPropListDTO["+i+"].propId");
		$(this).find("input:text:eq(1)").attr("name", "estateContMappingDTO.contractPropListDTO["+i+"].propertyNo");
		$(this).find("input:text:eq(2)").attr("name", "estateContMappingDTO.contractPropListDTO["+i+"].usage");
		$(this).find("input:text:eq(3)").attr("name", "estateContMappingDTO.contractPropListDTO["+i+"].unit");
		$(this).find("input:text:eq(4)").attr("name", "estateContMappingDTO.contractPropListDTO["+i+"].floor");
		$(this).find("input:text:eq(5)").attr("name", "estateContMappingDTO.contractPropListDTO["+i+"].totalArea");
		$('#propId_'+i).trigger("chosen:updated");
    });
	
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
  
function validateProperty(errorList){
	
  $('.appendableClass').each(function(i) {
			 row=i+1;
			 var propId_ = $("#propId_"+i).val();
			 if(propId_ =="" || propId_ =='0'  || propId_ == undefined ){
				 errorList.push(getLocalMessage('rnl.estate.prop.name'));
			 }
		  });
		return errorList;

 } 

function validateEstate(errorList){
	
		 var estateId = $("#estateId").val();
		 if(estateId =="" || estateId =='0'  || estateId == undefined ){
			 errorList.push(getLocalMessage('rl.property.master.estate.validate.msg'));
		  }
		 return errorList;

	 } 
function checkPropertyType(){
	addProperty = __doAjaxRequest('EstatePropMas.html?checkAddProperty', 'POST', 'esId='+$('#estateId').val(), false, 'json');
    return addProperty;
}

function validateMappingForm(errorList){
	
	if($('input[type=radio][name=estateContMappingDTO\\.contractId]').is(":checked") == false){
		 errorList.push(getLocalMessage('rnl.select.atleast.one.contractNo'));
	}
	validateEstate(errorList)
	return errorList;
}

function showAddPropertyValidation(warnMsg){
	
	message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	$(childDivName).html(message);
	$(childDivName).show();
	showModalBox(childDivName);
	return false;
	
} 

function getSaveFormResponse(returnData){
	successUrl='EstateContractMapping.html';
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
		$('.content-page').html(returnData);	
		prepareTags();
	}
	else 
	{
		alert("Invalid datatype received : " + returnData);
	}
	
	return false;
}

function showContract(contId,type){	
	
    var requestData = 'contId='+contId+'&type='+type+'&showForm=map';
	var ajaxResponse	=	doAjaxLoading('ContractAgreement.html?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
	
	if(type == 'V'){
		 $("#noa_header").show();
		 $("#ContractAgreement :input").prop("disabled", true);
		 $('.addCF3').attr('disabled',true);
		 $('.addCF4').attr('disabled',true);
		 $('.addCF5').attr('disabled',true);
		 $('.addCF2').attr('disabled',true);
		 $('.remCF2').attr('disabled',true);
		 $('.remCF3').attr('disabled',true);
		 $('.remCF4').attr('disabled',true);
		 $('.remCF5').attr('disabled',true);
		 $(".backButton").removeProp("disabled");
		// $("#backButton").removeProp("disabled");
	}
	
}

