$(document).ready(function() {
	var showUdyogDet =$("#showUdyogDet").val();
	if (showUdyogDet == 'N'){
	 $('.udyogDet').hide();
	}else
		$('.udyogDet').show();
	 var womenCentric = $("#womenCentric").val();
	 if (womenCentric === 'true'){
		 $('#isWomenCentric').prop('checked', true);
		
		 $('#isWomenCentric').val('Y');
	 }else{
		 $('#isWomenCentric').prop('checked', false);
		
		 $('#isWomenCentric').val('N');
	 }
    var orgShortNm = $("#orgshortName").val();
    if (orgShortNm == 'IA'){
    	$('.iaMand').show();
    	$('.cbboMand').hide();
    	$('.fpoMand').hide();
    }
    if(orgShortNm == 'CBBO'){
    	$('.cbboMand').show();
    	$('.iaMand').hide();
    	$('.fpoMand').hide();
    }
    if(orgShortNm == 'FPO'){
    	$('.fpoMand').show();
    	$('.iaMand').hide();
    	$('.cbboMand').hide();
    }
	 
		$("#fpoMastertables").dataTable({
			"oLanguage" : {
				"sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
		});
		
		
	 
	$("#dateIncorporation").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0,
		minDate : new Date(2020,3, 1),
		
		
	});
	$(".datepickerOfJoiningDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0
		
	});
	
	 
	/* var app = $("#appStatus").val();
			if (app == 'Y') {
				$('#approved').prop('checked', true);
			} else {
				('#approved').prop('checked', false);
			}*/
	

	
	$(".udyogAadharDatePicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange :"2020:2200",
		maxDate: 0
		
	});
	
	$(".appPendingDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0,
		minDate : new Date(2020,3, 1),
	
	});

	/*$('#isWomenCentric').on('click', function(){
	    var ifChecked = $(this).prop('checked');
	    if(ifChecked) {
	        $(this).val('Y')
	    } else {
	        $(this).val('N')
	    }
	});*/
	
    $('.showMand').hide();
    $('.chosen-select-no-results').chosen();

	$('#udyogAadharApplicable').on('click', function(){
	    var ifChecked = $(this).prop('checked');
	    if(ifChecked) {
	        $(this).val('Y')
	        $('.udyogDet').show();
	        // $('.udyogMand').show();
	    } else {
	        $(this).val('N')
	        $('.udyogDet').hide();
	       // $('.udyogMand').hide();
	    }
	});
	
	
	$('#approved').on('change', function(){
	    var ifChecked = $(this).prop('checked');
	    if(ifChecked) 
	        $(this).val('Y')
	     else 
	        $(this).val('N')
	});
	
    $('#sdb2').on('change', function() {
    			var sdb2 = $("#sdb2").val();
    			var request = {
    				"sdb2" : sdb2
    			};
    			var response = __doAjaxRequest('FPOMasterForm.html?getStateInfoByDistId', 'post', request,
    					false, 'json');
    			if (response.length != 0) {
    				$('#stateCategory').val(response.areaTypeValue);
    				$('#region').val(response.region);
    				$('#isAspirationalDist').val(response.aspirationallDist);
    				$('#isTribalDist').val(response.tirbalDist);
    				$('#odop').val(response.odopValue);
    			}
    });
    
    $('#cbboId').on('change', function() {
    	var cbboId = $("#cbboId").val();
    	var request = {
    		"cbboId" : cbboId
    	};
    	var response = __doAjaxRequest('FPOMasterForm.html?getTypeOFPromotinAgency', 'post', request,
    			false, 'json');
    	if (response == 0) {
    		$('#typeofPromAgen').attr("disabled", false);
    	}else{
    		$('#typeofPromAgen').val(response);
    	}
    });
    
  /*  $('#sdb3').on('change', function() {
    	var errorList = [];
    	var sdb3 =$("#sdb3").val();
    	var request = {
    			"sdb3" : sdb3
    		};
    		var response = __doAjaxRequest('FPOMasterForm.html?checkSpecialCateExist', 'post', request,
    				false, 'json');
    		if(response == true){
    			errorList.push(getLocalMessage("sfac.fpo.validation.spc.allocation"));
    			displayErrorsOnPage(errorList);
    		}
    });
    */
    
	$('.alpaSpecial').keypress(function(e) {
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		if (regex.test(str)) {
			return true;
		}
		e.preventDefault();
		return false;
	});


});




$('#cbboId').on('change', function() {
	var cbboId = $("#cbboId").val();
	var request = {
		"cbboId" : cbboId
	};
	var response = __doAjaxRequest('FPOMasterForm.html?getTypeOFPromotinAgency', 'post', request,
			false, 'json');
	if (response == 0) {
		$('#typeofPromAgen').attr("disabled", false);
	}else{
		$('#typeofPromAgen').val(response);
	}
});

function disbledOtherCropDet(obj,id) {
	var type = $("#cropType" + id).find("option:selected").attr('code');
	if (type == 'HNY'){
		$('#cropName'+ id).attr("disabled", true);
		$('#priSecCrop' + id).attr("disabled", true);
		$('#approvedByDmc' + id).attr("disabled", true);
		
	}else{
			$('#cropName'+ id).attr("disabled", false);
			$('#priSecCrop' + id).attr("disabled", false);
			$('#approvedByDmc' + id).attr("disabled", false);
		}
}



function addCropDetailsRow(obj,id) {
	var errorList = [];
	var type = $("#cropType" + id).find("option:selected").attr('code');
	if (type !='HNY'){
	 errorList = validateCropDetailsTable(errorList); 
	}
	if (errorList.length == 0) {
		var content = $('#cropDetailsTable tr').last().clone();
		$('#cropDetailsTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		reorderCropDetailsTable();
		//content.find("select:eq(0), select:eq(1), select:eq(2)").chosen().trigger("chosen:updated");
	} else {
		displayErrorsOnPage(errorList);
	}
}
function removeCropDetailsRow(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#cropDetailsTable tr').length;
	if ($("#cropDetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reorderCropDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}
function reorderCropDetailsTable() {
	$("#cropDetailsTable tbody tr").each(function(i) {
		// Id
		$(this).find("input:text:eq(0)").attr("id", "sqNo" + (i));
		$(this).find('select:eq(0)').attr('id','cropSeason' + i);
		$(this).find('select:eq(1)').attr('id','cropType' + i);
		$(this).find('input:text:eq(1)').attr('id','cropName' + i);
		$(this).find('select:eq(2)').attr('id','priSecCrop' + i);
		$(this).find('select:eq(3)').attr('id','approvedByDmc' + i);

		
		$(this).find("select:eq(0)").attr("name", "dto.fpoMasterDetailDto[" + i + "].cropSeason");
		$(this).find("select:eq(1)").attr("name", "dto.fpoMasterDetailDto[" + i + "].cropType");
		$(this).find("input:text:eq(1)").attr("name", "dto.fpoMasterDetailDto[" + i + "].cropName");
		$(this).find("select:eq(2)").attr("name", "dto.fpoMasterDetailDto[" + i + "].priSecCrop");
		$(this).find("select:eq(3)").attr("name", "dto.fpoMasterDetailDto[" + i	+ "].approvedByDmc");
		$("#sqNo" + i).val(i + 1);
		
	});
}

function saveFPOMasterForm(obj) {
	
	var errorList = [];
	 var fpoAge =$("#fpoAge").val();
	errorList = validateFPOMasterForm(errorList);
   // errorList = errorList.concat(validateCropDetailsTable());
    if (fpoAge >= 180){
    errorList = errorList.concat(validateAdministrativeDet());
    }
	if (errorList.length == 0) {
		var cbboName = $("#cbboId").find("option:selected").attr('code');
		var IaName = $("#iaId").find("option:selected").attr('code');
		 $('#iaMasterName').val(IaName);
		 $('#cbboMasterName').val(cbboName);
		return saveOrUpdateForm(obj, "FPO Master Details Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateFPOMasterForm(errorList) {
	
	var iaId =$("#iaId").val();
	var regAct = $("#regAct").val();
	var sdb1 = $("#sdb1").val();
	var sdb2 = $("#sdb2").val();
	var sdb3 = $("#sdb3").val();
	var dmcApproval = $("#dmcApproval").val();
    var specialAllocation =$("#specialAllocation").val();
    var fpoName =$("#fpoName").val();
    var companyRegNo =$("#companyRegNo").val();
    var dateIncorporation =$("#dateIncorporation").val();
    var fpoAge =$("#fpoAge").val();
    var fpoOffAddr =$("#fpoOffAddr").val();
    var fpoTanNo =$("#fpoTanNo").val();
    var fpoPanNo =$("#fpoPanNo").val();
    var noShareMem =$("#noShareMem").val();
    var paidupCapital =$("#paidupCapital").val();
    //var totalEquityAmt =$("#totalEquityAmt").val();
    var baseLineSurvey =$("#baseLineSurvey").val();
    var typeofPromAgen =$("#typeofPromAgen").val();
    var udyogAadharApplicable =$("#udyogAadharApplicable").val();
    var udyogAadharNo =$("#udyogAadharNo").val();
    var udyogAadharDate =$("#udyogAadharDate").val();
    var officeAddress =$("#officeAddress").val();
    var officeVillageName =$("#officeVillageName").val();
    var fpoPostOffice =$("#fpoPostOffice").val();
    var officePinCode =$("#officePinCode").val();
    var speciCategorycheck = $('#speciCategorycheck').val();
    var gstin = $('#gstin').val();
    var appPendingDate = $('#appPendingDate').val();
    var dmcApprovalCode = $("#dmcApproval").find("option:selected").attr('code');
    var allocationCategory = $('#allocationCategory').val();
    var allocationSubCategory = $('#allocationSubCategory').val();
    var authorizeCapital = $('#authorizeCapital').val();
    var approvedByIa  = $('#approvedByIa').val();
    var approvedByCbbo  = $('#approvedByCbbo').val();
    var approvedByFpo  = $('#approvedByFpo').val();
    var orgshortName  = $('#orgshortName').val();
	var cat = $("#allocationCategory").find("option:selected").attr('code');
	var dupFpoName = $('#dupFpoName').val();
	var dupCompRegNo = $('#dupCompRegNo').val();
	var viewMode = $("#viewMode").val();
	var acknowledgementNumber = $("#acknowledgementNumber").val();
	
	if (iaId == "" || iaId == "0" || iaId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.ianame"));
	}

	if (dmcApproval == "" || dmcApproval == "0") {
		errorList.push(getLocalMessage("sfac.fpo.validation.dmcApproval"));
	}
	if (dmcApprovalCode == 'PND'
			&& (appPendingDate == "" || appPendingDate == undefined)) {
		errorList.push(getLocalMessage("sfac.validation.appPendingDate"));
	}
	
	if (fpoName == "" || fpoName == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoName"));
	}
	if (companyRegNo == "" || companyRegNo == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoRegNo"));
	}
	if (viewMode == 'A'){
	 if (dupFpoName === 'true') {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoName.exist"));
	  }
	 if (dupCompRegNo === 'true'){
		errorList.push(getLocalMessage("sfac.fpo.validation.compRegNO.exist"));
	  }
	}
	if (dateIncorporation == "" || dateIncorporation == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.dateIncorporation"));
	}
	if (fpoAge == "" || fpoAge == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoAge"));
	}
	
	if ((acknowledgementNumber == null || acknowledgementNumber == "") && (fpoTanNo == "" || fpoTanNo == undefined) &&(fpoPanNo == "" || fpoPanNo == undefined)){
		errorList.push(getLocalMessage("sfac.fpo.validation.acknowledgementNumber"));
	 }
	  
   if (acknowledgementNumber == null || acknowledgementNumber == ""){
	if (fpoTanNo == "" || fpoTanNo == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoTanNo"));
	}
	if($.trim($("#fpoTanNo").val()) != "") {
		var panCardNo = $.trim($("#fpoTanNo").val());
		var panPat = /^([a-zA-Z]{4})(\d{5})([a-zA-Z]{1})$/;
        var code = /([C,P,H,F,A,T,B,L,J,G])/;
        var code_chk = panCardNo.substring(3,4);
        if (!panPat.test(panCardNo)) {
        	errorList.push(getLocalMessage("sfac.fpo.notValid.tanNo"));
        }        
	}
	
	if (fpoPanNo == "" || fpoPanNo == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoPanNo"));
	}
	if($.trim($("#fpoPanNo").val()) != "") {
		var panCardNo = $.trim($("#fpoPanNo").val());
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
        var code = /([C,P,H,F,A,T,B,L,J,G])/;
        var code_chk = panCardNo.substring(3,4);
        if (!panPat.test(panCardNo)) {
        	errorList.push(getLocalMessage("sfac.fpo.notValid.panNo"));
        }        
	}
   }
	
	if (authorizeCapital == "" || authorizeCapital == undefined){
		errorList.push(getLocalMessage("sfac.fpo.validation.authorizeCapital"));
	}
	
	if (paidupCapital == "" || paidupCapital == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.paidupCapital"));
	}
	
	if ((paidupCapital != 0 || paidupCapital != "") && (authorizeCapital != 0 || authorizeCapital != "") && (Number(paidupCapital) > Number(authorizeCapital))) {
		errorList.push(getLocalMessage("capital.validation"));
	}
	 if (sdb1 == "" || sdb1 == "0") {
		errorList.push(getLocalMessage("sfac.validation.SDB1"));
	}

	if (sdb2 == "" || sdb2 == "0") {
		errorList.push(getLocalMessage("sfac.validation.SDB2"));
	}

	if (sdb3 == "" || sdb3 == "0") {
		errorList.push(getLocalMessage("sfac.validation.SDB3"));
	}
	
	 if (allocationCategory == "" || allocationCategory == "0" || allocationCategory == undefined){
		 errorList.push(getLocalMessage("sfac.validation.allocationCategory"));
	 }
	
	 if ((cat == 'SPC') && (allocationCategory != "" || allocationCategory != "0")
			&& (allocationSubCategory == "" || allocationSubCategory == "0")) {
		errorList.push(getLocalMessage("sfac.validation.alcTargetSubCategory"));
	}
	if (speciCategorycheck == true) {
		errorList.push(getLocalMessage("sfac.fpo.validation.spc.allocation"));
	}
	 
	 
	if (regAct == "0" || regAct == "") {
		errorList.push(getLocalMessage("sfac.fpo.validation.regAct"));
	}
	
	if (typeofPromAgen == "" || typeofPromAgen == undefined || typeofPromAgen == "0") {
		errorList.push(getLocalMessage("sfac.fpo.validation.typeofPromAgen"));
	}
	if (officeAddress == "" || officeAddress == undefined || officeAddress == "0") {
		errorList.push(getLocalMessage("sfac.fpo.validation.officeAddress"));
	}
 
    if (officePinCode == "" || officePinCode == undefined || officePinCode == "0") {
		errorList.push(getLocalMessage("sfac.fpo.validation.officePinCode"));
	}
	
	/*
	 * if (specialAllocation == "0" || specialAllocation == "" ||
	 * specialAllocation == undefined) {
	 * errorList.push(getLocalMessage("sfac.fpo.validation.specialAllocation")); }
	 */

	
	
/*	if (fpoOffAddr == "" || fpoOffAddr == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.fpoOffAddr"));
	}*/

/*	if (gstin == "" || gstin == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.gstin"));
	}*/
	
	
	
	
	if($.trim($("#gstin").val()) != "") {
    	var gstinNo = $.trim($("#gstin").val());
        var reggst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([a-zA-Z]){1}([0-9]){1}?$/;
        if(!reggst.test(gstinNo) && gstinNo!=''){
                errorList.push(getLocalMessage("sfac.fpo.notValid.gstin"));
        }
	}
	
	/*if (totalEquityAmt == "" || totalEquityAmt == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.totalEquityAmt"));
	}*/
	
	
	
	
	if (baseLineSurvey == "" || baseLineSurvey == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.validation.baseLineSurvey"));
	}

	
	 if(udyogAadharApplicable == 'Y'){
		 if (udyogAadharNo == "" || udyogAadharNo == undefined || udyogAadharNo == "0") {
				errorList.push(getLocalMessage("sfac.fpo.validation.udyogAadharNo"));
			}
		 if (udyogAadharDate == "" || udyogAadharDate == undefined || udyogAadharDate == "0") {
				errorList.push(getLocalMessage("sfac.fpo.validation.udyogAadharDate"));
			}
	 }
	 
	 
	 
	 /* if (officeVillageName == "" || officeVillageName == undefined || officeVillageName == "0") {
			errorList.push(getLocalMessage("sfac.fpo.validation.officeVillageName"));
		}
	 
	 if (fpoPostOffice == "" || fpoPostOffice == undefined || fpoPostOffice == "0") {
			errorList.push(getLocalMessage("sfac.fpo.validation.fpoPostOffice"));
		}
	 
	*/
	
	
	
	/* if (orgshortName == 'IA'){
	 if (approvedByIa == "" || approvedByIa == undefined || approvedByIa == "0") {
			errorList.push(getLocalMessage("sfac.fpo.validation.approvedByIa"));
		}
	  }
	 else if(orgshortName == 'CBBO'){
	 if (approvedByCbbo == "" || approvedByCbbo == undefined || approvedByCbbo == "0") {
			errorList.push(getLocalMessage("sfac.fpo.validation.approvedByCbbo"));
		}
	 }
	 else if(orgshortName == 'FPO'){
	 if (approvedByFpo == "" || approvedByFpo == undefined || approvedByFpo == "0") {
			errorList.push(getLocalMessage("sfac.fpo.validation.approvedByFpo"));
		}
	 }*/
	return errorList;
}

function validateCropDetailsTable() {
	var errorList = [];
	var rowCount = $('#cropDetailsTable tr').length;	


	if (errorList == 0)
		$("#cropDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				var cropSeason = $("#cropSeason" + i).val();
				var cropType = $("#cropType" + i).val();
				var cropName = $("#cropName" + i).val();
				var priSecCrop = $("#priSecCrop" + i).val();
				var approvedByDmc = $("#approvedByDmc" + i).val();
				var type = $("#cropType" + i).find("option:selected").attr('code');  
				/*
				 * var odop = $("#odop" + i).val(); var specialCrop =
				 * $("#specialCrop" + i).val();
				 */
				var constant = 1;
       }
        else{
        	var cropSeason = $("#cropSeason" + i).val();
			var cropType = $("#cropType" + i).val();
			var cropName = $("#cropName" + i).val();
			var priSecCrop = $("#priSecCrop" + i).val();
			var approvedByDmc = $("#approvedByDmc" + i).val();
		
			var type = $("#cropType" + i).find("option:selected").attr('code'); 
/*
 * var odop = $("#odop" + i).val(); var specialCrop = $("#specialCrop" +
 * i).val();
 */
	        var constant = i+1;
     }
				if (cropType == '0' || cropType == undefined || cropType == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.cropType") +" " + (i + 1));
				}
				if (type !='HNY'){
				if (cropSeason == '0' || cropSeason == undefined || cropSeason == "") {
						errorList.push(getLocalMessage("sfac.fpo.validation.cropSeason") +" " + (i + 1));
				}
				if (cropName == undefined || cropName == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.cropName") + " " + (i + 1));
				}
				if (priSecCrop == '0' || priSecCrop == undefined || priSecCrop == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.priSecCrop") +" " + (i + 1));
				}
				
				if (approvedByDmc == '0' || approvedByDmc == undefined || approvedByDmc == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.approvedByDmc") +" " + (i + 1));
				}
				}
		});
	
	
	return errorList;
}

function getBankCode(id) {
	var actionUrl = 'FPOMasterForm.html?bankCode';
	var selectedValue = $('#bankName'+ id)./* find(":selected"). */val();
	var data = 'cbBankCode=' + selectedValue;

	$('#ifscCode'+id).html('');
	$('#ifscCode'+id).append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	

	var json = __doAjaxRequest(actionUrl, 'POST', data, false, 'json');
	
	$.each(json, function(index, value) {
		if (value.bankId != null) {
			$("#ifscCode"+id).append(
					$("<option></option>").attr("value", value.bankId).text(
							value.ifsc));
		}
	});
	$("#ifscCode"+id).trigger("chosen:updated");
	
	/*var code = result.split('-');
	$('#ifscCode'+ id).val(code[0]);
	$('#branchName'+ id).val(code[1]);*/

}

function getBankBranch(id) {
	var actionUrl = 'FPOMasterForm.html?getBranch';
	var selectedValue = $('#ifscCode'+ id)./* find(":selected"). */val();
	var data = 'cbBankCode=' + selectedValue;
	var result = __doAjaxRequest(actionUrl, 'POST', data, false, 'json');
	
	
	$('#branchName'+ id).val(result);

}

function getAgeOfFPO(obj) {
	var actionUrl = 'FPOMasterForm.html?getAgeOfFPO';
	var dateIncorporation = $("#dateIncorporation").val();
	var data = 'dateIncorporation=' + dateIncorporation;
	var result = __doAjaxRequest(actionUrl, 'POST', data, false, 'json');
	$('#fpoAge').val(result);
	if (result != null && result >= 180)
       $('.showMand').show();
	else
		$('.showMand').hide();
}

function getAppPendingField() {
	 var dmcApproval = $("#dmcApproval").find("option:selected").attr('code');
	if (dmcApproval=='PND'){
		$('.showMand').show();
		$('#appPendingDate').attr("disabled", false);
	}
	else{
		$('.showMand').hide();
		$('#appPendingDate').attr("disabled", true);
	}
}


$('#sdb2').on('change', function() {
			var sdb2 = $("#sdb2").val();
			var request = {
				"sdb2" : sdb2
			};
			var response = __doAjaxRequest('FPOMasterForm.html?getStateInfoByDistId', 'post', request,
					false, 'json');
			if (response.length != 0) {
				$('#stateCategory').val(response.areaTypeValue);
				$('#region').val(response.region);
				$('#isAspirationalDist').val(response.aspirationallDist);
				$('#isTribalDist').val(response.tirbalDist);
				$('#odop').val(response.odopValue);
			}
});

function checkSpecialCateExist() {
	var errorList = [];
	var sdb3 =$("#sdb3").val();
	var request = {
			"sdb3" : sdb3
		};
		var response = __doAjaxRequest('FPOMasterForm.html?checkSpecialCateExist', 'post', request,
				false, 'json');
		if (response == true){
			$('#speciCategorycheck').val(response);
			errorList.push(getLocalMessage("sfac.fpo.validation.spc.allocation"));
			displayErrorsOnPage(errorList);
		}
}

function getIaALlocationYear () {
	var iaId = $("#iaId").val();
	var request = {
			"iaId" : iaId
		};
		var response = __doAjaxRequest('FPOMasterForm.html?getIaALlocationYear', 'post', request,
				false, 'json');
		if (response == '0'){
			$('#iaAlcYear').val("0").trigger("chosen:updated");
		}
		else if (response != ""){
			$('#iaAlcYear').val(response).trigger("chosen:updated");
		}
		/*var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('FPOMasterForm.html?getBlockDetByIa', request, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();*/
}



function addAdminButton(obj) {
	var errorList = [];
	
	if (errorList.length == 0) {
		var content = $('#adminDetailsTable tr').last().clone();
		$('#adminDetailsTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		reorderAdminDetailsTable();
		//content.find("select:eq(0), select:eq(1), select:eq(2)").chosen().trigger("chosen:updated");
		$(".datepickerOfJoiningDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0
			
		});
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteAdminDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#adminDetailsTable tr').length;
	if ($("#adminDetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reorderAdminDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reorderAdminDetailsTable() {
	$("#adminDetailsTable tbody tr").each(function(i) {
		// Id
		$(".datepickerOfJoiningDate").removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("select:eq(0)").attr("id", 'dsgId' + i);
		$(this).find("select:eq(1)").attr("id", 'titleId' + i);
	
		$(this).find('input:text:eq(1)').attr('id','name' + i);
		$(this).find('input:text:eq(2)').attr('id','contactNo' + i);
		$(this).find('input:text:eq(3)').attr('id','dateOfJoining' + i);
		$(this).find('input:text:eq(4)').attr('id','emailId' + i);
		$(this).find('select:eq(2)').attr('id','nameOfBoard' + i);
		$(this).find('input:text:eq(5)').attr('id','din' + i);
		
		
		$(this).find("select:eq(0)").attr("name",  "dto.fpoAdministrativeDto[" + i + "].dsgId");
		$(this).find("select:eq(1)").attr("name",  "dto.fpoAdministrativeDto[" + i + "].titleId");
		$(this).find("input:text:eq(1)").attr("name", "dto.fpoAdministrativeDto[" + i + "].name");
		$(this).find("input:text:eq(2)").attr("name", "dto.fpoAdministrativeDto[" + i + "].contactNo");
		$(this).find("input:text:eq(3)").attr("name",	"dto.fpoAdministrativeDto[" + i	+ "].dateOfJoining");
		$(this).find("input:text:eq(4)").attr("name",	"dto.fpoAdministrativeDto[" + i	+ "].emailId");
		$(this).find("select:eq(2)").attr("name",	"dto.fpoAdministrativeDto[" + i	+ "].nameOfBoard").attr("onchange","enableDinOnYes(" + i + ")");
		$(this).find("input:text:eq(5)").attr("name",	"dto.fpoAdministrativeDto[" + i	+ "].din");
		$("#sNo" + i).val(i + 1);
		$(".datepickerOfJoiningDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0
			
		});
	});
}

function addBankButton(obj) {
	var errorList = [];
    errorList = validateBankDetailsTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#bankDetailsTable tr').last().clone();
		$('#bankDetailsTable tr').last().after(content);
		// content.find("select").val('0');
	    content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		
        content.find('[id^="bankName"]').chosen().trigger("chosen:updated");
        content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeBankDetailsTable();
	
		
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteBankDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#bankDetailsTable tr').length;
	if ($("#bankDetailsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeBankDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeBankDetailsTable() {
	$("#bankDetailsTable tbody tr").each(function(i) {
		// Id
		

		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('select:eq(0)').attr('id','bankName' + i);
		
		$(this).find('select:eq(1)').attr('id','ifscCode' + i);
		$(this).find('input:text:eq(3)').attr('id','branchName' + i);
		$(this).find('input:text:eq(4)').attr('id','accountNo' + i);
		
		$(this).find('select:eq(0)').attr("name", "dto.fpoBankDetailDto[" + i + "].bankName").attr("onchange","getBankCode(" + i + ")");
		$(this).find("input:text:eq(0)").attr("name", "dto.fpoBankDetailDto[" + i + "].sNo");
		
		$(this).find("select:eq(1)").attr("name", "dto.fpoBankDetailDto[" + i + "].ifscCode").attr("onchange","getBankBranch(" + i + ")");
		$(this).find("input:text:eq(3)").attr("name", "dto.fpoBankDetailDto[" + i + "].branchName");
		$(this).find("input:text:eq(4)").attr("name", "dto.fpoBankDetailDto[" + i + "].accountNo");
		
		$("#sNo" + i).val(i + 1);
		
		
		$('#bankDetails').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	
	});
}


function validateBankDetailsTable() {
	var errorList = [];
	var rowCount = $('#bankDetailsTable tr').length;	


	if (errorList == 0)
		$("#bankDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var bankName = $("#bankName" + i).val();
				var accountNo = $("#accountNo" + i).val();
				var ifscCode = $("#ifscCode" + i).val();
				var branchName = $("#branchName" + i).val();
				var constant = 1;
       }
        else{
        	var bankName = $("#bankName" + i).val();
			var accountNo = $("#accountNo" + i).val();
			var ifscCode = $("#ifscCode" + i).val();
			var branchName = $("#branchName" + i).val();
	        var constant = i+1;
     }
		
				if (bankName == undefined || bankName == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.bankName") + " " + (i + 1));
				}
				if (accountNo == '0' || accountNo == undefined || accountNo == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.accountNo") +" " + (i + 1));
				}
				
				if (ifscCode == '0' || ifscCode == undefined || ifscCode == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.ifscCode") +" " + (i + 1));
				}
				
				if (branchName == '0' || branchName == undefined || branchName == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.branch") +" " + (i + 1));
				}
		});
	
	return errorList;
}


function validateAdministrativeDet() {
	var errorList = [];
	var rowCount = $('#adminDetailsTable tr').length;	


	if (errorList == 0)
		$("#adminDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var dsgId = $("#dsgId" + i).val();
				var titleId = $("#titleId" + i).val();
				var name = $("#name" + i).val();
				var contactNo = $("#contactNo" + i).val();
				var dateOfJoining = $("#dateOfJoining" + i).val();
				var emailId = $("#emailId" + i).val();
				var nameOfBoard = $("#nameOfBoard" + i).find("option:selected").attr('code');
				var din = $("#din" + i).val();

				var constant = 1;
       }
        else{
    		var dsgId = $("#dsgId" + i).val();
			var titleId = $("#titleId" + i).val();
			var name = $("#name" + i).val();
			var contactNo = $("#contactNo" + i).val();
			var dateOfJoining = $("#dateOfJoining" + i).val();
			var emailId = $("#emailId" + i).val();
			var nameOfBoard = $("#nameOfBoard" + i).find("option:selected").attr('code');
			var din = $("#din" + i).val();
	        var constant = i+1;
     }
		
			if (dsgId == '0' || dsgId == undefined || dsgId == "") {
				errorList.push(getLocalMessage("sfac.validation.dsgId") +" " + (i + 1));
			}
			
			if (titleId == '0' || titleId == undefined || titleId == "") {
				errorList.push(getLocalMessage("sfac.validation.titleId") +" " + (i + 1));
			}
			
			if (name == undefined || name == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.Name") + " " + (i + 1));
			}
			if (contactNo == undefined || contactNo == "") {
				errorList.push(getLocalMessage("sfac.fpo.validation.contactNo") + " " + (i + 1));
		    }
				
			if (dateOfJoining == '0' || dateOfJoining == undefined || dateOfJoining == "") {
					errorList.push(getLocalMessage("sfac.fpo.validation.fpoDoj") +" " + (i + 1));
			}
			if (emailId == undefined || emailId == "") {
				errorList.push(getLocalMessage("sfac.validation.iaEmailId") + " " + (i + 1));
			}else {
				if (emailId != "") {
					var emailRegex = new RegExp(
							/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
					var valid = emailRegex.test(emailId);
					if (!valid) {
						errorList.push(getLocalMessage("sfac.valid.iaEmailId")+ " "+ (i + 1));
					}
				}
			}
			if (nameOfBoard =='Y' && (din == "" || din == undefined)){
				errorList.push(getLocalMessage("sfac.fpo.validate.din")+ " "+ (i + 1));
			}
		});
	
	return errorList;
}

function modifyCase(fpoId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	
	var requestData = {
		"mode" : mode,
		"fpoId" : fpoId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	var viewMode = $("#viewMode").val();
	var cat = $("#allocationCategory").find("option:selected").attr('code');
	if (cat == 'BLW' ||  mode == 'V')
		$('#allocationSubCategory').attr("disabled", true);
	
	$(".appPendingDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0,
		minDate : new Date(2020,3, 1),
	
	});
	 
	$("#dateIncorporation").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0,
		minDate : new Date(2020,3, 1),
		
		
	});
	$(".datepickerOfJoiningDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0
		
	});
	prepareTags();
}

function searchForm(obj) {
	var errorList = [];
	var fpoId = $("#fpoId").val();
	var fpoRegNo = $("#fpoRegNo").val();
	var fpoRegNo = $("#fpoRegNo").val();
	var iaId = $("#iaId").val();
	var divName = '.content-page';
	if ((fpoId == "0" || fpoId == undefined || fpoId == "") && (fpoRegNo == "0" || fpoRegNo == undefined || fpoRegNo == "") && (iaId == "0" || iaId == undefined || iaId == "")) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"fpoId" : fpoId,
			"fpoRegNo" : fpoRegNo,
			"iaId" :iaId
		};
		var ajaxResponse = doAjaxLoading('FPOMasterForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FPOMasterForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

$('#allocationCategory').on('change', function() {
	
	var requestData = {
			"allocationCategory" : $("#allocationCategory").val()
		};
		var URL = 'FPOMasterForm.html?getAlcSubCatList';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		$('#allocationSubCategory').html('');
		$('#allocationSubCategory').append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#allocationSubCategory').append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.lookUpDesc)));
		});
		$('#allocationSubCategory').trigger("chosen:updated");
});


$('#approved').on('change', function(){
    var ifChecked = $(this).prop('checked');
    if(ifChecked) 
        $(this).val('Y')
     else 
        $(this).val('N')
});

function enableDinOnYes(id){
	var nameOfBoard = $("#nameOfBoard" + id).find("option:selected").attr('code');

	if (nameOfBoard == 'Y'){
		$('#din'+ id).attr("disabled", false);
	}
	else{
		$('#din'+ id).attr("disabled", true);
		$('#din'+ id).val('');
	}
}

function disableSubCateForBLock(){
	var cat = $("#allocationCategory").find("option:selected").attr('code');
	if (cat == 'BLW')
		$('#allocationSubCategory').attr("disabled", true);
	else
		$('#allocationSubCategory').removeAttr('disabled');
	
}
function saveApprovalData(element) {
	var errorList = [];
	if ($("#remark").val() == "") {
		errorList.push(getLocalMessage("sfac.reamrk.validate"));
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				"Fpo Master Details Saved Successfully!", 'AdminHome.html','saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}


function getDistrictList(obj) {
	var errorList = [];
	var iaId =$("#iaId").val();
	
	if (iaId == "" || iaId == "0" || iaId == undefined) {
		$('#sdb1').val("0").trigger("chosen:updated");
		errorList.push(getLocalMessage("sfac.validation.ianame"));
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0){
	var requestData = {
		"sdb1" : $("#sdb1").val()
	};
	var URL = 'FPOMasterForm.html?getDistrictList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb2').html('');
	$('#sdb2').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb2').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb2').trigger("chosen:updated");
}
}

function getBlockList(obj) {
	var sdb2 = $("#sdb2").val();
	
	var requestData = {
		"sdb2" : sdb2,
	};
	var URL = 'FPOMasterForm.html?getBlockList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb3').html('');
	$('#sdb3').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb3').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb3').trigger("chosen:updated");
}

$('#fpoName').on('blur', function(){
	var errorList = [];
	var fpoName = $("#fpoName").val();
	var viewMode = $("#viewMode").val();
	if (fpoName != '' && viewMode == 'A'){
	var request = {
			"fpoName" : fpoName
		};
		var response = __doAjaxRequest('FPOMasterForm.html?checkFpoNameExist', 'post', request,
				false, 'json');
		if (response == true){
			$('#dupFpoName').val(response);
			errorList.push(getLocalMessage("sfac.fpo.validation.fpoName.exist"));
			displayErrorsOnPage(errorList);
		}
	}
});

$('#companyRegNo').on('blur', function(){
	var errorList = [];
	var companyRegNo = $("#companyRegNo").val();
	var viewMode = $("#viewMode").val();
	if (companyRegNo != '' && viewMode == 'A'){
	var request = {
			"companyRegNo" : companyRegNo
		};
		var response = __doAjaxRequest('FPOMasterForm.html?checkComRegNoExist', 'post', request,
				false, 'json');
		if (response == true){
			$('#dupCompRegNo').val(response);
			errorList.push(getLocalMessage("sfac.fpo.validation.compRegNO.exist"));
			displayErrorsOnPage(errorList);
		}
	}
});

$(".reset").click(function() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'FPOMasterForm.html');
	$("#postMethodForm").submit();

});