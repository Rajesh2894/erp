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
<script type="text/javascript" src="js/rnl/service/notice.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.notice.DemandNotice"
					text="Demand Notice" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="" cssClass="form-horizontal" id="noticePrintId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="rnl.notice.DemandNotice"
										text="Demand Notice" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="rnl.notice.contractNo" text="Contract No" /></label>
									<div class="col-sm-4">
										<form:select path="reportDTO.contractId" id="contractId"
											class="form-control mandColorClass chosen-select-no-results">
											<form:option value="0">
												<spring:message code='selectdropdown' />
											</form:option>
											<c:forEach items="${command.contractLookupList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label "><spring:message
											code="rnl.notice.print.tanant"
											text="Tenant Name" /></label>
									<div class="col-sm-4">
										<form:select path="reportDTO.vendorId" id="vendorId"
											class="form-control mandColorClass chosen-select-no-results"
											disabled="false">
											<form:option value="0">
												<spring:message code='selectdropdown' />
											</form:option>
											<c:forEach items="${command.venderlookupList}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

								<!-- Start button -->
								<div class="text-center clear padding-10">
									<button type="button" class="btn btn-blue-2" title="Search"
										onclick="searchNoticePrint()">
										<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
										<spring:message code="rnl.master.search" text="Search" />
									</button>

									<button type="button" onclick="window.location.href=''"
										class="btn btn-warning" title="Reset">
										<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
										<spring:message code="land.notice.reset" text="Reset" />
									</button>

								</div>
								<!-- End button -->

								<div class="table-responsive clear">
									<table class="table table-striped table-bordered"
										id="noticeBillTable">
										<thead>
											<tr>
												<th width="3%" align="center"><spring:message
														code="land.notice.srNo" text="Sr.No" /></th>
												<%-- <th class="text-center"><spring:message
														code="land.notice.all" text="All" /></th> --%>
												<th class="text-center"><spring:message
														code="rnl.notice.contractNo" text="Contract No." /></th>
												<th class="text-center"><spring:message
														code="rnl.notice.tenant" text="Tenant Name" /></th>
												<th class="text-center"><spring:message
														code="rnl.notice.outAmt" text="Outstanding Amount" /></th>
												<th><spring:message code="" text="Action Print" /></th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.reportDTOList}" var="notice"
												varStatus="status">
												<tr>
													<td class="text-center">${status.count}</td>
													<td>${notice.contractNo}</td>
													<td>${notice.tenantName}</td>
													<td>${notice.outstandingAmt}</td>
													<td align="center">
														<button style="height: 30px; width: 80px" type="button"
															class="btn btn-primary hidden-print"
															title="Print Notice Letter"
															onclick="printNotice('${notice.contractId}','ENG')">
															<i class="fa fa-print"> <spring:message code=""
																	text="English " /></i>
														</button>
														<button style="height: 30px; width: 80px" type="button"
															class="btn btn-primary hidden-print"
															title="Print Notice Letter"
															onclick="printNotice('${notice.contractId}','MAR')">
															<i class="fa fa-print"> <spring:message code=""
																	text="Marathi " /></i>
														</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>
							</div>
						</div>
					</div>
			</form:form>
			<!-- End Form -->

		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->