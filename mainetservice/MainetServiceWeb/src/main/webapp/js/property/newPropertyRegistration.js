
$(document).ready(function(){

	//$('#proAssAcqDate').attr('readonly', true);	
$("html, body").animate({ scrollTop: 0 }, "slow");
	
var minYear= $("#minFinancialYear").val();
//datepicker function for year of acquisition
$('.lessthancurrdate').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	minDate: minYear,
	maxDate: '-0d',
	yearRange: "-100:-0",
});

//datepicker function for construction completion date
$(".datepicker2").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	maxDate: '-0d',
	changeYear: true,
	  /* onSelect: function(selected) {
		   checkConstructionDate();
		 
	      } */
});
$("#proceed").hide();
$('#yearOfConstruc0').change(function(){	
	var yearOfAcquisition =  $("#proAssAcqDate").val();			
	if(yearOfAcquisition == '' || yearOfAcquisition == undefined){
			showAlertBoxForConstructionDate();
	}	
});
/*var locId=$("#locId").val();
if(locId!=null && locId!='' && locId!=undefined && locId>0){
	var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),$("#locId").val());
	$("#wardZone").html(result);
}*/

});

//If year of Acquisition date is not selected then show popUp message
function  checkConstructionDate()
{
	var yearOfAcquisition =  $("#proAssAcqDate").val();	
	if(yearOfAcquisition == '' || yearOfAcquisition == undefined){
		showAlertBoxForConstructionDate();
	}	
}

function showAlertBoxForConstructionDate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select Year of Acquisition first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#yearOfConstruc0').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

//To close the PopUp message
function closeAlertForm()
{
	var childDivName	=	'.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

//saved form data and display preview Page 
function getCheckList(element){ 
	

	var errorList = [];
	
	// Address Details
	if($('#isCorrespondenceAddressSame').is(":checked")){
		var assAddress= $("#assAddress").val();
		var assPincode=$("#assPincode").val();
		var assEmail=$("#assEmail").val();
		
		$("#assCorrAddress").val(assAddress);
		$("#assCorrPincode").val(assPincode);
		$("#assCorrEmail").val(assEmail);	
	}
	else{

		if($("#assCorrAddress").val()=='' || $("#assCorrPincode").val() == ''){			 
			 errorList.push("Please enter correspodence Address and correspodence Pincode "); 
		 }
	}
	
	
	// Unit Details
	
	if($('#checkDetail').is(":checked")){    // Display validation msg for deleted year  
	var returnDataLength =yearList.length;
	var tableLength=$("#unitDetailTable tr").length;	
	
	for(var i=0; i<returnDataLength;i++){
		var firstYearList=yearList[i];   
		var yearDelete=	$('#year0').find("option[value = '" + firstYearList + "']").text();
		var count=0;
		
		for(var j=0;j<tableLength;j++) {
			if(j%2!=0)
			{
				var secondYearList=$("#unitDetailTable").find('tr:eq(\"'+j+'\")').find("select:eq(0)").val();			
				if(firstYearList==secondYearList)
					{				
					count++;
					}	
			}
		}
		if(count==0)
		{
		errorList.push(getLocalMessage("unitdetails.Enterdetailsfor ")+" "+yearDelete); 
		}
	}
	}
	
	errorList = ValidateTick(errorList);	
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {

	if($("#checkDetail").is(':checked')){
		var theForm	=	'#NewPropertyRegistration';
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'NewPropertyRegistration.html?getCheckList';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			
			$(formDivName).html(returnData);
			reorderFirstRow();
			reOrderChangeUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
			reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			//occupancyTypeChange1();   
			yearLength();
			
			reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
			$('#checkDetail').prop('checked', true);
			$("#proceed").show();
			$("#checkList").hide();
			$("#checkListDiv").show();
			var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
			var data1 = {"ownershipType" : ownerType};
			var URL1 = 'NewPropertyRegistration.html?getOwnershipTypeDiv';
			var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
			$("#owner").html(returnData1);
			$("#owner").show();
		
		}
		yearLength();
	}
	}
	else {
		showVendorError(errorList);
	}
}


function confirmToProceed(element){ 
	var errorList = [];
	// Address Details
	if($('#isCorrespondenceAddressSame').is(":checked")){
		var assAddress= $("#assAddress").val();
		var assPincode=$("#assPincode").val();
		var assEmail=$("#assEmail").val();
		
		$("#assCorrAddress").val(assAddress);
		$("#assCorrPincode").val(assPincode);
		$("#assCorrEmail").val(assEmail);	
	}
	else{

		if($("#assCorrAddress").val()=='' || $("#assCorrPincode").val() == ''){			 
			 errorList.push("Please enter correspodence Address and correspodence Pincode "); 
		 }
	}
	
	
	// Unit Details
	if($('#checkDetail').is(":checked")){    // Display validation msg for deleted year  
	var returnDataLength =yearList.length;
	var tableLength=$("#unitDetailTable tr").length;	
	
	for(var i=0; i<returnDataLength;i++){
		var firstYearList=yearList[i];   
		var yearDelete=	$('#year0').find("option[value = '" + firstYearList + "']").text();
		var count=0;
		
		for(var j=0;j<tableLength;j++) {
			if(j%2!=0)
			{
				var secondYearList=$("#unitDetailTable").find('tr:eq(\"'+j+'\")').find("select:eq(0)").val();			
				if(firstYearList==secondYearList)
					{				
					count++;
					}	
			}
		}
		if(count==0)
		{
		errorList.push(getLocalMessage("unitdetails.Enterdetailsfor ")+" "+yearDelete); 
		}
	}
	}
	
	errorList = ValidateTick(errorList);	
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {

	if($("#checkDetail").is(':checked')){

		var theForm	=	'#NewPropertyRegistration';
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'NewPropertyRegistration.html?proceed';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
			reorderFirstRow();
			reOrderChangeUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
			reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			//occupancyTypeChange1();  
			reorderfactor('.specFact','.eachFact'); 
			$(formDivName).html(returnData);
			yearLength();
			var data1 = {"ownershipType" : ownerType};
			var URL1 = 'NewPropertyRegistration.html?getOwnershipTypeDiv';
			var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
			
			$("#owner").html(returnData1);
			$("#owner").show();
			$("#proceed").show();
			$("#checkList").hide();
			$('#checkDetail').prop('checked', true);
			
		}
		yearLength();
	}
	}
	else {
		showVendorError(errorList);
	}
}

function ValidateTick(errorList){

	var totalPlot =parseFloat($("#totalplot").val());
	var buildup =parseFloat($("#buildup").val());

	if(totalPlot=='' || totalPlot == undefined || (totalPlot<=0)){			 
		 errorList.push(getLocalMessage("property.enterTotalPlotArea")); 
	 }
	if(buildup=='' || buildup == undefined || (buildup<=0)){			 
		 errorList.push(getLocalMessage("property.enterBuildUpArea")); 
	 }
	if(buildup > totalPlot){	
		errorList.push(getLocalMessage("property.totalPloatArea"));
	}

	if($("input[name='provisionalAssesmentMstDto.proAssBillPayment']:checked").val()=="M"){
		var proAssLpReceiptAmt=$("#proAssLpReceiptAmt").val();
		var billAmount= $("#billAmount").val();
		var outstandingAmount= $("#outstandingAmount").val();
		var ReceiptNo= $("#assLpReceiptNo").val();		
		if(proAssLpReceiptAmt=='' || proAssLpReceiptAmt == undefined || (proAssLpReceiptAmt<=0)){			 
			 errorList.push(getLocalMessage("property.enterReceiptAmount")); 
		 }		
		if(billAmount=='' || billAmount == undefined || (billAmount<=0)){			 
			 errorList.push(getLocalMessage("property.enterBillAmount")); 
		 }		
		if(outstandingAmount=='' || outstandingAmount == undefined || (outstandingAmount<=0)){			 
			 errorList.push(getLocalMessage("property.enteroutstandingAmount")); 
		 }
		if(ReceiptNo=='' || ReceiptNo == undefined || (ReceiptNo<=0)){			 
			 errorList.push(getLocalMessage("property.enterReceiptNumber")); 
		 }
	}	
	if($("#checkDetail").prop('checked')==false){
		  errorList.push(getLocalMessage('prop.toProceed.check.unitDetails')); 
	}

	return errorList;
}
function validateFactorStatus(errorList){
	
	
	$('.factorQes').each(function(i){
		var level = i+1;
		var selectFactor = $.trim($('input[name="provisionalAssesmentMstDto.proAssfactor['+i+']"]:checked').val());
		if(selectFactor==0 || selectFactor=="")
		errorList.push(getLocalMessage("property.factorStatus")+" "+level);
		});
	
	return errorList;
}
// Method to calculate financial year from current date
function getChecklistAndCharges(element)
{
	var	formName =	"NewPropertyRegistration";
	var theForm	=	'#'+formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'NewPropertyRegistration.html?getCheckListAndCharges';
	var returnData =__doAjaxRequest(URL,'POST',requestData, false);
	var divName = '#validationDiv';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	$(divName).show();
	var free=$('#free').val();
	if(free=='N'){
		$("#chargesDiv").show();
		$("#payment").show();
		$("#applicantDetails").show();
		$('#chekListChargeId').hide();
	}
	else
	{
		$('#chekListChargeId').show();
		$("#chargesDiv").show();
		$("#applicantDetails").hide();
		$('.required-control').next().children().addClass('mandColorClass');
	}
}

//function is used to return financial year based on year of Acquisition date  
function getFinancialYear(){
	
	$("#yearOfConstruc0").val('');
	var lastPaymentDetails= $("#lastpaymentMadeUpto").val();
	var yearOfAcq =$("#proAssAcqDate").val();
	if(lastPaymentDetails=='0' || lastPaymentDetails==null || lastPaymentDetails==undefined)		
	{
	var data = {"yearOfAcq" : yearOfAcq};
	var URL = 'NewPropertyRegistration.html?getFinancialYear'; 
	var returnData = __doAjaxRequest(URL, 'POST', data, false);	
	clearTableData();
	yearLimit(returnData);
	$("#year0").val(returnData);
	$("#hiddenYear0").val(returnData);
	}
	$(".datepicker2").datepicker("option","maxDate", yearOfAcq);
}

//Used to get Next Schedule cycle based on Last Payment made status
function getPaymentMadeDetails(){
	
	$("#yearOfConstruc0").val('');
	$("#proAssAcqDate").val('');

	var schDetId=	$("#lastpaymentMadeUpto option:selected").val();
	var data = {"schDetId" : schDetId};
	var URL = 'NewPropertyRegistration.html?getNextScheduleFromLastPayDet'; 
	
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	clearTableData();
	//yearLimit();
	yearLimit(returnData[1]);
	$("#year0").val(returnData[1]);
	$("#hiddenYear0").val(returnData[1]);
	$('#proAssAcqDate').datepicker('option', 'maxDate', new Date(returnData[0]));
}

/* Display unit detail YearList based on year of acquisition/last payment made status till current Year
 	e.g year of acquisition 2015 then
    Dropdown of year in unit details contains yearlist(2014-2015,2015-2016,2016-2017,2017-2018)*/
function yearLimit(finYearId){
	$('.displayYearList').html('');
	var data = {"finYearId" : finYearId};
	var url = "NewPropertyRegistration.html?displayYearListBasedOnDate";
	var returnData=	__doAjaxRequest(url, 'post', data , false);
	$('.displayYearList').append('<option value="0">select Year</option>');
	$.each( returnData, function( key, value ) {		
		 $('.displayYearList').append('<option value="' + key + '">' + value  + '</option>');	 
		});
}

//Method called when user change Year of acquisition Date/ Last Payment mode status
function clearTableData(){

	$("#unitDetailTable").find("tr:gt(2)").remove();
	$('.secondUnitRow:last').addClass('in');
	$("#unitDetailTable tr").find("input:text").val();
	$("#unitDetailTable tr").find("select").val();
	$("#unitNo0").val('1');
	
	$('.unitSpecificInfo').html('');
	$('.factorSet').prop('checked',false);  
	$('.factorStatus').prop('checked',false);
	//$(".selectUnit ").val('1');
	$('#checkDetail').prop('checked', false)
	reorderFirstRow();
}

function reorderFirstRow(){
//	$(".firstUnitRow").find("select:eq(1)").attr("id", "assdUnitTypeId");
	$(".firstUnitRow").find("select:eq(1)").attr("id", "assdFloorNo");
	$(".firstUnitRow").find("select:eq(2)").attr("id", "assdConstruType");
	$(".firstUnitRow").find("select:eq(3)").attr("id", "assdUsagetype1");
	$(".firstUnitRow").find("select:eq(4)").attr("id", "assdUsagetype2");
	$(".firstUnitRow").find("select:eq(5)").attr("id", "assdUsagetype3");
	$(".firstUnitRow").find("select:eq(6)").attr("id", "assdUsagetype4");
	$(".firstUnitRow").find("select:eq(7)").attr("id", "assdUsagetype5");
//	$(".secondUnitRow ").find("select:eq(0)").attr("id", "assdRoadFactor");
	$(".secondUnitRow").find("select:eq(0)").attr("id", "assdOccupancyType");
	$(".secondUnitRow").find("select:eq(1)").attr("id", "natureOfProperty1");
	$(".secondUnitRow").find("select:eq(2)").attr("id", "natureOfProperty2");
	$(".secondUnitRow").find("select:eq(3)").attr("id", "natureOfProperty3");
	$(".secondUnitRow").find("select:eq(4)").attr("id", "natureOfProperty4");
	$(".secondUnitRow").find("select:eq(5)").attr("id", "natureOfProperty5");


	

}

//To reorder ID and Path
function reOrderUnitTabIdSequence(classNameFirst,classNamesecond) {
	
			$(classNameFirst).each(function(i) {
			// re-ordering id 	
			$(".datepicker2").removeClass("hasDatepicker");
			var incr=i+1;

			$(this).find("select:eq(0)").attr("id", "year"+i);
			$(this).find("input:hidden:eq(0)").attr("id", "hiddenYear"+i);
			$(this).find("input:hidden:eq(0)").attr("onchange", "compareDate("+(i)+")");
			
			$(this).find("select:eq(0)").attr("onchange", "unitCount("+(i)+")");
			$(this).find("input:text:eq(0)").attr("id", "unitNo"+i);
//			$(this).find("select:eq(1)").attr("id", "assdUnitTypeId"+i);
			$(this).find("select:eq(1)").attr("id", "assdFloorNo"+i);
			$(this).find("input:text:eq(1)").attr("id", "yearOfConstruc"+i);
			$(this).find("input:text:eq(1)").attr("onchange", "successErrorCheck("+(i)+")");

			$(this).find("select:eq(2)").attr("id", "assdConstruType"+i);
			$(this).find("input:text:eq(2)").attr("id", "taxableArea"+i);
			$(this).find("a:eq(0)").attr("data-target", "#group-of-rows-"+i);
			$(this).find("a:eq(0)").attr("aria-controls", "#group-of-rows-"+i);
			$(this).find("a:eq(1)").attr("id", "deleteRow_"+i);
			var utp=i;
			if(i>0){
			utp=i*6;
			}
			$(this).find("select:eq(3)").attr("id", "assdUsagetype"+utp);
			$(this).find("select:eq(4)").attr("id", "assdUsagetype"+(utp+1));
			$(this).find("select:eq(5)").attr("id", "assdUsagetype"+(utp+2));
			$(this).find("select:eq(6)").attr("id", "assdUsagetype"+(utp+3));
			$(this).find("select:eq(7)").attr("id", "assdUsagetype"+(utp+4));
			
			// re-ordering path binding
			if($("#checkDetail").prop('checked')==false){
				$("#unitNo"+i).val(incr);
			}
		
			$(this).find("select:eq(0)").attr("name","");
			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitNo");
			$(this).find("input:hidden:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].faYearId");
//			$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitTypeId");
			$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdFloorNo");
			$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdYearConstruction");
			$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdConstruType");
			$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype1");
			$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype2");
			$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype3");
			$(this).find("select:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype4");
			$(this).find("select:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype5");
			$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
			if(i>0)
			{
				if($("#checkDetail").prop('checked')==false){
					var a=i-1;
					$("#hiddenYear"+i).val($("#hiddenYear"+a).val());
					$("#year"+i).val($("#hiddenYear"+a).val());
				}
			}
			var unitNoFact=$("#unitNo"+i).val();
			if($(".selectUnit option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined && unitNoFact==null){				
				$(".selectUnit").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
			}
			datePickerLogic();
			
		});
		
		$(classNamesecond).each(function(j) {
			
			
			var utp=j;
			if(j>0){
			utp=j*6;
			}
			
//			$(this).find("select:eq(0)").attr("id", "assdRoadFactor"+j);
			$(this).find("select:eq(0)").attr("id", "assdOccupancyType"+j);
//			$(this).find("input:text:eq(0)").attr("id", "proAssdAnnualRent"+j);
			$(this).find("input:text:eq(0)").attr("id", "occupierName"+j);		
//			$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(j)+")");
			$(this).attr("id", "group-of-rows-"+j);
			
			$(this).find("select:eq(1)").attr("id", "natureOfProperty"+utp);
			$(this).find("select:eq(2)").attr("id", "natureOfProperty"+(utp+1));
			$(this).find("select:eq(3)").attr("id", "natureOfProperty"+(utp+2));
			$(this).find("select:eq(4)").attr("id", "natureOfProperty"+(utp+3));
			$(this).find("select:eq(5)").attr("id", "natureOfProperty"+(utp+4));


			// re-ordering path binding
//			$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdRoadFactor");
			$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdOccupancyType");
//			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdAnnualRent");
			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierName");
			
			$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty1");
			$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty2");
			$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty3");
			$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty4");
			$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty5");


		});
}



function downloadSheet(){
		
		window.location.href='NewPropertyRegistration.html?downloadSheet';

}


function resetFormData(resetBtn) {

    cleareFile(resetBtn);  
    
    if (resetBtn && resetBtn.form) {
            $(resetBtn.form)[0].reset();
            $('.alert').html('');
        	$("#unitDetailTable").find("tr:gt(2)").remove();
        	$('.secondUnitRow:last').addClass('in');        	      
        	$('.unitSpecificInfo').html('');
        //	$('input[name="factorStatus"]').attr('checked', false);

        	//reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');
        	$('#isCorrespondenceAddressSame').prop('checked', true);
        	reorderFirstRow();
        	yearLength();
    };
}



function backToFirstLevelAuth(obj){
	var data = {};
	var URL = 'SelfAssessmentAuthorization.html?backToFirstLevelAuth';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	var divName = '.AuthContent';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}

/*function compareBeforeAfterAuth(){
	var data = {};
	var URL = 'SelfAssessmentAuthorization.html?compareBeforeAfterAuth';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	var divName = '.content';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	
}*/

function compareBeforeAfterAuth(element){ 
	var errorList = [];
	if($('#checkDetail').is(":checked")){  
		
		// Display validation msg for deleted year  
		
		var finYearId= $("#hiddenYear0").val();
		var data = {"finYearId" : finYearId};
		var URL = 'NewPropertyRegistration.html?getFinanceYearListFromGivenDate';
		var returnData2 = __doAjaxRequest(URL, 'POST', data, false);
		yearList=returnData2;
		var returnDataLength =yearList.length;
		var tableLength=$("#unitDetailTable tr").length;	
	
		for(var i=0; i<returnDataLength;i++){
		var firstYearList=yearList[i];   
		var yearDelete=	$('#year0').find("option[value = '" + firstYearList + "']").text();
		var count=0;
		
		for(var j=0;j<tableLength;j++) {
			if(j%2!=0)
			{
				var secondYearList=$("#unitDetailTable").find('tr:eq(\"'+j+'\")').find("select:eq(0)").val();			
				if(firstYearList==secondYearList)
					{				
					count++;
					}	
			}
		}
		if(count==0)
		{
		errorList.push(getLocalMessage("unitdetails.Enterdetailsfor ")+" "+yearDelete); 
		}
	}
	}
	
	errorList = ValidateTick(errorList);	
	if (errorList.length == 0) {

	if($("#checkDetail").is(':checked')){
		var theForm	=	'#NewPropertyRegistration';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'NewPropertyRegAuthorization.html?compareBeforeAfterAuth';
/*		var URL = 'SelfAssessmentForm.html?proceed';
*/
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			var divName = '.content';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
		}
	}
	}
	else {
		showVendorError(errorList);
	}
}



function yearLength(){
	
	var dateFields = $('.dateClass');
    dateFields.each(function () {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}

function showVendorError(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}



//To close the PopUp message

//saved form data and display preview Page 
/*function confirmToProceed(element){ 
	var errorList = [];
	
	// Address Details
	if($('#isCorrespondenceAddressSame').is(":checked")){
		var assAddress= $("#assAddress").val();
		var assPincode=$("#assPincode").val();
		var assEmail=$("#assEmail").val();
		
		$("#assCorrAddress").val(assAddress);
		$("#assCorrPincode").val(assPincode);
		$("#assCorrEmail").val(assEmail);	
	}
	else{

		if($("#assCorrAddress").val()=='' || $("#assCorrPincode").val() == ''){			 
			 errorList.push("Please enter correspodence Address and correspodence Pincode "); 
		 }
	}
	
	
	// Unit Details
	if($('#checkDetail').is(":checked")){    // Display validation msg for deleted year  
	var returnDataLength =yearList.length;
	var tableLength=$("#unitDetailTable tr").length;	
	
	for(var i=0; i<returnDataLength;i++){
		var firstYearList=yearList[i];   
		var yearDelete=	$('#year0').find("option[value = '" + firstYearList + "']").text();
		var count=0;
		
		for(var j=0;j<tableLength;j++) {
			if(j%2!=0)
			{
				var secondYearList=$("#unitDetailTable").find('tr:eq(\"'+j+'\")').find("select:eq(0)").val();			
				if(firstYearList==secondYearList)
					{				
					count++;
					}	
			}
		}
		if(count==0)
		{
		errorList.push(getLocalMessage("unitdetails.Enterdetailsfor ")+" "+yearDelete); 
		}
	}
	}
	
	errorList = ValidateTick(errorList);	
	errorList = validateUnitDetailTable(errorList);	 // validate Unit detail table	
	errorList = validateFactorStatus(errorList);
	errorList=validateUnitSpecificInfoTable(errorList); // validate unit specific additional info table
	if (errorList.length == 0) {

	if($("#checkDetail").is(':checked')){
	
		var theForm	=	'#NewPropertyRegistration';
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'NewPropertyRegistration.html?proceed';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			var divName = '.content';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
		}
		yearLength();
	}
	}
	else {
		showVendorError(errorList);
	}
}*/

