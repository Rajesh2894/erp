$(document).ready(function() {
	
	prepareDateTag();
	$("#birthRegDraftDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	var dateFields = $('.date,.lessthancurrdate');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
	var langFlag = getLocalMessage('admin.lang.translator.flag');
	if(langFlag ==='Y'){
		$('#brChildName').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#brChildName").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'brChildNameMar',event,'');
			}else{
				$("#brChildNameMar").val('');
			}
		});
		$('#brBirthPlace').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#brBirthPlace").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'brBirthPlaceMar',event,'');
			}else{
				$("#brBirthPlaceMar").val('');
			}
		});
		$('#brBirthAddr').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#brBirthAddr").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'brBirthAddrMar',event,'');
			}else{
				$("#brBirthAddrMar").val('');
			}
		});
		$('#pdFathername').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#pdFathername").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'pdFathernameMar',event,'');
			}else{
				$("#pdFathernameMar").val('');
			}
		});
	
		$('#pdMothername').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#pdMothername").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'pdMothernameMar',event,'');
			}else{
				$("#pdMothernameMar").val('');
			}
		});
		$('#pdParaddress').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#pdParaddress").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'pdParaddressMar',event,'');
			}else{
				$("#pdParaddressMar").val('');
			}
		});
		$('#pdAddress').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#pdAddress").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'pdAddressMar',event,'');
			}else{
				$("#pdAddressMar").val('');
			}
		});
		$('#brInformantName').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#brInformantName").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'brInformantNameMar',event,'');
			}else{
				$("#brInformantNameMar").val('');
			}
		});
		$('#brInformantAddr').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#brInformantAddr").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'brInformantAddrMar',event,'');
			}else{
				$("#brInformantAddrMar").val('');
			}
		});
		$('#motheraddress').bind('click keyup', function(event) {
			var no_spl_char;
			no_spl_char = $("#motheraddress").val().trim();
			if(no_spl_char!=''){
				commonlanguageTranslate(no_spl_char,'motheraddressMar',event,'');
			}else{
				$("#motheraddressMar").val('');
			}
		});
	}
	   
});

function saveBirthData(element) {

	var errorList = [];
	
	var chargeStatus = $("#chargeStatus").val();
	var chargesAmount = $("#chargesAmount").val();
	errorList = validateBndData();
	if(chargeStatus=="CA" && chargesAmount!="0"){
		if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="N" && $("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="P" )
		{
			errorList.push(getLocalMessage("bnd.select.payment"));
		}
		else if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="N")
			{
			if($("#oflPaymentMode").val()==0 ) 
			{
			errorList.push(getLocalMessage("bnd.select.payment.mode"));}
			}else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="P")
				{
				if( $("#payModeIn").val()==0)
					{
					errorList.push(getLocalMessage("bnd.select.payment.mode"));
			       }
				}
	}
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		$('#brDob').prop("disabled", false);
		if (chargeStatus == 'CA' && chargesAmount!="0") {
			return saveOrUpdateForm(element, "",
					'BirthRegistrationForm.html?PrintReport', 'saveform');
		} else {
			return saveOrUpdateForm(element, "", 'BirthRegistrationForm.html',
					'saveform');
		}
	}
}

function validateBndData(errorList){
	var bptCode = $("#brBirthPlaceType :selected").attr('code');	
	var errorList = [];
	var birthType = $("#cpdRefTypeId").val();
	var dob = $('#brDob').val();
	var sex = $("#brSex").val();
	var birthWt = $("#BrBirthWt").val();
	var brBirthPlaceType = $("#brBirthPlaceType").val();
	var hospName = $("#hospitalList").val();
	var placeOfBrEng = $("#brBirthPlace").val();
	var placeOfBrMar = $("#brBirthPlaceMar").val();
	var brAddEng = $("#brBirthAddr").val();
	var brAddMar = $("#brBirthAddrMar").val();
	var infoNmEng = $("#brInformantName").val();
	var infoNmMar = $("#brInformantNameMar").val();
	var infoAddEng = $("#brInformantAddr").val();
	var infoAddMar = $("#brInformantAddrMar").val();
	var attType = $("#cpdAttntypeId").val();
	var delMeth = $("#cpdDelMethId").val();
	var pregDur = $("#brPregDuratn").val();
	var birthMrk = $("#brBirthMark").val();
	var cpdAttntypeId = $("#cpdAttntypeId").val();
	var pdFathername = $("#pdFathername").val();
	var pdFathernameMar = $("#pdFathernameMar").val();
	var cpdFEducnId = $("#cpdFEducnId").val();
	var cpdFOccuId = $("#cpdFOccuId").val();
	var pdMothername = $("#pdMothername").val();
	var pdMothernameMar = $("#pdMothernameMar").val();
	var cpdMEducnId = $("#cpdMEducnId").val();
	var cpdMOccuId = $("#cpdMOccuId").val();
	var pdAgeAtMarry = $("#pdAgeAtMarry").val();
	var pdAgeAtBirth = $("#pdAgeAtBirth").val();
	var pdLiveChildn = $("#pdLiveChildn").val();
	var motheraddress = $("#motheraddress").val();
	var motheraddressMar = $("#motheraddressMar").val();
	var pdParaddress = $("#pdParaddress").val();
	var pdParaddressMar = $("#pdParaddressMar").val();
	var cpdId1 = $("#cpdId1").val();
	var cpdId2 = $("#cpdId2").val();
	var cpdId3 = $("#cpdId3").val();
	var cpdId4 = $("#cpdId4").val();
	var cpdReligionId = $("#cpdReligionId").val();
	var pdRegUnitId = $("#pdRegUnitId").val();
	var brRegNo=$("#brRegNo").val();
	var brRegDate=$("#brRegDate").val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var brRegdate = new Date(brRegDate.replace(pattern, '$3-$2-$1'));
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var brDob = new Date(dob.replace(pattern, '$3-$2-$1'));
	var rowcount=$("#DeathTable tr").length 
	/*for(var i=0;i<rowcount-1;i++){
	 var checklistUploadedOrNot=$("#checkList"+i).val();
	 if(checklistUploadedOrNot==""){
		 errorList.push(getLocalMessage("bnd.upload.doc") +(i+1));
        }
    }*/
	
	/*if (birthType == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrTyp"));
	}*/
	if (dob == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrDt"));
	}
	if (brDob > currDate) {
		errorList.push(getLocalMessage("BirthRegDto.BrDtGr"));
	}
	if (sex == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrGen"));
	}
	/*if (birthWt == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrWt"));
	}*/
	if(birthWt != "" && birthWt >=10){
		errorList.push(getLocalMessage("BirthRegDto.BrWtValid"));
	}
	/*if (brBirthPlaceType == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrPlcTyp"));
	}*/
	/*if (bptCode == "I" && hospName == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrHosp"));
	}*/
	if (placeOfBrEng == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrPlcEng"));
	}
	if (placeOfBrMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrPlcReg"));
	}
	if (brAddEng == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrAddrEng"));
	}
	if (brAddMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrAddrReg"));
	}
	/*if (infoNmEng == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrInfoEng"));
	}
	if (infoNmMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrInfoReg"));
	}
	if (infoAddEng == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrInfoAddrEng"));
	}
	if (infoAddMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrInfoAddrReg"));
	}*/
	/*if (cpdAttntypeId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrAttn"));
	}
	if (delMeth == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrDel"));
	}
	if (pregDur == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrPreg"));
	}*/
	if(pregDur != "" && pregDur >=50){
		errorList.push(getLocalMessage("BirthRegDto.BrPregValid"));
	}
	
	/*if (birthMrk == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMark"));
	}
	*/
	if (pdFathername == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherNmEng"));
	}
	if (pdFathernameMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherNmReg"));
	}
	
	/*if (cpdFEducnId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherEdu"));
	}
	if (cpdFOccuId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherOcc"));
	}*/
	if (pdMothername == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherNmEng"));
	}
	if (pdMothernameMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherNmReg"));
	}
	/*if (cpdMEducnId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherEdu"));
	}
	if (cpdMOccuId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherOcc"));
	}*/
	/*if (pdAgeAtMarry == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMothAge"));
	}else*/ if(pdAgeAtMarry != "" && pdAgeAtMarry < 18){
		errorList.push(getLocalMessage("BirthRegDto.BrMothAgeValidate"));
	}
	/*if (pdAgeAtBirth == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMothAgeAtChild"));
	}else*/ if(pdAgeAtBirth != "" && pdAgeAtBirth < 12){
		errorList.push(getLocalMessage("BirthRegDto.BrMothAgeAtChildValidate"));
	}
	/*if (pdLiveChildn == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrNoLiveChild"));
	}*/
	if (motheraddress == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherAddrEng"));
	}if (motheraddressMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherAddrReg"));
	}
	/*if (pdParaddress == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrParentAddrEng"));
	}
	if (pdParaddressMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrParentAddrReg"));
	}
	if (cpdId1 == "0") {
		errorList.push(getLocalMessage("BirthRegDto.Country"));
	}
	if (cpdId2 == "0") {
		errorList.push(getLocalMessage("BirthRegDto.State"));
	}
	if (cpdId3 == "0") {
		errorList.push(getLocalMessage("BirthRegDto.Dist"));
	}
	if (cpdId4 == "0") {
		errorList.push(getLocalMessage("BirthRegDto.Tal"));
	}
	if (cpdReligionId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.Religion"));
	}
	if (pdRegUnitId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.RegUnit"));
	}*/
	if (brRegNo == "") {
		errorList.push(getLocalMessage("BirthRegDto.RegNo"));
	}
	if (brRegDate == "") {
		errorList.push(getLocalMessage("BirthRegDto.Date"));
	}
	if (brRegdate < brDob) {
		errorList.push(getLocalMessage("BirthRegDto.DateLess"));
	}
	if (brRegdate > currDate) {
		errorList.push(getLocalMessage("BirthRegDto.DateGreater"));
	}
	
	
	return errorList;
}


function resetBirthData() {
	window.open('BirthRegistrationForm.html', '_self');
}

$(document).ready(function() {  
    var end = new Date();
    end.setFullYear(2016);
    $("#brDob").datepicker({
        dateFormat : 'dd/mm/yy',
        changeMonth : true,
         changeYear: true,
        yearRange: "-200:+200",
        maxDate : new Date(end.getFullYear(), 11, 31)
    });
});

function getChecklistAndCharges(element) {

	var errorList = [];
	var flag = false;
	errorList = validateBndData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	}else{
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'BirthRegistrationForm.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
            $('#resetId').hide();
			$('#backId').hide();
			$('#savedrftId').hide();
			//$('#brDob').prop("disabled", true);
			var bptCode = $("#brBirthPlaceType :selected").attr('code');
			if(bptCode=="I")
			{
			$('#hospitalList').prop("disabled", false);
			}else{
				$('#hospitalList').prop("disabled",true);
			}
			//$('#hospitalList').prop("disabled", true);
			//$('#brRegNo').prop("disabled", true);
			}
           }
		}
  


function getAmountOnNoOfCopes(){
 	var errorList= [];
 	var form_url = $("#frmIssuCertificateForm").attr("action");
  	var url=form_url+'?getBNDCharge';
 	var isscopy=0;
 	var isscopy=$("#brCertNo").val();
 	if(isscopy=='' || isscopy==undefined ){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
 	}
 	else if($('#numberOfCopies').val()!='' && $('#numberOfCopies').val()!=undefined){
 		var requestData = "noOfCopies=" + $('#numberOfCopies').val()+ "&issuedCopy=" +isscopy;
 		var returnData = __doAjaxRequest(url, 'post', requestData, false,
 				'json');  
 		 $("#amount").val(returnData);
 	}
 	else{
 		//errorList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorList);
 	}
}

function validateBndDraftData(element) {
	
	var errorList = [];
	var dob = $('#brDob').val();
	var currDate = new Date();
	if (dob == "" || dob == null) {
		errorList.push(getLocalMessage("BirthRegDto.BrDt"));
	}
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var dob = new Date(dob.replace(pattern, '$3-$2-$1'));
	if (dob > currDate) {
		errorList
				.push(getLocalMessage("BirthRegDto.DateGreater"));
	}
	return errorList;
}

function searchBirthData(element) {

	var errorList = [];
	errorList = validateBirthSearchForm(errorList);
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var table = $('#birthRegDraftDataTable').DataTable();
		var url = "BirthRegistrationForm.html?searchBirthDraft";
		var requestData = "&applnId=" + $("#applnId").val() + "&brDob="
				+ $("#brDob").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');

		table.rows().remove().draw();
		if (returnData == 'Internal Server Error.') {
			errorList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorList);
		}

		var result = [];
		$
				.each(
						returnData,
						function(index) {
							var obj = returnData[index];
							let brDraftId = obj.brDraftId;
							let brDob = obj.brDob;
							let brChildName = obj.brChildname;
							let applnId = obj.applnId;
							let brSex = obj.brSex;
							let cpdRegUnit = obj.cpdDesc;

							if (brChildName == null || brChildName == "") {
								brChildName = "-";
							}
							if (applnId == "") {
								applnId = "-";
							}
							if (brSex == null || brSex == "" || brSex == 0) {
								brSex = "-";
							}
							if (cpdRegUnit == null || cpdRegUnit == "") {
								cpdRegUnit = "-"
							}

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ getDateFormat(brDob)
													+ '</div>',
											'<div class="text-center">'
													+ applnId + '</div>',
											'<div class="text-center">'
													+ brChildName + '</div>',
											'<div class="text-center">' 
													+ brSex + '</div>',
											'<div class="text-center">'
													+ cpdRegUnit + '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyBirth('
													+ brDraftId
													+ ","
													+applnId
													+ ',\'BirthRegistrationForm.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);
						});
		table.rows.add(result);
		table.draw();
	}
}

function validateBirthSearchForm(errorList) {

	var applnId = $('#applnId').val();
	var brDob = $("#brDob").val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var brdob = new Date(brDob.replace(pattern, '$3-$2-$1'));
	if (brDob != "" && brdob > currDate) {
		errorList
				.push(getLocalMessage("BirthRegDto.BrDtGr"));
	}
	
	if (applnId == "" && brDob == "") {
		errorList
				.push(getLocalMessage('TbDeathregDTO.label.searchcrit'));
	} else if (applnId != "" || brDob != "") {
		// go for Search
	}

	return errorList;
}

function modifyBirth(brDraftId, applicationId, formUrl, actionParam, mode) {
	
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : brDraftId,
		"applicationId" : applicationId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	var bptCode = $("#brBirthPlaceType :selected").attr('code');
	if(bptCode=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
	//$('#hospitalList').prop("disabled", true);

}

function resetMemberMaster(resetBtn){
	
	/*$('#designation').val('').trigger('chosen:updated');*/
	resetForm(resetBtn);
}



function saveDraftBirthData(element) {

	var errorList = [];
	errorList = validateBndDraftData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		
			$('#status').val("D");
			$('#brDraftId').val();
			return saveOrUpdateForm(element, "", 'BirthRegistrationForm.html',
					'saveform');
		}
	}


function selecthosp(element)
{
    var code=$(element).find(':selected').attr('code')
	if(code=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
}

function checkDate(errorList) {
	
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}
function openForm(formUrl, actionParam) {
	var divName = '.content-page';
	var requestData = {

	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function getRegNo() {
	
	var errorList = [];
	var brRegNo = $("#brRegNo").val();
	var brDraftId = $("#brDraftId").val();
	if (brRegNo != "") {
		var url = "BirthRegistrationForm.html?checkRegnoDupl";
		/*var requestData = "brRegNo=" + brRegNo + "&brDraftId="
				+ brDraftId;*/

		var requestData={
				"brRegNo": brRegNo	,
				"brDraftId" :brDraftId
		};
		var returnData = __doAjaxRequest(url, 'POST', requestData, false,
				'json');

		if (returnData == false) {
			errorList.push("Registration Number Already Exist");
			displayErrorsOnPage(errorList);
		}

	}
}

