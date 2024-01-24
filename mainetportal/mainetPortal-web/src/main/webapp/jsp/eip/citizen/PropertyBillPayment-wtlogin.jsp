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
<style>
	.widget-content.padding {
		overflow: visible !important;
	}
	.text-small{
		font-size: small;
	}
</style>
<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
<ol class="breadcrumb" id="CitizenService">
   <li>
      <a href="CitizenHome.html" class="internal">
        <spring:message code="home" text="Home"/></a>
   </li>
   <li class="active"><spring:message code='property.bill.payment.heading' text="Property Bill Payment"/></li>
</ol>
<!-- Start Content here --> 
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="propertyBill.BillCollection"/></h2>
      <apptags:helpDoc url="PropertyBillPayment.html"></apptags:helpDoc>
     <%-- <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help" aria-label="Help document"><i class="fa fa-question-circle fa-lg"></i></a> </div> --%>
    </div>
    <div class="widget-content padding">
      <form:form action="PropertyBillPayment.html" class="form-horizontal" name="PropertyBillPaymentSearch" id="PropertyBillPaymentSearch">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
       		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div> 
       		<form:hidden path="" id="empLoginName" value="${command.userSession.employee.emploginname}"/>
       		<form:hidden path="billingMethod" id="billingMethod"/>
			<div class="form-group" >		
				<apptags:input labelCode="propertydetails.PropertyNo." path="propBillPaymentDto.assNo"></apptags:input>
				<%-- <apptags:input labelCode="propertydetails.oldpropertyno" path="propBillPaymentDto.assOldpropno" cssClass="mandColorClass "></apptags:input>		 --%>
				<div class="" id="showFlatNo">
				<%-- <apptags:input labelCode="Flat No" path="propBillPaymentDto.flatNo" ></apptags:input> --%>
				
					<label class="col-sm-2 control-label"><spring:message
							code="property.FlatNo" text="Flat No" /></label>
					<div class="col-sm-4">
						<form:select path="propBillPaymentDto.flatNo" id="flatNo"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='property.select' text="select"/>
							</form:option>
							<c:forEach items="${command.flatNoList}" var="flatNoList">
								<form:option value="${flatNoList}">${flatNoList}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				</div>
			</div>
			
			
		
			<div class="form-group">
			<div class="text-center padding-bottom-10">
	           <button type="button" class="btn btn-blue-2" id="serchBtnDirect"
										onclick="SearchButton(this)">
										<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
				</button>
				<button type="button" class="btn btn-success" id="serchBtn"
										onclick="redirectToSearchPropNo(this)">
										<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search" text="Search"/>
				</button>  	
				<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='CitizenHome.html'">
							<spring:message code="propertyBill.Back" text="Back"></spring:message>
				</button>
				<button type="button" class="btn btn-warning" onclick="resetProperty();">
				<spring:message code="property.reset" text="Reset"/></button>
	          
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
	        
	       <p class="text-red text-small text-center"><strong><spring:message code="property.bill.payment.note" text="During online transaction, if receipt is not generated for successful transaction, then please use 'Receipt download' option to generate your receipt. If receipt is not generated using this option will be available after 48 Hrs. "/></strong></p>
      </form:form>
    </div>
  </div>
</div>
</div>