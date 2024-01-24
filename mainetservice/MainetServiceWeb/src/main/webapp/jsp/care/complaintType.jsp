<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			

<apptags:select labelCode="care.complaintType" items="${command.complaintTypes}"  path="command.careRequest.complaintType"  cssClass="chosen-select-no-results"
		 selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true"></apptags:select>
		 
<script>
$(document).ready(function(){
	//U#96737 Service Request/Complaint Type Select
	$('#complaintType').change(function() {
		//function call
		$('#residentId').val("");
		hitOnChangeSubtypeData();
		
	});
});

</script>
			                    
