
function searchRecords(ele){
	var errorList = [];
	var csCcn = $('#csCcn').val();
	var finId=$("#finId").val();
	var billNo = $('#billNo').val();
	if(finId==null || finId==""){
		errorList.push(getLocalMessage("water.arrear.select.financial.year"));
	}
     var requestData = {"csCcn":csCcn,"finId":finId,"billNo":billNo}
     errorList = ValidateInfo(errorList);	
     if (errorList.length == 0) {
     var URL = "ArrearEntryDeletion.html?search"
     var returnData =__doAjaxRequest(URL, 'POST', requestData, false); 
     $(formDivName).html(returnData);
     }
     else {
    	displayErrorsOnPage(errorList);
 		return false;
 	}
   
}
function ValidateInfo(errorList){
	var csCcn = $('#csCcn').val();
	
	if (csCcn == "" || csCcn == 0 || csCcn === undefined) {
		errorList.push(getLocalMessage("water.arrear.validconnNo"));
	}
	return errorList;
}

function backActionForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ArrearEntryDeletion.html');
	$("#postMethodForm").submit();
}


function deleteArrears(element){
	var theForm	=	'#ArrearEntryDeletion';
	var requestData = $('#csIdns').val();
	
	saveOrUpdateForm(element,
				"Arrears Deleted Successfully!",
				'AdminHome.html', 'saveform');
	
}