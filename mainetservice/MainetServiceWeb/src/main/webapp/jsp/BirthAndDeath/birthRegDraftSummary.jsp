<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript"
	src="js/birthAndDeath/birthRegistration.js"></script>
<script>

	$(document).ready(function() {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,

		});
	});

</script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthRegistrationDTO.birthRegistration" text="Birth Rregistration" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="birthRegDraftId" action="BirthRegistrationForm.html"
					method="POST" class="form-horizontal" name="birthRegDraftId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4>
						<spring:message code="BirthRegistrationDTO.birthRegistration" text="Birth Registration" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.applnId"
							path="birthRegDto.applnId" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="14">
						</apptags:input>
						
						<label class="col-sm-2 control-label">
							<spring:message code="BirthRegistrationDTO.brDob" text="Date of Birth" />
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="birthRegDto.brDob"
									cssClass="form-control mandColorClass" id="brDob" readonly="true" />
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
						<%-- <apptags:date fieldclass="datepicker" labelCode="BirthRegistrationDTO.brDob" readonly="true"
							datePath="birthRegDto.brDob">
						</apptags:date> --%>
					</div>

					<div class="text-center">
						<button type="button" class="btn btn-success" title="<spring:message code="BirthRegDto.search"/>"
							onclick="searchBirthData(this)">
							<spring:message code="BirthRegDto.search"/>
						</button>
						
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="<spring:message code="BirthRegDto.reset"/>"
							onclick="window.location.href ='BirthRegistrationForm.html'">
							<spring:message code="BirthRegDto.reset"/>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
						<button type="button" id="add" class="btn btn-green-3"
							onclick="openForm('BirthRegistrationForm.html','birthRegDraft')"
							title="<spring:message code="BirthRegDto.add" text="Add" />">
							<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
							<spring:message code="BirthRegDto.add" text="Add" />
						</button>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="birthRegDraftDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No." /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.brDob" text="Birth Date" /></th>
									<th width="20%" align="center"><spring:message
											code="BirthRegDto.applnNo" text="Application No." /></th>
									<th width="20%" align="center"><spring:message
											code="BirthRegDto.brChildName" text="Child Name" /></th>
									<th width="15%" align="center"><spring:message
											code="BirthRegistrationDTO.brSex" text="Sex" /></th>
									<th width="20%" align="center"><spring:message
											code="ParentDetailDTO.pdRegUnitId" text="Registration Unit" /></th>
									<th width="10%" align="center"><spring:message
											code="TbDeathregDTO.form.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${data}" var="birth" varStatus="item">

									<tr>
										<td class="text-center">${item.count}</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${birth.brDob }"/></td>

										<c:choose>
											<c:when test="${empty birth.applnId}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${birth.applnId}</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty birth.brChildname}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${birth.brChildname}</td>
											</c:otherwise>
										</c:choose>


										<c:choose>
											<c:when test="${birth.brSex==0}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${birth.brSex}</td>
											</c:otherwise>
										</c:choose>



										<c:choose>
											<c:when test="${birth.cpdDesc==null}">
												<td align="center"><p>-</p></td>
											 </c:when>
											<c:otherwise>
												<td align="center">${birth.cpdDesc}</td>
											</c:otherwise>
										</c:choose>


										<td class="text-center">
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit birth registration Form"
												onclick="modifyBirth('${birth.brDraftId}','${birth.applnId}','BirthRegistrationForm.html','editBND','E')">
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
</div>
