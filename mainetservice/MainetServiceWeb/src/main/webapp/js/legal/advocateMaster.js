
$(document).ready(function() {
	$('#AdvocateMasterForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
        //#120781 - year range added
	  $('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange: '-70:+10',
			
		    });
	  
	var envFlag = $("#envFlag").val();
	if (envFlag == 'Y') {
		$('.alpaSpecial').keypress(function(e) {
			var regex = new RegExp("^[A-Za-z0-9!@#& ]+$");
			var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
			if (regex.test(str)) {
				return true;
			}
			e.preventDefault();
			return false;
		});
	}
	
	$("#id_advocateMasterTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	
	$("#advChamberNo").attr('maxlength', 10);
	$("#advUid").attr('maxlength', 12);

});
function openAddAdvocateMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getAdvocateMaster(formUrl, actionParam, id) {
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var data= {
		"id":id	
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/*function Proceed(element) {
	var errorList = [];
	
	errorList = ValidateAdvocateMasterForm(errorList);
	
	if (errorList.length > 0) {
		//$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('lgl.saveAdvocateMaster'), 'AdvocateMaster.html',
				'saveform');
	}
}*/
function Proceed(element) {
	
	var errorList = [];
	errorList = ValidateAdvocateMasterForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	else
	{
	
	return saveOrUpdateForm(element,
			getLocalMessage('lgl.saveAdvocateMaster.'),
			'AdvocateMaster.html',
			'saveform');
	}
	
}




function modifyAdvocate(advId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : advId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};
/*.replace(/^[ ]+|[ ]+$/g,'')
*/
function ValidateAdvocateMasterForm(errorList) {
	
	
	var advLastNm = $("#advLastNm").val();
	var advFirstNm = $("#advFirstNm").val();
	//var advGen = $("#advGen").val();
	var advDob = $("#advDob").val();
	var advMobile = $("#advMobile").val();
	var advAddress = $("#advAddress").val();
	var advPanno = $("#advPanno").val();
	var aadharNumber = $("#advUid").val();
	var advAppfromdate = $("#advAppfromdate").val();
	var advApptodate = $("#advApptodate").val();
	var advEmail  = $("#advEmail").val();
	var advExperience = $("#advExperience").val();

	var advMiddleNm = $("#advLastNm").val();
	var advOfficeAddress = $("#advOfficeAddress").val();
	var advChamberNo = $("#advChamberNo").val();
	var advFeeType = $("#advFeeType").val();
	var advFeeAmt = $("#advFeeAmt").val();
	var adv_advocateTypeId = $("#adv_advocateTypeId").val();
	
	if (advLastNm == "" || advLastNm == null)
		errorList.push( getLocalMessage("lgl.validation.advLastNm"));
	if (adv_advocateTypeId == "0" || adv_advocateTypeId == null || adv_advocateTypeId == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.advtype"));
	if (advFirstNm == "" || advFirstNm == null)
		errorList.push( getLocalMessage("lgl.validation.advFirstNm"));
	/*if (advGen == "0" || advGen == null)
		errorList.push( getLocalMessage("lgl.validation.advGen"));   
	if (advDob == "" || advDob == null)
		errorList.push( getLocalMessage("lgl.validation.advDob"));)*/
	if (advMobile == "" || advMobile == null){
		errorList.push( getLocalMessage("lgl.validation.advMobile"));
	} else if( advMobile != ""){
		if(advMobile.length!=10){
			errorList
			.push(getLocalMessage("ContractualStaffMasterDTO.Validation.validMobileNo"));
		}
		else {
			var mobileRegex = /^[789]\d{9}$/;
			
			if (!mobileRegex.test(advMobile)) {
				errorList.push(getLocalMessage("lgl.validate.invalid.implementerPhoneNo"));
			}
		}
	}	
	
	/*if (advAddress == "" || advAddress == null)
		errorList.push( getLocalMessage("lgl.validation.advAddress"))*/;
	if (advAppfromdate == "" || advAppfromdate == null)
		{
		errorList.push( getLocalMessage("lgl.validation.advAppfromdate"));
		}
	if (advOfficeAddress == "" || advOfficeAddress == null)
	{/*lgl.validation.advOfficeAddress*/
	errorList.push( getLocalMessage("lgl.validation.advOfficeAddress"));
	}
	 if(advAppfromdate!= null || advAppfromdate!= "")
	{
		var dateErrList = validateDateFormat($("#advAppfromdate").val());
		if(dateErrList!=undefined)
		{errorList.push(getLocalMessage('Appointment Start Date : '+dateErrList));}
	}
	 if(advApptodate!= null || advApptodate!= "" || errorList=="" || errorList == null)
	 	{
		 	var dateErrList = validateDateFormat($("#advApptodate").val());
		 	if(dateErrList!=undefined)
		 	{
		 	errorList.push(getLocalMessage('Appointment End Date : '+dateErrList));
		 	}
	 	}
	
	
/*	if(aadharNumber == "" || aadharNumber == null )
		errorList.push( getLocalMessage("lgl.validation.aadharNumber"));
	if(advPanno == ""  || advPanno == null)
		errorList.push( getLocalMessage("lgl.validation.advPanno"));*/
	if(advEmail == ""  || advEmail == null)
		errorList.push(getLocalMessage("lgl.validation.advEmail"));
	/*if(advExperience == "" || advExperience == null)
		errorList.push(getLocalMessage("lgl.validation.Experience"));*/
	if(compareDate(advAppfromdate) > compareDate(advApptodate)){
		errorList.push( getLocalMessage("lgl.validation.dates"));
	}	
	
	
	
	var emailId = $.trim($("#advEmail").val());
	if (emailId !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(emailId);
	   if (!valid) {
		   errorList.push(getLocalMessage("Invalid.Email"));
	   } 
	}
	
	 if (advFeeType == "" || advFeeType == null || advFeeType == "select")
		{
		errorList.push( getLocalMessage("legal.enter.feeType"));
		}
	 
	  var empDOB =$("#advDob").val();
	 var today = new Date();
	   var curr_date = today.getDate();
	   var curr_month = today.getMonth() + 1;
	   var curr_year = today.getFullYear();

	   var pieces = empDOB.split('/');
	   var birth_date = pieces[0];
	   var birth_month = pieces[1];
	   var birth_year = pieces[2];

	   if(curr_date < birth_date)
		   curr_month=curr_month-1;
		 if(curr_month < birth_month)
			 curr_year=curr_year-1;
		 curr_year=curr_year-birth_year;
			 if( curr_year <= 0)
				 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
			 else if(curr_year<18)
				 errorList.push(getLocalMessage("citizen.login.reg.dob.error3")); 
	
	return errorList;
}

function compareDate(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function fnValidatePAN(Obj) {
	$('.error-div').hide();
	var errorList = [];
	if (Obj == null)
		Obj = $('#advPanno').val();
	if (Obj.value != "") {
		ObjVal = Obj.value;
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = ObjVal.substring(3, 4);
		if (ObjVal.search(panPat) == -1) {
			errorList.push( 'Invaild PAN Number');
			/*$('#advPanno').val("");*/
		} else if (code.test(code_chk) == false) {
			errorList.push( 'Invaild PAN Number');
			/*$('#advPanno').val("");*/
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
};

function backAdvocateMasterForm(){
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdvocateMaster.html');
	$("#postMethodForm").submit();
}

function getAdvocateMasterView(formUrl, actionParam, id) {
	if (!actionParam) {
		actionParam = "view";
	}
	var data = {
		"id":id	
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function resetForm() {
	/*
	$("#errorDiv").hide();
	$('input[type=text]').val('');  
    $('#Address').val(''); 
    $('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();*/
	
	openAddAdvocateMaster('AdvocateMaster.html', 'AddAdvocateMaster');
}



function validateDateFormat(dateElementId)
{

	 var errorList = [];
	 var dateValue= dateElementId;
	 
	 if(dateValue != null && dateValue != "")
	 {
	 		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 		if(dateValue.match(dateformat))
	 			{
	 				var opera1 = dateValue.split('/');
	 				//var temp  = sliDate.split(" ");
	 				//var temp1 = temp[0];
	 				//var sli;   //Splitting SliDate
	
	 				lopera1 = opera1.length;
	 				//lsli = sli.length;
	 				if (lopera1>1)
	 				{
	 					var pdate = dateValue.split('/');
	 				}
	 				var dd = parseInt(pdate[0]);
	 				var mm  = parseInt(pdate[1]);
	 				var yy = parseInt(pdate[2]);
	 	
	 				//var slimonth = parseInt(sli[1]);
	 			//	var sliyear  = parseInt(sli[2]);
	 				var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	 				if (mm==1 || mm>2)
	 				{
	 					if (dd>ListofDays[mm-1])
	 					{
	 						errorList.push('Invalid Date Format');
	 					}
	 				}
	 				if (mm==2)
	 				{
	 					var lyear = false;
	 						if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
	 						{
	 							lyear = true;
	 						}
	 					if ((lyear==false) && (dd>=29))
	 					{
	 						errorList.push('Invalid Date Format');
	 					}
	 					if ((lyear==true) && (dd>29))
	 					{
	 						errorList.push('Invalid Date Format');
	 					}
	 				}
	 				var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	 				var sDate = new Date(dateValue.replace(pattern,'$3-$2-$1'));
	 				/*if (sDate > new Date())
	 				{
	 				}*/
	 				/*if(yy == sliyear)
	 				{
	 					if(mm < slimonth && mm != slimonth)
	 					{
	 						errorList.push("Invalid value for month: " + mm + " Transaction Date must be between " +temp1+" and " + (new Date()).getFullYear());
	 					}
	 				}*/
	 				/*if(yy <sliyear  || yy > (new Date()).getFullYear())
	 				{
	 					errorList.push("Invalid value for year: " + yy + " Transaction Date must be between " +temp1+" and " + (new Date()).getFullYear());
	 				}*/
	 			}
	 		else
	 		{
	 			errorList.push('Invalid Date Format');
	 		}	
	 }
	 displayErrorsOnPage(errorList);
	 if(errorList.length > 0)
	{ /*{ correct = false;}
	 else{correct = true;}*/
		return errorList;
	}
	 
}

//120649
function resetAdvocateMaster(resetBtn) {
	var advId = $('#advId').val();
	getAdvocateMaster("AdvocateMaster.html", "EditAdvocateMaster",advId);
}

//127193
function searchData(){

	var errorList = [];
	var advId = $("#adv_advocateTypeId").val();
	var crtId = $("#crtId").val();
	var barCouncilNo = $("#adv_barCouncilNo").val();
	var advStatus = "";
	if ((advId == "0" || advId== undefined ) && (crtId == "" || crtId == undefined ) && (barCouncilNo == "" || barCouncilNo == undefined)){
		errorList.push(getLocalMessage("lgl.select.criteria"))
		displayErrorsOnPage(errorList);
		return false;
	}
		var data = {
			"advId"  : replaceZero($("#adv_advocateTypeId").val()),
			"advStatus" : advStatus,
			"crtId" :  replaceZero($("#crtId").val()),
			"barCouncilNo" :$("#adv_barCouncilNo").val(),
		};
		var divName = '.content-page';
		var formUrl = "AdvocateMaster.html?searchData";
		var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function reset() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdvocateMaster.html');
	$("#postMethodForm").submit();
}

function saveAdvocateDecisionForm(element) {
	var errorList = [];
	errorList = ValidateDecisionForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
 
		return saveOrUpdateForm(element, "Saved Successfully",
				'AdminHome.html', 'saveDecision');
	}
}

function ValidateDecisionForm(errorList) {
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('lgl.validate.decision'));
	else if(comments == undefined || comments =="")
		errorList.push(getLocalMessage('lgl.validate.remark'));
	return errorList;
}