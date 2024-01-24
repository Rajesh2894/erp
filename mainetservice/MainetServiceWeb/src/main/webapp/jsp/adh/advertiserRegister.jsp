<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/advertiserRegister.js"></script>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertiser.register.table.title"
					text="Advertisement Permit Register" />
			</h2>
			<apptags:helpDoc url="AdvertiserRegister.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				
				<div class="form-group">
					<div class="widget-content padding">
						<!-- FORM INPUT STARTS FROM HERE -->
						<label for="text-1"
							class="col-sm-2 control-label required-control"> <spring:message
								code="adh.from.date" text="From Date" /> <span class="mand"></span>
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="" id="fromDate"
									class="form-control datepicker" maxlength="10"
									placeholder="Enter From Date"
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>

						<label for="text-1"
							class="col-sm-2 control-label required-control"> <spring:message
								code="adh.to.date" text="To Date" /> <span class="mand"></span>
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="" id="toDate" class="form-control datepicker"
									maxlength="10" placeholder="Enter To Date"
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>

					</div>
				</div>
				
				
				<!--onClick="report('receivableReport.html','report')"  -->
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2"
						onClick="viewAdvertiserForm(this)"
						title="View Report">
						<spring:message code="" text="View Report"></spring:message>
					</button>

					<button type="button" class="btn btn-warning"
						onclick="window.location.href='AdvertiserRegister.html'"
						title="Reset">
						<spring:message code="" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>


<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/advertiserRegister.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<script language="javascript">
	function printhiv(printpage) {

		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertiser.register.table.title"
					text="Advertisement Permit Register" />
			</h2>
			<apptags:helpDoc url="AdvertiserRegister.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				
				<div class="form-group">
					<div class="widget-content padding">
						<!-- FORM INPUT STARTS FROM HERE -->
						<label for="text-1"
							class="col-sm-2 control-label required-control"> <spring:message
								code="adh.from.date" text="From Date" /> <span class="mand"></span>
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="" id="fromDate"
									class="form-control datepicker" maxlength="10"
									placeholder="Enter From Date"
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>

						<label for="text-1"
							class="col-sm-2 control-label required-control"> <spring:message
								code="adh.to.date" text="To Date" /> <span class="mand"></span>
						</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="" id="toDate" class="form-control datepicker"
									maxlength="10" placeholder="Enter To Date"
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>

					</div>
				</div>
				
				
				<!--onClick="report('receivableReport.html','report')"  -->
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2"
						onClick="viewAdvertiserForm(this)"
						title="View Report">
						<spring:message code="" text="View Report"></spring:message>
					</button>

					<button type="button" class="btn btn-warning"
						onclick="window.location.href='AdvertiserRegister.html'"
						title="Reset">
						<spring:message code="" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div> --%>