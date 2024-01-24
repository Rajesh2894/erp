<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script src="js/legal/courtMaster.js"></script>
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.courtmaster"
						text="Court Master" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="CourtMaster.html" />
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="lgl.fieldwith" /><i
					class="text-red-1">* </i> <spring:message code="lgl.ismandatory" />
				</span>
			</div>
			<!-- End mand-label -->


			<!-- Start Form -->
			<form:form action="CourtMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="courtmaster" id="id_courtmaster">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->

				<!-- Start button -->

				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="lgl.courtnm" text="Court Name" /></label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="courtMasterDto.id" id="crtId">
							<form:option value="0">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}" var="data">
								<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										<form:option value="${data.id}">${data.crtName}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${data.id}">${data.crtNameReg}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"
						for="courtType"><spring:message code="lgl.courttype" /></label>
					<apptags:lookupField items="${command.getLevelData('CTP')}"
						path="courtMasterDto.crtType" cssClass="form-control"
						selectOptionLabelCode="Select" hasId="true" />
				</div>
				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-blue-2 search"
						onclick="searchCourtData()">
						<i class="fa fa-search"></i>
						<spring:message code="lgl.search" text="Search"></spring:message>
					</button>

					<button type="Reset" class="btn btn-warning"
						onclick="window.location.href='CourtMaster.html'">
						<spring:message code="legal.btn.reset" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success"
						onclick="openAddCourtMaster('CourtMaster.html','AddCourtMaster','C');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="lgl.add" text="Add" />
					</button>
					
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
					
				</div>

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered" id="id_courtMasterTbl">
							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message code="lgl.courtnm" text="Court Name" /></th>
									<th><spring:message code="lgl.courttype" text="Court Type" /></th>
									<th><spring:message code="lgl.courtstarttime"
											text="Court Start Timing" /></th>
									<th><spring:message code="lgl.courtendtime"
											text="Court End Timing" /></th>
									<th><spring:message code="lgl.crtStatus" text="Status"></spring:message></th> 
									<th><spring:message code="lgl.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.courtMasterDtos}" var="court" varStatus="loop">
									<tr>
										<td align="center">${loop.count}</td>
										<td align="center">${court.crtName }</td>
										<td align="center">
											${command.getNonHierarchicalLookUpObject(court.crtType).getLookUpDesc()}
										</td>
         
         								<td align="center">${court.crtStartTime}</td>
										<td align="center">${court.crtEndTime}</td>
         								
										<td class="text-center">
										<c:choose>
											<c:when test="${court.crtStatus eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>	
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="InActive"></a>
											</c:otherwise>
										</c:choose>
										</td>	
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code="lgl.court.view"></spring:message>"
												title="View CourtDetails"
												onclick="modifyCourt(${court.id},'CourtMaster.html','ViewCourtMaster','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="<spring:message code="lgl.court.edit"></spring:message>"
												title="Edit CourtDetails"
												onclick="modifyCourt(${court.id},'CourtMaster.html','EditCourtMaster','E')">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>





