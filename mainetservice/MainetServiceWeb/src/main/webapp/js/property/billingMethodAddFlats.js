$(document).ready(function() {
	reOrderUnitTabIdSequence('.firstUnitRow', '.secondUnitRow');
	$(".disableFields").prop('disabled', true);
});

$("#unitDetailTable").on('click', '.addCF', function() {

	var errorList = [];
	var rowCount = $('#unitDetailTable tr:last').index() / 2;
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length == 0) {

		$(".datepicker2").removeClass("hasDatepicker");
		$(".lessthancurrdate").removeClass("hasDatepicker");		
		var content = $("#unitDetailTable").find('tr:eq(1),tr:eq(2)').clone();
		$('.secondUnitRow').removeClass('in');
		$("#unitDetailTable").append(content);
		$('.secondUnitRow:last').addClass('in');

		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		$('.error-div').hide();
		datePickerLogic();
		$(".disableFields").prop('disabled', false);
		reOrderUnitTabIdSequence('.firstUnitRow', '.secondUnitRow');
		removeDisabled('.firstUnitRow', '.secondUnitRow');
		$(".disableFields").prop('disabled', true);
		/*
		 * if ($("#checkDetail").prop('checked') == true) { $("#unitDetailTable
		 * tr:last").prev().find("select:eq(0)") .prop('disabled',
		 * false).removeClass("disabled") .addClass("mandColorClass"); }
		 */
		// var hiddenOther = $('#hiddenOther').val();
		// content.find("select:eq(1)").val(hiddenOther);
		return false;
	} else {
		displayErrorsOnPage(errorList);	
	}
});

function validateUnitDetailTable(errorList) {
	var constructDateFlag = $("#ConstructFlag").val();
	var a = a;
	var rowCount = $('#unitDetailTable tbody tr').length;
	var billMethod = $('#billMethod option:selected').attr('code');
	/*if (rowCount == 3) {
		reorderFirstRow();
	}*/
	$('.firstUnitRow')
			.each(
					function(i) {

						if (rowCount <= 2) {
							var year = $("#hiddenYear0").val();
							var floorNo = $("#assdFloorNo").val();
							var yearOfConstruc = $("#yearOfConstruc0").val();
							var firstAssesmentDate = $("#firstAssesmentDate0")
									.val();
							var usageType1 = $("#assdUsagetype1").val();
							var usageType2 = $("#assdUsagetype2").val();
							var usageType3 = $("#assdUsagetype3").val();
							var usageType4 = $("#assdUsagetype4").val();
							var usageType5 = $("#assdUsagetype5").val();
							var ConstruType = $("#assdConstruType").val();
							var taxableArea = $("#taxableArea0").val();
							var carpetArea = $("#carpetArea0").val();
							var flatNo = $("#flatNo0").val();
							var level = 1;
						} else {
							var year = $("#hiddenYear" + i).val();
							var floorNo = $("#assdFloorNo" + i).val();
							var yearOfConstruc = $("#yearOfConstruc" + i).val();
							var firstAssesmentDate = $(
									"#firstAssesmentDate" + i).val();

							var utp = i;
							if (i > 0) {
								utp = i * 6;
							}
							var usageType1 = $("#assdUsagetype" + utp).val();
							var usageType2 = $("#assdUsagetype" + (utp + 1))
									.val();
							var usageType3 = $("#assdUsagetype" + (utp + 2))
									.val();
							var usageType4 = $("#assdUsagetype" + (utp + 3))
									.val();
							var usageType5 = $("#assdUsagetype" + (utp + 4))
									.val();
							var ConstruType = $("#assdConstruType" + i).val();
							var taxableArea = $("#taxableArea" + i).val();
							var carpetArea = $("#carpetArea" + i).val();
							var flatNo = $("#flatNo" + i).val();
							var constructDateFlag = $("#ConstructFlag").val();
							var level = i + 1;
						}

						if (year == '' || year == undefined || year == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectYearlevel')
											+ " " + level);
						}

						if (floorNo == '0' || floorNo == undefined) {
							errorList
									.push(getLocalMessage('unitdetails.selectFloorNo')
											+ " " + level);
						}

						if (constructDateFlag != null
								&& constructDateFlag == 'Y') {
							if (yearOfConstruc == ''
									|| yearOfConstruc == undefined) {
								errorList
										.push(getLocalMessage('unitdetails.selectConstructionCompletionDate')
												+ " " + level);
							}
						}

						if (usageType1 == '0' || usageType1 == undefined) {
							errorList
									.push(getLocalMessage('unitdetails.selectUsageType')
											+ " " + level);
						}
						if ((usageType2 != undefined) && usageType2 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubUsageType')
											+ " " + level);
						}
						if ((usageType3 != undefined) && usageType3 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubUsageType')
											+ " " + level);
						}
						if ((usageType4 != undefined) && usageType4 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubUsageType')
											+ " " + level);
						}
						if ((usageType5 != undefined) && usageType5 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubUsageType')
											+ " " + level);
						}
						if (ConstruType == '0' || ConstruType == undefined) {
							errorList
									.push(getLocalMessage('unitdetails.selectConstructionType')
											+ " " + level);
						}
						if (billMethod != 'W') {
							if (flatNo == '' || flatNo == undefined) {
								errorList
										.push(getLocalMessage('unitdetails.flatNoValid')
												+ " " + level);
							}
						}

						if (taxableArea == '' || taxableArea == undefined)
							errorList
									.push(getLocalMessage('unitdetails.selectTaxableArea')
											+ " " + level);
						if (carpetArea == '' || carpetArea == undefined)
							errorList
									.push(getLocalMessage('unitdetails.selectCarpetArea')
											+ " " + level);
						if (carpetArea <= 0)
							errorList
									.push(getLocalMessage('unitdetails.selectValidCarpetArea')
											+ " " + level);
						if (taxableArea <= 0)
							errorList
									.push(getLocalMessage('unitdetails.selectValidTaxableArea')
											+ " " + level);

					});

	$('.secondUnitRow')
			.each(
					function(j) {
						if (rowCount <= 2) {

							var occupancyType = $("#assdOccupancyType").val();
							var natureOfProperty1 = $("#natureOfProperty1")
									.val();
							var natureOfProperty2 = $("#natureOfProperty2")
									.val();
							var natureOfProperty3 = $("#natureOfProperty3")
									.val();
							var natureOfProperty4 = $("#natureOfProperty4")
									.val();
							var natureOfProperty5 = $("#natureOfProperty5")
									.val();
							var level = 1;
						} else {

							var occupancyType = $("#assdOccupancyType" + j)
									.val();
							var utp = j;
							if (j > 0) {
								utp = j * 6;
							}
							var natureOfProperty1 = $("#natureOfProperty" + utp)
									.val();
							var natureOfProperty2 = $(
									"#natureOfProperty" + (utp + 1)).val();
							var natureOfProperty3 = $(
									"#natureOfProperty" + (utp + 2)).val();
							var natureOfProperty4 = $(
									"#natureOfProperty" + (utp + 3)).val();
							var natureOfProperty5 = $(
									"#natureOfProperty" + (utp + 4)).val();
							var level = j + 1;
						}

						if (constructDateFlag != null
								&& constructDateFlag == 'Y') {
							if (occupancyType == '0'
									|| occupancyType == undefined) {
								errorList
										.push(getLocalMessage('unitdetails.selectOccupacyType')
												+ " " + level);
							}
						}
						if (natureOfProperty1 == '0'
								|| natureOfProperty1 == undefined) {
							errorList
									.push(getLocalMessage('unitdetails.selectNatureOfProperty')
											+ " " + level);
						}
						if ((natureOfProperty2 != undefined)
								&& natureOfProperty2 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')
											+ " " + level);
						}
						if ((natureOfProperty3 != undefined)
								&& natureOfProperty3 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')
											+ " " + level);
						}
						if ((natureOfProperty4 != undefined)
								&& natureOfProperty4 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')
											+ " " + level);
						}
						if ((natureOfProperty5 != undefined)
								&& natureOfProperty5 == '0') {
							errorList
									.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')
											+ " " + level);
						}
					});
	return errorList;
}

function datePickerLogic() {
	var yearOfAcq = $("#assAcqDate").val();
	$(".datepicker2").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : yearOfAcq
	});

	$('.lessthancurrdate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
}

function reOrderUnitTabIdSequence(classNameFirst,classNamesecond) {

		var assType = "N";
		var ConstructFlag =  "Y";
		var noOfDetailRows = $("#noOfDetailRows").val();
				$(classNameFirst).each(function(i) {
				// re-ordering id 	
				$(".datepicker2").removeClass("hasDatepicker");
				$(".lessthancurrdate").removeClass("hasDatepicker");
				var incr=i+1;
				$(this).find("select:eq(0)").attr("id", "year"+i);
				$(this).find("input:hidden:eq(0)").attr("id", "hiddenYear"+i);
				$(this).find("input:hidden:eq(0)").attr("onchange", "compareDate("+(i)+")");			
				$(this).find("select:eq(0)").attr("onchange", "unitCount("+(i)+")");
				$(this).find("input:text:eq(0)").attr("id", "unitNo"+i);
//				$(this).find("select:eq(1)").attr("id", "assdUnitTypeId"+i);
				$(this).find("select:eq(1)").attr("id", "assdFloorNo"+i);
				if(ConstructFlag != null && ConstructFlag == 'Y'){
					$(this).find("input:text:eq(1)").attr("id", "flatNo"+i);
					$(this).find("input:text:eq(2)").attr("id", "yearOfConstruc"+i);
					if(assType == 'C' && assType != 'BIF'){
						$(this).find("input:text:eq(2)").attr("id", "lastAssesmentDate"+i);
						$(this).find("input:hidden:eq(1)").attr("id", "hiddenFirstAssesmentDate"+i);
						
						//$(this).find("input:text:eq(2)").attr("id", "lastAssesmentDate"+i).prop("readonly", true);
					}else if(assType != 'BIF'){					
						$(this).find("input:text:eq(3)").attr("id", "firstAssesmentDate"+i);
					}				
					$(this).find("input:text:eq(1)").attr("onchange", "successErrorCheck("+(i)+")");
					$(this).find("input:text:eq(2)").attr("onchange", "successErrorCheck("+(i)+")");
					if(assType != 'BIF'){				
						$(this).find("input:text:eq(4)").attr("id", "carpetArea"+i);
						$(this).find("input:text:eq(5)").attr("id", "taxableArea"+i);
						$(this).find("input:text:eq(6)").attr("id", "constructPermissionNo"+i);
						$(this).find("input:text:eq(7)").attr("id", "permissionUseNo"+i);
						$(this).find("input:text:eq(8)").attr("id", "assessmentRemark"+i);
					}else{
						//$(this).find("input:text:eq(2)").attr("id", "flatNo"+i);
						$(this).find("input:text:eq(3)").attr("id", "carpetArea"+i);
						$(this).find("input:text:eq(4)").attr("id", "taxableArea"+i);
						$(this).find("input:text:eq(5)").attr("id", "constructPermissionNo"+i);
						$(this).find("input:text:eq(6)").attr("id", "permissionUseNo"+i);
						$(this).find("input:text:eq(7)").attr("id", "assessmentRemark"+i);
					}
				}else{				
					//$(this).find("input:text:eq(1)").attr("id", "yearOfConstruc"+i);
					if(assType == 'C' && assType != 'BIF'){
						$(this).find("input:text:eq(1)").attr("id", "lastAssesmentDate"+i);
						$(this).find("input:hidden:eq(1)").attr("id", "hiddenFirstAssesmentDate"+i);
						
						//$(this).find("input:text:eq(2)").attr("id", "lastAssesmentDate"+i).prop("readonly", true);
					}else if(assType != 'BIF'){				
						$(this).find("input:text:eq(1)").attr("id", "firstAssesmentDate"+i);//changed
					}
					
					//$(this).find("input:text:eq(1)").attr("onchange", "successErrorCheck("+(i)+")");
					$(this).find("input:text:eq(1)").attr("onchange", "successErrorCheck("+(i)+")");
					if(assType != 'BIF'){
						$(this).find("input:text:eq(2)").attr("id", "flatNo"+i);
						$(this).find("input:text:eq(3)").attr("id", "carpetArea"+i);
						$(this).find("input:text:eq(4)").attr("id", "taxableArea"+i);
						$(this).find("input:text:eq(5)").attr("id", "constructPermissionNo"+i);
						$(this).find("input:text:eq(6)").attr("id", "permissionUseNo"+i);
						$(this).find("input:text:eq(7)").attr("id", "assessmentRemark"+i);
					}else{
						$(this).find("input:text:eq(1)").attr("id", "flatNo"+i);
						$(this).find("input:text:eq(2)").attr("id", "carpetArea"+i);
						$(this).find("input:text:eq(3)").attr("id", "taxableArea"+i);
						$(this).find("input:text:eq(4)").attr("id", "constructPermissionNo"+i);
						$(this).find("input:text:eq(5)").attr("id", "permissionUseNo"+i);
						$(this).find("input:text:eq(6)").attr("id", "assessmentRemark"+i);
					}
				}
				
				$(this).find("select:eq(2)").attr("id", "assdConstruType"+i);		
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
				//if($("#checkDetail").prop('checked')==false){
					$("#unitNo"+i).val(incr);
				//}
				$(this).find("select:eq(0)").attr("name","");
				$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitNo");
				$(this).find("input:hidden:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].faYearId");
//				$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitTypeId");
				$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdFloorNo");
				if(ConstructFlag != null && ConstructFlag == 'Y'){
					
					$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].flatNo");
					$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdYearConstruction");
					
					if(assType == 'C' && assType != 'BIF'){
						$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].lastAssesmentDate");
						$(this).find("input:hidden:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].firstAssesmentDate");
					}else if(assType != 'BIF'){				
						$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].firstAssesmentDate");
					}
					if(assType != 'BIF'){
						//newly chnaged
						$(this).find("input:text:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].carpetArea");
						$(this).find("input:text:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
						$(this).find("input:text:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].constructPermissionNo");
						$(this).find("input:text:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].permissionUseNo");
						$(this).find("input:text:eq(8)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assessmentRemark");
					}else{
						//$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].flatNo");
						$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].carpetArea");
						$(this).find("input:text:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
						$(this).find("input:text:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].constructPermissionNo");
						$(this).find("input:text:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].permissionUseNo");
						$(this).find("input:text:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assessmentRemark");
					}
				} else{
		            // $(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdYearConstruction");
					
					if(assType == 'C' && assType != 'BIF'){
						$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].lastAssesmentDate");
						$(this).find("input:hidden:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].firstAssesmentDate");
					}else if(assType != 'BIF'){
						$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].firstAssesmentDate");
					}
					if(assType != 'BIF'){
						$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].flatNo");
						$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].carpetArea");
						$(this).find("input:text:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
						$(this).find("input:text:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].constructPermissionNo");
						$(this).find("input:text:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].permissionUseNo");
						$(this).find("input:text:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assessmentRemark");
					}else{
						$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].flatNo");
						$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].carpetArea");
						$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
						$(this).find("input:text:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].constructPermissionNo");
						$(this).find("input:text:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].permissionUseNo");
						$(this).find("input:text:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assessmentRemark");
					}
				}				
				
				$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdConstruType");
				$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype1");
				$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype2");
				$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype3");
				$(this).find("select:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype4");
				$(this).find("select:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype5");
				
				//Newly Added	
				if(assType != 'BIF'){
					$(this).find("input:checkbox:eq(0)").attr("id", "legal" + i);
					$(this).find("input:checkbox:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + i + "].legal");
				}else{
					$(this).find("input:checkbox:eq(0)").attr("id", "assdBifurcateNo" + i);
					$(this).find("input:checkbox:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + i + "].assdBifurcateNo");
					$(this).find("input:checkbox:eq(1)").attr("id", "legal" + i);
					$(this).find("input:checkbox:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + i + "].legal");
				}			
				$(this).find('.addRoomClass').attr("id", "addRoom" + i);
				$(this).find(".addRoomClass").attr("onclick", "addRoomDetails("+(i)+")");
				
				if(i>0)
				{					
					var a=i-1;
					$("#hiddenYear"+i).val($("#hiddenYear"+a).val());
					$("#year"+i).val($("#hiddenYear"+a).val());				
				}
				//		
				
				/*if(assType != 'BIF'){
					$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
				}else{
					$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
				}*/
				
				/*if(i>0)
				{
					if($("#checkDetail").prop('checked')==false){
						var a=i-1;
						$("#hiddenYear"+i).val($("#hiddenYear"+a).val());
						$("#year"+i).val($("#hiddenYear"+a).val());
					}
				}*/
				/*			
				var unitNoFact=$("#unitNo"+i).val();
				if($(".selectUnit option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined && unitNoFact==null){				
					$(".selectUnit").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
				}*/
				datePickerLogic();								
				
			});
			
			$(classNamesecond).each(function(j) {
				var utp=j;
				if(j>0){
				utp=j*6;
				}			
				$(this).find("select:eq(0)").attr("id", "assdOccupancyType"+j);
				$(this).find("input:text:eq(0)").attr("id", "occupierName"+j);		
				$(this).attr("id", "group-of-rows-"+j);				
				$(this).find("input:text:eq(1)").attr("id", "occupierNameReg"+j);		
				$(this).find("input:text:eq(2)").attr("id", "occupierMobNo"+j);		
				$(this).find("input:text:eq(3)").attr("id", "occupierEmail"+j);
				$(this).find("input:text:eq(4)").attr("id", "actualRent"+j);								
				$(this).find("select:eq(1)").attr("id", "natureOfProperty"+utp);
				$(this).find("select:eq(2)").attr("id", "natureOfProperty"+(utp+1));
				$(this).find("select:eq(3)").attr("id", "natureOfProperty"+(utp+2));
				$(this).find("select:eq(4)").attr("id", "natureOfProperty"+(utp+3));
				$(this).find("select:eq(5)").attr("id", "natureOfProperty"+(utp+4));
				$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdOccupancyType");
				$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierName");
				$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty1");
				$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty2");
				$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty3");
				$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty4");
				$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty5");				
				$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierNameReg");
				$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierMobNo");
				$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierEmail");
				$(this).find("input:text:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].actualRent");				
			});
	}

function addRoomDetails(count, element) {

	$('#countOfRow').val(count);
	var errorList = [];
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		
		var theForm = '#frmBillMethodFlats';
		var action = "BillingMethodAuthorization.html";
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = action + '?addRoomDetails';
		var returnData = __doAjaxRequest(URL, 'post', requestData, false,
				'html');

		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}
}

function saveRoomData(element){

	var errorList = [];
	errorList=validateRoomDetails(element,errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);	
	}else{
		var unitChecked=$('#unitChecked').val();
		var ownerType=$('#ownerType').val();
		var theForm = '#frmBillMethodAddRoom';
		var action="BillingMethodAuthorization.html";		
		var requestData = __serializeForm(theForm);
		var URL = action+'?saveRoomDetails';
		var returnData = __doAjaxRequest(URL, 'post', requestData, false, 'html');
		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);				
		reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');			
	}	
}

function backFromRoomdetails(element){

	var ownerType=$('#ownerType').val();
	var unitChecked=$('#unitChecked').val();	
	var action="BillingMethodAuthorization.html";
	var requestData = {};
	var URL = action+'?saveRoomDetails';
	var returnData = __doAjaxRequest(URL, 'post', requestData, false, 'html');

	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);		
	reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');	
}

function validateRoomDetails(element, errorList) {

	var area=0.0;	
	$('.thirdRowRoomDetails').each(function(i) {

		var rowCount = $('#roomDetailstable tr').length;
		if (rowCount <= 2) {
			var roomType = $('#roomType').val();			
		}else{
			var roomType = $('#roomType' + i).val();			
		}		
		var roomLength = $('#roomLength' + i).val();
		var roomWidth = $('#roomWidth' + i).val();
		
		if (errorList.length <= 0) {
			if (roomType == "") {
				errorList.push(getLocalMessage("property.valid.roomType"));
			}
			if (roomLength == "") {
				errorList.push(getLocalMessage("property.valid.roomLength"));
			}
			if (roomWidth == "") {
				errorList.push(getLocalMessage("property.valid.roomWidth"));
			}
		}else{
			return errorList;
		}
	});
	if (errorList.length == 0) {
		var carpetArea =parseFloat($('#carpetArea').val());
		$('.thirdRowRoomDetails').each(function(i) {
			var roomArea = $('#roomArea' + i).val();
			area += roomArea;
		});
		area=parseFloat(area);
		if (area > carpetArea) {
			errorList.push(getLocalMessage("property.valid.validroomArea"));
		}
	}
	return errorList;
}

$("#roomDetailstable").on('click','.addRoomDetails',function(){
	
	var countOfRow=$("#countOfRow").val();
	var content =	$("#roomDetailstable").find('tr:eq(1)').clone();
	$(this).closest("#roomDetailstable").append(content);
	content.find("select").attr("value", "");
	content.find("input:text").val("");
	reOrderRoomTableIdSequence(countOfRow);
	return false;
});

// to delete row
$("#roomDetailstable").on("click", '.delButton', function(e) {

	var errorList = [];
	var countOfRow=$("#countOfRow").val();
	var rowCount = $('#roomDetailstable tr').length;
	if (rowCount <= 2) {
		
		errorList.push(getLocalMessage('property.valid.firstRow'));
		displayErrorsOnPage(errorList);		
		return false;
	} else {

		var index= $(this).closest('#roomDetailstable tr').index();				
		var action="BillingMethodAuthorization.html";						
		var requestData = {
			"countOfRow" : countOfRow,
			"index":index
		};	
		$(this).closest('#roomDetailstable tr').remove();
		var URL = action+'?removeRoomDetails';
		var returnData = __doAjaxRequest(URL, 'post', requestData, false, 'json');			
		reOrderRoomTableIdSequence(countOfRow);
	}
});

function reOrderRoomTableIdSequence(countOfRow) {

	$('.thirdRowRoomDetails').each(function(i) {

			var j=countOfRow;
						
			$(this).find("input:text:eq(0)").attr("id", "roomNo" + i);
			$(this).find("select:eq(0)").attr("id", "roomType" + i);
			$(this).find("input:text:eq(1)").attr("id", "roomLength" + i);
			$(this).find("input:text:eq(2)").attr("id", "roomWidth" + i);
			$(this).find("input:text:eq(3)").attr("id", "roomArea" + i);
			
			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + j + "].roomDetailsDtoList[" + i + "].roomNo");
			$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + j + "].roomDetailsDtoList[" + i + "].roomType");
			$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + j + "].roomDetailsDtoList[" + i + "].roomLength");
			$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + j + "].roomDetailsDtoList[" + i + "].roomWidth");
			$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[" + j + "].roomDetailsDtoList[" + i + "].roomArea");
			
			var incr=i+1;
			$("#roomNo"+i).val(incr);
			
		});
}

function calculateArea() {
	$('.thirdRowRoomDetails').each(function(i) {

		var roomLength = $('#roomLength' + i).val();
		var roomWidth = $('#roomWidth' + i).val();
		if (roomLength != "" && roomWidth != "") {
			var num = roomLength * roomWidth;
			var result = num.toFixed(2);
			$('#roomArea' + i).val(result);
		} else {
			$('#roomArea' + i).val();
		}
	});
}

$("#unitDetailTable").on('click', '.remCF', function() {

	var inputId = this.id;
	var left_text = inputId.lastIndexOf("_");
	var deletedRowCount = inputId.substring(left_text + 1);
	var data = {
		"deletedRowCount" : deletedRowCount
	};
	var URL = 'BillingMethodAuthorization.html?deleteUnitTableRow';
	var unitCount = $("#unitNo" + deletedRowCount).val();
	var a = 0;
	var tableLength = $('#unitDetailTable tr').length;
	for (var j = 1; j < tableLength; j++) // table tr length
	{
		var unitNoFact = $("#unitNo" + j).val();
		if (unitCount == unitNoFact) {
			a++;
		}
	}
	if (a == 1) {
		$(".selectUnit option[value=" + unitCount + "]").remove();
	}
	var index = $(this).closest("tr").index();
	if (($("#unitDetailTable tr").length) > 3 && (index != 1)) {
		var tr = $(this).closest("tr");
		tr.add(tr.next()).remove();
		$('.secondUnitRow:last').addClass('in');
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		reOrderUnitTabIdSequence('.firstUnitRow', '.secondUnitRow');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}
});

function backToFirstPage(obj) {
	var requestData = {};
	var URL = 'BillingMethodAuthorization.html?backToEditForm';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}

function updateFlats(element) {

	var Yes = getLocalMessage("unitdetails.Yes");
	var No = getLocalMessage("Unitdetails.No");
	var message = "";
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	message = getLocalMessage("property.updateFlatsConfirmation");
	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' name="Yes" class= "btn btn-success" value=\''
			+ Yes
			+ '\'  id=\'btnNo\' onclick="confirmToProceeed()"/>  <input type=\'button\' name="No" class= "btn btn-success" value=\''
			+ No + '\'  id=\'btnNo\' onclick="closeConfirmBoxForm()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}

function confirmToProceeed(element) {
	var errorList = [];
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		$.fancybox.close();
	} else {
		var theForm = '#frmBillMethodFlats';
		var appId = $("input[name='lableValueDTO.applicationId']").val();
		var labelId = $("input[name='lableValueDTO.lableId']").val();
		var serviceId = $("#serviceId").val();
		var requestData = __serializeForm(theForm);
		var URL = 'BillingMethodAuthorization.html?updateFloorDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'json');
		if (returnData == "") {
			loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',
					appId, labelId, serviceId);
		} else {
			var errorList = [];
			errorList.push(returnData);
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			}
		}
		$.fancybox.close();
	}
}

function removeDisabled(classNameFirst, classNamesecond) {
	var noOfDetailRows = $("#noOfDetailRows").val();
	$(classNameFirst).each(function(i) {

		if (i > noOfDetailRows) {
			$("#assdFloorNo" + i).removeClass("disableFields");
			$("#assdConstruType" + i).removeClass("disableFields");
			$("#assdUsagetype" + (i * 6)).removeClass("disableFields");
			$("#assdUsagetype" + ((i * 6) + 1)).removeClass("disableFields");
		}
	});
	$(classNamesecond).each(
			function(j) {
				if (j > noOfDetailRows) {
					$("#assdOccupancyType" + j).removeClass("disableFields");
					$("#natureOfProperty" + (j * 6)).removeClass(
							"disableFields");
					$("#natureOfProperty" + ((j * 6) + 1)).removeClass(
							"disableFields");
					$("#occupierName" + j).removeClass("disableFields");
					$("#occupierNameReg" + j).removeClass("disableFields");
					$("#occupierMobNo" + j).removeClass("disableFields");
					$("#occupierEmail" + j).removeClass("disableFields");
					$("#actualRent" + j).removeClass("disableFields");
				}
			});
}