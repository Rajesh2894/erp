<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 

<div id="heading_wrapper">
		
	<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

		<div class="form-div">
			<div class="element">
		<ul class="">
			<c:forEach items="${command.serviceMap}" var="serMap" varStatus="status">
				<form:form action="${serMap.value.serUrl}" method="post" id="frm${serMap.value.serviceId}">
					<input type="hidden" name="serviceId" value="${serMap.value.serviceId}" />
					 <li><a href="javascript:void(0);" onclick="javascript:document.getElementById('frm${serMap.value.serviceId}').submit();">${serMap.value.smServiceId.smServiceName}</a></li>
				</form:form>	
			</c:forEach>
		</ul>
		</div>
		</div>
		</div>
	</div>
	</div>
</div>