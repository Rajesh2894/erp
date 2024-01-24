<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script>
	function findNextPage(obj) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var url = 'WaterExceptionalGap.html?nextPage';
		var requestData = {
			"mode" : $("#addEdit").val(),
			"meterType" : $("#meterType").val(),
		}
		var returnData = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$("#result").html(returnData);
	}
</script>

<div id="result">

	<apptags:breadcrumb></apptags:breadcrumb>

	<!-- ============================================================== -->
	<!-- Start Content here -->
	<!-- ============================================================== -->
	<div class="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2><spring:message code="water.exception.gap.tool" text="Exceptional Gap Tool" /></h2>
				<!-- <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div> -->
				<apptags:helpDoc url="WaterExceptionalGap.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<form:form action="WaterExceptionalGap.html" method="POST"
					class="form-horizontal" id="exceptionalGapToolSelection">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<h4 class="margin-top-0">
						<spring:message code="water.exception.meter.mode.type" text="Mode and Meter Type" />
					</h4>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="" text="Select Action" /></label>
						<div class="col-sm-4">
							<form:select path="addEdit" class="form-control" id="addEdit">
								<form:option value=""><spring:message code="water.select" /></form:option>
								<form:option value="A"><spring:message code="water.exception.add.new.entry" /></form:option>
								<form:option value="E"><spring:message code="water.edit.exist.entry" /></form:option>
							</form:select>
						</div>

						<label class="control-label col-sm-2 "><spring:message
								code="MeterReadingDTO.meterType" /></label>
						<div>
							<c:set var="baseLookupCode" value="WMN" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}" path="meterType"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								showAll="false" selectOptionLabelCode="eip.select"
								isMandatory="true" />
						</div>

					</div>

					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-success"
							onclick="return findNextPage(this);"><spring:message code="water.btn.next" /></button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'"><spring:message code="water.btn.cancel" /></button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<!-- End of info box -->
