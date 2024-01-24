<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <script src="js/mainet/validation.js"></script>
 <script src="js/property/PropertyBillPaymentSearch.js" ></script>   
 <div  id="dataDiv">
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here --> 
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="propertyBill.BillCollection"/></h2>
      <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help" aria-label="Help document"><i class="fa fa-question-circle fa-lg"></i></a> </div>
    </div>
    <div class="widget-content padding">
      <form:form action="PropertyBillPayment.html" class="form-horizontal" name="PropertyBillPaymentSearch" id="PropertyBillPaymentSearch">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
       		<form:hidden path="" id="empLoginName" value="${command.userSession.employee.emploginname}"/>
			<div class="form-group" >		
				<apptags:input labelCode="propertydetails.PropertyNo." path="propBillPaymentDto.assNo"></apptags:input>
				<apptags:input labelCode="propertydetails.oldpropertyno" path="propBillPaymentDto.assOldpropno" cssClass="mandColorClass "></apptags:input>		
			</div>
		
			<div class="form-group">
			<div class="text-center padding-bottom-10">
	           <button type="button" class="btn btn-blue-2" id="serchBtn"
										onclick="SearchButton(this)">
										<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
				</button> 	
	          <button type="button" class="btn btn-danger" id="back"
								onclick="history.back()">
								<spring:message code="propertyBill.Back"></spring:message>
							</button>

							<button type="Reset" class="btn btn-warning" id="resetform"
								onclick="resetButton(element)">
								<spring:message code="property.Reset" />
							</button>
	        </div>
	        </div>
	        <c:if test="${command.billPayDto.redirectCheck eq 'Y'}">
	         <div class="form-group" style="margin-left: 0">
 			 	<spring:message code="property.redirect.assessment"/>         
 			 	<button type="button" class="btn btn-blue-2" id="serchBtn"
										onclick="redirectPage()">
										<spring:message code="unit.proceed"/>
				</button> 	
 			 </div>
 			 </c:if>
	        
      </form:form>
    </div>
  </div>
</div>
</div>