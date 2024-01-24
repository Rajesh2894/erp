<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			

			                    
<apptags:select labelCode="care.department" items="${command.departments}"  path="command.careRequest.departmentComplaint" cssClass="chosen-select-no-results" 
	     selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true"></apptags:select>

<script>

$('#departmentComplaint').change(function() {

	if($('#departmentComplaint').val() >0){
		var requestData = {"deptId":$('#departmentComplaint').val(),
							"orgId":$('#orgId').val()}
		var response = __doAjaxRequest('GrievanceDepartmentRegistration.html?grievanceComplaintTypes','post',requestData,false,'html');
		
		if(response=="") {
			$('#id_complaintType_div').html('');
			$('#id_complaintType_div').hide();
		} else{
			$('#id_complaintType_div').html(response);
			$('#id_complaintType_div').show();
		}
		
		var response = __doAjaxRequest('GrievanceDepartmentRegistration.html?grievanceLocations','post',requestData,false,'html');
		if(response=="") {
			$('#id_location_div').html('');
			$('#id_location_div').hide();
		} else{
			$('#id_location_div').html(response);
			$('#id_location_div').show();
		}
		
		
	}else if($('#departmentComplaint').val() == 0){
		$("#complaintType option[value!='0']").remove();
		$("#location option[value!='0']").remove();
	}
});

</script>