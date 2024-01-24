function getListOfCaseNo() {
	var advId=$('#advId option:selected').attr('value');

    var URL ='EventDate.html?getCaseNoList';
    var requestData = {"advId" : advId };

	
	var ajaxResponse = doAjaxLoading(URL, requestData,'html');
	var prePopulate = JSON.parse(ajaxResponse);
	$.each(prePopulate, function(index, value) {
		$('#caseId').append(
				$("<option></option>").attr("value", value.cseId).text(
						(value.cseSuitNo)));
	});
	$('#caseId').trigger("chosen:updated");

}

$(document).ready(function(){
	getLatestevent(e); 
});
function getLatestevent(e) 
{
	
   var errorList = [];	
	   
    
	if (errorList.length == 0) {
	    var events = [];
	    var URL ='EventDate.html?fetchHearingDetails';
	    requestData={};
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		//var returnData=[];
		var result = [];
	
		
		
		//var prePopulate = returnData;
	}else {
		displayErrorsOnPage(errorList);	
		return false;
	}
	if(returnData.length==0){
		$('#hearingDates tbody').append(
				'<tr><td colspan="6"> No Data Found </td></tr>'
			);
	}else{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index) {
			var i=index+1;
			var obj = prePopulate[index];
			date = obj.date;
			var startDate=date+'T09:00:00';
			var endDate=date+'T14:00:00'
			//alert(date);
			
			var caseno=obj.caseNo;
			var advName=obj.advocateName;
			var courtName=obj.courtName;
			$('#hearingDates tbody').append(
				'<tr><td>'+i+'</td><td>'+caseno+'</td><td>'+advName+'</td><td>'+date+'</td><td>'+courtName+'</td><td>'+obj.description+'</td></tr>'
			);
			i=i+1;
		});
	}
}
