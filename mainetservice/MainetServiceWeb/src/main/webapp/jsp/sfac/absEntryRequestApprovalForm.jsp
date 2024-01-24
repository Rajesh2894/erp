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
<script type="text/javascript" src="js/sfac/absEntryForm.js"></script>
<style>
ul.imp-notes {
	margin: 0 0 0 1rem;
}

ul.imp-notes li {
	font-size: 0.75em;
	list-style-type: disc;
}

table


#cbboAssessEntTable
>
thead
>
tr
>
:is

 

(
:not

 

(
th


:nth-child(1)
,
th


:nth-child(2)


	

)),
table


#cbboAssessEntTable
>
tbody
>
tr
>
:is

 

(
:not

 

(
th


:nth-child(1)
,
th


:nth-child(2)
,
td


:nth-child(1)
,
td


:nth-child(2)

 

)),
table


#cbboAssessEntTable
>
tfoot
>
tr
>
:is


	

(
:not

 

(
th


:nth-child(1)
,
th


:nth-child(2)

 

))
{
padding


:

 

0;
}
table#cbboAssessEntTable tbody tr th.text-left {
	text-align: left;
}

table.inner-table, table.inner-table-2 {
	width: 100%;
}

table.inner-table tbody tr th, table.inner-table tbody tr td, table.inner-table-2 thead tr th
	{
	border: solid rgb(8 131 55/ 94%) !important;
}

table


.inner-table

 

thead

 

tr

 

th


:not

 

(
:nth-child(2)

 

),
table


.inner-table

 

tbody

 

tr

 

:is


	

(
th


:not

 

(
:nth-child(2)

 

),
td


:not

 

(
:nth-child(2)

 

)),
table


.inner-table-2

 

thead

 

tr
>
th
,
table


.inner-table-2

 

tbody

 

tr
>
:is

 

(
th
,
td

 

)
{
padding


:

 

0
.3rem


;
}
table.inner-table>tbody>tr:not (:last-child ) >th, table.inner-table>tbody>tr:not
	 (:last-child ) >td {
	border-width: 0 0 1px 0 !important;
}

table.inner-table>tbody>tr:last-child>th, table.inner-table>tbody>tr:last-child>td
	{
	border-width: 0 !important;
}

table.inner-table-2 thead tr th, table.inner-table-2:last-child tbody tr th,
	table.inner-table-2:last-child tbody tr td {
	border-width: 0 1px 0 1px !important;
}

table.inner-table-2:not (:last-child ) tbody tr th, table.inner-table-2:not
	 (:last-child ) tbody tr td {
	border-width: 0 1px 1px 1px !important;
}

table#cbboAssessEntTable>thead>tr:nth-child(2)>th:nth-child(1), table#cbboAssessEntTable>thead>tr:nth-child(2)>th:nth-child(2)
	{
	width: 5.8rem;
}
table


.inner-table

 

thead

 

tr

 

th


:nth-child(1)
,
table


.inner-table

 

tbody

 

tr

 

:is


	

(
th


:nth-child(1)
,
td


:nth-child(1)

 

)
{
width


:

 

23
.16rem


;
}
table


.inner-table

 

thead

 

tr

 

th


:nth-child(3)
,
table


.inner-table

 

tbody

 

tr

 

:is


	

(
th


:nth-child(3)
,
td


:nth-child(3)

 

)
{
width


:

 

9
rem


;
}
table


.inner-table-2

 

thead

 

tr

 

th


:nth-child(1)
,
table


.inner-table-2

 

tbody

 

tr

 

:is


	

(
th


:nth-child(1)
,
td


:nth-child(1)

 

)
{
width


:

 

13
.72rem


;
}
table


.inner-table-2

 

thead

 

tr

 

th


:nth-child(2)
,
table


.inner-table-2

 

tbody

 

tr

 

:is


	

(
th


:nth-child(2)
,
td


:nth-child(2)

 

)
{
width


:

 

5
.2rem


;
}
table


.inner-table-2

 

thead

 

tr

 

th


:nth-child(3)
,
table


.inner-table-2

 

tbody

 

tr

 

:is


	

(
th


:nth-child(3)
,
td


:nth-child(3)

 

)
{
width


:

 

3
.94rem


;
}
table


.inner-table-2

 

thead

 

tr

 

th


:nth-child(4)
,
table


.inner-table-2

 

tbody

 

tr

 

:is


	

(
th


:nth-child(4)
,
td


:nth-child(4)

 

)
{
width


:

 

5
.3rem


;
}
table


.inner-table-2

 

thead

 

tr

 

th


:nth-child(5)
,
table


.inner-table-2

 

tbody

 

tr

 

:is


	

(
th


:nth-child(5)
,
td


:nth-child(5)

 

)
{
width


:

 

3
.84rem


;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.abs.entry.title"
					text="Audit Balance Sheet Entry Form" />
			</h2>
			<apptags:helpDoc url="ABSEntryApprovalForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="ABSEntryForm" action="ABSEntryApprovalForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.fpo.fpoName" text="FPO Name" /></label>
					<div class="col-sm-4">
						<form:input path="dto.fpoName" id="fpoName" class="form-control "
							disabled="true" />
						<form:hidden path="dto.fpoId" id="fpoId" />

					</div>

					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.fpo.fpoAddress" text="FPO Address" /></label>
					<div class="col-sm-4">
						<form:input path="dto.fpoAddress" id="fpoName"
							class="form-control " disabled="true" />


					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.abs.entry.crp.fd"
							text="Figure as the end of(current reporting period) From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dto.crpDateFrom" type="text"
								class="form-control datepicker mandColorClass" id="crpDateFrom"
								placeholder="dd/mm/yyyy" readonly="true"
								disabled="${command.viewMode eq 'V' ? true : false }" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>


					</div>

					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.abs.entry.crp.td"
							text="Figure as the end of(current reporting period) To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dto.crpDateTo" type="text"
								class="form-control datepicker mandColorClass" id="crpDateTo"
								placeholder="dd/mm/yyyy" readonly="true"
								disabled="${command.viewMode eq 'V' ? true : false }" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>


					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.abs.entry.prp.fd"
							text="Figure as the end of(Previous reporting period) From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dto.prpDateFrom" type="text"
								class="form-control datepicker mandColorClass" id="prpDateFrom"
								placeholder="dd/mm/yyyy" readonly="true"
								disabled="${command.viewMode eq 'V' ? true : false }" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>


					</div>

					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.abs.entry.prp.td"
							text="Figure as the end of(Previous reporting period) To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dto.prpDateTo" type="text"
								class="form-control datepicker mandColorClass" id="prpDateTo"
								placeholder="dd/mm/yyyy" readonly="true"
								disabled="${command.viewMode eq 'V' ? true : false }" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>


					</div>
				</div>


				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="absEntryTable" width="100%">
						<thead>
							<tr>
								<th colspan="3"><spring:message
										code="sfac.abs.entry.balance.sheet.detail"
										text="Balance Sheet Details" /></th>
								<%-- <th>
									<spring:message code="sfac.scoring" text="Scoring" />
								</th> --%>
							</tr>
							<tr>
								<th width="20%"><spring:message code="sfac.key.parameter"
										text="Key Parameter" /></th>

								<th>
									<table class="inner-table">
										<thead>
											<tr>
												<th width="30%"><spring:message
														code="sfac.abs.entry.particulars" text="Particulars" /></th>
												<th>
													<table class="inner-table-2">
														<thead>
															<tr>
																<th width="20%"><spring:message
																		code="sfac.ass.Condition" text="Condition" /></th>
																<th width="15%"><spring:message
																		code="sfac.abs.entry.crp"
																		text="Figure as the end of(current reporting period)" /></th>
																<th width="15%"><spring:message
																		code="sfac.abs.entry.prp"
																		text="Figure as the end of(Previous reporting period)" /></th>
															</tr>
														</thead>
													</table>
												</th>

											</tr>
										</thead>
									</table>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="d" value="0" scope="page"></c:set>
							<c:set var="w" value="0" scope="page"></c:set>
							<c:forEach var="absMastDto"
								items="${command.dto.auditBalanceSheetKeyParameterDtos}"
								varStatus="status">
								<tr>
									<td class="text-center vertical-align-middle" width="20%">${absMastDto.keyParameterDesc}</td>

									<td><c:set var="e" value="0" scope="page"></c:set>
										<table class="inner-table">

											<c:forEach var="detDto"
												items="${absMastDto.auditBalanceSheetSubParameterDtos}"
												varStatus="status">
												<tr>
													<td width="30%">${detDto.subParameterDesc}</td>
													<td><c:set var="f" value="0" scope="page"></c:set> <c:choose>
															<c:when
																test="${fn:length(detDto.auditBalanceSheetSubParameterDetailDtos)>0 }">
																<c:forEach var="subDto"
																	items="${detDto.auditBalanceSheetSubParameterDetailDtos}">

																	<table class="inner-table-2">
																		<tr>
																			<td width="20%">${subDto.conditionDesc}</td>
																			<td width="15%"><form:input
																					path="dto.auditBalanceSheetKeyParameterDtos[${d}].auditBalanceSheetSubParameterDtos[${e}].auditBalanceSheetSubParameterDetailDtos[${f}].crpAmount"
																					id="crpAmount" class="form-control " disabled="${command.viewMode eq 'V' ? true : false }"  /></td>
																			<td width="15%"><form:input
																					path="dto.auditBalanceSheetKeyParameterDtos[${d}].auditBalanceSheetSubParameterDtos[${e}].auditBalanceSheetSubParameterDetailDtos[${f}].prpAmount"
																					id="prpAmount" class="form-control " disabled="${command.viewMode eq 'V' ? true : false }" /></td>


																		</tr>
																	</table>
																	<c:set var="f" value="${f + 1}" scope="page" />
																</c:forEach>
															</c:when>
															<c:otherwise>


																<table class="inner-table-2">
																	<tr>
																		<td width="20%">&nbsp;</td>
																		<td width="15%"><form:input
																				path="dto.auditBalanceSheetKeyParameterDtos[${d}].auditBalanceSheetSubParameterDtos[${e}].auditBalanceSheetSubParameterDetailDtos[${f}].crpAmount"
																				id="crpAmount" class="form-control " disabled="${command.viewMode eq 'V' ? true : false }"  /></td>
																		<td width="15%"><form:input
																				path="dto.auditBalanceSheetKeyParameterDtos[${d}].auditBalanceSheetSubParameterDtos[${e}].auditBalanceSheetSubParameterDetailDtos[${f}].prpAmount"
																				id="prpAmount" class="form-control " disabled="${command.viewMode eq 'V' ? true : false }" /></td>


																	</tr>
																</table>
																<c:set var="f" value="${f + 1}" scope="page" />

															</c:otherwise>

														</c:choose></td>

												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>

										</table></td>
								</tr>
								<tr>

									<th colspan="3">
										<table class="inner-table">
											<tr>
												<th class="text-left">Total (A)</th>
												<th>
													<table class="inner-table-2">
														<tr>

															<th id="${d }crp">0.0</th>
															<th id="${d }prp">0.0</th>

														</tr>
													</table>
												</th>
												<th></th>
											</tr>
										</table>
									</th>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>

					</table>
				</div>

					<div class="">

						<apptags:CheckerAction hideForward="true" hideSendback="true"
							hideUpload="true"></apptags:CheckerAction>

					</div>



				<div class="text-center padding-top-10">
					<button type="button" align="center" class="btn btn-green-3"
							data-toggle="tooltip" data-original-title="Submit"
							onclick="saveABSEntryApprovalData(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>

						<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>

				<%-- </c:if> --%>

			</form:form>
		</div>
	</div>
</div>