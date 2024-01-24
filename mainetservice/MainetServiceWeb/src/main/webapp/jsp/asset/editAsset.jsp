<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/editAsset.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="${userSession.moduleDeptCode == 'AST' ? 'asset.information.management':'asset.information.ITmanagement'}"
					text="Asset Registration" />
			</h2>
			<apptags:helpDoc url="AssetRegistration.html"></apptags:helpDoc>
		</div>
		<div class="pagediv">
			<div class="widget-content padding">
				<form:form action="AssetRegistration.html" id="informationTabUpdate"
					method="post" class="form-horizontal">
					<input type="hidden" id="moduleDeptCode"
						value="${userSession.moduleDeptCode == 'AST' ? 'AQM':'IQM'}">
					<input type="hidden" id="moduleDeptUrl"
						value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
					<c:set var="assetFlag"
						value="${userSession.moduleDeptCode == 'AST' ? true : false}" />

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
											code="asset.information.assettype" /></a>
								</h4>
							</div>
							<div id="Applicant" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:choose>
										<c:when test="${assetFlag}">

											<div class="form-group">
												<%-- <apptags:input labelCode="asset.information.assetid"
								path="astDetailsDTO.assetInformationDTO.assetId"
								isMandatory="false"
								isDisabled="${command.modeType eq 'C'|| command.modeType eq 'V' || command.modeType eq 'E' || command.modeType eq 'D'}"></apptags:input> --%>

												<apptags:input labelCode="asset.information.assetcode"
													path="astDetailsDTO.assetInformationDTO.astCode"
													isMandatory="false"
													isDisabled="${command.modeType eq 'C'|| command.modeType eq 'V' || command.modeType eq 'E' || command.modeType eq 'D'}"></apptags:input>

												<apptags:input labelCode="asset.information.assetsrno"
													path="astDetailsDTO.assetInformationDTO.serialNo"
													isDisabled="true" isMandatory="true"
													cssClass="alphaNumeric"></apptags:input>
											</div>

										</c:when>

										<c:otherwise>
											<div class="form-group">
												<form:hidden
													path="astDetailsDTO.assetInformationDTO.astCode"
													id="astCode" />
												<apptags:input
													path="astDetailsDTO.assetInformationDTO.serialNo"
													labelCode="asset.information.registerSerialNo"
													cssClass="alphaNumeric  form-control mandColorClass required-control"
													onBlur="validateDuplicateSerialNumber(this)"
													isMandatory="true"
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
									<c:choose>

										<c:when test="${assetFlag}">
											<div class="form-group">
												<label class="col-sm-2 control-label required-control"><spring:message
														code="asset.information.assetname" /></label>
												<div class="col-sm-4">
													<form:input
														path="astDetailsDTO.assetInformationDTO.assetName"
														cssClass="form-control hasCharacter" id="assetName"
														data-rule-required="true"
														onblur="validateDuplicateAsset(this)" isMandatory="true"
														disabled="true" />
												</div>
												<apptags:textArea labelCode="asset.information.assetdetails"
													path="astDetailsDTO.assetInformationDTO.details"
													isDisabled="true" isMandatory="true"
													cssClass="alphaNumeric"></apptags:textArea>
											</div>
										</c:when>
										<c:otherwise>
											<form:hidden
												path="astDetailsDTO.assetInformationDTO.assetName"
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
													isDisabled="true" isMandatory="false"
													cssClass="alphaNumeric"></apptags:input>
												<apptags:textArea labelCode="asset.information.purpose"
													path="astDetailsDTO.assetInformationDTO.purpose"
													isDisabled="true" isMandatory="true"
													cssClass="alphaNumeric"></apptags:textArea>
											</c:when>
											<c:otherwise>
												<apptags:input labelCode="asset.information.assetsrno"
													path="astDetailsDTO.assetInformationDTO.assetModelIdentifier"
													isReadonly="${command.modeType eq 'V'}" isDisabled=""
													isMandatory="false" cssClass="alphaNumeric" maxlegnth="20"></apptags:input>

												<label class="col-sm-2 control-label "> <spring:message
														code="asset.information.ramSize" text="RAM Size" /></label>
												<c:set var="baseLookupCode" value='IRM' />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.ramSize"
													disabled="false" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="false"
													cssClass="form-control chosen-select-no-results" />
												<form:hidden
													path="astDetailsDTO.assetInformationDTO.purpose"
													id="purpose" value="Df" />

											</c:otherwise>
										</c:choose>
									</div>
									<!--------------------------- condition for the asset group and asset type ------------------------------ -->

									<!--------------------------- condition for the asset group and asset type  end --------------- -->
									<c:choose>

										<c:when test="${assetFlag}">
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
														cssClass="form-control" hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="Select" isMandatory="true"
														changeHandler="assetGroupChange(this)" />
												</c:if>
												<c:set var="baseLookupCodeTNG" value="TNG" />
												<c:set var="baseTNG"
													value="${command.getLevelData(baseLookupCodeTNG)}" />
												<c:if test="${not empty  baseTNG}">
													<!-- <div class="form-group"> -->
													<c:set var="baseLookupCodeTNG" value="TNG" />
													<label for=""
														class="col-sm-2 control-label required-control"><spring:message
															code="asset.information.assettype" /></label>
													<apptags:lookupField items="${baseTNG}"
														path="astDetailsDTO.assetInformationDTO.assetType"
														disabled="${command.modeType eq 'V'}"
														cssClass="form-control" hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="Select" isMandatory="true" />

												</c:if>
											</div>
											<div class="form-group">
												<c:set var="baseLookupCodeACL"
													value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
												<label class="col-sm-2 control-label required-control"
													for="assetgroup"> <spring:message
														code="asset.info.assetclass" /></label>
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCodeACL)}"
													path="astDetailsDTO.assetInformationDTO.assetClass2"
													disabled="true" cssClass="form-control"
													hasChildLookup="false" hasId="true" showAll="false"
													selectOptionLabelCode="Select" isMandatory="true"
													changeHandler="getClassification(this)" />
												<c:set var="baseLookupCodeASC"
													value="${userSession.moduleDeptCode == 'AST' ? 'ASC':'ISC'}" />
												<label class="col-sm-2 control-label required-control"
													for="assetgroup"> <spring:message
														code="asset.info.assetClassification" /></label>
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCodeASC)}"
													path="astDetailsDTO.assetInformationDTO.assetClass1"
													disabled="true" cssClass="form-control"
													hasChildLookup="false" hasId="true" showAll="false"
													selectOptionLabelCode="Select" isMandatory="true" />
											</div>
											<div class="form-group">
												<apptags:input labelCode="asset.information.assetsimilar"
													path="astDetailsDTO.assetInformationDTO.noOfSimilarAsset"
													isDisabled="true" isMandatory="false"
													cssClass="form-control hasNumber"></apptags:input>

												<label class="control-label col-sm-2" for=""> <spring:message
														code="asset.information.assetparentidenti" />
												</label>
												<div class="col-sm-4">
													<form:select
														path="astDetailsDTO.assetInformationDTO.assetParentIdentifier"
														disabled="true"
														cssClass="form-control chosen-select-no-results"
														id="astParentId">
														<form:option value="">
															<spring:message code='asset.info.select' text="Select" />
														</form:option>
														<c:forEach items="${command.serailNoSet}"
															var="serialNoSet">
															<form:option value="${serialNoSet}" code="">${serialNoSet}</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
											<div class="form-group">

												<apptags:input labelCode="asset.information.assetrfiid"
													path="astDetailsDTO.assetInformationDTO.rfiId"
													isDisabled="true" isMandatory="false"
													cssClass="alphaNumeric"></apptags:input>

												<apptags:input labelCode="asset.information.assetbarcodeno"
													path="astDetailsDTO.assetInformationDTO.barcodeNo"
													isDisabled="true" isMandatory="false"
													cssClass="form-control hasNumber"></apptags:input>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label required-control"
													for=""> <spring:message
														code="asset.information.assetstatus" />
												</label>
												<form:hidden
													path="astDetailsDTO.assetInformationDTO.assetStatus"
													id="assetStatusId"></form:hidden>
												<c:set var="baseLookupCode" value="AST" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.assetStatus"
													disabled="true" cssClass="form-control"
													hasChildLookup="false" hasId="true" showAll="false"
													selectOptionLabelCode="Select" isMandatory="true" />

												<apptags:input labelCode="asset.information.regdet"
													path="astDetailsDTO.assetInformationDTO.registerDetail"
													isDisabled="true" isMandatory="false"
													cssClass="alphaNumeric"></apptags:input>

											</div>
											<div class="form-group">
												<apptags:input labelCode="asset.information.assetbrandname"
													path="astDetailsDTO.assetInformationDTO.brandName"
													isDisabled="true" isMandatory="false"
													cssClass="hasCharacter"></apptags:input>
												<apptags:textArea labelCode="asset.information.assetremark"
													path="astDetailsDTO.assetInformationDTO.remark"
													isDisabled="true" isMandatory="false"
													cssClass="alphaNumeric" maxlegnth="100"></apptags:textArea>
											</div>
											<!------- complete------------------------- -->
											<div class="form-group">
												<label class="col-sm-2 control-label required-control">
													<spring:message code="asset.information.assetaquation" />
												</label>
												<c:set var="baseLookupCode"
													value="${userSession.moduleDeptCode == 'AST' ? 'AQM':'IQM'}" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.acquisitionMethod"
													disabled="true" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="true" cssClass="form-control" />

												<label class="col-sm-2 control-label"> <spring:message
														code="asset.information.assetinvestment" /></label>
												<c:set var="baseLookupCode" value="INR" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.investmentReason"
													disabled="true" cssClass="form-control"
													hasChildLookup="false" hasId="true" showAll="false"
													selectOptionLabelCode="Select" isMandatory="false" />
											</div>

										</c:when>
										<c:otherwise>
											<form:hidden
												path="astDetailsDTO.assetInformationDTO.assetClass1"
												id="assetClass1" value="11" />
											<div class="form-group">
												<label class="col-sm-2 control-label "> <spring:message
														code="asset.information.processor" text="Processor" /></label>
												<c:set var="baseLookupCode" value='IPR' />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.processor"
													disabled="false" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="false"
													cssClass="form-control chosen-select-no-results" />

												<label class="col-sm-2 control-label "> <spring:message
														code="asset.information.osName" text="OS Name" /></label>
												<c:set var="baseLookupCode" value='IOS' />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.osName"
													disabled="false" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="false"
													cssClass="form-control chosen-select-no-results" />
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label "> <spring:message
														code="asset.information.screenSize" text="Screen Size" /></label>
												<c:set var="baseLookupCode" value='ISZ' />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.screenSize"
													disabled="false" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="false"
													cssClass="form-control chosen-select-no-results" />
												<label class="col-sm-2 control-label "> <spring:message
														code="asset.information.hardDiskSize"
														text="Hard Disk Size" /></label>
												<c:set var="baseLookupCode" value='IHD' />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.hardDiskSize"
													disabled="false" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="false"
													cssClass="form-control chosen-select-no-results" />
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label "> <spring:message
														code="asset.information.manufacturingYear"
														text="Manifacturig Year" /></label>
												<div class="col-sm-4">
													<div class="input-group">
														<form:input class="form-control datepicker"
															disabled="false" id="manufacturingYear"
															path="astDetailsDTO.assetInformationDTO.manufacturingYear"
															isMandatory="false" maxlength="10"></form:input>
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
													</div>
												</div>
												<apptags:input labelCode="asset.information.assetbrandname"
													path="astDetailsDTO.assetInformationDTO.brandName"
													isDisabled="true" isMandatory="false"
													cssClass="hasCharacter"></apptags:input>
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
												<label class="col-sm-2 control-label required-control"
													for=""> <spring:message
														code="asset.information.assetstatus" />
												</label>
												<form:hidden
													path="astDetailsDTO.assetInformationDTO.assetStatus"
													id="assetStatusId"></form:hidden>
												<c:set var="baseLookupCode" value="AST" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="astDetailsDTO.assetInformationDTO.assetStatus"
													disabled="true" cssClass="form-control"
													hasChildLookup="false" hasId="true" showAll="false"
													selectOptionLabelCode="Select" isMandatory="true" />
											</div>
											<div class="form-group">

												<apptags:textArea labelCode="asset.information.assetremark"
													path="astDetailsDTO.assetInformationDTO.remark" maxlegnth="100"
													isDisabled="true" isMandatory="false"
													cssClass="alphaNumeric"></apptags:textArea>
											</div>

											
										</c:otherwise>
									</c:choose>
									<div class="form-group">
										<label for="" class="col-sm-2 control-label"><spring:message
												code="asset.information.maintainanceType" /></label>
										<div class="col-sm-4">
											<form:select path="" id="accountCode" cssClass="form-control"
												onchange="openAST(this.value)">
												<form:option value="" selected="selected">
													<spring:message code="asset.info.select" />
												</form:option>
												<form:option value="showAstInfoPage">
													<spring:message code="asset.maintainanceType.changeInfo" />
												</form:option>
												<c:if test="${userSession.moduleDeptCode == 'AST' }">
													<form:option value="showAstClsPage" selected="">
														<spring:message code="asset.maintainanceType.changeloc" />
													</form:option>
												</c:if>
												<c:choose>

													<c:when test="${userSession.moduleDeptCode == 'AST'}">
														<form:option value="showAstPurchPage">
															<spring:message code="asset.maintainanceType.changeacqu" />
														</form:option>
													</c:when>
													<c:otherwise>
														<form:option value="showAstPurchPage">
															<spring:message
																code="asset.maintainanceType.changePurchaseType" text ="Change Purchase Details"  />
														</form:option>
													</c:otherwise>
												</c:choose>

												<c:if test="${userSession.moduleDeptCode == 'AST' }">
													<form:option value="showAstRealEstate">
														<spring:message
															code="asset.maintainanceType.changerealestate" />
													</form:option>
												</c:if>
												<form:option value="showAstSerPage">
													<spring:message code="asset.maintainanceType.changeService" />
												</form:option>
												<%-- 	<form:option value="showAstInsuPage">
													<spring:message
														code="asset.maintainanceType.changeInsurance" />
												</form:option> --%>
												<form:option value="showAstInsuPageDataTable">
													<spring:message
														code="asset.maintainanceType.changeInsurance" />
												</form:option>
												<c:if test="${userSession.moduleDeptCode == 'AST' }">
													<form:option value="showAstLeasePage">
														<spring:message code="asset.maintainanceType.changeLease" />
													</form:option>
												</c:if>
												<form:option value="showAstDepreChartPage">
													<spring:message
														code="asset.maintainanceType.changeDepreciation" />
												</form:option>
												<form:option value="showAstLinePage">
													<spring:message code="asset.maintainanceType.changeLinear" />
												</form:option>
												<form:option value="showAstDocDet">
													<spring:message
														code="asset.maintainanceType.changeDocument" />
												</form:option>
											</form:select>
										</div>
									</div>


								</div>
							</div>
						</div>
					</div>
					<div class="text-center">
						<c:if test="${command.modeType eq 'E'}">
							<button type="button" class="btn btn-danger" name="button"
								id="Back" value="Back"
								onclick="backToSearch('${userSession.moduleDeptCode == 'AST' ? 'AST':'IAST'}')">
								<spring:message code="asset.information.back" />
							</button>
						</c:if>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>