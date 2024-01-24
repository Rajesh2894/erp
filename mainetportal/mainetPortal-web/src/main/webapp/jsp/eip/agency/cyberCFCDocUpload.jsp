
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

										<div class="regheader"><spring:message code="eip.agency.header.cyber" /></div>
									<div class="form-elements clear">
										<div class="form-elements">							
											<div class="element">
												<label><spring:message code="eip.agency.type" /> :</label>
												<form:input path="" value="${command.agencyType}" maxlength="20" readonly="true" cssClass="disablefield" tabindex="1" />
								
											</div>
											<div class="element">
											<label><spring:message code="eip.agency.name" /> :</label>
													<form:input path="" value="${userSession.getEmployee().getAgencyName()}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="2"/> 
								
											</div>
										</div>
										<div class="form-elements">							
											<div class="element">
												<label><spring:message code="eip.agency.location" /> :</label>
										
													<c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
													<form:input path="entity.agencyLocation" maxlength="500" disabled="true" tabindex="3" rows="1" cssClass="mandClassColor"/>
													</c:if>
													<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">
														<form:input path="entity.agencyLocation" maxlength="500" tabindex="3" rows="1" cssClass="mandClassColor"/>
														</c:if>
														<c:if test="${(empty command.cfcAttachmentsAfterReject) and (command.entity.isUploaded eq 'Y')}">
														<form:input path="entity.agencyLocation" value="${userSession.getEmployee().getAgencyLocation()}" maxlength="500" disabled="true" tabindex="3" rows="1" cssClass="mandClassColor"/>
														</c:if>
														
														<span class="mand">*</span>
											</div>
												<div class="element">
												<label><spring:message code="eip.agency.owner.name" /> :</label>
														<form:input path="" value="${userSession.getEmployee().getEmpname() }"  maxlength="10" readonly="true" cssClass="disablefield" tabindex="4"/> 
								
												</div>
										</div>
										<div class="form-elements">							
											<div class="element">
												<label><spring:message code="eip.agency.panNo" /> :</label>
													<form:input path="" value="${userSession.getEmployee().getPanCardNo()} " maxlength="10" readonly="true" cssClass="disablefield" tabindex="5"/>
								
											</div>
											<div class="element">
													<label><spring:message code="eip.agency.login.name" /> :</label>
														<form:input path="" value="${userSession.getEmployee().getEmploginname()}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="6"/> 
								
											</div>
										</div>	
									</div>
								
						
