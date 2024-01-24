var yearList;
$(function(){
	
	$("#year0").prop("disabled", true); 
	jQuery('.hasDecimal').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	$('.hideDetails').removeClass('in'); 
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
												
				content.find("input:text").val('');
				content.find("select").val('0');
				$('.error-div').hide();
				datePickerLogic();
				reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');  // reorder id and Path after Addition of new Row
				
				if($("#checkDetail").prop('checked')==true){   // after tick unable year 
						$("#unitDetailTable tr:last").prev().find("select:eq(0)").prop('disabled',false).removeClass("disabled").addClass("mandColorClass");
				}
				return false;
			}							
			else {
			displayErrorsOnPage(errorList);
		}			
	}); 

	/*To delete Row From the table*/ 
	$("#unitDetailTable").on('click','.remCF',function(){
		 var index = $(this).closest("tr").index();
		if(($("#unitDetailTable tr").length) >3 && (index!=1)) {
			var tr = $(this).closest("tr");
		    tr.add(tr.next()).remove();
		    $('.secondUnitRow:last').addClass('in');
			reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');
		}
		else {	
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			displayErrorsOnPage(errorList);
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
	if(($('option:selected', $("#proAssdOccupancyType"+id)).attr('code')) == 'TAN'){
		$("#proAssdAnnualRent"+id).prop('required',true);
	}else{
		$("#proAssdAnnualRent"+id).prop('required',false);
	}
}*/


//validation of unit details table
function validateUnitDetailTable(errorList){	
	var rowCount = $('#unitDetailTable tr').length;	
	$('.firstUnitRow').each(function(i) {	
		if(rowCount<=3)
			{			
			var year =$("#hiddenYear0").val();
//			var unitType=  $("#proAssdUnitTypeId").val();
			var floorNo=  $("#proAssdFloorNo").val();
			var yearOfConstruc=  $("#yearOfConstruc0").val();
			var usageType1=  $("#proAssdUsagetype1").val();
			var usageType2=  $("#proAssdUsagetype2").val();
			var usageType3=  $("#proAssdUsagetype3").val();
			var usageType4=  $("#proAssdUsagetype4"+i).val();
			var usageType5=  $("#proAssdUsagetype5"+i).val();
			var ConstruType=  $("#proAssdConstruType").val();
			var taxableArea=  $("#taxableArea0").val();			
			var level = 1;
			}
		else{
		var year =$("#hiddenYear"+i).val();	
//		var unitType=  $("#proAssdUnitTypeId"+i).val();
		var floorNo=  $("#proAssdFloorNo"+i).val();
		var yearOfConstruc=  $("#yearOfConstruc"+i).val();
		var usageType1=  $("#proAssdUsagetype1"+i).val();
		var usageType2=  $("#proAssdUsagetype2"+i).val();
		var usageType3=  $("#proAssdUsagetype3"+i).val();
		var usageType4=  $("#proAssdUsagetype4"+i).val();
		var usageType5=  $("#proAssdUsagetype5"+i).val();
		var ConstruType=  $("#proAssdConstruType"+i).val();
		var taxableArea=  $("#taxableArea"+i).val();

		var level = i+1;
		}
		 if(year=='0' || year == undefined || year == null){			 
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
//			var roadFactor =$("#proAssdRoadfactor").val();
			var occupancyType =$("#proAssdOccupancyType").val();
			var level=1;
		}
		else{
//			var roadFactor =$("#proAssdRoadfactor"+j).val();
			var occupancyType =$("#proAssdOccupancyType"+j).val();
			var level=j+1;
		}
		
//		if(roadFactor=='0' || roadFactor == undefined){
//			  errorList.push(getLocalMessage('unitdetails.selectRoadFactor')+" "+level); 
//		 }
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
			for(var i=1; i<=returnDataLength-1;i++)    // year
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
//						var unitSelectedValue=	$("#proAssdUnitTypeId"+a+" option:selected").val();
						var floorSelectedValue=	$("#proAssdFloorNo"+a+" option:selected").val();
						var usageSelectedValue1=$("#proAssdUsagetype1"+a+" option:selected").val();
						var usageSelectedValue2=$("#proAssdUsagetype2"+a+" option:selected").val();
						var usageSelectedValue3=$("#proAssdUsagetype3"+a+" option:selected").val();
						var usageSelectedValue4=$("#proAssdUsagetype4"+a+" option:selected").val();
						var usageSelectedValue5=$("#proAssdUsagetype5"+a+" option:selected").val();
						var constSelectedValue=	$("#proAssdConstruType"+a+" option:selected").val();
								
//						$('#unitDetailTable tr:last').find("select:eq(1)").find("option[value = '" + unitSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(1)").find("option[value = '" + floorSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(2)").find("option[value = '" + constSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(3)").find("option[value = '" + usageSelectedValue1 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(4)").find("option[value = '" + usageSelectedValue2 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(5)").find("option[value = '" + usageSelectedValue3 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(6)").find("option[value = '" + usageSelectedValue4 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(7)").find("option[value = '" + usageSelectedValue5 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("input:hidden:eq(0)").attr("value",year);
						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + year + "']").attr("selected", "selected");
						a++;
					}
					
					if(j%2==0)
					{	
//						var roadTypeSelectedValue=	$("#proAssdRoadfactor"+c+" option:selected").val();
						var occupancyTypeSelectedValue=	$("#proAssdOccupancyType"+c+" option:selected").val();
//						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + roadTypeSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + occupancyTypeSelectedValue + "']").attr("selected", "selected");
						
						//To make Monthly field required in case of Tenanted.
					/*	$(this).find("select:eq(1)").attr("onchange", "occupancyTypeChange("+(c)+")");
						if(($('option:selected', $("#proAssdOccupancyType"+c)).attr('code')) == 'TAN'){
							$("#proAssdAnnualRent"+c).prop('required',true);
						}else{
							$("#proAssdAnnualRent"+c).prop('required',false);
						}*/
						c++;
					
					}		
				}
			}
	
		}else {
			displayErrorsOnPage(errorList);
			$('#checkDetail').prop('checked', false);
		}
		
	}else{
		clearTableData();  
	}
}

//reorder id and path after click on checkbox
function reOrderChangeUnitTabIdSequence(classNameFirst,classNamesecond){

	$(classNameFirst).each(function(i) {
	$(this).find("select:eq(0)").attr("id", "year"+i);
	$(this).find("input:hidden:eq(0)").attr("id", "hiddenYear"+i);

	$(this).find("input:text:eq(0)").attr("id", "unitNo"+i);	
//	$(this).find("select:eq(1)").attr("id", "proAssdUnitTypeId"+i);
	$(this).find("select:eq(1)").attr("id", "proAssdFloorNo"+i);
	$(this).find("input:text:eq(1)").attr("id", "yearOfConstruc"+i);
	$(this).find("select:eq(3)").attr("id", "proAssdUsagetype1"+i);
	$(this).find("select:eq(2)").attr("id", "proAssdConstruType"+i);
	$(this).find("input:text:eq(2)").attr("id", "taxableArea"+i);
	$(this).find("a:eq(0)").attr("data-target", "#group-of-rows-"+i);
	$(this).find("a:eq(0)").attr("aria-controls", "#group-of-rows-"+i);
	$(this).find("select:eq(4)").attr("id", "proAssdUsagetype2"+i);
	$(this).find("select:eq(5)").attr("id", "proAssdUsagetype3"+i);
	$(this).find("select:eq(6)").attr("id", "proAssdUsagetype4"+i);
	$(this).find("select:eq(7)").attr("id", "proAssdUsagetype5"+i);
	
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
	});
	
	$(classNamesecond).each(function(j){
//		$(this).find("select:eq(0)").attr("id", "proAssdRoadfactor"+j);
		$(this).find("select:eq(0)").attr("id", "proAssdOccupancyType"+j);
//		$(this).find("input:text:eq(0)").attr("id", "proAssdAnnualRent"+j);
		$(this).find("input:text:eq(0)").attr("id", "occupierName"+j);		
		$(this).find("input:text:eq(2)").attr("id", "pTCount"+j);
		//$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(j)+")");
		$(this).attr("id", "group-of-rows-"+j);
		
		// re-ordering path binding
//		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdRoadFactor");
		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdOccupancyType");
//		$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdAnnualRent");
		
	});
}

function unitCount(id){	

	var year =$("#year"+id).val();
	var data = {"finId":year};
	var URL = 'SelfAssessmentForm.html?compareDate';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	
	$('#yearOfConstruc'+id).datepicker('option', 'maxDate', new Date(returnData));  //restrict construction completion date 

	var a=0;
	var count=0;
	var selYear=$("#year"+id).val();
	$("#hiddenYear"+id).val(selYear);
	var tableLength = $('#unitDetailTable tr').length;
	for(var j=1;j<tableLength;j++) // table tr length 
	{
		var dropDownvalue=$("#year"+a).val();
		if(dropDownvalue == selYear){
			count++;
		}
		a++;
	}
	$("#unitNo"+id).val(count);
	if($(".selectUnit option[value="+count+"]").length == 0){
		$(".selectUnit").append("<option>" +count+ "</option>");	
	}
}

function savePropertyFrom(element){
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

function editForm(obj){
	var ownerType = $("#ownershipId").val();
	var data = {};
	var URL = 'SelfAssessmentForm.html?editSelfAssForm';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(".content").html(returnData);
	reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); 
	var data1 = {"ownershipType" : ownerType};
	var URL1 = 'SelfAssessmentForm.html?getOwnershipTypeDiv';
	var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	$("#owner").html(returnData1);
	$("#owner").show();
	$('#checkDetail').prop('checked', true);
}


function showAddInfo(factorCode,factRadioVal,factorId){
	if($('input[name="provisionalAssesmentMstDto.proAssfactor['+factRadioVal+']"]:checked').val()=="Y"){
		var data = {"factorCode" : factorCode,
						"factorId":factorId	};		
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
			if($(".selectUnit option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined){
				$(".selectUnit").append("<option>" +unitNoFact+ "</option>");	
			}
			a++;
		}
	}else{
		$("#"+factorCode).html("");
	}
}


// reoder id and path for unit specific addition information table
function reorderfactor(specFact,eachFact)
{
	$(specFact).each(function(j) {
		$(specFact).each(function(i) {
			$(this).find("select:eq(0)").attr("id", "unitNoFact"+i).attr('onchange','resetFactorValue(this,'+i+')');
			$(this).find("select:eq(1)").attr("id", "proAssfFactorValue"+i).attr('onchange','enabledisable(this,'+i+')');
			$(this).find("input:hidden:eq(1)").attr("id","factPref"+i);
			$(this).find("input:hidden:eq(0)").attr("name","provAsseFactDtlDto["+i+"].assfFactorId");
			$(this).find("input:hidden:eq(1)").attr("name","provAsseFactDtlDto["+i+"].factorValueCode");
			$(this).find("select:eq(0)").attr("name","provAsseFactDtlDto["+i+"].unitNoFact");
			$(this).find("select:eq(1)").attr("name","provAsseFactDtlDto["+i+"].assfFactorValueId");
			$(this).find("a:eq(0)").attr("onclick", "addUnitRow("+(i)+")");
			//$(this).find("a:eq(1)").attr("onclick", "deleteUnitRow("+(i)+")");
		 });
		});
}


/*function downloadYearWiseTax(){
	var data = {};
	var URL = 'SelfAssessmentForm.html?exportYearWiseTaxDetail';
	window.location.href='SelfAssessmentForm.html?exportRateExcelData';
}*/
