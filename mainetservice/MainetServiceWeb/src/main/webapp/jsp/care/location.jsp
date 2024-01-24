<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			

			                    
<apptags:select labelCode="care.location" items="${command.locations}"  path="command.careRequest.location" cssClass="chosen-select-no-results"
		 selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true"></apptags:select>

<script>
$(document).ready(function(){
	$('#location').change(function() {
		var id = $('#location').val();
		var getPinCodeByLocation = "/MainetService/GrievanceDepartmentRegistration.html?getPincodeByLocationId";
		var _csrf = "${_csrf.token}";
		$.ajax({
			url: getPinCodeByLocation,
			type: "POST",
			data: {
				id: id,
				_csrf:_csrf
			},
			success: function(response) {
				$('#Pincode2').empty();
				if(response!=0){
					$('#Pincode2').val(response);
				}else
				{
					$('#Pincode2').val('');
				}
			},
			error: function(xhr,errorType,	exception) {
				var errorMessage = exception ||
				xhr.statusText;
				alert("Error occured :" + errorMessage);
			}
		});
	});
});

</script>