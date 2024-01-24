<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/trade_license/dataSuiteEntry.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		/* maxDate : '0' */
		});
	});
</script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="tl.TradeLicenseDataEntrySuite"
							text=" License Master  Data Form"></spring:message></b>

				</h2>

				<apptags:helpDoc url="dataEntrySuites.html"></apptags:helpDoc>
			</div>


			<div class="widget-content padding">

				<form:form method="POST" action="dataEntrySuites.html"
					class="form-horizontal" id="dataEntrySuite" name="dataEntrySuite">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
						<form:hidden path="removedIds" id="removedIds" />
						<form:hidden path="removedOwnIds" id="removedOwnIds" />
						<form:hidden path="" id="saveMode" value="${command.saveMode}" />
						<form:hidden path="propertyActive" id="propertyActive" />
					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="trade.license.ownerDetails" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">

										<label id="ownershipType"
											class="col-sm-2 control-label required-control"
											for="ownershipType"><spring:message
												code="license.details.ownershipType" /></label>

										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="FPT" />
											<form:select path="tradeMasterDetailDTO.trdFtype"
												onchange="getOwnerTypeDetails('Y')"
												class="form-control mandColorClass" id="trdFtype"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="trade.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>

										</div>
										<div class="col-sm-4">
											<c:if test="${command.getGisValue() eq 'Y'}">
												
												<button class="btn btn-blue-2 backButton" type="button"
													onclick=" window.open('${command.tradeMasterDetailDTO.gISUri}&id=${command.tradeMasterDetailDTO.trdLicno}')"
													id="gisViewMap">
													<spring:message text="View  on GIS map" code="" />
												</button>
												<button type="button"
													class="btn  backButton btn-success btn-submit"
													onclick=" window.open('${command.tradeMasterDetailDTO.gISUri}&uniqueid=${command.tradeMasterDetailDTO.trdLicno}')"
													id="gisMap">
													<spring:message text="Map on GIS map" code="" />
												</button>
											</c:if>
										</div>


									</div>

									<div id="owner"></div>

								</div>
							</div>
						</div>

						<div class="panel panel-default">

							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="trade.licenseDetails" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">


									<div class="form-group">
										<label id="licenseType"
											class="col-sm-2 control-label required-control"
											for="licenseType"><spring:message
												code="license.details.licenseType" /></label>
										<c:set var="baseLookupCode" value="LIT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tradeMasterDetailDTO.trdLictype"
											cssClass="form-control" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

										<apptags:input labelCode="license.details.OldLicenseNo"
											cssClass="form-control preventSpace" isReadonly=""
											isMandatory="true" path="tradeMasterDetailDTO.trdOldlicno"
											maxlegnth="100"></apptags:input>

									</div>


									<!-- <div id="licensePeriod" style="display: none;"> -->
									<div class="form-group">


										<label class="col-sm-2 control-label required-control"
											for="licenseFromPeriod"><spring:message
												code="license.details.licenseFromPeriod"
												text="License From Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="trdLicfromDate"
													path="tradeMasterDetailDTO.trdLicfromDate" readonly=""></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="licenseToPeriod"><spring:message
												code="license.details.licenseToPeriod"
												text="License To Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="trdLictoDate" path="tradeMasterDetailDTO.trdLictoDate"
													readonly=""></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>
									<!-- </div> -->
									<c:choose>
										<c:when test="${command.propertyActive eq 'Y' }">
											<div class="form-group">

												<label class="col-sm-2 control-label"><spring:message
														code="trade.license.propertyId" text="Property No." /></label>
												<div class="col-sm-4">

													<div class="input-group">
														<form:input path="tradeMasterDetailDTO.pmPropNo"
															type="text" class="form-control " id="propertyNo" />

														<a href="#" class="input-group-addon"
															onclick="getPropertyDetails(this);"><i
															class="fa fa-search"></i></a>
													</div>
												</div>


												<apptags:input labelCode="license.details.propertyOwnerName"
													cssClass="hasCharacter" isReadonly="false"
													path="tradeMasterDetailDTO.trdOwnerNm"></apptags:input>


											</div>

											<div class="form-group">

												<apptags:input labelCode="license.details.usagetype"
													cssClass="hasCharacter" isReadonly="true"
													path="tradeMasterDetailDTO.usage"></apptags:input>


												<apptags:input labelCode="license.details.outstandingtype"
													cssClass="hasCharacter" isReadonly="true"
													path="tradeMasterDetailDTO.totalOutsatandingAmt"></apptags:input>

											</div>
										</c:when>
										<c:otherwise>
											<div class="form-group">
												<apptags:input labelCode="trade.license.propertyId"
													cssClass="" isReadonly="" isMandatory="false"
													path="tradeMasterDetailDTO.pmPropNo"></apptags:input>
												<apptags:input labelCode="license.details.propertyOwnerName"
													cssClass="hasCharacter" isReadonly="" isMandatory="false"
													path="tradeMasterDetailDTO.trdOwnerNm"></apptags:input>
											</div>
											<div class="form-group">
												<apptags:input labelCode="license.details.flatNo"
													cssClass="" isMandatory=""
													placeholder="Enter Flat/Shop Number"
													path="tradeMasterDetailDTO.trdFlatNo"></apptags:input>
											</div>

										</c:otherwise>
									</c:choose>
									<div class="form-group">
										<c:choose>

											<c:when test="${command.propertyActive eq 'Y' }">
												<apptags:input labelCode="license.details.businessName"
													cssClass="preventSpace" isMandatory="true"
													path="tradeMasterDetailDTO.trdBusnm" maxlegnth="50"></apptags:input>
											</c:when>
											<c:otherwise>
												<apptags:input labelCode="license.details.businessName"
													cssClass="preventSpace" isMandatory="true"
													path="tradeMasterDetailDTO.trdBusnm" maxlegnth="50"></apptags:input>
											</c:otherwise>
										</c:choose>


										<apptags:input labelCode="license.details.businessAddress"
											cssClass="form-control preventSpace" isReadonly=""
											isMandatory="true" path="tradeMasterDetailDTO.trdBusadd"
											maxlegnth="200"></apptags:input>


									</div>

									<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="MWZ" hasId="true"
											showOnlyLabel="false"
											pathPrefix="tradeMasterDetailDTO.trdWard" isMandatory="true"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control" showAll="false"
											columnWidth="20%" />
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label" for="gstNumber"><spring:message
												code="business.gst.no" text="Business GST No." /></label>
										<div class="col-sm-4">
											<form:input class="form-control mandColorClass preventSpace"
												id="app_gstNumber" maxlength="15"
												placeholder="Ex: 22AAAAA0000A1Z5"
												disabled="${command.viewMode eq 'V' ? true : false }"
												readonly="" path="tradeMasterDetailDTO.gstNo"></form:input>
										</div>



										<label class="col-sm-2 control-label required-control"
											for="BusinessStartDate"><spring:message
												code="license.details.businessStartDate"
												text="Business Start Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													class="form-control mandColorClass datepicker2 addColor"
													placeholder="DD/MM/YYYY" autocomplete="off"
													id="businessStartDate"
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.trdLicisdate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>



									</div>
									<div id="agreementDate">
										<div class="form-group">

											<label class="col-sm-2 control-label" for="licenseFromPeriod"><spring:message
													code="" text="Agreement Start Date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input
														class="form-control mandColorClass fromDateClass"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id="agreementFromDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.agreementFromDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

											<label class="col-sm-2 control-label" for="licenseToPeriod"><spring:message
													code="" text="Agreement End Date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass toDateClass"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id="agreementToDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.agreementToDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

										</div>
									</div>

								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.details" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="itemDetails">
										<thead>

											<tr>
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass=" form-control required-control" showAll="false"
													hasTableForm="true" showData="false" columnWidth="20%" />

												<th width="8%"><spring:message code="trade.item.value" /></th>
												<%-- <th width="8%"><spring:message code="trade.item.quantity" /></th>
													<th width="8%"><spring:message code="trade.item.unit" /></th> --%>

												<%-- <th width="10%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
												<c:if test="${command.saveMode ne 'V'}">
													<th width="7%"></th>
												</c:if>
											</tr>
										</thead>
										<%-- <tfoot>
												<tr>
													<th colspan="2" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1"><form:input path=""
															id="totalitemDetail" cssClass="form-control text-right"
															readonly="true" placeholder="00.00" /></th>
													<th colspan="1" class="text-right"></th>

												</tr>
											</tfoot> --%>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO) > 0}">
													<c:forEach var="taxData"
														items="${command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO}"
														varStatus="status">
														<tr class="itemDetailClass">
															<form:hidden
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																isMandatory="true" hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control tradeCat "
																showAll="false" hasTableForm="true" showData="true" />

															<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item value" readonly=""
																	id="trdUnit${d}" /></td>

															<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdQuantity"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Quantity" readonly="" id="trdQuantity${d}" /></td>
															
															<td>
															<form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Unit" readonly="" id="trdUnit${d}" />
															</td> --%>

															<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td> --%>
															
															<c:if test="${command.saveMode ne 'V'}">
																<td align="center"><a href="javascript:void(0);"
																class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
																class="fa fa-plus-circle"></i></a> <a
																href="javascript:void(0);" data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteTableRow('itemDetails', $(this), 'removedIds', 'isDataTable');"><i
																class="fa fa-minus"></i></a></td>
															</c:if>


														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="itemDetailClass">
														<form:hidden
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
															id="triId${d}" />
														<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
															showOnlyLabel="false"
															pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															cssClass="tradeCat form-control required-control"
															showAll="false" hasTableForm="true" showData="true" />

														<td><form:input
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control text-right unit required-control hasNumber"
																placeholder="Enter Item value" readonly=""
																id="trdUnit${d}" /></td>

														<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdQuantity"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Quantity" readonly="" id="trdQuantity${d}" /></td>
															
															<td>
															<form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Unit" readonly="" id="trdUnit${d}" />
															</td>			 --%>

														<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td> --%>
																
																
																	<td align="center"><a href="javascript:void(0);"
																		class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
																		class="fa fa-plus-circle"></i></a> <a
																		href="javascript:void(0);" data-placement="top"
																		class="btn btn-danger btn-sm delButton"
																		onclick="deleteTableRow('itemDetails', $(this), 'removedIds', 'isDataTable');"><i
																		class="fa fa-minus"></i></a></td>
																	

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<c:if
							test="${not empty command.tradeMasterDetailDTO.cancelReason}">

							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="cancellation.remarks" /></a>
								</h4>
								<div class="form-group">
									<br>
									<apptags:textArea isMandatory="true"
										labelCode="trade.cancel.reason"
										path="tradeMasterDetailDTO.cancelReason" maxlegnth="1000"
										cssClass="preventSpace"
										isReadonly="${command.viewMode eq 'V' ? true : false }"></apptags:textArea>

									<%-- <label class="col-sm-2 control-label required-control"
										for="CancelDate"><spring:message
											code="trade.cancel.date" text="Cancellation Date" /></label>

									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass datepicker2 addColor"
											placeholder="DD/MM/YYYY" autocomplete="off" id="cancelDate"
											path="tradeMasterDetailDTO.cancelDate" readonly="true"
											size="10"></form:input>
 --%>
								</div>
							</div>
					</div>

					</c:if>

					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a4"> <spring:message
									code="document.upload.det" text="Upload Documents" /></a>
						</h4>
						<div id="a4" class="panel-collapse collapse in">
							<form:hidden path="removeCommonFileById"
								id="removeCommonFileById" />

							<div class="panel-body">

								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteCommonDoc">
											<tr>
												<th width="" align="center"><spring:message
														code="ser.no" text="" /><input type="hidden" id="srNo"></th>
												<th scope="col" width="64%" align="center"><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="scheme.view.document" /></th>
												<c:if test="${command.saveMode ne 'V'}">
													<th scope="col" width="8%"><spring:message
															code="trade.action" text=""></spring:message></th>
												</c:if>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="dataEntrySuite.html?Download" /></td>
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center"><a href='#'
															id="deleteCommonFile" onclick="return false;"
															class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
															<form:hidden path="" value="${lookUp.attId}" /></td>
													</c:if>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>
								<div id="doCommonFileAttachment">
									<div class="table-responsive">
										<c:set var="cd" value="0" scope="page" />
										<c:if test="${command.saveMode ne 'V'}">
											<table class="table table-bordered table-striped"
												id="attachCommonDoc">
												<tr>
													<th><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th><spring:message code="work.estimate.upload"
															text="Upload Document" /></th>
													<!-- <th scope="col" width="8%"><a
														onclick='doCommonFileAttachment(this);'
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a></th> -->
													<th scope="col" width="8%"></th>		
												</tr>

												<tr class="appendableUploadClass">


													<td><form:input
															path="commonFileAttachment[${cd}].doc_DESC_ENGL"
															class=" form-control" /></td>
													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
															currentCount="${cd}" showFileNameHTMLId="true"
															fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
														</apptags:formField></td>
													
														
													<td align="center"><a href="#" 
														onclick='doCommonFileAttachment(this);'
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a> <a
														href="#" id="0_file_${cd}" data-placement="top"
														onclick="doFileDelete(this)"
														class="btn btn-danger btn-sm delButton"><i
															class="fa fa-trash"></i></a></td>
												
														
															
												</tr>
												<c:set var="cd" value="${cd+1}" scope="page" />
											</table>
										</c:if>
									</div>
								</div>

							</div>
						</div>
					</div>
			</div>

			<div class="padding-top-10 text-center">
				<c:if test="${command.saveMode ne 'V'}">
					<button type="button" class="btn btn-success" id=""
						onclick="saveDataEntrySuiteForm(this);">
						<spring:message code="trade.save" text="Save" />
					</button>
				</c:if>
				<c:if test="${command.saveMode eq 'C'}">
					<button class="btn btn-warning"
						onclick="openDataEntrySuiteForm('C');" type="button">
						<i class="button-input"></i>
						<spring:message code="trade.reset" text="Reset" />
					</button>
				</c:if>

				<button type="button" class="btn btn-danger backButton" id=""
					onclick="window.location.href = 'dataEntrySuites.html'">
					<spring:message code="trade.back"></spring:message>
				</button>
			</div>


			</form:form>
		</div>
	</div>
</div>
</div>

