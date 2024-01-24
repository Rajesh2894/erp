
 var removeIdArray=[];
$(document).ready(function() {
	
	$('.decimal').on('input', function() {
    this.value = this.value
      .replace(/[^\d.]/g, '')             // numbers and decimals only
      .replace(/(^[\d]{4})[\d]/g, '$1')   // not more than 4 digits at the beginning
      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
  });
	
	function triggerDatatable(){
		$("#sumotoDetailsTab").dataTable({
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
$('#sumotoDetailsTab').on("click",'.addReButton',function(e){
	
	if ( $.fn.DataTable.isDataTable('#sumotoDetailsTab') ) {
		  $('#sumotoDetailsTab').DataTable().destroy();
		}
	var errorList = [];	
	errorList = validateSumotoDetails(errorList);
	if(errorList.length > 0){
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}else{
		$("#errorDiv").hide();
		  e.preventDefault();
		  var content = $('#sumotoDetailsTab tr').last().clone();
		  $('#sumotoDetailsTab tr').last().after(content);
		  content.find("input:text").val('');
		  content.find("select").val('');
		  content.find("input:hidden").val('');
		  content.find("input:text:eq(2)").attr("id","amount" + i).val("0");
		  content.find("textarea:eq(0)").attr("id","resolutionComment" + i).val("");

		  content.find("select:eq(1)").attr("name","momSumotoDtos[" + i+ "].status").val('A');

		  /*$('.decimal').on('input', function() {
			    this.value = this.value
			      .replace(/[^\d.]/g, '')             // numbers and decimals only
			      .replace(/(^[\d]{8})[\d]/g, '$1')   // not more than 7 digits at the beginning
			      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
			      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
			  });*/
		  reOrderTableIdRESequence();
	}	
});


//validation method for momForm
function validateSumotoDetails(errorList) {
	
	$("#sumotoDetailsTab tbody tr").each(function(i) {
		let position = " at sr no. " + (i + 1);
		let detailsOfReso = $('#detailsOfReso'+i).val();
		let amount = $('#amount'+i).val();
		let resolutionComment = $("#resolutionComment"+i).val();
		let status = $("#status"+i).val();
		
		if (detailsOfReso == "" || detailsOfReso == undefined) {
			errorList.push(getLocalMessage("council.mom.resolutionDetails")+ position);
		}
		if (amount == "" || amount == undefined) {
			errorList.push(getLocalMessage("council.mom.resolutionAmount")+  position);
		}
		if (resolutionComment == "" || resolutionComment == undefined) {
			errorList.push(getLocalMessage("council.mom.resolutionCommentEmpty")+  position);
		}
		if (status == "0" || status == undefined || status == "") {
			errorList.push(getLocalMessage("council.mom.statusAction") +position);
		}
	});
	return errorList;
}

function reOrderTableIdRESequence(){

	$('.appendableClass').each(function(i) {
		
		$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
		$("#sequence"+i).val(i+1);
		/*meValue*/
		$(this).find("select:eq(0)").attr("id", "sumotoDepId"+ i);
		$(this).find("select:eq(1)").attr("id", "status"+ i);
		$(this).find("input:hidden:eq(0)").attr("id", "momId"+ i);
		
	 	$(this).find("input:text:eq(1)").attr("id","detailsOfReso" + i);
		$(this).find("input:text:eq(2)").attr("id","amount" + i);
		$(this).find("textarea:eq(0)").attr("id","resolutionComment" + i);
		

		$(this).find("input:button:eq(0)").attr("id", "delButton"+ i);
		$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		
		/*$(this).find("select:eq(0)").attr("name","momSumotoDtos[" + i+ "].sumotoDepId");
		$(this).find("select:eq(1)").attr("name","momSumotoDtos[" + i+ "].status");*/
	 	
		$(this).find("input:text:eq(1)").attr("name","momSumotoDtos[" + i+ "].detailsOfReso");
		$(this).find("input:text:eq(2)").attr("name","momSumotoDtos[" + i+ "].amount");
		$(this).find("textarea:eq(0)").attr("name","momSumotoDtos[" + i+ "].resolutionComment");
		$(this).find("input:hidden:eq(0)").attr("name","momSumotoDtos[" + i+ "].momId");
	 	
	});
	//triggerDatatable();	
}
function triggerDatatable(){
	$("#sumotoDetailsTab").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' );	 	 
}

$("#sumotoDetailsTab").on("click", '.delButton', function(e) {
	
	if ( $.fn.DataTable.isDataTable('#sumotoDetailsTab') ) {
		$('#sumotoDetailsTab').DataTable().destroy();
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
		
		let sumotoId = $(this).closest('tr').attr('id'); //this is only used when delete functionality present 
		
		//var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if(sumotoId != ''){
			removeIdArray.push(sumotoId);
		}
		$('#removeSumotoIds').val(removeIdArray);
		reOrderTableIdRESequence();
	}
	e.preventDefault();
});

function saveSumotoDetails(element){
	
	let errorList =[];
	errorList = validateSumotoDetails(errorList); 
	if(errorList.length > 0){
		$('#sumotoDetailsTab').DataTable();
		$("#errorDiv").show();
	    displayErrorsOnPage(errorList);
	}else{
		 $('#sumotoDetailsTab').DataTable().destroy();
		 var formName = findClosestElementId(element, 'form');
		 var theForm = '#' + formName;
		 var requestData = __serializeForm(theForm);
		 var response = __doAjaxRequest('CouncilMOM.html?saveSumotoDetailsForm', 'POST',requestData, false);
		 showConfirmBoxfor(getLocalMessage("council.sumoto.resolution.save"));
	}
}
function showConfirmBoxfor(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("council.mom.proceed");

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
	var url = "CouncilMOM.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
			'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
	/*var ctId = $("#contractId").val();
	getContractDetailsByContId(ctId)
	//$("#contractId").trigger('change');	
	$("#sor").click();	*/
	prepareTags();	
	}

function backToCreateMOMForm(){
	
	var divName = '.content-page';
	var url = "CouncilMOM.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
			'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
	prepareTags();
}