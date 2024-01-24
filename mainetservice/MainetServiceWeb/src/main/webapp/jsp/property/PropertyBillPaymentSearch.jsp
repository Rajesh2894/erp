<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <script type="text/javascript" src="js/mainet/validation.js"></script>
 <script type="text/javascript" src="js/property/PropertyBillPaymentSearch.js" ></script>   
 <div  id="dataDiv">
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here --> 
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="propertyBill.BillCollection"/></h2>
     <!--  <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div> -->
   
       <apptags:helpDoc url="PropertyBillPayment.html"></apptags:helpDoc>

</div>
    <div class="widget-content padding">
      <form:form action="PropertyBillPayment.html" class="form-horizontal" name="PropertyBillPaymentSearch" id="PropertyBillPaymentSearch">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
       		<form:hidden path="billingMethodConfigure" id="billingMethodConfigure"/>
       		<form:hidden path="billingMethod" id="billingMethod"/>
       		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>

					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="property.propertytype" /> <span class="mand">*</span>
						</label>
						<div id="searchData col-sm-4">
							<label for="SinMul" class="radio-inline "> <form:radiobutton
									path="propBillPaymentDto.specNotSearchType" value="SM"
									class="SinMul" onchange="showSingleMultiple()" checked="true"/> <spring:message
									code="property.individual" /></label> <label for="group"
								class="radio-inline "> <form:radiobutton
									path="propBillPaymentDto.specNotSearchType" value="GP"
									class="group" onchange="showSingleMultiple()" /> <spring:message
									code="property.parentOrGrpProperty" /></label>

						</div>
					</div>


			<div class="form-group singleProps" >
				<apptags:input labelCode="propertydetails.PropertyNo." path="propBillPaymentDto.assNo" ></apptags:input>
				<apptags:input labelCode="propertydetails.oldpropertyno" path="propBillPaymentDto.assOldpropno"></apptags:input>		
			</div>
			<div class="form-group " id="showFlatNo">				
				<label class="col-sm-2 control-label"><spring:message
							code="" text="Flat No" /></label>
					<div class="col-sm-4">
						<form:select path="propBillPaymentDto.flatNo" id="flatNo"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='adh.select' />
							</form:option>
							<c:forEach items="${command.flatNoList}" var="flatNoList">
								<form:option value="${flatNoList}">${flatNoList}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
			</div>
			
			<%-- <div class="form-group parentPropNos" >
				<apptags:input labelCode="propertydetails.parentPropNo" path="propBillPaymentDto.parentPropNo" ></apptags:input>
			</div> --%>
					<div class="form-group parentPropNos">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="property.parentPropName" text="Parent Prop Name" /></label>
						<div class="col-sm-4">
							<form:select path="propBillPaymentDto.parentPropNo"
								id="parentPropNo"
								cssClass="form-control chosen-select-no-results"
								class="form-control mandColorClass" data-rule-required="true">
								<form:option value="">Select</form:option>
								<c:forEach items="${command.parentPropLookupList}" var="lookup">
									<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc} - ${lookup.lookUpCode}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<c:if test="${command.receiptType eq 'M'}">
			<div class="form-group padding-top-10 manualReceiptDate" >	
			<apptags:date labelCode="Manual Receipt Date" datePath="manualReeiptDate" fieldclass="lessthancurrdate"></apptags:date>
			</div>
			</c:if>
			
		
			<div class="form-group">
			<div class="text-center padding-bottom-10">
			 <button type="button" class="btn btn-blue-2" id="serchBtnDirect"
										onclick="SearchButton(this)">
										<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
				</button> 
			<button type="button" class="btn btn-blue-2" id="serchBtn"
										onclick="redirectToSearchPropNo(this)">
										<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
				</button> 	
	          
	        </div>
	        </div>
	        <c:if test="${command.billPayDto.redirectCheck eq 'Y'}">
	         <div class="form-group" style="margin-left: 0">
 			 	<spring:message code="property.redirect.assessment"/>         
 			 	<button type="button" class="btn btn-blue-2" id="serchBtn"
										onclick="window.location.href='PropertyAssessmentType.html'">
										<spring:message code="unit.proceed"/>
				</button> 	
 			 </div>
 			 </c:if>
	        
	        
      </form:form>
    </div>
  </div>
</div>
</div>