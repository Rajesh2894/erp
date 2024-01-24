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
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/care/landInspection.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="ln.title" text="Land Inspection" />
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
			<form:form method="post" action="LandInspection.html"
				class="form-horizontal" name="propForm" id="propForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<form:hidden path="modeType" id="modeType" />


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#complain"><spring:message
										code="ln.cmpln.det" text="Complain Details" /> </a>
							</h4>
						</div>
						<div id="complain" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2" for=complaintId><spring:message
											code='ln.cmpln.no' text="Complaint No" /></label>

									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text" class="form-control alfaNumric"
												maxlength="25" path="inspectionDto.complaintNo"
												id="complaintNo" disabled="${command.modeType eq 'V'}" />
											<span class="input-group-addon"
												onclick="getComplaintDetails(this)"><i
												class="fa fa-search"></i></span>
										</div>
									</div>

								</div>

								<div class="form-group">
									<label class="control-label col-sm-2" for="name"><spring:message
											code='ln.cmpln.det' text="Complaint Details" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											disabled="${command.modeType eq 'V'}"
											path="inspectionDto.complaintDet" id="name" maxlength="199" />
									</div>
									<!--Complaint Document code set -->
									<c:set var="count" value="${count + 1}" scope="page" />
									<label for="" class="col-sm-2 control-label"> <spring:message
											code="ln.cmpln.doc" text="Documents" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
										<c:if
											test="${command.complaintDocsList ne null  && not empty command.complaintDocsList }">
											<input type="hidden" name="deleteFileId"
												value="${command.complaintDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.complaintDocsList[0]}">
											<apptags:filedownload
												filename="${command.complaintDocsList[0].attFname}"
												filePath="${command.complaintDocsList[0].attPath}"
												actionUrl="AdminHome.html?Download"></apptags:filedownload>
										</c:if>
									</div>
								</div>


								<div class="form-group">
									<label class="control-label col-sm-2" for="surveyRep"><spring:message
											code='ln.inspec.surveyRep' text="Survey Report" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											disabled="${command.modeType eq 'V'}"
											path="inspectionDto.surveyReport" id="surveyReport"
											maxlength="290" />
									</div>

									<label class="control-label col-sm-2 required-control"
										for="surveyLandType"><spring:message
											code='ln.inspec.surveyLnType' text="Type of Land for Survey" /></label>
									<c:set var="baseLookupCode" value="TOL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="inspectionDto.lnTypeSurvey"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true" isMandatory="true"
										disabled="${command.modeType eq 'V'}" />
								</div>
							</div>
						</div>
					</div>

					<!-- Photo Details START-->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#photoDetails"><spring:message
										code='ln.inspec.phto.det' text="Photo Details" /></a>
							</h4>
						</div>
						<div id="photoDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<%-- <div id="uploadPhotoDiv">
									<div class="table-responsive">
										<c:set var="index" value="0" scope="page" />
										<table class="table table-bordered table-striped" id="attachPhoto">
											<tr>
												<th><spring:message code="" text="Sr No." /></th> 
												<th><spring:message code="" text="Latitude" /></th>
												<th><spring:message code="" text="Longitude" /></th>
												<th><spring:message code="" text="Upload" /></th>
												<th scope="col" width="8%"><a
													onclick='fileCountUpload(this);'
													class="btn btn-blue-2 btn-sm addButton"><i
														class="fa fa-plus-circle"></i></a></th>
											</tr>
											<tr class="appendableClass">
												<td id="sequnce">${d}</td>
												<td><form:input placeholder="Enter Latitude"
														path="photosList[${index}].descriptionType"
														disabled="${command.modeType eq 'V'}"
														class=" form-control landDatt" maxlength="20" /></td>
												<td><form:input placeholder="Enter Longitude"
														path="photosList[${index}].documentType"
														disabled="${command.modeType eq 'V'}"
														class=" form-control landDatt" maxlength="20" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7" isDisabled="${command.modeType eq 'V'}"
														fieldPath="photosList[${index}].uploadedDocumentPath"
														currentCount="${index}" showFileNameHTMLId="true"
														folderName="${index}" fileSize="WORK_COMMON_MAX_SIZE"
														isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
													</apptags:formField></td>
												<td class="text-center"><a href='#'
													id="0_file_${index}" onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="index" value="${index + 1}" scope="page" />
										</table>
									</div>
								</div> --%>

								<div class="form-group">
									<!-- Upload Document Start -->
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="ln.inspec.phto.up" text="Documents" />
									</label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">

										<c:if test="${command.modeType eq 'A'}">
											<apptags:formField fieldType="7"
												fieldPath="photosList[${count}].uploadedDocumentPath"
												currentCount="${count}" folderName="${count}"
												fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
												isMandatory="true" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
												cssClass="clear">
											</apptags:formField>
										</c:if>
										<c:forEach items="${command.photosDocs}" varStatus="loop">
											<input type="hidden" name="deleteFileId"
												value="${command.photosDocs[loop.index].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.photosDocs[loop.index]}">
											<apptags:filedownload
												filename="${command.photosDocs[loop.index].attFname}"
												filePath="${command.photosDocs[loop.index].attPath}"
												actionUrl="AdminHome.html?Download"></apptags:filedownload>
										</c:forEach>


									</div>

									<label class="control-label col-sm-2 " for="name"><spring:message
											code='ln.inspec.lat' text="Latitude" /></label>
									<div class="col-sm-4">
										<form:input placeholder="Enter Latitude"
											path="inspectionDto.latitude"
											onkeypress="return hasAmount(event, this, 8, 4)"
											disabled="${command.modeType eq 'V'}" class=" form-control "
											maxlength="12" />
									</div>

									<label class="control-label col-sm-2 " for="name"><spring:message
											code='ln.inspec.long' text="Longitude" /></label>
									<div class="col-sm-4">
										<form:input placeholder="Enter Longitude"
											path="inspectionDto.longitude"
											onkeypress="return hasAmount(event, this, 8, 4)"
											disabled="${command.modeType eq 'V'}" class=" form-control "
											maxlength="12" />
									</div>

									<apptags:textArea labelCode="ln.inspec.phto.caption"
										maxlegnth="98" path="inspectionDto.photoCaption"
										isMandatory="false" cssClass="mandColorClass comment"
										isDisabled="${command.modeType eq 'V'}" />
								</div>


							</div>
						</div>
					</div>
					<!-- Photo Details END-->

					<!-- Land Details STRAT-->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#landDetails"><spring:message
										code='ln.inspec.lnd.det' text="Land Details" /></a>
							</h4>
						</div>
						<div id="landDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										id="floorLabelId" for="floor"><spring:message
											code='ln.inspec.dis' text="District" /></label>
									<apptags:lookupField items="${command.getLevelData('DIS')}"
										path="inspectionDto.disttId"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true"
										disabled="${command.modeType eq 'V'}" />

									<label class="control-label col-sm-2 required-control"
										id="floorLabelId" for="floor"><spring:message
											code='ln.inspec.thl' text="Tehsil" /></label>
									<apptags:lookupField items="${command.getLevelData('THL')}"
										path="inspectionDto.tehsilId"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true"
										disabled="${command.modeType eq 'V'}" />


								</div>

								<div class="form-group">
									<apptags:input labelCode="ln.inspec.cat"
										isDisabled="${command.modeType eq 'V'}"
										path="inspectionDto.category" cssClass="alphaNumeric"
										maxlegnth="49"></apptags:input>

									<label class="control-label col-sm-2 required-control"
										id="floorLabelId" for="floor"><spring:message
											code='ln.inspec.ksrNo' text="Khasra No" /></label>
									<apptags:lookupField items="${command.getLevelData('KSR')}"
										path="inspectionDto.khasraNoId"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true"
										disabled="${command.modeType eq 'V'}" />
								</div>

								<div class="form-group">
									<apptags:input labelCode="ln.inspec.lkpArea"
										path="inspectionDto.lekhpalArea"
										cssClass="alphaNumeric preventSpace" maxlegnth="99"
										isMandatory="true" isReadonly="${command.modeType eq 'V' }"></apptags:input>

									<apptags:input labelCode="ln.inspec.rkba"
										path="inspectionDto.rakba"
										cssClass="alphaNumeric preventSpace" maxlegnth="99"
										isMandatory="false" isReadonly="${command.modeType eq 'V'}"></apptags:input>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2  required-control"
										for=complaintId><spring:message
											code='ln.inspec.vilMhl' text="Village/Mahal" /></label>

									<div class="col-sm-4">
										<div class="input-group">
											<%-- <apptags:lookupField items="${command.getLevelData('VLG')}"
												path="inspectionDto.villageId"
												cssClass="form-control chosen-select-no-results"
												selectOptionLabelCode="Select" hasId="true"
												disabled="${command.modeType eq 'V'}" /> --%>


											<form:select path="inspectionDto.villageId"
												cssClass="form-control"
												disabled="${command.modeType eq 'V'}" class="form-control"
												id="villageId">
												<form:option value="0">Select</form:option>
												<c:forEach items="${command.vlgLookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>



											<form:select path="inspectionDto.mahal" id="mahal"
												cssClass="form-control" isMandatory="true"
												class="form-control mandColorClass"
												disabled="${command.modeType eq 'V'}"
												data-rule-required="true">
												<form:option value="0">Select</form:option>
												<form:option value="ZA">ZA</form:option>
												<form:option value="Non-ZA">Non-ZA</form:option>
											</form:select>

										</div>
									</div>

									<apptags:input labelCode="ln.inspec.aakhya"
										path="inspectionDto.aakhya"
										cssClass="alphaNumeric preventSpace" maxlegnth="99"
										isMandatory="false" isReadonly="${command.modeType eq 'V'}"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="ln.inspec.khevatNo"
										path="inspectionDto.khevatNo"
										cssClass="alphaNumeric preventSpace" maxlegnth="49"
										isMandatory="true" isReadonly="${command.modeType eq 'V' }"></apptags:input>

									<apptags:input labelCode="ln.inspec.lnLordName"
										path="inspectionDto.lnLordName"
										cssClass="hasCharacter preventSpace" maxlegnth="99"
										isMandatory="true" isReadonly="${command.modeType eq 'V'}"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="ln.inspec.actNo"
										path="inspectionDto.accountNo"
										cssClass="alphaNumeric preventSpace" maxlegnth="18"
										isMandatory="true" isReadonly="${command.modeType eq 'V' }"></apptags:input>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="measurement">
										<spring:message code="ln.inspec.msmt" text="Measurement" /> <span><i
											class="text-red-1"></i></span>
									</label>
									<div align="center">
										<td colspan="3"><table width="200" border="1">
												<tr>
													<th>N</th>
													<th>S</th>
													<th>E</th>
													<th>W</th>
												</tr>
												<tr>
													<td><div align="center">
															<form:input path="inspectionDto.nMeasure"
																disabled="${command.modeType eq 'V'}"
																onkeypress="return hasAmount(event, this, 10, 2)"
																placeholder="0.0" cssClass="form-control" />
														</div></td>
													<td><div align="center">
															<form:input path="inspectionDto.sMeasure"
																disabled="${command.modeType eq 'V'}"
																onkeypress="return hasAmount(event, this, 10, 2)"
																placeholder="0.0" cssClass="form-control" />
														</div></td>
													<td><div align="center">
															<form:input path="inspectionDto.eMeasure"
																disabled="${command.modeType eq 'V'}"
																onkeypress="return hasAmount(event, this, 10, 2)"
																placeholder="0.0" cssClass="form-control" />
														</div></td>
													<td><div align="center">
															<form:input path="inspectionDto.wMeasure"
																disabled="${command.modeType eq 'V'}"
																onkeypress="return hasAmount(event, this, 13, 2)"
																placeholder="0.0" cssClass="form-control" />
														</div></td>
												</tr>
											</table></td>
									</div>

									<apptags:input labelCode="ln.inspec.prmtMark"
										path="inspectionDto.permanentMark"
										cssClass="alphaNumeric preventSpace" maxlegnth="99"
										isMandatory="true" isReadonly="${command.modeType eq 'V' }"></apptags:input>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="Demarcation">
										<spring:message code="ln.inspec.hdbi" text="Hadbandi" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.hadbandi" id="hadbandi"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

									<label class="col-sm-2 control-label" for="measured"> <spring:message
											code="ln.inspec.measure" text="Measured" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.measured" id="measured"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>
								</div>



								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										id="floorLabelId" for="floor"><spring:message
											code='ln.inspec.lnType' text="Land Type" /></label>
									<apptags:lookupField items="${command.getLevelData('TOL')}"
										path="inspectionDto.landTypeId"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true"
										disabled="${command.modeType eq 'V'}" />

									<label class="col-sm-2 control-label" for="subLdType">
										<spring:message code="ln.inspec.sblnType" text="Sub Land Type" />
										<span><i class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.subLdType" id="subLdType"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Agricultural">Agricultural Property</form:option>
											<form:option value="Residential">Residential Property</form:option>
											<form:option value="Commercial">Commercial Property</form:option>
											<form:option value="Industrial">Industrial Property</form:option>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="propertyType">
										<spring:message code="ln.inspec.proptyType"
											text="Property Type" /> <span><i class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.propertyType"
											id="propertyType"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Central Government">Central Government</form:option>
											<form:option value="State Government">State Government</form:option>
											<form:option value="Municipal">Municipal</form:option>
											<form:option value="Gram Sabha">Gram Sabha</form:option>
											<form:option value="Private">Private</form:option>
										</form:select>
									</div>
									<label class="col-sm-2 control-label" for="subPropType">
										<spring:message code="ln.inspec.subProptyType"
											text="Sub Property Type" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.subPropType" id="subPropType"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Banjar">Banjar</form:option>
											<form:option value="Horticulture">Horticulture</form:option>
											<form:option value="Nadi Nala">Nadi Nala</form:option>
											<form:option value="Park Land">Park Land</form:option>
											<form:option value="Dev Sathal">Dev Sathal</form:option>
											<form:option value="Religious Land">Religious Land</form:option>
											<form:option value="Naami-Benami">Naami-Benami</form:option>
											<form:option value="Naapi-Benaapi">Naapi-Benaapi</form:option>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="groundCondition">
										<spring:message code="ln.inspec.grdCOND"
											text="Ground Condition" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.groundCondition"
											id="groundConditionId"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="FINE">Fine</form:option>
											<form:option value="LESS">less</form:option>
											<form:option value="MORE">More</form:option>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-6">
										<small class="text-blue-2"> <spring:message
												code="ln.inspec.landLess"
												text="If the land is less then who owns it" />
										</small>
									</div>

									<label class="control-label col-sm-2" id="nameLbl"
										for="ln.inspec.lessKhasraNo"><spring:message
											code='ln.inspec.lessName' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="hasCharacter  form-control "
											path="inspectionDto.name" id="name"
											disabled="${command.modeType eq 'V'}" maxlength="98" />
									</div>

									<label class="control-label col-sm-2" id="khasraNoLbl"
										for="khasraNoLbl"><spring:message
											code='ln.inspec.lessKhasraNo' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control "
											path="inspectionDto.khasraNo" id="khasraNo"
											disabled="${command.modeType eq 'V'}" maxlength="48" />
									</div>


									<label for="text-1514452818192" class="col-sm-2 control-label"><spring:message
											code="ln.inspec.lessAreaLND" text="Area of Land" /></label>
									<div class="col-sm-4">
										<div class="input-group col-sm-12 ">
											<form:input type="text"
												cssClass="decimal text-right form-control"
												disabled="${command.modeType eq 'V'}"
												onkeypress="return hasAmount(event, this, 8, 2)"
												path="inspectionDto.areaOfLand" placeholder="0.0"
												maxlegnth="10"></form:input>
											<div class="input-group-field">
												<c:set var="baseLookupCode" value="UOA" />
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}"
													path="inspectionDto.areaMeasurement"
													disabled="${command.modeType eq 'V'}"
													cssClass="form-control" hasChildLookup="false" hasId="true"
													showAll="false" selectOptionLabelCode="Select"
													isMandatory="false" />
											</div>
										</div>
									</div>
								</div>


							</div>
						</div>
					</div>
					<!-- Land Details END-->

					<!--HADBANDI Details START -->

					<div class="panel panel-default" id="hadbandiSection">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#hadbandiDet"><spring:message
										code='ln.inspec.hd.det' text="Hadbandi Details" /></a>
							</h4>
						</div>
						<div id="hadbandiDet" class="panel-collapse collapse">
							<div class="panel-body">

								<div class="table-responsive margin-top-10">
									<c:set var="d" value="0" scope="page" />
									<table
										class="table table-striped table-condensed table-bordered"
										id="hadbandiTable">
										<thead>
											<tr>
												<th scope="col" width="3%"><spring:message
														code="ser.no" text="Sr.No." /></th>
												<th><spring:message code="ln.inspec.hd.dire"
														text="Direction" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.hd.name"
														text="Name" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.hd.add"
														text="Address" /></th>
												<th><spring:message code="ln.inspec.hd.contNo"
														text="Contact No" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.hd.road"
														text="Road" /></th>
												<th><spring:message code="ln.inspec.hd.ldMark"
														text="Land Mark" /></th>
												<c:if test="${command.modeType ne 'V' }">
													<th scope="col" width="5%"><a
														href="javascript:void(0);" data-toggle="tooltip"
														data-placement="top" onclick="addHadbandiEntry();"
														class=" btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.inspectionDto.demarcationsDtos) > 0}">
													<c:forEach
														items="${command.inspectionDto.demarcationsDtos}"
														varStatus="status">
														<tr class="hadbandiClass">
															<form:hidden
																path="inspectionDto.demarcationsDtos[${d}].demarcationId"
																id="demarcationId${d}" />
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequenceM${d}" value="${d+1}" disabled="true" /></td>
															<td><form:select
																	path="inspectionDto.demarcationsDtos[${d}].demarSide"
																	id="demarSide${d}" cssClass="form-control"
																	isMandatory="true"
																	disabled="${command.modeType eq 'V'}"
																	data-rule-required="true">
																	<form:option value="0">
																		<spring:message code="care.select" text="Select" />
																	</form:option>
																	<form:option value="North">NORTH</form:option>
																	<form:option value="South">SOUTH</form:option>
																	<form:option value="East">EAST</form:option>
																	<form:option value="West">WEST</form:option>
																</form:select></td>
															<td><form:input
																	path="inspectionDto.demarcationsDtos[${d}].name"
																	maxlength="98" data-rule-required="true" id="name${d}"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="required-control form-control hasCharacter" /></td>
															<td><form:input
																	path="inspectionDto.demarcationsDtos[${d}].address"
																	maxlength="298" id="address${d}"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="form-control" /></td>

															<td><form:input type="text"
																	path="inspectionDto.demarcationsDtos[${d}].contactNo"
																	disabled="${command.modeType eq 'V'}"
																	id="contactNo${d}" maxlength="10"
																	data-rule-required="true"
																	cssClass="required-control form-control hasMobileNo" /></td>

															<td><form:input
																	path="inspectionDto.demarcationsDtos[${d}].road"
																	id="road${d}" disabled="${command.modeType eq 'V'}"
																	cssClass="form-control" /></td>
															<td><form:input
																	path="inspectionDto.demarcationsDtos[${d}].landmark"
																	id="landmark${d}" disabled="${command.modeType eq 'V'}"
																	cssClass="form-control" /></td>



															<c:if test="${command.modeType ne 'V' }">
																<td align="center"><a href='#'
																	data-toggle="tooltip" data-placement="top"
																	class="btn btn-danger btn-sm delButton"
																	onclick="deleteHadbandiEntry($(this));"><i
																		class="fa fa-minus"></i></a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="hadbandiClass">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequenceM${d}" value="${d+1}" disabled="true" /></td>

														<td><form:select
																path="inspectionDto.demarcationsDtos[${d}].demarSide"
																id="demarSide${d}" cssClass="form-control"
																isMandatory="true" disabled="${command.modeType eq 'V'}"
																data-rule-required="true">
																<form:option value="0">
																	<spring:message code="care.select" text="select" />
																</form:option>
																<form:option value="North">NORTH</form:option>
																<form:option value="South">SOUTH</form:option>
																<form:option value="East">EAST</form:option>
																<form:option value="West">WEST</form:option>
															</form:select></td>
														<td><form:input type="text"
																path="inspectionDto.demarcationsDtos[${d}].name"
																maxlength="98" data-rule-required="true" id="name${d}"
																cssClass="required-control form-control hasCharacter " /></td>
														<td><form:input
																path="inspectionDto.demarcationsDtos[${d}].address"
																maxlength="298" id="address${d}" cssClass="form-control" /></td>

														<td><form:input type="text"
																path="inspectionDto.demarcationsDtos[${d}].contactNo"
																id="contactNo${d}" maxlength="10"
																onkeypress="return onlyNumber(this)"
																data-rule-required="true" data-rule-minlength="10"
																class=" form-control mandColorClass" /></td>


														<td><form:input
																path="inspectionDto.demarcationsDtos[${d}].road"
																maxlength="28" id="road${d}" cssClass="form-control" /></td>
														<td><form:input
																path="inspectionDto.demarcationsDtos[${d}].landmark"
																maxlength="28" id="landmark${d}" cssClass="form-control" /></td>

														<c:if test="${command.modeType ne 'V' }">
															<td align="center"><a href='#' data-toggle="tooltip"
																data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteHadbandiEntry($(this));"><i
																	class="fa fa-minus"></i></a></td>
														</c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>



							</div>
						</div>
					</div>
					<!--HADBANDI Details END -->

					<!--ENCROACHMENT Details START -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#encroachmentDet"><spring:message
										code='ln.inspec.ec.det' text="Encroachment Details" /></a>
							</h4>
						</div>
						<div id="encroachmentDet" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="encSingle">
										<spring:message code="ln.inspec.ec.single" text="Encroachment" />
										<span><i class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.encrSingleSelect"
											id="encrSingleSelect"
											cssClass="form-control chosen-select-no-results"
											isMandatory="false" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>


								</div>



								<div class="table-responsive margin-top-5">
									<c:set var="e" value="0" scope="page" />
									<table
										class="table table-striped table-condensed table-bordered"
										id="singleEncroachmentTable">
										<thead>
											<tr>
												<th><spring:message code="ln.inspec.hd.dire"
														text="Direction" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.ec.name"
														text="Name" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.ec.add"
														text="Address" /></th>
												<th><spring:message code="ln.inspec.ec.contNo"
														text="Contact No" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.ec.road"
														text="Road" /></th>
												<th><spring:message code="ln.inspec.ec.landMrk"
														text="Land Mark" /></th>

											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.inspectionDto.encroachmentsDtos) > 0}">
													<c:forEach
														items="${command.inspectionDto.encroachmentsDtos}"
														varStatus="status">
														<tr class="encroachmentClass">
															<form:hidden
																path="inspectionDto.encroachmentsDtos[${e}].encroachmentId"
																id="encroachmentIdS${e}" />
															<form:hidden
																path="inspectionDto.encroachmentsDtos[${e}].encrType"
																id="encrTypeS${e}" value="S" />

															<td><form:select
																	path="inspectionDto.encroachmentsDtos[${e}].direction"
																	id="directionS${e}" cssClass="form-control"
																	isMandatory="true"
																	disabled="${command.modeType eq 'V'}"
																	data-rule-required="true">
																	<form:option value="0">
																		<spring:message code="care.select" text="Select" />
																	</form:option>
																	<form:option value="North">NORTH</form:option>
																	<form:option value="South">SOUTH</form:option>
																	<form:option value="East">EAST</form:option>
																	<form:option value="West">WEST</form:option>
																</form:select></td>


															<td><form:input
																	path="inspectionDto.encroachmentsDtos[${e}].name"
																	id="nameS${e}" maxlength="98"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="required-control form-control hasCharacter" /></td>
															<td><form:input
																	path="inspectionDto.encroachmentsDtos[${e}].address"
																	disabled="${command.modeType eq 'V'}" id="addressS${e}"
																	maxlength="298" cssClass="form-control" /></td>

															<td><form:input
																	path="inspectionDto.encroachmentsDtos[${e}].contactNo"
																	id="contactNoS${e}"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="required-control form-control" /></td>

															<td><form:input
																	path="inspectionDto.encroachmentsDtos[${e}].road"
																	disabled="${command.modeType eq 'V'}" id="roadS${e}"
																	cssClass="form-control" /></td>
															<td><form:input
																	path="inspectionDto.encroachmentsDtos[${e}].landmark"
																	id="landmarkS${e}"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="form-control" /></td>

														</tr>
														<c:set var="e" value="${e + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="encroachmentClass">
														<form:hidden
															path="inspectionDto.encroachmentsDtos[${e}].encroachmentId"
															id="encroachmentId${e}" />
														<form:hidden
															path="inspectionDto.encroachmentsDtos[${e}].encrType"
															id="encrTypeS${e}" value="S" />

														<td><form:select
																path="inspectionDto.encroachmentsDtos[${e}].direction"
																id="directionS${e}" cssClass="form-control "
																isMandatory="true" disabled="${command.modeType eq 'V'}"
																data-rule-required="true">
																<form:option value="0">
																	<spring:message code="care.select" text="Select" />
																</form:option>
																<form:option value="North">NORTH</form:option>
																<form:option value="South">SOUTH</form:option>
																<form:option value="East">EAST</form:option>
																<form:option value="West">WEST</form:option>
															</form:select></td>
														<td><form:input
																path="inspectionDto.encroachmentsDtos[${e}].name"
																id="nameS${e}" maxlength="98"
																cssClass="required-control form-control hasCharacter" /></td>
														<td><form:input
																path="inspectionDto.encroachmentsDtos[${e}].address"
																id="addressS${e}" cssClass="form-control"
																maxlength="298" /></td>

														<td><form:input
																path="inspectionDto.encroachmentsDtos[${e}].contactNo"
																id="contactNoS${e}" maxlength="10"
																data-rule-required="true"
																cssClass="required-control form-control hasMobileNo" /></td>

														<td><form:input
																path="inspectionDto.encroachmentsDtos[${e}].road"
																maxlength="28" id="roadS${e}" cssClass="form-control" /></td>
														<td><form:input
																path="inspectionDto.encroachmentsDtos[${e}].landmark"
																maxlength="28" id="landmarkS${e}"
																cssClass="form-control" /></td>


													</tr>
													<c:set var="e" value="${e + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>



								<div class="form-group" id="multiEncroachDivId">
									<label class="col-sm-2 control-label" for="encMulti"> <spring:message
											code="ln.inspec.ec.multi" text="Multiple Encroachment" /> <span><i
											class="text-red-1"></i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.encrMultipleSelect"
											id="encrMultipleSelect"
											cssClass="form-control chosen-select-no-results"
											isMandatory="false" class="form-control mandColorClass"
											disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>
								</div>




								<div class="table-responsive margin-top-5"
									id="multiEncroachmentTable">
									<c:set var="f" value="0" scope="page" />
									<table
										class="table table-striped table-condensed table-bordered"
										id="multiEncroDetTable">
										<thead>
											<tr>

												<th scope="col" width="3%"><spring:message
														code="ser.no" text="Sr.No." /></th>
												<th><spring:message code="ln.inspec.hd.dire"
														text="Direction" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.ec.name"
														text="Name" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.ec.add"
														text="Address" /></th>
												<th><spring:message code="ln.inspec.ec.contNo"
														text="Contact No" /><i class="text-red-1">*</i></th>
												<th><spring:message code="ln.inspec.ec.road"
														text="Road" /></th>
												<th><spring:message code="ln.inspec.ec.landMrk"
														text="Land Mark" /></th>
												<c:if test="${command.modeType ne 'V' }">
													<th scope="col" width="5%"><a
														href="javascript:void(0);" data-toggle="tooltip"
														data-placement="top" onclick="addEntryData();"
														class=" btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.inspectionDto.multiEncroachmentsDtos) > 0}">
													<c:forEach
														items="${command.inspectionDto.multiEncroachmentsDtos}"
														varStatus="status">
														<tr class="multiEncroachmentClass">
															<form:hidden
																path="inspectionDto.multiEncroachmentsDtos[${f}].encroachmentId"
																id="encroachmentIdM${f}" />
															<form:hidden
																path="inspectionDto.multiEncroachmentsDtos[${f}].encrType"
																id="encrTypeM${f}" value="M" />
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequenceM${f}" value="${f+1}" disabled="true" /></td>

															<td><form:select
																	path="inspectionDto.multiEncroachmentsDtos[${f}].direction"
																	id="directionM${f}" cssClass="form-control"
																	isMandatory="true"
																	disabled="${command.modeType eq 'V'}"
																	data-rule-required="true">
																	<form:option value="0">
																		<spring:message code="care.select" text="Select" />
																	</form:option>
																	<form:option value="North">NORTH</form:option>
																	<form:option value="South">SOUTH</form:option>
																	<form:option value="East">EAST</form:option>
																	<form:option value="West">WEST</form:option>
																</form:select></td>

															<td><form:input
																	path="inspectionDto.multiEncroachmentsDtos[${f}].name"
																	id="nameM${f}" disabled="${command.modeType eq 'V'}"
																	cssClass="required-control form-control hasCharacter" /></td>
															<td><form:input
																	path="inspectionDto.multiEncroachmentsDtos[${f}].address"
																	id="addressM${f}" disabled="${command.modeType eq 'V'}"
																	cssClass="form-control" /></td>

															<td><form:input
																	path="inspectionDto.multiEncroachmentsDtos[${f}].contactNo"
																	id="contactNoM${f}"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="required-control form-control" /></td>

															<td><form:input
																	path="inspectionDto.multiEncroachmentsDtos[${f}].road"
																	id="roadM${f}" cssClass="form-control"
																	disabled="${command.modeType eq 'V'}" /></td>
															<td><form:input
																	path="inspectionDto.multiEncroachmentsDtos[${f}].landmark"
																	id="landmarkM${f}"
																	disabled="${command.modeType eq 'V'}"
																	cssClass="form-control" /></td>

															<c:if test="${command.modeType ne 'V' }">
																<td align="center"><a href='#'
																	data-toggle="tooltip" data-placement="top"
																	class="btn btn-danger btn-sm delButton"
																	onclick="deleteEntry($(this));"><i
																		class="fa fa-minus"></i></a></td>
															</c:if>
														</tr>
														<c:set var="f" value="${f + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="encroachmentClass">
														<form:hidden
															path="inspectionDto.multiEncroachmentsDtos[${f}].encroachmentId"
															id="encroachmentMId${f}" />
														<form:hidden
															path="inspectionDto.multiEncroachmentsDtos[${f}].encrType"
															id="encrTypeM${f}" value="M" />

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequenceM${f}" value="${f+1}" disabled="true" /></td>

														<td><form:select
																path="inspectionDto.multiEncroachmentsDtos[${f}].direction"
																id="directionM${f}" cssClass="form-control "
																isMandatory="true" disabled="${command.modeType eq 'V'}"
																data-rule-required="true">
																<form:option value="0">
																	<spring:message code="care.select" text="Select" />
																</form:option>
																<form:option value="North">NORTH</form:option>
																<form:option value="South">SOUTH</form:option>
																<form:option value="East">EAST</form:option>
																<form:option value="West">WEST</form:option>
															</form:select></td>
														<td><form:input
																path="inspectionDto.multiEncroachmentsDtos[${f}].name"
																id="nameM${f}" maxlength="98"
																cssClass="required-control form-control hasCharacter" /></td>
														<td><form:input
																path="inspectionDto.multiEncroachmentsDtos[${f}].address"
																id="addressM${f}" cssClass="form-control"
																maxlength="298" /></td>

														<td><form:input
																path="inspectionDto.multiEncroachmentsDtos[${f}].contactNo"
																id="contactNoM${f}" maxlength="10"
																data-rule-required="true"
																cssClass="required-control form-control hasMobileNo" /></td>

														<td><form:input
																path="inspectionDto.multiEncroachmentsDtos[${f}].road"
																maxlength="28" id="roadM${f}" cssClass="form-control" /></td>
														<td><form:input
																path="inspectionDto.multiEncroachmentsDtos[${f}].landmark"
																maxlength="28" id="landmarkM${f}"
																cssClass="form-control" /></td>

														<c:if test="${command.modeType ne 'V' }">
															<td align="center"><a href='#' data-toggle="tooltip"
																data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry($(this));"><i
																	class="fa fa-minus"></i></a></td>
														</c:if>
													</tr>
													<c:set var="f" value="${f + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>



							</div>
						</div>
					</div>
					<!--ENCROACHMENT Details END -->

					<!-- Case Details START-->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#caseDetails"><spring:message
										code='ln.inspec.case.det' text="Case Details" /></a>
							</h4>
						</div>
						<div id="caseDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="Case"> <spring:message code="ln.inspec.case.pend"
											text="Case Pendancy" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="inspectionDto.casePendancySelect"
											id="casePendancySelect"
											cssClass="form-control chosen-select-no-results"
											class="form-control" disabled="${command.modeType eq 'V'}"
											data-rule-required="true">
											<form:option value="0">Select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" id="crtNameLbl"
										for="crtNameFor"><spring:message
											code='ln.inspec.case.crtName' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasCharacter"
											path="inspectionDto.landCasesDtos[0].crtName" id="crtName"
											disabled="${command.modeType eq 'V'}" maxlength="98" />
									</div>




									<apptags:input labelCode="ln.inspec.case.crtAdd"
										path="inspectionDto.landCasesDtos[0].crtAdd"
										cssClass="alphaNumeric preventSpace" maxlegnth="148"
										isMandatory="false" isReadonly="${command.modeType eq 'V'}"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="ln.inspec.case.date"
										isDisabled="${command.modeType eq 'V'}"
										datePath="inspectionDto.landCasesDtos[0].crtDate"></apptags:date>
								</div>
								<div class="form-group">

									<label class="control-label col-sm-2" id="litigantLbl"
										for="crtNameFor"><spring:message
											code='ln.inspec.case.litigant' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control "
											path="inspectionDto.landCasesDtos[0].litigant" id="litigant"
											disabled="${command.modeType eq 'V'}" maxlength="98" />
									</div>

									<label class="control-label col-sm-2" id="respondentLbl"
										for="crtNameFor"><spring:message
											code='ln.inspec.case.resp' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control "
											path="inspectionDto.landCasesDtos[0].respondent"
											id="respondent" disabled="${command.modeType eq 'V'}"
											maxlength="98" />
									</div>


								</div>
								<div class="form-group">

									<label class="control-label col-sm-2" id="advocateNameLbl"
										for="crtNameFor"><spring:message
											code='ln.inspec.case.advoName' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasCharacter"
											path="inspectionDto.landCasesDtos[0].advocateName"
											id="advocateName" disabled="${command.modeType eq 'V'}"
											maxlength="48" />
									</div>


									<label class="col-sm-2 control-label" id="contactNoLbl"
										for="mobileNo"><spring:message
											code="ln.inspec.case.contName" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											id="contactNo" disabled="${command.modeType eq 'V'}"
											path="inspectionDto.landCasesDtos[0].contactNo"
											maxlength="10"></form:input>

									</div>
								</div>
								<div class="form-group">
									<apptags:textArea isMandatory="false"
										labelCode="ln.inspec.comments"
										isDisabled="${command.modeType eq 'V' }"
										path="inspectionDto.conviction" maxlegnth="249"
										cssClass="preventSpace"></apptags:textArea>
								</div>


							</div>
						</div>
					</div>
					<!-- Case Details END-->


					<!-- Upload Document Start -->
					<div class="form-group margin-top-10">
						<label for="" class="col-sm-2 control-label"> <spring:message
								code="lqp.upload.doc" text="Document" />
						</label>
						<c:set var="docCt" value="0" scope="page"></c:set>
						<div class="col-sm-4">

							<%-- currentCount="${docCt}" folderName="${docCt}" --%>
							<c:if test="${command.modeType eq 'A'}">
								<apptags:formField fieldType="7"
									fieldPath="attachments[${docCt}].uploadedDocumentPath"
									currentCount="6" folderName="6" fileSize="BND_COMMOM_MAX_SIZE"
									showFileNameHTMLId="true" isMandatory="true"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									cssClass="clear">
								</apptags:formField>
								<div class="col-sm-10">
									<small class="text-blue-2"> <spring:message
											code="ln.inspec.size.upload"
											text="(Upload Image File upto 5 MB)" />
									</small>
								</div>
							</c:if>




							<c:if
								test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
								<input type="hidden" name="deleteFileId"
									value="${command.attachDocsList[0].attId}">
								<input type="hidden" name="downloadLink"
									value="${command.attachDocsList[0]}">
								<apptags:filedownload
									filename="${command.attachDocsList[0].attFname}"
									filePath="${command.attachDocsList[0].attPath}"
									actionUrl="AdminHome.html?Download"></apptags:filedownload>
							</c:if>
						</div>
					</div>


					<!-- Upload Document end -->

				</div>


				<c:choose>
					<c:when test="${command.modeType eq 'A'}">
						<div class="text-center">
							<button type="button" id="save"
								class="btn btn-success btn-submit"
								onclick="saveLandInspection(this);">
								<spring:message code="care.submit" text="Submit" />
							</button>
							<button type="Reset" class="btn btn-warning"
								id="resetLnInspection">
								<spring:message code="bt.clear" />
							</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="widget-content padding panel-group accordion-toggle"
							id="accordion_single_collapse1">
							<apptags:CheckerAction hideForward="false" hideSendback="true"
								showInitiator="false"></apptags:CheckerAction>
						</div>
						<div class="text-center widget-content padding">
							<button type="button" id="save"
								class="btn btn-success btn-submit" onclick="saveDecision(this);">
								<spring:message code="master.save" text="Save" />
							</button>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel"
								onclick="window.location.href='AdminHome.html'"
								id="button-Cancel">
								<spring:message code="lqp.button.back" text="Back" />
							</button>
						</div>
					</c:otherwise>
				</c:choose>


			</form:form>
		</div>
	</div>
</div>