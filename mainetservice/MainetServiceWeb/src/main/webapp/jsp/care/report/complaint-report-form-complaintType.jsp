<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>
$(function() {
	$('#complaintType').chosen();	
});
</script>

<apptags:select labelCode="care.complaintType" items="${command.complaintTypes}"  path="command.careReportRequest.complaintType" 
		                    selectOptionLabelCode="Select" isMandatory="true" showAll="true" isLookUpItem="true"></apptags:select>