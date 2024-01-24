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
<script type="text/javascript" src="js/property/ownershipDetailsForm.js"></script>

<div  id="ownerType">

<div class="panel panel-default  ">
<!-- <div id="owner"> -->

<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
	 <h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#singleOwnerTable"><spring:message
				code="property.Ownerdetail" /></a> 
	</h4>

	 	<table id="singleOwnerTable" class="table table-striped table-bordered ">
                    
                        <tr>                  
                        	<th width="20%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
							<th width="9%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
							<th width="9%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
							<th width="20%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
							<th width="10%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
							<th width="10%"><spring:message code="property.email" /></th>
							<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
							<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
                        </tr>
                	<tbody>                	 
                        <tr>
									<td>
										<spring:message code="property.placeHolder.EnterOwnersName" text="Please Enter Owners Name" var="ownerName"/>									
										 <form:input id="assoOwnerName" 
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace"
											data-rule-required="true" placeholder="${ownerName}" maxlength="500" autocomplete="off"/> 
									</td>									
									
									 <td class="ownerDetails">
									<div>									
									<spring:message code="bill.Select" text="Select" var="gender"/>
									
									<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${gender}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/> </div>
										
									 </td> 
									
									<td class="ownerDetails">
									<div>
										<spring:message code="bill.Select" text="Select" var="Relation"/>
									
										<c:set var="baseLookupCode" value="REL" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${Relation}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/></div>
									
									</td> 
							
								<td class="mand">
								<spring:message code="property.placeHolder.EnterGuardianName" text="Please enter Guardian Name" var="GuardianName"/>
								
								<form:input id="assoGuardianName"
								path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace"
								 data-rule-required="true" placeholder="${GuardianName}" maxlength="500" autocomplete="off"/>   
								</td>
								
								
								<td>
								<spring:message code="property.placeHolder.EnterMobileNumber" text="Please enter Mobile Name" var="MobNo"/>
								
								<form:input id="assoMobileno"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass"
										 data-rule-minlength="10" onchange="fetchPropertyDetilsByMobileNo()" maxlength="10" data-rule-required="true" placeholder="${MobNo}" autocomplete="off"/>   
								</td>
									
								 <td><form:input id="emailId"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" autocomplete="off"/>   
								</td> 
						
								<td class="ownerDetails">
									<form:input id="assoAddharno"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control mandColorClass hasAadharNo" maxlength="12"
									data-rule-minlength="12" type="text" autocomplete="off"/>
								</td>
								<td class="companyDetails">
								 		<form:input id="pannumber" path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
										class="form-control text-uppercase hasNoSpace" maxLength="10"
										onchange="fnValidatePAN(this)" autocomplete="off"/>
								 </td>
								<form:hidden id="assoPrimaryOwn"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType" value="P" />								
						</tr>  
					</tbody>   
                     </table>
		 	 	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">
		 
		 
		  <h4 class="margin-top-0 margin-bottom-10 panel-title">
						 <a data-toggle="collapse" href="#jointOwnerTable"><spring:message
								code="property.Owner(s)detail" /></a> 
						 </h4> 
		 
		 	 <form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/>
				 <c:choose>	
				 	<c:when test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}" > 
						 <%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
						 <a data-toggle="collapse" href="#Owner(s)_Detail"><spring:message
								code="property.Owner(s)detail" /></a> 
						 </h4> --%>
				 
							<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
								
		
										<tr>
											<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
											<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
											<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
											<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
											<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
											<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email" /></th>											
											<th width="13%"><spring:message code="ownersdetail.adharno" /></th>
											<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
											<th width="8%"><spring:message code="property.add/delete" /></th>
										</tr>
									<tbody>
										<c:forEach  items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}" varStatus="status" >
										
										<tr class="jointOwner">
											<td>
											<spring:message code="property.placeHolder.EnterOwnersName" text="Please Enter Owners Name" var="ownerName"/>									
											
												<c:set var="d" value="0" scope="page" />
												<form:input id="assoOwnerName_${status.count-1}" 
													path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace"
													data-rule-required="true" placeholder="${ownerName}" maxlength="500" autocomplete="off"/>
											</td>
											
											<td class="ownerDetails">
											<spring:message code="bill.Select" text="Select" var="gender"/>
											
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].genderId" cssClass="form-control mandColorClass"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="${gender}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/>
											
							
											</td>
											
											<td class="ownerDetails">
											<spring:message code="bill.Select" text="Select" var="Relation"/>
											
											<c:set var="baseLookupCode" value="REL" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].relationId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${Relation}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/>
												
											</td>
									
										<td>
										<spring:message code="property.placeHolder.EnterGuardianName" text="Please enter Guardian Name" var="GuardianName"/>
									
										<form:input id="assoGuardianName_${status.count-1}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace"
										 data-rule-required="true" placeholder="${GuardianName}" maxlength="500" autocomplete="off"/>   
										</td>
										
										<td>
											<form:input id="assoPropertyShare_${status.count-1}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].propertyShare" class="form-control mandColorClass hasNumber"
											maxlength="3" autocomplete="off"/>
										</td>
										
										<td>
										<spring:message code="property.placeHolder.EnterMobileNumber" text="Please enter Mobile Name" var="MobNo"/>
										
										<form:input id="assoMobileno_${status.count-1}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoMobileno" class="hasMobileNo form-control mandColorClass"
												data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="${MobNo}" autocomplete="off"/>   
										</td>
										
										<td><form:input id="emailId_${status.count-1}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" autocomplete="off"/>   
										</td>
										
										<td class="ownerDetails">
											<form:input id="assoAddharno_${status.count-1}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoAddharno" class="form-control mandColorClass hasAadharNo"
											maxlength="12" data-rule-maxlength="12" data-rule-minlength="12" autocomplete="off"/>
										</td>
										<td class="companyDetails">
											<form:input id="pannumber${status.count-1}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoPanno" class="form-control hasPanno text-uppercase hasNoSpace" maxlength="10" onchange="fnValidatePAN(this)" autocomplete="off"/>
										</td>
									
										<td>
											<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm" id="deleteOwnerRow_${status.count-1}"><i class="fa fa-trash-o"></i></a>
										</td>
										
										</tr>
										
									</c:forEach>
										<form:hidden id="assoPrimaryOwn"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType"  value="P" />
										
								</tbody>
							</table>
						</c:when>
					
						<c:otherwise>					
						<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
							
									<tr>
										<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
										<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
										<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
										<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
										<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
										<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>										
										<th width="13%"><spring:message code="ownersdetail.adharno" /></th>
										<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
										<th width="8%"><spring:message code="property.add/delete" /></th>
									</tr>
								<tbody>	
									<tr class="jointOwner">
										<td>
											<spring:message code="property.placeHolder.EnterOwnersName" text="Please Enter Owners Name" var="ownerName"/>									
										
											<c:set var="d" value="0" scope="page" />
											<form:input id="assoOwnerName_${d}" 
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace"
												data-rule-required="true" placeholder="${ownerName}" maxlength="500" autocomplete="off"/>
										</td>
										
										<td class="ownerDetails">
											<spring:message code="bill.Select" text="Select" var="gender"/>
										
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" cssClass="form-control changeParameterClass mandColorClass"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="${gender}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/>
											
									
										</td>
										
										<td class="ownerDetails">
											<spring:message code="bill.Select" text="Select" var="Relation"/>
										
										
											<c:set var="baseLookupCode" value="REL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" cssClass="form-control changeParameterClass mandColorClass"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="${Relation}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/>
											
										</td>
								
									<td>
									<spring:message code="property.placeHolder.EnterGuardianName" text="Please enter Guardian Name" var="GuardianName"/>
									
									<form:input id="assoGuardianName_${d}"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace"
									 data-rule-required="true" placeholder="${GuardianName}" maxlength="500" autocomplete="off"/>   
									</td>
									
									<td>
										<form:input id="assoPropertyShare_${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].propertyShare" class="form-control mandColorClass hasNumber"
										maxlength="3" autocomplete="off"/>
									</td>
									
									<td>
									<spring:message code="property.placeHolder.EnterMobileNumber" text="Please enter Mobile Name" var="MobNo"/>
									
									<form:input id="assoMobileno_${d}"
											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass"
											data-rule-maxlength="10" data-rule-minlength="10" onchange="fetchPropertyDetilsByMobileNoWithId(0)" maxlength="10" data-rule-required="true" placeholder="${MobNo}" autocomplete="off"/>   
									</td>										
									
									<td><form:input id="emailId_${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" autocomplete="off"/>   
									</td>
									
									<td class="ownerDetails">
										<form:input id="assoAddharno_${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control mandColorClass hasAadharNo"
									    maxlength="12" data-rule-maxlength="12" data-rule-minlength="12" autocomplete="off"/>
									</td>
									<td class="companyDetails">
										<form:input id="pannumber${d}"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control hasPanno text-uppercase hasNoSpace" maxlength="10" onchange="fnValidatePAN(this)" autocomplete="off"/>
									</td>
								
									<td>
										<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm" id="deleteOwnerRow_0"><i class="fa fa-trash-o"></i></a>
									</td>
										<form:hidden id="assoPrimaryOwn"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType"  value="P" />
										
<%-- 										<form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/>								
 --%>																
									</tr>								
							</tbody>
							</table>						
						</c:otherwise>
					</c:choose>
			</c:when>
			
	<c:when test="${command.getOwnershipPrefix() eq 'JO'}">
	
	<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#Owner(s)_Detail"><spring:message
				code="property.Owner(s)detail" /></a>   
	</h4>	 --%>			
				
	</c:when>	
			
		<c:otherwise>
	
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#companyDetailTable"><spring:message
				code="property.Ownerdetail" /></a> 
				</h4> 
				
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
 										<spring:message code="property.placeHolder.EnterOwnersName" text="Please Enter Owners Name" var="ownerName"/>									
 																	
 										<form:input id="assoOwnerName"  
 											path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara preventSpace" 
 											data-rule-required="true" placeholder="${ownerName}" maxlength="500" autocomplete="off"/> 
 									</td> 
						
 								<td> 
 								<spring:message code="property.placeHolder.EnterGuardianName" text="Please enter Guardian Name" var="GuardianName"/>
 								
 								<form:input id="assoGuardianName" 
 								path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara preventSpace"  
 								 data-rule-required="true" placeholder="${GuardianName}" maxlength="500" autocomplete="off"/>  
								</td> 
								
 								<td>
 								<spring:message code="property.placeHolder.EnterMobileNumber" text="Please enter Mobile Name" var="MobNo"/>
 								
 								<form:input id="assoMobileno" 
 										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass" 
										data-rule-maxlength="10" onchange="fetchPropertyDetilsByMobileNo()" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="${MobNo}" autocomplete="off"/>   
 								</td> 
 								
 								<td><form:input id="emailId"
										path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" autocomplete="off"/>   
								</td>
								
								<td class="companyDetails"> 
								
									<form:input id="pannumber" 
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control hasPanno text-uppercase preventSpace" maxlength="10" onchange="fnValidatePAN(this)" autocomplete="off"/> 
								</td> 
									<form:hidden id="assoPrimaryOwn"
									path="command.provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOType"  value="P" />
 					</tr> 								
					</tbody>
					</table>	
		</c:otherwise>	
					</c:choose>
					</div>
					
				</div>

<!-- </div>  -->