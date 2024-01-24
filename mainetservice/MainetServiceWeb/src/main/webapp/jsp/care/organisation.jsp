<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			

			                    
<apptags:select labelCode="care.organisation" items="${command.organisations}"  path="command.careRequest.orgId"  cssClass="chosen-select-no-results"
			                    selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true"></apptags:select>

<script>
$('#orgId').change(function() {
	if($('#orgId').val() >0){
		var requestData = {"orgId":$('#orgId').val()}
		var response = __doAjaxRequest('GrievanceDepartmentRegistration.html?grievanceDepartments','post',requestData,false,'html');
		
		if(response=="") {
			$('#id_department_div').html('');
			$('#id_department_div').hide();
		} else{
			$('#id_department_div').html(response);
			$('#id_department_div').show();
		}
		
	}else if($('#orgId').val() == 0){
		$("#department option[value!='0']").remove();
		$("#complaintType option[value!='0']").remove();
		$("#location option[value!='0']").remove();
	}
});
</script>