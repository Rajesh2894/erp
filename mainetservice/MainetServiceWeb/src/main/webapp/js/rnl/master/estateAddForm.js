/**
 * ritesh.patil
 * 
 */
$(document).ready(function(){
	
	var lastchar;
	if($("#regDate").val() != ''){
		lastchar= $("#regDate").val().substring(0, 10);
		$("#regDate").val(lastchar);
	}
	if($("#constDate").val() != ''){
		lastchar = $("#constDate").val().substring(0, 10);
		$("#constDate").val(lastchar);
     }
	if($("#compDate").val() != ''){
		lastchar = $("#compDate").val().substring(0, 10);
		$("#compDate").val(lastchar);
	 }
	
	
	$("#regDate").datepicker({
		    changeMonth: true,
            changeYear: true,
            startDate: '01/01/1900',
			dateFormat : "dd/mm/yy",
			showMonthBeforeYear : true,
			maxDate:0,
			onClose: function(){$(this).toggleClass('ui-state-focus');},
			onSelect: function(selected) {
				var date2 = $('#regDate').datepicker('getDate');
	            date2.setDate(date2.getDate() - 1);
	            $('#compDate').datepicker('option', 'maxDate', date2);
	            $('#constDate').datepicker('option', 'maxDate', date2);
			} 
		});
	
	$("#constDate").datepicker({
	    changeMonth: true,
        changeYear: true,
        startDate: '01/01/1900',
		dateFormat : "dd/mm/yy",
		showMonthBeforeYear : true,
		maxDate:0,
		onClose: function(){$(this).toggleClass('ui-state-focus');}			
	});
	
	$("#compDate").datepicker({
	    changeMonth: true,
        changeYear: true,
        startDate: '01/01/1900',
		dateFormat : "dd/mm/yy",
		showMonthBeforeYear : true,
		maxDate:0,
		onClose: function(){$(this).toggleClass('ui-state-focus');},
		onSelect: function(selected) {
			var date2 = $('#compDate').datepicker('getDate');
            date2.setDate(date2.getDate() - 1);
            $('#constDate').datepicker('option', 'maxDate', date2);
		} 
	});
	
	
	 
	 $('.hasNumber').blur(function () { 
		    this.value = this.value.replace(/[^0-9]/g,'');
	  });
	  $('.alfaNumricSpecial').blur(function () { 
		    this.value = this.value.replace(/[^a-zA-Z0-9 ()/|,._';:-]/g,'');
	  }); 
	  $('.hasAsset').blur(function () { 
		    this.value = this.value.replace(/[^a-zA-Z0-9 ()/._+#'"-]/g,'');
	  }); 
	 
	 $('#submitEstate').click(function(){
		  var	formName =	findClosestElementId($(this), 'form');
			var theForm	=	'#'+formName;
		 
		    var errorList = [];
			 errorList = validateEstateMasterFormData(errorList);
			 if (errorList.length == 0) {
				 var	formName =	findClosestElementId($(this), 'form');
				 var theForm	=	'#'+formName;
				 return saveOrUpdateForm($(this),"", estateUrl,'saveform');
			}else{
				showRLValidation(errorList)
			 }
	  });
	 
	 /*if($("#assetNoId").val() != '' && $('#hiddeValue').val() == 'E'){
		 getAssetInfoDetails();	
	 	}*/
});


function validateEstateMasterFormData(errorList) {
	
	locId= $('#locId').val();
	address= $.trim($('#address').val());
	nameEng= $.trim($('#nameEng').val());
	nameReg= $.trim($('#nameReg').val());
	category= $('#category').val();
	assetNo= $('#assetNo').val();
	regNo= $.trim($('#regNo').val());
	regDate= $('#regDate').val();
	constDate= $('#constDate').val();
	compDate= $('#compDate').val();
	floors= $.trim($('#floors').val());
	basements= $.trim($('#basements').val());
	type1=$('#type1').val();
	type2=$('#type2').val();
	khashara=$.trim($('#surveyNo').val());
	natuOfLand=$('#natureOfLand').val();
	
	
	 if(locId =="" || locId == undefined ){
		 errorList.push(getLocalMessage('estate.master.location.validate.msg'));
	 }
	 
	 if(nameEng == "" || nameEng == undefined ){
		 errorList.push(getLocalMessage('estate.master.name.eng.validate.msg'));
	 }
	 if(nameReg == "" || nameReg == undefined){
		 errorList.push(getLocalMessage('estate.master.name.reg.validate.msg'));
	 }
	 if(address =="" || address == undefined ){
		 errorList.push(getLocalMessage('estate.master.address.validate.msg'));
	 }
	 
	 if(khashara =="" || khashara == undefined ){
		 errorList.push(getLocalMessage('estate.master.enter.khashra.no'));
	 }
	/* if(natuOfLand =="0" || natuOfLand =="" || natuOfLand == undefined ){
		 errorList.push(getLocalMessage('Please Choose Nature Of Land.'));
	 }*/

	 if(category =="0" || category =="" || category == undefined ){
		 errorList.push(getLocalMessage('estate.master.Category.validate.msg'));
	 }
	 if(type1 =="0" || type1 =="" || type1 == undefined ){
		 errorList.push(getLocalMessage('estate.master.type.validate.msg'));
	 }
	 if(type2 =="0" || type2 =="" || type2 == undefined ){
		 errorList.push(getLocalMessage('estate.master.subType.validate.msg'));
	 }
	 
	//T#139714
	let purpose=$('#purpose').val();
	let acqType=$('#acqType').val();
	let holdingType=$('#holdingType').val();
	
	 if(purpose =="0"){
		 errorList.push(getLocalMessage('estate.master.purpose.validate.msg'));
	 }
	 
	 if(acqType =="0"){
		 errorList.push(getLocalMessage('estate.master.acquisition.validate.msg'));
	 }
	 
	 if(holdingType =="0"){
		 errorList.push(getLocalMessage('estate.master.holding.validate.msg'));
	 }
	 
	 /*if(assetNo == "" || assetNo == undefined){
		 errorList.push(getLocalMessage('estate.master.asset.validate.msg'));
	 }*/
	 /*if(regNo == "" || regNo == undefined){
		 errorList.push(getLocalMessage('estate.master.regno.validate.msg'));
	 }
	 if(regDate == "" || regDate == undefined){
		 errorList.push(getLocalMessage('estate.master.regDate.validate.msg'));
	 }*/
	/* if(constDate == "" || constDate == undefined){
		 errorList.push(getLocalMessage('estate.master.consDate.validate.msg'));
	 }*/
	 /*if(compDate == "" || compDate == undefined){
		 errorList.push(getLocalMessage('estate.master.compDate.validate.msg'));
	 }*/
	/* if(floors == "" || floors == undefined){
		 errorList.push(getLocalMessage('estate.master.floor.validate.msg'));
	 }
	 
	if(basements == "" || basements == undefined){
		 errorList.push(getLocalMessage('estate.master.basements.validate.msg'));
	 }*/
	 
	return errorList;	

}

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}

function getAssetInfoDetails(){
	var errorList = [];
	var assetCode = $("#assetNo").val();
	if(assetNo == null ||assetNo == '' ){
		errorList.push(getLocalMessage("rnl.enter.assetNo"));
		displayErrorsOnPageView(errorList);
		return false;
	}
	var requestData = {
			assetCode : assetCode
	}
	var response = __doAjaxRequest('EstateMaster.html?getAssetDetails', 'post',requestData, false, 'json');
	//data set base on property
	var assetData = response;
	if(assetData.nameEng != null){
		$('#nameEng').val(assetData.nameEng);
		$('#nameReg').val(assetData.nameReg);
		$("#nameEng").prop('readonly', true);
		$("#nameReg").prop('readonly', true);
		//D#33046
		if(assetData.regNo != null && assetData.regNo != ''){
			$('#regNo').val(assetData.regNo).prop('readonly', true);
		}else{
			$("#regNo").prop('readonly', false);
			$('#regNo').val("");		
		}
		
		if(assetData.regDate != null){
			let regDate = getDateFormat(assetData.regDate);
			$('#regDate').val(regDate).prop('readonly', true);
		}else{
			$('#regDate').val("");		
		}
		
		if(assetData.constDate != null){
			$('#constDate').val(getDateFormat(assetData.constDate)).prop('readonly', true);
		}else{
			$('#constDate').val("");		
		}
		
		if(assetData.floors!= null){
			$('#floors').val(assetData.floors).prop('readonly', true);
		}else{
			$("#floors").prop('readonly', false);
			$('#floors').val("");	
		}
		if(assetData.latitude != null){
			$('#estateLatitude').val(assetData.latitude).prop('readonly', true);
		}else{
			$("#estateLatitude").prop('readonly', false);
			$('#estateLatitude').val("");	
		}		
		if(assetData.longitude != null){
			$('#estateLongitude').val(assetData.longitude).prop('readonly', true);
		}else{
			$("#estateLongitude").prop('readonly', false);
			$('#estateLongitude').val("");
		}		
		if(assetData.surveyNo != null && assetData.surveyNo != ''){
			$('#surveyNo').val(assetData.surveyNo).prop('readonly', true);
		}else{
			$("#surveyNo").prop('readonly', false);
			$('#surveyNo').val("");	
		}		
		if(assetData.locId != null){
			$('#locId').val(assetData.locId).prop('disabled', 'disabled');
		}else{
			$("#locId").prop('disabled', false);
			$('#locId').val('').trigger('chosen:updated');
			
		}
		
	}else{
		$('#nameEng').val("");
		$('#nameReg').val("");
		$('#regNo').val("");	
		$('#floors').val("");	
		$('#estateLatitude').val("");	
		$('#estateLongitude').val("");
		$('#surveyNo').val("");	
		$("#nameEng").prop('readonly', false);
		$("#nameReg").prop('readonly', false);
		$("#regNo").prop('readonly', false);
		$("#floors").prop('readonly', false);
		$("#estateLatitude").prop('readonly', false);
		$("#estateLongitude").prop('readonly', false);
		$("#surveyNo").prop('readonly', false);
		$("#locId").prop('disabled', false);
		$('#locId').val('').trigger('chosen:updated');
	}
}

$("#resetEstate").click(function(){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('EstateMaster.html?form', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
});

