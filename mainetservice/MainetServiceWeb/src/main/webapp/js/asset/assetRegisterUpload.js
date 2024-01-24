var UPLOADURL="AssetRegisterUpload.html";
var IT_ASSET_UPLOADURL ="ITAssetRegisterUpload.html";
function downloadTamplate(){
	var pgName =$('#atype').val()
	
	if(pgName == 'IAST'){
		window.location.href=IT_ASSET_UPLOADURL+"?ExcelTemplateData";
	}else{
		window.location.href=UPLOADURL+"?ExcelTemplateData";
	}
	
	
}

function uploadExcelFile1(){
	
	var errorList=[];
	var fileName=$("#uploadFileName").val().replace(/C:\\fakepath\\/i, '');
	if(fileName==null || fileName==""){
		errorList.push(getLocalMessage("excel.upload.vldn.error"));
	}
	if(errorList.length == 0){
		$("#filePath").val(fileName);
		var requestData = $.param($('#AssetRegisterUpload').serializeArray())
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(UPLOADURL+ "?loadExcelData",requestData, 'html');
		 var status=$(ajaxResponse).find('#successFlag'); 
		$('.content').removeClass('ajaxloader');
		if(status.val() == "E"){
			$(".tableDiv").hide();
			$("#errorTable").show();
		}else{
			showMessageOnSubmit(status.val())	
			
		}
		$(divName).html(ajaxResponse);
		prepareTags();
	}else{
		displayErrorsOnPage(errorList);
	}
	
	
	
}
function BackSearch(){
	
	window.location.href=UPLOADURL
}

//final summit confirm box	
function showMessageOnSubmit(message) {
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var cls = 'Proceed';
	
	var d	='<h5 class=\'text-center text-blue-2 padding-5\'>'+message+'</h5>';
	d	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', 
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}
function proceed() {
	$.fancybox.close();
}
function uploadExcelFile() {

	var errorList=[];
	var fileName=$("#uploadFileName").val().replace(/C:\\fakepath\\/i, '');
	if(fileName==null || fileName==""){
		errorList.push(getLocalMessage("excel.upload.vldn.error"));
	}
	if(errorList.length == 0){
		$("#filePath").val(fileName);
		var requestData = $.param($('#AssetRegisterUpload').serializeArray())
		var divName = '.content-page';
		var pgName = $('#atype').val()
		var url = "AssetRegisterUpload.html"+ "?loadExcelData"
		if(pgName == 'IAST'){
			url = "ITAssetRegisterUpload.html"+ "?ITAssetLoadExcelData"
		}
$.ajax({
	type:"POST",
	url : url,
	data : requestData,
	dataType:"html",
	 beforeSend: function() { 	
	      $("#button-save").html('<option>Please wait...</option>');
	      $("#button-save").prop('disabled', true);
	      $("#reset").prop('disabled', true);
	      $(".btn-danger").prop('disabled', true);   
	      $("#import").prop('disabled', true);  
	      $(".customfileupload ").prop('disabled', true);
	      $("#0_file_0").prop('disabled', true);
	    },
	    success : function(response) {
	    	 $("#button-save").html(getLocalMessage("ast.upload.saveexcel"));
		      $("#button-save").prop('disabled', false);
		      $("#reset").prop('disabled', false);
		      $(".btn-danger").prop('disabled', false);
		      $("#import").prop('disabled', false);  
		      $(".customfileupload ").prop('disabled',false); 
		      $("#0_file_0").prop('disabled', false); 
	    	 var status=$(response).find('#successFlag'); 
	    	 $('.content').removeClass('ajaxloader');
	 		if(status.val() == "E"){
	 			$(".tableDiv").hide();
	 			$("#errorTable").show();
	 			$(divName).html(response);
	 		}else{
	 			$(divName).html(response);
	 			showMessageOnSubmit(status.val())		
	 		}			 
			},
});
prepareTags();
}
else{
	displayErrorsOnPage(errorList);
}
}