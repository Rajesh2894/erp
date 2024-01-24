$(function(){
	 $("#povertyLineId").change(function () {
	        var isBelowPoverty = $('#povertyLineId').val();
	        if (isBelowPoverty == 'Y') {
	        	$('#bpldiv').show();
	        } else {
	        	$('#bpldiv').hide();
	        }
	    });
});

/*
 * 
 */
$(function(){
	$("#searchConnection").click(function(){
		
		/*_openPopUpForm('ChangeOfOwnership.html','getConnectionRecords');*/
		var errorList = [];
		errorList = validateApplicantInfo(errorList);
		var conNum= $.trim($("#conNum").val());
		if(conNum =="" || conNum == undefined ){
			errorList.push(getLocalMessage('water.serchconnectn.entrconnctnno'));
		 }
		if (errorList.length == 0) {
			
			var theForm = '#ChangeOfOwnershipId';
			var data = {
					"conNum" : conNum
				};
			data = __serializeForm(theForm);
				var URL = 'ChangeOfOwnership.html?getConnectionRecords';
				var a_middleName = $('#middleName').val();
				var a_emailId = $('#emailId').val();
				var a_flatNo = $('#flatNo').val();
				var a_buildingName = $('#buildingName').val();
				var a_roadName = $('#roadName').val();
				var a_areaName = $('#areaName').val();
				var a_blockName = $('#blockName').val();
				var a_villTownCity = $('#villTownCity').val();
				var a_aadharNo = $('#aadharNo').val();
				var a_povertyLineId = $('#povertyLineId').val();
				var a_bplNo = $('#bplNo').val();
				var a_dwzid1 = $('#dwzid1').val();
				var a_dwzid2 = $('#dwzid2').val();
				
				var responseObj = __doAjaxRequest(URL, 'POST', data, false);
				if (responseObj.status == 'N') {
				 errorList.push(getLocalMessage('water.serchconnectn.norecrdconcntno'+conNum));
				 
					$("#conName").val('');
					 $("#oldAdharNo").val('');
					 $("#conCat").val('');
					 $("#conType").val('');
					 $("#conSize").val('');
					 $('#searchConnection').attr('disabled',false);
					displayErrorsOnPage(errorList);
				} else {
					$('#fomDivId').html(responseObj);
					$('#povertyLineId').val(a_povertyLineId);
					//$('#searchConnection').attr('disabled',true);
					 $('#conSize').attr('disabled',true);
					 $(".error-div").hide();	
				}
				
				
		} else {
			displayErrorsOnPage(errorList);
		}
		
			
		});
});


/**
 * 
 */
function saveChangeOfOwnerShip(element) {
	var errorList = [];
	errorList = validateChangeOfOwnershipFormData(errorList);
	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'Y') {

			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'ChangeOfOwnership.html?redirectToPay', 'saveform');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'N') {
			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'ChangeOfOwnership.html?PrintReport', 'saveform');
		} else if ($("#FreeService").val() == 'F') {
			
			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'CitizenHome.html', 'saveform');
		}
	} else {
		displayErrorsOnPage(errorList);
	}
	
	
}


/**
 * used to find applicable checklist and charges for change of ownership
 * @param obj
 */
function getChecklistAndChargesForChangeOfOwner(obj) {
	
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);
	var isBPL = $('#povertyLineId').val();
	
	$('.appendableClass').each(function(i) {
		row=i+1;
		if (isAdditionalOwnerDetailRequired(i) == 'Y') {
			errorList = validateAdditionalOwnerTableData(errorList,i);
		}
	});
	
	
	if (errorList.length == 0) {
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var requestData = {
				"isBPL" : isBPL
		};
		
			requestData = __serializeForm(theForm);

		var url	=	'ChangeOfOwnership.html?getCheckListAndCharges';
		var returnData=__doAjaxRequest(url, 'post',requestData, false,'',obj);
		
		$('#fomDivId').html(returnData);
		$('#povertyLineId').val(isBPL);
		$('#searchConnection').attr('disabled',true);
		$('#confirmToProceedId').attr('disabled',true);
		
	} else {
		displayErrorsOnPage(errorList);
	}
}



/**
 * to validate Change Of Ownership Form data on submit
 * @param errorList
 * @returns
 */
function validateChangeOfOwnershipFormData(errorList) {
	

	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);
	//validating additional owners
	if (errorList.length == 0) {
		$('.appendableClass').each(function(i) {
			row=i+1;
			if (isAdditionalOwnerDetailRequired(i) == 'Y') {
				errorList = validateAdditionalOwnerTableData(errorList,i);
			}
		});
	} 
	
	return errorList;	

}

/**
 * validate applicant info
 * @param errorList
 * @returns
 */
function validateApplicantInfo(errorList) {
	
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	/*var gender= $.trim($('#gender').val());*/
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var applicantAreaName= $.trim($('#areaName').val());
	/*var blockName= $.trim($('#blockName').val());*/
	/*var villTownCity= $.trim($('#villTownCity').val());*/
	var applicantPinCode= $.trim($('#pinCode').val());
	var applicantAdharNo= $.trim($('#adharNo').val());
	var povertyLineId= $.trim($('#povertyLineId').val());
	
	
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	/* if(gender == "" || gender =='0' || gender == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantGender'));
	 }*/
	 if(applicantMobileNo == "" || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(applicantAreaName == "" || applicantAreaName == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantarea'));
	 }
/*	 if(blockName == "" || blockName == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantBlockName'));
	 }*/
	/* if(villTownCity == "" || villTownCity == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	*/ if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 if(povertyLineId == "" || povertyLineId ==''|| povertyLineId == undefined){
		 errorList.push(getLocalMessage('water.validation.Applicantpovertyln'));
	 } else {
		 if (povertyLineId == 'Y') {
			 var bplNo= $.trim($('#bplNo').val());
			 if (bplNo == '' || bplNo == undefined) {
				 errorList.push(getLocalMessage('water.validation.bplnocantempty'));
			 }
		 }
	 }
	 
	 return errorList;
}

/**
 * to validate old owner details
 */
function validateOldOwnerInfo(errorList) {
	
	var conNum= $.trim($('#conNum').val());
	 if(conNum == "" || conNum == undefined){
		 errorList.push(getLocalMessage('water.validation.connectionno'));
	 }
	return errorList;
}

/**
 * to validate New Owner Information
 * @param errorList
 * @returns
 */
function validateNewOwnerInfo(errorList) {
	
	var tMode = $.trim($('#ownerTransferMode').val())
	var cooNotitle= $.trim($('#cooNotitle').val());
	var cooNoname= $.trim($('#changeOwnerMaster\\.cooNoname').val()); 
	var cooNolname= $.trim($('#changeOwnerMaster\\.cooNolname').val()); 
	var newGender= $.trim($('#newGender').val()); 
	
	
	if(tMode == "" || tMode =='0' || tMode == undefined){
		 errorList.push(getLocalMessage('water.validation.transferMode'));
	}	
	
	if(cooNotitle == "" || cooNotitle =='0' || cooNotitle == undefined){
		 errorList.push(getLocalMessage('water.validation.ctitle'));
	 }
	 if(cooNoname == "" || cooNoname == undefined){
		 errorList.push(getLocalMessage('water.newowner.fname.validtn'));
	 }
	 if(cooNolname == "" || cooNolname == undefined){
		 errorList.push(getLocalMessage('water.newowner.lname.validtn'));
	 } 
	 if(newGender == "" || newGender =='0' || newGender == undefined){
		 errorList.push(getLocalMessage('water.newowner.gender.validtn'));
	 } 
	 
	return errorList;
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	
	
	
 	
	return false;
}


function closeOutErrBox(){
	$('.error-div').hide();
}

var count;
/**
 * additional owner event
 */
$(function(){
	
	$("#customFields").on('click','.addCF',function(i){
		
		var row=0;
		var errorList = [];
		errorList = validateAdditionalOwners(errorList);
		
		if (errorList.length == 0) {
			if (errorList.length == 0 ) {
				 var romm=0;
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;	
				content.find("input:text").val('');
				content.find("select").val('0');
				$('.error-div').hide();
				
				reOrderTableIdSequence();
			}else {
				displayErrorsOnPage(errorList);
			}
			
		}else {
			displayErrorsOnPage(errorList);
		}
	
});
	
$("#customFields").on('click','.remCF',function(){
		
		if($("#customFields tr").length != 2)
			{
				 $(this).parent().parent().remove();					
				 reOrderTableIdSequence();
			}
	   else
			{
		   var errorList = [];
		   errorList.push(getLocalMessage("water.additnlowner.deletrw.validtn"));
		   displayErrorsOnPage(errorList);
				//alert("You cannot delete first row");
			}
		
 });

});

/**
 * 
 */
function reOrderTableIdSequence() {

	
	$('.appendableClass').each(function(i) {

//		$(this).find("td:eq(0)").attr("id", "srNoId_"+i);
		$(this).find("select:eq(0)").attr("id", "caoNewTitle_"+i);
		$(this).find("input:text:eq(0)").attr("id", "caoNewFName_"+i);
		$(this).find("input:text:eq(1)").attr("id", "caoNewMName_"+i);
		$(this).find("input:text:eq(2)").attr("id", "caoNewLName_"+i);
		$(this).find("select:eq(1)").attr("id", "caoNewGender_"+i);
		$(this).find("input:text:eq(3)").attr("id", "caoNewUID_"+i);
		$("#srNoId_"+i).text(i+1);

		$(this).find("select:eq(0)").attr("name","additionalOwners["+i+"].caoNewTitle");
		$(this).find("input:text:eq(0)").attr("name","additionalOwners["+i+"].caoNewFName").attr("onkeyup","hasCharacterFField("+i+")");	
		$(this).find("input:text:eq(1)").attr("name","additionalOwners["+i+"].caoNewMName").attr("onkeyup","hasCharacterMField("+i+")");	
		$(this).find("input:text:eq(2)").attr("name","additionalOwners["+i+"].caoNewLName").attr("onkeyup","hasCharacterLField("+i+")");
		$(this).find("select:eq(1)").attr("name","additionalOwners["+i+"].caoNewGender");
		$(this).find("input:text:eq(3)").attr("name", "additionalOwners["+i+"].caoNewUID").attr("onkeyup","hasNumberField("+i+")");
		
				
	});
	
}



/**
 * validate additional owners details
 * @param errorList
 */
function validateAdditionalOwners(errorList) {
	
	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);
//	alert('errorlist='+errorList.length);
	if (errorList.length == 0) {
		$('.appendableClass').each(function(i) {
			row=i+1;
			if (isAdditionalOwnerDetailRequired(i)) {
				errorList = validateAdditionalOwnerTableData(errorList,i);
			}
			
		});
	}
	
	return errorList;
}
/**
 * validate each mandatory column of additional owner details 
 * @param errorList
 * @param i
 * @returns
 */
function validateAdditionalOwnerTableData(errorList, i) {

	
	 var applicantTitle = $.trim($("#caoNewTitle_"+i).val());
	 var applicantFirstName = $.trim($("#caoNewFName_"+i).val());		
	 var applicantLastName = $.trim($("#caoNewLName_"+i).val());		
	 var gender = $.trim($("#caoNewGender_"+i).val());		
	
	 if(applicantTitle =="" || applicantTitle =='0'  || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.additnlowner.titel.validtn'));
	 }
	 if(applicantFirstName == "" || applicantFirstName == undefined){
		 errorList.push(getLocalMessage('water.additnlowner.fname.validtn'));
	 }
	 if(applicantLastName == "" || applicantLastName == undefined){
		 errorList.push(getLocalMessage('water.additnlowner.lname.validtn'));
	 }
	 if(gender == "" || gender =='0' || gender == undefined){
		 errorList.push(getLocalMessage('water.additnlowner.gender.validtn'));
	 }
	
	 return errorList;
}

/**
 * function to validate additional owner info whether additional
 * owner info is required or not,if any mandatory field of 
 * additioal owner is entered, rest of mandatory fields need to be validated.
 */
function isAdditionalOwnerDetailRequired (index) {
	
	 var isAdditionalOwnerDetailRequired = "N";
	 var applicantTitle = $.trim($("#caoNewTitle_"+index).val());
	 var applicantFirstName = $.trim($("#caoNewFName_"+index).val());		
	 var applicantLastName = $.trim($("#caoNewLName_"+index).val());		
	 var gender = $.trim($("#caoNewGender_"+index).val());		
	
	 if(applicantTitle !="" && applicantTitle !='0' && applicantTitle !=0 && applicantTitle != undefined ){
		 isAdditionalOwnerDetailRequired = "Y";
	 }
	 if(applicantFirstName != "" &&  applicantFirstName != undefined){
		 isAdditionalOwnerDetailRequired = "Y";
	 }
	 if(applicantLastName != "" &&  applicantLastName != undefined){
		 isAdditionalOwnerDetailRequired = "Y";
	 }
	 if(gender != "" &&  gender !='0' &&  gender !=0 &&  gender != undefined){
		 isAdditionalOwnerDetailRequired = "Y";
	 }
	 console.log('applicantTitle='+applicantTitle+',applicantFirstName='+applicantFirstName+',applicantLastName='+applicantLastName+',gender='+gender);
	console.log('inside='+isAdditionalOwnerDetailRequired);
	 return isAdditionalOwnerDetailRequired;
}
