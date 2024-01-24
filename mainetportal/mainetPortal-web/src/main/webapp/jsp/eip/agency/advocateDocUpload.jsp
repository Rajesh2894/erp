<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="regheader">
					<spring:message code="eip.agency.header.advocate" />
						</div>
					
						<div class="form-elements clear">							
							<div class="element">
									<label><spring:message code="eip.agency.type" /> :</label>
									<form:input path="" value="${command.agencyType}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="1" />
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.advocate.name" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getAgencyName()}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="2"/> 
								
							</div>
						</div>
						
						<form:hidden path="agencyAdvocateMaster.countQual" id="countQual" />
			             <form:hidden path="agencyAdvocateMaster.countExp" id="countExp" />
						
						<div class="form-elements clear">							
							<div class="element">
							<label><spring:message code="eip.agency.location" /> :</label>
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">
									<form:textarea path="entity.agencyLocation" maxlength="500" tabindex="3" rows="1"  cssClass="mandClassColor"/>
									</c:if>
									<c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
									
									<form:input path="entity.agencyLocation"  disabled="true" maxlength="500" tabindex="3" rows="1"  cssClass="mandClassColor"/>
									</c:if>
									
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and (command.entity.isUploaded eq 'Y')}">
										<form:input path="entity.agencyLocation" disabled="true" maxlength="500" tabindex="3" rows="1"  cssClass="mandClassColor"/>
									</c:if>
														<span class="mand">*</span>
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.contactNo" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getEmpmobno()}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="4"/>
								
							</div>
						</div>
						
						
						
						<div class="form-elements clear">							
							<div class="element">
									<label><spring:message code="eip.agency.yearOfExpr" /> :</label>
									
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">									
									<form:input path="entity.agencyYearsOfExp" maxlength="3" tabindex="5" cssClass="hasNumber mandClassColor"/>
									</c:if>
									<c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
									<form:input path="entity.agencyYearsOfExp" disabled="true" maxlength="3" tabindex="5" cssClass="hasNumber mandClassColor"/>
									</c:if>
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and (command.entity.isUploaded eq 'Y')}">
									<form:input path="entity.agencyYearsOfExp" disabled="true" maxlength="3" tabindex="5" cssClass="hasNumber mandClassColor"/>
									</c:if>
								<span class="mand">*</span>
							</div>

                        
		
		 <div class="regheader"><spring:message code="lgl.dg.lQualification"/></div>
            <fieldset>
              <table class="gridtable" id="customFields">
              <tr>
                    <th><spring:message code="lgl.dg.lQualification"/></th>
                    <th><spring:message code="lgl.dg.lUniversity"/></th>
                    <th><spring:message code="lgl.dg.lYearOfPassing"/></th>
                    <th><spring:message code="lgl.remark"/></th>
                    <th><a href="javascript:void(0);" id="" class="addCF css_action"><i class="fa fa-plus-square-o"></i><spring:message code="lgl.add"/></a></th>
             </tr>
             
             
             <tr>
                  <c:forEach items="command.agencyAdvocateMaster.agencyAdvocateQualification">
                  
               <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateQualification[0].qQualification" id="qQualification0" cssClass="mandClassColor input2 hasCharacter maxLength500"/>
               </td>
                <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateQualification[0].qUniversity" id="qUniversity0" cssClass="mandClassColor input2 hasCharacter maxLength100"/>
               </td>
                <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateQualification[0].qPassyear" id="qPassyear0" cssClass="mandClassColor input2 hasNumber maxLength4"/>
               </td>
               <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateQualification[0].qRemark" id="qRemark0" cssClass="mandClassColor input2 hasCharacter maxLength500"/>
               </td>
                	<th><a href="javascript:void(0);" class="remCF css_action"><i class="fa fa-minus-square-o"></i><spring:message code="lgl.remove"/></a></th>
                  </c:forEach>
             </tr>
              </table>
            </fieldset>	
            <div class="regheader"><spring:message code="lgl.dg.lExperience"/></div>
            <table class="gridtable" id="exprience">
              <tr>
                    <th><spring:message code="lgl.dg.lOrganizationName"/></th>
                    <th><spring:message code="lgl.dg.lSpecializationArea"/></th>
                    <th><spring:message code="lgl.remark"/></th>
                    <th><spring:message code="lgl.dg.lExperience"/></th>
                    <th><a href="javascript:void(0);" id="" class="addCF1 css_action"><i class="fa fa-plus-square-o"></i> <spring:message code="lgl.add"/></a></th>
             </tr>
             
             
             <tr>
                  <c:forEach items="command.agencyAdvocateMaster.agencyAdvocateExperience">
                  
               <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateExperience[0].exOrganization" id="exOrganization0" cssClass="mandClassColor input2 hasCharacter maxLength100"/>
               </td>
                <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateExperience[0].exPracticearea" id="exPracticearea0" cssClass="mandClassColor input2 hasCharacter maxLength500"/>
               </td>
                <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateExperience[0].exRemark" id="exRemark0" cssClass="mandClassColor input2 hasCharacter maxLength500"/>
               </td>
               <td>
                      <form:input path="agencyAdvocateMaster.agencyAdvocateExperience[0].exExperience" id="exExperience0" cssClass="mandClassColor input2 hasNumber"/>
               </td>
                	<th><a href="javascript:void(0);" class="remCF1 css_action"><i class="fa fa-minus-square-o"></i> <spring:message code="lgl.remove"/></a></th>
                  </c:forEach>
             </tr>
              </table>
              </div>
					