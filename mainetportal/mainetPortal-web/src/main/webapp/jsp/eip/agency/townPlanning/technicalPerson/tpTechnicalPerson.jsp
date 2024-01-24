<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	$(document).ready(function() {
		<c:if test="${command.viewdata eq 'R' || command.viewdata eq 'H' }"> 
			$('#testID').find(':input').attr('disabled', 'disabled').removeClass('mandClassColor').not(":button").addClass("disablefield");
		
		</c:if> 
	});
</script>

			<div id="testID">
				<div class="widget-content padding">
					 <div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="common.addOwnTitle" /> :</label>
							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="entity.licTitle"  selectOptionLabelCode="tp.Title" hasId="true"  isMandatory="true" cssClass="form-control"/>
							</div>
							<label  class="col-sm-2 control-label required-control">
							<spring:message code="common.firstName" /> :
							</label>
							<div class="col-sm-4">
								<form:input path="entity.licFname" cssClass="form-control hasCharacter" maxlength="100"/>
								
							</div>
					</div>	
						
				   <div class="form-group">
								<label class="col-sm-2 control-label">Middle Name :</label>
							<div class="col-sm-4">
								<form:input path="entity.licMname"   cssClass="col-50 mandClassColor form-control input2 " maxlength="100"/>
							</div>
							<div class="col-sm-2 control-label required-control">
								<label><spring:message code="common.lastName" />:</label>
							</div>
							<div class="col-sm-4">
								<form:input path="entity.licLname"  cssClass="col-50 mandClassColor form-control hasCharacter input2 mandClassColor" maxlength="100"/>
							</div>
							
					</div>
					 <div class="form-group">
					       <div class="col-sm-2 control-label required-control">			
									<label><spring:message code="eip.citizen.reg.dateOfBirth" /> :</label>
					         </div>	
					         <div class="col-sm-4">			
									<form:input  path="" value="${command.licDob}"  maxlength="10"  cssClass="col-50 mandClassColor form-control datepicker cal" disabled="true"/>
							  </div>		
							
							<c:set var="baseLookupCode" value="TRY" />
							 <div class="col-sm-2 control-label required-control">		
								<label><spring:message code="eip.nationality"/> :</label>
							</div>	
							<div class="col-sm-4">
								<apptags:lookupField items="${command.getLevelData(baseLookupCode,1)}"
									path="entity.licNationality" selectOptionLabelCode="tp.SelectNationality" isMandatory="true"  hasId="true" cssClass="col-50 mandClassColor form-control"/>								
							</div>
					</div>		
					 <div class="form-group">	
							 <div >
									<label class="col-sm-2 control-label required-control"><spring:message code="eip.qualification"/> :</label>
							 </div>	
							<div class="col-sm-4"> 	
									<form:input path="entity.licQualification" maxlength="50" cssClass="col-50  form-control hasCharacterWithPeriod" />
							</div>		 
							  <div >
									<label class="col-sm-2 control-label required-control"><spring:message code="eip.experience"/> :</label>
							 </div>	
							 <div class="col-sm-4"> 	
									<form:input path="entity.licYearofexp" maxlength="3"  cssClass=" col-50 form-control has3Decimal"/>
                             </div>
					</div>
					 <div class="form-group">
						<c:if test="${!command.notMandatory }">					
						<div >
								<label class="col-sm-2 control-label required-control"><spring:message code="eip.regnNo"/><c:out value="${command.registrationInstitude}"></c:out> :</label>
						</div>	
						<div class="col-sm-4"> 		
								<form:input path=""  cssClass=" col-50 mandClassColor form-control " maxlength="100"/>
					    </div>
						</c:if>	 
						<c:if test="${command.notMandatory }"> 				
					    <div >
								<label class="col-sm-2 control-label required-control"><spring:message code="eip.regnNo"/><c:out value="${command.registrationInstitude}"></c:out> :</label>
					     </div>		
					    <div class="col-sm-4"> 			
								<form:input path="entity.licCntArcregno"   maxlength="100" cssClass=" col-50 mandClassColor form-control "/>
					    </div>			
						</c:if>	
						<div >
								<label class="col-sm-2 control-label"><spring:message code="eip.regnNoULB"/> :</label>
						</div>	
						<div class="col-sm-4"> 			
								<form:input path=""  maxlength="100" cssClass=" col-50 mandClassColor form-control " /> 
						</div>
					</div>
					
				 <div class="form-group">				
						<div >
								<label class="col-sm-2 control-label required-control"><spring:message code="eip.zone.address" /> :</label>
								
						</div>
							<div class="col-sm-4">	
								<form:textarea path="entity.licApplicantAddr" maxlength="500"  rows="1"  cssClass=" col-50 mandClassColor form-control "/>
								
							</div>	
						<div >
								<label class="col-sm-2 control-label"><spring:message code="eip.payment.mobileNo" /> :</label>
						</div>
						<div class="col-sm-4">		
								<form:input path=""  value="${command.mobileNo}" maxlength="10" readonly="true" cssClass="col-50  form-control disablefield hasNumber" />
						</div>		
					</div>
					
					
					 <div class="form-group">	
						<div>
							<label class="col-sm-2 control-label"><spring:message code="tp.TelNo"/> :</label>
						</div>	
						 <div class="col-sm-4">
							<form:input path="entity.licPhoneno"  pattern="[7-9]{1}[0-9]{9}" maxlength="10" cssClass="col-50  form-control  hasNumber" />
						</div>
						<div>
							<label class="col-sm-2 control-label required-control"><spring:message code="eip.admin.auth.email"/> :</label>
						</div>	
						 <div class="col-sm-4">
							<form:input path="entity.licEmail"  cssClass="col-50 mandClassColor form-control" maxlength="50"/>
						 </div>	
						</div>
					</div>
				</div>	
