<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script>
    function printContent(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
    }
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form:form action="EChallanEntry.html" method="POST" class="form-horizontal" id="printForm" commandName="command">
				<div class="row">
					<div class="col-xs-12 text-center">
						<h3 class="margin-bottom-0">
							<spring:message code="EChallan.PSCL" text="PSCL" />
						</h3>
					</div>
				</div>
				
				<br></br>
				<div>
					<p>
						<spring:message code="EChallan.acknowledgement.para1" />
						<span class="text-bold">${command.challanMasterDto.offenderName}</span> 
						<spring:message code="EChallan.acknowledgement.para2" />
						<span class="text-bold">${command.challanMasterDto.raidNo}</span>
						<spring:message code="EChallan.acknowledgement.para3" />
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>
						<spring:message code="EChallan.acknowledgement.para4" />
						<br></br>
						
						<!-- Table start -->
						<c:if test="${not empty  command.challanItemDetailsDtoList}">
							<h4 class="panel-title table margin-top-10" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2">
									<spring:message code="EChallan.itemDetails" text="Item Details" />
								</a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<table id="CollectionDetails"
									class="table table-striped table-bordered margin-top-10">
									<tr>
										<th><spring:message code="EChallan.itemNo" text="Item Number" /></th>
										<th><spring:message code="EChallan.itemName" text="Item Name" /></th>
										<th><spring:message code="EChallan.itemDesc" text="Item Description" /></th>
										<th><spring:message code="EChallan.quantity" text="Quantity" /></th>
									</tr>
									<tbody>
										<c:forEach  items="${command.challanItemDetailsDtoList}"
											var="data" varStatus="loop">
											<tr>
												<td class="text-center">${data.itemNo}</td>
												<td class="text-center">${data.itemName}</td>
												<td class="text-center">${data.itemDesc}</td>
												<td class="text-center">${data.itemQuantity}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
						
						<c:if test="${not empty  command.challanMasterDto.echallanItemDetDto}">
							<h4 class="panel-title table margin-top-10" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2">
									<spring:message code="EChallan.itemDetails" text="Item Details" />
								</a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<table id="CollectionDetails"
									class="table table-striped table-bordered margin-top-10">
									<tr>
										<th><spring:message code="EChallan.itemNo" text="Item Number" /></th>
										<th><spring:message code="EChallan.itemName" text="Item Name" /></th>
										<th><spring:message code="EChallan.itemDesc" text="Item Description" /></th>
										<th><spring:message code="EChallan.quantity" text="Quantity" /></th>
									</tr>
									<tbody>
										<c:forEach  items="${command.challanMasterDto.echallanItemDetDto}"
											var="data" varStatus="loop">
											<tr>
												<td class="text-center">${data.itemNo}</td>
												<td class="text-center">${data.itemName}</td>
												<td class="text-center">${data.itemDesc}</td>
												<td class="text-center">${data.itemQuantity}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
						<!-- Table end -->

						<br></br>
						<span class="text-bold"><spring:message code="EChallan.acknowledgement.para5" /></span>
						<br></br>
						<spring:message code="EChallan.acknowledgement.para6" />
						</br>
						<spring:message code="EChallan.acknowledgement.para7" />
						</br>
						<spring:message code="EChallan.acknowledgement.para8" />
					</p>
				</div>
				
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="EChallan.print" text="Print" />
					</button>

					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="EChallan.close" text="Close"></spring:message>
					</button>
				</div>
				
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

