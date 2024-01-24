$(document).ready(function() {
	
	$("#memberDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$('#couDOB').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: '-18y',
		yearRange: "-200:+200",
		//maxDate : new Date(end.getFullYear()-18, 11, 31)
	});
	
	$("#couDOB").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#couElecDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#couElecDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "1900:2200"
	});
	
	$("#couOathDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$('#couOathDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "1900:2200"
	});
	
	/* This method is used to Search All Council Member */
	$('#searchCouncilMember').click(function() {
		var errorList = [];
		/*var designation = $('#design').val();*/
		var couMemberType = $ ('#couMemberType').val();
		var couMemName = $('#couMemName').val();
		var couPartyAffilation = $('#couPartyAffilation').val();
		var couEleWZId1 = $('#couEleWZId1').val();
		var couEleWZId2 = $('#couEleWZId2').val();
		if((couEleWZId2 === undefined) && (couEleWZId1 === undefined)){
			couEleWZId2 = 0;
			couEleWZId1 = 0;
		}
		else if(couEleWZId2 === undefined){
			couEleWZId2 = 0;
		}
		else if(couEleWZId1 === undefined){
			couEleWZId1 = 0;
		}
		if ( couMemberType != 0 || couMemName != '' || couPartyAffilation != 0 || couEleWZId1 != 0 || couEleWZId2 != 0 ){
			var requestData = '&couMemberType=' + couMemberType + '&couMemName=' + couMemName + '&couPartyAffilation=' + couPartyAffilation 
			+ '&couEleWZId1=' + couEleWZId1 + '&couEleWZId2=' + couEleWZId2;
			var table = $ ('#memberDatatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest('CouncilMemberMaster.html?SearchCouncilMember','POST', requestData, false,'json');
			
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("council.member.validation.grid.nodatafound"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
			$.each(ajaxResponse.councilMemberDto,function(index) {
				var obj = ajaxResponse.councilMemberDto[index];	
				/*var designation = findDesignation(obj.couDesgId,ajaxResponse.designation);*/							
					result.push([index + 1,obj.couEleWZ1Desc,obj.couMemberTypeDesc,obj.couPartyAffDesc,obj.couMemName,
						'<td >'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="showGridOption(\''
						+ obj.couId
						+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
						+ '<button type="button" class="btn btn-danger btn-sm margin-right-10 "  onclick="showGridOption(\''
						+ obj.couId
						+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
				     	+ '</td>' ]);				
			});
			table.rows.add(result);
			table.draw();
		}
		else{
			errorList.push(getLocalMessage('council.member.validation.select.any.field'));
			displayErrorsOnPage(errorList);
		}
	});
	
	if ($('#saveMode').val() == 'V'){
		$('#MemberMaster input').attr('readonly', 'readonly');
		
	}
});
var correct = true;
//As per RFP Changes done
// function used to get designation name by designation Id
/*function findDesignation(designationid, designations){
	var designation = designationid;
	$.each(designations,function(index) {
		var obj = designations[index];
		if(obj.dsgid == designationid){
			designation = obj.dsgname;
		}
	});
	return designation;
}*/

// ajax call for add member master
function addMemberMaster(formUrl, actionParam) {
	
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

//email id validation
function validateEmail(emailId,submitBT) {
	var regexPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    let errorList = [];
    emailId = $('#couEmail').val();
    if(emailId != ''){
    	if(!regexPattern.test(emailId)){
    		if(!submitBT){
    			errorList.push(getLocalMessage('council.validation.emailId'));
            	displayErrorsOnPage(errorList);	
    		}
        	return false;
        }else{
        	$(".warning-div").hide();
        	return true;
        }	
    }else{
    	$(".warning-div").hide();
    	return true;
    }
}

//mobile no validation
function validateMobile(mobile) {
	var regexPattern = /^[6-9]\d{9}$/;
	return regexPattern.test(mobile);
}

// function used for save and validate council member
function saveform(obj) {
	var errorList = [];
	var designation = $('#designation').val();
	var couEduId = $('#couEduId').val();
	var couCastId = $('#couCastId').val();
	var couMemName = $('#couMemName').val();
	var couGen = $('#couGen').val();
	var couMobNo = $('#couMobNo').val();
	var couDOB = $('#couDOB').val();
	var couEmail = $('#couEmail').val();
	var couAddress = $('#couAddress').val();
	var couElecDate = $('#couElecDate').val();
	var couOathDate = $('#couOathDate').val();
	var couPartyAffilation = $('#couPartyAffilation').val();
	var couEleWZId1 = $('#couEleWZId1').val();
	var couEleWZId2 = $('#couEleWZId2').val();
	var couMemberType = $('#couMemberType').val();
	/*if (designation == '') {
		errorList
				.push(getLocalMessage('council.member.validation.designation'));
	}*/
	
	
	if (couEduId == '' || couEduId == 0) {
		errorList.push(getLocalMessage('council.member.validation.education'));
	}
	if (couCastId == '' || couCastId == 0) {
		errorList.push(getLocalMessage('council.member.validation.caste'));
	}
	if (couMemName == '') {
		errorList.push(getLocalMessage('council.member.validation.name'));
	}
	if (couGen == '' || couGen == 0) {
		errorList.push(getLocalMessage('council.member.validation.gender'));
	}
	if (couMobNo == '') {
		errorList.push(getLocalMessage('council.member.validation.mobno'));
	}else if (!validateMobile(couMobNo)) {
		errorList.push(getLocalMessage('council.validation.mobileNo'));
	}
	
	if (couDOB == '') {
		errorList.push(getLocalMessage('council.member.validation.dob'));
	}
	else
	{
		var dateErrList = validateDateFormat($("#couDOB").val());
		if(correct == false)
			{
			errorList.push(getLocalMessage('Date Of Birth : '+ dateErrList));
			}
		else
			{
				if(compareDateOfBirth(couDOB) == false)
					errorList.push(getLocalMessage('council.validation.dateofbirth'));
			}
	}
	
	
	/*if (couEmail == '') {
		errorList.push(getLocalMessage('council.member.validation.email'));
	}*/
	if(couEmail != ''){
		if(!validateEmail(couEmail,true)){
	    	errorList.push(getLocalMessage('council.validation.emailId'));
	    }else{
	    	$(".warning-div").hide();
	    }
	}
	
	if(couAddress == ''){
		errorList.push(getLocalMessage('council.member.validation.address'));
	}
	
	if (couElecDate == '') {
		errorList.push(getLocalMessage('council.member.validation.elecdate'));
	}
	else{
		var dateErrList = validateDateFormat($("#couElecDate").val());
		if(correct == false)
			{
			errorList.push(getLocalMessage('Elected Date : '+ dateErrList));
			}
	}
	if (couOathDate == '') {
		errorList.push(getLocalMessage('council.member.validation.oathdate'));
	}
	else{
		var dateErrList = validateDateFormat($("#couOathDate").val());
		if(correct == false)
			{
			errorList.push(getLocalMessage('Date Of taking oath: '+ dateErrList));
			}
	}
	if (couPartyAffilation == '' || couPartyAffilation == 0) {
		errorList.push(getLocalMessage('council.member.validation.partyaff'));
	}
	if ((compareDate(couDOB)) > compareDate(couElecDate)) {
		errorList.push(getLocalMessage('council.member.validation.checkdob'));
	}
	if ((compareDate(couElecDate)) > compareDate(couOathDate)) {
		errorList
				.push(getLocalMessage('council.member.validation.checkelecdate'));
	}
	if (couEleWZId1 == '' || couEleWZId1 == 0) {
		errorList.push(getLocalMessage('council.member.validation.ElecWard'));
	}
	if (couEleWZId2 == '' || couEleWZId2 == 0) {
		errorList.push(getLocalMessage('council.member.validation.ElecZone'));
	}
	
	
    if(couEleWZId1 == undefined){
    	
	}
	if(couEleWZId2 == undefined){
		
	}
	
	/*  if ((compareDate(couDOB)) > compareDate(today)) {
	  errorList.push(getLocalMessage('council.member.validation.checkdobwithcurrentdate')); }*/
	 
	if (couMemberType == '' || couMemberType == 0){
		errorList.push(getLocalMessage('council.member.validation.couMemberType'));
	}
	
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} 
	else {
		return saveOrUpdateForm(obj, '', 'CouncilMemberMaster.html', 'saveform');
	}
}

//function used for compare dates
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function backMemberMasterForm() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CouncilMemberMaster.html');
	$("#postMethodForm").submit();
}

function resetMemberMaster(resetBtn){
	/*$('#designation').val('').trigger('chosen:updated');*/
	resetForm(resetBtn);
}

// ajax call for Edit and View council member
function showGridOption (couId,action){
	
	  var isOther = $(this).find(":selected").attr("code");
	
	var actionData;
	var divName = formDivName;
	var requestData = 'couId=' + couId;
	if (action == "E") {
		actionData = 'editCouncilMemberMasterData';
		var ajaxResponse = doAjaxLoading('CouncilMemberMaster.html?' + actionData,
				requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);		
		var couPartyName= $("#couPartyName").val();
		if(couPartyName!=undefined ){			 
			  $(".partyName") .removeClass("hidden");
			
		}		
		prepareTags();
	}
	
	if (action == "V") {

		actionData = 'viewCouncilMemberMasterData';
		var ajaxResponse = doAjaxLoading('CouncilMemberMaster.html?' + actionData,
				requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		var couPartyName= $("#couPartyName").val();
		if(couPartyName!=undefined ){			 
			  $(".partyName") .removeClass("hidden");			
		}	
		prepareTags();
	}
}


$(".partyAffilation").on('change', function() {
	  var isOther = $(this).find(":selected").attr("code");
	 
	  if(isOther == "OTHER"){
		 
		  $(".partyName") .removeClass("hidden");
		
	  }else{ $(".partyName").addClass("hidden");}

	});


function compareDateOfBirth(date1)
{
	var result ;
	
		var today = new Date();
		var dateValue1 = date1;
		var split1 = dateValue1.split('/');
		
		var dd1 = parseInt(split1[0]);
		var mm1 = parseInt(split1[1]);
		var yy1 = parseInt(split1[2]);
  
	    var age = today.getFullYear() - yy1;
	    var m = (today.getMonth()+1) - mm1;
	    if (m < 0 || (m === 0 && today.getDate() < dd1)) {
	        age--;
	    }    

	    if(age<18)
	    	return result = false;
	    else return result = true;

}
function validateDateFormat(date1)
{
	 var errorList = [];
	
	 var dateValue= date1;
	 if(dateValue != null && dateValue != ""){
	 	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	 	if(dateValue.match(dateformat))
	 	{
	 	var opera1 = dateValue.split('/');
	 	lopera1 = opera1.length;
	 	if (lopera1>1)
	 	{
	 	var pdate = dateValue.split('/');
	 	}
	 	var dd = parseInt(pdate[0]);
	 	var mm  = parseInt(pdate[1]);
	 	var yy = parseInt(pdate[2]);
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
	 	if (sDate > new Date()) {
	 	}
	 	}
	 	else
	 	{
	 	errorList.push('Invalid Date Format');
	 	}	
	 }
	 
	 displayErrorsOnPage(errorList);
	  if(errorList.length > 0)
	 { correct = false;}
	 else{correct = true;}
		return errorList;
	
	 
}

