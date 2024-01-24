
function searchPropetryForDelete(){
	var errorList = [];
	var propNo=$("#propNo").val();
	var oldPropNo=$("#oldPropNo").val();
	var finId=$("#finId").val();
	if((propNo !=undefined && propNo!="") || (oldPropNo !=undefined && oldPropNo!="")){
		var data = {"propNo" : propNo,"oldPropNo":oldPropNo,"finId":finId};
		var url = 'DataEntrySuiteDeleteBill.html?searchPropetryForDelete';
		var returnData = __doAjaxRequest(url, 'POST', data, false);
		var divName = '.content';
		$(divName).html(returnData);
		 $(formDivName).html(returnData);
	}else{
		errorList
				.push(getLocalMessage("Please enter property number or old property number"));
		displayErrorsOnPage(errorList);
		return false;
	}
}


function deletePropertyBill(element){

	var theForm	=	'#DataEntryOutStandingView';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'DataEntrySuiteDeleteBill.html?deleteBill'; 
	
	saveOrUpdateForm(element,
			"Your application for contract Agreement saved successfully!",
			'DataEntrySuiteDeleteBill.html', 'deleteBill');

}

function backToDeleteSearchPage(){
	 var data={};
		var URL='DataEntrySuiteDeleteBill.html?backToDeleteSearchPage';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
}
