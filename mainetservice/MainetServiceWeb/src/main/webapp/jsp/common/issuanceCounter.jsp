<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/common/issuanceCounter.js"></script>

<script>
$(document).ready(function(){
	  $(window).resize(); // call once for good measure!
	  
	  $('.tab-buttons button:first-child').addClass('tab-selected');
	  var tabs = $('.tab-buttons button');
	  tabs.on('click', function(){
		 $(this).addClass('tab-selected').siblings().removeClass("tab-selected");
	  });
	 
	});

</script>
<style>
	#issuanceCounterTB{
		table-layout:fixed;
    	width: 98% !important;
	}
	 
</style>
<div id="searchAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="issuance.counter.header"  text="Issuance Counter"/>
				</h2>
				<apptags:helpDoc url="IssuanceCounter.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">

					<form:form id="issuance" name="issuance" class="form-horizontal"
						action="IssuanceCounter.html" method="post">

						<div class="compalint-error-div">
							<jsp:include page="/jsp/tiles/validationerror.jsp" />
							<div
								class="warning-div error-div alert alert-danger alert-dismissible"
								id="errorDiv"></div>
						</div>
						<form:hidden path="applicationSelected" id="applicationSelected" />
					
					
						
					
						<div class="text-left clear padding-10 tab-buttons">
							<button type="button" class="btn btn-warning" 
								name="button-submit" style="" id="button-submit" onclick="getPendingTaskList()">
								<spring:message code="issuance.counter.pending" text="Pending" />
								<span id="pendingITCount"> </span>
							</button>
		
							<button type="button" class="button-input btn btn-success"
								onclick="getIssuedTaskList()"
								name="button-submit" style="" id="button-submit">
								<spring:message code="issuance.counter.issued" text="Issued" />
								<span id="IssuedITCount"> </span>
							</button>
						</div>

						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="issuanceCounterTB">
								<thead>
									<tr>
										<th width="10%"><input type="checkbox" id="selectall" /> <spring:message code="cfc.all" text="All" /></th>
										
										<th width="14%" align="center"><spring:message code="issuance.counter.ReferenceNo" text="Reference No."/></th>
										<th width="14%" align="center"><spring:message code="issuance.counter.Type" text="Type"/></th>
										<th width="11%" align="center"><spring:message code="issuance.counter.ApplicationDate" text="Application Date"/></th>
										<th width="11%" align="center"><spring:message code="issuance.counter.Applicant'sName" text="Applicant's name"/></th>
										<th width="12%" align="center"><spring:message code="issuance.counter.ServiceName" text="Service Name"/></th>
										<th width="15%" align="center"><spring:message code="issuance.counter.Department" text="Department"/></th>
										<th width="15%" align="center"><spring:message code="issuance.counter.date" text="Issued Date"/></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
									
						<div class="text-center">
								<button type="button" class="btn btn-success issueBT" title="Submit"
										onClick="saveIssuanceCounter(this);">
										<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
										<spring:message code="issuance.counter.issue" text="Issue" />
									</button>
								
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel" style=""
									onclick="window.location.href='AdminHome.html'" id="button-Cancel">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="issuance.back" text="Back" />
								</button>	
							</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
