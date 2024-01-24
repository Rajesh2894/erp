<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetLinear.js"></script>
<div class="widget-content padding">
	<form:form action="AssetRegistration.html" id="assetLinear"
		method="post" class="form-horizontal">
		<form:hidden path="modeType" id="modeType" />
		<input type="hidden" id="moduleDeptUrl"
				value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivLine"></div>
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="Applicant"
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.lenear.lenearasset" /></a>
					</h4>
				</div>

				<div class="panel-body">
					<div class="form-group">
						<apptags:input labelCode="asset.lenear.startpoint"
							isDisabled="${command.modeType eq 'V'}"
							cssClass="decimal text-right form-control"
							path="astDetailsDTO.astLinearDTO.startPoint" isMandatory="true" maxlegnth="18"></apptags:input>
						<apptags:input labelCode="asset.lenear.endpoint"
							isDisabled="${command.modeType eq 'V'}"
							cssClass="decimal text-right form-control"
							path="astDetailsDTO.astLinearDTO.endPoint" isMandatory="true"  maxlegnth="18"></apptags:input>
					</div>

					<div class="form-group">
						<label for="text-1514452818192"
							class="col-sm-2 control-label required-control"><spring:message
								code="asset.lenear.length" /></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12 ">
								<form:input type="text" id="linearLength"
									cssClass="decimal text-right form-control"
									disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLinearDTO.length" placeholder="99.9" maxlength="18"></form:input>
								<div class="input-group-field">
									<c:set var="baseLookupCode" value="UOL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="astDetailsDTO.astLinearDTO.lengthUnit"
										disabled="${command.modeType eq 'V'}" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select" isMandatory="false" />
								</div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for=""> <spring:message
								code="asset.lenear.typeoffset1" /></label>
						<c:set var="baseLookupCode" value="TOO" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="astDetailsDTO.astLinearDTO.typeOffset1"
							disabled="${command.modeType eq 'V'}" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" />

						<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
								code="asset.lenear.offset1" /></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12 ">
								<form:input type="text"
									cssClass="decimal text-right form-control"
									disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLinearDTO.offset1" placeholder="99.9" maxlength="18"></form:input>
								<div class="input-group-field">
									<c:set var="baseLookupCode" value="UOL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="astDetailsDTO.astLinearDTO.offset1Value"
										disabled="${command.modeType eq 'V'}" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select" isMandatory="false" />
								</div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for=""> <spring:message
								code="asset.lenear.typeoffset2" /></label>
						<c:set var="baseLookupCode" value="TOO" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="astDetailsDTO.astLinearDTO.typeOffset2"
							disabled="${command.modeType eq 'V'}" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" />

						<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
								code="asset.lenear.offset2" /></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12 ">
								<form:input type="text"
									cssClass="decimal text-right form-control"
									disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLinearDTO.offset2" placeholder="99.9" maxlength="18"></form:input>
								<div class="input-group-field">
									<c:set var="baseLookupCode" value="UOL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="astDetailsDTO.astLinearDTO.offset2Value"
										disabled="${command.modeType eq 'V'}" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select" isMandatory="false" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<div class="panel panel-default">


						<div class="panel-body">


							<!-- Additional owner Detail section -->
							<h4>
								<spring:message code="asset.lenear.documentdetails" />
							</h4>
							<c:set var="d" value="0" scope="page" />
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="customFields">
									<tbody>
										<tr>

											<th><spring:message code="asset.lenear.srno" /></th>
											<th><spring:message code="asset.lenear.markercode" /></th>
											<th><spring:message code="asset.lenear.desc" /></th>
											<th><spring:message code="asset.lenear.markertype" /></th>
											<th><spring:message code="asset.lenear.startpoint" /></th>
											<th><spring:message code="asset.lenear.endpoint" /></th>
											<th><spring:message code="asset.lenear.uom" /></th>
											<%-- <c:if
												test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
												<th><spring:message code="asset.lenear.action" /></th>
											</c:if> --%>
										</tr>
										<tr class="appendableClass">

											<td><form:input type="text" id="OISrNo" value="1"
													disabled="disabled" path="" cssClass="form-control" /></td>
											<td><form:input
													path="astDetailsDTO.astLinearDTO.markCode"
													disabled="${command.modeType eq 'V'}" class="form-control alphaNumeric"
													id="linearMarkCode" maxlength="100" /></td>
											<td><form:input
													path="astDetailsDTO.astLinearDTO.markDesc"
													disabled="${command.modeType eq 'V'}" class="form-control alphaNumeric"
													id="linearMarkDesc" maxlength="100" /></td>

											<td><c:set var="baseLookupCode" value="MKT" /> <form:select
													path="astDetailsDTO.astLinearDTO.markType"
													disabled="${command.modeType eq 'V'}"
													cssClass="form-control" id="markType">
													<form:option value="">
														<spring:message code='' text="Select" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:input
													path="astDetailsDTO.astLinearDTO.gridStartPoint"
													disabled="${command.modeType eq 'V'}"
													cssClass="decimal text-right form-control"
													class="form-control" id="linearTabStartPoiint"
													maxlength="100" /></td>
											<td><form:input
													path="astDetailsDTO.astLinearDTO.gridEndPoint"
													disabled="${command.modeType eq 'V'}"
													cssClass="decimal text-right form-control"
													class="form-control" id="linearTabEndPoiint"
													maxlength="100" /></td>

											<td><c:set var="baseLookupCode" value="UOL" /> <form:select
													path="astDetailsDTO.astLinearDTO.uom"
													disabled="${command.modeType eq 'V'}"
													cssClass="form-control" id="uomId">
													<form:option value="">
														<spring:message code='' text="Select" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>
											<%--D#80699 <c:if
												test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
												<td><a href="javascript:void(0);" data-toggle=""
													data-placement="top" class="addCF btn btn-success btn-sm"
													data-original-title="Add" id="addCF"><i
														class="fa fa-plus-circle"></i></a> <a
													href="javascript:void(0);" data-toggle=""
													data-placement="top" class="remCF btn btn-danger btn-sm"
													data-original-title="Delete"><i class="fa fa-trash"></i></a></td>
											</c:if> --%>

										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${command.approvalProcess ne 'Y' }">
		<div class="text-center">
			<c:choose>
				<c:when test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
					<c:set var="backButtonAction" value="showTab('#astCod')" />
				</c:when>
				<c:otherwise>
					<c:set var="backButtonAction" value="backToHomePage()" />
				</c:otherwise>
			</c:choose>
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
				<button type="button" class="button-input btn btn-success"
					name="button" value="Save" onclick="saveAstLinear(this);" id="save">
					<spring:message code="asset.lenear.save&continue" />
				</button>
			</c:if>
			
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
				<button type="Reset" class="btn btn-warning"
					onclick="resetLine()">
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