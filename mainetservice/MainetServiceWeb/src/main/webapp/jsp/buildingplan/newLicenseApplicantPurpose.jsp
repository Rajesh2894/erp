<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<style>
#acqDetTable tr td input[type=text] {
	width: 160px;
}

.zone-ward .form-group>label[for="ddz3"] {
	clear: both;
}

.zone-ward .form-group>label[for="ddz3"], .zone-ward .form-group>label[for="ddz3"]+div
	{
	margin-top: 0.7rem;
}

.zone-ward .form-group>label[for="ddz4"], .zone-ward .form-group>label[for="ddz4"]+div
	{
	margin-top: 0.7rem;
}
#applicantPurposeForm .radio-inline{
	padding-top: 14px;
}
</style>

<script>
	$(document)
			.ready(
					function() {

						var kanalValue = parseFloat($(
						'#ciConsTypeKanal').val()) || 0;
						var marlaValue = parseFloat($(
								'#ciConsTypeMarla').val()) || 0;
						var sarsaiValue = parseFloat($(
								'#ciConsTypeSarsai').val()) || 0;
		
						var conversionKanalFactor = 0.125;
						var conversionMarlaFactor = 0.00625001;
						var conversionSarsaiFactor = 0.0006944438305254;
		
						var kanalAcreValue = kanalValue
								* conversionKanalFactor;
						var marlaAcreValue = marlaValue
								* conversionMarlaFactor;
						var sarsaiAcreValue = sarsaiValue
								* conversionSarsaiFactor;
		
						var totalValue = kanalAcreValue
								+ marlaAcreValue
								+ sarsaiAcreValue;
		
						totalValue = parseFloat(totalValue
								.toFixed(5));
		
						// Update the text content of the "Total" span for #ciConsTypeTotArea
						$('#ciConsTypeTotArea').val(
								totalValue);
						$("#consolTotArea" + rowCountClick)
								.val(totalValue);
		
						var inputValue = $(this).val();
		
						// Get the corresponding "Total" span ID
						
		
						
							$('#ciConsTypeKanal-total')
									.text(
											'Total: '
													+ kanalAcreValue
															.toFixed(5));
							$('#ciConsTypeMarla-total')
									.text(
											'Total: '
													+ marlaAcreValue
															.toFixed(5));
							$('#ciConsTypeSarsai-total')
									.text(
											'Total: '
													+ sarsaiAcreValue
															.toFixed(5));

						toggleKhasaraDeveloped();
						toggleConsolidation();
						toggleChangeInfoDiv();

						$(
								'input[name="licenseApplicationMasterDTO.khrsDevCollab"]')
								.change(function() {
									toggleKhasaraDeveloped();
								});

						function toggleKhasaraDeveloped() {
							// Check if "Yes" is selected
							if ($(
									'input[name="licenseApplicationMasterDTO.khrsDevCollab"]:checked')
									.val() === 'Yes') {
								
								$('#khasaraYdiv').show();
								prepareDateTag();
							} else {
								$('#khasaraYdiv').hide();
							}
						}
						
						$(".khrsDevCollabDiv").click(function() {
							if ($("input[name='licenseApplicationMasterDTO.khrsDevCollab']:checked").val() === 'Yes') {
								var divName = '#applicationPurpose';
								var requestData = $('form').serialize();
								var applicantPurposeResponse = __doAjaxRequest('NewTCPLicenseForm.html?getApplicantPurposeCheckList', 'POST', requestData, false, 'html');
								$(divName).html(applicantPurposeResponse);
								$('#khasaraYdiv').show();
							} else {
								$('#khasaraYdiv').hide();
							}
						});


						// Attach change event handler to the radio buttons
						$(
								'input[name="licenseApplicationMasterDTO.ciConsType"]')
								.change(function() {
									toggleConsolidation();
									 $('#ciConsTypeKanal, #ciConsTypeMarla, #ciConsTypeSarsai, #ciConsTypeTotArea').val('');
									 $('#bighaNonConsolidation, #biswaNonConsolidation, #biswansiNonConsolidation, #totalNonConsolidation').val('');
									 $("#kanal" + rowCountClick).val('');
									 $("#marla" + rowCountClick).val('');
									 $("#sarsai" + rowCountClick).val('');
									 $("#bigha" + rowCountClick).val('');
									 $("#biswa" + rowCountClick).val('');
									 $("#biswansi" + rowCountClick).val('');
									 $("#nonConsolTotArea" + rowCountClick).val('');
									 $("#consolTotArea" + rowCountClick).val('');
									 calculateTotalArea();
								});

						function toggleConsolidation() {
							// Check if "Consolidated" is selected
							if ($('#consolidationYes').is(':checked')) {
								$('#consolidatedDiv').show();
								$('#nonConsolidatedDiv').hide();
							} else if ($('#consolidationNo').is(':checked')) {
								$('#consolidatedDiv').hide();
								$('#nonConsolidatedDiv').show();
							} else {
								$('#consolidatedDiv').hide();
								$('#nonConsolidatedDiv').hide();
							}
						}

						$(
								'#ciConsTypeKanal, #ciConsTypeMarla, #ciConsTypeSarsai')
								.on(
										'input',
										function() {

											var kanalValue = parseFloat($(
													'#ciConsTypeKanal').val()) || 0;
											var marlaValue = parseFloat($(
													'#ciConsTypeMarla').val()) || 0;
											var sarsaiValue = parseFloat($(
													'#ciConsTypeSarsai').val()) || 0;

											var conversionKanalFactor = 0.125;
											var conversionMarlaFactor = 0.00625001;
											var conversionSarsaiFactor = 0.0006944438305254;

											var kanalAcreValue = kanalValue
													* conversionKanalFactor;
											var marlaAcreValue = marlaValue
													* conversionMarlaFactor;
											var sarsaiAcreValue = sarsaiValue
													* conversionSarsaiFactor;

											var totalValue = kanalAcreValue
													+ marlaAcreValue
													+ sarsaiAcreValue;

											totalValue = parseFloat(totalValue
													.toFixed(5));

											// Update the text content of the "Total" span for #ciConsTypeTotArea
											$('#ciConsTypeTotArea').val(
													totalValue);
											$("#consolTotArea" + rowCountClick)
													.val(totalValue);

											var inputValue = $(this).val();

											// Get the corresponding "Total" span ID
											var totalSpanId = $(this)
													.attr('id')
													+ '-total';

											if ($(this).is('#ciConsTypeKanal')) {
												$('#' + totalSpanId)
														.text(
																'Total: '
																		+ kanalAcreValue
																				.toFixed(5));
											} else if ($(this).is(
													'#ciConsTypeMarla')) {
												$('#' + totalSpanId)
														.text(
																'Total: '
																		+ marlaAcreValue
																				.toFixed(5));
											} else if ($(this).is(
													'#ciConsTypeSarsai')) {
												$('#' + totalSpanId)
														.text(
																'Total: '
																		+ sarsaiAcreValue
																				.toFixed(5));
											}

											calculateTotalArea();
										});

						$(
								'#bighaNonConsolidation, #biswaNonConsolidation, #biswansiNonConsolidation')
								.on(
										'input',
										function() {
											var bighaValue = parseFloat($(
													'#bighaNonConsolidation')
													.val()) || 0;
											var biswaValue = parseFloat($(
													'#biswaNonConsolidation')
													.val()) || 0;
											var biswansiValue = parseFloat($(
													'#biswansiNonConsolidation')
													.val()) || 0;

										    var nonConsolidationTypValue = $("input[name='licenseApplicationMasterDTO.ciNonConsTypeId']:checked").val();

											if(nonConsolidationTypValue == "1"){
												var conversionBighaFactor = 0.20833;
												var conversionBiswaFactor = 0.01042;
												var conversionBiswansiFactor = 0.00052;

											}else if(nonConsolidationTypValue == "2"){
												var conversionBighaFactor = 0.625;
												var conversionBiswaFactor = 0.03125;
												var conversionBiswansiFactor = 0.00156;

											}else{
												
												var errorList = [];
												errorList.push(getLocalMessage("Please select non-consilidation type"));
												displayErrorsOnPage(errorList);
												return false
											}

											var bighaAcreValue = bighaValue
													* conversionBighaFactor;
											var biswaAcreValue = biswaValue
													* conversionBiswaFactor;
											var biswansiAcreValue = biswansiValue
													* conversionBiswansiFactor;

											var totalValue = bighaAcreValue
													+ biswaAcreValue
													+ biswansiAcreValue;

											// Limit the total value to 3 decimal places
											totalValue = parseFloat(totalValue
													.toFixed(5));

											// Update the text content of the "Total" span for #ciConsTypeTotArea
											$('#totalNonConsolidation').val(
													totalValue);
											$(
													"#nonConsolTotArea"
															+ rowCountClick)
													.val(totalValue);

											// Get the corresponding "Total" span ID
											var totalSpanId = $(this)
													.attr('id')
													+ '-total';

											// Update the text content of the "Total" span with the respective calculated value
											if ($(this).is(
													'#bighaNonConsolidation')) {
												$('#' + totalSpanId)
														.text(
																'Total: '
																		+ bighaAcreValue
																				.toFixed(5));
											} else if ($(this).is(
													'#biswaNonConsolidation')) {
												$('#' + totalSpanId)
														.text(
																'Total: '
																		+ biswaAcreValue
																				.toFixed(5));
											} else if ($(this)
													.is(
															'#biswansiNonConsolidation')) {
												$('#' + totalSpanId)
														.text(
																'Total: '
																		+ biswansiAcreValue
																				.toFixed(5));
											}
											calculateTotalArea();
										});

						

						/* $(".editAcqDet").on("click", function() {
							var rowIndex = $(this).closest('tr').index();

							rowCountClick = rowIndex;
							alert(rowCountClick);

						}); */

						/* getTehsilList();
					    getVillageList();
					    getMurabaList();
					    getKhasraList(); */
				
					});

	function toggleChangeInfoDiv() {

		var changeInfo = $('#changeInfo').prop('checked');
		if (changeInfo) {
			$('#changeInfoDiv').show();
		} else {
			$('#changeInfoDiv').hide();
		}
	}

	$("#ddz1").change(function() {
		getTehsilList();
		var district = $(this).find("option:selected").text();
		$("#district" + rowCountClick).val(district);
	});
	$("#ddz2").change(function() {
		var devplan = $(this).find("option:selected").text();
		$("#devPlan" + rowCountClick).val(devplan);
	});

	$("#ddz3").change(function() {
		var zone = $(this).find("option:selected").text();
		$("#zone" + rowCountClick).val(zone);
	});

	$("#ddz4").change(function() {
		var sector = $(this).find("option:selected").text();
		$("#sector" + rowCountClick).val(sector);
	});

	$("#khrsLandTypeId").change(function() {
		var khrsLandTypeId = $(this).find("option:selected").text();
		$("#landType" + rowCountClick).val(khrsLandTypeId);
	});
	$("#khrsRevEst").change(function() {
		getMurabaList();
		var khrsRevEst = $(this).find("option:selected").text();
		$("#revEstate" + rowCountClick).val(khrsRevEst);
	});
	$("#khrsThesil").change(function() {
		getVillageList();
		var khrsThesil = $(this).find("option:selected").text();
		$("#thesil" + rowCountClick).val(khrsThesil);
	});

	$("#khrsMustil").change(function() {
		getKhasraList();
		var khrsMustil = $(this).find("option:selected").text();
		$("#rectangleNo" + rowCountClick).val(khrsMustil);
	});

	$("#khrsHadbast").change(function() {
		$("#hadbastNo" + rowCountClick).val($(this).val());
	});

	$("#khrsKilla").change(function() {
		doesLicExist();
		getOwnerData();
		$("#khasraNo" + rowCountClick).val($(this).find("option:selected").text());
	});

	$("#tcpNameOfLO").change(function() {
		$("#landOwnerName" + rowCountClick).val($(this).val());
	});

	$("#khrsDevCollabY, #khrsDevCollabN")
			.change(
					function() {
						var collaborationValue = $(
								"input[name='licenseApplicationMasterDTO.khrsDevCollab']:checked")
								.val();
						$("#devColab" + rowCountClick).val(collaborationValue);
					});

	$("#khrsDevComName").change(function() {
		$("#devCompName" + rowCountClick).val($(this).val());
	});

	$("#khrsColabRegDate").change(function() {
		$("#collabAgrDate" + rowCountClick).val($(this).val());
	});

	$("#isIrrevocableYes, #isIrrevocableNo").change(function() {
		var irrevocableValue = $("input[name='licenseApplicationMasterDTO.khrsCollabAggRevo']:checked").val();
		$("#collabAgrRev" + rowCountClick).val(irrevocableValue);
	});

	$("#authorizedSignatoryOwner").change(function() {
		$("#authSignLO" + rowCountClick).val($(this).val());
	});

	$("#khrsAurSignDev").change(function() {
		$("#authSignDev" + rowCountClick).val($(this).val());
	});

	$("#khrsRegAuth").change(function() {
		$("#regAuth" + rowCountClick).val($(this).val());
	});

	$("#changeInfo").change(
			function() {
				$("#chInfo" + rowCountClick).val(
						$(this).is(":checked") ? "Yes" : "No");
			});

	$("#min").change(function() {
		var isChecked = $(this).is(":checked");
		$("#minTable" + rowCountClick).val(isChecked ? "Y" : "N");
	});
	$("#recMustChange").change(function() {
		$("#mustilCh" + rowCountClick).val($(this).val());
	});

	$("#khasNoChange").change(function() {
		$("#khasaraCh" + rowCountClick).val($(this).val());
	});

	$("#LoNameMut").change(function() {
		$("#landOwnerMUJAM" + rowCountClick).val($(this).val());
	});

	$("#consolidationYes, #consolidationNo").change(function() {
	    var consolidationValue = $("input[name='licenseApplicationMasterDTO.ciConsType']:checked").val();
	    var consolTypeValue = (consolidationValue === 'Consolidated') ? 'Consolidated' : 'Non-consolidated';
	    $("#consolType" + rowCountClick).val(consolTypeValue);
	});

	$("#ciConsTypeKanal").change(function() {
		$("#kanal" + rowCountClick).val($("#ciConsTypeKanal").val());
	});
	$("#ciConsTypeMarla").change(function() {
		$("#marla" + rowCountClick).val($("#ciConsTypeMarla").val());
	});
	$("#ciConsTypeSarsai").change(function() {
		$("#sarsai" + rowCountClick).val($("#ciConsTypeSarsai").val());
	});

	$("#bighaNonConsolidation").change(function() {
		$("#bigha" + rowCountClick).val($("#bighaNonConsolidation").val());
	});
	$("#biswaNonConsolidation").change(function() {
		$("#biswa" + rowCountClick).val($("#biswaNonConsolidation").val());
	});
	$("#biswansiNonConsolidation").change(
			function() {
				$("#biswansi" + rowCountClick).val(
						$("#biswansiNonConsolidation").val());
			});
	$("#ciConsTypeTotArea").change(function() {
		$("#consolTotArea" + rowCountClick).val($("#ciConsTypeTotArea").val());
	});

	$("#totalNonConsolidation").change(
			function() {
				$("#nonConsolTotArea" + rowCountClick).val(
						$("#totalNonConsolidation").val());
			});

	$("#ciAquStatus").change(function() {
		$("#acqStat" + rowCountClick).val($(this).val());
	});

	$(document).on('change', '.consolTotArea, .nonConsolTotArea', function() {
		calculateTotalArea();
	});

	function calculateTotalArea() {
		var totalConsolArea = 0;
		var totalNonConsolArea = 0;

		$('.consolTotArea').each(function() {
			var value = parseFloat($(this).val()) || 0;
			totalConsolArea += value;
		});

		$('.nonConsolTotArea').each(function() {
			var value = parseFloat($(this).val()) || 0;
			totalNonConsolArea += value;
		});

		var totalArea = totalConsolArea + totalNonConsolArea;
		$('#ciTotArea').val(totalArea.toFixed(5));
	}
</script>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantPurposeForm"
					action="NewTCPLicenseForm.html" method="post"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden id ="muraba" path=""  />
					<input type="hidden" value="${command.saveMode}" id="hiddenSaveMode"/>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control "
							for="apptype"><spring:message
								code="" text="Application Type" /></label>
						<div class="col-sm-4">
							<c:set var="baseLookupCode" value="ATT" />
							<form:select path="licenseApplicationMasterDTO.appPAppType"
								cssClass="form-control mandColorClass" id="appPAppType" 
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
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label required-control "
							for="apptype"><spring:message
								code="" text="Purpose Name" /></label>
						<div class="col-sm-4">
							<form:select path="licenseApplicationMasterDTO.appPLicPurposeId"
								cssClass="form-control mandColorClass" id="appPLicPurposeId"
								disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${command.purposeList}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					
					<h4>
						<spring:message
								code="" text="Land schedule" />
					</h4>


					<div class="form-group">
						<label class="col-sm-12 " for=""><spring:message
								code="" text="Note:" /> <a
							href="https://tcpharyana.gov.in/policypanel.htm" target="_blank"><spring:message
								code="" text="The
								application to be received under policy dated 10.11.2017 shall
								only be accepted within window period." /></a>
						</label>
					</div>

					<div class="form-group">
						<label class="col-sm-12" for=""><spring:message
								code="" text="(i)Khasra-wise
							information to be provided in the following format" /></label>
					</div>

					<div class="zone-ward">
					<div class="form-group">
						<c:set var="baseLookupCode" value="DDZ" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="DDZ"
										hasId="true" pathPrefix="licenseApplicationMasterDTO.ddz"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="false"
										isMandatory="true" />
										</div>
					</div>
					<%-- <div class="form-group">
						
						<label class="col-sm-1 control-label required-control" for=""><spring:message
								code="" text="Sector" /></label>
						
						<div class="col-sm-2">
							<form:select path="licenseApplicationMasterDTO.khrsSec"
								cssClass="form-control mandColorClass" id="khrsSec">
								<form:option value="">
									<spring:message code='master.selectDropDwn' />
								</form:option>
								<c:forEach items="${acnPrefixList}" var="acnPrefixData">
										<form:option value="${acnPrefixData.lookUpCode }"
											code="${acnPrefixData.lookUpCode }">${acnPrefixData.descLangFirst }</form:option>
									</c:forEach>
							</form:select>
						</div>
					</div> --%>

					<div class="form-group">
					<%-- 	<c:choose>
							<c:when test="${command.saveMode eq 'A' }"> --%>
								<label class="col-sm-1 control-label required-control"
									for="name"> <spring:message code="" text="Tehsil" />
								</label>
								<div class="col-sm-2">
									<form:select path="licenseApplicationMasterDTO.khrsThesil"
										cssClass="form-control mandColorClass" id="khrsThesil"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="tehsil" items="${command.tehsilList}">
											<form:option value="${tehsil.code}" label="${tehsil.name}" />
										</c:forEach>
									</form:select>
								</div>

						<%-- 	</c:when>
							<c:otherwise>
								<label class="col-sm-1 control-label required-control"
									for="name"> <spring:message code="" text="Tehsil" />
								</label>
								<div class="col-sm-2">
									<form:select path="licenseApplicationMasterDTO.khrsThesil"
										cssClass="form-control mandColorClass" id="khrsThesil"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="tehsil" items="${command.tehsilList}">
											<form:option value="${tehsil.code}" label="${tehsil.name}" />
										</c:forEach>
									</form:select>
								</div>
							</c:otherwise>
						</c:choose> --%>
						
						<%-- <c:choose>
							<c:when test="${command.saveMode eq 'A' }"> --%>
								<label class="col-sm-1 control-label required-control"><spring:message
										code="" text="Revenue Estate" /></label>
								<div class="col-sm-2 ">
									<form:select path="licenseApplicationMasterDTO.khrsRevEst"
										cssClass="form-control mandColorClass" id="khrsRevEst"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="village" items="${command.villageList}">
											<form:option value="${village.code}" label="${village.name}" />
										</c:forEach>
									</form:select>
								</div>
							<%-- </c:when>
							<c:otherwise>
								<label class="col-sm-1 control-label required-control">
									<spring:message code="" text="Revenue Estate" />
								</label>
								<div class="col-sm-2">
									<form:select path="licenseApplicationMasterDTO.khrsRevEst"
										cssClass="form-control mandColorClass" id="khrsRevEst"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="village" items="${command.villageList}">
											<form:option value="${village.code}" label="${village.name}" />
										</c:forEach>
									</form:select>
								</div>

							</c:otherwise>
						</c:choose> --%>
						

						
						<label class="col-sm-1 control-label required-control"
							for=""><spring:message
								code="" text="Hadbast Number" /></label>
						<div class="col-sm-2  ">
							<form:input name="" type="text"
								class="form-control hasNumber"
								path="licenseApplicationMasterDTO.khrsHadbast" id="khrsHadbast" maxlength=""
								minlength=""
								disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
						</div>
						
						<%-- <c:choose>
							<c:when test="${command.saveMode eq 'A' }"> --%>
								<label class="col-sm-1 control-label required-control" for=""><spring:message
										code="" text="Rectangle/Mustil" /></label>
								<div class="col-sm-2  ">
									<form:select path="licenseApplicationMasterDTO.khrsMustil"
										cssClass="form-control mandColorClass" id="khrsMustil"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="must" items="${command.must.must}">
											<form:option value="${must}" label="${must}" />
										</c:forEach>
									</form:select>
								</div>
							<%-- </c:when>
							<c:otherwise>
								<label class="col-sm-1 control-label required-control" for="">
									<spring:message code="" text="Rectangle/Mustil" />
								</label>
								<div class="col-sm-2">
									<form:select path="licenseApplicationMasterDTO.khrsMustil"
										cssClass="form-control mandColorClass" id="khrsMustil"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="must" items="${command.must.must}">
											<form:option value="${must}" label="${must}" />
										</c:forEach>
									</form:select>
								</div>


							</c:otherwise>
						</c:choose> --%>
						
						
					</div>

					<div class="form-group">

					<%-- 	<c:choose>
							<c:when test="${command.saveMode eq 'A' }"> --%>
								<label class="col-sm-1 control-label required-control " for=""><spring:message
										code="" text="Khasra/Killa" /></label>
								<div class="col-sm-2  " style="padding-left: 15px;">
									<form:select path="licenseApplicationMasterDTO.khrsKilla"
										cssClass="form-control mandColorClass" id="khrsKilla"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="khasra" items="${command.khasraList}">
											<form:option value="${khasra.khewats}"
												label="${khasra.killa}" />
										</c:forEach>
									</form:select>
								</div>
							<%-- </c:when>
							<c:otherwise>
								<label class="col-sm-1 control-label required-control" for="">
									<spring:message code="" text="Khasra/Killa" />
								</label>
								<div class="col-sm-2 " style="padding-left: 15px;">
									<form:select path="licenseApplicationMasterDTO.khrsKilla"
										cssClass="form-control mandColorClass" id="khrsKilla"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach var="khasra" items="${command.khasraList}">
											<form:option value="${khasra.khewats}"
												label="${khasra.killa}" />
										</c:forEach>
									</form:select>
								</div>
							</c:otherwise>
						</c:choose>
						 --%>
						
						<label class="col-sm-1 check-header required-control" for="ownerTypeId"><spring:message
								code="" text="MIN" /></label>
						<div class="col-sm-2  " style="padding-left: 15px;">
							
							<form:checkbox id="min" path="licenseApplicationMasterDTO.min" value="Y"
									class="" onclick="" />
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label  " ><spring:message
								code="" text="Name of Land Owner" /></label>
						<div class="col-sm-4  ">
							<form:textarea id="tcpNameOfLO" path="licenseApplicationMasterDTO.tcpNameOfLO"
									class="form-control mandColorClass" data-rule-maxLength="200"
									data-rule-required="true" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
						</div>
					</div>


					<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="Whether Khasra been developed in
							collaboration"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="approved1" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.khrsDevCollab" value="Yes" id="khrsDevCollabY" class="khrsDevCollabDiv" name="khrsDevCollab" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> 
									<spring:message code="" text="Yes" />
							</label> <label for="disApproved" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.khrsDevCollab" value="No" id="khrsDevCollabN" class="khrsDevCollabDiv" name="khrsDevCollab" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="No" />
							</label>
						</div>
					</div>
					
					<div id="khasaraYdiv">
						<div class="form-group ">
							<label
								class="col-sm-2 radio1 control-label  required-control"><spring:message
								code="" text="Name of the developer company" /></label>
							<div class="col-sm-2 ">
								<form:input name="" type="text" disabled="true"
								 class="form-control" path="licenseApplicationMasterDTO.khrsDevComName"
									id="khrsDevComName" maxlength="100" value="${command.developerRegistrationDTO.companyName}" />
								
							</div>
							<label class="col-sm-2 radio1 control-label  required-control "><spring:message
									code="" text="Date of registering collaboration agreement" /></label>
							<div class="col-sm-2">
								<div class="input-group">
									<form:input
										class="form-control mandColorClass datepicker datepicker2 addColor currentDate"
										placeholder="DD/MM/YYYY" autocomplete="off"
										id="khrsColabRegDate"
										disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
										path="licenseApplicationMasterDTO.khrsColabRegDate"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							<label class="col-sm-2 radio1 control-label required-control"><spring:message
								code="" text="Whether collaboration agreement irrevocable (Yes/No)" /></label>
							<div class="col-sm-2">
							<label for="approved1" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.khrsCollabAggRevo" value="Y" id="isIrrevocableYes" name="isIrrevocable"
									 disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message code="" text="Yes" />
							</label> <label for="disApproved" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.khrsCollabAggRevo" value="N" id="isIrrevocableNo" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" name="isIrrevocable" /> <spring:message
									code="" text="No" />
							</label>
						</div>
						</div>
						
						<div class="form-group ">
							<label
								class="col-sm-2 radio1 control-label required-control"><spring:message
								code="" text="Name of authorized signatory on behalf of land owner(s)" /></label>
							<div class="col-sm-2 ">
								<form:input name="" type="text" class="form-control hasSpecialChara" path="licenseApplicationMasterDTO.khrsAuthSignLO"
									id="authorizedSignatoryOwner" maxlength="45"  disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
								
							</div>
							<label
								class="col-sm-2 radio1 control-label required-control"><spring:message
								code="" text="Name of authorized signatory on behalf of developer" /></label>
							<div class="col-sm-2 ">
								<form:input name="" type="text" class="form-control hasSpecialChara" path="licenseApplicationMasterDTO.khrsAurSignDev"
									id="khrsAurSignDev" maxlength="100" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
								
							</div>
							<label
								class="col-sm-2 radio1 control-label required-control"><spring:message
								code="" text="Registering Authority" /></label>
							<div class="col-sm-2 ">
								<form:input name="" type="text" class="form-control hasNameClass" path="licenseApplicationMasterDTO.khrsRegAuth"
									id="khrsRegAuth" maxlength="45" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
								
							</div>
						</div>
					<c:set var="search" value="\\" />
					<c:set var="replace" value="\\\\" />
						<div class="form-group">
							<c:if test="${not empty command.applicantPurposeCheckList}">
								<div class="overflow margin-top-10">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<c:set var="p" value="300" scope="page" />
												<c:set var="c" value="0" scope="page" />
												<tr>
													<th><spring:message code="water.serialNo" text="Sr No" /></th>
													<th><spring:message code="water.docName"
															text="Document Name" /></th>
													<th width="500"><spring:message
															code="water.uploadText" text="Upload" />
															<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span></th>
												</tr>

												<c:forEach items="${command.applicantPurposeCheckList}"
													var="lookUp" varStatus="lk">
													<tr>
														<td>${lookUp.documentSerialNo}</td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td>${lookUp.doc_DESC_ENGL}<c:if
																		test="${lookUp.checkkMANDATORY eq 'Y'}">
																		<span class="mand">*</span>
																	</c:if>
																</td>
															</c:when>
															<c:otherwise>
																<td>${lookUp.doc_DESC_Mar}<c:if
																		test="${lookUp.checkkMANDATORY eq 'Y'}">
																		<span class="mand">*</span>
																	</c:if></td>
															</c:otherwise>

														</c:choose>
														<td>
											
															<c:choose>
																	<c:when test="${command.saveMode ne 'V' }">
																		<div id="applicantPurposeDocs_${lk}">
																		<apptags:formField fieldType="7" labelCode=""
																	hasId="true"
																	fieldPath="applicantPurposeCheckList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="TCPHR_MAX_FILE_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
																	currentCount="${p}"
																	folderName="applicantPurposeCheckList${p}" />
																	</div>
																	</c:when>
																	<c:otherwise>
																	<c:if test="${not empty command.appPurposeDocumentList[c]}">
																		<c:set var="filePath"
																					value="${command.applicantDocumentList[c].attPath}" /> <c:set var="path"
																					value="${fn:replace(filePath,search,replace)}" />
																				<c:if test="${not empty path}">
																					<button type="button"
																						class="button-input btn btn-blue-2" name="button"
																						value="VIEW"
																						onclick="downloadFileInTag('${path}${replace}${command.applicantDocumentList[c].attFname}','NewTCPLicenseForm.html?Download','','')">
																						<spring:message code="" text="VIEW" />
																					</button>
																				</c:if>
																	</c:if>
																	</c:otherwise>
																</c:choose>
														</td>
													</tr>
													<c:set var="p" value="${p + 1}" scope="page" />
													<c:set var="c" value="${c + 1}" scope="page" />
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
						</div>
					</div>
					


					<div class="form-group">
						<label class="col-sm-2 control-label required-control" for="name"><spring:message
								code="" text="Type of land" /></label>
						<div class="col-sm-2">
							<c:set var="baseLookupCode" value="TYL" />
							<form:select path="licenseApplicationMasterDTO.khrsLandTypeId"
								cssClass="form-control mandColorClass" id="khrsLandTypeId"
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


					</div>


					<div class="form-group">
						<label class="col-sm-2  check-header" for="ownerTypeId"><spring:message
								code="" text="Change in Information" /></label>
						<div class="col-sm-2  " style="padding-left: 15px;">
							<form:checkbox id="changeInfo" path="" value="Y"
									class="" onclick="toggleChangeInfoDiv()" />
						</div>
					</div>

					<div id="changeInfoDiv">
					
						<div class="form-group">
						
							<label class="col-sm-2 control-label required-control"><spring:message
									code="" text="Rectangle No./Mustil(Changed)" /></label>
							<div class="col-sm-4 ">
								<form:input name="" type="text" class="form-control hasNumber" path=""
									id="recMustChange" maxlength="" minlength="" 
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
							</div>
							
							<label class="col-sm-2 control-label required-control hasNumber"><spring:message
									code="" text="Khasra number(Changed) " /></label>
							<div class="col-sm-4 ">
								<form:input name="" type="text" class="form-control" path=""
									id="khasNoChange" maxlength="" minlength="" 
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
							</div>
							
						</div>
						
						<div class="form-group">
						
							<label class="col-sm-2 control-label required-control"><spring:message
									code="" text="Name of the Land Owner as per Mutation/Jamabandi" /></label>
							<div class="col-sm-4 ">
								<form:input name="" type="text" class="form-control" path=""
									id="LoNameMut" maxlength="" minlength="" 
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/>
							</div>
							
							
						</div>
		
					</div>



			<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="Consolidation Type"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="consolidation" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.ciConsType" value="Consolidated" id="consolidationYes" name="consolidation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
									 /> <spring:message code="" text="Consolidated" />
							</label> <label for="consolidation" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.ciConsType" value="Non-Consolidated" id="consolidationNo" name="consolidation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="Non-Consolidated" />
							</label>
						</div>
					</div>

			<div id="consolidatedDiv">
				<div class="form-group">
					<table class="table table-striped table-bordered table-wrapper">
						<thead>

							<tr>

								<th class="text-center required-control"><spring:message code="" text="Kanal" /></th>
								<th class="text-center required-control"><spring:message code="" text="Marla" /></th>
								<th class="text-center required-control"><spring:message code="" text="Sarsai" /></th>
								<th class="text-center"><spring:message code="" text="Total Area (In Acres)" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>

								<td class="text-center"><form:input name="" type="text" class="form-control hasDecimal" path="licenseApplicationMasterDTO.ciConsTypeKanal"
									id="ciConsTypeKanal" maxlength="50" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <span id="ciConsTypeKanal-total"
									class="table-span"><spring:message code="" text="Total:0.00000" /> </span></td>
								<td class="text-center"><form:input name="" type="text" class="form-control hasDecimal" path="licenseApplicationMasterDTO.ciConsTypeMarla"
									id="ciConsTypeMarla" maxlength="50" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <span id="ciConsTypeMarla-total"
									class="table-span"><spring:message code="" text="Total:0.00000" /> </span></td>
								<td class="text-center"><form:input name="" type="text" class="form-control hasDecimal" path="licenseApplicationMasterDTO.ciConsTypeSarsai"
									id="ciConsTypeSarsai" maxlength="50" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <span id="ciConsTypeSarsai-total"
									class="table-span"><spring:message code="" text="Total:0.00000" /> </span></td>
								<td class="text-center"><form:input name="" type="text" class="form-control hasDecimal" path="licenseApplicationMasterDTO.ciConsTypeTotArea"
									id="ciConsTypeTotArea" maxlength="50" readonly="true" /></td>

							</tr>
						</tbody>
					</table>
				</div>

			</div>

			<div id="nonConsolidatedDiv">
				<div class="form-group">
						<label class="control-label col-sm-2 required-control"> <spring:message
								code=""
								text="Non Consolidation Type"></spring:message>
						</label>
						<div class="col-sm-10">
							<label for="consolidation" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.ciNonConsTypeId" value="1" id="nonConsolidationKachha" name="nonConsolidation"
									disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" /> <spring:message code="" text="Kachha" />
							</label> <label for="consolidation" class="radio-inline"> <form:radiobutton
									path="licenseApplicationMasterDTO.ciNonConsTypeId" value="2" id="nonConsolidationPuchka" name="nonConsolidation" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <spring:message
									code="" text="Puchka" />
							</label>
						</div>
					</div>
					
					<div class="form-group">
					<table class="table table-striped table-bordered table-wrapper">
						<thead>

							<tr>

								<th class="text-center required-control"><spring:message code="" text="Bigha" /></th>
								<th class="text-center required-control"><spring:message code="" text="Biswa" /></th>
								<th class="text-center required-control"><spring:message code="" text="Biswansi" /></th>
								<th class="text-center"><spring:message code="" text="Total Area (In Acres)" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>

								<td class="text-center"><form:input name="" type="text"
											class="form-control hasDecimal" path="" id="bighaNonConsolidation"
											maxlength="50" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/><span id="bighaNonConsolidation-total" class="table-span"> <spring:message code=""
											text="Total:" />
								</span></td>
								<td class="text-center"><form:input class="form-control hasDecimal" path=""
									id="biswaNonConsolidation" maxlength="50" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <span id="biswaNonConsolidation-total"
									class="table-span"><spring:message code="" text="Total:" /> </span></td>
								<td class="text-center"><form:input name="" type="text" class="form-control hasDecimal" path=""
									id="biswansiNonConsolidation" maxlength="50" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"/> <span id="biswansiNonConsolidation-total"
									class="table-span"><spring:message code="" text="Total:" /> </span></td>
								<td class="text-center"><form:input name="" type="text" class="form-control hasDecimal" path=""
									id="totalNonConsolidation" maxlength="50" disabled="true" /> </td>

							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="form-group">

						<label class="col-sm-2 control-label  required-control"><spring:message
									code="" text="Acquisition Status" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type="text"
								class="form-control"
								path="licenseApplicationMasterDTO.ciAquStatus" id="ciAquStatus" maxlength="100"
								minlength="" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}" />
						</div>
					</div>

					<div class="form-group">
						<div class="overflow margin-top-10">
							<div class="table-responsive">
							<c:set var="d" value="0" scope="page"></c:set>
								<table id="acqDetTable"								
									class="table table-striped table-bordered table-wrapper table-responsive">
									<thead>

										<tr>
											<th class="text-center" width="20%"><spring:message code="" text="District" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Development Plan" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Zone" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Sector" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Tehsil " /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Revenue Estate" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Hadbast No." /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Rectangle No." /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Khasra No." /></th>
											<th class="text-center" ><spring:message code="" text="Min" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Name of Land Owner" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Type of land" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Change in information" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Rectangle No./Mustil(Changed)" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Khasra Number(Changed)" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Name of the Land Owner as per
												Mutation/Jamabandi" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Whether Khasra been developed in
												collaboration" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Name of the developer company" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Date of registering
												collaboration agreement" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Whether collaboration agreement
												irrevocable (Yes/No)" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Name of authorized signatory on
												behalf of land owner(s)" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Name of authorized signatory on
												behalf of developer" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Registering Authority" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Collaboration Document" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="SPA/GPA/Board Resolution on
												behalf of land owner" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="SPA/GPA/Board Resolution on
												behalf of developer" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Collaborator Agreement Document" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Consolidation Type" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Kanal" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Marla" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Sarsai" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Bigha" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Biswa" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Biswansi" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Acquisition Status" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Consolidated Area(In Acres)" /></th>
											<th class="text-center" width="10%"><spring:message code="" text="Non-Consolidated Area(In Acres)" /></th>
											<c:if test="${command.saveMode ne 'V' }">
											<th class="text-center" width="10%"><spring:message code="" text="Action" /></th>
											</c:if>

										</tr>
									</thead>
							<c:choose>
							<c:when
								test="${empty command.licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList}">
								<tbody>
								<tr class="acqAppendClass">
									
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].district"
											class="form-control hasNameClass valid" id="district${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devPlan"
											class="form-control hasNameClass valid" id="devPlan${d}"
											readonly="true" /></td>
									<td class="text-center form-cell" width="40%"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].zone"
											class="form-control hasNameClass valid" id="zone${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sector"
											class="form-control hasNameClass valid" id="sector${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].thesil"
											class="form-control hasNameClass valid" id="thesil${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].revEstate"
											class="form-control hasNameClass valid" id="revEstate${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].hadbastNo"
											class="form-control hasNameClass valid" id="hadbastNo${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].rectangleNo"
											class="form-control hasNameClass valid" id="rectangleNo${d}"
											readonly="true" /></td disabled="true"class="text-center">
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasraNo"
											class="form-control hasNameClass valid" id="khasraNo${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].min"
											class="form-control hasNameClass valid" id="minTable${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerName"
											class="form-control hasNameClass valid" id="landOwnerName${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landType"
											class="form-control hasNameClass valid" id="landType${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].chInfo"
											class="form-control hasNameClass valid" id="chInfo${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].mustilCh"
											class="form-control hasNameClass valid" id="mustilCh${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasaraCh"
											class="form-control hasNameClass valid" id="khasaraCh${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerMUJAM"
											class="form-control hasNameClass valid" id="landOwnerMUJAM${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devColab"
											class="form-control hasNameClass valid" id="devColab${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devCompName"
											class="form-control hasNameClass valid" id="devCompName${d}"
											readonly="true" value="${command.developerRegistrationDTO.companyName}"  /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDate"
											class="form-control hasNameClass valid" id="collabAgrDate${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrRev"
											class="form-control hasNameClass valid" id="collabAgrRev${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignLO"
											class="form-control hasNameClass valid" id="authSignLO${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignDev"
											class="form-control hasNameClass valid" id="authSignDev${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].regAuth"
											class="form-control hasNameClass valid" id="regAuth${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabDec"
											class="form-control hasNameClass valid" id="collabDec${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brLO"
											class="form-control hasNameClass valid" id="brLO${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brDev"
											class="form-control hasNameClass valid" id="brDev${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDoc"
											class="form-control hasNameClass valid" id="collabAgrDoc${d}"
											readonly="true" /></td>
									<td class="text-center form-cell"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolType"
											class="form-control hasNameClass valid" id="consolType${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].kanal"
											class="form-control hasNameClass valid" id="kanal${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].marla"
											class="form-control hasNameClass valid" id="marla${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sarsai"
											class="form-control hasNameClass valid" id="sarsai${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].bigha"
											class="form-control hasNameClass valid" id="bigha${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswa"
											class="form-control hasNameClass valid" id="biswa${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswansi"
											class="form-control hasNameClass valid" id="biswansi${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].acqStat"
											class="form-control hasNameClass valid" id="acqStat${d}"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolTotArea"
											class="form-control hasNameClass valid consolTotArea" id="consolTotArea${d}" onchange="calculateTotalArea()"
											readonly="true" /></td>
									<td class="text-center"><form:input
											path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].nonConsolTotArea"
											class="form-control hasNameClass valid nonConsolTotArea" id="nonConsolTotArea${d}" onchange="calculateTotalArea()"
											readonly="true" /></td>
									
									<td class="text-center">
									<span style="display:flex"><a title="Add" id="addBtn"
										class="btn btn-blue-2 btn-sm addAcqDet margin-right-5" onclick=""><i
											class="fa fa-plus"></i></a> <a href="javascript:void(0);"
										class="btn btn-danger btn-sm delButtonTable margin-right-5" onclick=""><i
											class="fa fa-minus"></i></a>
											<a href="javascript:void(0);" onclick="handleEditClick(this)"
										class="btn btn-warning btn-sm editAcqDet margin-right-5"><i
											class="fa fa-edit"></i></a></span></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</tbody>
							</c:when>
										<c:otherwise>
											<tbody>
												<c:forEach var="dataList"
													items="${command.licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList}"
													varStatus="status">
													<tr class="acqAppendClass">

														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].district"
																class="form-control hasNameClass valid"
																id="district${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devPlan"
																class="form-control hasNameClass valid" id="devPlan${d}"
																readonly="true" /></td>
														<td class="text-center form-cell" width="40%"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].zone"
																class="form-control hasNameClass valid" id="zone${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sector"
																class="form-control hasNameClass valid" id="sector${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].thesil"
																class="form-control hasNameClass valid" id="thesil${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].revEstate"
																class="form-control hasNameClass valid"
																id="revEstate${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].hadbastNo"
																class="form-control hasNameClass valid"
																id="hadbastNo${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].rectangleNo"
																class="form-control hasNameClass valid"
																id="rectangleNo${d}" readonly="true" /></td disabled="true"class="text-center">
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasraNo"
																class="form-control hasNameClass valid"
																id="khasraNo${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].min"
																class="form-control hasNameClass valid"
																id="minTable${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerName"
																class="form-control hasNameClass valid"
																id="landOwnerName${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landType"
																class="form-control hasNameClass valid"
																id="landType${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].chInfo"
																class="form-control hasNameClass valid" id="chInfo${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].mustilCh"
																class="form-control hasNameClass valid"
																id="mustilCh${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasaraCh"
																class="form-control hasNameClass valid"
																id="khasaraCh${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerMUJAM"
																class="form-control hasNameClass valid"
																id="landOwnerMUJAM${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devColab"
																class="form-control hasNameClass valid"
																id="devColab${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devCompName"
																class="form-control hasNameClass valid"
																id="devCompName${d}" readonly="true"
																value="${command.developerRegistrationDTO.companyName}" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDate"
																class="form-control hasNameClass valid"
																id="collabAgrDate${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrRev"
																class="form-control hasNameClass valid"
																id="collabAgrRev${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignLO"
																class="form-control hasNameClass valid"
																id="authSignLO${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignDev"
																class="form-control hasNameClass valid"
																id="authSignDev${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].regAuth"
																class="form-control hasNameClass valid" id="regAuth${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabDec"
																class="form-control hasNameClass valid"
																id="collabDec${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brLO"
																class="form-control hasNameClass valid" id="brLO${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brDev"
																class="form-control hasNameClass valid" id="brDev${d}"
																readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDoc"
																class="form-control hasNameClass valid"
																id="collabAgrDoc${d}" readonly="true" /></td>
														<td class="text-center form-cell"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolType"
																class="form-control hasNameClass valid"
																id="consolType${d}" readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].kanal"
																class="form-control hasNameClass valid" id="kanal${d}"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].marla"
																class="form-control hasNameClass valid" id="marla${d}"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sarsai"
																class="form-control hasNameClass valid" id="sarsai${d}"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].bigha"
																class="form-control hasNameClass valid" id="bigha${d}"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswa"
																class="form-control hasNameClass valid" id="biswa${d}"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswansi"
																class="form-control hasNameClass valid"
																id="biswansi${d}" readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].acqStat"
																class="form-control hasNameClass valid" id="acqStat${d}"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolTotArea"
																class="form-control hasNameClass valid consolTotArea"
																id="consolTotArea${d}" onchange="calculateTotalArea()"
																readonly="true" /></td>
														<td class="text-center"><form:input
																path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].nonConsolTotArea"
																class="form-control hasNameClass valid nonConsolTotArea"
																id="nonConsolTotArea${d}"
																onchange="calculateTotalArea()" readonly="true" /></td>
														<c:if test="${command.saveMode ne 'V' }">
															<td class="text-center"><span style="display: flex"><a
																	title="Add" id="addBtn"
																	class="btn btn-blue-2 btn-sm addAcqDet margin-right-5"
																	onclick=""><i class="fa fa-plus"></i></a> <a
																	href="javascript:void(0);"
																	class="btn btn-danger btn-sm delButtonTable margin-right-5"
																	onclick=""><i class="fa fa-minus"></i></a> <a
																	href="javascript:void(0);"
																	onclick="handleEditClick(this)"
																	class="btn btn-warning btn-sm editAcqDet margin-right-5"><i
																		class="fa fa-edit"></i></a></span></td>
														</c:if>
														

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</tbody>
										</c:otherwise>
									</c:choose>
								</table>
							</div>
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label"><spring:message code="" text="Total Area(In Acres)" /></label>
						<div class="col-sm-2 ">
							<form:input name="" type="text"
								class="form-control" readonly="true"
								path="licenseApplicationMasterDTO.ciTotArea" id="ciTotArea" maxlength=""
								minlength="" />
						</div>
					</div>
					




					<div class="text-center">
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save" onclick="saveApplicationPurpose(this)"
									id="">
									<spring:message code="" text="Next" />
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save" onclick="saveApplicationPurpose(this)"
									id="">
									<spring:message code="" text="Save & Next" />
								</button>

							</c:otherwise>
						</c:choose>
						
						<button type="button" class="btn btn-danger"
							onclick="showTab('#applicantForm')" name="button"
							value="Back">
							<spring:message code="" text="Back" />
						</button>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>