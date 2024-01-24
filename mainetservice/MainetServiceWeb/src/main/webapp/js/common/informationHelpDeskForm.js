

function getServiceList() {
	var url = "InformationHelpDesk.html?getServiceList";
	var deptId = $("#deptId").val();

	var requestData = {
		"deptId" : deptId
	}

	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false,
			'json');

	$('#serviceId').html('');

	$('#serviceId').append(
			$("<option></option>").attr("value", "").text("Select"));

	$.each(ajaxResponse, function(index, data) {
		$('#serviceId').append(
				$("<option></option>").attr("value", data.smServiceId)
						.text(data.smServiceName));
	});
	$('#serviceId').trigger('chosen:updated');


}
function getCateoryList(){
	var deptId = $("#deptId").val();
	var serviceId = $("#serviceId").val();

	var requestData = {
			"deptId" : deptId,
			"serviceId" : serviceId
	};
	
	var returnData =__doAjaxRequest('InformationHelpDesk.html?getServiceDetails', 'post',requestData,	false);	
	var url = "InformationHelpDesk.html?getCateoryList";

	if((returnData[0] == 'ML' && returnData[1] == 'NTL') || (returnData[0] == 'WT' && returnData[1] == 'WNC') || (returnData[0] == 'AS' && returnData[1] == 'MUT')){
		if((returnData[0] == 'ML' && returnData[1] == 'NTL') || (returnData[0] == 'WT' && returnData[1] == 'WNC')){
			$("#chekFlag"). css("display", "block");	
			$("#chekFlagProp"). css("display", "none");
			var ajaxResponse =__doAjaxRequest(url, 'post',requestData,	false);	
			$('#categoryId').html('');

			$('#categoryId').append(
					$("<option></option>").attr("value", "").text("Select"));

			$.each(ajaxResponse, function(index, data) {
				$('#categoryId').append(
						$("<option></option>").attr("value", data.lookUpId)
								.text(data.lookUpDesc));
			});
			$('#categoryId').trigger('chosen:updated');
		}else if(returnData[0] == 'AS' && returnData[1] == 'MUT'){
			$("#chekFlagProp"). css("display", "block");
			$("#chekFlag"). css("display", "none");
			var ajaxResponse =__doAjaxRequest(url, 'post',requestData,	false);	
			$('#categoryIdProp').html('');

			$('#categoryIdProp').append(
					$("<option></option>").attr("value", "").text("Select"));

			$.each(ajaxResponse, function(index, data) {
				$('#categoryIdProp').append(
						$("<option></option>").attr("value", data.lookUpId)
								.text(data.lookUpDesc));
			});
			$('#categoryIdProp').trigger('chosen:updated');
		}
		
	 }else{
		$("#chekFlag"). css("display", "none");
		$("#chekFlagProp"). css("display", "none");
	 }
	
}



function getServiceInfo() {
	var errorList = [];
    errorList = validateSearchForm();
	var deptId = $("#deptId").val();
	var serviceId = $("#serviceId").val();
	var deptCode= $("#deptId").find("option:selected").attr('code');
	var categoryIdProp = $("#categoryIdProp").val();
	var categoryId = $("#categoryId").val();
	
	if (deptCode == 'AS'){
	var oldCategoryId=categoryId;
	if(categoryId ="" && (categoryId != categoryIdProp)){
		categoryId=categoryIdProp;
	 }
	}
	var url = "InformationHelpDesk.html?getServiceInfo";
	if (categoryId !="" && categoryId != undefined){
	var requestData = {
			"deptId" : deptId,
			"serviceId" : serviceId,
			"categoryId":categoryId
	};	
   }else if(categoryIdProp !="" && categoryIdProp != undefined){
	  var requestData = {
				"deptId" : deptId,
				"serviceId" : serviceId,
				"categoryId":categoryIdProp
		};	  
  }else{
	  var requestData = {
				"deptId" : deptId,
				"serviceId" : serviceId,
				"categoryId":categoryId
		};	
  }
	
	if (errorList.length == 0) {
		var ajaxResponse =__doAjaxRequest(url, 'post',requestData,	false);	
		 var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		var returnData =__doAjaxRequest('InformationHelpDesk.html?getServiceDetails', 'post',requestData,	false);	
		if (returnData[0] == 'AS' && returnData[1] == 'MUT')
			$("#chekFlagProp"). css("display", "block");
			else
			$("#chekFlagProp"). css("display", "none");
		if ((returnData[0] == 'ML' && returnData[1] == 'NTL') || (returnData[0] == 'WT' && returnData[1] == 'WNC'))
			$("#chekFlag"). css("display", "block");	
		else
			$("#chekFlag"). css("display", "none");		
	} else {
		displayErrorsOnPage(errorList);
	}
}


function validateSearchForm() {
	var errorList = [];
	var deptId = $("#deptId").val();
	var serviceId = $("#serviceId").val();
	var categoryIdProp = $("#categoryIdProp").val();
	var categoryId = $("#categoryId").val();
	var requestData = {
			"deptId" : deptId,
			"serviceId" : serviceId
	};
	var returnData = __doAjaxRequest('InformationHelpDesk.html?getServiceDetails', 'post', requestData,
			false);
	if (returnData != null || returnData != "") {
		if ((returnData[0] == 'ML' && returnData[1] == 'NTL')
				|| (returnData[0] == 'WT' && returnData[1] == 'WNC')) {
			if (categoryId == "" || categoryId == undefined)
				errorList.push(getLocalMessage('info.help.desk.validate.category'));
		} else if (returnData[0] == 'AS' && returnData[1] == 'MUT') {
			if (categoryIdProp == "" || categoryIdProp == undefined)
				errorList.push(getLocalMessage('info.help.desk.validate.transferType'));
		}
	}
	if (deptId == "" && serviceId == "") {
		errorList.push(getLocalMessage('info.help.desk.search_data'));
	}
	return errorList;
}

function resetSearchForm(){
	window.location.href = 'InformationHelpDesk.html';	
}

function back() {
	window.location.href = getLocalMessage("AdminHome.html");
}
