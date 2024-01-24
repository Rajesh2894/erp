<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/objection/hearingInspectionDateLetterPrint.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
<div class="widget">
	<div class="widget-header">
		<h2>		
			<b><spring:message code="obj.DateLetterPrinting"/></b>
		</h2>
         <apptags:helpDoc url="HearingInspectionDateLetterPrinting.html"></apptags:helpDoc>
	</div>

	<div class="widget-content padding">
		<form:form action="HearingInspectionDateLetterPrinting.html"
					class="form-horizontal" name="HearingInspectionDatePrintForm"
					id="HearingInspectionDatePrintForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			
			<div class="panel-group accordion-toggle" id="accordion_single_collapse">
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a1"><spring:message code="obj.DateLetterPrintingSearchCriteria"/></a>
					</h4>
					<div id="a1" class="panel-collapse collapse in">

							<div class="form-group">			
								<apptags:radio radioLabel="obj.Inspection,obj.Hearing" radioValue="I,H" labelCode="obj.HearingInspection" path="objectionDetailsDto.schedulingSelection"  isMandatory="true"></apptags:radio>
							</div>
							
							<div class="form-group">		
								<apptags:radio radioLabel="obj.against.objection,obj.routine.inspection" radioValue="AO,RI" labelCode="obj.InspectionType" path="objectionDetailsDto.inspectionType" isMandatory="true"></apptags:radio>
							</div>
							
							<div class="form-group">
									<apptags:select labelCode="obj.objDept"
										items="${command.departments}" path="objectionDetailsDto.objectionDeptId"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showAll="false" isLookUpItem="true"></apptags:select>

									<%--<label class="col-sm-2 control-label required-control"
										for="objtype"><spring:message code="obj.objectiontype" /></label>
									 <c:set var="baseLookupCode" value="OBJ" />
									<apptags:lookupField
										items="${command.serviceList}"
										path="objectionDetailsDto.serviceId" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="obj.title" /> --%>
							</div>
							
							<div class="form-group">
								<apptags:radio radioLabel="obj.single.mul,obj.all" radioValue="SM,A" labelCode="obj.SelectOption" path="objectionDetailsDto.selectType" isMandatory="true"></apptags:radio>
							</div>
							
							<div class="form-group">
								<apptags:textArea labelCode="obj.objectionno" path="objectionDetailsDto.objectionNumber" isMandatory="true"></apptags:textArea>
							</div>
							<div class="form-group">
							<div class="col-sm-12 text-center text-small"><i class="text-red-1"><spring:message code="obj.or"/></i></div>
							</div>
							<div class="form-group">
								<apptags:textArea labelCode="obj.property.no" path="" isMandatory="true" cssClass="hasNumber"></apptags:textArea>
								<apptags:textArea labelCode="obj.old.property.no" path="" isMandatory="true" cssClass="hasNumber"></apptags:textArea>
							</div>
						
					<!--Start Button-->
						<div class="text-center padding-15 clear">                   	
	                   		<button type="button" class="btn btn-success btn-submit" onclick="SearchRecords()"><spring:message code="property.search" text="" /></button>
	                   		<button type="Reset" class="btn btn-warning" id="resetformId" onclick="resetForm(resetformId);"><spring:message code="reset.msg" text="Reset"/></button>	
	                   	</div>
					<!--End Button-->
					</div>
					
					
						<div class="table-responsive clear" id="PropDetails">
							<table id="datatables" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th width="2%"><label class="checkbox-inline"><input
										type="checkbox" id="selectall"/><spring:message code="obj.select.all" text="Select All"/></label></th>
										<th width="15%"><spring:message code="obj.ReferenceNo"/></th>
										<th width="15%"><spring:message code="obj.AdditionalReferenceNo"/></th>
										<th width="15%"><spring:message code="obj.ObjectionNo"/></th>
 										<th width="15%"><spring:message code="obj.ObjectionDate"/></th> 
										<th width="20%"><spring:message code="obj.ObjectionIssuerName"/></th>
										<th width="20%"><spring:message code="obj.objectiontype"/></th>
										<th width="20%"><spring:message code="obj.HearingInspectionDateTime"/></th>
									</tr>
								</thead>
								<tbody>
								 <c:forEach var="objectionDetails" items="${command.getListObjectionDetails()}" varStatus="status" >  
									<tr>
										<td align="center">
										<label class="checkbox margin-left-20"><form:checkbox
												path="" value="Y"
												cssClass="checkall"  id="" /></label>
												<form:hidden path="" id="checkValue" value="Y"/>
										</td>
										
										<td class="text-center">${objectionDetails.objectionReferenceNumber}</td>
										<td class="text-center">${objectionDetails.objectionAddReferenceNumber}</td>
										<td class="text-center">${objectionDetails.objectionNumber}</td>
 										<td class="text-center">${objectionDetails.objectionDate}</td> 
										<td class="text-center">${objectionDetails.objectionIssuerName}</td>
										<td class="text-center">${objectionDetails.objectionType}</td>
										<td class="text-center">${objectionDetails.inspectionDate}</td>
									</tr>
								</c:forEach>   
								</tbody>
							</table>
						</div>
						
						
					<div class="form-group specialNoticebtn">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-info" id="btn1" onclick="">
								<spring:message code="obj.submit"/></button>

						</div>
					</div> 
					
			
				</div>
			</div>
		</form:form>
	</div>
</div>
</div>