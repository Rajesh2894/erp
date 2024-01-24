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
<script type="text/javascript"
	src="js/council/councilCommitteeMaster.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.commitee.summary.title"
					text="Summary Commitee Mapping" />
			</h2>
			<!-- 	<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilMemberCommitteeMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilMemberCommitteeMaster.html"
				cssClass="form-horizontal" id="CouncilCommiteeMaster"
				name="CouncilCommiteeMaster">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<div class="form-group">
					<label class="col-sm-2 control-label " for="inwardType"><spring:message
							code="council.commitee.commiteeName" text="Committee Name" /></label>
					<c:set var="baseLookupCode" value="CPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="councilMemberCommitteeMasterDto.committeeTypeId"
						cssClass="form-control" hasChildLookup="false" hasId="true"
						selectOptionLabelCode="applicantinfo.label.select"
						showOnlyLabel="Committee Type" />

					<label class="col-sm-2 control-label" for="inwardType"><spring:message
							code="council.commitee.memberName" text="Member Name" /></label>
					<div class="col-sm-4">
						<form:select path="councilMemberCommitteeMasterDto.memberId"
							id="memberId" cssClass="form-control chosen-select-no-results"
							class="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code='council.management.select' />
							</form:option>
							<c:forEach items="${command.couMemMasterDtoList}" var="member">
								<form:option value="${member.couId}">${member.couMemName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchCommitteeMember">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='CouncilMemberCommitteeMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary" title="Add"
						onclick="addCommitteeMaster('CouncilMemberCommitteeMaster.html','addCommittee');">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.add" text="Add" />
					</button>
				</div>
				<!-- End button -->
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="commiteeDatatables">
						<thead>
							<tr>
								<th><spring:message code="council.srno" text="Sr.No." /></th>
								<th><spring:message code="council.commitee.commiteeName"
										text="Committee Name" /></th>
								<th><spring:message code="council.commitee.memberName"
										text="Member Name" /></th>
								<th><spring:message code="council.member.type"
										text="Member Type" /></th>
								<th><spring:message code="council.member.electionward"
										text="Election Ward" /></th>
								<%-- <th><spring:message
										code="council.member.partyaffiliation"
										text="Party Affiliation" /></th> --%>
								<th><spring:message code="council.member.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.councilMemberCommitteeMasterDtoList}"
								var="committee" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td align="center">${committee.committeeType}</td>
									<td align="center">${committee.memberName}</td>
									<td align="center">${committee.couMemberTypeDesc}</td>
									<td align="center">${committee.elecWardDesc}</td>
									<%-- <td align="center">${committee.partyAFFDesc}</td> --%>
									<td class="text-center">
										<button type="button"
											class="btn btn-blue-2 btn-sm margin-right-10 "
											name="button-plus" id="button-plus"
											onclick="showGridOption(${committee.committeeTypeId},'V')"
											title="<spring:message code="council.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button"
											class="btn btn-danger btn-sm btn-sm margin-right-10"
											name="button-123" id=""
											onclick="showGridOption(${committee.committeeTypeId},'E')"
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