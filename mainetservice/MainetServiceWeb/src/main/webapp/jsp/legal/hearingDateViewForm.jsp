<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${not empty command.hearingEntry}">
	<c:if test="${command.caseEntryDTO.noteShowFlag eq 'Y' && command.saveMode eq 'E'}">
		<div>
			<h5><strong><i><spring:message code="lgl.hearingDetail.editNote" /></i></strong></h5>
		</div>
	</c:if>

	<div class="table-responsive clear">
	<table id="hearingDateTable" summary="HEARING DATA"  class="table table-bordered table-striped">
		<thead>
			<tr>
				<th><spring:message code="label.checklist.srno" text="Sr.No."/></th>
				<th><spring:message code="CaseHearingDTO.hrDate" text="Hearing Date"/></th>
				<th><spring:message code="CaseHearingDTO.hrStatus" text="Status"/></th>
				<th><spring:message code="CaseHearingDTO.hrPreparation" text="Preparation"/></th>
				<th><spring:message code="CaseHearingDTO.docUplod" text="Upload Documents"/></th>
				<th><spring:message code="lgl.action" text="Action"/></th>
			</tr>
		</thead>
			<tbody>
				<c:forEach items="${command.hearingEntry}" var="data" varStatus="index">
					<c:set var="hrId" value="${data.hrId}" />
    				<c:set var="attachDocs" value="${command.attachDocsMap[hrId]}" />
					<tr class="hearingClass">
						<td class="text-center" ><form:input path=""
												cssClass="form-control mandColorClass "
												id="sequence${index.index}" value="${index.count}" disabled="true" /></td>
						<td class="text-center" >
							<form:input path="hearingEntry[${index.index}].hrDate" class="form-control datepicker" value="" id="hrDate${index.index}" disabled="true"/>
						</td>
						<td class="text-center" >
							<c:set var="baseLookupCode" value="HSC" />  
							<form:select path="hearingEntry[${index.index}].hrStatus"
								cssClass="form-control mandColorClass" id="hrStatus${index.index}"
								onchange="" disabled="true"
								data-rule-required="true">
								<form:option value="0">
									<spring:message code="selectdropdown" text="select" />
								</form:option>
								<c:forEach items="${command.getSortedLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</td>
						
						<td class="text-center" >
							<form:input path="hearingEntry[${index.index}].hrPreparation" class="form-control" id="hrPreparation${index.index}" disabled="true"/>
						</td>
						<td class="text-center" >
						<c:if test="${not empty attachDocs}">
											
												<!-- File exists in AttachdocsMap, so display download link -->
												<apptags:filedownload filename="${attachDocs.attFname}"
													filePath="${attachDocs.attPath}"
													actionUrl="HearingDate.html?Download" />
											</c:if> <c:if
												test="${command.saveMode ne 'V' && empty attachDocs}">
												<!-- File doesn't exist in AttachdocsMap and not in view mode, so display file upload field -->
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="hearingEntry[${index.index}].file"
													isMandatory="false" showFileNameHTMLId="true"
													fileSize="WORK_COMMON_MAX_SIZE"
													maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CARE_VALIDATION_EXTENSION"
													checkListMandatoryDoc="${attachDocs.checkkMANDATORY}"
													checkListDesc="${docName}" currentCount="${index.index}" />
											</c:if>
						</td>
						<td class="text-center" width="10%">
							<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.hrStatus)"
											var="lookup" />
											
											
							<button type="button" class="btn btn-blue-2 btn-sm"
							onClick="getData(${data.hrId},'HV','HearingDetails.html?HearingDate')"
							data-original-title="View" title="<spring:message
									code="solid.waste.view" text="View"></spring:message>">
							<strong class="fa fa-eye"></strong><span class="hide"><spring:message
									code="solid.waste.view" text="View"></spring:message></span>
							</button>
							
							<c:if test="${command.saveMode eq 'E'}">
							<c:if test="${lookup.lookUpCode eq  'SH' ||  command.hearingEntry.size() == index.count }">
							<c:if test="${command.orgFlag ne 'Y'}">
							<button type="button" class="btn btn-warning btn-sm" 
								onClick="getData(${data.hrId},'HE','HearingDetails.html?HearingDate')"
								data-original-title="Edit" title="<spring:message
										code="solid.waste.edit" text="Edit"></spring:message>">
								<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
										code="solid.waste.edit" text="Edit"></spring:message></span>
							</button>
							</c:if>
							<c:if test="${command.orgFlag eq 'Y'}">
							<button type="button" class="btn btn-warning btn-sm"
								onClick="getData(${data.hrId},'HE','HearingDetails.html?HearingDate')"
								data-original-title="Edit" title="<spring:message
										code="solid.waste.edit" text="Edit"></spring:message>" disabled="disabled">
								<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
										code="solid.waste.edit" text="Edit"></spring:message></span>
							</button>
							</c:if>
							</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
</c:if>
