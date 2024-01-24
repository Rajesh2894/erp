/**
 * @author vishwajeet.kumar
 * New Changes Measurement sheet required on Work Revised Estimate
 *  @since 12 feb 2019
 */
 var removeIdArray=[];
$(document).ready(function() {
	
	calculateTotalRE();
	
	$('.decimal').on('input', function() {
    this.value = this.value
      .replace(/[^\d.]/g, '')             // numbers and decimals only
      .replace(/(^[\d]{4})[\d]/g, '$1')   // not more than 4 digits at the beginning
      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
  });
	
	function triggerDatatable(){
		$("#WorRevisedMSDetailsTab").dataTable({
			"oLanguage": { "sSearch": "" } ,
			"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
		    "iDisplayLength" : 10, 
		    "bInfo" : true,
		    "lengthChange": true,
		    "scrollCollapse": true,
		    "bSort" : false
		   }).fnPageChange( 'last' );	 	 
	}
	
});
$('#WorRevisedMSDetailsTab').on("click",'.addReButton',function(e){
	
	if ( $.fn.DataTable.isDataTable('#WorRevisedMSDetailsTab') ) {
		  $('#WorRevisedMSDetailsTab').DataTable().destroy();
		}
	var errorList = [];	
	errorList = validateREMSDetails(errorList);
	if(errorList.length > 0){
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}else{
		$("#errorDiv").hide();
		  e.preventDefault();
		  var content = $('#WorRevisedMSDetailsTab tr').last().clone();
		  $('#WorRevisedMSDetailsTab tr').last().after(content);
		  content.find("input:text").val('');
		  content.find("select").val('');
		  content.find("input:hidden").val('');
		  content.find("input:text:eq(2)").attr("id","meMentNumber" + i).val("1");
		  content.find("select:eq(0)").attr("name","measureDetailsREList[" + i+ "].meMentValType").val('C');
		  content.find("select:eq(1)").attr("name","measureDetailsREList[" + i+ "].meMentType").val('A');

		  $('.decimal').on('input', function() {
			    this.value = this.value
			      .replace(/[^\d.]/g, '')             // numbers and decimals only
			      .replace(/(^[\d]{4})[\d]/g, '$1')   // not more than 4 digits at the beginning
			      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
			      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
			  });
		  reOrderTableIdRESequence();
	}	
});

function validateREMSDetails(errorList){
	
	if ( $.fn.DataTable.isDataTable('#WorRevisedMSDetailsTab') ) {
		  $('#WorRevisedMSDetailsTab').DataTable().destroy();
		}
	$('.appendableClass').each(function(i) {
		var particulars = $("#meMentParticulare" + i).val();
		var subTotal = $("#meMentToltal" + i).val();
		var measurementList = i+1;	
		
		if(particulars == null || particulars == ""){
			errorList.push(getLocalMessage("work.measurement.sheet.details.enter.particulars")+" "+measurementList); 
		}
		if(subTotal =="" || subTotal =="0"){
			errorList.push(getLocalMessage("work.measurement.sheet.details.sub.total.does.not.blank")+" "+measurementList);
		}				   
	});	
	return errorList;	
}

function reOrderTableIdRESequence(){

	$('.appendableClass').each(function(i) {
		
		$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
		$("#sequence"+i).val(i+1);
		/*meValue*/
		$(this).find("select:eq(0)").attr("id", "meMentValType"+ i);
		$(this).find("select:eq(1)").attr("id", "meMentType"+ i);
		$(this).find("input:hidden:eq(0)").attr("id", "meMentId"+ i);
		
	 	$(this).find("input:text:eq(1)").attr("id","meMentParticulare" + i);
		$(this).find("input:text:eq(2)").attr("id","meMentNumber" + i);
		$(this).find("input:text:eq(3)").attr("id","meMentFormula" + i);
		$(this).find("input:text:eq(4)").attr("id","meMentLength" + i);
		$(this).find("input:text:eq(5)").attr("id","meMentBreadth" +i);
		$(this).find("input:text:eq(6)").attr("id","meMentHeight" + i);
		$(this).find("input:text:eq(7)").attr("id","meValue" + i);
		$(this).find("input:text:eq(8)").attr("id","meMentToltal" + i);

		$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
		$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		
		$(this).find("select:eq(0)").attr("name","measureDetailsREList[" + i+ "].meMentValType");
		$(this).find("select:eq(1)").attr("name","measureDetailsREList[" + i+ "].meMentType");
	 	
		$(this).find("input:text:eq(1)").attr("name","measureDetailsREList[" + i+ "].meMentParticulare");
		$(this).find("input:text:eq(2)").attr("name","measureDetailsREList[" + i+ "].meMentNumber");
		$(this).find("input:text:eq(3)").attr("name","measureDetailsREList[" + i+ "].meMentFormula");
		$(this).find("input:text:eq(4)").attr("name","measureDetailsREList[" + i+ "].meMentLength");
		$(this).find("input:text:eq(5)").attr("name","measureDetailsREList[" + i+ "].meMentBreadth");
		$(this).find("input:text:eq(6)").attr("name","measureDetailsREList[" + i+ "].meMentHeight");
		$(this).find("input:text:eq(7)").attr("name","measureDetailsREList[" + i+ "].meValue");
		$(this).find("input:text:eq(8)").attr("name","measureDetailsREList[" + i+ "].meMentToltal");
		$(this).find("input:hidden:eq(0)").attr("name","measureDetailsREList[" + i+ "].meMentId");
	 	
	});
	triggerDatatable();	
}
function triggerDatatable(){
	$("#WorRevisedMSDetailsTab").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' );	 	 
}

$("#WorRevisedMSDetailsTab").on("click", '.delButton', function(e) {
	
	if ( $.fn.DataTable.isDataTable('#WorRevisedMSDetailsTab') ) {
		$('#WorRevisedMSDetailsTab').DataTable().destroy();
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
			removeIdArray.push(deletedSodId);
		}
		$('#removeREMsheet').val(removeIdArray);
		reOrderTableIdRESequence();
	}
	e.preventDefault();
});

function calculateTotalRE() {

	var errorList =[];
	var subTotal=0;
	$('.appendableClass').each(function(i) {
				
		var toatlAmount=0.00;
	 	$("#meMentFormula"+i).prop("readonly", true);
 		$("#meMentLength"+i).prop("readonly", true);
 		$("#meMentBreadth"+i).prop("readonly", true);
 		$("#meMentHeight"+i).prop("readonly", true);
 		$("#meValue"+i).prop("readonly", true);
	 	
	 	if($("#meMentValType"+i).val()=="C"){
	 		$("#meMentLength"+i).prop("readonly", false);
	 		$("#meMentBreadth"+i).prop("readonly", false);
	 		$("#meMentHeight"+i).prop("readonly", false);
	 		$("#meMentFormula"+i).val("");
	 		
	 		if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0 && $("#meMentBreadth"+i).val()!="" && $("#meMentBreadth"+i).val()!=0 && $("#meMentHeight"+i).val() !="" && $("#meMentHeight"+i).val() !=0)
	 			toatlAmount=$("#meMentLength"+i).val() * $("#meMentBreadth"+i).val() * $("#meMentHeight"+i).val();
	 		else if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0 && $("#meMentBreadth"+i).val()!="" && $("#meMentBreadth"+i).val()!=0 )
	 			toatlAmount=$("#meMentLength"+i).val() * $("#meMentBreadth"+i).val();
	 		else if($("#meMentHeight"+i).val() !="" && $("#meMentHeight"+i).val() !=0 && $("#meMentBreadth"+i).val()!="" && $("#meMentBreadth"+i).val()!=0)
	 			toatlAmount= $("#meMentBreadth"+i).val() * $("#meMentHeight"+i).val();
	 		else if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0 && $("#meMentHeight"+i).val()!="" && $("#meMentHeight"+i).val()!=0)
	 			toatlAmount= $("#meMentLength"+i).val() * $("#meMentHeight"+i).val();
	 		else if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0)
	 			toatlAmount=$("#meMentLength"+i).val();
	 		else if($("#meMentBreadth"+i).val() !="" && $("#meMentBreadth"+i).val() !=0)
	 			toatlAmount=$("#meMentBreadth"+i).val();
	 		else if($("#meMentHeight"+i).val() !="" && $("#meMentHeight"+i).val() !=0)
	 			toatlAmount=$("#meMentHeight"+i).val();
	 		
	 	}else if($("#meMentValType"+i).val()=="D"){
	 		$("#meValue"+i).prop("readonly", false);
	 		$("#meMentLength"+i).val("");
	 		$("#meMentBreadth"+i).val("");
	 		$("#meMentHeight"+i).val("");
	 		toatlAmount=$("#meValue"+i).val();
	 	}else if($("#meMentValType"+i).val()=="F"){
	 		$("#meMentFormula"+i).prop("readonly", false);
	 		$("#meMentLength"+i).val("");
	 		$("#meMentBreadth"+i).val("");
	 		$("#meMentHeight"+i).val("");
	 		try {
	 			toatlAmount= eval($("#meMentFormula"+i).val()); 
	 		} catch (e) {
	 		    if (e instanceof SyntaxError) {
	 		    	toatlAmount=0.00;
	 		    	errorList.push(getLocalMessage("work.measurement.sheet.details.formula.validation ")+"  "+e.message);
	 		    	displayErrorsOnPage(errorList);
	 		 }
	 	}
	 }			 	
		$("#meMentToltal"+i).val("");
		if(toatlAmount!="")
		$("#meValue"+i).val((Number(toatlAmount)).toFixed(2));
		if($("#meMentType"+i).val()=="A" && toatlAmount!=undefined ){
			
			   var amountAdd=toatlAmount*$("#meMentNumber"+i).val();
			   if(amountAdd!="")
			   $("#meMentToltal"+i).val(amountAdd.toFixed(2));
			}else if($("#meMentType"+i).val()=="D" && toatlAmount!=undefined ){
			 	var amountSub=toatlAmount*$("#meMentNumber"+i).val();
			 	 if(amountSub!="")
			 	$("#meMentToltal"+i).val(-1*(amountSub.toFixed(2)));
			 }
		subTotal+=+$("#meMentToltal"+i).val();
			 	
	});
	 if(subTotal!="")
	$("#subTotal").val(subTotal.toFixed(2));
}

function saveWorkREMDetails(element){
	
	var errorList =[];
	errorList = validateREMSDetails(errorList); 
	if(errorList.length > 0){
		$('#WorRevisedMSDetailsTab').DataTable();
		$("#errorDiv").show();
	    displayErrorsOnPage(errorList);
	}else{
		 $('#WorRevisedMSDetailsTab').DataTable().destroy();
		 var formName = findClosestElementId(element, 'form');
		 var theForm = '#' + formName;
		 var requestData = __serializeForm(theForm);
		 var response = __doAjaxRequest('WorksRevisedEstimate.html?saveRevisedLbhForm', 'POST',requestData, false);
		 showConfirmBoxfor(getLocalMessage("works.estimate.measurement.sheet.save"));
	}
}
function showConfirmBoxfor(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedforSave()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}
	
function proceedforSave(){
	
	$.fancybox.close();
	var divName = '.content-page';
	var url = "WorksRevisedEstimate.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
			'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
	var ctId = $("#contractId").val();
	getContractDetailsByContId(ctId)
	//$("#contractId").trigger('change');	
	$("#sor").click();	
	prepareTags();	
	}

function backToRevisedEstimateForm(){
	
	var divName = '.content-page';
	var url = "WorksRevisedEstimate.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
			'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
	var ctId = $("#contractId").val();
	getContractDetailsByContId(ctId)
	$("#sor").click();	
	prepareTags();
}