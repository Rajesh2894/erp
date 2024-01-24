<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css" href="css/mainet/themes/jquery-ui-timepicker-addon.css" />


<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/ui/jquery-ui-sliderAccess.js"></script>

<script type="text/javascript">
$(document).ready(function(){

	$('#frTime123').timepicker({
		hourMin: 8,
		hourMax: 16
	});
	
	$("#addRoad").click(
			function() {
				var cnt = $('#tbl1 tr').length - 1;
				var row = '<td>'
					  +cnt
						+ '</td>'
						+ '<td>'
						+ '<c:set var="baseLookupCode" value="RDT" />'
						+ '<select name="csmrInfo.roadList['+cnt+'].crtRoadTypes" id="roadType'+cnt+'" class="form-control">'
						+ '<option value="0">'
						+ "Select Road Type"
						+ '</option>'
						+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
						+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
						+ '</c:forEach>'
						+ '</select></td>'
						+ '<td><input  type="text" class="form-control" name="csmrInfo.roadList['+cnt+'].crtRoadUnits" id="roadLength'+cnt+'"></input></td>'
						+ '<td><input  type="text" class="form-control" name="csmrInfo.roadList['+cnt+'].crtRoadTrench" id="roadTrench'+cnt+'"></input></td>';
				$('#tbl1 tr')
						.last()
						.after(
								'<tr>'
										+ row
										+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Road" id="deleteRoad" onclick="removeRow('
										+ cnt
										+ ')"><i class="fa fa-trash"></i></a></td></tr>');
				cnt++;

			});

$("#deleteRoad").click(function() {
if ($('#tbl1 tr').size() > 2) {

	$('#tbl1 tr:last-child').remove();
	cnt--;
} else {

	/*  var msg = getLocalMessage('lgl.cantRemove'); */
	var msg = "can not remove";
	showErrormsgboxTitle(msg);
}
});

/* $('#frTime').timepicker({
    minuteStep: 1,
    template: 'modal',
    appendWidgetTo: 'body',
    showSeconds: true,
    showMeridian: false,
    defaultTime: false
    
}); */

});

</script>




<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.connectiondetails" text="Connection Details"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/> <i class="text-red-1">*</i> <spring:message code="water.ismandtry"/>
				</span>
			</div>
			<form:form action="NewWaterConnectionDetails.html"
				class="form-horizontal form" name="frmNewWaterDetails"
				id="frmNewWaterDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<h4 class="margin-top-0"><spring:message code="water.meterDet.appDet" text="Application Details"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.service.name"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" path=""></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.application.number"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled=""
							path=""></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.applicnt.name"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled=""
							path=""></form:input>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.application.date"/></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control" disabled=""
							path=""></form:input>
					</div>
				</div>
				<h4><spring:message code="water.dist.legalStatus"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="water.status"/></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="WLI" />
						<select name="csmrInfo.csListatus" class="form-control" id="">
							<option value="0"><spring:message code="water.validation.connCCnStatus"/></option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<h4><spring:message code="water.dist.linedet" text="Distribution Line Details"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="water.newWaterConnectionDetails.DistributionLine"/></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="SLN" />
						<select name="csmrrCmd.cpdDistline" class="form-control" id="">
							<option value="0">Select Distributed Line</option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.dist.discharge"/></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" class="form-control"
								path="csmrrCmd.rcDistccndif"></form:input>
							<span class="input-group-addon" id="basic-addon1">M<sup>3</sup>/day
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="water.dist.pressure"/></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" type="text" class="form-control"
								path="csmrrCmd.rcDistpres"></form:input>
							<span class="input-group-addon" id="basic-addon1">Kg/cm<sup>2</sup></span>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="water.dist.length"/></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" type="text" class="form-control"
								path="csmrrCmd.rcLength"></form:input>
							<span class="input-group-addon" id="basic-addon1">Mtr.</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-xs-12 control-label"><spring:message code="water.dist.period"/></label>
					<div class="col-sm-2 col-xs-4">
						<form:input type="text" class="form-control"
							placeholder="From Hrs." path="csmrrCmd.rcDisttimefr" id="frTime123"></form:input>

					</div>
					<div class="col-sm-2  col-xs-4">
						<form:input type="text" class="form-control" placeholder="To Hrs."
							path="csmrrCmd.rcDisttimeto"></form:input>
					</div>
					<div class="col-sm-2  col-xs-4">
						<form:input type="text" class="form-control"
							placeholder="Total Hrs." path=""></form:input>
					</div>
				</div>
				<div class="form-group">
				 <label class="col-sm-2 control-label required-control"><spring:message code="water.dist.connType"/></label>
							<div class="col-sm-4">
											<c:set var="baseLookupCode" value="WCT" /> <select
										name="csmrInfo.csCcntype" class="form-control"
										id="">
											<option value="0">Select Connection Type</option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>
											</c:forEach>
									</select>
											</div>
											</div>
				<h4><spring:message code="water.road.det" text="Road Digging Details"/></h4>
				<div class="form-group">
					<label class="checkbox-inline"> <input name=""
						type="checkbox" value="Digging" checked=""> <spring:message code="water.newWaterConnectionDetails.RoadDiggingRequired"/>
					</label>
				</div>
			 <div class="table-responsive">
					<table class="table table-bordered table-striped" id="tbl1">
						<tr>
							<th><spring:message code="water.nodues.srno"/></th>
							<th><spring:message code="water.road.roadType"/></th>
							<th><spring:message code="water.road.roadlen"/></th>
							<th><spring:message code="water.road.roadTrench"/></th>
							<th><a data-toggle="tooltip" data-placement="top" title=""
								class="btn btn-blue-2 btn-sm" data-original-title="Add"
								id="addRoad"><i class="fa fa-plus"></i></a></th>
						</tr>
						<tr>
							<td>1</td>
							<td><c:set var="baseLookupCode" value="RDT" /> <select
								name="csmrInfo.roadList[0].crtRoadTypes" class="form-control"
								id="roadType0">
									<option value="0">Select Road Type</option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>
									</c:forEach>
							</select></td>
							<td><form:input name="" type="text" class="form-control"
									path="csmrInfo.roadList[0].crtRoadUnits" id="roadLength0"></form:input></td>
							<td><form:input name="" type="text" class="form-control"
									path="csmrInfo.roadList[0].crtRoadTrench" id="roadtrench0s"></form:input></td>
							<td><a data-toggle="tooltip" data-placement="top" title=""
								class="btn btn-danger btn-sm" data-original-title="Delete"
								id="deleteRoad"><i class="fa fa-trash"></i></a></td>
						</tr>

					</table>
				</div>  
				<h4><spring:message code="water.met.metOrNonmet" text="Metered / Non-Metered Connection"/></h4>
				<div class="form-group">
					<label class="checkbox-inline"> <form:radiobutton
							name="Metered" path="csmrInfo.csMeteredccn" /> <spring:message code="water.meter.meteredCheckBox"/>
					</label> <label class="checkbox-inline"> <form:radiobutton
							name="Metered" path="csmrInfo.csMeteredccn" /> <spring:message code="water.newWaterConnectionDetails.Non-Metered"/>
					</label>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" onclick="saveWaterDetails(this)"><spring:message code="water.btn.submit"/></button>
					<button type="button" class="btn btn-danger"><spring:message code="water.btn.cancel"/></button>
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>
