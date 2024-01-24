$(document).ready(function() {
	prepareDateTag();

$("#deathRegDraftTable").dataTable({
			"oLanguage" : {
				"sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
  });
			var langFlag = getLocalMessage('admin.lang.translator.flag');
			if (langFlag === 'Y') {
				$('#drDeceasedname').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drDeceasedname").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarDeceasedname', event, '');
							} else {
								$("#drMarDeceasedname").val('');
							}
						});

				$('#drRelativeName').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drRelativeName").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarRelativeName', event, '');
							} else {
								$("#drMarRelativeName").val('');
							}
						});
				$('#drMotherName').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drMotherName").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarMotherName', event, '');
							} else {
								$("#drMarMotherName").val('');
							}
						});
				$('#drDeceasedaddr').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drDeceasedaddr").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarDeceasedaddr', event, '');
							} else {
								$("#drMarDeceasedaddr").val('');
							}
						});
				$('#drDcaddrAtdeath').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drDcaddrAtdeath").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drDcaddrAtdeathMar', event, '');
							} else {
								$("#drDcaddrAtdeathMar").val('');
							}
						});
				$('#drDeathplace').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drDeathplace").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarDeathplace', event, '');
							} else {
								$("#drMarDeathplace").val('');
							}
						});
				$('#drDeathaddr').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drDeathaddr").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarDeathaddr', event, '');
							} else {
								$("#drMarDeathaddr").val('');
							}
						});
				$('#drInformantName').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drInformantName").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarInformantName', event, '');
							} else {
								$("#drMarInformantName").val('');
							}
						});
				$('#drInformantAddr').bind(
						'click keyup',
						function(event) {
							var no_spl_char;
							no_spl_char = $("#drInformantAddr").val().trim();
							if (no_spl_char != '') {
								commonlanguageTranslate(no_spl_char,
										'drMarInformantAddr', event, '');
							} else {
								$("#drMarInformantAddr").val('');
							}
						});

			}
});

$(document).ready(function() {
    var end = new Date();
    end.setFullYear(2016);
    $("#drDod").datepicker({
        dateFormat : 'dd/mm/yy',
        changeMonth : true,
         changeYear: true,
        yearRange: "-200:+200",
        maxDate : new Date(end.getFullYear(), 11, 31)
    });
});


function disFields(element) {
	
	if ($("#within2")!=""){
		$("#within2").val("");
	}
	if ($("#within1").prop('checked') == true){
		$("#within1").val("W");
	}
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
        $('#ceId').prop("disabled", false);
}


function enaFields(element) {

	if ($("#within1")!=""){
		$("#within1").val("");
	}
	if ($("#within2").prop('checked') == true){
		$("#within2").val("O");
	}
	$('#ceName').prop("disabled", false);
	$('#ceNameMar').prop("disabled", false);
	$('#ceAddr').prop("disabled", false);
	$('#ceAddrMar').prop("disabled", false);
	$('#ceId').prop("disabled", true);
	
	
	
}

function resetDeathData() {
	window.open('DeathRegistration.html', '_self');
}

function getChecklistAndCharges(element) {
	
	var errorList = [];
	var drDod = $("#drDod").val();
	
	var within1 = $("#within1").val();
	var within2 = $("#within2").val();
	errorList = validateBndData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	}
	else {
		var flag = false;
		var ceName = $("#ceName").val();
		var ceNameMar = $("#ceNameMar").val();
		var ceAddr = $("#ceAddr").val();
		var ceAddrMar = $("#ceAddrMar").val();
		var cemetryId = $("#ceId").val();
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			if(within1!=""){
				requestData=requestData+"&"+"tbDeathregDTO.ceName="+ceName +"&"+"tbDeathregDTO.ceNameMar="+ceNameMar +"&"+"tbDeathregDTO.ceAddr="+ceAddr +"&"+"tbDeathregDTO.ceAddrMar="+ceAddrMar +"&"+"cemetryId="+cemetryId ;
			}
			var URL = 'DeathRegistration.html?getCheckListAndCharges';
			var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
					'html');

			if (returnData) {
				var divName = '.pagediv';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$(divName).show();
				//$('#chekListChargeId').show();
				$('#proceedId').hide();
				$('#resetId').hide();
				$('#backId').hide();
				$('#drDod').prop("disabled", true);
				$('#savedrftId').hide();
				if(within1!="")
				{
				$('#ceName').prop("disabled", true);
				$('#ceNameMar').prop("disabled", true);
				$('#ceAddr').prop("disabled", true);
				$('#ceAddrMar').prop("disabled", true);
				$('#ceId').prop("disabled", false);
				}
			else if(within2!="")
				{
			$('#ceName').prop("disabled", false);
			$('#ceNameMar').prop("disabled", false);
			$('#ceAddr').prop("disabled", false);
			$('#ceAddrMar').prop("disabled", false);
			$('#ceId').prop("disabled", true);
				}
				var bptCode = $("#cpdDeathplaceType :selected").attr('code');
				if(bptCode=="I")
				{
				$('#hospitalList').prop("disabled", false);
				}else{
					$('#hospitalList').prop("disabled",true);
				}
			}

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

function saveDeathData(element) {
	var errorList = [];
	var chargeStatus=$("#chargeStatus").val();
	var chargesAmount=$("#chargesAmount").val();
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
		displayErrorsOnPage(errorList);
	} else {
		$('#drDod').prop("disabled", false);
		$('#ceName').prop("disabled", false);
		$('#ceNameMar').prop("disabled", false);
		$('#ceAddr').prop("disabled", false);
		$('#ceAddrMar').prop("disabled", false);
		$('#status').val("O");
		
		 if(chargeStatus=='CA' && chargesAmount!="0"){
			 return saveOrUpdateForm(element, "", 'DeathRegistration.html?PrintReport', 'saveform');
			
		 }else {
			 return saveOrUpdateForm(element, "", 'DeathRegistration.html', 'saveform');
		 }
	 	
	}
}

function validateBndData(element){
	var errorList = [];
	var drDod = $('#drDod').val();
	var drSex = $("#drSex").val();
	var age = $("#age").val();
	var ageperiod = $("#ageperiod").val();
	var cpdNationalityId = $("#cpdNationalityId").val();
	var drDeceasedname = $("#drDeceasedname").val();
	var drMarDeceasedname = $("#drMarDeceasedname").val();
	var drRelativeName = $("#drRelativeName").val();
	var drMarRelativeName  = $("#drMarRelativeName").val();
	var drMotherName = $("#drMotherName").val();
	var drMarMotherName=$("#drMarMotherName").val();
	var drDeceasedaddr = $("#drDeceasedaddr").val();
	var drMarDeceasedaddr = $("#drMarDeceasedaddr").val();
	var drDcaddrAtdeath = $("#drDcaddrAtdeath").val();
	var drDcaddrAtdeathMar = $("#drDcaddrAtdeathMar").val();
	var cpdDeathplaceType = $("#cpdDeathplaceType").val();
	var hospitalList = $("#hospitalList").val();
	var drDeathplace = $("#drDeathplace").val();
	var drMarDeathplace = $("#drMarDeathplace").val();
	var drDeathaddr = $("#drDeathaddr").val();
	var drMarDeathaddr = $("#drMarDeathaddr").val();
	var	drInformantName = $("#drInformantName").val();
	var drMarInformantName = $("#drMarInformantName").val();
	var drInformantAddr = $("#drInformantAddr").val();
	var cpdReligionId = $("#cpdReligionId").val();
	var cpdEducationId = $("#cpdEducationId").val();
	var cpdMaritalStatId = $("#cpdMaritalStatId").val();
	var cpdOccupationId = $("#cpdOccupationId").val();
	var cpdRegUnit = $("#cpdRegUnit").val();
	var cpdAttntypeId = $("#cpdAttntypeId").val();
	var within1 = $("#within1").val();
	var within2 = $("#within2").val();
	var ceId = $("#ceId").val();
	var ceName = $("#ceName").val();
	var ceNameMar = $("#ceNameMar").val();
	var ceAddr = $("#ceAddr").val();
	var ceAddrMar = $("#ceAddrMar").val();
	var cpdDeathcauseId = $("#cpdDeathcauseId").val();
	var mcDeathManner = $("#mcDeathManner").val();
	var drRegdate  = $("#drRegdate").val();
	var currDate = new Date();
	var mcVerifnDate = $("#mcVerifnDate").val();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drdod = new Date(drDod.replace(pattern, '$3-$2-$1'));
	var bptCode = $("#cpdDeathplaceType :selected").attr('code');
	var ceFlag = $('input[name="tbDeathregDTO.ceFlag"]:checked').val();
	var drRegno = $("#drRegno").val();
	var wardid = $("#wardid").val();
	//var currDate = new Date();
	var offlinebutton=$("#offlinebutton").val();
	var payAtCounter=$("#payAtCounter").val();
	var offlineModeFlagId=$("#offlineModeFlagId").val();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drRegDate = new Date(drRegdate.replace(pattern, '$3-$2-$1'));

	if (drRegDate > currDate) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.regDateGreater"));
	}
	if (drRegDate < drdod) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.regDatelessthandrdod"));
	}
	
	var rowcount=$("#DeathTable tr").length 

	/*for(var i=0;i<rowcount-1;i++){
	 var checklistUploadedOrNot=$("#checkList"+i).val();
	 if(checklistUploadedOrNot==""){
		 errorList.push(getLocalMessage("TbDeathregDTO.label.checklist"+(i+1)));
        }
    }*/
	var mcVerifnDate = new Date(mcVerifnDate.replace(pattern, '$3-$2-$1'));

	if (drDod == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDod"));
	}
	if (drdod > currDate) {
		errorList
				.push(getLocalMessage("TbDeathregDTO.label.drDodcurrDate"));
	}
	if (drRegdate == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drRegdate"));
	}
	if (drSex == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drSex"));
	}
	if (age == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.age"));
	}
	if (age >=150) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.validAge"));
	}
	if (ageperiod == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.agePeriod"));
	}
	if (cpdNationalityId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdNationalityId"));
	}
	if (drDeceasedname == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeceasedname"));
	}
	if (drMarDeceasedname == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeceasedname"));
	}
	if (drRelativeName == "" ) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drRelativeName"));
	}
	if (drMarRelativeName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarRelativeName"));
	}
	if (drMotherName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMotherName"));
	}
	if (drMarMotherName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarMotherName"));
	}
	if (drDeceasedaddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeceasedaddr"));
	}
	if (drMarDeceasedaddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeceasedaddr"));
	}
	if (drDcaddrAtdeath == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDcaddrAtdeath"));
	}
	if (drDcaddrAtdeathMar == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDcaddrAtdeathMar"));
	}
	if (cpdDeathplaceType == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdDeathplaceType"));
	}
	if (hospitalList != "" && bptCode != "I") {
		errorList.push("Select death place type as Hospital");
	}
	if (bptCode == "I" && hospitalList == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.hosp"));
	}
	if (drDeathplace == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeathplace"));
	}
	if (drMarDeathplace == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeathplace"));
	}
	if (drDeathaddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeathaddr"));
	}
	if (drMarDeathaddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeathaddr"));
	}
	if (drInformantName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drInformantName"));
	}
	if (drMarInformantName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarInformantName"));
	}
	if (drInformantAddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drInformantAddr"));
	}
	if (drMarInformantAddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarInformantAddr"));
	}
	if (cpdReligionId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdReligionId"));
	}
	if (cpdEducationId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdEducationId"));
	}
	if (cpdMaritalStatId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdMaritalStatId"));
	}
	if (cpdOccupationId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdOccupationId"));
	}
	if (cpdRegUnit == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdRegUnit"));
	}
	if (cpdAttntypeId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdAttntypeId"));
	}
	if(ceFlag!="W" && ceFlag!="O")
		{
	if(within1!= "" && within2!="")
		{
			errorList.push(getLocalMessage("TbDeathregDTO.label.CemeLocation"));
			
		}
		else if(within2!=""){
		if(ceName==""){
			errorList.push(getLocalMessage("TbDeathregDTO.label.ceName"));
			}
		if(ceNameMar==""){
			errorList.push(getLocalMessage("TbDeathregDTO.label.ceNameMar"));
			}
		if(ceAddr==""){
			errorList.push(getLocalMessage("TbDeathregDTO.label.ceAddr"));
			}
		if(ceAddrMar==""){
			errorList.push(getLocalMessage("TbDeathregDTO.label.ceAddrMar"));
			}
		}
	 else  if(within1!="")
		{
			if(ceId=="")
				{
				errorList.push(getLocalMessage("TbDeathregDTO.label.ceID"));
				}
			}
		}
	else
		{
		 if(ceFlag=="O"){
				if(ceName==""){
					errorList.push(getLocalMessage("TbDeathregDTO.label.ceName"));
					}
				if(ceNameMar==""){
					errorList.push(getLocalMessage("TbDeathregDTO.label.ceNameMar"));
					}
				if(ceAddr==""){
					errorList.push(getLocalMessage("TbDeathregDTO.label.ceAddr"));
					}
				if(ceAddrMar==""){
					errorList.push(getLocalMessage("TbDeathregDTO.label.ceAddrMar"));
					}
				}
			 else  if(ceFlag=="W")
				{
					if(ceId=="")
						{
						errorList.push(getLocalMessage("TbDeathregDTO.label.ceID"));
						}
					}
		}
		
	if (cpdDeathcauseId == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.cpdDeathcauseId"));
	}
	if (mcDeathManner == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.mcDeathManner"));
	}
	if (drdod > mcVerifnDate) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drdodmcVerifnDate"));
	}drRegno
	if (drRegno == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drRegno"));
	}
	if (wardid == "" || wardid== "0") {
		errorList.push(getLocalMessage("BirthRegDto.selectWardName"));
	}

	return errorList;
}

function saveDraftDeathData(element) {
	var errorList = [];
	errorList = validateBndDraftData();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} 
	else {
	    
		$('#drDod').prop("disabled", false);
		$('#status').val("D");
		$('#drDraftId').val();
		$('#ceName').prop("disabled", false);
		$('#ceNameMar').prop("disabled", false);
		$('#ceAddr').prop("disabled", false);
		$('#ceAddrMar').prop("disabled", false);
		return saveOrUpdateForm(element, "", 'DeathRegistration.html',
				'saveform');
	  
	}

}

function validateBndDraftData(element){
	var errorList = [];
	var drDod = $("#drDod").val();
	var drRegdate  = $("#drRegdate").val();
	var currDate = new Date();

	if (drDod == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDodblank"));
	}

	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drDod = new Date(drDod.replace(pattern, '$3-$2-$1'));

	if (drDod > currDate) {
		errorList
				.push(getLocalMessage("TbDeathregDTO.label.drDodcurrDate"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drRegdate = new Date(drRegdate.replace(pattern, '$3-$2-$1'));

	if (drRegdate > currDate) {
		errorList
				.push(getLocalMessage("TbDeathregDTO.label.regDateGreater"));
	}
	
	return errorList;

}

function selecthosp(element) {
	var errorsList = [];
	var code = $(element).find(':selected').attr('code')
	if (code == "I") {
		var hospitalList = $("#hospitalList").val();
		
		if (hospitalList == "0") {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.hosp"));
		}
		if (errorsList.length > 0) {
			displayErrorsOnPage(errorsList);
		} 
		$('#hospitalList')
		$('#hospitalList').prop("disabled", false);
	} else {
		$('#hospitalList').prop("disabled", true);
	}

}

function saveDeathRegApprovalData(obj){ 
	  var remark=$("#deathRegremark").val();
	  var approveOrReject=$("#deathRegstatus").val();
	  var errorList = [];
	  if(remark=="" || remark==undefined ){
		 errorList.push(getLocalMessage("TbDeathregDTO.label.rema"));
		}
	  if(approveOrReject=="" || approveOrReject==undefined){
		 errorList.push(getLocalMessage("TbDeathregDTO.label.rad"));
		}
	  if(errorList.length>0){
		 displayErrorsOnPage(errorList);
		}
	  else{
			//Save code
	  } 
}
function SearchDeathDraftData(obj) {

	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#deathRegDraftTable').DataTable();
		var url = "DeathRegistration.html?searchDeathDraft";
		var requestData = "&applnId=" + $("#applnId").val()
				+ "&drDod=" + $("#drDod").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		
		table.rows().remove().draw();
		if (returnData == 'Internal Server Error.') {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
		}
		
		var result = [];
		$
				.each(
						returnData,
						function(index) {
							var obj = returnData[index];
							let drDraftId = obj.drDraftId;
							let drDod = obj.drDod;
							let drDeceasedname=obj.drDeceasedname;
							let applnId = obj.applnId;
							let drSex = obj.drSex;
							let cpdRegUnit = obj.cpdDesc;

							
							if(drDeceasedname==null|| drDeceasedname==""){
								drDeceasedname="-";
							}
							if (applnId==""){
								applnId="-";
							}
							if (drSex==null || drSex==0 ){
								drSex="-";
							}
							if(cpdRegUnit==null|| cpdRegUnit==""){
								cpdRegUnit="-";
							}
							
							

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drDod) + '</div>',
											'<div class="text-center">'
													+ applnId + '</div>',
											'<div class="text-center">'
													+ drDeceasedname + '</div>',
											'<div class="text-center">'
													+ drSex + '</div>',
											'<div class="text-center">'
													+ cpdRegUnit + '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyDeath('
													+ drDraftId
													+ ","
													+applnId
													+ ',\'DeathRegistration.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);
						});
		table.rows.add(result);
		table.draw();
	} 
}
function validateDeathSearchForm(errorsList) {

	var applnId = $('#applnId').val();
	var drDod= $("#drDod").val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drdod = new Date(drDod.replace(pattern, '$3-$2-$1'));
	if (drDod != "" && (drdod > currDate)) {
		errorsList
				.push(getLocalMessage("Birth Date cannot be greater than Current Date"));
	}
	if (applnId == "" && drDod == "" ) {
		errorsList
				.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	} else if (applnId != "" || drDod != "") {
		// go for Search
	}
	
	return errorsList;
}
function modifyDeath(drDraftId, applicationId,formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
	"mode" : mode,
	"id" : drDraftId,
	"applicationId":applicationId
	 };
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	var ceFlag = $('input[name="tbDeathregDTO.ceFlag"]:checked').val();
	if(ceFlag=="W")
	{
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#ceId').prop("disabled", false);
	}
else if(ceFlag=="O")
	{
$('#ceName').prop("disabled", false);
$('#ceNameMar').prop("disabled", false);
$('#ceAddr').prop("disabled", false);
$('#ceAddrMar').prop("disabled", false);
$('#ceId').prop("disabled", true);
	}
	//$('#drDod').prop("disabled", true);
	
	var bptCode = $("#cpdDeathplaceType :selected").attr('code');
	if(bptCode=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
	//$('#hospitalList').prop("disabled", true);
	
	
}
function getAmountOnNoOfCopes(){
 	var errorsList= [];
 	var form_url = $("#frmDeathRegCorrForm").attr("action");
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
 		//errorsList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorsList);
 	}
}



function resetMemberMaster(resetBtn){
	
	//var applnId = $("#applnId").val();
	var apmApplicationId = $("#apmApplicationId").val();
	var drDraftId = $('#drDraftId').val();
	if((apmApplicationId != '') && drDraftId != ''){
	modifyDeath(drDraftId, apmApplicationId,"DeathRegistration.html", "editBND", "E");
	}else{
		resetForm(resetBtn);
	}
}

function getCemetery(element){
 	var errorsList= [];
  	var url = "DeathRegistration.html?getCemeteryDetails";
  	var requestData = "&ceId=" + $("#ceId").val();
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#ceName").val(returnData.ceName);
	 $("#ceNameMar").val(returnData.ceNameMar);
	 $("#ceAddr").val(returnData.ceAddr);
	 $("#ceAddrMar").val(returnData.ceAddrMar);
	
}

function toggleMedicalCert(obj) {
	if( $(obj).is(':checked') ) {
		$("#medCert").val("Y");
	} else {
		$("#medCert").val("N");
	}
}

function togglePregnAssoc(obj) {
	if( $(obj).is(':checked') ) {
		$("#mcPregnAssoc").val("Y");
	} else {
		$("#mcPregnAssoc").val("N");
	}
}

function toggleChewtb(obj) {
	if( $(obj).is(':checked') ) {
		$("#decChewtb").val("Y");
	} else {
		$("#decChewtb").val("N");
	}
}

function toggleAlcoholic(obj) {
	if( $(obj).is(':checked') ) {
		$("#decAlcoholic").val("Y");
	} else {
		$("#decAlcoholic").val("N");
	}
}
function toggleSmoker(obj) {
	if( $(obj).is(':checked') ) {
		$("#decSmoker").val("Y");
	} else {
		$("#decSmoker").val("N");
	}
}

function toggleChewarac(obj) {
	if( $(obj).is(':checked') ) {
		$("#decChewarac").val("Y");
	} else {
		$("#decChewarac").val("N");
	}
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
	var regno = $("#drRegno").val();
	var draftId = $("#drDraftId").val();
	if (regno != "") {
		var url = "DeathRegistration.html?checkRegnoDupl";
		var requestData = "drRegno=" + $("#drRegno").val() + "&drDraftId="
				+ $("#drDraftId").val();

		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');

		if (returnData == false) {
			errorList.push("This Registration No. is already registered");
			displayErrorsOnPage(errorList);
		}

	}
}

