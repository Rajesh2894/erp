<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/works_management/bidMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="works.bid.master" text="Bid Master" />
			</h2>
			<apptags:helpDoc url="BIDEntry.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="BIDEntry.html" class="form-horizontal" name=""
				id="tenderInitiation">
				<form:hidden path="serviceFlag" id="serviceFlag" />
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>


				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" text="Project Name" /> </label>
					<div class="col-sm-4">
						<form:select path="projectid"
							cssClass="form-control chosen-select-no-results mandColorClass"
							onchange="gettender()" id="projId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${tenderProjects}" var="projArray">
								<c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
								<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[1]}</form:option>
							</c:if>
							<c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
								<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[3]}</form:option>
							</c:if>
							</c:forEach>
						</form:select>
					</div>

					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="wms.BID.tenderNo" text="Tender No" /> </label>
					<div class="col-sm-4">
						<form:select path="tndNo"
							class="form-control chosen-select-no-results mandColorClass"
							id="tndNo">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.tnderNoList}" var="tender">
								<form:option value="${tender.initiationNo}">${tender.initiationNo}</form:option>

							</c:forEach>

						</form:select>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 type="h4" class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a3"> <spring:message
									code="works.bid.entry" text="Bid Entry" />
							</a>
						</h4>
					</div>
					<div id="a3" class="panel-collapse collapse in">
						<div class="panel-body padding-top-0">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-striped table-condensed table-bordered"
								id="bidentryDetail">

								<thead>
									<tr>
										<th width="7%"><spring:message code="ser.no" text="Sr. No." />
											<i class="text-red-1"></i></th>
										<th width="13%"><spring:message code="works.bidId" text="Bid ID" />
											<i class="text-red-1">*</i></th>
										<th width="15%"><spring:message code="works.bidderName"
												text="Bidder Name" /> <i class="text-red-1">*</i></th>
										<th width="20%"><spring:message code="works.techinical.status"
												text="Technical Evaluation Status" /> <i class="text-red-1">*</i></th>
										<th width="20%"><spring:message code="works.financial.status"
												text="Financial Evaluation Status" /> <i class="text-red-1">*</i></th>
										<th width="10%"><spring:message code="works.rank" text="Rank" /> <i
											class="text-red-1">*</i></th>
										<th width="17%"><spring:message code="works.management.action" text="Action" />
										<a href="javascript:void(0);" data-toggle="tooltip" title="<spring:message
												code="works.management.add" text="Add" />"
											data-placement="top" onclick="addEntryData();"
											class=" btn btn-success addtable btn-sm"><i
												class="fa fa-plus-circle"> </i></a>
											</th>

									</tr>
								</thead>
								<tbody>

									<tr class="BidClass">
									
										<td><form:input path="" id="sNo${d}" value="${d + 1}" readonly="true"
                                                cssClass="form-control " /></td>
										<td><form:input path="bidDtoList[${d}].bidNo" type="text" class="form-control "
												maxLength="100" id="bidId${d}"  onchange="CheckName(${d})"/></td>
										<td><form:input path="bidDtoList[${d}].bidIdDesc" type="text" class="form-control"
												maxLength="100" id="vendorName${d}" /></td>
										<td><form:input readonly="true" path="bidDtoList[${d}].tenderstatus" type="text" class="form-control"
												maxLength="100" id="tenderstatus${d}" /></td>
										<td><form:input  readonly="true" path="bidDtoList[${d}].financestatus" type="text" class="form-control"
												maxLength="100" id="financestatus${d}" /></td>
										<td><form:input readonly="true" path="bidDtoList[${d}].rank" type="text" class="form-control "
												maxLength="100" id="rank${d}" /></td>

										<td class="text-center">
										<!-- <a href="javascript:void(0);"
												class="btn btn-green-3"
											onclick="bidTechnicalEntry('BIDMaster.html','Technical',this);"><i
												class="fa fa-edit" id=""></i></a>
										 <a href="javascript:void(0);"
												class="btn btn-blue-3"
											onclick="bidFinancialEntry('BIDMaster.html','Financial');"><i
												class="fa fa-external-link" id=""></i></a> -->
										
										 <a href="javascript:void(0);"
												class="btn btn-danger btn-sm delButton" title="<spring:message
												code="works.management.delete"  text="Delete" />"
											onclick="deleteEntry($(this),'removedIds');"><i
												class="fa fa-minus" id=""></i></a></td>

									</tr>
									<c:set var="d" value="" scope="page" />

								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success"
						onclick="saveForm(this)"
						title="<spring:message code="works.management.save" text="Save"></spring:message>">
						<i class=" padding-right-5" aria-hidden="true"></i>
						<spring:message code="works.management.save" text="Save"></spring:message>
					</button>
					
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="resetForm()" id="button-Cancel"
						title="<spring:message code="works.management.back" text="Back"></spring:message>">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>

					<%-- <button type="button" class="btn btn-primary"
						title="Send for Approval" onclick="sendForApproval(${summaryDto.contId},'S');">
						<i class="padding-right-5" aria-hidden="true"></i>
						<spring:message code="" text="Send for Approval"></spring:message>
					</button> --%>


				</div>
			</form:form>
		</div>
	</div>
</div>
