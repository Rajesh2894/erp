/**
 * apurva.salgaonkar
 */

$(document).ready(function(){
	  $(".display-hide").not(".Non-Commercial").hide();
      $(".Non-Commercial").show();
      
  	var dateFields = $('.dateClass');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
     
if($("#hiddeMode").val()=="V" || $("#hiddeMode").val()=="E" )	
 {
     if($("input[name='contractMastDTO.contMode']:checked").val()=="C"){
        $(".display-hide").not(".Commercial").hide();
         $(".Commercial").show();
      
     }
		if($("input[name='contractMastDTO.contMode']:checked").val()=="N"){
          $(".display-hide").not(".Non-Commercial").hide();
         $(".Non-Commercial").show();
         $(".Commercial input:text").val("");
         $(".Commercial select").val("");
     }
		

		
 }
	
	$('.ContractMode').click(function(){	
        if($(this).attr("value")=="C"){
             $(".display-hide").not(".Commercial").hide();
            $(".Commercial").show();
         
        }
		if($(this).attr("value")=="N"){
             $(".display-hide").not(".Non-Commercial").hide();
            $(".Non-Commercial").show();
            $(".Commercial input:text").val("");
            $(".Commercial select").val("");
        }
    });
	
});

$( document ).ready(function() {
	$(".lessdatepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		maxDate: '-0d',
		changeYear: true,
	});
				
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true
	});
	
	$(".datepickerResulation").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		maxDate: '-0d',
		changeYear: true,
		   onSelect: function(selected) {
				$(".datepickerTender").datepicker("option","minDate", selected);
		      } 
	});
	
	$(".datepickerTender").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		maxDate: '-0d',
		changeYear: true,
		   onSelect: function(selected) {
				$(".lessthancurrdatefrom").datepicker("option","minDate", selected);	
				checkTenderDate();
		      } 
	});
	
	$('.lessthancurrdateto').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
	    onSelect: function(selected) {
	    $(".Insdatepicker").datepicker("option","maxDate", selected);	
		checkToDate();
	      } 
	    
	});
	
	$('.lessthancurrdatefrom').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
	    onSelect: function(selected) {
			$(".lessthancurrdateto").datepicker("option","minDate", selected);	
			$(".Insdatepicker").datepicker("option","minDate", selected);	
			checkFromDate();
	      } 
	});
	if($("#hiddeMode").val()=="V" || $("#hiddeMode").val()=="E" )	
	 {
		var resolutionDate =  $("#resolutionDate").val();
		$(".datepickerTender").datepicker("option","minDate", resolutionDate);
		var tenderDate =  $("#tenderDate").val();
		$(".lessthancurrdatefrom").datepicker("option","minDate", tenderDate);
		var fromDate =  $("#contractFromDate").val();
		$(".lessthancurrdateto").datepicker("option","minDate", fromDate);
	 }
	
	if($("#hiddeMode").val()=="C")	
	 {
		$( "#AgreementDate" ).datepicker( "setDate", new Date());	
	 }
	
	 var countdat=0;
	$('body').on('focus',".Insdatepicker", function(){
		$('.Insdatepicker').datepicker({
		    dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true
		});
		var fromDate =  $("#contractFromDate").val();
		var toDate =  $("#contractToDate").val();
		if(toDate!='' && fromDate!='' && countdat==0){
			countdat++;
	    $(".Insdatepicker").datepicker("option","minDate", fromDate);	
		$(".Insdatepicker").datepicker("option","maxDate", toDate);	}
		}); 
	$('body').on('focus',".hasNumber", function(){
		$(".hasNumber").keyup(function(event) {
	    this.value = this.value.replace(/[^0-9]/g,'');
	     });
		}); 
	});	


function OnChangePrimaryVender(cnt) {
	$('.contpPrimary').attr('checked', false);	
	$(".contpPrimary").attr("value", "N");
	$("#contp2Primary"+cnt).prop("checked", true);
	$("#contp2Primary"+cnt).attr("value", "Y");
}


function getEmpBasedOnDesgnation(obj) {
	var url = "ContractAgreement.html?getEmpBasedOnDesgnation";
	var degId = $(obj).val();
	var postdata = "degID="+degId;
	
	var json = __doAjaxRequest(url,'POST', postdata, false, 'json');
	 $('#representBy option').remove();
	var  optionsAsString="<option value='0'>select Employee</option>";
	 for (var j = 0; j < json.length; j++){

			optionsAsString += "<option value='" +json[j].lookUpId+"'>" + json[j].descLangFirst+"</option>";
              }
	$('#representBy').append(optionsAsString);
}

function getAllTaxesBasedOnDept(obj) {
	var url = "ContractAgreement.html?getAllTaxesBasedOnDept";
	var deptId = $(obj).val();
	var postdata = "deptId="+deptId;
	
	var json = __doAjaxRequest(url,'POST', postdata, false, 'json');
	 $('#taxId option').remove();
	var  optionsAsString="<option value='0'>select Tax</option>";
	 for (var j = 0; j < json.length; j++){

			optionsAsString += "<option value='" +json[j].lookUpId+"'>" + json[j].descLangFirst+"</option>";
              }
	$('#taxId').append(optionsAsString);
}

function getVenderNameOnVenderType(count) {	
		var url = "ContractAgreement.html?getVenderNameOnVenderType";
		var venTypeId = $("#venderType"+count).val();
		var postdata = "venTypeId="+venTypeId;
		
		var json = __doAjaxRequest(url,'POST', postdata, false, 'json');
		 $('#vendorId'+count+' option').remove();
		var  optionsAsString="<option value='0'>Select Vendor</option>";
		 for (var j = 0; j < json.length; j++){

				optionsAsString += "<option value='" +json[j].lookUpId+"'>" + json[j].descLangFirst+"</option>";
	              }
		$('#vendorId'+count).append(optionsAsString);
	}

function deleteCompleteRow(uploadId,uploadType) {
	if(uploadType=="UW"){
		if($("#customFields3 tr").length != 2)
		{
		 deleteCompletetRowAjax(uploadId);
		} 	 }
	if(uploadType=="V"){
		if($("#customFields5 tr").length != 2)
		{
		  deleteCompletetRowAjax(uploadId);
		}     }
	if(uploadType=="VW"){
		if($("#customFields4 tr").length != 2)
		{
			deleteCompletetRowAjax(uploadId);

		}   }
}

function deleteCompletetRowAjax(uploadId)
{
	var url = "ContractAgreement.html?deleteCompleteRow";
	var postdata = "uploadId="+uploadId;
	var json = __doAjaxRequest(url,'POST', postdata, false);
}

function reorderULB()
{
	$('.appendableClass').each(function(i) {
		 //Ids
		var inc=i+1;	
		var p1=1+i;
		$(this).find("input:text:eq(0)").attr("id", "contp1Name" + (inc));
	    $(this).find("input:text:eq(1)").attr("id", "contp1Address" + (inc));
	    $(this).find("input:text:eq(2)").attr("id", "contp1ProofIdNo" + (inc));
	    $(this).find("input:hidden:eq(0)").attr("id", "ULBType" + (inc));
	    $(this).find("input:hidden:eq(1)").attr("id", "presentRow" + (inc));
	    //names
		$(this).find("input:text:eq(0)").attr("name", "contractMastDTO.contractPart1List[" + (inc) + "].contp1Name");
		$(this).find("input:text:eq(1)").attr("name", "contractMastDTO.contractPart1List[" + (inc) + "].contp1Address");
		$(this).find("input:text:eq(2)").attr("name", "contractMastDTO.contractPart1List[" + (inc) + "].contp1ProofIdNo");
		$(this).find("input:hidden:eq(0)").attr("name", "contractMastDTO.contractPart1List[" + (inc) + "].contp1Type");
		$(this).find("input:hidden:eq(1)").attr("name", "contractMastDTO.contractPart1List[" + (inc) + "].active");
		$(this).find("a:eq(0)").attr("onclick", "fileUpload("+(p1)+",'UW')");
		$(this).find("a:eq(1)").attr("onclick", "deleteCompleteRow("+(p1)+",'UW')");
		});
}

function reorderTermCon()
{
	$('.appendableTermConClass').each(function(i) {
		 //Ids
		var count=i+1;
		$(this).find("textarea:eq(0)").attr("id", "termCon" + (i));
		 $(this).find("input:hidden:eq(0)").attr("id", "presentRow" + (i));
		 $(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
	    //names
		$(this).find("textarea:eq(0)").attr("name", "contractMastDTO.contractTermsDetailList[" + (i) + "].conttDescription");
	    $(this).find("input:hidden:eq(0)").attr("name","contractMastDTO.contractTermsDetailList[" + (i) + "].active");
	    $(this).find("input:text:eq(0)").attr("value",count);

		});
}

function reorderVender()
{
	var countven=0;
	$('.appendableVenClass').each(function(i) {
		 //Ids
		var p2=11+i;
		$(this).find("select:eq(0)").attr("id", "venderType" + (i));
		$(this).find("select:eq(1)").attr("id", "vendorId" + (i));
	    $(this).find("input:text:eq(0)").attr("id", "venderName" + (i));
	    $(this).find("input:radio:eq(0)").attr("id", "contp2Primary" + (i));
	    $(this).find("input:hidden:eq(0)").attr("id", "contp2Type" + (i));
	    $(this).find("input:hidden:eq(1)").attr("id", "presentRow" + (i));
	    
	    //names
		$(this).find("select:eq(0)").attr("name", "contractMastDTO.contractPart2List[" + (i) + "].contp2vType");
		$(this).find("select:eq(1)").attr("name", "contractMastDTO.contractPart2List[" + (i) + "].vmVendorid");
		$(this).find("input:text:eq(0)").attr("name", "contractMastDTO.contractPart2List[" + (i) + "].contp2Name");
	    $(this).find("input:radio:eq(0)").attr("name","contractMastDTO.contractPart2List[" + (i) + "].contp2Primary");
	    $(this).find("input:hidden:eq(0)").attr("name","contractMastDTO.contractPart2List[" + (i) + "].contp2Type");
	    $(this).find("input:hidden:eq(1)").attr("name","contractMastDTO.contractPart2List[" + (i) + "].active");
	    
		$(this).find("select:eq(0)").attr("onchange", "getVenderNameOnVenderType("+ (i)+")" );
		$(this).find("input:radio:eq(0)").attr("onchange","OnChangePrimaryVender("+ (i)+")");
		$(this).find("a:eq(0)").attr("onclick", "fileUpload("+(p2)+",'V')");
		$(this).find("a:eq(1)").attr("onclick", "deleteCompleteRow("+(p2)+",'V')");
		countven++;
		});
	
	$('.appendableWitClass').each(function(i) {
		 //Ids
		var countWit=i+countven;	
		var p2w=111+i;
		$(this).find("input:text:eq(0)").attr("id", "contp2Name" + (countWit));
	    $(this).find("input:text:eq(1)").attr("id", "contp2Address" + (countWit));
	    $(this).find("input:text:eq(2)").attr("id", "contp2ProofIdNo" + (countWit));
	    $(this).find("input:hidden:eq(0)").attr("id", "contp2Type" + (countWit));
	    $(this).find("input:hidden:eq(1)").attr("id", "presentRow" + (countWit));
	    //names
		$(this).find("input:text:eq(0)").attr("name", "contractMastDTO.contractPart2List[" + (countWit) + "].contp2Name");
		$(this).find("input:text:eq(1)").attr("name", "contractMastDTO.contractPart2List[" + (countWit) + "].contp2Address");
		$(this).find("input:text:eq(2)").attr("name", "contractMastDTO.contractPart2List[" + (countWit) + "].contp2ProofIdNo");
	    $(this).find("input:hidden:eq(0)").attr("name","contractMastDTO.contractPart2List[" +(countWit) + "].contp2Type");
	    $(this).find("input:hidden:eq(1)").attr("name","contractMastDTO.contractPart2List[" +(countWit) + "].active");
		$(this).find("a:eq(0)").attr("onclick", "fileUpload("+(p2w)+",'VW')");
		$(this).find("a:eq(1)").attr("onclick", "deleteCompleteRow("+(p2)+",'V')");

		});

}

//Save and Validation
function saveContractAgreementForm(element) {
	 var errorList = [];	
	 
	 	var AgreementDate = $.trim($("#AgreementDate").val());
	    if(AgreementDate==0 || AgreementDate=="")
	    errorList.push("Please select Agreement Date");
	    
	    var TenderNo = $.trim($("#TenderNo").val());
	    if(TenderNo==0 || TenderNo=="")
	    errorList.push("Please Enter Tender No");
	    
	    var tenderDate = $.trim($("#tenderDate").val());
	    if(tenderDate==0 || tenderDate=="")
		errorList.push("Please Select Tender Date");
	    
	    var resulationNo = $.trim($("#resulationNo").val());
	    if(resulationNo==0 || resulationNo=="")
		errorList.push("Please Enter Resulation No");
	    
	    var ContractType = $.trim($("#ContractType").val());
	    if(ContractType==0 || ContractType=="")
		errorList.push("Please select Contract Type");
	    
	    var contractFromDate = $.trim($("#contractFromDate").val());
	    if(contractFromDate==0 || contractFromDate=="")
	    errorList.push("Please Select Contract From Date");
	    
	    var ContractToDate = $.trim($("#contractToDate").val());
	    if(ContractToDate==0 || ContractToDate=="")
	    errorList.push("Please Select Contract To Date");
	    
	    var contpPrimary =  $(".contpPrimary:checked").length;
	    if(contpPrimary==0)
	    errorList.push("Please Select Primary Vender");
	    
	    var ContractMode = $.trim($(".ContractMode").val());
	    if(ContractMode==0 || ContractMode=="")
	    errorList.push("Please select Contract Mode");
	    
	    var allowRenewal = $.trim($("#allowRenewal").val());
	    if(allowRenewal==0 || allowRenewal=="")
	    errorList.push("Please select Allow Renewal or Not");
	    
	    var ResulationDate = $.trim($("#resolutionDate").val());
	    if(ResulationDate==0 || ResulationDate=="")
	    errorList.push("Please Select Resulation Date");
	    
	    var deptId = $.trim($("#deptId").val());
	    if(deptId==0 || deptId=="")
	    errorList.push("Please select ULB department");
	    
	    var desigantionId = $.trim($("#desigantionId").val());
	    if(desigantionId==0 || desigantionId=="")
	    errorList.push("Please select Designation");
	    
	    var representBy = $.trim($("#representBy").val());
	    if(representBy==0 || representBy=="")
	    errorList.push("Please select ULB Represented By");

	    if(Date.parse(tenderDate) >= Date.parse(AgreementDate)){
		    errorList.push("Contract Date is must be greater than Tender Date");
	    }
	    
	    var ContractMode = $.trim($("input[name='contractMastDTO.contMode']:checked").val());
	    if(ContractMode==0 || ContractMode=="")
		 errorList.push("Please select Contract Mode");
	    
	    if(ContractMode=='C')
	    	{
	    	var ContractPayment = $.trim($("#ContractPayment").val());
	 
	  	    if(ContractPayment==0 || ContractPayment=="")
	  	    errorList.push("Please select Contract Payment");
	  	    
	  	    var ContractAmount = $.trim($("#ContractAmount").val());
		    if(ContractAmount==0 || ContractAmount=="")
		    errorList.push("Please enter Contract Amount");
		    
		    var SecurityDeposit = $.trim($("#SecurityDeposit").val());
		    if(SecurityDeposit==0 || SecurityDeposit=="")
		    errorList.push("Please enter Security Deposit");
		    
		    var taxId = $.trim($("#taxId").val());
		    if(taxId==0 || taxId=="")
		  	errorList.push("Please select Tax Code");
		    
		    var id_noa = $.trim($("#id_noa").val());
		    if(id_noa==0 || id_noa=="")
		    errorList.push("Please enter No. of Installments");
		    
		    var totalAmt= 0;
		    var count=0;
		    var errcount=0;
	    	  $('.appendableClassInstallments').each(function(i) {
	    		  count++;
	    			var level=i+1;
	    		 	var PaymentTerms = $.trim($("#PaymentTerms"+i).val());
	    		 	var PaymentTermsCode = $("#PaymentTerms"+i+" option:selected").attr('code');
	  
	    	  	    if(PaymentTerms==0 || PaymentTerms=="") 
	    	  	    errorList.push("Please select Payment Terms Sr No."+" "+level);
	    	  	    
	    	  	    var amt = $.trim($("#amt"+i).val());
	    		    if(amt==0 || amt=="")
	    		    errorList.push("Please enter Amount Sr No."+" "+level);
	    		    
	    		    if(PaymentTermsCode=="AMT")
	    		    {
	    		    	totalAmt=parseFloat(totalAmt)+parseFloat(amt);
	    		    }
	    		    else if(PaymentTermsCode=="PER")
	    		    {
	    		    	var amount= (parseFloat(ContractAmount) *parseFloat(amt))/100;
	    		    	totalAmt=totalAmt+amount;
	    		    }
	    		    
	    		    var installmentsDate = $.trim($("#installmentsDate"+i).val());
	    		    if(installmentsDate==0 || installmentsDate==""){
	    		    	errcount++;
	    		    errorList.push("Please enter Security Deposit Sr No."+" "+level);}
	    	  });
	    	  if(errcount==0){
	    			for (var i= 0; i < count; i++) {
	    				var idate = new Date($("#installmentsDate"+i).val());
	    				for (var j= i+1; j < count; j++) {
	    	  			var jdate =new Date( $("#installmentsDate"+j).val());
	    	  			if(idate >= jdate){
	    	  				errorList.push("Due Date of Sr.No. "+(j+1) +" must be greater then Due Date of Sr.No."+(i+1));}
	    	  		} 
	    	  	}
	    	  }
	    	  if(!id_noa==0 && !id_noa==""){
	    		  if(totalAmt!=ContractAmount)
	    		  {
	    			  errorList.push("Total of Installments Amount and Contract Amount is Not Match");  
	    		  }
	    	  }
	    	}
	    $('.appendableClass').each(function(i) {
	    	var level=i+1;
		     var contp1Name=$.trim($("#contp1Name"+level).val());	
		   	 var contp1Address = $.trim($("#contp1Address"+level).val());
		     var contp1ProofIdNo= $.trim($("#contp1ProofIdNo"+level).val());

		   	 if(contp1Name==0 || contp1Name=="")
	   		 {
	   		    errorList.push(getLocalMessage("rnl.enter.ulb.witnessName.srNo"+" "+level));
	   		 }
		   	 if(contp1Address==0 || contp1Address=="")
		   		 {
		   		    errorList.push(getLocalMessage("rnl.enter.ulb.witnessAddress.srNo"+" "+level));
		   		 }
		   	 if(contp1ProofIdNo==0 || contp1ProofIdNo=="")
	   		 {
	   		    errorList.push(getLocalMessage("rnl.enter.ulb.witnessProofId.srNo"+" "+level));
	   		 }
			 if(contp1ProofIdNo!="" && contp1ProofIdNo.length!=12)
	   		 {
	   		    errorList.push(getLocalMessage("rnl.aadharNo.digit.ulbWitness.srNo"+" "+level));
	   		 }
		 
		     });
	    
	    var count=0;
	    $('.appendableVenClass').each(function(i) {
	    		count++;
		     var venderType=$.trim($("#venderType"+i).val());	
	
		   	 var vendorId = $.trim($("#vendorId"+i).val());
		     var venderName= $.trim($("#venderName"+i).val());
		     var level=i+1;
		   	 if(venderType==0 || venderType=="")
	   		 {
	   		    errorList.push(getLocalMessage("rnl.contract.vender.type"+" "+level));
	   		 }
		   	 if(vendorId==0 || vendorId=="")
		   		 {
		   		    errorList.push(getLocalMessage("rnl.contract.vender.name"+" "+level));
		   		 }
		   	 if(venderName==0 || venderName=="")
	   		 {
	   		    errorList.push(getLocalMessage("rnl.contract.vender.repst.by"+" "+level));
	   		 }
		     });
	    
	    
    $('.appendableWitClass').each(function(i) {
	    	var level=i+1;
	    	var wit=i+count;
		     var contp2Name=$.trim($("#contp2Name"+wit).val());	
		   	 var contp2Address = $.trim($("#contp2Address"+wit).val());
		     var contp2ProofIdNo= $.trim($("#contp2ProofIdNo"+wit).val());
		   	 if(contp2Name==0 || contp2Name=="")
	   		 {
	   		    errorList.push(getLocalMessage("rnl.contractvender.witness.name."+" "+level));
	   		 }
		   	 if(contp2Address==0 || contp2Address=="")
		   		 {
		   		    errorList.push(getLocalMessage("rnl.contract.vender.witness.add"+" "+level));
		   		 }
		   	 if(contp2ProofIdNo==0 || contp2ProofIdNo=="")
	   		 {
	   		    errorList.push(getLocalMessage("rnl.contract.vender.proof"+" "+level));
	   		 }
			 if(contp2ProofIdNo!="" && contp2ProofIdNo.length!=12)
	   		 {
	   		    errorList.push(getLocalMessage("rnl.aadharNo.digit.venderWitness.srNo"+" "+level));
	   		 }
		     });
    
    $('.appendableTermConClass').each(function(i) {
    	var level=i+1;
	     var termCon=$.trim($("#termCon"+i).val());	
	   	 if(termCon==0 || termCon=="")
   		 {
   		    errorList.push(getLocalMessage("rnl.enter.terms.condition.srNo"+" "+level));
   		 }
	     });
    
	    if(errorList.length > 0){ 
	    	showRLValidation(errorList);
	    return false;
	    }
	    
	 $('#empName').val($("#representBy  option:selected").text());
	
   return saveOrUpdateForm(element,"Your application for contract Agreement saved successfully!", 'ContractAgreement.html', 'save');
}

//End

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}


function checkTenderDate()
{
	var resolutionDate =  $("#resolutionDate").val();
	if(resolutionDate=='')
		{
			showAlertBoxFoTenderDate();
		}
}

function checkFromDate()
{
	var tenderDate =  $("#tenderDate").val();
	var resolutionDate =  $("#resolutionDate").val();	
	if(resolutionDate=='' || tenderDate=='')
		{
			showAlertBoxForFromDate();
		}
}

function checkToDate()
{
	var tenderDate =  $("#tenderDate").val();
	var resolutionDate =  $("#resolutionDate").val();	
	var fromDate =  $("#contractFromDate").val();	

	if(resolutionDate=='' || tenderDate=='' || fromDate=='')
		{
			showAlertBoxForToDate();
		}
}

function checkInstallDate()
{
	var tenderDate =  $("#tenderDate").val();
	var resolutionDate =  $("#resolutionDate").val();	
	var fromDate =  $("#contractFromDate").val();
	var toDate =  $("#contractToDate").val();
	if(resolutionDate=='' || tenderDate=='' || fromDate=='' || toDate=='')
	{
		showAlertBoxForInstallDate();
		return false;
	}
	return true;
}
function back(type) {
    if(type == ''){
    	value="ContractAgreement.html";
    } 
    else if(type=='ren') {
    	value="ContractRenewalEntry.html";
    }
    else{
    	value="EstateContractMapping.html";
    }
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', value);
	$("#postMethodForm").submit();
}

function showAlertBoxFoTenderDate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select Resolution Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#tenderDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}


function showAlertBoxForFromDate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select Tender Date and Resolution Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#contractFromDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForToDate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select Tender Date, Resolution Date and From Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#contractToDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForInstallDate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select Tender Date, Resolution Date,From Date and To Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#id_noa').val('');
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function closeAlertForm()
{
	var childDivName	=	'.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}


function fileUpload(uploadId,uploadType) {
/*	var url = "ContractAgreement.html?fileUpload";	
	var postdata = {
			'uploadId' : uploadId,
			'uploadType' : "W"
		};
	
	var ajaxResponse = __doAjaxRequest(url,'POST', postdata, false);

	_showChildForm(childDivName, ajaxResponse);*/
	 var requestData ={
				'uploadId' : uploadId,
				'uploadType' :uploadType
			};
	 var url = "ContractAgreement.html?fileUpload";
		$.ajax({
			url : url,
			data :requestData,
			type : 'POST',
			async : false,
			dataType : '',
		    success : function(response) {
				var divName = '.child-popup-dialog';
				
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				prepareTags();
				$("#file_list_0").hide();
				$("#file_list_1").hide();
				$(divName).removeClass('fancybox-close');
				showModalBoxWithoutClose(divName);
		},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});		
}




///Contract Upload JS

function onLoadUploadPage()
{
		 var url = "ContractAgreement.html?getUploadedImage";
		var data	= {};	
		var 	returnData=	__doAjaxRequest(url, 'post', data , false,'json');
		 $('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
 		var photoId=$("#photoId").val();
		var thumbId=$("#thumbId").val();
		$.each( returnData, function( key, value ) {
			if(key==0)
			{
		 		 $('#showPhoto').append('<img src="'+value+'" class="img-thumbnail">');
		 		 $('#removePhoto').append(' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('+photoId+',0)"><i class="fa fa-trash"></i></a>');
		 		 $("#pho").attr('class', 'row');
				 $("#addPhoto").attr('class', 'col-xs-6');
			}
			else if(key==1)
			{
				 $('#showThumb').append('<img src="'+value+'" class="img-thumbnail" >');
		 		 $('#removeThumb').append(' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('+thumbId+',1)"><i class="fa fa-trash"></i></a>');
		 		 $("#thum").attr('class', 'row');
				 $("#addThumb").attr('class', 'col-xs-6');
			}
	});
}


function deleteSingleUpload(deleteMapId,no)
{
	 var url = "ContractAgreement.html?deleteSingleUpload";
	 var requestData ={
				'deleteMapId' : deleteMapId,
				'deleteId' :no
			};	
		var 	returnData=	__doAjaxRequest(url, 'post', requestData , false,'json');
		$('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
	 	if(no==0)
	 	{
	 	      $("#pho").attr('class', 'text-center');
			  $("#addPhoto").attr('class', '');
	 	}
	 	else
	 	{
	 		 $("#thum").attr('class', 'text-center');
			 $("#addThumb").attr('class', '');
	 	}
			var photoId=$("#photoId").val();
			var thumbId=$("#thumbId").val();
			 $("#file_list_"+ (photoId) + "").hide();
	 		 $("#file_list_"+ (thumbId) + "").hide();
	 		 
		   $.each( returnData, function( key, value ) {
			if(key==0)
			{
		 		 $('#showPhoto').append('<img src="'+value+'"  class="img-thumbnail" >');
		 		 $('#removePhoto').append(' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('+photoId+',0)"><i class="fa fa-trash"></i></a>');
			}
			else if(key==1)
			{
				 $('#showThumb').append('<img src="'+value+'"  class="img-thumbnail" >');
		 		 $('#removeThumb').append(' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('+thumbId+',1)"><i class="fa fa-trash"></i></a>');
			}
	});
}
function otherTask()
{
    var url = "ContractAgreement.html?getUploadedImage";
	var data	= {};		
	var 	returnData=	__doAjaxRequest(url, 'post', data , false,'json');
	 $('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
	var photoId=$("#photoId").val();
	var thumbId=$("#thumbId").val();
		 $("#file_list_"+ (photoId) + " img").hide();
		 $("#file_list_"+ (thumbId) + " img").hide();
		$.each( returnData, function( key, value ) {
		if(key==0)
		{
	 		 $('#showPhoto').append('<img src="'+value+'"  class="img-thumbnail" >');
	 		 $('#removePhoto').append(' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('+photoId+',0)"><i class="fa fa-trash"></i></a>');
	 		 $("#pho").attr('class', 'row');
			 $("#addPhoto").attr('class', 'col-xs-6');
		}
		else if(key==1)
		{
			 $('#showThumb').append('<img src="'+value+'"  class="img-thumbnail" >');
	 		 $('#removeThumb').append(' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('+thumbId+',1)"><i class="fa fa-trash"></i></a>');
	 		 $("#thum").attr('class', 'row');
			 $("#addThumb").attr('class', 'col-xs-6');
		}
	});
} 

	
function deleteUploadedFile()
{
	 var url = "ContractAgreement.html?deleteUploadedFile";
	var data={
			"photoId":$("#photoId").val(),
			"thumbId":$("#thumbId").val(),
	};
	var returnData=	__doAjaxRequest(url, 'post', data , false);
	 $('.child-popup-dialog').hide();
		disposeModalBox();
		$.fancybox.close(); 
} 
 	
function submitUpload()
{
  $('.child-popup-dialog').hide();
	disposeModalBox();
	$.fancybox.close(); 
}


