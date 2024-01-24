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
<script type="text/javascript"
	src="js/disaster_management/occuranceBook.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,
		});

		$(".Moredatepicker").timepicker({

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
				<strong><spring:message code="disaster.Occurrence.book"
						text="Occurrence Book" /></strong>
				<apptags:helpDoc url="OccuranceBook.html"></apptags:helpDoc>
			</h2>
		</div>

		<div class="widget-content padding">
			<form:form action="DisasterOccuranceBook.html"
				name="DisasterOccuranceBook" id="DisasterOccuranceBook"
				method="POST" commandName="command" class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="OccuranceBookDTO.fromDate"
						datePath="occuranceBookDTO.fromDate"
						cssClass="custDate mandColorClass date" isMandatory="true" />
					<apptags:date fieldclass="datepicker"
						labelCode="disasteroccuranceBook.toDate"
						datePath="occuranceBookDTO.toDate"
						cssClass="custDate mandColorClass date" isMandatory="true" />
				</div>

				<%-- <div class="form-group">
					<label class="control-label col-sm-2" for="fireStation"> <spring:message
							code="OccuranceBookDTO.fireStation" text="Fire Station" /></label>
					<c:set var="baseLookupCode" value="FSN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="occuranceBookDTO.fireStation"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div> --%>
				
				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="CMT" hasId="true"
										pathPrefix="occuranceBookDTO.complaintType" 
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" cssClass="form-control"
										showAll="false" />
				
				</div>


				<!-- Buttons start -->

				<div class="text-center clear padding-10">
					<%-- Defect #158115 --%>
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchOccuranceBookDetail()"
						title='<spring:message code="OccuranceBookDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="OccuranceBookDTO.form.search" text="Search" />
					</button>
					<button type="button" id="reset"
						onclick="window.location.href='DisasterOccuranceBook.html'"
						class="btn btn-warning"
						title='<spring:message code="rstBtn" text="Reset" />'>
						<spring:message code="rstBtn" text="Reset" />
					</button>
					<input type="button"
						title='<spring:message code="bckBtn" text="Back" />'
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print"
						value='<spring:message code="bckBtn" text="Back" />'>
				</div>


				<!-- Table Grid Start  -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="occuranceBookDataTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="audit.mgmt.srno" text="Sr.No" /></th>

								<th width="15%" align="center"><spring:message
										code="FireCallRegisterDTO.cmplntNo" text="Call Number" /></th>
								<th width="15%" align="center"><spring:message
										code="ComplainRegisterDTO.callType" text="Call Type" /></th>
								<th width="15%" align="center"><spring:message
										code="ComplainRegisterDTO.callSubtype" text="Call Sub Type" /></th>
								<th width="30%" align="center"><spring:message
										code="DisasterCallDetailsDTO.callDetails"
										text="Call Details" /></th>
								<th width="5%" align="center"><spring:message
										code="OccuranceBookDTO.form.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${occbooks}" var="occbook" varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td>${occbook.complainNo}</td>
									<td>${occbook.complaintType1Desc}</td>
									<td>${occbook.complaintType2Desc}</td>
									<td>${occbook.complaintDescription}</td>
									<td class="text-center">
										<button type="button" class="btn btn-warning btn-sm"
											title="<spring:message  code="FireCallRegister.form.add" text="Add"/>"
											onclick="openFormOcc('${occbook.complainId}','DisasterOccuranceBook.html','occuranceBook','A','${occbook.complainNo}')">
											<i class="fa fa-plus-circle padding-right-5"></i>
										</button> <input id="cmplntNo" name="cmplntNo" type="hidden"
										value="${occbook.complainNo}">

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

