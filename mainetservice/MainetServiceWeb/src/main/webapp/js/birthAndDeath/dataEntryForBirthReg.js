$(document).ready(function() {
	
	prepareDateTag();
	$("#dataEntryBirthRegDataTable").dataTable({
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

function searchBirthData(element) {
	
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#dataEntryBirthRegDataTable').DataTable();
		var url = "dataEntryForBirthReg.html?searchBirthRegDetail";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
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
			+ '\',\'dataEntryForBirthReg.html\',\'editBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
			+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifybirth(\''
            +brId
			+ '\',\'dataEntryForBirthReg.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
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
	var URL = 'dataEntryForBirthReg.html?printBndAcknowledgement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	if(returnData!=null && returnData!=""){
	var title = 'Birth Registration Correction Acknowlegement';
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

$("#brRegDate").keyup(function(e) {
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

function modifybirth(brId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
	"mode" : mode,
	"id" : brId
	 };
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	//$('#brDob').prop("disabled", true);
	var bptCode = $("#brBirthPlaceType :selected").attr('code');
	if(bptCode=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
	//$('#brRegNo').prop("disabled", true);
}

function resetMemberMaster(resetBtn) {
	
	var brId = $('#brId').val();
	modifybirth(brId, "dataEntryForBirthReg.html", "editBND", "E");
}

function saveBirthData(element) {

	var errorList = [];
	
	errorList = validateBndData();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		$('#brDob').prop("disabled", false);
			return saveOrUpdateForm(element, "", 'dataEntryForBirthReg.html',
					'saveform');
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
	
	if (birthType == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrTyp"));
	}
	if (dob == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrDt"));
	}
	if (brDob > currDate) {
		errorList.push(getLocalMessage("BirthRegDto.BrDtGr"));
	}
	if (sex == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrGen"));
	}
	if (birthWt == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrWt"));
	}
	if(birthWt >=10||birthWt<=0){
		errorList.push(getLocalMessage("BirthRegDto.BrWtValid"));
	}
	if (brBirthPlaceType == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrPlcTyp"));
	}
	if (bptCode == "I" && hospName == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrHosp"));
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
	if (infoNmEng == "") {
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
	}
	if (cpdAttntypeId == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrAttn"));
	}
	if (delMeth == "0") {
		errorList.push(getLocalMessage("BirthRegDto.BrDel"));
	}
	if (pregDur == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrPreg"));
	}
	if(pregDur >=50){
		errorList.push(getLocalMessage("BirthRegDto.BrPregValid"));
	}
	
	if (birthMrk == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMark"));
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
	if (pdAgeAtMarry == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMothAge"));
	}
	if (pdAgeAtBirth == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMothAgeAtChild"));
	}
	if (pdLiveChildn == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrNoLiveChild"));
	}
	if (motheraddress == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherAddrEng"));
	}if (motheraddressMar == "") {
		errorList.push(getLocalMessage("BirthRegDto.BrMotherAddrReg"));
	}
	if (pdParaddress == "") {
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
	}
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
