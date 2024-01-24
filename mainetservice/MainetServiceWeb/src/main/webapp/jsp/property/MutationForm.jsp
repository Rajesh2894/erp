
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


<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->	
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="propertyTax.Mutation" text="Mutation"/></strong></h2>				
				<%-- <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help"/></span></i></a>
				</div> --%>
				
				<apptags:helpDoc url="MutationForm.html"></apptags:helpDoc>
				
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
	
		
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.changeInAss"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="MutationForm.html"
					class="form-horizontal form" name="MutationForm"
					id="MutationForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
<%-- 			<div class="padding-10"><strong><i><spring:message code="property.EnterDetailstosearchproperty" /></i></strong></div> --%>
			
			<form:hidden  path="" id="kdmcMutFlag" class="form-control" value="${command.getKdmcMutFlag()}"/>
			<form:hidden  path="" id="isLastAuth" class="form-control" value="${command.isLastAuthorizer()}"/>
			<form:hidden  path="" id="isBeforeLastAuth" class="form-control" value="${command.isBeforeLastAuthorizer()}"/>
			<form:hidden  path="" id="bypassPublicNotice" class="form-control" value="${command.getBypassPublicNotice()}"/>
			
			<!-- Start Each Section -->
		 <c:if test="${command.isLastAuthorizer() ne true}">
			<div class="form-group">
				
					<form:hidden  path="" id="isLastAuth" class="form-control" value="${command.isLastAuthorizer()}"/>
					<apptags:input labelCode="Enter Property No." path="provisionalAssesmentMstDto.assNo" maxlegnth="18" cssClass="mandColorClass"  isDisabled="${command.getAssType() eq 'MUT' ? true : false}"></apptags:input>
					<apptags:input labelCode="OLD PID." path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="16" cssClass="mandColorClass"  isDisabled="${command.getAssType() eq 'MUT' ? true : false}"></apptags:input>	
					
                   	
					<!--Search Button-->
					<c:if test="${command.getAssType() ne 'MUT'}">
                   	<div class="text-center padding-15 clear">                   	
                   	<button type="button" class="btn btn-blue-2" onclick="getMutationDetail()"><spring:message code="property.search" text=""/></button>	
                   	</div>
               </c:if>
			</div> 
		</c:if>

			<!-- End Each Section -->
				<div class="accordion-toggle">
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
<!------------------------------------------------------------Land Details------------------------------------------------------->
				<form:hidden path="assType" id="assType"/>
				 
				<%-- <h4 class="margin-top-10 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.LandDetail" /></a>
				</h4> --%>
				<div class="panel-collapse collapse in margin-top-15" id="propertyDetail">				
				
						<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="oldpropertyno"><spring:message
										code="propertydetails.oldpropertyno" /></label>
										<div class="col-sm-4">
								<form:input path="" type="text" class="form-control alphaNumeric preventSpace" id="oldpropertyno" readonly="true" value="${command.provisionalAssesmentMstDto.assOldpropno}" maxlength="20"/>
								</div>
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
       </div> 
			
				<!----------------------------------------------Existing Property Address Details-------------------------------------------------------->
			
 				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyAddress"><spring:message code="propertyTax.Mutation.ExistPropDetails"/></a>
				</h4>
				<div class="panel-collapse collapse in" id="propertyAddress">
						<div class="form-group">
							<label for="assAddress" class="col-sm-2 control-label "><spring:message code="property.propertyaddress" text="Property address"/></label>
                      		<div class="col-sm-4">
                       			<form:textarea class="form-control"  id="assAddress" path="provisionalAssesmentMstDto.assAddress" disabled="true"/>
                  		    </div>
							
							<label class="col-sm-2 control-label" for="location"><spring:message code="property.location"/></label>
							<div class="col-sm-4">
						 		<form:select path="provisionalAssesmentMstDto.locId"
												 class="chosen-select-no-results" id="location" disabled="true" >
													<form:option value="">
														<spring:message code="property.selectLocation" text="Select location"/>
													</form:option>
													<c:forEach items="${command.location}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
								</form:select> 
							</div> 
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
					
					
					
					
				<!-----------------------------------------------Transfer Details-------------------------------------------------------------------->
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#transferDetails"><spring:message code="propertyTax.mutation.TransferDetails"/></a>
					</h4>
				 <div class="panel-collapse collapse in" id="transferDetails">
				 
					 <div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="propertyTax.mutation.TransferType"/></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="TFT" /> 
									<c:choose>
									<c:when test="${command.saveButFlag eq 'Y'}">
									<form:select path="propTransferDto.transferType" disabled="true"
											 class="form-control changeParameterClass mandColorClass"  data-rule-required="true" onchange="fetchNewCheckList()">
												<form:option value="">
													<spring:message code="bill.Select" text="Select"/>
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</c:when>
									<c:otherwise>
									<form:select path="propTransferDto.transferType" 
											 class="form-control changeParameterClass mandColorClass"  data-rule-required="true" onchange="fetchNewCheckList()">
												<form:option value="">
													<spring:message code="bill.Select" text="Select"/>
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</c:otherwise>
									 </c:choose>
									</div>
		
							<label class="col-sm-2 control-label required-control"><spring:message code="propertyTax.mutation.TransferDate" text="Actual Transfer Date"/></label>
							<div class="col-sm-4">
							<div class="input-group"> 
							<c:choose>
									<c:when test="${command.saveButFlag eq 'Y'}">
									<form:input path="propTransferDto.actualTransferDate" disabled="true" class="lessthancurrdate form-control mandColorClass dateClass"  id="actualTransferDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>	
									</c:when>
									<c:otherwise>
									<form:input path="propTransferDto.actualTransferDate" class="lessthancurrdate form-control mandColorClass dateClass"  id="actualTransferDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</c:otherwise>
							</c:choose>		
							</div>
							
							</div>
					</div>
					<div class="form-group">
					
						<label class="col-sm-2 control-label" for="marketValue"><spring:message code="property.MarketValue" text="Market Value"/></label>
							<div class="col-sm-4">
							<c:choose>
									<c:when test="${command.saveButFlag eq 'Y'}">
									<form:input  type="text" class="form-control mandColorClass hasNumber text-right" disabled="true"
				 					path="propTransferDto.marketValue"  id="marketValue" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'marketValue')"></form:input>
				 					</c:when>
				 					<c:otherwise>
				 					<form:input  type="text" class="form-control mandColorClass hasNumber text-right" 
				 					path="propTransferDto.marketValue"  id="marketValue" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'marketValue')"></form:input>
				 					</c:otherwise>
				 			</c:choose>
							</div>
							
							<label class="col-sm-2 control-label" for="salesDeedValue"><spring:message code="property.SalesDeedValue" text="Sales Deed Value"/></label>
							<div class="col-sm-4">
							<c:choose>
									<c:when test="${command.saveButFlag eq 'Y'}">
									<form:input  type="text" class="form-control mandColorClass hasNumber text-right" disabled="true"
				 					path="propTransferDto.salesDeedValue"  id="salesDeedValue" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'salesDeedValue')"></form:input>
				 					</c:when>
				 					<c:otherwise>
				 					<form:input  type="text" class="form-control mandColorClass hasNumber text-right"
				 					path="propTransferDto.salesDeedValue"  id="salesDeedValue" onkeypress="return hasAmount(event, this, 15, 2)" onchange="getAmountFormatInDynamic((this),'salesDeedValue')"></form:input>
				 					</c:otherwise>
				 			</c:choose>
							</div>
						
					</div> 
					</div> 
					
					
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#NewOwnshipDetail"><spring:message code="propertyTax.mutation.NewOwnerDetails" text="New Owner Details (First owner will be the primary owner)"/></a>
	</h4>
					
					<!-------------------------------------------------------New Owner Details------------------------------------------------------->
					
					
						
						 <div class="panel-collapse collapse in" id="NewOwnshipDetail">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="OwnershipDetail"><spring:message
										code="property.ownershiptype" /></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="OWT" /> 
									<c:choose>
									<c:when test="${command.saveButFlag eq 'Y'}">
									<form:hidden  path="ownershipPrefixNew"  id="ownershipNewId" class="form-control"/>
									<form:select path="propTransferDto.ownerType" disabled="true"
											onchange="getOwnerTypeInfo()" class="form-control mandColorClass" id="ownerInfo" data-rule-required="true">
												<form:option value="">
													<spring:message code="property.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</c:when>
									<c:otherwise>
									<form:select path="propTransferDto.ownerType" 
											onchange="getOwnerTypeInfo()" class="form-control mandColorClass" id="ownerInfo" data-rule-required="true">
												<form:option value="">
													<spring:message code="property.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</c:otherwise>
									</c:choose>
									</div>		
							</div>
						</div> 
<%-- 						<form:hidden path="selfAss" id="selfAss"/> --%>
					
					
					<div id="ownerDetails">
					
					</div>				
								
			
			
			<!-- Start button -->
				<c:if test="${command.getMutationLevelFlag() eq 'M'}">  
			<div class="text-center padding-10">
				<button type="button" class="btn btn-success"
				onclick="getCheckListAndCharges(this)" id="checkListCharge"><spring:message code="unit.proceed"/></button>

			</div>
			</c:if>
			</c:if>
			
<div id="detailDiv">			
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
		
	<!--   Tax Calculation -->
<c:if test="${command.getApprovalFlag() ne 'Y' &&  not empty command.propTransferDto.charges}">  
   <h4 class="margin-top-10 margin-bottom-10 panel-title ">
   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="property.Charges" text="Charges"/></a>
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
	<!-- 	  				  <td>--</td>
		                  <td>--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td> -->
                <c:forEach var="tax" items="${command.propTransferDto.charges}"  varStatus="status">
                  <tr>
                  <td>${status.count}</td>
                  <td>${tax. getTaxDesc()}</td>
                  <td class="text-right">${tax.getTotalTaxAmt()}</td>
                </tr>
                </c:forEach>
<%--                   <tr>
                    <th colspan="2" class="text-right"><spring:message code="propertyTax.Total"/></th>
                    <th class="text-right">${totArrTax}</th>
					<th class="text-right">${totCurrTax}</th>
					<th class="text-right">${totTotTax}</th>
                  </tr> --%>
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
			
			
			
			
	 <c:if test="${command.getAppliChargeFlag() eq 'Y' && command.getSaveButFlag() eq 'Y' && not empty command.propTransferDto.charges}">  
		<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp"/>		
	</c:if>
	</div>
                <c:if test="${not empty command.documentList}">	
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
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="SelfAssessmentForm.html?Download"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
					 </c:if>     
        <c:if test="${command.getMutationLevelFlag() eq 'A' && command.getApprovalFlag() ne 'Y'}">  
       	<apptags:CheckerAction></apptags:CheckerAction>
		</c:if>
        </div>

          <div class="text-center padding-top-10">
				<c:if test="${command.getSaveButFlag() eq 'Y' && command.getMutationLevelFlag() eq 'M'}">  
			
			        			<button type="button" class="btn btn-success btn-submit"
							onclick="savePropertyFrom(this)" id="saveMut">
							save Mutation
						</button>
			</c:if>
			   
				<c:if test="${command.getMutationLevelFlag() eq 'A'}">  
			        			<button type="button" class="btn btn-success btn-submit"
							onclick="saveMutationWithEdit(this)" id="saveMutAut">
							<spring:message code="propertyTax.mutation.saveMutation" text="Save Mutation"/>
						</button>
		
						
			</c:if>
		</div>

				</form:form>

		</div>
		</div>			
</div>


