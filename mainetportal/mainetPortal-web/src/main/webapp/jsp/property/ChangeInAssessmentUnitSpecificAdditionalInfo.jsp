<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script src="js/property/unitSpecificAdditionalInfo.js"></script>


<div  class="padding-15"> 
<table id="unitSpecificInfoTable${lookup.lookUpCode}" class="table table-striped table-bordered ">
              <tbody>
                        <tr>
                        	<th width="25%" class="required-control"><spring:message code="unit.ApplicableUnitNo"/></th>
                            <th width="25%" class="required-control"><spring:message code="unit.FactorValue"/></th>
                            <th width="15%"><spring:message code="unit.Add/Delete"/></th>                       	 
                        </tr>  
                        <c:forEach items="${taxValue}" var="factor" varStatus="status">      
                                      
                        <tr class="specFact"> 
	                       	<td class="col-sm-5">		                        		
	                        		<form:select id="unitNoFact${p1}" class="form-control mandColorClass selectUnit" path="provAsseFactDtlDto[${p1}].unitNoFact" onchange="resetFactorValue(this,0);">
									<form:option value="0">Select Option</form:option> 																	
										<c:forEach items="${command.getUnitNoList()}" var="unitNo">
										<form:option  value="${unitNo}" selected="${unitNo == factor.getUnitNoFact() ? 'selected' : ''}">${unitNo}</form:option>										
										</c:forEach>
									</form:select> 
	                        </td>                 
	                        <td class="col-sm-5 ">
		                        	<form:hidden value="${factor.getProAssfFactorId()}" path="" id="proAssfFactorId"/>
		                        	<form:hidden value="${factor.getFactorValueCode()}" path="" id="factPref${status.count-1}"/>
		                        	
  	
									<c:set var="baseLookupCode" value="${factor.getFactorValueCode()}" />
										<form:select path="provAsseFactDtlDto[${p1}].proAssfFactorValue" id="proAssfFactorValue${p1}" cssClass="form-control changeParameterClass mandColorClass factor" onchange="enabledisable(this,0);">
										<form:option value="0" code="">select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
<%-- 										<form:option code="${lookUp.lookUpCode}" value="${lookUp.lookUpId}" selected="${lookUp.lookUpId == factor.getProAssfFactorValue() ? 'selected' : ''}">${lookUp.lookUpDesc}</form:option>
 --%>										<form:option code="${lookUp.lookUpCode}" value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>										
																				
										</c:forEach>
										</form:select> 
	
							</td>
						  <c:set var="p1" value="${p1+1}" > </c:set>
							<td class="col-sm-2 text-center col-sm-2">
									<a href="javascript:void(0);" title="Add" class="unitSpecificAdd btn btn-success btn-sm" onclick="addUnitRow(${status.count-1})"><i class="fa fa-plus-circle"></i></a>			
	 								<a href="javascript:void(0);" title="Delete" class="unitSpecificRem btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></a> 
									
							</td>
                        </tr>
                        </c:forEach>                         
          	</tbody>         	  
           </table>
 </div>
