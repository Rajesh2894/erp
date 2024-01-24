/**
 *  RAJENDRA BHUJBAL

 */

/*            Defect # 143163                        */
function addedrowRemarkvalidation(){
	debugger;
	var uploadFile=[];
	var uploadRemark=[];
	var remarkValid=[];
	var totalRow=$("#coloumnCount").val();
	var rowID= totalRow-1;
	
	if(totalRow > 1){
		for (var i=1; i<=rowID; i++){
			 //uploadFile.push($('#scrutinyDocs'+i+'.attPath').val());
			var xt=document.getElementById("scrutinyDocs"+i+".attPath").value;
			 uploadFile.push(xt);
			 //uploadRemark.push($('#docRemark'+i).val());
		}
		for(j=0;j<uploadFile.length;j++){
			if(uploadFile[j] != '' && uploadRemark[j]==''){
				
				/*var remarkValidMsg= getLocalMessage('cfc.validation.remark');
				remarkValidMsg=remarkValidMsg+(j+1);
				remarkValid.push(remarkValidMsg);*/
			}
		}
		
	}
	return remarkValid;
}

function getLableValueByQuery(obj,labelId,labelValue,level)
{
	

	var data = 'labelId='+labelId+'&labelValue='+labelValue;
	
	var surl 	= 'ScrutinyLabelView.html'+'?'+'getLabelValueByQuery';

	var returnD =__doAjaxRequest(surl,'post',data,false);

	if(returnD == "")
	{
		$('#lValue'+labelId).val('');
		$('#lValue'+labelId).css({"background-color": "#F5BCA9"});
	}
	else
	{
		$('#lValue'+labelId).val(returnD);
		$('#lValue'+labelId).attr("editable", "false");		
		var tabindex = $('#lValue'+labelId).attr('tabindex');
		tabindex = parseInt(tabindex)+1;
		$('input[tabindex='+tabindex+']').focus();
		saveLableValue(obj,labelId,level);		
	}
		
}
function submitScrutinyLabels(obj,successMessage,actionParam,successUrl)
{	
	var errorList=[];
	/*var remarkValid=[];
	remarkValid= addedrowRemarkvalidation();*/
	if($("#wokflowDecision").val()==''){
		errorList.push(getLocalMessage('cfc.validation.decesion'));	
	}
	
   //#140388
   //Defect #191491
	if ($("#reamrkValidFlag").val() == 'Y') {
		if ($("#scrutinyDecisionRemark").val() == '') {
			errorList.push(getLocalMessage('cfc.validation.remark'));
		}
	}
	
	//Defect #191491
	/*if(remarkValid.length !=0){
		for(var k=0; k < remarkValid.length; k++){
			errorList.push(remarkValid[k]);
		}
	}*/
	
	$("#frmScrutinyLabel option:selected").each(function () {
		    
		   var $this = $(this);
		   if ($this.length) {
		    var selText = $this.val();
		    if(selText=='-1')
		    {
		    errorList.push(getLocalMessage('select.ans')+" "+$(this).context.parentNode.tabIndex);
		    }
		   }
		});
	
	if(errorList =='')
		{
		  return doFormAction(obj,successMessage, actionParam, true , successUrl);
		}
	else
		{
		displayErrorsOnPage (errorList);
		  return false;
		}
	

}
function saveScrutinyLabels(obj,successMessage,actionParam,successUrl)
{	
	return doFormAction(obj,successMessage, actionParam, true , successUrl);
	
}

function loadScrutinyLabel(formUrl,appId,labelId,serviceId)
{
/*
	
	var csrf_token = $('meta[name=_csrf]').attr('content');
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=formUrl;

	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='appId';
	my_tb.value=appId;
	my_form.appendChild(my_tb); 
	
	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='labelId';
	my_tb.value=labelId;
	my_form.appendChild(my_tb); 
	
	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='serviceId';
	my_tb.value=serviceId;
	my_form.appendChild(my_tb);
	
	var	my_tb_csrf=document.createElement('INPUT');
	my_tb_csrf.type='HIDDEN';
	my_tb_csrf.name= '_csrf';
	my_tb_csrf.value = csrf_token;
	my_form.appendChild(my_tb_csrf); 
	
	document.body.appendChild(my_form);
	my_form.submit();
	*/
	var requestData={'appId':appId,'labelId':labelId,'serviceId':serviceId}
	var response = __doAjaxRequest(formUrl, 'POST',requestData, false,'html');
	//$(".content").html(response);
	$(formDivName).html(response);	
		prepareTags();
	  $("html, body").animate({ scrollTop: 0 }, "slow");
	  
	
}

function submitFormByPost(action)
{
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=action;
	
	document.body.appendChild(my_form);
	my_form.submit();
}

function showHideRow(obj,id)
{
	var val = obj.value;
	
	var data = 'cmdid='+val;
	
	var url = 'TrutiPatra.html?isCompleted';
	
	var returnData =__doAjaxRequest(url,'post',data,false);
	
	if ($.isPlainObject(returnData))
	{
		var success = returnData['command']['status'];
		
		if(success)
		{
			$('#'+id).hide();
		}
		else
		{
			$('#'+id).show();
		}
	}
}

function checkUnchack(obj,id,textId)
{
	 var select = document.getElementById(id);
	 
	 if(select)
	{
		 select.checked = obj.checked;
	}
	 
	 if(obj.checked)
	 {
		 document.getElementById(textId).value="";
		 document.getElementById(textId).disabled = false;
	 }
	 else
	 {
		 document.getElementById(textId).value="";
		 document.getElementById(textId).disabled = true;
	 }
	 
}