<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/tableHeadFixer.js"></script>
<script src="js/masters/chargemaster.js"></script>

<script>

$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true
});

</script>
            
    	<div class="mand-label clearfix"><span><spring:message code="contract.breadcrumb.fieldwith" text="Field with"/> <i class="text-red-1">*</i><spring:message code="contract.breadcrumb.ismandatory" text="is mandatory"/> </span></div>
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${mode}" var="form_mode" />
		
<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
</div>

		<form:form method="post" action="${url_form_submit}" name="ChargeMas"
			id="ChargeMas" class="form-horizontal" modelAttribute="tbChargeMaster">
			
			 <div class="form-group">
			 <label class="col-sm-2 control-label required-control"><spring:message code="common.master.service.name" text="Service Name :"/></label>
			<div class="col-sm-4">
			 <c:if test="${form_mode eq 'update' || form_mode eq 'E' }">
              	<form:select id="serviceName" path="cmServiceId" cssClass="form-control mandClassColor " disabled="true">
						<form:option value="" >--Select--</form:option>
						<c:forEach items="${serviceList}" var="servideMst">
							<form:option value="${servideMst.smServiceId}">${servideMst.smServiceName}</form:option>						
						</c:forEach>
				</form:select>
				<form:hidden path="cmServiceId"/>
			</c:if>
			<c:if test="${form_mode eq 'A'}">
              	<form:select id="serviceName" path="cmServiceId" cssClass="form-control mandClassColor ">
						<form:option value="" >--Select--</form:option>
						<c:forEach items="${serviceList}" var="servideMst">
							<form:option value="${servideMst.smServiceId}">${servideMst.smServiceName}</form:option>						
						</c:forEach>
				</form:select>
			</c:if>
			
				</div>
				
			</div>
			<input type="hidden" id="mode" value="${form_mode}">
			
			 
	    	<c:set var="c" value="0" scope="page" />
			<c:set var="d" value="0" scope="page" />
			<c:set var="rowNumVisible" value="-1" scope="page" />
	<div class="table-responsive" id="payOtherdID">
	
				<c:if test="${form_mode eq 'A'}">
	
				<table id="chargeMasterTab" class="table table-hover table-bordered table-striped appendableClass">
				<tbody>
				
					<tr>
						<th><spring:message code="common.master.charge.applicable" text="Charge Applicable"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.type" text="Charge Type"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.slab.depend" text="Slab Depend"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.flat.depend" text="Flat Depend"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.sequence" text="Charge Sequence"/> <span class="mand float-right">*</span></th>
					</tr>

					<tr>
					
						<td><form:select id="cmChargeapplicableat${d}"
								path="dto[${d}].cmChargeapplicableat"
								cssClass="form-control">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${chargeApp}" var="chargeApp">
									<form:option value="${chargeApp.lookUpId}">${chargeApp.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>

						<td><form:select id="cmChargeType${d}"
								path="dto[${d}].cmChargeType"
								cssClass="form-control">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${chargeType}" var="chargeType">
									<form:option value="${chargeType.lookUpId}">${chargeType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
							<td><form:select id="cmSlabDepend${d}"
								path="dto[${d}].cmSlabDepend"
								cssClass="form-control">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${slabDepend}" var="slabDepend">
									<form:option value="${slabDepend.lookUpId}">${slabDepend.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
								
							<td><form:select id="cmFlatDepend${d}"
								path="dto[${d}].cmFlatDepend"
								cssClass="form-control">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${flatDepend}" var="flatDepend">
									<form:option value="${flatDepend.lookUpId}">${flatDepend.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
							<td><form:select id="cmChargeSequence${d}"
								path="dto[${d}].cmChargeSequence"
								cssClass="form-control">
								<form:option value="">--Select--</form:option>
								<form:options items="${sequence}" />
							</form:select></td>
							
					</tr>
					 
					 <tr>
						<th><spring:message code="common.master.charge.desc.english" text="Charge Description English"/></th>
						<th><spring:message code="common.master.charge.desc.regional" text="Charge Description regional"/></th>
						<th><spring:message code="common.master.charge.start.date" text="Charge Start Date"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.end.date" text="Charge End Date"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.addremove" text="Add/Remove"/></th>
					</tr>
					
					<tr>
						<td><form:input id="cmChargedescriptionEng${d}"
								path="dto[${d}].cmChargedescriptionEng" class="form-control" />
								<form:hidden
								path="dto[${d}].cmId" id="cmId${d}" /></td>

						<td><form:input id="cmChargedescriptionReg${d}"
								path="dto[${d}].cmChargedescriptionReg" class="form-control" /></td>

						<td><form:input path="dto[${d}].cmChargeStartDateStr"
								cssClass="datepicker cal form-control" readonly=""
								value="${date}" id="cmChargeStartDateStr${d}" /></td>

						<td><form:input path="dto[${d}].cmChargeEndDateStr"
								cssClass="datepicker cal form-control" readonly=""
								value="${date}" id="cmChargeEndDateStr${d}" /></td>

						<td>
						 <a data-toggle="tooltip" data-placement="top" title="Add" class="btn btn-success btn-sm addButton" id="addButton${d}"><i class="fa fa-plus-circle"></i></a>
						
						<a data-toggle="tooltip" data-placement="top" title="Delete" class="btn btn-danger btn-sm delButton"  id="delButton${d}"><i class="fa fa-trash-o"></i></a>
						</td>

					</tr>
				
				</tbody>
				</table>
				
				</c:if>
				<!--  edit start-->
				<c:if test="${form_mode eq 'E'}">
					<c:if test="${fn:length(tbChargeMaster.dto) > 0}">
						
								<c:forEach var="dataList" items="${tbChargeMaster.dto}" varStatus="status">
								
				 <table id="chargeMasterTab" class="table table-hover table-bordered table-striped appendableClass" >
				 
					<tr>
						
						<th><spring:message code="common.master.charge.code" text="Charge Code"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.applicable" text="Charge Applicable"/><span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.type" text="Charge Type"/><span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.slab.depend" text="Slab Depend"></spring:message><span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.flat.depend" text="Flat Depend"/><span class="mand float-right">*</span></th>
						
						
					</tr>

					<tr>
						
						<td><form:input id="cmId${d}"
								path="dto[${d}].cmId" class="form-control" readonly="true"/></td>

						<td><form:select id="cmChargeapplicableat${d}"
								path="dto[${d}].cmChargeapplicableat"
								cssClass="form-control" disabled="true">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${chargeApp}" var="chargeApp">
									<form:option value="${chargeApp.lookUpId}">${chargeApp.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>

						<td><form:select id="cmChargeType${d}"
								path="dto[${d}].cmChargeType"
								cssClass="form-control" disabled="true">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${chargeType}" var="chargeType">
									<form:option value="${chargeType.lookUpId}">${chargeType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
							<td><form:select id="cmSlabDepend${d}"
								path="dto[${d}].cmSlabDepend"
								cssClass="form-control" disabled="true">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${slabDepend}" var="slabDepend">
									<form:option value="${slabDepend.lookUpId}">${slabDepend.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
							
							<td><form:select id="cmFlatDepend${d}"
								path="dto[${d}].cmFlatDepend"
								cssClass="form-control" disabled="true">
								<form:option value="">-Select-</form:option>
								<c:forEach items="${flatDepend}" var="flatDepend">
									<form:option value="${flatDepend.lookUpId}">${flatDepend.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
					</tr>
					 
					 <tr>
						<th><spring:message code="common.master.charge.sequence" text="Charge Sequence"/><span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.desc.english" text="Charge Description English"/></th>
						<th><spring:message code="common.master.charge.desc.regional" text="Charge Description regional"/></th>
						<th><spring:message code="common.master.charge.start.date" text="Charge Start Date"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="Charge End Date" text="Charge End Date" /> <span class="mand float-right">*</span></th>
					
					</tr>
					<tr>
					
					<td><form:select id="cmChargeSequence${d}"
								path="dto[${d}].cmChargeSequence"
								cssClass="form-control" disabled="true">
								<form:option value="">--Select--</form:option>
								<form:options items="${sequence}" />
							</form:select></td>
					
						<td><form:input id="cmChargedescriptionEng${d}"
								path="dto[${d}].cmChargedescriptionEng" class="form-control" disabled="true"/>
								<form:hidden
								path="dto[${d}].cmId" id="cmId${d}" /></td>

						<td><form:input id="cmChargedescriptionReg${d}"
								path="dto[${d}].cmChargedescriptionReg" class="form-control" disabled="true"/></td>

						<td><form:input path="dto[${d}].cmChargeStartDateStr"
								cssClass="datepicker cal form-control" readonly=""
								value="${date}" id="cmChargeStartDateStr${d}" disabled="true"/></td>

						<td><form:input path="dto[${d}].cmChargeEndDateStr"
								cssClass="datepicker cal form-control" readonly=""
								value="${date}" id="cmChargeEndDateStr${d}" disabled="true"/></td>
					
					</tr>
					
					<tr>
				<td colspan="5" class="divider"></td>
				</tr>
					<c:set var="d" value="${d + 1}" scope="page" />
				</table>
				<tr>
				<td colspan="5" class="divider" id="divide"></td>
				</tr>
				</c:forEach>
				</c:if>
					<!-- edit end  -->
				
				</c:if>
				
				
				<c:if test="${form_mode eq 'update'}">
					
								
				 <table id="chargeMasterTab" class="table table-hover table-bordered table-striped appendableClass" >
				 
					<tr>
						
						<th>Charge Code <span class="mand float-right">*</span></th>
						<th>Charge Applicable <span class="mand float-right">*</span></th>
						<th>Charge Type <span class="mand float-right">*</span></th>
						<th>Slab Depend <span class="mand float-right">*</span></th>
						<th>Flat Depend <span class="mand float-right">*</span></th>
						
						
					</tr>

					<tr>
						
						<td><form:input id="cmId${d}"
								path="dto[${d}].cmId" class="form-control" readonly="true"/></td>

						<td><form:select id="cmChargeapplicableat${d}"
								path="dto[${d}].cmChargeapplicableat"
								cssClass="form-control" >
								<form:option value="">-Select-</form:option>
								<c:forEach items="${chargeApp}" var="chargeApp">
									<form:option value="${chargeApp.lookUpId}">${chargeApp.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>

						<td><form:select id="cmChargeType${d}"
								path="dto[${d}].cmChargeType"
								cssClass="form-control" >
								<form:option value="">-Select-</form:option>
								<c:forEach items="${chargeType}" var="chargeType">
									<form:option value="${chargeType.lookUpId}">${chargeType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
							<td><form:select id="cmSlabDepend${d}"
								path="dto[${d}].cmSlabDepend"
								cssClass="form-control" >
								<form:option value="">-Select-</form:option>
								<c:forEach items="${slabDepend}" var="slabDepend">
									<form:option value="${slabDepend.lookUpId}">${slabDepend.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
										
							<td><form:select id="cmFlatDepend${d}"
								path="dto[${d}].cmFlatDepend"
								cssClass="form-control" >
								<form:option value="">-Select-</form:option>
								<c:forEach items="${flatDepend}" var="flatDepend">
									<form:option value="${flatDepend.lookUpId}">${flatDepend.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
							
							
							
					</tr>
					 
					 <tr>
						<th><spring:message code="common.master.charge.sequence" text="Charge Sequence"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.desc.english" text="Charge Description English"/></th>
						<th><spring:message code="common.master.charge.desc.regional" text="Charge Description regional"/></th>
						<th><spring:message code="common.master.charge.start.date" text="Charge Start Date"/> <span class="mand float-right">*</span></th>
						<th><spring:message code="common.master.charge.end.date" text="Charge End Date"/><span class="mand float-right">*</span></th>
					</tr>
					<tr>
					
					<td><form:select id="cmChargeSequence${d}"
								path="dto[${d}].cmChargeSequence"
								cssClass="form-control" >
								<form:option value="">--Select--</form:option>
								<form:options items="${sequence}" />
							</form:select></td>
					
						<td><form:input id="cmChargedescriptionEng${d}"
								path="dto[${d}].cmChargedescriptionEng" class="form-control" />
								</td>

						<td><form:input id="cmChargedescriptionReg${d}"
								path="dto[${d}].cmChargedescriptionReg" class="form-control" /></td>

						<td><form:input path="dto[${d}].cmChargeStartDateStr"
								cssClass="datepicker cal form-control" readonly=""
								value="${date}" id="cmChargeStartDateStr${d}" /></td>

						<td><form:input path="dto[${d}].cmChargeEndDateStr"
								cssClass="datepicker cal form-control" readonly=""
								value="${date}" id="cmChargeEndDateStr${d}" /></td>
					
					</tr>
					
					
					<c:set var="d" value="${d + 1}" scope="page" />
				</table>
				
				
				</c:if>
			
	</div>		 
			
		 <div class="text-center padding-10">
		 <c:if test="${form_mode ne 'E'}">
			<button type="button" class="btn btn-success btn-submit" onclick="submitChargeMasterForm(this);" >Submit</button>
			 <button type="Reset" class="btn btn-danger">Reset</button>
			 </c:if>
			 <apptags:backButton url="ChargeMaster.html"  cssClass="btn btn-primary"></apptags:backButton>
		</div>

		</form:form>
		