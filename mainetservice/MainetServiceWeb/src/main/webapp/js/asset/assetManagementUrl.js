function assetUrl(formUrl,actionParam){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}

function setDepartmentCode(){
	var selectedType = $("#dpDeptId").find("option:selected").attr('code');
	$("#departmentCode").val(selectedType);
}

function adminPage(url){
	
	
	var divName = '.content-page';
	var ajaxResponse = __doAjaxRequest(url, 'POST', {}, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}