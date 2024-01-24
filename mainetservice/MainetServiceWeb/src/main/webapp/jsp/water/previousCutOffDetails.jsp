<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript">

</script>
		<form:form id="connection" action="MeterCutOFF.html" class="form-horizontal">
				<div class="table-responsive">
                <table class="table table-hover table-bordered table-striped">
				<tr>
				<th><spring:message code="water.meter.cutOffDate"/></th>
				<th><spring:message code="water.meter.cutOffReading"/></th>
				<th><spring:message code="water.meter.CutOffReason"/></th>
				<th><spring:message code="water.meter.cutOffMeterNo"/></th>
				<th><spring:message code="water.meterDet.ownerShip"/></th>
				<th><spring:message code="water.apprvdBy"/></th>
				
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
	
