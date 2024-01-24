<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="js/mainet/validation.js"></script>
<script src="js/legal/advocateMaster.js"></script>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.advocatemaster"
						text="Advocate Masters" /></strong>
			</h2>
		</div>
		<apptags:helpDoc url="AdvocateMaster.html" />

		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="AdvocateMaster.html" class="form-horizontal form"
				name="" id="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
				<li><label id="errorId"></label></li>
				</ul>
				</div>
				<!-- End Validation include tag -->
				<div class="form-group">

					<label for="" class="control-label col-sm-2 required-control"><spring:message
							code="lgl.advocate.type" text="Advocate Type" /></label>
					<apptags:lookupField items="${command.getLevelData('AVT')}"
						path="advocateMasterDTO.adv_advocateTypeId"
						cssClass="form-control" selectOptionLabelCode="Select"
						hasId="true" isMandatory="false" />

					<%-- <label class="control-label col-sm-2"> <spring:message
							code="lgl.courtnm" text="Court Name" /></label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="advocateMasterDTO.adv_courtNameId" id="crtId">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}" var="data">
								<form:option value="${data.id}">${data.crtName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<apptags:input labelCode="lgl.barcouncil.no"
						path="advocateMasterDTO.adv_barCouncilNo" isMandatory="false"
						cssClass="form-control hasNoSpace" maxlegnth="16"></apptags:input>
				</div> --%>
					<label class="col-sm-2 control-label"><spring:message
							code="lgl.advocate.status" text="Advocate Status" /><span></span>
					</label>
					<div class="col-sm-4">

						<label class="radio-inline" for="advStatusYes"> <form:radiobutton
								name="advStatus" path="advocateMasterDTO.advStatus"
								checked="checked" value="Y" id="advStatusYes"></form:radiobutton>
							<spring:message code="lgl.yes" text="Yes" />
						</label> <label class="radio-inline" for="advStatusNo"> <form:radiobutton
								name="advStatus" path="advocateMasterDTO.advStatus" value="N"
								id="advStatusNo"></form:radiobutton> <spring:message
								code="lgl.no" text="No" />
						</label>
					</div>

				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-blue-2 search"
						onclick="searchData()">
						<i class="fa fa-search"></i>
						<spring:message code="lgl.search" text="Search"></spring:message>
					</button>

					<button type="Reset" class="btn btn-warning"
						onclick="window.location.href='AdvocateMaster.html'">
						<spring:message code="legal.btn.reset" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success"
						onclick="openAddAdvocateMaster('AdvocateMaster.html','AddAdvocateMaster');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="lgl.add" text="Add" />
					</button>
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
				</div>
				<!-- End button -->

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_advocateMasterTbl">
							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message code="lgl.firstName" text="First Name" /></th>
									<th><spring:message code="lgl.lastName" text="Last Name" /></th>
									<%-- 	<th><spring:message code="lgl.gender" text="Gender" /></th>
									<th><spring:message code="lgl.dateOfBirth"
											text="Date Of Birth" /></th> --%>
									<th><spring:message code="lgl.advocate.mobileNumber"
											text="Phone Number" /></th>
									<th><spring:message code="lgl.email" text="Email" /></th>
									<th><spring:message code="lgl.address" text="Address" />
									</th>

									<th width="100"><spring:message code="lgl.advocate.status"
											text="Status" /></th>

									<th width="100"><spring:message code="lgl.action"
											text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.advocateMasterDTOList}" var="item"
									varStatus="loop">
									<tr>
										<td align="center">${loop.count}</td>
										<td align="center">${item.advFirstNm}</td>
										<td align="center">${item.advLastNm}</td>
										<%-- <td align="center">
											${command.getNonHierarchicalLookUpObject(item.advGen).getLookUpDesc()}
										</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${item.advDob}" /></td> --%>
										<td align="center">${item.advMobile}</td>
										<td align="center">${item.advEmail}</td>
										<td align="center">${item.advAddress}</td>
										<td class="text-center"><c:choose>
												<c:when test="${item.advStatus eq 'Y'}">
													<a href="#" class="fa fa-check-circle fa-2x green "
														title="Active"></a>
												</c:when>
												<c:otherwise>
													<a href="#" class="fa fa-times-circle fa-2x red "
														title="InActive"></a>
												</c:otherwise>
											</c:choose></td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code="lgl.view.advocate.details" text="View Advocte Details"></spring:message>"
												onclick="getAdvocateMasterView('AdvocateMaster.html','ViewAdvocateMaster', ${item.advId})">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="<spring:message code="lgl.edit.advocate.details" text="Edit Advocte Details"></spring:message>"
												onclick="getAdvocateMaster('AdvocateMaster.html','EditAdvocateMaster', ${item.advId})">
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
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->
