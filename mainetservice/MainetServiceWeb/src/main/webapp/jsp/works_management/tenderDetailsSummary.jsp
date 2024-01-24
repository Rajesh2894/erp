<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>


<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code=""
					text="Update/Get Tender/Quotation Details Summary" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="" class="form-horizontal" id="" name="">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="" text="Project Name" /></label>
					<div class="col-sm-4">

						<form:select path="tenderMasDto.projId"
							cssClass="form-control chosen-select-no-results" id="projId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${tenderProjects}" var="projArray">
								<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Initiation No." /> </label>
					<div class="col-sm-4">
						<form:input path="tenderMasDto.initiationNo" cssClass="form-control" id="" readonly=""
							data-rule-required="" />
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Initiation Date" /></label>
					<div class="col-sm-4">
						<form:input path="tenderMasDto.initiationDate" id=""
							class="form-control mandColorClass datepicker" value=""
							readonly="" data-rule-required="" disabled="" />
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button class="btn btn-success  search"
						onclick="searchMilestone();" type="button">
						<i class="button-input"></i>
						<spring:message code="" text="Update/Get Details" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href=''">
						<i class="button-input"></i>
						<spring:message code="reset.msg" />
					</button>
				</div>
<%-- 				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="" text="Initiation No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="" text="Initiation Date" />
								<th scope="col" width="18%" class="text-center"><spring:message
										code="" text="Tender/Quotation No." /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="" text="Amount" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="" text="Vendor Name" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${milestoneList}" var="mstDto">
								<tr>
									<td>${mstDto.mileStoneDesc}</td>
									<td></td>
									<td>${mstDto.mileStoneWeight}</td>
									<td>${mstDto.msStartDate}</td>
									<td>${mstDto.msEndDate}</td>
									<td class="text-center">
										<button type="button" class="btn btn-primary btn-sm"
											title="Update Progress" onclick="">
											<i class="fa fa-line-chart"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div> --%>
			</form:form>
		</div>
	</div>
</div>