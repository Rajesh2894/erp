<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/asset/assetPurchaserInfromation.js"></script>
	<script type="text/javascript" src="js/asset/assetServiceInformation.js"></script>
	<script type="text/javascript" src="js/asset/assetServiceInfoAdd.js"></script>
	<!--This page assetPurchaseInformation.jsp is used by two modules 
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
        <c:set var="assetFlag"	value="${userSession.moduleDeptCode == 'AST' ? true : false}" />

			<c:if test="${userSession.moduleDeptCode != 'AST' && userSession.moduleDeptCode != 'IAST'}">
				<c:set var="assetFlag"	value="${command.astDetailsDTO.assetInformationDTO.deptCode == 'AST' ? true : false}" />
			</c:if>
<div class="widget">  
<div class="widget-content padding assetPurchasePage">
	<form:form action="AssetRegistration.html" id="assetPurchase"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<c:if test="${userSession.moduleDeptCode == 'AST'}">    
		
		</c:if>
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivP"></div>
			<c:if test = "${userSession.moduleDeptCode == 'AST' }">
		<form:hidden path="modeType" id="modeType" />
		   </c:if>
		<input type="hidden" id="moduleDeptCode"
			value="${userSession.moduleDeptCode == 'AST' ? 'AST':'IAST'}">
		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
			<c:choose>
			<c:when test="${assetFlag}">
				<input type="hidden" id="atype" value="AST" />
			</c:when>
			<c:otherwise>
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
										code="asset.purchaser.acquisition" /></a>
							</c:when>
							<c:otherwise>
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#purchaseDetails"><spring:message
										code="asset.tab.purchase.details" text="Purchase Details" /></a>
							</c:otherwise>
						</c:choose>

					</h4>
				</div>
				<c:choose>
					<c:when test="${assetFlag}">
						<div id="Applicant" class="panel-collapse collapse in">
					</c:when>
					<c:otherwise>
						<div id="purchaseDetails" class="panel-collapse collapse in">
					</c:otherwise>
				</c:choose>
					<div class="panel-body">

						<div class="form-group">
							<c:choose>

								<c:when test="${assetFlag}">
									<label class="control-label  col-sm-2" for=""> <spring:message
											code="asset.purchaser.acquired" />
									</label>
								</c:when>
								<c:otherwise>
									<label class="control-label  col-sm-2" for=""> <spring:message
											code="asset.purchaser.vendorName" text="Vendor Name" />
									</label>
								</c:otherwise>
							</c:choose>

							<div class="col-sm-4">
								<form:select
									path="astDetailsDTO.assetPurchaseInformationDTO.fromWhomAcquired"
									disabled="${command.modeType eq 'V'}"
									class="chosen-select-no-results form-control" id="vm_VendorId"
									onchange="setVendorCode();">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:forEach items="${command.vendorList}" var="vendor">
										<form:option value="${vendor.vmVendorid}"
											code="${vendor.vmVendorcode}">${vendor.vmVendorname}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<c:choose>

							<c:when test="${assetFlag}">
                             <apptags:input labelCode="asset.purchaser.manufacturer"
								isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.manufacturer"
								isMandatory="false" maxlegnth="100"></apptags:input>
							</c:when>
							<c:otherwise>
                              <!-- need to write code for ADD button after discussion  -->
							</c:otherwise>
						</c:choose>
							
						</div>
						
						<c:choose>

							<c:when test="${assetFlag}">
							<div class="form-group">
                                <apptags:input labelCode="asset.purchaser.orderno"
								cssClass="form-control" isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.purchaseOrderNo"
								isMandatory="false" maxlegnth="20"></apptags:input>
								
							<label class="col-sm-2 control-label " id="purDate"
								for="datepicker"><spring:message
									code="asset.purchaseOrder.date" /></label>
							<div class="col-sm-4 ">
								<div class="input-group">
									<form:input class=" form-control datepicker"
										id="purchaseOrderDate" data-label="#purDate"
										disabled="${command.modeType eq 'V'}" isMandatory="false"
										path="astDetailsDTO.assetPurchaseInformationDTO.purchaseOrderDate"
										maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							</div>
							</c:when>
							<c:otherwise>
							<div class="form-group">
                                <apptags:input labelCode="asset.purchaser.pono"
								cssClass="form-control" isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.purchaseOrderNo"
								isMandatory="true" maxlegnth="20"></apptags:input>
								<apptags:input labelCode="asset.purchaser.purchaseValue"
										cssClass="decimal text-left form-control"
										isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										path="astDetailsDTO.assetPurchaseInformationDTO.costOfAcquisition"
										isMandatory="true" maxlegnth="12"></apptags:input>
								</div>
								
							</c:otherwise>
						</c:choose>
							


						


						<div class="form-group">
							<c:choose>

								<c:when test="${assetFlag}">
									<apptags:input labelCode="asset.purchaser.cost"
										cssClass="decimal text-right form-control"
										isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										path="astDetailsDTO.assetPurchaseInformationDTO.costOfAcquisition"
										isMandatory="true" maxlegnth="12"></apptags:input>
									<label class="col-sm-2 control-label required-control "
										id="acqDate" for="datepicker"><spring:message
											code="asset.acquisition.dates" /></label>
								</c:when>
								<c:otherwise>
									
									<label class="col-sm-2 control-label required-control "
										id="acqDate" for="datepicker"><spring:message
											code="asset.acquisition.dateOfPurchase"
											text="Date of Purchase" /></label>
								</c:otherwise>
							</c:choose>
							<div class="col-sm-4 ">
								<div class="input-group">
									<c:choose>
										<c:when
											test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">

											<c:choose>

												<c:when test="${assetFlag}">
													<c:set var="backButtonAction" value="showTab('#astClass')" />
												</c:when>
												<c:otherwise>
													<c:set var="backButtonAction" value="showTab('#astInfo')" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:set var="backButtonAction" value="backToHomePage()" />
										</c:otherwise>
									</c:choose>
									<c:set var="date"
										value="${command.astDetailsDTO.assetPurchaseInformationDTO.dateOfAcquisition}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date}"
										var="dateOfAcquisition" />
									<form:input class="mandColorClass form-control datepicker"
										id="dateOfAcquisition" data-label="#acqDate"
										disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										isMandatory="true"
										path="astDetailsDTO.assetPurchaseInformationDTO.dateOfAcquisition"
										value="${dateOfAcquisition}" maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							<c:if test="${!assetFlag}">
							<apptags:input labelCode="asset.licenseNo"
								cssClass="form-control" isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.licenseNo"
								isMandatory="false" maxlegnth="50"></apptags:input>
								</c:if>
						</div>

						<!-- License No And License Date -->
<c:choose>

							<c:when test="${assetFlag}">
<div class="form-group">
							<apptags:input labelCode="asset.licenseNo"
								cssClass="form-control" isDisabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.licenseNo"
								isMandatory="false" maxlegnth="50"></apptags:input>

							<label class="col-sm-2 control-label" id="licDate"
								for="datepicker"><spring:message
									code="asset.license.date" /></label>
							<div class="col-sm-4 ">
								<div class="input-group">
									<c:set var="date"
										value="${command.astDetailsDTO.assetPurchaseInformationDTO.licenseDate}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date}"
										var="licenseDate" />
									<form:input class="mandColorClass form-control datepicker"
										id="licenseDate" data-label="#licDate"
										disabled="${command.modeType eq 'V'}" isMandatory="false"
										path="astDetailsDTO.assetPurchaseInformationDTO.licenseDate"
										value="${licenseDate}" maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
							</c:when>
							<c:otherwise>
<div class="form-group">
							

							<label class="col-sm-2 control-label" id="licDate"
								for="datepicker"><spring:message
									code="asset.license.date" /></label>
							<div class="col-sm-4 ">
								<div class="input-group">
									<c:set var="date"
										value="${command.astDetailsDTO.assetPurchaseInformationDTO.licenseDate}" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${date}"
										var="licenseDate" />
									<form:input class="mandColorClass form-control datepicker"
										id="licenseDate" data-label="#licDate"
										disabled="${command.modeType eq 'V'}" isMandatory="false"
										path="astDetailsDTO.assetPurchaseInformationDTO.licenseDate"
										value="${licenseDate}" maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
							<label class="col-sm-2 control-label" for=""> <spring:message
									code="asset.purchaser.paymentmode" /></label>
							<c:set var="baseLookupCode" value="PAY" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								disabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.modeOfPayment"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								isMandatory="false" showAll="false"
								selectOptionLabelCode="Select" />
						</div>
							</c:otherwise>
						</c:choose>
						
						<c:choose>

							<c:when test="${assetFlag}">
								<div class="form-group">

									<apptags:input labelCode="asset.purchaser.initial.bookvalue"
										cssClass="decimal text-right form-control"
										isDisabled="${command.modeType eq 'V' || (command.modeType eq 'E')}"
										path="astDetailsDTO.assetPurchaseInformationDTO.initialBookValue"
										isMandatory="false" maxlegnth="18"></apptags:input>

									<label class="col-sm-2 control-label" id="iniBookDate"
										for="datepicker"><spring:message
											code="asset.initialbook.date" /></label>
									<div class="col-sm-4 ">
										<div class="input-group">
											<form:input class=" form-control datepicker"
												id="initialBookDate" data-label="#iniBookDate"
												disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
												isMandatory="false"
												path="astDetailsDTO.assetPurchaseInformationDTO.initialBookDate"
												maxlength="10"></form:input>
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>


								</div>
								
						<div class="form-group">
							<label class="col-sm-2 control-label" for=""> <spring:message
									code="asset.purchaser.paymentmode" /></label>
							<c:set var="baseLookupCode" value="PAY" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								disabled="${command.modeType eq 'V'}"
								path="astDetailsDTO.assetPurchaseInformationDTO.modeOfPayment"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								isMandatory="false" showAll="false"
								selectOptionLabelCode="Select" />

							<apptags:lookupFieldSet baseLookupCode="CAS" hasId="true"
								disabled="${command.modeType eq 'V'}"
								pathPrefix="astDetailsDTO.assetPurchaseInformationDTO.countryOfOrigin"
								isMandatory="false" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true" cssClass="form-control"
								showAll="false" />
						</div>
							</c:when>
							<c:otherwise>

						<div class="form-group">
							

							<apptags:lookupFieldSet baseLookupCode="CAS" hasId="true"
								disabled="${command.modeType eq 'V'}"
								pathPrefix="astDetailsDTO.assetPurchaseInformationDTO.countryOfOrigin"
								isMandatory="false" hasLookupAlphaNumericSort="true" 
								hasSubLookupAlphaNumericSort="true" cssClass="form-control"
								showAll="false" />
								<label class="col-sm-2 control-label " id="warrantydate"
								for="datepicker"><spring:message
									code="asset.purchase.warrantytilldate" /></label>
							<div class="col-sm-4 ">
								<div class="input-group">
									<form:input class=" form-control datepicker"
										id="warrantytilldate" data-label="#warrantydate"
										disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										isMandatory="false"
										path="astDetailsDTO.assetPurchaseInformationDTO.warrantyTillDate"
										maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
						
							</c:otherwise>
						</c:choose>


						
							<c:choose>

								<c:when test="${assetFlag}">
								<div class="form-group">
							<label class="col-sm-2 control-label " id="warrantydate"
								for="datepicker"><spring:message
									code="asset.purchase.warrantytilldate" /></label>
							<div class="col-sm-4 ">
								<div class="input-group">
									<form:input class=" form-control datepicker"
										id="warrantytilldate" data-label="#warrantydate"
										disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
										isMandatory="false"
										path="astDetailsDTO.assetPurchaseInformationDTO.warrantyTillDate"
										maxlength="10"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
									<label class="col-sm-2 control-label " id="creationDate"
										for="datepicker"><spring:message
											code="asset.creation.date" /></label>
									<div class="col-sm-4 ">
										<div class="input-group">
											<form:input class=" form-control datepicker"
												id="astCreationDate" data-label="#creationDate"
												disabled="${command.modeType eq 'V' || (command.modeType eq 'E' && command.checkTransaction eq 'Y')}"
												isMandatory="false"
												path="astDetailsDTO.assetPurchaseInformationDTO.astCreationDate"
												maxlength="10"></form:input>
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									
										</div>
									</div>
							<c:if test="${assetFlag}">
								<div class="form-group">
								
									<apptags:input labelCode="asset.development.proposal"
										path="astDetailsDTO.assetPurchaseInformationDTO.devProposal"
										isMandatory="false" maxlegnth="500"></apptags:input>
								</div>
							</c:if>



							</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>
							

						
						<div class="form-group">
							<c:if
								test="${ (command.modeType eq 'V' || command.modeType eq 'E') && assetFlag }">
								<apptags:input labelCode="asset.purchaser.latest.bookvalue"
									cssClass="decimal text-right form-control"
									isDisabled="${command.modeType eq 'V' || command.modeType eq 'E'}"
									path="astDetailsDTO.assetPurchaseInformationDTO.latestBookValue"
									isMandatory="false" maxlegnth="18"></apptags:input>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${!assetFlag}">
<div class="panel-body">
						<jsp:include page="/jsp/asset/assetInformation.jsp" />
					</div>
					</c:if>
		<c:if test="${command.approvalProcess ne 'Y' && assetFlag}">
			<div class="text-center">

				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">

						<c:choose>

							<c:when test="${assetFlag}">
								<c:set var="backButtonAction" value="showTab('#astClass')" />
							</c:when>
							<c:otherwise>
								<c:set var="backButtonAction" value="showTab('#astInfo')" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction" value="backToHomePage()" />
					</c:otherwise>
				</c:choose>
				<c:if
					test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save"
						onclick="saveAssetPurchaserInformation(this);" id="save">
						<spring:message code="asset.purchaser.save&continue" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning"
						onclick="resetPurchase()">
						<spring:message code="reset.msg" text="Reset" />
					</button>
				</c:if>
				<button type="button" class="btn btn-danger" name="button" id="Back"
					value="Back" onclick="${backButtonAction}">
					<spring:message code="asset.information.back" />
				</button>
			</div>
		</c:if>
	</form:form>
</div>
</div>
