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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/property/unitDetails.js" type="text/javascript"></script>
<script src="js/property/unitSpecificAdditionalInfo.js" type="text/javascript"></script>
<script type="text/javascript" src="js/property/ownershipDetailsForm.js"></script>
<style>
.addColor{
	background-color: #fff !important
}
 #unitDetailTable label{
 	font-size:0.7rem;
 }
</style>

<div class="accordion-toggle">
<h4 class="margin-top-0 margin-bottom-10 panel-title">
<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
</h4>
<form:hidden path="ConstructFlag" id="ConstructFlag"/>
<div class="panel-collapse collapse in" id="UnitDetail">
		<c:choose>
		<c:when test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}"> 
		<div class="form-group" style="margin-left: 0">
 			 		<spring:message code="property.propFloorDetail"/>
 		</div> 
		<div class="table-responsive">
		<c:set var="d" value="0" scope="page" />			
                <table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>                        
                        <tr>
                        	<th width="13%" class="required-control"><spring:message code="unitdetails.year"/></th>
                            <th width="5%" class="required-control"><spring:message code="unitdetails.unitno"/></th>
<%--                             <th width="9%" class="required-control"><spring:message code="unitdetails.unittype"/></th> --%>
                            <th width="13%" class="required-control"><spring:message code="unitdetails.floorno"/></th>
                             
                            <c:if test="${command.getConstructFlag() eq 'Y' }">
                             <th width="15%" class="required-control"><spring:message code="unitdetails.ConstcompletionDate"/></th> 
                             </c:if>
                            <th width="15%" class="required-control"><spring:message code="unitDetails.firstAssessmentDate"/></th>
                            <th width="15%" class="required-control"><spring:message code="unitdetails.constructiontype"/></th>
                            <apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false" hasTableForm="true" showData="false" columnWidth="12%" />
                     	 	<th width="12%" class="required-control"><spring:message code="unitdetails.taxable"/></th>
                     	 	
                        	 
                        	<th colspan="2"><a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i class="fa fa-plus-circle"></i></a></th>
                        
                        </tr>
                          
       					<c:forEach var="unitDetails" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                                            
                    	<tr class="firstUnitRow">
                  
                      		 <td>                                       	
                        	    <form:select path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].faYearId" id="year${status.count-1}" class="form-control disabled text-center mandColorClass displayYearList" disabled="true">
		  						<form:option value=""><spring:message code="prop.selectSelectYear"/></form:option>
		  						<c:forEach items="${command.financialYearMap}" var="yearMap">
		  						<form:option value="${yearMap.key}" label="${yearMap.value}"></form:option>
		  						</c:forEach>
		 						</form:select>
		 						<form:hidden path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].faYearId" id="hiddenYear${status.count-1}"/>		 						    
		 					</td>
                        	 
                        	 <td>
              					<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdUnitNo" type="text" class="form-control mandColorClass text-center unit required-control" id="unitNo${status.count-1}" data-rule-required="true" readonly="true" />
              				</td>
						
							<%-- <td>
								<c:set var="baseLookupCode" value="UTP" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdUnitTypeId" cssClass="form-control changeParameterClass mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select Unit Type"  hasTableForm="true" isMandatory="true" />
							</td> --%>
						
							<td>
								<c:set var="baseLookupCode" value="IDE" />
								<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdFloorNo" cssClass="form-control changeParameterClass mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="prop.selectFloorNo" isMandatory="true" hasTableForm="true" />
							</td>
						<c:if test="${command.getConstructFlag() eq 'Y' }">
							<td>
		 						<div class="input-group successErrorCheck"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdYearConstruction" class="form-control datepicker2 mandColorClass dateClass addColor" id="yearOfConstruc0" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" readonly="true"/>									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
							</td>
							</c:if>
							<td>
		 						<div class="input-group successErrorCheck"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].firstAssesmentDate" class="form-control lessthancurrdate mandColorClass dateClass addColor" id="firstAssesmentDate0" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" readonly="true"/>									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
							</td>
                            <td> 
                            	<c:set var="baseLookupCode" value="CSC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdConstruType" cssClass="form-control changeParameterClass mandColorClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="prop.selectConstType" isMandatory="true" hasTableForm="true"/>
							</td>
							<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false" hasTableForm="true" showData="true" columnWidth="10%"/>
					
							 <td width="150"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdBuildupArea" type="text" class="form-control has2Decimal mandColorClass text-right" id="taxableArea${status.count-1}" data-rule-required="true" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'proAssdBuildupArea')" placeholder="999999.99"/></td>
                           
	                         <td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-0" aria-expanded="false" aria-controls="group-of-rows-0"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
							 <td class="text-center"><a href="javascript:void(0);" class="remCF btn btn-danger btn-xs delete" id="deleteRow_"><i class="fa fa-minus-circle"></i></a></td>
							
                       	</tr> 
                    
			      		<tr class="secondUnitRow collapse in" id="group-of-rows-0">
			           		 <td colspan="11">

			              	 <legend class="text-blue-3 text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
							 </legend>
            
				             <div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
				             	<div class="form-group" >   
           			
				           			<%-- <label for="road-type" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.RoadType"/> </label>
				            		<div class="col-sm-2">    		
				           				<c:set var="baseLookupCode" value="RFT" />
										<apptags:lookupField  items="${command.getLevelData(baseLookupCode)}"
				 									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdRoadFactor" cssClass="form-control changeParameterClass " 
				 									hasChildLookup="false" hasId="true" showAll="false" 
													selectOptionLabelCode="Select Road Factor" isMandatory="true" hasTableForm="true"  />
			                		</div> --%> 
									<label for="occupancy" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.occupancy"/> </label>
				               		<div class="col-sm-4">	
				           				<c:set var="baseLookupCode" value="OCS" /> 
				 						<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" 
													path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdOccupancyType" cssClass="form-control changeParameterClass " 
													hasChildLookup="false" hasId="true" showAll="false"
				 									selectOptionLabelCode="prop.selectSelectOccupancyType" isMandatory="true" hasTableForm="true" changeHandler="loadOccupierName(this)"/> 
		 							</div>
		 							
		 							
										<%-- <label for="proAssdAnnualRent${status.count-1}" class="col-sm-2 control-label"><spring:message code="property.rent"/></label>	 							
										<div class="col-sm-2">
										<form:input cssClass="form-control text-right"
																	onkeypress="return hasAmount(event, this, 15, 2)"
																	id="proAssdAnnualRent${status.count-1}"  path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdAnnualRent"
																	placeholder="999999.99"
																	onchange="getAmountFormatInDynamic((this),'monthly-rent')"></form:input>	 							
										</div> --%>
										
										<label for="occupierName${status.count-1}" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label> 
 			          		   		<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" class="form-control hasCharacter preventSpace" id="occupierName${status.count-1}" maxlength="500"/></div>
									
								</div>
								
								<div class="form-group">
			          			
			          			<apptags:lookupFieldSet baseLookupCode="PTP" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfproperty"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false"  columnWidth="10%"/>
	
								</div>	
								<%-- <div class="form-group">
 			          				<label for="occupierName${status.count-1}" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label> 
 			          		   		<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" class="form-control hasCharacter preventSpace" id="occupierName${status.count-1}" maxlength="500"/></div>
			          			</div> --%>			
						</div>
			        	 </td>
			         	</tr> 
				</c:forEach>               		
                </tbody>
                </table>                
</div>                        
		       <div class="form-group">
					<div class="col-sm-6">
						<label for="" class="radio-inline">
						<form:checkbox path="" value="" onchange="displayDetailsTillCurrentYear()" id="checkDetail" />
						<spring:message code="unitdetails.changeInunitdetails"/>
						</label>
					</div>
				</div>
 			 	 
 		</c:when>
 
 			 	<c:otherwise>	
 			 		<div class="form-group" style="margin-left: 0">
 			 		<spring:message code="property.propFloorDetail"/>
 					</div>
				<div class="table-responsive">
				<c:set var="d" value="0" scope="page" />
			
                <table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>
                        <tr>
                        	<th width="13%" class="required-control"><spring:message code="unitdetails.year"/></th>
                            <th width="5%" class="required-control"><spring:message code="unitdetails.unitno"/></th>
<%--                             <th width="9%" class="required-control"><spring:message code="unitdetails.unittype"/></th> --%>
                            <th width="13%" class="required-control"><spring:message code="unitdetails.floorno"/></th>
                          
                       
                              <c:if test="${command.getConstructFlag() eq 'Y'}">
                      
                           <th width="15%" class="required-control"><spring:message code="unitdetails.ConstcompletionDate"/></th> 
                           </c:if>
                            <th width="15%" class="required-control"><spring:message code="unitDetails.firstAssessmentDate"/></th>
                            <th width="15%" class="required-control"><spring:message code="unitdetails.constructiontype"/></th>
                            <apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix=""
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false" hasTableForm="true" showData="false" columnWidth="12%" />
                     	 	<th width=" 12%" class="required-control"><spring:message code="unitdetails.taxable"/></th>
                        	 
                        	<th colspan="2"><a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i class="fa fa-plus-circle"></i></a></th>
                        
                        </tr>
                                              
                    	<tr class="firstUnitRow">
                   
                      		 <td>                                       	
                        	    <form:select path="" id="year0" class="form-control disabled text-center mandColorClass displayYearList" data-rule-required="true">
		  						<form:option value=""><spring:message code="prop.selectYear"/></form:option>
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
										selectOptionLabelCode="prop.selectFloorNo" isMandatory="true" hasTableForm="true" />
							</td>
						<c:if test="${command.getConstructFlag() eq 'Y'}">
							<td>
		 						<div class="input-group successErrorCheck"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdYearConstruction" class="form-control datepicker2 mandColorClass dateClass addColor" id="yearOfConstruc0" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" readonly="true"/>									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
								
							</td>
							</c:if>
							<td>
		 						<div class="input-group successErrorCheck"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].firstAssesmentDate" class="form-control lessthancurrdate mandColorClass dateClass addColor" id="firstAssesmentDate0" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" readonly="true"/>									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
								
							</td>
                            <td> 
                            	<c:set var="baseLookupCode" value="CSC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdConstruType" cssClass="form-control changeParameterClass mandColorClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="prop.selectSelectConstructionType" isMandatory="true" hasTableForm="true"/>
							</td>
							
								
           					<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false" hasTableForm="true" showData="true" columnWidth="10%"/>
					
							<td width="150"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdBuildupArea" type="text" class="form-control has2Decimal mandColorClass text-right" id="taxableArea0" data-rule-required="true" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'proAssdBuildupArea')" placeholder="999999.99"/></td>
                           
	                         <td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-0" aria-expanded="false" aria-controls="group-of-rows-0"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
							<td class="text-center"><a href="javascript:void(0);" class="remCF btn btn-danger btn-xs delete"  id="deleteRow_"><i class="fa fa-minus-circle"></i></a></td>
							
                       	</tr> 
                    
			      <tr class="secondUnitRow collapse in" id="group-of-rows-0">
			            <td colspan="11">

			               <legend class="text-blue-3 text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
							</legend>
            
				             <div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
				             	<div class="form-group" >   
           			
				           			<%-- <label for="road-type" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.RoadType"/> </label>
				            		<div class="col-sm-2">
	
			           				<c:set var="baseLookupCode" value="RFT" />
									<apptags:lookupField  items="${command.getLevelData(baseLookupCode)}"
			 									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdRoadFactor" cssClass="form-control changeParameterClass " 
			 									hasChildLookup="false" hasId="true" showAll="false" 
												selectOptionLabelCode="Select Road Factor" isMandatory="true" hasTableForm="true"  />
			                		</div> --%> 
									<label for="occupancy" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.occupancy"/> </label>
				               		<div class="col-sm-4">	
		
			           				<c:set var="baseLookupCode" value="OCS" /> 
			 						<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" 
												path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdOccupancyType" cssClass="form-control changeParameterClass " 
												hasChildLookup="false" hasId="true" showAll="false"
			 									selectOptionLabelCode="prop.selectSelectOccupancyType" isMandatory="true" hasTableForm="true" changeHandler="loadOccupierName(this)"/>
		 							</div>
								
									<%-- <label for="proAssdAnnualRent" class="col-sm-2 control-label"><spring:message code="property.rent"/></label>	 							
									<div class="col-sm-2">
									<form:input cssClass="form-control text-right"
												onkeypress="return hasAmount(event, this, 15, 2)"
												id="proAssdAnnualRent" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdAnnualRent"
												placeholder="999999.99"
												onchange="getAmountFormatInDynamic((this),'monthly-rent')"></form:input>	 							
									</div> --%>
									
									<label for="occupier-name" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
									<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierName" type="text" class="form-control hasCharacter preventSpace" id="occupierName" maxlength="500"/></div>
									
							
								</div>
				
								<div class="form-group">
				          	
						          		<apptags:lookupFieldSet baseLookupCode="PTP" hasId="true"
									showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdNatureOfproperty"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control required-control" showAll="false"  columnWidth="10%"/>
								</div>
								<%-- <div class="form-group">
						    
				          		    <label for="occupier-name" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
				          		    <div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].occupierName" type="text" class="form-control hasCharacter preventSpace" id="occupierName" maxlength="500"/></div>
				          		
				          		</div>	 --%>			
						</div>
			         </td>
			         </tr>                		
                  </tbody>
                </table>                
			</div>                        
		       <div class="form-group">
					<div class="col-sm-6">
					<label for="" class="radio-inline">
						<form:checkbox path="" value="" onchange="displayDetailsTillCurrentYear()" id="checkDetail" />
							<spring:message code="unitdetails.changeInunitdetails"/>
					</label>
					</div>
					
					<form:hidden path="unitDetailTableCount" id="tblCount"/>								
				</div>
				
		</c:otherwise>
</c:choose>

            </div>
</div>


<c:if test="${command.getFactorNotApplicable() ne 'NA'}"> 
<div class="accordion-toggle">           
<h4 class="margin-top-0 margin-bottom-10 panel-title">
<a data-toggle="collapse" href="#unitSpecificInfo"><spring:message code="unit.UnitSpecificAddiInfo"/></a>
 </h4>
<div class="panel-collapse collapse in" id="unitSpecificInfo">      
 			<div class="form-group">
 			<c:choose>
 			<c:when test="${not empty command.getProvAsseFactDtlDto()}">
 			 
 			<c:set var="baseLookupCode" value="FCT" /> 
            <c:forEach items="${command.getLevelData(baseLookupCode)}" var="factor" varStatus="status"><!--Question loop  -->
	            <label class="col-sm-8 control-label padding-top-0" ><spring:message code="property.Is" text="Is "/>${factor.lookUpDesc}<spring:message  code="property.Applicable" text=" Applicable?"/></label>
				<c:set value='${factor.lookUpCode}' var="facCode"></c:set>
				<c:set value='${factor.lookUpId}' var="factId"></c:set>
				
				<div class="col-sm-4">
				<label for="Yes" class="radio-inline">	
				<form:radiobutton class="factorSet"  path="provisionalAssesmentMstDto.proAssfactor[${status.count-1}]" value="Y"  id="Yes" onchange="showAddInfo('${facCode}','${status.count-1}')"/>
				<spring:message code="unitdetails.Yes"></spring:message></label>
				<label for="No" class="radio-inline">
				<form:radiobutton class="factorStatus" path="provisionalAssesmentMstDto.proAssfactor[${status.count-1}]" value="N"  id="No" onchange="showAddInfo('${facCode}','${status.count-1}')"/>
				<spring:message code="Unitdetails.No"></spring:message></label>
				</div> 
		
   			<c:set var="p1" value="1" > </c:set>
   			<div id="${facCode}" class="eachFact"> 
	    	<c:forEach items="${command.getProvAsseFactDtlDto()}" var="factableLoop" varStatus="status">    <!--loop to check weather data is available for question   -->
		   		<c:if test="${factableLoop.assfFactorId eq factId}">
	           	<c:if test="${p1 eq 1}">
	           	<div id="${facCode}" class="eachFact"> 
		          	<div class="padding-15 unitSpecificInfo">
		            <table id="unitSpecificInfoTable${facCode}" class="table table-striped table-bordered ">
		                    <tbody>
		                        <tr>
		                        	<th width="25%" class="required-control"><spring:message code="unit.ApplicableUnitNo"/></th>
		                            <th width="25%" class="required-control"><spring:message code="unit.FactorValue"/></th>
		                            <th width="15%"><spring:message code="unit.Add/Delete"/></th>                       	 
		                        </tr>
		                        
				       		<c:forEach items="${command.getProvAsseFactDtlDto()}" var="details" varStatus="status">    
				            <c:if test="${details.assfFactorId  eq factId}">
		                        
		                        <tr class="specFact">  
			                       	<td class="col-sm-5">    
			                       	<form:select id="unitNoFact0" class="form-control mandColorClass selectUnit" path="provAsseFactDtlDto[${status.count-1}].unitNoFact" onchange="resetFactorValue(this,0);">                   		                        		
											<form:option value="0">Select Option</form:option>
											<form:option value="ALL">All</form:option>
											
											<c:if test="${command.maxUnit gt 0}">
											<c:forEach  var="i" begin="1" end="${command.maxUnit}">
												<form:option value="${i}">${i}</form:option>	
											</c:forEach>
											</c:if>
											<%-- <form:option value="${details.unitNoFact}">${details.unitNoFact}</form:option>  --%>								
									</form:select>
			                        </td> 
			                        
			                                        
		                        	<td class="col-sm-5 ">
		                        		<form:hidden path="provAsseFactDtlDto[${status.count-1}].assfFactorId" value="${factId}"/>
		                        	 	<form:hidden path="provAsseFactDtlDto[${status.count-1}].factorValueCode" id="factPref" value="${facCode}"/>
		                        	
										<c:set var="baseLookupCode" value="${details.getFactorValueCode()}" />
										<form:select path="provAsseFactDtlDto[${status.count-1}].assfFactorValueId" 
										id="FactorValue0" cssClass="form-control changeParameterClass mandColorClass factor" onchange="enabledisable(this,0);">
										 <form:option value="0" code="">select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
										<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										
										</form:select>
									</td>
									
									<td class="col-sm-2 text-center col-sm-2">
										<a href="javascript:void(0);" title="Add" class="unitSpecificAdd btn btn-success btn-sm" onclick="addUnitRow('0')"><i class="fa fa-plus-circle"></i></a>								
		 								<a href="javascript:void(0);" title="Delete" class="unitSpecificRem btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></a>  
										
									</td>
								<%-- 	<form:hidden path="unitStatusCount" id="UnitInfotblCount"/> --%>
		                        </tr>
		                        		</c:if>
		     				</c:forEach>
		                    </tbody>
		            </table>
				</div>
	           </div>   
	           </c:if>
         		<c:set var="p1" value="${p1+1}" > </c:set>            
           		</c:if>
            </c:forEach>
            </div>
            </c:forEach>
 			 </c:when>
			
			<c:otherwise>					
	            <c:set var="baseLookupCode" value="FCT" /> 
	            <c:set var="d" value="0" />
	            <c:forEach items="${command.getLevelData(baseLookupCode)}" var="factor" varStatus="status">
	            	<!-- if check  lookUpCode is NA then ignore -->
	            	<c:if test="${factor.lookUpCode ne 'NA' }">
	            	
		          	<label class="col-sm-8 control-label padding-top-0" ><spring:message code="property.Is" text="Is "/>${factor.lookUpDesc} <spring:message  code="property.Applicable" text=" Applicable?"/></label>
					<c:set value='${factor.lookUpCode}' var="facCode"></c:set>
					
					<div class="col-sm-4 factorQes" >
					<label for="Yes" class="radio-inline">	
					<form:radiobutton class="factorSet selectFactor" path="provisionalAssesmentMstDto.proAssfactor[${d}]" value="Y"  id="Yes"  onchange="showAddInfo('${facCode}','${d}')"/>
					<spring:message code="unitdetails.Yes"></spring:message></label>
					<label for="No_" class="radio-inline">
					<form:radiobutton class="factorStatus selectFactor" path="provisionalAssesmentMstDto.proAssfactor[${d}]" value="N"  id="No_"  onchange="showAddInfo('${facCode}','${d}')"/>
					<spring:message code="Unitdetails.No"></spring:message></label>
					</div>  
					  
		           	<div id="${facCode}" class="eachFact"> 
		          
		           	</div>
		           	<c:set var="d" value="${d+1}" />  
		           	</c:if>          
	            </c:forEach>
	          <%--   <form:hidden path="unitStatusCount" id="factorValue"/> --%>
            </c:otherwise>
            </c:choose>
            </div>
</div>
</div>
</c:if>