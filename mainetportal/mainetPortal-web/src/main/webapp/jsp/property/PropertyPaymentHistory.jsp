<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/viewPropertyDetails.js"></script>

<c:if test="${ empty  command.collectionDetails}">

<h2 style="color:red;"><spring:message code="property.NoHistoryFound" text="No History Found"/></h2>
</c:if>

<c:if test="${not empty  command.collectionDetails}">
	
					<div id="a2" class="margin-10">
							<table id="CollectionDetails" class="table table-striped table-bordered margin-top-10">
				                    
				                        <tr>    
				                         
				                        <th width="10%"><spring:message code="propertyTax.SrNo"/></th>                   
				                        	<th width="10%"><spring:message code="property.receiptno" text="Receipt No"/></th>
											<th width="12%"><spring:message code="property.receiptdate" text="Receipt Date"/></th>
											<th width="12%"><spring:message code="property.viewDetails.PaymentMode" text="Payment Mode"/></th>
											<th width="12%"><spring:message code="property.viewDetails.Amount" text="Amount"/></th>
											<th width="12%"><spring:message code="property.download" text="Download"/></th>
										</tr>	
									
										<tbody>
										<c:forEach var="collection" items="${command.collectionDetails}" varStatus="status">	
										 <tr> 
										 <td>${status.count}</td>
											<td>${collection.lookUpId}</td>
											<td>${collection.lookUpCode}</td>
											<td>${collection.lookUpType}</td>
											<td>${collection.otherField}</td>
											<td class="text-center"><button class="btn btn-primary btn-sm" type="button" onclick="DownloadReceiptPdfFile(${collection.lookUpId})"><i class="fa fa-download" aria-hidden="true"></i></button></td>
											</tr>
											</c:forEach>
										</tbody>
							</table>
							
						
					</div>				
					
</c:if>	
<div class="col-sm-12 text-center margin-top-10">				
<a href="CitizenHome.html"  id="back" class="btn btn-danger"><spring:message code="bckBtn" text="Back" /></a>	</div>	
	