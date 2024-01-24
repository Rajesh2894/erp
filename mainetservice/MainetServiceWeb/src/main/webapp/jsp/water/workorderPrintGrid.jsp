<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/water/workorderPrintGrid.js">
    
</script>
<script type="text/javascript">
    $(document)
	    .ready(
		    function() {
			$("#datatables").dataTable(
				{
				    "oLanguage" : {
					"sSearch" : ""
				    },
				    "aLengthMenu" : [ [ 5, 10, 15, -1 ],
					    [ 5, 10, 15, "All" ] ],
				    "iDisplayLength" : 5,
				    "bInfo" : true,
				    "lengthChange" : true

				});
			var langId = $("#langId").val();
			/* alert(langId); */
			var url = "ServiceMaster.html?refreshServiceData";
			var deptId = 200000001;

			var returnData = "deptId=" + deptId
			/* 	alert(returnData); */
			$
				.ajax({
				    url : url,
				    method : "POST",
				    data : returnData,
				    success : function(response) {
					/* alert(response); */
					$("#serviceId").html('');
					$('#serviceId')
						.append(
							$("<option></option>")
								.attr("value",
									"")
								.attr("code",
									"")
								.text(
									getLocalMessage('Select')));
					/* alert($("#langId").val()); */
					if (langId == 1) {
					    if (response != "") {
						$
							.each(
								response,
								function(index,
									value) {

								    $(
									    '#serviceId')
									    .append(
										    $(
											    "<option></option>")
											    .attr(
												    "value",
												    value.smServiceId)
											    .attr(
												    "code",
												    value.smShortdesc)
											    .text(
												    value.smServiceName));
								});

						$('#serviceId').append(
							$("<option></option>")
								.attr("value",
									-1)
								.text("All"));
					    }

					} else {

					    if (response != "") {
						$
							.each(
								response,
								function(index,
									value) {
								    $(
									    '#serviceId')
									    .append(
										    $(
											    "<option></option>")
											    .attr(
												    "value",
												    value.smServiceId)
											    .attr(
												    "code",
												    value.smShortdesc)
											    .text(
												    value.smServiceNameMar));
								});

						$('#serviceId').append(
							$("<option></option>")
								.attr("value",
									-1)
								.text("All"));
					    }
					}
				    },
				    error : function(xhr, ajaxOptions,
					    thrownError) {
					var errorList = [];
					errorList
						.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				    }
				});
		    });

    $(document).on("load",function() {
	var langId = $("#langId").val();
    });
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget" id="widget2">
		<div class="widget-header">
			<h2>
				<spring:message code="water.order.gener.print"
					text="Work Order Genration and Printing" />
			</h2>
		</div>
		<div class="widget-content padding">
			<!-- <div class="error-div" style="display: none;" id="serviceRuleDefMas"></div> -->
			<div class="error-div alert alert-danger alert-dismissible"
				style="display: none;" id="serviceRuleDefMas"></div>
			<form:form id="serviceruleDefForm" modelAttribute="tbApprejMas"
				name="tbApprejMas" class="form-horizontal" method="post"
				commandName="tbApprejMas">

				<!-- Here i have to start the code -->
				<input type="hidden" value="${LanguageId}" id="langId">
				<div class="form-group">
					<%-- <label class="col-sm-2 control-label">Department:</label>
					<div class="col-sm-4">
						<input type="hidden" id="langId" value="${languageId}" /> <select
							class="form-control" id="deptId"
							onchange="refreshServiceData(this)">
							<option value="">Select</option>
							<c:choose>
								<c:when test="${languageId eq 1}">
									<c:forEach items="${deptList}" var="deptData">
										<option value="${deptData.dpDeptid }"
											code="${deptData.dpDeptcode }">${deptData.dpDeptdesc }</option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${deptList}" var="deptData">
										<option value="${deptData.dpDeptid }"
											code="${deptData.dpDeptcode }">${deptData.dpNameMar }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select>
					</div> --%>

					<label class="col-sm-2 control-label"><spring:message
							code="water.order.service" text="Service Name:" /></label>
					<div class="col-sm-4">
						<select class="form-control" id="artServiceId" name="artServiceId">
							<option><spring:message
							code="water.select" text="Select" /></option>
							<c:forEach items="${serviceMasList}" var="servceMasData">
								<option value="${servceMasData.smServiceId }"
									code="${servceMasData.smShortdesc }">${servceMasData.smServiceName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div id="divid">
					<div class="text-center padding-top-10 padding-bottom-10">
						<input class="btn btn-success"  onClick="searchServiceMst2(this);"
							value="<spring:message
							code="water.search" text="Search " />" type="button"> <input
							class="btn btn-warning"  value="<spring:message
							code="water.btn.reset" text="Reset" />" type="Reset"
							onclick="resetHomePage()">
					</div>


					<!-- <div>
						<table id="grid"></table>
						<div id="pagered"></div>
					</div> -->



					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="datatables">
							<thead>
								<tr>
									<%-- <th scope="col" width="10%" align="center"><spring:message
											code="ser.no" text="Sr. No." /> --%>
									<th scope="col" width="12%" align="center"><spring:message
											code="work.order.application.no" text="Application No" />
									<th scope="col" width="22%" class="text-center"><spring:message
											code="work.order.consumer.name" text="Consumer Name" /></th>
									<th scope="col" width="22%" align="center"><spring:message
											code="work.order.plumber.name" text="Plumber Name" /></th>
									<th scope="col" width="6%" class="text-center"><spring:message
											code="work.order.print" text="Print" /></th>
									<th scope="col" width="6%" class="text-center"><spring:message
											code="work.ordergen.view" text="View" /></th>
								</tr>
							</thead>

							<tbody class="text-center">
								<c:forEach items="${workodergenraitonList}" var="mstDto">
									<tr>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

				</div>
			</form:form>

		</div>
	</div>
</div>

