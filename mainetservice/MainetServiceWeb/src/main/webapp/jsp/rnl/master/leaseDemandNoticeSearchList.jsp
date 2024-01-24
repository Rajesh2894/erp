<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rnl/master/leaseDemandNoticeGeneration.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">
					<spring:message code="rnl.book.help" text="Help" />
			</span>
			</a>
		</div>

		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="rnl.title.leaseNotice" text="Lease Demand Notice Generation" />
				</h2>
			</div>
			<div class="widget">
				<div class="mand-label clearfix">
					<span><spring:message code="material.management.mand"
							text="Field with" /> <i class="text-red-1">*</i> <spring:message
							code="material.management.mand.field" text="is mandatory" /> </span>
				</div>
				<div class="widget-content padding">
					<form:form action="LeaseDemandNoticeGeneration.html" commandName="command" method="POST" class="form-horizontal" 
							   name="leaseDemandNoticeGeneration" id="leaseDemandNoticeGeneration" >
					 
					 	<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span id="errorId"></span>
						</div>
						
						<div class="form-group">
							<label for="text-1" class="col-sm-2 control-label required-control"> 
								<spring:message code="rnl.notice.conntype" text="Connection Type" />
							</label>
							<div class="col-sm-4 margin-top-5">
								<label for="noticeSingle" class="radio-inline"> 				
									 <form:radiobutton class="radio-group margin-right-5 noticeType" onclick="payModefields();" 
									  id="single" path="dto.noticeTypeDesc" value="S" disabled="true"  />
									<spring:message code="rnl.notice.single" text="Single" />
								</label> 
								<label for="noticeMultiple" class="radio-inline"> 								
								    <form:radiobutton class="radio-group noticeType" onclick="payModefields();"
								      id="multiple" path="dto.noticeTypeDesc" value="M" checked="true" />
									<spring:message code="rnl.notice.multiple" text="Multiple" /> 
								</label>
							</div>
						</div>
						
						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label propNo"> <spring:message
									code="rnl.notice.propNo" text="Property Number" />
							</label>
							<div class="col-sm-4">
								<spring:message code="rnl.enterPropNo" text="Enter the Property Number" var="enterPropertyNo" />
								<form:input type="text" class="form-control propNo"
									id="propNo" path="dto.refNo"
									placeholder="${enterPropertyNo}" />
							
							</div>
						</div>

						<div class="form-group">
							<label class="control-label required-control col-sm-2 nType"><spring:message
									code="demand.notice.noticeType" text="Notice Type" /></label>
							<c:set var="baseLookupCode" value="RNG" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}" path="dto.notTyp"
								cssClass="form-control nType" hasChildLookup="false"
								hasId="true" showAll="false" disabled="true"
								selectOptionLabelCode="applicantinfo.label.select" />

							<label class="control-label col-sm-2 required-control location"
								for="locationId"><spring:message code="estate.label.Location" /></label>
							<div class="col-sm-4 location">
								<form:select path="dto.locationId" id="locationId" disabled="true"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
								</form:select>
								
							</div>
						</div>

						<div class="text-center padding-top-10">
							<button type="button" id="generateBtn" class="btn btn-success btn-submit"
								onclick="saveOrUpdateForm(this,'DemandNoticeGenerationsuccessfully!','LeaseDemandNoticeGeneration.html', 'saveform')">
								<spring:message code="rnl.GenerateDemandNotice" />
							</button>
							<button type="button" id="searchBtn" onclick="searchData('LeaseDemandNoticeGeneration.html','searchDetails');" 
								class="btn btn-success" title="<spring:message code="rnl.master.search" text="Search" />" >
								<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
								<spring:message code="rnl.master.search" text="Search" />
							</button>
							<button type="button" class="btn btn-warning"
								onclick="window.location.href='LeaseDemandNoticeGeneration.html'">
								<spring:message code="rnl.master.reset" />
							</button>
							<button type="button" class="btn btn-danger" onclick="backButton();">
								<spring:message code="rnl.master.back" />
							</button>
						</div>

						<!-- table for multiple records -->
								<c:if test="${not empty command.demandDtoList}">
								<table class="table table-bordered margin-bottom-10 myTable"" id="datatables">
								<thead>
									<tr>
										<th class="text-center"><spring:message code="rnl.master.sr.no" text="Sr No." /></th>
										<th class="text-center"><spring:message code="rnl.notice.propNo" text="Property Number" /></th>
										<th class="text-center"><spring:message code="rnl.fromDate" text="From Date" /></th>
										<th class="text-center"><spring:message code="rnl.toDate" text="To Date" /></th>
										<th class="text-center"><spring:message code="rnl.pendingAmount" text="Pending Amount" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.demandDtoList}" var="data" varStatus="loop">
										<tr>
											<td class="text-center"><c:out value="${loop.index + 1}" ></c:out></td>
											<td class="text-center" path="demandDtoList[${e}].refNo">${data.refNo}</td>
											<td class="text-center" path="demandDtoList[${e}].fromDate">${data.fromDate}</td>
											<td class="text-center" path="demandDtoList[${e}].toDate">${data.toDate}</td>
											<td class="text-center" path="demandDtoList[${e}].pendingAmount">${data.pendingAmount}</td>

										</tr>
										<c:set var="e" value="${e + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>

						<div class="text-center padding-top-10">
							<button type="button" id="generateBtn"
								class="btn btn-success btn-submit"
								onclick="saveOrUpdateForm(this,'DemandNoticeGenerationsuccessfully!','LeaseDemandNoticeGeneration.html', 'saveform')">
								<spring:message code="rnl.GenerateDemandNotice" />
							</button>
						</div>
						</c:if>
					</form:form>
				</div>
			</div>
		</div>

	</div>
	</div>