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
<!-- End JSP Necessary Tags -->

<script src="js/property/propertyAddressDetails.js"></script>


<style>
  #hideBillingDetails{display:none;}  
</style>
 
<div class="accordion-toggle">
		<h4 class="margin-top-10 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
 			</h4>
	<div class="table-responsive">
				<c:set var="d" value="0" scope="page" />
			
                <table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>
                        <tr>
                        	<th width="11%" class="required-control"><spring:message code="unitdetails.year"/></th>
                            <th width="5%" class="required-control"><spring:message code="unitdetails.unitno"/></th>
<%--                             <th width="9%" class="required-control"><spring:message code="unitdetails.unittype"/></th> --%>
                            <th width="13%" class="required-control"><spring:message code="unitdetails.floorno"/></th>
                            <th width="13%" class="required-control"><spring:message code="unitdetails.ConstcompletionDate"/></th>
                            <th width="15%" class="required-control"><spring:message code="unitdetails.constructiontype"/></th>
                            <apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix=""
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false" hasTableForm="true" showData="false" columnWidth="10%" />
                     	 	<th width="8%" class="required-control"><spring:message code="unitdetails.taxable"/></th>
                     	 	<th width="8%" class="required-control"><spring:message text="Subdivision Group"/></th>
                     	 	<th width="8%" class="required-control"><spring:message text="Action"/></th>
                     	 	<th width="8%" class="required-control"><spring:message text="Tax Details"/></th>
                        	 
<!--                         	<th colspan="2"><a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i class="fa fa-plus-circle"></i></a></th> -->
                        
                        </tr>
                                              
                    	<tr class="firstUnitRow">
                       
                      		 <td>                                       	
                        	    <form:select path="" id="year0" class="form-control disabled text-center mandColorClass displayYearList" data-rule-required="true">
		  						<form:option value="0" label="Select Year"></form:option>
		  						<c:forEach items="${command.financialYearMap}" var="yearMap">
		  						<form:option value="${yearMap.key}" label="${yearMap.value}"></form:option>
		  						</c:forEach>
		 						</form:select>
		 						<form:hidden path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].faYearId" id="hiddenYear0"/>
<%-- 		 					<form:hidden path="leastFinYear" id="minYear"/>	 --%>
		 						    
		 					</td>
                        	 
                        	 <td>
              					<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUnitNo" type="text" class="form-control  text-center unit required-control" id="unitNo0" value="1" data-rule-required="true" readonly="true" />
              				</td>
						
							<%-- <td>
								<c:set var="baseLookupCode" value="UTP" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUnitTypeId" cssClass="form-control changeParameterClass "
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select Unit Type"  hasTableForm="true" isMandatory="true" />
							</td> --%>
						
							<td>
								<c:set var="baseLookupCode" value="IDE" />
								<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdFloorNo" cssClass="form-control changeParameterClass "
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select Floor No" isMandatory="true" hasTableForm="true" />
							</td>
						
							<td>
		 						<div class="input-group"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdYearConstruction" class="form-control datepicker2 mandColorClass dateClass" id="yearOfConstruc0" data-rule-required="true" placeholder="DD/MM/YYYY" />									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
								
							</td>
                            <td> 
                            	<c:set var="baseLookupCode" value="CSC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdConstruType" cssClass="form-control changeParameterClass mandColorClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select Construction Type" isMandatory="true" hasTableForm="true"/>
							</td>
							
								
           					<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false" hasTableForm="true" showData="true" columnWidth="10%"/>
					
							<td width="150"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdBuildupArea" type="text" class="form-control has2Decimal mandColorClass text-right" id="taxableArea0" data-rule-required="true" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'proAssdBuildupArea')" placeholder="999999.99"/></td>
                           
<!-- 	                         <td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-0" aria-expanded="false" aria-controls="group-of-rows-0"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                            -->
<!-- 							<td class="text-center"><a href="javascript:void(0);" class="remCF btn btn-danger btn-xs delete"  id="deleteRow_"><i class="fa fa-minus-circle"></i></a></td> -->
							
                       	</tr> 
                    
			      <tr class="secondUnitRow collapse in" id="group-of-rows-0">
<!-- 			            <td colspan="11"> -->

<%-- 			               <legend class="text-blue-3 text-left"><spring:message code="unitdetails.AdditionalUnitDetails" /> --%>
<!-- 							</legend> -->
            
<!-- 				             <div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12"> -->
<!-- 				             	<div class="form-group" >    -->
           			
<%-- 			           			<label for="road-type" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.RoadType"/> </label> --%>
<!-- 			            		<div class="col-sm-2"> -->

<%-- 		           				<c:set var="baseLookupCode" value="RFT" /> --%>
<%-- 								<apptags:lookupField  items="${command.getLevelData(baseLookupCode)}" --%>
<%-- 		 									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdRoadFactor" cssClass="form-control changeParameterClass "  --%>
<%-- 		 									hasChildLookup="false" hasId="true" showAll="false"  --%>
<%-- 											selectOptionLabelCode="Select Road Factor" isMandatory="true" hasTableForm="true"  /> --%>
<!-- 		                		</div>  -->
<%-- 								<label for="occupancy" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.occupancy"/> </label> --%>
<!-- 			               		<div class="col-sm-2">	 -->
	
<%-- 		           				<c:set var="baseLookupCode" value="OCS" />  --%>
<%-- 		 						<apptags:lookupField --%>
<%-- 											items="${command.getLevelData(baseLookupCode)}"  --%>
<%-- 											path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdOccupancyType" cssClass="form-control changeParameterClass "  --%>
<%-- 											hasChildLookup="false" hasId="true" showAll="false" --%>
<%-- 		 									selectOptionLabelCode="Select Occupancy Type" isMandatory="true" changeHandler="occupancyTypeChange('');" hasTableForm="true"/> --%>
<!-- 	 							</div> -->
							
<!-- 							<div id="monthlyRentField"> -->
							
<%-- 							<label for="proAssdAnnualRent" class="col-sm-2 control-label"><spring:message code="property.rent"/></label>	 							 --%>
<!-- 								<div class="col-sm-2"> -->
<%-- 								<form:input cssClass="form-control text-right" --%>
<%-- 											onkeypress="return hasAmount(event, this, 15, 2)" --%>
<%-- 											id="proAssdAnnualRent" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdAnnualRent" --%>
<%-- 											placeholder="999999.99" --%>
<%-- 											onchange="getAmountFormatInDynamic((this),'monthly-rent')"></form:input>	 							 --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							</div> -->
				
<!-- 							<div class="form-group"> -->
					    
<%-- 			          		    <label for="occupier-name" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label> --%>
<%-- 			          		    <div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierName" type="text" class="form-control hasSpecialChara hasNoSpace" id="occupierName" /></div> --%>
			          		
<!-- 			          		</div>				 -->
<!-- 						</div> -->
<!-- 			         </td> -->
			         </tr>                		
                  </tbody>
                </table>                
			</div>    
</div>

