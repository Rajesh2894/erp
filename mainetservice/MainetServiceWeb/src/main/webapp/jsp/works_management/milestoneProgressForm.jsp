<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/milestoneProgress.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
#uploadPreview ul li {
	display: inline-block !important;
}

#uploadPreview .img-thumbnail {
	display: block !important
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.ProgressUpdateSummary"
					text="Progress Update Summary" />
			</h2>
			<div class="additional-btn">
			     <apptags:helpDoc url="MilestoneProgress.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="MilestoneProgress.html" class="form-horizontal"
				id="milestoneProgress" name="milestoneProgress">
				<form:hidden path="mileStoneDTO.projId" id="projId" />
				<form:hidden path="mileStoneDTO.workId" id="workId" />
				<form:hidden path="mileStoneDTO.mileId" id="mileId" />
				<form:hidden path="length" id="length" />
				<form:hidden path="" id="startDate"
					value="${command.mileStoneDTO.msStartDate}" />
				<form:hidden path="" id="endDate"
					value="${command.mileStoneDTO.msEndDate}" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div>
					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="tab">
							<tr>
								<th scope="col" width="5%" align="center"><spring:message
										code="ser.no" text="Sr. No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.MilestoneDescription" text="Milestone Description" />
								<th scope="col" width="18%" align="center"><spring:message
										code="wms.MilestoneWeightage" text="Milestone Weightage" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="wms.MilestoneStartDate" text="Milestone Start Date" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.MilestoneEnddate" text="Milestone End date" /></th>
							</tr>


							<tr>
								<td class="text-center"><form:input id="totalWeightage"
										class=" form-control text-right" value="1" path=""
										readonly="true" /></td>
								<td class="text-center">${command.mileStoneDTO.mileStoneDesc}</td>
								<td class="text-center">${command.mileStoneDTO.mileStoneWeight}</td>
								<td class="text-center">${command.mileStoneDTO.msStartDate}</td>
								<td class="text-center">${command.mileStoneDTO.msEndDate}</td>
							</tr>


						</table>
					</div>
				</div>

				<div id="uploadTagDiv">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page" />
						<table class="table table-bordered table-striped" id="attachDoc">
							<tr>
								<th width="20%"><spring:message
										code="wms.ProgressUpdateDate" text="Progress Update Date" /></th>
								<th width="20%"><spring:message
										code="wms.PhysicalProgressPercent" text="Physical Progress %" /></th>
								<th width="20%"><spring:message code="wms.UploadImages"
										text="Upload Images" /></th>
								<th width="32%"><spring:message code="wms.UploadedImages"
										text="Uploaded Images" /></th>
								<th scope="col" width="8%"><a
									title='<spring:message code="works.management.add" />'
									onclick='fileCountUpload(this);'
									class="btn btn-blue-2 btn-sm addButton"> <i
										class="fa fa-plus-circle"></i></a></th>
							</tr>
							<tbody>
								<c:choose>
									<c:when test="${empty command.progressList}">


										<tr class="appendableClass">
											<td><form:input path="progressList[${d}].proUpdateDate"
													class="form-control mandColorClass datepicker text-center"
													id="proUpdateDate${d}" /></td>
											<td><form:input path="progressList[${d}].phyPercent"
													class=" form-control mandColorClass text-right numbersOnly"
													onkeypress="return hasAmount(event, this, 3, 2)"
													onchange="getAmountFormatInDynamic((this),'phyPercent')"
													id="phyPercent${d}" /></td>
											<td class="text-center"><apptags:formField fieldType="7"
													fieldPath="attachments[${d}].uploadedDocumentPath"
													currentCount="${d}" showFileNameHTMLId="true"
													folderName="${d}" fileSize="ECHALLAN_MAX_SIZE"
													isMandatory="false"
													maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
													validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
													callbackOtherTask="otherTask();">
												</apptags:formField>
												<small class="text-blue-2"> <spring:message
												code="milestone.progress.UploadFile"
				            					text="(Upload Image File upto 50 MB and Only Jpg,Jpeg,Png,Gif,Bmp Extension(S) File(S) are allowed.)" />
					    						</small>
												</td>

											<td><div id="uploadPreview">
													<c:forEach items="${command.fileNames}" var="entry">
														<c:if test="${entry.key eq d}">

															<ul>
																<c:forEach items="${entry.value}" var="val">
																	<li id=""><img src="${val}" width="100"
																		height="100" class="img-thumbnail"><a
																		href="${val}" download><i class="fa fa-download"></i></a></li>
																</c:forEach>
															</ul>

														</c:if>
													</c:forEach>
												</div></td>
											<td class="text-center"><a href='#' id="file_list_${d}"
												title='<spring:message code="works.management.delete" />'
												class='btn btn-danger btn-sm delButton'
												onclick="doFileDeletion($(this),${d});"><i
													class="fa fa-trash"></i></a></td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />

									</c:when>
									<c:otherwise>
										<c:forEach var="count" items="${command.progressList}">
											<tr class="appendableClass">
												<td><form:input path="progressList[${d}].proUpdateDate"
														class="form-control mandColorClass datepicker text-center"
														id="proUpdateDate${d}" /></td>
												<td><form:input path="progressList[${d}].phyPercent"
														class=" form-control mandColorClass text-right numbersOnly"
														onkeypress="return hasAmount(event, this, 3, 2)"
														onchange="getAmountFormatInDynamic((this),'phyPercent')"
														id="phyPercent${d}" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="ECHALLAN_MAX_SIZE"
														isMandatory="false"
														maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
														validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
														callbackOtherTask="otherTask();">
													</apptags:formField>
													<small class="text-blue-2"> <spring:message
													code="milestone.progress.UploadFile"
				            						text="(Upload Image File upto 50 MB and Only pdf,doc,docx,xls,xlsx extension(s) file(s) are allowed.)" />
					   								 </small>
													</td>

												<td><div id="uploadPreview">
														<c:forEach items="${command.fileNames}" var="entry">
															<c:if test="${entry.key eq d}">

																<ul>
																	<c:forEach items="${entry.value}" var="val">
																		<li id=""><img src="${val}" width="100"
																			height="100" class="img-thumbnail"><a
																			href="${val}" download><i class="fa fa-download"></i></a></li>
																	</c:forEach>
																</ul>

															</c:if>
														</c:forEach>
													</div></td>
												<td class="text-center"><a href='#' id="file_list_${d}"
													title='<spring:message code="works.management.delete" />'
													class='btn btn-danger btn-sm delButton'
													onclick="doFileDeletion($(this),${d});"><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />

										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="works.management.save" text="Save" />'
						onclick="saveProgress(this);">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.save" text="Save" />
					</button>
					<button type="button" class="button-input btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='MilestoneProgress.html'"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>