<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript">

</script>
		<form:form action="MeterRestoration.html" class="form-horizontal">
			<div class="table-responsive">
                <table class="table table-hover table-bordered table-striped">
				<tr>
				<th><spring:message code="water.meterRes.restorationDate"/></th>
				<th><spring:message code="water.meterRes.restorationReading"/></th>
				<th><spring:message code="water.remark"/></th>
				<th><spring:message code="water.meter.cutOffMeterNo"/></th>
				<th><spring:message code="water.meterRes.ownership"/></th>
				<th><spring:message code="water.disconet.approve.by"/></th>
				
				</tr>                                             
				  <c:forEach items="${command.getCutOffRestorationDTO()}" var="previousCutOff" varStatus="status">
				<tr>
				<td>${previousCutOff.cutOffDate}</td>
				<td>${previousCutOff.cutResRead}</td>
				<td>${previousCutOff.cutResRemark}</td>
				<td>${previousCutOff.meterNo}</td>
				<td>${previousCutOff.ownership}</td>
				<td>${previousCutOff.approvedBy}</td>
				</tr>
				 </c:forEach>
				</table>
			</div>
				
		</form:form>
	
