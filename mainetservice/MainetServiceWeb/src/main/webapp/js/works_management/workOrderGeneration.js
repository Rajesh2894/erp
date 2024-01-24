/**
 * @author vishwajeet.kumar
 * @since 14 May 2018
 */

var WorkOrderURL = "PwWorkOrderGeneration.html";
var fileArray = [];
var termsCondById=[];

$(document).ready(function() {
					/**
					 * This method is used to Search All Works Order Generation
					 * workOrderSummary.jsp START METHOD
					 */
        $("#searchWorkOrder").click(function() {
        	
			var errorList = [];
			var workOrderNo = $('#workOrderNo').val();
			var workOrderDate = $('#workOrderDate').val();
			//var vendorName = $('#vendorName').val();
			var contractFromDate = $('#contractFromDate').val();
			var contractToDate = $('#contractToDate').val();
			
			if (workOrderNo != ''|| workOrderDate != ''|| contractFromDate != ''
					|| contractToDate != '') {
				var requestData = 'workOrderNo='+ workOrderNo+ '&workOrderDate='+ workOrderDate+  
				  '&contractFromDate='+ contractFromDate+ '&contractToDate='+ contractToDate;
						var table = $('#datatables').DataTable();
						table.rows().remove().draw();
						$(".warning-div").hide();
						var ajaxResponse = __doAjaxRequest(WorkOrderURL+ '?filterWorkOrderListData', 'POST',requestData, false,'json');
				
				var result = [];								
				$.each(ajaxResponse,function(index) {
					var obj = ajaxResponse[index];
					result.push([obj.workOrderNo,/*obj.orderDateDesc,*/obj.workName,obj.contractMastDTO.contNo, obj.actualStartDateDesc,'<td >'
					  + '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="editAndViewWorkOrder(\''+ obj.workId + '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
					  + '<button type="button" class="btn btn-primary btn-sm margin-right-10 "  onclick="editAndViewWorkOrder(\''+ obj.workId + '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
					  + '<button type="button" class="btn btn-warning btn-sm margin-right-10" onClick="workPrintWorkOrder(\''+obj.workId  +'\')"  title="Print Work Order"><i class="fa fa-print"></i></button>'
			+ '</td>' ]);
});
	table.rows.add(result);
	table.draw();
	} else {
	    errorList.push(getLocalMessage('work.management.valid.select.any.field'));
		displayErrorsOnPage(errorList);
	}
 });

   $("#datatables").dataTable(
		{
			"oLanguage" : {
				"sSearch" : ""
				},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],[ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$('.datepickers').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "-100:-0"
	});	
	
	$('.datepickers1').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate :'0D',
		yearRange : "-100:-0"
	});
	
	 getContractDetails();
	// getTendorDetails();
});

function openAddWorkOrderGeneration(formUrl, actionParam) {	 
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getContractDetails(){
	var contId = $("#contId").find("option:selected").attr('value');
	if(contId == undefined ||contId == 0 ){
		$("#contractFromDate").val('');
		$("#contractToDate").val('');
		$("#vendorName").val('');	
		$("#workName").val('');
		$("#contToPeriod").val('');
		$("#contractValue").val('');
		$("#tenderNo").val('');
		$("#tenderdate").val('');
		return false;
	}
	if(contId != '' && contId != undefined && contId != 0){
		$("selectContractId").val(contId);
		var divName = '.content-page';
		var requestData = {
				'contId' : contId,
			}
		var ajaxResponse = __doAjaxRequest(WorkOrderURL + '?getWorkName', 'POST', requestData, false, 'json');
		$("#workName").val(ajaxResponse);
	}
	var contractDet = $("#contId").find("option:selected").attr('code').split(",");

	$("#contractFromDate").val(contractDet[0]);
	$("#contractToDate").val(contractDet[1]);
	$("#vendorName").val(contractDet[2]);	
	
	$("#contractValue").val(contractDet[3]);
	$("#contToPeriod").val(contractDet[4]);
	$("#tenderNo").val(contractDet[5]);
	$("#tenderdate").val(contractDet[6]);
	$("#contToPeriodUnit").val(contractDet[7]);
	$("#deptId").val(contractDet[8]);

}



function saveWorkOrder(workOrderData){	 
	var errorList = [];
	
	var contId = $("#contId").find("option:selected").attr('value');
	var empId = $("#empId").find("option:selected").attr('value');
     			
	var contractFromDate = $("#contractFromDate").val();
	var contractToDate = $("#contractToDate").val();
	
	var startToDate = $("#startToDate").val();
	
	var workOrderDate = $("#workOrderDate").val();
	
	if(workOrderDate =='' || workOrderDate == null ){
		errorList.push(getLocalMessage("work.order.please.enter.workOrder.date"));
	}
	
	if(contId =='' || contId == '0' ){
		errorList.push(getLocalMessage("work.order.please.select.contract.no"));
	}
	
	if(startToDate == ''){
		errorList.push(getLocalMessage("work.order.please.enter.date.to.start.the.work"));
	}
	
	
	if (contractFromDate != ""  && contractToDate != "") {
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var fDate = new Date(contractFromDate.replace(pattern, '$3-$2-$1'));
	var tDate = new Date(contractToDate.replace(pattern, '$3-$2-$1'));
	var sDate = new Date(startToDate.replace(pattern, '$3-$2-$1'));
	if (sDate < fDate) {
		errorList.push(getLocalMessage("work.order.start.date.cannot.greater.then.agreement.from.date")+ " "+contractFromDate);
	}
	if(sDate >= tDate){
		errorList.push(getLocalMessage("work.order.start.date.cannot.greater.then.agreement.to.date")+ " " +contractToDate);
	}
}
	if(empId == ''){
		errorList.push(getLocalMessage("work.order.please.select.work.assignee"));
	}	
	//As per Demo(23/8/18) Discussion The terms and conditions Not Mandatory field
	
	//errorList = validateTermsAndCondition(errorList);
	
	if(errorList.length > 0){
		//$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}else{
		//$("#errorDiv").hide();
		
		return saveOrUpdateForm(workOrderData,getLocalMessage('work.order.creation.successfull'), WorkOrderURL, 'saveform');
	}
}

function validateTermsAndCondition(errorList){
	
	$(".workOrdertermsAppendableClass").each(function(i) {		
		var termsCondition = $("#workOrdertermsDesc" +i).val();
		var constant = i+1;
		
		if(termsCondition == '' || termsCondition ==undefined){
			errorList.push(getLocalMessage("work.order.please.enter.terms.and.condition")+" "+constant);
		}
	});
	
	return errorList;
}


$("#workOrdertermsAndCondition").on("click",'.addWorkOrdertermsAndCondition',function(e) {
	
	  var count = $('#workOrdertermsAndCondition tr').length - 1;
	  var errorList = [];
	  errorList = validateTermsAndCondition(errorList);	
      if (errorList.length > 0) { 
	       displayErrorsOnPage(errorList);
	 } 
    else {			  
			    e.preventDefault();
			    var clickedRow = $(this).parent().parent().index();
				var content = $('#workOrdertermsAndCondition tr').last().clone();
				$('#workOrdertermsAndCondition tr').last().after(content);
				content.find("input:hidden").attr("value", "");
				content.find("input:text").val('');
				content.find("textarea").val('');			
				reOrderTermsAndConditionFromGen();
			}
});

function reOrderTermsAndConditionFromGen(){
	
	$('.workOrdertermsAppendableClass').each(function(i){		 
		$(this).find("hidden:eq(0)").attr("id","workOrdertermsId" + (i));	
		$(this).find("input:text:eq(0)").attr("id" ,"sNo" + (i));
		$(this).find("textarea:eq(0)").attr("id" ,"workOrdertermsDesc" + (i));
		
		$(this).find("input:hidden:eq(0)").attr("name","workOrderDto.workOrderTermsDtoList[" + (i) + "].termsId");
		$(this).find("textarea:eq(0)").attr("name","workOrderDto.workOrderTermsDtoList[" + (i)+ "].termsDesc");
		$("#sNo" + i).val(i+1);
	});
}

$('#workOrdertermsAndCondition').on("click",'.deleteWorkOrderTermsDetails' , function(e){
	 
	var errorList = [];
	var count = 0;
	$('.workOrdertermsAppendableClass').each(function(i){
		count += 1;
	});
	var rowCount = $('#workOrdertermsAndCondition tbody tr').length;
	 if(rowCount <= 1){
		 errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	 }	 
	   if(errorList.length > 0){
		    displayErrorsOnPage(errorList);
			return false;
	 }else{
		 $(this).parent().parent().remove();
		 var termsAndConditionId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(termsAndConditionId != ''){
				termsCondById.push(termsAndConditionId);
				}
			$('#removeTermsCondById').val(termsCondById);
			reOrderTermsAndConditionFromGen();
	 }	
});

function editAndViewWorkOrder(workId,mode){ 
	debugger;
	var divName = '.content-page';
	var requestData = {
			'workId' : workId,
			'mode'   : mode
		}
	var ajaxResponse = __doAjaxRequest(WorkOrderURL + '?editViewWorkOrderGeneration', 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/*function viewWorkOrder(workId){	 
	var divName = '.content-page';
	var requestData = {
			'workId' : workId,
		}
	var ajaxResponse = __doAjaxRequest(WorkOrderURL + '?viewWorkOrderGeneration', 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}*/

function workPrintWorkOrder(workId){	 
	var divName = '.content-page';
	var requestData = {
			'workId' : workId,
		}
	var ajaxResponse = __doAjaxRequest(WorkOrderURL + '?printWorkOrderGeneration', 'POST', requestData, false, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function viewContractDetails(){
	
	var errorList = [];
	var contId = $("#contId").find("option:selected").attr('value');
	if(contId == '0'){
		errorList.push(getLocalMessage("work.order.please.select.contract.no"));
		 displayErrorsOnPage(errorList);
		return false;
	}else {
		var divName = '.content-page';
		var url = "PwWorkOrderGeneration.html?viewContractDetails";
		var actionParam = $("form").serialize() + '&contId=' + contId ;
		var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
		$('.content').removeClass('ajaxloader');
		$('.content').html(ajaxResponse);
		
		var type ='V';
	//	showContractForm('ContractAgreement.html',contId,type);
		
	$("#partyDetails :input").prop("disabled", true);
	    $("#ContractAgreement :input").prop("disabled", true);
		 $('.addCF3').attr('disabled',true);
		 $('.addCF4').attr('disabled',true);
		 $('.addCF5').attr('disabled',true);
		 $('.addCF2').attr('disabled',true);
		 $('.remCF2').attr('disabled',true);
		 $('.remCF3').attr('disabled',true);
		 $('.remCF4').attr('disabled',true);
		 $('.remCF5').attr('disabled',true);
		 $('.uploadbtn').attr('disabled',false);
		 $(".backButton").removeProp("disabled");
		 $("#partyDetails").removeProp("disabled");
	}
}
 function showContractForm(formUrl,contId,type){
	 
	 var showForm="WOG";
     var requestData = 'contId='+contId+'&type='+type+'&showForm='+showForm;
	 var ajaxResponse	=	doAjaxLoading(formUrl+'?form', requestData, 'html');
	 $('.content').removeClass('ajaxloader');
	 $('.content').html(ajaxResponse);
	 prepareTags();
	}

function backWorkOrder(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', WorkOrderURL);
	$("#postMethodForm").submit();
}

function resetWorkOrder() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', WorkOrderURL);
	$("#postMethodForm").submit();
}
function backWorkOrderGeneration(){
		
  var divName = '.content-page';
  var url = "PwWorkOrderGeneration.html?showWorkOrderCurrentForm";
  var requestData = {};
  var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
  $(divName).removeClass('ajaxloader');
  $(divName).html(ajaxResponse);
  prepareTags();		
}

function resetFormLevel(resetBtn) {	
		resetBtn.form.reset();
		$('[id*=file_list]').html('');
        $("#contId").trigger("chosen:updated");
        prepareTags();
}

function otherDeletionTask(){
	return false;
}
function otherTask() {
	return false;
}

function fileCountUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var URL ="PwWorkOrderGeneration.html?fileCountUpload";
	var response = __doAjaxRequest(URL,'POST', requestData, false, 'html');
	$("#workOrderAttachment").html(response);
	prepareTags();
	//Defect #155385
	var addTitle = getLocalMessage("works.management.add");
	var deleteTitle = getLocalMessage("works.management.delete");
	$('.addButton').attr('title',addTitle);
	$('.delButton').attr('title',deleteTitle);
}

$("#attachDoc").on("click", '.delButton', function(e) {
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

	}
	e.preventDefault();
});

$("#deleteDoc").on("click",'#deleteFile',function(e) {
		var errorList = [];
		if (errorList.length > 0) {
			$("#errorDiv").show();
			 displayErrorsOnPage(errorList);
		     return false;
		} else {
			$(this).parent().parent().remove();
			var fileId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if (fileId != '') {
				fileArray.push(fileId);
			}
			$('#removeFileById').val(fileArray);
		}
	});



//#71812
function addTermsDetails(){
	
	var errorList = [];
	//errorList = validateEntryDetails();
	if (errorList.length == 0) {
		//addTableRow('customFields2');
		var content = $('#workOrdertermsAndCondition tr').last().clone();
		$('#workOrdertermsAndCondition tr').last().after(content);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		reorderDropDownTermCon();
	} else {
		$('#workOrdertermsAndCondition').DataTable();
		displayErrorsOnPage(errorList);
	}

}

function reorderDropDownTermCon() {
	
	$('.workOrdertermsAppendableClass').each(
			function(i) {
				var count = i + 1;
				
				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i)).val(i + 1);
				
				$(this).find("select:eq(0)")
				.attr("id", "workOrdertermsDesc" + (i)).attr(
						"name",
						"workOrderDto.workOrderTermsDtoList["
								+ (i) + "].termsDesc");

			});
}



function getAgrementByDeptId(){ 
	var divName = '.content-page';
	var dpDeptId = $("#deptId").val();
	var requestData = {
			'dpDeptId' : dpDeptId
		}
	var ajaxResponse = __doAjaxRequest(WorkOrderURL + '?getAgrementByDeptId', 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
