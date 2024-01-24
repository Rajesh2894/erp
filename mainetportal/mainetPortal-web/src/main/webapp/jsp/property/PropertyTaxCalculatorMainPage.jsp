<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="js/property/propertyTaxCalculator.js" ></script>  
<style>
.widget-content.padding {
    overflow: visible;
}
</style>
 
<!-- End JSP Necessary Tags -->
<div class="content">
<div class="widget">
        <div class="widget-header">
          <h2><spring:message code="property.taxCalculator" text="Property Tax Calculator"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="property.Help"/></span></a></div>
        </div>
        
        <div class="widget-content padding">
	
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="PropertyTaxCalculator.html"
					class="form-horizontal form" name="PropertyTaxCalculatorPage"
					id="PropertyTaxCalculatorPage">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>

<div class="accordion-toggle">
	 
<%-- <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">   --%>
                  <div class="form-group ulbList" >
                       <label for="orgId" class="col-sm-2 control-label">
						<spring:message code="eip.org.select" text="Please select your Municipality"/></label> 
			<div class="col-sm-4">
				      <form:select path="orgId" id="loginselectedOrg" cssClass="form-control chosen-select-no-results">
						<form:option value="-1"> <spring:message code="eip.org.select" /></form:option>
								<c:forEach items="${command.userSession.organisationsList}" var="orglist">
				<optgroup label="${orglist.key}">
					<c:forEach items="${orglist.value}" var="org">
						<c:if test="${userSession.languageId eq 1}"><option value="${org.orgid}">${org.ONlsOrgname}</option></c:if>
						<c:if test="${userSession.languageId eq 2}"><option value="${org.orgid}">${org.ONlsOrgnameMar}</option></c:if>
					</c:forEach>
				</optgroup>

				</c:forEach>
				    </form:select>
				    </div>
				   
                   </div>
                   
                   <div class="text-center padding-10">			
			<button type="button" class="btn btn-success"
				onclick="ShowFormDetails(this)" id="displayDetails"><spring:message code="property.button.Submit" text="Submit"/></button>
		</div>
                   
<%--               </c:if>  --%>
              
              <div id=showTaxCalculator>
              
              </div>
 
</div>
					</form:form>
						
</div>
</div>
			</div>			
