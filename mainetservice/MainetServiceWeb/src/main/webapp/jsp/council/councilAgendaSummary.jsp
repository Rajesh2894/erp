<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/council/councilAgendaMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.agenda.summary.title"
					text="Summary Agenda" />
			</h2>
			<apptags:helpDoc url="CouncilAgendaMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilAgendaMaster.html"
				cssClass="form-horizontal" id="CouncilAgendaMaster"
				name="CouncilAgendaMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
							
					<div class="form-group">
						<label class="control-label col-sm-2" for="committeeType"><spring:message
								code="council.committeeType" text="Committe Type"></spring:message></label>
						<c:set var="baseLookupCode" value="CPT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="couAgendaMasterDto.committeeTypeId"
							cssClass="form-control required-control" isMandatory="false"
							selectOptionLabelCode="selectdropdown" hasId="true" />
							
						<apptags:input labelCode="council.agenda.agendaNo" path="couAgendaMasterDto.agendaNo"
						cssClass="form-control hasNumber"></apptags:input>
					</div>
													
						<!-- date picker input set -->
					<div class="form-group">
						<apptags:date fieldclass="datepicker" labelCode="council.from.date"
							datePath="couAgendaMasterDto.agendaFromDate" ></apptags:date>
						<apptags:date fieldclass="datepicker" labelCode="council.to.date"
							datePath="couAgendaMasterDto.agendaToDate" ></apptags:date>
					</div>
							
					<!-- Start button -->
					<div class="text-center clear padding-10">
						<button type="button" class="btn btn-blue-2" title="<spring:message code="council.button.search" text="Search"></spring:message>"
							id="searchCouncilAgenda">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.search" text="Search" />
						</button>

						<button type="button"
							onclick="window.location.href='CouncilAgendaMaster.html'"
							class="btn btn-warning" title="<spring:message code="council.button.reset" text="Reset"></spring:message>">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.reset" text="Reset" />
						</button>

						<button type="button" class="btn btn-primary"
							onclick="addAgendaMaster('CouncilAgendaMaster.html','addCouncilAgenda',${command.proposalPresent} );"
							title="<spring:message code="council.button.addmember" text="Add"></spring:message>">
							<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.add"
								text="Add" />
						</button>
					</div>
					<!-- End button -->

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="agendaDatatables">
							<thead>
								<tr>
									<th width="3%" align="center"><spring:message
											code="council.member.srno" text="Sr.No" /></th>
									<th class="text-center"><spring:message
											code="council.agenda.agendaNo" text="Agenda number" /></th>
									<th class="text-center"><spring:message
											code="council.agenda.committeeType" text="Committee Type" /></th>
									<th class="text-center"><spring:message
											code="council.agenda.date"  text="Date"/></th>
									<th class="text-center"><spring:message
											code="council.member.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.couAgendaMasterDtoList}"
									var="agenda" varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${agenda.agendaNo}</td>
										<td class="text-center">${agenda.committeeType}</td>
										<td class="text-center">${agenda.agenDate}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm margin-right-10 "
												name="button-plus" id="button-plus"
												onclick="showGridOption(${agenda.agendaId},'V')"
												title="
									      <spring:message code="council.button.view" text="view"></spring:message>">
												<i class="fa fa-eye" aria-hidden="true"></i>
											</button>

											<button type="button" class="btn btn-danger btn-sm btn-sm margin-right-10"
												name="button-123" id=""
												onclick="showGridOption(${agenda.agendaId},'E')"
												title="<spring:message code="council.button.edit" text="edit"></spring:message>">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button> 
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->