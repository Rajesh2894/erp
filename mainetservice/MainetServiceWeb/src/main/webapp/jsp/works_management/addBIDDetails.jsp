<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.BID.addDetails" text="Add Details" />
			</h2>

			<div class="additional-btn">
				<apptags:helpDoc url="TenderInitiation.html"></apptags:helpDoc>
			</div>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span> <spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="TenderInitiation.html" class="form-horizontal"
				name="BidMasterDetail" id="BidMasterDetail" modelAttribute="command">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="wms.BID.technicalDetails" text="Technical Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="c" value="0" scope="page"></c:set>
							<table class="table table-bordered table-striped"
								id="technicalDetail">

								<thead>
									<tr>
										<th scope="col" width="3%"><spring:message code="ser.no"
												text="Sr.No." /></th>
										<th width="12%"><spring:message code='wms.BID.paramDesc'
												text="Parameter Description" /></th>
										<th width="8%"><spring:message code='wms.BID.mark'
												text="Mark" /></th>
										<th width="8%"><spring:message code='wms.BID.obtained'
												text="Obtained" /></th>
										<th width="10%"><spring:message code='wms.BID.weighttage'
												text="Weightage %" /></th>
										<th width="10%"><spring:message code='wms.BID.finalMarks'
												text="Final Marks" /></th>
										<%-- <th width="10%"><button type="button"
										onclick='return false;'
										class="btn btn-blue-2 btn-sm addTechDetail"
										id="techDetail${d}">
										<i class="fa fa-plus-circle"></i>
									</button></th> --%>
										<c:if
											test="${fn:length(command.bidMasterDto.technicalBIDDetailDtos)<=0}">
											<th scope="col" width="5%"><a href="javascript:void(0);"
												data-toggle="tooltip" data-placement="top"
												onclick="addTechnicalEntryData(${command.techParamList.size() gt 0});"
												class=" btn btn-success btn-sm"><i
													class="fa fa-plus-circle"></i></a></th>
										</c:if>

									</tr>
								</thead>
								<c:choose>
									<c:when
										test="${fn:length(command.bidMasterDto.technicalBIDDetailDtos)>0}">
										<c:forEach
											items="${command.bidMasterDto.technicalBIDDetailDtos}"
											varStatus="status">
											<tr class="techParamDetailRow">
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${c}"
														value="${c+1}" disabled="true" /></td>
												<td><c:if test="${command.techParamList.size() gt 0}">
														<form:select
															path="bidMasterDto.technicalBIDDetailDtos[${c}].paramDescId"
															id="paramDescId${c}" cssClass="form-control"
															onchange="getLongValueForTechParam(${c})">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.techParamList}"
																var="techParam">
																<form:option value="${techParam}">${techParam}</form:option>
															</c:forEach>
														</form:select>
													</c:if> <c:if test="${empty command.techParamList}">
														<form:input
															path="bidMasterDto.technicalBIDDetailDtos[${c}].paramDescId"
															id="paramDescId${c}" cssClass="form-control" />
													</c:if></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].mark"
														id="mark${c}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].obtained"
														id="obtained${c}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].weightage"
														cssClass="form-control text-right " id="weightage${c}"
														onkeypress="return hasAmount(event, this, 3, 2)"
														onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
														onkeyup="getTotalAmount()" /></td>

												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].finalMark"
														id="finalMark${c}" cssClass="form-control hasNumber" /></td>
												<!-- <td class="text-center"><a href="javascript:void(0);"
													class="btn btn-danger btn-sm delButton" id="deleteBtn"
													onclick=""><i class="fa fa-minus"></i></a></td> -->

											</tr>
											<c:set var="c" value="${c + 1}" scope="page" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tbody>
											<tr class="techParamDetailRow">
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${c}"
														value="${c+1}" disabled="true" /></td>
												<td><c:if test="${command.techParamList.size() gt 0}">
														<form:select
															path="bidMasterDto.technicalBIDDetailDtos[${c}].paramDescId"
															id="paramDescId${c}" cssClass="form-control"
															onchange="getLongValueForTechParam(${c})">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.techParamList}"
																var="techParam">
																<form:option value="${techParam}">${techParam}</form:option>
															</c:forEach>
														</form:select>
													</c:if> <c:if test="${empty command.techParamList}">
														<form:input
															path="bidMasterDto.technicalBIDDetailDtos[${c}].paramDescId"
															id="paramDescId${c}" cssClass="form-control" />
													</c:if></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].mark"
														id="mark${c}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].obtained"
														id="obtained${c}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].weightage"
														cssClass="form-control text-right " id="weightage${c}"
														onkeypress="return hasAmount(event, this, 3, 2)"
														onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
														onkeyup="getTotalAmount()" /></td>

												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${c}].finalMark"
														id="finalMark${c}" cssClass="form-control hasNumber" /></td>
												<td class="text-center"><a href="javascript:void(0);"
													class="btn btn-danger btn-sm delButton" id="deleteBtn"
													onclick="deleteTechEntry($(this),'removedIds');"><i
														class="fa fa-minus"></i></a></td>

												<c:set var="c" value="${c + 1}" scope="page" />
											</tr>
										</tbody>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="wms.BID.commercialDetails" text="Commercial Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-bordered table-striped"
								id="commercialDetail">

								<thead>
									<tr>
										<th scope="col" width="3%"><spring:message code="ser.no"
												text="Sr.No." /></th>
										<th width="14%"><spring:message code='wms.BID.paramDesc'
												text="Parameter Description" /></th>
										<th width="7%"><spring:message
												code='wms.BID.baseRateAsPerTender'
												text="Base Rate as per Tender" /></th>
										<th width="7%"><spring:message code='wms.BID.quotedPrice'
												text="Quoted Price" /></th>
										<th width="8%"><spring:message code='wms.BID.mark'
												text="Mark" /></th>
										<th width="8%"><spring:message code='wms.BID.obtained'
												text="Obtained" /></th>
										<th width="8%"><spring:message code='wms.BID.weighttage'
												text="Weightage %" /></th>
										<th width="8%"><spring:message code='wms.BID.finalMarks'
												text="Final Marks" /></th>
										<!-- <th width="10%"><button type="button"
										class="btn btn-blue-2 btn-sm"
										onclick="addCommercialEntryData();">
										<i class="fa fa-plus-circle"></i>
									</button></th> -->
										<c:if
											test="${fn:length(command.bidMasterDto.commercialBIDDetailDtos)<=0}">
											<th scope="col" width="5%"><a href="javascript:void(0);"
												data-toggle="tooltip" data-placement="top"
												onclick="addCommercialEntryData(${command.commParamList.size() gt 0});"
												class=" btn btn-success btn-sm"><i
													class="fa fa-plus-circle"></i></a></th>
										</c:if>
									</tr>
								</thead>

								<c:choose>
									<c:when
										test="${fn:length(command.bidMasterDto.commercialBIDDetailDtos)>0}">
										<c:forEach
											items="${command.bidMasterDto.commercialBIDDetailDtos}"
											varStatus="status">
											<tr class="commercialEntry">
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" disabled="true" /></td>
												<td><c:if test="${command.commParamList.size() gt 0}">
														<form:select
															path="bidMasterDto.commercialBIDDetailDtos[${d}].paramDescId"
															id="commParamDescId${d}" cssClass="form-control"
															onchange="getLongValueforCommParam(${d})">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.commParamList}"
																var="commParam">
																<form:option value="${commParam}">${commParam}</form:option>
															</c:forEach>
														</form:select>
													</c:if> <c:if test="${empty command.commParamList}">
														<form:input
															path="bidMasterDto.commercialBIDDetailDtos[${d}].paramDescId"
															id="commParamDescId${d}" cssClass="form-control" />
													</c:if></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].baseRate"
														id="baseRate${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].quotedPrice"
														id="quotedPrice${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].mark"
														id="mark${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].obtained"
														id="obtained${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].weightage"
														id="weightage${d}" cssClass="form-control"
														onkeypress="return hasAmount(event, this, 3, 2)"
														onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
														onkeyup="getTotalAmount()" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].finalMark"
														id="finalMark${d}" cssClass="form-control hasNumber" /></td>
												<!-- <td class="text-center"><a href="javascript:void(0);"
													class="btn btn-danger btn-sm delButton" id="deleteBtn"
													onclick=""><i class="fa fa-minus"></i></a></td> -->
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tbody>
											<tr class="commercialEntry">
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" disabled="true" /></td>
												<td><c:if test="${command.commParamList.size() gt 0}">
														<form:select
															path="bidMasterDto.commercialBIDDetailDtos[${d}].paramDescId"
															id="commParamDescId${d}" cssClass="form-control"
															onchange="getLongValueforCommParam(${d})">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.commParamList}"
																var="commParam">
																<form:option value="${commParam}">${commParam}</form:option>
															</c:forEach>
														</form:select>
													</c:if> <c:if test="${empty command.commParamList}">
														<form:input
															path="bidMasterDto.commercialBIDDetailDtos[${d}].paramDescId"
															id="paramDescId${d}" cssClass="form-control" />
													</c:if></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].baseRate"
														id="baseRate${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].quotedPrice"
														id="quotedPrice${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].mark"
														id="mark${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].obtained"
														id="obtained${d}" cssClass="form-control hasNumber" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].weightage"
														id="weightage${d}" cssClass="form-control"
														onkeypress="return hasAmount(event, this, 3, 2)"
														onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
														onkeyup="getTotalAmount()" /></td>
												<td><form:input
														path="bidMasterDto.commercialBIDDetailDtos[${d}].finalMark"
														id="finalMark${d}" cssClass="form-control hasNumber" /></td>
												<td class="text-center"><a href="javascript:void(0);"
													class="btn btn-danger btn-sm delButton" id="deleteBtn"
													onclick="deleteEntry($(this),'removedIds');"><i
														class="fa fa-minus"></i></a></td>
												<c:set var="d" value="${d + 1}" scope="page" />
											</tr>
										</tbody>
									</c:otherwise>
								</c:choose>

							</table>
						</div>
					</div>
				</div>
				<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="swm.fileupload" /> </label>
				<div class="col-sm-4">

					<apptags:formField fieldType="7" labelCode="" hasId="true"
						fieldPath="bidMasterDto.ternitDoc[0]" isMandatory="false" showFileNameHTMLId="true"
						fileSize="CARE_COMMON_MAX_SIZE"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CARE_VALIDATION_EXTENSION"
						currentCount="500" />
					<small class="text-blue-2"> <spring:message
							code="social.checklist.tooltip" text="(Upload Image File upto 1 MB)" />
				</div>
			</div>
				<div class="text-center clear padding-10">

					<c:if test="${command.bidMode ne 'V'}">
						<button class="btn btn-success add"
							onclick="saveBIDDetails(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.bidMode eq 'A'}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="showBIDForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />
						</button>
					</c:if>
					<c:if test="${command.bidMode ne 'A'}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="showBIDDetailsForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />
						</button>
					</c:if>
				</div>

			</form:form>

		</div>
		<!-- End Widget Content here -->
	</div>
</div>
<!-- End of Content -->