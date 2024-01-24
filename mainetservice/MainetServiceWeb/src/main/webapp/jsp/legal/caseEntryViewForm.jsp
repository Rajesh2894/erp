<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- CASE ENTRY PAGE -->
			
			<div class="form-group">
					<apptags:input labelCode="caseEntryDTO.cseName"
						path="caseEntryDTO.cseName" isMandatory="true"
						cssClass="hasNameClass form-control"
						isDisabled="${true }"></apptags:input>
						
						<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.cseDeptid" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.cseDeptid" id="cseDeptid"
							disabled="${true }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="department">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">

					<apptags:input labelCode="caseEntryDTO.cseSuitNo"
						path="caseEntryDTO.cseSuitNo" isMandatory="true"
						cssClass="alphaNumeric form-control "
						isDisabled="${true }"></apptags:input>

					<apptags:input labelCode="caseEntryDTO.cseRefsuitNo"
						path="caseEntryDTO.cseRefsuitNo" isMandatory="true"
						cssClass="alphaNumeric form-control"
						isDisabled="${true }"></apptags:input>

				</div>
				
				
				<div class="form-group">
				
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.cseTypId" text="Case Type" /></label>
				
						<c:set var="baseLookupCode" value="TOC" />  
						<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseTypId"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${true }" />

					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.csePeicDroa" text="Organisation As" /></label>
					<c:set var="baseLookupCode" value="OZA" />  <!-- OZA -->
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.csePeicDroa"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${true }" />

				</div>
				<div class="form-group">
						
						<apptags:lookupFieldSet baseLookupCode="CCT" hasId="true"
						showOnlyLabel="false" pathPrefix="caseEntryDTO.cseCatId"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control margin-bottom-10"
						showAll="false" columnWidth="20%" 
						disabled="${true }"/>


				</div>
				
				
				<div class="form-group">

					<apptags:date labelCode="caseEntryDTO.cseDate"
						datePath="caseEntryDTO.cseDate" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker"
						isDisabled="${true }"></apptags:date>

					<apptags:date labelCode="caseEntryDTO.cseEntryDt"
						datePath="caseEntryDTO.cseEntryDt" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker"
						isDisabled="${true }"></apptags:date>

				</div>
				
				<div class="form-group">

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.crtId" text="Court Name"/>
					</label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select
							class=" mandColorClass form-control"   
							path="caseEntryDTO.crtId" id="crtId"
							disabled="${true }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${courtMasterDTOList}" var="courtMasterDTO">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
					
					<apptags:input labelCode="caseEntryDTO.cseMatdet1"
						path="caseEntryDTO.cseMatdet1" isMandatory="true"
						cssClass="hasNameClass form-control"
						isDisabled="${true}" />
					
						<%-- 	<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.locId" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.locId" id="locId"
							disabled="${true }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${locations}" var="location">
								<form:option value="${location.locId}">${location.locName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>	

				</div>
				
				
				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for=""><spring:message
							code="caseEntryDTO.cseState" text="State" /></label>
					<c:set var="baseLookupCode" value="STT" />
					<!-- STT -->
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseState"
						cssClass="mandColorClass form-control" hasChildLookup="false"
						hasId="true" showAll="false"
						selectOptionLabelCode="selectdropdown" showOnlyLabel="State"
						disabled="${true }" />

					<apptags:input labelCode="caseEntryDTO.cseCity"
						path="caseEntryDTO.cseCity" isMandatory="true"
						cssClass="form-control" maxlegnth="250"
						isDisabled="${true}" />

				</div>
				

				
					<div class="form-group">

					<%-- <apptags:input labelCode="caseEntryDTO.cseSectAppl"
						path="caseEntryDTO.cseSectAppl" isMandatory="true"
						cssClass="hasNameClass form-control"
						isDisabled="${true}" />
 --%>
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.cseCaseStatusId" text="Case Status" /></label>
					<c:set var="baseLookupCode" value="CSS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseCaseStatusId"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${true}" />
						
						
					<apptags:input labelCode="caseEntryDTO.cseRemarks"
						path="caseEntryDTO.cseRemarks" isMandatory="true"
						cssClass="hasNameClass form-control"
						isDisabled="${true}" />

				</div>
				
					<!-- CASE ENTRY END  -->
				<!-- CASE Document Start  -->
				
				<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="legal.case.doc" text="Case Documents Uploading" /></a>
						</h4>
				
					</div>
		
						<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">

							
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteCommonDoc1">
										<tr>
											<th width="" align="center"><spring:message
													code="ser.no" text="" /><input type="hidden" id="srNo"></th>
											<th scope="col" width="64%" align="center"><spring:message
													code="work.estimate.document.description"
													text="Document Description" /></th>
											<th scope="col" width="30%" align="center"><spring:message
													code="scheme.view.document" /></th>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.caseAttachDocsList}" var="lookUp">
											<tr>
												<td>${e+1}</td>
												<td>${lookUp.dmsDocName}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="CaseEntry.html?Download" /></td>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</table>
								</div>
								<br>
							
						</div>
					</div>
				</div>
			</div>

<!-- #117248 -->
<script>
	var cseDate = $('#cseDate').val();
	var cseEntryDt = $('#cseEntryDt').val();
	if (cseDate) {
		$('#cseDate').val(cseDate.split(' ')[0]);
	}
	if (cseEntryDt) {
		$('#cseEntryDt').val(cseEntryDt.split(' ')[0]);
	}
</script>