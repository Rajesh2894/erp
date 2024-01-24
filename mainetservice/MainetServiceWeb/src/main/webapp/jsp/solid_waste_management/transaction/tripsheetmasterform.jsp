<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/tripSheet.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.tripsheet"
						text="Trip Sheet" /></strong>
			</h2>
			<apptags:helpDoc url="TripSheetMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" /><i
					class="text-red-1">* </i> <spring:message
						code="solid.waste.mand.field" /> </span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="TripSheetMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="tripsheetmasterform" id="id_tripsheetform">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="swm.tripsheet" text="Trip Sheet" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when
										test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
										<div class="form-group">
											<apptags:input labelCode="swm.tripdate" isReadonly="true"
												cssClass="datepicker tripdate" path="tripSheetDto.tripDate"
												isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : true }" />

											<label class="col-sm-2 control-label " for=""><spring:message
													code="swm.beat" text="Beat No." /></label>
											<div class="col-sm-4">
												<form:select path="tripSheetDto.beatNo"
													cssClass="form-control " id="beatNo" onchange=""
													disabled="${command.saveMode eq 'V' ? true : true }"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.routelist}" var="lookup">
														<form:option value="${lookup.beatId}">${lookup.beatNo } &nbsp; ${ lookup.beatName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="vehicleno"><spring:message
													code="swm.vehiclenumber" /></label>
											<div class="col-sm-4">
												<form:select path="tripSheetDto.veId" id="veId"
													class="form-control mandColorClass " label="Select"
													disabled="${command.saveMode eq 'V' ? true : true }">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
													</c:forEach>
												</form:select>
											</div>
											<label class="col-sm-2 control-label required-control"
												for="desposalsite"><spring:message
													code="swm.dsplsite" /> </label>
											<div class="col-sm-4">
												<form:select path="tripSheetDto.deId"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select"
													disabled="${command.saveMode eq 'V' ? true : true }"
													id="deId">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.mrfMasterList}" var="lookUp">
														<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="In Time"><spring:message
													code="swm.intime" /><span><i class="text-red-1">*</i></span></label>
											<div class="col-sm-4">
												<form:input path="tripSheetDto.tripIntimeDesc"
													cssClass="form-control  mandColorClass datetimepicker3 "
													id="tripIntimeDesc"
													disabled="${command.saveMode eq 'V' ? true : true }" />
											</div>
											<label class="col-sm-2 control-label" for="Out Time"><spring:message
													code="swm.outtime" /></label>
											<div class="col-sm-4">
												<form:input path="tripSheetDto.tripOuttimeDesc"
													cssClass="form-control  datetimepicker3 "
													id="tripOuttimeDesc"
													disabled="${command.saveMode eq 'V' ? true : false }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="tripEntweight"><spring:message
													code="swm.entrywght" /><span><i class="text-red-1">*</i></span></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="tripSheetDto.tripEntweight"
														onchange="sub()"
														cssClass="form-control mandColorClass text-right"
														onkeypress="return hasAmount(event, this, 12, 2)"
														id="tripEntweight"
														disabled="${command.saveMode eq 'V' ? true : true }" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
											<label class="col-sm-2 control-label" for="tripExitweight"><spring:message
													code="swm.exitwght" /> </label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="tripSheetDto.tripExitweight"
														onchange="sub()"
														cssClass="form-control text-right"
														onkeypress="return hasAmount(event, this, 12, 2)"
														id="tripExitweight"
														disabled="${command.saveMode eq 'V' ? true : false }" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="totalgarbage"><spring:message
													code="swm.totalgarbage" /> <span><i
													class="text-red-1">*</i></span></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="tripSheetDto.tripTotalgarbage"
														cssClass="form-control mandColorClass hasDecimal text-right"
														id="tripTotalgarbage" disabled="" readonly="true" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
											<apptags:input labelCode="swm.drivername"
												path="tripSheetDto.tripDrivername" isMandatory=""
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>
										<div class="form-group">
											<apptags:input labelCode="swm.weighslipNo"
												path="tripSheetDto.tripWeslipno" isMandatory=""
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
											<c:if test="${command.attachDocsList.isEmpty() }">
												<c:if test="${command.saveMode ne 'V'}">
													<label class="col-sm-2 control-label"><spring:message
															code="swm.fileupload" /></label>
													<div class="col-sm-4">
														<small class="text-blue-2"> <spring:message
																code="swm.uploadnote" text="(Upload File upto 5MB)" />
														</small>
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="" isMandatory="false"
															showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
															currentCount="0" />
													</div>
												</c:if>
											</c:if>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="swm.waste.segregated" text="Waste Segregated?" /></label>
											<div class="col-sm-4">
												<label class="radio-inline "> <form:radiobutton
														id="wasteSeg" path="tripSheetDto.wasteSeg" value="Y" disabled="${command.saveMode eq 'V' ? true : true }"/>
													<spring:message code="solid.waste.Yes" text="Yes" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="wasteSeg" path="tripSheetDto.wasteSeg" value="N"
														checked="" disabled="${command.saveMode eq 'V' ? true : true }"/> <spring:message
														code="solid.waste.No" text="No" />
												</label>
											</div>
										</div>
										<c:if test="${! command.attachDocsList.isEmpty() }">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocs">
													<tr>
														<th width="20%"><spring:message
																code="public.toilet.master.srno" text="Sr. No." /></th>
														<th><spring:message code="swm.viewDocument"
																text="View Document" /></th>
													</tr>
													<c:forEach items="${command.attachDocsList}" var="lookUp"
														varStatus="d">
														<tr>
															<td align="center">${d.count}</td>
															<td align="center"><apptags:filedownload
																	filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="TripSheetMaster.html?Download" /></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</c:if>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<apptags:input labelCode="swm.tripdate" isReadonly="true"
												cssClass="datepicker tripdate" path="tripSheetDto.tripDate"
												isMandatory="true" />
											<label class="col-sm-2 control-label " for=""><spring:message
													code="swm.beat" text="Beat No." /></label>
											<div class="col-sm-4">
												<form:select path="tripSheetDto.beatNo"
													cssClass="form-control " id="beatNo" onchange=""
													disabled="" data-rule-required="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.routelist}" var="lookup">
														<form:option value="${lookup.beatId}">${lookup.beatNo } &nbsp; ${ lookup.beatName}</form:option>
													</c:forEach>
												</form:select>
											</div>

										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="vehicleno"><spring:message
													code="swm.vehiclenumber" /></label>
											<div class="col-sm-4">
												<form:select path="tripSheetDto.veId"
													class="form-control mandColorClass " label="Select"
													id="veId">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}">${lookup.veNo} </form:option>
													</c:forEach>
												</form:select>
											</div>
											<label class="col-sm-2 control-label required-control"
												for="desposalsite"><spring:message
													code="swm.dsplsite" /> </label>
											<div class="col-sm-4">
												<form:select path="tripSheetDto.deId"
													class="form-control mandColorClass chosen-select-no-results"
													label="Select" id="deId">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.mrfMasterList}" var="lookUp">
														<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="In Time"><spring:message
													code="swm.intime" /><span><i class="text-red-1">*</i></span></label>
											<div class="col-sm-4">
												<form:input path="tripSheetDto.tripIntimeDesc"
													cssClass="form-control  mandColorClass datetimepicker3 "
													id="tripIntimeDesc" disabled="" />
											</div>
											<label class="col-sm-2 control-label" for="Out Time"><spring:message
													code="swm.outtime" /></label>
											<div class="col-sm-4">
												<form:input path="tripSheetDto.tripOuttimeDesc"
													cssClass="form-control   datetimepicker3 "
													id="tripOuttimeDesc" disabled="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="tripEntweight"><spring:message
													code="swm.entrywght" /><span><i class="text-red-1">*</i></span></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="tripSheetDto.tripEntweight"
														onchange="sub()"
														cssClass="form-control  mandColorClass  text-right"
														onkeypress="return hasAmount(event, this, 12, 2)"
														id="tripEntweight" disabled="" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
											<label class="col-sm-2 control-label" for="tripExitweight"><spring:message
													code="swm.exitwght" /> </label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="tripSheetDto.tripExitweight"
														onchange="sub()"
														cssClass="form-control  text-right"
														onkeypress="return hasAmount(event, this, 12, 2)"
														id="tripExitweight" disabled="" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="totalgarbage"><spring:message
													code="swm.totalgarbage" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="tripSheetDto.tripTotalgarbage"
														cssClass="form-control mandColorClass hasDecimal text-right"
														id="tripTotalgarbage" disabled="" readonly="true" />
													<span class="input-group-addon"><spring:message
															code="swm.kgs" text="Kilograms" /></span>
												</div>
											</div>
											<apptags:input labelCode="swm.drivername"
												path="tripSheetDto.tripDrivername" isMandatory=""
												isDisabled=""></apptags:input>
										</div>
										<div class="form-group">
											<apptags:input labelCode="swm.weighslipNo"
												path="tripSheetDto.tripWeslipno" isMandatory=""
												isDisabled=""></apptags:input>
											<label class="col-sm-2 control-label"><spring:message
													code="swm.fileupload" /></label>
											<div class="col-sm-4">
												<small class="text-blue-2"> <spring:message
														code="swm.uploadnote" text="(Upload File upto 5MB)" />
												</small>
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="" isMandatory="false" showFileNameHTMLId="true"
													fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
													currentCount="0" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="swm.waste.segregated" text="Waste Segregated?" /></label>
											<div class="col-sm-4">
												<label class="radio-inline "> <form:radiobutton
														id="wasteSeg" path="tripSheetDto.wasteSeg" value="Y" />
													<spring:message code="solid.waste.Yes" text="Yes" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="wasteSeg" path="tripSheetDto.wasteSeg" value="N"
														checked="checked" /> <spring:message
														code="solid.waste.No" text="No" />
												</label>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"> <spring:message
											code="swm.breakup" text="Break Up " /></a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="id_tripsheetTbl">
										<thead>
											<tr>
												<th scope="col" width="1%"><spring:message
														code="population.master.srno" text="Sr.No." /></th>
												<th scope="col" width="20%"><spring:message
														code="swm.wastetype" text="Waste Type" /></th>
												<th scope="col" width="20%"><spring:message
														code="swm.volume" text="Volume" /></th>
												<c:if test="${command.saveMode ne 'V'}">
													<th scope="col" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>
												</c:if>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<td></td>
												<td align="right"><span style="font-weight: bold"><spring:message
															code="swm.total" text="Total" /></span></td>
												<td align="center">
													<div class="input-group col-sm-6 ">
														<input type="text"
															class="form-control mandColorClass text-right"
															id="id_total" disabled><span
															class="input-group-addon"><spring:message
																code="swm.kgs" text="Kilograms" /></span>
													</div>
												</td>
												<c:if test="${command.saveMode ne 'V'}">
													<td></td>
												</c:if>
											</tr>
										</tfoot>
										<tbody>
											<c:choose>
												<c:when
													test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
													<c:forEach var="tripInfo"
														items="${command.tripSheetDto.tbSwTripsheetGdets}"
														varStatus="status">
														<tr>
															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequence${d}" value="${d+1}" disabled="true" /></td>
															<td align="center"><div
																	class="input-group col-sm-6 ">
																	<c:set var="baseLookupCode" value="WTY" />
																	<form:select
																		path="tripSheetDto.tbSwTripsheetGdets[${d}].wastType"
																		cssClass="form-control mandColorClass"
																		id="wastType${d}" onchange=""
																		disabled="${command.saveMode eq 'V' ? true : false }"
																		data-rule-required="true">
																		<form:option value="0">
																			<spring:message code="solid.waste.select"
																				text="select" />
																		</form:option>
																		<c:forEach
																			items="${command.getSortedLevelData(baseLookupCode,1)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</div></td>
															<td align="center">
																<div class="input-group col-sm-6 ">
																	<form:input
																		path="tripSheetDto.tbSwTripsheetGdets[${d}].tripVolume"
																		cssClass="form-control  mandColorClass text-right"
																		onkeypress="return hasAmount(event, this, 12, 2)"
																		onchange="sum()" id="tripVolume${d }"
																		disabled="${command.saveMode eq 'V' ? true : false }" />
																	<span class="input-group-addon"><spring:message
																			code="swm.kgs" text="Kilograms" /></span>
																</div>
															</td>
															<c:if test="${command.saveMode ne 'V'}">
																<td class="text-center" width="8%">
																<a href="javascript:void(0);" data-toggle="tooltip"
																	title="Add" data-placement="top"
																	onclick="addEntryData('id_tripsheetTbl');"
																	class=" btn btn-success btn-sm"><i
																		class="fa fa-plus-circle"></i></a>
																<a class="btn btn-danger btn-sm delButton" title="Delete"
																	onclick="deleteEntry('id_tripsheetTbl',$(this),'sequence${d}')">
																		<i class="fa fa-minus"></i>
																</a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${d}" value="${d+1}" disabled="true" /></td>
														<td align="center"><div class="input-group col-sm-6 ">
																<c:set var="baseLookupCode" value="WTY" />
																<form:select
																	path="tripSheetDto.tbSwTripsheetGdets[${d}].wastType"
																	cssClass="form-control mandColorClass"
																	id="wastType${d}" onchange="" disabled=""
																	data-rule-required="true">
																	<form:option value="0">
																		<spring:message code="solid.waste.select"
																			text="select" />
																	</form:option>
																	<c:forEach
																		items="${command.getSortedLevelData(baseLookupCode,1)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>
															</div></td>
														<td align="center">
															<div class="input-group col-sm-6 ">
																<form:input
																	path="tripSheetDto.tbSwTripsheetGdets[${d}].tripVolume"
																	cssClass="form-control  mandColorClass  text-right"
																	onkeypress="return hasAmount(event, this, 12, 2)"
																	onchange="sum()" id="tripVolume${d }" disabled="" />
																<span class="input-group-addon"><spring:message
																		code="swm.kgs" text="Kilograms" /></span>
															</div>
														</td>
														<td class="text-center" width="8%">
														<a href="javascript:void(0);" data-toggle="tooltip"
															title="Add" data-placement="top"
															onclick="addEntryData('id_tripsheetTbl');"
															class=" btn btn-success btn-sm"><i
																class="fa fa-plus-circle"></i></a>
														<a class="btn btn-danger btn-sm delButton" title="Delete"
															onclick="deleteEntry('id_tripsheetTbl',$(this),'sequence${d}')">
																<i class="fa fa-minus"></i>
														</a></td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)" id="btnSave">
							<spring:message code="solid.waste.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="button" class="btn btn-warning"
							onclick="ResetForm(this)" id="btndelete">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="TripSheetMaster.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
