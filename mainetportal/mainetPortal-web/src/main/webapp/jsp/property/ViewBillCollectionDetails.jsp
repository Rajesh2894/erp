<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/viewPropertyDetails.js"></script>
<style>
@media print {
	@page {
		size: A4 landscape;
		margin: 0.5cm;
	}
	#btnPrint {
		display: none;
	}
	#back {
		display: none;
	}
	
}
</style>
<!-- End JSP Necessary Tags -->

<div class="widget">
     <%--   <div class="widget-header">
			<h2><strong><spring:message code="propertyView.SelfAssesmentView" /></strong></h2>	
		</div> --%>	
		
        <div class="widget-content padding">	
		<form:form action="ViewPropertyDetail.html"
					class="form-horizontal form" name="ViewPropertyDetail"
					id="ViewPropertyDetail">	
			<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div> --%>

			<!-- Start Each Section -->
			<c:if test="${command.getIntgrtionWithBP() eq 'Y'}">
			<div class="form-group">
				<apptags:input labelCode="property.buildingpermission" path="" isDisabled="true"></apptags:input> 
					<apptags:input labelCode="property.LandPermissionNo." path="" isDisabled="true"></apptags:input>
			</div>
			 </c:if>
			<div class="form-group">
			<apptags:input labelCode="propertydetails.PropertyNo." path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>
			
			</div>
      
<!-------------------------------------Owner Details (First owner will be the primary owner)----------------------------------------------->
           
    <div class="accordion-toggle ">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
				code="property.Ownershipdetail" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="OwnshipDetail">
			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
				<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" disabled="true"/>
				<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/>
				
				</div>
		    </div>
		   
		    
		<c:choose>
		<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
	 	<table id="singleOwnerTable" class="table table-striped table-bordered ">
        <tbody>
        <tr>
                   
        <th width="20%"><spring:message code="ownersdetail.ownersname" /></th>
		<th width="9%"><spring:message code="ownersdetail.gender" /></th>
		<th width="9%"><spring:message code="ownerdetails.relation" /></th>
		<th width="20%"><spring:message code="ownersdetails.GuardianName" /></th>
		<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
		<th width="10%"><spring:message code="property.email" /></th>
		<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
		<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
        </tr>
                		
      <tr>
		<td>
			<form:input id="assoOwnerName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/>
		</td>
									
		<td class="ownerDetails">
				<form:input id="ownerGender" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssGenderId"   class="form-control" disabled="true"/>				
		</td>
									
		<td class="ownerDetails">		
			<form:input id="ownerRelation" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssRelationId"  class="form-control" disabled="true"/>
		</td>
							
		<td class="mand">
			<form:input id="assoGuardianName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control" disabled="true" />   
		</td>
	
		<td>
			<form:input id="assoMobileno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true"/>   
		</td>
		
		<td><form:input id="emailId" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" disabled="true" />   
		</td>
									
		<td class="ownerDetails">
			<form:input id="assoAddharno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control "  disabled="true"/>
		</td>
		<td class="companyDetails">
			<form:input id="assoPanno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control"  disabled="true"/>
		</td>
	</tr>
	</tbody>
    </table>
	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
		 <c:set var="a" value='0'/>
		 
					<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
						
								<tr>
									<th width="17%"><spring:message code="ownersdetail.ownersname" /></th>
									<th width="10%"><spring:message code="ownersdetail.gender" /></th>
									<th width="8%" ><spring:message code="ownerdetails.relation" /></th>
									<th width="17%"><spring:message code="ownersdetails.GuardianName" /></th>
									<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
									<th width="11%"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
								</tr>
						<tbody>
						<c:forEach var="ownershipTypeList" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">
            			<tr>
                		 <td>${ownershipTypeList.assoOwnerName}</td>
                		 <td>${ownershipTypeList.proAssGenderId}</td>
                		 <td>${ownershipTypeList.proAssRelationId}</td>
                		 <td>${ownershipTypeList.assoGuardianName}</td>
                		 <td>${ownershipTypeList.propertyShare}</td>
                		 <td>${ownershipTypeList.assoMobileno}</td>
                		 <td>${ownershipTypeList.eMail}</td>                		 
                		 <td>${ownershipTypeList.assoAddharno}</td>
                		 <td>${ownershipTypeList.assoPanno}</td>
            			</tr>
                		 </c:forEach>
						</tbody>
					</table>
					</c:when>
				<c:otherwise>
				<table id="companyDetailTable" class="table text-left table-striped table-bordered">
					<tbody>					
						<tr>
									<th width="25%"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
									<th width="25%"><spring:message code="ownersdetail.contactpersonName" /></th>
									<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						
 						<tr>
 						<td> 
 						<form:input id="assoOwnerName_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/> 
 						</td> 
						
 						<td> 
 						<form:input id="assoGuardianName_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control " disabled="true" />  
						</td> 
								
 						<td><form:input id="assoMobileno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true" />   
 						</td> 
 						
 						<td><form:input id="emailId_${d}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
						</td>
								
						<td class="companyDetails"> 
						<form:input id="assoPanno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" disabled="true" /> 
						</td> 
 						</tr> 								
					</tbody>
					</table>				
					</c:otherwise>	
					</c:choose>
				 </div>
				 
<!---------------------------------------------------------------Land Details----------------------------------------------------------------->
            
				<form:hidden path="assType" id="assType"/>
				 
				<%-- <h4 class="margin-top-10 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.LandDetail" /></a>
				</h4> 
				<div class="panel-collapse collapse in" id="propertyDetail">--%>				
				
						<div class="form-group margin-top-10">
								<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20" isReadonly="true"></apptags:input>				
								<apptags:input cssClass="alphaNumeric preventSpace" labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc"  isReadonly="true"></apptags:input>
			           			<form:hidden  path="landTypePrefix" class="form-control landValue"/> 		
						</div>
						
<c:choose>
	 <c:when test="${command.getLandTypePrefix() eq 'KPK'}">
		<div class="form-group">		
		
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>
		</div>
		<div class="form-group">						
 			<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
			<label class="col-sm-2 control-label required-control"
									for="khasra"><spring:message code="propertydetails.csnno" text="Khasra No"/></label>
				<div class="col-sm-4">									
				<form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass" id="khasara" disabled="true"/>
				</div>
		</div>
		
		<div class="form-group">
		<div class="text-center padding-top-10">
		<c:if test="${command.getAssType() eq 'VIW'}">  
		<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch Land Details"/></button> 
		</c:if>
		
		</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
		
	
</c:when>

<c:when test="${command.getLandTypePrefix() eq 'NZL'}">
		<div class="form-group">			
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>		
		</div>
			
		<div class="form-group">			
				<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
				<apptags:input labelCode="Mohalla" path="provisionalAssesmentMstDto.mohallaDesc" isDisabled="true"></apptags:input> 								
		</div>	
		
		<div class="form-group">
				<apptags:input labelCode="propertydetails.streetno" path="provisionalAssesmentMstDto.assStreetNoDesc" isDisabled="true"></apptags:input> 												
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
		</div>		
		
		<div class="form-group">
			<div class="text-center padding-top-10">
		 	<c:if test="${command.getAssType() eq 'VIW'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch Land Details"/></button> 
			</c:if>	 
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>

</c:when>
	


<c:when test="${command.getLandTypePrefix() eq 'DIV'}">

		<div class="form-group">			
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>		
		</div>
			
		<div class="form-group">			
				<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
				<apptags:input labelCode="Mohalla" path="provisionalAssesmentMstDto.mohallaDesc" isDisabled="true"></apptags:input> 								
		</div>	
		
		<div class="form-group">
				<apptags:input labelCode="propertydetails.streetno" path="provisionalAssesmentMstDto.assStreetNoDesc" isDisabled="true"></apptags:input> 												
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
		</div>	
		
		<div class="form-group">
			<div class="text-center padding-top-10">
		 	<c:if test="${command.getAssType() eq 'VIW'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch Land Details"/></button> 
			</c:if>	 
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
 
</c:when>
</c:choose>
<!-- </div>  -->
       
            	
<!-------------------------------------------------------Property Address Details------------------------------------------->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#propertyAddress"><spring:message
				code="property.Propertyaddress" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="propertyAddress">
			<div class="form-group">
					<apptags:textArea labelCode="property.propertyaddress" path="provisionalAssesmentMstDto.assAddress" isDisabled="true"></apptags:textArea>
					<apptags:input labelCode="property.location" path="provisionalAssesmentMstDto.locationName" isDisabled="true"></apptags:input>	
						
            </div> 
           
            <div class="form-group">
            		<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assPincode" isDisabled="true"></apptags:input>
            		<%-- <apptags:input labelCode="property.email" path="provisionalAssesmentMstDto.assEmail" isDisabled="true"></apptags:input> --%>
<%--             		<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>  	 --%>
      
            </div>
            </div>
                
<!-----------------------------------------------------Correspondence Address Details------------------------------------------------->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
				code="property.correspondenceaddress" /></a>
			</h4>
            
			<div class="panel-collapse collapse in" id="correspondenceAddress">	
			<div class="form-group">    
					<apptags:textArea labelCode="property.correspondenceaddress" path="provisionalAssesmentMstDto.assCorrAddress" isDisabled="true"></apptags:textArea>
					<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assCorrPincode" isDisabled="true"></apptags:input>
	
            </div>            
         
          </div>
   
        	
<!----------------------------------------------------------Tax-Zone details------------------------------------------------------ --> 
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#taxZone"><spring:message
				code="property.taxZoneDetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="taxZone">
			<div class="panel-body">
				<div class="form-group">
			
				<apptags:input labelCode="property.propertyZone" path="provisionalAssesmentMstDto.assWardDesc1" isDisabled="true"></apptags:input>
				
				<c:if test="${command.provisionalAssesmentMstDto.assWard2 ne null}">			
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc2" isDisabled="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<c:if test="${command.provisionalAssesmentMstDto.assWard3 ne null}">
				
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc3" isDisabled="true"></apptags:input>
				</c:if>
				<c:if test="${command.provisionalAssesmentMstDto.assWard4 ne null}">
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc4" isDisabled="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<c:if test="${command.provisionalAssesmentMstDto.assWard5 ne null}">
				
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc5" isDisabled="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>
				</div>
			
			</div>
			</div>
        	
<!-------------------------------------------------------Land / Building Details----------------------------------------------------- -->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#BuildingDetails"><spring:message
				code="property.buildingdetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="BuildingDetails">
		
			<div class="form-group">
				<apptags:input labelCode="property.yearofacquisition" path="provisionalAssesmentMstDto.proAssAcqDateFormat" isDisabled="true"></apptags:input>
				<apptags:input labelCode="property.totalplot" path="provisionalAssesmentMstDto.assPlotArea" isDisabled="true"></apptags:input>
			</div>
		
		</div>

<!----------------------------------------------------------Unit Details------------------------------------------------------------>
 <c:if test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}"> 
		
 			<h4 class="margin-top-10 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
 			</h4>
			<div class="panel-collapse collapse in" id="UnitDetail">
			<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    
                        <tr>
 							<th width="7%"><spring:message code="unitdetails.year"/></th>
                        	<th width="4%"><spring:message code="unitdetails.unitno"/></th> 	
                        	<th width="10%"><spring:message code="unitdetails.floorno"/></th>
                        	<th width="10%"><spring:message code="unitdetails.dateofConstruction"/></th>
                        	<th width="20%"><spring:message code="unitdetails.constructiontype"/></th>
                        	<th width="12%"><spring:message code="unitdetails.usagefactor"/></th>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype2 ne null }">
                        	<th width="12%"><spring:message code="unitdetails.usagesubtype"/></th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype3 ne null}">
                        	<th width="12%"><spring:message code="unitdetails.usagesubtype"/></th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype4 ne null}">
                        	<th width="12%"><spring:message code="unitdetails.usagesubtype"/></th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype5 ne null}">
                        	<th width="12%"><spring:message code="unitdetails.usagesubtype"/></th>   
                        	</c:if>
        	 				<th width="12%"><spring:message code="unitdetails.taxable"/></th> 
             	 			<c:if test="${command.getAssMethod() eq 'ARV'}">
             	 			<th width="11%"><spring:message code="unitdetails.ARV"/><br><spring:message code="unitdetails.ARVFormula"/></th>
             	 			<th width="12%"><spring:message code="unitdetails.maintCharge"/><br><spring:message code="unitdetails.maintChargeFormula"/></th>            	 			
							<th width="12%"><spring:message code="unitdetails.RV"/><br><spring:message code="unitdetails.RVFormula"/></th>
							</c:if>
							<c:if test="${command.getAssMethod() eq 'CV'}">
							<th width="10%"><spring:message code="unitdetails.CV"/></th>
							</c:if>
             	 			<th width="6%"><spring:message code="unit.ViewMoreDetails"/></th>
                        </tr>
                        
                  <tbody>       
                  <c:forEach var="unitDetails" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                       	<tr>
	                        <td class="text-center">${unitDetails.proFaYearIdDesc}</td>                                                                                                          
	                        <td class="text-center">${unitDetails.assdUnitNo}</td>
	                        <td class="text-center">${unitDetails.proFloorNo} </td>
	                        <td class="text-center">${unitDetails.proAssdConstructionDate}</td> 
	                        <td class="text-center">${unitDetails.proAssdConstruTypeDesc}</td>
	                        <td class="text-center">${unitDetails.proAssdUsagetypeDesc} </td>
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc2 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc2} </td>
	                        </c:if>
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc3 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc3} </td>
	                        </c:if>
	                        
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc4 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc4} </td>
	                        </c:if>
	                        
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc5 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc5} </td>
	                        </c:if>
	                        	                        
	                    	<td class="text-center">${unitDetails.assdBuildupArea}</td>
	                    	<c:if test="${command.getAssMethod() eq 'ARV'}">
	                    	<td class="text-center">${unitDetails.assdAlv} </td>
	                    	<td class="text-center">${unitDetails.maintainceCharge} </td>	                    	
	                        <td class="text-center">${unitDetails.assdRv}</td>
	                        </c:if>
	                        <c:if test="${command.getAssMethod() eq 'CV'}">
	                    	<td class="text-center">${unitDetails.assdCv}</td>
	                    	</c:if>
	                    	<td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-${status.count-1}" aria-expanded="false" aria-controls="group-of-rows-${status.count-1}"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
	                    	
    					</tr>

            
				         <tr class="secondUnitRow collapse in previewTr hideDetails" id="group-of-rows-${status.count-1}">
				            <td colspan="12">
				
				               <legend class="text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
								</legend>
				            
				             	<div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
					             	<div class="form-group" >   					           			
					           			
										<label for="proAssdOccupancyType" class="col-sm-2 control-label"><spring:message code="unitdetails.occupancy"/> </label>
										<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdOccupancyTypeDesc"  id="proAssdOccupancyType" class="form-control" disabled="true"/></div>
										
										<%-- <c:if test="${unitDetails.getProAssdOccupancyTypeDesc() eq 'Tenanted'}">
										<label for="assdAnnualRent" class="col-sm-2 control-label"><spring:message code="property.rent"/> </label>
										<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdAnnualRent" class="form-control" disabled="true"/></div>									
										</c:if> --%>
										
										<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			          		    		<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div>
									</div>
									
									
									<div class="form-group" >   					           			
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc1"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
										
										<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdNatureOfpropertyDesc2 ne null}">
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc2"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
										
										</c:if>
										
										<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdNatureOfpropertyDesc3 ne null}">
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc3"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
										
										</c:if>
									</div>	
									
									<div class="form-group" >   					           			
										<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdNatureOfpropertyDesc4 ne null}">
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc4"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
										</c:if>
										<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdNatureOfpropertyDesc5 ne null}">
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc5"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
										</c:if>
									</div>
												
								</div>
				         	</td>
				         </tr>
                   </c:forEach>

               </tbody>
			</table>
      </div>
 </c:if>              
               
<!---------------------------------------------------unitSpecific Additional Info------------------------------------------------------- -->
<c:if test="${not empty command.provAsseFactDtlDto}"> 
	         
		<h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#unitSpecificInfo"><spring:message code="unit.UnitSpecificAddiInfo"/></a>
 		</h4>
		<div class="panel-collapse collapse in" id="unitSpecificInfo">      
 			
 			<table id="unitSpecificInfoTable" class="table table-striped table-bordered ">
            
            <tr>
            	 <th width="20%"><spring:message code="unit.Factor"/></th>
                 <th width="10%"><spring:message code="unit.ApplicableUnitNo"/></th>
                 <th width="30%"><spring:message code="unit.FactorValue"/></th>
            </tr>
            <tbody>
            <c:forEach var="factorDetails" items="${command.getProvAsseFactDtlDto()}" >
            <tr>
              <td>${factorDetails.proAssfFactorIdDesc}</td>                                                                                                          
              <td>${factorDetails.unitNoFact}</td>
              <td>${factorDetails.proAssfFactorValueDesc}</td>
             </tr>				
			</c:forEach>
			 </tbody>
			</table>	
		</div>
 
  </c:if>

<c:if test="${not empty  command.appDocument}">
<h4 class="panel-title table margin-top-10" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a3"><spring:message code="property.ApprovedDocuments" text="Approved Documents"/></a>
					</h4>
					<div id="a3" class="panel-collapse collapse in">
							<table id="docs" class="table table-striped table-bordered margin-top-10">
				                    
				                        <tr>    
				                         
				                        <th width="10%"><spring:message code="propertyTax.SrNo"/></th>                   
				                        	<th width="20%"><spring:message code="property.ApplicationNo" text="Application No"/></th>
											<th width="20%"><spring:message code="property.ServiceName" text="Service Name"/></th>
											<th width="20%"><spring:message code="property.Documents" text="Documents"/></th>
										</tr>	
									
										<tbody>
										<c:forEach var="doc" items="${command.appDocument}" varStatus="status">	
										<c:set var="service" value="${doc.value[0].descriptionType}"></c:set>
										 <tr> 
										 <td>${status.count}</td>
											<td>${doc.key}</td>
											<td>${service}</td>
											<td>
											<table>
											<c:forEach var="entry" items="${doc.value}">
											<tr>
											 <td >
											<apptags:filedownload filename="${entry.documentName}" filePath="${entry.uploadedDocumentPath}" actionUrl="ViewPropertyDetail.html?Download"></apptags:filedownload>
											 </td>
											</tr>
											</c:forEach>
											</table>
											</td>
											
											
											</tr>
											</c:forEach>
										</tbody>
							</table>
					</div>
					
</c:if>
<!-------------------------------------------------- Bill Details-------------------------------------------------------------------------->


					<h4 class="panel-title table margin-top-10" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a1"><spring:message code="property.viewDetails.BillHistory" text="Bill History"/></a>
					</h4>
					<div id="a1" class="panel-collapse collapse in">
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
				
<!---------------------------------------------------- Collection Details------------------------------------------------------------------>
<c:if test="${not empty  command.collectionDetails}">
					
				
						<h4 class="panel-title table margin-top-10" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a2"><spring:message code="property.PaymentHistory" text="Payment History"/></a>
					</h4>
					<div id="a2" class="panel-collapse collapse in">
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
											<td>${collection.lookUpParentId}</td>
											<td>${collection.lookUpCode}</td>
											<td>${collection.lookUpType}</td>
											<td>${collection.otherField}</td>
											<td class="text-center"><button class="btn btn-primary btn-sm" type="button" onclick="DownloadReceiptPdfFile(${collection.lookUpId},${collection.lookUpParentId})"><i class="fa fa-download" aria-hidden="true"></i></button></td>
											</tr>
											</c:forEach>
										</tbody>
							</table>
					</div>				
					
</c:if>					
					
					
	</div>	
	<div class="text-center padding-top-10">
					
						<%-- <button type="button" class="btn btn-blue-2"  id="back"
									onclick="BackToSearch()"><spring:message code="property.Back" text="Back"/>
						</button> --%>
						<button type="button" class="btn btn-blue-2"  id="back"
									onclick="window.location.href='ViewPropertyDetail.html'"><spring:message code="property.Back" text="Back"/>
						</button> 
						
						<input type='button' id='btnPrint' value='Print'
		style='width: 100px; position: fixed; bottom: 10px; left: 47%; z-index: 111;'
		onclick='window.print()' />
	</div>
	</form:form>
	</div>
</div>
