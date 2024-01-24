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

<script type="text/javascript">
$(document).ready(function(){
	$('.trmGroupDept').attr('disabled',true);
});

</script>
					 <div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="water.consumerName"></spring:message></label>
						<div class="col-sm-4">
							<form:input path="changeOwnerResponse.oldOwnerFullName" type="text"
								class="form-control disablefield" id="conName" disabled="true"/>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="water.aadhar" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control disablefield"
								path="changeOwnerResponse.cooUidNo" id="oldAdharNo" disabled="disabled"/>
						</div>
					</div>
					<div class="form-group">
						<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
							showOnlyLabel="false" pathPrefix="ownerDTO.csmrInfoDTO.trmGroup" 
							isMandatory="false" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"	
							cssClass="form-control trmGroupDept"  showAll="true"  />   
					</div>
					<div class="form-group">

						<label class="col-sm-2 control-label"><spring:message
								code="water.ConnectionSize" /></label>
								
						<div class="col-sm-4">
						 <div class="input-group">
							<c:set var="baseLookupCode" value="CSZ" />
								<form:select path="changeOwnerResponse.conSize" class="form-control" id="conSize" >
									<form:option value="0"><spring:message code="water.select" /></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
								</form:select>
								<span class="input-group-addon"><spring:message code="water.inch" /></span>
								</div>
						</div>
					</div>
