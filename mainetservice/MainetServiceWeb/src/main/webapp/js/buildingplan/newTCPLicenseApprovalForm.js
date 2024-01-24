/**
 * 
 */

$(document).ready(function() {
	$("#showCompanyApiHeading").hide();
		$("#itemDetails tbody tr.itemDetailClass").each(function(i) {
			 var subpurpose1 = $("#subpurpose1" + i).val();
			 if(subpurpose1 != undefined && subpurpose1 != "0"){
				 getSubpurpose2(i);
				 var hiddenSubPur2 = $("#selSubPurpose2" + i).val();
				 $("#subpurpose2" + i).val(hiddenSubPur2);
			 }
			 
		 });
		



		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != 'None' && 
				$('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != undefined) {
			$('#emburanceDiv').show();
		} else {
			$('#emburanceDiv').hide();
		}
		 toggleCollaborator();
		 toggleInsolvency();
		 toggleAppliedLand();
		 toggleAppliedLandCourt();
		 toggleAppliedRevenueRasta();
		 toggleWatercourse();
		 toggleAcquisitionStatus();
		 toggleCompactBlock();
		 toggleApproach();
		 toggleSiteApproachable();
		 toggleinternalCirculation();
		 toggleaccessNHSR();
		 toggleApproachAvailable();
		 togglePermitLine();
		 toggleFeaturePassing();
		 toggleAqusitionProceeding();
		 toggleReleaseOfLand();
		 toggleAdjoiningOwnLand();
		 toggleAdjoiningOtherLand();
		 toggleDeveloperConsent();
		 
	    

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]').change(function() {
	    	toggleEncumbrance();
	    });
		
	    function toggleEncumbrance() {
	        if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != 'No') {
	            $('#emburanceDiv').show();
	        } else {
	            $('#emburanceDiv').hide();
	        }
	    }
	    

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation"]').change(function() {
	    	toggleCollaborator();
	    });

	    function toggleCollaborator() {
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation"]:checked').val() == 'Yes') {
	    		$('#collaboratorDiv').show();
	        } else {
	            $('#collaboratorDiv').hide();
				
	       	}
	    	
	    }

	    

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv"]').change(function() {
	    	toggleInsolvency();
	    });

	    function toggleInsolvency(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv"]:checked').val() == 'Yes') {
	    		$('#insolvencyDiv').show();
	        } else {
	            $('#insolvencyDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand"]').change(function() {
	    	toggleAppliedLand();
	    });

	    function toggleAppliedLand(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand"]:checked').val() == 'No') {
	    		$('#appliedLandDiv').show();
	        } else {
	            $('#appliedLandDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd"]').change(function() {
	    	toggleAppliedLandCourt();
	    });

	    function toggleAppliedLandCourt(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd"]:checked').val() == 'Yes') {
	    		$('#appliedLandCourtDiv').show();
	        } else {
	            $('#appliedLandCourtDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta"]').change(function() {
	    	toggleAppliedRevenueRasta();
	    });

	    function toggleAppliedRevenueRasta(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta"]:checked').val() == 'Yes') {
	    		$('#revenueRastaDiv').show();
	        } else {
	            $('#revenueRastaDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse"]').change(function() {
	    	toggleWatercourse();
	    });

	    function toggleWatercourse(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse"]:checked').val() == 'Yes') {
	    		$('#watercourseDiv').show();
	        } else {
	            $('#watercourseDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus"]').change(function() {
	    	toggleAcquisitionStatus();
	    });

	    function toggleAcquisitionStatus(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus"]:checked').val() == 'Yes') {
	    		$('.acquisitionStatusDiv').show();
	        } else {
	            $('.acquisitionStatusDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock"]').change(function() {
	    	toggleCompactBlock();
	    });

	    function toggleCompactBlock(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock"]:checked').val() == 'No') {
	    		$('#compactBlockDiv').show();
	        } else {
	            $('#compactBlockDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr"]').change(function() {
	    	toggleApproach();
	    });

	    function toggleApproach(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr"]:checked').val() == 'Category-I approach') {
	    		$('#category1Div').show();
	    		$('#category2Div').hide();
	    		
	        } else if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr"]:checked').val() == 'Category-II approach') {
	        	$('#category2Div').show();
	            $('#category1Div').hide();
				
	       	}else{
	       		$('#category2Div').hide();
	            $('#category1Div').hide();
	        }
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR"]').change(function() {
	    	toggleSiteApproachable();
	    });

	    function toggleSiteApproachable(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR"]:checked').val() == 'Yes') {
	    		$('#siteApproachableDiv').show();
	        } else {
	            $('#siteApproachableDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR"]').change(function() {
	    	toggleinternalCirculation();
	    });

	    function toggleinternalCirculation(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR"]:checked').val() == 'Yes') {
	    		$('#internalCirculationDiv').show();
	        } else {
	            $('#internalCirculationDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="accessNHSR"]').change(function() {
	    	toggleaccessNHSR();
	    });

	    function toggleaccessNHSR(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag"]:checked').val() == 'Yes') {
	    		$('#accessNHSRDiv').show();
	        } else {
	            $('#accessNHSRDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther"]').change(function() {
	    	toggleApproachAvailable();
	    });

	    function toggleApproachAvailable(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther"]:checked').val() == 'Yes') {
	    		$('#approachAvailableDiv').show();
	        } else {
	            $('#approachAvailableDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine"]').change(function() {
	    	togglePermitLine();
	    });

	    function togglePermitLine(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine"]:checked').val() == 'Yes') {
	    		$('#permitLineDiv').show();
	        } else {
	            $('#permitLineDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature"]').change(function() {
	    	toggleFeaturePassing();
	    });

	    function toggleFeaturePassing(){
	    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature"]:checked').val() == 'Yes') {
	    		$('#featurePassingDiv').show();
	        } else {
	            $('#featurePassingDiv').hide();
				
	       	}
	    	
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr"]').change(function() {
	    	toggleAqusitionProceeding();
	    });
		
	    function toggleAqusitionProceeding() {
	        if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr"]:checked').val() == 'Yes') {
	            $('#aqusitionProceedingDiv').show();
	        } else {
	            $('#aqusitionProceedingDiv').hide();
	        }
	    }

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"]').change(function() {
	    	toggleReleaseOfLand();
	    });
		
	    function toggleReleaseOfLand() {
	        if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"]:checked').val() == 'Yes') {
	            $('#releaseOfLandDiv').show();
	        } else {
	            $('#releaseOfLandDiv').hide();
	        }
	    }

	    $('input[name="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA"]').change(function() {
	    	toggleAdjoiningOwnLand();
	    });
		
	    function toggleAdjoiningOwnLand() {
	        if ($('input[name="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA"]:checked').val() == 'Yes') {
	            $('#adjoiningOwnLandDiv').show();
	        } else {
	            $('#adjoiningOwnLandDiv').hide();
	        }
	    }

	    $('input[name="licenseApplicationMasterDTO.landCategoryDTO.eAppOL"]').change(function() {
	    	toggleAdjoiningOtherLand();
	    });
		
	    function toggleAdjoiningOtherLand() {
	        if ($('input[name="licenseApplicationMasterDTO.landCategoryDTO.eAppOL"]:checked').val() == 'Yes') {
	            $('#adjoiningOtherLandDiv').show();
	        } else {
	            $('#adjoiningOtherLandDiv').hide();
	        }
	    }

	    $('input[name="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv"]').change(function() {
	    	toggleDeveloperConsent();
	    });
		
	    function toggleDeveloperConsent() {
	        if ($('input[name="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv"]:checked').val() == 'Yes') {
	            $('#developerConsentDiv').show();
	        } else {
	            $('#developerConsentDiv').hide();
	        }
	    }
	    

	    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac"]').change(function() {
	   	 	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac"]:checked').val() == 'No') {
	   	 		$("#vacantRemark").addClass("required-control");
	   	   	}
	    	
	    });
	    
	    
	    var devTypeCode=$('#devType option:selected').attr('code');
		if(devTypeCode=="COM" || devTypeCode=="PAF" || devTypeCode =="0" || devTypeCode == undefined){
			$("#CINDiv").show();
			$("#companyDiv").show();
			$("#dateOfIncorporation").show();
			$("#commonDetails").show();
			$("#stakeholderDiv").show();
			$("#LLPDiv").hide();
			$("#nameDiv").hide();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").hide();
			$("#firmNameDiv").hide();
			if($("#companyDetailsAPIFlagId").val()=='Y'){
				$("#showCompanyApiHeading").show();
			}
		}else if(devTypeCode=="IND" || devTypeCode=="HUF"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").hide();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").hide();
			$("#nameDiv").show();
			$("#genderDiv").show();
			$("#dobDiv").show();
			$("#panNo").show();
			$("#firmNameDiv").hide();
			$("#showCompanyApiHeading").hide();
		}
		else if(devTypeCode=="PRF"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").hide();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").hide();
			$("#nameDiv").show();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").show();
			$("#firmNameDiv").hide();
			$("#showCompanyApiHeading").hide();
		}
		else if(devTypeCode=="LLP"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").show();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").show();
			$("#nameDiv").hide();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").hide();
			$("#firmNameDiv").show();
			$("#showCompanyApiHeading").hide();
		}
		$("#mcaCompanyData").hide();
});


function openNewLicenseForm(formUrl, actionParam, mode) {
	var divName = '.content-page';
	var id = null;
	var data = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function draftNewLicenseForm(formUrl, actionParam, mode, id) {
	var divName = '.content-page';
	var data = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

//to populate data in acquisition table function in JSP
var rowCountClick = 0;


function saveApplicationForm(element){	
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#applicantForm';
		var targetDivName = '#applicationPurpose';
		var elementData = null;
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicense', 'POST', requestData,
				false, '', 'html');
		$(divName).removeClass('ajaxloader');	
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#newLicenseParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#applicationPurpose-tab').removeClass('disabled');
				$('.nav li#applicationPurpose-tab').removeClass('link-disabled');
				$('.nav li#landSchedule-tab').addClass('disabled');
				$('.nav li#detailsOfLand-tab').addClass('disabled');
				$('.nav li#charges-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#applicationPurpose"]').tab('show');
			prepareDateTag();
		}	
 
}

function saveApplicationPurpose(element){	
	var errorList = [];
	errorList = validatePurposeInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#applicationPurpose';
		var targetDivName = '#landSchedule';
		var elementData = null;
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicensePurpose', 'POST', requestData,
				false, '', 'html');
		$(divName).removeClass('ajaxloader');	
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#newLicenseParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#landSchedule-tab').removeClass('disabled');
				$('.nav li#landSchedule-tab').removeClass('link-disabled');
				//$('.nav li#landSchedule-tab').addClass('disabled');
				$('.nav li#detailsOfLand-tab').addClass('disabled');
				$('.nav li#charges-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#landSchedule"]').tab('show');
			prepareDateTag();
		}	
 
}

function saveApplicationLand(element){	
	var errorList = [];
	//errorList = validateLandInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#landSchedule';
		var targetDivName = '#detailsOfLand';
		var elementData = null;
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicenseLandDetails', 'POST', requestData,
				false, '', 'html');
		$(divName).removeClass('ajaxloader');	
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#newLicenseParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#detailsOfLand-tab').removeClass('disabled');
				$('.nav li#detailsOfLand-tab').removeClass('link-disabled');
				$('.nav li#charges-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#detailsOfLand"]').tab('show');
			prepareDateTag();
		}	
 
}

function saveApplicationDetaisofLand(element){	
	var errorList = [];
	errorList = validateDetailsofLandInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#detailsOfLand';
		var targetDivName = '#chargesPage';
		var elementData = null;
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicenseDetailsOfLandAndGetCharges', 'POST', requestData,
				false, '', 'html');
		$(divName).removeClass('ajaxloader');	
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#newLicenseParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#charges-tab').removeClass('disabled');
				$('.nav li#charges-tab').removeClass('link-disabled');
			}
			$(''+parentTab+' a[href="#chargesPage"]').tab('show');
			prepareDateTag();
		}	
 
}


function saveFinalApp(obj){
	var errorList = [];
	//errorList = validateForm(errorList);
	
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Form Saved successfully",
				"NewTCPLicenseForm.html?proceedToPayment", 'saveform');
	} else {
		//$(".compalint-error-div").show();
		displayErrorsOnPage(errorList);
	}
	
	
}

$(function() {
	
$("#itemDetails").on('click', '.addItemCF', function() {
		
		var errorList = [];
		//errorList = validateitemDetailsTable(errorList);
		if (errorList.length == 0) {

			var content = $("#itemDetails").find('tr:eq(1)').clone();
			$("#itemDetails").append(content);

			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			//content.find("select:eq(1)").val(null);
			
		
			$('.error-div').hide();
			reOrderItemDetailsSequence(); // reorder id and Path
			hasNumber();
		} else {
			displayErrorsOnPage(errorList);
		}
	});

	$("#itemDetails").on('click', '.delButton', function() {

		if ($("#itemDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderItemDetailsSequence();
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});



		$("#surroundingDetail").on('click','.addCF',function(){
              
		var errorList = [];
		//errorList = validateOwnerDetailsTable(errorList);
		if (errorList.length == 0) {
			var content = $("#surroundingDetail").find('tr:eq(1)').clone();
			$("#surroundingDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}
	});
	
	$("#acqDetTable").on('click','.addAcqDet',function() {
	    var errorList = [];
	    // errorList = validateAcquisitionDetailsTable(errorList);

	    if (errorList.length == 0) {
	    	var rowCount = $("#acqDetTable tr").length - 1;
	        var newRow = $("#acqDetTable").find('tr:eq(1)').clone();
	        reOrderAcquisitionDetailsSequence(newRow, rowCount);// reorder id and Path
	        $("#acqDetTable").append(newRow);
	    	rowCountClick++;
	        calculateTotalArea();
	        $('.error-div').hide();
	        // Add any additional logic you need after adding a new row
	        return false;
	    } else {
	        displayErrorsOnPage(errorList);
	    }
	});
	
	
$(function() {
		
		$("#acqDetTable").on('click', '.delButton', function() {
			

			if ($("#acqDetTable tr").length != 2) {
				$(this).parent().parent().remove();
				reOrderAcquisitionDetailsSequence(); // reorder id and Path
				rowCountClick--;
				calculateTotalArea();
			} else {
				var errorList = [];
				errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
				displayErrorsOnPage(errorList);
			}
		});
	});
	
	
});

function reOrderOwnerDetailsSequence(appendableClass) {
	
	$(appendableClass).each(
		function(i) 
		{
			
			// id binding
			$(this).find("input:text:eq(0)").attr("id", "pocketName" + i);
			$(this).find("input:text:eq(1)").attr("id", "north" + i);
			$(this).find("input:text:eq(2)").attr("id", "south" + i);
			$(this).find("input:text:eq(3)").attr("id", "east" + i);
			$(this).find("input:text:eq(4)").attr("id", "west"+i);

			
			$(this).find("input:text:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].pocketName");
			$(this).find("input:text:eq(1)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].north");
			$(this).find("input:text:eq(2)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].south");
			$(this).find("input:text:eq(3)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].east");
			$(this).find("input:text:eq(4)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].west");
		

		});
}

function reOrderItemDetailsSequence() {
    $("#itemDetails tbody tr.itemDetailClass").each(function(i) {
       

        $(this).find("select:eq(0)").attr("id", "subpurpose1" + i);
        $(this).find("select:eq(1)").attr("id", "subpurpose2" + i);
        $(this).find("select:eq(2)").attr("id", "far" + i);

        $(this).find("input:text:eq(0)").attr("id", "area" + i);

        $(this).find("select:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].applicationPurpose2").attr("onchange", "getSubpurpose2(" + i +")");
        $(this).find("select:eq(1)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].applicationPurpose3");
        $(this).find("select:eq(2)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].far");
        $(this).find("input:text:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].area");
    });
}

function reOrderAcquisitionDetailsSequence(newRow, rowCount) {
	
    var fields = ["district", "devPlan", "zone", "sector", "thesil", "revEstate", "hadbastNo", "rectangleNo",
        "khasraNo", "min", "landOwnerName", "landType", "chInfo", "mustilCh", "khasaraCh", "landOwnerMUJAM",
        "devColab", "devCompName", "collabAgrDate", "collabAgrRev", "authSignLO", "authSignDev", "regAuth",
        "collabDec", "brLO", "brDev", "collabAgrDoc", "consolType", "kanal", "marla", "sarsai", "bigha", "biswa",
        "biswansi", "acqStat", "consolTotArea", "nonConsolTotArea"];

    fields.forEach(function (field, index) {
    	
        var input = newRow.find("input:text:eq(" + index + ")");
        input.attr("id", field + rowCount);
        input.attr("name", "licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[" + rowCount + "]." + field);
        input.attr("readonly", "true");
    });
    
}

function hasNumber() {
	$('.hasNumber').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
}

function getSubpurpose2(id) {

	subpurpose1 = $('#subpurpose1' + id).val();

	var requestData = {
		"subpurpose1" : subpurpose1
	};
	var URL = 'NewTCPLicenseForm.html?getSubPurpose2List';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');

	var subPurpose2Select = $('#subpurpose2' + id);
	subPurpose2Select.empty();

	// Append a default option
	subPurpose2Select.append('<option value="">Select</option>');
	for (var i = 0; i < returnData.length; i++) {
		var lookUp = returnData[i];
		subPurpose2Select.append('<option value="' + lookUp.lookUpId + '">'
				+ lookUp.lookUpDesc + '</option>');
	}

}

function getTehsilList(){debugger;
	
	var district = $("#ddz1").val();
	
	var requestData = {
			"districtId" : district
	};
	
	var URL = 'NewTCPLicenseForm.html?getTehsilListByDistId';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var tehsilList = returnData;

    $('#khrsThesil').empty();

    $('#khrsThesil').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < tehsilList.length; i++) {
        $('#khrsThesil').append($('<option>', {
            value: tehsilList[i].code, // Adjust this based on your tehsil object structure
            text: tehsilList[i].name // Adjust this based on your tehsil object structure
        }));
    }
	
}


function getVillageList(){
	
	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil
	};
	
	var URL = 'NewTCPLicenseForm.html?getVillagesList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var tehsilList = returnData;

    $('#khrsRevEst').empty();

    $('#khrsRevEst').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < tehsilList.length; i++) {
        $('#khrsRevEst').append($('<option>', {
            value: tehsilList[i].code, // Adjust this based on your tehsil object structure
            text: tehsilList[i].name // Adjust this based on your tehsil object structure
        }));
    }
	
}

function getMurabaList(){
	
	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	var khrsRevEst = $("#khrsRevEst").val();
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil,
			"NVCode": khrsRevEst
	};
	
	var URL = 'NewTCPLicenseForm.html?getMurabaByNVCODE';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var murabaData = returnData.must;

    $('#khrsMustil').empty();

    $('#khrsMustil').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < murabaData.length; i++) {
        $('#khrsMustil').append($('<option>', {
            value: murabaData[i],
            text: murabaData[i]// Adjust this based on your tehsil object structure
        }));
    }
	
	
}

function getKhasraList(){
	
	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	var khrsRevEst = $("#khrsRevEst").val();
	var khrsMustil = $("#khrsMustil").val();
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil,
			"NVCode": khrsRevEst,
			"muraba": khrsMustil
	};
	
	var URL = 'NewTCPLicenseForm.html?getKhasraListByNVCODE';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var murabaData = returnData.must;

	var tehsilList = returnData;

    $('#khrsKilla').empty();

    $('#khrsKilla').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < tehsilList.length; i++) {
        $('#khrsKilla').append($('<option>', {
            value: tehsilList[i].khewats, // Adjust this based on your tehsil object structure
            text: tehsilList[i].killa // Adjust this based on your tehsil object structure
        }));
        
    }
	
	
}

function getOwnerData(){

	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	var khrsRevEst = $("#khrsRevEst").val();
	var khrsKilla = $("#khrsKilla").val();
	
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil,
			"NVCode": khrsRevEst,
			"_Khewat": khrsKilla
	};
	
	var URL = 'NewTCPLicenseForm.html?getOwnersbykhewatOnline';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var tehsilList = returnData;
	
	$('#tcpNameOfLO').val("");
	
	var names = ""; 
	
	
	// Add options for each tehsil in the returned list
	for (var i = 0; i < tehsilList.length; i++) {
		names += tehsilList[i].name;
	}
	
	$('#tcpNameOfLO').val(names);
	$("#landOwnerName" + rowCountClick).val(names);
	

}

function validateApplicantInfo(errorList){
	 if (!$('#confirmCheckbox').is(':checked')) {
		 errorList.push(getLocalMessage("Please confirm if Information fetched from developer registration is updated"));
	 }
	 
	 return errorList;
}

function validatePurposeInfo(errorList){
	
	var appPAppType = $("#appPAppType").val();
	var purpose = $("#appPLicPurposeId").val();
	var district = $("#ddz1").val();
	var developmentPlan = $("#ddz2").val();
	var zone = $("#ddz3").val();
	var sector = $("#ddz4").val();
	var tehsil = $("#khrsThesil").val();
	var revEstate = $("#khrsRevEst").val();
	var hadbastNo = $("#khrsHadbast").val();
	var mustil = $("#khrsMustil").val();
	var khrsKilla = $("#khrsKilla").val();
	var khrsDevCollabValue = $('input[name="licenseApplicationMasterDTO.khrsDevCollab"]:checked').val();
	var khrsLandTypeId = $('#khrsLandTypeId').val();
	var changeInfoChecked = $('#changeInfo').is(':checked');
	var consolTypeValue = $('input[name="licenseApplicationMasterDTO.ciConsType"]:checked').val();
	var aquStatus = $('#ciAquStatus').val();
	
	if(appPAppType=="" || appPAppType==null || appPAppType=="0"){
		errorList.push(getLocalMessage("Please select Application type"));
	}
	
	if(purpose=="" || purpose==null || purpose=="0"){
		errorList.push(getLocalMessage("Please select Purpose type"));
	}
	
	if(district=="" || district==null || district=="0"){
		errorList.push(getLocalMessage("Please select District"));
	}
	
	if(developmentPlan=="" || developmentPlan==null || developmentPlan=="0"){
		errorList.push(getLocalMessage("Please select Development plan"));
	}
	
	if(zone=="" || zone==null || zone=="0"){
		errorList.push(getLocalMessage("Please select Zone"));
	}
	
	if(sector=="" || sector==null || sector=="0"){
		errorList.push(getLocalMessage("Please select Sector"));
	}
	
	if(tehsil=="" || tehsil==null || tehsil==undefined){
		errorList.push(getLocalMessage("Please select Tehsil"));
	}

	if(revEstate=="" || revEstate==null || revEstate==undefined){
		errorList.push(getLocalMessage("Please select Revenue estate"));
	}
	
	if(hadbastNo=="" || hadbastNo==null || hadbastNo==undefined){
		errorList.push(getLocalMessage("Please enter Hadbast no."));
	}
	
	if(mustil=="" || mustil==null || mustil== undefined){
		errorList.push(getLocalMessage("Please select Rectangle/Mustil"));
	}
	
	if(khrsKilla=="" || khrsKilla==null || khrsKilla== undefined){
		errorList.push(getLocalMessage("Please select Khasra/Killa"));
	}
	
	if (khrsDevCollabValue === undefined || khrsDevCollabValue === null || khrsDevCollabValue === "") {
	    errorList.push(getLocalMessage("Please select whether Khasra has been developed in collaboration"));
	}
	
	if(khrsDevCollabValue == "Y"){
		
		var khrsDevComName = $('#khrsDevComName').val();
		if (khrsDevComName === "" || khrsDevComName === null || khrsDevComName === undefined) {
		    errorList.push(getLocalMessage("Please enter the Name of the developer company"));
		}

		var khrsColabRegDate = $('#khrsColabRegDate').val();
		if (khrsColabRegDate === "" || khrsColabRegDate === null || khrsColabRegDate === undefined) {
		    errorList.push(getLocalMessage("Please enter the Date of registering collaboration agreement"));
		}

		var isIrrevocable = $('input[name="isIrrevocable"]:checked').val();
		if (isIrrevocable === undefined || isIrrevocable === null || isIrrevocable === "") {
		    errorList.push(getLocalMessage("Please select whether collaboration agreement is irrevocable"));
		}

		var authorizedSignatoryOwner = $('#authorizedSignatoryOwner').val();
		if (authorizedSignatoryOwner === "" || authorizedSignatoryOwner === null || authorizedSignatoryOwner === undefined) {
		    errorList.push(getLocalMessage("Please enter the Name of authorized signatory on behalf of land owner(s)"));
		}

		var khrsAurSignDev = $('#khrsAurSignDev').val();
		if (khrsAurSignDev === "" || khrsAurSignDev === null || khrsAurSignDev === undefined) {
		    errorList.push(getLocalMessage("Please enter the Name of authorized signatory on behalf of developer"));
		}
		
		
		var khrsRegAuth = $('#khrsRegAuth').val();
		if (khrsRegAuth === "" || khrsRegAuth === null || khrsRegAuth === undefined) {
		    errorList.push(getLocalMessage("Please enter the Registering Authority"));
		}

		
	}
	
	if (khrsLandTypeId === "" || khrsLandTypeId === null || khrsLandTypeId === undefined) {
	    errorList.push(getLocalMessage("Please select the Type of land"));
	}
	
	if(changeInfoChecked){
		
		var recMustChangeValue = $('#recMustChange').val();
		if (recMustChangeValue === null || recMustChangeValue === undefined || recMustChangeValue.trim() === "") {
		    errorList.push(getLocalMessage("Please enter Rectangle No./Mustil(Changed)"));
		}

		var khasNoChangeValue = $('#khasNoChange').val();
		if (khasNoChangeValue === null || khasNoChangeValue === undefined || khasNoChangeValue.trim() === "") {
		    errorList.push(getLocalMessage("Please enter Khasra number(Changed)"));
		}

		var loNameMutValue = $('#LoNameMut').val();
		if (loNameMutValue === null || loNameMutValue === undefined || loNameMutValue.trim() === "") {
		    errorList.push(getLocalMessage("Please enter Name of the Land Owner as per Mutation/Jamabandi"));
		}
	}
	
	if (consolTypeValue === undefined || consolTypeValue === null || consolTypeValue === "") {
	    errorList.push(getLocalMessage("Please select Consolidation Type "));
	}
	
	if(consolTypeValue != null){
		if(consolTypeValue ==="C"){
			var ciConsTypeKanalValue = $('#ciConsTypeKanal').val();
			if (ciConsTypeKanalValue === null || ciConsTypeKanalValue === undefined || ciConsTypeKanalValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Kanal"));
			}

			var ciConsTypeMarlaValue = $('#ciConsTypeMarla').val();
			if (ciConsTypeMarlaValue === null || ciConsTypeMarlaValue === undefined || ciConsTypeMarlaValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Marla"));
			}

			var ciConsTypeSarsaiValue = $('#ciConsTypeSarsai').val();
			if (ciConsTypeSarsaiValue === null || ciConsTypeSarsaiValue === undefined || ciConsTypeSarsaiValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Sarsai"));
			}
		}else if(consolTypeValue ==="NC"){
			
			var nonConsTypeId = $('input[name="licenseApplicationMasterDTO.ciNonConsTypeId"]:checked').val();
			if (nonConsTypeId === undefined || nonConsTypeId === null || nonConsTypeId.trim() === "") {
			    errorList.push(getLocalMessage("Please select Non Consolidation Type"));
			}

			var bighaNonConsolidationValue = $('#bighaNonConsolidation').val();
			if (bighaNonConsolidationValue === null || bighaNonConsolidationValue === undefined || bighaNonConsolidationValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Bigha"));
			}

			var biswaNonConsolidationValue = $('#biswaNonConsolidation').val();
			if (biswaNonConsolidationValue === null || biswaNonConsolidationValue === undefined || biswaNonConsolidationValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Biswa"));
			}

			var biswansiNonConsolidationValue = $('#biswansiNonConsolidation').val();
			if (biswansiNonConsolidationValue === null || biswansiNonConsolidationValue === undefined || biswansiNonConsolidationValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Biswansi"));
			}
		}
	}
	
	if(aquStatus=="" || aquStatus==null || aquStatus==undefined){
		errorList.push(getLocalMessage("Please enter Aquistion Status"));
	}
	
	return errorList;
}

function validateDetailsofLandInfo(errorList){
	
	$("#itemDetails tbody tr.itemDetailClass").each(function(i) {
		
		var purpose = $("#subpurpose1" + i).val();
		var subpurpose = $("#subpurpose2" + i).val();
		var far = $("#far" + i).val();
		var area = $("#area" + i).val();
		var row = i + 1;
		if(purpose=="" || purpose==null || purpose==undefined){
			errorList.push(getLocalMessage("Please select component at :" + row));
		}
		
		if(subpurpose=="" || subpurpose==null || subpurpose==undefined){
			errorList.push(getLocalMessage("Please select sub component at :" + row));
		}
		
		if(far=="" || far==null || far==undefined){
			errorList.push(getLocalMessage("Please select far at :" + row));
		}
		
		if(area=="" || area==null || area==undefined){
			errorList.push(getLocalMessage("Please select area at :" + row));
		}
		
	});
	
	return errorList;
	
}

function onlinePaymentReceipt(){
	var requestData = {
			 
			"applicationNo" : $("#applicationNo").val()
		};
	/*var data = $('#applicantInfoFormView').serialize();*/
	var URL = 'NewTCPLicenseForm.html?onlinePaymentReceipt';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	window.open(returnData, '_blank');
}

function handleEditClick(button) {
    var rowIndex = $(button).closest('tr').index();
    rowCountClick = rowIndex;
    alert('Button in row ' + (rowIndex) + ' clicked.');
}
function fetchCinDetails(element){
	var msgList = [];
	var cinNo = $("#cinNoId").val();	
	if (cinNo!=null) {
		$(".error-div").html('');	
		var requestData = {
			"cinNo":cinNo	
		};
		var cinDataResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?fetchCinDataOnNewLicense', 'POST', requestData,
				false);
		if(cinDataResponse.apiStatus){
			$("#mcaCompanyData").show();
			$("#cinNoAApiId").val(cinDataResponse.cin);
			$("#companyNameApiId").val(cinDataResponse.companyName);
			$("#dateOfIncorporationIdApiId").val(cinDataResponse.incorporationDate);
			$("#registeredAddressApiId").val(cinDataResponse.registeredAddress);
			$("#emailIdApiId").val(cinDataResponse.email);
			$("#mobNoIdApiId").val(cinDataResponse.registeredContactNo);
		}else{
			$("#mcaCompanyData").hide();
			msgList.push(cinDataResponse.errorMsg);
			displayMsg(msgList);
		}
		
		var directorDataResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?fetchDirectorDataOnNewLicense', 'POST', requestData,
				false, 'json');
		if(directorDataResponse.length>0){
			$.each(directorDataResponse, function(index, data){				
				$('#directorsInfoMCATable tbody').append(
						'<tr><td>'+parseInt(index+1)+'</td><td>'+data.din+'</td><td>'+data.name+'</td><td>'+data.contactNumber+'</td></tr>'
					);
			});
		}
	}
}

function displayMsg(msgList){
	var alertMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeAlertBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(msgList, function(index) {
		alertMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + msgList[index] + '</li>';
	});
	alertMsg += '</ul>';			 
	$(".alert-msg-div").html(alertMsg);					
	$(".alert-msg-div").removeClass('hide')
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	msgList = [];	
	return false;
}
function closeAlertBox (){
	$("#alertMsgDiv").html('');
}