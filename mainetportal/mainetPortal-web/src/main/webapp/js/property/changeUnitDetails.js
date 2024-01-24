var yearList;
$(function(){
	
	$('.secondUnitRow').removeClass('in');
	$('.secondUnitRow:last').addClass('in');
	
	//$("#year0").prop("disabled", true);
	$('.firstUnitRow').each(function(i){
		$("#year"+i).prop("disabled", true); 
	});
	
	jQuery('.hasDecimal').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	//$('.hideDetails').removeClass('in'); 
	 $(".datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true
	});
	 
	/*To add new Row into table*/ 
	$("#unitDetailTable").on('click','.addCF',function(){
			var errorList = [];
			errorList = validateUnitDetailTable(errorList);					
			if (errorList.length == 0) {	
				
			$(".datepicker2").removeClass("hasDatepicker");	
				var content =	$("#unitDetailTable").find('tr:eq(1),tr:eq(2)').clone();
				$('.secondUnitRow').removeClass('in');
				$("#unitDetailTable").append(content);
				$('.secondUnitRow:last').addClass('in');
												
				//content.find("input:text").val('');
				content.find("select").val('0');
				content.find("input:hidden:eq(0)").val('0');
				content.find("input:text:eq(1)").val('');
				content.find("input:text:eq(3)").val('');
				content.find("input:text:eq(2)").prop('disabled',true);

				$('.error-div').hide();
				datePickerLogic();
				reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');  // reorder id and Path after Addition of new Row
			 // after tick unable year 
						$("#unitDetailTable tr:last").prev().find("select:eq(0)").prop('disabled',false).removeClass("disabled").addClass("mandColorClass");
				
				return false;
			}							
			else {
				showErrorOnPage(errorList);
		}			
	}); 

	/*To delete Row From the table*/ 
	$("#unitDetailTable").on('click','.remCF',function(){
		
		
		//To remove entry from database
		var inputId=this.id;		
		var left_text = inputId.lastIndexOf("_");
		var deletedRowCount = inputId.substring(left_text+1);		
		var data = {"deletedRowCount" : deletedRowCount};
		var URL = 'SelfAssessmentForm.html?deleteUnitTableRow';
//		reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');
		
		
		//To delete Unit No from unit specific additional information 
		var unitCount= $("#unitNo"+deletedRowCount).val();		
		var a=0; 
		var tableLength = $('#unitDetailTable tr').length;
		for(var j=1;j<tableLength;j++) // table tr length 
		{
			var unitNoFact=$("#unitNo"+j).val();   			
			if(unitCount==unitNoFact){
				a++;
			}
		}
		if(a==1 && unitCount!=""){
			$(".selectUnit option[value="+unitCount+"]").remove();
		}

		
		//remove row From UI
		var index = $(this).closest("tr").index();
		if(($("#unitDetailTable tr").length) >3 && (index!=1)) {
			var tr = $(this).closest("tr");
		    tr.add(tr.next()).remove();
		    $('.secondUnitRow:last').addClass('in');
			var returnData = __doAjaxRequest(URL, 'POST', data, false);
			reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');
			
		}
		else {	
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			showErrorOnPage(errorList);
		}	
	});
		
	$('body').on('click', 'tr.secondUnitRow', function() {
	      $('tr.in').removeClass('active');
	});
	
	$('body').on('click', 'a.click_advance', function() {	
		$("i", this).toggleClass("fa-caret-up fa-caret-down");
	}); 
	
});


function datePickerLogic(){
	var yearOfAcq =$("#proAssAcqDate").val();
	 $(".datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,
			maxDate:yearOfAcq
		});
}


/*function occupancyTypeChange(id){
	if(($('option:selected', $("#assdOccupancyType"+id)).attr('code')) == 'TAN'){
		$("#proAssdAnnualRent"+id).prop('disabled', false);;
		$("#proAssdAnnualRent"+id).prop('required',true);
		
	}else{
		$("#proAssdAnnualRent"+id).val('');
		$("#proAssdAnnualRent"+id).prop('disabled', true);;
		$("#proAssdAnnualRent"+id).prop('required',false);
	}
}
function occupancyTypeChange1(){
	$('.secondUnitRow').each(function(j){
		if(($('option:selected', $("#assdOccupancyType"+j)).attr('code')) == 'TAN'){
			$("#proAssdAnnualRent"+j).prop('disabled', false);;
			$("#proAssdAnnualRent"+j).prop('required',true);
			
		}else{
			$("#proAssdAnnualRent"+j).val('');
			$("#proAssdAnnualRent"+j).prop('disabled', true);;
			$("#proAssdAnnualRent"+j).prop('required',false);
		}
	});
}*/


//validation of unit details table
function validateUnitDetailTable(errorList){
	var rowCount = $('#unitDetailTable tr').length;	
	if(rowCount==3){
		reorderFirstRow();
	}
	$('.firstUnitRow').each(function(i) {	
		if(rowCount<=3)
			{			
			var year =$("#hiddenYear0").val();
//			var unitType=  $("#assdUnitTypeId").val();
			var floorNo=  $("#assdFloorNo").val();
			var yearOfConstruc=  $("#yearOfConstruc0").val();
			var usageType1=  $("#assdUsagetype1").val();
			var usageType2=  $("#assdUsagetype2").val();
			var usageType3=  $("#assdUsagetype3").val();
			var usageType4=  $("#assdUsagetype4").val();
			var usageType5=  $("#assdUsagetype5").val();
			var ConstruType=  $("#assdConstruType").val();
			var taxableArea=  $("#taxableArea0").val();			
			var level = 1;
			}
		else{
		var year =$("#hiddenYear"+i).val();	
//		var unitType=  $("#assdUnitTypeId"+i).val();
		var floorNo=  $("#assdFloorNo"+i).val();
		var yearOfConstruc=  $("#yearOfConstruc"+i).val();
		
		var utp=i;
		if(i>0){
		utp=i*6;
		}
		var usageType1=  $("#assdUsagetype"+utp).val();
		var usageType2=  $("#assdUsagetype"+(utp+1)).val();
		var usageType3=  $("#assdUsagetype"+(utp+2)).val();
		var usageType4=  $("#assdUsagetype"+(utp+3)).val();
		var usageType5=  $("#assdUsagetype"+(utp+4)).val();
		
		var ConstruType=  $("#assdConstruType"+i).val();
		var taxableArea=  $("#taxableArea"+i).val();

		var level = i+1;
		}
		 if(year=='' || year == undefined || year == '0'){			 
			 errorList.push(getLocalMessage('unitdetails.selectYearlevel')+" " +level); 
		 }
		/* if(unitType=='0' || unitType == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectUnitType')+" " +level); 
		 }*/ 		 
		 if(floorNo=='0' || floorNo == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectFloorNo')+ " " +level); 
		 } 			 
		 if(yearOfConstruc=='' || yearOfConstruc == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectConstructionCompletionDate')+ " " +level); 
		 } 		 	
		 if(usageType1=='0' || usageType1 == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectUsageType')+" " +level); 
		 } 
		 if((usageType2 != undefined) && usageType2=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 } 
		 if((usageType3 != undefined) && usageType3=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 } 
		 if((usageType4 != undefined) && usageType4=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 }
		 if((usageType5 != undefined) && usageType5=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 }
		 if(ConstruType=='0' || ConstruType == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectConstructionType')+" "+level); 
		 } 
		 if(taxableArea=='' || taxableArea == undefined || (taxableArea<=0)){
			  errorList.push(getLocalMessage('unitdetails.selectTaxableArea')+" " +level); 
		 }		 	 
	});
	
	$('.secondUnitRow').each(function(j) {	
		if(rowCount<=3){
//			var roadFactor =$("#assdRoadFactor").val();
			var occupancyType =$("#assdOccupancyType").val();
			var level=1;
		}
		else{
//			var roadFactor =$("#assdRoadFactor"+j).val();
			var occupancyType =$("#assdOccupancyType"+j).val();
			var level=j+1;
		}
		
		/*if(roadFactor=='0' || roadFactor == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectRoadFactor')+" "+level); 
		 }*/
		if(occupancyType=='0' || occupancyType == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectOccupacyType')+" " +level); 
		 }
	});
	return errorList;
}

// display details till current year after click on checkbox (unit detail) 
function displayDetailsTillCurrentYear(){
	if($('#checkDetail').is(":checked")){
		var errorList = [];
		var rowCount = $('#unitDetailTable tr').length;	
		if(rowCount<=3){
			reorderFirstRow();
		}
		errorList = validateUnitDetailTable(errorList);	
		if (errorList.length == 0) {
			var finYearId= $("#hiddenYear0").val();
			var data = {"finYearId" : finYearId};
			var URL = 'SelfAssessmentForm.html?getFinanceYearListFromGivenDate';
			var returnData = __doAjaxRequest(URL, 'POST', data, false);
			yearList=returnData;
			var returnDataLength =returnData.length;
			var tableLength=$("#unitDetailTable tr").length;
			for(var i=1; i<=returnDataLength-1;i++)    // yearList
			{ 
				var a=0;
				var c=0;
				var year= returnData[i];
				for(var j=1;j<tableLength;j++) // table tr length 
				{
					var content =	$("#unitDetailTable").find('tr:eq(\"'+j+'\")').clone();
					$('.secondUnitRow').removeClass('in');
					$("#unitDetailTable").append(content);
					$('.secondUnitRow:last').addClass('in');
					reOrderChangeUnitTabIdSequence('.firstUnitRow','.secondUnitRow');		
					if(j%2!=0)  
					{			
//						var unitSelectedValue=	$("#assdUnitTypeId"+a+" option:selected").val();
						var floorSelectedValue=	$("#assdFloorNo"+a+" option:selected").val();
						var usageSelectedValue1=$("#assdUsagetype1"+a+" option:selected").val();
						var usageSelectedValue2=$("#assdUsagetype2"+a+" option:selected").val();
						var usageSelectedValue3=$("#assdUsagetype3"+a+" option:selected").val();
						var usageSelectedValue4=$("#assdUsagetype4"+a+" option:selected").val();
						var usageSelectedValue5=$("#assdUsagetype5"+a+" option:selected").val();
						var constSelectedValue=	$("#assdConstruType"+a+" option:selected").val();
								
						$('#unitDetailTable tr:last').find("select:eq(1)").find("option[value = '" + unitSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(2)").find("option[value = '" + floorSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(3)").find("option[value = '" + constSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(4)").find("option[value = '" + usageSelectedValue1 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(5)").find("option[value = '" + usageSelectedValue2 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(6)").find("option[value = '" + usageSelectedValue3 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(7)").find("option[value = '" + usageSelectedValue4 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(8)").find("option[value = '" + usageSelectedValue5 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("input:hidden:eq(0)").attr("value",year);
						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + year + "']").attr("selected", "selected");
						a++;
					}
					
					if(j%2==0)
					{	
//						var roadTypeSelectedValue=	$("#assdRoadFactor"+c+" option:selected").val();
						var occupancyTypeSelectedValue=	$("#assdOccupancyType"+c+" option:selected").val();
//						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + roadTypeSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + occupancyTypeSelectedValue + "']").attr("selected", "selected");
						
						//To make Monthly field required in case of Tenanted.
						/*$(this).find("select:eq(1)").attr("onchange", "occupancyTypeChange("+(c)+")");
						if(($('option:selected', $("#assdOccupancyType"+c)).attr('code')) == 'TAN'){							
							$("#proAssdAnnualRent"+c).prop('required',true);
						}else{
							$("#proAssdAnnualRent"+c).prop('required',false);
						}*/
						c++;
					
					}		
				}
			}
	
		}else {
			showErrorOnPage(errorList);
			$('#checkDetail').prop('checked', false);			
		}
		
	}else{		
		$(".selectUnit option:gt(2)").remove();		
		clearTableData();  
	}
}

//reorder id and path after click on checkbox
function reOrderChangeUnitTabIdSequence(classNameFirst,classNamesecond){
	
	$(classNameFirst).each(function(i) {
	$(this).find("select:eq(0)").attr("id", "year"+i);
	$(this).find("input:hidden:eq(0)").attr("id", "hiddenYear"+i);
	$(this).find("input:text:eq(0)").attr("id", "unitNo"+i);	
//	$(this).find("select:eq(1)").attr("id", "assdUnitTypeId"+i);
	$(this).find("select:eq(1)").attr("id", "assdFloorNo"+i);
	$(this).find("input:text:eq(1)").attr("id", "yearOfConstruc"+i);
	$(this).find("select:eq(3)").attr("id", "assdUsagetype1"+i);
	$(this).find("select:eq(2)").attr("id", "assdConstruType"+i);
	$(this).find("input:text:eq(2)").attr("id", "taxableArea"+i);
	$(this).find("a:eq(0)").attr("data-target", "#group-of-rows-"+i);
	$(this).find("a:eq(0)").attr("aria-controls", "#group-of-rows-"+i);
	$(this).find("a:eq(1)").attr("id", "deleteRow_"+i);
	$(this).find("select:eq(4)").attr("id", "assdUsagetype2"+i);
	$(this).find("select:eq(5)").attr("id", "assdUsagetype3"+i);
	$(this).find("select:eq(6)").attr("id", "assdUsagetype4"+i);
	$(this).find("select:eq(7)").attr("id", "assdUsagetype5"+i);
	
	$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitNo");
	$(this).find("input:hidden:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].faYearId");
//	$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitTypeId");
	$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdFloorNo");
	$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdYearConstruction");

	$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype1");
	$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdConstruType");
	$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
	
	$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype2");
	$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype3");
	$(this).find("select:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype4");
	$(this).find("select:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype5");
	
	var unitNoFact=$("#unitNo"+i).val();
	if($(".selectUnit option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined){
		$(".selectUnit").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
	}
	
	});
	
	$(classNamesecond).each(function(j){
//		$(this).find("select:eq(0)").attr("id", "assdRoadFactor"+j);
		$(this).find("select:eq(0)").attr("id", "assdOccupancyType"+j);
//		$(this).find("input:text:eq(0)").attr("id", "proAssdAnnualRent"+j);
		
		$(this).find("input:text:eq(0)").attr("id", "occupierName"+j);		
		//$(this).find("input:text:eq(2)").attr("id", "pTCount"+j);
//		$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(j)+")");
		$(this).attr("id", "group-of-rows-"+j);
		
		// re-ordering path binding
//		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdRoadFactor");
		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdOccupancyType");
	//	$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdAnnualRent");
		$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierName");

		
	});
}

function unitCount(id){		
	//var unitCount= $("#unitNo"+id).val();
	var year =$("#year"+id).val();
	var data = {"finId":year};
	var URL = 'SelfAssessmentForm.html?compareDate';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	
	$('#yearOfConstruc'+id).datepicker('option', 'maxDate', new Date(returnData));  //restrict construction completion date based on year of Acquisition

	var a=0;
	var count=0;
	var selYear=$("#year"+id).val();
	$("#hiddenYear"+id).val(selYear);
	var tableLength = $('#unitDetailTable tr').length;
	for(var j=1;j<tableLength;j++) // unit detail table tr length 
	{
		var dropDownvalue=$("#year"+a).val();
		if(dropDownvalue == selYear){
			count++;
		}
		a++;
	}
	$("#unitNo"+id).val(count);
	if($(".selectUnit option[value="+count+"]").length == 0){
		$(".selectUnit").append("<option value="+count+">" +count+ "</option>");	
	}
}

function savePropertyFrom(element,flag){
if(flag=='A'){
	var requestData = {};
	var theForm	=	'#frmSelfAssessmentForm';
	requestData = __serializeForm(theForm);
	var URL = 'SelfAssessmentAuthorization.html?saveAuthorization';
	var returnData =doFormActionForSaveProperty(element,"Saved Successfully",URL, true, "AdminHome.html");
	if(returnData)
	{
		var divName = '.AuthContent';
/*			$(divName).removeClass('ajaxloader');
*/			$(divName).html(returnData);
	}
}else{
	var errorList = [];
	var partialAmt= $("#partialAmt").val();
	if(partialAmt=='' ||partialAmt == undefined || (partialAmt<=0))
	{
			errorList.push(getLocalMessage("property.EnterAmountValid"));
			showErrorOnPage(errorList);
	}
	else{
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'SelfAssessmentForm.html?redirectToPay', 'saveform');
		}
		else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
			{
			 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'SelfAssessmentForm.html?PrintReport', 'saveform');
			}
		else
		{
		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'SelfAssessmentForm.html', 'saveform');
		}
	}
}
var a ="String";
}

function saveAuthorizationWithEdit(element,flag){
	
		var requestData = {};
		var theForm	=	'#frmSelfAssessmentForm';
		requestData = __serializeForm(theForm);
		var URL = 'SelfAssessmentAuthorization.html?saveAuthorizationWithEdit';
		var returnData =doFormActionForSaveProperty(element,"Saved Successfully",URL, true, "AdminHome.html");
		if(returnData)
		{
			var divName = '.AuthContent';
	/*			$(divName).removeClass('ajaxloader');
	*/			$(divName).html(returnData);
		}
	}


function editSelfAssForm(obj){
	var ownerType = $("#ownershipId").val();

	var data = {};
	var URL = 'SelfAssessmentForm.html?editSelfAssForm';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	reorderFirstRow();
	reOrderChangeUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
	reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
	
	//occupancyTypeChange1();   
	yearLength();

	reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
	var data1 = {"ownershipType" : ownerType};
	var URL1 = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
	var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	
	$("#owner").html(returnData1);
	$("#owner").show();
	$('#checkDetail').prop('checked', true);
	
}
function showAddInfo(factorCode,factRadioVal){
	
	if($('input[name="provisionalAssesmentMstDto.proAssfactor['+factRadioVal+']"]:checked').val()=="Y"){
		
		var data = {"factorCode" : factorCode};		
		var URL = 'SelfAssessmentForm.html?getFactorValueDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#"+factorCode).html(returnData);
		
		$("#"+factorCode).show();
		reorderfactor('.specFact','.eachFact');
		var a=0;
		var tableLength = $('#unitDetailTable tr').length;
		for(var j=1;j<tableLength;j++) // table tr length 
		{
			var unitNoFact=$("#unitNo"+a).val();
			var unitSelectId=".factQuestion"+factRadioVal;
			if($(""+unitSelectId+" option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined){
				$(""+unitSelectId+"").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
			}
			a++;
		}
	}else{	 
		var data = {"factorCode" : factorCode};
		var URL = 'SelfAssessmentForm.html?deleteFactorStatus';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#"+factorCode).html('');
		reorderfactor('.specFact','.eachFact');
	}
}
// reorder id and path for unit specific addition information table
function reorderfactor(specFact,eachFact)
{
	$(eachFact).each(function(j) {
		$(specFact).each(function(i) {
			$(this).find("select:eq(0)").attr("id", "unitNoFact"+i).attr('onchange','resetFactorValue(this,'+i+')');
			//$(this).find("select:eq(0)").attr("class","applicableUnitNo"+i);
			$(this).find("select:eq(1)").attr("id", "assfFactorValueId"+i).attr('onchange','enabledisable(this,'+i+')');
			$(this).find("input:hidden:eq(0)").attr("name","provAsseFactDtlDto["+i+"].assfFactorId");
			$(this).find("input:hidden:eq(1)").attr("id","factPref"+i);
			$(this).find("input:hidden:eq(1)").attr("name","provAsseFactDtlDto["+i+"].factorValueCode");
			$(this).find("select:eq(0)").attr("name","provAsseFactDtlDto["+i+"].unitNoFact");
			$(this).find("select:eq(1)").attr("name","provAsseFactDtlDto["+i+"].assfFactorValueId");
			$(this).find("a:eq(0)").attr("onclick", "addUnitRow("+(i)+")");
			$(this).find("a:eq(1)").attr("id", "deleteFactorTableRow_"+i);			
		 });
		$(this).find("select:eq(0)").attr("class","form-control mandColorClass selectUnit factQuestion"+j);

		});
}

function downloadYearWiseTax(){
	var data = {};
	var URL = 'SelfAssessmentForm.html?exportYearWiseTaxDetail';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
}

function doFormActionForSaveProperty(obj,successMessage, URL, sendFormData, successUrl)
{
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	
	             var requestData = {};
	
				if (sendFormData) {
					
					requestData = __serializeForm(theForm);
				}
			
				var url	=URL;
				
				
				var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
				if ($.isPlainObject(returnData))
				{
					var message = returnData.command.message;
					
					var hasError = returnData.command.hasValidationError;
					
					if (!message) {
						message = successMessage;
					}
					
					if(message && !hasError)
						{
						   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
						   		
						   		showSaveResultBox(returnData, message, 'AdminHome.html');
						   	
						   	else
						   		
						   		showSaveResultBox(returnData, message, successUrl);
						}
					else if(hasError)
					{
						$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
					}
					else
						return returnData;
					
				}
				else if (typeof(returnData) === "string")
				{
					$(formDivName).html(returnData);	
					prepareTags();
				}
				else 
				{
					alert("Invalid datatype received : " + returnData);
				}
				
				return false;
				
	
}
function showErrorOnPage(errorList){
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

//missing reordering code add
function reOrderChangeAssessUnitTabIdSequence(classNameFirst,classNamesecond){
	$(classNameFirst).each(function(i) {
	$(this).find("select:eq(0)").attr("id", "year"+i);
	$(this).find("input:hidden:eq(0)").attr("id", "hiddenYear"+i);
	$(this).find("input:hidden:eq(1)").attr("id", "hiddenFirstAssesmentDate"+i);
	
	$(this).find("input:text:eq(0)").attr("id", "unitNo"+i);	
//	$(this).find("select:eq(1)").attr("id", "assdUnitTypeId"+i);
	$(this).find("select:eq(1)").attr("id", "assdFloorNo"+i);
	$(this).find("input:text:eq(1)").attr("id", "yearOfConstruc"+i);
	
	//$(this).find("input:text:eq(2)").attr("id", "lastAssesmentDate"+i);
	
	
	//$(this).find("select:eq(3)").attr("id", "assdUsagetype1"+i);
	$(this).find("select:eq(2)").attr("id", "assdConstruType"+i);
	$(this).find("input:text:eq(3)").attr("id", "taxableArea"+i);
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
	
	$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitNo");
	$(this).find("input:hidden:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].faYearId");
	$(this).find("input:hidden:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].firstAssesmentDate");
	
//	$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitTypeId");
	$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdFloorNo");
	$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdYearConstruction");

	//$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].lastAssesmentDate");
	
	
	$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype1");
	$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdConstruType");
	$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
	
	$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype2");
	$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype3");
	$(this).find("select:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype4");
	$(this).find("select:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype5");

	
	var unitNoFact=$("#unitNo"+i).val();

	if($(".selectUnit option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined && unitNoFact==null){
	
		$(".selectUnit").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
	}
	
	});
	
	$(classNamesecond).each(function(j){
		var utp=j;
		if(j>0){
		utp=j*6;
		}
		
//		$(this).find("select:eq(0)").attr("id", "assdRoadFactor"+j);
		$(this).find("select:eq(0)").attr("id", "assdOccupancyType"+j);
//		$(this).find("input:text:eq(0)").attr("id", "proAssdAnnualRent"+j);
		
		$(this).find("input:text:eq(0)").attr("id", "occupierName"+j);		
		//$(this).find("input:text:eq(2)").attr("id", "pTCount"+j);
		//$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(j)+")");
		$(this).attr("id", "group-of-rows-"+j);
		
		$(this).find("select:eq(1)").attr("id", "natureOfProperty"+utp);
		$(this).find("select:eq(2)").attr("id", "natureOfProperty"+(utp+1));
		$(this).find("select:eq(3)").attr("id", "natureOfProperty"+(utp+2));
		$(this).find("select:eq(4)").attr("id", "natureOfProperty"+(utp+3));
		$(this).find("select:eq(5)").attr("id", "natureOfProperty"+(utp+4));
		
		// re-ordering path binding
//		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdRoadFactor");
		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdOccupancyType");
//		$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdAnnualRent");
		$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierName");

		$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty1");
		$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty2");
		$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty3");
		$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty4");
		$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty5");
		
	});
}