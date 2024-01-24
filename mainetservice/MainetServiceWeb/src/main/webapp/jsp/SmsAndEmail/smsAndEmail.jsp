<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/SmsAndEmail/smsAndEmail.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<div class="smsEmailForm">
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content"> 
	<div class="widget ">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="common.smsAndEmail" text="SMS And Email" /></strong>
			</h2>
			 <apptags:helpDoc url="SMSAndEmail.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			
	<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
    </div>
	<form:form action="SMSAndEmail.html" method="post" class="form-horizontal" id="formList">
			<div class="form-group">
			<label class="col-sm-2 control-label" for="entity.dpDeptid.dpDeptid"><spring:message code="rti.depName" text="Department Name"/></label>
			<div class="col-sm-4">
				<form:select path="entity.dpDeptid.dpDeptid" onchange="fn_setService(this,'serviceList')" cssClass="form-control chosen-select-no-results mandColorClass" id="deptId" >
					<form:option value="0">
						<spring:message code="sms.dept" text="Select" />
					</form:option>
					<form:options items="${command.departmentList}" />
				</form:select>
			</div>
			<label class="col-sm-2 control-label" for="entity.smfid.smfid"><spring:message 
					code="rti.serviceName" text="Service Name"/></label>
			<div class="col-sm-4">
				<form:select path="entity.serviceId.smServiceId" cssClass="form-control chosen-select-no-results mandColorClass mediaType serviceList" id="serviceId">
					<form:option value="0"><spring:message code="sms.service" /></form:option>
				</form:select>
			</div>
		</div>
		
	<div class="form-group">
		<label class="col-sm-2 control-label"> <spring:message code="common.event" text="Event"/> </label>
		<div class="col-sm-4"> 
			<form:select path="entity.smfid.smfid" cssClass="form-control chosen-select-no-results mandColorClass" id="eventId">
			<form:option value="0"><spring:message code="master.selectDropDwn" text="Select" /></form:option>
			<c:forEach items="${command.eventsList}" var="eventArray" >
			<form:option value="${eventArray[0]}" label="[ ${eventArray[4]} ]>>[ ${eventArray[3]} ] "></form:option>
			</c:forEach>
			</form:select>
		</div>
			
		<%-- <label class="col-sm-2 control-label"><spring:message code="sms.transactionlType" /></label>
		<div class="col-sm-4">				
			<label class="radio-inline"><form:radiobutton path="entity.tranOrNonTran" value="Y" name="transactionalType" id="transactionalType"/><spring:message code="sms.transactional" /></label>
			<label class="radio-inline"><form:radiobutton path="entity.tranOrNonTran" value="N" name="transactionalType" id="transactionalType"/><spring:message code="sms.nonTransactional" /></label>
		</div> --%>
		
		 <label class="col-sm-2 control-label"><spring:message code="rti.alertSub" text="Alert Type"/></label>
		<div class="col-sm-4">
		  <label class="radio-inline"><form:radiobutton path="entity.alertType" value="S" id="alertType"/><spring:message code="rti.bysms" text="Sms"/></label>
		  <label class="radio-inline"><form:radiobutton path="entity.alertType" value="E" id="alertType"/> <spring:message code="rti.bymail" text="Email"/></label>
		  <label class="radio-inline"><form:radiobutton path="entity.alertType" value="B" id="alertType"/><spring:message code="rti.both" text="Both"/></label>
		</div>
	</div>
			
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="eip.typeofmsg"/></label>
		<div class="col-sm-4">
		<form:select path="entity.smsAndmailTemplate.messageType" cssClass="form-control mandColorClass chosen-select-no-results" id="messageType">
		<form:option value=""><spring:message code="master.selectDropDwn" text="Select" /></form:option>
		<c:forEach items="${command.messageLookUp}" var="type">
		<form:option value="${type.lookUpCode}">${type.lookUpDesc}</form:option>
		</c:forEach>
		</form:select>
		</div>
	</div>

			<div class="text-center padding-bottom-20">
			<button type="button" class="btn btn-blue-2" onclick="searchTemplate()"><spring:message code="master.search" text="Search"/></button>
			<button type="Reset" class="btn btn-warning" onclick="resetSearchList()"><spring:message code="reset.msg" text="Reset"/></button>
			<button type="button" class="btn btn-success" onclick="createTemplate()"><spring:message code="add.msg" text="Add" /></button>
			</div>
		<input type="hidden" id="langId" value="${userSession.languageId}" />
		<table id="grid" class="padding-bottom-20"></table>
			<div id="pagered"></div>
	</form:form>

		</div>
	</div>
</div>

</div>