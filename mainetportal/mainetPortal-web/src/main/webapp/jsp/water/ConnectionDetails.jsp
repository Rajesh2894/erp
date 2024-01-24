<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
$(document).ready(function(){
	$(':radio').removeAttr('checked');
});
function populateRecrds(element)
{
	 var URL = 'ChangeOfOwnership.html?getSelectedData';
	 var data = {
				"id" : element,
			};

	 var returnData=__doAjaxRequest(URL,'post',data,false);
		  
			 $("#conNum").val(returnData.connectionNumber);
			 $("#conName").val(returnData.cooOname);
			 $("#conCat").val(returnData.conCategory);
			 $("#conType").val(returnData.conType);
			 $("#conSize").val(returnData.conSize);
			 $("#oldAdharNo").val(returnData.cooUidNo);
			 $("#dwzid1").val(returnData.codDwzid1);
		
			 
			 
			 $('.child-popup-dialog').hide();

				disposeModalBox();

				$.fancybox.close(); 
			
} 
</script>
<form:form id="connection" action="ChangeOfOwnership.html" class="form-horizontal">
				<div class="table-responsive">
                <table class="table table-hover table-bordered table-striped">
				<tr>
				<th><spring:message code="water.nodues.connectionNo"/></th>
				<th><spring:message code="water.owner.details.oname"/></th>
				<th><spring:message code="water.select"/></th>
				</tr>
				 <c:forEach items="${command.getChangeOwnerMaster().getResponseDto()}" var="conection" varStatus="status">
				<tr>
				<td>${conection.connectionNumber}</td>
				<td>${conection.cooOname}</td>
				<td>
				<form:radiobutton id="radio${status.index}" path="radio" onclick="populateRecrds(${status.index})"/>
				</td>
				
				</tr>
				 </c:forEach>
				</table>
				</div>
				
		</form:form>
	
