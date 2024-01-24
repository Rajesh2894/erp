<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			
			<div class="widget-header">
		         <h4><spring:message code="eip.techPersonDet"/></h4>
	        </div>
	        
	      <div class="widget-content padding">
	         <div class="form-group">
                <label class="col-sm-2 control-label required-control"><spring:message code="eip.nameOfApplicant" />:</label>
                <div class="col-sm-4">
                  	<c:set var="baseLookupCode" value="TTL" />
					<form:input path="titleName" value=""  cssClass=" form-control" disabled="true"/>
                </div>
                <label class="col-sm-2 control-label required-control">First Name</label>
                <div class="col-sm-4">
                  <form:input path="licenseMaster.licFname" cssClass=" form-control " disabled="true"/>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">Middle Name</label>
                <div class="col-sm-4">
                 <form:input path="licenseMaster.licMname"   cssClass="form-control" disabled="true"/>
                </div>
                <label class="col-sm-2 control-label required-control">Last Name</label>
                <div class="col-sm-4">
                  <form:input path="licenseMaster.licLname"  cssClass=" form-control" disabled="true"/>
                </div>
              </div>
	        
				<!-- <div class="form-group"> -->
					<%-- <div class="table clear">
							<div class="col-155">
								<label><spring:message code="eip.nameOfApplicant" /> :</label>
							</div>
							<div class="col-50">
								<c:set var="baseLookupCode" value="TTL" />
								<form:input path="" value=""  cssClass=" form-control hasCharacter input2 disablefield" disabled="true"/>
								 
							</div>
							<div class="col-130">
								<form:input path="licenseMaster.licFname" cssClass=" form-control " disabled="true"/>
								
							</div>
							<div class="col-165">
								<form:input path="licenseMaster.licMname"   cssClass="form-control" disabled="true"/>
							</div>
							<div class="col-180">
								<form:input path="licenseMaster.licLname"  cssClass=" form-control" disabled="true"/>
								</div>
					</div> --%>
					
					 <div class="form-group">							
							<label class="col-sm-2 control-label required-control" ><spring:message code="eip.citizen.reg.dateOfBirth" /> :</label>
							<div class="col-sm-4">
							<form:input path="entity.empdob" maxlength="10" readonly="true" cssClass=" form-control " disabled="true"/>
							</div>
							<%-- <c:set var="baseLookupCode" value="TRY" />
								<label  class="col-sm-2 control-label required-control"><spring:message code="eip.nationality"/> :</label>
								<div class="col-sm-4">
								<apptags:lookupField items="${command.getLevelData(baseLookupCode,1)}"
									path="" selectOptionLabelCode="tp.SelectNationality" isMandatory="true"  hasId="true" showOnlyLabel="true"/>								
							</div> --%>
					</div>
					 <div class="form-group">	
							<label   class="col-sm-2 control-label required-control"><spring:message code="eip.qualification"/> :</label>
							<div class="col-sm-4">		
								<form:input path="licenseMaster.licQualification" maxlength="50" cssClass="form-control" disabled="true" /> 
							</div>
								<label  class="col-sm-2 control-label required-control"><spring:message code="eip.experience"/> :</label>
							<div class="col-sm-4">
								<form:input path="" maxlength="4"  cssClass="form-control" disabled="true"/>
							</div>
					</div>	 
					 <c:set value="${command.getCpdValue()}" var="cpdValue"/>	
					<div class="form-group">							
						<div class="element">
								<label  class="col-sm-2 control-label required-control"><spring:message code="eip.regnNo"/>
									<c:if test="${cpdValue eq  'A'}">
									<spring:message code="eip.coa"/>
									</c:if>
									<c:if test="${cpdValue eq  'D'}">
									<spring:message code="eip.itpi"/>
									</c:if>
									<c:if test="${cpdValue eq  'G' || cpdValue eq  'F' }">
									<spring:message code="eip.ie"/>
									</c:if>:</label>
								<div class="col-sm-4">
									<form:input path="licenseMaster.licCntArcregno"  cssClass="form-control" disabled="true"/>
								</div>
						</div>
								<label class="col-sm-2 control-label required-control"><spring:message code="eip.regnNoULB"/> :</label>
						<div class="col-sm-4">
								<form:input path=""  cssClass="form-control" disabled="true"/> 
						</div>
					</div>
					<div class="form-group">							
								<label class="col-sm-2 control-label required-control" ><spring:message code="eip.admin.contactUs.address" /> :</label>
						<div class="col-sm-4">		
								<form:textarea path="licenseMaster.licApplicantAddr" maxlength="500"  rows="1"  cssClass="form-control" disabled="true"/>
						</div>
								<label class="col-sm-2 control-label required-control"><spring:message code="eip.payment.mobileNo" /> :</label>
						<div class="col-sm-4">
								<form:input path="licenseMaster.licPhoneno"  maxlength="10" readonly="true" cssClass="form-control" disabled="true"/>
						</div>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="eip.TelNo"/> :</label>
						<div class="col-sm-4">
							<form:input path="licenseMaster.licPhoneno"  cssClass="disablefield" disabled="true"/>
						</div>
							<label class="col-sm-2 control-label required-control"><spring:message code="eip.admin.auth.email"/> :</label>
						<div class="col-sm-4">	
							<form:input path="licenseMaster.licEmail"  cssClass="disablefield" disabled="true"/>
						</div>
					</div> 
				</div>
					