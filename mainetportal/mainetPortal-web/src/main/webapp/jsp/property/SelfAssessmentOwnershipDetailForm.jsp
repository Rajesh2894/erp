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
<script src="js/mainet/validation.js"></script>
<script src="js/property/ownershipDetailsForm.js"></script>

<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'JO'}">
<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#SingleOwnerDetails"><spring:message
				code="property.Owner(s)detail" /></a> 
</h4>

</c:when>
<c:otherwise>

<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#SingleOwnerDetails"><spring:message
				code="property.Ownerdetail" /></a> 
	</h4>
</c:otherwise>
</c:choose>




<div  id="ownerType">


<!-- <div id="owner"> -->
<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
	
	<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#SingleOwnerDetails"><spring:message
				code="property.Owner(s)detail" /></a> 
	</h4> --%>
	<div class="panel-collapse collapse in" id="SingleOwnerDetaills">

	 	<table id="singleOwnerTable" class="table table-striped table-bordered ">
                    
                        <tr>                  
                        	<th width="20%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
							<th width="9%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
							<th width="9%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
							<th width="20%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
							<th width="10%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
							<th width="10%"><spring:message code="property.email"/></th>		
							<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
							<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
                        </tr>
               		 <tbody>
                        <tr>
									<td>
										<form:input id="assoOwnerName" 
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete"
											data-rule-required="true" placeholder="Enter Owner Name" maxlength="500" />
									</td>
									
							  		
									 <td class="ownerDetails">
			
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" cssClass="form-control changeParameterClass mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="property.sel.optn.gender"  hasTableForm="true" isMandatory="true" />
									</td> 
									
									<td class="ownerDetails">
									
										<c:set var="baseLookupCode" value="REL" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" cssClass="form-control changeParameterClass mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="property.sel.optn.relation"  hasTableForm="true" isMandatory="true" />
									
									</td>
							
								<td class="mand">
								<form:input id="assoGuardianName"
								path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete"
								 data-rule-required="true" placeholder="Enter Guardian Name" maxlength="500"/>   
								</td>
	
								<td><form:input id="assoMobileno"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass"
										 data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
								</td>
									
								<td><form:input id="emailAdd"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass preventSpace form-control"
										maxlength="254"/>   
								</td>

								<td class="ownerDetails">
									<form:input id="assoAddharno"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control mandColorClass hasAadharNo" maxlength="12"
									data-rule-minlength="12" type="text"/>
								</td>
								<td class="companyDetails">
								 		<form:input id="pannumber" path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
										class="form-control text-uppercase hasNoSpace Autocomplete" maxLength="10"
										onchange="fnValidatePAN(this)" />
								 </td>
								<form:hidden id="assoPrimaryOwn"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType" value="P" />
								
						</tr>
                        
                        
                        </tbody>
                     </table>
                     </div>
		 	 	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">
				 <c:choose>	
				 	<c:when test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}" > 
						 <%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
						 <a data-toggle="collapse" href="#JointOwnerDetail"><spring:message
								code="property.JointOwner(s)detail"/></a> 
						 </h4> --%>
				 <div class="panel-collapse collapse in" id="JointOwnerDetail">
							<table id="jointOwnerTable" class="table text-left table-striped table-bordered">

										<tr>
											<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
											<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
											<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
											<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
											<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
											<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email"/></th>					
											<th width="13%"><spring:message code="ownersdetail.adharno" /></th>
											<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
											<th width="8%"><spring:message code="property.add/delete" /></th>
										</tr>
									<tbody>
									<c:forEach  items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}" varStatus="status" >
										
										<tr class="jointOwner">
											<td>
												<c:set var="d" value="0" scope="page" />
												<form:input id="assoOwnerName_${status.count-1}" 
													path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete"
													data-rule-required="true" placeholder="Enter Owner Name" maxlength="500"/>
											</td>
											
											<td class="ownerDetails">
											
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" cssClass="form-control changeParameterClass mandColorClass"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="property.sel.optn.gender"  hasTableForm="true" isMandatory="true" />
											
							
											</td>
											
											<td class="ownerDetails">
											
											<c:set var="baseLookupCode" value="REL" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" cssClass="form-control changeParameterClass mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="property.sel.optn.relation"  hasTableForm="true" isMandatory="true" />
												
											</td>
									
										<td>
										<form:input id="assoGuardianName_${status.count-1}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete"
										 data-rule-required="true" placeholder="Enter Guardian Name" maxlength="500"/>   
										</td>
										
										<td>
											<form:input id="assoPropertyShare_${status.count-1}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].propertyShare" class="form-control mandColorClass hasNumber"
											maxlength="3" />
										</td>
										
										<td><form:input id="assoMobileno_${status.count-1}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoMobileno" class="hasMobileNo form-control mandColorClass"
												data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
										</td>
											
										<td><form:input id="emailAdd_${status.count-1}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass preventSpace form-control"
										maxlength="254"/>   
										</td>
										
										<td class="ownerDetails">
											<form:input id="assoAddharno_${status.count-1}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoAddharno" class="form-control mandColorClass hasAadharNo Autocomplete"
											maxlength="12" data-rule-maxlength="12" data-rule-minlength="12" />
										</td>
										<td class="companyDetails">
											<form:input id="pannumber${status.count-1}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoPanno" class="form-control hasPanno text-uppercase hasNoSpace Autocomplete" maxlength="10" onchange="fnValidatePAN(this)"/>
										</td>
									
										<td>
											<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm" id="deleteOwnerRow_${status.count-1}"><i class="fa fa-trash-o"></i></a>
										</td>
											<form:hidden id="assoPrimaryOwn"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoOType"  value="P" />
										
											<form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/>
										
										</tr>
									</c:forEach>
								</tbody>
							</table>
							</div>
						</c:when>
					
						<c:otherwise>					
							<%--  <h4 class="margin-top-0 margin-bottom-10 panel-title">
					 		 <a data-toggle="collapse" href="#OtherOwnerDetail"><spring:message
								code="property.Owner(s)detail" /></a> 
					 		 </h4> --%>
			  				<div class="panel-collapse collapse in" id="OtherOwnerDetail">
							<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
								
									<tr>
										<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
										<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
										<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
										<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
										<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
										<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email"/></th>					
										<th width="13%"><spring:message code="ownersdetail.adharno" /></th>
										<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
										<th width="8%"><spring:message code="property.add/delete" /></th>
									</tr>
								<tbody>
									<tr class="jointOwner">
										<td>
											<c:set var="d" value="0" scope="page" />
											<form:input id="assoOwnerName_${d}" 
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace"
												data-rule-required="true" placeholder="Please Enter" maxlength="200"/>
										</td>
										
										<td class="ownerDetails">
										
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" cssClass="form-control changeParameterClass mandColorClass"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="ownersdetail.Gender"  hasTableForm="true" isMandatory="true" />
											
									
										</td>
										
										<td class="ownerDetails">
										
										
											<c:set var="baseLookupCode" value="REL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" cssClass="form-control changeParameterClass mandColorClass"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="ownersdetail.Relation"  hasTableForm="true" isMandatory="true" />
											
										</td>
								
									<td>
									<form:input id="assoGuardianName_${d}"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete"
									 data-rule-required="true" placeholder="Enter Guardian Name" maxlength="500"/>   
									</td>
									
									<td>
										<form:input id="assoPropertyShare_${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].propertyShare" class="form-control mandColorClass hasNumber Autocomplete"
										maxlength="3" />
									</td>
									
									<td><form:input id="assoMobileno_${d}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass Autocomplete"
											data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Enter Mobile Number"/>   
									</td>
									
									<td><form:input id="emailAdd_${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass preventSpace form-control"
										maxlength="254"/>   
									</td>										
									
									<td class="ownerDetails">
										<form:input id="assoAddharno_${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control mandColorClass hasAadharNo Autocomplete"
									    maxlength="12" data-rule-maxlength="12" data-rule-minlength="12"/>
									</td>
									<td class="companyDetails">
										<form:input id="pannumber${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control hasPanno text-uppercase hasNoSpace Autocomplete" maxlength="10" onchange="fnValidatePAN(this)" />
									</td>
								
									<td>
										<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm" id="deleteOwnerRow_0"><i class="fa fa-trash-o"></i></a>
									</td>
										<form:hidden id="assoPrimaryOwn"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType"  value="P" />
										
										<form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/>								
																
									</tr>								
							</tbody>
							</table>
						</div>
						</c:otherwise>
					</c:choose>
			</c:when>
			
	<c:when test="${command.getOwnershipPrefix() eq 'JO'}">
	<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#JointOwnerDetail"><spring:message
				code="property.Owner(s)detail" /></a>   
	</h4>		 --%>		
	
				
	</c:when>	
			
		<c:otherwise>
				<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#OwnersDetail"><spring:message
				code="property.Owner(s)detail" /></a> 
				</h4> --%>
				<div class="panel-collapse collapse in" id="OwnersDetail">
				
				<table id="companyDetailTable" class="table text-left table-striped table-bordered">

									
						<tr>
									<th width="25%" class="required-control"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
									<th width="25%" class="required-control"><spring:message code="ownersdetail.contactpersonName" /></th>
									<th width="15%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email"/></th>							
									<th width="15%"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						<tbody>
 						<tr>
 									<td> 									
 										<form:input id="assoOwnerName"  
 											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete" 
 											data-rule-required="true" placeholder="Enter Owner Name" maxlength="500" /> 
 									</td> 
						
 								<td> 
 								<form:input id="assoGuardianName" 
 								path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace Autocomplete"  
 								 data-rule-required="true" placeholder="Enter Guardian Name" maxlength="500"/>  
								</td> 
								
 								<td><form:input id="assoMobileno" 
 										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass" 
										data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Enter Mobile Number"/>   
 								</td> 
 								
 								<td><form:input id="emailAdd"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass preventSpace form-control"
										maxlength="254"/>   
								</td>
								
								<td class="companyDetails"> 								
									<form:input id="pannumber" 
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control hasPanno text-uppercase preventSpace Autocomplete" maxlength="10" onchange="fnValidatePAN(this)"/> 
								</td> 
									<form:hidden id="assoPrimaryOwn"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType"  value="P" />
 					</tr> 								
					</tbody>
					</table>
					</div>	
		</c:otherwise>	
	</c:choose>
</div>
			
