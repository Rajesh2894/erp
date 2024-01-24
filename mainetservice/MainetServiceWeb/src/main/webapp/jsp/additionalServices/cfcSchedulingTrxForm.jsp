<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<!-- <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript"
	src="js/additionalServices/cfcSchedulingTrx.js"></script>
	<script src="assets/libs/fullcalendar/moment.min.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="CFCSchedulingTrx.html" method="post"
				class="form-horizontal" name="cfcSchedulingTrxSummary"
				id="cfcSchedulingTrxSummary">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="SFT.collection.mast" text="Collection Master" />
				</h4>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="SFT.collection.center.no" text="Collection Center No." /></label>

					<div class="col-sm-4">
						<form:input path="cfcCollectionMasterDto.cmCollncentreno"
							id="collncentreno" class="form-control mandColorClass"
							/>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="SFT.collection.denter.desc"
							text="Collection Center Description" /></label>

					<div class="col-sm-4">
						<form:input path="cfcCollectionMasterDto.cmDescription"
							id="collectiondesc" class="form-control mandColorClass"
							maxlength="200" />
					</div>

				</div>


				<div class="form-group">

					<c:set var="baseLookupCode" value="CWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="CWZ" hasId="true"
						pathPrefix="cfcCollectionMasterDto.cfcWard"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="false"
						isMandatory="true" />


				</div>
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="cfc.easytap.det" text="Easy Tap Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">

			<%-- 				<div class="form-group">

								<label for="address" class="col-sm-4 control-label"><spring:message
										code="rti.selectAdd"></spring:message> </label>
								<div class="col-sm-4">
									<input type="checkbox" class="esyTap margin-top-10 margin-left-0"
										checked onChange="valueChanged()" data-rule-required="true">
								</div>

							</div> --%>

							<!-- <div id="easyTapDet" class="add" style="display: none;"> -->

								<div class="form-group">


									<apptags:input labelCode="cfc.deviceId" 
										path="cfcCollectionMasterDto.deviceId" maxlegnth="50"></apptags:input>

								</div>
							<!-- </div> -->
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="SFT.counter.det" text="Counter Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-bordered table-striped"
								id="counterDetails">

								<thead>
									<tr>
										<th class="col-sm-2 required-control" style="text-align: left"><spring:message
												code="SFT.counter.no" text="Counter No."></spring:message></th>
										<th class="col-sm-4 required-control" style="text-align: left"><spring:message
												code="SFT.counter.desc" text="Description"></spring:message></th>
										<th class="col-sm-2" style="text-align: center"><button
												type="button" class="" onclick="addcounterEntry()"
												title="Add Counter Entry">
												<i class="fa fa-plus-circle"></i>

											</button></th>

									</tr>
								</thead>
							
								<tbody>
									<c:choose>
										<c:when
											test="${fn:length(command.cfcCollectionMasterDto.cfcCounterMasterDtos)>0}">
											<c:forEach
												items="${command.cfcCollectionMasterDto.cfcCounterMasterDtos}"
												var="asset">

												<tr class="counterEntryRow">
													<td><form:input
															path="cfcCollectionMasterDto.cfcCounterMasterDtos[${d}].cuCountcentreno"
															id="counterNo${d}"
															class="form-control mandColorClass"
														 /></td>

													<td><form:input
															path="cfcCollectionMasterDto.cfcCounterMasterDtos[${d}].cuDescription"
															id="description${d}" class="form-control mandColorClass"
															maxlength="200" /></td>
													<td class="text-center" width="8">
														<button type="button" class="btn btn-blue-2 btn-sm"
															title="Add Schedule Detail" id="addScheduleEntryButton"
															onclick="addScheduleDetail(this,${d})">
															<i class="fa fa-plus-circle"></i>
															<spring:message code="" text="Add Schedule" />
														</button>
														<button type="button"
															class="btn btn-danger btn-sm delButton"
															title="Delete Counter"
															onclick="deleteCounterEntry($(this),'removedIds');">
															<i class="fa fa-minus"></i>

														</button>
													</td>
													<c:set var="d" value="${d + 1}" scope="page" />
												</tr>
											</c:forEach>
										</c:when>

										<c:otherwise>
											<tr class="counterEntryRow">
												<td><form:input
														path="cfcCollectionMasterDto.cfcCounterMasterDtos[${d}].cuCountcentreno"
														id="counterNo${d}"
														class="form-control mandColorClass"
														/></td>

												<td><form:input
														path="cfcCollectionMasterDto.cfcCounterMasterDtos[${d}].cuDescription"
														id="description${d}" class="form-control mandColorClass"
														maxlength="200" /></td>
												<td class="text-center" width="8">
													<button type="button" class="btn btn-blue-2 btn-sm"
														title="Add Schedule" id="addScheduleEntryButton"
														onclick="addScheduleDetail(this,${d})">
														<i class="fa fa-plus-circle"></i>
														<spring:message code="SFT.add.schedule"
															text="Add Schedule" />
													</button>
													<button type="button"
														class="btn btn-danger btn-sm delButton"
														title="Delete Counter"
														onclick="deleteCounterEntry($(this),'removedIds');">
														<i class="fa fa-minus"></i>

													</button>
												</td>
												<c:set var="d" value="${d + 1}" scope="page" />
											</tr>

										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>












				<form:hidden path="counterIndex" id="counterIndex" />


				<c:if test="${command.saveMode eq 'C'}">

					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a4"> <spring:message
									code="" text="Schedule Details" /></a>
						</h4>
						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="c" value="0" scope="page"></c:set>
								<c:set var="index" value="${command.counterIndex}" scope="page"></c:set>
								<table class="table table-bordered table-striped"
									id="ScheduleDetails">

									<thead>
										<tr>
											<th class="col-sm-4" style="text-align: left"><spring:message
													code="SFT.from.date" text="From Date & Time"></spring:message></th>
											<th class="col-sm-4" style="text-align: left"><spring:message
													code="SFT.to.date" text="To Date & time"></spring:message></th>
											<th class="col-sm-2" style="text-align: left"><spring:message
													code="SFT.user.name" text="User Name"></spring:message></th>
											<th class="col-sm-2" style="text-align: left"><spring:message
													code="SFT.schedule.fre" text="Daily Frequency"></spring:message></th>		
											<c:if test="${command.saveMode eq 'E'}">
												<th class="col-sm-1" style="text-align: left"><spring:message
														code="SFT.status" text="Status"></spring:message></th>
											</c:if>
											<th class="col-sm-2" style="text-align: center"><button
													type="button" class="btn btn-blue-2 btn-sm"
													onclick="addScheduleEntry()" title="Add Schedule Entry">
													<i class="fa fa-plus-circle"></i>

												</button></th>

										</tr>
									</thead>

									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.cfcCollectionMasterDto.
															cfcCounterMasterDtos[index].cfcCounterScheduleDtos)>0}">
												<c:forEach
													items="${command.cfcCollectionMasterDto.
																cfcCounterMasterDtos[index].cfcCounterScheduleDtos}"
													var="asset">
													<tr class="scheduleEntryRow">
														<td><div class="input-group">
																<form:input
																	path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].csFromTime"
																	id="fromDate${c}"
																	class="form-control mandColorClass fromDate"
																	onchange="" />
																<label class="input-group-addon"><i
																	class="fa fa-calendar"></i><span class="hide"> <spring:message
																			code="" text="icon" /></span><input type="hidden"></label>
															</div></td>


														<td><div class="input-group">
																<form:input
																	path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].csToTime"
																	id="endDate${c}"
																	class="form-control mandColorClass toDate"
																	onchange="" />
																<label class="input-group-addon"><i
																	class="fa fa-calendar"></i><span class="hide"> <spring:message
																			code="" text="icon" /></span><input type="hidden"></label>
															</div></td>
														<td><form:select
																path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].csUserId"
																class="form-control chosen-select-no-results" id="empId${c}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.empList}" var="emp">
																	<form:option value="${emp.empId}">${emp.empname}</form:option>
																</c:forEach>
															</form:select></td>
															
														<td><form:select
																path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].frequencySts"
																class="form-control" id="frequencySts${c}">
																<form:option value="Y">
																	<spring:message code='' text="Yes"/>
																</form:option>
																<form:option value="N">
																	<spring:message code='' text="No"/>
																</form:option>
																
															</form:select></td>
														
															
														<c:if test="${command.saveMode eq 'E'}">
															<td class="text-center"><input type="checkbox"
																id="status${c}" value="" class="selectedRow" /></td>
														</c:if>
														<td class="text-center" width="10"><button
																type="button" class="btn btn-danger btn-sm delButton"
																title="Delete Counter"
																onclick="deleteScheduleEntry($(this),'removedIds');">
																<i class="fa fa-minus"></i>

															</button></td>
														<c:set var="c" value="${c + 1}" scope="page" />
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="scheduleEntryRow">
													<td><div class="input-group">
															<form:input
																path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].csFromTime"
																id="fromDate${c}"
																class="form-control mandColorClass fromDate"
																onchange="" />
															<label class="input-group-addon"><i
																class="fa fa-calendar"></i><span class="hide"> <spring:message
																		code="" text="icon" /></span><input type="hidden"></label>
														</div></td>


													<td><div class="input-group">
															<form:input
																path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].csToTime"
																id="endDate${c}"
																class="form-control mandColorClass toDate"
																onchange="" />
															<label class="input-group-addon"><i
																class="fa fa-calendar"></i><span class="hide"> <spring:message
																		code="" text="icon" /></span><input type="hidden"></label>
														</div></td>
													<td><form:select
															path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].csUserId"
															class="form-control chosen-select-no-results" id="empId${c}">
															<form:option value="">Select</form:option>
															<c:forEach items="${command.empList}" var="emp">
																<form:option value="${emp.empId}">${emp.empname}</form:option>
															</c:forEach>
														</form:select></td>
														
														<td><form:select
																path="cfcCollectionMasterDto.cfcCounterMasterDtos[${index}].cfcCounterScheduleDtos[${c}].frequencySts"
																class="form-control" id="frequencySts${c}">
																<form:option value="Y">
																	<spring:message code='' text="Yes"/>
																</form:option>
																<form:option value="N">
																	<spring:message code='' text="No"/>
																</form:option>
														</form:select></td>
														
													<c:if test="${command.saveMode eq 'E'}">
														<td class="text-center"><input type="checkbox"
															id="status${c}" value="" class="selectedRow" /></td>
													</c:if>
													<td class="text-center" width="10"><button
															type="button" class="btn btn-danger btn-sm delButton"
															title="Delete Counter"
															onclick="deleteScheduleEntry($(this),'removedIds');">
															<i class="fa fa-minus"></i>

														</button></td>
													<c:set var="c" value="${c + 1}" scope="page" />
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</c:if>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C'}">
						<button class="btn btn-success  submit"
							onclick="saveCollectionData(this)" id="Submit" type="button"
							name="button" value="save">
							<i class="button-input"></i>
							<spring:message code="CFC.save" text="Save" />
						</button>
					</c:if>

					<button type="button" class="btn btn-warning" onclick="formForCreate()">
						<spring:message code="NHP.reset" />
					</button>

					<button type="back" class="btn btn-danger" onclick="backForm()">
						<spring:message code="NHP.back" />
					</button>


				</div>
			</form:form>
		</div>
	</div>
</div>
