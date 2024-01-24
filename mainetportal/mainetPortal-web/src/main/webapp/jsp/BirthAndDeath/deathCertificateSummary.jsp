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
				<h2>
					<spring:message code="Issuance.death.certificate" text="Issue Death Certificate" />
				</h2>
				<apptags:helpDoc url="IssuanceDeathCertificate.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="frmDeathRegistrationCorrection"
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
						<div class="form-group" align="center" style="display:none"><spring:message code="birth.death.or" text="OR" /></div>
						
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
						maxlegnth="4" isMandatory="true">
					</apptags:input>
						
					<apptags:input labelCode="TbDeathregDTO.drRegno" maxlegnth="20"
						path="tbDeathregDTO.drRegno" cssClass="hasNumClass form-control" isMandatory="true">
					</apptags:input>
				 </div>
				
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
					
					<label class="col-sm-2 control-label required-control">
						<spring:message code="TbDeathregDTO.drDod" text="Date of Death" />
					</label>
					<div class="col-sm-4">
					    <div class="input-group">
					        <form:input path="tbDeathregDTO.drDod"
					            cssClass="form-control mandColorClass" id="drDod"
					          />
					        <div class="input-group-addon">
					            <i class="fa fa-calendar"></i>
					        </div>
					    </div>
					</div>
				<%-- <apptags:date fieldclass="datepicker" 
							labelCode="TbDeathregDTO.drDod" datePath="tbDeathregDTO.drDod">
						</apptags:date> --%>
						
					<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
						path="tbDeathregDTO.drDeceasedname" 
						cssClass="hasNameClass form-control">
					</apptags:input>
				 </div>
				
				

					
					<div class="text-center">
						<button type="button" class="btn btn-blue-3" title="Search"
							onclick="SearchDeathCertificateData()">
							<i class="fa padding-left-4" aria-hidden="true"></i>
							<spring:message code="BirthRegDto.search"/>
						</button>
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Reset"
							onclick="window.location.href ='IssuanceDeathCertificate.html'">
							<i class="fa padding-left-4" aria-hidden="true"></i>
							<spring:message code="BirthRegDto.reset"/>
						</button>
						<apptags:backButton url="CitizenHome.html"></apptags:backButton>
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
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.drDod" text="Deceased Date" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drRegno" text="Registration Number" /></th>
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.drDeceasedname" text="Deceased Name" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drSex" text="Sex" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drRelativeName" text="Father Name / Husband Name" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drMotherName" text="Mother Name" /></th>
									<%-- <th width="15%" align="center">
										<spring:message code="TbDeathregDTO.cpdRegUnit" text="Reg. Unit" />
									</th> --%>
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.form.action" text="Action" /></th>
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








 
 
 