$(document).ready(function() {
	prepareDateTag();
	$("#deathCorrDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
   });
	//D#127333 Translator	 
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
	$('#drDeceasedname').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drDeceasedname").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drMarDeceasedname',event,'');
		}else{
			$("#drMarDeceasedname").val('');
		}
	});
	$('#drRelativeName').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drRelativeName").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drMarRelativeName',event,'');
		}else{
			$("#drMarRelativeName").val('');
		}
	});
	$('#drMotherName').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drMotherName").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drMarMotherName',event,'');
		}else{
			$("#drMarMotherName").val('');
		}
	});
	$('#drDeceasedaddr').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drDeceasedaddr").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drMarDeceasedaddr',event,'');
		}else{
			$("#drMarDeceasedaddr").val('');
		}
	});
	$('#drDcaddrAtdeath').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drDcaddrAtdeath").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drDcaddrAtdeathMar',event,'');
		}else{
			$("#drDcaddrAtdeathMar").val('');
		}
	});
	$('#drDeathplace').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drDeathplace").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drMarDeathplace',event,'');
		}else{
			$("#drMarDeathplace").val('');
		}
	});
	$('#drDeathaddr').bind('click keyup', function(event) {
		var no_spl_char;
		no_spl_char = $("#drDeathaddr").val().trim();
		if(no_spl_char!=''){
			commonlanguageTranslate(no_spl_char,'drMarDeathaddr',event,'');
		}else{
			$("#drMarDeathaddr").val('');
		}
	});
	
		
	}
});

$("#drDod").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});

function correctionsCategory(element) {
	var returndata;
	var corrCategory = $.trim($("#corrCategory").val());
	var listArray = new Array();
	var corrCategoryArr = [];
	corrCategoryArr = corrCategory.split(",");

	var list = document.getElementById('corrCategory');
	for (i = 0; i < list.options.length; i++) {
		listArray[i] = list.options[i].value;
	}
	returndata = getDeathDataById(element);
	$.each(listArray, function(index) {
		
		if (corrCategoryArr.includes(listArray[index])) {
			$("." + listArray[index]).attr("disabled", false);
		} else {
			$("." + listArray[index]).attr("disabled", true);
			
			if((!(corrCategoryArr.includes('DRDOD')))&&(listArray.includes('DRDOD'))){
				  $('#drDod').val(getDateFormat(returndata.drDod));
		       }
			   if((!(corrCategoryArr.includes('DRSEX')))&&(listArray.includes('DRSEX'))){
				   $('#drSex').val(returndata.drSex);
		       }
			   if((!(corrCategoryArr.includes('DRDECESEAGE')))&&(listArray.includes('DRDECESEAGE'))){
				   $('#age').val(returndata.drDeceasedage);
				   $('#ageperiod').val(returndata.cpdAgeperiodId);
		      }
			if((!(corrCategoryArr.includes('DRDECEASEDNAME')))&&(listArray.includes('DRDECEASEDNAME'))){
				   $('#drDeceasedname').val(returndata.drDeceasedname);
				   $('#drMarDeceasedname').val(returndata.drMarDeceasedname);
		      }
			if((!(corrCategoryArr.includes('PARENTNAME')))&&(listArray.includes('PARENTNAME'))){
				   $('#drRelativeName').val(returndata.drRelativeName);
				   $('#drMarRelativeName').val(returndata.drMarRelativeName);
				   $('#drMotherName').val(returndata.drMotherName);
				   $('#drMarMotherName').val(returndata.drMarMotherName);
		      }
			if((!(corrCategoryArr.includes('ADDRESS')))&&(listArray.includes('ADDRESS'))){
				   $('#drDeceasedaddr').val(returndata.drDeceasedaddr);
				   $('#drMarDeceasedaddr').val(returndata.drMarDeceasedaddr);
				   $('#drDcaddrAtdeath').val(returndata.drMotherName);
				   $('#drDcaddrAtdeath').val(returndata.drMarMotherName);
		      }
			if((!(corrCategoryArr.includes('DRDEATHPLACE')))&&(listArray.includes('DRDEATHPLACE'))){
				   $('#drDeathplace').val(returndata.drDeathplace);
				   $('#drMarDeathplace').val(returndata.drMarDeathplace);
		      }
			if((!(corrCategoryArr.includes('DRDEATHADDR')))&&(listArray.includes('DRDEATHADDR'))){
				   $('#drDeathaddr').val(returndata.drDeathaddr);
				   $('#drMarDeathaddr').val(returndata.drMarDeathaddr);
		      }
			if((!(corrCategoryArr.includes('CPDRELIGIONID')))&&(listArray.includes('CPDRELIGIONID'))){
				   $('#cpdReligionId').val(returndata.cpdReligionId);
		      }
		}

	});

}

function SearchDeathCorrectionData(element) {
	
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#deathCorrDataTable').DataTable();
		var url = "DeathRegistrationCorrection.html?searchDeathDataForCorr";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		/*var requestData = "drCertNo=" + $('#drCertNo').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applnId=" + $("#applnId").val()
				+ "&drDod=" + $("#drDod").val()+"&drDeceasedname=" + $("#drDeceasedname").val();*/
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		
		table.rows().remove().draw();
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			errorsList.push(getLocalMessage("BirthDeath.NoRecord.Error"));
			displayErrorsOnPage(errorsList);
		}else{
			var n=0;
		
		var result = [];
		$
				.each(
						returnData,
						function(index) {
							var obj = returnData[index];
							if(obj.drStatus == "Y"){
								n++;
							let drId = obj.drId;
							let drDod = obj.drDod;
							let drDeceasedname=obj.drDeceasedname;
							let drRegno = obj.drRegno;
							let drRegdate = obj.drRegdate;
							let drSex = obj.drSex;
							let cpdRegUnit = obj.cpdDesc;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drDod) + '</div>',
											'<div class="text-center">'
													+ drRegno + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drRegdate) + '</div>',
											'<div class="text-center">'
													+ drDeceasedname + '</div>',
											'<div class="text-center">'
													+ drSex + '</div>',
											'<div class="text-center">'
													+ cpdRegUnit + '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-blue-3 btn-sm margin-right-5"  onclick="modifyDeath(\''
													+ drId
													+ '\',\'DeathRegistrationCorrection.html\',\'editBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyDeath(\''
													+ drId
													+ '\',\'DeathRegistrationCorrection.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);
							}
							});
		table.rows.add(result);
		table.draw();
		if(n==0){
			errorsList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
			displayErrorsOnPage(errorsList);
			
			}
	} 
 }
	},2);
}

function bndRegAcknow(element) {
	var URL = 'DeathRegistrationCorrection.html?printBndAcknowledgement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	if(returnData!=null && returnData!=""){
	var appId = $($.parseHTML(returnData)).find("#applicationId").html();
	var title = appId;
	prepareTags();
	var printWindow = window.open('', '_blank');

	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
}


function validateDeathSearchForm(errorsList) {
	var drCertNo = $('#drCertNo').val();
	//var applnId = $('#applnId').val();
	var year = $("#year").val();
	var drRegno = $("#drRegno").val();
	var drDod= $("#drDod").val();
	var drDeceasedname=$("#drDeceasedname").val();
	var cpdDeathcauseId=$("#cpdDeathcauseId").val();
	var drSex=$("#drSex").val();
	
	// validate the fields
	validatedates(errorsList);
	if (year == "" && drRegno == "" && drDod == "" && drDeceasedname == ""
			&& (cpdDeathcauseId == "0"||cpdDeathcauseId=="") && (drSex == "0" || drSex == "")) {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	} else {
       //go for search
	} 
	return errorsList;
}

function validatedates(errorList) {
	$('.error-div').hide();
	var name = "Issuance certificate";
	var trTenderDate;
	if (($("#year").val()) != undefined) {
		trTenderDate = $("#year").val();
	}
	if (trTenderDate != null && trTenderDate != "") {
		var yy = parseInt(trTenderDate);
		var min_date = 1940;
		var max_date = (new Date()).getFullYear();
		if (yy < min_date || yy > (new Date()).getFullYear()) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		} else if (yy < min_date) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		}
	}
	return errorList;
}

function modifyDeath(drId, formUrl, actionParam, mode) {
	var errorsList = [];
	var divName = '.content-page';
	var requestData = {
	"mode" : mode,
	"id" : drId
	 };
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	if (ajaxResponse == 'Internal Server Error.') {
		errorsList.push("This application is in progress")
		$('#frmDeathRegistrationCorrection').trigger("reset");
		displayErrorsOnPage(errorsList);
	}
	else{
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#cpdDeathplaceType').prop("disabled", true);
	$('#hospitalList').prop("disabled", true);
	$('#drDod').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	$('#age').prop("disabled", true);
	$('#ageperiod').prop("disabled", true);
	}
}


function getChecklistAndCharges(element) {
	
	var errorList = [];
	var corrCategory = $.trim($("#corrCategory").val());
	var age = $("#age").val();
	var kdmcEnv = $("#kdmcEnv").val();
	var cpdRegUnit = $("#cpdRegUnit").val();
	var cpdRegUnitCode = $("#cpdRegUnit").find("option:selected").attr('code');
	if (kdmcEnv =='Y' && (cpdRegUnit == "0" || cpdRegUnitCode=="NS")) {
		errorList.push(getLocalMessage("bnd.regUnit.validation"));
	}
	var value = $('option:selected', $("#ageperiod")).attr('code');
	if (corrCategory.includes("DRDECESEAGE")) {
		if (value == "DO") {
			if (age > 31)
				errorList
						.push(getLocalMessage("bnd.agePeriod.days"));
		} else if (value == "MO") {
			if (age > 11 || age < 2)
				errorList
						.push(getLocalMessage("bnd.agePeriod.months"));
		} else if (value == "YR") {
			if (age > 150 || age < 1)
				errorList
						.push(getLocalMessage("bnd.agePeriod.years"));
		}
	}
	if(corrCategory == '' || corrCategory == 'undefined'){
		errorList.push(getLocalMessage("birth.lable.correction"));
	}
	var drDod = $("#drDod").val();
	var amount=$("#amount").val();
	var currDate= new Date();
	var noOfCopies=$("#numberOfCopies").val();
	var occupation=$("#cpdOccupationId").val();
	errorList = validateApplicantDetails(errorList);
	if(occupation == "0")
	{
		errorList.push(getLocalMessage("BirthRegistrationDTO.occupation"));
	}
	if (noOfCopies == "" || noOfCopies==0) {
		errorList.push(getLocalMessage("death.label.demandcop"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}else{
	if(amount == "N"){
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
	}else{
		if(drDod == ""){
			errorList.push(getLocalMessage("deceased Date Should not be blank"));
		}
		
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
		var drDod = new Date(drDod.replace(pattern, '$3-$2-$1'));
		//var mcVerifnDate = new Date(mcVerifnDate.replace(pattern, '$3-$2-$1'));
	
		if (drDod > currDate) {
			errorList.push(getLocalMessage("deceased Date cannot be greater than Current Date"));
		}

		
		if (errorList.length > 0) {
			checkDate(errorList);
		}
		else{
		var flag = false;
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'DeathRegistrationCorrection.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');

		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
			$('#resetId').hide();
			$('#backId').hide();
			$('#drDod').prop("disabled", true);
			//$('#within1').prop("disabled", true)
			//$('#within2').prop("disabled", true)
			$('#ceName').prop("disabled", true);
			$('#ceNameMar').prop("disabled", true);
			$('#ceAddr').prop("disabled", true);
			$('#ceAddrMar').prop("disabled", true);
			$('#cemeteryList').prop("disabled", true);
		}
		
		correctionsCategory();
		$('#correct').hide();

 }
	}
	}
}
function disFields(element) {
	
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", false);
}

function enaFields(element) {
	$('#ceName').prop("disabled", false);
	$('#ceNameMar').prop("disabled", false);
	$('#ceAddr').prop("disabled", false);
	$('#ceAddrMar').prop("disabled", false);
	$('#cemeteryList').prop("disabled", true);
}


function resetDeathCorrData() {
	window.open('DeathRegistrationCorrection.html', '_self');
}

function saveDeathCorrData(element) {

	var errorList = [];	
	var corrCategory = $.trim($("#corrCategory").val());
	var age = $("#age").val();
	var value = $('option:selected', $("#ageperiod")).attr('code');
	if (corrCategory.includes("DRDECESEAGE")) {
		if (value == "DO") {
			if (age > 31)
				errorList
						.push(getLocalMessage("bnd.agePeriod.days"));
		} else if (value == "MO") {
			if (age > 11 || age < 2)
				errorList
						.push(getLocalMessage("bnd.agePeriod.months"));
		} else if (value == "YR") {
			if (age > 150 || age < 1)
				errorList
						.push(getLocalMessage("bnd.agePeriod.years"));
		}
	}
	var chargeStatus=$("#chargeStatus").val();
	var amount=$("#amount").val();
	var rowcount=$("#DeathTable tr").length 
	var drId = $("#drId").val();
	var saveOrUpdate ;
	errorList = validateApplicantDetails(errorList);
	if(amount == "N"){
		errorList.push("Charges Not Found. Kindly Contact Administrator.");
 		displayErrorsOnPage(errorList);
	}else{
	/*for(var i=0;i<rowcount-1;i++){
	var checklistUploadedOrNot=$("#checkList"+i).val();
	 if(checklistUploadedOrNot==""){
		 errorList.push(getLocalMessage("bnd.upload.doc") +(i+1));
       }
	}*/
	
		$('#drDod').prop("disabled", false);
		 if(chargeStatus != 'CA' || amount == "0" || amount==null || amount =="0.0"){
			 if (errorList.length > 0){
					displayErrorsOnPage(errorList);
				} else{
			saveOrUpdate = saveOrUpdateForm(element, "", 'DeathRegistrationCorrection.html', 'saveform');
			  $('#proceedId').hide();
			  $('#resetId').hide();
			  $('#backId').hide();
			  bndRegAcknow();
				}
		 }else {
			 if (chargeStatus == 'CA' && amount != "0" && amount != null && amount != "0.0") {
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
				
					if (errorList.length > 0) {
						displayErrorsOnPage(errorList);
					}
					else{
						
						
			  saveOrUpdate = saveOrUpdateForm(element, "", 'DeathRegistrationCorrection.html?PrintReport', 'saveform');
			 
				  $('#proceedId').hide();
				  $('#resetId').hide();
				  $('#backId').hide();
				  bndRegAcknow();
			  
		 }
		 }
}
}
}

function validateBndData(element){
	
	var errorList = [];
	var rowcount=$("#DeathTable tr").length 

	for(var i=0;i<rowcount-1;i++){
	 var checklistUploadedOrNot=$("#checkList"+i).val();
	 if(checklistUploadedOrNot==""){
		 errorList.push(getLocalMessage("bnd.upload.doc") +(i+1));
        }
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

function saveDeathRegApprovalData(obj){ 
	 
	  var remark=$("#deathRegremark").val();
	  var approveOrReject=$("#deathRegstatus").val();
	  var errorList = [];
	  if(remark=="" || remark==undefined ){
		 errorList.push("Please enter the Remark");
		}
	  if(approveOrReject=="" || approveOrReject==undefined){
		 errorList.push("Please select Radio Button");
		}
	  if(errorList.length>0){
		 displayErrorsOnPage(errorList);
		}
	  else{
			//Save code
	  } 
}




function getAmountOnNoOfCopes(){
	
 	var errorsList= [];
 	var chargeStatus = $("#chargeStatus").val();
 	if (chargeStatus == 'CA' || chargeStatus == 'CC') {
 	var form_url = $("#frmDeathRegCorrForm").attr("action");
  	var url=form_url+'?getBNDCharge';
 	var isscopy=$("#alreayIssuedCopy").val();
 	if(isscopy=='' || isscopy==undefined ){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
	 if(returnData=='0' || returnData=='N'){
		 $('#payId').hide();
		 $('#amountid').hide();
	 }
	 else{
		 $('#payId').show();
		 $('#amountid').show();
	 }
 	}
 	else if($('#numberOfCopies').val()!='' && $('#numberOfCopies').val()!=undefined){
 		var requestData = "noOfCopies=" + $('#numberOfCopies').val()+ "&issuedCopy=" +isscopy;
 		var returnData = __doAjaxRequest(url, 'post', requestData, false,
 				'json');  
 		 $("#amount").val(returnData);
 		if(returnData=='0' || returnData=='N'){
 			 $('#payId').hide();
 			$('#amountid').hide();
 		 }
 		else{
 			 $('#payId').show();
 			 $('#amountid').show();
 		 }
	
 	}
 	else{
 		//errorsList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorsList);
 	}
 	}
}

function resetMemberMaster(resetBtn) {
	var drId = $('#drId').val();
	modifyDeath(drId, "DeathRegistrationCorrection.html", "editBND", "E");
}

function validateApplicantDetails(errorList) {
	var titleId = $("#titleId").val();
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var cityName = $("#cityName").val();
	var pincodeNo = $("#pincodeNo").val();
	var mobileNo = $("#mobileNo").val();
	var email = $("#email").val();
	var drDod = $("#drDod").val();
	var currDate= new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
	var drDod = new Date(drDod.replace(pattern, '$3-$2-$1'));
	var occupation=$("#cpdOccupationId").val();
	if(occupation == "0")
	{
		errorList.push(getLocalMessage("BirthRegistrationDTO.occupation"));
	}
	
	if (titleId == "0") {
		errorList.push(getLocalMessage("requestDTO.valid.title"));
	}
	if (fName == "" || fName == null || fName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.fName"));
	}
	if (lName == "" || lName == null || lName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.lName"));
	}
	
	if (cityName == "" || cityName == null || cityName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.cityName"));
	}
	if (pincodeNo == "" || pincodeNo == null || pincodeNo == undefined) {

	} else {
		if (pincodeNo.length < 6) {
			errorList.push(getLocalMessage("requestDTO.valid.invalidPinCode"));
		}
	}
	
	if (mobileNo == "" || mobileNo == null || mobileNo == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.mobile"));
	} else {
		if (mobileNo.length < 10) {
			errorList.push(getLocalMessage("requestDTO.valid.invalidMobile"));
		}
	}
	if (email == "" || email == null || email == undefined) {
		
	} else {
		var emailId = $.trim($("#email").val());
		if (emailId != "") {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(emailId);
			if (!valid) {
				errorList.push(getLocalMessage("requestDTO.valid.invalidEmail"));
			}
		}
	}
	if(!(moment($('#drDod').val(), 'DD/MM/YYYY',true).isValid())){
		errorList.push(getLocalMessage("bnd.valid.dateOfDeath"));
	}else{
		var year = moment($('#drDod').val(),"DD/MM/YYYY").year();
		if(year<1900){
			errorList.push(getLocalMessage("bnd.valid.dateOfDeath"));
		}
		if (drDod > currDate) {
			errorList.push(getLocalMessage("death.label.drDodcurrDate"));
		}
	}
	
	return errorList;
}

function getDeathDataById(element){
	var errorList = [];
	var url = "DeathRegistrationCorrection.html?getDeathDataById";
	var requestData = "drId=" + $('#drId').val();
	var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
	return returnData;
}

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


$("#drDod").keyup(function(e) {
	var errorList = [];
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
	
});