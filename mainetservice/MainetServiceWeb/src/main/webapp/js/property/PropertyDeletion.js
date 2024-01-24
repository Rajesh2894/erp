
function deletePropertyBy(element){

	//alert("in delete");
	
	
/*var propNo=$("#proertyNo").val();
	//if(propNo !=undefined && propNo!=""){

		var data = {"propNo" : propNo};
		var url ='PropertyDeletion.html?searchProperty';
		var returnData = __doAjaxRequest(url, 'POST', data, false);
		var divName = '.content';
		$(divName).html(returnData);
	//}
*/		
		return saveOrUpdateForm(element,
				"Demand Notice generation  done successfully",
				'PropertyDeletion.html','searchProperty');	
		
		
		
		
}