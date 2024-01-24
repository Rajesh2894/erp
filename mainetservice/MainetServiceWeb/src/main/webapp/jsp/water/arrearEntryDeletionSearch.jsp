<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/arrearEntryDeletion.js"></script>
 

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><spring:message code="water.bill.detail.entry"/></strong></h2>				
				<div class="additional-btn">
					<apptags:helpDoc url="ArrearEntryDeletion.html"></apptags:helpDoc>
				</div>
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">* </i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
	 <form:form action="ArrearEntryDeletion.html"
					class="form-horizontal form" name="ArrearEntryDeletion "
					id="ArrearEntryDeletion">	
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
		
		<form:hidden path="csmrInfo.csIdn" id ="csIdns"/>
		<%-- <form:hidden path="billMasList.bmIdno" id ="bmIdno"/> --%>
		
		
	
		<c:choose>
		<c:when test="${not empty command.getBillMasList()}"> 
			<c:set var="d" value="0" scope="page" />
                <table id="taxdetailTable" class="table table-striped table-bordered appendableClass taxDetails">
                    <tbody>
                        <tr>
                        <th width="20%"><spring:message code="property.financialyear"/></th>
                            <th width="50%"><spring:message code="taxdetails.taxdescription"/></th>
                     	 	<th width="30%"><spring:message code="taxdetails.arrears"/></th>
                        </tr>
                  <c:forEach var="billMas" items="${command.getBillMasList()}" varStatus="masStatus" >              
                     <c:forEach var="billDet" items="${billMas.getTbWtBillDet()}" varStatus="detStatus" >                   
                  <tr class="firstUnitRow  ${masStatus.count%2==0? "trfirst": "trSecond"}">
                      		 <td>                                       	
                        	  
		 						
		 			<form:input path="" type="text" class="form-control has2Decimal mandColorClass text-center" id="year0"  readonly="true"
		 			value="${billMas.bmGenDes}" disabled="true"/>			
		 		
		 						    
		 					</td>
            <td width="150"> 
	           			 <form:select id="taxDesc" path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].taxId" class="form-control mandColorClass"  disabled="true" >
							<form:option value="0">
								<spring:message code="property.sel.optn" text="Select" />
							</form:option>
							<c:forEach items="${command.taxesMaster}" var="tbTax">
							<form:option value="${tbTax.taxId}">${tbTax.taxDesc}</form:option>
							</c:forEach>
					  </form:select>
			   	</td> 
							 <td width="150"><form:input path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].bdCsmp" type="text" class="form-control has2Decimal mandColorClass text-right" id="areear0"  disabled="true"/></td>
							
                       	</tr> 
                       	</c:forEach>
                       	</c:forEach> 
                    </tbody>
                 </table>
                 </c:when>

             </c:choose>
			
			<!-- Start button -->
			<div class="text-center padding-10">
				  
					 <button type="button" class="btn btn-success" id="deleteBtn"
							onclick="deleteArrears(this)">
							<spring:message code="water.arrear.delete" text="Delete Arrears"/>
						</button>
					 <button type="button" class="btn btn-danger"
					 onclick="backActionForm(this)" id="backEntry"><spring:message code="water.btn.back"/></button>
				   
			</div>
			
			<!--  End button -->
			
		</form:form>
		<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->	
		</div>						
<!-- End of Content -->
 