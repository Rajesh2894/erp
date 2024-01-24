<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetServiceInformation.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<!--This page assetServiceInformation.jsp is used by two modules 
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
         <c:if test="${assetFlag}">               
     <div class="widget-content padding serviceInfoPage">
     </c:if>

	<form:form action="AssetRegistration.html" id="assetService"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		 <c:if test="${userSession.moduleDeptCode == 'AST'}">  
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivS"></div>
			</c:if>
			<c:if test = "${userSession.moduleDeptCode == 'AST' }">
		<form:hidden path="modeType" id="modeType" />
		</c:if>
		<form:hidden path="accountIsActiveOrNot" id="accountIsActiveOrNot" />
		<form:hidden path="bindingStatus" id="bindingStatus" />
		
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
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant1"><spring:message
								code="asset.service.serviceinfo" /></a>
					</h4>
				</div>

				<c:if
					test="${!assetFlag && (command.modeType eq 'C' || command.modeType eq 'D' || (fn:length(command.astDetailsDTO.astSerList)==0 && command.modeType eq 'E' ) || command.modeType eq 'E')}">
					<div id="Applicant1" class="panel-collapse collapse in">
						<div class="panel-body">


							<!-----  this is for check box condition  only for create   ---->
							<c:if
								test="${command.modeType eq 'C' || command.modeType eq 'D' || command.modeType eq 'E'  }">
								<div class="form-group">
									<label class="col-sm-2 control-label" id="serviceAppLbl" for="">
										<spring:message code="asset.service.applicable" />
									</label>
									<div class="col-sm-4">
										<form:checkbox path="astDetailsDTO.isServiceAplicable"
											checked="${astDetailsDTO.isServiceAplicable}"
											id="isServiceInfoApplicable" onclick="showAndHides()"
											class="margin-top-10 margin-left-10" disabled="false"></form:checkbox>

									</div>
								</div>
								<div id="hideAndShowServiceInfo">
							</c:if>


							<!------- ----------------- end  ------------------------------->


	<form:hidden id = "editItAssetIndex"  path="index" />
								


							<c:set var="index" value="${(fn:length(command.astDetailsDTO.astSerList))  }" scope="page" />
							<form:hidden path="astDetailsDTO.astSerList[${index}].editFlag"
								value="E" />

							
									<div class="form-group">
										 <form:hidden
											path="astDetailsDTO.astSerList[${index}].assetServiceId"
											id="assetServiceId"  /> 
										<apptags:input labelCode="asset.service.serviceprov"
											isDisabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.astSerList[${index}].serviceProvider"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<label class="col-sm-2 control-label " id = "stDate" for=""><spring:message
												code="asset.service.startdate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													disabled="${command.modeType eq 'V'}" id="serviceStartDate"
													path="astDetailsDTO.astSerList[${index}].serviceStartDate"
													isMandatory="${command.modeType eq 'E' }" maxlength="10"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
									<div class="form-group">
										<apptags:input labelCode="asset.service.warranty"
											isDisabled="${command.modeType eq 'V'}"
											cssClass="form-control hasNumber"
											path="astDetailsDTO.astSerList[${index}].warrenty"
											isMandatory="false" maxlegnth="18"></apptags:input>
										<label class="col-sm-2 control-label"  id ="endDate" for=""><spring:message
												code="asset.service.expiredate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													disabled="${command.modeType eq 'V'}"
													id="serviceExpiryDate"
													path="astDetailsDTO.astSerList[${index}].serviceExpiryDate"
													isMandatory="${command.modeType eq 'E'}"  maxlength="10" ></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
											
										</div>

									</div>

							
							<div class="form-group">
								<apptags:textArea labelCode="assetservice.description"
									isDisabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astSerList[${index}].serviceDescription"
									isMandatory="false" maxlegnth="100"></apptags:textArea>
							</div>
							<c:set var="index" value="${index + 1}" scope="page" />
								
						</div>
					</div>
				</c:if>
				<input type="hidden" id="astSerListSize" value="${fn:length(command.astDetailsDTO.astSerList) }" />
				<c:choose>
					<c:when
						test="${command.modeType ne 'C' && command.modeType ne 'D' && fn:length(command.astDetailsDTO.astSerList)>0}">
						<%-- 				<c:if test="${command.modeType ne 'C' && fn:length(command.astDetailsDTO.astSerList)>0}"> --%>
						<div id="Applicant1" class="panel-collapse collapse in">
							<div class="panel-body">



								<!-- button----------------------- -->

								<table class="table table-striped table-bordered "
									id="serviceDataTable">
									<thead>
										<tr>
										<c:if test="${assetFlag}">
											<th align="left"><spring:message
													code="asset.service.serviceno" /></th>
													</c:if>
											<th align="center"><spring:message
													code="asset.service.serviceprov" /></th>
											<th align="right"><spring:message code="" text="Actions" />
											
												 <c:if test="${assetFlag && command.modeType ne 'V'}">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onclick="createData(${fn:length(command.astDetailsDTO.astSerList)},this,'${command.modeType}');"
														title="Add Data" id="addbut" value="false">
														<i class="fa fa-plus-circle"></i>
													</button>
												</c:if> 
												</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${command.astDetailsDTO.astSerList}"
											var="astList" varStatus="count">
										<c:if test = "${( !assetFlag && (astList.serviceProvider ne '' && astList.serviceProvider ne null) || assetFlag)}">
											<tr>
												<c:if test="${assetFlag}">
												<td>${astList.serviceNo}</td>
												</c:if>
												<td>${astList.serviceProvider}</td>
												<td class="text-center"><c:if
														test="${command.modeType ne 'E'}">
														
														<button type="button" class="btn btn-blue-2 btn-sm "
															onClick="createData(${count.index},this,'${command.modeType}');"
															title="View Asset">
															<i class="fa fa-eye"></i>
														</button>
													</c:if> <c:if test="${command.modeType ne 'V'}">
													
														<button type="button" class="btn btn-success btn-sm "
															onclick="createData(${count.index},this,'${command.modeType}');"
															title="Add Data">
															<i class="fa fa-pencil"></i>
														</button>
													</c:if></td>
											</tr>
											</c:if>
											
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>
						<%-- 				</c:if> --%>
					</c:when>
					<c:when
						test="${command.modeType eq 'V' && fn:length(command.astDetailsDTO.astSerList)==0}">

						<div id="Applicant1" class="panel-collapse collapse in">
							<div class="panel-body">
								<!-- button----------------------- -->
								<table class="table table-striped table-bordered "
									id="serviceDataTable">
									<thead>
										<tr>
											<th align="left"><spring:message
													code="asset.service.serviceno" /></th>
											<th align="center"><spring:message
													code="asset.service.serviceprov" /></th>
											<th align="right"><spring:message code="" text="Actions" />
											</th>
										</tr>
									</thead>
									<tbody>

										<tr>
											<td>No Results Found</td>
											<td>No Results Found</td>
											<td>No Results Found</td>
										</tr>


									</tbody>
								</table>
							</div>
						</div>

					</c:when>
				</c:choose>
				<!--button------------------------------->
				<!--------------------------------------------- this is service information start-------------------------------------------------->
				<c:if
					test="${assetFlag && (command.modeType eq 'C' || command.modeType eq 'D' || (fn:length(command.astDetailsDTO.astSerList)==0 && command.modeType eq 'E' ))}">
					<div id="Applicant" class="panel-collapse collapse in">
						<div class="panel-body">


							<!-----  this is for check box condition  only for create   ---->
							<c:if
								test="${command.modeType eq 'C' || command.modeType eq 'D'}">
								<div class="form-group">
									<label class="col-sm-2 control-label" id="serviceAppLbl" for="">
										<spring:message code="asset.service.applicable" />
									</label>
									<div class="col-sm-4">
										<form:checkbox path="astDetailsDTO.isServiceAplicable"
											checked="${astDetailsDTO.isServiceAplicable}"
											id="isServiceInfoApplicable" onclick="showAndHides()"
											class="margin-top-10 margin-left-10" disabled="false"></form:checkbox>

									</div>
								</div>
								<div id="hideAndShowServiceInfo">
							</c:if>


							<!------- ----------------- end  ------------------------------->





							<c:set var="index" value="0" scope="page" />
							<form:hidden path="astDetailsDTO.astSerList[${index}].editFlag"
								value="E" />

							<c:choose>

								<c:when test="${assetFlag}">
									<div class="form-group">
										<apptags:input labelCode="asset.service.serviceno"
											cssClass="form-control alphaNumeric"
											isDisabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.astSerList[${index}].serviceNo"
											isMandatory="true" maxlegnth="20"></apptags:input>
										<apptags:input labelCode="asset.service.serviceprov"
											isDisabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.astSerList[${index}].serviceProvider"
											isMandatory="true" maxlegnth="100"></apptags:input>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for=""><spring:message
												code="asset.service.startdate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													disabled="${command.modeType eq 'V'}" id="serviceStartDate"
													path="astDetailsDTO.astSerList[${index}].serviceStartDate"
													isMandatory="false" maxlength="10"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
										<label class="col-sm-2 control-label" for=""><spring:message
												code="asset.service.expiredate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													disabled="${command.modeType eq 'V'}"
													id="serviceExpiryDate"
													path="astDetailsDTO.astSerList[${index}].serviceExpiryDate"
													isMandatory="false" maxlength="10"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>
									<div class="form-group">
										<apptags:input labelCode="asset.service.amount"
											isDisabled="${command.modeType eq 'V'}"
											cssClass="decimal text-right form-control"
											path="astDetailsDTO.astSerList[${index}].amount"
											isMandatory="false" maxlegnth="18"></apptags:input>
										<apptags:input labelCode="asset.service.warranty"
											isDisabled="${command.modeType eq 'V'}"
											cssClass="form-control hasNumber"
											path="astDetailsDTO.astSerList[${index}].warrenty"
											isMandatory="false" maxlegnth="18"></apptags:input>
									</div>
									<div class="form-group">
										<c:if test="${command.accountIsActiveOrNot eq true}">
											<label class="col-sm-2 control-label " for="costcenter">
												<spring:message code="asset.service.accountHead" />
											</label>
											<div class="col-sm-4">
												<form:select
													path="astDetailsDTO.astSerList[${index}].costCenter"
													disabled="${command.modeType eq 'V'}"
													cssClass="form-control chosen-select-no-results"
													id="ServiceCostCenterId">
													<form:option value="">
														<spring:message code='asset.info.select' text="Select" />
													</form:option>
													<c:if
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<c:forEach items="${command.acHeadCode}"
															var="costCenterList">
															<form:option value="${costCenterList.lookUpDesc }"
																code="${costCenterList.lookUpDesc}">${costCenterList.lookUpDesc}</form:option>
														</c:forEach>
													</c:if>
													<c:if
														test="${userSession.getCurrent().getLanguageId() ne 1}">
														<c:forEach items="${command.acHeadCode}"
															var="costCenterList">
															<form:option value="${costCenterList.descLangSecond }"
																code="${costCenterList.descLangSecond}">${costCenterList.descLangSecond}</form:option>
														</c:forEach>
													</c:if>
												</form:select>
											</div>
										</c:if>
										<c:if test="${command.accountIsActiveOrNot ne true}">
											<apptags:input labelCode="asset.service.accountHead"
												path="astDetailsDTO.astSerList[${index}].costCenter"
												isDisabled="${command.modeType eq 'V'}" isMandatory="false"
												cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
										</c:if>
										<apptags:textArea labelCode="asset.service.content"
											isDisabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.astSerList[${index}].serviceContent"
											isMandatory="false" maxlegnth="100"></apptags:textArea>
									</div>
								</c:when>
								<c:otherwise>
									<div class="form-group">
										<%-- <form:hidden
											path="astDetailsDTO.astSerList[${index}].serviceNo"
											id="serviceNo" value="123" /> --%>
										<apptags:input labelCode="asset.service.serviceprov"
											isDisabled="${command.modeType eq 'V'}"
											path="astDetailsDTO.astSerList[${index}].serviceProvider"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<label class="col-sm-2 control-label" for=""><spring:message
												code="asset.service.startdate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													disabled="${command.modeType eq 'V'}" id="serviceStartDate"
													path="astDetailsDTO.astSerList[${index}].serviceStartDate"
													isMandatory="false" maxlength="10"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
									<div class="form-group">
										<apptags:input labelCode="asset.service.warranty"
											isDisabled="${command.modeType eq 'V'}"
											cssClass="form-control hasNumber"
											path="astDetailsDTO.astSerList[${index}].warrenty"
											isMandatory="false" maxlegnth="18"></apptags:input>
										<label class="col-sm-2 control-label" for=""><spring:message
												code="asset.service.expiredate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													disabled="${command.modeType eq 'V'}"
													id="serviceExpiryDate"
													path="astDetailsDTO.astSerList[${index}].serviceExpiryDate"
													isMandatory="false" maxlength="10"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>

								</c:otherwise>
							</c:choose>
							<div class="form-group">
								<apptags:textArea labelCode="assetservice.description"
									isDisabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astSerList[${index}].serviceDescription"
									isMandatory="false" maxlegnth="100"></apptags:textArea>
							</div>
							<c:set var="index" value="${index + 1}" scope="page" />
						</div>
					</div>
				</c:if>

				<!--------------------------------------------- this is service information end---------------------------------------------- -->
			</div>
		</div>
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1" style="display: none">
			<div class="panel-heading">
				<h4 class="panel-title table" id="">
					<a data-toggle="collapse" class=""
						data-parent="#accordion_single_collapse1" href="#a1"><spring:message
							code="asset.service.realstate" /></a>
				</h4>
			</div>
			<div id="a1" class="panel-collapse collapse in">
				<div class="panel-body">
					<div class="form-group" id="serviceno">
						<apptags:input labelCode="asset.service.assesmentno"
							cssClass="form-control hasNumber"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.assessmentNo"
							isMandatory="false" maxlegnth="20"></apptags:input>
						<apptags:input labelCode="asset.service.regno"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.propertyRegistrationNo"
							isMandatory="false" maxlegnth="20"></apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="asser.service.taxcode"
							cssClass="form-control hasNumber"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.taxCode"
							isMandatory="false" maxlegnth="20"></apptags:input>
						<apptags:input labelCode="asser.service.amount"
							cssClass="decimal text-right form-control"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.realEstAmount"
							isMandatory="false" maxlegnth="18"></apptags:input>
					</div>
					<div class="form-group" id="taxcode">
						<label class="col-sm-2 control-label" for=""> <spring:message
								code="asser.service.taxzone" /></label>
						<div class="col-sm-4">
							<form:select
								path="astDetailsDTO.assetRealEstateInfoDTO.taxZoneLocation"
								disabled="${command.modeType eq 'V'}" cssClass="form-control"
								id="taxZoneId">
								<form:option value="0">
									<spring:message code='' text="Select" />
								</form:option>
								<c:forEach items="${command.location}" var="lookUp">
									<form:option value="${lookUp.lookUpId}" code="">${lookUp.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<%-- <c:set var="baseLookupCode" value="ATN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							disabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.taxZoneLocation"
							cssClass="form-control" hasChildLookup="false" hasId="true"
							showAll="false" selectOptionLabelCode="Select"
							isMandatory="false" /> --%>
						<apptags:input labelCode="asser.service.municipality"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.assetRealEstateInfoDTO.municipalityName"
							isMandatory="false" maxlegnth="100"></apptags:input>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${!assetFlag}">
                    <div class="panel-body">
						<jsp:include page="/jsp/asset/assetDocumentDetails.jsp" />
					</div>
					</c:if>
		<c:if test="${command.approvalProcess ne 'Y' && assetFlag }">
			<div class="text-center">

				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
						<c:set var="backButtonAction"
							value="showTab('${userSession.moduleDeptCode == 'AST' ? '#astRealEstate':'#astPurch'}')" />
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction" value="backToHomePage()" />
					</c:otherwise>
				</c:choose>
				<c:if
					test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save"
						onclick="saveAssetServiceInformation(this);" id="save">
						<spring:message code="asset.service.save&continue" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning"
						onclick="resetService()">
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
 <c:if test="${assetFlag}">               
     </div>
     </c:if>
