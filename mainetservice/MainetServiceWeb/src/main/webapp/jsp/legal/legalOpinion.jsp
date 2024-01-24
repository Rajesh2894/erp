<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/legal/legalOpinion.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.lglOpn" text="Legal Opinion" />
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="HearingDate.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="LegalOpinion.html" name="LegalOpinion"
				id="LegalOpinion" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />



				<div class="form-group">

					<label class="control-label col-sm-2"> <spring:message
							code="lgl.dep" text="Department" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="legalOpinionDetailDTO.opniondeptId" id="opinionDeptid">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="department">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>


				</div>
			
				<div class="text-center clear padding-10">

					<button type="button" class="btn btn-blue-2 search"
						onclick="searchCaseForLegalOpinion();return false;">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success"
						onclick="openForm('LegalOpinion.html','ADD');" name="button-Add"
						style="" id="button-submit">
						<spring:message code="lgl.add" text="Add" />
					</button>

					<button type="Reset" class="btn btn-warning"
						onclick="resetLegalopinionSearch();">
						<spring:message code="lgl.reset" text="Reset"></spring:message>
					</button>
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
				</div>


				<div class="table-responsive clear">
					<table summary="SUMMARY"
						class="table table-bordered table-striped pmId" id="opinionTable">
						<thead>
							<tr>
								<th><spring:message code="label.checklist.srno"
										text="Sr.No." /></th>
								<th><spring:message code="caseEntryDTO.cseSectAppl" text="Section Act Applied" /></th>
								<th><spring:message code="caseEntryDTO.cseMatdet1" text="Matter of Dispute" /></th>
								<th><spring:message code="lgl.action" text="Action" /></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.legalOpinionDetailDTOList}"
								var="data" varStatus="index">
								<tr>
									<td class="text-center" width="2%">${index.count}</td>
									<td class="text-center" width="7%">${data.sectionActApplied}</td>
									<td class="text-center" width="10%">${data.matterOfDispute}</td>
									<td class="text-center"  width="10%">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View Opinion Details"
											onclick="viewOpinion(${data.id},'LegalOpinion.html','VIEW','V')">
											<i class="fa fa-eye"></i>
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