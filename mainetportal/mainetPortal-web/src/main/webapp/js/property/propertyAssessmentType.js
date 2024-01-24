function getAssessmentType() {

var assessmentType = $("#selectedAssType"+" option:selected").attr("code");
//var assessmentTypeDesc = $("#selectedAssType"+" option:selected").text();

if(assessmentType!=undefined){
	
	var data = {"assessmentType" : assessmentType};
	if(($('option:selected', $("#selectedAssType")).attr('code')) == 'C' || ($('option:selected', $("#selectedAssType")).attr('code')) == 'NC'){		
		var URL = 'PropertyAssessmentType.html?showChangeInAssessment';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
	}
		$(formDivName).html(returnData);
}
else{
	
	$(formDivName).html("");
}
}

