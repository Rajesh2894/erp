$(document).ready(function() {

	yearLength();

});


 function SearchDESProperties(element) {
 		var data = $('#MutationIntimation').serialize();
 		var URL = 'MutationIntimation.html?searchData';
 		var returnData = __doAjaxRequest(URL, 'POST', data, false);
 		$(formDivName).html(returnData);
 		reloadGrid('gridDataEntrySuite');
 }

 function downloadRegDoc(bmid){
	 	var data={};
		var URL='MutationIntimation.html?downloadRegDoc';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		window.open(returnData,'_blank' );
}
 
 function downloadMutDoc(bmid){
	 	var data={};
		var URL='MutationIntimation.html?downloadMutDoc';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		window.open(returnData,'_blank' );
}

 
 
function yearLength(){	
		var dateFields = $('.dateClass');
	    dateFields.each(function () {
	    	
	            var fieldValue = $(this).val();
	            if (fieldValue.length > 10) {
	                    $(this).val(fieldValue.substr(0, 10));
	            }
	    })
	}


function saveMutation(element){	

	 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'AdminHome.html', 'saveMutationWithoutEdit');

}

function savePropertyFrom(element){

	var errorList = [];
	errorList = validateMutationIntimation(errorList);	
	if (errorList.length == 0) {
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
		var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationForm.html?redirectToPay', 'saveform');
		if(returnData==false){
			setOwner(returnData);
			}
		}
		else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
			{
			var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationForm.html?PrintReport', 'saveform');
			if(returnData==false){
				setOwner(returnData);
				}
			}
		else{
			var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'AdminHome.html', 'saveform');
			if(returnData==false){
				setOwner(returnData);
			}
		}
	}else {
		showErrorOnPage(errorList);
}	
}

function validateMutationIntimation(errorList){
	var level = 1;
	var marketValue=  $("#marketValue").val();
	var salesDeedValue=  $("#salesDeedValue").val();
	
	 if(marketValue=='' || marketValue == undefined || marketValue<=0){
		  errorList.push(getLocalMessage('property.MarketValueEnt')+ " " +level); 
	 } 	
	 if(salesDeedValue=='' || salesDeedValue == undefined || salesDeedValue<=0){
		  errorList.push(getLocalMessage('property.SalesDeedValueEnt')+ " " +level); 
	 } 	
	$('.jointOwner').each(function(i) {	
		var level = i+1;
		var relationId=  $("#ownerRelation_"+i).val();
		 if(relationId=='0' || relationId == undefined){
			  errorList.push(getLocalMessage('property.sel.optn.relation')+ " " +level); 
		 } 	
		
	});
	return errorList;
}

function showErrorOnPage(errorList){
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


function backToPreviousPage(obj){
	var data={};
	var URL='MutationIntimation.html?backToMainPage';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}

function openUpdateForm(formName,actionParam)
{
	showloader(true);
	setTimeout(function(){codeopenUpdateForm(formName,actionParam)},2);
}

function codeopenUpdateForm(formName,actionParam){
	//common function for grid rewrite here for more functionality
	var theForm	=	'#'+formName;

	var divName	=	".widget-content";

	var url	=	$(theForm).attr('action');

	if (!actionParam) {}
	else
	{
		url+='?'+actionParam;
	}
	var requestData = __serializeForm(theForm);

	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false);

	if(ajaxResponse==""){
		showAlertBoxForPropertyEdit();
		}
	else{
		$(divName).html(ajaxResponse);
	}
}

function showAlertBoxForPropertyEdit(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">This Property is not Editable,Dues are pending</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
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
