<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form action="ScrutinyLabelView.html?setViewData" method="post">
	
	<div class="btn_fld clear margin-top-5 text-center"> 
       		<input type="hidden" value="${userSession.getScrutinyCommonParamMap().get('APM_APPLICATION_ID')}" name="appId"/>
       		<input type="hidden" value="${userSession.getScrutinyCommonParamMap().get('SM_SERVICE_ID')}" name="serviceId"/>
       		<input type="hidden" value="${userSession.getScrutinyCommonParamMap().get('taskId')}" name="taskId"/>
       		<c:if test="${not empty userSession.getScrutinyCommonParamMap().get('REFERENCE_ID')}">
       		<input type="hidden" value="${userSession.getScrutinyCommonParamMap().get('REFERENCE_ID')}" name="refNo"/>
       		</c:if>
       		<input type="button" class="btn btn-success" value="<spring:message code="cfc.scrutiny" />" id="scrutinyBtn" onclick="openScrutinyDetailsForm(this);"/>
      	 		<apptags:backButton url="AdminHome.html"></apptags:backButton>
      	 		
	</div>
	</form:form>
