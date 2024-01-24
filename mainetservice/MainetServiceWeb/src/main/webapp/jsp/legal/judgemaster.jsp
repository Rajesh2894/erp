<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="js/legal/judgemaster.js"></script>
<!-- <script src="js/mainet/validation.js"></script> -->
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.judgemaster"
						text="judgemaster" /></strong>
			</h2>
			<apptags:helpDoc url="JudgeMaster.html" />
		</div>
		
		<div class="widget-content padding">
		<!-- Start Form -->
			<form:form action="JudgeMaster.html" class="form-horizontal form"
				name="" id="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->

				<!-- Start button -->
				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="lgl.courtnm" text="Court Name" /></label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="judgeMasterDto.judgeDetails[0].crtId" id="crtId">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}" var="data">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${data.id}">${data.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${data.id}">${data.crtName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="control-label col-sm-2"> <spring:message
							code="lgl.appointmentStatus" text="Status" /></label>
					<div class="col-sm-4">
						<form:select path="judgeMasterDto.judgeDetails[0].judgeStatus"
							cssClass="form-control chosen-select-no-results" id="judgeStatus">
							<form:option value="">Select</form:option>
							<form:option value="Y">Yes</form:option>
							<form:option value="N">No</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="lgl.benchName" text="Bench Name" /></label>
                   <div class="col-sm-4">
					<form:select
						class=" mandColorClass form-control chosen-select-no-results"
						path="judgeMasterDto.judgeBenchName" id="judgeBenchName">
						<form:option value="">
							<spring:message code="lgl.select" text="Select" />
						</form:option>
						<c:forEach items="${command.judgeMasterDtos}" var="data">
							<form:option value="${data.judgeBenchName}">${data.judgeBenchName}</form:option>
						</c:forEach>
					</form:select>
					</div>
				</div>

				<div class="text-center clear padding-10">
				<button type="button" class="btn btn-blue-2 search"
						onclick="searchJugdgeData()">
						<i class="fa fa-search"></i>
						<spring:message code="lgl.search" text="Search"></spring:message>
					</button>

					<button type="Reset" class="btn btn-warning" onclick="window.location.href='JudgeMaster.html'">
						<spring:message code="legal.btn.reset" text="Reset"></spring:message>
					</button>
					
					<button type="submit" class="button-input btn btn-success"
						onclick="openAddJudgeMaster('JudgeMaster.html','AddJudgeMaster');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="lgl.add" text="Add" />
					</button>
					
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
					
				</div>
				<!-- End button -->
				
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_judgeMasterTbl">
							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message code="lgl.firstName" text="First Name" /></th>
									<th><spring:message code="lgl.lastName" text="Last Name" /></th>
									<%-- <th><spring:message code="lgl.gender" text="Gender" /></th> --%>
<%-- 									<th><spring:message code="lgl.dateOfBirth"
											text="Date Of Birth" /></th>
 --%>									<th><spring:message code="lgl.phoneNumber"
											text="Phone Number" /></th>
									<th><spring:message code="lgl.email" text="Email" /> </th>
									<%-- <th><spring:message code="lgl.address" text="Address" /></th> --%>
									<th><spring:message code="lgl.action" text="Action" /></th>
	
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.judgeMasterDtos}" var="judge"
									varStatus="loop">
									<tr>
										<td align="center">${loop.count}</td>
										<td align="center">${judge.judgeFName}</td>
										<td align="center">${judge.judgeLName}</td>
										<%-- <td align="center">
											${command.getNonHierarchicalLookUpObject(judge.judgeGender).getLookUpDesc()}
										</td> --%>
										<%-- <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${judge.judgeDob}" /></td> --%>
										<td align="center">${judge.contactPersonPhoneNo}</td>
										<td align="center">${judge.contactPersonEmail}</td>
										<%-- <td align="center">${judge.judgeAddress}</td> --%>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code="lgl.judge.view.details" text="View JudgeDetails"></spring:message>"
												onclick="modifyJudge(${judge.id},'JudgeMaster.html','ViewJudgeMaster','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="<spring:message code="lgl.judge.edit.details" text="Edit JudgeDetails"></spring:message>"
												onclick="modifyJudge(${judge.id},'JudgeMaster.html','EditJudgeMaster','E')">
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
