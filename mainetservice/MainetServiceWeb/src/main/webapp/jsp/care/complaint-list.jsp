<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-reopen-feedback.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"	rel="stylesheet" type="text/css" />


   <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
    
	 <div class="widget">
	   <div class="widget-header">
	      <h2><spring:message code="care.search" text="Search Grievance"/></h2>
	   </div>
	   <div class="widget-content padding">
	  
	   <apptags:helpDoc url="GrievanceDepartmentReopen.html"></apptags:helpDoc> 
	   
	      <form:form method="POST" action="GrievanceDepartmentReopen.html"
					commandName="command" 
					class="form-horizontal"
					id="form_grievanceReopen">
			
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
	         
	         <div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered" id="id_complaintList">
							<thead>
								<tr>
									<th width="10%"><spring:message code="care.reports.tokenNo" text="Token Number" /></th>
									<th width="20%"><spring:message code="care.reports.dateOfRequest" text="Date Of Request" /></th>
									<th width="12%"><spring:message code="care.reports.department" text="Department" /></th>
									<th width="25%"><spring:message code="care.reports.complaintType" text="Complaint Type" /></th>
									<th width="25%"><spring:message code="care.reports.complaintDescription" text="Complaint Description" /></th>
									<th width="10%"><spring:message code="care.reports.status" text="Status" /></th>
									<th width="8%"><spring:message code="care.action.Action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.careRequests}" var="complaint" varStatus="loop">
									<tr>
										<td align="center">
										<c:choose>
											<c:when test="${empty  complaint.complaintId}">
												<c:out value="${complaint.applicationId}"></c:out>
											</c:when>	
											<c:otherwise>
												<c:out value="${complaint.complaintId}"></c:out>
											</c:otherwise>
										</c:choose>	
										</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy hh:mm a" value="${complaint.dateOfRequest}" /></td>
										<td><c:out value="${userSession.languageId eq 1 ? complaint.departmentComplaintDesc : complaint.departmentComplaintDescReg }"></c:out></td>
										<td><c:out value="${userSession.languageId eq 1 ? complaint.complaintTypeDesc : complaint.complaintTypeDescReg }"></c:out></td>
										<td><c:out value="${complaint.description}"></c:out></td>
										<td>
											<c:if test="${complaint.status eq 'CLOSED'}">
											 <span class="text-green-1"> 
													<spring:message code="care.status.closed" text="Closed"/>
											</span> 
											</c:if>
											<c:if test="${complaint.status eq 'EXPIRED'}">
											 <span class="text-red-1"> 
													<spring:message code="care.status.expired"  text="Expired"/>
											</span> 
											</c:if>
											<c:if test="${complaint.status eq 'PENDING'}">
											 <span class="text-orange-5"> 
													<spring:message code="care.status.pending"  text="Pending"/>
											</span> 
											</c:if>	
										</td>
										<td align="center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Complaint"
												onclick="searchAndViewComplaint('${complaint.complaintId}')">
												<i class="fa fa-eye"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
	         		</div>
	         </div>
	         <div class="text-center clear padding-10">
	             <button id="btnBack" type="button" class="btn btn-danger hidden-print" onclick="javascript:openRelatedForm('GrievanceDepartmentReopen.html','this');"><spring:message code="care.back" text="Back"/></button>
              </div>
         </form:form>
	   </div>
	</div>
   </div>