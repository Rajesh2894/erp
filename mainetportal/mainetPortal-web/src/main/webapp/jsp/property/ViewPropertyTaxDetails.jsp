<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/eip/citizen/dashboard.js"></script>
<!-- End JSP Necessary Tags -->
 <apptags:breadcrumb></apptags:breadcrumb> 
 
 <div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><b><spring:message code="property.TaxDetails"/></b></h2>				
				
	</div>
	
		
	<div class="widget-content padding">
		<form:form action="ViewPropertyDetail.html"
					class="form-horizontal form" name="ViewPropertyDetail"
					id="ViewPropertyDetail">	
			<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div> --%>
  
  <!--------------------------------------------Tax Calculation-------------------------------------------------------------- -->     
    <div class="accordion-toggle ">

		   <h4 class="margin-top-10 margin-bottom-10 panel-title ">
		   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="property.TaxDetails"/></a>
		   </h4>         
		    <div class="panel-collapse collapse in" id="TaxCalculation">      
		     
		       
		            <div class="table-responsive">
		              <table class="table table-striped table-condensed table-bordered">
		                  <tr>
		                    <th width="50"><spring:message code="propertyTax.SrNo"/></th>
		                    <th width="400"><spring:message code="propertyTax.TaxName"/></th>
		                    <th width="200" class="text-right"><spring:message code="propertyTax.Arrears"/></th>
		                    <th width="200" class="text-right"><spring:message code="propertyTax.CurrentYear"/></th>
		                    <th width="200" class="text-right"><spring:message code="propertyTax.Total"/></th>
		                  </tr>
		                  <tbody>
		                  
			                <c:forEach var="tax" items="${command.authComBillList}"  varStatus="status">
			                 <c:forEach var="taxdet" items="${tax.tbWtBillDet}"  varStatus="status1">
			                 <c:set var="total" value="${tax.bmTotalAmount+tax.bmActualArrearsAmt}">
			                 </c:set>
			                  <tr>
			                  <td>${status1.count}</td>
			                  <td>${taxdet.taxDesc}</td>
			                   <td class="text-right">${taxdet.bdPrvArramt}</td>
			                  <td class="text-right">${taxdet.bdCurTaxamt}</td>
			                  <td class="text-right">${taxdet.bdPrvArramt+taxdet.bdCurTaxamt}</td>
			                </tr>
			                </c:forEach>
			                </c:forEach>
		                </tbody>
		              </table>
		            </div>
		  <div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  <th width="500" class="text-right">${total}</th>
                </tr>
              </table>
            </div>
		        
		          </div>
	</div>
	
	
	         <c:if test="${command.assType ne 'D' }"> 
			<div class="text-center padding-top-10">
					
						<button type="button" class="btn btn-blue-2" 
									onclick="BackToDetails()"><spring:message code="property.Back" text="Back"/>
						</button>
						

			</div>
	
		      </c:if>
	                  
	                  <c:if test="${command.assType eq 'D' }">
	                  
	                
	                   <div class="text-center padding-top-10">
					
						<button type="button" class="btn btn-blue-2" 
									onclick="backProperty()"><spring:message code="property.Back" text="Back"/>
						</button>
						
	                  </div>
	                
					
					</c:if>
	
	
	</form:form>
	</div>
	
	</div>
</div>