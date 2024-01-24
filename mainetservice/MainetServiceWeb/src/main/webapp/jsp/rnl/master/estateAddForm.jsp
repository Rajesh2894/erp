<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/rnl/master/rl-file-upload.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/estateAddForm.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content animated ">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.estate.form.name" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="master.field.message" /> <i
					class="text-red-1">*</i> <spring:message
						code="master.field.mandatory.message" /></span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form method="post" action="EstateMaster.html"
				class="form-horizontal" name="estateForm" id="estateForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="estateMaster.hiddeValue" id="hiddeValue" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<!------------------------ Estate information Start ------------------- -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#masterInfo"><spring:message
										code="estate.information.master.info" /></a>
							</h4>
						</div>
						<div id="masterInfo" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label for="code" class="control-label col-sm-2"><spring:message
											code='estate.label.code' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.code" id="code" disabled="true"></form:input>
									</div>


									<label for="assetNo" class="control-label col-sm-2"><spring:message
											code='estate.label.asset' /></label>
									<%-- <div class="col-sm-4" class="form-control alfaNumricSpecial">
						<form:input type="text" class="form-control hasAsset"
							path="estateMaster.assetNo" maxlength="30" id="assetNo"></form:input>
					</div> --%>

									<%-- <div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getPropertyMasterDetails(this)" path="estatePropMaster.assesmentPropId"
												id="propertyNo" data-rule-required="true"></form:input>
										</div> --%>
									<c:choose>
										<c:when test="${empty command.assetCodeList}">
											<div class="col-sm-4">
												<div class="input-group">
													<form:input type="text"
														class="form-control alfaNumricSpecial" maxlength="30"
														path="estateMaster.assetNo" id="assetNo" />
													<span class="input-group-addon"
														onclick="getAssetInfoDetails(this)"><i
														class="fa fa-search"></i></span>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-sm-4">
												<form:select path="estateMaster.assetNo" disabled="${command.modeType eq 'V'}"
													class="form-control chosen-select-no-results" id="assetNo"
													onchange="getAssetInfoDetails(this)">
													<form:option value="">
														<spring:message code='council.management.select' />
													</form:option>
													<c:forEach items="${command.assetCodeList}" var="assetCode">
														<form:option value="${assetCode}">${assetCode}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</c:otherwise>
									</c:choose>

								</div>
								<div class="form-group">
									<label for="nameEng"
										class="control-label col-sm-2 required-control"><spring:message
											code='estate.label.name' /> <spring:message
											code="rnl.master.eng" text="(Eng)"></spring:message></label>
									<div class="col-sm-10">
										<form:input type="text" class="form-control alfaNumricSpecial"
											path="estateMaster.nameEng" maxlength="1000" id="nameEng"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label for="nameReg"
										class="control-label col-sm-2 required-control"><spring:message
											code='estate.label.name' /> <spring:message
											code="rnl.master.reg" text="(Reg)"></spring:message></label>
									<div class="col-sm-10">
										<form:input type="text" class="form-control "
											path="estateMaster.nameReg" maxlength="1000" id="nameReg"></form:input>
									</div>
								</div>

								<div class="form-group">
									<%-- <label class="control-label col-sm-2 required-control" ><spring:message code="rnl.master.nature.land" text="Nature Of Land"></spring:message></label>
	                 <apptags:lookupField items="${command.getLevelData('NOL')}" path="estateMaster.natureOfLand" cssClass="form-control" 
	                      selectOptionLabelCode="Select" hasId="true" isMandatory="true"/> --%>
									<label for="category"
										class="control-label col-sm-2 required-control"><spring:message
											code='estate.label.Category' /></label>
									<div class="col-sm-4">
										<form:select id="category" class="form-control"
											path="estateMaster.category">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<form:options items="${command.categoryType}" />
										</form:select>
									</div>

								</div>

								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="ETY" hasId="true"
										pathPrefix="estateMaster.type" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control" />
								</div>
								<c:if test="${command.applicableENV eq 'TSCL' }">
									<!--T#139714  -->
									<div class="form-group">
										<label class="col-sm-2 control-label required-control ">
										 <spring:message code="" text="Purpose"/>
										</label>
										<apptags:lookupField items="${command.getLevelData('EPR')}"
											path="estateMaster.purpose" disabled="${command.modeType eq 'V'}"
											cssClass="form-control chosen-select-no-results"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="Select" isMandatory="true" />
											
										<label class="col-sm-2 control-label required-control ">
										 <spring:message code="" text="Acquisition Type"/>
										</label>
										<apptags:lookupField items="${command.getLevelData('AQM')}"
											path="estateMaster.acqType" disabled="${command.modeType eq 'V'}"
											cssClass="form-control chosen-select-no-results"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="Select" isMandatory="true" />
										
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label required-control ">
										 <spring:message code="" text="Type Of Holding"/>
										</label>	
										<apptags:lookupField items="${command.getLevelData('EHT')}"
											path="estateMaster.holdingType" disabled="${command.modeType eq 'V'}"
											cssClass="form-control chosen-select-no-results"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="Select" isMandatory="true" />
										
									</div>
								</c:if>
								
								
								<div class="form-group">
									<label for="regNo" class="control-label col-sm-2"><spring:message
											code='estate.label.regno' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.regNo" id="regNo"></form:input>
									</div>
									<label for="regDate" class="control-label col-sm-2"><spring:message
											code='estate.label.regDate' /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="estateMaster.regDate" type="text"
												class="form-control" id="regDate" readOnly="true" />
											<label class="input-group-addon" for=regDate><i
												class="fa fa-calendar"></i></label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="constDate" class="control-label col-sm-2"><spring:message
											code='estate.label.conDate' /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text" class="form-control"
												path="estateMaster.constDate" id="constDate" readOnly="true" />
											<label class="input-group-addon" for="constDate"><i
												class="fa fa-calendar"></i></label>
										</div>
									</div>
									<label for="compDate" class="control-label col-sm-2"><spring:message
											code='estate.label.compDate' /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text" class="form-control"
												path="estateMaster.compDate" id="compDate" readOnly="true" />
											<label class="input-group-addon" for="compDate"><i
												class="fa fa-calendar"></i></label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="floors" class="control-label col-sm-2"><spring:message
											code='estate.label.noFloors' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											path="estateMaster.floors" maxlength="3" id="floors" />
									</div>
									<label for="basements" class="control-label col-sm-2"><spring:message
											code='estate.label.noBase' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											path="estateMaster.basements" maxlength="3" id="basements" />
									</div>
								</div>
							</div>
						</div>
					</div>

					<!------------------------ Estate information End --------------------->

					<!------------------------ Estate Location Start --------------------->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#a1"><spring:message
										code="estate.information.master.loc" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"><spring:message
											code='estate.label.address' /></label>
									<div class="col-sm-10">
										<form:textarea path="estateMaster.address" id="address"
											class="form-control alfaNumricSpecial" maxlength="1000" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="rnl.estate.master.latitude" text="Latitude" /></label>
									<div class="col-sm-4">
										<form:input path="estateMaster.latitude"
											cssClass="form-control " placeholder="Latitude"
											id="estateLatitude" />
									</div>

									<label class="col-sm-2 control-label"> <spring:message
											code="rnl.estate.master.longitude" text="Longitude" />
									</label>
									<div class="col-sm-4">
										<form:input path="estateMaster.longitude"
											class="form-control " maxlength="" placeholder="Longitude"
											id="estateLongitude" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2 required-control"><spring:message
											code="rnl.estate.master.Khasra.no" text="Khasra No."></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.surveyNo" id="surveyNo"></form:input>
									</div>
									<label for="locId"
										class="control-label col-sm-2 required-control"><spring:message
											code='estate.label.Location' /></label>
									<div class="col-sm-4">
										<form:select id="locId" class="form-control required-control"
											path="estateMaster.locId">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.locationList}" var="objArray">
												<form:option value="${objArray[0]}">
													<c:choose>
														<c:when test="${userSession.languageId eq 2}">${objArray[2]}</c:when>
														<c:otherwise>${objArray[1]}</c:otherwise>
													</c:choose>
												</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

								<div class="form-group">
									<label for="floors" class="control-label col-sm-2"><spring:message
											code='estate.label.east' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.east" maxlength="180" id="east" />
									</div>
									<label for="basements" class="control-label col-sm-2"><spring:message
											code='estate.label.west' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.west" maxlength="180" id="west" />
									</div>
								</div>
								<div class="form-group">
									<label for="floors" class="control-label col-sm-2"><spring:message
											code='estate.label.north' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.north" maxlength="180" id="north" />
									</div>
									<label for="basements" class="control-label col-sm-2"><spring:message
											code='estate.label.south' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estateMaster.south" maxlength="180" id="south" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<!------------------------ Estate Location End --------------------->


					<!------------------------ Estate Documents Start --------------------->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#a2"><spring:message
										code="estate.information.master.doc" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive margin-bottom-10">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<th><spring:message code='estate.table.column.srno' /></th>
												<th><spring:message code='estate.table.column.doc' /></th>
												<th><spring:message code='estate.table.column.header' /></th>
											</tr>
											<tr>
												<td>1</td>
												<td><spring:message code='estate.table.upload.image' /></td>
												<td><c:if test="${command.modeType eq 'V'}">
														<c:forEach items="${command.documentList}" var="lookUp"
															varStatus="lk">
															<c:if test="${lookUp.serialNo eq 0}">
																<apptags:filedownload filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="EstateMaster.html?Download" />
															</c:if>
														</c:forEach>
													</c:if> <c:if test="${command.modeType ne 'V'}">
														<apptags:formField fieldType="7"
															fieldPath="estateMaster.imagesPath" labelCode=""
															currentCount="0" showFileNameHTMLId="true"
															fileSize="COMMOM_MAX_SIZE"
															maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT" folderName="0"
															validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
													</c:if></td>
											</tr>
											<tr>
												<td>2</td>
												<td><spring:message code='estate.table.upload.terms' /></td>
												<td><c:if test="${command.modeType eq 'V'}">
														<c:forEach items="${command.documentList}" var="lookUp"
															varStatus="lk">
															<c:if test="${lookUp.serialNo eq 1}">
																<apptags:filedownload filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="EstateMaster.html?Download" />
															</c:if>
														</c:forEach>
													</c:if> <c:if test="${command.modeType ne 'V'}">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="estateMaster.docsPath" isMandatory="false"
															showFileNameHTMLId="true" fileSize="COMMOM_MAX_SIZE"
															maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
															folderName="1" currentCount="1" />
													</c:if></td>

											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>


					</div>

				</div>
				<div class="text-center padding-top-10">
					<c:if test="${command.modeType ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							id="submitEstate">
							<spring:message code="bt.save" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C'}">
						<button type="Reset" class="btn btn-warning" id="resetEstate">
							<spring:message code="bt.clear" />
						</button>
					</c:if>
					<input type="button" id="backBtn" class="btn btn-danger"
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				</div>
			</form:form>
		</div>
	</div>
</div>