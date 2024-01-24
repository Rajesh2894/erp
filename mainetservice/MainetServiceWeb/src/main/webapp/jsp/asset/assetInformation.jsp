<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetInformation.js"></script>
<script type="text/javascript" src="js/asset/editAsset.js"></script>
<script type="text/javascript" src="js/asset/viewAsset.js"></script>
<script type="text/javascript" src="js/asset/assetServiceInformation.js"></script>
<!-- changes for bulk eport -->
<script type="text/javascript" src="js/asset/assetRegisterUpload.js"></script>
  <script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- T#101107 This page assetInformation.jsp is used by two modules 
named as 1) Asset Module(AST) 
         2) ITAsset Module(IAST)
          if(Asset Module){
          assetFlag = true;
         }else{
         assetFlag =false;
         }
         <c:choose>
            <c:when test="${assetFlag}">
               Asset Module code
			</c:when>
			<c:otherwise>
              ITAsset Module code
			</c:otherwise>
		</c:choose>
                -->
               
<c:set var="assetFlag"
			value="${userSession.moduleDeptCode == 'AST' ? true : false}" />

			<c:if test="${userSession.moduleDeptCode != 'AST' && userSession.moduleDeptCode != 'IAST'}">
				<c:set var="assetFlag"	value="${command.astDetailsDTO.assetInformationDTO.deptCode == 'AST' ? true : false}" />
			</c:if>     
 <c:if test="${assetFlag}">               
<div class="widget-content padding" id="contentInformation">
</c:if>
	<form:form action="AssetRegistration.html" id="informationTabForm"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<c:if test="${userSession.moduleDeptCode == 'AST'}">    
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDiv"></div>
			</c:if>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="saveMode" id="saveMode" />
		<form:hidden path="astDetailsDTO.assetInformationDTO.assetId"
			id="assetId" />
		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
			
		<c:choose>
			<c:when test="${assetFlag}">
				<form:hidden path="astDetailsDTO.assetInformationDTO.assetClass1"
					id="assetClassification" />
				<input type="hidden" id="atype" value="AST" />
			</c:when>
			<c:otherwise>
			<form:hidden path="astDetailsDTO.assetInformationDTO.assetClass1"
					id="assetClassification" />
				<input type="hidden" id="atype" value="IAST" />
			</c:otherwise>
		</c:choose>

		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
					<c:choose>

							<c:when test="${assetFlag}">
                             <a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.information.assettype" /></a>
							</c:when>
							<c:otherwise>
                             <a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.item.details" text="Item Detais"/></a>
							</c:otherwise>
						</c:choose>
						
					</h4>
					<!----------------------------------------------- Asset information start ---------------------------------- -->
				</div>


				<div id="Applicant" class="panel-collapse collapse in">
					<div class="panel-body">
						<c:if test="${assetFlag}">

							<div class="form-group">
								<div class="col-sm-8"></div>
								<c:forEach items="${command.getLevelData('ASC')}"
									var="assetsType">
									<c:if
										test="${assetsType.lookUpId eq command.astDetailsDTO.assetInformationDTO.assetClass1}">
										<c:if test="${assetsType.lookUpCode eq 'IMO'}">
											<c:if test="${command.getGisValue() eq 'Y'}">
												<%-- <button type="button" class="btn btn-success btn-submit"
													onclick=" window.open('${command.gISUri}&uniqueid=${command.astDetailsDTO.assetInformationDTO.astCode}')"
													id="">
													<spring:message text="Map asset on GIS map" code="" />
												</button>
												<button class="btn btn-blue-2" type="button"
													onclick=" window.open('${command.gISUri}&id=${command.astDetailsDTO.assetInformationDTO.astCode} ')"
													id="">
													<spring:message text="View asset on GIS map" code="" />
												</button> --%>
											</c:if>
										</c:if>
									</c:if>
								</c:forEach>
							</div>
						</c:if>
					</div>
					<c:choose>
						<c:when test="${assetFlag}">
							<div class="form-group">
								<apptags:input labelCode="asset.information.assetcode"
									path="astDetailsDTO.assetInformationDTO.astCode"
									isMandatory="false"
									isDisabled="${command.modeType eq 'C'|| command.modeType eq 'V' || command.modeType eq 'E' || command.modeType eq 'D'}"></apptags:input>
								<apptags:input path="astDetailsDTO.assetInformationDTO.serialNo"
									labelCode="asset.information.assetsrno"
									cssClass="form-control alphaNumeric"
									onBlur="validateDuplicateSerialNumber(this)"
									isMandatory="false"
									isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									maxlegnth="100" />
							</div>
						</c:when>

						<c:otherwise>
							<div class="form-group">
								<%-- <form:hidden path="astDetailsDTO.assetInformationDTO.astCode"
									id="astCode" /> --%>
								<apptags:input path="astDetailsDTO.assetInformationDTO.serialNo"
									labelCode="asset.information.registerSerialNo"
									cssClass="alphaNumeric  form-control mandColorClass required-control"
									onBlur="validateDuplicateSerialNumber(this)" isMandatory="false"
									isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									maxlegnth="100" />
								<label class="col-sm-2 control-label required-control"
									for="assetgroup"> <spring:message
										code="asset.information.hardwareName" text="Hardware Name" /></label>
								<c:set var="baseLookupCodeACL"
									value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCodeACL)}"
									path="astDetailsDTO.assetInformationDTO.assetClass2"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="true" changeHandler="getClassification(this)" />
							</div>
						</c:otherwise>
					</c:choose>

					<!-- </div> -->
					<!--add new prefix  -->


					<c:choose>

						<c:when test="${assetFlag}">
							<c:set var="baseLookupCodeACL"
								value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
							<c:set var="baseLookupCodeASC"
								value="${userSession.moduleDeptCode == 'AST' ? 'ASC':'ISC'}" />
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="assetgroup"> <spring:message
										code="asset.info.assetclass" /></label>
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCodeACL)}"
									path="astDetailsDTO.assetInformationDTO.assetClass2"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="true" changeHandler="getClassification(this)" />
								<label class="col-sm-2 control-label required-control"
									for="assetgroup"> <spring:message
										code="asset.info.assetClassification" /></label>
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCodeASC)}"
									path="astDetailsDTO.assetInformationDTO.assetClass1"
									disabled="true" cssClass="form-control" hasChildLookup="false"
									hasId="true" showAll="false" selectOptionLabelCode="Select"
									isMandatory="true" />
							</div>
						</c:when>
						<c:otherwise>

							
						</c:otherwise>
					</c:choose>





					<!--add new prefix  -->

					<c:choose>

						<c:when test="${assetFlag}">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="asset.information.assetname" /></label>
								<div class="col-sm-4">
									<form:input path="astDetailsDTO.assetInformationDTO.assetName"
										cssClass="form-control alphaNumeric" id="assetName"
										maxlength="100" data-rule-required="true" isMandatory="true"
										disabled="${command.modeType eq 'V' || command.modeType eq 'E'}" />
								</div>
								<apptags:textArea labelCode="asset.information.assetdetails"
									path="astDetailsDTO.assetInformationDTO.details"
									isDisabled="${command.modeType eq 'V'}" isMandatory="true"
									maxlegnth="100"></apptags:textArea>
							</div>
						</c:when>
						<c:otherwise>
							<form:hidden path="astDetailsDTO.assetInformationDTO.assetName"
								id="assetName" value="Df" />
							<form:hidden path="astDetailsDTO.assetInformationDTO.details"
								id="details" value="Df" /> 
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<c:choose>

							<c:when test="${assetFlag}">
								<apptags:input labelCode="asset.information.assetidenti"
									path="astDetailsDTO.assetInformationDTO.assetModelIdentifier"
									isReadonly="${command.modeType eq 'V'}" isDisabled=""
									isMandatory="false" cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
								<apptags:textArea labelCode="asset.information.purpose"
									path="astDetailsDTO.assetInformationDTO.purpose"
									isDisabled="${command.modeType eq 'V'}" isMandatory="true"
									cssClass="alphaNumeric" maxlegnth="100"></apptags:textArea>
							</c:when>
							<c:otherwise>
								<apptags:input labelCode="asset.information.assetsrno"
									path="astDetailsDTO.assetInformationDTO.assetModelIdentifier"
									isReadonly="${command.modeType eq 'V'}" 
                                    isDisabled="${command.modeType eq 'V' }"
									isMandatory="true" cssClass="alphaNumeric" maxlegnth="100"></apptags:input>

								<label class="col-sm-2 control-label "> <spring:message
										code="asset.information.ramSize" text="RAM Size" /></label>
								<c:set var="baseLookupCode"
									value='IRM' />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.ramSize"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="false"
									cssClass="form-control chosen-select-no-results" />
								<form:hidden path="astDetailsDTO.assetInformationDTO.purpose"
									id="purpose" value="Df" />

							</c:otherwise>
						</c:choose>

					</div>
					<c:choose>

						<c:when test="${assetFlag}">

							<c:if
								test="${command.modeType eq 'V' && not empty command.astDetailsDTO.assetInformationDTO.assetModelIdentifier}">
								<div class="form-group">
									<c:set var="GISID"
										value="${command.astDetailsDTO.assetInformationDTO.assetModelIdentifier}"></c:set>

									<div class="col-sm-2"></div>
									<div class="col-sm-2">
										<a href="jsp/asset/GIS/serviceConnection.jsp?ids=${GISID}"
											id="GISID" target="_blank"><spring:message code=""
												text="Click Here To View Location" /></a>
									</div>
								</div>
							</c:if>
							<!--------------------------- condition for the asset group and asset type ------------------------------ -->
							<c:set var="baseLookupCodeASG" value="ASG" />
							<c:set var="baseASG"
								value="${command.getLevelData(baseLookupCodeASG)}" />
							<div class="form-group">
								<c:if test="${not empty baseASG}">
									<c:set var="baseLookupCodeASG" value="ASG" />
									<label class="col-sm-2 control-label " for="assetgroup">
										<spring:message code="asset.information.assetgroup" />
									</label>
									<apptags:lookupField items="${baseASG}"
										path="astDetailsDTO.assetInformationDTO.assetGroup"
										disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="false" selectOptionLabelCode="Select"
										isMandatory="true" />
								</c:if>
								<c:set var="baseLookupCodeTNG" value="TNG" />
								<c:set var="baseTNG"
									value="${command.getLevelData(baseLookupCodeTNG)}" />
								<c:if test="${not empty  baseTNG}">
									<!-- <div class="form-group"> -->
									<c:set var="baseLookupCodeTNG" value="TNG" />
									<label for="" class="col-sm-2 control-label"><spring:message
											code="asset.information.assettype" /></label>
									<apptags:lookupField items="${baseTNG}"
										path="astDetailsDTO.assetInformationDTO.assetType"
										disabled="${command.modeType eq 'V'}" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select"  />

								</c:if>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label class="col-sm-2 control-label "> <spring:message
										code="asset.information.processor" text="Processor" /></label>
								<c:set var="baseLookupCode"
									value='IPR' />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.processor"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="false"
									cssClass="form-control chosen-select-no-results" />

								<label class="col-sm-2 control-label "> <spring:message
										code="asset.information.osName" text="OS Name" /></label>
								<c:set var="baseLookupCode"
									value='IOS' />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.osName"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y') }" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="false"
									cssClass="form-control chosen-select-no-results" />
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label "> <spring:message
										code="asset.information.screenSize" text="Screen Size" /></label>
								<c:set var="baseLookupCode"
									value='ISZ'/>
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.screenSize"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="false"
									cssClass="form-control chosen-select-no-results" />
								<label class="col-sm-2 control-label "> <spring:message
										code="asset.information.hardDiskSize" text="Hard Disk Size" /></label>
								<c:set var="baseLookupCode"
									value='IHD' />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.hardDiskSize"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="Select"
									isMandatory="false"
									cssClass="form-control chosen-select-no-results" />
							</div>

							<form:hidden path="astDetailsDTO.assetInformationDTO.assetGroup"
								id="assetGroup" value="11" />
							<form:hidden path="astDetailsDTO.assetInformationDTO.assetType"
								id="assetType" value="11" />
						</c:otherwise>
					</c:choose>

					<!--------------------------- condition for the asset group and asset type  end --------------- -->



					<c:choose>

						<c:when test="${assetFlag}">
							<div class="form-group">
								<apptags:input labelCode="asset.information.assetsimilar"
									path="astDetailsDTO.assetInformationDTO.noOfSimilarAsset"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="form-control hasNumber" maxlegnth="20"></apptags:input>

								<label class="control-label col-sm-2" for=""> <spring:message
										code="asset.information.assetparentidenti" />
								</label>
								<div class="col-sm-4">
									<form:select
										path="astDetailsDTO.assetInformationDTO.assetParentIdentifier"
										disabled="${command.modeType eq 'V'}"
										cssClass="form-control chosen-select-no-results"
										id="astParentId">
										<form:option value="">
											<spring:message code='asset.info.select' text="Select" />
										</form:option>
										<c:forEach items="${command.serailNoSet}" var="serialNoSet">
											<form:option value="${serialNoSet}" code="">${serialNoSet}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<apptags:input labelCode="asset.information.assetrfiid"
									path="astDetailsDTO.assetInformationDTO.rfiId"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="alphaNumeric form-control" maxlegnth="4"
									onBlur="duplicateCheckRfId(this);"></apptags:input>
								<apptags:input labelCode="asset.information.assetbarcodeno"
									path="astDetailsDTO.assetInformationDTO.barcodeNo"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="form-control hasNumber" maxlegnth="20"></apptags:input>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control">
									<spring:message code="asset.information.assetaquation" />
								</label>
								<c:set var="baseLookupCode"
									value="${userSession.moduleDeptCode == 'AST' ? 'AQM':'IQM'}" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.acquisitionMethod"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select" isMandatory="true"
									cssClass="form-control chosen-select-no-results" />

								<apptags:input labelCode="asset.information.regdet"
									path="astDetailsDTO.assetInformationDTO.registerDetail"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="alphaNumeric" maxlegnth="100"></apptags:input>

							</div>
							<div class="form-group">
								<apptags:input labelCode="asset.information.assetbrandname"
									path="astDetailsDTO.assetInformationDTO.brandName"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="hasCharacter" maxlegnth="100"></apptags:input>
								<apptags:textArea labelCode="asset.information.rmk"
									path="astDetailsDTO.assetInformationDTO.remark"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="alphaNumeric" maxlegnth="100"></apptags:textArea>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label class="col-sm-2 control-label "> <spring:message
										code="asset.information.manufacturingYear"
										text="Manifacturig Year" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input class="form-control " disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
											id="manufacturingYear"
											path="astDetailsDTO.assetInformationDTO.manufacturingYear"
											isMandatory="false" maxlength="10"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
								<apptags:input labelCode="asset.information.assetbrandname"
									path="astDetailsDTO.assetInformationDTO.brandName"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="hasCharacter" maxlegnth="100"></apptags:input>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control">
									<spring:message code="asset.information.purchaseMethod"
										text="Purchase Method" />
								</label>
								<c:set var="baseLookupCode"
									value="${userSession.moduleDeptCode == 'AST' ? 'AQM':'IQM'}" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.acquisitionMethod"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select" isMandatory="true"
									cssClass="form-control chosen-select-no-results" />
								<label class="col-sm-2 control-label required-control" for="">
									<spring:message code="asset.information.assetstatus" />
								</label>
								<form:hidden
									path="astDetailsDTO.assetInformationDTO.assetStatus"
									id="assetStatusId"></form:hidden>
								<c:set var="baseLookupCode" value="AST" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.assetStatus"
									disabled="true" cssClass="form-control" hasChildLookup="false"
									hasId="true" showAll="false" selectOptionLabelCode="Select"
									isMandatory="true" />
							</div>
						<div class="form-group">

							<label class="col-sm-2 control-label"
								for="applicantTitle"> <spring:message
									code="asset.information.printerType" text="Printer Type" />
							</label>
							<c:set var="baseLookupCode" value="PRT" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="astDetailsDTO.assetInformationDTO.printerTypeId"
								cssClass="form-control chosen-select-no-results" disabled="${command.modeType eq 'V'}"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="applicantinfo.label.select"/>

						</div>
						<div class="form-group">
								<apptags:textArea labelCode="asset.information.rmk"
									path="astDetailsDTO.assetInformationDTO.remark"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="alphaNumeric" maxlegnth="100"></apptags:textArea>
									
									<c:set var="baseLookupCodeASC"
								value="${userSession.moduleDeptCode == 'AST' ? 'ASC':'ISC'}" />

							 <apptags:lookupField
									items="${command.getLevelData(baseLookupCodeASC)}"
									path="astDetailsDTO.assetInformationDTO.assetClass1"
									disabled="true" cssClass="form-control itAssetClass1" hasChildLookup="false"
									hasId="true" showAll="false" selectOptionLabelCode="Select"
									isMandatory="true" />
							</div>
						</c:otherwise>
					</c:choose>
					<%-- <div class="form-group">
						<apptags:input labelCode="asset.information.assetbrandname"
							path="astDetailsDTO.assetInformationDTO.brandName"
							isDisabled="${command.modeType eq 'V'}" isMandatory="false"
							cssClass="hasCharacter" maxlegnth="100"></apptags:input>
						<apptags:textArea labelCode="asset.information.rmk"
							path="astDetailsDTO.assetInformationDTO.remark"
							isDisabled="${command.modeType eq 'V'}" isMandatory="false"
							cssClass="alphaNumeric" maxlegnth="100"></apptags:textArea>
					</div> --%>
					<!------- complete------------------------- -->




					<%-- 	<apptags:input labelCode="asset.information.assetcusodian"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.custodian"
								isMandatory="false"></apptags:input> --%>
					<c:choose>
						<c:when test="${assetFlag}">
							<div class="form-group">

								<label class="col-sm-2 control-label required-control" for="">
									<spring:message code="asset.information.assetstatus" />
								</label>
								<form:hidden
									path="astDetailsDTO.assetInformationDTO.assetStatus"
									id="assetStatusId"></form:hidden>
								<c:set var="baseLookupCode" value="AST" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.assetStatus"
									disabled="true" cssClass="form-control" hasChildLookup="false"
									hasId="true" showAll="false" selectOptionLabelCode="Select"
									isMandatory="true" />
								<label class="control-label col-sm-2" for=""> <spring:message
										code="asset.information.assetemployeeid" />
								</label>
								<div class="col-sm-4">
									<form:select
										path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.employeeId"
										disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										cssClass="form-control chosen-select-no-results"
										id="assetEmpId">
										<form:option value="">
											<spring:message code='asset.info.select' text="Select" />
										</form:option>
										<c:forEach items="${command.empList}" var="employeeList">
											<form:option value="${employeeList.empId }" code="">${employeeList.empname }</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</c:when>
						<c:otherwise>
						<c:if test ="${command.modeType eq 'C'  }">
						<div class="form-group">
									<label class="col-sm-2 control-label"  for="">
										<spring:message code="asset.bulk.export" />
									</label>
									<div class="col-sm-4">
										<form:checkbox path="astDetailsDTO.isBulkExport"
											checked="true"
											id="isBulkExport" onclick ="showAndHideBulkExport()"
											class="margin-top-10 margin-left-10" disabled="false"></form:checkbox>

									</div>
									<div id ="quantityDiv">
									<apptags:input labelCode="asset.requisition.quantity"
										cssClass="decimal text-right form-control"
										isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y') || command.modeType eq 'E'}"
										path="astDetailsDTO.assetInformationDTO.quantity"
										isMandatory="true" maxlegnth="5"></apptags:input>
										</div>
								</div>
						<div class="form-group" id ="bulkExport">
						<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
								code="excel.template.generate" text="Generate Excel" /></label>
						 <div class="col-sm-3 text-left">
							<button type="button" class="btn btn-success save"
								name="button-Cancel" value="import"
								onclick="downloadBulkTamplate();" id="import">
								<spring:message code="excel.template" text="Excel Template" />
							</button>
						 </div> 
						
                 <div class="form-group text-center">
						<label class="col-sm-3 control-label" for="ExcelFileUpload"><spring:message
								code="excel.file.upload" text="Excel File Upload" /></label>
						<div class="col-sm-3 text-left"> 
							<apptags:formField fieldPath="uploadFileName"
								showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
								currentCount="1" fieldType="7">
							</apptags:formField>
							<small class="text-blue-2" >(Upload
								Excel upto 5MB )</small>
						 </div> 
						
						<%-- <form:hidden path="uploadFileName" id="filePath" /> --%>
					</div>
					</div>
					</c:if>
						</c:otherwise>
					</c:choose>






					<div class="form-group" style="display: none" id="buildPropId">
						<apptags:input labelCode="asset.search.roadName" maxlegnth="49"
							path="astDetailsDTO.assetInformationDTO.roadName"
							isMandatory="false"
							isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"></apptags:input>

						<apptags:input labelCode="asset.search.pincode"
							cssClass='hasPincode'
							path="astDetailsDTO.assetInformationDTO.pincode"
							isMandatory="false" maxlegnth="6"
							isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"></apptags:input>

					</div>
				</div>
			</div>

			<!----------------------------------------------- Asset information end ---------------------------------- -->
			<!----------------------------------------------- Asset inventory start ---------------------------------- -->
		
	
		
		<!-- Purchase Information changes ends -->
		
			<!--T#92465  -->
			<c:if test="${userSession.moduleDeptCode == 'AST'}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse1" href="#a1"><spring:message
									code="asset.information.assetinventoryinformation" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<apptags:input labelCode="asset.information.assetinventoryno"
									path="astDetailsDTO.assetInformationDTO.assetInventoryInfoDTO.inventoryNo"
									isDisabled="${command.modeType eq 'V'}" isMandatory="false"
									cssClass="form-control hasNumber" maxlegnth="20"></apptags:input>
								<label class="col-sm-2 control-label" for="datepicker"><spring:message
										code="asset.information.assetinventoryon" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input class="form-control datepicker" id="txtFromDate1"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetInventoryInfoDTO.lastInventoryOn"
											maxlength="10"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
							</div>

							<div class="form-group">
								<apptags:input labelCode="asset.information.assetinventorynote"
									isDisabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.assetInformationDTO.assetInventoryInfoDTO.inventoryNote"
									isMandatory="false" maxlegnth="100"></apptags:input>

								<label class="col-sm-2 control-label"> <spring:message
										code="asset.information.assetinvestment" /></label>
								<c:set var="baseLookupCode" value="INR" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="astDetailsDTO.assetInformationDTO.investmentReason"
									disabled="${command.modeType eq 'V'}" cssClass="form-control"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select" isMandatory="false" />
								<%-- 							<apptags:checkbox
								labelCode="asset.information.assetinventorylist" value="Y"
								disabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetInformationDTO.assetInventoryInfoDTO.includeAssetInventoryLst"></apptags:checkbox> --%>

							</div>

						</div>
					</div>
				</div>
			</c:if>
			<!------------------------ Asset inventory end ----------------------- -->
			<!------------------------ Asset posting information start  ----------------------- -->
			<%-- <div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#a2"><spring:message
								code="asset.information.assetpostinginformation" /></a>
					</h4>
				</div>
				<div id="a2" class="panel-collapse collapse in">
					<div class="panel-body">
							<div class="form-group">
							<label class="col-sm-2 control-label" for="txtFromDate"><spring:message
									code="asset.information.assetcapateliseon" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker" id="txtFromDate"
										disabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.capitalizeOn"
										maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>

							<label class="col-sm-2 control-label" for="txtToDate"><spring:message
									code="asset.information.assetfirstaquisition" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control date datepicker" id="txtToDate"
										disabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.firstAcquisitionOn"
										maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>

						</div>

						<div class="form-group">
							<apptags:input labelCode="asset.information.assetacquisitionyear"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.acquisitionYear"
								isMandatory="false" cssClass="hasNumber" maxlegnth="4"></apptags:input>
							<label class="col-sm-2 control-label" for="Capitalizeon"><spring:message
									code="asset.information.assetorderon" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker" id="txtToDate1"
										disabled="${command.modeType eq 'V'}"
										path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.orderOn"
										maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>

						<div class="form-group">
								<apptags:input labelCode="asset.information.assetcusodian"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.custodian"
								isMandatory="false"></apptags:input>

							<label class="control-label col-sm-2" for=""> <spring:message
									code="asset.information.assetemployeeid" />
							</label>
							<div class="col-sm-4">
								<form:select
									path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.employeeId"
									disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
									cssClass="form-control chosen-select-no-results"
									id="assetEmpId">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:forEach items="${command.empList}" var="employeeList">
										<form:option value="${employeeList.empId }" code="">${employeeList.empname }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
							<div class="form-group">
							<label class="control-label col-sm-2" for=""> <spring:message
									code="asset.information.location" />
							</label>
							<div class="col-sm-4">
								<form:select
									path="astDetailsDTO.assetInformationDTO.assetPostingInfoDTO.location"
									disabled="${command.modeType eq 'V'}"
									cssClass="form-control chosen-select-no-results"
									id="assetLocId">
									<form:option value="">
										<spring:message code='' text="Select" />
									</form:option>
									<c:forEach items="${command.locList}" var="locationList">
										<form:option value="${locationList.locId }" code="">${locationList.locNameEng }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</div>
			</div> --%>
			<!------------------------ Asset posting information end  ----------------------- -->
			<c:if test="${userSession.moduleDeptCode == 'AST'}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse1" href="#a3"><spring:message
									code="asset.information.assetspecification" /></a>
						</h4>
					</div>
					<div id="a3" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetlength" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.length"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.lengthValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>

								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetbreadth" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.breadth"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.breadthValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetheight" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.height"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.heightValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>

								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetwidth" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.width"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.widthValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetwieght" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.weight"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOW" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.weightValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>

								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetarea" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.area"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOA" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.areaValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetvolume" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.volume"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOV" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.volumeValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>
								<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
										code="asset.information.assetcarpet" /></label>
								<div class="col-sm-4">
									<div class="input-group col-sm-12 ">
										<form:input type="text"
											cssClass="decimal text-right form-control"
											disabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.carpet"
											placeholder="99.9" maxlegnth="18"></form:input>
										<div class="input-group-field">
											<c:set var="baseLookupCode" value="UOA" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.carpetValue"
												disabled="${command.modeType eq 'V'}"
												cssClass="form-control" hasChildLookup="false" hasId="true"
												showAll="false" selectOptionLabelCode="Select"
												isMandatory="false" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<apptags:input labelCode="asset.information.no.of.floor"
									isDisabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.assetInformationDTO.assetSpecificationDTO.noOfFloor"
									isMandatory="false"
									cssClass="hasNumber text-right form-control" maxlegnth="12"></apptags:input>


							</div>
						</div>
					</div>

				</div>
			</c:if>
		</div>
		
<c:if test="${!assetFlag}">
<div class="panel-body">
						<%-- <jsp:include page="/jsp/asset/assetInformation.jsp" /> --%>
						<jsp:include page="/jsp/asset/assetServiceInformation.jsp" />						
					</div>
					<%-- <div class="panel-body">
						<jsp:include page="/jsp/asset/assetServiceInformation.jsp" />
					</div>
				    <div class="panel-body">
						<jsp:include page="/jsp/asset/assetDocumentDetails.jsp" />
					</div> --%>
</c:if>

<c:if test = "${!assetFlag && command.approvalProcess eq 'Y'  }">
<c:if
							test='${(command.astDetailsDTO.assetInformationDTO.appovalStatus != null) && (command.astDetailsDTO.assetInformationDTO.appovalStatus == "P") && (command.approvalViewFlag == "A")}'>
							<div class="panel-group accordion-toggle"
								id="accordion_single_collapse">
								<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
							</div>
							<div class="text-center widget-content padding">
								<button type="button" id="save"
									class="btn btn-success btn-submit"
									onclick="saveAssetInfoApprovalData(this);">
									<spring:message code="asset.transfer.save" text="Save" />
								</button>
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel"
									onclick="window.location.href='AdminHome.html'"
									id="button-Cancel">
									<spring:message code="asset.information.back" text="Back" />
								</button>
							</div>
						</c:if>
</c:if>
		<c:if test="${command.approvalProcess ne 'Y' }">
			<div class="text-center">
				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
						<c:choose>
							<c:when
								test="${command.modeType eq 'C' && command.approvalProcess eq 'SEND' }">
								<c:set var="backButtonAction" value="backToDashBoard()" />
							</c:when>
							<c:otherwise>
								<c:set var="backButtonAction"
									value="backFormAstInfo('${userSession.moduleDeptCode == 'AST' ? 'AST':'IAST'}')" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction"
							value="backToHomePage('${userSession.moduleDeptCode == 'AST' ? 'AST':'IAST'}')" />
					</c:otherwise>
				</c:choose>
			<c:choose>

				<c:when test="${assetFlag}">
					<c:if
						test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveAssetInformation(this)"
							id="save">
							<spring:message code="asset.information.save&continue" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
						<button type="Reset" class="btn btn-warning" onclick="resetInfo()">
							<spring:message code="reset.msg" text="Reset" />
						</button>

					</c:if>
					<button type="button" class="btn btn-danger" name="button"
						id="Back" value="Back" onclick="${backButtonAction}">
						<spring:message code="asset.information.back" />
					</button>
				</c:when>
				<c:otherwise>
					<c:if
						test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" id="saveITAsset">
							<spring:message code="asset.documentdetails.save&continue" />
						</button>
					</c:if>


					<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button class="btn btn-warning" id="resetItAsset"
									type="Reset">
									<spring:message code="reset.msg" text="Reset" />
								</button>
						
					</c:if>
					<button type="button" class="btn btn-danger" name="button"
						id="Back" value="Back"
						onclick="window.location.href='ITAssetSearch.html'">
						<spring:message code="asset.information.back" />
					</button>


				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
	</form:form>
	 <c:if test="${assetFlag}">               
     </div>
     </c:if>

<script>
$("#saveITAsset").click(function(){
	
	saveITAssetForm(this);
	});
function downloadBulkTamplate(){
	
	window.location.href="ITAssetRegistration.html?ExcelTemplateDataBulk";



}

</script>