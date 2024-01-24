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
<script type="text/javascript" src="js/council/councilMemberMaster.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.member.summary.title"
					text="Summary Council Member" />
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilMemberMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="CouncilMemberMaster.html"
				cssClass="form-horizontal" id="CouncilMemberMaster"
				name="CouncilMemberMaster">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="form-group">
					<c:set var="baseLookupCode" value="EWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="EWZ" hasId="true"
						pathPrefix="couMemMasterDto.couEleWZId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" />
				</div>
				<div class="form-group">
					<!-- As per RFP changes done -->
					<%-- 	<label class="control-label col-sm-2"><spring:message
											code="council.member.designation" text="Designation" /></label>
									<div class="col-sm-4 ">
										<form:select path="" id="design"
											cssClass="form-control chosen-select-no-results"
											class="form-control" data-rule-required="true">
											<form:option value="">Select</form:option>
											<c:forEach items="${command.designationList}" var="lookUp">
												<form:option value="${lookUp.dsgid}">${lookUp.dsgname}</form:option>
											</c:forEach>
										</form:select>
									</div> --%>

					<label class="col-sm-2 control-label"
						for="inwardType"><spring:message
							code="council.member.type" text="Member Type" /></label>
					<c:set var="baseLookupCode" value="MET" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="couMemMasterDto.couMemberType" cssClass="form-control"
						hasChildLookup="false" hasId="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="council.member.type"
						disabled="${command.saveMode eq 'V'}" />

					<label class="col-sm-2 control-label" for="inwardType"><spring:message
							code="council.member.partyaffiliation" text="Party Affiliation" /></label>
					<c:set var="baseLookupCode" value="PAF" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="couMemMasterDto.couPartyAffilation" cssClass="form-control"
						hasChildLookup="false" hasId="true"
						selectOptionLabelCode="applicantinfo.label.select"
						showOnlyLabel="Party Affiliation" />
				</div>
				<div class="form-group">
					<apptags:input labelCode="council.member.name"
						path="couMemMasterDto.couMemName" cssClass="form-control"></apptags:input>
				</div>



				<!-- Start button -->
				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchCouncilMember">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='CouncilMemberMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.button.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-primary"
						onclick="addMemberMaster('CouncilMemberMaster.html','addCouncilMember');"
						title="Add Council Member">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="council.member.sum.button.add"
							text="Add" />
					</button>
				</div>
				<!-- End button -->


				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="memberDatatables">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="council.member.srno" text="Sr.No" /></th>
								<%-- <th width="15%" align="center"><spring:message
										code="council.member.zone" text="Zone" /></th> --%>
								<th width="25%" align="center"><spring:message
										code="council.member.ward" text="Ward" /></th>
								<th width="20%" align="center"><spring:message
										code="council.member.type" text="Member Type" /></th>
								<th width="25%" align="center"><spring:message
										code="council.member.partyaffiliation"
										text="Party Affiliation" /></th>
								<th width="15%" align="center"><spring:message
										code="council.member.name" text="Name" /></th>
								<th width="10%" align="center"><spring:message
										code="council.member.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.couMemMasterDtoList}"
								var="masterData" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${masterData.couEleWZ1Desc}</td>
									<%-- <td>${masterData.couEleWZ2Desc}</td> --%>
									<td>${masterData.couMemberTypeDesc}</td>
									<td>${masterData.couPartyAffDesc}</td>
									<td>${masterData.couMemName}</td>
									<td class="text-center">
										<button type="button"
											class="btn btn-blue-2 btn-sm margin-right-10 "
											name="button-plus" id="button-plus"
											onclick="showGridOption(${masterData.couId},'V')"
											title="
									      <spring:message code="council.button.view" text="view"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button"
											class="btn btn-danger btn-sm btn-sm margin-right-10"
											name="button-123" id=""
											onclick="showGridOption(${masterData.couId},'E')"
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