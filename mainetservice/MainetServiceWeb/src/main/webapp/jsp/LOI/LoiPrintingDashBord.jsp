<div id="onlineServices" title="D2K Application" style="display: none"
	class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="js/loi/loidashboard.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<ol class="breadcrumb">
      <li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
      <li>Home</li>
      <li>LOI</li>
      <li class="active">LOI Print</li>
    </ol>
<div class="content">
	<div class="animated slideInDown">
		<div class="widget">
			<div class="widget-header">
				<h2 id="statusHeading">
					<spring:message code="citizen.services.heading"></spring:message>
				</h2>
				<div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
			</div>
			<div class="widget-content padding">
				<%-- <ul id="demo2" class="nav nav-tabs">
					<li class="active"><a href="#Inprocess" data-toggle="tab"><spring:message code="" text="LOI Details" /></a></li>
				</ul> --%>
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<ul><li><label id="errorId"></label></li></ul>
				</div>
				<form:form action="LoiPrintingController.html" method="POST"
					name="LoiPrintData" class="form-horizontal" id="LoiPrintData">
					<form:hidden path="orgId" id="orgId" />
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="workflow.type.dept.name" text="Department Name" /></label>
						<div class="col-sm-4">
							<form:select path="searchDto.deptId" id="deptId"
								onchange="getServices(this);"
								class="form-control mandColorClass chosen-select-no-results">
								<form:option value="0">
									<spring:message code="selectdropdown" text="select" />
								</form:option>
								<c:forEach items="${command.deptList}" var="objArray">
									<c:if test="${userSession.languageId eq 1}">
										<form:option value="${objArray[0]}">${objArray[1]}</form:option>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:option value="${objArray[0]}" label="">${objArray[2]}</form:option>
									</c:if>
								</c:forEach>
							</form:select>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="workflow.type.service.name" text="Service Name" /></label>
						<div class="col-sm-4">
							<form:select path="searchDto.serviceId" id="serviceId"
								class="form-control chosen-select-no-results">
								<form:option value="0">
									<spring:message code="selectdropdown" text="Select" />
								</form:option>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label "><spring:message
								code="eip.citizen.repayment.applicationid" text="Application No" /></label>
						<div class="col-sm-4">
							<form:select path="searchDto.applicationId" id="appId"
								class="form-control mandColorClass chosen-select-no-results">
								<form:option value="0">
									<spring:message code="selectdropdown" text="select" />
								</form:option>
								<c:forEach items="${command.loiDetailList}" var="objArray">
									<form:option value="${objArray[1]}" label="">${objArray[1]}</form:option>
								</c:forEach>
							</form:select>
						</div>

						<label class="col-sm-2 control-label "><spring:message
								code="eip.dashboard.loino" text="LOI No" /></label>
						<div class="col-sm-4">
							<form:select path="searchDto.loiNo" id="loiNo"
								class="form-control mandColorClass chosen-select-no-results">
								<form:option value="0">
									<spring:message code="selectdropdown" text="select" />
								</form:option>
								<c:forEach items="${command.loiDetailList}" var="objArray">
									<form:option value="${objArray[2]}" label="">${objArray[2]}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
								code="" text="Applicant Name" /></label>
						<div class="col-sm-4">
							<form:select path="searchDto.applicantName" id="applicantName"
								class="form-control mandColorClass chosen-select-no-results">
								<form:option value="">
									<spring:message code="selectdropdown" text="select" />
								</form:option>
								<c:forEach items="${command.applNameList}" var="aplList">
									<c:if test="${userSession.languageId eq 1}">
										<form:option value="${aplList.apmFname} ${aplList.apmLname}">${aplList.apmFname} ${aplList.apmLname}</form:option>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:option value="${aplList.apmFname} ${aplList.apmLname}">${aplList.apmFname} ${aplList.apmLname}</form:option>
									</c:if>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<input type="hidden" value="" id="deptIdHidden" />
					<input type="hidden" value="" id="serviceIdHidden" />
					<input type="hidden" value="" id="appIdHidden" />
					<input type="hidden" value="" id="loiNoHidden" />
					<input type="hidden" value="" id="applicantNameHidden" />
					<input type="hidden" value="${userSession.languageId}" id="langId"/>

					<div class="text-center padding-bottom-20">
						<button type="button" class="btn btn-success" id="searchData">
							<spring:message code="master.search" text="Search" />
						</button>
						<button type="Reset" class="btn btn-warning" id="reset"
							onclick="back();"> <spring:message code="eip.commons.reset" />
						</button>
						<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
					</div>


					<div class="tab-content">
						<div class="tab-pane fade active in" id="Inprocess">
							<div id="LoiDiv">
								<table id="loigrid"></table>
								<div id="loipagered"></div>
							</div>
						</div>

					</div>
			</div>
			 </form:form>
		</div>
	</div>
</div>

