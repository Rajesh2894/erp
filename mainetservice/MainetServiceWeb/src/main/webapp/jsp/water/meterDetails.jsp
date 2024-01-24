<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 <script type="text/javascript">
	$(document).ready(function(){
		$("#checkBoxId").prop("disabled", true);
		$("#checkBoxId").prop("checked", true)
	});
</script>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="water.met.det"/></h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" class="form-horizontal form">
				
				 		<div class="form-group">
						 	<div class="col-sm-6">
								 <label class="checkbox-inline">
							 		<form:checkbox path=""  value="Y" id="checkBoxId" readonly="true"/> 
							 			<spring:message code="water.meter.meteredCheckBox"/>
							 	</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message code="water.meter.cutOffMeterNo"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control " path="" id="" readonly="true" value="${command.meterMasPreviousDetailDTO.mmMtrno}" ></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.mtrMake"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control" readonly="true"  path="" id="" value="${command.meterMasPreviousDetailDTO.mmMtrmake}" ></form:input>
							</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.mtrCost"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control" path="" id="" readonly="true" value="${command.meterMasPreviousDetailDTO.mmMtrcost}"  ></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.instlDt"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control" path="" id=""   readonly="true" value="${command.meterMasPreviousDetailDTO.meterInstallDate}" ></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.meter.cutOffMeterIntialReading"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control" path="" id="" readonly="true" value="${command.meterMasPreviousDetailDTO.mmInitialReading}" ></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message code="water.meter.cutOffMeterMaxReading"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control" path="" id="" readonly="true" value="${command.meterMasPreviousDetailDTO.maxMeterRead}" ></form:input>
							</div>
						</div>
		              	 <div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="water.meterDet.ownerShip"/></label>
							<div class="col-sm-4">
								<form:input  type="text" class="form-control" path="" id="" readonly="true" value="${command.meterMasPreviousDetailDTO.meterOwnerShip}" ></form:input>
							</div>
						</div>
			</form:form>
		</div>
	</div>
</div>
