/**
 * @author vishwajeet.kumar
 */
var RevisedEstimateURL = "WorksRevisedEstimate.html";
var removeSorRevised = [];
var removeNonSorById = [];
var removeWorkEstimateById = [];

$(document).ready(function() {

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		/* maxDate: '-0d', */
		changeYear : true,
	});

	$("#datatables").dataTable({ "oLanguage": { "sSearch": "" } ,
		 "aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
		 "iDisplayLength" : 5, 
		  "bInfo" : true,
		  "lengthChange": true 
		});	
	
/**
 * This method is used to calculate NonSor Sub total
 */
	$('.appendableClass').each(function(i) {
		
		var sum = 0;
		var basicRate = parseFloat($("#sorRate" + i).val());
		var quantity = parseFloat($("#workQuantity" + i).val());
		if (!isNaN(quantity)) {
			sum = +(basicRate * quantity);
		}
			
		if (!isNaN(sum)) {
			$("#workAmount" + i).val(
					sum.toFixed(2));
			var grandTotal = 0;
			$(".amount").each(function() {
				var nonSorTotal = parseFloat($(this).val());
				grandTotal += isNaN(nonSorTotal) ? 0 : nonSorTotal;
															});
			var tot = $("#totalnonSor").val(grandTotal.toFixed(2));
			
			$('#totalSor').html(grandTotal.toFixed(2));
		}
	});

	/**
	 * This method is used to Search Revised Estimate Details
	 * workRevisedEstimateSummary.jsp START METHOD
	 */
	$(".searchRevisedEstimate").click(function() {
		
		var errorList = [];
		var contractAgreementNo = $('#contractId').val();
		var contractAgreementDate = $('#contractAgreementDate').val();
		var contp2Name = $('#vendorId').val();
		
		if (contractAgreementNo != ''|| contractAgreementDate != ''	|| contp2Name != '') {
			var requestData = 'contractAgreementNo='+ contractAgreementNo
			                  + '&contractAgreementDate='+ contractAgreementDate
			                  + '&venderId=' + contp2Name;
			var table = $('#datatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest('WorksRevisedEstimate.html?filterRevisedEstimateListData','POST', requestData, false,'json');
			var result = [];
			if (ajaxResponse.length != 0) {
				$.each(ajaxResponse,function(index) {
					var obj = ajaxResponse[index];
					if (obj.workOrderStatus != "Draft") {
						result.push([
							obj.contractNo,obj.contractDate,obj.vendorName,obj.workOrderStatus,
							'<td>'+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="getActionForDefination(\''+ obj.contId
							+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
							+ '</td>' ]);
					} else {
						result.push([obj.contractNo,obj.contractDate,obj.vendorName,obj.workOrderStatus,
							'<td>'
							+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="getActionForDefination(\''	+ obj.contId
							+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
							+ '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="getActionForDefination(\''
							+ obj.contId+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
							+ '<button type="button" class="btn btn-green-3 btn-sm btn-sm margin-right-10" onclick="sendForApproval(\''+ obj.contId+ '\',\'S\')"  title="Send for Approval"><i class="fa fa-share-square-o"></i></button>'
							+ '</td>' ]);

					}
				});
				table.rows.add(result);
				table.draw();
			}
		} else {
			errorList.push(getLocalMessage('work.Def.valid.select.any.field'));
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
	
	/*$('#revisedAppendableClass tbody tr').each(function(i) {
		
		var sordCategorysordCategory = $("#sordCategory" + i).val();
	});*/
});

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

function getResetReviseFlag(){
	
	var reviseFlag = $("#reviseFlag").find("option:selected").attr('value');
	if(reviseFlag == undefined || reviseFlag ==0){
		return false;
	}	
	if ($('input[name="estimateRadioFlag"]').attr('checked', false));
	{
		$("#sorRevisedEstimateTag").html(false);
		let rMode = $("#saveMode").val();
		/*if(rMode == 'E' || rMode == 'V' ){	*/
			var url = "WorksRevisedEstimate.html?clearList";
			/*var response = __doAjaxRequest(url,'POST', {}, false,html);*/			
			var response = __doAjaxRequest(url, 'POST', {}, false,'html');
			 /*//$('.content-page').html(response);
			 $('.content-page').removeClass('ajaxloader');
			$('.content-page').html(response);
			prepareTags();*/
		/*}*/
	}
}

function openAddRevisedEstimate(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function resetRevisedEstimate() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WorksRevisedEstimate.html');
	$("#postMethodForm").submit();
}

function getAgreementDetils(obj) {
	
	getContractDetailsByContId($(obj).val())
	$("#reviseFlag").val('0');
}

function getContractDetailsByContId(contId) {
	
	if(contId ==0 ||contId== ''){
		$('#contractAgreementDate').val('');
		$('#startDate').val('');
		$('#endDate').val('');
		$('#contractAmount').val('');		
	}else{
		var requestData = {
				'contractId' : contId
			}
			var response = __doAjaxRequest('WorksRevisedEstimate.html?getContractDetails',
					'POST', requestData, false, 'json');
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
	}	
}

  function getSorNonSorDetails(element){
	  
	  var errorList = [];
		var reviseFlag = $("#reviseFlag").val();
		var contractNo = $("#contractId").val();
		
		var radioValue = $("input[name='estimateRadioFlag']:checked").val();
		
		if (contractNo == "" || contractNo == "0") {
			errorList.push(getLocalMessage("work.contract.agreement.number"));
		}
		if (reviseFlag == "0" || reviseFlag == undefined || reviseFlag == "") {
			errorList.push(getLocalMessage("validation.revise.estimate.entry"));
		}
		
		if(radioValue == ''){
			errorList.push(getLocalMessage("validation.revise.estimate.entry"));
		}
		
		if (errorList.length > 0) {
			 $("#errorDiv").show();
			 showErr(errorList);
		} else {
			$("#errorDiv").hide();
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var actionParam = {
				'sorNonSor' : element.value,
				'contractNo' : contractNo,
				'reviseFlag' : reviseFlag
			}
			var requestData = __serializeForm(theForm);
			var response = __doAjaxRequest('WorksRevisedEstimate.html?sorAndNonSorList',
					'POST', actionParam, false, 'html');
			$("#sorRevisedEstimateTag").html(response);		
			prepareTags()		
		}
  }


 function getAllItemsRevisedList(index){	
	 
	 $('#sorDIteamNo' + index).html('');
	 $('#sorDIteamNo' + index).append(
			 $("<option></option>").attr("value", "").text(
					 getLocalMessage('selectdropdown'))).trigger(
					 'chosen:updated');
	 var requestData = {
			 "sorChapterVal" : $('#sordCategory' + index).val()
	 }
	 var sorItemsList = __doAjaxRequest("WorksRevisedEstimate.html" + '?getAllRevisedItemsList',
			 'POST', requestData, false, 'json');
	 $.each(sorItemsList, function(i, value) {
		 var code=value.sorDDescription+"$#"+value.sorIteamUnit+"$#"+value.sorBasicRate+"$#"+value.sordId+"$#"+value.sorId;
		 $('#sorDIteamNo' + index).append(
				 $("<option></option>").attr("value", value.sorDIteamNo).attr("code",
						 code).text(
								 value.sorDIteamNo));
	 });
	 $('#sorDIteamNo' + index).trigger('chosen:updated');
	 $('#sorDDescription' + index).val("");
	 $('#sorIteamUnit' + index).val("");
	 $('#sorBasicRate' + index).val("");
 }

 function getSorRevisedItmsDescription(element,currentRow){
	 
	 var errorList = [];
		if (errorList.length == 0) {
			$('.revisedAppendableClass').each(function(i) {
				if (currentRow != i && (element.value == $("#sorDIteamNo" + i).val())) {
					$("#sorDIteamNo" + currentRow).val("");
					errorList.push(getLocalMessage("wms.dup.itemNo.not.allow"));
					$("#errorDiv").show();
					showErr(errorList);
					return false;
				}
			});
		} else if(errorList.length > 0) {
				$("#sorDIteamNo" + currentRow).val("");
				showErr(errorList);
				return false;
			}	 	 
		var unitRateString=$("#sorDIteamNo"+currentRow).find("option:selected").attr('code');
		var sorDdId=$("#sordCategory"+currentRow).val();
		if(unitRateString!="" && unitRateString!=undefined){
		var rateAndunit = $("#sorDIteamNo"+currentRow).find("option:selected").attr('code').split("$#");
		$("#sorDDescription"+currentRow).val(rateAndunit[0]);
		$("#sorBasicRate"+currentRow).val(rateAndunit[2]);
		$("#sorIteamUnit"+currentRow).val(rateAndunit[1]);
		//$("#sorIteamUnit2"+index).val(rateAndunit[1]);
		$("#sorDetailsId"+currentRow).val(rateAndunit[3]);
		$("#sorId"+currentRow).val(rateAndunit[4]);
		
		/*$("#sorCheckBox"+index).val(true);*/
		$("#addMeasurement" + currentRow).attr("onclick","measurementSheetAction('selectAllSorDataBySorId',"+rateAndunit[3]+","+rateAndunit[4]+","+null+");");
		
		}
 }
  
 $("#estimateSorRevDetails").on("click", '.addRevisedButtonForSor',function(e) {
		
		if ( $.fn.DataTable.isDataTable('#estimateSorRevDetails') ) {
				$('#estimateSorRevDetails').DataTable().destroy();
			 }
			var errorList = [];	
			errorList = validateSorDetails(errorList);
			if(errorList.length > 0){
				$("#errorDiv").show();
				displayErrorsOnPage(errorList);
			}else{
				$("#errorDiv").hide();
	           e.preventDefault();	
	           var clickedRow = $('#estimateSorRevDetails tr').length-2;	
	           var category= $('#sordCategory'+clickedRow).val();
	 		  	var itemNo= $('#sorDIteamNo'+clickedRow).val();
				var content = $('#estimateSorRevDetails tbody tr').last().clone();
				$('#estimateSorRevDetails tbody tr').last().after(content);
				content.find("input:text").val('');
				content.find("textarea").val('');
				content.find("input:hidden").val('');
				content.find("select").val('');
				content.find("button").val('');
				content.find('div.chosen-container').remove();
				content.find("select:eq(0)").chosen().trigger("chosen:updated"); 
				content.find("select:eq(1)").chosen().trigger("chosen:updated"); 
				reOrderFromSorTableIdSequence();
				//$('#sordCategory'+(clickedRow+1)).val(category).trigger("chosen:updated");
				//$('#sorDIteamNo'+(clickedRow+1)).val(itemNo).trigger("chosen:updated");
			}												
			/*if(!validatedirectAbstractList()){
				$('#directAbstractTab').DataTable();
				return false;
			}*/						
		});

			  		   
	$("#estimateSorRevDetails").on("click", '.delRevButton', function(e) {
		
		if ( $.fn.DataTable.isDataTable('#estimateSorRevDetails') ) {
			 $('#estimateSorRevDetails').DataTable().destroy();
			}
			var countRows = -1;
			$('.revisedAppendableClass').each(function(i) {
			if ( $(this).closest('tr').is(':visible') ) {
					countRows = countRows + 1;			
			}
		});
		  row = countRows;
		  if (row != 0) {
			$(this).parent().parent().remove();
			row--;
			var estimateId= $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			var deleteSorDID = $(this).parent().parent().find('input[type=hidden]:last').attr('value');	
			
			
			if(estimateId != ''){
				removeSorRevised.push(estimateId);
			}
			if(estimateId == '' && deleteSorDID != null){
				removeWorkEstimateById.push(deleteSorDID);
			}
			$('#removeSorDId').val(removeWorkEstimateById);
			$('#removeEstimateDetails').val(removeSorRevised);
			  reOrderFromSorTableIdSequence();
		 }
		e.preventDefault();
	});	


	function reOrderFromSorTableIdSequence() {
		$('.revisedAppendableClass').each(function(i) {						
				
				$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
				$(this).find($('[id^="sordCategory"]')).attr('id',"sordCategory"+i+"_chosen");
				$(this).find($('[id^="sorDIteamNo"]')).attr('id',"sorDIteamNo"+i+"_chosen");
				$(this).find("select:eq(0)").attr("id", "sordCategory"+ i).attr("onchange","getAllItemsRevisedList("+i+");");
				$(this).find("select:eq(1)").attr("id", "sorDIteamNo"+ i).attr("onchange","getSorRevisedItmsDescription(this,"+i+");");
				$(this).find("select:eq(2)").attr("id", "sorIteamUnit"+ i);
				
				$(this).find("textarea:eq(0)").attr("id","sorDDescription" + i);
				$(this).find("input:text:eq(3)").attr("id","sorBasicRate" + i);
				$(this).find("input:text:eq(4)").attr("id","quanity" + i);
				$(this).find("input:text:eq(5)").attr("id","total" + i);
				
				$(this).find("input:hidden:eq(0)").attr("id", "sorCheckedEstimationId" + (i));
				$(this).find("input:hidden:eq(1)").attr("id" ,"sorId" + (i));
				$(this).find("input:hidden:eq(2)").attr("id", "sorDetailsId" + (i));
				$(this).find("input:hidden:eq(3)").attr("id", "sorIteamUnitHidden" + (i));
				
				
				$(this).find("button:eq(0)").attr("id", "addMeasurement"+ i);
				$(this).find("button:eq(1)").attr("id", "delRevButton"+ i);
				$(this).parents('tr').find('.delRevButton').attr("id", "delRevButton"+ i);

				$("#sequence" + i).val(i + 1);
				$(this).find("select:eq(0)").attr("name","revisedEstimateSorList[" + i+ "].sordCategory");
				$(this).find("select:eq(1)").attr("name","revisedEstimateSorList[" + i+ "].sorDIteamNo");
				$(this).find("select:eq(2)").attr("name","revisedEstimateSorList[" + i+ "].sorIteamUnit");
				
				$(this).find("textarea:eq(0)").attr("name","revisedEstimateSorList[" + i+ "].sorDDescription");
				$(this).find("input:text:eq(3)").attr("name","revisedEstimateSorList[" + i+ "].sorBasicRate");
				$(this).find("input:text:eq(4)").attr("name","revisedEstimateSorList[" + i+ "].workEstimQuantity");
				$(this).find("input:text:eq(5)").attr("name","revisedEstimateSorList[" + i+ "].workEstimAmount");
				
				$(this).find("input:hidden:eq(0)").attr("name", "revisedEstimateSorList[" + i+ "].workEstemateId");
				$(this).find("input:hidden:eq(1)").attr("name" ,"revisedEstimateSorList[" + i+ "].sorId");		
				$(this).find("input:hidden:eq(2)").attr("name","revisedEstimateSorList[" + i+ "].sordId");	
				$(this).find("input:hidden:eq(3)").attr("name","revisedEstimateSorList[" + i+ "].sorIteamUnit");
				//$(this).find("input:hidden:eq(4)").attr("name","revisedEstimateSorList[" + i+ "].workEstimAmount");
				
		  });
	}
 
	function validateSorDetails(errorList) {
		
		var errorList = [];
		var j = 0;
		$("#estimateSorRevDetails tbody tr").each(function(i) {
			var sorconstant = i + 1;
			var category = $("#sordCategory" + i).val();
			var itemCode = $("#sorDIteamNo" + i).val();
			var description = $("#sorDDescription" + i).val();
			var itemUnit = $("#sorIteamUnit" + i).val();
			var rate = $("#sorBasicRate" + i).val();
			var quantity = $("#quanity" + i).val();			
			if (category == "" || category == "0") {
				errorList.push(getLocalMessage("sor.select.category")+ " " + sorconstant);
			}
			
			if (itemCode == "" || itemCode == null) {
				errorList.push(getLocalMessage("work.estimate.enter.item.code")	+ " " + sorconstant);
			}
			if (description == "" || description == null) {
				errorList.push(getLocalMessage("work.estimate.enter.description")	+ " " + sorconstant);
			}
			if (itemUnit == "" || itemUnit == "0") {
				errorList.push(getLocalMessage("work.estimate.select.unit")	+ " " + sorconstant);
			}
			if (rate == "" || rate == "") {
				errorList.push(getLocalMessage("work.estimate.enter.rate")	+ " " + sorconstant);
			}
			/*if (quantity == "" || quantity == "0") {
				errorList.push(getLocalMessage("work.estimate.enter.quantity")	+ " " + sorconstant);
			}*/
							
		});
		return errorList;
	}

function measurementSheetAction(params,sordId,sorId,workEid){
	 
	/*var actionParam = {
			'sordId' : sordId,
			'sorId' : sorId,
			'workEid':workEid
	}*/
	if(workEid == null){
		workEid = 0;
	}
	var requestData = $("form").serialize() + '&sordId=' + sordId + '&sorId='
	+ sorId + '&workEid=' + workEid;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WorksRevisedEstimate.html' + '?' + params, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
	
$("#nonSorDetails").on("click", '.addNonSORDetails', function(e) {
	
	var count = $('#nonSorDetails tr').length - 1;
	var errorList = [];
	errorList = validateNonSORItemsDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#nonSorDetails tr').last().clone();
		$('#nonSorDetails tr').last().after(content);
		content.find("input:text").val('');
		content.find("select").attr("select", "select").val('');
		content.find("input:text").val('');
		content.find("input:hidden").val('');
		reOrderNonSORItemsDetails();
	}
});

function validateNonSORItemsDetails(errorList) {
	$(".appendableClass").each(function(i) {
		
		var itemCode = $("#sorDIteamNo" + i).val();
		var sorDesc = $("#sorDDescription" + i).val();
		var sorItemUnit = $("#sorIteamUnit" + i).val();
		var sorBasicRate = $("#sorRate" + i).val();
		var quantity = $("#workQuantity" + i).val();		
		var nonSorConstant = i + 1;
		if (itemCode == "") {
			errorList.push(getLocalMessage("work.estimate.enter.item.code")	+ " " + nonSorConstant);
		}
		if (sorDesc == "") {
			errorList.push(getLocalMessage("work.estimate.enter.description")	+ " " + nonSorConstant);
		}
		if (sorItemUnit == "") {
			errorList.push(getLocalMessage("work.estimate.select.unit")+ " " + nonSorConstant);
		}
		if (sorBasicRate == "") {
			errorList.push(getLocalMessage("work.estimate.enter.rate") + " " + nonSorConstant);
		}
		if (quantity == "") {
			errorList.push(getLocalMessage("work.estimate.enter.quantity")+ " " + nonSorConstant);
		}
	});
	return errorList;
}

function reOrderNonSORItemsDetails() {
	$('.appendableClass').each(function(i) {
		
		$(this).find("input:hidden:eq(0)").attr("id","workEstemateId" + (i));
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("input:text:eq(1)").attr("id","sorDIteamNo" + (i));
		$(this).find("input:text:eq(2)").attr("id","sorDDescription" + (i));
		$(this).find("select:eq(0)").attr("id","sorIteamUnit" + (i));
		$(this).find("input:text:eq(3)").attr("id",
				"sorRate" + (i));
		$(this).find("input:text:eq(4)").attr("id",
				"workQuantity" + (i));
		$(this).find("input:text:eq(5)").attr("id",
				"workAmount" + (i));

		// Names
		$(this).find("input:hidden:eq(0)").attr("name",	"workEstimateNonSorList[" + (i)	+ "].workEstemateId");
		$("#sNo" + i).val(i + 1);
		$(this).find("input:text:eq(1)").attr("name", "workEstimateNonSorFormList[" + (i) + "].sorDIteamNo");
		$(this).find("input:text:eq(2)").attr("name","workEstimateNonSorFormList[" + (i)+ "].sorDDescription");
		$(this).find("select:eq(0)").attr("name","workEstimateNonSorFormList[" + (i)+ "].sorIteamUnit");
		$(this).find("input:text:eq(3)").attr("name","workEstimateNonSorFormList[" + (i)+ "].sorBasicRate");
		$(this).find("input:text:eq(4)").attr("name","workEstimateNonSorFormList[" + (i)+ "].workEstimQuantity");
		$(this).find("input:text:eq(5)").attr("name","workEstimateNonSorList[" + (i)+ "].workEstimAmount");
		
	});
}

$("#nonSorDetails").on("click",'.deleteNonSORDetails',function(e) {
	
	var errorList = [];		
	if ($.fn.DataTable.isDataTable('#NonSorTable')) {
		$('#NonSorTable').DataTable().destroy();
	}
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
		var nonSORId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (nonSORId != '') {
			removeNonSorById.push(nonSORId);
		}
		$('#removeNonSorById').val(removeNonSorById);
		reOrderNonSORItemsDetails();
	}
	e.preventDefault();			
});

$("#nonSorDetails").on('input', '.calculation', function() {
	
	$('.appendableClass').each(function(i) {
		
		var sum = 0;
		var basicRate = parseFloat($("#sorRate" + i).val());
		var quantity = parseFloat($("#workQuantity" + i).val());
		if (!isNaN(quantity)) {
			sum = (basicRate * quantity);
		}
		if (!isNaN(sum)) {
			$("#workAmount" + i).val(sum.toFixed(2));
			var grandTotal = 0;
			$(".amount").each(function() {
				var nonSorTotal = parseFloat($(this).val());
				grandTotal += isNaN(nonSorTotal) ? 0 : nonSorTotal;
			});
			var tot = $("#totalnonSor").val(grandTotal.toFixed(2));
			$('#totalSor').html(grandTotal.toFixed(2));
		}
	});
});

function finalSaveRevisedEstimate(object) {
	
	var errorList = [];
	var contractNo = $("#contractId").val();
	var nonSor = $("#nonSor").val();
	var sor = $("#sor").val();
	var reviseFlag = $("#reviseFlag").val();
	var seletedSor = $('input[name=estimateRadioFlag]:checked').val();
	var actionParam = "";

	
	if (contractNo == "" || contractNo == "0") {
		errorList.push(getLocalMessage("work.contract.agreement.number"));
	}

	if (reviseFlag == "0" || reviseFlag == undefined || reviseFlag == "") {
		errorList.push(getLocalMessage("validation.revise.estimate.entry"));
	}
     if(seletedSor == undefined || seletedSor == ''){
    	 errorList.push(getLocalMessage("work.estimate.select.estimate.type"));
     }	
	if (seletedSor == "S") {
		errorList = validateSorDetails(errorList);
		actionParam = "saveSorDetails";
	} else if (seletedSor == "N") {
		errorList = validateNonSORItemsDetails(errorList);
		actionParam = "saveNonSorDetails";
	} 
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		var formName = findClosestElementId(object, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('WorksRevisedEstimate.html?' + actionParam,
				'POST', requestData, false);
		finalShowConfirmBox();
	}
}

function finalShowConfirmBox() {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '
			+ getLocalMessage('wms.revisedEstimate.save.success') + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForRevisedEstimate()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceedForRevisedEstimate() {
	
	var requestFormFlag = $("#requestFormFlag").val();

	if (requestFormFlag == 'WCV') {
		var divName = '.content-page';
		var url = "WorkRevisedEstimateApproval.html?showApprovalCurrentForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		$.fancybox.close();
		return false;
	} else {
		$("#postMethodForm").prop('action', 'WorksRevisedEstimate.html');
		$("#postMethodForm").submit();
		$.fancybox.close();
	}
}


function getActionForDefination(contId,mode){
	
	var estimateType = "";
		
	var divName = '.content-page';
	var url = "WorksRevisedEstimate.html?editViewRevisedEstimate";
	var actionParam = {
			'contId' : contId,
			'mode' :mode,
			'estimateType' : estimateType
		}	
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getContractDetailsByContId(contId);
	
}

function backForm() {
	
	var requestFormFlag = $("#requestFormFlag").val();
	if (requestFormFlag == 'WCV') {
		var divName = '.content-page';
		var url = "WorkContractVariationApproval.html?showApprovalCurrentForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		return false;
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'WorksRevisedEstimate.html');
		$("#postMethodForm").submit();
	}
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


function sendForApproval(contId,mode){
	
	var divName = '.content-page';
	var url = "WorksRevisedEstimate.html?sendForApproval";
	var requestData = {
		'contId' : contId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	/* $( ".searchContractVariation" ).trigger("click"); */
	showRevisedEstimateForApproval(ajaxResponse, contId, mode);
}

function showRevisedEstimateForApproval(ajaxResponse, contId, mode) {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	var sMsg = '';
	if (ajaxResponse.checkStausApproval == "Y") {
		sMsg = getLocalMessage("work.contract.variation.initiated.approval");
	} else if (ajaxResponse.checkStausApproval == "N") {
		sMsg = getLocalMessage("work.contract.variation.approval.not.defined");
	} else {
		sMsg = getLocalMessage("work.contract.variation.approval.initiation.fail");
		sMsg1 = getLocalMessage("work.contact.administrator");
	}

	if (ajaxResponse.checkStausApproval == "Y") {
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>' + '</div>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
				+ ' onclick="proceed()"/>' + '</div>';
	} else if (ajaxResponse.checkStausApproval == "N") {
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>' + '</div>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
				+ ' onclick="proceed()"/>' + '</div>';
	} else {
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center red padding-12">' + sMsg + '</p>'
				+ '<p class="text-center red padding-12">' + sMsg1 + '</p>'
				+ '</div>';
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

function proceed() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WorksRevisedEstimate.html');
	$("#postMethodForm").submit();
}

/*$("#estimateSorRevDetails").on('change', '.calculation', function() {
	
	$('.appendableClass').each(function(i) {
		
		var sum = 0;
		var basicRate = parseFloat($("#sorBasicRate" + i).val());
		var quantity = parseFloat($("#quanity" + i).val());
		var labourrate = parseFloat($("#sorLabourRate" + i).val());
		if (!isNaN(quantity)) {
			if (isNaN(labourrate)) {
				sum = (basicRate * quantity);
			} else {
				sum = (basicRate * quantity + labourrate);
			}
		}
		if (!isNaN(sum)) {
			$("#total" + i).val(sum.toFixed(2));
			var grandTotal = 0;
			$(".amount").each(function() {
				var sorTotal = parseFloat($(this).val());
				grandTotal += isNaN(sorTotal) ? 0 : sorTotal;
			});
			var tot = $("#totalnonSor").val(grandTotal.toFixed(2));
			$('#totalSor').html(grandTotal.toFixed(2));
		}
	});
});*/


/*function getTableDetails(element, contractNo, status) {
	
	var errorList = [];
	var status = $("#status").val();
	var contractNo = $("#contractId").val();

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
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var actionParam = {
			'value' : element.value,
			'contractNo' : contractNo,
			'status' : status
		}

		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('RevisedEstimate.html?sorAndNonSorList',
				'POST', actionParam, false, 'html');
		$("#sortablevariation").html(response);		
		prepareTags();
	}	
}

function getDetails() {
	
	var status = $("#status").find("option:selected").attr('value');
	if (status == undefined || status == 0) {
		return false;
	}
	if (status != '' && status != undefined && status != 0) {
		$("selectContractId").val(status);
		var divName = '.content-page';
		var requestData = {
			'status' : status,
		}
		var response = __doAjaxRequest(
				'RevisedEstimate.html?sorAndNonSorList', 'POST',
				requestData, false, 'json');

		if ($('input[name="estimateRadioFlag"]').attr('checked', false));
		{
			$("#sortablevariation").html(false);
		}
	}
}
*/