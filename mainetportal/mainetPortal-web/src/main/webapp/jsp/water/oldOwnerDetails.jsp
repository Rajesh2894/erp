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

					 <div class="form-group">
						<label class="col-sm-2 control-label" for="conName"><spring:message
						
								code="water.consumerName"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="changeOwnerResponse.oldOwnerFullName" type="text"
								class="form-control disablefield" id="conName" disabled="true"/>
						</div>

						<label class="col-sm-2 control-label" for="oldAdharNo"><spring:message
								code="water.aadhar" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control disablefield"
								path="changeOwnerResponse.cooUidNo" id="oldAdharNo" disabled="true"/>
						</div>
					</div>
					<div class="form-group margin-bottom-0">
						<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
							showOnlyLabel="false" pathPrefix="changeOwnerResponse.trmGroup"
							isMandatory="false" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" disabled="true"
							cssClass="form-control"  showAll="true"/>
					</div>
					<div class="form-group">

						<label class="col-sm-2 control-label" for="conSize"><spring:message
						
								code="water.ConnectionSize" /></label>
								
						<div class="col-sm-4">
						 <div class="input-group">
							<c:set var="baseLookupCode" value="CSZ" />
								<form:select path="changeOwnerResponse.conSize" class="form-control" id="conSize" disabled="true">
									<form:option value="0"><spring:message code="water.select" /></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
								</form:select>
								<span class="input-group-addon"><spring:message code="water.inch" /></span>
								</div>
						</div>
					</div>
