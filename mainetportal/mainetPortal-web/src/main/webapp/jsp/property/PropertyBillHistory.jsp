<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/viewPropertyDetails.js"></script>

<c:if test="${ empty  command.billMasList}">

<h2 style="color:red;"><spring:message code="property.NoHistoryFound" text="No Payment History Found"/></h2>
</c:if>

 <c:if test="${ not  empty  command.billMasList}">        
					<div id="a2" class="margin-10">
							
						<table id="BillDetails" class="table table-striped table-bordered margin-top-10">
			                    
			                        <tr> 
			                        <th width="10%"><spring:message code="propertyTax.SrNo"/></th>                 
			                        	<th width="10%"><spring:message code="property.viewDetails.yearFromTo" text="Year(From-To)"/></th>
										<th width="12%"><spring:message code="property.viewDetails.DueDate" text="Due Date"/></th>
										<th width="12%"><spring:message code="property.viewDetails.TotalDemand" text="Total Demand"/></th>
										<th width="12%"><spring:message code="property.viewDetails.BalanceAmount" text="Balance Amount"/></th>	
										<th width="12%"><spring:message code="property.viewDetails.Rebate" text="Rebate(If Any)"/></th>			
										<th width="12%"><spring:message code="property.viewDetails.Action" text="Action"/></th>
									    <th width="12%"><spring:message code="property.download" text="Download"/></th> 
									
									</tr>	
							
									<tbody>
									<c:forEach var="billMasList" items="${command.billMasList}" varStatus="status">	
									 <tr> 	
									 <td>${status.count}</td>
										<td>${billMasList.bmCcnOwner}</td>
										<td>${billMasList.bmRemarks}</td>
										<td>${billMasList.bmTotalAmount}</td>
										<td>${billMasList.bmTotalOutstanding}</td>	
										<td>${billMasList.bmToatlRebate}</td>			
				                    	<td class="text-center"><button class="btn btn-primary btn-sm" type="button" onclick="ViewAssDetails(${billMasList.bmIdno})"><spring:message code="property.ViewTaxDetails" text="View Tax Details"/></button></td>
			                        	<td class="text-center"><button class="btn btn-primary btn-sm" type="button" onclick="DownloadPdfFile(${billMasList.bmIdno})"><i class="fa fa-download" aria-hidden="true"></i></button></td>	            
				                    		                           
								
									 </tr> 
									</c:forEach>
									</tbody>
								
						</table>
				
			
			   	</div>
</c:if>				   	
			<div class="col-sm-12 text-center margin-top-10">		
			<a href="CitizenHome.html"  id="back" class="btn btn-danger"><spring:message code="bckBtn" text="Back" /></a>
			</div>

			