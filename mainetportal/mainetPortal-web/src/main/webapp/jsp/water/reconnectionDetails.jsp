<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/water/reconnection.js"></script>
	<form:form id="connection" action="WaterReconnectionForm.html" class="form-horizontal">
	
	
	<div class="widget margin-bottom-0">
		<div class="widget-header"><h2><spring:message code="water.reconnection.reconnectionDetails"></spring:message></h2></div>
		<div class="widget-content padding">
			<div class="table-responsive">
              <table class="table table-hover table-bordered table-striped">
				<tr>
					<th><spring:message code="water.ConnectionNo"/></th>
					<th><spring:message code="water.owner.details.oname"/></th>
					<th><spring:message code="water.select"/></th>
				</tr>                                             
				<c:forEach items="${command.getReconnRequestDTO().getResponseDTOs()}" var="conection" varStatus="status">
				<tr>
					<td>${conection.connectionNo}</td>
					<td>${conection.consumerName}</td>
					<td>
				 	<form:radiobutton id="radio${status.index}" path="radio" onclick="populateRecrds(${status.index})"/> 
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		</div>
	</div>		
	</form:form>
	
