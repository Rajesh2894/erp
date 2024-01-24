<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/disaster_management/departmentalScrutiny.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="ComplainRegisterDTO.departmental.scrutiny" text="Departmental Scrutiny" /></strong>
			</h2>
		</div>
		
		<br>

		<div class="widget-content padding">

			<form:form action="DepartmentalScrutiny.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmDepartmentalScrutiny" id="frmDepartmentalScrutiny">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				
				<div class="form-group">
					<apptags:input labelCode="ComplainRegisterDTO.callNo"	path="complainRegisterDTO.complainNo" maxlegnth="15" cssClass="alphaNumeric form-control" isMandatory="true"/>
				</div>	
				
				<spring:message code="ComplainRegisterDTO.Or" text="OR" />
				
				<div class="form-group">
				
					<apptags:date fieldclass="datepicker" labelCode="AllAttendedDisasterDTO.fromDate"
						datePath="complainRegisterDTO.frmDate" isMandatory="true" cssClass="custDate mandColorClass date">
					</apptags:date>
							
					<apptags:date fieldclass="datepicker" labelCode="AllAttendedDisasterDTO.toDate"
						datePath="complainRegisterDTO.toDate" isMandatory="true" cssClass="custDate mandColorClass date">
					</apptags:date>
								     
				</div>
				


				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2" onclick="searchDeptScrutiny()"
						title='<spring:message code="CemeteryMasterDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="CemeteryMasterDTO.form.search" text="Search" />
					</button>

					<button type="button" id="reset" onclick="window.location.href='DepartmentalScrutiny.html'" class="btn btn-warning"
						title='<spring:message code="CemeteryMasterDTO.form.reset" text="Reset" />'>
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="CemeteryMasterDTO.form.reset" text="Reset" />
					</button>
					
					<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger  hidden-print"
						title='<spring:message code="bt.backBtn" text="Back" />'
						value='<spring:message code="bt.backBtn" text="Back" />'>

				</div>				

				<!-- Table Grid Start  -->
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="complainRegDataTable">
						<thead>
							<tr>
								<th align="center"><spring:message code="ComplainRegisterDTO.srNo" text="Sr.No" /></th>
								<th align="center"><spring:message code="ComplainRegisterDTO.callNo" text="Call No." /></th>
								<th align="center"><spring:message code="ComplainRegisterDTO.call.date" text="Call Date" /></th>
								<th align="center"><spring:message code="ComplainRegisterDTO.callType" text="Call Type" /></th>
								<th align="center"><spring:message code="DisasterCallDetailsDTO.callDetails" text="Call Details" /></th>
								<th align="center"><spring:message code="ComplainRegisterDTO.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${complainReg}" var="complainRegVar" varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td>${complainRegVar.complainNo}</td>
									<td><fmt:formatDate pattern = "dd/MM/yyyy" value ="${complainRegVar.createdDate}"/></td>
									<td>${complainRegVar.complaintType1Desc}</td>
									<td>${complainRegVar.complaintDescription}</td>

									<td class="text-center">
										<button type="button" class="btn btn-warning btn-sm"
											title="<spring:message code="ComplainRegisterDTO.edit.departmental.scrutiny" text="Edit Departmental Scrutiny" />"
											onclick="modifyComplainReg('${complainRegVar.complainId}','DepartmentalScrutiny.html','editComplainReg','E')">
											<i class="fa fa-pencil"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
				<!-- Table Grid End  -->

			</form:form>

		</div>
	</div>
</div>





