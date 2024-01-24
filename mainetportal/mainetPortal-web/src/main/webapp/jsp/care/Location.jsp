<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			

<apptags:select labelCode="care.location" items="${command.locations}"  path="command.careRequestDto.location" cssClass="chosen-select-no-results"
			                    selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true"></apptags:select>


<script>
 $(document).ready(function(){
	 
	 $('#location').change(function() {
			var id = document.getElementById("location").value;
			var getPinCodeByLocation = "grievance.html?getPincodeByLocationId";
			var requestData = 'id='+id;
			var response = __doAjaxRequest(getPinCodeByLocation,'post',requestData,false,'html');
			var obj = jQuery.parseJSON(response);
			if(!$.isEmptyObject(response)){
				$('#Pincode2').empty();
				if(response!=0){
					$('#Pincode2').val(response);
				}else
				{
					$('#Pincode2').val('');
				}
			}
		});
}); 

</script>