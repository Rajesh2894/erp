<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/disaster_management/injusrydetails.js"></script>
<style>
#disasterDataTable tbody tr td:nth-child(5) {
	word-break: break-all;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="disaster.call.closure" text="Call Closure" /></strong>
			</h2>
		</div>
		

		<div class="widget-content padding">

			<form:form action="InjuryDetails.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmInjuryDetails" id="frmInjuryDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
			<!-- 	<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div> -->
				
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
</div>
				

				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="CMT" hasId="true"
										pathPrefix="complainRegisterDTO.complaintType" 
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" cssClass="form-control"
										showAll="false" />
				
				</div>
				
				<div class="form-group"> 
					<label class="col-sm-2 control-label  "
										for="location">
					<spring:message code="ComplainRegisterDTO.location" text="Location" />
					</label>
									<div class="col-sm-4">
										<form:select path="" cssClass="form-control"
											id="location"  data-rule-required="true"  >
											<form:option value="0">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${locations}" var="loc">
												<form:option value="${loc.locId}">${loc.locNameEng}-${loc.locArea}</form:option>
											</c:forEach>
										</form:select>
									</div>
									
									<apptags:input labelCode="ComplainRegisterDTO.callNo"
										path="complainRegisterDTO.complainNo" cssClass="hasAlphaNumeric"
									 	maxlegnth="50" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="disaster.Department.Scrutiny"
							text="Department Scrutiny Status" />
					</label>
					<div class="col-sm-4">
						<form:select path="complainRegisterDTO.complainStatus"
							cssClass="form-control" id="srutinyStatus">
							<form:option value="">
								<spring:message
									code='validation.complainRegisterDTO.select.status' />
							</form:option>
							<form:option value="APPROVED">
								<spring:message code="disaster.approved"
									text="APPROVED" />
							</form:option>
							<form:option value="PENDING">
								<spring:message code="disaster.pending" text="PENDING" />
							</form:option>
						</form:select>
					</div>
				</div>




				<!-- Buttons start -->

		<div class="text-center margin-top-10">
			<%-- <input type="button" onClick="searchHospitalData(this);" value="<spring:message code="bt.search"/>" class="btn btn-success" id="Search">
	 --%>		
 				<button type="button" id="search" class="btn btn-blue-2" onclick = "searchDisasterData()"
 					title='<spring:message code="bt.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="bt.search" text="Search" />
				</button>
				<%-- Defect #152220 --%>
				<button type="button" class="btn btn-warning"
					onclick="window.location.href='InjuryDetails.html'"
					title='<spring:message code="rstBtn" text="Reset" />'>
					<spring:message code="rstBtn" text="Reset" />
				</button>
			<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print"
				title='<spring:message code="bt.backBtn" text="Back" />'
				value='<spring:message code="bt.backBtn" text="Back" />'>
		</div>
	
		
			<!-- Table Grid Start  -->
        
         <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="disasterDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="ComplainRegisterDTO.srNo" text="Sr.No" /></th>
									<th width="15%" align="center"><spring:message
											code="ComplainRegisterDTO.callNo" text="Call No" /></th>
									<th width="15%" align="center"><spring:message
											code="ComplainRegisterDTO.callType" text="Call Type" /></th>
									<th width="15%" align="center"><spring:message
											code="ComplainRegisterDTO.callSubtype" text="Call Sub Type" /></th>
									<th width="30%" align="center">
										<spring:message code="ComplainRegisterDTO.description" text="Description" /></th>
									<th width="10%" align="center">
										<spring:message code="ComplainRegisterDTO.status" text="Status" /></th>
									<th width="10%" align="center"><spring:message
											code="ComplainRegisterDTO.action" text="Action" /></th>
										
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${complaints}" var="comp" varStatus="item">
									
								<tr>
								 	<td class="text-center">${item.count}</td>
								   	<td >${comp.complainNo}</td>
									<td>${comp.codDesc}</td>
									<td>${comp.codDesc1}</td>			
									<td>${comp.complaintDescription}</td>
									<td>${comp.complainStatus}</td>
									<td class="text-center">
											<c:if test="${comp.complainStatus ne 'Rejected'}">
											<button type="button" class="btn btn-warning btn-sm"
												title="<spring:message code="ComplainRegisterDTO.edit" text="Edit" />"
												onclick="modifyInjury('${comp.complainId}','InjuryDetails.html','editDisInjury','E')">
												<i class="fa fa-pencil"></i>
											</button> 
											</c:if>
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