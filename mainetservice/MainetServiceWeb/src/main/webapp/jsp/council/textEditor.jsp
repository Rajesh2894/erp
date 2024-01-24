<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/ckeditor/ckeditor.js"></script>
<script src="assets/libs/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript">

$(document)
.ready(
    function() 
    {
    	 $('#String1').ckeditor({skin : 'bootstrapck'});
    });



    function closeCurrentTab() {
	close();
    }
</script>




<form:form action="CouncilMOM.html" cssClass="form-horizontal">
	<div class="form-group">
	
	
	
	<%-- <label class="col-sm-2 control-label "><spring:message
											code="council.mom.actionTaken" text="Action Taken" /></label>
					<div class="col-sm-4">
				<form:select path="meetingMOMDtos[0].status"
					cssClass="form-control chosen-select-no-results" id="status0"
					disabled="${command.saveMode eq 'VIEW' }">
					<form:option value="0">
						<spring:message code='council.dropdown.select' />
					</form:option>
					<c:forEach items="${command.getLevelData('PPS')}" var="lookUp">
						<form:option value="${lookUp.lookUpCode}"
							code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
					</c:forEach>
				</form:select>
				</div>
	
	
	
	 --%>
	
	
	
	
	
	
	
		<div class="col-sm-2">
			<spring:message code="council.mom.resolutionComment"
				text="Resolution Comments" />

		</div>

		<div class="col-sm-8">
			<form:textarea id="String1" path="resolutionComments" 
				 disabled="${command.saveMode eq 'VIEW' }"/>
				
		</div>
	</div>
</form:form>