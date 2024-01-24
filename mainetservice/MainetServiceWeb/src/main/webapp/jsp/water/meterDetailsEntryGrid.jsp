<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/water/meterDetailsEntry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
	<div class="widget-header">
	<h2><spring:message code="water.meterDet.entry"/></h2>
	</div>
		<div class="error-div" style="display:none;" id="errorDivDeptMas"></div>
		<div class="widget-content padding">

	<form:form  method="post" name=""  class="form-horizontal">
			
			<div class="table">
				<table id="workOrderGrid"></table>
				<div id="pagered"></div>
			</div>
	</form:form>
	</div>
	</div>
	</div>
	