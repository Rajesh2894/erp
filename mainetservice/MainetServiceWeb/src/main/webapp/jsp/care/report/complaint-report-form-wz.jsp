<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
			
<script type="text/javascript">
$(document).ready(function(){
	$('select[id^="codIdOperLeve"]').val('-1');
    var kdmcEnv =  $("#kdmcEnv").val();
	if (($("input[name='careReportRequest.reportName']:checked")
			.val() == "S") && kdmcEnv == 'Y') {
		//$('#zone-ward').html(response);
		$('#zone-ward').show();
	}
});
</script>			

<div class="form-group">
<apptags:lookupFieldSet baseLookupCode="${command.prefixName}" hasId="true" pathPrefix="command.careReportRequest.codIdOperLevel"
		hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="false" isNotInForm="true"
		cssClass="form-control margin-bottom-15"  showAll="true" isMandatory="false"/>
</div>		       