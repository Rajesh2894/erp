
function searchPropetryForDelete(){
	
	var propNo=$("#propNo").val();
	if(propNo !=undefined && propNo!=""){

		var data = {"propNo" : propNo};
		var url = 'PropertyBillDeletion.html?searchPropetryForDelete';
		var returnData = __doAjaxRequest(url, 'POST', data, false);
		var divName = '.content';
		$(divName).html(returnData);
		

/*		if(returnData)
		{
			var divName = '.content';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
		}*/
	}
}


function PropertyBillDeletion(element)
{
	
	return saveOrUpdateForm(element,
			"Your application for contract Agreement saved successfully!",
			'PropertyBillDeletion.html','deleteBill');

}

function backToDeleteSearchPage(){
	 var data={};
		var URL='PropertyBillDeletion.html?backToDeleteSearchPage';
		var returnData=__doAjaxRequest(URL, 'POST', data, false);
		$(formDivName).html(returnData);
}
