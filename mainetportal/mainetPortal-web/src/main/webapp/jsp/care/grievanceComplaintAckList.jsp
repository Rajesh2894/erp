<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="js/care/grievance-status.js"></script>
<div class="widget">
		<!-- <div class="widget-header">
			<h2>
				Complaint Status</h2>
		</div> -->
		<div class="widget-content padding">

<form:form>
          <h4><spring:message code="" text=""/></h4>
			 <div class="table-responsive">
            <table class="table table-bordered table-striped">
              <tr>
                <th><spring:message code="care.tokennumber" /></th>
                <th><spring:message code="care.dateOfRequest" text="Date Of Request"/></th>
                <th><spring:message code="care.department" /></th>
                <th><spring:message code="care.complaintType" /></th>
                <th><spring:message code="care.complaintDescription" /></th>
                <th><spring:message code="care.status" /></th>
                <th><spring:message code="care.Action" /></th>
              </tr>
              <c:forEach items="${command.careDTO}" var="requestLists" varStatus="status">
                <tr>
                  <td >
                  <c:choose>
                  <c:when test="${empty requestLists.complaintId}">
                  <c:out value="${requestLists.applicationId}" />
                    </c:when>
                    
                    <c:otherwise>
                     <c:out value="${requestLists.complaintId}" />
                     </c:otherwise>
                    </c:choose>
                    </td>
                  <td >
                    <fmt:formatDate pattern="dd/MM/yyyy hh:mm a" value="${requestLists.createdDate}" />
                   </td>  
                   
                   <c:choose>
                   		<c:when test="${userSession.languageId eq 1}">
                   			<td><c:out value="${requestLists.departmentComplaintDesc}"> </c:out></td>
			                <td><c:out value="${requestLists.complaintTypeDesc}"></c:out></td>
                   		</c:when>
                   		<c:otherwise>
                   			<td><c:out value="${requestLists.departmentComplaintDescReg}"> </c:out></td>
			                <td><c:out value="${requestLists.complaintTypeDescReg}"></c:out></td>
                   		</c:otherwise>
                   </c:choose>
                   
                  <td><c:out value="${requestLists.description}">
                    </c:out></td>
                  <td>
                  	<c:if test="${requestLists.status eq 'CLOSED'}">
						 <span class="text-green-1"> 
								<spring:message code="care.status.closed" text="Closed"/>
						</span> 
						</c:if>
						<c:if test="${requestLists.status eq 'EXPIRED'}">
						 <span class="text-red-1"> 
								<spring:message code="care.status.expired"  text="Expired"/>
						</span> 
						</c:if>
						<c:if test="${requestLists.status eq 'PENDING'}">
						 <span class="text-orange-1"> 
								<spring:message code="care.status.pending"  text="Pending"/>
						</span> 
						</c:if>
				
					</td>
                  	<td>
                  	<a href="javascr:void(0);"  onclick="getAckStatus('${requestLists.applicationId}')"  class="btn btn-blue-2 btn-sm">
						<spring:message code="care.view"	text="View" />
					</a>
                  	</td>
                </tr>
              </c:forEach>
            </table>
            </div>
</form:form>
</div>
</div>