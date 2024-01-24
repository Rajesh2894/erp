
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/fire_management/callRegister.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,
		});

		$(".Moredatepicker").timepicker({
			//s dateFormat: 'dd/mm/yy',		
			changeMonth : true,
			changeYear : true,
			minDate : '0',
		});

		$("#time").timepicker({

		});

	});
</script>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="FireCallRegisterDTO.form.fire.call.register" text="Fire call register" /></strong>
				<apptags:helpDoc url="FireCallRegister.html"></apptags:helpDoc>
			</h2>
		</div>


		<div class="widget-content padding">
			<form:form action="FireCallRegister.html" name="frmFireCallRegister"
				id="frmFireCallRegister" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				
				<div class="form-group">
				
					<apptags:date fieldclass="datepicker" labelCode="FireCallRegisterDTO.fromDate"
						datePath="entity.fromDate" isMandatory="true">
					</apptags:date>
							
					<apptags:date fieldclass="datepicker" labelCode="FireCallRegisterDTO.toDate" 
						datePath="entity.toDate" isMandatory="true">
					</apptags:date>
								     
				</div>


				<div class="form-group">
						

					<label class="control-label col-sm-2"
						for="fireStation"> <spring:message
							code="FireCallRegisterDTO.fireStation" text="Fire Station" /></label>
					<c:set var="baseLookupCode" value="FSN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="entity.fireStation"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
			
						
					<apptags:input labelCode="FireCallRegisterDTO.cmplntNo" path="entity.cmplntNo"
						cssClass="complainNo" isMandatory="false" maxlegnth="50" />

				</div>

				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchDetails()" title='<spring:message code="FireCallRegisterDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="FireCallRegisterDTO.form.search"
							text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='FireCallRegister.html'"
						class="btn btn-warning" title='<spring:message code="rstBtn" text="Reset" />'>
						<spring:message code="rstBtn" text="Reset" />
					</button>


					<input type="button"
						title='<spring:message code="bckBtn" text="Back" />'
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value='<spring:message code="bckBtn" text="Back" />'>
						
						
						<button type="button" id="add" class="btn btn-blue-2"
						onclick="openForm('FireCallRegister.html','fireCallRegister')"
						title='<spring:message code="FireCallRegister.form.add" text="Add" />'>
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="FireCallRegister.form.add" text="Add" />
					</button>
				</div>

	          <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="fireCallRegister">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message code="FireCallRegisterDTO.srno" text="Sr.No" /></th>
									<%-- <th width="15%" align="center"><spring:message code="FireCallRegisterDTO.form.date=Date" text="Date Time" /></th> --%>
									<th width="10%" align="center"><spring:message
										code="FireCallRegisterDTO.date" text="Call Date" />
								<spring:message
										code="FireCallRegisterDTO.form.time" text="And Time" /></th>
									<th width="25%" align="center"><spring:message code="FireCallRegisterDTO.cmplntNo" text="Complain No" /></th>
									<th width="20%" align="center"><spring:message code="FireCallRegisterDTO.incidentDesc" text="Incident Description" /></th>
									<th width="20%" align="center"><spring:message code="FireCallRegisterDTO.dutyOfficer" text="Duty Officer"/></th>
									<th width="15%" align="center"><spring:message code="FireCallRegisterDTO.form.action" text="Action" /></th>
										
								</tr>
							</thead>
							 <tbody>
							<c:forEach items="${listfirecalls}" var="firecall" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${firecall.date}" />
									${firecall.time}</td>
									<td>${firecall.cmplntNo}</td>
									<td>${firecall.incidentDesc}</td>			
									<td>${firecall.dutyOfficer}</td>
										
									<td class="text-center">
										
											<button type="button" class="btn btn-warning btn-sm"
												title="<spring:message  code="FireCallRegisterDTO.editFireCall" text="Edit Fire Call Master"/>"
												onclick="modifyFireDetails('${firecall.cmplntId}','FireCallRegister.html','edit','E')">
												<i class="fa fa-pencil"></i>
											</button> 
											
									</td>
														
								</tr>
							</c:forEach>
							</tbody>
							
							</table>
							</div>

			</form:form>

		</div>


	</div>
</div>


























