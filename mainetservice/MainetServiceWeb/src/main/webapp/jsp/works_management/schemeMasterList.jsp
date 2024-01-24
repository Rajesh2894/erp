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
<script type="text/javascript" src="js/works_management/schemeMaster.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="scheme.master.summary" text="" />
			</h2>
			<div class="additional-btn">
			    <apptags:helpDoc url="WmsSchemeMaster.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1">*</i> <spring:message code="" text="" /></span>
			</div>
			<form:form action="WmsSchemeMaster.html" class="form-horizontal"
				name="wmsSchemeMaster" id="wmsSchemeMasterList">
				<form:hidden path="cpdMode" id="cpdMode" />
				<form:hidden path="UADstatusForScheme" id="UADstatusForScheme" />
				<form:hidden path="" id="isDefaultStatusSchm"
					value="${userSession.organisation.defaultStatus}" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="scheme.master.title" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="scheme.master.schemename.eng" /></label>
									<div class="col-sm-4">
										<form:input path="" id="schNameEng" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<%-- <apptags:date fieldclass="datepicker"
										datePath="schemeMasterDTO.wmSchStrDate"
										labelCode="scheme.master.startdate" cssClass="custDate"></apptags:date> --%>
									<%-- <apptags:date fieldclass="datepicker"
										datePath="schemeMasterDTO.wmSchEndDate"
										labelCode="scheme.master.enddate" cssClass="custDate"></apptags:date> --%>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="wms.SourceofFund" text="Source of Fund" /></label>
									<div class="col-sm-4">
										<form:select path=""
											class="form-control chosen-select-no-results" id="sourceCode"
											onchange="getSchemeDetails(this,'N');">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.sourceLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label "><spring:message
											code="scheme.master.select.scheme.name"
											text="Select Scheme Name" /></label>
									<div class="col-sm-4" onclick="return testFunction(this)">
										<form:select path="" id="sourceName"
											class="form-control chosen-select-no-results sourceNameSearch ">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
										</form:select>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center clear padding-10">
						<button class="btn btn-blue-2  search" id="searchSchemeMaster"
							type="button">
							<i class="fa fa-search padding-right-5"></i>
							<spring:message code="works.management.search" text="" />
						</button>
						<button type="Reset" class="btn btn-warning"
							onclick="resetScheme()">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
						<c:if
							test="${command.UADstatusForScheme ne  'YES' || userSession.organisation.defaultStatus eq 'Y'}">
							<button class="btn btn-primary add"
								onclick="openAddSchemeMaster('WmsSchemeMaster.html','AddSchemeMaster');"
								type="button">
								<i class="fa fa-plus-circle padding-right-5"></i>
								<spring:message code="works.management.add" text="" />
							</button>
						</c:if>
					</div>
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered" id="datatables">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="ser.no" text="Sr.No" /></th>
									<%-- <th width="10%" align="center"><spring:message
											code="scheme.master.schemecode" text="" /></th> --%>
									<th width="25%" align="center"><spring:message
											code="scheme.master.schemename.eng" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="scheme.master.fund" text="" /></th>
									<%-- <th width="15%" align="center"><spring:message
											code="scheme.master.startdate" text="" /></th> --%>
									<%-- <th width="15%" align="center"><spring:message
											code="scheme.master.enddate" text="" /></th> --%>
									<th width="10%" align="center"><spring:message
											code="works.management.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${command.UADstatusForScheme eq 'YES' && userSession.organisation.defaultStatus ne 'Y'}">
										<c:forEach items="${command.schemeMasterList}"
											var="schemeMasterDto" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<%-- <td>${schemeMasterDto.wmSchCode}</td> --%>
												<td>${schemeMasterDto.wmSchNameEng}</td>
												<c:if test="${command.cpdMode eq 'L'}">
													<td>${schemeMasterDto.schFundDesc}</td>
												</c:if>
												<c:if test="${command.cpdMode ne 'L'}">
													<td>${schemeMasterDto.schFundName}</td>
												</c:if>
												<%--<td align="center">${schemeMasterDto.schStrDateDesc}</td>
										 <td align="center">${schemeMasterDto.schEndDateDesc}</td> --%>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onClick="showGridOption(${schemeMasterDto.wmSchId} , 'V')"
														title="<spring:message code="works.management.view"></spring:message>">
														<i class="fa fa-eye"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${command.schemeMasterList}"
											var="schemeMasterDto" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<%-- <td>${schemeMasterDto.wmSchCode}</td> --%>
												<td>${schemeMasterDto.wmSchNameEng}</td>
												<c:if test="${command.cpdMode eq 'L'}">
													<td>${schemeMasterDto.schFundDesc}</td>
												</c:if>
												<c:if test="${command.cpdMode ne 'L'}">
													<td>${schemeMasterDto.schFundName}</td>
												</c:if>
												<%--<td align="center">${schemeMasterDto.schStrDateDesc}</td>
										 <td align="center">${schemeMasterDto.schEndDateDesc}</td> --%>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onClick="showGridOption(${schemeMasterDto.wmSchId} , 'V')"
														title="<spring:message code="works.management.view"></spring:message>">
														<i class="fa fa-eye"></i>
													</button>
													<button type="button" class="btn btn-success btn-sm"
														onClick="showGridOption(${schemeMasterDto.wmSchId}, 'E')"
														title="<spring:message code="works.management.edit"></spring:message>">
														<i class="fa fa-pencil-square-o"></i>
													</button>
													<button type="button" class="btn btn-danger btn-sm"
														onClick="showGridOption(${schemeMasterDto.wmSchId} , 'D')"
														title="<spring:message code="works.management.delete"></spring:message>">
														<i class="fa fa-trash aria-hidden='true'"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
