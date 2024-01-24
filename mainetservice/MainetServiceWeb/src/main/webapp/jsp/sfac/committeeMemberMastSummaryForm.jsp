<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/sfac/committeeMemberMastForm.js"></script>


<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Committee Member Summary Form" />
			</h2>
			<apptags:helpDoc url="CommitteeMemberMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="committeeMemberMastSummaryForm"
				action="CommitteeMemberMaster.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.committee.type" text="Committee Type" /></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="comMemDto.committeeTypeId"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.committee.mem.name" text="Name" /></label>
					<div class="col-sm-4">
						<form:select path="comMemDto.comMemberId" id="comMemberId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select'" />
							</form:option>
							<c:forEach items="${command.comMemDtoList}" var="dto">
								<form:option value="${dto.comMemberId}">${dto.memberName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.button.search" text="Search"/>'
						onclick="searchForm(this,'CommitteeMemberMaster.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset" />'
						onclick="window.location.href ='CommitteeMemberMaster.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>

					<button type="button" class="btn btn-blue-2"
						title='<spring:message code="sfac.button.add" text="Add" />'
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add" />
					</button>
				</div>



				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="committeeDetails">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.srno" text="Sr. No." /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.committee.mem.name" text="Member Name" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.committee.mem.org" text="Oragnization" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.designation" text="Designation" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.committee.mem.since" text="Member Since" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.status" text="Status" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.comMemDtoList}" var="dto"
								varStatus="status">
							 <tr>
									<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.memberName}</td>
								<td class="text-center">${dto.organization}</td>
										<td class="text-center">${dto.designation}</td>
									<td class="text-center">${dto.memberSince}</td>
									<td class="text-center">${dto.statusDesc}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="modifyCase(${dto.comMemberId},'CommitteeMemberMaster.html','EDIT','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit"
											onclick="modifyCase(${dto.comMemberId},'CommitteeMemberMaster.html','EDIT','E')">
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
