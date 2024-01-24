function getAssessmentType() {

var assessmentType = $("#selectedAssType"+" option:selected").attr("code");
var assessmentTypeDesc = $("#selectedAssType"+" option:selected").text();

if(assessmentType!=undefined){
	var data = {"assessmentType" : assessmentTypeDesc};
	if(($('option:selected', $("#selectedAssType")).attr('code')) == 'C'){
		
		var URL = 'PropertyAssessmentType.html?showChangeInAssessment';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
	}
	if(($('option:selected', $("#selectedAssType")).attr('code')) == 'NC'){
		//var data = {"assessmentType" : assessmentType};
		var URL = 'PropertyAssessmentType.html?showNoChangeInAssessment';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	}
		$(".ShowAssessmentType").html(returnData);	
}
else{
	$(".ShowAssessmentType").html("");
}
}

