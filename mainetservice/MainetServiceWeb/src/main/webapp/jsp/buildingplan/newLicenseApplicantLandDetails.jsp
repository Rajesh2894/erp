<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script>

$(document).ready(function() {
	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != 'None' 
			&& $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != undefined) {
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
	 //toggleCompactBlock();
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
        if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != 'None') {
            $('#emburanceDiv').show();
        } else {
            $('#emburanceDiv').hide();
        }
    }
    
    $(".encumbDiv").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val() != 'None') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var encumbranceResponse = __doAjaxRequest('NewTCPLicenseForm.html?getEncumbranceCheckList', 'POST', requestData, false, 'html');
			$(divName).html(encumbranceResponse);
            $('#emburanceDiv').show();
        } else {
            $('#emburanceDiv').hide();
        }
	});
    
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
    
    $(".insolvencyClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv"]:checked').val() == 'Yes') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var InsolvencyCheckListResponse = __doAjaxRequest('NewTCPLicenseForm.html?getInsolvencyCheckList', 'POST', requestData, false, 'html');
			$(divName).html(InsolvencyCheckListResponse);
			$('#insolvencyDiv').show();
        } else {
        	$('#insolvencyDiv').hide();
        }
	});

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
    
    $(".spAppliedLandClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand"]:checked').val() == 'No') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var ShajraAppCheckListResponse = __doAjaxRequest('NewTCPLicenseForm.html?getShajraAppCheckList', 'POST', requestData, false, 'html');
			$(divName).html(ShajraAppCheckListResponse);
			$('#appliedLandDiv').show();
        } else {
        	 $('#appliedLandDiv').hide();
        }
	});

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
    
    $(".appliedLandCourtClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd"]:checked').val() == 'Yes') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var appliedLandCourtResponse = __doAjaxRequest('NewTCPLicenseForm.html?getCourtOrdersCheckList', 'POST', requestData, false, 'html');
			$(divName).html(appliedLandCourtResponse);
			$('#appliedLandCourtDiv').show();
        } else {
        	$('#appliedLandCourtDiv').hide();
        }
	});

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
    		prepareDateTag();
        } else {
            $('.acquisitionStatusDiv').hide();
			
       	}
    	
    }

   /*  $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock"]').change(function() {
    	toggleCompactBlock();
    }); */

    /* function toggleCompactBlock(){
    	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock"]:checked').val() == 'No') {
    		$('#compactBlockDiv').show();
        } else {
            $('#compactBlockDiv').hide();
			
       	}
    	
    } */

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

    $(".accessNHSRClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag"]:checked').val() == 'Yes') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var NHSRCheckListResponse = __doAjaxRequest('NewTCPLicenseForm.html?getNHSRCheckList', 'POST', requestData, false, 'html');
			$(divName).html(NHSRCheckListResponse);
			$('#accessNHSRDiv').show();
        } else {
        	$('#accessNHSRDiv').hide();
        }
	});
    
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
    
    $(".approachAvailableClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther"]:checked').val() == 'Yes') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var existingApprCheckListResponse = __doAjaxRequest('NewTCPLicenseForm.html?getExistingApprCheckList', 'POST', requestData, false, 'html');
			$(divName).html(existingApprCheckListResponse);
			$('#approachAvailableDiv').show();
        } else {
        	$('#approachAvailableDiv').hide();
        }
	});

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
    
    $(".excludedAqusitionProcClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr"]:checked').val() == 'Yes') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var ReleaseOrdCheckListResponse = __doAjaxRequest('NewTCPLicenseForm.html?getReleaseOrdCheckList', 'POST', requestData, false, 'html');
			$(divName).html(ReleaseOrdCheckListResponse);
			$('#aqusitionProceedingDiv').show();
        } else {
        	$('#aqusitionProceedingDiv').hide();
        }
	});


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

    $(".developerConsentClass").click(function() {
		if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.developerConsentFlag"]:checked').val() == 'Yes') {
        	var divName = '#landSchedule';
			var requestData = $('form').serialize();
			var UsageAllotteeCheckListResponse = __doAjaxRequest('NewTCPLicenseForm.html?getUsageAllotteeCheckList', 'POST', requestData, false, 'html');
			$(divName).html(UsageAllotteeCheckListResponse);
			$('#developerConsentDiv').show();
        } else {
        	 $('#developerConsentDiv').hide();
        }
	});
    
    $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac"]').change(function() {
   	 	if ($('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac"]:checked').val() == 'No') {
   	 		$("#vacantRemark").addClass("required-control");
   	   	}
    	
    });
	     
	});
</script>
<style>
#applicantLandInfoForm .radio-inline{
	padding-top: 13px;
}
</style>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantLandInfoForm"
					action="NewTCPLicenseForm.html" method="post"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>


					<h4>
						<spring:message code="" text="1. Encumbrance" />
					</h4>
					<c:set var="search" value="\\" />
					<c:set var="replace" value="\\\\" />
					<div class="form-group">
						<label class="control-label col-sm-3 required-control"> <spring:message
								code=""
								text="(a) Any encumbrance with respect to following"></spring:message>
						</label>
						<div class="col-sm-9">
							<label for="mortage" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Rehan / Mortgage" id="mortage" class="encumbDiv" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="encumbrance" /> <spring:message code="" text="Rehan / Mortgage" />
							</label> 
							<label for="patta" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Patta/Lease" id="patta" class="encumbDiv" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="encumbrance" /> <spring:message
									code="" text="Patta/Lease" />
							</label>
							<label for="gairmarusi" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Gairmarusi" id="gairmarusi" class="encumbDiv" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="encumbrance" /> <spring:message
									code="" text="Gairmarusi" />
							</label>
							<label for="loan" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Loan" id="loan" class="encumbDiv" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="encumbrance" /> <spring:message
									code="" text="Loan" />
							</label>
							<label for="other" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="Any Other" id="other" class="encumbDiv" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="encumbrance" /> <spring:message
									code="" text="Any Other" />
							</label>
							<label for="none" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb" value="None" id="none" class="encumbDiv" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="encumbrance" /> <spring:message
									code="" text="None" />
							</label>
						</div>
					</div>
					<div id="emburanceDiv">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" for="name">
								<spring:message code="" text="Remark" />
							</label>
							<div class="col-sm-4 ">
								<form:textarea id="emburanceRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumbRemarks"
									class="form-control mandColorClass" data-rule-maxLength="200" maxlength="45" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
									data-rule-required="true" />
							</div>

							<c:if test="${not empty command.encumbranceCheckList}">
								<c:set var="en" value="400" scope="page" />
								<c:forEach items="${command.encumbranceCheckList}"
									var="enLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
										code="" text="${enLookUp.doc_DESC_ENGL}" /></label>

									<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="encumbranceDocs_${lk}" class="col-sm-4">
											<apptags:formField fieldType="7" labelCode=""
												hasId="true"
												fieldPath="encumbranceCheckList[${lk.index}]"
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="TCPHR_MAX_FILE_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
												currentCount="${en}"
												folderName="encumbranceCheckList${en}" />
												<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
										</div>	
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.encumbranceDocumentList[0]}">
												<c:set var="filePath"
													value="${command.encumbranceDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.encumbranceDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
																		
									<c:set var="en" value="${en + 1}" scope="page" />
								</c:forEach>											
							</c:if>
						</div>

					</div>

					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="(b) Existing litigation, if any, concerning applied land including co-sharers and collaborator."></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="yes" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation" value="Yes" id="collaboratorYes" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="collaborator" /> <spring:message
									code="" text="Yes" />
							</label> <label for="no" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation" value="No" id="collaboratorNo" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="collaborator" /> <spring:message code=""
									text="No" />
							</label>
						</div>
					</div>
					
					<div id="collaboratorDiv">
					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="Court orders, if any, affecting applied land."></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="yes" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd" value="Yes" id="appliedLandCourtYes" class="appliedLandCourtClass" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="appliedLandCourt" /> <spring:message
									code="" text="Yes" />
							</label> <label for="no" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd" value="No" id="appliedLandCourtNo" class="appliedLandCourtClass" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="appliedLandCourt" /> <spring:message code=""
									text="No" />
							</label>
						</div>
						</div>
						
						<div id="appliedLandCourtDiv">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" >
								<spring:message code="" text="Remark Case No" />
							</label>
							<div class="col-sm-4 ">
								<form:textarea id="appliedLandCourtRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrdRemAse"
									class="form-control mandColorClass" data-rule-maxLength="200"
									data-rule-required="true"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
							</div>
							<c:if test="${not empty command.courtOrdersLandCheckList}">
								<c:set var="co" value="450" scope="page" />
								<c:forEach items="${command.courtOrdersLandCheckList}"
									var="coLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
										code="" text="${coLookUp.doc_DESC_ENGL}" /></label>																								
										
									<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="courtOrdersDocs_${lk}" class="col-sm-4">
											<apptags:formField fieldType="7" labelCode=""
												hasId="true"
												fieldPath="courtOrdersLandCheckList[${lk.index}]"
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="TCPHR_MAX_FILE_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
												currentCount="${co}"
												folderName="courtOrdersCheckList${co}" />
												
												<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
										</div>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.courtOrdersLandDocumentList[0]}">
												<c:set var="filePath"
													value="${command.courtOrdersLandDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.courtOrdersLandDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:set var="co" value="${co + 1}" scope="page" />
								</c:forEach>											
							</c:if>
						</div>

					</div>
					</div>
					
					
					
					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="(c) Any insolvency/liquidation proceedings against the Land Owing Company/Developer Company"></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="yes" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv" value="Yes" id="insolvencyYes" class="insolvencyClass" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="insolvency" /> <spring:message
									code="" text="Yes" />
							</label> <label for="no" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv" value="No" id="insolvencyNo" class="insolvencyClass" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="insolvency" /> <spring:message code=""
									text="No" />
							</label>
						</div>
					</div>
					
					<div id="insolvencyDiv">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" for="name">
								<spring:message code="" text="Remark" />
							</label>
							<div class="col-sm-4 ">
								<form:textarea id="insolvencyRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolvRemarks"
									class="form-control mandColorClass" data-rule-maxLength="200"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
							</div>
							<c:if test="${not empty command.insolvencyLandCheckList}">
								<c:set var="s" value="500" scope="page" />
								<c:forEach items="${command.insolvencyLandCheckList}"
									var="insLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
										code="" text="${insLookUp.doc_DESC_ENGL}" /></label>																								
										
									<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="insolvencyLandDocs_${lk}" class="col-sm-4">
											<apptags:formField fieldType="7" labelCode=""
												hasId="true"
												fieldPath="insolvencyLandCheckList[${lk.index}]"
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="TCPHR_MAX_FILE_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
												currentCount="${s}"
												folderName="insolvencyLandCheckList${s}" />
												
												<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
										</div>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.insolvencyLandDocumentList[0]}">
												<c:set var="filePath"
													value="${command.insolvencyLandDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.insolvencyLandDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:set var="s" value="${s + 1}" scope="page" />
								</c:forEach>											
							</c:if>
						</div>

					</div>

					
					<h4>
						<spring:message code="" text="2. Shajra Plan" />
					</h4>
					
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(a) As per applied land"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand" value="Yes" id="appliedYes" class="spAppliedLandClass" name="appliedLand"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand" value="No" id="appliedNo" class="spAppliedLandClass" name="appliedLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>

					<div id="appliedLandDiv">
						<div class="form-group">
							<c:if test="${not empty command.shajraAppLandCheckList}">
								<c:set var="a" value="550" scope="page" />
								<c:forEach items="${command.shajraAppLandCheckList}"
									var="saLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
										code="" text="${saLookUp.doc_DESC_ENGL}" /></label>																								
										
									<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="shajraAppLandDocs_${lk}" class="col-sm-4">
											<apptags:formField fieldType="7" labelCode=""
												hasId="true"
												fieldPath="shajraAppLandCheckList[${lk.index}]"
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="TCPHR_MAX_FILE_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
												currentCount="${a}"
												folderName="shajraAppLandCheckList${a}" />
												
												<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
										</div>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.shajraAppLandDocumentList[0]}">
												<c:set var="filePath"
													value="${command.shajraAppLandDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.shajraAppLandDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:set var="a" value="${a + 1}" scope="page" />
								</c:forEach>											
							</c:if>
						</div>
					</div>

					
					
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(b) Revenue rasta"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="revenueRasta" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta" value="Yes" id="revenueRastaYes" name="revenueRasta" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								 /> <spring:message code="" text="Yes" />
							</label> <label for="revenueRasta" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta" value="No" id="revenueRastaNo" name="revenueRasta" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>

					<div id="revenueRastaDiv">
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="Rasta Type"></spring:message>
						</label>
						<div class="col-sm-3">
							<label  class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTyp" value="Consolidated" id="consolidatedRastaYes" name="consolidatedRasta" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								 /> <spring:message code="" text="Consolidated" />
							</label> <label  class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTyp" value="Unconsolidated" id="consolidatedRastaNo" name="consolidatedRasta" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message
									code="" text="Unconsolidated" />
							</label>
						</div>
						
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Consolidated/Unconsolidated" /></label>
						<div class="col-sm-2">
						<c:set var="baseLookupCode" value="CON" />
							<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTypConId"
								cssClass="form-control mandColorClass" id="spRevRastaTypConId"
								disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
							</div>
						
						<label class="col-sm-1 control-label required-control" for=""><spring:message
								code="" text="Width" /></label>
						<div class="col-sm-2 ">
							<form:input name="" type="text"
								class="form-control" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTypConWid" id="spRevRastaTypConWid"
								maxlength="20" />
						</div>
						
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(c) Watercourse"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="watercourse" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse" value="Yes" id="watercourseYes" name="watercourse"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="watercourse" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse" value="No" id="watercourseNo" name="watercourse" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					
					<div id="watercourseDiv">
						<div class="form-group">
							
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Type" /></label>
						<div class="col-sm-2">
							<c:set var="baseLookupCode" value="CON" />
							<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourseTypId"
								cssClass="form-control mandColorClass" id="watercourseType" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
						
						<label class="col-sm-2 control-label required-control" for=""><spring:message
								code="" text="Width" /></label>
						<div class="col-sm-2 ">
							<form:input name="" type="text"
								class="form-control" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourseTypWid" id="spWatercourseTypWid"
								maxlength="20" />
						</div>
						
						
						<label class="col-sm-2 control-label required-control"> <spring:message
									code="" text="Remarks" /></label>
						<div class="col-sm-2 ">
							<form:textarea id="watercourseRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourseTypRem"
									class="form-control mandColorClass" data-rule-maxLength="200"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
						</div>
						
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="Original Shajra Plan by Patwari"></spring:message>
						</label>
						<div class="col-sm-4">
							<label for="" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandPatwari" value="Yes" id="OGShajraPlanYes" name="OGShajraPlan"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandPatwari" value="No" id="OGShajraPlanNo" name="OGShajraPlan" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
						
					</div>
					
					
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="Original Shajra plan showing Boundary of Applied Land"></spring:message>
						</label>
						<div class="col-sm-4">
							<label for="" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandBoundary" value="Yes" id="boundryAppliesLandYes" name="boundryAppliesLand"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandBoundary" value="No" id="boundryAppliesLandNo" name="boundryAppliesLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(d) Acquisition status"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="acquisitionStatus" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus" value="Yes" id="acquisitionStatusYes" name="acquisitionStatus"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="acquisitionStatus" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus" value="No" id="acquisitionStatusNo" name="acquisitionStatus" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					<div class="acquisitionStatusDiv">
						<div class="form-group">
						
							<label class="col-sm-2 radio1 control-label  required-control "><spring:message
									code="" text="Date of section 4 notification" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input
										class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
										placeholder="DD/MM/YYYY" autocomplete="off"
										id="spAcqStatusD4Not" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
										path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusD4Not"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							
							<label class="col-sm-2 radio1 control-label  required-control "><spring:message
									code="" text="Date of section 6 notification" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input
										class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
										placeholder="DD/MM/YYYY" autocomplete="off"
										id="spAcqStatusD6Not"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
										path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusD6Not"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							
							<label class="col-sm-2 radio1 control-label  required-control "><spring:message
									code="" text="Date of award" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input
										class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
										placeholder="DD/MM/YYYY" autocomplete="off"
										id="spAcqStatusDAward"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
										path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusDAward"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>
					
					<div class="acquisitionStatusDiv">
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(g) Whether Land Released/Excluded from aqusition proceeding"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="compactBlock" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr" value="Yes" id="excludedAqusitionProcYes" class="excludedAqusitionProcClass" name="excludedAqusitionProc" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
									 /> <spring:message code="" text="Yes" />
							</label> <label for="compactBlock" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr" value="No" id="excludedAqusitionProcNo" class="excludedAqusitionProcClass" name="excludedAqusitionProc" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message
									code="" text="No" />
							</label>
						</div>
						</div>
					</div>
					
					<div id="aqusitionProceedingDiv">
					
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="" text="Whether land compensation received"></spring:message>
							</label>
							<div class="col-sm-4">
								<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusLdComp"
										value="Yes" id="landCompensationYes" name="landCompensation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusLdComp"
										value="No" id="landCompensationNo" name="landCompensation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
							
							<label class="col-sm-2 control-label  required-control "><spring:message
									code="" text="Date of release" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input
										class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
										placeholder="DD/MM/YYYY" autocomplete="off" id="spAcqStatusRelDate" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
										path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelDate"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							
						</div>
						
						
						<div class="form-group">
							

							<label class="col-sm-2 control-label" for=""><spring:message
									code="" text="Add Remarks if any" /></label>
							<div class="col-sm-4 ">
								<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusSiteDet"
									id="siteDetials" maxlength="20" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
							</div>
						</div>
						
						
						<div class="form-group">
							<c:if test="${not empty command.releaseOrderCheckList}">
								<c:set var="r" value="600" scope="page" />
								<c:forEach items="${command.releaseOrderCheckList}"
									var="rLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
										code="" text="${rLookUp.doc_DESC_ENGL}" /></label>																								
										
										<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="releaseOrderDocs_${lk}" class="col-sm-4">
											<apptags:formField fieldType="7" labelCode=""
												hasId="true"
												fieldPath="releaseOrderCheckList[${lk.index}]"
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="TCPHR_MAX_FILE_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
												currentCount="${r}"
												folderName="releaseOrderCheckList${r}" />
												
												<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
										</div>	
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.releaseOrderDocumentList[0]}">
												<c:set var="filePath"
													value="${command.releaseOrderDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.releaseOrderDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>									
									<c:set var="r" value="${r + 1}" scope="page" />
								</c:forEach>											
							</c:if>

							<label class="control-label col-sm-2 required-control"> <spring:message
									code="" text="whether litigation regarding release of Land"></spring:message>
							</label>
							<div class="col-sm-4">
								<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"
										value="Yes" id="releaseOfLandYes" name="releaseOfLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
										code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"
										value="No" id="releaseOfLandNo" name="releaseOfLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
										code="" text="No" />
								</label>
							</div>

						</div>
						
						
						<div id="releaseOfLandDiv">
							<div class="form-group">
								<label class="col-sm-2 control-label" for=""><spring:message
										code="" text="CWP/SLP Number" /></label>
								<div class="col-sm-4 ">
									<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelCwpSl"
										id="cwpSlpNumber" maxlength="20" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
								</div>
							</div>
						</div>
						
					</div>

					

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(e) Whether in Compact Block"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="compactBlock" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock" value="Yes" id="compactBlockYes" name="compactBlock"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="compactBlock" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock" value="No" id="compactBlockNo" name="compactBlock" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					
					<div id="compactBlockDiv">
					
						<div class="form-group">
					
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Separated by" /></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="SEB" />
							<form:select path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlockSep"
								cssClass="form-control mandColorClass required-control" id="spCompactBlockSep" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
						
						<label class="col-sm-2 control-label" for=""><spring:message
								code="" text="Number of Pockets" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type="text"
								class="form-control hasNumber required-control"
								path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlockPkt" id="spCompactBlockPkt"
							maxlength="2" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
						
						</div>
						
					</div>
					
					

					<h4>
						<spring:message code="" text="3. Surroundings" />
					</h4>

					<div class="form-group">
						<div class="overflow margin-top-10">
							<div class="table-responsive">
								<c:set var="d" value="0" scope="page" />
								<table class="table table-striped table-bordered table-wrapper" id="surroundingDetail">
									<thead>

										<tr>
											<th class="text-center"><spring:message code=""
													text="Pocket Name" /></th>
											<th class="text-center"><spring:message code=""
													text="North" /></th>
											<th class="text-center"><spring:message code=""
													text="South" /></th>
											<th class="text-center"><spring:message code=""
													text="East" /></th>
											<th class="text-center"><spring:message code=""
													text="West" /></th>
											<c:if test="${command.saveMode ne 'V' }">
											<th class="text-center"><spring:message code=""
													text="Action" /></th>
											</c:if>
										</tr>
									</thead>
									<tbody>
				                    <c:choose>
				                        <c:when test="${empty command.licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList}">
				                            <tr class="appendableClass">
				                                <td class="text-center">
				                                    <form:input name="" type="text"
				                                        class="form-control"
				                                        path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].pocketName"
				                                        id="pocketName${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                </td>
				                                <td class="text-center">
				                                    <form:input name="" type="text"
				                                        class="form-control"
				                                        path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].north"
				                                        id="north${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                </td>
				                                <td class="text-center">
				                                    <form:input name="" type="text"
				                                        class="form-control"
				                                        path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].south"
				                                        id="south${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                </td>
				                                <td class="text-center">
				                                    <form:input name="" type="text"
				                                        class="form-control"
				                                        path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].east"
				                                        id="east${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                </td>
				                                <td class="text-center">
				                                    <form:input name="" type="text"
				                                        class="form-control"
				                                        path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].west"
				                                        id="west${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                </td>
				                                <c:if test="${command.saveMode ne 'V' }">
				                                    <td class="text-center">
				                                        <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" onclick=""
				                                            class="addCF btn btn-success btn-sm">
				                                            <i class="fa fa-plus-circle"></i>
				                                        </a>
				                                        <a href="javascript:void(0);" class="btn btn-danger btn-sm delButtonPocket" onclick="">
				                                            <i class="fa fa-minus"></i>
				                                        </a>
				                                    </td>
				                                </c:if>
				                            </tr>
				                        </c:when>
				                        <c:otherwise>
				                            <c:forEach var="dataList" items="${command.licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList}" varStatus="status">
				                                <tr class="appendableClass">
				                                    <td class="text-center">
				                                        <form:input name="" type="text"
				                                            class="form-control"
				                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].pocketName"
				                                            id="pocketName${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                    </td>
				                                    <td class="text-center">
				                                        <form:input name="" type="text"
				                                            class="form-control"
				                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].north"
				                                            id="north${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                    </td>
				                                    <td class="text-center">
				                                        <form:input name="" type="text"
				                                            class="form-control"
				                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].south"
				                                            id="south${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                    </td>
				                                    <td class="text-center">
				                                        <form:input name="" type="text"
				                                            class="form-control"
				                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].east"
				                                            id="east${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                    </td>
				                                    <td class="text-center">
				                                        <form:input name="" type="text"
				                                            class="form-control"
				                                            path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[${d}].west"
				                                            id="west${d}" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
				                                    </td>
				                                    <c:if test="${command.saveMode ne 'V' }">
				                                        <td class="text-center">
				                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" onclick=""
				                                                class="addCF btn btn-success btn-sm">
				                                                <i class="fa fa-plus-circle"></i>
				                                            </a>
				                                            <a href="javascript:void(0);" class="btn btn-danger btn-sm delButtonPocket" onclick="">
				                                                <i class="fa fa-minus"></i>
				                                            </a>
				                                        </td>
				                                    </c:if>
				                                </tr>
				                                <c:set var="d" value="${d + 1}" scope="page" />
				                            </c:forEach>
				                        </c:otherwise>
				                    </c:choose>
				                </tbody>
								</table>
							</div>
						</div>
					</div>
					
					<h4>
						<spring:message code="" text="4. Existing Approach" />
					</h4>
					
					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="(a) Details of existing approach as per policy dated 20-10-2020"></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="approach1" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr" value="Category-I approach" id="approachYes" name="approach" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Category-I approach" />
							</label>
							<label for="approach2" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr" value="Category-II approach" id="approachNo" name="approach" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message
									code="" text="Category-II approach" />
							</label>
						</div>
					</div>

					<div id="category1Div">
					
						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(a) Approach available from minimum 4 karam (22 ft) wide revenue rasta."></spring:message>
							</label>
							<div class="col-sm-7">
								<label for="approach1" class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.aAppWRevRas" value="Yes" id="minimum4karamYes" name="minimum4karam" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label for="approach2" class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.aAppWRevRas" value="No" id="minimum4karamNo" name="minimum4karam" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(b) Approach available from minimum 11 feet wide revenue rasta and applied site abuts acquired alignment of 
										the sector road and there is no stay regarding construction on the land falling under the abutting sector road"></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.bAppASR" value="Yes" id="abuttingSectorRoadYes" name="abuttingSectorRoad" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.bAppASR" value="No" id="abuttingSectorRoadNo" name="abuttingSectorRoad" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(c) Applied site abouts already constructed sector road or internal circulation road of approved sectoral plan (of min. 18m/24m width as the case may be) provided
										 its entire stretch required for approach is licenced and is further leading upto atleast 4 karam wide revenue rasta. "></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.cAppCSR" value="Yes" id="atleast4KaramYes" name="atleast4Karam" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.cAppCSR" value="No" id="atleast4KaramNo" name="atleast4Karam" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(d) Applied land is accessible from a minimum 4 karam wide rasta through adjoining own land of the applicant (but not applied for licence). "></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA" value="Yes" id="adjoiningOwnLandYes" name="adjoiningOwnLand"  disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes"/>
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA" value="No" id="adjoiningOwnLandNo" name="adjoiningOwnLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
									<spring:message code="" text="No"/>
								</label>
							</div>
						</div>
						
						<div id="adjoiningOwnLandDiv">
							<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(d1) If applicable, whether the applicant has donated at least 4 karam wide strip from its adjoining own land in favour of the Gram Panchayat/Municipality, in order to connect the applied site to existing 4 karam rasta?  "></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.d1AppAccGPM" value="Yes" id="applicantDonatedStripYes" name="applicantDonatedStrip" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.d1AppAccGPM" value="No" id="applicantDonatedStripNo" name="applicantDonatedStrip" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(e) Applied land is accessible from a minimum 4 karam wide rasta through adjoining other’s land"></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.eAppOL" value="Yes" id="adjoiningOtherLandYes" name="adjoiningOtherLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.eAppOL" value="No" id="adjoiningOtherLandNo" name="adjoiningOtherLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						
						<div id="adjoiningOtherLandDiv">
							<div class="form-group">
								<label class="control-label col-sm-5 required-control"> <spring:message
										code=""
										text="(e1) whether the land-owner of the adjoining land has donated at least 4 karam wide strip of land to the Gram Panchayat/Municipality, in a manner that the applied site gets 
											connected to existing public rasta of atleast 4 karam width?"></spring:message>
								</label>
								<div class="col-sm-7">
									<label class="radio-inline"> <form:radiobutton
											path="licenseApplicationMasterDTO.landCategoryDTO.e1AppOLGM" value="Yes" id="connectedExistingPublicYes" name="connectedExistingPublic" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
										<spring:message code="" text="Yes" />
									</label> <label class="radio-inline"> <form:radiobutton
											path="licenseApplicationMasterDTO.landCategoryDTO.e1AppOLGM" value="No" id="connectedExistingPublicNo" name="connectedExistingPublic" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
										<spring:message code="" text="No" />
									</label>
								</div>
							</div>
						</div>
						
						
					</div>
					<div id="category2Div">
						<div class="form-group">
						<label class="col-sm-2 sub-title control-label"><spring:message
								code="" text="(a) Enter Width in Meters" /></label>
						<div class="col-sm-4">
							<form:input name="" type="text"
								class="form-control hasDecimal"
								disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								path="licenseApplicationMasterDTO.landCategoryDTO.cat2Width" id="cat2Width"
								onkeypress="return hasAmount(event, this, 5, 2)" />
						</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="(b) Whether irrevocable consent from such developer/ colonizer for uninterrupted usage of such internal 
										road for the purpose of development of the colony by the applicant or by its agencies and for usage by its allottees submitted "></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv" value="Yes" id="developerConsentYes" class="developerConsentClass" name="developerConsent" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv" value="No" id="developerConsentNo" class="developerConsentClass" name="developerConsent" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>

						<div id="developerConsentDiv">
						
							<div class="form-group">
								<c:if test="${not empty command.usageAllotteesCheckList}">
									<c:set var="u" value="650" scope="page" />
									<c:forEach items="${command.usageAllotteesCheckList}"
										var="uLookUp" varStatus="lk">
										<label class="col-sm-2 control-label" for=""><spring:message
												code="" text="${uLookUp.doc_DESC_ENGL}" /></label>
										
										<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="usageAllotteesDocs_${lk}" class="col-sm-4">
											<apptags:formField fieldType="7" labelCode="" hasId="true"
												fieldPath="usageAllotteesCheckList[${lk.index}]"
												isMandatory="false" showFileNameHTMLId="true"
												fileSize="TCPHR_MAX_FILE_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
												currentCount="${u}" folderName="usageAllotteesCheckList${u}" />
												
												<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
										</div>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.usageAllotteesDocumentList[0]}">
												<c:set var="filePath"
													value="${command.usageAllotteesDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.usageAllotteesDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
										<c:set var="u" value="${u + 1}" scope="page" />
									</c:forEach>
								</c:if>
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-sm-5 required-control"> <spring:message
									code=""
									text="Access from NH/SR"></spring:message>
							</label>
							<div class="col-sm-7">
								<label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag" value="Yes" id="accessNHSRYes" class="accessNHSRClass" name="accessNHSR" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton
										path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag" value="No" id="accessNHSRNo" class="accessNHSRClass" name="accessNHSR" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						
						
						
						
					</div>

					<div id="accessNHSRDiv">
						<div class="form-group">
							<c:if test="${not empty command.accessNHSRCheckList}">
								<c:set var="n" value="700" scope="page" />
								<c:forEach items="${command.accessNHSRCheckList}"
									var="nLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="" text="${nLookUp.doc_DESC_ENGL}" /></label>
																		<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="accessNHSRDocs_${lk}" class="col-sm-4">
										<apptags:formField fieldType="7" labelCode="" hasId="true"
											fieldPath="accessNHSRCheckList[${lk.index}]"
											isMandatory="false" showFileNameHTMLId="true"
											fileSize="TCPHR_MAX_FILE_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
											currentCount="${n}" folderName="accessNHSRCheckList${n}" />
											
											<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
									</div>

										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.accessNHSRDocumentList[0]}">
												<c:set var="filePath"
													value="${command.accessNHSRDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.accessNHSRDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:set var="n" value="${n + 1}" scope="page" />
								</c:forEach>
							</c:if>
						</div>
					</div>

					<h4>
						<spring:message code="" text="5. Details of proposed approach." />
					</h4>
					
					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="(A) Site approachable from proposed sector road/ Development Plan Road"></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="approach1" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR" value="Yes" id="siteApproachableYes" name="siteApproachable" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code="" text="Yes" />
							</label>
							<label for="approach2" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR" value="No" id="siteApproachableNo" name="siteApproachable" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					
					<div id="siteApproachableDiv">
						<div class="form-group">
						
						<label class="col-sm-2 sub-title control-label" for="appNo"><spring:message
								code="" text="(a) Enter Width in Meters" /></label>
						<div class="col-sm-4">
							<form:input name="" type="text"
								class="form-control"
								path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.aPaSiteAprSrWid" id="aPaSiteAprSrWid"
								maxlength="20" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
						</div>
						<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(b) Whether acquired?"></spring:message>
						</label>
						<div class="col-sm-4">
							<label class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSrAcq" value="Yes" id="whetherAcquiredYes" name="whetherAcquired" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code="" text="Yes" />
							</label>
							<label class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSrAcq" value="No" id="whetherAcquiredNo" name="whetherAcquired" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
						</div>
						<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="(c) Whether constructed?"></spring:message>
						</label>
						<div class="col-sm-4">
							<label class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSrCons" value="Yes" id="whetherConstructedYes" name="whetherConstructed" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label>
							<label class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSrCons" value="No" id="whetherConstructedNo" name="whetherConstructed" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
						
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code=""
									text="(d) Whether Service road along sector road acquired?"></spring:message>
							</label>
							<div class="col-sm-4">
								<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.dPaSiteAprSrSRA"
										value="Yes" id="sectorRoadAcquiredYes" name="sectorRoadAcquired" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.dPaSiteAprSrSRA"
										value="No" id="sectorRoadAcquiredNo" name="sectorRoadAcquired" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code=""
									text="(e) Whether Service road along sector road constructed?"></spring:message>
							</label>
							<div class="col-sm-4">
								<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.ePaSiteAprSrSRC"
										value="Yes" id="sectorRoadConstructedYes"
										name="sectorRoadConstructed" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code=""
										text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.ePaSiteAprSrSRC"
										value="No" id="sectorRoadConstructedNo"
										name="sectorRoadConstructed" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code=""
										text="No" />
								</label>
							</div>
						</div>

					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="(B) Site approachable from internal circulation / sectoral plan road."></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="approach1" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR" value="Yes" id="internalCirculationYes" name="internalCirculation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code="" text="Yes" />
							</label>
							<label for="approach2" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR" value="No" id="internalCirculationNo" name="internalCirculation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>

					<div id="internalCirculationDiv">
						<div class="form-group">

							<label class="col-sm-2 sub-title control-label" for="appNo"><spring:message
									code="" text="(a) Enter Width in Meters" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control has2Decimal " path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.aPaSiteAprSprWid"
									id="internalCirculationwidthMtrs" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" onkeypress="return hasAmount(event, this, 5, 2)" placeholder="99999.99" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="" text="(b) Whether acquired?"></spring:message>
							</label>
							<div class="col-sm-2">
								<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSprAcq"
										value="Yes" id="internalCirculationAcquiredYes" name="internalCirculationAcquired" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
										code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSprAcq"
										value="No" id="internalCirculationAcquiredNo" name="internalCirculationAcquired" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
										code="" text="No" />
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="" text="(c) Whether constructed?"></spring:message>
							</label>
							<div class="col-sm-2">
								<label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSprCons"
										value="Yes" id="internalCirculationConstructedYes" name="internalCirculationConstructed" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="Yes" />
								</label> <label class="radio-inline"> <form:radiobutton path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSprCons"
										value="No" id="internalCirculationConstructedNo" name="internalCirculationConstructed" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
									<spring:message code="" text="No" />
								</label>
							</div>

						</div>

					</div>

					<div class="form-group">
						<label class="control-label col-sm-5 required-control"> <spring:message
								code=""
								text="(C) Any other type of existing approach available"></spring:message>
						</label>
						<div class="col-sm-7">
							<label for="approach1" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther" value="Yes" id="approachAvailableYes" class="approachAvailableClass" name="approachAvailable" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code="" text="Yes" />
							</label>
							<label for="approach2" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther" value="No" id="approachAvailableNo" class="approachAvailableClass" name="approachAvailable" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					
					<div id="approachAvailableDiv">
						<div class="form-group">
							<c:if test="${not empty command.existingApproachCheckList}">
								<c:set var="p" value="750" scope="page" />
								<c:forEach items="${command.existingApproachCheckList}"
									var="pLookUp" varStatus="lk">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="" text="${pLookUp.doc_DESC_ENGL}" /></label>
									<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="existingApproachDocs_${lk}" class="col-sm-4">
										<apptags:formField fieldType="7" labelCode="" hasId="true"
											fieldPath="existingApproachCheckList[${lk.index}]"
											isMandatory="false" showFileNameHTMLId="true"
											fileSize="TCPHR_MAX_FILE_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
											currentCount="${p}" folderName="existingApproachCheckList${p}" />
											
											<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span>
									</div>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.existingApproachDocumentList[0]}">
												<c:set var="filePath"
													value="${command.existingApproachDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.existingApproachDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:set var="p" value="${p + 1}" scope="page" />
								</c:forEach>
							</c:if>
						</div>
					</div>
					
					<h4>
						<spring:message code="" text="6. Site Condition" />
					</h4>
					
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(a) Vacant" /></label>
						<div class="col-sm-3">
							<label for="revenue" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac" value="Yes" id="vacantYes" name="vacant"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="revenue" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac" value="No" id="vacantNo" name="vacant" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
							
						</div>

						<label class="col-sm-2 control-label" for="name"> <spring:message
									code="" text="Vacant Remark" /></label>
						<div class="col-sm-4 ">
							<form:textarea id="vacantRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVacRemmark"
									class="form-control mandColorClass" data-rule-maxLength="200" maxlength="100"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(b) HT line" /></label>
						<div class="col-sm-3">
							<label for="htLine" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLine" value="Yes" id="htLineYes" name="htLine"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> <label for="htLine" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLine" value="No" id="htLineNo" name="htLine" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
							
						</div>

						<label class="col-sm-2 control-label required-control"> <spring:message
									code="" text="HT Line Remark" /></label>
						<div class="col-sm-4 ">
							<form:textarea id="htLineRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLineRemark"
									class="form-control mandColorClass" data-rule-maxLength="200"  maxlength="100"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(c) IOC Gas Pipeline" /></label>
						<div class="col-sm-3">
							<label for="gasPipeline" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPline" value="Yes" id="gasPipelineYes" name="gasPipeline"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> 
							<label for="gasPipeline" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPline" value="No" id="gasPipelineNo" name="gasPipeline" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
							
						</div>

						<label class="col-sm-2 control-label required-control"> <spring:message
									code="" text="IOC Gas Pipeline Remark" /></label>
						<div class="col-sm-4 ">
							<form:textarea id="gasPipelineRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPlineRemark"
									class="form-control mandColorClass" data-rule-maxLength="200" maxlength="100"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(d) Nallah" /></label>
						<div class="col-sm-3">
							<label for="nallah" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallah" value="Yes" id="nallahYes" name="nallah" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								 /> <spring:message code="" text="Yes" />
							</label> 
							<label for="nallah" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallah" value="No" id="nallahNo" name="nallah" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
							
						</div>

						<label class="col-sm-2 control-label required-control"> <spring:message
									code="" text="Nallah Remark" /></label>
						<div class="col-sm-4 ">
							<form:textarea id="nallahRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallahRemark"
									class="form-control mandColorClass" data-rule-maxLength="200"  maxlength="100"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(e) Utility/Permit Line" /></label>
						<div class="col-sm-3">
							<label for="permitLine" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine" value="Yes" id="permitLineYes" name="permitLine"
								disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Yes" />
							</label> 
							<label for="nallah" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine" value="No" id="permitLineNo" name="permitLine" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message
									code="" text="No" />
							</label>
							
						</div>

						<label class="col-sm-2 control-label required-control"> <spring:message
									code="" text="Utility/Permit Line Remark" /></label>
						<div class="col-sm-4 ">
							<form:textarea id="permitLineRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineRemark"
									class="form-control mandColorClass" data-rule-maxLength="200" maxlength="100"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
					</div>
					
					<div id="permitLineDiv">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" for=""><spring:message
								code="" text="Width of Row (in ft.)" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type="text"
								class="form-control"
								path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineWidth" id="permitLineWidth"
								maxlength="20" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(f) Whether other land falls within the applied land" /></label>
						<div class="col-sm-3">
							<label  class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineAL" value="Yes" id="fallsWithinAppliedLandYes" name="fallsWithinAppliedLand"
								 disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code="" text="Yes" />
							</label> 
							<label class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineAL" value="No" id="fallsWithinAppliedLandNo" name="fallsWithinAppliedLand" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
							
						</div>

						<label class="col-sm-2 control-label required-control"> <spring:message
								code="" text="Remark" /></label>
						<div class="col-sm-4 ">
							<form:textarea id="fallsWithinAppliedLandRemark" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineALRemark"
								class="form-control mandColorClass" data-rule-maxLength="200"
								data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label required-control"
							for=""> <spring:message code="" text="(g) Any other feature passing through site" /></label>
						<div class="col-sm-3">
							<label for="featurePassing" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature" value="Yes" id="featurePassingYes" name="featurePassing" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
								 /> <spring:message code="" text="Yes" />
							</label> 
							<label for="featurePassing" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature" value="No" id="featurePassingNo" name="featurePassing" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
							
						</div>
						
					</div>

					<div id="featurePassingDiv">
						<div class="form-group">
							`<label class="col-sm-2 control-label required-control" for=""><spring:message
									code="" text="Details thereof" /></label>
							<div class="col-sm-4 ">
								<form:input name="" type="text" class="form-control" path="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeatureDet"
									id="detailsthereof" maxlength="20" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
							</div>
						</div>
					</div>

					<div>
						<c:if test="${not empty command.landScheduleCheckList}">
							<h4>
								<spring:message code="" text="Upload Documents" />
								<small class="text-blue-2"><spring:message code=""
										text="Only .pdf and jpeg, jpg, png files allowed" /></small>
							</h4>

							<div id="landScheduleCheckListDiv">
								<div class="panel-body">

									<div class="overflow margin-top-10 margin-bottom-10">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<c:set var="b" value="100" scope="page" />
													<c:set var="c" value="0" scope="page" />
													<tr>
														<th><spring:message code="water.serialNo"
																text="Sr No" /></th>
														<th><spring:message code="water.docName"
																text="Document Name" /></th>
														<%-- <th><spring:message code="water.status" text="Status" /></th> --%>
														<th width="500"><spring:message
																code="water.uploadText" text="Upload" />
																<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span></th>
													</tr>

													<c:forEach items="${command.landScheduleCheckList}"
														var="lookUp" varStatus="lk">

														<tr>
															<td>${lookUp.documentSerialNo}</td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<td>${lookUp.doc_DESC_ENGL}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																</c:when>
																<c:otherwise>
																	<td>${lookUp.doc_DESC_Mar}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																</c:otherwise>

															</c:choose>
															<%-- <c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																<td><spring:message code="water.doc.mand" /></td>
															</c:if>
															<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																<td><spring:message code="water.doc.opt" /></td>
															</c:if> --%>
															<td>
																
																<c:choose>
																	<c:when test="${command.saveMode ne 'V' }">
																		<div id="landScheduleDocs_${lk}">
																			<apptags:formField fieldType="7" labelCode=""
																				hasId="true"
																				fieldPath="landScheduleCheckList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="TCPHR_MAX_FILE_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
																				currentCount="${b}"
																				folderName="landScheduleCheckList${b}" />
																		</div>
																	</c:when>
																	<c:otherwise>
																		<c:if
																			test="${not empty command.landScheduleDocumentList[c]}">
																			<c:set var="filePath"
																				value="${command.landScheduleDocumentList[c].attPath}" />
																			<c:set var="path"
																				value="${fn:replace(filePath,search,replace)}" />
																			<c:if test="${not empty path}">
																				<button type="button"
																					class="button-input btn btn-blue-2" name="button"
																					value="VIEW"
																					onclick="downloadFileInTag('${path}${replace}${command.landScheduleDocumentList[c].attFname}','NewTCPLicenseForm.html?Download','','')">
																					<spring:message code="" text="VIEW" />
																				</button>
																			</c:if>
																		</c:if>
																	</c:otherwise>
																</c:choose>

															</td>
														</tr>
														<c:set var="b" value="${b + 1}" scope="page" />
														<c:set var="c" value="${c + 1}" scope="page" />
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>

								</div>
							</div>
						</c:if>
					</div>
					<div class="text-center">
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save" onclick="saveApplicationLand(this)"
									id="">
									<spring:message code="" text="Next" />
								</button>							
							</c:when>
							<c:otherwise>
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save" onclick="saveApplicationLand(this)"
									id="">
									<spring:message code="" text="Save & Next" />
								</button>							
							</c:otherwise>						
						</c:choose>

						<button type="button" class="btn btn-danger"
							onclick="showTab('#applicationPurpose')" name="button"
							value="Back">
							<spring:message code="" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>