/**
 * @author Vishwajeet.kumar
 * @author Jeetendra.pal
 * @since 5 feb 2018
 */

var fileArray=[];
var removeOverHeadById=[];
var removeNonSorById=[];
var removeLabourById=[];
var removeDirectAbstract=[];
var removeLabourFormIdArray=[];
var removeMachineryById=[];
var WorkContractURL = "ContractVariation.html";

$(document).ready(function() { $(function()
	 {
	
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		/* maxDate: '-0d', */
		changeYear: true,
	});
	 });

/**
 * This method is used to calculate NonSor Sub total
 * workNonSorItems.jsp
 *  START METHOD
 */
$('.appendableClass').each(function (i) {
	
	 var sum = 0;
	 var basicRate = parseFloat($("#sorRate" + i).val());
	 var quantity = parseFloat($("#workQuantity" + i).val());
	 if (!isNaN(quantity)) {
   	 sum =+ (basicRate * quantity);
    }
	
	 if(!isNaN(sum)){
		 $("#workAmount" + i).val(sum.toFixed(2));
        var grandTotal = 0;
 	     $(".amount").each(function () {
 	    	 var nonSorTotal = parseFloat($(this).val());
 	          grandTotal += isNaN(nonSorTotal) ? 0 : nonSorTotal;
 	     });
 	 var tot =   $("#totalnonSor").val(grandTotal.toFixed(2));
 	  
 	 $('#totalSor').html(grandTotal.toFixed(2));
     }
});

/**
 * This method is used to Search Contract Variation Details
 * contractvariationSummary.jsp
 *  START METHOD
 */
  $(".searchContractVariation").click(function() {
	  
	var errorList=[];
		var contractAgreementNo = $('#contractId').val();
		var contractAgreementDate = $('#contractAgreementDate').val();
		var contp2Name = $('#vendorId').val();
		
		if( contractAgreementNo!= '' ||contractAgreementDate != '' || contp2Name != ''){
			var requestData = 'contractAgreementNo='+contractAgreementNo +'&contractAgreementDate='+contractAgreementDate+'&venderId='+contp2Name;
			var table = $('#datatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			//var ajaxResponse = __doAjaxRequest('ContractVariation.html?filterContractVariationListData', 'POST' ,false,requestData,'json');
			var ajaxResponse = __doAjaxRequest(WorkContractURL+ '?filterContractVariationListData', 'POST',requestData, false,'json');
			var result = [];
			if (ajaxResponse.length != 0) {
				$.each(ajaxResponse, function(index){
				var obj = ajaxResponse[index];
				if(obj.workOrderStatus != "Draft"){
					result.push([obj.contractNo,obj.contractDate,obj.vendorName,obj.workOrderStatus,'<td>'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="getActionForDefination(\''+obj.contId+'\',\'V\')" title="View Variation Details"><i class="fa fa-eye"></i></button>'
						+ '</td>' ]);
				}else {
					result.push([obj.contractNo,obj.contractDate,obj.vendorName,obj.workOrderStatus,'<td>'
			        	 + '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="getActionForDefination(\''+obj.contId+'\',\'V\')" title="View Variation Details"><i class="fa fa-eye"></i></button>'
			        	 + '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="getActionForDefination(\''+obj.contId+'\',\'E\')"  title="Edit Variation Details"><i class="fa fa-pencil"></i></button>'
			        	 + '<button type="button" class="btn btn-green-3 btn-sm btn-sm margin-right-10" onclick="sendForApproval(\''+obj.contId+'\',\'S\')"  title="Send for Approval"><i class="fa fa-share-square-o"></i></button>'
			        	 + '</td>' ]);
			
				}				         
	         });
			table.rows.add(result);
			table.draw();
		}
		}else{
			errorList.push(getLocalMessage('work.management.valid.select.any.field'));
			displayErrorsOnPage(errorList);
		}
		
   });
	  
$('.decimal').on('input', function() {
    this.value = this.value
      .replace(/[^\d.]/g, '')             // numbers and decimals only
      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
  });
	  
   $("#datatables").dataTable({ "oLanguage": { "sSearch": "" } ,
	 "aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	 "iDisplayLength" : 5, 
	  "bInfo" : true,
	  "lengthChange": true 
	});	
			
$("#toBeHide").hide();	   
	   
$("#directAbstractTab").on("click", '.addButton',function(e) {
	
	if ( $.fn.DataTable.isDataTable('#directAbstractTab') ) {
			$('#directAbstractTab').DataTable().destroy();
		 }
		var errorList = [];	
		errorList = validateAbstractTab(errorList);
		if(errorList.length > 0){
			
			 showErr(errorList);	
		}else{		
            e.preventDefault();							
			var content = $('#directAbstractTab tr').last().clone();
			$('#directAbstractTab tr').last().after(content);
			content.find("input:text").val('');
			content.find("select:eq(1)").val('');
			content.find("input:hidden").val('');
		 	
		 	$('.decimal').on('input', function() {
			    this.value = this.value
			      .replace(/[^\d.]/g, '')             // numbers and decimals only
			      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
			      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
			      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
			  });							
			reOrderTableIdSequence();
		}												
		/*if(!validatedirectAbstractList()){
			$('#directAbstractTab').DataTable();
			return false;
		}*/						
	});

		  		   
$("#directAbstractTab").on("click", '.delButton', function(e) {
	if ( $.fn.DataTable.isDataTable('#directAbstractTab') ) {
		 $('#directAbstractTab').DataTable().destroy();
		}
		var countRows = -1;
		$('.appendableClass').each(function(i) {
		if ( $(this).closest('tr').is(':visible') ) {
				countRows = countRows + 1;			
		}
	});
	  row = countRows;
	  if (row != 0) {
		$(this).parent().parent().remove();
		row--;
		var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if(deletedSodId != ''){
			removeDirectAbstract.push(deletedSodId);
		}
		$('#removeDirectAbstract').val(removeDirectAbstract);
		  reOrderTableIdSequence();
	 }
	e.preventDefault();
});	


function reOrderTableIdSequence() {
	
	var cpMode = $("#cpdModeHideSor").val();
	$('.appendableClass').each(function(i) {
		
		if(cpMode == 'N'){
			$(this).find("select:eq(0)").attr("id", "sordCategory"+ i)
			$(this).find("input:text:eq(0)").attr("id","sordSubCategory" + i);
			$(this).find("input:text:eq(1)").attr("id","sorDIteamNo" + i);
			$(this).find("input:text:eq(2)").attr("id","sorDDescription" + i);
			$(this).find("select:eq(1)").attr("id", "sorIteamUnit"+ i)
			$(this).find("input:text:eq(3)").attr("id","sorBasicRate" +i);
			$(this).find("input:text:eq(4)").attr("id","workQuantity" + i);
			$(this).find("input:text:eq(5)").attr("id","workEstimAmount" + i);
			$(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));

			$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
			$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
			
			$(this).find("input:hidden:eq(0)").attr("name","workEstimateList[" + i+ "].workEstemateId");
			$(this).find("select:eq(0)").attr("name","workEstimateBillQuantityList[" + i+ "].sordCategory");
			$(this).find("input:text:eq(0)").attr("name","workEstimateBillQuantityList[" + i+ "].sordSubCategory");
			$(this).find("input:text:eq(1)").attr("name","workEstimateBillQuantityList[" + i+ "].sorDIteamNo");
			$(this).find("input:text:eq(2)").attr("name","workEstimateBillQuantityList[" + i+ "].sorDDescription");
			$(this).find("select:eq(1)").attr("name","workEstimateBillQuantityList[" + i+ "].sorIteamUnit");
			$(this).find("input:text:eq(3)").attr("name","workEstimateBillQuantityList[" + i+ "].sorBasicRate");
			$(this).find("input:text:eq(4)").attr("name","workEstimateBillQuantityList[" + i+ "].workEstimQuantity");
			$(this).find("input:text:eq(5)").attr("name","workEstimateBillQuantityList[" + i+ "].workEstimAmount");
			
			/*var toatlAmount=$("#sorBasicRate"+i).val() * $("#workEstimQuantity"+i).val();
			 $("#workEstimAmount"+i).val(toatl);	*/	
			calculateTotalRate();
		}else{
			$(this).find("select:eq(0)").attr("id", "sordCategory"+ i)
			$(this).find("input:text:eq(0)").attr("id","sorDIteamNo" + i);
			$(this).find("input:text:eq(1)").attr("id","sorDDescription" + i);
			$(this).find("select:eq(1)").attr("id", "sorIteamUnit"+ i)
			$(this).find("input:text:eq(2)").attr("id","sorBasicRate" +i);
			$(this).find("input:text:eq(3)").attr("id","workQuantity" + i);
			$(this).find("input:text:eq(4)").attr("id","workEstimAmount" + i);
			$(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));

			$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
			$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
			
			$(this).find("input:hidden:eq(0)").attr("name","workEstimateList[" + i+ "].workEstemateId");
			$(this).find("select:eq(0)").attr("name","workEstimateBillQuantityList[" + i+ "].sordCategory");
			$(this).find("input:text:eq(0)").attr("name","workEstimateBillQuantityList[" + i+ "].sorDIteamNo");
			$(this).find("input:text:eq(1)").attr("name","workEstimateBillQuantityList[" + i+ "].sorDDescription");
			$(this).find("select:eq(1)").attr("name","workEstimateBillQuantityList[" + i+ "].sorIteamUnit");
			$(this).find("input:text:eq(2)").attr("name","workEstimateBillQuantityList[" + i+ "].sorBasicRate");
			$(this).find("input:text:eq(3)").attr("name","workEstimateBillQuantityList[" + i+ "].workEstimQuantity");
			$(this).find("input:text:eq(4)").attr("name","workEstimateBillQuantityList[" + i+ "].workEstimAmount");
			
			/*var toatlAmount=$("#sorBasicRate"+i).val() * $("#workEstimQuantity"+i).val();
			 $("#workEstimAmount"+i).val(toatl);	*/	
			calculateTotalRate();
		}		
	  });
	$('#directAbstractTab').DataTable();
 }
			//end dynamic table	
});					    		   
			 $('[id^="sordCategory"]').data('rule-required',true);
			 $('[id^="sorDIteamNo"]').data('rule-required',true);
			 $('[id^="sorDDescription"]').data('rule-required',true);
			 $('[id^="sorIteamUnit"]').data('rule-required',true);
			 $('[id^="sorBasicRate"]').data('rule-required',true);

function calculateTotalRate() {
	
	$('.appendableClass').each(function(i) {
		
		var total = 0;			
		var basicRate =parseFloat($("#sorBasicRate" + i).val());
		var quantity = parseFloat($("#workQuantity" + i).val());
				
		if(!isNaN(quantity) && !isNaN(basicRate)){
			total =+(basicRate * quantity);
		}	
		if(!isNaN(total)){
			$("#workEstimAmount" + i).val(total.toFixed(2));			
		}
										 					
	});
}

function getActionForDefination(contId,mode){
	
	var estimateType = "";
	if(estimateType == "undefined" || estimateType == "" || estimateType == "null")
	{
		estimateType = "S";
	}
	
	var divName = '.content-page';
	var url = "ContractVariation.html?viewContractVariation";
	var actionParam = {
			'contId' : contId,
			'mode' :mode,
			'estimateType' : estimateType
		}
	
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getSorByValue();
	 getContractDetailsByContId(contId);
	
}

function getSorByValue() {
	
		var actionParam = {
		'actionParam' : $("#sorList").val(),
		'sorId' : $("#sorId").val()
	}
	var url = "WorkEstimate.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}

function backForm() {
	
	var requestFormFlag = $("#requestFormFlag").val();
	if(requestFormFlag == 'WCV'){		
		var divName = '.content-page';
  	    var url = "WorkContractVariationApproval.html?showApprovalCurrentForm";
  	    var requestData = {};
  	    var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
  	    $(divName).removeClass('ajaxloader');
  	    $(divName).html(ajaxResponse);
  	    prepareTags();
  	    return false;
	}else{
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'ContractVariation.html');
		$("#postMethodForm").submit();	
	}
	
}

function validateAbstractTab(errorList){
	
	   $('.appendableClass').each(function(i) {
		   var directAbstract = i+1;
		   var category = $("#sordCategory" +i).find("option:selected").attr('value');
		   var subCategory = $("#sordSubCategory" +i).val();
		   var itemCode = $("#sorDIteamNo"+i).val();
		   var description = $("#sorDDescription" +i).val();
		   var itemUnit = $("#sorIteamUnit"+i).find("option:selected").attr('value');
		   var rate = $("#sorBasicRate" +i).val();
		   var quantity = $("#workEstimQuantity" +i).val();
		   var total = $("#workEstimAmount" + i).val();
		   var cpMode = $("#cpdModeHideSor").val();
		   
		   if(category =="" || category =="0"){
			   errorList.push(getLocalMessage("sor.select.category") +" "+directAbstract);
		   }
		   if(cpMode == 'N'){
			   if(subCategory==null || subCategory == ""){
				   errorList.push(getLocalMessage("work.estimate.select.sub.category") +" "+directAbstract);
			   } 
		   }
		   		   
		   if(itemCode =="" || itemCode ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.item.code") +" "+directAbstract);
		   }
		   if(description =="" || description ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.description") +" "+directAbstract);
		   }
		   if(itemUnit =="" || itemUnit =="0"){
			   errorList.push(getLocalMessage("work.estimate.select.unit") +" "+directAbstract);
		   }
		   if(rate == ""||rate == ""){
			   errorList.push(getLocalMessage("work.estimate.enter.rate") +" "+directAbstract);
		   }
		   if(quantity == ""||quantity=="0"){
			   errorList.push(getLocalMessage("work.estimate.enter.quantity") +" "+directAbstract);
		   }
		   if(total=="0" || total ==""){
			   errorList.push(getLocalMessage("work.estimate.enter.total") +" "+directAbstract);
		   }
	   });
	   return errorList;
}

function toBeHide(){
	$("#toBeHide").show();
}

function getRateUnitBySorId(index){	
	var unitRateString=$("#gRateMastId"+index).find("option:selected").attr('code');
	if(unitRateString!="" && unitRateString!=undefined){
	var rateAndunit = $("#gRateMastId"+index).find("option:selected").attr('code').split(",");
	$("#maItemUnit"+index).val(rateAndunit[0]);
	$("#rate"+index).val(rateAndunit[1]);
	}
}

function saveBillQuantityDetails(billQuantityData){
	
	var errorList = [];
	var contractNo = $("#contractId").val();
	
	if(contractNo==""||contractNo=="0"){
		errorList.push(getLocalMessage("work.contract.agreement.number"));
	}
	if ($(".defination").val() ==""){
	errorList.push(getLocalMessage("work.estimate.workDefination.required"));
	}
	if ($(".workName").val() ==""){
	errorList.push(getLocalMessage("work.estimate.work.name.required"));
	}	
   if (errorList.length > 0) {
	  $("#errorDiv").show();
	   showErr(errorList);	
    } else {
	   $("#errorDiv").hide();	
	   var formName = findClosestElementId(billQuantityData, 'form');
	   var theForm = '#' + formName;
	   var requestData = __serializeForm(theForm);
	   var response = __doAjaxRequest(
			   'WorkNonSorForm.html?saveBillQuantityDetails', 'POST',
			   requestData, false);
	   showConfirmBoxForOverHeads(getLocalMessage('work.estimate.nonSor.creation.success'));
  }
}
$("#nonSorDetails").on("click",'.addNonSORDetails',function(e) {
	
	  var count = $('#nonSorDetails tr').length - 1;
	  var errorList = [];
	  errorList = validateNonSORItemsDetails(errorList);	
  if (errorList.length > 0) { 
  	       $("#errorDiv").show();
	              showErr(errorList);
			 } 
        else {			      
        	$("#errorDiv").addClass('hide');
        	e.preventDefault();
			var clickedRow = $(this).parent().parent().index();
			var content = $('#nonSorDetails tr').last().clone();
			$('#nonSorDetails tr').last().after(content);
			content.find("input:text").val('');
			content.find("select").attr("select", "select").val('');
			content.find("input:text").val('');
			content.find("input:hidden").val('');
			/*content.find("input:text").attr("value", "");
			content.find("select").attr("selected", "selected").val('');
			content.find("input:text").val('');
			content.find("input:hidden").val('');*/
			reOrderNonSORItemsDetails();
			}
});

function validateNonSORItemsDetails(errorList){
$(".appendableClass").each(function(i) {	
	
		var itemCode = $("#sorDIteamNo" + i).val();
		var sorDesc = $("#sorDDescription" + i).val();
		var sorItemUnit = $("#sorIteamUnit" + i).val();
		var sorBasicRate = $("#sorRate" + i).val();
		var quantity = $("#workQuantity" + i).val();
		//var amount = $("#workAmount" + i).val();
		
		var nonSorConstant = i+1;
		if(itemCode == ""){
			errorList.push(getLocalMessage("work.estimate.enter.item.code")+" "+nonSorConstant);
		}
		if(sorDesc == ""){
			errorList.push(getLocalMessage("work.estimate.enter.description")+" "+nonSorConstant);
		}
		if(sorItemUnit == ""){
			errorList.push(getLocalMessage("work.estimate.select.unit")+" "+nonSorConstant);
		}
		if(sorBasicRate == ""){
			errorList.push(getLocalMessage("work.estimate.enter.rate")+" "+nonSorConstant);
		}
		if(quantity == ""){
			errorList.push(getLocalMessage("work.estimate.enter.quantity")+" "+nonSorConstant);
		}					
	});
	return errorList;	
}

function reOrderNonSORItemsDetails(){	
  $('.appendableClass').each(function(i){
	
	 $(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));
	  $(this).find("input:text:eq(0)").attr("id" ,"sNo" + (i));
	  $(this).find("input:text:eq(1)").attr("id" ,"sorDIteamNo" + (i));
	  $(this).find("input:text:eq(2)").attr("id","sorDDescription" + (i));
	  $(this).find("select:eq(0)").attr("id","sorIteamUnit" + (i));
	  $(this).find("input:text:eq(3)").attr("id" ,"sorRate" + (i));
	  $(this).find("input:text:eq(4)").attr("id","workQuantity" + (i));
	  $(this).find("input:text:eq(5)").attr("id" ,"workAmount" + (i));
	
	 

	//Names
	  $(this).find("input:hidden:eq(0)").attr("name","workEstimateNonSorList[" + (i)+ "].workEstemateId");
	  $("#sNo" + i).val(i+1);
	$(this).find("input:text:eq(1)").attr("name" ,"workEstimateNonSorFormList[" + (i)+ "].sorDIteamNo");
	$(this).find("input:text:eq(2)").attr("name","workEstimateNonSorFormList[" + (i)+ "].sorDDescription");
	$(this).find("select:eq(0)").attr("name","workEstimateNonSorFormList[" + (i)+ "].sorIteamUnit");
	$(this).find("input:text:eq(3)").attr("name","workEstimateNonSorFormList[" + (i)+ "].sorBasicRate");
	$(this).find("input:text:eq(4)").attr("name","workEstimateNonSorFormList[" + (i)+ "].workEstimQuantity");
	$(this).find("input:text:eq(5)").attr("name","workEstimateNonSorList[" + (i)+ "].workEstimAmount");
	
});
}

$("#nonSorDetails").on("click",'.deleteNonSORDetails',function(e) {
	
	var errorList = [];
	
	if ( $.fn.DataTable.isDataTable('#NonSorTable') ) {
		 $('#NonSorTable').DataTable().destroy();
		}
		var countRows = -1;
		$('.appendableClass').each(function(i) {
		if ( $(this).closest('tr').is(':visible') ) {
				countRows = countRows + 1;			
		}
	});
		row = countRows;
		  if (row != 0) {
			$(this).parent().parent().remove();
			row--;
			var nonSORId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(nonSORId != ''){
				removeNonSorById.push(nonSORId);
				}
			$('#removeNonSorById').val(removeNonSorById);
			reOrderLaborDetails();
		 }
		e.preventDefault();
	
	/*var counter = 0;
	$('.appendableClass').each(function(i) {
				counter += 1;
	 });
	var rowCount = $('#nonSorDetails tr').length;
	if (rowCount <= 3) {
		errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	}*/
	/*if (errorList.length > 0) {
		$("#errorDiv").show();
	    showErr(errorList);
		return false;
		}else{
	
		$(this).parent().parent().remove();
		var nonSORId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if(nonSORId != ''){
			removeNonSorById.push(nonSORId);
			}
		$('#removeNonSorById').val(removeNonSorById);
		reOrderLaborDetails();
	     }*/
});
   
$("#nonSorDetails").on('input','.calculation', function () {
	
      $('.appendableClass').each(function (i) {
    	  
    	 var sum = 0;
    	 var basicRate = parseFloat($("#sorRate" + i).val());
    	 var quantity = parseFloat($("#workQuantity" + i).val());
    	 if (!isNaN(quantity)) {
        	 sum = (basicRate * quantity);
         }    	
    	 if(!isNaN(sum)){
    		 $("#workAmount" + i).val(sum.toFixed(2));
             var grandTotal = 0;
      	     $(".amount").each(function () {
      	    	 var nonSorTotal = parseFloat($(this).val());
      	          grandTotal += isNaN(nonSorTotal) ? 0 : nonSorTotal;
      	     });
      	 var tot =   $("#totalnonSor").val(grandTotal.toFixed(2));
      	 $('#totalSor').html(grandTotal.toFixed(2));
          }
     });             
});


$("#sorDetails").on('change','.calculation', function () {
	
      $('.appendableClass').each(function (i) {
    	  
    	 var sum = 0;
    	 var basicRate = parseFloat($("#sorBasicRate" + i).val());
    	 var quantity = parseFloat($("#quanity" + i).val());
    	 var labourrate = parseFloat($("#sorLabourRate" + i).val());
    	 if (!isNaN(quantity)) {
    		 if(isNaN(labourrate)){
    			 sum = (basicRate * quantity);
    		 }else{
    			 sum = (basicRate * quantity + labourrate);
    		 }       	
         }    	
    	 if(!isNaN(sum)){
    		 $("#total" + i).val(sum.toFixed(2));
             var grandTotal = 0;
      	     $(".amount").each(function () {
      	    	 var sorTotal = parseFloat($(this).val());
      	          grandTotal += isNaN(sorTotal) ? 0 : sorTotal;
      	     });
      	 var tot =   $("#totalnonSor").val(grandTotal.toFixed(2));
      	 $('#totalSor').html(grandTotal.toFixed(2));
          }
     });             


});

function showConfirmBoxForFinalSubmit(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	
	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '+ getLocalMessage(successMsg) +' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input type=\'button\' value=\'' + cls
		+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
		+ ' onclick="proceedForFinalSubmit()"/>' + '</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	
	return false;
}
/**
 * Work Enclosure File Upload All method are End
 */

/**
 *This Method  is used to final submit of work Estimation data
 * @return workEstimation.jsp
 */

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function saveNonSorDetails(nonSorData) {
	

	var errorList = [];
	var contractNo = $("#contractId").val();
	var nonSor = $("#nonSor").val();
	var sor = $("#sor").val();
	var bill = $("#billOfQuantity").val();
	var status = $("#status").val();
	var seletedSor = $('input[name=estimateRadioFlag]:checked').val();
	var actionParam = "";

	if (seletedSor == "S") {
		errorList = validateSorDetails(errorList);
		actionParam = "saveSorDetails";
	} else if (seletedSor == "N") {
		errorList = validateNonSORItemsDetails(errorList);
		actionParam = "saveNonSorDetails";
	} else if (seletedSor == "B") {
		errorList = validateAbstractTab(errorList);
		actionParam = "saveBillQuantityDetails";
	}

	if (contractNo == "" || contractNo == "0") {
		errorList.push(getLocalMessage("work.contract.agreement.number"));
	}

	if (status == "0" || status == undefined || status == "") {
		errorList.push(getLocalMessage("validation.revise.estimate.entry"));
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		var formName = findClosestElementId(nonSorData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('ContractVariation.html?' + actionParam,
				'POST', requestData, false);
		showConfirmBox();
	}
}
    
    
function showConfirmBox() {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '+ getLocalMessage('contract.variation.form.sucess') +' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceed() {
	var requestFormFlag = $("#requestFormFlag").val();
	
	if(requestFormFlag == 'WCV'){		
	    var divName = '.content-page';
	    var url = "WorkContractVariationApproval.html?showApprovalCurrentForm";
	    var requestData = {};
	    var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	    $(divName).removeClass('ajaxloader');
	    $(divName).html(ajaxResponse);
	    prepareTags();
	    $.fancybox.close();
	    return false;
	}
	else {
		$("#postMethodForm").prop('action', 'ContractVariation.html');
		$("#postMethodForm").submit();
		$.fancybox.close();
	} 			
}

function getTableDetails(element,contractNo,status) {
	
	
	var errorList = [];
	var status = $("#status").val();
	var contractNo = $("#contractId").val();
	
	
	if(contractNo==""||contractNo=="0"){
		errorList.push(getLocalMessage("work.contract.agreement.number"));
	}
	if(status == "0" || status == undefined || status == ""){
		errorList.push(getLocalMessage("validation.revise.estimate.entry"));
	}
	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var actionParam = {
			'value' : element.value,
			'contractNo' :contractNo,
			'status' :status
	}
	
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('ContractVariation.html?getNonSorFormList', 'POST',
			actionParam, false, 'html');
	 if (errorList.length > 0) {
		  $("#errorDiv").show();
		   showErr(errorList);
	 }
	 else{
		$("#errorDiv").hide();
		  $("#sortablevariation").html(response); 
	 } 
	 
	/* if (response.length == 0) {
			jQuery.each(response, function() {
				errorList.push(this);
			});
			$("#errorDiv").show();
			showErr(errorList);
		} else {
			showConfirmBox("SOR Items Saved sucessfully");
		}*/
	 
	 /*else if($('input[name=estimateRadioFlag]:checked').val()=="S")
		 {
		 $("#errorDiv").hide();
		  $("#sortablevariation").html(response);
		 	
		 }
	 else if(($('input[name=estimateRadioFlag]:checked').val()=="N"))
		 {
		 $("#errorDiv").hide();	
		 $("#NonSorTable").html(response);
		
		 
		 }
	 else if(($('input[name=estimateRadioFlag]:checked').val()=="B"))
		 {
		 $("#errorDiv").hide();	
		 $("#billOfQuantity1").html(response);
		 
		 }*/
	
	prepareTags();
	displayErrorsOnPage(errorList);
}
		
function getContractDetils(obj){
	getContractDetailsByContId($(obj).val())
	$("#status").val('0');
}

function getContractDetailsByContId(contId){
	
	var requestData = {
			'contractId' :contId
	}
	var response = __doAjaxRequest('ContractVariation.html?getContractDetails', 'POST',
			requestData, false, 'json');
	if (response.contDate != null) {

		$('#contractAgreementDate').val(response.contDate);
	} 
	if (response.contFromDate != null) {
			$('#startDate').val(response.contFromDate);

	} 
	if (response.contToDate != null) {
		$('#endDate').val(response.contToDate);

	} 
	if (response.contAmount != null) {
		$('#contractAmount').val(response.contAmount);
	} 
	
	getDetails(false);
}

function addEntryData() {

	var errorList = [];
	errorList = validateSorDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('sorDetails');
	} else {
		$('#sorDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}
function closeOutErrBox() {
	
	$('.warning-div').addClass('hide');
	   $("#errorDiv").hide();	
}


function validateSorDetails(errorList){
	
	
	var errorList = [];
	var j = 0;
	if ($.fn.DataTable.isDataTable('#sorDetails')) {
		$('#sorDetails').DataTable().destroy();
	}
	$("#sorDetails tbody tr")
	.each(
			function(i) {
		   var sorconstant = i+1;
		   var category = $("#sordCategory" +i).val();
		   //var subCategory = $("#sordSubCategory" +i).val();
		   var itemCode = $("#sorDIteamNo"+i).val();
		   var description = $("#sorDDescription" +i).val();
		   var itemUnit = $("#sorIteamUnit"+i).val();
		   var rate = $("#sorBasicRate" +i).val();
		  // var labourrate = $("#sorLabourRate" + i).val();
		   var quantity = $("#quanity" +i).val();
		  // var total = $("#total" + i).val();
		   
		   if(category =="" || category =="0"){
			   errorList.push(getLocalMessage("sor.select.category") +" "+sorconstant);
		   }
		   
		   if(itemCode =="" || itemCode ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.item.code") +" "+sorconstant);
		   }
		   if(description =="" || description ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.description") +" "+sorconstant);
		   }
		   if(itemUnit =="" || itemUnit =="0"){
			   errorList.push(getLocalMessage("work.estimate.select.unit") +" "+sorconstant);
		   }
		   if(rate == ""||rate == ""){
			   errorList.push(getLocalMessage("work.estimate.enter.rate") +" "+sorconstant);
		   }
		   if(quantity == ""||quantity=="0"){
			   errorList.push(getLocalMessage("work.estimate.enter.quantity") +" "+sorconstant);
		   }
		   /*if(total=="0" || total ==""){
			   errorList.push(getLocalMessage("work.estimate.enter.total") +" "+directAbstract);
		   }*/
	   });
	   return errorList;
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function openAddContractVariation(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
	if(actionParam=='AddContractVariation' && $("#sorList").val()!="")
		getSorByValue();
}

function sendForApproval(contId,mode){
	
		var divName = '.content-page';
		var url = "ContractVariation.html?sendForApproval";
		var requestData = {
				'contId' : contId,
				'mode' :mode
			}
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		/*$( ".searchContractVariation" ).trigger("click");		*/
		showContractVariationForApproval(ajaxResponse, contId, mode);
		
}

function showContractVariationForApproval(ajaxResponse,contId,mode){
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	var sMsg = '';
		if (ajaxResponse.checkStausApproval == "Y") {
			sMsg = getLocalMessage("work.contract.variation.initiated.approval");
		} else if(ajaxResponse.checkStausApproval == "N") {
					sMsg = getLocalMessage("work.contract.variation.approval.not.defined");
		}else {
				sMsg = getLocalMessage("work.contract.variation.approval.initiation.fail");
				sMsg1 = getLocalMessage("work.contact.administrator");
		}

		if (ajaxResponse.checkStausApproval == "Y") {
				message += '<div class="text-center padding-top-25">'+'<p class="text-center text-blue-2 padding-12">' + sMsg
						+ '</p>'+'</div>';
				message += '<div class=\'text-center padding-bottom-10\'>'
						+ '<input type=\'button\' value=\'' + cls
						+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
						+ ' onclick="proceed()"/>' + '</div>';
			} else if(ajaxResponse.checkStausApproval == "N") {
					message += '<div class="text-center padding-top-25">'+ '<p class="text-center text-blue-2 padding-12">' + sMsg
							+ '</p>'+'</div>';
					message += '<div class=\'text-center padding-bottom-10\'>'
							+ '<input type=\'button\' value=\'' + cls
							+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
							+ ' onclick="proceed()"/>' + '</div>';
			}else{
					message += '<div class="text-center padding-top-25">' +'<p class="text-center red padding-12">' + sMsg + '</p>'
							+'<p class="text-center red padding-12">' + sMsg1 + '</p>'
							+'</div>';
					message += '<div class=\'text-center padding-bottom-10\'>'
							+ '<input type=\'button\' value=\'' + cls
							+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
							+ ' onclick="proceed()"/>' + '</div>';
				}
			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showModalBoxWithoutClose(errMsgDiv);
			return false;
	}

function proceed(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ContractVariation.html');
	$("#postMethodForm").submit();
}

function getDetails(){	 
	
	
	var status = $("#status").find("option:selected").attr('value');
	if(status == undefined ||status == 0 ){
		return false;
	}
	if(status != '' && status != undefined && status != 0){
		$("selectContractId").val(status);
		var divName = '.content-page';
		var requestData = {
				'status' : status,
			}
		var response = __doAjaxRequest('ContractVariation.html?getNonSorFormList', 'POST',
				requestData, false, 'json');
		
		if ($('input[name="estimateRadioFlag"]').attr('checked', false));
		{
			$("#sortablevariation").html(false);	
		}
	}	
}
