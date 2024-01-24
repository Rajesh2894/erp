$(document).ready(function() {
	
	prepareDateTag();
	$("#BirthCorrDataTable").dataTable({
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
			
		}
});			


function correctionsCategory(element) {
	var errorsList = [];
	var returndata;
	var corrCategory = $.trim($("#corrCategory").val());
	var listArray = new Array();
	var corrCategoryArr = [];
	corrCategoryArr = corrCategory.split(",");

	var list = document.getElementById('corrCategory');
	for (i = 0; i < list.options.length; i++) {
		listArray[i] = list.options[i].value;
	}
	
	returndata = getChildNameById(element);
	$.each(listArray, function(index) {
		
		if (corrCategoryArr.includes(listArray[index])) {
			$("." + listArray[index]).attr("disabled", false);
		} else {
			$("." + listArray[index]).attr("disabled", true);
			  if((!(corrCategoryArr.includes('BRDOB')))&&(listArray.includes('BRDOB'))){
				  $('#brDob').val(getDateFormat(returndata.brDob));
		       }
			   if((!(corrCategoryArr.includes('BRSEX')))&&(listArray.includes('BRSEX'))){
				   $('#brSex').val(returndata.brSex);
		       }
			    if((!(corrCategoryArr.includes('BRCHILDNAME')))&&(listArray.includes('BRCHILDNAME'))){
			    	$('#brChildName').val(returndata.brChildName);
					$('#brChildNameMar').val(returndata.brChildNameMar);
			    }
				if((!(corrCategoryArr.includes('PARENTNAME')))&&(listArray.includes('PARENTNAME'))){
					$('#pdFathername').val(returndata.parentDetailDTO.pdFathername);
					$('#pdFathernameMar').val(returndata.parentDetailDTO.pdFathernameMar);
					$('#pdMothername').val(returndata.parentDetailDTO.pdMothername);
					$('#pdMothernameMar').val(returndata.parentDetailDTO.pdMothernameMar);
				}
				if((!(corrCategoryArr.includes('ADDRESS')))&&(listArray.includes('ADDRESS'))){
					$('#pdParaddress').val(returndata.parentDetailDTO.pdParaddress);
					$('#pdParaddressMar').val(returndata.parentDetailDTO.pdParaddressMar);
					$('#pdAddress').val(returndata.parentDetailDTO.pdAddress);
					$('#pdAddressMar').val(returndata.parentDetailDTO.pdAddressMar);
				}
				if((!(corrCategoryArr.includes('CPDRELIGIONID')))&&(listArray.includes('CPDRELIGIONID'))){
					$('#cpdReligionId').val(returndata.parentDetailDTO.cpdReligionId);
				}
				
		}

	});
	
	if(corrCategoryArr.includes('BRCHILDNAME')){
		if(returndata.brChildName=="" && returndata.brChildNameMar=="" || returndata.brChildName==null && returndata.brChildNameMar==null){
			$('#brChildName').prop("disabled", true);
			$('#brChildNameMar').prop("disabled", true);
			errorsList.push(getLocalMessage("bnd.child.valid.childName"));
		}else{
			$('#brChildName').prop("enabled", true);
			$('#brChildNameMar').prop("enabled", true);
		}
		 if (errorsList.length > 0) {
		   displayErrorsOnPage(errorsList);
		 }
		 return errorsList;
	}

}

function searchBirthData(element) {
	
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#BirthCorrDataTable').DataTable();
		var url = "BirthCorrectionForm.html?searchBirthDetailForCorr";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		/*var requestData = "brCertNo=" + $('#brCertNo').val()
				+ "&applnId=" + $('#applnId').val() + "&year="
				+ $("#year").val() + "&brRegNo=" + $("#brRegNo").val() +"&brDob=" + $("#brDob").val() + "&brChildName=" +$("#brChildName").val();*/
		var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
		table.rows().remove().draw();
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			errorsList.push(getLocalMessage("BirthDeath.NoRecord.Error"));
			displayErrorsOnPage(errorsList);
		}
		else{
	var n=0;
		//var m=0; 
			var result = [];
			
			$
			.each(
			returnData,
			function(index) {
			var obj = returnData[index];
			if(obj.brStatus == "A"){
					n++;
			let brDob = obj.brDob;
			let brChildName=obj.brChildName;
			if(brChildName == null){
				brChildName = "";
			}
			let pdFathername=obj.parentDetailDTO.pdFathername;
			let pdMothername=obj.parentDetailDTO.pdMothername;
			let brRegNo = obj.brRegNo;
			let brRegDate = obj.brRegDate;
			let brSex = obj.brSex;
			let cpdRegUnit = obj.cpdRegUnit;
			let brId = obj.brId;

			result
			.push([
			'<div class="text-center">'
			+ (index + 1) + '</div>',
			'<div class="text-center">'
			+ getDateFormat(brDob) + '</div>',
			'<div class="text-center">'
			+ brRegNo + '</div>',
			'<div class="text-center">'
			+ getDateFormat(brRegDate) + '</div>',
			'<div class="text-center">'
			+ brChildName + '</div>',
			'<div class="text-center">'
			+ pdFathername + '</div>',
			'<div class="text-center">'
			+ pdMothername + '</div>',
			'<div class="text-center">'
			+ brSex + '</div>',
			'<div class="text-center">'
			+ cpdRegUnit + '</div>',
			'<div class="text-center">'
			+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifybirth(\''
			+ brId
			+ '\',\'BirthCorrectionForm.html\',\'editBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
			+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifybirth(\''
            +brId
			+ '\',\'BirthCorrectionForm.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
			+ '</div>' ]);
		
			
			/*+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyIncident(\''
			+ incidentId
			+ '\',\'dailyIncidentRegister.html\',\'viewDIR\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
			+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyIncident(\''
			+ incidentId
			+ '\',\'dailyIncidentRegister.html\',\'editDIR\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
			+ '</div>' ]);*/
			//}
				/*else if(obj.birthWfStatus == "OPEN")
				{
					errorsList.push("APPLICATION IS IN PROGESS")
					$('#frmIssuCertificateForm').trigger("reset");
					displayErrorsOnPage(errorsList);
					
					}*/
			}
			});
			table.rows.add(result);
			table.draw();
			
			if(n==0){
				errorsList.push(getLocalMessage("BirthDeath.NoRecord.Error"));
				displayErrorsOnPage(errorsList);
				
				}
			}
	}
	},2);
	
}

function bndRegAcknow(element) {
	var URL = 'BirthCorrectionForm.html?printBndAcknowledgement';
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

function validateBirthSearchForm(errorsList) {

	var certNo = $('#brCertNo').val();
	var year = $("#year").val();
	var brRegNo = $("#brRegNo").val();
	var brDob = $("#brDob").val();
	var brChildName = $("#brChildName").val();
	var brSex = $('#brSex').val();
	var hospitalList = $('#hospitalList').val();
	var pdFathername = $('#pdFathername').val();
	var pdMothername = $('#pdMothername').val();
	
	// validate the fields
	validatedates(errorsList);
	if (year == "" && brRegNo == "" && brDob== "" && brChildName == "" 
		&& brSex == "0" && hospitalList == "" && pdFathername == "" && pdMothername == "") {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	} else  {
	// go for Search
	
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

function validateBirthCorrSearchForm(errorsList) {
	var certNo = $('#brCertNo').val();
	var regDate = $("#brRegDate").val();
	var regNo = $("#brRegNo").val();
	var apmApplicationId = $("#apmApplicationId").val();

	if (certNo == "" && regDate == "" && regNo == "" && apmApplicationId == "") {
		errorsList.push(getLocalMessage('Please enter at least one search criteria'));
	}
	return errorsList;
}

function saveBirthCorrData(element) {
	var errorList = [];
	var chargeStatus=$("#chargeStatus").val();
	var amount=$("#amount").val();
	var brId= $("#brId").val();
	var rowcount=$("#BirthCorrTable tr").length
	var noOfCopies=$("#noOfCopies").val();
	var saveOrUpdate ;
	if (noOfCopies == "" || noOfCopies==0) {
		errorList.push(getLocalMessage("death.label.demandcop"));
	}
   errorList = validateApplicantDetails(errorList);
	 
	   if (amount == "N") {
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
		displayErrorsOnPage(errorList);
	} else {
		/*for (var i = 0; i < rowcount - 1; i++) {
			var checklistUploadedOrNot = $("#checkList" + i).val();
			if (checklistUploadedOrNot == "") {
				errorList.push(getLocalMessage("bnd.upload.doc") + (i + 1));
			}
		}*/
		if (chargeStatus == 'CA' && amount != "0" && amount != null
				&& amount != "0.0") {
			if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val() != "N"
					&& $("input[name='offlineDTO.onlineOfflineCheck']:checked")
							.val() != "P") {
				errorList.push(getLocalMessage("bnd.select.payment"));
			} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
					.val() == "N") {
				if ($("#oflPaymentMode").val() == 0) {
					errorList.push(getLocalMessage("bnd.select.payment.mode"));
				}
			} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
					.val() == "P") {
				if ($("#payModeIn").val() == 0) {
					errorList.push(getLocalMessage("bnd.select.payment.mode"));
				}
			}

			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			} else {
				saveOrUpdate = saveOrUpdateForm(element, "",
						'BirthCorrectionForm.html?PrintReport', 'saveform');

				$('#proceedId').hide();
				$('#resetId').hide();
				$('#backId').hide();

				bndRegAcknow();

			}

		} else {
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			} else {
				saveOrUpdate =  saveOrUpdateForm(element, "", 'BirthCorrectionForm.html',
						'saveform');
				$('#proceedId').hide();
				$('#resetId').hide();
				$('#backId').hide();
				bndRegAcknow();
			}
		}
	}
}

function resetBirthCorrData() {
	window.open('BirthCorrectionForm.html', '_self');
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

$("#brDob").keyup(function(e) {
	var errorList = [];
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
	
});

function getChecklistAndCharges(element) {
	var errorList = [];
	var amount=$("#amount").val();
	var noOfCopies=$("#noOfCopies").val();
	var kdmcEnv = $("#kdmcEnv").val();
	var pdRegUnitId = $("#pdRegUnitId").val();
	var pdRegUnitCode = $("#pdRegUnitId").find("option:selected").attr('code');
	if (noOfCopies == "" || noOfCopies==0) {
		errorList.push(getLocalMessage("death.label.demandcop"));
	}
	if (kdmcEnv =='Y' && (pdRegUnitId == "0" || pdRegUnitCode=="NS")) {
		errorList.push(getLocalMessage("bnd.regUnit.validation"));
	}
	var correctionCtg = $.trim($("#corrCategory").val());
	if(correctionCtg == '' || correctionCtg == 'undefined'){
		 errorList.push(getLocalMessage("birth.lable.correction"));

	}
	errorList = validateApplicantDetails(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}else{
	if(amount == "N"){
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
	}else{
	var flag = false;
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'BirthCorrectionForm.html?getCheckListAndCharges';
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
			$('#brDob').prop("disabled", true);
			var bptCode = $("#brBirthPlaceType :selected").attr('code');
			if(bptCode=="I")
			{
			$('#hospitalList').prop("disabled", false);
			}else{
				$('#hospitalList').prop("disabled",true);
			}
			//$('#hospitalList').prop("disabled", true);
			$('#brRegNo').prop("disabled", true);
			}
		
		correctionsCategory();
		$('#correct').hide();
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

function modifybirth(brId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
	"mode" : mode,
	"id" : brId
	 };
	//$('#brDob').prop("disabled", true);
	
	
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#brDob').prop("disabled", true);
	var bptCode = $("#brBirthPlaceType :selected").attr('code');
	if(bptCode=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
	//$('#hospitalList').prop("disabled", true);
	$('#brRegNo').prop("disabled", true);
}

function getAmountOnNoOfCopes(){
	
 	var errorsList= [];
 	var chargeStatus = $("#chargeStatus").val();
 	if (chargeStatus == 'CA' || chargeStatus == 'CC') {
 	var form_url = $("#frmBirthCorrectionForm").attr("action");
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
	var brId = $('#brId').val();
	modifybirth(brId, "BirthCorrectionForm.html", "editBND", "E");
}

function validateApplicantDetails(errorList) {
	var titleId = $("#titleId").val();
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var cityName = $("#cityName").val();
	var pincodeNo = $("#pincodeNo").val();
	var mobileNo = $("#mobileNo").val();
	var email = $("#email").val();
	var dob = $('#brDob').val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var brDob = new Date(dob.replace(pattern, '$3-$2-$1'));
	
	var sex = $("#brSex").val();
	var placeOfBrEng = $("#brBirthPlace").val();
	var placeOfBrMar = $("#brBirthPlaceMar").val();
	var brAddEng = $("#brBirthAddr").val();
	var brAddMar = $("#brBirthAddrMar").val();
	var pdFathername = $("#pdFathername").val();
	var pdFathernameMar = $("#pdFathernameMar").val();
	var cpdFEducnId = $("#cpdFEducnId").val();
	var cpdFOccuId = $("#cpdFOccuId").val();
	var pdMothername = $("#pdMothername").val();
	var pdMothernameMar = $("#pdMothernameMar").val();
	var cpdMEducnId = $("#cpdMEducnId").val();
	var cpdMOccuId = $("#cpdMOccuId").val();
	var pdAgeAtBirth = $("#pdAgeAtBirth").val();
	var pdLiveChildn = $("#pdLiveChildn").val();
	var pdParaddress = $("#pdParaddress").val();
	var pdParaddressMar = $("#pdParaddressMar").val();
	var pdAddress = $("#pdAddress").val();
	var pdAddressMar = $("#pdAddressMar").val();
	var cpdReligionId = $("#cpdReligionId").val();
	var wardid = $("#wardid").val();
	var infoNmEng = $("#brInformantName").val();
	var infoAddEng = $("#brInformantAddr").val();
	
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
	if(!(moment($('#brDob').val(), 'DD/MM/YYYY',true).isValid())){
		errorList.push(getLocalMessage("bnd.valid.dateOfBirth"));
	}else{
		var year = moment($('#brDob').val(),"DD/MM/YYYY").year();
		if(year<1900){
			errorList.push(getLocalMessage("bnd.valid.dateOfBirth"));
		}
		if (brDob > currDate) {
			errorList.push(getLocalMessage("BirthRegDto.BrDtGr"));
		}
	}
	
	if (sex == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrGen"));
	}
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
	if (pdFathername == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherNmEng"));
	}
	if (pdFathernameMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherNmReg"));
	}
	if (cpdFEducnId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherEdu"));
	}
	if (cpdFOccuId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrFatherOcc"));
	}
	if (pdMothername == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherNmEng"));
	}
	if (pdMothernameMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherNmReg"));
	}
	if (cpdMEducnId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherEdu"));
	}
	if (cpdMOccuId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherOcc"));
	}
	if (pdAgeAtBirth == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMothAgeAtChild"));
	}
	/*if (pdLiveChildn == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrNoLiveChild"));
	}*/
	if (pdParaddress == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrParentAddrEng"));
	}
	if (pdParaddressMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrParentAddrReg"));
	}
	if (pdAddress == "") {
		errorList.push(getLocalMessage("BirthRegDto.pdAddress"));
	}
	if (pdAddressMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.pdAddressMar"));
	}
	if (cpdReligionId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.Religion"));
	}
	if (wardid == "0") {
		errorList.push(getLocalMessage("BirthRegDto.selectWardName"));
	}
	if (infoNmEng == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrInfoEng"));
	}
	if (infoAddEng == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrInfoAddrEng"));
	}
	
	return errorList;
}

function getChildNameById(element){
	var errorList = [];
	var url = "BirthCorrectionForm.html?getChildNameById";
	var requestData = "brId=" + $('#brId').val();
	var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
	return returnData;
}