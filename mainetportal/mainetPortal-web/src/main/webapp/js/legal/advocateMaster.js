$(document).ready(function() {
	$("#reset").on("click", function() {
		window.location.reload("#AdvocateMasterForm")
	});
	
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
	  $('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange: '-70:+10',
			maxDate : '0',
		    });
	 
	
	$("#advChamberNo").attr('maxlength', 10);
	$("#advUid").attr('maxlength', 12);

	
	jQuery('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','14');
	    $(this).attr('minlength','12');
	});
});


function Proceed(element) {
	
	var errorList = [];
	errorList = ValidateAdvocateMasterForm(errorList);
	$('.educationDetailsClass').each(function(i) {
		errorList = validateDetails(errorList,i);
     });
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	else
	{
	return saveOrUpdateForm(element,
			getLocalMessage('lgl.saveAdvocateMaster.'),
			'CitizenHome.html',
			'saveform');
	}
	
}


function addEntryData() {

	var errorList = [];

	 $('.educationDetailsClass').each(function(i) {
			errorList = validateDetails(errorList,i);
	     });
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow('educationDetails', false);
		reOrderIdSequence('.educationDetailsClass');
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	}
	return false;
}

function reOrderIdSequence(educationDetailsClass){

	$(educationDetailsClass).each(function(i) {
		//ID
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
	
		$(this).find("input:text:eq(1)").attr("id", "qualificationCourse" + i);
		$(this).find("input:text:eq(2)").attr("id", "instituteState" + i);
		$(this).find("input:text:eq(3)").attr("id", "boardUniversity" + i);
		$(this).find("input:text:eq(4)").attr("id", "result" + i);
		$(this).find("input:text:eq(5)").attr("id", "passingYear" + i);
		$(this).find("input:text:eq(6)").attr("id", "percentage" + i);
		
			//NAME
		$(this).find("input:text:eq(0)").attr("name", i);
		$(this).find("input:text:eq(1)").attr("name", "advocateMasterDTO.advEducationDetailDTOList[" + i + "].qualificationCourse");
		$(this).find("input:text:eq(2)").attr("name", "advocateMasterDTO.advEducationDetailDTOList[" + i + "].instituteState");
		$(this).find("input:text:eq(3)").attr("name", "advocateMasterDTO.advEducationDetailDTOList[" + i + "].boardUniversity");
		$(this).find("input:text:eq(4)").attr("name", "advocateMasterDTO.advEducationDetailDTOList[" + i + "].result");
		$(this).find("input:text:eq(5)").attr("name", "advocateMasterDTO.advEducationDetailDTOList[" + i + "].passingYear");
		$(this).find("input:text:eq(6)").attr("name", "advocateMasterDTO.advEducationDetailDTOList[" + i + "].percentage");
		
	});
}

function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('educationDetails', obj, ids);
	$('#educationDetails').DataTable().destroy();
	triggerTable();
}




function ValidateAdvocateMasterForm(errorList) {
	var advLastNm = $("#advLastNm").val();
	var advFirstNm = $("#advFirstNm").val();
	//var advGen = $("#advGen").val();
	var advDob = $("#advDob").val();
	var advMobile = $("#advMobile").val();
	var advAddress = $("#advAddress").val();
	var advPanno = $("#advPanno").val();
	var aadharNumber = $("#advUid").val();

	var advEmail  = $("#advEmail").val();
	var advExperience = $("#advExperience").val();

	var advMiddleNm = $("#advLastNm").val();
	var advOfficeAddress = $("#advOfficeAddress").val();
	var advChamberNo = $("#advChamberNo").val();
	var barCouncilNo = $("#adv_barCouncilNo").val();

	
	if (advLastNm == "" || advLastNm == null)
		errorList.push( getLocalMessage("lgl.validation.advLastNm"));
	if (advFirstNm == "" || advFirstNm == null)
		errorList.push( getLocalMessage("lgl.validation.advFirstNm"));
	/*if (advGen == "0" || advGen == null)
		errorList.push( getLocalMessage("lgl.validation.advGen"));   
	if (advDob == "" || advDob == null)
		errorList.push( getLocalMessage("lgl.validation.advDob"));)*/
	if (advMobile == "" || advMobile == null)
		errorList.push( getLocalMessage("lgl.validation.advMobile"));
	if (advAddress == "" || advAddress == null)
		errorList.push( getLocalMessage("lgl.validation.advAddress"));
/*	if (advOfficeAddress == "" || advOfficeAddress == null)
	{
	errorList.push( getLocalMessage("lgl.validation.advOfficeAddress"));
	}*/
	if(barCouncilNo == "" || barCouncilNo == null )
		errorList.push( getLocalMessage("lgl.validation.barCouncilNo"));
	
/*	if(aadharNumber == "" || aadharNumber == null )
		errorList.push( getLocalMessage("lgl.validation.aadharNumber"));
	if(advPanno == ""  || advPanno == null)
		errorList.push( getLocalMessage("lgl.validation.advPanno"));*/
	/*
	if(advExperience == "" || advExperience == null)
		errorList.push(getLocalMessage("lgl.validation.Experience"));*/
	//#146347
	if(advEmail == ""  || advEmail == null)
		errorList.push(getLocalMessage("lgl.validation.advEmail"));
	var emailId = $.trim($("#advEmail").val());
	if (emailId !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(emailId);
	   if (!valid) {
		   errorList.push(getLocalMessage("Invalid.Email"));
	   } 
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


function backPage() {
	window.location.href = getLocalMessage("CitizenHome.html");
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
	 			}
	 		else
	 		{
	 			errorList.push('Invalid Date Format');
	 		}	
	 }
	 displayErrorsOnPage(errorList);
	 if(errorList.length > 0)
	{ 
		return errorList;
	}
	 
}


function reset() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdvocateMaster.html');
	$("#postMethodForm").submit();
}

function validateDetails(errorList,i){
	var passingYear = $('#passingYear'+i).val();
	var percentage = $('#percentage'+i).val();

	var currYr=new Date().getFullYear();
	
	if (passingYear != "" && passingYear > currYr) {
		errorList.push(getLocalMessage("lgl.adv.validate.passingYear") + (i+1));
	}
	
	if (percentage != "" && percentage > 100){
		errorList.push(getLocalMessage("lgl.adv.validate.percentage") + (i+1));
	}
	
	return errorList;
}