function showKyeValueList(obj){
	
	if($("#selectedPropertiesFile option:selected").val() > 0)
	{
		var fileName=$("#selectedPropertiesFile option:selected").text();
		
		var data="propertyFileName="+fileName+"&value="+$("#selectedPropertiesFile option:selected").val();
		var ajaxResponse = __doAjaxRequest('LableUpdateSearch.html?displayEachProperty',
				'POST',data, false,'json');

			showProperties(ajaxResponse);
	}
};

var showProperties = function(responseObj){
	//reloadGrid("gridLableUpdateSearch");
	$('#gridLableUpdateSearch').setGridParam({datatype:'json'}).trigger("reloadGrid",[{page:1}]);
};


$(document).ready(function() {
	
	if($('#selectedPropertiesFile').val() > 0)
	{
		showKyeValueList($('#selectedPropertiesFile'));
	}
});

function _openChildForm(k,f)
{

	

	var childDivName	=	'.child-popup-dialog';
	


	
	var data = { 'key': k ,'file': f};
	



	var ajaxResponse = __doAjaxRequest('LableUpdateSearch.html?getPopUp',
			'POST',data, false);
	
	_showChildForm(childDivName, ajaxResponse);
}

function _showChildForm(divName, ajaxResponse) {
	
	$(divName).html(ajaxResponse);
	showModalBox(divName);
}