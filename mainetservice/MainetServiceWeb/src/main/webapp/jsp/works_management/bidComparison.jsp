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
<link rel="stylesheet"
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<script>
	$(function() {
		chosen();
	})
</script>


<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.BID.comparison" text="Bid Comparison" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="common.sequenceconfig.fieldwith"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="common.sequenceconfig.mandatory" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="BIDMaster.html" name="bidMaster" id="bidMaster"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<c:if test="${flag ne 'C'}">
					<div class="form-group">

						<label for="text-1" class="col-sm-2 control-label"><spring:message
								code="wms.BID.bidId" text="BID Id" /></label>
						<div class="col-sm-4">
							<form:input path="" id="bidId" cssClass="form-control" />
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="wms.BID.vendorName" text="Vendor Name" /></label>

						<div class="col-sm-4">

							<div class="table-responsive clear">
								<table class="table table-striped table-bordered"
									id="searchAssetHome">
									<tbody>

										<c:forEach items="${command.vendorMapList}" var="vendorList">
											<tr>
												<td><input type="checkbox" id="vendorId"
													value="${vendorList.key}" class="selectedRow" /></td>
												<td><input type="text" id="vendorId"
													value="${vendorList.value}" class="selectedRow" /></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>


							</div>

						</div>
					</div>

					<div class="text-center margin-bottom-10">

						<button type="button" class="btn btn-success" title="Search"
							onclick="getBidDetailsToCompare()">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="" text="Add"></spring:message>
						</button>

					</div>
				</c:if>
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="wms.BID.technical" text="Technical" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-bordered table-striped"
								id="comparisonTable">

								<tr>
									<th width="8%"></th>
									<c:forEach items="${bidCompareDtosforTech}"
										var="bidCompareDtoforTech">
										<th style="text-align: center" width="8%">${bidCompareDtoforTech.vendorName}</th>
									</c:forEach>
								</tr>

								<tr>
									<th width="8%">Parameter Description</th>
									<c:forEach items="${bidCompareDtosforTech}"
										var="bidCompareDtoforTech">
										<th width="8%">Score</th>
									</c:forEach>
								</tr>

								<c:forEach items="${paramNameforTech}" var="paramNameforTech">

									<tr>
										<th width="8%">${paramNameforTech}</th>
										<c:set var="d" value="0" />
										<c:forEach items="${bidCompareDtosforTech}"
											var="bidCompareDtosforTech">

											<c:if test="${paramNames.size() gt d}">
												<c:forEach items="${bidCompareDtosforTech.paramMap}"
													var="outerMap">

													<c:forEach items="${outerMap.value}" var="innerMap">
														<c:if test="${innerMap.key == paramNameforTech}">
															<th width="8%">${innerMap.value}</th>
														</c:if>
													</c:forEach>

													<c:set var="d" value="${d + 1}" />
												</c:forEach>
											</c:if>

										</c:forEach>




									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>



				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="wms.BID.commercial" text="Commercial" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-bordered table-striped"
								id="comparisonTable">

								<tr>
									<th width="8%">Parameter Description</th>
									<c:forEach items="${bidCompaareDtos}" var="bidCompareDto">
										<th width="8%">Score</th>
									</c:forEach>
								</tr>

								<c:forEach items="${paramNames}" var="paramName">

									<tr>
										<th width="8%">${paramName}</th>
										<c:set var="d" value="0" />
										<c:forEach items="${bidCompaareDtos}" var="bidCompareDto">

											<c:if test="${paramNames.size() gt d}">
												<c:forEach items="${bidCompareDto.paramMap}" var="outerMap">

													<c:forEach items="${outerMap.value}" var="innerMap">
														<c:if test="${innerMap.key == paramName}">
															<th width="8%">${innerMap.value}</th>
														</c:if>
													</c:forEach>

													<c:set var="d" value="${d + 1}" />
												</c:forEach>
											</c:if>

										</c:forEach>




									</tr>
								</c:forEach>
							</table>
						</div>
					</div>

				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="showBIDSearchForm()" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>