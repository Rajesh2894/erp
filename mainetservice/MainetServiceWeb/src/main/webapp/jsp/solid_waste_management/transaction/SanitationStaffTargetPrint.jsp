<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/SanitationStaffTarget.js"></script>
<script type="text/javascript">
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<strong><spring:message code="swm.sanitationstafftrgt"
						text="Sanitation Staff Target" /></strong>
			</div>
			<div class="widget-content padding">
				<form:form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-sm-2">
							<h1></h1>
						</div>
						<div class="col-xs-8 col-sm-8  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								${ userSession.getCurrent().organisation.ONlsOrgname} <br>
								<spring:message code="solid.waste.targetFor"
									text="Sanitation
								Staff Target For" />
								<c:if test="${command.sanitationStaffTargetDto.sanType eq 1}">
									<spring:message code="swm.segregation" text="Segregation"
										var="Segregation" />${Segregation}										
								</c:if>
								<c:if test="${command.sanitationStaffTargetDto.sanType eq 2}">
									<spring:message code="swm.disposal" text=" Disposal"
										var="Disposal" />${Disposal}
								</c:if>
								<c:if test="${command.sanitationStaffTargetDto.sanType eq 3}">
									<spring:message code="swm.collection" text=" collection"
										var="collection" />${collection}
								</c:if>
							</h2>
						</div>
						<div class="col-sm-2">
							<p>
								<spring:message code="swm.day.wise.month.report.date"
									text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="swm.day.wise.month.report.time"
									text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="clearfix padding-10"></div>
						<div align="center" class="form-group">
							<div class="col-sm-3"></div>
							<label for="select-1479372680758" class="col-sm-1 control-label"><spring:message
									code="swm.fromDate" text="From
								Date" /></label>
							<div class="col-sm-2">
								<p>
									<fmt:formatDate
										value="${command.sanitationStaffTargetDto.sanTgfromdt}"
										pattern="dd/MM/yyyy" />
								</p>
							</div>
							<label for="select-1479372680758" class="col-sm-1 control-label"><spring:message
									code="swm.toDate" text="To
								Date" /></label>
							<div class="col-sm-2">
								<p>
									<fmt:formatDate
										value="${command.sanitationStaffTargetDto.sanTgtodt}"
										pattern="dd/MM/yyyy" />
								</p>
							</div>
						</div>
						<div class="padding-5 clear">&nbsp;</div>
						<div class="overflow-visible">
							<div id="export-excel">
								<table class="table table-bordered table-condensed">
									<thead>
										<tr>
											<th class="text-center"><spring:message
													code="public.toilet.master.srno" text="Sr.No." /></th>
											<th class="text-center"><spring:message
													code="swm.empname" text="Employee Name" /></th>
											<c:if test="${command.sanitationStaffTargetDto.sanType eq 1}">
												<th class="text-center"><spring:message
														code="swm.wastetype" text="Waste Type" /></th>
												<th class="text-center"><spring:message
														code="swm.wastSubType1" text="Waste SubType(1)" /></th>
												<th class="text-center"><spring:message
														code="swm.wastSubType2" text="Waste SubType(2)" /></th>
											</c:if>
											<c:if test="${command.sanitationStaffTargetDto.sanType eq 2}">
												<th class="text-center"><spring:message
														code="swm.dsplsite" text="Waste Collection Centre" /></th>
											</c:if>
											<c:if test="${command.sanitationStaffTargetDto.sanType eq 3}">
												<th class="text-center"><spring:message
														code="swm.route" text="Route" /></th>
											</c:if>
											<th class="text-center"><spring:message
													code="swm.grbgVol" text="Garbage Volume" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th colspan="7" class="ts-pager form-horizontal">
												<div class="btn-group">
													<button type="button" class="btn first">
														<i class="fa fa-step-backward" aria-hidden="true"></i>
													</button>
													<button type="button" class="btn prev">
														<i class="fa fa-arrow-left" aria-hidden="true"></i>
													</button>
												</div> <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
												<div class="btn-group">
													<button type="button" class="btn next">
														<i class="fa fa-arrow-right" aria-hidden="true"></i>
													</button>
													<button type="button" class="btn last">
														<i class="fa fa-step-forward" aria-hidden="true"></i>
													</button>
												</div> <select class="pagesize input-mini form-control"
												title="Select page size">
													<option selected="selected" value="10" class="form-control">10</option>
													<option value="20">20</option>
													<option value="30">30</option>
													<option value="all"><spring:message
															code="swm.report.all.records" text="All Records" /></option>
											</select> <select class="pagenum input-mini form-control"
												title="Select page number"></select>
											</th>
										</tr>
									</tfoot>
									<tbody>

										<c:set var="d" value="0" scope="page"></c:set>
										<c:forEach
											items="${command.sanitationStaffTargetDto.sanitationStaffTargetDet}"
											var="data" varStatus="index">
											<tr>
												<td class="text-center">${d+1}</td>
												<td class="text-center">${command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].employeeName}</td>
												<c:if
													test="${command.sanitationStaffTargetDto.sanType eq 1}">
													<td class="text-center">${command.getHierarchicalLookUpObject(command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].codWast1).getLookUpDesc()}</td>
													<td class="text-center">${command.getHierarchicalLookUpObject(command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].codWast2).getLookUpDesc()}</td>
													<td class="text-center">${command.getHierarchicalLookUpObject(command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].codWast3).getLookUpDesc()}</td>
												</c:if>
												<c:if
													test="${command.sanitationStaffTargetDto.sanType eq 2}">
													<td class="text-center">${command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].disposalSite}</td>
												</c:if>
												<c:if
													test="${command.sanitationStaffTargetDto.sanType eq 3}">
													<td class="text-center">${command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].routeName}</td>
												</c:if>
												<td class="text-center">${command.sanitationStaffTargetDto.sanitationStaffTargetDet[d].sandVolume }
													Kgs</td>

												<c:set var="d" value="${d + 1}" scope="page" />
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('Sanitation Staff Target Details');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>
							<spring:message code="solid.waste.print" text="Print" />
						</button>
						<apptags:backButton url="VehicleTarget.html"></apptags:backButton>

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>