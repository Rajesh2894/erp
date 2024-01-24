$(document).ready(function() {
	showSingleMultiple();
});

function SearchButton(element) {
		$(element).prop('disabled',true);
		var data = $('#ViewPropertyDetail').serialize();
		var URL = 'ViewPropertyDetail.html?searchData';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
		$(element).prop('disabled',false);
		reloadGrid('gridViewPropertyDetail');
}

function displayError(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}
 
 function ViewAssDetails(bmid){
	 	var data={
	 		"bmIdNo":bmid
	 	};
		var URL='ViewPropertyDetail.html?viewPropDet';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
}
  function downoladBill(bmid){
	 	var data={
	 		"bmIdNo":bmid
	 	};
		var URL='ViewPropertyDetail.html?propertyBillDownload';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		//var filepath=$("#billFilePath").val();
		//alert("filepath"+returnData);
		window.open(returnData,'_blank' );
		//$(formDivName).html(returnData);
}
  
 function downoladReceipt(reciptId,receiptNo){
	 	var data={
	 		"reciptId":reciptId,
	 		"receiptNo":receiptNo
	 	};
		var URL='ViewPropertyDetail.html?receiptDownload';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		if(returnData!=""){
		var title='Revenue Receipt';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');		
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();	
		}		
}
 
function ViewBillDetails(bmid){
	 	var data={
	 		"bmIdNo":bmid
	 	};
		var URL='ViewPropertyDetail.html?viewBillDet';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$("#viewPropDet").html(returnData);
}

function backToViewProprtyDeatils(){
	 var data={};
			var URL='ViewPropertyDetail.html?backToViewProprtyDeatils';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$("#viewPropDet").html(returnData);
}
 
 function BackToDetails(){
	 var data={};
			var URL='ViewPropertyDetail.html?backToPropDet';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$(".content").html(returnData);
 }
 

 
 function BackToSearch(){
	 var data={};
			var URL='ViewPropertyDetail.html?backToSearch';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$(formDivName).html(returnData);
}

 function Reset(){
	 var data={};
			var URL='ViewPropertyDetail.html?resetViewProp';
			var returnData=__doAjaxRequest(URL, 'POST', data, false);
			$(formDivName).html(returnData);
 }
 
 function addDataEntryDetails(){
	 showloader(true);
	    setTimeout(function(){	
	 var data={};
		var URL='DataEntrySuite.html?addDataEntryDetails';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);},2);	 
 }
 
 function SearchDESProperties(element) {
	 showloader(true);
	 setTimeout(function(){
		 codeSearchDESProperties(element)	
	 },2);
 }
 
 function codeSearchDESProperties(element){
	$(element).prop('disabled',true);
	var data = $('#DataEntrySuite').serialize();
	var URL = 'DataEntrySuite.html?searchData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	 $(element).prop('disabled',false);
	reloadGrid('gridDataEntrySuite');
 }
 
 $(document).ready(function() {
		reloadGrid('gridViewPropertyDetail');
	});
 
//fetch API details on selection of button 
 function getLandApiDetails(obj){
 	var landTypePrefix=$(".landValue").val();
 	var data = {};
 	var URL = 'ViewPropertyDetail.html?getLandTypeApiDetails';
 	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
 	$("#showAuthApiDetails").html(returnData);
 	$("#showAuthApiDetails").show();		
 }
 
 function downoladSpecialNotice(noticeNo){
	 	var data={
	 		noticeNo:noticeNo
	 	};
		var URL='ViewPropertyDetail.html?propertySpecialNoticeDownload';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);

		var divName = formDivName;
		$('.content').removeClass('ajaxloader');
		$(divName).html(returnData);
		prepareTags();
		/*if(returnData!=""){
		var title='Notice';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');		
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();	
		}*/		
}
 


 function sendNotification(element) {

	var assNo = $("#assNo").val();
	var data = {
		"assNo" : assNo
	};
	var URL = 'ViewPropertyDetail.html?sendNotification';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'json');
	showAlertBox(returnData);
}
 
 function showAlertBox(returnData) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	if (returnData) {
		var messages = getLocalMessage('property.notificationSent');
	} else {
		var messages = getLocalMessage('property.problemOccurredWhileSend');
	}
	var cls = getLocalMessage('bt.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">' + messages
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}
 
function closeAlertForm() {
	var childDivName = '.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

function showSingleMultiple() {
	
	if ($("input[name='searchDto.specNotSearchType']:checked").val() == "IV") {
		$('#parentGrp1').val('');
		$('#parentGrp2').val('');
		$('.individualProp').show();
		$('.parentPropNos').hide();
	} else if ($("input[name='searchDto.specNotSearchType']:checked").val() == "GP") {
		$('#proertyNo').val('');
		$('#oldPid').val('');
		$('#ownerName').val('');
		$('#mobileno').val('');
		$('#assWard1').val('');
		$('#assWard2').val('');
		$("#locId").val("").trigger("chosen:updated");
		$('#propLvlRoadType').val('');
		$('#assdConstruType').val('');
		$('#assdUsagetype1').val('');
		$('.individualProp').hide();
		$('.parentPropNos').show();
	}
}
