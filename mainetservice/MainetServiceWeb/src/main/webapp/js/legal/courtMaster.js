$(document).ready(function() {
	/*
	 * $(function() { $('.datetimepicker3').timepicker(); });
	 */
	
	$(function() {
		$('.datetimepicker3').timepicker();
	});
	
	/*
	 * $(".datepicker").datepicker({ dateFormat : 'dd/mm/yy', changeMonth :
	 * true, changeYear : true, maxDate : '0' });
	 */

	$("#id_courtmasterform").validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	var envFlag = $("#envFlag").val();
	if (envFlag == 'Y'){
	$('.alphaChar').keypress(function (e) {
	    var regex = new RegExp("^[a-zA-Z'-. ]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }

	    e.preventDefault();
	    return false;
	});

	$('#crtWebsite').keypress(function (e) {
	    var regex = new RegExp("^[a-zA /-:.-0-9]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    e.preventDefault();
	    return false;
	});
	}
	
	$("#id_courtMasterTbl").dataTable(
			{
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
						[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
	
});



function openAddCourtMaster(formUrl,actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode":mode,
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function modifyCourt(court_id,formUrl,actionParam, mode){
	
	var divName = '.content-page';
	var requestData = {
			"mode":mode,
			"id":court_id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}

function confirmToProceed(element) {
	
	var errorList = [];
	errorList = ValidateCourtMaster(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);	
	} else {
		return saveOrUpdateForm(element,getLocalMessage('lgl.saveCourtMaster'), 'CourtMaster.html', 'saveform');
	}
	
}

function ValidateCourtMaster(errorList) {
	var crtType = $("#crtType").val();
	var crtName = $("#crtName").val();
	var crtStartTime = $("#crtStartTime").val();
	var crtEndTime = $("#crtEndTime").val();
	// User Story #18533 changes as per SUDA module commitee review
	/* var crtPhoneNo = $("#crtPhoneNo").val(); */
	/* var crtEmailId = $("#crtEmailId").val(); */
	var crtAddress = $("#crtAddress").val();
	var webAddress= $("#crtWebsite").val();
	
	var crtState = $("#crtState").val();
	var crtCity = $("#crtCity").val();
	
	var envFlag = $("#envFlag").val();
	
	if(crtType=="0"||crtType==null)
		errorList.push(getLocalMessage("lgl.validation.crttype"));
	if(crtName==""||crtName==null)
		errorList.push(getLocalMessage("lgl.validation.crtname"));
	if(crtStartTime==""||crtStartTime==null)
		errorList.push(getLocalMessage("lgl.validation.crtstarttime"));
	if(crtEndTime==""||crtEndTime==null)
		errorList.push(getLocalMessage("lgl.validation.crtendtime"));
/*
 * if(crtPhoneNo==""||crtPhoneNo==null)
 * errorList.push(getLocalMessage("lgl.validation.crtphoneno"));
 */
	if(crtAddress==""||crtAddress==null)
		errorList.push(getLocalMessage("lgl.validation.crtaddress"));
	
	
	
	if (crtState == "0" || crtState == null || crtState == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseState"));

	if (crtCity == "" || crtCity == null || crtCity == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseCity"));
	
/*
 * var emailId = $.trim($("#crtEmailId").val()); if (emailId !="") { var
 * emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i); var
 * valid = emailRegex.test(emailId); if (!valid) {
 * errorList.push(getLocalMessage("Invalid.Email")); } }
 */
	if (envFlag == 'N'){
	 var webAddress = $.trim($("#crtWebsite").val());
		if (webAddress !="")		
		{
		 //120799
		  var valid =/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g.test(webAddress);
			 
		   if (!valid) {
			   errorList.push(getLocalMessage("Invalid.WebAdd"));
		   } 
		}
	}
		return errorList;
		
	
}

function resetForm(){
	openAddCourtMaster('CourtMaster.html', 'AddCourtMaster', 'C');
}

//120649
function resetCourtMaster(resetBtn) {
	var id = $('#id').val();
	modifyCourt(id,'CourtMaster.html', 'EditCourtMaster','E');
}

//127193
function searchCourtData(){

	var errorList = [];
	
	var crtId = $("#crtId").val();
	var crtType = $("#crtType").val();
	if ((crtType == "0" || crtType == undefined ) && (crtId == "" || crtId == undefined || crtId == "0" )){
		errorList.push(getLocalMessage("lgl.select.criteria"))
		displayErrorsOnPage(errorList);
		return false;
	}
		var data = {		
			"crtId" :  replaceZero($("#crtId").val()),
			"crtType"  : replaceZero($("#crtType").val()),
		};
		var divName = '.content-page';
		var formUrl = "CourtMaster.html?searchCourtData";
		var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function reset(){
	$('#crtId').val('0').trigger('chosen:updated');
	$('#crtType').val('0').trigger('chosen:updated'); 	
}