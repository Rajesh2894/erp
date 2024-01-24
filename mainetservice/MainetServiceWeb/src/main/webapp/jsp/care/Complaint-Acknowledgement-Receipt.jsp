<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>


    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown"> 
      <!-- Your awesome content goes here -->
      <div class="widget" id="receipt">
        <div class="widget-content padding">
        <form action="" method="get">
          <div class="row">
            <div class="col-xs-12 text-center">
              <h3 class="margin-bottom-0">${complaintAcknowledgementModel.organizationName}</h3>
              <p><spring:message code="care.receipt" text="Complaint Acknowledgement Receipt"/></p>
            </div>
           
          </div>
          
          <div class="row margin-top-30">
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.token" text="Token Number:"/></div>
            <div class="col-xs-3">${complaintAcknowledgementModel.tokenNumber}</div>
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.dateAndTiem" text="Date & Time :"/></div>
	        <div class="col-xs-3">${complaintAcknowledgementModel.formattedDate}</div>
          </div>
           <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.applicantname" text="Applicant Name :"/></div>
            <div class="col-xs-3">${complaintAcknowledgementModel.complainantName}</div>
            <div class="col-xs-3 text-right"><spring:message code="care.receipt.status" text="Status :"/></div>
          	<div class="col-xs-3">
          		<c:if test="${complaintAcknowledgementModel.status eq 'CLOSED'}">
					 <span class="text-green-1"> 
							<spring:message code="care.status.closed" text="Closed"/>
					</span> 
					</c:if>
					<c:if test="${complaintAcknowledgementModel.status eq 'EXPIRED'}">
					 <span class="text-red-1"> 
							<spring:message code="care.status.expired"  text="Expired"/>
					</span> 
					</c:if>
					<c:if test="${complaintAcknowledgementModel.status eq 'PENDING'}">
					 <span class="text-orange-5"> 
							<spring:message code="care.status.pending"  text="Pending"/>
					</span> 
				</c:if>	
          	</div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.department" text="Department :"/></div>
            <div class="col-xs-3">${complaintAcknowledgementModel.department}</div>
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.complaintsubtype" text="Complaint Sub Type :"/></div>
            <div class="col-xs-3">${complaintAcknowledgementModel.complaintSubType}</div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.location" text="Location :"/></div>
            <div class="col-xs-6">${complaintAcknowledgementModel.ward}</div>
          </div>
          <div class="row margin-top-10">
          	<div class="col-xs-3 text-right"><spring:message code="care.receipt.description" text="Description :"/></div>
            <div class="col-xs-6"><c:out value="${complaintAcknowledgementModel.description}"/></div>
          </div>
          <h5><spring:message code="care.receipt.escalationdetails" text="Escalation Details"/></h5>
          <div class="table-responsive margin-top-10">
            <table class="table table-bordered table-condensed">
			  <tr>
			     <th><spring:message code="care.receipt.level" text="Level"/></th>
			    <th><spring:message code="care.receipt.duration" text="Duration (D:H:M)"/></th>
			    <th><spring:message code="care.receipt.employeename" text="Employee Name"/></th>
			    <th><spring:message code="care.receipt.designation" text="Designation"/></th>
			    <th><spring:message code="care.receipt.department2" text="Department"/></th>
			    <th><spring:message code="care.receipt.email" text="Email"/></th>
			  </tr>
			  <c:forEach items="${complaintAcknowledgementModel.escalationDetailsList}" var="requestLists" varStatus="status">
			                <tr>
			                  <td class="text-center"><c:out value="${requestLists.level}">
			                    </c:out></td>
			                  <td><c:out value="${requestLists.sla}">
			                    </c:out></td>
			                  <td><c:out value="${requestLists.empName}">
			                    </c:out></td>
			                  <td><c:out value="${requestLists.designation}">
			                    </c:out></td>
			                  <td><c:out value="${requestLists.department}">
			                    </c:out>
			                    </td>
			                  <td><c:out value="${requestLists.email}">
			                    </c:out>
			                    </td>
			                </tr>
			              </c:forEach>
			</table>

          </div>
          <div class="text-center margin-top-10">
            <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> <spring:message code="care.receipt.print" text="Print"/></button>
             <button type="button" class="btn btn-danger hidden-print" onclick="window.location.href='AdminHome.html'"><spring:message code="care.receipt.back" text="Back"/></button>
          </div>
        
          </form>
        </div>
      </div>
      <!-- End of info box --> 
    </div>
 

<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
$("html, body").animate({ scrollTop: 0 }, "slow");
</script>
