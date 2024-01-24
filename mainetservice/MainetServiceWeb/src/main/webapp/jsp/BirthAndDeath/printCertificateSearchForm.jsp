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
<script type="text/javascript" src="js/birthAndDeath/printCertificate.js"></script>
<script type="text/javascript" src="js/common/dsc.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>

	$(document).ready(function() {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,

		});
	});

</script>



<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthRegDto.printCertFrm" text="Print Certificate Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="PrintCertificate"
					action="PrintCertificate.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4>
						<spring:message code="BirthRegDto.printCert" text="Print certificate" />
					</h4>
<%-- 
					<div class="form-group">
						<apptags:input labelCode="Certificate No."
							path="tbDeathregDTO.drCertNo" isMandatory="false"
							cssClass="hasNumClass form-control" maxlegnth="12">
						</apptags:input>
					
					</div>
						<div class="form-group" align="center">OR</div> --%>
						
					<div class="form-group"> 
						<apptags:input labelCode="BirthRegDto.applnId"
							path="tbDeathregDTO.applnId" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="10">
						</apptags:input>
					</div>
					
					<%-- <div class="form-group" align="center">OR</div>
					<div class="form-group">
					<apptags:input labelCode="Year"
						path="tbDeathregDTO.year" cssClass="hasNumber form-control"
						maxlegnth="4">
					</apptags:input>
						
					<apptags:input labelCode="TbDeathregDTO.drRegno"
						path="tbDeathregDTO.drRegno" cssClass="hasNumClass form-control">
					</apptags:input>
				 </div> --%>
				
				<%-- <div class="form-group" align="center">OR</div>
					<div class="form-group">
				<apptags:date fieldclass="datepicker" 
							labelCode="TbDeathregDTO.drDod" datePath="tbDeathregDTO.drDod">
						</apptags:date>
						
					<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
						path="tbDeathregDTO.drDeceasedname" 
						cssClass="hasNameClass form-control">
					</apptags:input>
				 </div> --%>
					
					<div class="text-center">
						<button type="button" class="btn btn-blue-3" title="Search"
							onclick="SearchDateForPrintCertificate()">
							<spring:message code="BirthRegDto.search"/>
						</button>
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='PrintCertificate.html'">
							<spring:message code="BirthRegDto.reset"/>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
					<select name="ddl1" id="ddl1">
						<option value="0">Select Certificate</option>
					</select>

					<%-- 	 <c:if test="${not empty command.tbDeathRegDtoList}">  --%>
						
						<!-- grid search -->
					<spring:eval
				expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('C2','CRD',${UserSession.organisation}).getOtherField()" var="otherField"/>
				<input type="hidden" value="${otherField}" id="coordinates" />
					 <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="printcertiDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="20%" align="center"><spring:message
											code="BirthRegDto.applnId" text="Application Id" /></th>
									<th width="25%" align="center"><spring:message
											code="TbDeathregDTO.drRegno" text="Registration No" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegDto.brCertNo" text="Certificate No" /></th>
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.form.status" text="Status" /></th>
									<th width="20%" align="center"><spring:message
											code="HospitalMasterDTO.form.action" text="Action" /></th>
								</tr>
							</thead>
							
							<tbody>
							<%-- <c:forEach items="${command.tbDeathRegDtoList}" var="deathRegDTO" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
									<td>${deathRegDTO.drDod}</td>
									<td>${deathRegDTO.applnId}</td>
									<td>${deathRegDTO.drDeceasedname}</td>
									<td>${deathRegDTO.drSex}</td>			
									<td>${deathRegDTO.cpdRegUnit}</td>
									
									<td class="text-center">
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Death registration Form"
												onclick="modifyDeath('${deathRegDTO.drId}','PrintCertificate.html','editBND','E')">
												<i class="fa fa-pencil"></i>
											</button> 
											
									</td>
								</tr>
							</c:forEach> --%>
							</tbody>
							
						</table>
					</div>
					<%-- </c:if> --%>
			</form:form>

			</div>

		</div>

	</div>
</div>










 
 
 