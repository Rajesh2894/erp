var array=[];
$(document)
		.ready(
				function() {
					
					$("#nestable ol").not(':has(li)').remove();	  //used for menu new one
					$("#nestable li").not(':has(ol)').removeClass('dd-list');
					$('#addDataBackButtonNo').show();
					$('#addDataBackButton').hide();
					var postJson={};
					$(".checkbox1").change(function () {
						
						if($(this).is(':checked')){
				  	    	array.push($(this).attr('id'));
				  	    					  	    	
				  	    }else{
				  	    	var value1=$(this).attr('id');
				  	    	array = jQuery.grep(array, function( item ) {
				  	    	  return item !== value1;
				  	    	});
				  	    }
				  	    //console.log("final String "+array.toString());
				  	});
					
					
					
					$('#dataEntitleSubmit').click(function(){
						
						var msg = '';
						if (array.length == 0) {
							msg += "<h5 class='text-blue-2'>"
									+ getLocalMessage('menu.node.select.trans.entitle')
									+ "</h5>";
						}
						

						if (msg != '') {
							$('.msg-dialog-box').html("<div class='padding-10'>" + msg + "</div>");
						    showModalBox('.msg-dialog-box');
						} else {
							saveRoleEntitleData($(this),getLocalMessage('menu.success.msg'),'entitlement.html?getSessionData');
						}
						
						
					});
					
					var res= __doAjaxRequest("entitlement.html?getRoleIds",'post', {}, false, 'json');
					$.each( res, function( index, value ){
					  $('#'+value).prop('checked', true);
					  array.push(value.replace(/\\/g, ''));
					});
					
					//console.log("page render string "+array.toString());
});

function saveRoleEntitleData(obj, successMessage, successUrl) {

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
    //alert(array.toString());
	requestData = "array="+array.toString();

	var url = "entitlement.html?saveDataEntitle";

	var returnData = __doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
		
	if ($.isPlainObject(returnData)) {
		var message = returnData.command.message;
       // alert(message);
		var hasError = returnData.command.hasValidationError;

		if (!message) {
			message = successMessage;
		}

		if (message && !hasError) {
			if (returnData.command.hiddenOtherVal == 'SERVERERROR')
				showSaveResultBox(returnData, message, 'AdminHome.html');

			else
				showSaveResultBox(returnData, message, successUrl);
		} else if (hasError) {
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');
		} else
			return returnData;

	} else if (typeof (returnData) === "string") {
		$(formDivName).html(returnData);
		prepareTags();
	} else {
		alert("Invalid datatype received : " + returnData);
	}

	return false;
}

