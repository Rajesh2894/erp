$(document).ready(function() {
	prepareDateTag();
	$("#deathregDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
   });
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
$('#drInformantName').bind('click keyup', function(event) {
	var no_spl_char;
	no_spl_char = $("#drInformantName").val().trim();
	if(no_spl_char!=''){
		commonlanguageTranslate(no_spl_char,'drMarInformantName',event,'');
	}else{
		$("#drMarInformantName").val('');
	}
});
$('#drInformantAddr').bind('click keyup', function(event) {
	var no_spl_char;
	no_spl_char = $("#drInformantAddr").val().trim();
	if(no_spl_char!=''){
		commonlanguageTranslate(no_spl_char,'drMarInformantAddr',event,'');
	}else{
		$("#drMarInformantAddr").val('');
	}
});

	}
});


function SearchDeathData(element) {
	
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#deathregDataTable').DataTable();
		var url = "dataEntryForDeathReg.html?searchDeathRegData";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
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
													+ '\',\'dataEntryForDeathReg.html\',\'editBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyDeath(\''
													+ drId
													+ '\',\'dataEntryForDeathReg.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);
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
	var URL = 'dataEntryForDeathReg.html?printBndAcknowledgement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	if(returnData!=null && returnData!=""){
	var title = 'Death Registration Correction Acknowlegement';
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
	var regNo = $('#drRegno');
	if (($("#year").val()) != undefined) {
		trTenderDate = $("#year").val();
	}
	if (trTenderDate != null && trTenderDate != "") {
		var yy = parseInt(trTenderDate);
		if (yy < 1940 || yy > (new Date()).getFullYear()) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		} else if (yy < 1940) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		}
	}
	return errorList;
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

$("#drRegdate").keyup(function(e) {
	var errorList = [];
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
	
});


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
		errorsList.push(getLocalMessage("BirthRegistrationDTO.call.norecord"))
		$('#frmDeathRegistrationCorrection').trigger("reset");
		displayErrorsOnPage(errorsList);
	}
	else{
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	//$('#cpdDeathplaceType').prop("disabled", true);
	$('#hospitalList').prop("disabled", true);
	//$('#drDod').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	}
}

function resetMemberMaster(resetBtn) {
	
	var drId = $('#drId').val();
	modifyDeath(drId, "dataEntryForDeathReg.html", "editBND", "E");
}

function getDeathDataById(element){
	var errorList = [];
	var url = "dataEntryForDeathReg.html?getDeathDataById";
	var requestData = "drId=" + $('#drId').val();
	var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
	return returnData;
}

function saveDeathData(element) {
	
	var errorList = [];
	errorList = validateBndData();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		$('#drDod').prop("disabled", false);
		$('#ceName').prop("disabled", false);
		$('#ceNameMar').prop("disabled", false);
		$('#ceAddr').prop("disabled", false);
		$('#ceAddrMar').prop("disabled", false);
		$('#status').val("O");
		return saveOrUpdateForm(element, "", 'dataEntryForDeathReg.html', 'saveform');
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
	var mcVerifnDate = new Date(mcVerifnDate.replace(pattern, '$3-$2-$1'));

	if (drDod == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDod"));
	}
	if (drdod > currDate) {
		errorList
				.push(getLocalMessage("TbDeathregDTO.label.drDodcurrDate"));
	}
	if (mcVerifnDate > currDate) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drmcVerifnDateCurrDate"));
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
	}
	if (drRegno == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drRegno"));
	}
	
	return errorList;
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