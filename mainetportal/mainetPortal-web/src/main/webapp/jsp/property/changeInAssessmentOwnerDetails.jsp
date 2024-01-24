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
<!-- <script src="js/property/ownershipDetailsForm.js"></script>
 -->
 
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

<div class="panel panel-default  ">
<div id="owner">
<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#SingleOwnerDetails"><spring:message
				code="property.Owner(s)detail" /></a> 
	</h4> --%>
	<div class="panel-collapse collapse in" id="SingleOwnerDetails">

	 	<table id="singleOwnerTable" class="table table-striped table-bordered " >
                    <tbody>
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
                
                        <tr>
									<td>
										<form:input id="assoOwnerName" 
											path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara"
											data-rule-required="true" placeholder="Please Enter Owners Name" maxlength="200" readonly="true" />
									</td>
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="GEN" /> 
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" readonly="true" class="form-control mandColorClass" data-rule-required="true" id="ownerGender_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td>
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="REL" /> 
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" class="form-control mandColorClass" readonly="true"  data-rule-required="true" id="ownerRelation_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn.relation" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td>
							
								<td class="mand">
								<form:input id="assoGuardianName"
								path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara"
								 data-rule-required="true" placeholder="Please Enter Guardian Name" maxlength="500" readonly="true" />   
								</td>
								
								
								<td ><form:input id="assoMobileno"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass"
										data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter" readonly="true"/>   
								</td>
								
								<td><form:input id="emailId"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" readonly="true"/>   
								</td>	
								
								<td class="ownerDetails">
									<form:input id="assoAddharno"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="hasAadharNo form-control mandColorClass" maxlength="12"
									 placeholder="Please Enter" data-rule-maxlength="12" data-rule-minlength="12" readonly="true"/>
								</td>
								<td class="companyDetails">
									<form:input id="assoPanno"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" readonly="true" class="form-control text-uppercase hasPanno" maxlength="10"/>
								</td>
								
						</tr>
                        
                        
                        </tbody>
                     </table>
                     </div>
		 	 	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
		<%--  <h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#JointOwnerDetail"><spring:message
				code="property.JointOwner(s)detail"/></a> 
		</h4> --%>
		 	<div class="panel-collapse collapse in" id="JointOwnerDetail">
		 
					<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
						<tbody>

								<tr>
									<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
									<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
									<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
									<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
									<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
									<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>										
									<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
								</tr>
							
								<c:forEach  items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}" varStatus="status" >
								
								<tr class="jointOwner">
									<td>
										<c:set var="d" value="0" scope="page" />
										<form:input id="assoOwnerName_${status.count-1}" 
											path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoOwnerName" class="form-control mandColorClass hasSpecialChara"
											data-rule-required="true" placeholder="Please Enter" maxlength="200" readonly="true"/>
									</td>
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="GEN" /> 
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].genderId" class="form-control mandColorClass" id="ownerGender_${status.count-1}" readonly="true">
										<form:option value="0">
										<spring:message code="property.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td>
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="REL" /> 
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].relationId" class="form-control mandColorClass" id="ownerRelation_${status.count-1}" readonly="true">
										<form:option value="0">
										<spring:message code="property.sel.optn.relation" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td>
							
								<td>
								<form:input id="assoGuardianName_${status.count-1}"
								path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoGuardianName" class="form-control mandColorClass hasSpecialChara"
								 data-rule-required="true" placeholder="Please Enter" maxlength="500" readonly="true"/>   
								</td>
								
								<td>
									<form:input id="assoPropertyShare_${status.count-1}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].propertyShare" class="form-control mandColorClass hasNumber"
									maxlength="3" readonly="true" />
								</td>
								
								<td><form:input id="assoMobileno_${status.count-1}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoMobileno" class="hasMobileNo form-control mandColorClass"
										data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter" readonly="true"/>   
								</td>
								
								<td><form:input id="emailId_${status.count-1}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" readonly="true"/>   
								</td>		
								
								<td class="ownerDetails">
									<form:input id="assoAddharno_${status.count-1}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoAddharno" class="form-control mandColorClass hasAadharNo"
									maxlength="12" data-rule-maxlength="12" data-rule-minlength="12" readonly="true"/>
								</td>
								<td class="companyDetails">
									<form:input id="assoPanno_${status.count-1}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${status.count-1}].assoPanno" class="form-control hasPanno text-uppercase" maxlength="10" readonly="true"/>
								</td>
							
								
								
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
					</div>
					</c:when>
			
	<c:when test="${command.getOwnershipPrefix() eq 'JO'}">
	<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#Owner(s)_Detail"><spring:message
				code="property.Owner(s)detail" /></a>   
	</h4> --%>
				
				
	</c:when>	
			
		<c:otherwise>
				<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#OtherOwnerDetail"><spring:message
				code="property.Owner(s)detail" /></a> 
				</h4> --%>
			<div class="panel-collapse collapse in" id="OtherOwnerDetail">
				
			<table id="companyDetailTable" class="table text-left table-striped table-bordered">

					<tbody>				
						<tr>
									<th width="25%" class="required-control"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
									<th width="25%" class="required-control"><spring:message code="ownersdetail.contactpersonName" /></th>
									<th width="15%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>										
									<th width="15%"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						
 						<tr>
 									<td> 									
 										<form:input id="assoOwnerName"  
 											path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass hasSpecialChara" 
 											data-rule-required="true" placeholder="Please Enter" maxlength="200" readonly="true"/> 
 									</td> 
						
 								<td > 
 								<form:input id="assoGuardianName" 
 								path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass hasSpecialChara"  
 								 data-rule-required="true" placeholder="Please Enter" maxlength="500" readonly="true"/>  
								</td> 
								
 								<td><form:input id="assoMobileno" 
 										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasMobileNo form-control mandColorClass" 
										data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter" readonly="true"/>   
 								</td> 
 								
 								<td><form:input id="emailId"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true" readonly="true"/>   
								</td>	
								
								<td class="companyDetails"> 
								
									<form:input id="assoPanno" 
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control hasPanno text-uppercase" maxlength="10" readonly="true" /> 
								</td> 
 					</tr> 
								
					</tbody>
					</table>
					</div>
					
					</c:otherwise>	
					</c:choose>
					</div>
					
				</div>

</div> 