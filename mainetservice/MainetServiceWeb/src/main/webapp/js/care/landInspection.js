$(document).ready(function() {
	
	$('#hadbandiTable').dataTable({
				searching: false,
				paging: false,
				info: false
			});
	
	$('#multiEncroDetTable').dataTable({
				searching: false,
				paging: false,
				info: false
			});
	
	$( "#crtDate" ).datepicker({
        dateFormat : 'dd/mm/yy',
    		changeMonth : true,
    		changeYear : true,
    		yearRange : "1948:2200"
    });
	
	
	if($('#encrSingleSelect').val() == 'N' || $('#encrSingleSelect').val()=='0' ){
		$('#singleEncroachmentTable').hide();
	}else{
		$('#singleEncroachmentTable').show();
	}
	
	if($('#encrMultipleSelect').val() == 'N' || $('#encrMultipleSelect').val()=='0' ){
		$('#multiEncroachDivId').hide();
		$('#multiEncroachmentTable').hide();
	}else{
		$('#multiEncroachmentTable').show();
		$('#multiEncroachDivId').show();
	}
   
	
	
	//on Select Hadbandi
	$('#hadbandi').on('change', function() {
		  if(this.value == 'Y'){
			  $('#hadbandiSection').show();
		  }else{
			  $('#hadbandiSection').hide();
		  }	  
	});
	
	$('#groundConditionId').on('change', function() {
		  if(this.value == 'LESS'){
			  //mark mandatory name and KHASRA no
			  $("#nameLbl").addClass("required-control");
			  $("#khasraNoLbl").addClass("required-control");
		  }else{
			  $("#nameLbl").removeClass( "required-control")
			  $("#khasraNoLbl").removeClass( "required-control")
		  }	  
	});
	
	
	//ENCROACHMENT
	$('#encrSingleSelect').on('change', function() {
		  if(this.value == 'Y'){
			  $('#singleEncroachmentTable').show();
			  $('#multiEncroachDivId').show();
		  }else{
			  $('#singleEncroachmentTable').hide();
			  $('#multiEncroachDivId').hide();
			  //No set for Multi Encroachment option
			  $('#encrMultipleSelect option[value="N"]').attr("selected", "selected").trigger("chosen:updated");;
		  }	  
	});
	
	
	$('#encrMultipleSelect').on('change', function() {
		  if(this.value == 'Y'){
			  $('#multiEncroachmentTable').show();
		  }else{
			  $('#multiEncroachmentTable').hide();
		  }	  
	});
	
	
	$('#casePendancySelect').on('change', function() {
		  if(this.value == 'Y'){
			  $("#crtNameLbl").addClass("required-control");
			  $("#litigantLbl").addClass("required-control");
			  $("#respondentLbl").addClass("required-control");
			  $("#advocateNameLbl").addClass("required-control");
			  $("#contactNoLbl").addClass("required-control");
		  }else{
			  $("#crtNameLbl").removeClass( "required-control")
			  $("#litigantLbl").removeClass( "required-control")
			  $("#respondentLbl").removeClass( "required-control")
			  $("#advocateNameLbl").removeClass( "required-control")
			  $("#contactNoLbl").removeClass( "required-control")
		  }	  
	});
	
	
	
	/*Reset Query REG Form*/
	$("#resetLnInspection").click(function(){
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('LegislativeQuestion.html', {}, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	});
	
	
	


});//end of document load


function addHadbandiEntry() {
	var errorList = [];
	errorList = validateHadbandiDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('hadbandiTable');	
	} else {
		displayErrorsOnPage(errorList);
	}

	$('#hadbandiTable').DataTable().destroy();
	//triggerTable();
}


function deleteHadbandiEntry(obj, ids) {
	// get table row number
	var rowCount = $('#hadbandiTable >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("ln.inspec.vldnn.delete.entry"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}

	deleteTableRow('hadbandiTable', obj);
	$('#hadbandiTable').DataTable().destroy();
	//triggerTable();	
}



function addEntryData() {
	var errorList = [];
	errorList = validateMultiEncrochDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('multiEncroDetTable');	
	} else {
		displayErrorsOnPage(errorList);
	}

	$('#multiEncroDetTable').DataTable().destroy();
	//triggerTable();
}


function deleteEntry(obj, ids) {
	// get table row number
	var rowCount = $('#multiEncroDetTable >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("ln.inspec.vldnn.delete.entry"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}

	deleteTableRow('multiEncroDetTable', obj);
	$('#multiEncroDetTable').DataTable().destroy();
	//triggerTable();	
}




function saveLandInspection(obj){
	/*test purpose below code
	let photoCount=$('#file_list_0 ul li').length-1;
	let endDocCount=$('#file_list_6 ul li').length-1;*/
	
	var errorList = [];
	errorList = validateLandInspection(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(obj, '', 'AdminHome.html', 'saveform');
	}
}

function validateLandInspection(errorList){
	let lnTypeSurvey = $('#lnTypeSurvey').val();
	let disttId = $('#disttId').val();
	let tehsilId = $('#tehsilId').val();
	let khasraNoId = $('#khasraNoId').val();
	let lekhpalArea = $('#lekhpalArea').val();
	let villageId = $('#villageId').val();
	let mahal = $('#mahal').val();
	let khevatNo = $('#khevatNo').val();
	let lnLordName = $('#lnLordName').val();
	let accountNo = $('#accountNo').val();
	let permanentMark = $('#permanentMark').val();
	let hadbandi = $('#hadbandi').val();
	let measured = $('#measured').val();
	let landTypeId = $('#landTypeId').val();
	let subLdType = $('#subLdType').val();
	let propertyType = $('#propertyType').val();
	let subPropType = $('#subPropType').val();
	
	if (lnTypeSurvey == undefined || lnTypeSurvey == "" || lnTypeSurvey=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.surveyLnType"));
	if (disttId == undefined || disttId == "" || disttId=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.distId"));
	if (tehsilId == undefined || tehsilId == "" || tehsilId=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.tehsilId"));
	if (khasraNoId == undefined || khasraNoId == "" || khasraNoId=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.khasraNoId"));
	if (lekhpalArea == undefined || lekhpalArea == "")
		errorList.push(getLocalMessage("ln.inspec.vldnn.lekhpalArea"));
	if (villageId == undefined || villageId == "" || villageId=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.vilg"));
	if (mahal == undefined || mahal == "" || mahal=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.mahl"));
	if (khevatNo == undefined || khevatNo == "")
		errorList.push(getLocalMessage("ln.inspec.vldnn.khevatNo"));
	if (lnLordName == undefined || lnLordName == "")
		errorList.push(getLocalMessage("ln.inspec.vldnn.lnLoardName"));
	if (accountNo == undefined || accountNo == "")
		errorList.push(getLocalMessage("ln.inspec.vldnn.acctNo"));
	if (permanentMark == undefined || permanentMark == "")
		errorList.push(getLocalMessage("ln.inspec.vldnn.permMark"));
	
	if (hadbandi == undefined || hadbandi == "" || hadbandi=="0"){
		errorList.push(getLocalMessage("ln.inspec.vldnn.hadbandi"));
	}else if(hadbandi == 'Y'){
		errorList=validateHadbandiDetails(errorList);
	}
		
	
	
	if (measured == undefined || measured == "" || measured=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.measured"));
	if (landTypeId == undefined || landTypeId == "" || landTypeId == "0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.ldType"));
	if (subLdType == undefined || subLdType == "" || subLdType=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.subLdType"));
	if (propertyType == undefined || propertyType == "" || propertyType=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.propType"));
	if (subPropType == undefined || subPropType == "" || subPropType=="0")
		errorList.push(getLocalMessage("ln.inspec.vldnn.subPropType"));
	
	
	let groundConditionId = $('#groundConditionId').val();
	if (groundConditionId == undefined || groundConditionId == "" || groundConditionId=="0"){
		errorList.push(getLocalMessage("ln.inspec.vldnn.grdCondi"));
	}else if(groundConditionId == 'LESS'){
		let name = $('#name').val();
		let khasraNo = $('#khasraNo').val();
		if (name == undefined || name == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.lessName"));
		if (khasraNo == undefined || khasraNo == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.lessKhasraNo"));
	}
	
	let encrSingleSelect = $('#encrSingleSelect').val();
	if (encrSingleSelect == undefined || encrSingleSelect == "" || encrSingleSelect=="0"){
		errorList.push(getLocalMessage("ln.inspec.vldnn.singleEncroachSelect"));
		
	}else if(encrSingleSelect == 'Y'){
		let name0 = $('#nameS0').val();
		let contactNo0 = $('#contactNoS0').val();
		let direction0 = $('#directionS0').val();
		
		if (direction0 == undefined || direction0 == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.singleEncrDirec"));
		if (name0 == undefined || name0 == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.singleEncrName"));
		if (contactNo0 == undefined || contactNo0 == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.singleEncrContNo"));
		
	}
	
	
	//here validate multiple ENCROACHMENT if YES
	let encrMultipleSelect = $('#encrMultipleSelect').val();
	if (encrMultipleSelect == undefined || encrMultipleSelect == "" || encrMultipleSelect=="0"){
		errorList.push(getLocalMessage("ln.inspec.vldnn.multiEncroachSelect"));
	}else if(encrMultipleSelect == 'Y'){
		errorList=validateMultiEncrochDetails(errorList);
	}
	
	
	let casePendancySelect = $('#casePendancySelect').val();
	if (casePendancySelect == undefined || casePendancySelect == "" || casePendancySelect=="0"){
		errorList.push(getLocalMessage("ln.inspec.vldnn.casePend"));
	}else if(casePendancySelect == 'Y'){
		let crtName = $('#crtName').val();
		let litigant = $('#litigant').val();
		let respondent = $('#respondent').val();
		let advocateName = $('#advocateName').val();
		let contactNo = $('#contactNo').val();
		if (crtName == undefined || crtName == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.crtName"));
		if (litigant == undefined || litigant == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.litigant"));
		if (respondent == undefined || respondent == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.respondent"));
		if (advocateName == undefined || advocateName == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.advocateName"));
		if (contactNo == undefined || contactNo == "" )
			errorList.push(getLocalMessage("ln.inspec.vldnn.contactNo"));
	}
	
	
	//Documents validation 
	var docLength=$('#file_list_0 ul li').length;//
	if(docLength == 0){
		errorList.push(getLocalMessage("ln.inspec.vldnn.docAttach"));
	}
	
	return errorList;
	
}

function validateMultiEncrochDetails(errorList) {
	let tempArrayIds = [];
	$("#multiEncroDetTable tbody tr")
			.each(
					function(i) {
						let position = " at sr no. " + (i + 1);
						let directionM = $('#directionM'+ i).val();
						let name = $('#nameM' + i).val();
						let contactNo = $('#contactNoM' + i).val();
						
						if (directionM == undefined || directionM == "")
							errorList.push(getLocalMessage("ln.inspec.vldnn.multiEncrDirec")+ position);
						if (name == undefined || name == "")
							errorList.push(getLocalMessage("ln.inspec.vldnn.multiEncrName")+ position);
						if (contactNo == '' || contactNo == undefined)
							errorList.push(getLocalMessage('ln.inspec.vldnn.multiEncrContNo') +position);
						
						if(errorList.length == 0){
							tempArrayIds.push(name);	
						}
					});

	let sortedArray = tempArrayIds.sort();
	let results = [];
	for (let i = 0; i < sortedArray.length - 1; i++) {
		if (sortedArray[i + 1] == sortedArray[i]) {
			results.push(sortedArray[i]);
		}
	}
	if (results.length > 0) {
		//for duplicate validation
		//errorList.push(getLocalMessage("asset.annualPlan.vldnn.duplicateClass"));
	}
	return errorList;
}

function validateHadbandiDetails(errorList) {
	let tempArrayIds = [];
	$("#hadbandiTable tbody tr")
			.each(
					function(i) {
						let position = " at sr no. " + (i + 1);
						let demarSide =  $('#demarSide' + i).val();
						let name = $('#name' + i).val();
						let contactNo = $('#contactNo' + i).val();
						
						
						if (name == undefined || name == "")
							errorList.push(getLocalMessage("ln.inspec.vldnn.hadbDirect ")+ position);
						if (name == undefined || name == "")
							errorList.push(getLocalMessage("ln.inspec.vldnn.hadbName")+ position);
						if (contactNo == '' || contactNo == undefined)
							errorList.push(getLocalMessage('ln.inspec.vldnn.hadbNameContNo') +position);
						
						if(errorList.length == 0){
							tempArrayIds.push(name);	
						}
					});

	var sortedArray = tempArrayIds.sort();
	var results = [];
	for (var i = 0; i < sortedArray.length - 1; i++) {
		if (sortedArray[i + 1] == sortedArray[i]) {
			results.push(sortedArray[i]);
		}
	}
	if (results.length > 0) {
		//for duplicate validation
		//errorList.push(getLocalMessage("asset.annualPlan.vldnn.duplicateClass"));
	}
	return errorList;
}


//approval of Land Inspection Entry REG form
function saveDecision(obj) {
	var errorList = [];
	//errorList = answerRegValidation(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'AdminHome.html', 'saveDecision');
	}
}


function onlyNumber(obj) {
	 var val = obj.value;
	 val = val.replace(/[^\d].+/, "");
      if ((event.which < 48 || event.which > 57)) {
          event.preventDefault();
      }
}
