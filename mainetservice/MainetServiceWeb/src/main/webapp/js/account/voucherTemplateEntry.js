$( document ).ready(function() {
	
	 $("#voucherIdFin").val($("#hiddenVoucherId").val());

	$("#financialId").change(function(e) {
		$("#financialTemp").removeClass("hide");
	});
	
	$("#saveDiv").hide();

	$("#mappingType").change(function(e) {
	 if ($('#mappingType option:selected').attr('code') == "PN" || $('#mappingType option:selected').attr('code') =="pn")
	 {
		$("#financialDiv").addClass("hide");
		$("#financialYear").addClass("hide");
		$("#saveDiv").hide();
		$("#voucherMode").val("P");
		$("#voucherTemp").removeClass("hide");
		$("#financialTemp").addClass("hide");

	}
	else if($('#mappingType option:selected').attr('code') == "FYW" || $('#mappingType option:selected').attr('code') =="fyw")
	{
	    $("#financialDiv").hide();
		$("#financialYear").removeClass("hide");
		$("#permanentDiv").addClass("hide");
		$("#saveDiv").hide();
		$("#voucherMode").val("F");
		$("#tempDiv").hide(); 
		$("#voucherTemp").addClass("hide");
		$("#voucherTemp").show();
		}
	else
		{
		$("#tempDiv").show();
		$("#financialDiv").addClass("hide");
		$("#financialYear").addClass("hide");
		$("#permanentDiv").addClass("hide");
		$("#saveDiv").hide();
		$("#voucherTemp").addClass("hide");
		$("#financialTemp").addClass("hide");
		$("#financialTemp").show();
		}
	
    });
 
});


function showPermenantTempData(obj) { 
	var voucherId = $("#voucherId").val();
	var errorList = [];
	
	if(voucherId == null ||voucherId=='') {
		errorList.push(getLocalMessage('account.select.voucher.template'));  
	}
	if(errorList.length == 0){
		
	var url = "VoucherTemplateMaster.html?getPermanentList";
	
    var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);

	var	returnData =__doAjaxRequest(url, 'post', requestData, false,'', obj);
		
		var divName = ".content-page";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		
		$("#permanentDiv").removeClass("hide");
		$("#voucherTemp").removeClass("hide");
		$("#mainDiv").removeClass("hide");
		$("#saveDiv").show();
		$("#voucherClass select").prop("disabled", true);
        $("#saveButton").hide();
		$("#clearButton").hide();

		$('.voucherClass').each(function(i) {

			 $('#voutStatusFlg'+i).prop("disabled", true);
			 $('#accHeadId'+i).prop("disabled", true);
			 $('#addVoucher'+i).prop("disabled", true);
			 $('#deleteVoucher'+i).prop("disabled", true);
			 
		 });
		return false;
    }else{
	       $("#permanentDiv").addClass("hide");
         }
	}


function showFinanceTempData(obj) { 
	
	var voucherId = $("#voucherIdFin").val();
	var financialId = $("#financialId").val();
	var errorList = [];
	
	if(voucherId == null ||voucherId=='') {
		errorList.push(getLocalMessage('account.select.voucher.template'));  
	}
	if(financialId == null ||financialId=='') {
		errorList.push(getLocalMessage('account.select.financialYear'));  
	}
	if(errorList.length > 0){ 
	    showVoucherError(errorList);
	    return false;
	}else
	{
		
	var url = "VoucherTemplateMaster.html?getFinanceList";
	
    var	formName =	findClosestElementId(obj, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
	var	returnData =__doAjaxRequest(url, 'post', requestData, false,'', obj);
		var divName = ".content-page";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);

		$("#financialYear").removeClass("hide");
		$("#financialDiv").removeClass("hide");
		$("#financialTemp").removeClass("hide");
		$("#saveDiv").show();
		$("#saveButton").hide();
		$("#clearButton").hide();
		$("#financeClass select").prop("disabled", true);

        $('.financeClass').each(function(i) {
			 $('#voutStatusFlgFin'+i).prop("disabled", true);
			 $('#accHeadIdFin'+i).prop("disabled", true);
			 $('#addVoucherFin'+i).prop("disabled", true);
			 $('#deleteVoucherFin'+i).prop("disabled", true);
			 
		 });
		return false;

    }
}

function editForm(){
	 $('select').attr("disabled", false);
	
	 $('.voucherClass').each(function(i) {
		 var flagCode=$('#voutStatusFlg'+i).val();
		 
		  if(flagCode=="I"){
			 $('#voutStatusFlg'+i).prop("disabled", true);
			 $('#accHeadId'+i).prop("disabled", true);
		     }
		     $('#addVoucher'+i).prop("disabled", false);
			 $('#deleteVoucher'+i).prop("disabled", true);
	         });
	 
	 $('.financeClass').each(function(i) {
		  var flagCodeFin=$('#voutStatusFlgFin'+i).val();
		  if(flagCodeFin=="I"){
			 $('#voutStatusFlgFin'+i).prop("disabled", true);
			 $('#accHeadIdFin'+i).prop("disabled", true);
		     }
		     $('#addVoucherFin'+i).prop("disabled", false);
			 $('#deleteVoucherFin'+i).prop("disabled", true);
         	});
	 
	 $("#editButton").hide();
	 $("#clearButton").show();
	 $("#saveButton").show();
}

    function saveVoucherMaster(element)
    {
    	 var errorList = [];
         var activeStatusFlgCounterList = [];
    	 var primaryCode;
    	 var secondaryCode;
    	 var prefix;
    	 var fundCode;
    	 var functionCode;
    	 var statusFlag;
         var activeStatusFlgCounter = 0;
    	
    	 if ($('#mappingType').val() == "0") {
    	 errorList.push(getLocalMessage("account.select.mappingType"));
    	 }
    	 if ($('#mappingType option:selected').attr('code') == "PN" || $('#mappingType option:selected').attr('code') =="pn") {
    	 $('.voucherClass').each(function(i) {

        	 var accHeadId=$("#accHeadId"+i).val();
             var statusFlag=$("#voutStatusFlg"+i).val();
			 
		      $("#hiddenSacHeadId"+i).val($("#accHeadId"+i).val());
			
			 $("#hiddenVoutStatusFlg"+i).val($("#voutStatusFlg"+i).val());
			  
			 if(statusFlag=="A"){
				activeStatusFlgCounter ++;
		      }

        	 if(accHeadId==0 ||accHeadId=='')
     		{
        		 errorList.push(getLocalMessage("account.select.acchead"));
     		}
        	 
        	 if(statusFlag==0 ||statusFlag=='')
      		{
         		 errorList.push(getLocalMessage("account.select.status.flag"));
      		}

    	 });
    	 }
    	 else
    		 {
    		 var finId=$('#financialId').val();
    		 
    		 if(finId==0 ||finId=='')
    		{
    		 errorList.push(getLocalMessage("account.select.financialYear"));
    		}
    		 
    		 else
    			 {
    	    $('.financeClass').each(function(i) {

             var accHeadIdFin=$("#accHeadIdFin"+i).val();
             var statusFlagFin=$("#voutStatusFlgFin"+i).val();
                
             $("#hiddenSacHeadIdFin"+i).val($("#accHeadIdFin"+i).val());

             $("#hiddenVoutStatusFlgFin"+i).val($("#voutStatusFlgFin"+i).val());
             
   			 if(statusFlagFin=="A"){
   				activeStatusFlgCounter ++;
   		      }

           	 if(accHeadIdFin==0 ||accHeadIdFin=='')
        		{
           		 errorList.push(getLocalMessage("account.select.acchead"));
        		}
           	 
           	 if(statusFlagFin==0 ||statusFlagFin=='')
         		{
            		 errorList.push(getLocalMessage("account.select.status.flag "));
         		}
            	 });
    			 }
    		 } 

    	 if(activeStatusFlgCounter > 1){
    		 errorList.push(getLocalMessage("account.mulActive.acc.not.add"));
    	 }
    	 
    	 if(errorList.length > 0){ 
		    	showVoucherError(errorList);
		    return false;
		    }
    	 
	    var	formName =	findClosestElementId(element, 'form');
	    var theForm	=	'#'+formName;
	    var requestData = __serializeForm(theForm);
	    var url	=	$(theForm).attr('action');
	   var status=__doAjaxRequestForSave(url, 'post', requestData, false,'', element); 

	    var obj=$(status).find('#saveSuccess'); 
	    var emptyFlag=$(status).find('#isempty'); 
	    if(emptyFlag.val()=='Y')
	    	{
	    	showEmptyBox();
	    	return false;
	    	}
	    if(obj.val()=='Y')
	    	{
	    	showConfirmBox();
	    	}
	     else
	    	{
	    	 $(".content-page").html(status);
		     $(".content-page").show();
	    	}
    }
    function showConfirmBox()
	{
		  var	errMsgDiv		='.msg-dialog-box';
			var message='';
			var cls = 'Proceed';
			message	+='<h4 class="text-center padding-10 text-info">Voucher Template Entry Master Submitted Successfully</h4>';
			message	+='<div class="text-center padding-bottom-10">'+	
			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\'    '+ 
			' onclick="proceed()"/>'+	
			'</div>';
			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showModalBox(errMsgDiv);
	
  }
    function showEmptyBox()
	{
		  var	errMsgDiv		='.msg-dialog-box';
			var message='';
			var cls = 'Proceed';
			message	+='<h4 class="text-center padding-10 text-info">No Records For save</h4>';
			message	+='<div class="text-center padding-bottom-10">'+	
			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
			' onclick="proceed()"/>'+	
			'</div>';
			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showModalBox(errMsgDiv);
	
  }
	function proceed() {
		window.location.href = 'VoucherTemplateMaster.html'; 
	}
	
	 function showVoucherError(errorList)
	 {
		 var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
            errMsg += '</ul>';
             $('#errorId').html(errMsg);
			$('#errorDivId').show();	 
	 }
	
	 $("#permanentDiv").on("click", '.addVoucher',function(e) {
					var errorList = [];
					var accHeadIdCounter = 0;
					var voutStatusFlgCounter = 0;
					var row=0;
					 var noOfTotRow = 0;
					$('.voucherClass').each(function(i) {
						row = i;		
					
						var accHeadId = $("#accHeadId" + i).val();
						var voutStatusFlg = $("#voutStatusFlg" + i).val();
					
						if(accHeadId== ''){
							if(accHeadIdCounter == 0) {
								 errorList.push(getLocalMessage('account.select.acchead')); 
								 accHeadIdCounter++;
							}
						}
						if(voutStatusFlg== ''){
							if(voutStatusFlgCounter == 0) {
								 errorList.push(getLocalMessage('please select status')); 
								 voutStatusFlgCounter++;
							}
						}
							
					});

					if(errorList.length > 0){ 
				    	showVoucherError(errorList);
				    return false;
				    }
					var content = $(this).closest('tr').clone();
				  	$(this).closest("tr").after(content);
				  	var clickedIndex = $(this).parent().parent().index() - 1;	
				  	content.find("select").val('');
                    content.find("input:hidden:eq(0)").attr("value", "");
					content.find("input:hidden:eq(1)").attr("value", "");
					content.find("input:hidden:eq(2)").attr("value", "");
					//enable -disable
					var indexCount = (clickedIndex+1);

					reOrderVoucherGrid();
					//e.preventDefault();
					
					$("#accHeadId"+indexCount).prop("disabled",false);
					$("#voutStatusFlg"+indexCount).prop("disabled",false);
					
		});
		
		
	 $("#financialDiv").on("click", '.addVoucherFin',function(e) {
			var errorList = [];
			var accHeadIdCounter = 0;
			var voutStatusFlgCounter = 0;
		    
			$('.financeClass').each(function(i) {
				var accHeadId = $("#accHeadIdFin" + i).val();
				var voutStatusFlg = $("#voutStatusFlgFin" + i).val();
			
				if(accHeadId== ''){
					if(accHeadIdCounter == 0) {
						 errorList.push(getLocalMessage('account.select.acchead')); 
						 accHeadIdCounter++;
					}
				}
				if(voutStatusFlg== ''){
					if(voutStatusFlgCounter == 0) {
						 errorList.push(getLocalMessage('account.select.status')); 
						 voutStatusFlgCounter++;
					}
				}
			});
			if(errorList.length > 0){ 
		    	showVoucherError(errorList);
		    return false;
		    }
		  	var content = $(this).closest('tr').clone();
		  	$(this).closest("tr").after(content);
		  	var clickedIndex = $(this).parent().parent().index() - 1;	
		  	content.find("select").val('');
            content.find("input:hidden:eq(0)").attr("value", "");
			content.find("input:hidden:eq(1)").attr("value", "");
			content.find("input:hidden:eq(2)").attr("value", "");
			
			
			reOrderFinVoucherGrid();
			e.preventDefault();
});
	 
	 $("#permDiv").on("click", '.deleteVoucher',function(e) {
		 
		 var errorList = [];
		 var clickedRow = $(this).parent().parent().index();
		 var firstRow=clickedRow--;
		  var voutStatusFlg = $("#voutStatusFlg" + clickedRow).val();
		  
		 if(voutStatusFlg== 'I'){
			 errorList.push(getLocalMessage('account.inactiveHead.not.delete')); 
		  }	
		 if(errorList.length > 0){ 
		    	showVoucherError(errorList);
		    return false;
		    }
		 
	    	 var rowCount = $('#permDiv tr').length;
			if(rowCount<=2){
				return false;
			}
			 $(this).parent().parent().remove();
			// noOfTotRow--;
			 reOrderVoucherGrid();
			 e.preventDefault();
	 });

	 $("#finDiv").on("click", '.deleteVoucherFin',function(e) {
		 
		 var errorList = [];
		 var clickedRow = $(this).parent().parent().index();
		 var firstRow=clickedRow--;
		  var voutStatusFlg = $("#voutStatusFlgFin" + clickedRow).val();
		  
		 if(voutStatusFlg== 'I'){
			 errorList.push(getLocalMessage('account.inactiveHead.not.delete')); 
		  }	
		 if(errorList.length > 0){ 
		    	showVoucherError(errorList);
		    return false;
		    }
		 var rowCount = $('#finDiv tr').length;
			if(rowCount<=2){
				return false;
			}
			 $(this).parent().parent().remove();					
			 reOrderFinVoucherGrid();
			 e.preventDefault();
		
	 });
		function reOrderVoucherGrid(){
			$('.voucherClass').each(function(i) {
				//Ids
				$(this).find("select:eq(0)").attr("id", "accHeadId"+(i));
				$(this).find("select:eq(1)").attr("id", "voutStatusFlg" + (i));
				
				$(this).find("input:hidden:eq(0)").attr("id", "voutid" + (i)); 
				$(this).find("input:hidden:eq(1)").attr("id", "hiddenSacHeadId"+(i));  
				$(this).find("input:hidden:eq(2)").attr("id", "hiddenVoutStatusFlg" + (i)); 
				
				//names
				$(this).find("select:eq(0)").attr("name", "voucherTemplateList[" + (i) + "].tbAcSecondaryheadMaster.sacHeadId");
			    $(this).find("select:eq(1)").attr("name", "voucherTemplateList[" + (i) + "].voutStatusFlg");
				
			    $(this).find("input:hidden:eq(0)").attr("name", "voucherTemplateList[" + (i) + "].voutId");
			    $(this).find("input:hidden:eq(1)").attr("name", "voucherTemplateList[" + (i) + "].hiddenSacHeadId");
			    $(this).find("input:hidden:eq(2)").attr("name", "voucherTemplateList[" + (i) + "].hiddenVoutStatusFlg");
			    
			    $(this).find(".addVoucher").attr("id", "addVoucher"+(i));
			    $(this).find(".deleteVoucher").attr("id", "deleteVoucher"+(i));
		    	      
	            $(this).closest("tr").attr("id", "voucherClass" + (i)); 
			
			});
		}
		
		
		function reOrderFinVoucherGrid(){
			$('.financeClass').each(function(i) {
				//Ids
				$(this).find("select:eq(0)").attr("id", "accHeadIdFin"+(i));
				$(this).find("select:eq(1)").attr("id", "voutStatusFlgFin" + (i));
				
				$(this).find("input:hidden:eq(0)").attr("id", "voutIdFin" + (i)); 
				$(this).find("input:hidden:eq(1)").attr("id", "hiddenSacHeadIdFin"+(i));  
				$(this).find("input:hidden:eq(2)").attr("id", "hiddenVoutStatusFlgFin" + (i)); 

				//names
				$(this).find("select:eq(0)").attr("name", "voucherFinanceList[" + (i) + "].tbAcSecondaryheadMaster.sacHeadId");
			    $(this).find("select:eq(1)").attr("name", "voucherFinanceList[" + (i) + "].voutStatusFlg");
				
			    $(this).find("input:hidden:eq(0)").attr("name", "voucherFinanceList[" + (i) + "].voutId");
			    $(this).find("input:hidden:eq(1)").attr("name", "voucherFinanceList[" + (i) + "].hiddenSacHeadId");
			    $(this).find("input:hidden:eq(2)").attr("name", "voucherFinanceList[" + (i) + "].hiddenVoutStatusFlg");
			  
			    $(this).find(".addVoucherFin").attr("id", "addVoucherFin"+(i));
			    $(this).find(".deleteVoucherFin").attr("id", "deleteVoucherFin"+(i));
		    	      
	            $(this).closest("tr").attr("id", "financeClass" + (i)); 
			
			});
		}

		 