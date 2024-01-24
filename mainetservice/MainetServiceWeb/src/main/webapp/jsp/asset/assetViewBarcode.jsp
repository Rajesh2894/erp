<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script>
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
	});
	$(function() {

		$(".table").tablesorter({

			cssInfoBlock : "avoid-sort",

		});

	});
</script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="asset.search.search" />
			</h2>
			<apptags:helpDoc url="AssetSearch.html"></apptags:helpDoc>
		</div>
		<div class="pagediv">
			<div class="widget-content padding">

				<form:form id="assetSearchId" name="assetSearch"
					class="form-horizontal" action="AssetRegistration.html"
					method="post">
					<div id="receipt">
					<div class="form-group">
							<div
								class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3>
							
								<strong></strong>
							</div>
					</div>
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="overflow-visible">
						<div id="export-excel">
						<table class="table table-bordered table-condensed ">
								<thead>
									<tr>
										<th width="45%"><spring:message code="Serial No."
													text="Serial No." /></th>
										<th width="55%"><spring:message code=""
													text="Barcode" /></th>
									</tr>
								</thead>
									<tfoot>
										<tr>
											<th colspan="2" class="ts-pager form-horizontal">
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
													<option value="all">All Records</option>
											</select> <select class="pagenum input-mini form-control"
												title="Select page number"></select>
											</th>
										</tr>
									</tfoot>
								
								<tbody>
									<c:forEach items="${command.barcodeList}"
										var="barcode" varStatus="count">
										<tr>
											<td>${barcode.serialNo}</td>
											<td align="center"><img src="AssetSearch.html?barcodeImg=&assetId=${barcode.astId}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							</div>
							</div>
							</div>
							
								<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-success hidden-print" type="button">
						<i class="fa fa-print"></i> Print
					</button>
						<%-- <apptags:backButton url="AssetSearch.html"></apptags:backButton> --%>
						<button type="button" class="btn btn-danger" name="button" id="Back"
						value="Back" onclick="window.location.href = '${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}'">
						<spring:message code="asset.information.back" />
					</button>
				</div>
					</form:form>
					</div>
					</div>
                    </div>
                    </div>