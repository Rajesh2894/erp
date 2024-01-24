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
<script type="text/javascript" src="js/birthAndDeath/deathRegistrationCorrection.js"></script>
<script type="text/javascript" src="js/birthAndDeath/IssuenceBirthCertificate.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>

	$(document).ready(function() {
	    var end = new Date();
	    end.setFullYear(2016);
	    $("#drDod").datepicker({
	        dateFormat : 'dd/mm/yy',
	        changeMonth : true,
	         changeYear: true,
	        yearRange: "-200:+200",
	        maxDate : new Date(end.getFullYear(), 11, 31)
	    });
	});


</script>

<html>


<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
			<c:if test="${command.actionViewFlag ne 'Y'}">
				<h2>
					<spring:message code="Issuance.death.certificate" text="Issuance of Death Certificate" />
				</h2></c:if>
				<c:if test="${command.actionViewFlag eq 'Y'}">
				<h2>
					<spring:message code="death.reg.search" text="Death Registration Search" />
				</h2></c:if>
				<!-- <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div> -->
				<apptags:helpDoc url="IssuanceDeathCertificate.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="frmDeathcertSummm"
					action="IssuanceDeathCertificate.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="${deathRegDTO.drId}"
						cssClass="hasNumber form-control" id="drId" />

					<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>

					<%-- <h4>
						<spring:message code="TbDeathregDTO.deathCorr" text="Death Correction" />
					</h4> --%>
					<div class="form-group padding_top_10 text-red-1" align="center"><spring:message code="death.data.available" text="" /></div>

					<div class="form-group" style="display:none">
						<apptags:input labelCode="BirthRegDto.brCertNo"
							path="tbDeathregDTO.drCertNo" isMandatory="false"
							cssClass="hasNumClass form-control" maxlegnth="12">
						</apptags:input>
					
					</div>
						<div class="form-group" align="center" style="display:none">
						<spring:message code="birth.death.or" text="OR" />
						</div>
						
					<%-- <div class="form-group"> 
						<apptags:input labelCode="BirthRegDto.applnId"
							path="tbDeathregDTO.applnId" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="20">
						</apptags:input>
					</div>
					
					<div class="form-group" align="center">OR</div> --%>
					<div class="form-group">
					<apptags:input labelCode="BirthRegDto.year"
						path="tbDeathregDTO.year" cssClass="hasNumber form-control"
						maxlegnth="4">
					</apptags:input>
						
					<apptags:input labelCode="TbDeathregDTO.drRegno"
						path="tbDeathregDTO.drRegno" cssClass="hasNumClass form-control">
					</apptags:input>
				 </div>
				
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
					
					<label class="col-sm-2 control-label">
						<spring:message code="TbDeathregDTO.drDod" text="Date of Death" />
					</label>
					<div class="col-sm-4">
					    <div class="input-group">
					        <form:input path="tbDeathregDTO.drDod"
					            cssClass="form-control mandColorClass" id="drDod"
					            readonly="true" />
					        <div class="input-group-addon">
					            <i class="fa fa-calendar"></i>
					        </div>
					    </div>
					</div>
				
					<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
						path="tbDeathregDTO.drDeceasedname" 
						cssClass="hasNameClass form-control">
					</apptags:input>
				 </div>
				
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
						<label class="control-label col-sm-2 "
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
						</label>
						<c:set var="baseLookupCode" value="DCA" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdDeathcauseId" cssClass="form-control"
							 hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 "
							for="Census"> <spring:message code="TbDeathregDTO.drSex"
								text="Gender" />
						</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.drSex" cssClass="form-control"
							 hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					
					<div class="text-center">
						<button type="button" class="btn btn-success" title="Search"
							onclick="SearchDeathCertificateData(this)">
							<spring:message code="BirthRegDto.search"/>
						</button>
						<c:if test="${command.actionViewFlag ne 'Y'}">
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Reset"
							onclick="window.location.href ='IssuanceDeathCertificate.html'">
							<spring:message code="BirthRegDto.reset"/>
						</button></c:if>
						<c:if test="${command.actionViewFlag eq 'Y'}">
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='IssuanceDeathCertificate.html?deathRegSearch'">
							<spring:message code="BirthRegDto.reset"/>
						</button></c:if>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
					
			<%-- 	 <c:if test="${not empty command.tbDeathRegDtoList}">  --%>
						
						<!-- grid search -->
					
					 <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="deathCorrDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drDod" text="Deceased Date" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drRegno" text="Registration Number" /></th>
									<th width="10%" align="center">
										<spring:message code="TbDeathregDTO.drRegdate" text="Registration Date" />
									</th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drDeceasedname" text="Deceased Name" /></th>
									<th width="10%" align="center"><spring:message
											code="TbDeathregDTO.drSex" text="Sex" /></th>
									<th width="10%" align="center">
										<spring:message code="TbDeathregDTO.cpdRegUnit" text="Reg. Unit" />
									</th>
									<c:if test="${command.actionViewFlag ne 'Y'}">
									<th width="5%" align="center"><spring:message
											code="TbDeathregDTO.form.action" text="Action" /></th></c:if>
								</tr>
							</thead>
							
							<%-- <tbody>
							<c:forEach items="${command.tbDeathRegDtoList}" var="deathRegDTO" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
									<td>${deathRegDTO.drDod}</td>
									<td>${deathRegDTO.drRegno}</td>
									<td>${deathRegDTO.drDeceasedname}</td>
									<td>${deathRegDTO.drSex}</td>			
									<td>${deathRegDTO.cpdRegUnit}</td>
									
									<td class="text-center">
									<button type="button" class="btn btn-blue-3 btn-sm"
										title="Issuance DeathCertificate Form"
											onclick="modifyDeath('${deathRegDTO.drId}''','IssuanceDeathCertificate','editBND','A')">
													<i class="fa fa-building-o"></i>
													</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Death registration Form"
												onclick="modifyDeath('${deathRegDTO.drId}','DeathRegistrationCorrection.html','editBND','E')">
												<i class="fa fa-pencil"></i>
											</button> 
											
									</td>
								</tr>
							</c:forEach>
							</tbody> --%>
							
						</table>
					</div>
					<%-- </c:if> --%>
			</form:form>

			</div>

		</div>

	</div>
</div>


</html>








 
 
 