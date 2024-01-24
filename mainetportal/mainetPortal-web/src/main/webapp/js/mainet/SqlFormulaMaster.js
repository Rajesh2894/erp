function searchForm(formObj) {
	var errorList = [];
var abc=$("#dpDeptid").val();
	if(abc=="0")	
		{
		errorList.push(getLocalMessage('adv.sqlCodeFormula.dept.must.selected.errorMsg'));
		}

if (errorList.length == 0) {
	

	var URL = 'SqlFormulaMaster.html?getSearchData';
	var data = {
		"sfCode" : $("#sfCode1").val(),
		
/*		"dpDeptid" : $("#dpDeptid").val(),
*/		
	};

	
	__doAjaxRequest(URL, 'POST', data, false, 'json');

	reloadGrid('gridSqlFormulaMaster', 'json');
	}


	
	else{
		showHoardingError(errorList);
	}
}




function showHoardingError(errorList) {
	var errMsg = '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
	$('.error-div').show();
}




function openViewModeForm(formName,actionParam) {
	
	var theForm	=	'#'+formName;
	
	var divName	=	formDivName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam) {
		//Do nothing if no action param is set.
	} else {
		url+='?'+actionParam;
	}
	
	var requestData = __serializeForm(theForm);
	
	var ajaxResponse	=	doAjaxLoading(url, requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	prepareTags();
	
	$(divName).show();

}



$("#resettButon").click(function() { 

$('.error-div').remove();
	
	
	resetForm(this);
	
});	

$( document ).ready(function() {
	
	$("#sfCode1").val("");

	
	if($('#mode').val() == 'R') {
		
		$("#sfCode").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#sfSqlstmt").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#sfRemarks").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#dpDeptid").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");

	  }

	if($('#mode').val() == 'E') {
		
		$("#sfCode").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#dpDeptid").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");

	  }

	reloadGrid('gridSqlFormulaMaster', 'json');
});

function promptErrorMessage(warnMsg) {
	
	var message='';
	var ammMediaCode='';
	var msg='<div class="sucess subsize1"><h3>'+warnMsg+'</h3></div>';
	message	+='<p>'+ msg+'</p>';
	
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
	
	return false;
}


function checkDuplicate(){
	var requestData = {
			'sfCode' : $("#sfCode").val(),
		'dpDeptid':$("#dpDeptid").val(),
		};
		var url = "SqlFormulaMaster.html?checkDuplicate";
		var returnData1 = __doAjaxRequest(url, 'post', requestData, false, 'text');
		if(returnData1 !=1){
			promptErrorMessage(getLocalMessage('adv.sqlCodeFormula.already.exist.errorMsg'));
			
			$("#sfCode").val("");
			$("#dpDeptid").val("");
			
		}
}



function clearErrorDiv() {
	$('.error-div').hide();
	
	$("#SqlFormulaMaster")[0].reset();
	
	$(getElemId("dpDeptid")).val("");
	$(getElemId("sfCode")).val("");
	$("#sfCode1").val("");
	$("#gridSqlFormulaMaster").jqGrid("clearGridData");

	
}

