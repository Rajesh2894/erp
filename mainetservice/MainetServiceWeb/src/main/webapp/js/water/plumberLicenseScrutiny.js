function reOrderTableIdSequence() {
	$('.academicAppendable').each(function(i) {
		// for generating dynamic Id
		$(this).find("td:eq(0)").attr("id", "academicSrNoId"+i);
		$(this).find("input:text:eq(0)").attr("id", "qualificationId" + i);
		$(this).find("input:text:eq(1)").attr("id", "instituteNameId" + i);
		$(this).find("input:text:eq(2)").attr("id", "instituteAddrsId" + i);
		$(this).find("input:text:eq(3)").attr("id", "passYearId" + i);
		$(this).find("input:text:eq(4)").attr("id", "passMonthId" + i);
		$(this).find("input:text:eq(5)").attr("id", "percentGradeId" + i);
		$(this).find("input:hidden:eq(0)").attr("id", "plumQualHiddenId" + i);
		$(this).parents('tr').find('.academicDeleteRow').attr("id", "academicDelButton"+ i);
		$(this).parents('tr').find('.academicAddRow').attr("id", "academicAddButton"+ i);
		$("#academicSrNoId"+i).text(i+1);
		
		// for generating dynamic path
		$(this).find("input:text:eq(0)").attr("name", "plumberQualificationDTOList[" + i + "].plumQualification");
		$(this).find("input:text:eq(1)").attr("name", "plumberQualificationDTOList[" + i + "].plumInstituteName");
		$(this).find("input:text:eq(2)").attr("name", "plumberQualificationDTOList[" + i + "].plumInstituteAddress");
		$(this).find("input:text:eq(3)").attr("name", "plumberQualificationDTOList[" + i + "].plumPassYear");
		$(this).find("input:text:eq(4)").attr("name", "plumberQualificationDTOList[" + i + "].plumPassMonth");
		$(this).find("input:text:eq(5)").attr("name", "plumberQualificationDTOList[" + i + "].plumPercentGrade");
		$(this).find("input:hidden:eq(0)").attr("name", "plumberQualificationDTOList[" + i	+ "].plumQualId");
	});
 }	

function reOrderExperienceTableIdSequence() {

	$(".experienceAppendable").each(function(i) {
		$(".datepicker").datepicker("destroy");
		// for generating dynamic Id
		$(this).find("td:eq(0)").attr("id", "experienceSrNoId"+i);
		$(this).find("select:eq(0)").attr("id", "firmTypeId" + i);
		$(this).find("input:text:eq(0)").attr("id", "companyNameId" + i);
		$(this).find("input:text:eq(1)").attr("id", "companyAddrsId" + i);
		$(this).find("input:text:eq(2)").attr("id", "expFromDateId" + i);
		$(this).find("input:text:eq(3)").attr("id", "expToDateId" + i);
		$(this).find("input:text:eq(4)").attr("id", "experienceId" + i);
		$(this).find("input:hidden:eq(0)").attr("id", "plumExpHiddenId" + i);
		$(this).parents('tr').find('.experienceDeleteRow').attr("id", "experienceDelButton"+ i);
		$(this).parents('tr').find('.experienceAddRow').attr("id", "experienceAddButton"+ i);
		$("#experienceSrNoId"+i).text(i+1);
		// for generating dynamic path
		$(this).find("input:text:eq(0)").attr("name", "plumberExperienceDTOList[" + i + "].plumCompanyName");
		$(this).find("input:text:eq(1)").attr("name", "plumberExperienceDTOList[" + i + "].plumCompanyAddress");
		$(this).find("input:text:eq(2)").attr("name", "plumberExperienceDTOList[" + i + "].expFromDate");
		$(this).find("input:text:eq(3)").attr("name", "plumberExperienceDTOList[" + i + "].expToDate");
		$(this).find("input:text:eq(4)").attr("name", "plumberExperienceDTOList[" + i + "].experience");
		$(this).find("select:eq(0)").attr("name", "plumberExperienceDTOList[" + i + "].firmType");
		$(this).find("input:hidden:eq(0)").attr("name", "plumberExperienceDTOList[" + i	+ "].plumExpId");
		$(".datepicker").datepicker({
        	dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,
			yearRange: "-100:-0",
			onSelect: function(selected,evnt) {
	         	updateExperienceDetails();
	    	}
		});
	});
 }	

function validateAcademicDetails(errorList,i){
	 var qualification = $.trim($("#qualificationId"+i).val());
	 var institute = $.trim($("#instituteNameId"+i).val());
	 var address = $.trim($("#instituteAddrsId"+i).val());
	 var passYear = $.trim($("#passYearId"+i).val());
	 var passMonth = $.trim($("#passMonthId"+i).val());
	 var grade = $.trim($("#percentGradeId"+i).val());
	 if(qualification == 0 || qualification == "" ){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterQual"));
	 }
	 if(institute == 0 || institute == ""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterInstituteName"));
	 }
	 if(address == 0 || address ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterInstituteAddrs"));
	 }	
	 if(passYear == 0 || passYear ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterPassingYear"));
	 }	
	 if(passMonth == 0 || passMonth ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterPassingMonth"));
	 }	
	 if(grade == 0 || grade ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterPercentOrGrade"));
	 }	
	 return errorList;
}

function validateExperienceDetails(errorList,i){
	
	 var companyName = $.trim($("#companyNameId"+i).val());
	 var companyAddrs = $.trim($("#companyAddrsId"+i).val());
	 var fromDate = $.trim($("#expFromDateId"+i).val());
	 var toDate = $.trim($("#expToDateId"+i).val());
	 var experience = $.trim($("#experienceId"+i).val());
	 var firmType = $.trim($("#firmTypeId"+i).val());
	 if( companyName == "" ){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterEmployeersName"));
	 }
	 if(companyAddrs  == ""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.enterEmployeersAddrs"));
	 }
	 if(fromDate ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.selectFrmDt"));
	 }	
	 if(toDate ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.selectToDt"));
	 }	
	 if(firmType == 0 || firmType ==""){
		 errorList.push(getLocalMessage("water.plumberLicense.valMsg.selectFrmType"));
	 }	
	 if((new Date(dateFormate(fromDate)) > new Date(dateFormate(toDate)))){
			errorList.push(getLocalMessage("water.plumberLicense.valMsg.frmDtLessthanToDt"));
	} 
	 return errorList;
}
	//to formate date date picker formate to new date formate
	function dateFormate(date){
	 	var chunks = date.split('/');
		var newDate = chunks[1]+'/'+chunks[0]+'/'+chunks[2];
		return newDate;
	}

function updateExperienceDetails(){
	var sumYear =0;
	var sumMonth=0;
	var year = 0;
	var month =0;
	 $(".experienceAppendable").each(function(i) {
		var frmDt = $("#expFromDateId"+i).val();
		var toDt = $("#expToDateId"+i).val();
		var experience = calculateExp(frmDt,toDt);
		if(isNaN(toDt) && isNaN(frmDt)){
	 		$("#experienceId"+i).val(experience);
	 		var splitExp =experience.split('.')
	 		var year = splitExp[0];
	 		var month =splitExp[1];
	 		sumYear += Number(year);
	 		sumMonth += Number(month);
		}
	 });
	var YrsMnth=0;
	var yearToMonth =  sumYear*12;
	var totalMonth = yearToMonth + Number(sumMonth);
	var remainderMonth=totalMonth%12;
	 if(totalMonth >= 12){
    	var year =(totalMonth/12).toFixed(1);
    	var str_array = year.split('.');
    	var yrs =str_array[0];
    	YrsMnth = yrs +"." + remainderMonth;
	  }else{
	    YrsMnth="0."+remainderMonth;
	  }
	 $("#totalExpId").val(YrsMnth);
};

function calculateExp(a,b){
	var chunks1 = a.split('/');
    var chunks2 = b.split('/');
	var  fromDate= chunks1[1]+'/'+chunks1[0]+'/'+chunks1[2];
    var tDate = chunks2[1]+'/'+chunks2[0]+'/'+chunks2[2];
    if(isNaN(fromDate) && isNaN(tDate) ){
		var frmDate=new Date(fromDate);
		var toDate = new Date(tDate); 
		var months;
	    months = (toDate.getFullYear() - frmDate.getFullYear()) * 12;
	    months -= frmDate.getMonth() + 1;
	    months += toDate.getMonth();
	    var total= months <= 0?0:months;
	    var totalMonths = total+1;
	    var remainderMonths=totalMonths%12;
	    var YrsMnth = 0;
	    if(totalMonths >= 12){
	    	var year =(totalMonths/12).toFixed(1);
	    	var str_array = year.split('.');
	    	var yrs =str_array[0];
	    	YrsMnth = yrs +"." + remainderMonths;
	    }else{
	    	YrsMnth="0."+remainderMonths;
	    }
		return YrsMnth;
	}	
}

function editForm(obj){
		$("#submitBtn").show();
		$('textarea').attr('readonly',false);
		$('input[type=text]').attr('disabled',false);
		$('select').attr("disabled", false);
		$(".academicAddRow").attr("disabled", false);
		$(".academicDeleteRow").attr("disabled", false);
		$(".experienceAddRow").attr("disabled", false);
		$(".experienceDeleteRow").attr("disabled", false);
		$("#editId").hide();
	}


function updatedPlumberLicenseDetailsOnScrutiny(element) {
	
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = checkAcademicDetails(errorList);
	errorList = checkExperienceDetails(errorList);
	
	var isBPL = $('#povertyLineId').val();
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,"", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
	
}

 function checkAcademicDetails(errorList){
  	$(".academicAppendable").each(function(i) {
	  errorList  =validateAcademicDetails(errorList,i);
	});
  return errorList;
 }
 
 function checkExperienceDetails(errorList){
	  	$(".experienceAppendable").each(function(i) {
		       errorList  =validateExperienceDetails(errorList,i);
		});
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

 function validateApplicantInfo(errorList) {
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var gender= $.trim($('#gender').val());
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var applicantAreaName= $.trim($('#areaName').val());
	var blockName= $.trim($('#blockName').val());
	var villTownCity= $.trim($('#villTownCity').val());
	var applicantPinCode= $.trim($('#pinCode').val());
	var applicantAdharNo= $.trim($('#adharNo').val());
	var povertyLineId= $.trim($('#povertyLineId').val());
	var dwzid1= $.trim($('#dwzid1').val());
	var dwzid2= $.trim($('#dwzid2').val());
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(gender == "" || gender =='0' || gender == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantGender'));
	 }
	 if(applicantMobileNo == "" || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(applicantAreaName == "" || applicantAreaName == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantarea'));
	 }

	 if(villTownCity == "" || villTownCity == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 if(povertyLineId == "" || povertyLineId =='0'|| povertyLineId == undefined){
		 errorList.push("Is below poverty line must be selected");
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

function closeOutErrBox(){
	$('.error-div').hide();
}

