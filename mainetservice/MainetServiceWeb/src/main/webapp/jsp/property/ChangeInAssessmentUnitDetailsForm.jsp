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
<script src="js/property/changeUnitDetails.js" type="text/javascript"></script>
<script src="js/property/selfAssessment.js" type="text/javascript"></script>
<script src="js/property/unitSpecificAdditionalInfo.js" type="text/javascript"></script>
<script src="js/property/unitDetails.js" type="text/javascript"></script>

<div class="accordion-toggle">
<h4 class="margin-top-0 margin-bottom-10 panel-title">
<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
</h4>
<form:hidden  path="noOfDaysEditableFlag"  id="noOfDaysEditableFlag"/>
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
<%--                             <th width="8%" class="required-control"><spring:message code="unitdetails.unittype"/></th>
 --%>                            <th width="15%" class="required-control"><spring:message code="unitdetails.floorno"/></th>
                            <th width="15%" class="required-control"><spring:message code="unitdetails.ConstcompletionDate"/></th>
                            <th width="15%"><spring:message code="" text="Last Assessment Date"/></th>
<%--                             <th width="14%" class="required-control"><spring:message code="property.changeInAss.EffectiveDate"/></th>           
 --%>                            <th width="15%" class="required-control"><spring:message code="unitdetails.constructiontype"/></th>
                            <apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix=""
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false" hasTableForm="true" showData="false" columnWidth="13%" />
                     	 	<th width="12%" class="required-control"><spring:message code="unitdetails.taxable"/></th>

								<c:if test="${command.noOfDaysEditableFlag ne 'Y'}">
									<th colspan="2"><a href="javascript:void(0);" title="Add"
										class="addCFCIA btn btn-success btn-sm unit" id="addUnitRow"><i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
								<c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
									<th colspan="2"><a href="javascript:void(0);" title="Add"
										class="addCFCIA btn btn-success btn-sm unit" id="addUnitRow"><i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
							</tr>
                          
       					<c:forEach var="unitDetails" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                                            
                    	<tr class="firstUnitRow">                     
                      		 <td>
                      			<c:set value="" var="year"></c:set>                                  	
                        	    <form:select path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].faYearId" id="year${status.count-1}" class="form-control disabled text-center mandColorClass displayYearList">
		  						<form:option value="0" label="Select Year"></form:option>
		  						<c:forEach items="${command.financialYearMap}" var="yearMap">
		  						<form:option value="${yearMap.key}" selected="${year == yearMap.key ? 'selected' : ''}">${yearMap.value}</form:option>
		  						</c:forEach>
		 						</form:select>
		 						<form:hidden path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].faYearId" id="hiddenYear${status.count-1}"/>		 						    
		 						<form:hidden path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].firstAssesmentDate" id="hiddenFirstAssesmentDate${status.count-1}"/>			
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
										selectOptionLabelCode="Select Floor No" isMandatory="true" hasTableForm="true" />
							</td>
						
							<td>
		 						<div class="input-group successErrorCheck"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdYearConstruction" class="form-control datepicker2 mandColorClass dateClass " id="yearOfConstruc${status.count-1}" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
							</td>
							
							<td>
		 						<div class="input-group successErrorCheck"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].lastAssesmentDate" class="form-control datepicker2 mandColorClass dateClass " id="lastAssesmentDate${status.count-1}" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" disabled="true"/>									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
							</td>
							
						<%-- 	<td>
		 						<div class="input-group"> 
								<form:input type="text" path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assEffectiveDate" class="form-control datepicker2 mandColorClass dateClass " id="proAssEffectiveDate${status.count-1}" data-rule-required="true" placeholder="DD/MM/YYYY" />									
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span></div>
								
							</td> --%>
							
                            <td> 
                            	<c:set var="baseLookupCode" value="CSC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdConstruType" cssClass="form-control changeParameterClass mandColorClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select Construction Type" isMandatory="true" hasTableForm="true"/>
							</td>
							<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false" hasTableForm="true" showData="true" columnWidth="10%"/>
					
							<td width="150"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdBuildupArea" type="text" class="form-control has2Decimal mandColorClass text-right" id="taxableArea${status.count-1}" data-rule-required="true" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'proAssdBuildupArea')" placeholder="999999.99"/></td>
                           
	                         <td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-${status.count-1}" aria-expanded="false" aria-controls="group-of-rows-${status.count-1}"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
							 <c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
							 <td class="text-center"><a href="javascript:void(0);" class="remCF btn btn-danger btn-xs delete" id="deleteRow_"><i class="fa fa-minus-circle"></i></a></td>
							</c:if>
							
							<c:if test="${command.noOfDaysEditableFlag ne 'Y'}">
							<td class="text-center"><a href="javascript:void(0);" class="remCF btn btn-danger btn-xs delete" id="deleteRow_"><i class="fa fa-minus-circle"></i></a></td>
							</c:if>
                       	</tr> 
                    
			      		<tr class="secondUnitRow collapse in" id="group-of-rows-${status.count-1}">
			           		 <td colspan="11">

			              	 <legend class="text-blue-3 text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
							 </legend>
            
				             <div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12 padding-0 overflow-hidden">
				             	<div class="form-group" >   
           			
				           			<%-- <label for="road-type" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.RoadType"/> </label>
				            		<div class="col-sm-2">    		
				           				<c:set var="baseLookupCode" value="RFT" />
										<apptags:lookupField  items="${command.getLevelData(baseLookupCode)}"
				 									path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdRoadFactor" cssClass="form-control changeParameterClass " 
				 									hasChildLookup="false" hasId="true" showAll="false" 
													selectOptionLabelCode="Select Road Factor" isMandatory="true" hasTableForm="true"  />
			                		</div>  --%>
										<label for="occupancy" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.occupancy"/> </label>
				               		<div class="col-sm-4">	
				           				<c:set var="baseLookupCode" value="OCS" /> 
				 						<apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" 
													path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdOccupancyType" cssClass="form-control changeParameterClass " 
													hasChildLookup="false" hasId="true" showAll="false"
				 									selectOptionLabelCode="Select Occupancy Type" isMandatory="true" hasTableForm="true" changeHandler="loadOccupierName(this)"/> 
		 							</div>
										<%-- <label for="proAssdAnnualRent${status.count-1}" class="col-sm-2 control-label"><spring:message code="property.rent"/></label>	 							
									<div class="col-sm-2">
										<form:input cssClass="form-control has2Decimal text-right"
																	onkeypress="return hasAmount(event, this, 15, 2)"
																	id="proAssdAnnualRent${status.count-1}"  path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdAnnualRent"
																	placeholder="999999.99"
																	onchange="getAmountFormatInDynamic((this),'monthly-rent')"></form:input>	 							
									</div> --%>
									
									<label for="occupier-name${status.count-1}" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
				          		    <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control hasCharacter hasNoSpace" id="occupierName${status.count-1}" /></div>
								</div>
				
								<%-- <div class="form-group">					 
				 					<label for="occupier-name${status.count-1}" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
				          		    <div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control hasCharacter hasNoSpace" id="occupierName${status.count-1}" /></div>
			          			</div> --%>	
			          			
			          			<div class="form-group">
			          			
			          			<apptags:lookupFieldSet baseLookupCode="PTP" hasId="true"
									showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfproperty"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control required-control" showAll="false"  columnWidth="10%"/>
	
								</div>			
						</div>
			        	 </td>
			         	</tr> 
				</c:forEach>               		
                </tbody>
                </table>                  
</div>                        
 			 <div class="form-group">

				</div>	 
				<c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
				<div class="form-group">
					<div class="col-sm-6">
						<label for="" class="radio-inline">
						<form:checkbox path="" value="" onchange="displayDetailsTillCurrentYear()" id="checkDetail" />
						<spring:message code="unitdetails.changeInunitdetails"/>
						</label>
					</div>
				</div>
				</c:if>
 		</c:when>
</c:choose>
</div>
</div>

<!-- D#99306 -->
<c:if test="${command.getFactorNotApplicable() ne 'NA'}"> 
<div class="accordion-toggle">           
<h4 class="margin-top-0 margin-bottom-10 panel-title">
<a data-toggle="collapse" href="#unitSpecificInfo"><spring:message code="unit.UnitSpecificAddiInfo"/></a>
 </h4>
<div class="panel-collapse collapse in" id="unitSpecificInfo">      
 			<div class="form-group">
    		<c:set var="p1" value="0" > </c:set>																																							
            <c:forEach items="${command.getDisplayfactorMap()}" var="entry" varStatus="key">
            <c:set var="taxValue" value="${entry.value}"/>
            <c:set var="prefix"  value='FCT' />
            <c:set var="id"  value="${entry.key}" />
            <c:set var="lookup" value="${command.getdesc(id,prefix)}" />
            <!-- D#99306 -->
            <c:if test="${lookup.lookUpCode ne 'NA' }">
<%--             <label class="col-sm-8 control-label" ><spring:message code="" text="Is ${lookup.lookUpDesc} Applicable?"/></label> --%>
	        <label class="col-sm-8 control-label padding-top-0" ><spring:message code="property.Is" text="Is "/>${lookup.lookUpDesc} <spring:message  code="property.Applicable" text=" Applicable?"/></label>

          	<c:if test="${taxValue eq null}">
             <apptags:radio cssClass="col-sm-4 addInfo" radioLabel="Yes,No" radioValue="Y,N" labelCode="" path="provisionalAssesmentMstDto.proAssfactor[${key.count-1}]"  defaultCheckedValue="N" changeHandler="showAddInfo('${lookup.lookUpCode}','${key.count-1}')"></apptags:radio>	          
             																									
             </c:if>
             <c:if test="${taxValue ne null}">
             <apptags:radio cssClass="col-sm-4 addInfo" radioLabel="Yes,No" radioValue="Y,N" labelCode="" path="provisionalAssesmentMstDto.proAssfactor[${key.count-1}]"  defaultCheckedValue="Y" changeHandler="showAddInfo('${lookup.lookUpCode}','${key.count-1}')"></apptags:radio>	          
			 </c:if> 
			 

 			
 			 
			<div id="${lookup.lookUpCode}" class="eachFact"> 
		    <c:if test="${taxValue ne null}">
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
<%--   		                        	<form:hidden value="${factor.getProAssfFactorIdDesc()}" path="" id="proAssfFactorId"/> 
 --%> 		                      
 		                        	<form:hidden path="provAsseFactDtlDto[${p1}].assfFactorId" value="${factor.getAssfFactorId()}"/>
 		                        	 <form:hidden value="${factor.getFactorValueCode()}" path="" id="factPref${status.count-1}"/> 
		                        	
  	
									<c:set var="baseLookupCode" value="${factor.getFactorValueCode()}" />
										<form:select path="provAsseFactDtlDto[${p1}].assfFactorValueId" id="assfFactorValueId${p1}" cssClass="form-control changeParameterClass mandColorClass factor" onchange="enabledisable(this,0);">
										<form:option value="0" code="">select</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
<%-- 										<form:option code="${lookUp.lookUpCode}" value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>										
 --%>										<form:option  value="${lookUp.lookUpId}" selected="${lookUp.lookUpId == factor.getAssfFactorValueId() ? 'selected' : ''}">${lookUp.lookUpDesc}</form:option>										
										</c:forEach>
										</form:select> 
	
							</td>
						  <c:set var="p1" value="${p1+1}" > </c:set>
							<td class="col-sm-2 text-center col-sm-2">
									<a href="javascript:void(0);" title="Add" class="unitSpecificAdd btn btn-success btn-sm" onclick="addUnitRow(${status.count-1})"><i class="fa fa-plus-circle"></i></a>			
	 								<a href="javascript:void(0);" title="Delete" class="unitSpecificRem btn btn-danger btn-sm" id="deleteFactorTableRow_"><i class="fa fa-trash-o"></i></a> 
									
							</td>
                        </tr>
                        </c:forEach>                         
          	</tbody>         	  
           </table>
           </div>  
           </c:if>
               </div>   
               </c:if>
            </c:forEach>
            </div>
</div>
</div>
</c:if>
