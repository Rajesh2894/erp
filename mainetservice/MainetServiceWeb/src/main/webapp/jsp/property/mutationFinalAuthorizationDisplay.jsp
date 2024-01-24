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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/mutation.js"></script>

<ol class="breadcrumb">
		<li><a href="AdminHome.html"><spring:message code="cfc.home" text="Home"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="property.property" text="Property tax"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="cfc.transaction" text="Transactions"/></a></li>
		<li class="active"><spring:message text="Mutation Authorization" code="propety.mutation.auth" /></li>
	</ol>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">

			<%-- <c:if test="${command.noOfDaysAuthFlag eq 'Y'}">
				<h3 align="center">This application is under period of public
					notice . This application cannot be approved</h3>
			</c:if>
			
			<c:if test="${command.noOfDaysAuthFlag eq 'I'}">
				<h3 align="center">This application is under period of objection. This application cannot be approved</h3>
			</c:if> --%>
			<h2><strong><spring:message code="propertyTax.Mutation" text="Mutation"/></strong></h2>				
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help"/></span></i></a>
				</div>
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
	
		
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">*</i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="MutationFinalApprovalAndLetterGeneration.html"
					class="form-horizontal form" name="MutationFinalApprovalAndLetterGeneration"
					id="MutationFinalApprovalAndLetterGeneration">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			
			<form:hidden  path="" id="isLastAuth" class="form-control" value="${command.isLastAuthorizer()}"/>
			<form:hidden  path="" id="isBeforeLastAuth" class="form-control" value="${command.isBeforeLastAuthorizer()}"/>
			<form:hidden  path="" id="bypassPublicNotice" class="form-control" value="${command.getBypassPublicNotice()}"/>
			<form:hidden  path="" id="appNo" class="form-control" value="${command.propTransferDto.apmApplicationId}"/>
			<%-- Defect #155273 --%>
			<form:hidden  path="" id="authFlag" class="form-control" value="${command.noOfDaysAuthFlag}"/>
			<!-- Start Each Section -->
<c:if test="${command.saveButFlag eq 'N'}">
<div class="form-group">
<apptags:input labelCode="Application No" path="applicationNo"></apptags:input>
</div>

<div class="text-center padding-15 clear" id="searchBtn">
							<button type="button" class="btn btn-warning"
								onclick="getMutationDetailForApproval1(this)">
								<spring:message code="property.search" text="" />
							</button>
						</div>
</c:if>
			
<c:if test="${command.saveButFlag eq 'Y'}">
<div class="form-group">
							<label class="col-sm-2 control-label"
								for="applicantTitle"><spring:message
									code="applicantinfo.label.title" /></label>
							<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="applicantDetailDto.applicantTitle" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								disabled="true"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label"
								for="firstName"><spring:message
									code="applicantinfo.label.firstname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="true"
									path="applicantDetailDto.applicantFirstName" id="firstName"
									data-rule-required="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="middleName"><spring:message
									code="applicantinfo.label.middlename" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="true"
									path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
							</div>
							<label class="col-sm-2 control-label"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="true"
									path="applicantDetailDto.applicantLastName" id="lastName"
									data-rule-required="true"></form:input>
							</div>
						</div>
						
						<div class="form-group">
								<label class="col-sm-2 control-label"
									for="buildingName"><spring:message code="prop.no.dues.applicant.building.name"
										text="Building Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.buildingName" id="buildingName"
										 disabled="true"/>
								</div>
								<label class="col-sm-2 control-label" for="blockName"><spring:message
										code="applicantinfo.label.blockname" text="Block Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.blockName" id="blockName"
										disabled="true"/>
								</div>
							</div>
							
								<div class="form-group wardZone">

									<apptags:lookupFieldSet baseLookupCode="CWZ" hasId="true"
										showOnlyLabel="false"
										pathPrefix="applicantDetailDto.dwzid" isMandatory="false"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control" showAll="false"
										showData="true" disabled="true"/>
										
								</div>
								
								<div class="form-group">
								<label class="col-sm-2 control-label" for="cbillingRoadName"><spring:message
										code="applicantinfo.label.roadname" text="Road Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control "
										path="applicantDetailDto.roadName" id="roadName"
										disabled="true"/>
								</div>
								<label class="col-sm-2 control-label" for="areaName"><spring:message
										code="applicantinfo.label.areaname" text="Area Name" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hideElement"
										path="applicantDetailDto.areaName" id="areaName"
										disabled="true"/>
								</div>
								</div>
								
								<div class="form-group">
								
								<label class="col-sm-2 control-label                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 "
									for="pinCode"><spring:message code="applicantinfo.label.pincode" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasNumber hideElement"
										path="applicantDetailDto.pinCode" id="pinCode"  disabled="true"/>
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.mobile" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasMobileNo" maxlength="10"
										path="applicantDetailDto.mobileNo" id="mobileNo" disabled="true"/>
								</div>
							</div>
							<div class="form-group">

								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasemailclass"
										path="applicantDetailDto.emailId" id="emailId" disabled="true"/>
								</div>
							</div>
							
								<div class="accordion-toggle">


			<!-- End Each Section -->
			 <c:if test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">

				<!----------------------------------------Existing Owner Details (First owner will be the primary owner)---------------------------->
 					<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#OwnshipDetail"><spring:message code="propertyTax.Mutation.ExistOwnerDetails" text="Existing Owner Details"/></a>
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
			            <tbody>      		
					      <tr>
							<td>
								<form:input id="assoOwnerName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/>
							</td>
														
							<td>
									<form:input id="ownerGender" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssGenderId"   class="form-control" disabled="true"/>				
							</td>
														
							<td>		
								<form:input id="ownerRelation" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssRelationId"  class="form-control" disabled="true"/>
							</td>
												
							<td>
								<form:input id="assoGuardianName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control" disabled="true" />   
							</td>
						
							<td>
								<form:input id="assoMobileno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true"/>   
							</td>
								
							<td><form:input id="emailId"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
							</td>						
							<td>
								<form:input id="assoAddharno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control "  disabled="true"/>
							</td>
							<td>
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
													
									<tr>
												<th width="25%"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
												<th width="25%"><spring:message code="ownersdetail.contactpersonName" /></th>
												<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
												<th width="10%"><spring:message code="property.email" /></th>												
												<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
									</tr>
									<tbody>
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
				 
<!-------------------------------------------------------------Land Details---------------------------------------------------------------------------------->
            
				<%-- <h4 class="margin-top-10 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#landDetail"><spring:message
				code="property.LandDetail" /></a>
				</h4> --%>
	
			<div class="panel-collapse collapse in margin-top-15" id="landDetail">
           		  <div class="form-group">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
       	  
            	  	<apptags:input labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc" isDisabled="true"></apptags:input>            	  	
            	  	<form:hidden  path="landTypePrefix" class="form-control landValue"/> 
                    <form:hidden class="form-control" id="khasraNo" path="knownKhaNo"/>	<!--For entering value in khasra/plot field-->
		     	  </div>
          	   
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
				<form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass" id="displayEnteredKhaNo" disabled="true"/>
				</div>
		</div>
		
		<div class="form-group">
		<div class="text-center padding-top-10">
		<c:if test="${command.getAssType() eq 'MUT'}">  
		<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
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
			<c:if test="${command.getAssType() eq 'MUT'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
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
			<c:if test="${command.getAssType() eq 'MUT'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>

</c:when>
</c:choose>
			
<!----------------------------------------------Existing Property Address Details-------------------------------------------------------->
			
 				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyAddress"><spring:message code="propertyTax.Mutation.ExistPropDetails"/></a>
				</h4>
				<div class="panel-collapse collapse in" id="propertyAddress">
						<div class="form-group">
						<label for="assAddress" class="col-sm-2 control-label "><spring:message code="property.propertyaddress" text="Property Address"/></label>
                      		<div class="col-sm-4">
                       	<form:textarea class="form-control"  id="assAddress" path="provisionalAssesmentMstDto.assAddress" disabled="true"/>
                  		</div>
							
						<label for="location" class="col-sm-2 control-label "><spring:message code="property.location"/></label>
	              		<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.locationName" type="text" id="location" class="form-control" disabled="true"/></div>
      					
						 </div>              
			                 
	            <div class="form-group">
							<label class="col-sm-2 control-label required-control" for="proAssPincode"><spring:message code="property.pincode"/></label>
							<div class="col-sm-4">
							<form:input class="form-control hasPincode"
				 					path="provisionalAssesmentMstDto.assPincode" id="proAssPincode" maxlength="6" readonly="true"></form:input>
			            	</div>
							<label class="col-sm-2 control-label " for="assdAlv"><spring:message code="" text="ARV"/></label>
							<div class="col-sm-4">
							<form:input class="form-control"
				 					path="provisionalAssesmentMstDto.totalArv" id="assdAlv"  readonly="true"></form:input>
			            	</div>
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
				
				<apptags:input labelCode="property.propertyMohalla" path="provisionalAssesmentMstDto.assWardDesc3" isDisabled="true"></apptags:input>
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
					
					
					
				<!-----------------------------------------------Transfer Details-------------------------------------------------------------------->
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#transferDetails"><spring:message code="propertyTax.mutation.TransferDetails" text=""/></a>
					</h4>
				 <div class="panel-collapse collapse in" id="transferDetails">
					 <div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="propertyTax.mutation.TransferType" text="Transfer Type"/></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="TFT" /> <form:select path="propTransferDto.transferType"
											 class="form-control changeParameterClass mandColorClass"  data-rule-required="true" disabled="true">
												<form:option value="">
													<spring:message code="property.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</div>
		
							<label class="col-sm-2 control-label required-control"><spring:message code="propertyTax.mutation.TransferDate" text="Actual Transfer Date"/></label>
							<div class="col-sm-4">
							<div class="input-group"> 
									<form:input path="propTransferDto.actualTransferDate" class="lessthancurrdate form-control mandColorClass dateClass" id="actualTransferDate" data-rule-required="true" placeholder="DD/MM/YYYY" readonly="true" autocomplete="off"/>
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>			
							</div>
							
							</div>
					</div>
					<div class="form-group">
					
						<label class="col-sm-2 control-label required-control" for="marketValue"><spring:message code="property.MarketValue" text="Market Value"/></label>
							<div class="col-sm-4">
							<form:input  type="text" class="form-control mandColorClass hasNumber"
				 					path="propTransferDto.marketValue" id="marketValue" readonly="true" data-rule-required="true"></form:input>
							</div>
							
							<label class="col-sm-2 control-label required-control" for="salesDeedValue"><spring:message code="property.SalesDeedValue" text="Sales Deed Value"/></label>
							<div class="col-sm-4">
							<form:input  type="text" class="form-control mandColorClass hasNumber"
				 					path="propTransferDto.salesDeedValue" id="salesDeedValue" readonly="true" data-rule-required="true"></form:input>
							</div>
						
					</div> 
					</div> 
					
		<!-------------------------------------------------------New Owner Details On Authorization------------------------------------------------------->			
					
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#newOwnerDetails"><spring:message code="propertyTax.mutation.NewOwnerDetails" text="New Owner Details (First owner will be the primary owner)"/></a>
						</h4>
						
							<div class="panel-collapse collapse in" id="newOwnerDetails">
			
						<div class="form-group">
							<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
							<div class="col-sm-4"><form:input path="propTransferDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" disabled="true"/>
 							<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/>
							</div>
			   			 </div>
		   
		    
					<c:choose>
					<c:when test="${command.getOwnershipPrefixNew() eq 'SO'}">
				 	<table id="singleOwnerTable" class="table table-striped table-bordered ">
			        
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
			            <tbody>      		
					      <tr>
							<td>
								<form:input id="assoOwnerName" path="propTransferDto.propTransferOwnerList[0].ownerName" class="form-control" disabled="true"/>
							</td>
														
							<td>
									<form:input id="ownerGender" path="propTransferDto.propTransferOwnerList[0].genderIdDesc"   class="form-control" disabled="true"/>				
							</td>
														
							<td>		
								<form:input id="ownerRelation" path="propTransferDto.propTransferOwnerList[0].relationIdDesc"  class="form-control" disabled="true"/>
							</td>
												
							<td>
								<form:input id="assoGuardianName" path="propTransferDto.propTransferOwnerList[0].guardianName" class="form-control" disabled="true" />   
							</td>
						
							<td>
								<form:input id="assoMobileno" path="propTransferDto.propTransferOwnerList[0].mobileno" class="hasNumber form-control" disabled="true"/>   
							</td>
							
							<td><form:input id="emailId"
										path="propTransferDto.propTransferOwnerList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
							</td>
														
							<td>
								<form:input id="assoAddharno" path="propTransferDto.propTransferOwnerList[0].addharno" class="form-control "  disabled="true"/>
							</td>
							<td>
								<form:input id="assoPanno" path="propTransferDto.propTransferOwnerList[0].panno" class="form-control"  disabled="true"/>
							</td>
						 </tr>
						</tbody>
			   		 </table>
					</c:when>
					 	 	
					 <c:when test="${command.getOwnershipPrefixNew() eq 'JO'}">	
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
											<c:forEach var="ownershipTypeList" items="${command.getPropTransferDto().getPropTransferOwnerList()}">
					            			<tr>
					                		 <td>${ownershipTypeList.ownerName}</td>
					                		 <td>${ownershipTypeList.genderIdDesc}</td>
					                		 <td>${ownershipTypeList.relationIdDesc}</td>
					                		 <td>${ownershipTypeList.guardianName}</td>
					                		 <td>${ownershipTypeList.propertyShare}</td>
					                		 <td>${ownershipTypeList.mobileno}</td>
					                		 <td>${ownershipTypeList.eMail}</td>					                		 
					                		 <td>${ownershipTypeList.addharno}</td>
					                		 <td>${ownershipTypeList.panno}</td>
					            			</tr>
					                		 </c:forEach>
	
											</tbody>
								</table>
					</c:when>
					<c:otherwise>
						<table id="companyDetailTable" class="table text-left table-striped table-bordered">
													
									<tr>
												<th width="25%"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
												<th width="25%"><spring:message code="ownersdetail.contactpersonName" /></th>
												<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
												<th width="10%"><spring:message code="property.email" /></th>											
												<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
									</tr>
									<tbody>
			 						<tr>
			 						<td> 
			 						<form:input id="assoOwnerName_${d}" path="propTransferDto.propTransferOwnerList[0].ownerName" class="form-control" disabled="true"/> 
			 						</td> 
									
			 						<td> 
			 						<form:input id="assoGuardianName_${d}" path="propTransferDto.propTransferOwnerList[0].guardianName" class="form-control " disabled="true" />  
									</td> 
											
			 						<td><form:input id="assoMobileno_${d}" path="propTransferDto.propTransferOwnerList[0].mobileno" class="hasNumber form-control" disabled="true" />   
			 						</td> 
									
									<td><form:input id="emailId_${d}"
										path="propTransferDto.propTransferOwnerList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
									</td>
									<td class="companyDetails"> 
									<form:input id="assoPanno_${d}" path="propTransferDto.propTransferOwnerList[0].panno" class="form-control" disabled="true" /> 
									</td> 
			 						</tr> 								
									</tbody>
						</table>				
					</c:otherwise>	
					</c:choose>
				 </div>			
					
							
		
			</c:if>
		
<c:if test="${command.getApprovalFlag() eq 'Y' && not empty command.propTransferDto.charges}">  
   <h4 class="margin-top-10 margin-bottom-10 panel-title ">
   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="propertyTax.mutation.Loi.charges" text="LOI Charges"/></a>
    </h4>         
    <div class="panel-collapse collapse in" id="TaxCalculation">      
     
      <c:set var="totPayAmt" value="0"/>
            <div class="table-responsive">
              <table class="table table-striped table-condensed table-bordered">
                <tbody>
                  <tr>
                    <th width="50"><spring:message code="propertyTax.SrNo"/></th>
                    <th width="600"><spring:message code="propertyTax.TaxName"/></th>
                    <th width="400"><spring:message code="propertyTax.Total"/></th>
                  </tr>
                <c:forEach var="tax" items="${command.propTransferDto.charges}"  varStatus="status">
                  <tr>
                  <td>${status.count}</td>
                  <td>${tax. getTaxDesc()}</td>
                  <td class="text-right">${tax.getTotalTaxAmt()}</td>
                </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
            <div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  <th width="500" class="text-right">${command.propTransferDto.billTotalAmt}</th>
                </tr>
              </table>
            </div>
          </div>
      </c:if> 				
					
		
					
			
<c:if test="${not empty command.checkList}"> 

<h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentUpload"><spring:message code="propertyTax.DocumentsUpload"/></a></h4>
                
                <div id="DocumentUpload" class="panel-collapse collapse in">
                  <div class="panel-body">
                    
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">
															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td> <spring:message
																				code="water.doc.opt" />
																	</td>
																</c:if>
																<td><div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="${lk.index}" />
																	</div>
																	</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>									
                  </div>
                </div>
    
          
        </c:if>   
        
        <c:if test="${command.showFlag eq 'Y' }">
					<h4>LOI Fees and Charges in Details</h4>
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
								<th scope="col" width="80">Sr. No</th>
								<th scope="col">Charge Name</th>
								<th scope="col">Amount</th>
							</tr>
							<c:forEach var="charges" items="${command.loiDetail}"
								varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${charges.loiRemarks}</td>
									<td><fmt:formatNumber value="${charges.loiAmount}"
											type="number" var="amount" minFractionDigits="2"
											maxFractionDigits="2" groupingUsed="false" /> <form:input
											path="" type="text" class="form-control text-right"
											value="${amount}" readonly="true" /></td>
								</tr>
							</c:forEach>
							
							<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('ASCL','ENV',${UserSession.organisation}).getOtherField()"
										var="otherField" />
										<c:if test="${command.getServiceShortCode() eq 'MUT'}">
										<c:if test="${otherField eq 'Y' }">
										<td colspan="2"><span class="pull-right"><b>Total Amount
												</b></span></td>
									<td class="text-right">${command.totalAmntIncApplFee}</td>
										<tr>
									<td colspan="2"><span class="pull-right"><b>Already paid application fee
												</b></span></td>
									<td class="text-right">${command.applicationFee}</td>

								</tr>
										
										</c:if>
										</c:if>
								<tr>
								<td colspan="2"><span class="pull-right"><b>Total
											LOI Amount</b></span></td>
								<td class="text-right">${command.totalLoiAmount}</td>

							</tr>
						</table>
					</div>
				</c:if>
        
        
				<!--T#92833 Applicant Details -->
				<%-- <c:if test="${command.getCheckListVrfyFlag() eq 'N'}"> --%>
			<c:if test="${not empty command.documentList}">
			<h4 class="margin-top-0"><spring:message text="Applicant Details" code="cfc.applicant.detail"/></h4>
			
				<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="checklistVerification.serviceName"  text="Service Name" /> </label>
                <div class="col-sm-4">
                	<span class="form-control height-auto">${command.applicationDetails.applicationService }</span>
                </div>
                 <label class="col-sm-2 control-label"><spring:message text="Transfer Type " code="propertyTax.mutation.TransferType" /> </label> 
					<div class="col-sm-4">
	                	<span class="form-control">${command.propTransferDto.transferTypeDesc }</span>
					</div>
				</div>
				
				<div class="form-group">
	               
					<label class="col-sm-2 control-label"><spring:message text="Application ID " code="cfc.application" /> </label> 
					<div class="col-sm-4">
	                	<span class="form-control">${command.applicationDetails.apmApplicationId }</span>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message text="Application Date" code="cfc.applIcation.date" /> </label>
					<div class="col-sm-4">
					<c:set value="${command.applicationDetails.apmApplicationDate }" var="appDate"/>
							<span class="form-control"><fmt:formatDate type="date" value="${appDate}" pattern="dd/MM/yyyy" /></span>
					</div>
					
				</div>
				

			</c:if>
			<!--END Applicant Details -->
			<c:set var="loopCount" value='0' />
			 <c:if test="${not empty command.documentList}">	
                         <h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentUpload"><spring:message code="propertyTax.UploadDocumnet"/></a></h4>
                         
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.docName" text="Document Name" /></label></th>
												     <th><label class="tbold"><spring:message
																code="water.download"/></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="SelfAssessmentForm.html?previewDownload"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						
					 </c:if>     
				<%-- </c:if> --%>
				<!--END T#92833 -->
				<%--  <div>
<jsp:include page="/jsp/cfc/checklist/ChecklistVerificationApproval.jsp"/>
</div> --%>
	<c:if test="${command.getMutationLevelFlag() eq 'A' && command.getApprovalFlag() ne 'Y'}">  
	<!-- D#91615 -->
	<c:if test="${command.noOfDaysAuthFlag eq 'N'}">
	<c:choose>
	<c:when test="${command.getCurentCheckerLevel() == 1 && command.getCheckListVrfyFlag() eq 'N'}">
	<c:choose>
	<c:when test="${command.isLastAuthorizer() == 'true'}">
		<apptags:CheckerAction hideSendback="true"></apptags:CheckerAction>
	</c:when>
	<c:otherwise>
			<apptags:CheckerAction hideSendback="true" hideReject="true"></apptags:CheckerAction>
	</c:otherwise>
	</c:choose>
	</c:when>
	<c:otherwise>
	
	
	<c:choose>
	<c:when test="${command.isLastAuthorizer() == 'true'}">
		<apptags:CheckerAction></apptags:CheckerAction>
	</c:when>
	<c:otherwise>
			<apptags:CheckerAction hideReject="true"></apptags:CheckerAction>
	</c:otherwise>
	</c:choose>
	
	
	
			
	</c:otherwise>
	</c:choose>
       
       	</c:if>
		</c:if>
		
          <div class="text-center padding-top-10">
          <c:if test="${command.getLoiChargeApplFlag() eq 'N'}">
				<c:if test="${command.getMutationLevelFlag() eq 'A'}">  
			<c:if test="${command.noOfDaysAuthFlag eq 'N'}">
			        			<button type="button" class="btn btn-success btn-submit" value="${command.apmApplicationId }"
							onclick="saveMutationWithoutEdit(this)" id="submit">
							<spring:message code="propertyTax.mutation.saveMutation" text="Save Mutation"/>
						</button>
					<c:if test="${command.isBeforeLastAuthorizer()}"> 
									<button type="button" class="btn btn-success btn-submit"
							onclick="editMutation(this)" id="submit">
						<spring:message code="property.Edit" text="Edit"/>
						</button>
						</c:if>
						</c:if>
						</c:if>
			</c:if>
			<c:if test="${command.getLoiChargeApplFlag() eq 'Y'}">
			<button type="button" class="btn btn-success" id="submit"
							onclick="generateLoiCharges(this)">
							<spring:message code="propertyTax.mutation.generate.loi" text="Generate Loi"></spring:message>
						</button>
			</c:if>
          
			</div>
			<!--  End button -->
		</div>
<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>

 <div class="text-center padding-top-10">
			        			<button type="button" class="btn btn-success btn-submit" value="${command.apmApplicationId }"
							onclick="saveFinalApprovalMutation(this)" id="submit">
							<spring:message code="propertyTax.mutation.saveMutation" text="Save Mutation"/>
						</button>
          
			</div>
				</c:if>	
					</form:form>

		</div>
	
		</div>
			</div>
<!-- End of Content -->
		<!-- End Form -->


