<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script type="text/javascript" src="js/birthAndDeath/dataEntryForBirthReg.js"></script> 
<script>

	$(document).ready(function() {
	    var end = new Date();
	    end.setFullYear(2016);
	    $("#brDob").datepicker({
	        dateFormat : 'dd/mm/yy',
	        changeMonth : true,
	         changeYear: true,
	        yearRange: "-200:+200",
	        maxDate : new Date(end.getFullYear(), 11, 31)
	    });
	});


</script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="dataentry.birthRegForm" text="Birth Registration Data Entry Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding" id="">
				<form:form id="birthCorrSumm"
					action="dataEntryForBirthReg.html" method="POST"
					class="form-horizontal" name="birthCorrectionFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="${birthRegDto.brId}"
						cssClass="hasNumber form-control" id="brId" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv">
					</div>

					<h4>
						<spring:message code="dataentry.birthReg" text="Birth Registration" />
					</h4>

					<div class="form-group" style="display:none">
						<apptags:input labelCode="BirthRegDto.brCertNo"
							path="birthRegDto.brCertNo" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="12">
						</apptags:input>

					</div>

					<div class="form-group" align="center" style="display:none"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group padding_top_10 text-red-1" align="center"><spring:message code="birth.data.available" text="" /></div>
				<div class="form-group">
				
				
				<apptags:input labelCode="BirthRegDto.year" path="birthRegDto.year"
						cssClass="hasNumber form-control" maxlegnth="4">
					</apptags:input>
					<apptags:input labelCode="BirthRegDto.brRegNo"
						path="birthRegDto.brRegNo" cssClass="hasNumClass form-control"
						maxlegnth="20">
					</apptags:input>
				
					
				</div>
				
					<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<spring:message code="BirthRegistrationDTO.brDob" text="Date of Birth" />
					</label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <form:input path="birthRegDto.brDob" 
                                cssClass="form-control mandColorClass" id="brDob"
                                readonly="true" />
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                        </div>
                    </div>
						
				<apptags:input labelCode="BirthRegistrationDTO.brChildName"
							path="birthRegDto.brChildName" isMandatory=""
							cssClass="hasNameClass form-control" maxlegnth="400">
						</apptags:input>
				</div>
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
						<label class="control-label col-sm-2 "
							for="Census"> <spring:message
								code="BirthRegistrationDTO.brSex" text="Gender" />
						</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.brSex" cssClass="form-control"
							 hasId="true"
							selectOptionLabelCode="selectdropdown" />
							
						<label class="control-label col-sm-2 "
											for="Census"> <spring:message
												code="BirthRegistrationDTO.hiId" text="Hospital Name" />
										</label>

										<div class="col-sm-4">
											<form:select path="birthRegDto.hiId" 
											  class="form-control chosen-select-no-results"
												id="hospitalList" >
												<form:option value="">
													<spring:message code="Select" text="Select" />
												</form:option>
												<c:forEach items="${command.hospitalMasterDTOList}"
													var="hospList">
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${hospList.hiId}">${hospList.hiNameMar}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</div>
									</div>
					
					<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>

			<div class="form-group">
				<apptags:input labelCode="ParentDetailDTO.pdFathername"
					path="birthRegDto.parentDetailDTO.pdFathername" 
					cssClass="hasNameClass form-control" maxlegnth="100">
				</apptags:input>
				<apptags:input labelCode="ParentDetailDTO.pdMothername"
					path="birthRegDto.parentDetailDTO.pdMothername" 
					cssClass="hasNameClass form-control" maxlegnth="100">
				</apptags:input>
			</div>

			<div class="text-center"> 
						<button type="button" align="center" class="btn btn-success" title="Search"
							onclick="searchBirthData(this)">
							<spring:message code="BirthRegDto.search"/>
						</button>
						<button type="button" align="center" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='dataEntryForBirthReg.html'">
							<spring:message code="BirthRegDto.reset"/>
						</button>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
					
					 <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="dataEntryBirthRegDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.brDob" text="Birth Date" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegDto.brRegNo" text="Registration Number" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.brRegDate" text="Registration Date" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegDto.brChildName" text="Child Name" /></th>		
									<th width="13%" align="center"><spring:message
											code="ParentDetailDTO.pdFathername" text="Father Name(in English)" /></th>
									<th width="12%" align="center"><spring:message
											code="ParentDetailDTO.pdMothername" text="Mother Name(in English)" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.brSex" text="Sex" /></th>
									<th width="10%" align="center">
										<spring:message code="TbDeathregDTO.cpdRegUnit" text="Registration Unit" />
									</th>
									<th width="10%" align="center"><spring:message
									code="TbDeathregDTO.form.action" text="Action" /></th>
									</tr>
									</thead>
									<tbody>
							<c:forEach items="${registrationDetail}" var="birth" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
									<td>${birth.brDob}</td>
									<td>${birth.brRegNo}</td>
									<td>${birth.brChildName}</td>
									<td>${birth.brSex}</td>			
									<td>${birth.cpdRegUnit}</td>
									
									<td class="text-center">
									<button type="button" class="btn btn-blue-3 btn-sm"
										title="Issuance BirthCertificate Form"
											onclick="modifybirth('${BirthRegDto.brId}''','IssuanceBirthCertificate','editBND','A')">
													<i class="fa fa-building-o"></i>
													</button>
									<button type="button" class="btn btn-blue-2 btn-sm"
												title="View CourtDetails"
												onclick="modifybirth(${BirthRegDto.brId},'BirthCorrectionForm.html','editBND','V')">
												<i class="fa fa-eye"></i>
											</button>
									<button type="button" class="btn btn-warning btn-sm"
										title="Edit birth registration Form"
											onclick="modifybirth('${BirthRegDto.brId}''','BirthCorrectionForm.html','editBND','E')">
													<i class="fa fa-pencil"></i>
													</button>
													
									</td>
								</tr>
							</c:forEach>
							</tbody>
	
						</table>
					</div>
					
					</form:form>
					</div>
			</div>
		</div>
	</div>
