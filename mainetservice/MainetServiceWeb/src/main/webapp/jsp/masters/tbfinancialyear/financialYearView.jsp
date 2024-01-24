<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/script-library.js"></script>

<script>
	var today = new Date();
	var fromYear = today.getFullYear();
	$("#fromdate").datepicker({

		dateFormat : 'dd/mm/yy',
		changeDate : false,
		changeMonth : false,
		changeYear : true,
		defaultDate : '1/4/' + fromYear,
		stepMonths : 0,
		beforeShowDay : function(date) {
			if (date.getDate() == 1) {
				return [ true, '' ];
			}
			return [ false, '' ];
		}
	});

	var toYear = fromYear + 1;

	function getToDate() {
		var selectedYear;
		$("#todate").focusin(function(event) {
			var startDate = $("#fromdate").datepicker('getDate');
			selectedYear = startDate.getFullYear();
			var toYearr = selectedYear + 1;
			$('#todate').val("31/03/" + toYearr);
		});
	}

	$(document)
			.ready(
					function() {
						var status = $('#status option:selected').attr("code");
						if (status == 'OPN') {
							$('#month-status-div').show();
						} else if (status == 'HCL') {
							$('#month-status-div').hide();
						}

						if ($('.checkboxall:checked').length == $('.checkboxall').length) {
							$('#select_all').prop('checked', true);
						} else {
							$('#select_all').prop('checked', false);
						}

						$("#select_all").change(
								function() {
									$("input:checkbox").prop('checked',
											$(this).prop("checked"));
								});

						$('.checkboxall')
								.on(
										'click',
										function() {
											if ($('.checkboxall:checked').length == $('.checkboxall').length) {
												$('#select_all').prop(
														'checked', true);
											} else {
												$('#select_all').prop(
														'checked', false);
											}
										});

						if ($('#modevalue').val() == "N") {
							$('#editOrViewRecord').find('*').attr('disabled',
									'disabled').removeClass("mandClassColor");
							$('#submit').hide();
							$('#refreshData').hide();
						}

						if ($('#modevalue').val() == "Y") {
							$("#fromdate").datepicker("destroy");
						}
					});
</script>
<div class="financialyeardiv">
	<c:if test="${tbFinancialyear.hasError eq false}">
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li class="active">Financial Year Master</li>
		</ol>
	</c:if>

	<c:url value="${mode}" var="form_mode" />
	<div class="form-div" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2 title="Financial Year"><spring:message code="finyear.form.caption" text="Financial Year Master"/></h2>
				<apptags:helpDoc url="Financialyear.html" helpDocRefURL="Financialyear.html"></apptags:helpDoc>
				<div class="additional-btn">
				 
			</div>
			</div>
			<div class="widget-content padding">
				<form:form method="post" action="Financialyear.html"
					name="finyeardetail" id="finyeardetail" class="form-horizontal"
					modelAttribute="tbFinancialyear">
					<div id="editOrViewRecord">
						<form:hidden path="editOrView" value="${editOrView}"
							id="modevalue" />
						<form:hidden path="faYear" />

						<c:if test="${form_mode =='update'}">
						<h4>Details</h4>
							<div class="form-group">

								<label class="col-sm-2 control-label" for="fromDate"><spring:message
								code="finyear.form.fromDate"/></label>
								<div class="col-sm-4">
									<form:input path="fromDate" cssClass="form-control"
										disabled="true" id="fromdate" onclick="getToDate()"
										placeholder="DD/MM/YYYY" />
								</div>

								<label class="col-sm-2 control-label" for="toDate"><spring:message code="finyear.form.toDate" /></label>
								<div class="col-sm-4">
									<form:input path="toDate" cssClass="form-control" id="todate"
										disabled="true" placeholder="DD/MM/YYYY" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label" for="status"><spring:message code="finyear.form.status" /></label>
								<div class="col-sm-4">
									<%-- <c:forEach items="${tbFinancialyear.financialyearOrgMap}" var="finYearDetails" varStatus="status">	 --%>

									<form:select path="financialyearOrgMap[${0}].yaTypeCpdId"
										id="status" cssClass="form-control" onchange="showMonthDiv()">
										<form:option value="0"><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>

										<c:forEach items="${yeartype}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>

									</form:select>
									<form:hidden path="financialyearOrgMap[${0}].mapId" />
								</div>
							</div>
						</c:if>

					</div>
					<div id="month-status-div">
						<h4>Month Details</h4>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="startMonth"
								id="startMonth-lbl"><spring:message
									code="finyear.form.startmonth" /></label>
							<div class="col-sm-4">
								<form:select path="financialyearOrgMap[${0}].faFromMonth"
									cssClass="form-control" id="startMonth"
									onchange="selectMonth()" disabled="true">
									<form:option value=""><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>
									<c:forEach items="${monthList}" var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>

							</div>


							<label class="col-sm-2 control-label" for="endMonth"
								id="endMonth-lbl"><spring:message
									code="finyear.form.endmonth" /></label>
							<div class="col-sm-4">
								<form:select path="financialyearOrgMap[${0}].faToMonth"
									cssClass="form-control" id="endMonth" disabled="true">
									<form:option value=""><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>
									<c:forEach items="${monthListAll}" var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>

							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="monthStatus"
								id="monthStatus-lbl"><spring:message
									code="finyear.form.monthstatus" /></label>
							<div class="col-sm-4">
								<form:select path="financialyearOrgMap[${0}].faMonthStatus"
									id="monthStatus" cssClass="form-control" disabled="true">
									<form:option value="0"><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>
									<c:forEach items="${monthtype}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>
					</div>
					<div class="text-center padding-bottom-20" id="divSubmit">
						<button type="button" title="Back" name="back"
							onClick="window.location.href='Financialyear.html'"
							class="btn btn-danger hidden-print"><spring:message
									code="back" text="Back"/></button>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>