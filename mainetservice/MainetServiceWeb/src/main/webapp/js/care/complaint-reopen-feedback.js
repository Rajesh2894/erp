$(document).ready(function(){

	//$('#reopenForm').hide();
	$('#feedbackForm').hide();
	//$('#btnDiv2').hide();
	//$('#btnDiv3').hide();
	var star = $("#hiddenFeedbackRate").val();
	if(star !='' || star !=null){
		$('#rating-input').val(star);
		$('#hiddenFeedbackRateUpdtaed').val(star);
	} 

	$('#btnReopen').click(function() {
		
		//D#131629 1st check here complaint is valid for reopen or not
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('GrievanceDepartmentReopen.html?validComplForReopen', {}, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		
		$('#reopenForm').slideDown( "slow" );
		$('#btnDiv1').hide();
		$('#btnDiv2').show();
		
	});

	$('#btnFeedBack').click(function() {
		$('#feedbackForm').slideDown( "slow" );
		$('#btnDiv1').hide();
		$('#btnDiv3').show();
	});


	$('.btnCancel').click(function() {
		$('#reopenForm').slideUp( "slow" );
		$('#feedbackForm').slideUp( "slow" );
		$('#btnDiv1').show();
		$('#btnDiv2').hide();
		$('#btnDiv3').hide();
		$('#errorDiv').hide();
		$("#form_grievanceReopen").trigger("reset");
	});
	
	$("#id_complaintList").dataTable(
			{
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
						[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});

});

function searchAndViewComplaint(searchString){
	
	$("#errorDiv").hide();
	searchString = (searchString== null)?$(".searchString").val():searchString;
	var errorList=[];
	if(searchString == undefined || searchString == null || searchString == ""){
		errorList.push(getLocalMessage('care.validator.searchString'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = {
				"searchString":searchString,
				"hitFrom":"GrievanceDepartmentReopen.html"
		};
		var ajaxResponse = doAjaxLoading('GrievanceDepartmentReopen.html?searchGrievance', requestData, 'html',
				divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function reopenComplaint(obj){
	var errorList=[];
	var remark = $('#Remark').val();
	var reopeningReason = $('#reopeningReason').val();
	if(remark != undefined && remark != null)
		remark = remark.trim();
	if(remark == undefined || remark == null || remark == ""){
		errorList.push(getLocalMessage('care.validator.remark'));	
	}
	if(reopeningReason != undefined && reopeningReason != null)
		reopeningReason = reopeningReason.trim();
	if(reopeningReason == undefined || reopeningReason == null || reopeningReason == "" || reopeningReason == "0"){
		errorList.push(getLocalMessage('care.validation.error.reopeningReason'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,"", "GrievanceDepartmentReopen.html", 'reopenGrievance');
	}else{
		displayErrorsOnPage(errorList);
	}

}


function submitComplaintFeedbck(obj){
	var errorList=[];
	var remark = $('#feedback').val();
	var ratinginput=$('#rating-input').val();

	if(remark != undefined && remark != null)
		remark = remark.trim();
	if(remark == undefined || remark == null || remark == ""){
		errorList.push(getLocalMessage('care.validator.remark'));	
	}
	if (ratinginput == undefined || ratinginput=='0'|| ratinginput == "") {
		errorList.push(getLocalMessage('care.validator.ratinginput'));
	} 
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,getLocalMessage('care.feedback.save'), "GrievanceDepartmentReopen.html", 'grievanceFeedback');
	}else{
		displayErrorsOnPage(errorList);
	}

}

/*function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}*/

function printContent(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$("#btnDiv1, .table-pagination, .remove-btn, .paging-nav, tfoot tr.print-remove, .dataTables_length, .dataTables_filter, .dataTables_info, .dataTables_paginate").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}