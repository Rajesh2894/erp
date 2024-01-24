<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.fancybox-inner{width:800px !important;}
.fancybox-wrap{width:800px !important;}
</style>


				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<b><spring:message code="prop.mob.tiltle"/></b>
				</h4>
				
				<div class="text-center padding-10">
				<button type="button" class="btn btn-success"
				onclick="closePopup(this)" id="continue"><spring:message code="property.continue"/></button>
				
				<button type="button" class="btn btn-warning"
				onclick="window.location.href='AdminHome.html'" id="exit"><spring:message code="property.exit"/></button>
			</div>
			
					<table  class="table  table-bordered " >
                       	<tr>
                        	<th width="10%" class="required-control"><spring:message code="propertydetails.PropertyNo."/></th>
                            <th width="20%" class="required-control"><spring:message code="property.OwnerName" /></th>
                            <th width="10%" class="required-control"><spring:message code="property.location" /></th>
                             <apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false" hasTableForm="true" showData="false" columnWidth="12%" />
                 
                        </tr>
				<c:forEach  items="${command.getPropCommonDtoList()}" varStatus="status" var="detail">
                     <tbody> 
                    	<tr class="">
                  
                      		<td>${detail.proertyNo}</td>
							<td>${detail.ownerName}</td>
							<td>${detail.loction}</td>
							<td>${detail.assWard1}</td>
							<c:if test="${detail.assWard2 ne null}">		
							<td>${detail.assWard2}</td>
							</c:if>
							<c:if test="${detail.assWard3 ne null}">		
							<td>${detail.assWard3}</td>
							</c:if>
							<c:if test="${detail.assWard4 ne null}">		
							<td>${detail.assWard4}</td>
							</c:if>		
							<c:if test="${detail.assWard5 ne null}">		
							<td>${detail.assWard5}</td>
							</c:if>
                       	</tr>
                     </tbody>
               </c:forEach>
                     </table>                        
				

			

               